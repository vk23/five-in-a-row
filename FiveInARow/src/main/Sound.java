package main;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.swing.JMenuItem;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kaligula
 */
public class Sound {
        private Sequencer sequencer;
        private GameFrame game;
        private JMenuItem sndMenu;
        public Sound(GameFrame game, JMenuItem soundMenu) {
                this.game=game;
                sndMenu=soundMenu;
                try{                        
                        //Получаем секвенсер и трансмиттер для него
                        sequencer=MidiSystem.getSequencer(false);
                        Transmitter transmitter=sequencer.getTransmitter();
                        
                        //Получаем синтезатор и его ресивер.
                        Synthesizer synthesizer=MidiSystem.getSynthesizer();
                        Receiver receiver=synthesizer.getReceiver();

                        //Соединяем трансмиттер с ресивером.
                        transmitter.setReceiver(receiver);

                        //Открываем оба устройства.
                        sequencer.open();
                        synthesizer.open();
                        System.out.println("devices connected");
                        
                        //Загружаем миди-файл в секвенсер, ставим бесконечный повтор.
                        URL song=Starter.class.getResource("resources/song1.mid");                        
                        File file=new File("/media/kaligula/DownLoad/song1.mid");                        
                        Sequence sequence=MidiSystem.getSequence(song);
                        sequencer.setSequence(sequence);
                        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
                        
                } catch (MidiUnavailableException e) {
                        new Error(game, "Sound device is busy");
                        sndMenu.setSelected(false); 
                } catch (InvalidMidiDataException ex) {
                        System.out.println(ex);
                } catch (IOException e) {
                        System.out.println(e);
                } catch (NullPointerException e) {
                        System.out.println(e);
                        System.exit(1); 
                }
        }

        public void startPlayback() {
                sequencer.start();
        }
        public void stopPlayback() {
                sequencer.stop();
        }
}
