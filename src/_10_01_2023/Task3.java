package _10_01_2023;

import java.util.*;
import java.util.stream.Collectors;

public class Task3 {
    public static String reverseWords(String s) {
        StringTokenizer st = new StringTokenizer(s, " ");
        List<String> result = new ArrayList<>();

        while (st.hasMoreTokens()) {
            String curr = st.nextToken();
            result.add(curr);
        }

        Collections.reverse(result);
        StringBuilder ss = new StringBuilder();
        for (String curr : result) {
            ss.append(curr).append(" ");
        }

        return ss.toString().trim();
    }

    public static String reverseWordsStreams(String s) {
        String str = Arrays.stream(s.split(" "))
                .reduce("", (a, b) -> b + " " + a);
        return str;

    }

    public static void main(String[] args) {
        String str = "It comes a day after thousands " +
                "of his supporters stormed government " +
                "offices in the Brazilian capital";

        //System.out.println(reverseWords(str));
        System.out.println(reverseWordsStreams(str));
    }
}