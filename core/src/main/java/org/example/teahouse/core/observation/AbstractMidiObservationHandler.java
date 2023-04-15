package org.example.teahouse.core.observation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.DisposableBean;

public abstract class AbstractMidiObservationHandler<T extends Observation.Context> implements ObservationHandler<T>, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String startTimeKey = this.getClass().getName();

    ExecutorService executorService;

    private final BlockingQueue<Note> queue;

    protected final Synthesizer synthesizer;

    protected AbstractMidiObservationHandler(MeterRegistry registry) throws MidiUnavailableException {
        this(registry, 2);
    }

    protected AbstractMidiObservationHandler(MeterRegistry registry, int nThreads) throws MidiUnavailableException {
        this.synthesizer = MidiSystem.getSynthesizer();
        this.synthesizer.open();
        this.queue = new LinkedBlockingQueue<>(100);
        this.executorService = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < nThreads; i++) {
            CompletableFuture.runAsync(this::processQueue, executorService);
        }
        Gauge.builder("midi.queue.size", this.queue::size).tag("handler", this.getClass().getSimpleName()).register(registry);
        Gauge.builder("midi.queue.remaining", this.queue::remainingCapacity).tag("handler", this.getClass().getSimpleName()).register(registry);
    }

    public record Note(@Nonnull MidiChannel channel, int noteNumber, int velocity, long duration) {}

    @Nonnull
    protected abstract Note contextToNote(@Nonnull T context);

    @Override
    public void onStart(@Nonnull T context) {
        context.put(this.startTimeKey, System.nanoTime());
    }

    @Override
    public void onStop(@Nonnull T context) {
        try {
            if (this.queue.remainingCapacity() < 1) {
                this.queue.poll();
            }
            this.queue.put(contextToNote(context));
        }
        catch (InterruptedException e) {
            this.logger.warn("Unable to schedule note to play", e);
        }
    }

    @Override
    public void destroy() {
        this.executorService.shutdownNow();
        this.synthesizer.close();
    }

    protected int randomNote(@Nonnull int[] notes) {
        return notes[(int)(Math.random() * notes.length)];
    }

    protected int randomNote(int beginInclusive, int endExclusive) {
        return (int)(Math.random() * (endExclusive - beginInclusive)) + beginInclusive;
    }

    protected long duration(@Nonnull Observation.Context context) {
        @SuppressWarnings("DataFlowIssue")
        long nanos = System.nanoTime() - context.<Long>get(this.startTimeKey);
        return Math.max(100, TimeUnit.NANOSECONDS.toMillis(nanos));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void processQueue() {
        try {
            while (true) {
                Note note = this.queue.take();
                note.channel().noteOn(note.noteNumber(), note.velocity());
                sleep(note.duration());
                note.channel().noteOff(note.noteNumber());
            }
        }
        catch (InterruptedException e) {
            this.logger.warn("Unable to fetch note to play", e);
        }
    }

    private void sleep(long duration) {
        try {
            Thread.sleep(duration);
        }
        catch (InterruptedException e) {
            this.logger.warn("Playing note was interrupted", e);
        }
    }
}
