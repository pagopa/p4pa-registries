package it.gov.pagopa.pu.registry.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilitiesTest {

  @Test
  void givenNullInput_whenSplit_CommaString_thenReturnEmptyArray() {
    String[] result = Utilities.splitCommaString(null);
    assertNotNull(result);
    assertEquals(0, result.length);
  }

  @Test
  void givenEmptyInput_whenSplit_CommaString_thenReturnEmptyArray() {
    String[] result = Utilities.splitCommaString("");
    assertNotNull(result);
    assertEquals(0, result.length);
  }

  @Test
  void givenCommaSeparatedString_whenSplit_CommaString_thenReturnArray() {
    String[] result = Utilities.splitCommaString("a,b,c");
    assertArrayEquals(new String[]{"a", "b", "c"}, result);
  }

  @Test
  void givenSingleElement_whenSplit_CommaString_thenReturnArrayWithOneElement() {
    String[] result = Utilities.splitCommaString("single");
    assertArrayEquals(new String[]{"single"}, result);
  }

  @Test
  void givenExactSizeArray_whenStreamAndExtend_thenReturnSameElements() {
    String[] values = {"x", "y"};
    List<String> result = Utilities.streamAndExtend(values, 2, null).toList();
    assertEquals(List.of("x", "y"), result);
  }

  @Test
  void givenLongerArrayThanMin_whenStreamAndExtend_thenReturnOriginalTrimmedElements() {
    String[] values = {" val1 ", " val2 "};
    List<String> result = Utilities.streamAndExtend(values, 1, null).toList();
    assertEquals(List.of("val1", "val2"), result);
  }

  @Test
  void testGetTraceId(){
    // Given
    String expectedResult = "TRACEID";
    setTraceId(expectedResult);

    // When
    String result = Utilities.getTraceId();

    // Then
    Assertions.assertSame(expectedResult, result);
    clearTraceIdContext();
  }

  public static void setTraceId(String traceId) {
    MDC.put("traceId", traceId);
  }
  public static void clearTraceIdContext(){
    MDC.clear();
  }
}
