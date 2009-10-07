/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *Информационная панель.
 * @author kaligula
 */
public class InfoPanel extends JPanel{
        private JLabel information;

        /**
         * Конструктор информационной панели.
         */
        public InfoPanel() {
                setLayout(new BorderLayout());

                information=new JLabel("gl&hf");
                information.setFont(Helper.SMALL_FONT);
//                information.setHorizontalTextPosition(JLabel.RIGHT);
//                information.setHorizontalAlignment(JLabel.RIGHT);
                add(information,BorderLayout.EAST);
        }
        public void setInfoLabel(String text) {
                information.setText(text); 
        }
}
