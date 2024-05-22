package org.example.teahouse.tea.observation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import jakarta.annotation.Nonnull;
import org.example.teahouse.core.observation.AbstractMidiObservationHandler;

import org.springframework.context.annotation.Profile;
import org.springframework.http.server.observation.ServerRequestObservationContext;
import org.springframework.stereotype.Component;

@Component
@Profile("melody")
public class MelodyObservationHandler extends AbstractMidiObservationHandler<ServerRequestObservationContext> {

    // D is D4, d is an octave higher (D5)
    // ₣ is mapped to F# so that every note can be encoded with one character
    private static final Map<String, Integer> noteToNoteNumber = Map.of(
        "D", 62,
        "E", 64,
        "₣", 66,
        "G", 67,
        "A", 69,
        "B", 71,
        "d", 74
    );

    private static final String melody = """
            D E G E BBB BBB AAAAAA
            D E G E AAA AAA GGGGGG
            D E G E GGGG AAAAAA ₣₣
            DD DD AAAA GGGGGGGG
            D E G E BBB BBB AAAAAA
            D E G E dddd ₣₣ GGGGGG
            D E G E GGGG AA ₣₣₣₣₣₣
            DD AAAA GGGGGGGG
            """;

    private final MidiChannel channel;

    private final List<Note> program;

    private final AtomicInteger programPosition = new AtomicInteger();

    public MelodyObservationHandler(MeterRegistry registry) throws MidiUnavailableException {
        super(registry);
        // ch #0 is set to Acoustic Grand Piano by default
        this.channel = this.synthesizer.getChannels()[0];
        this.program = Arrays.stream(melody.split("[ \n]"))
            .map(this::toNote)
            .toList();
    }

    @Nonnull
    @Override
    protected Note contextToNote(@Nonnull ServerRequestObservationContext context) {
        return program.get(programPosition.getAndIncrement() % program.size());
    }

    @Override
    public boolean supportsContext(@Nonnull Observation.Context context) {
        return context instanceof ServerRequestObservationContext;
    }

    private Note toNote(String encodedNote) {
        Integer noteNumber = noteToNoteNumber.get(encodedNote.substring(0, 1));
        return new Note(channel, noteNumber, 64, encodedNote.length() * 120L);
    }
}
