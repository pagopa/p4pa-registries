package it.gov.pagopa.pu.registry.event.registry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagopaEventType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.EXISTING_PROPERTY,
  property = "typeKey",
  defaultImpl = it.gov.pagopa.pu.registry.event.registry.dto.RegistryPagoPaEventDTO.class,
  visible = true
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = PaSendRTV2ResponseEventDTO.class, name = "paSendRTV2.RESP"),
  @JsonSubTypes.Type(value = PaSendRTV2RequestEventDTO.class, name = "paSendRTV2.REQ"),
})
public class RegistryPagoPaEventDTO<T> {
  private String registryId;
  private String registryOrigin;
  private String registryType;
  private OffsetDateTime dateTime;
  private String traceId;
  private String brokerStationId;
  private String orgFiscalCode;
  private String iuv;
  private String nav;
  private String ccp;
  private String pspId;
  private String pspChannelId;
  private String paymentMethod;
  private RegistryEventCategory eventCategory;
  @NotNull
  private RegistryPagopaEventType eventType;
  @NotNull
  private RegistryEventSubType eventSubType;
  private String requestorId;
  private String grantorId;
  private RegistryOutcome outcome;
  @NotNull
  private T body;

  @JsonProperty("typeKey")
  public String getTypeKey() {
    return eventType.name() + "." + eventSubType.name();
  }
}
