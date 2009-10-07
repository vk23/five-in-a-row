/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * Игровое поле.
 * @author kaligula
 */
public class GameFrame extends JFrame {
        private final int DIM=Helper.SIDE_LENGTH; //Размер игрового поля.
        private Cell[] cells=new Cell[DIM*DIM];    //Массив игровых клеток.
        private int[][] data=new int[DIM][DIM];   //Массив для сохранения данных и расчета игры.
        private Fishka fishka;
        private Sender sender;
        private boolean moveDone=false;
        private int lastIndex;
        private Color defaultCellColor=Helper.DEFAULT_COLOR;
        //private boolean gameEnded=false;
        private JMenuBar menuBar;
        private InfoPanel infoPanel;
        private JPanel cellPanel;
        
        /**Конструктор игрового поля для хоста
         *
         * @param f объект-фишка.
         * @throws java.io.IOException
         */
        public GameFrame(Fishka f) throws IOException {
                super("Крестики-нолики");
                fishka=f;                
                initGame(true);
                sender=new Host(this);
                moveDone=true;  //Первым ходит хост.
        }

        /**Конструктор игрового поля для клиента
         * @param f обьект-фишка.
         * @param addr IP адрес хоста.
         * @throws java.io.IOException
         */
        public GameFrame(Fishka f,String addr) throws IOException {
                super("Крестики-нолики");
                fishka=f;
                initGame(false);
                sender=new Client(this,addr);                
        }

        //Инициализация компонентов.
        private void initGame(boolean disableButtons) {
                System.out.println("Создается фрейм игры");
                
                setVisible(true);
                setLocation(Helper.START_LOCATION);
                setLayout(new BorderLayout());

                //Создаем панель меню.
                menuBar=new Menu(this);
                setJMenuBar(menuBar);

                //Создаем клеточное поле.
                cellPanel=new JPanel(new GridLayout(DIM, DIM));
                add(cellPanel);
                
                //Заполнение поля клетками.                
                fillTheField(disableButtons);                

                //Создаем массив значений.
                data=new int[DIM][DIM];

                //Создаем информационную панель внизу окна.
                infoPanel=new InfoPanel();
                add(infoPanel,BorderLayout.SOUTH);
                
                //Добавляем прослушивание закрытия окна - завершение работы программы.
                addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                                //Выводим значение из массива данных (для отладки.)
                                for(int i=0;i<DIM;i++) {
                                        for(int j=0;j<DIM;j++) {
                                                System.out.print(data[i][j]);
                                        }
                                        System.out.println("");
                                }
                                //Выход из программы.
                                System.exit(0); 
                        }
                });
                pack();                
        }

        /**Установка данных по-умолчанию(из сети). Смотри setData(int row,int col, Fishka fishka,boolean fromNet);
         */
        public void setData(int row,int col,Fishka fishka) {                
                moveDone=true;                  //Ход противника совершен, игрок может ходить.
                setData(row, col, fishka, true);
        }

        /**Установка данных с параметром, указывающим откуда получены данные(из сети или в результате клика на игровом поле).
         * @param row х координата клетки.
         * @param col у координата клетки.
         * @param fishka объект, указывающий какая фишка должна быть установлена в данной клетке.
         * @param fromNet указывает откуда пришли данные: true - из сети.
         */
        public void setData(int row,int col, Fishka fishka,boolean fromNet) {
                data[row][col]=fishka.get();
                cells[row*DIM+col].setField(fishka.getIcon());
                //Подсветка последнего совершенного хода.
                showLastMove(row, col);
                
                try{
                        //если данные получены не из сети, тогда создаем объект для сериализации и отправляем его по сети.
                        if(!fromNet) {
                                sender.sendData(new Move(row, col, fishka));
                                waitForNextMove();
                        }
                }
                catch(IOException e) {
                        System.out.println(e);
                }
                
                //Запускаем подсчет элементов, выстроенных в ряд.
                int[] winRow=new Calculation(row, col, data).calculate();
                //Если получен массив (не null), значит получена выигрышная комбинация. Завершаем игру.
                if(winRow!=null) {
                        System.out.println("We have a winner: "+fishka.get());
                        endGame(winRow, fishka.get());
                        System.out.println("MoveDone="+moveDone);
                        return;
                }
                
                //Чей ход.
                String text=(moveDone ? "Ваш ход" : "Ожидание хода");
                infoPanel.setInfoLabel(text);
        }

        //Возвращаем массив значений.
        public int[][] getData() {
                return data;
        }

        //Завершение игры
        private void endGame(int[] winRow,int fishka) {
                //Делаем все кнопки неактивными.
                for(Cell cell:cells) {
                        cell.setEnabled(false);                        
                }
                //Устанавливаем подсветку для выигрышных клеток.
                for(int i=0;i<winRow.length;i+=2) {
                        int index=winRow[i]*DIM+winRow[i+1];
                        cells[index].setEnabled(true);
                        cells[index].setRolloverEnabled(false); 
                }                
                infoPanel.setInfoLabel("The End. Winner: "+fishka);
        }
        /**Чтобы ждать хода противника устанавливаем значение переменной в ложь.
         */
        public void waitForNextMove() {
                moveDone=false;               
        }
        /**Проверяем сделал ли противник свой ход.
         * @return Возвращает true если противник сделал свой ход.
         */
        public boolean isMoveDone() {
                return moveDone;
        }
        /**
         * Проверяет закончилась ли игра.
         * @return
         */
