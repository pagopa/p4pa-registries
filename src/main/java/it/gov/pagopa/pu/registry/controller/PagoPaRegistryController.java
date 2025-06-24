package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.controller.generated.PagoPaRegistryApi;
import it.gov.pagopa.pu.registry.dto.generated.PagoPaRegistryDTO;
import it.gov.pagopa.pu.registry.service.pagopa.PagoPaRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PagoPaRegistryController implements PagoPaRegistryApi {

  private final PagoPaRegistryService pagopaRegistryService;

  @Override
  public ResponseEntity<PagoPaRegistryDTO> getPagoPaRegistry(String registryId) {
    log.info("User requested getPagoPaRegistry having registryId {}", registryId);

    return ResponseEntity.ok(pagopaRegistryService.getPagoPaRegistry(registryId));
  }
}
