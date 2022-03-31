package ru.bykova.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.bykova.project.model.entity.CurrencyEntity;

public interface CurrencyRepository extends PagingAndSortingRepository<CurrencyEntity, Integer> {

    CurrencyEntity findByCurrency(String currency);

}
