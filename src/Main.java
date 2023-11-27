import java.util.Scanner;

public class Main {
    private static final int START_YEAR_MIN = 2002;
    private static final int START_YEAR_MAX = 2021;
    private static final double INITIAL_CAPITAL = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите год начала жизни на проценты (" + START_YEAR_MIN + "-" + START_YEAR_MAX + "): ");
        String inputYear = scanner.nextLine();
        int startYear = Integer.parseInt(inputYear);
        if (startYear < START_YEAR_MIN || startYear > START_YEAR_MAX) {
            throw new IllegalArgumentException("Год должен быть в диапазоне " + START_YEAR_MIN + "-" + START_YEAR_MAX + "!");
        }
        double maxWithdrawal = calculateMaxWithdrawal(startYear, INITIAL_CAPITAL);
        System.out.printf("Максимальный процент изъятия: %.1f%%", maxWithdrawal);
        scanner.close();
    }

    private static double calculateMaxWithdrawal(int startYear, double initialCapital) {
        double withdrawalPercent = 100;
        double capital;
        double expenses;

        for (double percent = withdrawalPercent; percent > 0; percent -= 0.5) {
            capital = initialCapital;
            expenses = capital * (percent / 100);
            boolean isPositiveBalance = checkPositiveBalance(startYear, capital, expenses);
            if (isPositiveBalance) {
                withdrawalPercent = percent;
                break;
            }
        }
        return withdrawalPercent;
    }

    private static boolean checkPositiveBalance(int startYear, double capital, double expenses) {
        boolean isFirstYear = true;

        for (int yearIndex = startYear - START_YEAR_MIN; yearIndex < Constants.INFLATION_RATE.length - 1; yearIndex++) {
            if (isFirstYear == false) {
                expenses *= (1 + Constants.INFLATION_RATE[yearIndex - 1] / 100);
            }
            isFirstYear = false;
            capital -= expenses;
            capital *= (Constants.MOEX_RATE[yearIndex + 1] / Constants.MOEX_RATE[yearIndex]);
            if (capital < 0) {
                return false;
            }
        }
        return true;
    }
}