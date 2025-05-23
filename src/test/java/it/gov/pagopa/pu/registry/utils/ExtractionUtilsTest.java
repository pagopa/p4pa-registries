package it.gov.pagopa.pu.registry.utils;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtractionUtilsTest {

  @Test
  void testExtractIuds_present() {
    String str1 = "IUD: id1, id2, id3; CODE: code1, code2;";
    String str2 = "Hello world. IUD: id4, id5 ; CODE: c1;";
    String str3 = "Hello. CODE: c2; IUD: id6, id7, id8  ;";

    Set<String> expected1 = Set.of("id1", "id2", "id3");
    Set<String> expected2 = Set.of("id4", "id5");
    Set<String> expected3 = Set.of("id6", "id7", "id8");

    assertEquals(expected1, ExtractionUtils.extractIudIdsFromDescription(str1));
    assertEquals(expected2, ExtractionUtils.extractIudIdsFromDescription(str2));
    assertEquals(expected3, ExtractionUtils.extractIudIdsFromDescription(str3));
  }

  @Test
  void testExtractIuds_absent() {
    String str1 = "Hello there. IUD code1, code2";
    String str2 = "Completely unrelated text.";
    String str3 = "";

    assertTrue(ExtractionUtils.extractIudIdsFromDescription(str1).isEmpty());
    assertTrue(ExtractionUtils.extractIudIdsFromDescription(str2).isEmpty());
    assertTrue(ExtractionUtils.extractIudIdsFromDescription(str3).isEmpty());
  }

}
