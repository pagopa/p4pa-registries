package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.dto.generated.SilRegistryDTO;
import it.gov.pagopa.pu.registry.service.sil.SilRegistryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SilRegistryControllerTest {
  @Mock
  private SilRegistryService service;
  private SilRegistryController controller;

  @BeforeEach
  void setUp() {
    this.controller = new SilRegistryController(service);
  }

  @Test
  void shouldReturnPagoPaRegistryDTOWhenRequestIsValid() {
    // Given
    SilRegistryDTO dto = new SilRegistryDTO();

    Mockito.when(service.getSilRegistry("id123"))
      .thenReturn(dto);

    // When
    SilRegistryDTO result = controller.getSilRegistry("id123").getBody();

    // Then
    assertNotNull(result);
    assertEquals(dto, result);
  }

  @Test
  void shouldReturnNotFoundWhenRegistryDoesNotExist() {
    Mockito.when(service.getSilRegistry("id123"))
      .thenThrow(ResourceNotFoundException.class);

    // Then
    assertThrows(ResourceNotFoundException.class, () -> {
      controller.getSilRegistry("id123");
    });
  }

}
