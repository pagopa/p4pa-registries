package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionIoEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class DebtPositionIoEventDTO2InstallmentRegistryMapper {

  public List<InstallmentRegistry> map(DebtPositionIoEventDTO dto) {
    if (dto.getPayload().getMessages() == null) return List.of();

    return dto.getPayload().getMessages().stream()
      .filter(ioMessageDTO -> StringUtils.hasText(ioMessageDTO.getNav()))
      .map(ioMessageDTO -> InstallmentRegistry.builder()
        .eventId(dto.getEventId() + "." + ioMessageDTO.getNav())
        .eventType(dto.getEventType())
        .traceId(dto.getTraceId())
        .eventDateTime(dto.getEventDateTime())
        .eventDescription(dto.getEventDescription())
        .debtPositionId(dto.getPayload().getDebtPositionId())
        .organizationId(dto.getPayload().getOrganizationId())
        .nav(ioMessageDTO.getNav())
        .build())
      .toList();
  }

}
