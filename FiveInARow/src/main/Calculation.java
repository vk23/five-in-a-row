/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *Расчет данных: подсчет количества элементов в ряду.
 * @author kaligula
 */
public class Calculation {
        private int x, y;
        private int[][] data;
        private final int VAL;        
        private int[] winRow=new int[10]; //Координаты выигрышных клеток через запятую (x1,y1,x2,y2,...,x5,y5);
        
        public Calculation(int x,int y,int[][] data) {
                //Получаем таблицу значений и выбранную клетку.
                this.x=x;
                this.y=y;
                this.data=data;
                VAL=data[x][y];
                winRow[0]=x;winRow[1]=y; //Записываем координаты выбранной клетки в выигрышный массив.                 
        }
        //Функция подсчета. Результат: выигрышная комбинация или  null.
        public int[] calculate() {
                int delta[]={-1,-1,0,-1,1,-1,-1,0};     //Приращения координаты, в зависимости от четырех возможных направлений расчета ряда.
                for(int row=0;row<4;row++) {           //4 ряда.                        
                        //Переменные координаты
                        int i=x,j=y;
                        //Индекс в выигрышном массиве изменяется от 2 до 10(первые 2 значения - значения текущей клетки).
                        int k=2;
                        //Получаем приращения для текущего ряда.
                        int dx=delta[row*2];
                        int dy=delta[row*2+1];
                        //Первый цикл: проверяет клетки в одну сторону от текущей.
               loop1: while(k<10) {
                                //Получаем координатый клетки, необходимой для сравнения.
                                i+=dx;j+=dy;
                                //Координаты тест-клетки не должны выходить за рамки поля.
                                if((i>=0 & j>=0)&&(i<Helper.SIDE_LENGTH & j<Helper.SIDE_LENGTH)) {
                                        //Если ее значение совпадает со значением текущей клетки, то переходим к следующей клетке,
                                        //иначе выход из первого цикла.
                                        if(data[i][j]==VAL) {
                                                winRow[k++]=i;
                                                winRow[k++]=j;
                                        }
                                        else {
                                                break loop1;
                                        }
                                }
                                else {
                                        break loop1;
                                }
                        }                        
                        //Возвращаем значения переменных координат.
                        i=x;j=y;
                        //Меняем направление приращения на противоположное.
                        dx=-dx;dy=-dy;
                        //Второй цикл: проверяет клетки в противоположной стороне.
               loop2: while(k<10) {
                                i+=dx;j+=dy;
                                if((i>=0 & j>=0)&&(i<Helper.SIDE_LENGTH & j<Helper.SIDE_LENGTH)) {
                                        if(data[i][j]==VAL) {
                                                winRow[k++]=i;
                                                winRow[k++]=j;                                                
                                        }
                                        else {
                                                break loop2;
                                        }
                                }
                                else {
                                        break loop2;
                                }
                        }
                        //Если выигрышный массив заполнился - возвращаем его значение.
                        if(k==10) {                                
                                System.out.println("We have a winner: "+VAL);
                                return winRow;
                        }                        
                }
                //Если нет выигрышной комбинации - возвращаем null;
                return null;
        }
}
