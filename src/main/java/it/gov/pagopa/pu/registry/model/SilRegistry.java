package it.gov.pagopa.pu.registry.model;

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
  private String registryId;
  private String registryOrigin;
  private String registryType;
  private OffsetDateTime dateTime;
  private String traceId;
  private String brokerFiscalCode;
  private String orgFiscalCode;
  private String iuv;
  private String nav;
  private String eventType;
  private String eventSubType;
  private String requestorId;
  private String grantorId;
  private String outcome;
  private byte[] body;
}
