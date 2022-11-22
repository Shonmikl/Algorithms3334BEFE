package _22_11_2022.backpack;

import java.util.ArrayList;

//Жадные алгоритмы
public class Greedy {
    public static void main(String[] args) {

        int[] weight = {3, 4, 5, 8, 9};
        int[] price = {1, 6, 4, 7, 6};
        int maxWeight = 13;

        /**
         * Будем искать самый дорогой предмет и класть его в рюкзак если
         * объем рюкзака это позволяет сделать
         */

        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();

        int  resultWeight = 0;

        for (int i = 0; i < weight.length; i++) {
            indexes.add(i);
        }

        //находим максимум, и если можно положить, то кладем
        while ((!indexes.isEmpty())) {
            int maxValue = price[indexes.get(0)];
            int maxIndex = indexes.get(0);

            for (int i = 1; i < indexes.size(); i++) {
                if(maxValue < price[indexes.get(i)]) {
                    maxValue = price[indexes.get(i)];
                    maxIndex = indexes.get(i);
                }
            }

            resultWeight = resultWeight + weight[maxIndex];
            if(resultWeight > maxWeight) {
                break;
            }
            //мы кладем в рюкзак до тех пор,
            //пока не закончится наш набор или не будет превышен вес рюкзака
            result.add(maxIndex);
            indexes.remove(maxIndex);
        }

        System.out.println("Содержимое рюкзака: ");
        for (Integer integer : result) {
            System.out.println(integer + 1);
        }
    }
}