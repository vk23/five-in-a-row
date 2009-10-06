/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *Выводит диалоговое окно с ошибкой.
 * @author kaligula
 */
public class Error extends JDialog implements ActionListener{
        /**
         * Создает диалоговое окно, содержащее информацию о произошедшей ошибке.
         * @param game фрейм являющийся владельцем данного диалого.
         * @param error информация об ошибке.
         */
        public Error(JFrame game,String error) {
                super(game,"Error");
                Point p=Helper.START_LOCATION;
                setLocation(new Point(p.x+150,p.y+200));                
                setLayout(new GridLayout(2,1));
                setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                setVisible(true);
                setModal(true);
                setResizable(false);
                
                //Создаем надпись, содержащую информацию об ошибке.                
                JLabel lab=new JLabel(error);
                lab.setFont(Helper.SMALL_FONT);
                add(lab);

                //Кнопка ОК.
                JButton ok=new JButton("OK");                
                ok.addActionListener(this);
                JPanel jpanel=new JPanel(new FlowLayout());
                jpanel.add(ok);
                add(jpanel);
                
                pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                this.dispose();
        }
}
