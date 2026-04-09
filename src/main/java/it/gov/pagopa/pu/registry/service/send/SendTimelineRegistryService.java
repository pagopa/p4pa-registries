package it.gov.pagopa.pu.registry.service.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
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
public class SendTimelineRegistryService {

  private final SendTimelineRegistryRepository pagopaRegistryRepository;
  private final RegistryEventSendTimelineEventDTOSendTimelineRegistryMapper registryEventSendTimelineEventDTOSendTimelineRegistryMapper;
  private final DataCipherService dataCipherService;

  @Transactional
  public void consumeSendTimelineEvent(RegistryEventSendTimelineDTO event) {
    List<PagoPaRegistry> registry = registryEventSendTimelineEventDTOSendTimelineRegistryMapper.map(event);
    if (CollectionUtils.isEmpty(registry)) return;
    pagopaRegistryRepository.saveAll(registry);
  }

  public PagoPaRegistryDTO getPagoPaRegistry(String registryId) {
    PagoPaRegistry entity = pagopaRegistryRepository.findById(registryId)
      .orElseThrow(() -> new ResourceNotFoundException("[PAGOPA_REGISTRY_NOT_FOUND] No registry found with id: " + registryId));

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
