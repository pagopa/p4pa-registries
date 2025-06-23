package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventPagoPaDTO2PagoPaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import it.gov.pagopa.pu.registry.repository.PagoPaRegistryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PagoPaRegistryService {

  private final PagoPaRegistryRepository pagopaRegistryRepository;
  private final RegistryEventPagoPaDTO2PagoPaRegistryMapper registryEventPagoPaDTO2PagoPaRegistryMapper;

  @Transactional
  public void consumePaymentEvent(RegistryEventPagoPaDTO event) {
    List<PagoPaRegistry> registry = registryEventPagoPaDTO2PagoPaRegistryMapper.map(event);
    if (CollectionUtils.isEmpty(registry)) return;
    pagopaRegistryRepository.saveAll(registry);
  }

}
