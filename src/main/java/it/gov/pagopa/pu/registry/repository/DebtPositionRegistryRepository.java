package it.gov.pagopa.pu.registry.repository;

import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "debtPositionRegistries", path = "debt-position-registries")
public interface DebtPositionRegistryRepository extends MongoRepository<DebtPositionRegistry, String> {
}
