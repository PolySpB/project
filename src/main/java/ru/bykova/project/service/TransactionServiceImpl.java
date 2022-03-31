package ru.bykova.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bykova.project.model.TransactionStatus;
import ru.bykova.project.model.entity.BankBookEntity;
import ru.bykova.project.model.entity.TransactionEntity;
import ru.bykova.project.repository.BankBookRepository;
import ru.bykova.project.repository.TransactionRepository;
import ru.bykova.project.repository.TransactionStatusRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private BankBookRepository bankBookRepository;
    private TransactionStatusRepository transactionStatusRepository;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(BankBookRepository bankBookRepository,
                                  TransactionStatusRepository transactionStatusRepository,
                                  TransactionRepository transactionRepository) {
        this.bankBookRepository = bankBookRepository;
        this.transactionStatusRepository = transactionStatusRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transferBetweenBankBook(String sourceBankBookNumber, String targetBankBookNumber, BigDecimal amount) {
        Optional<BankBookEntity> sourceBankBookOptional = bankBookRepository.findByNumber(sourceBankBookNumber);
        Optional<BankBookEntity> targetBankBookOptional = bankBookRepository.findByNumber(targetBankBookNumber);

        if (!sourceBankBookOptional.isPresent()) {
            throw new IllegalArgumentException(String.format("BankBook with number=%s don't exist!", sourceBankBookNumber));
        }

        if (!targetBankBookOptional.isPresent()) {
            throw new IllegalArgumentException(String.format("BankBook with number=%s don't exist!", targetBankBookNumber));
        }

        BankBookEntity sourceBankBook = sourceBankBookOptional.get();
        BankBookEntity targetBankBook = targetBankBookOptional.get();

        if (sourceBankBook.getNumber().equals(targetBankBook.getNumber())) {
            throw new IllegalArgumentException("Source and target bankBooks can't have same numbers!");
        }

        if (!Objects.equals(sourceBankBook.getCurrency().getId(), targetBankBook.getCurrency().getId())) {
            throw new IllegalArgumentException(String.format("BankBooks have different currencies: sourceBankBookCurrency=%s, targetBankBookCurrency=%s",
                    sourceBankBook.getCurrency().getCurrency(), targetBankBook.getCurrency().getCurrency()));
        }

        TransactionEntity transactionEntity = TransactionEntity.builder()
                .sourceBankBookId(sourceBankBook)
                .targetBankBookId(targetBankBook)
                .amount(amount)
                .initiationDate(LocalDateTime.now())
                .status(transactionStatusRepository.findByStatus(TransactionStatus.PROCESSING.name()))
                .build();


        if (sourceBankBook.getAmount().compareTo(amount) < 0) {
            transactionEntity.setCompletionDate(LocalDateTime.now());
            transactionEntity.setStatus(transactionStatusRepository.findByStatus(TransactionStatus.DECLINED.name()));
            transactionRepository.save(transactionEntity);

            throw new IllegalArgumentException(String.format("Not enough money on sourceBankBook: balance=%s, transactionAmount=%s", sourceBankBook.getAmount(), amount));
        } else {
            sourceBankBook.setAmount(sourceBankBook.getAmount().subtract(amount));
            targetBankBook.setAmount(targetBankBook.getAmount().add(amount));
            bankBookRepository.save(sourceBankBook);
            bankBookRepository.save(targetBankBook);

            transactionEntity.setCompletionDate(LocalDateTime.now());
            transactionEntity.setStatus(transactionStatusRepository.findByStatus(TransactionStatus.SUCCESSFUL.name()));
            transactionRepository.save(transactionEntity);
        }
    }
}
