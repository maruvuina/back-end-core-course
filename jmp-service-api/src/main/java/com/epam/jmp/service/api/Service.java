package com.epam.jmp.service.api;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

/**
 * API for service communication.
 */
public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.birthday(), LocalDate.now()))
                .average()
                .orElse(0);
    }

    static boolean isPayable(User user) {
        return user.birthday().isBefore(LocalDate.now().minusYears(18));
    }

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> filter);

    ServiceType getServiceType();

    static List<Service> getServices(ServiceType serviceType) {
        return StreamSupport.stream(ServiceLoader.load(Service.class).spliterator(), false)
                .filter(bank -> bank.getServiceType() == serviceType)
                .toList();
    }
}
