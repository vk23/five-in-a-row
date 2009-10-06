/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *Класс, который запускается при старте программы. Выбор загрузки клиентской части или создание новой игры.
 * @author kaligula
 */
public class Starter extends JFrame implements ActionListener{
        private JButton ok,exit;
        private JRadioButton yes,no;
        private ButtonGroup choice;
        private JTextField ip_addr;
        private boolean joinToHost=false;
        
        private Starter() {
                super("Java 5inRow game.");
                initWindow();
                
        }
        /**
         * Загрузка программы и создание стартового окна.
         * @param args
         */
        public static void main(String[] args) {
                Starter start=new Starter();
                start.pack();
                start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                start.setVisible(true);
                
        }

        //Инициализация компонентов стартового окна.
        private void initWindow() {
                setLocation(Helper.START_LOCATION);
                setLayout(new BorderLayout());
                setResizable(false);
                //Вопрос.
                JLabel question=new JLabel("Create new game?");
                question.setFont(Helper.BIG_FONT);
                
                //Переключатель для создания новой игры.
                yes=new JRadioButton("Yes",true);
                yes.setFont(Helper.SMALL_FONT);
                yes.setName("yes");
                yes.addActionListener(this);

                //Переключатель для присоединения к созданной игре.
                no=new JRadioButton("No, join this host");
                no.setFont(Helper.SMALL_FONT);
                no.setName("no");
                no.addActionListener(this);

                //Объединяем переключатели в группу.
                choice=new ButtonGroup();
                choice.add(yes);
                choice.add(no);

                //Поле ввода IP-адреса.
                ip_addr=new JTextField("127.0.0.1");
                ip_addr.setForeground(Color.GRAY);

                //Панель, на которой будут размещены переключатели и поле ввода.
                JPanel radioPanel=new JPanel(new GridLayout(2,2));
                radioPanel.add(yes);
                radioPanel.add(new JPanel());
                radioPanel.add(no);
                radioPanel.add(ip_addr);

                //Кнопка запуска программы.
                ok=new JButton("OK");
                ok.addActionListener(this);
                
//                ok.addKeyListener(new KeyAdapter() {
//                        public void keyPressed(KeyEvent ke) {
//                                System.out.println("fds");
//                                int key=ke.getKeyCode();
//                                System.out.println(key);
//                                if(key==KeyEvent.VK_ENTER) {
//                                        try {
//                                                showGameFrame();
//                                        } catch (InterruptedException ex) {
//                                                System.out.println(ex);
//                                        } catch (IOException ex) {
//                                                System.out.println(ex);
//                                        }
//                                }
//                        }
//                });

                //Кнопка выхода из программы.
                exit=new JButton("Exit");
                exit.addActionListener(this);

                //Панель кнопок.
                JPanel buttonPanel=new JPanel(new FlowLayout());
                buttonPanel.add(ok);
                buttonPanel.add(exit);
                
                //Добавляем вопрос и панели на фрейм.
                add(question,BorderLayout.NORTH);
                add(radioPanel,BorderLayout.CENTER);
                add(buttonPanel,BorderLayout.SOUTH);
        }
        /**
         *
         * @param e событие.
         */
        public void actionPerformed(ActionEvent e) {
                //Получаем название события.
                String action=e.getActionCommand();
                //Переключение переключателей =)
                if(e.getSource() instanceof JRadioButton) {
                        if(action.equals("Yes")) {
                                joinToHost = false; //создать новую игру.
                        }
                        else {
                                joinToHost = true; //присоединится к игре.
                        }
                }
                //Выход из программы.
                if(action.equalsIgnoreCase("exit")) {
                        System.exit(0); 
                }
                //Нажатие кнопки ок, запуск программы.
                if(action.equalsIgnoreCase("ok")) {
                        try {
                                showGameFrame();
                        } catch (InterruptedException ex) {                                
                        } catch(IOException ex) {
                        }
                }
        }
        //Запуск программы.
        private void showGameFrame() throws InterruptedException, IOException {
                //Скрываем стартовое окно.
                this.dispose();
                //Получаем введеный IP-адрес.
                String address=ip_addr.getText().trim();                
                Fishka f;
                //Создаем поле с белыми фишками(крестики) и присоединияемся к игре.
                if(joinToHost) {
                        System.out.println("Connecting to "+address);                        
                        f=new White();
                        GameFrame gf=new GameFrame(f,address);
                }
                //Создаем поле с черными фишками(нолики) и создаем новую игру.
                else {
                        System.out.println("Creating new game");                        
                        f=new Black();
                        GameFrame gf=new GameFrame(f);
                }                
        }
}
