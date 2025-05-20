package it.gov.pagopa.pu.registry.mapper.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionEventDTO;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.InstallmentDTO;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentEventType;
import it.gov.pagopa.pu.workflowhub.dto.generated.PaymentOptionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.IntStream;

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
    result.forEach(TestUtils::checkNotNullFields);

    assertEquals(2, result.size());
    assertNotNull(dto.getPayload().getPaymentOptions());
    assertNotNull(dto.getPayload().getPaymentOptions().getFirst().getInstallments());
    assertEquals(dto.getEventType(), result.getFirst().getEventType());
    assertEquals(dto.getTraceId(), result.getFirst().getTraceId());
    assertEquals(dto.getEventDateTime(), result.getFirst().getEventDateTime());
    assertEquals(dto.getEventDescription(), result.getFirst().getEventDescription());
    assertEquals(dto.getPayload().getDebtPositionId(), result.getFirst().getDebtPositionId());
    assertEquals(dto.getPayload().getOrganizationId(), result.getFirst().getOrganizationId());

    IntStream.range(0, dto.getPayload().getPaymentOptions().size()).forEach(i -> {
      PaymentOptionDTO paymentOptionDTO = dto.getPayload().getPaymentOptions().get(i);
      IntStream.range(0, paymentOptionDTO.getInstallments().size()).forEach(j -> {
        InstallmentDTO installmentDTO = paymentOptionDTO.getInstallments().get(j);
        InstallmentRegistry installmentRegistry = result.get(i + j);
        TestUtils.checkNotNullFields(installmentRegistry);
        assertEquals(dto.getEventId() + "." + installmentDTO.getNav(), installmentRegistry.getEventId());
        assertEquals(installmentDTO.getNav(), installmentRegistry.getNav());
        assertEquals(installmentDTO.getUpdateOperatorExternalId(), installmentRegistry.getOperatorExternalUserId());
      });
    });
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
