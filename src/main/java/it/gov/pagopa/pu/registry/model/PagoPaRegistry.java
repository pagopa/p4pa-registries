package it.gov.pagopa.pu.registry.model;

import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagoPaEventType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Document(collection = "pagopa_registry")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class PagoPaRegistry implements Serializable {

  @Id
  @NotNull
  private String registryId;
  @NotNull
  private String registryOrigin;
  @NotNull
  private OffsetDateTime dateTime;
  @NotNull
  private String traceId;
  private String brokerStationId;
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
  private RegistryPagoPaEventType eventType;
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
