/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.Serializable;

/**
 *Класс, содержащий координаты и значение фишки, для передачи данных по сети.
 * @author kaligula
 */
public class Move implements Serializable{
        /**
         * координаты клетки.
         */
        public int x,y;
        /**
         * Объект-фишка установленный в клетку.
         */
        public Fishka fishka;
        /**
         * Конструктор объекта, содержащего информацию о ходе.
         * @param row х координата.
         * @param col у координата.
         * @param f объект-фишка.
         */
        public Move(int row,int col,Fishka f) {
                x=row;
                y=col;
                fishka=f;
        }
}
