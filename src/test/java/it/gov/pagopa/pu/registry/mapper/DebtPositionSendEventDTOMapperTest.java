package it.gov.pagopa.pu.registry.mapper;

import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendEventDTO;
import it.gov.pagopa.pu.registry.event.payments.dto.DebtPositionSendNotificationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebtPositionSendEventDTOMapperTest {

  private DebtPositionSendEventDTOMapper mapper;

  @BeforeEach
  void setUp() {
    this.mapper = new DebtPositionSendEventDTOMapper();
  }

  @Test
  void GivenEmptyEventDescriptionWhenRetrievedThenReturnsOnlyAdditionalInfo() {
    // Given
    DebtPositionSendEventDTO dto = new DebtPositionSendEventDTO();
    dto.setEventDescription("");
    dto.setPayload(new DebtPositionSendNotificationDTO());
    dto.getPayload().setIun("123456");
    dto.getPayload().setNotificationDate(OffsetDateTime.parse("2025-06-01T10:00+02:00"));

    // When
    String eventDescription = mapper.getEventDescription(dto);

    // Then
    assertEquals("IUN: 123456; NotificationDate: 2025-06-01T10:00+02:00", eventDescription);
  }

  @Test
  void GivenEventDescriptionWhenRetrievedThenReturnsOnlyAdditionalInfo() {
    // Given
    DebtPositionSendEventDTO dto = new DebtPositionSendEventDTO();
    dto.setEventDescription("Event description");
    dto.setPayload(new DebtPositionSendNotificationDTO());
    dto.getPayload().setIun("123456");
    dto.getPayload().setNotificationDate(OffsetDateTime.parse("2025-06-01T10:00+02:00"));

    // When
    String eventDescription = mapper.getEventDescription(dto);

    // Then
    assertEquals("Event description; IUN: 123456; NotificationDate: 2025-06-01T10:00+02:00", eventDescription);
  }

}
