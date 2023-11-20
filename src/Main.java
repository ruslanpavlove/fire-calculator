import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input:");
        try {
            String inputYear = scanner.nextLine();
            int startYear = Integer.parseInt(inputYear);
            if (startYear < 2002 || startYear > 2021) {
                throw new IllegalArgumentException("Output:\nthrows Exception…");
            }
            double maxWithdrawal = withdrawal(startYear);
            System.out.print("Output:\n" + maxWithdrawal);
        } catch (NumberFormatException e) {
            System.out.print("Output:\nthrows Exception…");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
        scanner.close();
    }

    private static double withdrawal(int startYear) {
        double withdrawalPercent = 100; // 100 / (2022 - startYear) + 5; // Начальное предположение процента изъятия
        double initialCapital = 1; // Начальный капитал
        double capital;
        double expenses;

        for (double percent = withdrawalPercent; percent > 0; percent = percent - 0.5) { // Перебор процентов
            capital = initialCapital;
            expenses = capital * (percent / 100);
            boolean isPositiveBalance = true; // Флаг устойчивости капитала
            boolean isFirstYear = true; // Флаг первого года

            for (int yearIndex = startYear - 2002; yearIndex < Constants.INFLATION_RATE.length - 1; yearIndex++) {
                if (isFirstYear == false) {
                    expenses *= (1 + Constants.INFLATION_RATE[yearIndex - 1] / 100); // Корректировка расходов с учетом инфляции, в первый год не учитывается
                }
                isFirstYear = false;

                capital -= expenses; // Изымание денег из капитала на расходы

                capital *= (Constants.MOEX_RATE[yearIndex + 1] / Constants.MOEX_RATE[yearIndex]); // Корректировка капитала на прирост

                capital *= (1 - Constants.INFLATION_RATE[yearIndex] / 100); // Индексация на инфляцию

                if (capital < 0) {
                    isPositiveBalance = false; // Проверка на устойчивость капитала
                    break;
                }
            }
            if (isPositiveBalance) {
                withdrawalPercent = percent; // Выход из цикла, когда найден процент при котором итоговый капитал >= 0
                break;
            }
        }
        return withdrawalPercent;
    }
}