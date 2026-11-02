package it.gov.pagopa.pu.registry.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class NotificationRefusedError {
  private String code;
  private String detail;
  private Integer recIndex;
}
