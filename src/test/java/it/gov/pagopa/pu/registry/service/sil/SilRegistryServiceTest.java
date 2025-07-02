package it.gov.pagopa.pu.registry.service.sil;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.dto.generated.SilRegistryDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistrySilEventType;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventSilDTO2SilRegistryMapper;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.repository.SilRegistryRepository;
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
class SilRegistryServiceTest {
  @Mock
  private SilRegistryRepository repositoryMock;
  @Mock
  private RegistryEventSilDTO2SilRegistryMapper mapperServiceMock;
  @Mock
  private DataCipherService dataCipherServiceMock;

  private SilRegistryService service;

  @BeforeEach
  void setUp() {
    this.service =new SilRegistryService(repositoryMock, mapperServiceMock, dataCipherServiceMock);
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(mapperServiceMock, repositoryMock, dataCipherServiceMock);
  }

  @Test
  void whenConsumePaymentEventThenMapperAndRepositoryAreInvoked() {
    // Given
    List<SilRegistry> registries = List.of(
      new SilRegistry(),
      new SilRegistry()
    );

    Mockito.when(mapperServiceMock.map(Mockito.any(RegistryEventSilDTO.class))).thenReturn(registries);

    // When
    this.service.consumePaymentEvent(Mockito.mock(RegistryEventSilDTO.class));

    // Then
    Mockito.verify(mapperServiceMock, Mockito.times(1))
      .map(Mockito.any(RegistryEventSilDTO.class));
    Mockito.verify(repositoryMock, Mockito.times(1))
      .saveAll(Mockito.any());
  }

  @Test
  void shouldReturnRegistryDTOWhenRegistryExists() {
    // Given
    SilRegistry testEntity = getMockedSilRegistry(UUID.randomUUID().toString());
    testEntity.setBodyCiphered("test".getBytes(StandardCharsets.UTF_8));

    Mockito.when(repositoryMock.findById(testEntity.getRegistryId()))
      .thenReturn(Optional.of(testEntity));
    Mockito.when(dataCipherServiceMock.decrypt(testEntity.getBodyCiphered()))
      .thenReturn("test");

    // When
    SilRegistryDTO result = service.getSilRegistry(testEntity.getRegistryId());

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getRegistryId()).isEqualTo(testEntity.getRegistryId());
    assertThat(result.getDateTime()).isEqualTo(testEntity.getDateTime());
    assertThat(result.getTraceId()).isEqualTo(testEntity.getTraceId());
    assertThat(result.getBrokerFiscalCode()).isEqualTo(testEntity.getBrokerFiscalCode());
    assertThat(result.getOrgFiscalCode()).isEqualTo(testEntity.getOrgFiscalCode());
    assertThat(result.getIuv()).isEqualTo(testEntity.getIuv());
    assertThat(result.getNav()).isEqualTo(testEntity.getNav());
    assertThat(result.getEventType()).isEqualTo(testEntity.getEventType());
    assertThat(result.getEventSubType()).isEqualTo(testEntity.getEventSubType());
    assertThat(result.getRequestorId()).isEqualTo(testEntity.getRequestorId());
    assertThat(result.getGrantorId()).isEqualTo(testEntity.getGrantorId());
    assertThat(result.getOutcome()).isEqualTo(testEntity.getOutcome());
    assertThat(result.getBody()).isEqualTo("test");

    Mockito.verify(repositoryMock, Mockito.times(1)).findById(testEntity.getRegistryId());
    Mockito.verify(dataCipherServiceMock, Mockito.times(1)).decrypt(testEntity.getBodyCiphered());
  }

  @Test
  void shouldThrowWhenRegistryDoesNotExists() {
    // Given
    Mockito.when(repositoryMock.findById("id123"))
      .thenReturn(Optional.empty());

    // Then
    assertThrows(ResourceNotFoundException.class, () -> {
      service.getSilRegistry("id123");
    });
    Mockito.verify(repositoryMock, Mockito.times(1)).findById("id123");
    Mockito.verify(dataCipherServiceMock, Mockito.never()).decrypt(any());
  }

  private SilRegistry getMockedSilRegistry(String id) {
    return SilRegistry.builder()
      .registryId(id)
      .dateTime(OffsetDateTime.now())
      .traceId("trace123")
      .brokerFiscalCode("brokerFiscalCode123")
      .orgFiscalCode("fiscalCode123")
      .iuv("iuv123")
      .nav("nav123")
      .eventType(RegistrySilEventType.PTDP_paaSILAutorizzaImportFlusso)
      .eventSubType(RegistryEventSubType.REQ)
      .requestorId("requestorId123")
      .grantorId("grantorId123")
      .outcome(RegistryOutcome.OK)
      .bodyCiphered(null)
      .build();
  }
}
