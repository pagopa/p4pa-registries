package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrySendTemplateDTO2PSentTemplateRegistryMapperTest {
  @Mock
  private DataCipherService dataCipherServiceMock;
  @InjectMocks
  private RegistrySendTemplateDTO2PSentTemplateRegistryMapper mapper;

  private final PodamFactory podamFactory = TestUtils.getPodamFactory();

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(dataCipherServiceMock);
  }

  @Test
  void testMapToSendTimelineRegistry() {
    //GIVEN
    RegistryEventSendTimelineDTO dto = podamFactory.manufacturePojo(RegistryEventSendTimelineDTO.class);
    dto.setEventSubType(RegistryEventSubType.RESP);
    dto.setOutcome(RegistryOutcome.OK);
    dto.setBody("body");

    when(dataCipherServiceMock.encrypt("body")).thenReturn("encryptedBody".getBytes());

    //WHEN
    SendTimelineRegistry actualEntity = mapper.mapToSendTimelineRegistry(dto);
    //THEN
    TestUtils.checkNotNullFields(actualEntity, "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");
  }
}
