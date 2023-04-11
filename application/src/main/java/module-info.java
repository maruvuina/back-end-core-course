module jmp.application {
    uses com.epam.jmp.service.api.Service;
    uses com.epam.jmp.bank.api.Bank;

    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;

}
