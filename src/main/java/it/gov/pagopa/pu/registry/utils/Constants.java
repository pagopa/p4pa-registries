package it.gov.pagopa.pu.registry.utils;

import java.time.ZoneId;

public class Constants {

  private Constants(){}

  public static final ZoneId ZONEID = ZoneId.of("Europe/Rome");

  public static final String PAGOPA_REGISTRY_TYPE = "REGISTRY_PAGOPA";
  public static final String SIL_REGISTRY_TYPE = "REGISTRY_SIL";

}

