package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RegistryEventPagoPaDTO2PagoPaRegistryMapper extends BaseRegistryMapper<RegistryEventPagoPaDTO, PagoPaRegistry> {

  private final DataCipherService dataCipherService;

  public RegistryEventPagoPaDTO2PagoPaRegistryMapper(DataCipherService dataCipherService) {
    this.dataCipherService = dataCipherService;
  }

  @Override
  protected String getIuv(RegistryEventPagoPaDTO dto) {
    return dto.getIuv();
  }

  @Override
  protected String getNav(RegistryEventPagoPaDTO dto) {
    return dto.getNav();
  }

  @Override
  protected PagoPaRegistry map(RegistryEventPagoPaDTO dto, String iuv, String nav) {
    return PagoPaRegistry.builder()
      .registryId(dto.getRegistryId() + Objects.requireNonNullElse(iuv, ""))
      .registryOrigin(dto.getRegistryOrigin())
      .dateTime(dto.getDateTime())
      .traceId(dto.getTraceId())
      .brokerStationId(dto.getBrokerStationId())
      .orgFiscalCode(dto.getOrgFiscalCode())
      .iuv(iuv)
      .nav(nav)
      .ccp(dto.getCcp())
      .pspId(dto.getPspId())
      .pspChannelId(dto.getPspChannelId())
      .paymentMethod(dto.getPaymentMethod())
      .eventCategory(dto.getEventCategory())
      .eventType(dto.getEventType())
      .eventSubType(dto.getEventSubType())
      .requestorId(dto.getRequestorId())
      .grantorId(dto.getGrantorId())
      .outcome(dto.getOutcome())
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build();
  }
}
