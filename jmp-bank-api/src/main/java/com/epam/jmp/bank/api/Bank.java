package com.epam.jmp.bank.api;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public interface Bank {

    BankCard createBankCard(User user, BankCardType bankCardType);

    BankType getBankType();

    static List<Bank> getBanks(BankType bankType) {
        return StreamSupport.stream(ServiceLoader.load(Bank.class).spliterator(), false)
                .filter(bank -> bank.getBankType() == bankType)
                .toList();
    }
}
