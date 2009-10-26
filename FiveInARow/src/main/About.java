/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *JFrame which contains information about project.
 * @author kaligula
 */
public class About extends JDialog{

        /**
         * Constructor for About-frame
         */
        public About() {
                super();
                setTitle("About");
                setResizable(false); 
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setLocation(Helper.START_LOCATION);
                setLayout(new BorderLayout());

                //Information area
                JTextArea area=new JTextArea(" This is 1v1 game \"Five in a Row\". \n Written in Java by Kaligula. \n");
                area.setEditable(false);
                add(area,BorderLayout.NORTH);

                //Just a label with text "link to project"
                JLabel linkLabel=new JLabel(" Link to project: ");
                linkLabel.setFont(Helper.SMALL_FONT);
                add(linkLabel,BorderLayout.CENTER);
                
                //This label contains URL to project
                final JLabel link=new JLabel(" https://sourceforge.net/projects/fiveinarowproj/ ");
                link.setForeground(Color.BLUE);
                
                //Opening link in default system browser.
                link.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent me) {
                                Desktop desktop=Desktop.getDesktop();
                                try {
                                        desktop.browse(new URI(link.getText().trim()));
                                } catch (Exception ex) {
                                        System.out.println(ex);
                                }
                        }
                });
                link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
                add(link,BorderLayout.SOUTH);

                setVisible(true);
                pack();
        }
}
