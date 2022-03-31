package ru.bykova.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bykova.project.model.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
}
