package org.example.teahouse.water.observation;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiUnavailableException;

import io.micrometer.common.lang.NonNull;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import jakarta.annotation.Nonnull;
import net.ttddyy.observation.tracing.ConnectionContext;
import net.ttddyy.observation.tracing.DataSourceBaseContext;
import org.example.teahouse.core.observation.AbstractMidiObservationHandler;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("midi")
public class PianoObservationHandler extends AbstractMidiObservationHandler<DataSourceBaseContext> {

    private final MidiChannel piano;

    public PianoObservationHandler(MeterRegistry registry) throws MidiUnavailableException {
        super(registry);
        // ch #0 is set to Acoustic Grand Piano by default
        this.piano = this.synthesizer.getChannels()[0];
    }

    @Nonnull
    @Override
    protected Note contextToNote(@Nonnull DataSourceBaseContext context) {
//        int[] notes = {36, 38, 40, 43, 45}; // C2-A2
//        int[] notes = {48, 50, 52, 55, 57}; // C3-A3

        // C major pentatonic scale: C4 (60), D4(62), E4 (64), G4 (67), A4 (69)
        int[] notes = {60, 62, 64, 67, 69};
        return new Note(this.piano, randomNote(notes), 64, duration(context));
    }

    @Override
    public boolean supportsContext(@NonNull Observation.Context context) {
        return context instanceof ConnectionContext;
    }
}
