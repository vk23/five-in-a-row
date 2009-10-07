/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.Serializable;

/**
 *Сериализуемый объект, для передачи данных о начале новой игры.
 * @author kaligula
 */
public class NewGame implements Serializable{
        //Переменная сигнализирующая о праве первого хода.
        public boolean firstMove;
        //Конструктор объекта новой игры.
        public NewGame (boolean firstMove) {
                this.firstMove=firstMove;
        }
}
