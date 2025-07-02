package it.gov.pagopa.pu.registry.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.pu.registry.config.json.JsonConfig;
import it.gov.pagopa.pu.registry.enums.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RegistryInterfaceEventDTOTest {

  private final Map<String, Class<? extends RegistryInterfaceEventDTO>> enum2ExpectedModel = Map.ofEntries(
    Map.entry(RegistryType.REGISTRY_PAGOPA.name(), RegistryEventPagoPaDTO.class),
    Map.entry(RegistryType.REGISTRY_SIL.name(), RegistryEventSilDTO.class)
  );

  private final ObjectMapper objectMapper = new JsonConfig().objectMapper();

  @Test
  void testSubTypesConfig() {
    JsonSubTypes jsonSubTypes = RegistryInterfaceEventDTO.class.getAnnotation(JsonSubTypes.class);
    Assertions.assertNotNull(jsonSubTypes);

    Map<String, Class<? extends RegistryInterfaceEventDTO>> actualMapping =
      Arrays.stream(jsonSubTypes.value())
        .flatMap(subType -> {
          Stream<String> nameStream = subType.names().length > 0
            ? Arrays.stream(subType.names())
            : Stream.of(subType.name()).filter(StringUtils::isNotBlank);

          return nameStream.map(name ->
            Map.entry(name,
              (Class<? extends RegistryInterfaceEventDTO>) subType.value()));
        })
        .collect(Collectors.toMap(
          Map.Entry::getKey,
          Map.Entry::getValue,
          (existing, replacement) -> existing,
          HashMap::new
        ));

    Assertions.assertEquals(enum2ExpectedModel, actualMapping);
  }

  @Test
  void testSerializationPagoPa() throws JsonProcessingException {
    RegistryEventPagoPaDTO expectedEvent = RegistryEventPagoPaDTO.builder()
      .registryId("registry123")
      .registryOrigin("test-origin")
      .registryType(RegistryType.REGISTRY_PAGOPA)
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .eventType(RegistryPagoPaEventType.PaForNode_paVerifyPaymentNotice)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor123")
      .grantorId("grantor123")
      .brokerStationId("77777777777_01")
      .brokerFiscalCode("77777777777")
      .orgFiscalCode("12345678901")
      .iuv("123456789012345678")
      .nav("3123456789012345678")
      .ccp("T123456789012345")
      .pspId("AGID_01")
      .pspChannelId("88888888888_01")
      .paymentMethod("CP")
      .eventCategory(RegistryEventCategory.INTERFACCIA)
      .outcome(RegistryOutcome.OK)
      .body("{\"test\": \"payload\"}")
      .build();

    String serialized = objectMapper.writeValueAsString(expectedEvent);
    RegistryInterfaceEventDTO result = objectMapper.readValue(serialized, RegistryInterfaceEventDTO.class);

    Assertions.assertEquals(expectedEvent, result);
    Assertions.assertInstanceOf(RegistryEventPagoPaDTO.class, result);
  }

  @Test
  void testSerializationSil() throws JsonProcessingException {
    RegistryEventSilDTO expectedEvent = RegistryEventSilDTO.builder()
      .registryId("registry456")
      .registryOrigin("sil-origin")
      .registryType(RegistryType.REGISTRY_SIL)
      .dateTime(OffsetDateTime.now())
      .traceId("trace456")
      .eventType(RegistrySilEventType.PTDP_paaSILAutorizzaImportFlusso)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor456")
      .grantorId("grantor456")
      .brokerFiscalCode("77777777777")
      .orgFiscalCode("12345678901")
      .iuv("123456789012345678")
      .nav("3123456789012345678")
      .outcome(RegistryOutcome.OK)
      .body("{\"flussoId\": \"FLUSSO123\", \"numeroRiga\": 1}")
      .build();

    String serialized = objectMapper.writeValueAsString(expectedEvent);
    RegistryInterfaceEventDTO result = objectMapper.readValue(serialized, RegistryInterfaceEventDTO.class);

    Assertions.assertEquals(expectedEvent, result);
    Assertions.assertInstanceOf(RegistryEventSilDTO.class, result);
  }

  @Test
  void testDeserializationByEventType() throws JsonProcessingException {
    for (Map.Entry<String, Class<? extends RegistryInterfaceEventDTO>> entry : enum2ExpectedModel.entrySet()) {
      Class<? extends RegistryInterfaceEventDTO> expectedClass = entry.getValue();

      String json;

      if (expectedClass.equals(RegistryEventPagoPaDTO.class)) {
        json = String.format("""
          {
            "registryId": "test-registry",
            "registryType": "%s",
            "dateTime": "2025-01-01T12:00:00Z",
            "traceId": "test-trace",
            "eventType": "paVerifyPaymentNotice",
            "eventSubType": "REQ",
            "requestorId": "test-requestor",
            "grantorId": "test-grantor",
            "orgFiscalCode": "12345678901",
            "eventCategory": "INTERFACCIA",
            "outcome": "OK"
          }
          """, entry.getKey());
      } else {
        json = String.format("""
          {
            "registryId": "test-registry",
            "registryType": "%s",
            "dateTime": "2025-01-01T12:00:00Z",
            "traceId": "test-trace",
            "eventType": "paaSILAutorizzaImportFlusso",
            "eventSubType": "REQ",
            "requestorId": "test-requestor",
            "grantorId": "test-grantor",
            "brokerFiscalCode": "77777777777",
            "orgFiscalCode": "12345678901",
            "outcome": "OK"
          }
          """, entry.getKey());
      }

      RegistryInterfaceEventDTO result = objectMapper.readValue(json, RegistryInterfaceEventDTO.class);

      Assertions.assertInstanceOf(expectedClass, result,
        String.format("Event type %s should deserialize to %s", entry.getKey(), expectedClass.getSimpleName()));
      Assertions.assertEquals(RegistryType.valueOf(entry.getKey()), result.getRegistryType());
    }
  }


  @Test
  void testPagoPaRequiredFields() throws JsonProcessingException {
    RegistryEventPagoPaDTO event = RegistryEventPagoPaDTO.builder()
      .registryId("registry123")
      .registryType(RegistryType.REGISTRY_PAGOPA)
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .eventType(RegistryPagoPaEventType.PaForNode_paVerifyPaymentNotice)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor123")
      .grantorId("grantor123")
      .orgFiscalCode("12345678901")
      .eventCategory(RegistryEventCategory.INTERFACCIA)
      .outcome(RegistryOutcome.OK)
      .build();

    String json = objectMapper.writeValueAsString(event);
    RegistryEventPagoPaDTO result = objectMapper.readValue(json, RegistryEventPagoPaDTO.class);

    Assertions.assertEquals(event.getOrgFiscalCode(), result.getOrgFiscalCode());
    Assertions.assertEquals(event.getEventCategory(), result.getEventCategory());
    Assertions.assertEquals(event.getOutcome(), result.getOutcome());
  }

  @Test
  void testSilRequiredFields() throws JsonProcessingException {
    RegistryEventSilDTO event = RegistryEventSilDTO.builder()
      .registryId("registry456")
      .registryType(RegistryType.REGISTRY_SIL)
      .dateTime(OffsetDateTime.now())
      .traceId("trace456")
      .eventType(RegistrySilEventType.PTDP_paaSILAutorizzaImportFlusso)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor456")
      .grantorId("grantor456")
      .brokerFiscalCode("77777777777")
      .orgFiscalCode("12345678901")
      .outcome(RegistryOutcome.OK)
      .build();

    String json = objectMapper.writeValueAsString(event);
    RegistryEventSilDTO result = objectMapper.readValue(json, RegistryEventSilDTO.class);

    Assertions.assertEquals(event.getBrokerFiscalCode(), result.getBrokerFiscalCode());
    Assertions.assertEquals(event.getOrgFiscalCode(), result.getOrgFiscalCode());
    Assertions.assertEquals(event.getOutcome(), result.getOutcome());
  }

  @Test
  void testPagoPaOptionalFields() throws JsonProcessingException {
    RegistryEventPagoPaDTO event = RegistryEventPagoPaDTO.builder()
      .registryId("registry123")
      .registryOrigin("origin")
      .registryType(RegistryType.REGISTRY_PAGOPA)
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .eventType(RegistryPagoPaEventType.PaForNode_paGetPaymentV2)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor123")
      .grantorId("grantor123")
      .orgFiscalCode("12345678901")
      .eventCategory(RegistryEventCategory.INTERFACCIA)
      .outcome(RegistryOutcome.OK)
      .brokerStationId("77777777777_01")
      .brokerFiscalCode("77777777777")
      .iuv("123456789012345678")
      .nav("3123456789012345678")
      .ccp("T123456789012345")
      .pspId("AGID_01")
      .pspChannelId("88888888888_01")
      .paymentMethod("CP")
      .body("{\"test\": \"data\"}")
      .build();

    String json = objectMapper.writeValueAsString(event);
    RegistryEventPagoPaDTO result = objectMapper.readValue(json, RegistryEventPagoPaDTO.class);

    Assertions.assertEquals(event, result);
    Assertions.assertEquals("77777777777_01", result.getBrokerStationId());
    Assertions.assertEquals("AGID_01", result.getPspId());
    Assertions.assertEquals("CP", result.getPaymentMethod());
  }

  @Test
  void testSilOptionalFields() throws JsonProcessingException {
    RegistryEventSilDTO event = RegistryEventSilDTO.builder()
      .registryId("registry456")
      .registryOrigin("sil-origin")
      .registryType(RegistryType.REGISTRY_SIL)
      .dateTime(OffsetDateTime.now())
      .traceId("trace456")
      .eventType(RegistrySilEventType.PTPR_pivotSILAutorizzaImportFlusso)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor456")
      .grantorId("grantor456")
      .brokerFiscalCode("77777777777")
      .orgFiscalCode("12345678901")
      .outcome(RegistryOutcome.OK)
      .iuv("123456789012345678")
      .nav("3123456789012345678")
      .body("{\"flussoId\": \"FLUSSO123\", \"stato\": \"AUTORIZZATO\"}")
      .build();

    String json = objectMapper.writeValueAsString(event);
    RegistryEventSilDTO result = objectMapper.readValue(json, RegistryEventSilDTO.class);

    Assertions.assertEquals(event, result);
    Assertions.assertEquals("123456789012345678", result.getIuv());
    Assertions.assertEquals("3123456789012345678", result.getNav());
  }

}
