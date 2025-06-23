package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistrySilEventType;
import it.gov.pagopa.pu.registry.model.SilRegistry;
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

class RegistryEventSilDTO2SilRegistryMapperTest {

  private DataCipherService dataCipherService;
  private RegistryEventSilDTO2SilRegistryMapper mapper;
  private final PodamFactory podamFactory = TestUtils.getPodamFactory();

  @BeforeEach
  void setUp() {
    dataCipherService = mock(DataCipherService.class);
    mapper = new RegistryEventSilDTO2SilRegistryMapper(dataCipherService);
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

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    List<SilRegistry> result = mapper.map(dto);

    assertEquals(1, result.size());
    SilRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry, "iuv", "nav");
    assertEquals(dto.getRegistryId(), resultRegistry.getRegistryId());
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

    when(dataCipherService.encrypt("body")).thenReturn("encryptedBody".getBytes());

    List<SilRegistry> result = mapper.map(dto);

    assertEquals(1, result.size());
    SilRegistry resultRegistry = result.getFirst();
    TestUtils.checkNotNullFields(resultRegistry);
    assertEquals(dto.getRegistryId() + dto.getIuv(), resultRegistry.getRegistryId());
    assertEquals(dto.getIuv(), resultRegistry.getIuv());
    assertEquals(dto.getNav(), resultRegistry.getNav());
  }

}
