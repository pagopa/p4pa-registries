package it.gov.pagopa.pu.registry.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "debt_position_registry")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InstallmentRegistry extends BaseRegistry implements Serializable {
  private String nav;
}
