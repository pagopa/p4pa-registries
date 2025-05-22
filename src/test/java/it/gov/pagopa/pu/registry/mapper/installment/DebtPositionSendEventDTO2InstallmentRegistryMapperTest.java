package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendNotificationDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebtPositionSendEventDTO2InstallmentRegistryMapperTest {

  private DebtPositionSendEventDTO2InstallmentRegistryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DebtPositionSendEventDTO2InstallmentRegistryMapper();
  }

  @Test
  void map_shouldMapCorrectly_whenValidInput() {
    DebtPositionSendEventDTO dto = createValidDto();
    List<InstallmentRegistry> result = mapper.map(dto);
    result.forEach(installmentRegistry -> {
      TestUtils.checkNotNullFields(installmentRegistry, "operatorExternalUserId");
    });

    assertEquals(2, result.size());
    assertEquals(dto.getEventType(), result.getFirst().getEventType());
    assertEquals(dto.getTraceId(), result.getFirst().getTraceId());
    assertEquals(dto.getEventDateTime(), result.getFirst().getEventDateTime());
    assertEquals(dto.getEventDescription(), result.getFirst().getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getFirst().getDebtPositionId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getFirst().getOrganizationId());
    String firstNoticeCode = dto.getPayload().getNoticeCodes().get(0);
    String secondNoticeCode = dto.getPayload().getNoticeCodes().get(1);
    assertEquals(dto.getEventId() + "." + firstNoticeCode, result.getFirst().getEventId());
    assertEquals(firstNoticeCode, result.getFirst().getNav());

    assertEquals(dto.getEventId() + "." + secondNoticeCode, result.get(1).getEventId());
    assertEquals(secondNoticeCode, result.get(1).getNav());
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
