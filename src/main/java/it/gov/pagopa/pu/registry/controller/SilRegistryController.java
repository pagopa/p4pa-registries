package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.controller.generated.SilRegistryApi;
import it.gov.pagopa.pu.registry.dto.generated.SilRegistryDTO;
import it.gov.pagopa.pu.registry.service.sil.SilRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SilRegistryController implements SilRegistryApi {

  private final SilRegistryService silRegistryService;

  @Override
  public ResponseEntity<SilRegistryDTO> getSilRegistry(String registryId) {
    log.info("User requested getSilRegistry having registryId {}", registryId);

    return ResponseEntity.ok(silRegistryService.getSilRegistry(registryId));
  }
}
