
package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JTextArea;

/**
 *JFrame which contains help information.
 * @author kaligula
 */
public class Help extends JDialog{

        public Help() {
                super();
                setTitle("Help");
                setResizable(false);
                setLocation(Helper.START_LOCATION);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                
                JTextArea area=new JTextArea(0, 40);
                String text=" To start new game you need to choose \"Create new game\" and wait for client connection " +
                        "or you may connect to existed game, in this case you need to choose \"Join to host\"  and input host's address. " +
                        "\n When the game created players should move by turns. When someone achieves victory (five counters in a row) the game will end. " +
                        "Players can restart game using main menu. " +
                        "\n Records will be written when you close the programm and will be restored when you start new game.";
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setText(text);
                area.setEditable(false);
                area.setPreferredSize(new Dimension(0, 200));
                add(area);

                setVisible(true);
                pack();
        }
}
