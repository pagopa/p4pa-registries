package it.gov.pagopa.pu.registry.dto;

import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SendTimelineRegistryDTO {
  @NotNull
  private String registryId;
  @NotNull
  private OffsetDateTime dateTime;
  @NotNull
  private String traceId;
  @NotNull
  private RegistryEventSubType eventSubType;
  @NotNull
  private String requestorId;
  @NotNull
  private String grantorId;
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
  private OffsetDateTime eventTimestamp;
  private List<String> legalFactIds;
  @NotNull
  private RegistryOutcome outcome;
}
