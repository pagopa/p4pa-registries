package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistryEventSilDTO2SilRegistryMapper {
  private final DataCipherService dataCipherService;
  private final RegistryMappingService mappingService;

  public RegistryEventSilDTO2SilRegistryMapper(DataCipherService dataCipherService, RegistryMappingService mappingService) {
    this.dataCipherService = dataCipherService;
    this.mappingService = mappingService;
  }

  public List<SilRegistry> mapToSilRegistry(RegistryEventSilDTO dto) {
    return mappingService.mapRegistries(dto,
      RegistryEventSilDTO::getIuv,
      RegistryEventSilDTO::getNav,
      (d, iuv, nav) -> SilRegistry.builder()
      .eventId(dto.getRegistryId())
      .dateTime(dto.getDateTime())
      .traceId(dto.getTraceId())
      .brokerFiscalCode(dto.getBrokerFiscalCode())
      .orgFiscalCode(dto.getOrgFiscalCode())
      .iuv(iuv)
      .nav(nav)
      .eventType(dto.getEventType())
      .eventSubType(dto.getEventSubType())
      .requestorId(dto.getRequestorId())
      .grantorId(dto.getGrantorId())
      .outcome(dto.getOutcome())
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build());
  }
}
