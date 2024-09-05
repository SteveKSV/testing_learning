package org.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
 /*
  @author - Stepan Klem
  @project - IntegerToRoman
  @date: 28.08.2024
 */

public class ConverterTest {

    private final Converter converter = new Converter();

    @Test
    public void WhenNumberIsLessThan_1ThenReturnNull() {
        assertNull(converter.intToRoman(0));
        assertNull(converter.intToRoman(-5));
    }
    @Test public void WhenNumberIs_4ThenReturnIV() {
        assertEquals("IV", converter.intToRoman(4));
    }
    @Test public void WhenNumberIs_9ThenReturnIX() { assertEquals("IX", converter.intToRoman(9)); }
    @Test public void WhenNumberIs_40ThenReturnXL() { assertEquals("XL", converter.intToRoman(40)); }
    @Test public void WhenNumberIs_90ThenReturnXC() { assertEquals("XC", converter.intToRoman(90)); }
    @Test public void WhenNumberIs_400ThenReturnCD() { assertEquals("CD", converter.intToRoman(400)); }
    @Test public void WhenNumberIs_900ThenReturnCM() { assertEquals("CM", converter.intToRoman(900)); }
    @Test public void WhenNumberIs_3ThenReturnIII() { assertEquals("III", converter.intToRoman(3)); }
    @Test public void WhenNumberIs_8ThenReturnVIII() { assertEquals("VIII", converter.intToRoman(8)); }
    @Test public void WhenNumberIs_30ThenReturnXXX() { assertEquals("XXX", converter.intToRoman(30)); }
    @Test public void WhenNumberIs_80ThenReturnLXXX() { assertEquals("LXXX", converter.intToRoman(80)); }
    @Test public void WhenNumberIs_300ThenReturnCCC() { assertEquals("CCC", converter.intToRoman(300)); }
    @Test public void WhenNumberIs_1ThenReturnI() { assertEquals("I", converter.intToRoman(1)); }
    @Test public void WhenNumberIs_3999ThenReturnMMMCMXCIX() { assertEquals("MMMCMXCIX", converter.intToRoman(3999)); }
    @Test public void WhenNumberIs_1987ThenReturnMCMLXXXVII() { assertEquals("MCMLXXXVII", converter.intToRoman(1987)); }
    @Test public void WhenNumberIs_5ThenReturnV() { assertEquals("V", converter.intToRoman(5)); }
    @Test public void WhenNumberIs_6ThenReturnVI() { assertEquals("VI", converter.intToRoman(6)); }
    @Test public void WhenNumberIs_7ThenReturnVII() { assertEquals("VII", converter.intToRoman(7)); }
    @Test public void WhenNumberIs_10ThenReturnX() { assertEquals("X", converter.intToRoman(10)); }
    @Test public void WhenNumberIs_50ThenReturnL() { assertEquals("L", converter.intToRoman(50)); }
    @Test public void WhenNumberIs_60ThenReturnLX() { assertEquals("LX", converter.intToRoman(60)); }
    @Test public void WhenNumberIs_70ThenReturnLXX() { assertEquals("LXX", converter.intToRoman(70)); }
    @Test public void WhenNumberIs_100ThenReturnC() { assertEquals("C", converter.intToRoman(100)); }
    @Test public void WhenNumberIs_200ThenReturnCC() { assertEquals("CC", converter.intToRoman(200)); }
    @Test public void WhenNumberIs_500ThenReturnD() { assertEquals("D", converter.intToRoman(500)); }
    @Test public void WhenNumberIs_600ThenReturnDC() { assertEquals("DC", converter.intToRoman(600)); }
    @Test public void WhenNumberIs_700ThenReturnDCC() { assertEquals("DCC", converter.intToRoman(700)); }
    @Test public void WhenNumberIs_1000ThenReturnM() { assertEquals("M", converter.intToRoman(1000)); }
    @Test public void WhenNumberIs_2000ThenReturnMM() { assertEquals("MM", converter.intToRoman(2000)); }
    @Test public void WhenNumberIs_2500ThenReturnMMD() { assertEquals("MMD", converter.intToRoman(2500)); }
    @Test public void WhenNumberIs_2700ThenReturnMMDCC() { assertEquals("MMDCC", converter.intToRoman(2700)); }
    @Test public void WhenNumberIs_1500ThenReturnMD() { assertEquals("MD", converter.intToRoman(1500)); }
    @Test public void WhenNumberIs_3333ThenReturnMMMCCCXXXIII() { assertEquals("MMMCCCXXXIII", converter.intToRoman(3333)); }
    @Test public void WhenNumberIs_1776ThenReturnMDCCLXXVI() { assertEquals("MDCCLXXVI", converter.intToRoman(1776)); }
    @Test public void WhenNumberIs_2024ThenReturnMMXXIV() { assertEquals("MMXXIV", converter.intToRoman(2024)); }
    @Test public void WhenNumberIs_3888ThenReturnMMMDCCCLXXXVIII() { assertEquals("MMMDCCCLXXXVIII", converter.intToRoman(3888)); }
    @Test public void WhenNumberIs_49ThenReturnXLIX() { assertEquals("XLIX", converter.intToRoman(49)); }
    @Test public void WhenNumberIs_94ThenReturnXCIV() { assertEquals("XCIV", converter.intToRoman(94)); }
    @Test public void WhenNumberIs_444ThenReturnCDXLIV() { assertEquals("CDXLIV", converter.intToRoman(444)); }
    @Test public void WhenNumberIs_944ThenReturnCMXLIV() { assertEquals("CMXLIV", converter.intToRoman(944)); }
    @Test public void WhenNumberIs_88ThenReturnLXXXVIII() { assertEquals("LXXXVIII", converter.intToRoman(88)); }
    @Test public void WhenNumberIs_111ThenReturnCXI() { assertEquals("CXI", converter.intToRoman(111)); }
}