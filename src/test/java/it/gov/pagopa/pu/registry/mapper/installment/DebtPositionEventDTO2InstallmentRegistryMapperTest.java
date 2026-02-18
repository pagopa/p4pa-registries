package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import it.gov.pagopa.pu.workflowhub.dto.generated.InstallmentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DebtPositionEventDTO2InstallmentRegistryMapperTest {

  private DebtPositionEventDTO2InstallmentRegistryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DebtPositionEventDTO2InstallmentRegistryMapper();
  }

  @Test
  void map_shouldMapCorrectly_whenValidInput() {
    DebtPositionEventDTO dto = TestUtils.getPodamFactory().manufacturePojo(DebtPositionEventDTO.class);
    List<InstallmentRegistry> result = mapper.map(dto);
    result.forEach(r -> TestUtils.checkNotNullFields(r , "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId"));

    assertEquals(25, result.size());
    assertNotNull(dto.getPayload().getPaymentOptions());
    assertNotNull(dto.getPayload().getPaymentOptions().getFirst().getInstallments());
    assertEquals(dto.getEventType(), result.getFirst().getEventType());
    assertEquals(dto.getTraceId(), result.getFirst().getTraceId());
    assertEquals(dto.getEventDateTime(), result.getFirst().getEventDateTime());
    assertEquals(dto.getEventDescription(), result.getFirst().getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getFirst().getDebtPositionId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getFirst().getOrganizationId());

    InstallmentDTO firstInstallmentDTO = dto.getPayload().getPaymentOptions().getFirst().getInstallments().get(0);
    InstallmentDTO secondInstallmentDTO = dto.getPayload().getPaymentOptions().getFirst().getInstallments().get(1);
    TestUtils.checkNotNullFields(result.get(0), "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");
    TestUtils.checkNotNullFields(result.get(1), "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");
    assertEquals(dto.getEventId() + "." + firstInstallmentDTO.getNav(), result.get(0).getEventId());
    assertEquals(firstInstallmentDTO.getNav(), result.get(0).getNav());
    assertEquals(firstInstallmentDTO.getUpdateOperatorExternalId(), result.get(0).getOperatorExternalUserId());

    assertEquals(dto.getEventId() + "." + secondInstallmentDTO.getNav(), result.get(1).getEventId());
    assertEquals(secondInstallmentDTO.getNav(), result.get(1).getNav());
    assertEquals(secondInstallmentDTO.getUpdateOperatorExternalId(), result.get(1).getOperatorExternalUserId());
  }

}
