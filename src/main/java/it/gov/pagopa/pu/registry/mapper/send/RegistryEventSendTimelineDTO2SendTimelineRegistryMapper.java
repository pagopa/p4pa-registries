package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

import java.util.List;

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
      .campaignId(dto.getCampaignId())
      .eventId(dto.getEventId())
      .eventType(dto.getEventType())
      .sendNotificationId(dto.getSendNotificationId())
      .notificationRequestId(dto.getNotificationRequestId())
      .iun(dto.getIun())
      .recipientIndex(dto.getRecipientIndex())
      .newStatus(dto.getNewStatus())
      .eventTimestamp(dto.getEventTimestamp())
      .legalFactIds(dto.getLegalFactIds())
      .errors(mapErrorList(dto.getErrors()))
      .outcome(dto.getOutcome())
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build();
  }

  private List<SendTimelineRegistry.NotificationRefusedError> mapErrorList(List<RegistryEventSendTimelineDTO.NotificationRefusedErrorDTO> errorDTOList) {
    return errorDTOList.stream()
      .map(this::mapErrorDTO)
      .toList();
  }

  private SendTimelineRegistry.NotificationRefusedError mapErrorDTO(RegistryEventSendTimelineDTO.NotificationRefusedErrorDTO errorDTO) {
    return new SendTimelineRegistry.NotificationRefusedError(
      errorDTO.errCode(),
      errorDTO.detail(),
      errorDTO.recIndex()
    );
  }

}
