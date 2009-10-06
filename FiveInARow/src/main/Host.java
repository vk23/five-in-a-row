/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *Класс-поток хоста. Создает сервер-сокет и общается по сети.
 * @author kaligula
 */
public class Host implements Sender,Runnable{
        private ServerSocket server;
        private Socket socket;
        private ObjectOutputStream out;
        private GameFrame game;
        /**
         * Конструктор серверного потока.
         * @param gf ссылка на игровое поле хоста.
         * @throws java.io.IOException
         */
        public Host(GameFrame gf) throws IOException {
                //Создаем сервер-сокет и запускаем поток.
                server=new ServerSocket(6666);
                game=gf;
                new Thread(this).start();
        }

        public void run() {
                try {
                        //Принимаем входящее подключение (только 1).
                        socket = server.accept();
                        System.out.println("Host: Socket accepted: "+socket.getInetAddress().toString());
                        //Запускаем игру - делаем клетки активными.
                        game.startGame();
                        //Получаем потоки, связанные с данным соединением.
                        out=new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
                        //Читаем входящий сетевой поток.
                        while(true) {
                                Thread.sleep(500);
                                        Move move=(Move)in.readObject();                //Читаем объект из сети и десериализуем его.
                                        game.setData(move.x, move.y, move.fishka);   //Устанавливаем значения клетки, полученной из сети.
                        }                                       
                } catch(InterruptedException ex) {
                        new Error(game, "Host-thread interrupted!");
                } catch(Exception exc) {
                        System.out.println(exc);
                        new Error(game,"Connection problem!");
                }
        }
        /**
        *Отправка данных по сети, после клика на клетке.
        */
        public void sendData(Move move) throws IOException {
                //Сериализация и запись объекта в сетевой поток.
                out.writeObject(move); 
                out.flush();                 
                System.out.println("Object written");
        }
}
