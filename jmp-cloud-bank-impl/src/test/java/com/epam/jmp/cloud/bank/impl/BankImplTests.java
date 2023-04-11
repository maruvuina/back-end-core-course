package com.epam.jmp.cloud.bank.impl;


import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.bank.api.BankType;
import com.epam.jmp.cloud.bank.exception.BankCardException;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankImplTests {

    private final Bank bank = new BankImpl();

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Anna", "Karenina", LocalDate.of(2000, 1, 1));
    }

    @ParameterizedTest
    @EnumSource(value = BankCardType.class, names = {"UNKNOWN"}, mode = EnumSource.Mode.EXCLUDE)
    void createBankCard(BankCardType bankCardType) {
        BankCard bankCard = bank.createBankCard(user, bankCardType);
        assertNotNull(bankCard);
        assertNotNull(bankCard.getNumber());
    }

    @Test
    void testCreateBankCardWithUnknownType() {
        assertThrows(BankCardException.class, () -> bank.createBankCard(user, BankCardType.UNKNOWN));
    }


    @Test
    void getBankType() {
        assertEquals(BankType.FIRST_BANK, bank.getBankType());
    }
}
