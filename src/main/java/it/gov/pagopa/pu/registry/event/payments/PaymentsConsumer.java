package it.gov.pagopa.pu.registry.event.payments;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Slf4j
@Service
public class PaymentsConsumer implements Consumer<PaymentEventDTO<?>> {

  @Override
  public void accept(PaymentEventDTO paymentEventDTO) {
    log.info("Read eventId {} of type eventType {}", paymentEventDTO.getEventId(), paymentEventDTO.getEventType());
  }

}
