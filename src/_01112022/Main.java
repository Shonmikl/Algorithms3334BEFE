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
        String expressionText = "5*5+(3*3*3)";
        List<Lexeme> lexemes = lexAnalyze(expressionText);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        System.out.println(expr(lexemeBuffer));
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

    //4. Вспомогательный класс
    //для хранения инфы прохода по массиву
    //"123+5*9*(4*9-6)
    public static class LexemeBuffer {
        private int pos;
        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPos() {
            return pos;
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

    //Пишем синтаксический анализатор
    public static int expr(LexemeBuffer lexemes) {
        //25+9-(16*5-(11+6))
        Lexeme lexeme = lexemes.next();
        //сделать проверку на пустое выражение
        //если первая лексема это конец строки то вернем 0
        if (lexeme.type == LexemeType.END) {
            return 0;
        } else {
            //иначе вернемся назад и запустим вычисления + и -
            //будем смотреть на выражение внутри начиная с + и -
            lexemes.back();
            return plusMinus(lexemes);
        }
    }

    public static int factor(LexemeBuffer lexemes) {
        //прочитать лексему
        Lexeme lexeme = lexemes.next();
        //смотрим ее тип
        switch (lexeme.type) {
            case NUMBER: //если лексема само число
                return Integer.parseInt(lexeme.value);
            case LEFT_BRACKET:
                //если левая скобка
                //то смотрим что в скобках
                int value = expr(lexemes);
                lexeme = lexemes.next();
                //25+9-(16*5-(11+6))
                //если правой скобки нет то выражение не верно
                if(lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token:" + lexeme.value);
                }
                return value;
            default:
                throw new RuntimeException("Unexpected token:" + lexeme.value);
        }
    }

    public static int multDiv(LexemeBuffer lexemes) {
        //значение первого числа(фактора)
        int value = factor(lexemes);
        //25+9-(16*5-(11+6))
        while (true) {
            //достаем след лексему
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL:
                    value  = value * factor(lexemes);
                    break;
                case OP_DIV:
                    value = value / factor(lexemes);
                    break;
                case END:
                case RIGHT_BRACKET:
                case OP_PLUS:
                case OP_MINUS:
                    //если мы не умножаем и не делим то возвращаем
                    //указатель назад
                    //и возвращаем позицию первого множителя
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token:" + lexeme.value);
            }
        }
    }

    public static int plusMinus(LexemeBuffer lexemes) {
        //25+9-(16*5-(11+6*9))
        int value = multDiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS:
                    value = value + multDiv(lexemes);
                    break;
                case OP_MINUS:
                    value = value - multDiv(lexemes);
                case END:
                case RIGHT_BRACKET:
                    lexemes.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token:" + lexeme.value);
            }
        }
    }
}