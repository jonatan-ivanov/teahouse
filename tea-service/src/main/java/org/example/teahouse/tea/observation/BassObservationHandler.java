package org.example.teahouse.tea.observation;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

import feign.micrometer.FeignContext;
import io.micrometer.common.lang.NonNull;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import jakarta.annotation.Nonnull;
import org.example.teahouse.core.observation.AbstractMidiObservationHandler;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("midi")
public class BassObservationHandler extends AbstractMidiObservationHandler<FeignContext> {

    private final MidiChannel bass;

    public BassObservationHandler(MeterRegistry registry) throws MidiUnavailableException {
        super(registry);
        this.bass = synthesizer.getChannels()[0];
        // Slap Bass 1 is bank #0 preset #36
        this.bass.programChange(synthesizer.getAvailableInstruments()[36].getPatch().getProgram());
    }

    @Nonnull
    @Override
    protected Note contextToNote(@Nonnull FeignContext context) {
        // C major pentatonic scale from E2 on the bass: E2 (40), G2(43), A2 (45), C3 (48), D3 (50), E3 (52)
        int[] notes = {40, 43, 45, 48, 50, 52};
        return new Note(this.bass, randomNote(notes), 64, duration(context));
    }

    @Override
    public boolean supportsContext(@NonNull Observation.Context context) {
        // Only for water-service, there is also tealeaf-service
        return context instanceof FeignContext feignContext
            && feignContext.getCarrier().url().contains("/waters/");
    }
}
