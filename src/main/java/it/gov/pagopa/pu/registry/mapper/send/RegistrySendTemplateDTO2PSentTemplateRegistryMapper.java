package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineFullRegistryDTO;
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
      .eventTimestamp(dto.getEventTimestamp())
      .legalFactIds(dto.getLegalFactIds())
      .outcome(dto.getOutcome())
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build();
  }

  public SendTimelineRegistryDTO mapToSendTimelineDTO(SendTimelineRegistry sendRegistry) {
    SendTimelineRegistryDTO sendTimelineRegistryDTO = new SendTimelineRegistryDTO();
    this.mapToSendTimelineDTO(sendTimelineRegistryDTO, sendRegistry);
    return sendTimelineRegistryDTO;
  }

  public SendTimelineFullRegistryDTO mapToSendTimelineFullDTO(SendTimelineRegistry sendRegistry) {
    SendTimelineFullRegistryDTO registrySendTimelinePiiDTO = new SendTimelineFullRegistryDTO();
    this.mapToSendTimelineDTO(registrySendTimelinePiiDTO, sendRegistry);
    registrySendTimelinePiiDTO.setBody(dataCipherService.decrypt(sendRegistry.getBodyCiphered()));
    return registrySendTimelinePiiDTO;
  }

  private void mapToSendTimelineDTO(SendTimelineRegistryDTO sendTimelineDTO, SendTimelineRegistry sendRegistry) {
    sendTimelineDTO.setRegistryId(sendRegistry.getRegistryId());
    sendTimelineDTO.setDateTime(sendRegistry.getDateTime());
    sendTimelineDTO.setTraceId(sendRegistry.getTraceId());
    sendTimelineDTO.setEventSubType(sendRegistry.getEventSubType());
    sendTimelineDTO.setRequestorId(sendRegistry.getRequestorId());
    sendTimelineDTO.setGrantorId(sendRegistry.getGrantorId());
    sendTimelineDTO.setOrganizationId(sendRegistry.getOrganizationId());
    sendTimelineDTO.setStreamId(sendRegistry.getStreamId());
    sendTimelineDTO.setEventId(sendRegistry.getEventId());
    sendTimelineDTO.setEventType(sendRegistry.getEventType());
    sendTimelineDTO.setNotificationRequestId(sendRegistry.getNotificationRequestId());
    sendTimelineDTO.setIun(sendRegistry.getIun());
    sendTimelineDTO.setRecipientIndex(sendRegistry.getRecipientIndex());
    sendTimelineDTO.setNewStatus(sendRegistry.getNewStatus());
    sendTimelineDTO.setEventTimestamp(sendRegistry.getEventTimestamp());
    sendTimelineDTO.setLegalFactIds(sendRegistry.getLegalFactIds());
    sendTimelineDTO.setOutcome(sendRegistry.getOutcome());
  }
}
