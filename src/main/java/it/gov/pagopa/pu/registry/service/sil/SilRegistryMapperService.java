package it.gov.pagopa.pu.registry.service.sil;

import it.gov.pagopa.pu.registry.dto.RegistryEventSilDTO;
import it.gov.pagopa.pu.registry.mapper.pagopa.RegistryEventSilDTO2SilRegistryMapper;
import it.gov.pagopa.pu.registry.model.SilRegistry;
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
public class SilRegistryMapperService {
  private final RegistryEventSilDTO2SilRegistryMapper registryEventSilDTO2SilRegistryMapper;

  public List<SilRegistry> map(@Valid @NotNull RegistryEventSilDTO event) {
    return registryEventSilDTO2SilRegistryMapper.map(event);
  }
}
