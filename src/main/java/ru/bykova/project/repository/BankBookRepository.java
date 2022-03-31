package ru.bykova.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bykova.project.model.entity.BankBookEntity;

import java.util.Optional;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Integer> {

    Optional<BankBookEntity> findByNumber(String number);

}
