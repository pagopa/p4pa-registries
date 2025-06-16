package it.gov.pagopa.pu.registry.model;

import it.gov.pagopa.pu.registry.enums.EventCategory;
import it.gov.pagopa.pu.registry.enums.EventSubType;
import it.gov.pagopa.pu.registry.enums.Outcome;
import it.gov.pagopa.pu.registry.enums.RegistryType;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
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
public class PagopaRegistry implements Serializable {

  @Id
  private String registryId;
  private PaymentEventType eventType;
  private OffsetDateTime eventDateTime;
  private String eventDescription;
  private String operatorExternalUserId;
  private String traceId;

  private RegistryType registryType;
  private String brokerStationId;
  private String orgFiscalCode;
  private String iuv;
  private String nav;
  private String ccp;
  private String pspId;
  private String pspChannelId;
  private String paymentMethod;
  private EventCategory eventCategory;
  private EventSubType eventSubType;
  private String requestorId;
  private String grantorId;
  private Outcome outcome;
  private String body;
}
