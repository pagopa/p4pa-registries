package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionIoEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
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
public class DebtPositionRegistryMapperService {

  private final DebtPositionEventDTO2DebtPositionRegistryMapper debtPositionEventDTO2DebtPositionRegistryMapper;
  private final DebtPositionIoEventDTO2DebtPositionRegistryMapper debtPositionIoEventDTO2DebtPositionRegistryMapper;
  private final DebtPositionSendEventDTO2DebtPositionRegistryMapper debtPositionSendEventDTO2DebtPositionRegistryMapper;

  public DebtPositionRegistry map(@Valid @NotNull PaymentEventDTO<?> event) {
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
