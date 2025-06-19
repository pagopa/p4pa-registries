package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RegistryEventSilDTO2SilRegistryMapper extends BaseRegistryMapper<RegistryEventSilDTO, SilRegistry> {

  private final DataCipherService dataCipherService;

  public RegistryEventSilDTO2SilRegistryMapper(DataCipherService dataCipherService) {
    this.dataCipherService = dataCipherService;
  }

  @Override
  protected String getIuv(RegistryEventSilDTO dto) {
    return dto.getIuv();
  }

  @Override
  protected String getNav(RegistryEventSilDTO dto) {
    return dto.getNav();
  }

  @Override
  protected SilRegistry build(RegistryEventSilDTO dto, String iuv, String nav) {
    return SilRegistry.builder()
      .registryId(dto.getRegistryId() + Objects.requireNonNullElse(iuv, ""))
      .registryOrigin(dto.getRegistryOrigin())
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
      .build();
  }
}
