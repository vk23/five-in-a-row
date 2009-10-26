/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *Интерфейс, описывающий игровые фишки.
 * Его реализации: Cross(), Zero() - описывают конкретные фишки.
 * @author kaligula
 */

//Интерфейс, описывающий абстрактную фишку и ее методы
public interface Fishka extends Serializable{
        /**Получить значение фишки(1 или -1), для занесения в таблицу значений
         * @return 1,-1.
         */
        int get();
        /**Получить иконку для фишки.
         * @return Icon
         */
        Icon getIcon();     
}

//Класс, описывающий крестики.
class Cross implements Fishka {
        private int i=1;
        private ImageIcon icon=new ImageIcon(Starter.class.getResource("resources/null.png"));
        public int get() {
                return i;
        }
        public Icon getIcon() {
                return icon;
        }
}
//Класс, описывающий нолики.
class Zero implements Fishka {
        private int i=-1;
        private ImageIcon icon=new ImageIcon(Starter.class.getResource("resources/cross.png"));
        public int get() {
                return i;
        }
        public Icon getIcon() {
                return icon;
        }
}