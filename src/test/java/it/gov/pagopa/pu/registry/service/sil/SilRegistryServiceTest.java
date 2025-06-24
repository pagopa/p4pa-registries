package it.gov.pagopa.pu.registry.service.sil;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventSilDTO2SilRegistryMapper;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.repository.SilRegistryRepository;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SilRegistryServiceTest {
  @Mock
  private SilRegistryRepository repository;
  @Mock
  private RegistryEventSilDTO2SilRegistryMapper mapperService;
  @Mock
  private DataCipherService dataCipherService;

  private SilRegistryService service;

  @BeforeEach
  void setUp() {
    this.service =new SilRegistryService(repository, mapperService, dataCipherService);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(mapperService, repository, dataCipherService);
  }

  @Test
  void whenConsumePaymentEventThenMapperAndRepositoryAreInvoked() {
    // Given
    List<SilRegistry> registries = List.of(
      new SilRegistry(),
      new SilRegistry()
    );

    Mockito.when(mapperService.map(Mockito.any(RegistryEventSilDTO.class))).thenReturn(registries);

    // When
    this.service.consumePaymentEvent(Mockito.mock(RegistryEventSilDTO.class));

    // Then
    Mockito.verify(mapperService, Mockito.times(1))
      .map(Mockito.any(RegistryEventSilDTO.class));
    Mockito.verify(repository, Mockito.times(1))
      .saveAll(Mockito.any());
  }
}
