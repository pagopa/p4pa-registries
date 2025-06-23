package it.gov.pagopa.pu.registry.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.pu.registry.config.json.JsonConfig;
import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryEventType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.gov.pagopa.pu.registry.utils.Constants.SIL_UNIT;

class RegistryEventDTOTest {

  private final Map<RegistryEventType, Class<? extends RegistryEventDTO>> enum2ExpectedModel = Map.ofEntries(
    // PagoPA events
    Map.entry(RegistryEventType.paVerifyPaymentNotice, RegistryEventPagoPaDTO.class),
    Map.entry(RegistryEventType.paGetPaymentV2, RegistryEventPagoPaDTO.class),
    Map.entry(RegistryEventType.paSendRTV2, RegistryEventPagoPaDTO.class),
    Map.entry(RegistryEventType.newDebtPosition, RegistryEventPagoPaDTO.class),
    Map.entry(RegistryEventType.createPosition, RegistryEventPagoPaDTO.class),
    Map.entry(RegistryEventType.updatePosition, RegistryEventPagoPaDTO.class),
    Map.entry(RegistryEventType.deletePosition, RegistryEventPagoPaDTO.class),
    Map.entry(RegistryEventType.fetchPaymentReporting, RegistryEventPagoPaDTO.class),

    // SIL events - paaSIL operations
    Map.entry(RegistryEventType.paaSILAutorizzaImportFlusso, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILChiediStatoImportFlusso, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILImportaDovuto, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILPrenotaExportFlusso, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILPrenotaExportFlussoIncrementaleConRicevuta, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILChiediStatoExportFlusso, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILInviaDovuti, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILVerificaAvviso, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILChiediPagati, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILChiediPagatiConRicevuta, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILInviaCarrelloDovuti, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.paaSILChiediEsitoCarrelloDovuti, RegistryEventSilDTO.class),

    // SIL events - pivotSIL operations
    Map.entry(RegistryEventType.pivotSILAutorizzaImportFlusso, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.pivotSILChiediStatoImportFlusso, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.pivotSILAutorizzaImportFlussoTesoreria, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.pivotSILChiediStatoImportFlussoTesoreria, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.pivotSILPrenotaExportFlussoRiconciliazione, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.pivotSILChiediStatoExportFlussoRiconciliazione, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.pivotSILChiediAccertamento, RegistryEventSilDTO.class),

    // SIL events - other operations
    Map.entry(RegistryEventType.attualizzazioneImporti, RegistryEventSilDTO.class),
    Map.entry(RegistryEventType.notificaPagamento, RegistryEventSilDTO.class)
  );

  private final ObjectMapper objectMapper = new JsonConfig().objectMapper();

  @Test
  void testExpectedMapIsCompleted() {
    Assertions.assertEquals(
      Arrays.stream(RegistryEventType.values()).collect(Collectors.toSet()),
      enum2ExpectedModel.keySet()
    );
  }

  @Test
  void testSubTypesConfig() {
    JsonSubTypes jsonSubTypes = RegistryEventDTO.class.getAnnotation(JsonSubTypes.class);
    Assertions.assertNotNull(jsonSubTypes);

    Map<RegistryEventType, Class<? extends RegistryEventDTO>> actualMapping =
      Arrays.stream(jsonSubTypes.value())
        .flatMap(subType -> {
          Stream<String> nameStream = subType.names().length > 0
            ? Arrays.stream(subType.names())
            : Stream.of(subType.name()).filter(StringUtils::isNotBlank);

          return nameStream.map(name ->
            Map.entry(RegistryEventType.valueOf(name),
              (Class<? extends RegistryEventDTO>) subType.value()));
        })
        .collect(Collectors.toMap(
          Map.Entry::getKey,
          Map.Entry::getValue,
          (existing, replacement) -> existing,
          () -> new EnumMap<>(RegistryEventType.class)
        ));

    Assertions.assertEquals(enum2ExpectedModel, actualMapping);
  }

  @Test
  void testSerializationPagoPa() throws JsonProcessingException {
    RegistryEventPagoPaDTO expectedEvent = RegistryEventPagoPaDTO.builder()
      .registryId("registry123")
      .registryOrigin("test-origin")
      .registryType("PAGOPA")
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .eventType(RegistryEventType.paVerifyPaymentNotice)
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
    RegistryEventDTO result = objectMapper.readValue(serialized, RegistryEventDTO.class);

    Assertions.assertEquals(expectedEvent, result);
    Assertions.assertInstanceOf(RegistryEventPagoPaDTO.class, result);
  }

  @Test
  void testSerializationSil() throws JsonProcessingException {
    RegistryEventSilDTO expectedEvent = RegistryEventSilDTO.builder()
      .registryId("registry456")
      .registryOrigin("sil-origin")
      .registryType("SIL")
      .dateTime(OffsetDateTime.now())
      .traceId("trace456")
      .eventType(RegistryEventType.paaSILAutorizzaImportFlusso)
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
    RegistryEventDTO result = objectMapper.readValue(serialized, RegistryEventDTO.class);

    Assertions.assertEquals(expectedEvent, result);
    Assertions.assertInstanceOf(RegistryEventSilDTO.class, result);
  }

