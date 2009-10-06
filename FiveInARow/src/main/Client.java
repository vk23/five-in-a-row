/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author kaligula
 */
public class Client implements Sender,Runnable{
        private Socket socket;
        private String ip_str;
        private GameFrame game;
        private ObjectOutputStream out;
        /**
         * Конструктор клиентского потока.
         * @param gf ссылка на игровое поле, принадлежащее клиенту.
         * @param ip_str IP адрес хоста.
         */
        public Client(GameFrame gf,String ip_str)  {
                game=gf;
                this.ip_str=ip_str;
                new Thread(this).start();
        }

        public void run() {
                try{
                        
                        //Соединяемся с хостом.                        
                        InetAddress ip=Inet4Address.getByName(ip_str);
                        socket=new Socket(ip, 6666);
                        System.out.println("Client: Socket connected");
                        
                        //Получаем потоки, связаные с сокетом.
                        out=new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
                        
                        //Читаем входящий поток.
                        while(true) {
                                Thread.sleep(500);
                                        Move move=(Move)in.readObject();                                       
                                        game.setData(move.x, move.y, move.fishka);                                
                        }
                }                
                catch(InterruptedException exc) {
                        new Error(game,"Client-thread interrupted!");
                }
                catch(Exception exc) {
                        System.out.println(exc);                        
                        new Error(game,"Connection problem!");
                }
        }
        /**Отправка данных по сети.
         *
         * @param move сериализованный объект, содержащий информацию о совершенном ходе.
         * @throws java.io.IOException
         */
        public void sendData(Move move) throws IOException {
                out.writeObject(move);
                out.flush();
                System.out.println("Object written");
        }
}