//        public boolean isGameEnded() {
//                return gameEnded;
//        }
//        public void setNewGameStarted() {
//                gameEnded=false;
//        }
        
        //Подсветка последнего хода.
        private void showLastMove(int x,int y) {
                //Возвращаем цвет фона ПРЕДПОСЛЕДНЕГО хода.
                cells[lastIndex].setBackground(defaultCellColor);
                int index=x*DIM+y;
                //Устанавливаем  цвет последнего хода.
                cells[index].setBackground(new Color(150,255,130));
                lastIndex=index;
        }
        /**Начинаем игру(делаем кнопки активными).
         */
        public void fillCellArray() {
                for(Cell cell:cells) {
                        cell.setEnabled(true);                        
                }
        }
        /**
         * Перезапуск игры. Розыгрыш первого хода. Перезаполнение игрового поля. Отправка данных по сети.
         * @throws java.io.IOException
         */
        public void restartGame() throws IOException {
                
                Random rnd=new Random();
                boolean myFirstMove=rnd.nextBoolean();
                moveDone=myFirstMove;
                NewGame newGame=new NewGame(!myFirstMove);
                sender.sendNewGame(newGame);
                
                restoreTheField();
        }
        /**
         * Перезапуск  игры по данным полученым из сети.
         * @param firstMove первый ход.
         */
        public void restartGame(boolean firstMove) {               
                moveDone=firstMove;
                restoreTheField();
        }

        /**Заполняем игровое поле клетками.
         * Если disableButtons=true, тогда временно блокируем клетки до сетевого соединения(хост).
         * */
        private void fillTheField(boolean disableButtons) {
                for(int i=0;i<DIM*DIM;i++) {
                        cells[i]=new Cell(this,fishka,i);
                        if(disableButtons) {
                                cells[i].setEnabled(false);
                        }
                        cellPanel.add(cells[i],i);
                }
                cellPanel.validate();
        }
        private void restoreTheField() {
                //Удаляем и создаем новые клетки.
                cellPanel.removeAll();
                fillTheField(false);
                cellPanel.validate();
                //Создаем новый массив значений.
                data=new int[DIM][DIM];

//                setNewGameStarted();
                System.out.println("new game started");
                //Чей первый ход.
                String moveText=(moveDone ? "Ваш ход" : "Ходит ваш оппонент");
                infoPanel.setInfoLabel("Розыгрыш первого хода: "+moveText);
        }
}
