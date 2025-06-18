package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagopaEventType;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class RegistryEventPagoPaDTO2PagoPaRegistryMapper {
  private final DataCipherService dataCipherService;

  public RegistryEventPagoPaDTO2PagoPaRegistryMapper(DataCipherService dataCipherService) {
    this.dataCipherService = dataCipherService;
  }

  public List<PagopaRegistry> mapToPagoPaRegistry(RegistryEventPagoPaDTO dto) {
    List<PagopaRegistry> result = new ArrayList<>();

    String iuvRaw = dto.getIuv();
    String navRaw = dto.getNav();

    boolean iuvEmpty = (iuvRaw == null || iuvRaw.isBlank());
    boolean navEmpty = (navRaw == null || navRaw.isBlank());

    if (iuvEmpty && navEmpty) {
      PagopaRegistry pagopaRegistry = buildRegistry(dto, null, null);
      result.add(pagopaRegistry);
    } else {
      List<String> iuvList = iuvEmpty ? Collections.emptyList() :
        Arrays.stream(iuvRaw.split(",")).map(String::trim).toList();

      List<String> navList = navEmpty ? Collections.emptyList() :
        Arrays.stream(navRaw.split(",")).map(String::trim).toList();

      if (iuvList.size() == navList.size()) {
        IntStream.range(0, iuvList.size())
          .mapToObj(i -> buildRegistry(dto, iuvList.get(i), navList.get(i)))
          .forEach(result::add);
      } else {
        throw new IllegalArgumentException("mapToPagoPaRegistry - The size of the IUV list (" +
          iuvList.size() + ") does not match the size of the NAV list (" + navList.size() + ").");
      }
    }

    return result;
  }

  private PagopaRegistry buildRegistry(RegistryEventPagoPaDTO dto, String iuv, String nav) {
    return PagopaRegistry.builder()
      .eventId(dto.getRegistryId())
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
      .eventCategory(RegistryEventCategory.valueOf(dto.getEventCategory()))
      .eventType(RegistryPagopaEventType.valueOf(dto.getEventType()))
      .eventSubType(RegistryEventSubType.valueOf(dto.getEventSubType()))
      .requestorId(dto.getRequestorId())
      .grantorId(dto.getGrantorId())
      .outcome(RegistryOutcome.valueOf(dto.getOutcome()))
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build();
  }
}
