package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendNotificationDTO;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebtPositionSendEventDTO2DebtPositionRegistryMapperTest {

  private DebtPositionSendEventDTO2DebtPositionRegistryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DebtPositionSendEventDTO2DebtPositionRegistryMapper();
  }

  @Test
  void map_shouldMapCorrectly_whenValidInput() {
    DebtPositionSendEventDTO dto = createValidDto();
    DebtPositionRegistry result = mapper.map(dto);
    TestUtils.checkNotNullFields(result, "operatorExternalUserId", "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");

    assertEquals(dto.getEventId(), result.getEventId());
    assertEquals(dto.getEventType(), result.getEventType());
    assertEquals(dto.getTraceId(), result.getTraceId());
    assertEquals(dto.getEventDateTime(), result.getEventDateTime());
    assertEquals(dto.getEventDescription(), result.getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getDebtPositionId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getOrganizationId());
  }

  private DebtPositionSendEventDTO createValidDto() {
    DebtPositionSendNotificationDTO payload = DebtPositionSendNotificationDTO.builder()
      .debtPositionId(123L)
      .organizationId(789L)
      .noticeCodes(List.of("nav1", "nav2"))
      .build();

    return DebtPositionSendEventDTO.builder()
      .eventId("eventId1")
      .eventType(PaymentEventType.DP_CREATED)
      .traceId("traceId2")
      .eventDateTime(OffsetDateTime.now())
      .eventDescription("Some event description")
      .payload(payload)
      .build();
  }

}
