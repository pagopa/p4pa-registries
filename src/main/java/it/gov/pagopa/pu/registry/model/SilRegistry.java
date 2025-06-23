package it.gov.pagopa.pu.registry.model;

import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistrySilEventType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Document(collection = "sil_registry")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class SilRegistry implements Serializable {

  @Id
  @NotNull
  private String registryId;
  @NotNull
  private String registryOrigin;
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
  private byte[] bodyCiphered;
}
