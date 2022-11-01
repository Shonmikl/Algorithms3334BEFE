package _01112022;

import java.util.ArrayList;
import java.util.List;

//Метод рекурсивного спуска
public class Main {
    /**
     * 1. Лексикографический анализ
     * 2. Синтаксический анализ
     * 3. Написать методы с рекурсивным спуском
     *
     * expr - выражение которое "смотрит" на выражение
     * plusMinus - смотрит есть ли multdiv
     * multDiv - смотрят на цифры
     * factor - смотрят на expr
     * lexeme - каждый элемент выражения
     */

    public static void main(String[] args) {
        String expressionText = "122-1/(11+2*(163-4*3))";
        //String s = "202+8";
    }

    //1. Зададим тип лексем
    public enum LexemeType {
        LEFT_BRACKET,
        RIGHT_BRACKET,
        OP_PLUS,
        OP_MINUS,
        OP_MUL,
        OP_DIV,
        NUMBER,
        END;
    }

    //2. Класс для предоставления отдельной лексемы
    public static class Lexeme {
        LexemeType type; // тип лексемы
        String value; // чем наша лексемы является в тексте

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Lexeme{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    //3. Функцию лексического анализа.
    //Принимать строку с выражением и возвращать массив лексем
    public static List<Lexeme> lexAnalyze(String expText) {
        //Будет хранить наши лексемы
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        //указатель движения по стринге
        int pos = 0;
        //пока мы не дошли до конца нашего текста
        //будем идти по строке и генерировать наши лексемы
        while (pos < expText.length()) {
            //String expText = "202+80";202
            //берем символ из текста
            //и добавляем в массив
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    //двигаемся дальше
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    //проверяем на цифры
                    if(c <= '9' && c>='0') {
                        //создаем StringBuilder в который мы будем
                        //добавлять наши символы
                        StringBuilder sb = new StringBuilder();
                        //смысл цикла в том что мы вписываем все цифры
                        //и добавляем их в массив лексем
                        //до тех пор, пока не встретиться что то другое
                        //и склеиваем их в одно число, после этого
                        //число добавляем в массив лексем
                        do {
                            sb.append(c);
                            pos++;
                            //если достигли конца строки, то break;
                            if (pos >= expText.length()) {
                                break;
                            }
                            //достаем след символ из строки
                            c = expText.charAt(pos);
                        } while (c <= '9' && c>= '0');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        //а если не число
                        //если символ не пробел
                        if(c != ' ') {
                            //то в нашем выражении ошибка
                            throw new RuntimeException("Unexpected character:" + c);
                        }
                        //a если пробел
                        pos++;
                    }
            }
        }
        //в самом конце надо добавить лексему END
        //и вернуть массив лексем
        lexemes.add(new Lexeme(LexemeType.END, ""));
        return lexemes;
    }

}











