package it.gov.pagopa.pu.registry.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.utils.Constants;
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
  defaultImpl = RegistryInterfaceEventDTO.class,
  visible = true
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = RegistryEventPagoPaDTO.class, name = Constants.PAGOPA_REGISTRY_TYPE),
  @JsonSubTypes.Type(value = RegistryEventSilDTO.class, names = Constants.SIL_REGISTRY_TYPE)
})
public class RegistryInterfaceEventDTO {
  @NotNull
  private String registryId;
  private String registryOrigin;
  @NotNull
  private String registryType;
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
