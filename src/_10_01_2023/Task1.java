package _10_01_2023;

public class Task1 {
    //todo доделать проверку на скобочную последовательность (сложную)
    // + вывести на экран недостающий символ
    public static boolean pars(String str) {
        int checker = 0; //сделаем счетчик
        for (int i = 0; i < str.length(); i++) {
            if (checker < 0) {
                return false;
            }

            String symbol = str.substring(i, i + 1); //получаем символ
            if ("(".equals(symbol)) {
                checker++;
            } else {
                checker--;
            }
        }
        return checker == 0;
    }

    public static void main(String[] args) {
        String sTrue = "()()()";
        String sFalse = "()()()(";

        String example = "[()({[()({])({)}}])({)}}";

        System.out.println(pars(sFalse));
        System.out.println(pars(sTrue));
    }
}