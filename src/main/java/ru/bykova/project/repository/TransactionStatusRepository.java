package ru.bykova.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bykova.project.model.entity.TransactionStatusEntity;

public interface TransactionStatusRepository extends JpaRepository<TransactionStatusEntity, Integer> {

    TransactionStatusEntity findByStatus(String transactionStatus);

}
