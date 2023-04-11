import com.epam.jmp.bank.api.Bank;
import com.epam.jmp.cloud.bank.impl.BankImpl;

module jmp.cloud.bank.impl {

    requires transitive jmp.bank.api;
    exports com.epam.jmp.cloud.bank.impl;
    provides Bank with BankImpl;
}
