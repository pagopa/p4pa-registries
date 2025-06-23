package it.gov.pagopa.pu.registry.event.registry;

import it.gov.pagopa.pu.registry.dto.RegistryEventDTO;
import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.service.pagopa.PagoPaRegistryService;
import it.gov.pagopa.pu.registry.service.sil.SilRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistryEventsConsumer implements Consumer<RegistryEventDTO> {

  private final PagoPaRegistryService pagoPaRegistryService;
  private final SilRegistryService silRegistryService;

  @Override
  public void accept(RegistryEventDTO event) {
    try {
      switch (event) {
        case RegistryEventPagoPaDTO registryEventPagoPaDTO:
          pagoPaRegistryService.consumePaymentEvent(registryEventPagoPaDTO);
          break;
        case RegistryEventSilDTO registryEventSilDTO:
          silRegistryService.consumePaymentEvent(registryEventSilDTO);
          break;
        default:
          log.warn("Unsupported event type: registryId={} registryOrigin={} registryType={} traceId={} eventType={} eventSubType={}",
            event.getRegistryId(), event.getRegistryOrigin(), event.getRegistryType(), event.getTraceId(),event.getEventType(), event.getEventSubType());
          break;
      }
    } catch (Exception exception) {
      log.error("Error processing payment event: registryId={} registryOrigin={} registryType={} traceId={} eventType={} eventSubType={}",
        event.getRegistryId(), event.getRegistryOrigin(), event.getRegistryType(), event.getTraceId(),event.getEventType(), event.getEventSubType(), exception);
    }
  }
}
