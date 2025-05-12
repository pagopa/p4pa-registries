package it.gov.pagopa.pu.registry.model;

import it.gov.pagopa.pu.registry.enums.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class BaseRegistry {
  @Id
  private String eventId;
  private EventType eventType;
  private LocalDateTime eventDateTime;
  private String eventDescription;
  private String operatorExternalUserId;
  private String traceId;
}
