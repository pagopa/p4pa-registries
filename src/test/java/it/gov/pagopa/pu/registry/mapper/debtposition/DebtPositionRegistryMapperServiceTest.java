package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.*;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class DebtPositionRegistryMapperServiceTest {

  @Mock
  private DebtPositionEventDTO2DebtPositionRegistryMapper dpEventMapperMock;
  @Mock
  private DebtPositionIoEventDTO2DebtPositionRegistryMapper dpIoEventMapperMock;
  @Mock
  private DebtPositionSendEventDTO2DebtPositionRegistryMapper dpSendEventMapperMock;

  private DebtPositionRegistryMapperService service;

  @BeforeEach
  void setUp() {
    service = new DebtPositionRegistryMapperService(
      dpEventMapperMock,
      dpIoEventMapperMock,
      dpSendEventMapperMock
    );
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(dpIoEventMapperMock);
  }

  @Test
  void givenDPEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    DebtPositionEventDTO dto = new DebtPositionEventDTO();
    dto.setEventType(PaymentEventType.DP_CREATED);
    dto.setPayload(new DebtPositionDTO());
    DebtPositionRegistry debtPositionRegistry = new DebtPositionRegistry();

    Mockito.when(dpEventMapperMock.map(Mockito.same(dto)))
           .thenReturn(debtPositionRegistry);

    // When
    DebtPositionRegistry result = this.service.map(dto);

    // Then
    assertSame(debtPositionRegistry, result);
  }

  @Test
  void givenDPIoEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    DebtPositionIoEventDTO dto = new DebtPositionIoEventDTO();
    dto.setEventType(PaymentEventType.IO_NOTIFIED);
    dto.setPayload(new DebtPositionIoNotificationDTO());
    DebtPositionRegistry debtPositionRegistry = new DebtPositionRegistry();

    Mockito.when(dpIoEventMapperMock.map(Mockito.same(dto)))
      .thenReturn(debtPositionRegistry);

    // When
    DebtPositionRegistry result = this.service.map(dto);

    // Then
    assertSame(debtPositionRegistry, result);
  }

  @Test
  void givenDPSendEventWHenMappedThenInvokesExpectedMapper() {
    // Given
    DebtPositionSendEventDTO dto = new DebtPositionSendEventDTO();
    dto.setEventType(PaymentEventType.SEND_NOTIFICATION_CREATED);
    dto.setPayload(new DebtPositionSendNotificationDTO());
    DebtPositionRegistry debtPositionRegistry = new DebtPositionRegistry();

    Mockito.when(dpSendEventMapperMock.map(Mockito.same(dto)))
      .thenReturn(debtPositionRegistry);

    // When
    DebtPositionRegistry result = this.service.map(dto);

    // Then
    assertSame(debtPositionRegistry, result);
  }

}
