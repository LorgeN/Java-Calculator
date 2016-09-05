package com.lorgen.calculator.numerical;

public class NumberUtils {
    public static boolean isPrime(int number) {
        if (number % 2 == 0) return false;
        for (int i = 3; i * i <= number; i += 2) if (number % i == 0) return false;
        return true;
    }

    public static boolean isInteger(double number) {
        return number == (int) number;
    }

    public static boolean isNatural(double number) {
        if (!isInteger(number)) return false;
        return number > 0;
    }
}
