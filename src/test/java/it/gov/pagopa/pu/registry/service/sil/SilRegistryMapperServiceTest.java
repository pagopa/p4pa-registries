package it.gov.pagopa.pu.registry.service.sil;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistrySilEventType;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventSilDTO2SilRegistryMapper;
import it.gov.pagopa.pu.registry.model.SilRegistry;
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
class SilRegistryMapperServiceTest {
  @Mock
  private RegistryEventSilDTO2SilRegistryMapper registryEventSilDTO2SilRegistryMapperMock;

  private SilRegistryMapperService service;
  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
    service = new SilRegistryMapperService(
      registryEventSilDTO2SilRegistryMapperMock
    );
  }

  @AfterEach
  void afterEach() {
    Mockito.verifyNoMoreInteractions(registryEventSilDTO2SilRegistryMapperMock);
  }

  @Test
  void givenPaSendRTVRequestEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    RegistryEventSilDTO dto = new RegistryEventSilDTO();
    dto.setEventType(RegistrySilEventType.attualizzazioneImporti);
    dto.setEventSubType(RegistryEventSubType.REQ);
    dto.setBody("{}");
    List<SilRegistry> registries = List.of(new SilRegistry());

    Mockito.when(registryEventSilDTO2SilRegistryMapperMock.map(Mockito.same(dto)))
      .thenReturn(registries);

    // When
    List<SilRegistry> result = this.service.map(dto);

    // Then
    assertSame(registries, result);
  }

  @Test
  void givenPaSendRTVResponseEventWhenMappedThenInvokesExpectedMapper() {
    // Given
    RegistryEventSilDTO dto = new RegistryEventSilDTO();
    dto.setEventType(RegistrySilEventType.attualizzazioneImporti);
    dto.setEventSubType(RegistryEventSubType.RESP);
    dto.setBody("{}");
    List<SilRegistry> registries = List.of(new SilRegistry());

    Mockito.when(registryEventSilDTO2SilRegistryMapperMock.map(Mockito.same(dto)))
      .thenReturn(registries);

    // When
    List<SilRegistry> result = this.service.map(dto);

    // Then
    assertSame(registries, result);
  }

  @Test
  void givenUnknownEventWhenMappedThenReturnsNull() {
    // Given
    RegistryEventSilDTO dto = new RegistryEventSilDTO();
    dto.setEventType(RegistrySilEventType.attualizzazioneImporti);
    dto.setBody("{}");

    // When
    List<SilRegistry> result = this.service.map(dto);

    // Then
    assertNotNull(result);
    assertEquals(0, result.size());
    Mockito.verify(registryEventSilDTO2SilRegistryMapperMock, Mockito.times(1))
      .map(Mockito.any(RegistryEventSilDTO.class));
  }

  @Test
  void givenNullPayloadWhenMappedThenReturnsNull() {
    // Given
    RegistryEventSilDTO dto = new RegistryEventSilDTO();
    dto.setEventType(RegistrySilEventType.attualizzazioneImporti);
    dto.setBody(null);

    // When
    List<SilRegistry> result = this.service.map(dto);
    Set<ConstraintViolation<RegistryEventSilDTO>> violations = validator.validate(dto);

    // Then
    assertFalse(violations.isEmpty());
    assertNotNull(result);
    assertEquals(0, result.size());
    Mockito.verify(registryEventSilDTO2SilRegistryMapperMock, Mockito.times(1))
      .map(Mockito.any(RegistryEventSilDTO.class));
  }

  @Test
  void givenNullEventTypeWhenMappedThenReturnsNull() {
    // Given
    RegistryEventSilDTO dto = new RegistryEventSilDTO();
    dto.setBody("{}");

    // When
    List<SilRegistry> result = this.service.map(dto);
    Set<ConstraintViolation<RegistryEventSilDTO>> violations = validator.validate(dto);

    // Then
    assertFalse(violations.isEmpty());
    assertTrue(violations.stream().anyMatch(constraintViolation -> "eventType".equals(constraintViolation.getPropertyPath().toString())));
    assertNotNull(result);
    assertEquals(0, result.size());
    Mockito.verify(registryEventSilDTO2SilRegistryMapperMock, Mockito.times(1))
      .map(Mockito.any(RegistryEventSilDTO.class));
  }
}
