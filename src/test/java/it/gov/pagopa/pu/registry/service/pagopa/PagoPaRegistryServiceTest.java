package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.event.registry.dto.RegistryPagoPaEventDTO;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import it.gov.pagopa.pu.registry.repository.PagopaRegistryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagoPaRegistryServiceTest {
  @Mock
  private PagopaRegistryRepository repository;
  @Mock
  private PagoPaRegistryMapperService mapperService;

  private PagoPaRegistryService service;

  @BeforeEach
  void setUp() {
    this.service = new PagoPaRegistryService(repository, mapperService);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(mapperService, repository);
  }

  @Test
  void whenConsumePaymentEventThenMapperAndRepositoryAreInvoked() {
    // Given
    Mockito.when(mapperService.map(Mockito.any(RegistryPagoPaEventDTO.class))).thenReturn(new PagopaRegistry());

    // When
    this.service.consumePaymentEvent(Mockito.mock(RegistryPagoPaEventDTO.class));

    // Then
    Mockito.verify(mapperService, Mockito.times(1))
      .map(Mockito.any(RegistryPagoPaEventDTO.class));
    Mockito.verify(repository, Mockito.times(1))
      .save(Mockito.any());
  }
}
