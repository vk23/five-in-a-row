/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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
        private Sender thread;
        private boolean moveDone=false;
        private int lastIndex;
        private Color defaultCellColor;
        private JMenuBar menuBar;
        private InfoPanel infoPanel;
        private JPanel cellPanel;
        
        /**Конструктор игрового поля для хоста
         *
         * @param f объект-фишка.
         * @throws java.io.IOException
         */
        public GameFrame(Fishka f) throws IOException {
                super("Крестики-нолики 0.2b");
                fishka=f;
                thread=new Host(this);                
                initGame(true);
                moveDone=true;  //Первым ходит хост.
        }

        /**Конструктор игрового поля для клиента
         * @param f обьект-фишка.
         * @param addr IP адрес хоста.
         * @throws java.io.IOException
         */
        public GameFrame(Fishka f,String addr) throws IOException {
                super("Крестики-нолики 0.2b");
                fishka=f;
                thread=new Client(this,addr);                
                initGame(false);
        }

        //Инициализация компонентов.
        private void initGame(boolean disableButtons) {
                System.out.println("Создается фрейм игры");
                
                setVisible(true);
                setLocation(Helper.START_LOCATION);
                setLayout(new BorderLayout());

                //Создаем панель меню.
                menuBar=new Menu();
                setJMenuBar(menuBar);

                //Создаем клеточное поле.
                cellPanel=new JPanel(new GridLayout(DIM, DIM));
                add(cellPanel);
                //Заполнение поля клетками.
                long start=System.currentTimeMillis();                  //Старт-таймер вычисляющий время создания клеток на поле.
                fillTheField(disableButtons);
                long stop=System.currentTimeMillis();                   //Стоп-таймер
                System.out.println("Arrray filled in "+(stop-start));   //Вывод времени в консоль.

                //Создаем информационную панель внизу окна.
                infoPanel=new InfoPanel();
                add(infoPanel,BorderLayout.SOUTH);
                
                defaultCellColor=cells[0].getBackground();
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

        /**Установка данных по-умолчанию(из сети)          
         */
        public void setData(int row,int col,Fishka fishka) {
                setData(row, col, fishka, true);
                moveDone=true;                  //Ход противника совершен, игрок может ходить.
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
                //Запускаем подсчет элементов, выстроенных в ряд.
                int[] winRow=new Calculation(row, col, data).calculate();
                //Если получен массив (не null), значит получена выигрышная комбинация. Завершаем игру.
                if(winRow!=null) {
                        System.out.println("We have a winner: "+fishka.get());
                        endGame(winRow, col);
                }
                try{
                        //если данные получены не из сети, тогда создаем объект для сериализации и отправляем его по сети.
                        if(!fromNet) {
                                thread.sendData(new Move(row, col, fishka)); 
                                waitForNextMove();
                        }
                }
                catch(IOException e) {
                        System.out.println(e);
                }
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
        }
        /**Чтобы ждать хода противника устанавливаем значение переменной в ложь.
         */
        public void waitForNextMove() {
                moveDone=false;
        }
        /**Проверяем сделал ли противник свой ход.
         */
        public boolean isMoveDone() {
                return moveDone;
        }
        //Подсветка последнего хода.
        private void showLastMove(int x,int y) {
                //Возвращаем цвет фона ПРЕДПОСЛЕДНЕГО хода.
                cells[lastIndex].setBackground(defaultCellColor);
                int index=x*DIM+y;
                //Устанавливаем  цвет последнего хода.
                cells[index].setBackground(Color.YELLOW);
                lastIndex=index;
        }
        /**Начинаем игру(делаем кнопки активными).
         */
        public void startGame() {
                for(Cell cell:cells) {
                        cell.setEnabled(true);                        
                }
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
        }
}
