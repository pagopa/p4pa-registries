package it.gov.pagopa.pu.registry.event.registry.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PaSendRTV2ResponseEventDTO extends RegistryPagoPaEventDTO<PaSendRTV2ResponseEventDTO.Body> {
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  @ToString
  public static class Body {
    private Outcome outcome;
    private XmlRequestFaultDTO fault;

    public enum Outcome {
      OK,
      KO
    }
  }
}
