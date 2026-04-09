package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

@Service
public class RegistrySendTemplateDTO2PSentTemplateRegistryMapper {

  private final DataCipherService dataCipherService;

  public RegistrySendTemplateDTO2PSentTemplateRegistryMapper(DataCipherService dataCipherService) {
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
      .outcome(dto.getOutcome())
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build();
  }

}
