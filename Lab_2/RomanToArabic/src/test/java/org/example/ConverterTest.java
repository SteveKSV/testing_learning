package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 /*
  @author - Stepan Klem
  @project - RomanToArabic
  @date: 04.09.2024
 */

class ConverterTest {

    private final Converter converter = new Converter();

    // Тести для базових римських чисел

    @Test
    void whenRomanIsV_ThenReturn5() {
        assertEquals(5, converter.convertRomanToArabic("V"));
    }

    @Test
    void whenRomanIsNull_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic(null);
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input cannot be null", ex.getMessage());
    }

    @Test
    void whenRomanIsEmpty_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("");
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input cannot be empty", ex.getMessage());
    }

    @Test
    void whenRomanIsLongerThan15_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("MMMMMMMMMMMMMMMM"); // 16 characters
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input length must be between 1 and 15", ex.getMessage());
    }

    @Test
    void whenRomanContainsInvalidCharacter_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("A");
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input contains invalid characters", ex.getMessage());
    }

    @Test
    void whenRomanContainsSpaces_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("IV X");
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input contains invalid characters", ex.getMessage());
    }

    @Test
    void whenRomanContainsLowercaseLetters_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("iv");
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input contains invalid characters", ex.getMessage());
    }

    @Test
    void whenRomanContainsSpecialCharacters_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("IV#");
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input contains invalid characters", ex.getMessage());
    }

    @Test
    void whenRomanContainsNumbers_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("IV2");
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input contains invalid characters", ex.getMessage());
    }

    @Test
    void whenRomanIsMixedValidAndInvalid_ThenThrowException() {
        Exception ex = Assertions.assertThrows(Exception.class, () -> {
            converter.convertRomanToArabic("IVXAB");
        });
        Assertions.assertSame(IllegalArgumentException.class, ex.getClass());
        Assertions.assertEquals("Input contains invalid characters", ex.getMessage());
    }

}
