package it.gov.pagopa.pu.registry.dto;

import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryEventSendTimelineDTO extends RegistryInterfaceEventDTO {
  @NotNull
  private Long organizationId; //Codice interno a PU dell'ente a cui le notifiche fanno capo
  @NotNull
  private String streamId; //Identificativo dello stream nel quale questo evento è stato inviato da SEND
  @NotNull
  private String eventId; //Identificativo dell'evento inviato da SEND
  @NotNull
  private String eventType; //Identificativo del tipo di evento: nel caso di interazioni con SEND rappresenta la categoria dell'evento
  @NotNull
  private String notificationRequestId; //Identificativo della richiesta di notifica SEND
  private String iun; //IUN della notifica
  private Long recipientIndex;
  private String newStatus; //Nuovo passaggio di stato della notifica che questo evento rappresenta
  private RegistryOutcome outcome; //OK/KO,
  @NotNull
  private String body; //Corpo del messaggio scambiato
}
