package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.dto.generated.PagoPaRegistryDTO;
import it.gov.pagopa.pu.registry.service.pagopa.PagoPaRegistryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagoPaRegistryControllerTest {
  @Mock
  private PagoPaRegistryService serviceMock;
  private PagoPaRegistryController controller;

  @BeforeEach
  void setUp() {
    this.controller = new PagoPaRegistryController(serviceMock);
  }

  @Test
  void shouldReturnPagoPaRegistryDTOWhenRequestIsValid() {
    // Given
    PagoPaRegistryDTO dto = new PagoPaRegistryDTO();

    Mockito.when(serviceMock.getPagoPaRegistry("id123"))
      .thenReturn(dto);

    // When
    PagoPaRegistryDTO result = controller.getPagoPaRegistry("id123").getBody();

    // Then
    assertNotNull(result);
    assertEquals(dto, result);
  }

  @Test
  void shouldReturnNotFoundWhenRegistryDoesNotExist() {
    Mockito.when(serviceMock.getPagoPaRegistry("id123"))
      .thenThrow(ResourceNotFoundException.class);

    // Then
    assertThrows(ResourceNotFoundException.class, () -> {
      controller.getPagoPaRegistry("id123");
    });
  }

}
