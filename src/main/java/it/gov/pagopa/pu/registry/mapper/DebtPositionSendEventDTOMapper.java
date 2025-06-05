package it.gov.pagopa.pu.registry.mapper;

import it.gov.pagopa.pu.registry.dto.KeyValueDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.utils.StringBuilderUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DebtPositionSendEventDTOMapper {

  public String getEventDescription(DebtPositionSendEventDTO dto) {
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

    return sb.toString();
  }

}
