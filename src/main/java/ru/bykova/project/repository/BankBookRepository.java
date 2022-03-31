package ru.bykova.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bykova.project.entity.BankBookEntity;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Integer> {
}
