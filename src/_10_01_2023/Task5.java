package _10_01_2023;

public class Task5 {
    private static boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length()-1;

        while (start != end) {
            if(s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    private static boolean isPalindromeRec(String s) {
        if(s.length() == 1 || s.length() == 0) {
            return true;
        }

        if(s.charAt(0) == s.charAt(s.length() - 1)) {
            return isPalindromeRec(s.substring(1, s.length()-1));
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "MAMA";
        String d = "ATATA";

//        System.out.println(isPalindrome(s));
        System.out.println(isPalindrome(s));
        System.out.println(isPalindromeRec(s));
    }
}
