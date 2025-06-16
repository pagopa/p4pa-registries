package it.gov.pagopa.pu.registry.model;

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
  private String eventCategory;
  private String eventType;
  private String eventSubType;
  private String requestorId;
  private String grantorId;
  private String outcome;
  private byte[] body;
}
