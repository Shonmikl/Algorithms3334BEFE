package lesson6dynamic;

import java.util.ArrayList;
import java.util.Arrays;

public class KMP {
    static String source = "ababababbbbbaaaabbbabbabb";
    static String template = "bbb";

    public static void main(String[] args) {
        System.out.println(Arrays.toString(search(source, template).toArray()));
    }

    //простой алгоритм
    //source = AAAAABBBAABBABBBABABBAABBBAABABBA template = AAAAB
    //O(source*template)
    static ArrayList<Integer> search(String source, String template) {
        ArrayList<Integer> foundPosition = new ArrayList<>();
        //цикл по каждому символу
        for (int i = 0; i < source.length(); i++) {
            //указатель позиции шаблона
            int templatePosition = 0;
            //пока не вышли за границы шаблона
            //...и не вышли за границы ресурса
            //...и символы в шаблоне и в ресурсе равны
            while (templatePosition < template.length()
                    && i + templatePosition < source.length()
                    && template.charAt(templatePosition) == source.charAt(i + templatePosition)) {
                templatePosition++;
            }
            //мы прошли по всем символам и если они совпали - значит нашли слово(вхождение)
            if (templatePosition == template.length()) {
                //просто его добавить в наш лист
                foundPosition.add(i);
            }
        }
        return foundPosition;
    }
}