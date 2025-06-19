package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

@Service
public class RegistryEventPagoPaDTO2PagoPaRegistryMapper extends BaseRegistryMapper<RegistryEventPagoPaDTO, PagopaRegistry> {

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
  protected PagopaRegistry build(RegistryEventPagoPaDTO d, String iuv, String nav) {
    return PagopaRegistry.builder()
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
      .build();
  }
}
