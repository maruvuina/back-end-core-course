package com.epam;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.bank.api.BankType;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.service.api.Service;
import com.epam.jmp.service.api.ServiceType;

import java.time.LocalDate;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        var harry = new User("Harry", "Potter", LocalDate.of(1980, 7, 31));
        var hermione = new User("Hermione", "Granger", LocalDate.of(1979, 9, 19));
        var ron = new User("Ron", "Weasley", LocalDate.of(1980, 4, 1));

        Service service = Service.getServices(ServiceType.FIRST_SERVICE).get(0);
        Bank bank = Bank.getBanks(BankType.FIRST_BANK).get(0);

        var creditCardHarry = bank.createBankCard(harry, BankCardType.CREDIT);
        service.subscribe(creditCardHarry);
        var debitCardOfHermione = bank.createBankCard(hermione, BankCardType.DEBIT);
        service.subscribe(debitCardOfHermione);
        var creditCardOfRon = bank.createBankCard(ron, BankCardType.CREDIT);
        service.subscribe(creditCardOfRon);

        Optional<Subscription> subscription =
                service.getSubscriptionByBankCardNumber(creditCardHarry.getNumber());
        subscription.ifPresent(System.out::println);

        System.out.println("Average users age: " + service.getAverageUsersAge());
    }
}
