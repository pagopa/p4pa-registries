package it.gov.pagopa.pu.registry.service.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.debtposition.DebtPositionRegistryMapperService;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.registry.repository.DebtPositionRegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DebtPositionRegistryService {

  private final DebtPositionRegistryMapperService debtPositionRegistryMapperService;
  private final DebtPositionRegistryRepository debtPositionRegistryRepository;

  @Transactional
  public void consumePaymentEvent(PaymentEventDTO<?> event) {
    DebtPositionRegistry registry = debtPositionRegistryMapperService.map(event);
    if (registry == null) return;
    debtPositionRegistryRepository.save(registry);
  }

}
