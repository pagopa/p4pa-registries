package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.*;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DebtPositionRegistryMapperServiceTest {

  private DebtPositionRegistryMapperService debtPositionRegistryMapperService;
  private DebtPositionEventDTO2DebtPositionRegistryMapper debtPositionEventDTO2DebtPositionRegistryMapper;
  private DebtPositionIoEventDTO2DebtPositionRegistryMapper debtPositionIoEventDTO2DebtPositionRegistryMapper;
  private DebtPositionSendEventDTO2DebtPositionRegistryMapper debtPositionSendEventDTO2DebtPositionRegistryMapper;

  @BeforeEach
  void setUp() {
    this.debtPositionEventDTO2DebtPositionRegistryMapper = Mockito.spy(new DebtPositionEventDTO2DebtPositionRegistryMapper());
    this.debtPositionIoEventDTO2DebtPositionRegistryMapper = Mockito.spy(new DebtPositionIoEventDTO2DebtPositionRegistryMapper());
    this.debtPositionSendEventDTO2DebtPositionRegistryMapper = Mockito.spy(new DebtPositionSendEventDTO2DebtPositionRegistryMapper());

    debtPositionRegistryMapperService = new DebtPositionRegistryMapperService(
        debtPositionEventDTO2DebtPositionRegistryMapper,
        debtPositionIoEventDTO2DebtPositionRegistryMapper,
        debtPositionSendEventDTO2DebtPositionRegistryMapper
    );
  }

  @Test
  void map_shouldReturnDebtPositionRegistry_whenEventIsDebtPositionEvent() {
    DebtPositionEventDTO dto = new DebtPositionEventDTO();
    dto.setEventType(PaymentEventType.DP_CREATED);
    dto.setPayload(new DebtPositionDTO());
    this.debtPositionRegistryMapperService.map(dto);
    Mockito.verify(debtPositionEventDTO2DebtPositionRegistryMapper, Mockito.times(1)).map(Mockito.any(DebtPositionEventDTO.class));
  }

  @Test
  void map_shouldReturnDebtPositionRegistry_whenEventIsDebtPositionIoEvent() {
    DebtPositionIoEventDTO dto = new DebtPositionIoEventDTO();
    dto.setEventType(PaymentEventType.IO_NOTIFIED);
    dto.setPayload(new DebtPositionIoNotificationDTO());
    this.debtPositionRegistryMapperService.map(dto);
    Mockito.verify(debtPositionIoEventDTO2DebtPositionRegistryMapper, Mockito.times(1)).map(Mockito.any(DebtPositionIoEventDTO.class));
  }

  @Test
  void map_shouldReturnDebtPositionRegistry_whenEventIsDebtPositionSendEvent() {
    DebtPositionSendEventDTO dto = new DebtPositionSendEventDTO();
    dto.setEventType(PaymentEventType.SEND_NOTIFICATION_CREATED);
    dto.setPayload(new DebtPositionSendNotificationDTO());
    this.debtPositionRegistryMapperService.map(dto);
    Mockito.verify(debtPositionSendEventDTO2DebtPositionRegistryMapper, Mockito.times(1)).map(Mockito.any(DebtPositionSendEventDTO.class));
  }

}
