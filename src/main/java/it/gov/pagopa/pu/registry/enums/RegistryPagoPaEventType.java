package it.gov.pagopa.pu.registry.enums;

import lombok.Getter;

@Getter
@SuppressWarnings("java:S115") // Suppressing constant naming warning: this is required to match with the api name
public enum RegistryPagoPaEventType {

  PaForNode_paVerifyPaymentNotice,
  PaForNode_paGetPaymentV2,
  PaForNode_paSendRTV2,

  ACA_newDebtPosition,

  GPD_createPosition,
  GPD_updatePosition,
  GPD_deletePosition,

  NodeForPa_fetchPaymentReporting
}
