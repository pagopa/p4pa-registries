package it.gov.pagopa.pu.registry.utils;

import it.gov.pagopa.pu.registry.dto.KeyValueDTO;

import java.util.List;

public class StringBuilderUtils {

  public static void appendFromArray(StringBuilder sb, List<KeyValueDTO<String>> array, String separator, String delimiter) {
    if (array == null || array.isEmpty()) {
      return;
    }

    array.forEach(item -> {
      if (!sb.isEmpty()) {
        sb.append(delimiter);
      }

      sb.append(item.getKey()).append(separator).append(item.getValue());
    });
  }

}
