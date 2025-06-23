package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventPagoPaDTO2PagoPaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import it.gov.pagopa.pu.registry.repository.PagoPaRegistryRepository;
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
  private PagoPaRegistryRepository repository;
  @Mock
  private RegistryEventPagoPaDTO2PagoPaRegistryMapper mapper;

  private PagoPaRegistryService service;

  @BeforeEach
  void setUp() {
    this.service = new PagoPaRegistryService(repository, mapper);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(mapper, repository);
  }

  @Test
  void whenConsumePaymentEventThenMapperAndRepositoryAreInvoked() {
    // Given
    List<PagoPaRegistry> pagopaRegistries = List.of(
      new PagoPaRegistry(),
      new PagoPaRegistry()
    );

    Mockito.when(mapper.map(Mockito.any(RegistryEventPagoPaDTO.class))).thenReturn(pagopaRegistries);

    // When
    this.service.consumePaymentEvent(Mockito.mock(RegistryEventPagoPaDTO.class));

    // Then
    Mockito.verify(mapper, Mockito.times(1))
      .map(Mockito.any(RegistryEventPagoPaDTO.class));
    Mockito.verify(repository, Mockito.times(1))
      .saveAll(Mockito.any());
  }
}
