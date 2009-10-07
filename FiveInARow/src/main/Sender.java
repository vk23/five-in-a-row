/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.IOException;

/**
 *Интерфейс, содержащий метод для отправки данных по сети.
 * @author kaligula
 */
interface Sender {
        /**
         * Отправить по сети объект, содержащий информацию о ходе.
         * @param move объект, содержащий информацию о ходе.
         * @throws java.io.IOException
         */
        public void sendData(Move move) throws IOException;
        /**
         * Отправить по сети объект, сигнализирующий о начале новой игры.
         * @param newGame объект, содержащий информацию о начале новой игры.
         * @throws java.io.IOException
         */
        public void sendNewGame(NewGame newGame) throws IOException;
}
