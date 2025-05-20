package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionIoEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DebtPositionIoEventDTO2InstallmentRegistryMapper {

  public List<InstallmentRegistry> map(DebtPositionIoEventDTO dto) {
    if (dto.getPayload().getMessages() == null) return List.of();

    List<String> iudList;

    if (dto.getEventDescription().startsWith("IUD:")) {
      iudList = List.of(dto.getEventDescription().split(":")[1].trim().split(","));
    } else {
      iudList = null;
    }

    return dto.getPayload().getMessages().stream()
      .filter(ioMessageDTO -> {
        if (iudList == null) return true;
        return iudList.contains(ioMessageDTO.getNotificationId());
      })
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
      .collect(Collectors.toList());
  }

}
