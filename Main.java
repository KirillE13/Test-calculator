import java.io.IOException;
import java.util.TreeMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calc one = new Calc();
        System.out.println("Input: ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        System.out.println("Output: ");
        System.out.println(Calc.calc(str));

        scanner.close();
    }
}
class Calc {
    // Создаем TreeMap с римскими цифрами для входящих данных.
    static TreeMap<String, Integer> roman = new TreeMap<>();
    // Создаем TreeMap с римскими цифрами для вывода результата.
    static TreeMap<Integer, String> arabian = new TreeMap<>();

    Calc() {
        roman.put("I", 1);
        roman.put("II", 2);
        roman.put("III", 3);
        roman.put("IV", 4);
        roman.put("V", 5);
        roman.put("VI", 6);
        roman.put("VII", 7);
        roman.put("VIII", 8);
        roman.put("IX", 9);
        roman.put("X", 10);

        arabian.put(1, "I");
        arabian.put(4, "IV");
        arabian.put(5, "V");
        arabian.put(9, "IX");
        arabian.put(10, "X");
        arabian.put(40, "XL");
        arabian.put(50, "L");
        arabian.put(90, "XC");
        arabian.put(100, "C");
    }
// Проверяем на правильность написания арабских цифр. П. 2 требований.
    static boolean isLegal(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
// Проверяем, чтобы оба операнда были в римской системе счисления. П. 5 требований.
    static boolean isRoman(String s) {
        return roman.containsKey(s);
    }
// Метод переводит входящие римские цифры в int.
    static int romanToInt(String s) {
        return roman.get(s);
    }
//Перевод результата работы калькулятора в римскую систему счисления.
    static String romanToArabian(int i) {
        int number = arabian.floorKey(i);
        if (i == number) {
            return arabian.get(i);
        }
        return arabian.get(number) + romanToArabian(i - number);
    }

    public static String calc(String input) {
        String[] arguments = input.trim().split(" ");

        String result = "";
        try {
            // Ограничиваем ввод пользователем данных: два операнда и один оператор.
            if (arguments.length != 3) throw new ArrayIndexOutOfBoundsException("Введите два операнда и один оператор (+, -, /, *) через пробелы.");
            int a, b;
            // Приведенный ниже код переводит введенные римские цифры в int для дальнейшей обработки.
            if (Calc.isRoman(arguments[0]) & Calc.isRoman(arguments[2])) {
                a = Calc.romanToInt(arguments[0]);
                b = Calc.romanToInt(arguments[2]);
            }

            else {
                // Проверяем правильность ввода арабских цифр и ограничиваем их от 1 до 10 включительно.
                if (!Calc.isLegal(arguments[0]) | !Calc.isLegal(arguments[2]))
                    throw new NumberFormatException("Ипользуйте одну систему счисления: либо арабские цифры, либо римские цифры от I до X включительно.");
                a = Integer.parseInt(arguments[0]);
                b = Integer.parseInt(arguments[2]);
                if (a < 1 | a > 10 | b < 1 | b > 10) throw new IOException("Калькулятор принимает на вход числа от 1 до 10 включительно.");
            }
            // Выполняем математическое действие.
            int operation = switch (arguments[1]) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                case "/" -> a / b;
                default -> throw new IllegalStateException("Корректно введите один оператор (+, -, /, *).");
            };
            // Выводим результат в арабской системе счисления, если пользователь ввел арабские цифры или выводим результат в римской системе, если пользователь ввел римские цифры.
            if (Calc.isRoman(arguments[0]) & (operation > 0)) {
                result = romanToArabian(operation);
            } else if(Calc.isRoman(arguments[0]) & (operation < 0)) {
                throw new IOException("В римской системе счисления нет отрицательных чисел.");
            } else
                result = Integer.toString(operation);
        } catch (IOException | ArrayIndexOutOfBoundsException | IllegalStateException | NumberFormatException e) {
            System.out.println("throws Exception: " + e);
        }
        return result;
    }
}