package shop.exceptions;

import java.util.Scanner;

public class inputData {

    private static Integer i;
    private static double  d;

    public static Integer inputInt(Scanner scanner) {

        boolean isValid = false;
        while (!isValid) {
            isValid = true;

            try {
                 i = Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {
                System.out.println("Грешен вход на данни! Моля въведете отново");
                isValid = false;
            }
        }
        return i;
    }
    public static double inputDouble(Scanner scanner){
        boolean isValid = false;
        while (!isValid) {
            isValid = true;

            try {
                d = Double.parseDouble(scanner.nextLine());

            } catch (Exception e) {
                System.out.println("Грешен вход на данни! Моля въведете отново");
                isValid = false;
            }
        }
        return d;
    }
}
