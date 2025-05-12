package it.gov.pagopa.pu.registry.repository;

import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "installmentRegistries", path = "installment-registry")
public interface InstallmentRegistryRepository extends MongoRepository<InstallmentRegistry, String> {
}
