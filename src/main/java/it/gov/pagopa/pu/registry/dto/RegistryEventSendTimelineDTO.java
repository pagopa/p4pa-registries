package it.gov.pagopa.pu.registry.dto;

import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryEventSendTimelineDTO extends RegistryInterfaceEventDTO {
  @NotNull
  private Long organizationId;
  @NotNull
  private String streamId;
  @NotNull
  private String eventId;
  @NotNull
  private String eventType;
  @NotNull
  private String notificationRequestId;
  private String iun;
  private Integer recipientIndex;
  private String newStatus;
  @NotNull
  private RegistryOutcome outcome;
  private String body;
}
