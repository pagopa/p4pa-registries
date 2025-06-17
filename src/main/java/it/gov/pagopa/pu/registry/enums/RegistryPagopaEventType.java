package it.gov.pagopa.pu.registry.enums;

import lombok.Getter;

@Getter
@SuppressWarnings("java:S115") // Suppressing constant naming warning: this is required to match with the api name
public enum RegistryPagopaEventType {

  paVerifyPaymentNotice(true),
  paGetPaymentV2(true),
  paSendRTV2(true),
  newDebtPosition(false),
  createPosition(false),
  updatePosition(false),
  deletePosition(false),
  fetchPaymentReporting(false);

  private final boolean exposedByPU;

  RegistryPagopaEventType(boolean exposedByPU) {
    this.exposedByPU = exposedByPU;
  }

}
