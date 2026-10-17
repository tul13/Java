import java.util.Scanner;
class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например: 2 * 6.5):");

        String input = scanner.nextLine().trim();
        try {
            double result = calculate(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.err.println("Ошибка вычисления: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Вычисляет выражение вида "число оператор число".
     */
    public static double calculate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Пустое выражение");
        }

        // Ищем оператор среди + - * /
        // Обрабатываем возможные отрицательные числа: ищем оператор,
        // у которого есть число слева (не считая знака минус в начале числа).
        char operator = 0;
        int opIndex = -1;

        for (int i = 1; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                operator = c;
                opIndex = i;
                break;
            }
        }

        if (opIndex == -1) {
            throw new IllegalArgumentException("Не найден оператор (+, -, *, /)");
        }

        String leftPart = expression.substring(0, opIndex).trim();
        String rightPart = expression.substring(opIndex + 1).trim();

        double a;
        double b;
        try {
            a = Double.parseDouble(leftPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное левое число: '" + leftPart + "'");
        }
        try {
            b = Double.parseDouble(rightPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное правое число: '" + rightPart + "'");
        }

        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Неизвестный оператор: " + operator);
        };
    }
}
