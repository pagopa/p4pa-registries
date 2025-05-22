package it.gov.pagopa.pu.registry.event.payments.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class DebtPositionSendEventDTO extends PaymentEventDTO<DebtPositionSendNotificationDTO> {
}
