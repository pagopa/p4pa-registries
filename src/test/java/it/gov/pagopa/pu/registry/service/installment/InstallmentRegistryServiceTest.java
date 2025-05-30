package it.gov.pagopa.pu.registry.service.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.installment.InstallmentRegistryMapperService;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.repository.InstallmentRegistryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class InstallmentRegistryServiceTest {

  @Mock
  private InstallmentRegistryRepository repository;
  @Mock
  private InstallmentRegistryMapperService mapperService;

  private InstallmentRegistryService service;

  @BeforeEach
  void setUp() {
    this.service = new InstallmentRegistryService(mapperService, repository);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(repository, mapperService);
  }

  @Test
  void consumePaymentEvent_whenRequestIsValid() {
    Mockito.when(mapperService.map(Mockito.any(PaymentEventDTO.class)))
      .thenReturn(List.of(new InstallmentRegistry(), new InstallmentRegistry()));
    this.service.consumePaymentEvent(Mockito.mock(PaymentEventDTO.class));
    Mockito.verify(mapperService, Mockito.times(1))
        .map(Mockito.any(PaymentEventDTO.class));
    Mockito.verify(repository, Mockito.times(1))
        .saveAll(Mockito.any());
  }

}
