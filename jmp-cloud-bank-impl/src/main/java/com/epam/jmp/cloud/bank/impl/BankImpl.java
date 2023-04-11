package com.epam.jmp.cloud.bank.impl;

import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.bank.api.BankType;
import com.epam.jmp.cloud.bank.exception.BankCardException;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class BankImpl implements Bank {

    private final Map<BankCardType, BiFunction<String, User, BankCard>> creators;

    public BankImpl() {
        this.creators = new HashMap<>();
        this.creators.put(BankCardType.CREDIT, CreditBankCard::new);
        this.creators.put(BankCardType.DEBIT, DebitBankCard::new);
    }

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        var number = UUID.randomUUID().toString();
        return creators.getOrDefault(bankCardType, this::throwIfTypeIsUnknown)
                .apply(number, user);
    }

    @Override
    public BankType getBankType() {
        return BankType.FIRST_BANK;
    }

    private BankCard throwIfTypeIsUnknown(String number, User user) {
        throw new BankCardException("Unknown card type: " + number);
    }
}
