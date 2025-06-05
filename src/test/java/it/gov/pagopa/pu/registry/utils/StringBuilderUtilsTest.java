package it.gov.pagopa.pu.registry.utils;

import it.gov.pagopa.pu.registry.dto.KeyValueDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringBuilderUtilsTest {

  @Test
  void GivenBaseWhenAppendFromListThenResultIsCorrect() {
    // Given
    StringBuilder sb = new StringBuilder();
    sb.append("This is a description.");
    List<KeyValueDTO<String>> arr = List.of(
      new KeyValueDTO<>("key1", "value1"),
      new KeyValueDTO<>("key2", "value2")
    );

    // When
    StringBuilderUtils.appendFromArray(sb, arr, ": ", "; ");

    // Then
    assertEquals("This is a description.; key1: value1; key2: value2", sb.toString());
  }

  @Test
  void GivenEmptyBaseWhenAppendFromListThenResultIsCorrect() {
    // Given
    StringBuilder sb = new StringBuilder();
    List<KeyValueDTO<String>> arr = List.of(
      new KeyValueDTO<>("key1", "value1"),
      new KeyValueDTO<>("key2", "value2")
    );

    // When
    StringBuilderUtils.appendFromArray(sb, arr, ": ", "; ");

    // Then
    assertEquals("key1: value1; key2: value2", sb.toString());
  }

}
