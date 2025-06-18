package it.gov.pagopa.pu.registry.dto;

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
  private String body;
}
