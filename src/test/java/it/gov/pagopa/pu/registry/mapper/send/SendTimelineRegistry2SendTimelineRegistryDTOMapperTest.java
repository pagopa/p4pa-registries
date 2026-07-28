package it.gov.pagopa.pu.registry.mapper.send;

import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryExtendedDTO;
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

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendTimelineRegistry2SendTimelineRegistryDTOMapperTest {
  @Mock
  private DataCipherService dataCipherServiceMock;
  @InjectMocks
  private SendTimelineRegistry2SendTimelineRegistryDTOMapper mapper;

  private final PodamFactory podamFactory = TestUtils.getPodamFactory();

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(dataCipherServiceMock);
  }

  @Test
  void testMapToSendTimelineRegistryDTO() {
    //GIVEN
    SendTimelineRegistry registry = podamFactory.manufacturePojo(SendTimelineRegistry.class);

    //WHEN
    SendTimelineRegistryDTO actualDTO = mapper.mapToSendTimelineRegistryDTO(registry);

    //THEN
    TestUtils.checkNotNullFields(actualDTO);
  }

  @Test
  void testMapToSendTimelineRegistryExtendedDTO() {
    //GIVEN
    SendTimelineRegistry registry = podamFactory.manufacturePojo(SendTimelineRegistry.class);
    String decryptedBody = "decryptedBody";
    byte[] bodyCiphered = decryptedBody.getBytes(StandardCharsets.UTF_8);
    registry.setBodyCiphered(bodyCiphered);

    when(dataCipherServiceMock.decrypt(bodyCiphered))
      .thenReturn(decryptedBody);

    //WHEN
    SendTimelineRegistryExtendedDTO actualDTO = mapper.mapToSendTimelineRegistryExtendedDTO(registry);

    //THEN
    Assertions.assertEquals(decryptedBody, actualDTO.getBody());
    TestUtils.checkNotNullFields(actualDTO);
  }
}
