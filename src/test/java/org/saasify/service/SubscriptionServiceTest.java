package org.saasify.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.saasify.exceptions.InsufficientFundsException;
import org.saasify.models.Client;
import org.saasify.models.Subscription;
import org.saasify.models.SubscriptionPlan;
import org.saasify.repository.ClientRepository;
import org.saasify.repository.SubscriptionPlanRepository;
import org.saasify.repository.SubscriptionRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    public void processBilling_shouldSucceed_whenClientHasEnoughFunds(){
        String dni = "12345678A";
        int planId = 1;

        Client mockClient = new Client(UUID.randomUUID(),dni, "Daniel" , "danialcaniz12@gmail.com", "pass", new BigDecimal("100.00"));
        SubscriptionPlan mockPlan = new SubscriptionPlan(planId,"Premium", new BigDecimal("20.00"), 1);

        when(clientRepository.findByDni(dni)).thenReturn(Optional.of(mockClient));
        when(subscriptionPlanRepository.findById(planId)).thenReturn(Optional.of(mockPlan));

        subscriptionService.processBilling(dni,planId);

        assertEquals(new BigDecimal("80.00"), mockClient.getBankBalance());
        verify(clientRepository, times(1)).save(mockClient);
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    public void processBilling_shouldThrowException_whenFundsAreInsufficient(){
        String dni = "12345678A";
        int planId = 1;

        Client mockClient = new Client(UUID.randomUUID(),dni, "Daniel" , "danialcaniz12@gmail.com", "pass", new BigDecimal("10.00"));
        SubscriptionPlan mockPlan = new SubscriptionPlan(planId,"Premium", new BigDecimal("20.00"), 1);

        when(clientRepository.findByDni(dni)).thenReturn(Optional.of(mockClient));
        when(subscriptionPlanRepository.findById(planId)).thenReturn(Optional.of(mockPlan));

        assertThrows(InsufficientFundsException.class, () -> {
           subscriptionService.processBilling(dni,planId);
        });

        assertEquals(new BigDecimal("10.00"), mockClient.getBankBalance());
        verify(clientRepository, never()).save(mockClient);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    public void processBilling_shouldThrowException_whenClientDoesNotExist(){
        String dni = "99999999A";
        int planId = 1;

        Client mockClient = new Client(UUID.randomUUID(),dni, "Daniel" , "danialcaniz12@gmail.com", "pass", new BigDecimal("10.00"));
        SubscriptionPlan mockPlan = new SubscriptionPlan(planId,"Premium", new BigDecimal("20.00"), 1);


        when(clientRepository.findByDni(dni)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           subscriptionService.processBilling(dni,planId);
        });

        assertEquals("Client not found", exception.getMessage());

        verify(clientRepository, never()).save(mockClient);
        verify(subscriptionRepository, never()).save(any(Subscription.class));
        verify(subscriptionPlanRepository, never()).findById(anyInt());
    }

}
