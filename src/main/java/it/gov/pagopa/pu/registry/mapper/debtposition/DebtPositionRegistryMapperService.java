package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.*;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DebtPositionRegistryMapperService {

  private final DebtPositionEventDTO2DebtPositionRegistryMapper debtPositionEventDTO2DebtPositionRegistryMapper;
  private final DebtPositionIoEventDTO2DebtPositionRegistryMapper debtPositionIoEventDTO2DebtPositionRegistryMapper;
  private final DebtPositionSendEventDTO2DebtPositionRegistryMapper debtPositionSendEventDTO2DebtPositionRegistryMapper;

  public DebtPositionRegistry map(PaymentEventDTO<?> event) {
    if (event == null) {
      log.warn("Received null event");
      return null;
    }

    if (event.getPayload() == null) {
      log.warn("Received event with null payload: eventType={} eventId={} traceId={} eventDescription={}",
               event.getEventType(), event.getEventId(), event.getTraceId(), event.getEventDescription());
      return null;
    }

    if (event.getEventType() == null) {
      log.warn("Received event with null event type: eventId={} traceId={} eventDescription={}",
               event.getEventId(), event.getTraceId(), event.getEventDescription());
      return null;
    }

    switch (event) {
      case DebtPositionEventDTO debtPositionEvent -> {
        return debtPositionEventDTO2DebtPositionRegistryMapper.map(debtPositionEvent);
      }
      case DebtPositionIoEventDTO debtPositionIoEvent -> {
        return debtPositionIoEventDTO2DebtPositionRegistryMapper.map(debtPositionIoEvent);
      }
      case DebtPositionSendEventDTO debtPositionSendEvent -> {
        return debtPositionSendEventDTO2DebtPositionRegistryMapper.map(debtPositionSendEvent);
      }
      default -> {
        log.warn("Unsupported payload type: eventType={} eventId={} traceId={} eventDescription={}",
          event.getEventType(), event.getEventId(), event.getTraceId(), event.getEventDescription());
        return null;
      }
    }
  }

}
