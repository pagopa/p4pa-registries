package it.gov.pagopa.pu.registry.mapper;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DebtPositionSendEventDTOMapper {

  public String getEventDescription(DebtPositionSendEventDTO dto) {
    return Stream.of(
        dto.getEventDescription(),
        Optional.ofNullable(dto.getPayload().getIun()).map(iun -> "IUN: " + iun).orElse(null),
        Optional.ofNullable(dto.getPayload().getNotificationDate()).map(dt -> "NotificationDate: " + dt).orElse(null)
        )
      .filter(StringUtils::hasText)
      .collect(Collectors.joining("; "));
  }

}
