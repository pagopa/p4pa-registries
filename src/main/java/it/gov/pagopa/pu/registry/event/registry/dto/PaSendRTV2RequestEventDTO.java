package it.gov.pagopa.pu.registry.event.registry.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PaSendRTV2RequestEventDTO extends RegistryPagoPaEventDTO<PaSendRTV2RequestEventDTO.Body> {
  @Data
  @Builder
  @NoArgsConstructor @AllArgsConstructor
  @ToString @EqualsAndHashCode
  public static class Body {
    private String idPA;
    private String idBrokerPA;
    private String idStation;
    private String fiscalCode;
    private String noticeNumber;
    private byte[] receiptBytes;
  }
}
