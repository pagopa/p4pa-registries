package it.gov.pagopa.pu.registry.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionUtils {

  private ExtractionUtils() {
    // Constructor is intentionally empty to hide the default public one
  }

  private static final Pattern IUD_MATCH_PATTERN = Pattern.compile("IUD:\\s*([^;]*)\\s*(?:;|$)");

  public static Set<String> extractIudsFromDescription(String description) {
    Set<String> iuds = new HashSet<>();
    Matcher matcher = IUD_MATCH_PATTERN.matcher(description);

    if (matcher.find()) {
      String ids = matcher.group(1);
      String[] splitIds = ids.split(",");
      for (String id : splitIds) {
        String trimmedId = id.trim();
        if (!trimmedId.isEmpty()) {
          iuds.add(trimmedId);
        }
      }
    }

    return iuds;
  }

}
