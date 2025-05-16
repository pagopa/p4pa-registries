package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class DebtPositionEventDTO2DebtPositionRegistryMapper {

  public DebtPositionRegistry map(DebtPositionEventDTO dto) {
    Objects.requireNonNull(dto.getEventId(), "eventId cannot be null");
    Objects.requireNonNull(dto.getEventType(), "eventType cannot be null");
    Objects.requireNonNull(dto.getTraceId(), "traceId cannot be null");
    Objects.requireNonNull(dto.getEventDateTime(), "eventDateTime cannot be null");
    Objects.requireNonNull(dto.getPayload(), "payload cannot be null");
    Objects.requireNonNull(dto.getPayload().getDebtPositionId(), "payload.debtPositionId cannot be null");
    Objects.requireNonNull(dto.getPayload().getUpdateOperatorExternalId(), "payload.updateOperatorExternalId cannot be null");
    Objects.requireNonNull(dto.getPayload().getOrganizationId(), "payload.organizationId cannot be null");

    return DebtPositionRegistry.builder()
      .eventId(dto.getEventId())
      .eventType(dto.getEventType())
      .traceId(dto.getTraceId())
      .eventDateTime(dto.getEventDateTime())
      .eventDescription(dto.getEventDescription())
      .operatorExternalUserId(dto.getPayload().getUpdateOperatorExternalId())
      .debtPositionId(dto.getPayload().getDebtPositionId())
      .organizationId(dto.getPayload().getOrganizationId())
      .build();
  }

}
