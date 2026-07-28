package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

@Service
public class RegistryEventSendTimelineDTO2SendTimelineRegistryMapper {

  private final DataCipherService dataCipherService;

  public RegistryEventSendTimelineDTO2SendTimelineRegistryMapper(DataCipherService dataCipherService) {
    this.dataCipherService = dataCipherService;
  }

  public SendTimelineRegistry mapToSendTimelineRegistry(RegistryEventSendTimelineDTO dto) {
    return SendTimelineRegistry.builder()
      .registryId(dto.getRegistryId())
      .registryOrigin(dto.getRegistryOrigin())
      .dateTime(dto.getDateTime())
      .traceId(dto.getTraceId())
      .eventSubType(dto.getEventSubType())
      .requestorId(dto.getRequestorId())
      .grantorId(dto.getGrantorId())
      .organizationId(dto.getOrganizationId())
      .streamId(dto.getStreamId())
      .eventId(dto.getEventId())
      .eventType(dto.getEventType())
      .notificationRequestId(dto.getNotificationRequestId())
      .iun(dto.getIun())
      .recipientIndex(dto.getRecipientIndex())
      .newStatus(dto.getNewStatus())
      .eventTimestamp(dto.getEventTimestamp())
      .legalFactIds(dto.getLegalFactIds())
      .outcome(dto.getOutcome())
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build();
  }

}
