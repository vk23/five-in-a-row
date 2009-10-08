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

        /**
         * Конструктор для создания звукового объекта.
         * @param game ссылка на игровое поле.
         * @param soundMenu ссылка на элемент меню sound.
         */
        public Sound(GameFrame game, JMenuItem soundMenu) {
                this.game=game;
                sndMenu=soundMenu;
                
                        //Получаем секвенсер
                try{
                        sequencer=MidiSystem.getSequencer(true);                        
                        System.out.println("true");
                } catch(MidiUnavailableException ex) {
                        try {
                                sequencer=MidiSystem.getSequencer(false);                                
                                System.out.println("false");
                        }
                        catch (MidiUnavailableException exc) {
                                //throw new RuntimeException(exc);
                                System.out.println(exc);
                        }
                }
                
                try{                        
                        //Получаем трансмиттер для сиквенсера.
                        Transmitter transmitter=sequencer.getTransmitter();
                        
                        //Получаем синтезатор и его ресивер.
                        Synthesizer synthesizer=MidiSystem.getSynthesizer();
                        Receiver receiver=synthesizer.getReceiver();                        
                        
                        //Соединяем трансмиттер с ресивером.
                        transmitter.setReceiver(receiver);                        
                                                
                        sequencer.open();
                        synthesizer.open();
                        
                        //Загружаем миди-файл в секвенсер, ставим бесконечный повтор.
                        URL song=Starter.class.getResource("resources/song1.mid");
                        File file=new File("/media/kaligula/DownLoad/song1_3instr.mid");
                        Sequence sequence=MidiSystem.getSequence(song);
                        sequencer.setSequence(sequence);
                        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);                                            
                        
                } catch (MidiUnavailableException e) {
                        new Error(game, "MIDI device is unavailable");
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
        /**
         * Проверяет играет ли мелодия.
         * @return Возвращает true если мелодия играет.
         */
        public boolean isRunning() {
                return sequencer.isRunning();
        }
        /**
         *Запускает проигрывание мелодии.
         */
        public void startPlayback() {
                sequencer.start();
        }
        /**
         * Останавливает проигрывание.
         */
        public void stopPlayback() {
                sequencer.stop();
        }
}
