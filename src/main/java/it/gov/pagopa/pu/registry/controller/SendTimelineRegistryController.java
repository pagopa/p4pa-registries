package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.controller.generated.SendTimelineRegistryApi;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryExtendedDTO;
import it.gov.pagopa.pu.registry.service.send.SendTimelineRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SendTimelineRegistryController implements SendTimelineRegistryApi {

  private final SendTimelineRegistryService sendTimelineRegistryService;

  @Override
  public ResponseEntity<List<SendTimelineRegistryDTO>> getSendTimelineRegistries(String notificationRequestId) {
    log.info("User requested getSendTimelineRegistries having notificationRequestId {}", notificationRequestId);

    return ResponseEntity.ok(sendTimelineRegistryService.getSendTimelineRegistries(notificationRequestId));
  }

  @Override
  public ResponseEntity<List<SendTimelineRegistryExtendedDTO>> getExtendedSendTimelineRegistries(String notificationRequestId) {
    log.info("User requested getExtendedSendTimelineRegistries having notificationRequestId {}", notificationRequestId);

    return ResponseEntity.ok(sendTimelineRegistryService.getExtendedSendTimelineRegistries(notificationRequestId));
  }
}
