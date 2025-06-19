package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.event.registry.dto.RegistryPagoPaEventDTO;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import it.gov.pagopa.pu.registry.repository.PagopaRegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PagoPaRegistryService {

  private final PagopaRegistryRepository pagopaRegistryRepository;
  private final PagoPaRegistryMapperService pagoPaRegistryMapperService;

  @Transactional
  public void consumePaymentEvent(RegistryPagoPaEventDTO<?> event) {
    PagopaRegistry registry = pagoPaRegistryMapperService.map(event);
    if (registry == null) return;
    pagopaRegistryRepository.save(registry);
  }

}
