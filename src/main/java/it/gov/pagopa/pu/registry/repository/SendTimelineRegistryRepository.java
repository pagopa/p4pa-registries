package it.gov.pagopa.pu.registry.repository;

import it.gov.pagopa.pu.registry.model.SendTimelineRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "send-timeline-registries")
public interface SendTimelineRegistryRepository extends MongoRepository<SendTimelineRegistry, String> {

  List<SendTimelineRegistry> findByNotificationRequestId(String notificationRequestId);

}
