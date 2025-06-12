package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.*;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class InstallmentRegistryMapperServiceTest {

  @Mock
  private DebtPositionEventDTO2InstallmentRegistryMapper dpEventMapperMock;
  @Mock
  private DebtPositionIoEventDTO2InstallmentRegistryMapper dpIoEventMapperMock;
  @Mock
  private DebtPositionSendEventDTO2InstallmentRegistryMapper dpSendEventMapperMock;

  private InstallmentRegistryMapperService service;

  @BeforeEach
  void setUp() {
    service = new InstallmentRegistryMapperService(
      dpEventMapperMock,
      dpIoEventMapperMock,
      dpSendEventMapperMock
    );
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(dpEventMapperMock, dpIoEventMapperMock, dpSendEventMapperMock);
  }

  @Test
  void givenDPEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    DebtPositionEventDTO dto = new DebtPositionEventDTO();
    dto.setEventType(PaymentEventType.DP_CREATED);
    dto.setPayload(new DebtPositionDTO());
    List<InstallmentRegistry> installmentRegistries = List.of(new InstallmentRegistry());

    Mockito.when(dpEventMapperMock.map(Mockito.same(dto))).thenReturn(installmentRegistries);

    // When
    List<InstallmentRegistry> results = this.service.map(dto);

    // Then
    assertSame(installmentRegistries, results);
  }

  @Test
  void givenDPIoEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    DebtPositionIoEventDTO dto = new DebtPositionIoEventDTO();
    dto.setEventType(PaymentEventType.IO_NOTIFIED);
    dto.setPayload(new DebtPositionIoNotificationDTO());
    List<InstallmentRegistry> installmentRegistries = List.of(new InstallmentRegistry());

    Mockito.when(dpIoEventMapperMock.map(Mockito.same(dto))).thenReturn(installmentRegistries);

    // When
    List<InstallmentRegistry> results = this.service.map(dto);

    // Then
    assertSame(installmentRegistries, results);
  }

  @Test
  void givenDPSendEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    DebtPositionSendEventDTO dto = new DebtPositionSendEventDTO();
    dto.setEventType(PaymentEventType.SEND_NOTIFICATION_CREATED);
    dto.setPayload(new DebtPositionSendNotificationDTO());
    List<InstallmentRegistry> installmentRegistries = List.of(new InstallmentRegistry());

    Mockito.when(dpSendEventMapperMock.map(Mockito.same(dto))).thenReturn(installmentRegistries);

    // When
    List<InstallmentRegistry> results = this.service.map(dto);

    // Then
    assertSame(installmentRegistries, results);
  }

  @Test
  void givenNullEventWhenMappedThenReturnsEmpty() {
    // When
    List<InstallmentRegistry> results = this.service.map(null);

    // Then
    assertEquals(0, results.size());
  }

  @Test
  void givenNullPayloadWhenMappedThenReturnsEmpty() {
    // Given
    PaymentEventDTO<Object> dto = new PaymentEventDTO<>();
    dto.setEventType(PaymentEventType.SYNC_ERROR);
    dto.setPayload(null);

    // When
    List<InstallmentRegistry> results = this.service.map(dto);

    // Then
    assertEquals(0, results.size());
  }

  @Test
  void givenNullEventTypeWhenMappedThenReturnsEmpty() {
    // Given
    PaymentEventDTO<Object> dto = new PaymentEventDTO<>();
    dto.setPayload(new Object());

    // When
    List<InstallmentRegistry> results = this.service.map(dto);

    // Then
    assertEquals(0, results.size());
  }

}
