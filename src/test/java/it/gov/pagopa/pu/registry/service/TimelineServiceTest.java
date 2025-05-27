package it.gov.pagopa.pu.registry.service;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.debtposition.DebtPositionRegistryMapperService;
import it.gov.pagopa.pu.registry.mapper.installment.InstallmentRegistryMapperService;
import it.gov.pagopa.pu.registry.repository.DebtPositionRegistryRepository;
import it.gov.pagopa.pu.registry.repository.InstallmentRegistryRepository;
import it.gov.pagopa.pu.registry.service.debtposition.DebtPositionRegistryService;
import it.gov.pagopa.pu.registry.service.installment.InstallmentRegistryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TimelineServiceTest {

  private DebtPositionRegistryService debtPositionRegistryService;
  private InstallmentRegistryService installmentRegistryService;
  private TimelineService timelineService;

  @BeforeEach
  void setUp() {
    this.debtPositionRegistryService = Mockito.spy(new DebtPositionRegistryService(
      Mockito.mock(DebtPositionRegistryMapperService.class),
      Mockito.mock(DebtPositionRegistryRepository.class)
    ));
    this.installmentRegistryService = Mockito.spy(new InstallmentRegistryService(
      Mockito.mock(InstallmentRegistryMapperService.class),
      Mockito.mock(InstallmentRegistryRepository.class)
    ));
    this.timelineService = new TimelineService(debtPositionRegistryService, installmentRegistryService);
  }

  @Test
  void consumePaymentEvent_whenEventIsValid_shouldCallServices() {
    PaymentEventDTO<?> event = Mockito.mock(PaymentEventDTO.class);

    timelineService.consumePaymentEvent(event);

    Mockito.verify(debtPositionRegistryService, Mockito.times(1)).consumePaymentEvent(event);
    Mockito.verify(installmentRegistryService, Mockito.times(1)).consumePaymentEvent(event);
  }

  @Test
  void consumePaymentEvent_whenEventIsNull_shouldNotCallServices() {
    timelineService.consumePaymentEvent(null);
    Mockito.verify(debtPositionRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
    Mockito.verify(installmentRegistryService, Mockito.never()).consumePaymentEvent(Mockito.any());
  }

}
