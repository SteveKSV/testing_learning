package org.example;

/*
  @author - Stepan Klem
  @project - IntegerToRoman
  @date: 28.08.2024
*/

public class Main {
    public static void main(String[] args) {
        Converter converter = new Converter();
        int number = 1987;
        String romanNumeral = converter.intToRoman(number);
        System.out.println("Число " + number + " у римських цифрах: " + romanNumeral);
    }
}