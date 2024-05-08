package org.example.teahouse.tea;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class FrenchHornDemo {
    public static void main(String[] args) throws MidiUnavailableException, InterruptedException {
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        MidiChannel channel = synthesizer.getChannels()[0];
        // French Horn is bank #0 preset #60
        channel.programChange(synthesizer.getAvailableInstruments()[60].getPatch().getProgram());

        // C4 (middle C)
        int note = 60;
        try {
            channel.noteOn(note, 90);
            Thread.sleep(1_000);
        }
        finally {
            channel.noteOff(note);
        }
    }
}
