package _01112022;

public class MinArray {

    public static void getMin(int[] array) {
        int minValue = array[0];
        int minIndex = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }

        System.out.println("Min Value: = " + minValue);
        System.out.println("Min element index: = " + minIndex);
    }

    public static void getSecondMin(int[] array) {
        int min = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        for (int j : array) {
            if (j < min) {
                min2 = min;
                min = j;

            } else if (j < min2 && j != min) {
                min2 = j;
            }
        }

        if (min2 != Integer.MAX_VALUE) {
            System.out.println("Second Min Value: = " + min2);
        } else {
            System.out.println("Second Min Value doesn't exist");
        }
        System.out.println("Min Value: = " + min);
    }


    public static void main(String[] args) {
        int[] a = {98, 7, 7, 61, 23, 65, 4, 0, 12, 4, 48, 9, 5, 13, 2};
        int[] s = {99, 99, 99, 99, 99};
        int[] d = {1, 99, 99, 99, 0};
        getSecondMin(a);
        getSecondMin(s);
        getSecondMin(d);
    }
}
