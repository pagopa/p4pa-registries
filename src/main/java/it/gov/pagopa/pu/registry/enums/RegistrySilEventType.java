package it.gov.pagopa.pu.registry.enums;

import lombok.Getter;

@Getter
@SuppressWarnings("java:S115") // Suppressing constant naming warning: this is required to match with the api name
public enum RegistrySilEventType {

  PTDP_paaSILAutorizzaImportFlusso,
  PTDP_paaSILImportaDovuto,
  PTDP_paaSILInviaDovuti,
  PTDP_paaSILInviaCarrelloDovuti,
  PTDP_paaSILVerificaAvviso,
  PTDP_paaSILChiediPosizioniAperte,
  PTDP_paaSILChiediStoricoPagamenti,

  PTPR_pivotSILAutorizzaImportFlussoTesoreria,
  PTPR_pivotSILAutorizzaImportFlusso,

  SIL_attualizzazioneImporti,
  SIL_notificaPagamento
}
