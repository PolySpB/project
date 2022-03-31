package ru.bykova.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.bykova.project.model.BankBookDto;
import ru.bykova.project.utils.ErrorUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class BankBookServiceImpl implements BankBookService {

    private final List<BankBookDto> BANKBOOK_STORAGE = new CopyOnWriteArrayList<>();
    private final AtomicInteger bankBookSequenceId = new AtomicInteger(1);
    private final AtomicInteger userSequenceId = new AtomicInteger(1);
    private final AtomicInteger numberSequenceId = new AtomicInteger(1000);

    @PostConstruct
    public void initBankBookStorage() {
        int userId = userSequenceId.getAndIncrement();
        BankBookDto bankBook1 = BankBookDto.builder()
                .id(bankBookSequenceId.getAndIncrement())
                .userId(userId)
                .number(String.valueOf(numberSequenceId.getAndIncrement()))
                .amount(new BigDecimal("1500"))
                .currency("RUB")
                .build();
        BankBookDto bankBook2 = BankBookDto.builder()
                .id(bankBookSequenceId.getAndIncrement())
                .userId(userId)
                .number(String.valueOf(numberSequenceId.getAndIncrement()))
                .amount(new BigDecimal("300"))
                .currency("USD")
                .build();
        Collections.addAll(BANKBOOK_STORAGE, bankBook1, bankBook2);
    }

    @Override
    public List<BankBookDto> getAllBankBooksInStorage() {
        return BANKBOOK_STORAGE;
    }

    @Override
    public List<BankBookDto> getAllBankBookByUserId(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId can't be null!");
        }

        List<BankBookDto> bankBooks = getBankBookByUserId(userId);

        if (CollectionUtils.isEmpty(bankBooks)) {
            throw new NoSuchElementException(ErrorUtils.NOT_FOUND);
        } else {
            return bankBooks;
        }
    }

    @Override
    public BankBookDto getBankBookByBankBookId(Integer bankBookId) {
        if (bankBookId == null) {
            throw new IllegalArgumentException("BankBookId can't be null!");
        }

        for (BankBookDto bankBook : BANKBOOK_STORAGE) {
            if (bankBook.getId().equals(bankBookId)) {
                return bankBook;
            }
        }
        throw new NoSuchElementException(ErrorUtils.NOT_FOUND);
    }

    @Override
    public BankBookDto createBankBook(BankBookDto bankBookDto) {
        checkDtoArgs(bankBookDto);

        List<BankBookDto> allBankBookByUserId = getBankBookByUserId(bankBookDto.getUserId());
        for (BankBookDto bankBook : allBankBookByUserId) {
            if (bankBookDto.getNumber().equalsIgnoreCase(bankBook.getNumber()) && bankBookDto.getCurrency().equalsIgnoreCase(bankBook.getCurrency())) {
                String errorMessage = String.format("BankBook number=%s, currency=%s for user=%s already exists!",
                        bankBookDto.getNumber(), bankBookDto.getCurrency(), bankBookDto.getUserId());
                throw new IllegalArgumentException(errorMessage);
            }
        }

        BANKBOOK_STORAGE.add(bankBookDto);

        return bankBookDto;
    }

    @Override
    public BankBookDto updateBankBook(BankBookDto bankBookDto) {
        BankBookDto bankBookById = getBankBookById(bankBookDto.getId());

        if (!bankBookById.getNumber().equalsIgnoreCase(bankBookDto.getNumber())) {
            throw new IllegalArgumentException("Can't change bankBook number!");
        }

        bankBookById.setUserId(bankBookDto.getUserId());
        bankBookById.setAmount(bankBookDto.getAmount());
        bankBookById.setCurrency(bankBookDto.getCurrency());

        return getBankBookById(bankBookDto.getId());
    }

    @Override
    public void deleteBankBookByBankBookId(Integer bankBookId) {
        if (bankBookId == null) {
            throw new IllegalArgumentException("BankBookId can't be null!");
        }

        BANKBOOK_STORAGE.removeIf(next -> next.getId().equals(bankBookId));
    }

    @Override
    public void deleteAllBankBooksByUserId(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId can't be null!");
        }

        BANKBOOK_STORAGE.removeIf(next -> next.getUserId().equals(userId));
    }

    private List<BankBookDto> getBankBookByUserId(Integer userId) {
        List<BankBookDto> bankBooks = new ArrayList<>();
        for (BankBookDto bankBook : BANKBOOK_STORAGE) {
            if (bankBook.getUserId().equals(userId)) {
                bankBooks.add(bankBook);
            }
        }
        return bankBooks;
    }

    private void checkDtoArgs(BankBookDto bankBookDto) {
        Integer id = bankBookDto.getId();
        Integer userId = bankBookDto.getUserId();
        String number = bankBookDto.getNumber();
        String currency = bankBookDto.getCurrency();
        if (id == null || userId == null || number == null || currency == null) {
            throw new IllegalArgumentException("Id, userId, number or currency can't be null!");
        }
    }

    private BankBookDto getBankBookById(Integer bankBookId) {
        if (bankBookId != null) {
            for (BankBookDto bankBook : BANKBOOK_STORAGE) {
                if (bankBookId.equals(bankBook.getId())) {
                    return bankBook;
                }
            }
        }
        throw new IllegalArgumentException("BankBookId can't be null!");
    }

}
