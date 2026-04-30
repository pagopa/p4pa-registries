package it.gov.pagopa.pu.registry.service.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.mapper.send.RegistrySendTemplateDTO2PSentTemplateRegistryMapper;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.repository.SendTimelineRegistryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SendTimelineRegistryServiceTest {

  @Mock
  private SendTimelineRegistryRepository sendTimelineRegistryRepositoryMock;
  @Mock
  private RegistrySendTemplateDTO2PSentTemplateRegistryMapper registryEventSendTimelineEventDTO2SendTimelineRegistryMapperMock;

  @InjectMocks
  private SendTimelineRegistryService sendTimelineRegistryService;

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      sendTimelineRegistryRepositoryMock,
      registryEventSendTimelineEventDTO2SendTimelineRegistryMapperMock
    );
  }


  @Test
  void givenMapOkWhenConsumeSendTimelineEventThenSave() {
    //GIVEN
    RegistryEventSendTimelineDTO event = new RegistryEventSendTimelineDTO();
    SendTimelineRegistry entity = new SendTimelineRegistry();

    Mockito.when(registryEventSendTimelineEventDTO2SendTimelineRegistryMapperMock.mapToSendTimelineRegistry(event))
      .thenReturn(entity);

    //WHEN
    sendTimelineRegistryService.consumeSendTimelineEvent(event);

    //THEN
    Mockito.verify(registryEventSendTimelineEventDTO2SendTimelineRegistryMapperMock)
      .mapToSendTimelineRegistry(event);
    Mockito.verify(sendTimelineRegistryRepositoryMock)
      .save(entity);
  }

  @Test
  void givenMapKOWhenConsumeSendTimelineEventThenSkip() {
    //GIVEN
    RegistryEventSendTimelineDTO event = new RegistryEventSendTimelineDTO();

    Mockito.when(registryEventSendTimelineEventDTO2SendTimelineRegistryMapperMock.mapToSendTimelineRegistry(event))
      .thenReturn(null);

    //WHEN
    sendTimelineRegistryService.consumeSendTimelineEvent(event);

    //THEN
    Mockito.verify(registryEventSendTimelineEventDTO2SendTimelineRegistryMapperMock)
      .mapToSendTimelineRegistry(event);
    Mockito.verify(sendTimelineRegistryRepositoryMock, Mockito.times(0))
      .save(Mockito.any(SendTimelineRegistry.class));
  }
}
