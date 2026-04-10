package it.gov.pagopa.pu.registry.model;

import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document(collection = "send_timeline_event")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class SendTimelineRegistry extends BaseEntity {

  @Id
  @NotNull
  private String registryId;
  @NotNull
  private String registryOrigin;
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

  @NotNull
  private RegistryOutcome outcome;
  private byte[] bodyCiphered;
}
