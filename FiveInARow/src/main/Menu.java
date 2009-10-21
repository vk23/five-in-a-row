/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *Создает панель меню.
 * @author kaligula
 */
public class Menu extends JMenuBar implements ActionListener,ItemListener{
        private GameFrame gameFrame;
        private JMenu mainMenu,helpMenu, soundMenu;
        private JMenuItem start,exit,help,about, song1, song2,songoff;
        private Sound sound;
        ButtonGroup group;
        
        /**
         * Создает панель меню.
         * @param gameFrame ссылка на игровое поле.
         */
        public Menu(GameFrame gameFrame) {
                super();
                this.gameFrame=gameFrame;
                initMenu();
        }
        //Инициализация элементов меню.
        private void initMenu() {
                setFont(Helper.SMALL_FONT);                
                //Создаем главное меню.
                mainMenu=new JMenu("Main");                
                add(mainMenu);
                //Создаем меню помощи.
                helpMenu=new JMenu("Help");
                add(helpMenu);
                //Создаем меню звука.
                soundMenu=new JMenu("Sound");

                //Создаем элементы меню.
                start=new JMenuItem("New game");                
                exit=new JMenuItem("Exit");
                about=new JMenuItem("About");
                help=new JMenuItem("Help");
                song1=new JCheckBoxMenuItem("Memory Remains");
                song1.setName("song1");
                song2=new JCheckBoxMenuItem("Memory Remains 2");
                song2.setName("song2");
                songoff=new JCheckBoxMenuItem("Off");
                songoff.setName("Off");
                
                //Создаем группу чекбоксов.
                group=new ButtonGroup();
                group.add(songoff);
                group.add(song1);
                group.add(song2);
                
                //Вешаем на них лисенеры.
                start.addActionListener(this);
                exit.addActionListener(this);
                about.addActionListener(this);
                help.addActionListener(this);
                song1.addItemListener(this);
                song2.addItemListener(this);
                songoff.addItemListener(this);
                
                //Добавляем элементы в меню.
                soundMenu.add(songoff);
                soundMenu.add(song1);
                soundMenu.add(song2);
                mainMenu.add(start);
                mainMenu.add(soundMenu);
                mainMenu.addSeparator();
                mainMenu.add(exit);                
                helpMenu.add(help);
                helpMenu.add(about);
        }

        /**
         * Обработка событий меню.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
                //Название произошедшего события.
                String action=e.getActionCommand();
                //Выход из программы.
                if(action.equals("Exit")) {
                        gameFrame.saveAndExit();
                }
                //Перезапуск игры.
                else if(action.equals("New game")) {
                                try {
                                        gameFrame.restartGame();
                                } catch (IOException ex) {
                                        new Error("IOException within restartGame()");
                                }                        
                }
                else if(action.equals("About")) {
                        System.out.println("About");                        
                }
                else if(action.equals("Help")) {
                        System.out.println("Help");
                }
        }
        /**Обработка событий чекбоксов.
         * @param e событие
         */
        public void itemStateChanged(ItemEvent e) {
                if(sound!=null) {
                        sound.stopPlayback();
                        sound=null;
                }

                JCheckBoxMenuItem obj=(JCheckBoxMenuItem)e.getItem();
                String selectedSong=obj.getName();

                if(!selectedSong.equals("Off")) {
                        sound=new Sound(selectedSong);
                        if(sound.isOK()) {
                                sound.startPlayback();
                        }
                        else {                                
                                group.clearSelection();
                        }
                }                 
        }
}
