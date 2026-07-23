package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryPiiDTO;
import it.gov.pagopa.pu.registry.service.send.SendTimelineRegistryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SendTimelineRegistryControllerTest {

  @Mock
  private SendTimelineRegistryService serviceMock;
  private SendTimelineRegistryController controller;

  @BeforeEach
  void setUp() {
    this.controller = new SendTimelineRegistryController(serviceMock);
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
  void givenValidRequestIdWhenGetExtendedSendTimelineRegistriesThenReturnSendTimelineRegistryPiiDTO() {
    // Given
    SendTimelineRegistryPiiDTO dto = new SendTimelineRegistryPiiDTO();
    dto.setRegistryId("registryId");
    dto.setNotificationRequestId("notificationRequestId");
    dto.setBody("bodyString");
    List<SendTimelineRegistryPiiDTO> expectedRegistrytList = List.of(dto);

    Mockito.when(serviceMock.getExtendedSendTimelineRegistries("notificationRequestId"))
      .thenReturn(expectedRegistrytList);

    // When
    ResponseEntity<List<SendTimelineRegistryPiiDTO>> result = controller.getExtendedSendTimelineRegistries("notificationRequestId");

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
