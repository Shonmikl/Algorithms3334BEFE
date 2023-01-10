package _10_01_2023;

public class Task4 {
    public static int reverse(int x) {
        int sign = 1;

        if (x < 0) {
            x = x * -1;
            sign = -1;
        }
        int result = 0;
        int max = Integer.MAX_VALUE;

        while (x > 0) {
            int mod = x % 10;

            if (max / 10 > result || (max / 10 == result && max % 10 >= mod)) {
                result = result * 10 + mod;
            } else {
                return 0;
            }
            x = x / 10;
        }
        return result * sign;
    }

    public static void main(String[] args) {
        //input 123 -> out 321
        //input -456 -> out -654
        //input 120 -> out 21
        int ex = -456;
//        int ex2 = 1230;
        System.out.println(reverse(ex));
//       System.out.println(reverse(ex2));

    }
}