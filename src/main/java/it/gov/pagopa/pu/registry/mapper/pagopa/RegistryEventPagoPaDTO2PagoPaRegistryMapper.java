package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistryEventPagoPaDTO2PagoPaRegistryMapper {
  private final DataCipherService dataCipherService;
  private final RegistryMappingService mappingService;

  public RegistryEventPagoPaDTO2PagoPaRegistryMapper(DataCipherService dataCipherService, RegistryMappingService mappingService) {
    this.dataCipherService = dataCipherService;
    this.mappingService = mappingService;
  }

  public List<PagopaRegistry> mapToPagoPaRegistry(RegistryEventPagoPaDTO dto) {
    return mappingService.mapRegistries(dto,
      RegistryEventPagoPaDTO::getIuv,
      RegistryEventPagoPaDTO::getNav,
      (d, iuv, nav) -> PagopaRegistry.builder()
        .eventId(d.getRegistryId())
        .dateTime(d.getDateTime())
        .traceId(d.getTraceId())
        .brokerStationId(d.getBrokerStationId())
        .orgFiscalCode(d.getOrgFiscalCode())
        .iuv(iuv)
        .nav(nav)
        .ccp(d.getCcp())
        .pspId(d.getPspId())
        .pspChannelId(d.getPspChannelId())
        .paymentMethod(d.getPaymentMethod())
        .eventCategory(d.getEventCategory())
        .eventType(d.getEventType())
        .eventSubType(d.getEventSubType())
        .requestorId(d.getRequestorId())
        .grantorId(d.getGrantorId())
        .outcome(d.getOutcome())
        .bodyCiphered(dataCipherService.encrypt(d.getBody()))
        .build());
  }
}
