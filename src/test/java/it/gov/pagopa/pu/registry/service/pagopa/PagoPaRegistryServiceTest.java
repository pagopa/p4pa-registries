package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import it.gov.pagopa.pu.registry.repository.PagopaRegistryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
    List<PagoPaRegistry> pagopaRegistries = List.of(
      new PagoPaRegistry(),
      new PagoPaRegistry()
    );

    Mockito.when(mapperService.map(Mockito.any(RegistryEventPagoPaDTO.class))).thenReturn(pagopaRegistries);

    // When
    this.service.consumePaymentEvent(Mockito.mock(RegistryEventPagoPaDTO.class));

    // Then
    Mockito.verify(mapperService, Mockito.times(1))
      .map(Mockito.any(RegistryEventPagoPaDTO.class));
    Mockito.verify(repository, Mockito.times(1))
      .saveAll(Mockito.any());
  }
}
