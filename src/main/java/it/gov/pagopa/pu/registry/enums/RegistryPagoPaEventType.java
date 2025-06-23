package it.gov.pagopa.pu.registry.enums;

import lombok.Getter;

@Getter
@SuppressWarnings("java:S115") // Suppressing constant naming warning: this is required to match with the api name
public enum RegistryPagoPaEventType {

  paVerifyPaymentNotice,
  paGetPaymentV2,
  paSendRTV2,
  newDebtPosition,
  createPosition,
  updatePosition,
  deletePosition,
  fetchPaymentReporting;

}
