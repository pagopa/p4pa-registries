package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryPagopaEventType;
import it.gov.pagopa.pu.registry.event.registry.dto.PaSendRTV2RequestEventDTO;
import it.gov.pagopa.pu.registry.event.registry.dto.PaSendRTV2ResponseEventDTO;
import it.gov.pagopa.pu.registry.event.registry.dto.RegistryPagoPaEventDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.PaSendRTV2RequestEventDTO2PagopaRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.pagopa.PaSendRTV2ResponseEventDTO2PagopaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagoPaRegistryMapperServiceTest {
  @Mock
  private PaSendRTV2RequestEventDTO2PagopaRegistryMapper paSendRtvRequestMapperMock;
  @Mock
  private PaSendRTV2ResponseEventDTO2PagopaRegistryMapper paSendRtvResponseMapperMock;

  private PagoPaRegistryMapperService service;
  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    service = new PagoPaRegistryMapperService(
      paSendRtvRequestMapperMock,
      paSendRtvResponseMapperMock
    );
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(paSendRtvRequestMapperMock, paSendRtvResponseMapperMock);
  }

  @Test
  void givenPaSendRTVRequestEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    PaSendRTV2RequestEventDTO dto = new PaSendRTV2RequestEventDTO();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setBody(new PaSendRTV2RequestEventDTO.Body());
    PagopaRegistry registry = new PagopaRegistry();

    Mockito.when(paSendRtvRequestMapperMock.map(Mockito.same(dto)))
      .thenReturn(registry);

    // When
    PagopaRegistry result = this.service.map(dto);

    // Then
    assertSame(registry, result);
  }

  @Test
  void givenPaSendRTVResponseEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    PaSendRTV2ResponseEventDTO dto = new PaSendRTV2ResponseEventDTO();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setEventSubType(RegistryEventSubType.RESP);
    dto.setBody(new PaSendRTV2ResponseEventDTO.Body());
    PagopaRegistry registry = new PagopaRegistry();

    Mockito.when(paSendRtvResponseMapperMock.map(Mockito.same(dto)))
      .thenReturn(registry);

    // When
    PagopaRegistry result = this.service.map(dto);

    // Then
    assertSame(registry, result);
  }

  @Test
  void givenUnknownEventWhenMappedThenReturnsNull() {
    // Given
    RegistryPagoPaEventDTO<Object> dto = new RegistryPagoPaEventDTO<>();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setBody(new Object());

    // When
    PagopaRegistry result = this.service.map(dto);

    // Then
    assertSame(null, result);
  }

  @Test
  void givenNullEventWhenMappedThenThrows() {
    // Then
    assertThrows(NullPointerException.class, () -> this.service.map(null));
  }

  @Test
  void givenNullPayloadWhenMappedThenReturnsNull() {
    // Given
    RegistryPagoPaEventDTO<Object> dto = new RegistryPagoPaEventDTO<>();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setBody(null);

    // When
    PagopaRegistry result = this.service.map(dto);
    Set<ConstraintViolation<RegistryPagoPaEventDTO<Object>>> violations = validator.validate(dto);

    // Then
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(constraintViolation -> "body".equals(constraintViolation.getPropertyPath().toString())));
    assertNull(result);
  }

  @Test
  void givenNullEventTypeWhenMappedThenReturnsNull() {
    // Given
    RegistryPagoPaEventDTO<Object> dto = new RegistryPagoPaEventDTO<>();
    dto.setBody(new Object());

    // When
    PagopaRegistry result = this.service.map(dto);
    Set<ConstraintViolation<RegistryPagoPaEventDTO<Object>>> violations = validator.validate(dto);

    // Then
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(constraintViolation -> "eventType".equals(constraintViolation.getPropertyPath().toString())));
    assertNull(result);
  }
}
