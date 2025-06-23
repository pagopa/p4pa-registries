package it.gov.pagopa.pu.registry.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryEventType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.EXISTING_PROPERTY,
  property = "eventType",
  defaultImpl = RegistryEventDTO.class,
  visible = true
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = RegistryEventPagoPaDTO.class, names = {
    "paVerifyPaymentNotice",
    "paGetPaymentV2",
    "paSendRTV2",
    "newDebtPosition",
    "createPosition",
    "updatePosition",
    "deletePosition",
    "fetchPaymentReporting"
  }),
  @JsonSubTypes.Type(value = RegistryEventSilDTO.class, names = {
    "paaSILAutorizzaImportFlusso",
    "paaSILChiediStatoImportFlusso",
    "paaSILImportaDovuto",
    "paaSILPrenotaExportFlusso",
    "paaSILPrenotaExportFlussoIncrementaleConRicevuta",
    "paaSILChiediStatoExportFlusso",
    "paaSILInviaDovuti",
    "paaSILVerificaAvviso",
    "paaSILChiediPagati",
    "paaSILChiediPagatiConRicevuta",
    "paaSILInviaCarrelloDovuti",
    "paaSILChiediEsitoCarrelloDovuti",
    "pivotSILAutorizzaImportFlusso",
    "pivotSILChiediStatoImportFlusso",
    "pivotSILAutorizzaImportFlussoTesoreria",
    "pivotSILChiediStatoImportFlussoTesoreria",
    "pivotSILPrenotaExportFlussoRiconciliazione",
    "pivotSILChiediStatoExportFlussoRiconciliazione",
    "pivotSILChiediAccertamento",
    "attualizzazioneImporti",
    "notificaPagamento"
  })
})
public class RegistryEventDTO {
  @NotNull
  private String registryId;
  private String registryOrigin;
  private String registryType;
  @NotNull
  private OffsetDateTime dateTime;
  @NotNull
  private String traceId;
  @NotNull
  private RegistryEventType eventType;
  @NotNull
  private RegistryEventSubType eventSubType;
  @NotNull
  private String requestorId;
  @NotNull
  private String grantorId;
}
