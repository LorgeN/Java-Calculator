package com.lorgen.calculator.numbers;

import com.lorgen.calculator.Calculator;

import java.util.LinkedList;
import java.util.List;

public class NumberUtils {
    //TODO: Add timeout to prime related tasks

    public static boolean isPrime(long number) {
        if ((number != 2 && number % 2 == 0) || number <= 1) return false;
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

    public static List<Long> findPrimes(long to) {
        return NumberUtils.findPrimes(0, to);
    }

    public static List<Long> findPrimes(long from, long to) {
        List<Long> primes = new LinkedList<>();
        if (from <= 2) primes.add(2L);
        if (from % 2 == 0) from++;
        for (long i = from; i <= to; i+=2) if (NumberUtils.isPrime(i)) primes.add(i);
        return primes;
    }

    public static long estimatedPrimeAmount(long to) {
        return (long) (to/Math.log(to));
    }

    public static long estimatedPrimeAmount(long from, long to) {
        return NumberUtils.estimatedPrimeAmount(to)-NumberUtils.estimatedPrimeAmount(from);
    }

    public static Long[] factorize(long number) {
        if (NumberUtils.isPrime(number)) return new Long[] {number};
        List<Long> factors = new LinkedList<>();
        long left = number;

        while (left != 1) {
            if (NumberUtils.isPrime(left)) {
                factors.add(left);
                left = 1;
                continue;
            }

            for (Long prime : NumberUtils.findPrimes((long) Math.sqrt(left))) {
                if (NumberUtils.isInteger((double) left / (double) prime)) {
                    factors.add(prime);
                    left = left / prime;
                    break;
                }
            }
        }

        return factors.toArray(new Long[factors.size()]);
    }
}
