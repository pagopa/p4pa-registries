package it.gov.pagopa.pu.registry.service.send;

import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineFullRegistryDTO;
import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.mapper.send.RegistrySendTemplateDTO2PSentTemplateRegistryMapper;
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
  private final RegistrySendTemplateDTO2PSentTemplateRegistryMapper registryEventSendTimelineEventDTO2SendTimelineRegistryMapper;

  @Transactional
  public void consumeSendTimelineEvent(RegistryEventSendTimelineDTO event) {
    SendTimelineRegistry registry = registryEventSendTimelineEventDTO2SendTimelineRegistryMapper.mapToSendTimelineRegistry(event);
    if (registry == null) return;
    sendTimelineRegistryRepository.save(registry);
  }

  public List<SendTimelineRegistryDTO> getSendTimelineRegistries(String notificationRequestId) {
    return sendTimelineRegistryRepository.findByNotificationRequestId(notificationRequestId)
      .stream()
      .map(registryEventSendTimelineEventDTO2SendTimelineRegistryMapper::mapToSendTimelineDTO)
      .toList();
  }

  public List<SendTimelineFullRegistryDTO> getFullSendTimelineRegistries(String notificationRequestId) {
    return sendTimelineRegistryRepository.findByNotificationRequestId(notificationRequestId)
      .stream()
      .map(registryEventSendTimelineEventDTO2SendTimelineRegistryMapper::mapToSendTimelineFullDTO)
      .toList();
  }

}
