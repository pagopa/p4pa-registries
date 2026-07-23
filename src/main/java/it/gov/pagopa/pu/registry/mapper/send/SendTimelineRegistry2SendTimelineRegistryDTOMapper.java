package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryPiiDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

@Service
public class SendTimelineRegistry2SendTimelineRegistryDTOMapper {

  protected final DataCipherService dataCipherService;

  public SendTimelineRegistry2SendTimelineRegistryDTOMapper(DataCipherService dataCipherService) {
    this.dataCipherService = dataCipherService;
  }

  public SendTimelineRegistryDTO mapToSendTimelineRegistryDTO(SendTimelineRegistry sendTimelineRegistry) {
    SendTimelineRegistryDTO sendTimelineRegistryDTO = new SendTimelineRegistryDTO();
    this.mapToDTO(sendTimelineRegistryDTO, sendTimelineRegistry);
    return sendTimelineRegistryDTO;
  }

  public SendTimelineRegistryPiiDTO mapToSendTimelineRegistryPiiDTO(SendTimelineRegistry sendTimelineRegistry) {
    SendTimelineRegistryPiiDTO sendTimelineRegistryPiiDTO = new SendTimelineRegistryPiiDTO();
    this.mapToDTO(sendTimelineRegistryPiiDTO, sendTimelineRegistry);
    sendTimelineRegistryPiiDTO.setBody(dataCipherService.decrypt(sendTimelineRegistry.getBodyCiphered()));
    return sendTimelineRegistryPiiDTO;
  }

  private void mapToDTO(SendTimelineRegistryDTO sendTimelineRegistryDTO, SendTimelineRegistry sendTimelineRegistry) {
    sendTimelineRegistryDTO.setRegistryId(sendTimelineRegistry.getRegistryId());
    sendTimelineRegistryDTO.setDateTime(sendTimelineRegistry.getDateTime());
    sendTimelineRegistryDTO.setTraceId(sendTimelineRegistry.getTraceId());
    sendTimelineRegistryDTO.setEventSubType(sendTimelineRegistry.getEventSubType());
    sendTimelineRegistryDTO.setRequestorId(sendTimelineRegistry.getRequestorId());
    sendTimelineRegistryDTO.setGrantorId(sendTimelineRegistry.getGrantorId());
    sendTimelineRegistryDTO.setOrganizationId(sendTimelineRegistry.getOrganizationId());
    sendTimelineRegistryDTO.setStreamId(sendTimelineRegistry.getStreamId());
    sendTimelineRegistryDTO.setEventId(sendTimelineRegistry.getEventId());
    sendTimelineRegistryDTO.setEventType(sendTimelineRegistry.getEventType());
    sendTimelineRegistryDTO.setNotificationRequestId(sendTimelineRegistry.getNotificationRequestId());
    sendTimelineRegistryDTO.setIun(sendTimelineRegistry.getIun());
    sendTimelineRegistryDTO.setRecipientIndex(sendTimelineRegistry.getRecipientIndex());
    sendTimelineRegistryDTO.setNewStatus(sendTimelineRegistry.getNewStatus());
    sendTimelineRegistryDTO.setEventTimestamp(sendTimelineRegistry.getEventTimestamp());
    sendTimelineRegistryDTO.setLegalFactIds(sendTimelineRegistry.getLegalFactIds());
    sendTimelineRegistryDTO.setOutcome(sendTimelineRegistry.getOutcome());
  }
}
