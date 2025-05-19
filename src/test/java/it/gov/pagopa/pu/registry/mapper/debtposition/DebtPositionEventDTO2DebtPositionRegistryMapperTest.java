package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DebtPositionEventDTO2DebtPositionRegistryMapperTest {

  private DebtPositionEventDTO2DebtPositionRegistryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DebtPositionEventDTO2DebtPositionRegistryMapper();
  }

  @Test
  void map_shouldMapCorrectly_whenValidInput() {
    DebtPositionEventDTO dto = createValidDto();
    DebtPositionRegistry result = mapper.map(dto);
    TestUtils.checkNotNullFields(result);

    assertEquals(dto.getEventId(), result.getEventId());
    assertEquals(dto.getEventType(), result.getEventType());
    assertEquals(dto.getTraceId(), result.getTraceId());
    assertEquals(dto.getEventDateTime(), result.getEventDateTime());
    assertEquals(dto.getEventDescription(), result.getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getDebtPositionId());
    assertEquals(dto.getPayload().getUpdateOperatorExternalId(), result.getOperatorExternalUserId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getOrganizationId());
  }

  private DebtPositionEventDTO createValidDto() {
    DebtPositionDTO payload = DebtPositionDTO.builder()
      .debtPositionId(123L)
      .updateOperatorExternalId("externalOperatorId456")
      .organizationId(789L)
      .build();

    return DebtPositionEventDTO.builder()
      .eventId("eventId1")
      .eventType(PaymentEventType.DP_CREATED)
      .traceId("traceId2")
      .eventDateTime(OffsetDateTime.now())
      .eventDescription("Some event description")
      .payload(payload)
      .build();
  }

}
