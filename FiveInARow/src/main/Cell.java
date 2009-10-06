/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *Класс, описывающий клетку, создаваемую на основе кнопки.
 * @author kaligula
 */
public class Cell extends JButton implements ActionListener{
        private final int ID;               //Уникальный номер клетки, необходим для получения координат на поле.
        private boolean first=true;     //Первый клик на клетке?
        private GameFrame gamefrm;
        private Fishka fishka;
        /**
         * Конструктор, создающий игровую клетку.
         * @param gf ссылка на игровое поле.
         * @param f объект-фишка.
         * @param i уникальный номер клетки.
         */
        public Cell(GameFrame gf,Fishka f,int i) {
                super();
                gamefrm=gf;
                fishka=f;
                ID=i;                
                setPreferredSize(Helper.CELL_SIZE);
                setFocusPainted(false);
                addActionListener(this); 
        } 
        /**Нажатие на клетке.
         * @param e
         */
        public void actionPerformed(ActionEvent e) {
                if(first && gamefrm.isMoveDone()) {
                        //Исходя из значения номера клетки, получаем ее координаты и устанавливаем новое значение на поле.
                        int x,y;
                        x=ID/Helper.SIDE_LENGTH;
                        y=ID%Helper.SIDE_LENGTH;                        
                        gamefrm.setData(x, y,fishka,false);
                        first=false;
                }
                
        }
        /**Устанавливает новую иконку (фишку) для данной клетки.
         *
         * @param icon новая иконка клетки.
         */
        public void setField(Icon icon) {
                setIcon(icon);
                setRolloverEnabled(false);
        }
        /**
         * Получить уникальный номер клетки.
         * @return возвращает номер клетки.
         */
        public int getID() {
                return ID;
        }
}
