package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.dto.generated.PagoPaRegistryDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventPagoPaDTO2PagoPaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import it.gov.pagopa.pu.registry.repository.PagoPaRegistryRepository;
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
public class PagoPaRegistryService {

  private final PagoPaRegistryRepository pagopaRegistryRepository;
  private final RegistryEventPagoPaDTO2PagoPaRegistryMapper registryEventPagoPaDTO2PagoPaRegistryMapper;
  private final DataCipherService dataCipherService;

  @Transactional
  public void consumePaymentEvent(RegistryEventPagoPaDTO event) {
    List<PagoPaRegistry> registry = registryEventPagoPaDTO2PagoPaRegistryMapper.map(event);
    if (CollectionUtils.isEmpty(registry)) return;
    pagopaRegistryRepository.saveAll(registry);
  }

  public PagoPaRegistryDTO getPagoPaRegistry(String registryId) {
    PagoPaRegistry entity = pagopaRegistryRepository.findById(registryId)
      .orElseThrow(() -> new ResourceNotFoundException("No registry found with id: " + registryId));

    return PagoPaRegistryDTO.builder()
        .registryId(entity.getRegistryId())
        .dateTime(entity.getDateTime())
        .traceId(entity.getTraceId())
        .brokerStationId(entity.getBrokerStationId())
        .orgFiscalCode(entity.getOrgFiscalCode())
        .iuv(entity.getIuv())
        .nav(entity.getNav())
        .ccp(entity.getCcp())
        .pspId(entity.getPspId())
        .pspChannelId(entity.getPspChannelId())
        .paymentMethod(entity.getPaymentMethod())
        .eventCategory(entity.getEventCategory())
        .eventType(entity.getEventType())
        .eventSubType(entity.getEventSubType())
        .requestorId(entity.getRequestorId())
        .grantorId(entity.getGrantorId())
        .outcome(entity.getOutcome())
        .body(dataCipherService.decrypt(entity.getBodyCiphered()))
        .build();
  }

}
