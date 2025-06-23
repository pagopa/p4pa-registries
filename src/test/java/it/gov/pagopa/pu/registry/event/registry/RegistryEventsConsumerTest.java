package it.gov.pagopa.pu.registry.event.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.gov.pagopa.pu.registry.config.json.JsonConfig;
import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.dto.RegistryInterfaceEventDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryPagopaEventType;
import it.gov.pagopa.pu.registry.enums.RegistrySilEventType;
import it.gov.pagopa.pu.registry.enums.RegistryType;
import it.gov.pagopa.pu.registry.service.pagopa.PagoPaRegistryService;
import it.gov.pagopa.pu.registry.service.sil.SilRegistryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RegistryEventsConsumerTest {

  private final ObjectMapper objectMapper = new JsonConfig().objectMapper();

  @Mock
  private PagoPaRegistryService pagoPaRegistryService;
  @Mock
  private SilRegistryService silRegistryService;

  private RegistryEventsConsumer consumer;

  @BeforeEach
  void setUp() {
    consumer = new RegistryEventsConsumer(
        pagoPaRegistryService,
        silRegistryService
    );
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(pagoPaRegistryService, silRegistryService);
  }

  @Test
  void whenAcceptPagoPaRegistryEventThenPagoPaRegistryServiceIsCalled() throws JsonProcessingException {
    // Given
    RegistryEventPagoPaDTO pagoPaEventDTO = new RegistryEventPagoPaDTO();
    pagoPaEventDTO.setRegistryId(UUID.randomUUID().toString());
    pagoPaEventDTO.setRegistryOrigin("test-origin");
    pagoPaEventDTO.setRegistryType(RegistryType.REGISTRY_PAGOPA);
    pagoPaEventDTO.setTraceId(UUID.randomUUID().toString());
    pagoPaEventDTO.setBrokerStationId("broker-station-id");
    pagoPaEventDTO.setEventType(RegistryPagopaEventType.paSendRTV2);
    pagoPaEventDTO.setEventSubType(RegistryEventSubType.REQ);
    pagoPaEventDTO.setBody("{}");

    String json = objectMapper.writeValueAsString(pagoPaEventDTO);
    RegistryInterfaceEventDTO eventDTO = objectMapper.readValue(json, RegistryInterfaceEventDTO.class);

    // When
    consumer.accept(eventDTO);

    // Then
    ArgumentCaptor<RegistryEventPagoPaDTO> captor = ArgumentCaptor.forClass(RegistryEventPagoPaDTO.class);
    Mockito.verify(pagoPaRegistryService).consumePaymentEvent(captor.capture());
    RegistryEventPagoPaDTO capturedEvent = captor.getValue();
    assertEquals(pagoPaEventDTO.getRegistryId(), capturedEvent.getRegistryId());
    assertEquals(pagoPaEventDTO.getRegistryOrigin(), capturedEvent.getRegistryOrigin());
    assertEquals(pagoPaEventDTO.getRegistryType(), capturedEvent.getRegistryType());
    assertEquals(pagoPaEventDTO.getTraceId(), capturedEvent.getTraceId());
    assertEquals(pagoPaEventDTO.getBrokerStationId(), capturedEvent.getBrokerStationId());
    assertEquals(pagoPaEventDTO.getEventType(), capturedEvent.getEventType());
    assertEquals(pagoPaEventDTO.getEventSubType(), capturedEvent.getEventSubType());
    Mockito.verify(silRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
  }

  @Test
  void whenAcceptSilRegistryEventThenSilRegistryServiceIsCalled() throws JsonProcessingException {
    // Given
    RegistryEventSilDTO silEventDTO = new RegistryEventSilDTO();
    silEventDTO.setRegistryId(UUID.randomUUID().toString());
    silEventDTO.setRegistryOrigin("test-origin");
    silEventDTO.setRegistryType(RegistryType.REGISTRY_SIL);
    silEventDTO.setTraceId(UUID.randomUUID().toString());
    silEventDTO.setEventType(RegistrySilEventType.paaSILAutorizzaImportFlusso);
    silEventDTO.setEventSubType(RegistryEventSubType.REQ);
    silEventDTO.setBrokerFiscalCode("broker-fiscal-code");
    silEventDTO.setBody("{}");

    String json = objectMapper.writeValueAsString(silEventDTO);
    RegistryInterfaceEventDTO eventDTO = objectMapper.readValue(json, RegistryInterfaceEventDTO.class);

    // When
    consumer.accept(eventDTO);

    // Then
    ArgumentCaptor<RegistryEventSilDTO> captor = ArgumentCaptor.forClass(RegistryEventSilDTO.class);
    Mockito.verify(silRegistryService).consumePaymentEvent(captor.capture());
    RegistryEventSilDTO capturedEvent = captor.getValue();
    assertEquals(silEventDTO.getRegistryId(), capturedEvent.getRegistryId());
    assertEquals(silEventDTO.getRegistryOrigin(), capturedEvent.getRegistryOrigin());
    assertEquals(silEventDTO.getRegistryType(), capturedEvent.getRegistryType());
    assertEquals(silEventDTO.getTraceId(), capturedEvent.getTraceId());
    assertEquals(silEventDTO.getBrokerFiscalCode(), capturedEvent.getBrokerFiscalCode());
    assertEquals(silEventDTO.getEventType(), capturedEvent.getEventType());
    assertEquals(silEventDTO.getEventSubType(), capturedEvent.getEventSubType());
    Mockito.verify(pagoPaRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
  }

  @Test
  void whenAcceptUnknownRegistryEventThenNoServiceIsCalled() {
    // Given
    RegistryInterfaceEventDTO eventDTO = new RegistryInterfaceEventDTO();
    eventDTO.setRegistryId(UUID.randomUUID().toString());
    eventDTO.setRegistryOrigin("test-origin");
    eventDTO.setRegistryType(null);
    eventDTO.setTraceId(UUID.randomUUID().toString());

    // When
    consumer.accept(eventDTO);

    // Then
    Mockito.verify(pagoPaRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
    Mockito.verify(silRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
  }

  @Test
  void whenPagoPaRegistryServiceThrowsErrorThenItDoesntFail() throws JsonProcessingException {
    // Given
    RegistryEventPagoPaDTO pagoPaEventDTO = new RegistryEventPagoPaDTO();
    pagoPaEventDTO.setRegistryId(UUID.randomUUID().toString());
    pagoPaEventDTO.setRegistryOrigin("test-origin");
    pagoPaEventDTO.setRegistryType(RegistryType.REGISTRY_PAGOPA);
    pagoPaEventDTO.setTraceId(UUID.randomUUID().toString());
    pagoPaEventDTO.setBrokerStationId("broker-station-id");
    pagoPaEventDTO.setEventType(RegistryPagopaEventType.paSendRTV2);
    pagoPaEventDTO.setEventSubType(RegistryEventSubType.REQ);
    pagoPaEventDTO.setBody("{}");

    String json = objectMapper.writeValueAsString(pagoPaEventDTO);
    RegistryInterfaceEventDTO eventDTO = objectMapper.readValue(json, RegistryInterfaceEventDTO.class);

    Mockito.doThrow(RuntimeException.class).when(pagoPaRegistryService).consumePaymentEvent(Mockito.any(RegistryEventPagoPaDTO.class));

    // Then
    assertDoesNotThrow(() -> consumer.accept(eventDTO));
    Mockito.verify(silRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
  }


}
