package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.event.registry.dto.PaSendRTV2RequestEventDTO;
import it.gov.pagopa.pu.registry.event.registry.dto.PaSendRTV2ResponseEventDTO;
import it.gov.pagopa.pu.registry.event.registry.dto.RegistryPagoPaEventDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.PaSendRTV2RequestEventDTO2PagopaRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.pagopa.PaSendRTV2ResponseEventDTO2PagopaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class PagoPaRegistryMapperService {
  private final PaSendRTV2RequestEventDTO2PagopaRegistryMapper paSendRTV2RequestEventDTO2PagopaRegistryMapper;
  private final PaSendRTV2ResponseEventDTO2PagopaRegistryMapper paSendRTV2ResponseEventDTO2PagopaRegistryMapper;

  public PagopaRegistry map(@Valid @NotNull RegistryPagoPaEventDTO<?> event) {
    switch (event) {
      case PaSendRTV2RequestEventDTO paSendRTV2RequestEvent -> {
        return paSendRTV2RequestEventDTO2PagopaRegistryMapper.map(paSendRTV2RequestEvent);
      }

      case PaSendRTV2ResponseEventDTO paSendRTV2ResponseEvent -> {
        return paSendRTV2ResponseEventDTO2PagopaRegistryMapper.map(paSendRTV2ResponseEvent);
      }

      default -> {
        log.warn("Unsupported payload type: eventType={} registryId={} traceId={} eventSubType={}",
          event.getEventType(), event.getRegistryId(), event.getTraceId(), event.getEventSubType());
        return null;
      }
    }
  }
}
