package it.gov.pagopa.pu.registry.repository;

import it.gov.pagopa.pu.registry.model.SilRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "sil-registries")
public interface SilRegistryRepository extends MongoRepository<SilRegistry, String> {
}
