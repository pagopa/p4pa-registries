package it.gov.pagopa.pu.registry.repository;

import it.gov.pagopa.pu.registry.enums.RegistryPagopaEventType;
import it.gov.pagopa.pu.registry.model.PagoPaRegistry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.OffsetDateTime;

@RepositoryRestResource(path = "pagopa-registries")
public interface PagopaRegistryRepository extends MongoRepository<PagoPaRegistry, String> {

  @Query("{" +
    "    $and: [" +
    "        { $or: [{ $expr: { $eq: ['?0', 'null'] }}, { eventType: ?0 }] }," +
    "        { $or: [{ $expr: { $eq: ['?1', 'null'] }}, { dateTime: { $gte: ?1 } }] }," +
    "        { $or: [{ $expr: { $eq: ['?2', 'null'] }}, { dateTime: { $lte: ?2 } }] }," +
    "        { $or: [{ $expr: { $eq: ['?3', 'null'] }}, { orgFiscalCode: ?3 }] }," +
    "        { $or: [{ $expr: { $eq: ['?4', 'null'] }}, { iuv: ?4 }] }" +
    "    ] }")
  Page<PagoPaRegistry> searchByFilters(
    RegistryPagopaEventType eventType,
    OffsetDateTime startDate,
    OffsetDateTime endDate,
    String orgFiscalCode,
    String iuv,
    Pageable pageable
  );

}
