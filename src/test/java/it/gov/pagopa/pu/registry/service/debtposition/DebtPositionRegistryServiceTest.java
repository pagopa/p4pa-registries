package it.gov.pagopa.pu.registry.service.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.debtposition.DebtPositionRegistryMapperService;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.registry.repository.DebtPositionRegistryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DebtPositionRegistryServiceTest {

  @Mock
  private DebtPositionRegistryRepository repository;
  @Mock
  private DebtPositionRegistryMapperService mapperService;

  private DebtPositionRegistryService service;

  @BeforeEach
  void setUp() {
    this.service = new DebtPositionRegistryService(mapperService, repository);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(mapperService, repository);
  }

  @Test
  void whenConsumePaymentEventThenMapperAndRepositoryAreInvoked() {
    // Given
    Mockito.when(mapperService.map(Mockito.any(PaymentEventDTO.class))).thenReturn(new DebtPositionRegistry());

    // When
    this.service.consumePaymentEvent(Mockito.mock(PaymentEventDTO.class));

    // Then
    Mockito.verify(mapperService, Mockito.times(1))
        .map(Mockito.any(PaymentEventDTO.class));
    Mockito.verify(repository, Mockito.times(1))
        .save(Mockito.any());
  }

}
