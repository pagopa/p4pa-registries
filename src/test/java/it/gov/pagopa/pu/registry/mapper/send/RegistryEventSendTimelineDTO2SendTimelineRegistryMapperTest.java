package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.NotificationRefusedErrorDTO;
import it.gov.pagopa.pu.registry.dto.RegistryEventSendTimelineDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.model.NotificationRefusedError;
import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import it.gov.pagopa.pu.registry.service.DataCipherService;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistryEventSendTimelineDTO2SendTimelineRegistryMapperTest {
  @Mock
  private DataCipherService dataCipherServiceMock;
  @InjectMocks
  private RegistryEventSendTimelineDTO2SendTimelineRegistryMapper mapper;

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
    List<NotificationRefusedErrorDTO> errorDTOList = List.of(
      new NotificationRefusedErrorDTO("code1", "detail1", 0),
      new NotificationRefusedErrorDTO("code2", "detail2", 1)
    );
    dto.setErrors(errorDTOList);

    List<NotificationRefusedError> expectedErrorList = List.of(
      new NotificationRefusedError("code1", "detail1", 0),
      new NotificationRefusedError("code2", "detail2", 1)
    );

    when(dataCipherServiceMock.encrypt("body")).thenReturn("encryptedBody".getBytes());

    //WHEN
    SendTimelineRegistry actualEntity = mapper.mapToSendTimelineRegistry(dto);
    //THEN
    TestUtils.checkNotNullFields(actualEntity, "creationDate", "updateDate", "updateOperatorExternalId", "updateTraceId");
    Assertions.assertEquals(2, actualEntity.getErrors().size());
    Assertions.assertTrue(actualEntity.getErrors().contains(expectedErrorList.getFirst()));
    Assertions.assertTrue(actualEntity.getErrors().contains(expectedErrorList.getLast()));
  }
}
