package ru.bykova.project.service;

import ru.bykova.project.model.dto.BankBookDto;

import java.util.List;

public interface BankBookService {
    List<BankBookDto> getAllBankBooksInStorage();

    BankBookDto getBankBookByBankBookId(Integer bankBookId);

    BankBookDto createBankBook(BankBookDto bankBookDto);

    BankBookDto updateBankBook(BankBookDto bankBookDto);

    void deleteBankBookByBankBookId(Integer bankBookId);
}
