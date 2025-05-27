package it.gov.pagopa.pu.registry.service.debtposition;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.debtposition.DebtPositionEventDTO2DebtPositionRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.debtposition.DebtPositionIoEventDTO2DebtPositionRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.debtposition.DebtPositionRegistryMapperService;
import it.gov.pagopa.pu.registry.mapper.debtposition.DebtPositionSendEventDTO2DebtPositionRegistryMapper;
import it.gov.pagopa.pu.registry.model.DebtPositionRegistry;
import it.gov.pagopa.pu.registry.repository.DebtPositionRegistryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DebtPositionRegistryServiceTest {

  private DebtPositionRegistryService debtPositionRegistryService;
  private DebtPositionRegistryRepository debtPositionRegistryRepository;
  private DebtPositionRegistryMapperService debtPositionRegistryMapperService;

  @BeforeEach
  void setUp() {
    this.debtPositionRegistryRepository = Mockito.spy(DebtPositionRegistryRepository.class);
    this.debtPositionRegistryMapperService = Mockito.spy(new DebtPositionRegistryMapperService(
      Mockito.mock(DebtPositionEventDTO2DebtPositionRegistryMapper.class),
      Mockito.mock(DebtPositionIoEventDTO2DebtPositionRegistryMapper.class),
      Mockito.mock(DebtPositionSendEventDTO2DebtPositionRegistryMapper.class)
    ));
    this.debtPositionRegistryService = new DebtPositionRegistryService(
        debtPositionRegistryMapperService, debtPositionRegistryRepository);
  }

  @Test
  void consumePaymentEvent_whenRequestIsValid() {
    Mockito.when(debtPositionRegistryMapperService.map(Mockito.any(PaymentEventDTO.class))).thenReturn(new DebtPositionRegistry());
    this.debtPositionRegistryService.consumePaymentEvent(Mockito.mock(PaymentEventDTO.class));
    Mockito.verify(debtPositionRegistryMapperService, Mockito.times(1))
        .map(Mockito.any(PaymentEventDTO.class));
    Mockito.verify(debtPositionRegistryRepository, Mockito.times(1))
        .save(Mockito.any());
  }

}
