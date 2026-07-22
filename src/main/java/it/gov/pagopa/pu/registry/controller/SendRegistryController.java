package it.gov.pagopa.pu.registry.controller;

import it.gov.pagopa.pu.registry.controller.generated.SendRegistryApi;
import it.gov.pagopa.pu.registry.dto.SendTimelineRegistryDTO;
import it.gov.pagopa.pu.registry.dto.SendTimelineFullRegistryDTO;
import it.gov.pagopa.pu.registry.service.send.SendTimelineRegistryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SendRegistryController implements SendRegistryApi {

  private final SendTimelineRegistryService sendTimelineService;

  @Override
  public ResponseEntity<List<SendTimelineRegistryDTO>> getSendTimelineRegistries(String notificationRequestId) {
    log.info("User requested getSendTimelineRegistries having notificationRequestId {}", notificationRequestId);

    return ResponseEntity.ok(sendTimelineService.getSendTimelineRegistries(notificationRequestId));
  }

  @Override
  public ResponseEntity<List<SendTimelineFullRegistryDTO>> getFullSendTimelineRegistries(String notificationRequestId) {
    log.info("User requested getFullSendTimelineRegistries having notificationRequestId {}", notificationRequestId);

    return ResponseEntity.ok(sendTimelineService.getFullSendTimelineRegistries(notificationRequestId));
  }
}
