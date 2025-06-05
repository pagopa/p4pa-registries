package it.gov.pagopa.pu.registry.dto;

import lombok.*;

@Builder
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KeyValueDTO<T> {
  private String key;
  private T value;
}
