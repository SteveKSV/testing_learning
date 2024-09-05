package org.example;

 /*
  @author - Stepan Klem
  @project - IntegerToRoman
  @date: 28.08.2024
 */

public class Converter {
  public String intToRoman(int num) {
      if (num < 1) {
          return null;
      }

    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    StringBuilder roman = new StringBuilder();

    for (int i = 0; i < values.length; i++) {
     // Додаємо символи, поки можемо відняти значення від числа
     while (num >= values[i]) {
      roman.append(symbols[i]);
      num -= values[i];
     }
   }

   return roman.toString();
  }
}
