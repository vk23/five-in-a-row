/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.Serializable;

/**
 *
 * @author kaligula
 */
public class Player implements Serializable{
        private String name;
        private int win;
        private Fishka fishka;

        public Player(String name, Fishka f) {
                this.name=name;
                fishka=f;
        }

        public String getName() {
                return name;
        }

        public int getWins() {
                return win;
        }

        public Fishka getFishka() {
                return fishka;
        }
        public void increaseWinCounter() {
                win++;
        }
}
