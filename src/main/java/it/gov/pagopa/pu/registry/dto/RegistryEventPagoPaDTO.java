package it.gov.pagopa.pu.registry.dto;

import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagoPaEventType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryEventPagoPaDTO extends RegistryInterfaceEventDTO {
  @NotNull
  private RegistryPagopaEventType eventType;
  private String brokerStationId;
  private String brokerFiscalCode;
  @NotNull
  private String orgFiscalCode;
  private String iuv;
  private String nav;
  private String ccp;
  private String pspId;
  private String pspChannelId;
  private String paymentMethod;
  @NotNull
  private RegistryEventCategory eventCategory;
  @NotNull
  private RegistryOutcome outcome;
  private String body;
}
