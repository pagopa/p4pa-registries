package it.gov.pagopa.pu.registry.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryType;
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
  property = "registryType",
  visible = true
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = RegistryEventPagoPaDTO.class, name = "REGISTRY_PAGOPA"),
  @JsonSubTypes.Type(value = RegistryEventSilDTO.class, names = "REGISTRY_SIL"),
  @JsonSubTypes.Type(value = RegistryEventSendTimelineDTO.class, names = "REGISTRY_SEND")
})
public class RegistryInterfaceEventDTO {
  @NotNull
  private String registryId;
  private String registryOrigin;
  @NotNull
  private RegistryType registryType;
  @NotNull
  private OffsetDateTime dateTime;
  @NotNull
  private String traceId;
  @NotNull
  private RegistryEventSubType eventSubType;
  @NotNull
  private String requestorId;
  @NotNull
  private String grantorId;
}
