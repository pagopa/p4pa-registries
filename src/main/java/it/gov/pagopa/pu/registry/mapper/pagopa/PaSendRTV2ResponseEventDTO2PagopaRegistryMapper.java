package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.event.registry.dto.PaSendRTV2ResponseEventDTO;
import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaSendRTV2ResponseEventDTO2PagopaRegistryMapper {

  public PagopaRegistry map(PaSendRTV2ResponseEventDTO dto) {
    return new PagopaRegistry();
  }

}
