package it.gov.pagopa.pu.registry.dto;

import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
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
public class RegistryEventSilDTO extends RegistryEventDTO {
  @NotNull
  private String brokerFiscalCode;
  @NotNull
  private String orgFiscalCode;
  private String iuv;
  private String nav;
  @NotNull
  private RegistryOutcome outcome;
  private String body;
}
