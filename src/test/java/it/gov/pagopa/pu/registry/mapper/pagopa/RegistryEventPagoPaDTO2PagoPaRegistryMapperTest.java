package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagoPaEventType;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv(null);
    dto.setNav(null);
    dto.setEventCategory(RegistryEventCategory.INTERFACCIA);
    dto.setEventType(RegistryPagoPaEventType.PaForNode_paGetPaymentV2);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setOutcome(RegistryOutcome.OK);
    dto.setBody("body");

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    List<PagoPaRegistry> result = mapper.map(dto);

    assertEquals(1, result.size());
    PagoPaRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry, "iuv", "nav", "body", "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");
    assertEquals(dto.getRegistryId(), resultRegistry.getRegistryId());
    assertNull(resultRegistry.getIuv());
    assertNull(resultRegistry.getNav());
  }

  @Test
  void givenIuvAndNavValuedWhenMapThenMappedCorrectly() {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setEventCategory(RegistryEventCategory.INTERFACCIA);
    dto.setEventType(RegistryPagoPaEventType.PaForNode_paGetPaymentV2);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setOutcome(RegistryOutcome.OK);
    dto.setBody("body");

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    List<PagoPaRegistry> result = mapper.map(dto);

    assertEquals(1, result.size());
    PagoPaRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry, "body", "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");
    assertEquals(dto.getRegistryId() + dto.getIuv(), resultRegistry.getRegistryId());
    assertEquals(dto.getIuv(), resultRegistry.getIuv());
    assertEquals(dto.getNav(), resultRegistry.getNav());
  }

}
