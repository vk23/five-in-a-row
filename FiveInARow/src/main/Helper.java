/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

/**
 *Класс, содержащий статические данные, константы.
 * @author kaligula
 */
public class Helper {
        public static final Point START_LOCATION=new Point(640,480);    //Стартовое положение на экране(верхний левый угол фрейма).
        public static final Dimension CELL_SIZE=new Dimension(20,20);    //Размер клетки(кнопки).
        public static final int SIDE_LENGTH=20;                                   //Количество клеток в ширину, высоту(квадратное поле).
        public static final Font SMALL_FONT=new Font("Sans", Font.PLAIN, 12);
        public static final Font BIG_FONT=new Font("Sans",Font.BOLD,14);
}
