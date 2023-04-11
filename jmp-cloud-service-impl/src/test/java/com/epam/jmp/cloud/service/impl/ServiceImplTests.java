package com.epam.jmp.cloud.service.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.api.Service;
import com.epam.jmp.service.api.ServiceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceImplTests {

    private final Service service = new ServiceImpl();

    private User user;

    private BankCard bankCard;

    @BeforeEach
    public void setUp() {
        user = new User("Anna", "Karenina", LocalDate.of(2000, 1, 1));
        String cardNumber = UUID.randomUUID().toString();
        bankCard = new CreditBankCard(cardNumber, user);
    }

    @Test
    void testSubscribe() {
        service.subscribe(bankCard);
        var subscriptions = service.getAllSubscriptionsByCondition(subscription -> subscription.bankcard().equals(bankCard.getNumber()));
        assertFalse(subscriptions.isEmpty());
        assertEquals(bankCard.getNumber(), subscriptions.get(0).bankcard());

    }

    @Test
    void getSubscriptionByBankCardNumber() {
        service.subscribe(bankCard);
        Optional<Subscription> subscription = service.getSubscriptionByBankCardNumber(bankCard.getNumber());
        assertTrue(subscription.isPresent());
        assertEquals(bankCard.getNumber(), subscription.get().bankcard());

    }

    @Test
    void testGetAllUsers() {
        service.subscribe(bankCard);
        var users = service.getAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(user, users.get(0));
    }

    @Test
    void testGetAllSubscriptionsByCondition() {
        service.subscribe(bankCard);
        var subscriptions = service.getAllSubscriptionsByCondition(subscription -> subscription.bankcard().equals(bankCard.getNumber()));
        assertFalse(subscriptions.isEmpty());
        assertEquals(bankCard.getNumber(), subscriptions.get(0).bankcard());
    }

    @Test
    void testGetAllSubscriptionsByConditionWithUnknownCondition() {
        var subscriptions = service.getAllSubscriptionsByCondition(subscription -> false);
        assertTrue(subscriptions.isEmpty());
    }

    @Test
    void getServiceType() {
        assertEquals(ServiceType.FIRST_SERVICE, service.getServiceType());
    }
}
