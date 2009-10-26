
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

        public Player(String name) {
                this.name=name;
        }
        
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
        public void setName(String newName) {
                name=newName;
        }

        public void setFishka(Fishka f) {
                fishka=f;
        }
}
