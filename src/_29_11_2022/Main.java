package _29_11_2022;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(getIntRadix(2555, 21));
    }
    //Таблица символов
    private static List<Character> getDigitTable() {
        ArrayList<Character> digits = new ArrayList<>();
        for (char i = '0'; i <= '9'; i++) {
            digits.add(i);
        }
        for (char i = 'A'; i <= 'Z' ; i++) {
            digits.add(i);
        }
        return digits;
    }

    public static String getIntRadix(int number, int radix) {
        List<Character> digits = getDigitTable();

        //Проверки на адекватность
        if(radix < 2 || radix >= digits.size() || number < 0) {
            throw new IllegalArgumentException();
        }

        StringBuilder valueStr = new StringBuilder();

        //Пока число больше 0
        // ex 255 -> 2
        while (number > 0) {
            //...добавляем очередной символ для соответствующего разряда в начало строки
            // Мы берем символ из таблицы, который соответствует остатку от
            // деления числа которое является основанием нашей системы счисления
            valueStr.insert(0, digits.get(number % radix));
            number = number / radix;
        }
        return valueStr.toString();
    }
}