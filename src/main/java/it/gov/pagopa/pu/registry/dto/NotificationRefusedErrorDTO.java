package it.gov.pagopa.pu.registry.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class NotificationRefusedErrorDTO {
  private String errCode;
  private String detail;
  private Integer recIndex;
}
