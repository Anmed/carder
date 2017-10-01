package com.anmed.storage.validator;


public class CardValidationUtil {

    /**
     * @param numbers string representation o card number
     * @return result of validation
     * @see <a href="https://en.wikipedia.org/wiki/Luhn_algorithm#Java">wiki page</a>
     */
    static boolean validatedByLuhnAlgorithm(char[] numbers) {

        int sum = 0;
        int length = numbers.length;
        for (int i = 0; i < length; i++) {

            // get digits in reverse order
            int digit = Character.getNumericValue(numbers[length - i - 1]);

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }
}
