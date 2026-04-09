package it.gov.pagopa.pu.registry.model;

import it.gov.pagopa.pu.registry.enums.RegistryEventCategory;
import it.gov.pagopa.pu.registry.enums.RegistryEventSubType;
import it.gov.pagopa.pu.registry.enums.RegistryOutcome;
import it.gov.pagopa.pu.registry.enums.RegistryPagoPaEventType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document(collection = "pagopa_registry")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class SendTimelineRegistry extends BaseEntity {

  @Id
  @NotNull
  private String registryId;
  @NotNull
  private String registryOrigin;
  @NotNull
  private OffsetDateTime dateTime;
  @NotNull
  private String traceId;
  @NotNull
  private RegistryEventSubType eventSubType;
  @NotNull
  private String requestorId;
  @NotNull
  private String grantorId;

  @NotNull
  private RegistryEventCategory eventCategory;

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

  @NotNull
  private RegistryOutcome outcome;
  private byte[] bodyCiphered;
}
