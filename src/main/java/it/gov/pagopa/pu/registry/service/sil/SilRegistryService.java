package it.gov.pagopa.pu.registry.service.sil;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.dto.generated.SilRegistryDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventSilDTO2SilRegistryMapper;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.repository.SilRegistryRepository;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SilRegistryService {

  private final SilRegistryRepository silRegistryRepository;
  private final RegistryEventSilDTO2SilRegistryMapper registryEventSilDTO2SilRegistryMapper;
  private final DataCipherService dataCipherService;

  @Transactional
  public void consumePaymentEvent(RegistryEventSilDTO event) {
    List<SilRegistry> registry = registryEventSilDTO2SilRegistryMapper.map(event);
    if (CollectionUtils.isEmpty(registry)) return;
    silRegistryRepository.saveAll(registry);
  }

  public SilRegistryDTO getSilRegistry(String registryId) {
    SilRegistry entity = silRegistryRepository.findById(registryId)
        .orElseThrow(() -> new ResourceNotFoundException("[SIL_REGISTRY_NOT_FOUND] No registry found with id: " + registryId));

    return SilRegistryDTO.builder()
        .registryId(entity.getRegistryId())
        .dateTime(entity.getDateTime())
        .traceId(entity.getTraceId())
        .brokerFiscalCode(entity.getBrokerFiscalCode())
        .orgFiscalCode(entity.getOrgFiscalCode())
        .iuv(entity.getIuv())
        .nav(entity.getNav())
        .eventType(entity.getEventType())
        .eventSubType(entity.getEventSubType())
        .requestorId(entity.getRequestorId())
        .grantorId(entity.getGrantorId())
        .outcome(entity.getOutcome())
        .body(dataCipherService.decrypt(entity.getBodyCiphered()))
        .build();
  }

}
