package it.gov.pagopa.pu.registry.service.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventPagoPaDTO2PagoPaRegistryMapper;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class PagoPaRegistryMapperService {
  private final RegistryEventPagoPaDTO2PagoPaRegistryMapper registryEventPagoPaDTO2PagoPaRegistryMapper;

  public List<PagoPaRegistry> map(@Valid @NotNull RegistryEventPagoPaDTO event) {
    return registryEventPagoPaDTO2PagoPaRegistryMapper.map(event);
  }
}
