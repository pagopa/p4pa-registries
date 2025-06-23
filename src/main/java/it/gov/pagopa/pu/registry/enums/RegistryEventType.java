package it.gov.pagopa.pu.registry.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static it.gov.pagopa.pu.registry.utils.Constants.PAGOPA_UNIT;
import static it.gov.pagopa.pu.registry.utils.Constants.SIL_UNIT;

@Getter
@RequiredArgsConstructor
public enum RegistryEventType {

  paVerifyPaymentNotice(PAGOPA_UNIT),
  paGetPaymentV2(PAGOPA_UNIT),
  paSendRTV2(PAGOPA_UNIT),
  newDebtPosition(PAGOPA_UNIT),
  createPosition(PAGOPA_UNIT),
  updatePosition(PAGOPA_UNIT),
  deletePosition(PAGOPA_UNIT),
  fetchPaymentReporting(PAGOPA_UNIT),

  paaSILAutorizzaImportFlusso(SIL_UNIT),
  paaSILChiediStatoImportFlusso(SIL_UNIT),
  paaSILImportaDovuto(SIL_UNIT),
  paaSILPrenotaExportFlusso(SIL_UNIT),
  paaSILPrenotaExportFlussoIncrementaleConRicevuta(SIL_UNIT),
  paaSILChiediStatoExportFlusso(SIL_UNIT),
  paaSILInviaDovuti(SIL_UNIT),
  paaSILVerificaAvviso(SIL_UNIT),
  paaSILChiediPagati(SIL_UNIT),
  paaSILChiediPagatiConRicevuta(SIL_UNIT),
  paaSILInviaCarrelloDovuti(SIL_UNIT),
  paaSILChiediEsitoCarrelloDovuti(SIL_UNIT),
  pivotSILAutorizzaImportFlusso(SIL_UNIT),
  pivotSILChiediStatoImportFlusso(SIL_UNIT),
  pivotSILAutorizzaImportFlussoTesoreria(SIL_UNIT),
  pivotSILChiediStatoImportFlussoTesoreria(SIL_UNIT),
  pivotSILPrenotaExportFlussoRiconciliazione(SIL_UNIT),
  pivotSILChiediStatoExportFlussoRiconciliazione(SIL_UNIT),
  pivotSILChiediAccertamento(SIL_UNIT),
  attualizzazioneImporti(SIL_UNIT),
  notificaPagamento(SIL_UNIT);

  private final String unit;

}
