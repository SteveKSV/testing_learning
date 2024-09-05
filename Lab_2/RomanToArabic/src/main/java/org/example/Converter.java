package org.example;

/*
 @author - Stepan Klem
 @project - RomanToArabic
 @date: 04.09.2024
*/

public class Converter {
    private static final String VALID_ROMAN_REGEX = "[IVXLCDM]+";

    public int convertRomanToArabic(String roman) {
        if (roman == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        if (roman.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        if (roman.length() < 1 || roman.length() > 15) {
            throw new IllegalArgumentException("Input length must be between 1 and 15");
        }

        if (!roman.matches(VALID_ROMAN_REGEX)) {
            throw new IllegalArgumentException("Input contains invalid characters");
        }

        // Масив для зберігання значень римських чисел
        int[] values = new int[26]; // Створюємо масив з розміром 26 (кількість літер в англійському алфавіті)
        // Встановлюємо значення для кожного римського символу, використовуючи їх позицію в алфавіті
        values['I' - 'A'] = 1;
        values['V' - 'A'] = 5;
        values['X' - 'A'] = 10;
        values['L' - 'A'] = 50;
        values['C' - 'A'] = 100;
        values['D' - 'A'] = 500;
        values['M' - 'A'] = 1000;

        // Ініціалізуємо підсумок
        int arabic = 0;

        // Проходимо через всі символи римського числа
        for (int i = 0; i < roman.length(); i++) {
            int current = values[roman.charAt(i) - 'A']; // Отримуємо значення поточного символу

            // Якщо це останній символ або наступний символ має менше або рівне значення, додаємо поточне значення
            if (i == roman.length() - 1 || values[roman.charAt(i + 1) - 'A'] <= current) {
                arabic += current;
            } else {
                // Якщо наступний символ має більше значення, віднімаємо поточне значення (обробляємо випадки як IV, IX)
                arabic -= current;
            }
        }

        return arabic; // Повертаємо підсумкове арабське число
    }
}
