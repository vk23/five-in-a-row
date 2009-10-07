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
        private GameFrame gamefrm;
        private Fishka fishka;
        /**
         * Сигнализирует о том, что в клетке произошли изменения.
         */
        public boolean changed=false;
        
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
         * @param e событие.
         */
        public void actionPerformed(ActionEvent e) {
                if(!changed && gamefrm.isMoveDone()) {
                        //Исходя из значения номера клетки, получаем ее координаты и устанавливаем новое значение на поле.
                        int x,y;
                        x=ID/Helper.SIDE_LENGTH;
                        y=ID%Helper.SIDE_LENGTH;                        
                        gamefrm.setData(x, y,fishka,false);
                        //Флаг, сигнализирующий о том, что клетка уже была изменена.
                        changed=true;
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
