package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DebtPositionSendEventDTO2DebtPositionRegistryMapper {

  public DebtPositionRegistry map(DebtPositionSendEventDTO dto) {
    return DebtPositionRegistry.builder()
      .eventId(dto.getEventId())
      .eventType(dto.getEventType())
      .traceId(dto.getTraceId())
      .eventDateTime(dto.getEventDateTime())
      .eventDescription(dto.getEventDescription())
      .debtPositionId(dto.getPayload().getDebtPositionId())
      .organizationId(dto.getPayload().getOrganizationId())
      .build();
  }

}
