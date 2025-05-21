package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DebtPositionSendEventDTO2InstallmentRegistryMapper {

  public List<InstallmentRegistry> map(DebtPositionSendEventDTO dto) {
    if (dto.getPayload().getNoticeCodes() == null) return List.of();

    return dto.getPayload().getNoticeCodes().stream()
      .map(noticeCode -> InstallmentRegistry.builder()
        .eventId(dto.getEventId() + "." + noticeCode)
        .eventType(dto.getEventType())
        .traceId(dto.getTraceId())
        .eventDateTime(dto.getEventDateTime())
        .eventDescription(dto.getEventDescription())
        .debtPositionId(dto.getPayload().getDebtPositionId())
        .organizationId(dto.getPayload().getOrganizationId())
        .nav(noticeCode)
        .build())
      .collect(Collectors.toList());
  }

}
