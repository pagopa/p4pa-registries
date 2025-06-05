package it.gov.pagopa.pu.registry.service;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.service.debtposition.DebtPositionRegistryService;
import it.gov.pagopa.pu.registry.service.installment.InstallmentRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimelineService {

  private final DebtPositionRegistryService debtPositionRegistryService;
  private final InstallmentRegistryService installmentRegistryService;

  public void consumePaymentEvent(PaymentEventDTO<?> event) {
    if (event == null) {
      log.warn("Received null payment event, skipping processing.");
      return;
    }

    if (event.getPayload() == null) {
      log.warn("Received payment event with null payload, skipping processing. eventId={} traceId={} eventType={}",
          event.getEventId(), event.getTraceId(), event.getEventType());
      return;
    }

    log.info("Processing payment event: eventId={} traceId={} eventType={} payloadType={}",
      event.getEventId(), event.getTraceId(), event.getEventType(), event.getPayload().getClass().getSimpleName());
    debtPositionRegistryService.consumePaymentEvent(event);
    installmentRegistryService.consumePaymentEvent(event);
  }

}
