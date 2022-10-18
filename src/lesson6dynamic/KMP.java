package lesson6dynamic;

import java.util.ArrayList;
import java.util.Arrays;

public class KMP {
    static String source = "aabaabaabaabbbbbaaaabbbaabbabb";
    static String template = "aabaab";

    public static void main(String[] args) {

        System.out.println(Arrays.toString(search(source, template).toArray()));

        System.out.println(Arrays.toString(prefixFunction(template)));

        System.out.println(Arrays.toString(KMPSearch(source, template).toArray()));
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

    //отдельный метод нахождения префиксной функции [0 1 0 1 2 3]=={aabaab}
    static int[] prefixFunction(String template) {
        //создать массив для функции
        int[] values = new int[template.length()];
        /**
         * если символ в образце совпадает с символом в начале образца
         * соответсвующую позицию
         * записываем максимальное значение из того что там уже было
         * или записываем позицию совпавшего символа внутри образца
         */
        for (int i = 1; i < template.length(); i++) {
            int templatePosition = 0;
            //ищем внутри себя
            while (i + templatePosition < template.length()
            && template.charAt(templatePosition) == template.charAt(i + templatePosition)) {
                values[i + templatePosition] = Math.max(values[i + templatePosition], templatePosition + 1);
                templatePosition++;
            }
        }
        return values;
    }

    public static ArrayList<Integer> KMPSearch(String source, String template) {
        //массив для нахождения вхождений
        ArrayList<Integer> found = new ArrayList<>();
        //вычислить префиксную функцию
        int[] prefixFunctionArray = prefixFunction(template);

        int sourcePosition = 0;
        int templatePosition = 0;

        //пока не дошли до конца текста
        while (sourcePosition < source.length()) {
            //если символ в шаблоне совпадает с символом в ресурсе
            if (template.charAt(templatePosition) == source.charAt(sourcePosition)) {
                //просто двигаемся дальше
                templatePosition++;
                sourcePosition++;
            }
            //если все символы совпали
            if (templatePosition == template.length())  {
                //записываем начало вхождения
                found.add(sourcePosition - templatePosition);
                //берем из префиксной функции значение куда
                //нужно вернуться из последнего значения
                //(которое совпало)
                templatePosition = prefixFunctionArray[templatePosition-1];
                //если не нашли совпадения
                //...и символ шаблона не совпал с символом ресурса

            } else if(sourcePosition < source.length()
                    && template.charAt(templatePosition) != source.charAt(sourcePosition)) {
                //если это не первый символ шаблона
                if (templatePosition != 0) {
                    //просто вернуть что находится на соответствующей позиции префиксной функции
                    //те, если не нашли необходимый элемент то просто вернулись на нужную позицию
                    //-> вернулись назад в то место с которого можно продолжить поиск
                    templatePosition = prefixFunctionArray[templatePosition - 1];
                } else {
                    //...если все-таки первый символ шаблона
                    //то просто двигаемся дальше по source
                    sourcePosition = sourcePosition + 1;
                }
            }
        }
        return found;
    }
}