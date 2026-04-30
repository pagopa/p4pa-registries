package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionIoEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionIoNotificationDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebtPositionIoEventDTO2InstallmentRegistryMapperTest {

  private DebtPositionIoEventDTO2InstallmentRegistryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DebtPositionIoEventDTO2InstallmentRegistryMapper();
  }

  @Test
  void whenMappingToInstallmentRegistryThenReturnsCorrectResults() {
    // Given
    DebtPositionIoEventDTO dto = createValidDto();

    // When
    List<InstallmentRegistry> result = mapper.map(dto);

    // Then
    result.forEach(installmentRegistry ->
      TestUtils.checkNotNullFields(installmentRegistry,
        "operatorExternalUserId",
        "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId"
      ));

    assertEquals(2, result.size());
    assertEquals(4, dto.getPayload().getMessages().size());
    assertEquals(dto.getEventType(), result.getFirst().getEventType());
    assertEquals(dto.getTraceId(), result.getFirst().getTraceId());
    assertEquals(dto.getEventDateTime(), result.getFirst().getEventDateTime());
    assertEquals(dto.getEventDescription() + "; ioNotificationId: notificationId1", result.getFirst().getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getFirst().getDebtPositionId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getFirst().getOrganizationId());

    DebtPositionIoNotificationDTO.IoMessage firstInstallmentDTO = dto.getPayload().getMessages().getFirst();
    DebtPositionIoNotificationDTO.IoMessage secondInstallmentDTO = dto.getPayload().getMessages().get(1);
    assertEquals(dto.getEventId() + "." + firstInstallmentDTO.getNav(), result.get(0).getEventId());
    assertEquals(firstInstallmentDTO.getNav(), result.get(0).getNav());

    assertEquals(dto.getEventId() + "." + secondInstallmentDTO.getNav(), result.get(1).getEventId());
    assertEquals(secondInstallmentDTO.getNav(), result.get(1).getNav());
  }

  @Test
  void whenGetEventDescriptionThenReturnCorrectDescription() {
    // Given
    DebtPositionIoEventDTO dto = createValidDto();

    // When
    List<InstallmentRegistry> results = mapper.map(dto);

    // Then
    assertEquals("Some event description; ioNotificationId: notificationId1", results.getFirst().getEventDescription());
  }

  private DebtPositionIoEventDTO createValidDto() {
    List<DebtPositionIoNotificationDTO.IoMessage> ioMessagess = List.of(
      DebtPositionIoNotificationDTO.IoMessage.builder()
        .serviceId("serviceId1")
        .nav("nav1")
        .notificationId("notificationId1")
        .build(),
      DebtPositionIoNotificationDTO.IoMessage.builder()
        .serviceId("serviceId2")
        .nav("nav2")
        .notificationId("notificationId2")
        .build(),
      DebtPositionIoNotificationDTO.IoMessage.builder()
        .serviceId("serviceId3")
        .nav(null)
        .notificationId("notificationId3")
        .build(),
      DebtPositionIoNotificationDTO.IoMessage.builder()
        .serviceId("serviceId4")
        .nav("")
        .notificationId("notificationId4")
        .build()
    );

    DebtPositionIoNotificationDTO payload = DebtPositionIoNotificationDTO.builder()
      .debtPositionId(123L)
      .debtPositionTypeOrgId(456L)
      .organizationId(789L)
      .messages(ioMessagess)
      .build();

    return DebtPositionIoEventDTO.builder()
      .eventId("eventId1")
      .eventType(PaymentEventType.DP_CREATED)
      .traceId("traceId2")
      .eventDateTime(OffsetDateTime.now())
      .eventDescription("Some event description")
      .payload(payload)
      .build();
  }

}
