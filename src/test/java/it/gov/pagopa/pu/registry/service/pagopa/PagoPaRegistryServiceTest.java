package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.dto.generated.PagoPaRegistryDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagoPaEventType;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventPagoPaDTO2PagoPaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import it.gov.pagopa.pu.registry.repository.PagoPaRegistryRepository;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PagoPaRegistryServiceTest {
  @Mock
  private PagoPaRegistryRepository repository;
  @Mock
  private RegistryEventPagoPaDTO2PagoPaRegistryMapper mapper;
  @Mock
  private DataCipherService dataCipherService;

  private PagoPaRegistryService service;

  @BeforeEach
  void setUp() {
    this.service = new PagoPaRegistryService(repository, mapper, dataCipherService);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(mapper, repository, dataCipherService);
  }

  @Test
  void whenConsumePaymentEventThenMapperAndRepositoryAreInvoked() {
    // Given
    List<PagoPaRegistry> pagopaRegistries = List.of(
      new PagoPaRegistry(),
      new PagoPaRegistry()
    );

    Mockito.when(mapper.map(any(RegistryEventPagoPaDTO.class))).thenReturn(pagopaRegistries);

    // When
    this.service.consumePaymentEvent(Mockito.mock(RegistryEventPagoPaDTO.class));

    // Then
    Mockito.verify(mapper, Mockito.times(1))
      .map(any(RegistryEventPagoPaDTO.class));
    Mockito.verify(repository, Mockito.times(1))
      .saveAll(any());
  }

  @Test
  void shouldReturnRegistryDTOWhenRegistryExists() {
    // Given
    PagoPaRegistry testEntity = getMockedPagoPaRegistry(UUID.randomUUID().toString());
    testEntity.setBodyCiphered("test".getBytes(StandardCharsets.UTF_8));

    Mockito.when(repository.findById(testEntity.getRegistryId()))
      .thenReturn(Optional.of(testEntity));
    Mockito.when(dataCipherService.decrypt(testEntity.getBodyCiphered()))
      .thenReturn("test");

    // When
    PagoPaRegistryDTO result = service.getPagoPaRegistry(testEntity.getRegistryId());

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getRegistryId()).isEqualTo(testEntity.getRegistryId());
    assertThat(result.getDateTime()).isEqualTo(testEntity.getDateTime());
    assertThat(result.getTraceId()).isEqualTo(testEntity.getTraceId());
    assertThat(result.getBrokerStationId()).isEqualTo(testEntity.getBrokerStationId());
    assertThat(result.getOrgFiscalCode()).isEqualTo(testEntity.getOrgFiscalCode());
    assertThat(result.getIuv()).isEqualTo(testEntity.getIuv());
    assertThat(result.getNav()).isEqualTo(testEntity.getNav());
    assertThat(result.getCcp()).isEqualTo(testEntity.getCcp());
    assertThat(result.getPspId()).isEqualTo(testEntity.getPspId());
    assertThat(result.getPspChannelId()).isEqualTo(testEntity.getPspChannelId());
    assertThat(result.getPaymentMethod()).isEqualTo(testEntity.getPaymentMethod());
    assertThat(result.getEventCategory()).isEqualTo(testEntity.getEventCategory());
    assertThat(result.getEventType()).isEqualTo(testEntity.getEventType());
    assertThat(result.getEventSubType()).isEqualTo(testEntity.getEventSubType());
    assertThat(result.getRequestorId()).isEqualTo(testEntity.getRequestorId());
    assertThat(result.getGrantorId()).isEqualTo(testEntity.getGrantorId());
    assertThat(result.getOutcome()).isEqualTo(testEntity.getOutcome());
    assertThat(result.getBody()).isEqualTo("test");

    Mockito.verify(repository, Mockito.times(1)).findById(testEntity.getRegistryId());
    Mockito.verify(dataCipherService, Mockito.times(1)).decrypt(testEntity.getBodyCiphered());
  }

  @Test
  void shouldThrowWhenRegistryDoesNotExists() {
    // Given
    Mockito.when(repository.findById("id123"))
      .thenReturn(Optional.empty());

    // Then
    assertThrows(ResourceNotFoundException.class, () -> {
      service.getPagoPaRegistry("id123");
    });
    Mockito.verify(repository, Mockito.times(1)).findById("id123");
    Mockito.verify(dataCipherService, Mockito.never()).decrypt(any());
  }

  private PagoPaRegistry getMockedPagoPaRegistry(String id) {
    return PagoPaRegistry.builder()
      .registryId(id)
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .brokerStationId("station123")
      .orgFiscalCode("fiscalCode123")
      .iuv("iuv123")
      .nav("nav123")
      .ccp("ccp123")
      .pspId("pspId123")
      .pspChannelId("pspChannelId123")
      .paymentMethod("paymentMethod123")
      .eventCategory(RegistryEventCategory.INTERFACCIA)
      .eventType(RegistryPagoPaEventType.paSendRTV2)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestorId123")
      .grantorId("grantorId123")
      .outcome(RegistryOutcome.OK)
      .bodyCiphered(null)
      .build();
  }
}
