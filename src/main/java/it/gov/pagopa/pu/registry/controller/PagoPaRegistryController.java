package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.controller.generated.PagoPaRegistryApi;
import it.gov.pagopa.pu.registry.dto.generated.PagoPaRegistryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PagoPaRegistryController implements PagoPaRegistryApi {

  @Override
  public ResponseEntity<PagoPaRegistryDTO> getPagoPaRegistry(String registryId) {
    log.info("User requested getPagoPaRegistry having registryId {}", registryId);

    return PagoPaRegistryApi.super.getPagoPaRegistry(registryId);
  }
}
