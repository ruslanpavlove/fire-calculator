import java.util.Scanner;

public class Main {
    private static final int START_YEAR_MIN = 2002;
    private static final int START_YEAR_MAX = 2021;
    private static final double INITIAL_CAPITAL = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите год начала жизни на проценты (" + START_YEAR_MIN + "-" + START_YEAR_MAX + "): ");
        try {
            String inputYear = scanner.nextLine();
            int startYear = Integer.parseInt(inputYear);
            if (startYear < START_YEAR_MIN || startYear > START_YEAR_MAX) {
                throw new IllegalArgumentException("Год должен быть в диапазоне " + START_YEAR_MIN + "-" + START_YEAR_MAX + "!");
            }
            double maxWithdrawal = withdrawal(startYear);
            System.out.printf("Максимальный процент изъятия: %.1f%%", maxWithdrawal);
        } catch (NumberFormatException e) {
            System.out.print("Неправильный формат ввода!");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        scanner.close();
    }

    private static double withdrawal(int startYear) {
        double withdrawalPercent = 100;
        double capital;
        double expenses;

        for (double percent = withdrawalPercent; percent > 0; percent = percent - 0.5) {
            capital = INITIAL_CAPITAL;
            expenses = capital * (percent / 100);
            boolean isPositiveBalance = true;
            boolean isFirstYear = true;

            for (int yearIndex = startYear - START_YEAR_MIN; yearIndex < Constants.INFLATION_RATE.length - 1; yearIndex++) {
                if (isFirstYear == false) {
                    expenses *= (1 + Constants.INFLATION_RATE[yearIndex - 1] / 100);
                }
                isFirstYear = false;

                capital -= expenses;
                capital *= (Constants.MOEX_RATE[yearIndex + 1] / Constants.MOEX_RATE[yearIndex]);

                if (capital < 0) {
                    isPositiveBalance = false;
                    break;
                }
            }
            if (isPositiveBalance) {
                withdrawalPercent = percent;
                break;
            }
        }
        return withdrawalPercent;
    }
}