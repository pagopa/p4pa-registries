package it.gov.pagopa.pu.registry.utils;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RegistryUtils {

  private RegistryUtils() {
  }

  public static String[] split(String toSplit) {
    return StringUtils.hasText(toSplit)
      ? toSplit.split(",")
      : new String[0];
  }

  public static Stream<String> streamAndExtend(String[] values, int minimumSize) {
    Stream<String> out = Arrays.stream(values).map(String::trim);
    int diff = minimumSize - values.length;
    if (diff > 0) {
      Stream<String> fillerStream = IntStream.range(0, diff)
        .mapToObj(i -> null);
      return Stream.concat(out, fillerStream);
    }
    return out;
  }
}

