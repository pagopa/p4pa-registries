package it.gov.pagopa.pu.registry.service.sil;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.repository.SilRegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SilRegistryService {

  private final SilRegistryRepository silRegistryRepository;
  private final SilRegistryMapperService silRegistryMapperService;

  @Transactional
  public void consumePaymentEvent(RegistryEventSilDTO event) {
    List<SilRegistry> registry = silRegistryMapperService.map(event);
    if (registry == null || registry.isEmpty()) return;
    silRegistryRepository.saveAll(registry);
  }

}
