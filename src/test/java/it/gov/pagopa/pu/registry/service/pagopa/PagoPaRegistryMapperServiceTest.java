package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryPagopaEventType;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventPagoPaDTO2PagoPaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagoPaRegistryMapperServiceTest {
  @Mock
  private RegistryEventPagoPaDTO2PagoPaRegistryMapper registryEventPagoPaDTO2PagoPaRegistryMapperMock;

  private PagoPaRegistryMapperService service;
  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    service = new PagoPaRegistryMapperService(
      registryEventPagoPaDTO2PagoPaRegistryMapperMock
    );
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(registryEventPagoPaDTO2PagoPaRegistryMapperMock);
  }

  @Test
  void givenPaSendRTVRequestEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    RegistryEventPagoPaDTO dto = new RegistryEventPagoPaDTO();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setBody("{}");
    List<PagoPaRegistry> registries = List.of(new PagoPaRegistry());

    Mockito.when(registryEventPagoPaDTO2PagoPaRegistryMapperMock.map(Mockito.same(dto)))
      .thenReturn(registries);

    // When
    List<PagoPaRegistry> result = this.service.map(dto);

    // Then
    assertSame(registries, result);
  }

  @Test
  void givenPaSendRTVResponseEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    RegistryEventPagoPaDTO dto = new RegistryEventPagoPaDTO();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setEventSubType(RegistryEventSubType.RESP);
    dto.setBody("{}");
    List<PagoPaRegistry> registries = List.of(new PagoPaRegistry());

    Mockito.when(registryEventPagoPaDTO2PagoPaRegistryMapperMock.map(Mockito.same(dto)))
      .thenReturn(registries);

    // When
    List<PagoPaRegistry> result = this.service.map(dto);

    // Then
    assertSame(registries, result);
  }

  @Test
  void givenUnknownEventWhenMappedThenReturnsNull() {
    // Given
    RegistryEventPagoPaDTO dto = new RegistryEventPagoPaDTO();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setBody("{}");

    // When
    List<PagoPaRegistry> result = this.service.map(dto);

    // Then
    assertNotNull(result);
    assertEquals(0, result.size());
    Mockito.verify(registryEventPagoPaDTO2PagoPaRegistryMapperMock, Mockito.times(1))
      .map(Mockito.any(RegistryEventPagoPaDTO.class));
  }

  @Test
  void givenNullPayloadWhenMappedThenReturnsNull() {
    // Given
    RegistryEventPagoPaDTO dto = new RegistryEventPagoPaDTO();
    dto.setEventType(RegistryPagopaEventType.paSendRTV2);
    dto.setBody(null);

    // When
    List<PagoPaRegistry> result = this.service.map(dto);
    Set<ConstraintViolation<RegistryEventPagoPaDTO>> violations = validator.validate(dto);

    // Then
    assertFalse(violations.isEmpty());
    assertNotNull(result);
    assertEquals(0, result.size());
    Mockito.verify(registryEventPagoPaDTO2PagoPaRegistryMapperMock, Mockito.times(1))
      .map(Mockito.any(RegistryEventPagoPaDTO.class));
  }

  @Test
  void givenNullEventTypeWhenMappedThenReturnsNull() {
    // Given
    RegistryEventPagoPaDTO dto = new RegistryEventPagoPaDTO();
    dto.setBody("{}");

    // When
    List<PagoPaRegistry> result = this.service.map(dto);
    Set<ConstraintViolation<RegistryEventPagoPaDTO>> violations = validator.validate(dto);

    // Then
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(constraintViolation -> "eventType".equals(constraintViolation.getPropertyPath().toString())));
    assertNotNull(result);
    assertEquals(0, result.size());
    Mockito.verify(registryEventPagoPaDTO2PagoPaRegistryMapperMock, Mockito.times(1))
      .map(Mockito.any(RegistryEventPagoPaDTO.class));
  }
}
