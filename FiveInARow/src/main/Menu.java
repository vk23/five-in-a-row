/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 *Создает панель меню.
 * @author kaligula
 */
public class Menu extends JMenuBar implements ActionListener{
        private JMenu mainMenu,helpMenu;
        private JMenuItem start, sound,exit,help,about;
        /**
         * Создает панель меню.
         */
        public Menu() {
                super();
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

                //Создаем элементы меню.
                start=new JMenuItem("Start");
                sound=new JMenuItem("Sound");
                exit=new JMenuItem("Exit");
                about=new JMenuItem("About");
                help=new JMenuItem("Help");

                //Вешаем на них лисенеры.
                start.addActionListener(this);
                sound.addActionListener(this);
                exit.addActionListener(this);
                about.addActionListener(this);
                help.addActionListener(this);

                //Добавляем элементы в меню.
                mainMenu.add(start);
                mainMenu.add(sound);
                mainMenu.addSeparator();
                mainMenu.add(exit);                
                helpMenu.add(help);
                helpMenu.add(about);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                String action=e.getActionCommand();
                if(action.equalsIgnoreCase("exit")) {
                        System.exit(0);
                }
        }
}
