package it.gov.pagopa.pu.registry.mapper.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebtPositionEventDTO2DebtPositionRegistryMapperTest {

  private DebtPositionEventDTO2DebtPositionRegistryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DebtPositionEventDTO2DebtPositionRegistryMapper();
  }

  @Test
  void map_shouldMapCorrectly_whenValidInput() {
    DebtPositionEventDTO dto = TestUtils.getPodamFactory().manufacturePojo(DebtPositionEventDTO.class);

    DebtPositionRegistry result = mapper.map(dto);
    TestUtils.checkNotNullFields(result, "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");

    assertEquals(dto.getEventId(), result.getEventId());
    assertEquals(dto.getEventType(), result.getEventType());
    assertEquals(dto.getTraceId(), result.getTraceId());
    assertEquals(dto.getEventDateTime(), result.getEventDateTime());
    assertEquals(dto.getEventDescription(), result.getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getDebtPositionId());
    assertEquals(dto.getPayload().getUpdateOperatorExternalId(), result.getOperatorExternalUserId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getOrganizationId());
  }

}
