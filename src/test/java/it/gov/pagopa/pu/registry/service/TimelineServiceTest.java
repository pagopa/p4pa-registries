package it.gov.pagopa.pu.registry.service;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.service.debtposition.DebtPositionRegistryService;
import it.gov.pagopa.pu.registry.service.installment.InstallmentRegistryService;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
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
  void whenConsumePaymentEventValidThenRegistryServicesAreInvoked() {
    //Given
    PaymentEventDTO<Object> event = new PaymentEventDTO<>();
    event.setEventId("test-event-id");
    event.setTraceId("test-trace-id");
    event.setEventType(PaymentEventType.DP_CREATED);
    event.setPayload(new Object());

    // When
    service.consumePaymentEvent(event);

    // Then
    Mockito.verify(debtPositionRegistryService, Mockito.times(1)).consumePaymentEvent(event);
    Mockito.verify(installmentRegistryService, Mockito.times(1)).consumePaymentEvent(event);
  }

  @Test
  void whenConsumePaymentEventInvalidThenRegistryServicesAreNotInvoked() {
    // When
    service.consumePaymentEvent(null);

    // Then
    Mockito.verify(debtPositionRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
    Mockito.verify(installmentRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
  }

}
