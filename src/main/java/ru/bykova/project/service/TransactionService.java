package ru.bykova.project.service;

import java.math.BigDecimal;

public interface TransactionService {

    void transferBetweenBankBook(String sourceBankBookNumber, String targetBankBookNumber, BigDecimal amount);

}
