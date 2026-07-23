package it.gov.pagopa.pu.registry.service.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryExtendedDTO;
import it.gov.pagopa.pu.registry.mapper.send.RegistryEventSendTimelineDTO2SendTimelineRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.send.SendTimelineRegistry2SendTimelineRegistryDTOMapper;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.repository.SendTimelineRegistryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendTimelineRegistryServiceTest {

  @Mock
  private SendTimelineRegistryRepository sendTimelineRegistryRepositoryMock;
  @Mock
  private RegistryEventSendTimelineDTO2SendTimelineRegistryMapper registryEventSendTimelineDTO2SendTimelineRegistryMapperMock;
  @Mock
  private SendTimelineRegistry2SendTimelineRegistryDTOMapper sendTimelineRegistry2SendTimelineRegistryDTOMapperMock;

  @InjectMocks
  private SendTimelineRegistryService sendTimelineRegistryService;

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      sendTimelineRegistryRepositoryMock,
      registryEventSendTimelineDTO2SendTimelineRegistryMapperMock,
      sendTimelineRegistry2SendTimelineRegistryDTOMapperMock
    );
  }

  @Test
  void givenMapOkWhenConsumeSendTimelineEventThenSave() {
    //GIVEN
    RegistryEventSendTimelineDTO event = new RegistryEventSendTimelineDTO();
    SendTimelineRegistry entity = new SendTimelineRegistry();

    when(registryEventSendTimelineDTO2SendTimelineRegistryMapperMock.mapToSendTimelineRegistry(event))
      .thenReturn(entity);

    //WHEN
    sendTimelineRegistryService.consumeSendTimelineEvent(event);

    //THEN
    verify(registryEventSendTimelineDTO2SendTimelineRegistryMapperMock)
      .mapToSendTimelineRegistry(event);
    verify(sendTimelineRegistryRepositoryMock)
      .save(entity);
  }

  @Test
  void givenMapKOWhenConsumeSendTimelineEventThenSkip() {
    //GIVEN
    RegistryEventSendTimelineDTO event = new RegistryEventSendTimelineDTO();

    when(registryEventSendTimelineDTO2SendTimelineRegistryMapperMock.mapToSendTimelineRegistry(event))
      .thenReturn(null);

    //WHEN
    sendTimelineRegistryService.consumeSendTimelineEvent(event);

    //THEN
    verify(registryEventSendTimelineDTO2SendTimelineRegistryMapperMock)
      .mapToSendTimelineRegistry(event);
    verify(sendTimelineRegistryRepositoryMock, Mockito.times(0))
      .save(Mockito.any(SendTimelineRegistry.class));
  }

  @Test
  void givenValidRequestNotificationIdWhenGetSendTimelineRegistriesThenReturnRegistryList() {
    //GIVEN
    String notificationRequestId = "notificationRequestId";
    SendTimelineRegistry registry = new SendTimelineRegistry();
    SendTimelineRegistryDTO expectedDTO = new SendTimelineRegistryDTO();
    expectedDTO.setRegistryId("registryId");

    when(sendTimelineRegistryRepositoryMock.findByNotificationRequestId(notificationRequestId))
        .thenReturn(List.of(registry));
    when(sendTimelineRegistry2SendTimelineRegistryDTOMapperMock.mapToSendTimelineRegistryDTO(registry))
      .thenReturn(expectedDTO);

    //WHEN
    List<SendTimelineRegistryDTO> actualDTOList = sendTimelineRegistryService.getSendTimelineRegistries(notificationRequestId);

    //THEN
    Assertions.assertNotNull(actualDTOList);
    Assertions.assertEquals(1, actualDTOList.size());
    Assertions.assertEquals(expectedDTO, actualDTOList.getFirst());
  }

  @Test
  void givenInvalidRequestNotificationIdWhenGetSendTimelineRegistriesThenReturnEmptyList() {
    //GIVEN
    String notificationRequestId = "notificationRequestId";

    when(sendTimelineRegistryRepositoryMock.findByNotificationRequestId(notificationRequestId))
      .thenReturn(Collections.emptyList());

    //WHEN
    List<SendTimelineRegistryDTO> actualDTOList = sendTimelineRegistryService.getSendTimelineRegistries(notificationRequestId);

    //THEN
    Assertions.assertNotNull(actualDTOList);
    Assertions.assertEquals(0, actualDTOList.size());
  }

  @Test
  void givenValidRequestNotificationIdWhenGetExtendedSendTimelineRegistriesThenReturnRegistryList() {
    //GIVEN
    String notificationRequestId = "notificationRequestId";
    SendTimelineRegistry registry = new SendTimelineRegistry();
    SendTimelineRegistryExtendedDTO expectedDTO = new SendTimelineRegistryExtendedDTO();
    expectedDTO.setRegistryId("registryId");
    expectedDTO.setBody("decryptedBody");

    when(sendTimelineRegistryRepositoryMock.findByNotificationRequestId(notificationRequestId))
      .thenReturn(List.of(registry));
    when(sendTimelineRegistry2SendTimelineRegistryDTOMapperMock.mapToSendTimelineRegistryExtendedDTO(registry))
      .thenReturn(expectedDTO);

    //WHEN
    List<SendTimelineRegistryExtendedDTO> actualDTOList = sendTimelineRegistryService.getExtendedSendTimelineRegistries(notificationRequestId);

    //THEN
    Assertions.assertNotNull(actualDTOList);
    Assertions.assertEquals(1, actualDTOList.size());
    Assertions.assertEquals(expectedDTO, actualDTOList.getFirst());
  }

  @Test
  void givenInvalidRequestNotificationIdWhenGetExtendedSendTimelineRegistriesThenReturnEmptyList() {
    //GIVEN
    String notificationRequestId = "notificationRequestId";

    when(sendTimelineRegistryRepositoryMock.findByNotificationRequestId(notificationRequestId))
      .thenReturn(Collections.emptyList());

    //WHEN
    List<SendTimelineRegistryExtendedDTO> actualDTOList = sendTimelineRegistryService.getExtendedSendTimelineRegistries(notificationRequestId);

    //THEN
    Assertions.assertNotNull(actualDTOList);
    Assertions.assertEquals(0, actualDTOList.size());
  }
}
