package it.gov.pagopa.pu.registry.repository;

import it.gov.pagopa.pu.registry.model.PagopaRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "pagopa-registries")
public interface PagopaRegistryRepository extends MongoRepository<PagopaRegistry, String> {
}
