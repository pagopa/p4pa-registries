package it.gov.pagopa.pu.registry.service.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.installment.InstallmentRegistryMapperService;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.repository.InstallmentRegistryRepository;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
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
  void whenConsumePaymentEventThenMapperAndRepositoryAreInvoked() {
    // Given
    PaymentEventDTO<DebtPositionEventDTO> dto = new PaymentEventDTO<>();
    dto.setEventType(PaymentEventType.DP_CREATED);
    dto.setPayload(new DebtPositionEventDTO());
    List<InstallmentRegistry> expectedResults = List.of(new InstallmentRegistry());

    Mockito.when(mapperService.map(Mockito.same(dto)))
      .thenReturn(expectedResults);

    // When
    this.service.consumePaymentEvent(dto);

    // Then
    Mockito.verify(repository)
        .saveAll(expectedResults);
  }

}
