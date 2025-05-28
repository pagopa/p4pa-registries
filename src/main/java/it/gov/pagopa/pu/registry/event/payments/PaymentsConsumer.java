package it.gov.pagopa.pu.registry.event.payments;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.service.TimelineService;
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
    log.info("Read eventId {} of type eventType {}", paymentEventDTO.getEventId(), paymentEventDTO.getEventType());
    try {
      timelineService.consumePaymentEvent(paymentEventDTO);
    } catch (Exception exception) {
      log.error("Error processing payment event: {}", paymentEventDTO, exception);
    }
  }

}
