package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.InstallmentDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentOptionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DebtPositionEventDTO2InstallmentRegistryMapperTest {

  private DebtPositionEventDTO2InstallmentRegistryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new DebtPositionEventDTO2InstallmentRegistryMapper();
  }

  @Test
  void map_shouldMapCorrectly_whenValidInput() {
    DebtPositionEventDTO dto = createValidDto();
    List<InstallmentRegistry> result = mapper.map(dto);

    assertEquals(2, result.size());
    assertNotNull(dto.getPayload().getPaymentOptions());
    assertNotNull(dto.getPayload().getPaymentOptions().getFirst().getInstallments());

    InstallmentDTO firstInstallment = dto.getPayload().getPaymentOptions().getFirst().getInstallments().getFirst();

    assertEquals(dto.getEventId() + "." + firstInstallment.getNav(), result.getFirst().getEventId());
    assertEquals(dto.getEventType(), result.getFirst().getEventType());
    assertEquals(dto.getTraceId(), result.getFirst().getTraceId());
    assertEquals(dto.getEventDateTime(), result.getFirst().getEventDateTime());
    assertEquals(dto.getEventDescription(), result.getFirst().getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getFirst().getDebtPositionId());
    assertEquals(firstInstallment.getUpdateOperatorExternalId(), result.getFirst().getOperatorExternalUserId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getFirst().getOrganizationId());
  }

  @Test
  void map_shouldFailMapping_whenNullValues() {
    final var ref = new Object() {
      DebtPositionEventDTO dto = createValidDto();
    };

    // validate eventId
    ref.dto.setEventId(null);
    assertThrows(NullPointerException.class, () -> mapper.map(ref.dto));

    // validate eventType
    ref.dto = createValidDto();
    ref.dto.setEventType(null);
    assertThrows(NullPointerException.class, () -> mapper.map(ref.dto));

    // validate traceId
    ref.dto = createValidDto();
    ref.dto.setTraceId(null);
    assertThrows(NullPointerException.class, () -> mapper.map(ref.dto));

    // validate event datetime
    ref.dto = createValidDto();
    ref.dto.setEventDateTime(null);
    assertThrows(NullPointerException.class, () -> mapper.map(ref.dto));

    // validate payload
    ref.dto = createValidDto();
    ref.dto.setPayload(null);
    assertThrows(NullPointerException.class, () -> mapper.map(ref.dto));

    // validate payload debt position id
    ref.dto = createValidDto();
    ref.dto.getPayload().setDebtPositionId(null);
    assertThrows(NullPointerException.class, () -> mapper.map(ref.dto));

    // validate payload installment operator external id
    ref.dto = createValidDto();
    ref.dto.getPayload().getPaymentOptions().getFirst().getInstallments().getFirst().setUpdateOperatorExternalId(null);
    assertEquals(1, mapper.map(ref.dto).size());

    // validate payload installment nav
    ref.dto = createValidDto();
    ref.dto.getPayload().getPaymentOptions().getFirst().getInstallments().getFirst().setNav(null);
    assertEquals(1, mapper.map(ref.dto).size());

    // validate payload organization id
    ref.dto = createValidDto();
    ref.dto.getPayload().setOrganizationId(null);
    assertThrows(NullPointerException.class, () -> mapper.map(ref.dto));
  }

  private DebtPositionEventDTO createValidDto() {
    List<InstallmentDTO> installments = List.of(
      InstallmentDTO.builder()
        .nav("nav1")
        .updateOperatorExternalId("operatorExternalId123")
        .build(),
      InstallmentDTO.builder()
        .nav("nav2")
        .updateOperatorExternalId("operatorExternalId456")
        .build()
    );

    List<PaymentOptionDTO> paymentOptions = List.of(
      PaymentOptionDTO.builder()
        .installments(installments)
        .build()
    );

    DebtPositionDTO payload = DebtPositionDTO.builder()
      .debtPositionId(123L)
      .updateOperatorExternalId("externalOperatorId456")
      .organizationId(789L)
      .paymentOptions(paymentOptions)
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
