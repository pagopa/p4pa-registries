package it.gov.pagopa.pu.registry.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistryUtilsTest {

  @Test
  void givenNullInput_whenSplit_thenReturnEmptyArray() {
    String[] result = RegistryUtils.split(null);
    assertNotNull(result);
    assertEquals(0, result.length);
  }

  @Test
  void givenEmptyInput_whenSplit_thenReturnEmptyArray() {
    String[] result = RegistryUtils.split("");
    assertNotNull(result);
    assertEquals(0, result.length);
  }

  @Test
  void givenCommaSeparatedString_whenSplit_thenReturnArray() {
    String[] result = RegistryUtils.split("a,b,c");
    assertArrayEquals(new String[]{"a", "b", "c"}, result);
  }

  @Test
  void givenSingleElement_whenSplit_thenReturnArrayWithOneElement() {
    String[] result = RegistryUtils.split("single");
    assertArrayEquals(new String[]{"single"}, result);
  }

  @Test
  void givenExactSizeArray_whenStreamAndExtend_thenReturnSameElements() {
    String[] values = {"x", "y"};
    List<String> result = RegistryUtils.streamAndExtend(values, 2).toList();
    assertEquals(List.of("x", "y"), result);
  }

  @Test
  void givenLongerArrayThanMin_whenStreamAndExtend_thenReturnOriginalTrimmedElements() {
    String[] values = {" val1 ", " val2 "};
    List<String> result = RegistryUtils.streamAndExtend(values, 1).toList();
    assertEquals(List.of("val1", "val2"), result);
  }
}
