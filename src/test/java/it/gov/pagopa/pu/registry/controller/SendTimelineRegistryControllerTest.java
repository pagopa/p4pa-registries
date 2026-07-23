package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryExtendedDTO;
import it.gov.pagopa.pu.registry.service.send.SendTimelineRegistryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SendTimelineRegistryControllerTest {

  @Mock
  private SendTimelineRegistryService serviceMock;
  @InjectMocks
  private SendTimelineRegistryController controller;

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      serviceMock
    );
  }

  @Test
  void givenValidRequestIdWhenGetSendTimelineRegistriesThenReturnSendTimelineRegistryDTO() {
    // Given
    SendTimelineRegistryDTO dto = new SendTimelineRegistryDTO();
    dto.setRegistryId("registryId");
    dto.setNotificationRequestId("notificationRequestId");
    List<SendTimelineRegistryDTO> expectedRegistrytList = List.of(dto);

    Mockito.when(serviceMock.getSendTimelineRegistries("notificationRequestId"))
      .thenReturn(expectedRegistrytList);

    // When
    ResponseEntity<List<SendTimelineRegistryDTO>> actualResult = controller.getSendTimelineRegistries("notificationRequestId");

    // Then
    assertNotNull(actualResult);
    assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    assertEquals(expectedRegistrytList, actualResult.getBody());
  }

  @Test
  void givenInvalidRequestIdWhenGetSendTimelineRegistriesThenNotFound() {
    Mockito.when(serviceMock.getSendTimelineRegistries("notificationRequestId"))
      .thenThrow(ResourceNotFoundException.class);

    // Then
    assertThrows(
      ResourceNotFoundException.class,
      () -> controller.getSendTimelineRegistries("notificationRequestId")
    );
  }

  @Test
  void givenValidRequestIdWhenGetExtendedSendTimelineRegistriesThenReturnSendTimelineRegistryExtendedDTO() {
    // Given
    SendTimelineRegistryExtendedDTO dto = new SendTimelineRegistryExtendedDTO();
    dto.setRegistryId("registryId");
    dto.setNotificationRequestId("notificationRequestId");
    dto.setBody("bodyString");
    List<SendTimelineRegistryExtendedDTO> expectedRegistrytList = List.of(dto);

    Mockito.when(serviceMock.getExtendedSendTimelineRegistries("notificationRequestId"))
      .thenReturn(expectedRegistrytList);

    // When
    ResponseEntity<List<SendTimelineRegistryExtendedDTO>> result = controller.getExtendedSendTimelineRegistries("notificationRequestId");

    // Then
    assertNotNull(result);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(expectedRegistrytList, result.getBody());
  }

  @Test
  void givenInvalidRequestIdWhenGetExtendedSendTimelineRegistriesThenNotFound() {
    Mockito.when(serviceMock.getExtendedSendTimelineRegistries("notificationRequestId"))
      .thenThrow(ResourceNotFoundException.class);

    // Then
    assertThrows(
      ResourceNotFoundException.class, () ->
      controller.getExtendedSendTimelineRegistries("notificationRequestId")
    );
  }

}
