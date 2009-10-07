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
import java.util.concurrent.TimeUnit;

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

//
//        public void run() {
//                try{
//
//                        //Соединяемся с хостом.
//                        InetAddress ip=Inet4Address.getByName(ip_str);
//                        socket=new Socket(ip, 6666);
//                        System.out.println("Client: Socket connected");
//
//                        //Получаем потоки, связаные с сокетом.
//                        out=new ObjectOutputStream(socket.getOutputStream());
//                        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
//
//                        //Читаем входящий поток.
//                        while(true) {
//                                Thread.sleep(1000);
//                                if(!game.isGameEnded()) {
//                                        Move move=(Move)in.readObject();
//                                        game.setData(move.x, move.y, move.fishka);
//                                }
//                                else {
//                                        NewGame newGame=(NewGame)in.readObject();
//                                        game.restartGame(newGame.firstMove);
////                                        game.setNewGameStarted();
//                                }
//                        }
//                }
//                catch(InterruptedException exc) {
//                        new Error(game,"Client-thread interrupted!");
//                }
//                catch(ClassCastException exc) {
//                        new Error(game,exc.toString());
//                }
//                catch(Exception exc) {
//                        System.out.println(exc);
//                        new Error(game,exc.toString());
//                }
//        }
        /**Отправка данных по сети.
         *
         * @param move сериализованный объект, содержащий информацию о совершенном ходе.
         * @throws java.io.IOException
         */
        public void sendData(Move move) throws IOException {
                out.writeObject(move);
                out.flush();                
        }
        /**
         * Отправка данных о начале новой игры.
         * @param newGame объект, содержащий информацию о новой игре.
         * @throws java.io.IOException
         */
        public void sendNewGame(NewGame newGame) throws IOException {                
                //Сериализация и запись объекта в сетевой поток.
                out.writeObject(newGame);
                out.flush();
                System.out.println("Client:Object written");
        }

        public void run() {
                try {
                        InetAddress ip=Inet4Address.getByName(ip_str);
                        socket=new Socket(ip,Helper.PORT);
                        
                        //Создаем потоки, связанные с сокетом.
                        out=new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());

                        //Читаем сообщения из сети.
                        while(true) {
                                TimeUnit.MILLISECONDS.sleep(750);
                                Object obj=in.readObject();
                                if(obj instanceof Move) {
                                        Move move=(Move)obj;
                                        game.setData(move.x, move.y, move.fishka);
                                }
                                else {
                                        NewGame newGame=(NewGame)obj;
                                        game.restartGame(newGame.firstMove);
                                }
                        }

                } catch (IOException ex) {
                        new Error(game, "Connection problem");
                } catch (InterruptedException ex) {
                        new Error(game, ex.toString());
                } catch (ClassNotFoundException ex) {
                        new Error(game, ex.toString());
                } catch (ClassCastException ex) {
                        new Error(game, ex.toString());
                }
        }
}
