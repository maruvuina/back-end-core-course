package com.epam.jmp.cloud.service.impl;

import com.epam.jmp.cloud.exception.SubscriptionException;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.api.Service;
import com.epam.jmp.service.api.ServiceType;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ServiceImpl implements Service {

    private final Map<User, List<Subscription>> storage = new HashMap<>();

    @Override
    public void subscribe(BankCard bankCard) {
        var user = bankCard.getUser();
        var number = bankCard.getNumber();
        var subscription = new Subscription(number, LocalDate.now());
        storage.computeIfAbsent(user, u -> new LinkedList<>()).add(subscription);
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber) {
        return Optional.ofNullable(filterSubscriptions(subscription -> subscription.bankcard().equals(cardNumber))
                .findFirst()
                .orElseThrow(() -> new SubscriptionException("Cannot get subscription by card number")));
    }

    @Override
    public List<User> getAllUsers() {
        return storage.keySet().stream().toList();
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> filter) {
        return filterSubscriptions(filter).toList();
    }

    @Override
    public ServiceType getServiceType() {
        return ServiceType.FIRST_SERVICE;
    }

    private Stream<Subscription> filterSubscriptions(Predicate<Subscription> filter) {
        return storage.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(filter);
    }
}
