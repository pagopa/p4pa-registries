package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.workflowhub.dto.generated.InstallmentDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentOptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class DebtPositionEventDTO2InstallmentRegistryMapper {

  public List<InstallmentRegistry> map(DebtPositionEventDTO dto) {
    Objects.requireNonNull(dto.getEventId(), "eventId cannot be null");
    Objects.requireNonNull(dto.getEventType(), "eventType cannot be null");
    Objects.requireNonNull(dto.getTraceId(), "traceId cannot be null");
    Objects.requireNonNull(dto.getEventDateTime(), "eventDateTime cannot be null");
    Objects.requireNonNull(dto.getPayload(), "payload cannot be null");
    Objects.requireNonNull(dto.getPayload().getDebtPositionId(), "payload.debtPositionId cannot be null");
    Objects.requireNonNull(dto.getPayload().getUpdateOperatorExternalId(), "payload.updateOperatorExternalId cannot be null");
    Objects.requireNonNull(dto.getPayload().getOrganizationId(), "payload.organizationId cannot be null");
    Objects.requireNonNull(dto.getPayload().getPaymentOptions(), "payload.paymentOptions cannot be null");

    List<InstallmentRegistry> installmentRegistries = new ArrayList<>();

    for (PaymentOptionDTO paymentOptionDTO : dto.getPayload().getPaymentOptions()) {
      if (paymentOptionDTO.getInstallments() == null) continue;

      for (InstallmentDTO installmentDTO : paymentOptionDTO.getInstallments()) {
        if (installmentDTO.getNav() == null) {
          log.warn("InstallmentDTO nav is null, skipping this installment");
          continue;
        }

        if (installmentDTO.getUpdateOperatorExternalId() == null) {
          log.warn("InstallmentDTO updateOperatorExternalId is null, skipping this installment");
          continue;
        }

        InstallmentRegistry installmentRegistry = InstallmentRegistry.builder()
          .eventId(dto.getEventId() + "." + installmentDTO.getNav())
          .eventType(dto.getEventType())
          .traceId(dto.getTraceId())
          .eventDateTime(dto.getEventDateTime())
          .eventDescription(dto.getEventDescription())
          .operatorExternalUserId(installmentDTO.getUpdateOperatorExternalId())
          .debtPositionId(dto.getPayload().getDebtPositionId())
          .organizationId(dto.getPayload().getOrganizationId())
          .nav(installmentDTO.getNav())
          .build();

        installmentRegistries.add(installmentRegistry);
      }
    }

    return installmentRegistries;
  }

}
