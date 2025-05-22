package it.gov.pagopa.pu.registry.event.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class DebtPositionSendNotificationDTO {
  private String sendNotificationId;
  private Long debtPositionId;
  private Long organizationId;
  private String iun;
  private OffsetDateTime notificationDate;
  private String status;
  private List<String> noticeCodes;
}
