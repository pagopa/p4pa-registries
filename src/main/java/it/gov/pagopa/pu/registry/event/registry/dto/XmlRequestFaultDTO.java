package it.gov.pagopa.pu.registry.event.registry.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class XmlRequestFaultDTO {
  private String faultCode;
  private String faultString;
  private String id;
  private String description;
  private Integer serial;
  private String originalFaultCode;
  private String originalFaultString;
  private String originalDescription;
}
