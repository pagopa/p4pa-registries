package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.controller.generated.PagopaRegistryApi;
import it.gov.pagopa.pu.registry.dto.generated.PagoPaRegistryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PagoPaRegistryController implements PagopaRegistryApi {

  @Override
  public ResponseEntity<PagoPaRegistryDTO> getPagopaRegistry(String registryId) {
    log.info("User requested getPagopaRegistry having registryId {}", registryId);

    return PagopaRegistryApi.super.getPagopaRegistry(registryId);
  }
}
