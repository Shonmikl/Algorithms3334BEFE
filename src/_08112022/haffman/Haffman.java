package _08112022.haffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

/**
 * 1. Посчитать сколько раз каждый символ встречается в тексте
 * 2. Так как мы будем использовать кодовое дерево - надо написать класс для кодового дерева
 * 3. Реализуем алгоритм
 * 4. Декодируем
 */
public class Haffman {
    public static void main(String[] args) {
//        String text =
//                "Sri Lanka Cricket wishes to emphasise that " +
//                        "it adopts a 'zero tolerance' policy for any such conduct " +
//                        "by a player and will provide all the required support to " +
//                        "the Australian law enforcement authorities to carry out an " +
//                        "impartial inquiry into the incident.";

        String text = "0101100110011011111010111101101010010000111101111" +
                "01011111100110111010110000111100001111000100101110100011" +
                "11110010100111001011011111000001010111001111110110100101110" +
                "10011001110010111111010011111011001111110101010011001110001" +
                "1010011111011111000001010110101100100101101110010110000011001" +
                "00111010100101011100100000111101110011000001011101010001111110" +
                "0000001101001111110101000001111110100100011010101111111101010110" +
                "0100010101000110101001111010110000001111111011110111000001101000" +
                "111100100111111010100010101110001001011000000001110111010010110010" +
                "110111011010101100111110100000000111001011111100111100111001000010" +
                "10001101110011100010101111010010001011100111001101001001111001011011" +
                "10010111111001110101110010001101000011001110100001011110101001110000110" +
                "1000100111110001001000000011010011010111000001011100010000111111011000" +
                "10010111101101001101100110111100101001110010110111101011101100110010001" +
                "11110110100010011111101010011110110001010111011011001001101111010000111" +
                "10110100100001010001101110010001111110110100001011011100101111110011110" +
                "110100101011011010101100010000101011101";

        String aab = "aabaacca";

        long time = System.currentTimeMillis();

        //Вычисляем частоту символов в тексте.
        TreeMap<Character, Integer> frequencies = countFrequency(text);

        //Генерируем список листов деревьев.
        ArrayList<CodeTreeNode> codeTreeNodes = new ArrayList<>();
        for (Character c : frequencies.keySet()) {
            codeTreeNodes.add(new CodeTreeNode(c, frequencies.get(c)));
        }

        //Строим кодовое дерево с помощью г-на Хаффмана
        CodeTreeNode tree = huffman(codeTreeNodes);

        //Генерируем таблицу префиксных кодов, с помощью нашего кодового дерева.
        //Ключом у нас является СИМВОЛ, а значением является СТРОКА содержащая 0 и 1,
        //которая является кодом символа, который мы ищем.
        TreeMap<Character, String> codes = new TreeMap<>();
        for (Character c : frequencies.keySet()) {
            //На данном этапе, добавляем мы добавляем код для нашего символа из нашего дерева.
            //В качестве начального пути передаем пустую строку (""),
            //в которую мы будем добавлять наши 0 и 1
            codes.put(c, tree.getDecodeForCharacter(c, ""));
        }

        System.out.println("Таблица префиксных кодов: " + codes);
        System.out.println("**************************************************************************");

        //Кодируем текст.
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            //Просто берем и идем по строке и для каждого символа записываем код
            //этого символа который мы сгенерировали при помощи кодового дерева.
            encoded.append(codes.get(text.charAt(i)));
        }

        System.out.println("Размер исходной строки: " + text.getBytes().length * 8 + " бит.");
        System.out.println("Размер сжатой строки: " + encoded.length() + " бит.");
        System.out.println("Биты сжатой строки: " + encoded);

        //А как раскодировать?
        String decoded = huffmanDecode(encoded.toString(), tree);

        System.out.println("Расшифрованная строка: " + decoded);

