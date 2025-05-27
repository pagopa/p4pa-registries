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
    log.info("Processing payment event: {}", event);
    debtPositionRegistryService.consumePaymentEvent(event);
    installmentRegistryService.consumePaymentEvent(event);
  }

}
