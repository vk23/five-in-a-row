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
        private JLabel information,players;

        /**
         * Конструктор информационной панели.
         */
        public InfoPanel() {
                setLayout(new BorderLayout());

                players=new JLabel("Player vs Player");
                players.setFont(Helper.SMALL_FONT);
                add(players,BorderLayout.WEST);
                
                information=new JLabel("gl&hf");
                information.setFont(Helper.SMALL_FONT);
                add(information,BorderLayout.EAST);
        }
        public void setInfoLabel(String text) {
                information.setText(text); 
        }

        public void setPlayersInfo(Player player1,Player player2) {
                players.setText(player1.getName()+" vs "+player2.getName()+" "+player1.getWins()+":"+player2.getWins());
        }
}
