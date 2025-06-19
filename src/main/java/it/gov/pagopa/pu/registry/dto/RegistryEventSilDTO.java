package it.gov.pagopa.pu.registry.dto;

import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistrySilEventType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegistryEventSilDTO {
  @NotNull
  private String registryId;
  private String registryOrigin;
  private String registryType;
  @NotNull
  private OffsetDateTime dateTime;
  @NotNull
  private String traceId;
  @NotNull
  private String brokerFiscalCode;
  @NotNull
  private String orgFiscalCode;
  private String iuv;
  private String nav;
  @NotNull
  private RegistrySilEventType eventType;
  @NotNull
  private RegistryEventSubType eventSubType;
  @NotNull
  private String requestorId;
  @NotNull
  private String grantorId;
  @NotNull
  private RegistryOutcome outcome;
  private String body;
}
