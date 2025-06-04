package it.gov.pagopa.pu.registry.event.payments;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.service.TimelineService;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PaymentsConsumerTest {

  @Mock
  private TimelineService timelineService;

  private PaymentsConsumer consumer;

  @BeforeEach
  void setUp() {
    consumer = new PaymentsConsumer(timelineService);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(timelineService);
  }

  @Test
  void whenAcceptValidPaymentEventThenTimelineServiceIsCalled() {
    // Given
    PaymentEventDTO<Object> paymentEventDTO = new PaymentEventDTO<>();
    paymentEventDTO.setEventId(UUID.randomUUID().toString());
    paymentEventDTO.setTraceId(UUID.randomUUID().toString());
    paymentEventDTO.setEventType(PaymentEventType.DP_CREATED);
    paymentEventDTO.setPayload(new Object());

    // When
    consumer.accept(paymentEventDTO);

    // Then
    Mockito.verify(timelineService).consumePaymentEvent(Mockito.same(paymentEventDTO));
  }


}
