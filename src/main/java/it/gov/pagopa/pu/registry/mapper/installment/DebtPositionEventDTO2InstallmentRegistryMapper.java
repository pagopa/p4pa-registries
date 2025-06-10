package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.utils.ExtractionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DebtPositionEventDTO2InstallmentRegistryMapper {

  public List<InstallmentRegistry> map(DebtPositionEventDTO dto) {
    if (dto.getPayload().getPaymentOptions() == null) return List.of();

    Set<String> iudIds = ExtractionUtils.extractIudIdsFromDescription(dto.getEventDescription());

    return dto.getPayload().getPaymentOptions().stream()
      .filter(paymentOptionDTO -> paymentOptionDTO.getInstallments() != null)
      .flatMap(paymentOptionDTO -> paymentOptionDTO.getInstallments().stream())
      .filter(installmentDTO -> iudIds.isEmpty() || iudIds.contains(installmentDTO.getIud()))
      .map(installmentDTO -> InstallmentRegistry.builder()
        .eventId(dto.getEventId() + "." + installmentDTO.getNav())
        .eventType(dto.getEventType())
        .traceId(dto.getTraceId())
        .eventDateTime(dto.getEventDateTime())
        .eventDescription(dto.getEventDescription())
        .operatorExternalUserId(installmentDTO.getUpdateOperatorExternalId())
        .debtPositionId(dto.getPayload().getDebtPositionId())
        .organizationId(dto.getPayload().getOrganizationId())
        .nav(installmentDTO.getNav())
        .build())
      .toList();
  }

}
