package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistryEventPagoPaDTO2PagoPaRegistryMapperTest {

  private DataCipherService dataCipherService;
  private RegistryEventPagoPaDTO2PagoPaRegistryMapper mapper;
  private final PodamFactory podamFactory = TestUtils.getPodamFactory();

  @BeforeEach
  void setUp() {
    dataCipherService = mock(DataCipherService.class);
    mapper = new RegistryEventPagoPaDTO2PagoPaRegistryMapper(dataCipherService);
  }

  @Test
  void givenIuvAndNavEmptyWhenMapThenOk() {
    // Given
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv(null);
    dto.setNav(null);
    dto.setEventCategory("INTERNO");
    dto.setEventType("paVerifyPaymentNotice");
    dto.setEventSubType("REQ");
    dto.setOutcome("OK");
    when(dataCipherService.encrypt(anyString())).thenReturn("encryptedBody".getBytes());

    // When
    List<PagopaRegistry> result = mapper.mapToPagoPaRegistry(dto);

    // Then
    assertEquals(1, result.size());
    PagopaRegistry registry = result.getFirst();
    TestUtils.checkNotNullFields(registry, "iuv", "nav");
    assertNull(registry.getIuv());
    assertNull(registry.getNav());
  }

  @Test
  void givenIuvAndNavListsEqualWhenMapThenOk() {
    // Given
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv("01340000100000192, 01340000100000192");
    dto.setNav("301340000100000191, 301340000100000191");
    dto.setEventCategory("INTERNO");
    dto.setEventType("paVerifyPaymentNotice");
    dto.setEventSubType("REQ");
    dto.setOutcome("OK");
    when(dataCipherService.encrypt(anyString())).thenReturn("encryptedBody".getBytes());

    // When
    List<PagopaRegistry> result = mapper.mapToPagoPaRegistry(dto);

    // Then
    assertEquals(2, result.size());
    result.forEach(TestUtils::checkNotNullFields);
  }

  @ParameterizedTest
  @MethodSource("provideInvalidIuvNavCombinations")
  void givenInvalidIuvNavCombinationsWhenMapThenThrowException(String iuv, String nav) {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv(iuv);
    dto.setNav(nav);
    dto.setEventCategory("INTERNO");
    dto.setEventType("paVerifyPaymentNotice");
    dto.setEventSubType("REQ");
    dto.setOutcome("OK");

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
      mapper.mapToPagoPaRegistry(dto)
    );

    assertTrue(exception.getMessage().contains("does not match"));
  }

  private static Stream<Arguments> provideInvalidIuvNavCombinations() {
    return Stream.of(
      Arguments.of("01340000100000192, 01340000100000192", "301340000100000191"),
      Arguments.of(" ", "301340000100000191"),
      Arguments.of("01340000100000192", " "),
      Arguments.of("01340000100000192", null),
      Arguments.of(null, "301340000100000191")
    );
  }
}