  @Test
  void testDeserializationByEventType() throws JsonProcessingException {
    for (Map.Entry<RegistryEventType, Class<? extends RegistryEventDTO>> entry : enum2ExpectedModel.entrySet()) {
      RegistryEventType eventType = entry.getKey();
      Class<? extends RegistryEventDTO> expectedClass = entry.getValue();

      String json;

      if (expectedClass.equals(RegistryEventPagoPaDTO.class)) {
        json = String.format("""
          {
            "registryId": "test-registry",
            "dateTime": "2025-01-01T12:00:00Z",
            "traceId": "test-trace",
            "eventType": "%s",
            "eventSubType": "REQ",
            "requestorId": "test-requestor",
            "grantorId": "test-grantor",
            "orgFiscalCode": "12345678901",
            "eventCategory": "INTERFACCIA",
            "outcome": "OK"
          }
          """, eventType.name());
      } else {
        json = String.format("""
          {
            "registryId": "test-registry",
            "dateTime": "2025-01-01T12:00:00Z",
            "traceId": "test-trace",
            "eventType": "%s",
            "eventSubType": "REQ",
            "requestorId": "test-requestor",
            "grantorId": "test-grantor",
            "brokerFiscalCode": "77777777777",
            "orgFiscalCode": "12345678901",
            "outcome": "OK"
          }
          """, eventType.name());
      }

      RegistryEventDTO result = objectMapper.readValue(json, RegistryEventDTO.class);

      Assertions.assertInstanceOf(expectedClass, result,
        String.format("Event type %s should deserialize to %s", eventType, expectedClass.getSimpleName()));
      Assertions.assertEquals(eventType, result.getEventType());
    }
  }

  @Test
  void testPagoPaEventTypesMapping() {
    Set<RegistryEventType> pagoPaEventTypes = enum2ExpectedModel.entrySet().stream()
      .filter(entry -> entry.getValue().equals(RegistryEventPagoPaDTO.class))
      .map(Map.Entry::getKey)
      .collect(Collectors.toSet());

    Set<RegistryEventType> expectedPagoPaTypes = Set.of(
      RegistryEventType.paVerifyPaymentNotice,
      RegistryEventType.paGetPaymentV2,
      RegistryEventType.paSendRTV2,
      RegistryEventType.newDebtPosition,
      RegistryEventType.createPosition,
      RegistryEventType.updatePosition,
      RegistryEventType.deletePosition,
      RegistryEventType.fetchPaymentReporting
    );

    Assertions.assertEquals(expectedPagoPaTypes, pagoPaEventTypes);
  }

  @Test
  void testSilEventTypesMapping() {
    Set<RegistryEventType> silEventTypes = enum2ExpectedModel.entrySet().stream()
      .filter(entry -> entry.getValue().equals(RegistryEventSilDTO.class))
      .map(Map.Entry::getKey)
      .collect(Collectors.toSet());

    Set<RegistryEventType> expectedSilTypes = Arrays.stream(RegistryEventType.values())
      .filter(eventType -> SIL_UNIT.equals(eventType.getUnit()))
      .collect(Collectors.toSet());

    Assertions.assertEquals(expectedSilTypes, silEventTypes);
  }

  @Test
  void testPagoPaRequiredFields() throws JsonProcessingException {
    RegistryEventPagoPaDTO event = RegistryEventPagoPaDTO.builder()
      .registryId("registry123")
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .eventType(RegistryEventType.paVerifyPaymentNotice)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor123")
      .grantorId("grantor123")
      .orgFiscalCode("12345678901")
      .eventCategory(RegistryEventCategory.INTERFACCIA)
      .outcome(RegistryOutcome.OK)
      .build();

    String json = objectMapper.writeValueAsString(event);
    RegistryEventPagoPaDTO result = (RegistryEventPagoPaDTO) objectMapper.readValue(json, RegistryEventDTO.class);

    Assertions.assertEquals(event.getOrgFiscalCode(), result.getOrgFiscalCode());
    Assertions.assertEquals(event.getEventCategory(), result.getEventCategory());
    Assertions.assertEquals(event.getOutcome(), result.getOutcome());
  }

  @Test
  void testSilRequiredFields() throws JsonProcessingException {
    RegistryEventSilDTO event = RegistryEventSilDTO.builder()
      .registryId("registry456")
      .dateTime(OffsetDateTime.now())
      .traceId("trace456")
      .eventType(RegistryEventType.paaSILAutorizzaImportFlusso)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestor456")
      .grantorId("grantor456")
      .brokerFiscalCode("77777777777")
      .orgFiscalCode("12345678901")
      .outcome(RegistryOutcome.OK)
      .build();

    String json = objectMapper.writeValueAsString(event);
    RegistryEventSilDTO result = (RegistryEventSilDTO) objectMapper.readValue(json, RegistryEventDTO.class);

    Assertions.assertEquals(event.getBrokerFiscalCode(), result.getBrokerFiscalCode());
    Assertions.assertEquals(event.getOrgFiscalCode(), result.getOrgFiscalCode());
    Assertions.assertEquals(event.getOutcome(), result.getOutcome());
  }

  @Test
  void testPagoPaOptionalFields() throws JsonProcessingException {
    RegistryEventPagoPaDTO event = RegistryEventPagoPaDTO.builder()
      .registryId("registry123")
      .registryOrigin("origin")
      .registryType("PAGOPA")
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .eventType(RegistryEventType.paGetPaymentV2)
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
    RegistryEventPagoPaDTO result = (RegistryEventPagoPaDTO) objectMapper.readValue(json, RegistryEventDTO.class);

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
      .registryType("SIL")
      .dateTime(OffsetDateTime.now())
      .traceId("trace456")
      .eventType(RegistryEventType.pivotSILAutorizzaImportFlusso)
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
    RegistryEventSilDTO result = (RegistryEventSilDTO) objectMapper.readValue(json, RegistryEventDTO.class);

    Assertions.assertEquals(event, result);
    Assertions.assertEquals("123456789012345678", result.getIuv());
    Assertions.assertEquals("3123456789012345678", result.getNav());
  }

}
