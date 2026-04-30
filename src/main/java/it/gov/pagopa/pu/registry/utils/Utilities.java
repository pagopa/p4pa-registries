package it.gov.pagopa.pu.registry.utils;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utilities {

  private Utilities() {
  }

  public static String[] splitCommaString(String toSplit) {
    return StringUtils.hasText(toSplit)
      ? toSplit.split(",")
      : new String[0];
  }

  public static Stream<String> streamAndExtend(String[] values, int minimumSize, String padding) {
    Stream<String> out = Arrays.stream(values).map(String::trim);
    int diff = minimumSize - values.length;
    if (diff > 0) {
      Stream<String> fillerStream = IntStream.range(0, diff)
        .mapToObj(i -> padding);
      return Stream.concat(out, fillerStream);
    }
    return out;
  }

  public static String getTraceId(){
    return MDC.get("traceId");
  }
}

