package it.gov.pagopa.pu.registry.service.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.installment.InstallmentRegistryMapperService;
import it.gov.pagopa.pu.registry.repository.InstallmentRegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentRegistryService {

  private final InstallmentRegistryMapperService installmentRegistryMapperService;
  private final InstallmentRegistryRepository installmentRegistryRepository;

  @Transactional
  public void consumePaymentEvent(PaymentEventDTO<?> event) {
    installmentRegistryRepository.saveAll(installmentRegistryMapperService.map(event));
  }

}
