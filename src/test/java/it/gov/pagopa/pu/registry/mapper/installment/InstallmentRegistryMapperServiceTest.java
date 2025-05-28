package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.*;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InstallmentRegistryMapperServiceTest {

  private InstallmentRegistryMapperService installmentRegistryMapperService;
  private DebtPositionEventDTO2InstallmentRegistryMapper debtPositionEventDTO2InstallmentRegistryMapper;
  private DebtPositionIoEventDTO2InstallmentRegistryMapper debtPositionIoEventDTO2InstallmentRegistryMapper;
  private DebtPositionSendEventDTO2InstallmentRegistryMapper debtPositionSendEventDTO2InstallmentRegistryMapper;

  @BeforeEach
  void setUp() {
    this.debtPositionEventDTO2InstallmentRegistryMapper = Mockito.spy(new DebtPositionEventDTO2InstallmentRegistryMapper());
    this.debtPositionIoEventDTO2InstallmentRegistryMapper = Mockito.spy(new DebtPositionIoEventDTO2InstallmentRegistryMapper());
    this.debtPositionSendEventDTO2InstallmentRegistryMapper = Mockito.spy(new DebtPositionSendEventDTO2InstallmentRegistryMapper());

    installmentRegistryMapperService = new InstallmentRegistryMapperService(
        debtPositionEventDTO2InstallmentRegistryMapper,
        debtPositionIoEventDTO2InstallmentRegistryMapper,
        debtPositionSendEventDTO2InstallmentRegistryMapper
    );
  }

  @Test
  void map_shouldReturnInstallmentRegistry_whenEventIsDebtPositionEvent() {
    DebtPositionEventDTO dto = new DebtPositionEventDTO();
    dto.setEventType(PaymentEventType.DP_CREATED);
    dto.setPayload(new DebtPositionDTO());
    this.installmentRegistryMapperService.map(dto);
    Mockito.verify(debtPositionEventDTO2InstallmentRegistryMapper, Mockito.times(1)).map(Mockito.any(DebtPositionEventDTO.class));
  }

  @Test
  void map_shouldReturnInstallmentRegistry_whenEventIsDebtPositionIoEvent() {
    DebtPositionIoEventDTO dto = new DebtPositionIoEventDTO();
    dto.setEventType(PaymentEventType.IO_NOTIFIED);
    dto.setPayload(new DebtPositionIoNotificationDTO());
    this.installmentRegistryMapperService.map(dto);
    Mockito.verify(debtPositionIoEventDTO2InstallmentRegistryMapper, Mockito.times(1)).map(Mockito.any(DebtPositionIoEventDTO.class));
  }

  @Test
  void map_shouldReturnInstallmentRegistry_whenEventIsDebtPositionSendEvent() {
    DebtPositionSendEventDTO dto = new DebtPositionSendEventDTO();
    dto.setEventType(PaymentEventType.SEND_NOTIFICATION_CREATED);
    dto.setPayload(new DebtPositionSendNotificationDTO());
    this.installmentRegistryMapperService.map(dto);
    Mockito.verify(debtPositionSendEventDTO2InstallmentRegistryMapper, Mockito.times(1)).map(Mockito.any(DebtPositionSendEventDTO.class));
  }

}
