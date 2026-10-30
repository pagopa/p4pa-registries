package it.gov.pagopa.pu.registry.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class NotificationRefusedErrorDTO {
  private String errorCode;
  private String detail;
  private Integer recIndex;
}
