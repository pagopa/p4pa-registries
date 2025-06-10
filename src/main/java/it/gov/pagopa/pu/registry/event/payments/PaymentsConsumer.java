package it.gov.pagopa.pu.registry.event.payments;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.service.TimelineService;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentsConsumer implements Consumer<PaymentEventDTO<?>> {

  private final TimelineService timelineService;

  @Override
  public void accept(PaymentEventDTO paymentEventDTO) {
    if (
      paymentEventDTO.getEventType() != PaymentEventType.IO_NOTIFIED
      && paymentEventDTO.getEventType() != PaymentEventType.SEND_NOTIFICATION_CREATED
      && paymentEventDTO.getEventType() != PaymentEventType.SEND_NOTIFICATION_DATE
      && paymentEventDTO.getEventType() != PaymentEventType.SEND_NOTIFICATION_ERROR
    ) {
      return;
    }
    try {
      timelineService.consumePaymentEvent(paymentEventDTO);
    } catch (Exception exception) {
      log.error("Error processing payment event: eventId={} traceId={} eventType={} payloadType={}",
        paymentEventDTO.getEventId(), paymentEventDTO.getTraceId(),paymentEventDTO.getEventType(),
        paymentEventDTO.getPayload().getClass().getSimpleName(), exception);
    }
  }

}
