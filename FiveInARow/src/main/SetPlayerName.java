
package main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *Диалоговое окно для ввода нового имени игрока.
 * @author kaligula
 */
class SetPlayerName extends JDialog implements ActionListener{
        private JTextField jtf;
        private JButton ok;
        private GameFrame game;
        
        public SetPlayerName(GameFrame gameFrame) {
                super();
                setTitle("New player");
                game=gameFrame;
                setLocation(Helper.START_LOCATION);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                setLayout(new FlowLayout()); 
                setVisible(true);
                
                jtf=new JTextField(13);
                add(jtf);

                ok=new JButton("OK");
                ok.addActionListener(this);
                add(ok);

                pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                String name=jtf.getText();
                game.setPlayerInfo(Helper.identifyPlayer(name));
                try {
                        game.restartGame();
                } catch (IOException ex) {
                        new Error(ex.getMessage());
                }
                this.dispose();
        }

}
