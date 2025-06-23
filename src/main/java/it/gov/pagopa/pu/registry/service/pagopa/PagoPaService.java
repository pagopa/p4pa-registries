package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.generated.PagoPaRegistryDTO;

public interface PagoPaService {

  PagoPaRegistryDTO getPagopaRegistry(String registryId);
}
