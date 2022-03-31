package ru.bykova.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bykova.project.model.entity.BankBookEntity;
import ru.bykova.project.model.entity.CurrencyEntity;
import ru.bykova.project.model.entity.UserEntity;
import ru.bykova.project.mapper.BankBookMapper;
import ru.bykova.project.model.dto.BankBookDto;
import ru.bykova.project.repository.BankBookRepository;
import ru.bykova.project.repository.CurrencyRepository;
import ru.bykova.project.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankBookServiceImpl implements BankBookService {

    private final BankBookRepository bankBookRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;

    public BankBookServiceImpl(BankBookRepository bankBookRepository,
                               CurrencyRepository currencyRepository,
                               UserRepository userRepository) {
        this.bankBookRepository = bankBookRepository;
        this.currencyRepository = currencyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BankBookDto> getAllBankBooksInStorage() {
        return bankBookRepository.findAll()
                .stream()
                .map(BankBookMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BankBookDto getBankBookByBankBookId(Integer bankBookId) {
        try {
            BankBookEntity bankBookEntity = bankBookRepository.getById(bankBookId);
            return BankBookMapper.entityToDto(bankBookEntity);
        } catch (EntityNotFoundException e) {
            throw new NoSuchElementException(String.format("BankBook with id=%s don't exist!", bankBookId));
        }

    }

    @Override
    public BankBookDto createBankBook(BankBookDto bankBookDto) {
        String currency = bankBookDto.getCurrency();

        CurrencyEntity currencyEntity = extractCurrencyFromDict(currency);
        UserEntity userEntity = extractUserFromDb(bankBookDto.getUserId());

        BankBookEntity bankBookEntity = BankBookMapper.dtoToEntity(bankBookDto);
        bankBookEntity.setUser(userEntity);
        bankBookEntity.setCurrency(currencyEntity);
        bankBookRepository.save(bankBookEntity);

        return BankBookMapper.entityToDto(bankBookEntity);
    }

    @Override
    public BankBookDto updateBankBook(BankBookDto bankBookDto) {
        Optional<BankBookEntity> bankBookEntityOptional = bankBookRepository.findById(bankBookDto.getId());

        if (bankBookEntityOptional.isPresent()) {
            BankBookEntity bankBookEntity = bankBookEntityOptional.get();

            if (!bankBookEntity.getNumber().equalsIgnoreCase(bankBookDto.getNumber())) {
                throw new IllegalArgumentException("Can't change bankBook number!");
            }

            CurrencyEntity currencyEntity = extractCurrencyFromDict(bankBookDto.getCurrency());
            UserEntity userEntity = extractUserFromDb(bankBookDto.getUserId());

            bankBookEntity.setUser(userEntity);
            bankBookEntity.setNumber(bankBookDto.getNumber());
            bankBookEntity.setAmount(bankBookDto.getAmount());
            bankBookEntity.setCurrency(currencyEntity);

            bankBookRepository.save(bankBookEntity);
            return BankBookMapper.entityToDto(bankBookEntity);
        }

        throw new IllegalArgumentException(String.format("BankBook with id=%s don't exist!", bankBookDto.getId()));
    }

    @Override
    public void deleteBankBookByBankBookId(Integer bankBookId) {
        bankBookRepository.deleteById(bankBookId);
    }

    private CurrencyEntity extractCurrencyFromDict(String currency) {
        CurrencyEntity byCurrency = currencyRepository.findByCurrency(currency);

        if (byCurrency == null) {
            throw new IllegalArgumentException(String.format("Currency %s is invalid!", currency));
        }

        return byCurrency;
    }

    private UserEntity extractUserFromDb(Integer userId) {
        Optional<UserEntity> userById = userRepository.findById(userId);

        if (userById.isPresent()) {
            return userById.get();
        } else {
            throw new IllegalArgumentException(String.format("User with id=%s don't exist!", userId));
        }
    }

}
