package it.gov.pagopa.pu.registry.service;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.service.debtposition.DebtPositionRegistryService;
import it.gov.pagopa.pu.registry.service.installment.InstallmentRegistryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimelineServiceTest {

  @Mock
  private DebtPositionRegistryService debtPositionRegistryService;
  @Mock
  private InstallmentRegistryService installmentRegistryService;

  private TimelineService service;

  @BeforeEach
  void setUp() {
    this.service = new TimelineService(debtPositionRegistryService, installmentRegistryService);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(debtPositionRegistryService, installmentRegistryService);
  }

  @Test
  void consumePaymentEvent_whenEventIsValid_shouldCallServices() {
    PaymentEventDTO<?> event = Mockito.mock(PaymentEventDTO.class);

    service.consumePaymentEvent(event);

    Mockito.verify(debtPositionRegistryService, Mockito.times(1)).consumePaymentEvent(event);
    Mockito.verify(installmentRegistryService, Mockito.times(1)).consumePaymentEvent(event);
  }

  @Test
  void consumePaymentEvent_whenEventIsNull_shouldNotCallServices() {
    service.consumePaymentEvent(null);
    Mockito.verify(debtPositionRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
    Mockito.verify(installmentRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
  }

}
