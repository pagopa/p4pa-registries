package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.enums.*;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class RegistryEventSilDTO2SilRegistryMapper {
  private final DataCipherService dataCipherService;

  public RegistryEventSilDTO2SilRegistryMapper(DataCipherService dataCipherService) {
    this.dataCipherService = dataCipherService;
  }

  public List<SilRegistry> mapToSilRegistry(RegistryEventSilDTO dto) {
    List<SilRegistry> result = new ArrayList<>();

    String iuvRaw = dto.getIuv();
    String navRaw = dto.getNav();

    boolean iuvEmpty = (iuvRaw == null || iuvRaw.isBlank());
    boolean navEmpty = (navRaw == null || navRaw.isBlank());

    if (iuvEmpty && navEmpty) {
      SilRegistry silRegistry = buildRegistry(dto, null, null);
      result.add(silRegistry);
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
        throw new IllegalArgumentException("mapToSilRegistry - The size of the IUV list (" +
          iuvList.size() + ") does not match the size of the NAV list (" + navList.size() + ").");
      }
    }

    return result;
  }

  private SilRegistry buildRegistry(RegistryEventSilDTO dto, String iuv, String nav) {
    return SilRegistry.builder()
      .eventId(dto.getRegistryId())
      .dateTime(dto.getDateTime())
      .traceId(dto.getTraceId())
      .brokerFiscalCode(dto.getBrokerFiscalCode())
      .orgFiscalCode(dto.getOrgFiscalCode())
      .iuv(iuv)
      .nav(nav)
      .eventType(RegistrySilEventType.valueOf(dto.getEventType()))
      .eventSubType(RegistryEventSubType.valueOf(dto.getEventSubType()))
      .requestorId(dto.getRequestorId())
      .grantorId(dto.getGrantorId())
      .outcome(RegistryOutcome.valueOf(dto.getOutcome()))
      .bodyCiphered(dataCipherService.encrypt(dto.getBody()))
      .build();
  }
}
