package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.dto.KeyValueDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.registry.utils.StringBuilderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DebtPositionSendEventDTO2DebtPositionRegistryMapper {

  public DebtPositionRegistry map(DebtPositionSendEventDTO dto) {
    StringBuilder sb = new StringBuilder();

    if (StringUtils.hasText(dto.getEventDescription())) {
      sb.append(dto.getEventDescription());
    }

    List<KeyValueDTO<String>> additionalInformationArray = new ArrayList<>();

    if (dto.getPayload() != null && dto.getPayload().getIun() != null) {
      additionalInformationArray.add(new KeyValueDTO<>("IUN", dto.getPayload().getIun()));
    }

    if (dto.getPayload() != null && dto.getPayload().getNotificationDate() != null) {
      additionalInformationArray.add(new KeyValueDTO<>("NotificationDate", dto.getPayload().getNotificationDate().toString()));
    }

    StringBuilderUtils.appendFromArray(sb, additionalInformationArray, ": ", "; ");

    return DebtPositionRegistry.builder()
      .eventId(dto.getEventId())
      .eventType(dto.getEventType())
      .traceId(dto.getTraceId())
      .eventDateTime(dto.getEventDateTime())
      .eventDescription(sb.toString())
      .debtPositionId(dto.getPayload().getDebtPositionId())
      .organizationId(dto.getPayload().getOrganizationId())
      .build();
  }

}
