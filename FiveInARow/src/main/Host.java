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
import java.util.concurrent.TimeUnit;

/**
 *Класс-поток хоста. Создает сервер-сокет и общается по сети.
 * @author kaligula
 */
public class Host implements Sender,Runnable{
        private ServerSocket server;
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private GameFrame game;
        
        /**
         * Конструктор серверного потока.
         * @param gf ссылка на игровое поле хоста.
         * @throws java.io.IOException
         */
        public Host(GameFrame gf)  {
                game=gf;
                new Thread(this).start();
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
        /**
         * Отправка данных по сети для создания новой игры.
         * @param newGame
         * @throws java.io.IOException
         */
        public void sendNewGame(NewGame newGame) throws IOException {               
                //Сериализация и запись объекта в сетевой поток.
                out.writeObject(newGame);
                out.flush();
                System.out.println("Object written");
        }
        /**
         * Посылает информацию об игроке.
         * @param player объект-игрок.
         * @throws java.io.IOException
         */
        public void sendNewPlayerInfo(Player player) throws IOException{
                 //Сериализация и запись объекта в сетевой поток.
                out.writeObject(player);
                out.flush();
                System.out.println("Client:Object written");
        }
        
        public void run() {
                try {
                        server = new ServerSocket(Helper.PORT);
                        socket=server.accept();

                        //Создаем потоки, связанные с сокетом.
                        out=new ObjectOutputStream(socket.getOutputStream());
                        in=new ObjectInputStream(socket.getInputStream());

                        //Делаем клетки активными, т.к. подключился клиент
                        game.enableCells();
                        //Отсылаем данные об игроке.
                        sendNewPlayerInfo(game.getPlayer());
                        
                        //Читаем сообщения из сети.
                        while(true) {
                                TimeUnit.MILLISECONDS.sleep(750);
                                Object obj=in.readObject();

                                if(obj instanceof Player) {
                                        Player player=(Player)obj;
                                        game.setNewPlayerInfo(player);
                                }
                                else if(obj instanceof NewGame){
                                        NewGame newGame=(NewGame)obj;
                                        game.restartGame(newGame.firstMove);
                                }
                                else {
                                        Move move=(Move)obj;
                                        game.setData(move.x, move.y, move.fishka);
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
                } finally {
                        try{
                                out.close();
                                in.close();
                        }
                        catch(IOException ex) {
                        }
                }

        }        
}
