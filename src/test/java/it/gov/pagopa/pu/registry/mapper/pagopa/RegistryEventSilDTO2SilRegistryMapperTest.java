package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.enums.*;
import it.gov.pagopa.pu.registry.model.SilRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import org.apache.commons.lang3.function.TriFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistryEventSilDTO2SilRegistryMapperTest {

  private DataCipherService dataCipherService;
  private RegistryMappingService mappingService;
  private RegistryEventSilDTO2SilRegistryMapper mapper;
  private final PodamFactory podamFactory = TestUtils.getPodamFactory();

  @BeforeEach
  void setUp() {
    dataCipherService = mock(DataCipherService.class);
    mappingService = mock(RegistryMappingService.class);
    mapper = new RegistryEventSilDTO2SilRegistryMapper(dataCipherService, mappingService);
  }

  @Test
  void givenIuvAndNavEmptyWhenMapThenOk() {
    RegistryEventSilDTO dto = podamFactory.manufacturePojo(RegistryEventSilDTO.class);
    dto.setIuv(null);
    dto.setNav(null);
    dto.setEventType(RegistrySilEventType.paaSILChiediPagatiConRicevuta);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setOutcome(RegistryOutcome.OK);
    dto.setBody("body");

    @SuppressWarnings("unchecked")
    ArgumentCaptor<TriFunction<RegistryEventSilDTO, String, String, SilRegistry>> captor =
      ArgumentCaptor.forClass(TriFunction.class);

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    when(mappingService.mapRegistries(
      eq(dto),
      argThat(f -> f.apply(dto) == null),
      argThat(f -> f.apply(dto) == null),
      captor.capture()
    )).thenAnswer(invocation -> {
      TriFunction<RegistryEventSilDTO, String, String, SilRegistry> builder = captor.getValue();
      SilRegistry built = builder.apply(dto, null, null);
      return List.of(built);
    });

    List<SilRegistry> result = mapper.mapToSilRegistry(dto);

    assertEquals(1, result.size());
    SilRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry, "iuv", "nav");
    assertNull(resultRegistry.getIuv());
    assertNull(resultRegistry.getNav());
  }

  @Test
  void givenIuvAndNavValuedWhenMapThenMappedCorrectly() {
    RegistryEventSilDTO dto = podamFactory.manufacturePojo(RegistryEventSilDTO.class);
    dto.setEventType(RegistrySilEventType.paaSILChiediPagatiConRicevuta);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setOutcome(RegistryOutcome.OK);
    dto.setBody("body");

    @SuppressWarnings("unchecked")
    ArgumentCaptor<TriFunction<RegistryEventSilDTO, String, String, SilRegistry>> captor =
      ArgumentCaptor.forClass(TriFunction.class);

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    when(mappingService.mapRegistries(
      eq(dto),
      argThat(f -> dto.getIuv().equals(f.apply(dto))),
      argThat(f -> dto.getNav().equals(f.apply(dto))),
      captor.capture()
    )).thenAnswer(invocation -> {
      TriFunction<RegistryEventSilDTO, String, String, SilRegistry> builder = captor.getValue();
      SilRegistry built = builder.apply(dto, dto.getIuv(), dto.getNav());
      return List.of(built);
    });

    List<SilRegistry> result = mapper.mapToSilRegistry(dto);

    assertEquals(1, result.size());
    SilRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry);
    assertEquals(dto.getIuv(), resultRegistry.getIuv());
    assertEquals(dto.getNav(), resultRegistry.getNav());
  }

}
