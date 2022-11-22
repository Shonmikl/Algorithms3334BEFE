package _22_11_2022.backpack;

import java.util.ArrayList;

//Метод динамического программирования
public class Dynamic {
    public static void main(String[] args) {
        int[] weight = {3, 4, 5, 8, 9, 1};
        int[] price = {1, 6, 4, 7, 6, 5};
        int maxWeight = 30;
        int count = weight.length;
        int[][] A;

        //Первый индекс это количество предметов
        //Второй это объем рюкзака
        //Само значение это максимальная стоимость
        A = new int[count + 1][];
        for (int i = 0; i < count + 1; i++) {
            A[i] = new int[maxWeight + 1];
        }

        //k - это набор предметов
        //s - размер рюкзака
        for (int k = 0; k <= count; k++) {
            for (int s = 0; s <= maxWeight; s++) {
                //если размер нашего набора предметов 0
                //или рюкзак 0, то стоимость тоже 0
                if (k == 0 || s == 0) {
                    A[k][s] = 0;
                } else {
                    //если размер рюкзака >= размеру текущего предмета...
                    if (s >= weight[k - 1]) {
                        A[k][s] = Math.max(A[k - 1][s],
                                A[k - 1][s - weight[k - 1]] + price[k - 1]);
                    } else {
                        //если предмет в рюкзак не влезает, то и нечего ему там делать
                        A[k][s] = A[k - 1][s];
                    }
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        traceResult(A, weight, count, maxWeight, result);
        System.out.println("Содержимое рюкзака: ");
        for (Integer integer : result) {
            System.out.println(integer);
        }
    }

    //Пишем отдельный метод который идет по
    //нашей матрице и выдергивает оттуда соответствующие предметы
    private static void traceResult(int[][] A, int[] weight, int k, int s, ArrayList<Integer> result) {
        //если стоимость A[k][s] == 0
        if (A[k][s] == 0) {
            //то ничего не добавляем
            return;
        }

        //Если две стоимости одинаковые, то k-й предмет в наш набор не вошел
        //и мы дальше идем по таблице без учета ДАННОГО предмета
        if (A[k - 1][s] == A[k][s]) {
            //ПРОСТО УМЕНЬШАЕМ НАБОР НА 1
            traceResult(A, weight, k - 1, s, result);
        } else {
            //иначе этот предмет участвует в формировании стоимости
            traceResult(A, weight, k - 1, s - weight[k - 1], result);
            result.add(0, k);
        }
    }
}