        System.out.println(System.currentTimeMillis() - time);
    }

    //1. Считаем кол-во символов
    private static TreeMap<Character, Integer> countFrequency(String text) {
        TreeMap<Character, Integer> freqMap = new TreeMap<>();
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Integer count = freqMap.get(c);
            freqMap.put(c, count != null ? count + 1 : 1);
        }
        return freqMap;
    }

    //2. Класс для предоставления кодового дерева
    private static class CodeTreeNode implements Comparable<CodeTreeNode> {
        //Хранимый символ
        Character content;
        //Частота или "вес"
        int weight;
        //Левый и правый потомок
        CodeTreeNode left;
        CodeTreeNode right;

        public CodeTreeNode(Character content, int weight) {
            this.content = content;
            this.weight = weight;
        }

        public CodeTreeNode(Character content, int weight, CodeTreeNode left, CodeTreeNode right) {
            this.content = content;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(CodeTreeNode o) {
            return o.weight - weight;
        }

        /**
         * Нам необходимо написать метод, который делает проход по нашему
         * кодовому дереву, от корня до конкретного символа и при повороте направо лил налево
         * вычисляет последовательность 0 или 1.
         * В качестве параметра будем передавать СИМВОЛ который мы ищем и СТРОКУ состоящую
         * из нулей и единиц и дописывать туда 0 или 1 при соответствующем повороте.
         */

        public String getDecodeForCharacter(Character ch, String parentPath) {
            //Нам нужен символ который мы ищем.
            //Если мы нашли наш символ.....то мы его нашли
            if (content == ch) {
                //возвращаем путь
                return parentPath;
            } else {
                //есть ли левый потомок
                if (left != null) {
                    //тогда мы вызываем ту же функцию рекурсивно
                    //и передаем символ который, ищем, и дописываем путь (0 тк потомок левый)
                    String path = left.getDecodeForCharacter(ch, parentPath + 0);
                    if (path != null) {
                        //это значит, что в ветке нашелся нужный символ
                        //ну и передаем этот символ и дописываем путь
                        return path;
                    }
                }
                if (right != null) {
                    String path = right.getDecodeForCharacter(ch, parentPath + 1);
                    if (path != null) {
                        return path;
                    }
                }
            }
            return null;
        }
    }

    //3. Реализуем алгоритм.
    // Метод принимает в качестве параметров список узлов, а возвращает дерево
    private static CodeTreeNode huffman(ArrayList<CodeTreeNode> codeTreeNodes) {

        //Пока в нашем списке узлов больше чем один
        while (codeTreeNodes.size() > 1) {
            Collections.sort(codeTreeNodes);

            //Берем два узла с самыми маленькими значениями,
            //получаем новый узел и тут же его удаляем.
            //В результате получим два узла которые мы свяжем промежуточным узлом.
            CodeTreeNode left = codeTreeNodes.remove(codeTreeNodes.size() - 1);
            CodeTreeNode right = codeTreeNodes.remove(codeTreeNodes.size() - 1);

            //Получили.
            //Теперь нам необходимо создать промежуточный узел.
            //Вес промежуточного узла равен сумме весов потомков
            CodeTreeNode parent = new CodeTreeNode(null, right.weight + left.weight, left, right);

            //Теперь просто кладем наш новый узел обратно в массив.
            //Прокручиваем данный алгоритм пока не останется всего один узел,
            //который и будет корнем нашего кодового дерева
            codeTreeNodes.add(parent);

        }
        return codeTreeNodes.get(0);
    }

    //4. Декодируем.
    //Берем строку с 0 и 1, и идем по дереву.
    //Идем по дереву пока не дойдем до листа.
    //После того как дойдем до листа -> возвращаем символ.
    private static String huffmanDecode(String encoded, CodeTreeNode tree) {
        //Нам нужен StringBuilder для хранения полученной строки (расшифрованных данных).
        StringBuilder decoded = new StringBuilder();

        //Нам нужна переменная которая будет хранить узел когда она
        //будет спускаться по дереву.
        //Изначально он будет равен самому корневому узлу.
        CodeTreeNode node = tree;

        //Теперь проходимся по нашим БИТАМ, нашей зашифрованной строки.
        for (int i = 0; i < encoded.length(); i++) {
            //Если текущий бит == 0, то идем налево, == 1 -> направо
            node = encoded.charAt(i) == '0' ? node.left : node.right;

            //Если мы дошли до какого-то листа и у него есть символ....
            if (node.content != null) {
                //...тогда добавляем этот символ в нашу последовательность(в наш StringBuilder)...
                decoded.append(node.content);
                //...и возвращаем текущий узел
                node = tree;
            }
        }
        return decoded.toString();
    }
}