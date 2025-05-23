package it.gov.pagopa.pu.registry.model;

import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Document(collection = "debt_position_registry")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString
public class DebtPositionRegistry implements Serializable {
  @Id
  private String eventId;
  private PaymentEventType eventType;
  private OffsetDateTime eventDateTime;
  private String eventDescription;
  private String operatorExternalUserId;
  private String traceId;
  private Long debtPositionId;
  private Long organizationId;
}
