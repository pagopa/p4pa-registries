package it.gov.pagopa.pu.registry.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractionUtils {

  public static Set<String> extractIudIdsFromDescription(String description) {
    Set<String> iudIds = new HashSet<>();
    Pattern pattern = Pattern.compile("IUD:\\s*([^;]*)");
    Matcher matcher = pattern.matcher(description);

    if (matcher.find()) {
      String ids = matcher.group(1);
      String[] splitIds = ids.split(",");
      for (String id : splitIds) {
        String trimmedId = id.trim();
        if (!trimmedId.isEmpty()) {
          iudIds.add(trimmedId);
        }
      }
    }

    return iudIds;
  }

}
