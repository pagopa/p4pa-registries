package it.gov.pagopa.pu.registry.service.installment;

import it.gov.pagopa.pu.registry.event.payments.dto.PaymentEventDTO;
import it.gov.pagopa.pu.registry.mapper.installment.DebtPositionEventDTO2InstallmentRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.installment.DebtPositionIoEventDTO2InstallmentRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.installment.DebtPositionSendEventDTO2InstallmentRegistryMapper;
import it.gov.pagopa.pu.registry.mapper.installment.InstallmentRegistryMapperService;
import it.gov.pagopa.pu.registry.model.InstallmentRegistry;
import it.gov.pagopa.pu.registry.repository.InstallmentRegistryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class InstallmentRegistryServiceTest {

  private InstallmentRegistryService installmentRegistryService;
  private InstallmentRegistryRepository installmentRegistryRepository;
  private InstallmentRegistryMapperService installmentRegistryMapperService;

  @BeforeEach
  void setUp() {
    this.installmentRegistryRepository = Mockito.spy(InstallmentRegistryRepository.class);
    this.installmentRegistryMapperService = Mockito.spy(new InstallmentRegistryMapperService(
      Mockito.mock(DebtPositionEventDTO2InstallmentRegistryMapper.class),
      Mockito.mock(DebtPositionIoEventDTO2InstallmentRegistryMapper.class),
      Mockito.mock(DebtPositionSendEventDTO2InstallmentRegistryMapper.class)
    ));
    this.installmentRegistryService = new InstallmentRegistryService(
      installmentRegistryMapperService, installmentRegistryRepository);
  }

  @Test
  void consumePaymentEvent_whenRequestIsValid() {
    Mockito.when(installmentRegistryMapperService.map(Mockito.any(PaymentEventDTO.class))).thenReturn(List.of(new InstallmentRegistry(), new InstallmentRegistry()));
    this.installmentRegistryService.consumePaymentEvent(Mockito.mock(PaymentEventDTO.class));
    Mockito.verify(installmentRegistryMapperService, Mockito.times(1))
        .map(Mockito.any(PaymentEventDTO.class));
    Mockito.verify(installmentRegistryRepository, Mockito.times(1))
        .saveAll(Mockito.any());
  }

}
