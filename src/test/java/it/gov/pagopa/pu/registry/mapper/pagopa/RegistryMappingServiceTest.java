package it.gov.pagopa.pu.registry.mapper.pagopa;

import it.gov.pagopa.pu.registry.dto.RegistryEventPagoPaDTO;
import it.gov.pagopa.pu.registry.utils.TestUtils;
import org.junit.jupiter.api.Test;
import uk.co.jemos.podam.api.PodamFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistryMappingServiceTest {

  private final RegistryMappingService service = new RegistryMappingService();
  private final PodamFactory podamFactory = TestUtils.getPodamFactory();

  @Test
  void givenBothIuvAndNavNull_whenMapRegistries_thenReturnSingleEmptyElement() {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv(null);
    dto.setNav(null);

    List<String> result = service.mapRegistries(
      dto,
      RegistryEventPagoPaDTO::getIuv,
      RegistryEventPagoPaDTO::getNav,
      (d, iuv, nav) -> iuv + "-" + nav
    );

    assertEquals(List.of("null-null"), result);
  }

  @Test
  void givenIuvNullAndNavPresent_whenMapRegistries_thenIuvIsEmpty() {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv(null);
    dto.setNav("nav1,nav2");

    List<String> result = service.mapRegistries(
      dto,
      RegistryEventPagoPaDTO::getIuv,
      RegistryEventPagoPaDTO::getNav,
      (d, iuv, nav) -> iuv + "-" + nav
    );

    assertEquals(List.of("null-nav1", "null-nav2"), result);
  }

  @Test
  void givenIuvPresentAndNavNull_whenMapRegistries_thenNavIsEmpty() {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv("iuv1,iuv2");
    dto.setNav(null);

    List<String> result = service.mapRegistries(
      dto,
      RegistryEventPagoPaDTO::getIuv,
      RegistryEventPagoPaDTO::getNav,
      (d, iuv, nav) -> iuv + "-" + nav
    );

    assertEquals(List.of("iuv1-null", "iuv2-null"), result);
  }

  @Test
  void givenMultipleIuvAndNavPresent_whenMapRegistries_thenReturnZippedList() {
    RegistryEventPagoPaDTO dto = podamFactory.manufacturePojo(RegistryEventPagoPaDTO.class);
    dto.setIuv("iuv1,iuv2");
    dto.setNav("nav1,nav2");

    List<String> result = service.mapRegistries(
      dto,
      RegistryEventPagoPaDTO::getIuv,
      RegistryEventPagoPaDTO::getNav,
      (d, iuv, nav) -> iuv + "-" + nav
    );

    assertEquals(List.of("iuv1-nav1", "iuv2-nav2"), result);
  }
}


