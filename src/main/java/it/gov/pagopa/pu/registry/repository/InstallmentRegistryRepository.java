package it.gov.pagopa.pu.registry.repository;

import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "installment-registries")
public interface InstallmentRegistryRepository extends MongoRepository<InstallmentRegistry, String> {
  List<InstallmentRegistry> findAllByDebtPositionIdAndNav(Long debtPositionId, String nav);
  List<InstallmentRegistry> findAllByDebtPositionId(Long debtPositionId);
}
