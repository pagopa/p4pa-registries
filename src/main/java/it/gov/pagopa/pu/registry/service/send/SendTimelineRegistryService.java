package it.gov.pagopa.pu.registry.service.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.mapper.send.RegistrySendTemplateDTO2PSentTemplateRegistryMapper;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.repository.SendTimelineRegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
