package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagopaEventType;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
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

class RegistryEventPagoPaDTO2PagoPaRegistryMapperTest {

  private DataCipherService dataCipherService;
  private RegistryMappingService mappingService;
  private RegistryEventPagoPaDTO2PagoPaRegistryMapper mapper;
  private final PodamFactory podamFactory = TestUtils.getPodamFactory();

  @BeforeEach
  void setUp() {
    dataCipherService = mock(DataCipherService.class);
    mappingService = mock(RegistryMappingService.class);
    mapper = new RegistryEventPagoPaDTO2PagoPaRegistryMapper(dataCipherService, mappingService);
  }

  @Test
  void givenIuvAndNavEmptyWhenMapThenOk() {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv(null);
    dto.setNav(null);
    dto.setEventCategory(RegistryEventCategory.INTERFACCIA);
    dto.setEventType(RegistryPagopaEventType.paGetPaymentV2);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setOutcome(RegistryOutcome.OK);
    dto.setBody("body");

    @SuppressWarnings("unchecked")
    ArgumentCaptor<TriFunction<RegistryEventPagoPaDTO, String, String, PagopaRegistry>> captor =
      ArgumentCaptor.forClass(TriFunction.class);

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    when(mappingService.mapRegistries(
      eq(dto),
      argThat(f -> f.apply(dto) == null),
      argThat(f -> f.apply(dto) == null),
      captor.capture()
    )).thenAnswer(invocation -> {
      TriFunction<RegistryEventPagoPaDTO, String, String, PagopaRegistry> builder = captor.getValue();
      PagopaRegistry built = builder.apply(dto, null, null);
      return List.of(built);
    });

    List<PagopaRegistry> result = mapper.mapToPagoPaRegistry(dto);

    assertEquals(1, result.size());
    PagopaRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry, "iuv", "nav");
    assertNull(resultRegistry.getIuv());
    assertNull(resultRegistry.getNav());
  }

  @Test
  void givenIuvAndNavValuedWhenMapThenMappedCorrectly() {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setEventCategory(RegistryEventCategory.INTERFACCIA);
    dto.setEventType(RegistryPagopaEventType.paGetPaymentV2);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setOutcome(RegistryOutcome.OK);
    dto.setBody("body");

    @SuppressWarnings("unchecked")
    ArgumentCaptor<TriFunction<RegistryEventPagoPaDTO, String, String, PagopaRegistry>> captor =
      ArgumentCaptor.forClass(TriFunction.class);

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    when(mappingService.mapRegistries(
      eq(dto),
      argThat(f -> dto.getIuv().equals(f.apply(dto))),
      argThat(f -> dto.getNav().equals(f.apply(dto))),
      captor.capture()
    )).thenAnswer(invocation -> {
      TriFunction<RegistryEventPagoPaDTO, String, String, PagopaRegistry> builder = captor.getValue();
      PagopaRegistry built = builder.apply(dto, dto.getIuv(), dto.getNav());
      return List.of(built);
    });

    List<PagopaRegistry> result = mapper.mapToPagoPaRegistry(dto);

    assertEquals(1, result.size());
    PagopaRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry);
    assertEquals(dto.getIuv(), resultRegistry.getIuv());
    assertEquals(dto.getNav(), resultRegistry.getNav());
  }

}
