/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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
        public static final Color DEFAULT_COLOR=new Color(238,238,238);
        public static final int PORT=6666;
        public static final File file=new File("records.dat");

        /**
         * Считывает рекорды из файла и извлекает необходимую запись
         * @param playerName имя игрока, чью запись необходимо извлечь из файла.
         * @param f фишка игрока.
         * @return возвращает объект, содержащий информацию об игроке.
         */
        @SuppressWarnings("unchecked")
        public static Player identifyPlayer(String playerName, Fishka f) {
                Player newP=null;
                HashMap<String,Player> records=null; 

                try{
                        ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
                        records=(HashMap<String, Player>) in.readObject();
                } catch (IOException e) {
                        System.out.println(e);
                } catch (ClassNotFoundException e) {
                        System.out.println(e);
                }
                               
                if(records!=null) {
                        newP = records.get(playerName);
                }
               
                if(newP==null) {
                        newP=new Player(playerName, f);
                        System.out.println("created new player");
                }
                return newP;
        }
        /**
         * Считывает рекорды из файла, перезаписывает результат текущего игрока и сохраняет рекорды обратно в файл.
         * @param player объект, содержащий информацию об игроке, результат которого надо сохранить.
         */
        @SuppressWarnings("unchecked")
        public static void saveRecords(Player player) {
                HashMap<String,Player> records=null;
                ObjectOutputStream out;
                ObjectInputStream in;
                try{                                                 
                        in=new ObjectInputStream(new FileInputStream(file));
                        records=(HashMap<String, Player>) in.readObject();
                        
                }
                catch(FileNotFoundException exc) {
                        System.out.println("File not found: "+exc);
                }
                catch(IOException exc) {
                        System.out.println(exc);
                }
                catch(ClassNotFoundException e) {
                        System.out.println(e);
                }

                if(records==null) {
                                records=new HashMap<String, Player>();
                        }
                        records.put(player.getName(), player);
                        
                try{
                        out=new ObjectOutputStream(new FileOutputStream(file));
                        out.writeObject(records);
                }
                catch(FileNotFoundException exc) {
                        System.out.println("File not found: "+exc);
                }
                catch(IOException exc) {
                        System.out.println(exc);
                }
        }        
}
