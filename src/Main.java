import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Год:");
        String inputYear = scanner.nextLine();

        try {
            int startYear = Integer.parseInt(inputYear);
            if (startYear < 2002 || startYear > 2021) {
                throw new IllegalArgumentException("throws Exception…");
            }
        } catch (NumberFormatException e) {
            System.out.print("Введи год в диапазоне от 2002 до 2021");
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
        }
    }
}