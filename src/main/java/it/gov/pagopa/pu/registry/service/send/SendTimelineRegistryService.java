package it.gov.pagopa.pu.registry.service.send;

import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryExtendedDTO;
import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.mapper.send.RegistryEventSendTimelineDTO2SendTimelineRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.send.SendTimelineRegistry2SendTimelineRegistryDTOMapper;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.repository.SendTimelineRegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendTimelineRegistryService {

  private final SendTimelineRegistryRepository sendTimelineRegistryRepository;
  private final RegistryEventSendTimelineDTO2SendTimelineRegistryMapper registryEventSendTimelineDTO2SendTimelineRegistryMapper;
  private final SendTimelineRegistry2SendTimelineRegistryDTOMapper sendTimelineRegistry2SendTimelineRegistryDTOMapper;

  @Transactional
  public void consumeSendTimelineEvent(RegistryEventSendTimelineDTO event) {
    SendTimelineRegistry registry = registryEventSendTimelineDTO2SendTimelineRegistryMapper.mapToSendTimelineRegistry(event);
    if (registry == null) return;
    sendTimelineRegistryRepository.save(registry);
  }

  public List<SendTimelineRegistryDTO> getSendTimelineRegistries(String notificationRequestId) {
    return sendTimelineRegistryRepository.findByNotificationRequestId(notificationRequestId)
      .stream()
      .map(sendTimelineRegistry2SendTimelineRegistryDTOMapper::mapToSendTimelineRegistryDTO)
      .toList();
  }

  public List<SendTimelineRegistryExtendedDTO> getExtendedSendTimelineRegistries(String notificationRequestId) {
    return sendTimelineRegistryRepository.findByNotificationRequestId(notificationRequestId)
      .stream()
      .map(sendTimelineRegistry2SendTimelineRegistryDTOMapper::mapToSendTimelineRegistryExtendedDTO)
      .toList();
  }

}
