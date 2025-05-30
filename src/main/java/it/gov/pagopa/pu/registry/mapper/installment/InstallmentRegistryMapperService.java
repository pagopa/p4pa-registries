package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.*;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentRegistryMapperService {

  private final DebtPositionEventDTO2InstallmentRegistryMapper debtPositionEventDTO2InstallmentRegistryMapper;
  private final DebtPositionIoEventDTO2InstallmentRegistryMapper debtPositionIoEventDTO2DebtPositionRegistryMapper;
  private final DebtPositionSendEventDTO2InstallmentRegistryMapper debtPositionSendEventDTO2DebtPositionRegistryMapper;

  public List<InstallmentRegistry> map(PaymentEventDTO<?> event) {
    if (event == null) {
      return List.of();
    }

    if (event.getPayload() == null) {
      log.warn("Received event with null payload: eventType={} eventId={} traceId={} eventDescription={}",
        event.getEventType(), event.getEventId(), event.getTraceId(), event.getEventDescription());
      return List.of();
    }

    if (event.getEventType() == null) {
      log.warn("Received event with null event type: eventId={} traceId={} eventDescription={}",
        event.getEventId(), event.getTraceId(), event.getEventDescription());
      return List.of();
    }

    switch (event) {
      case DebtPositionEventDTO debtPositionEvent -> {
        return debtPositionEventDTO2InstallmentRegistryMapper.map(debtPositionEvent);
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
        return List.of();
      }
    }
  }

}
