package ru.bykova.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bykova.project.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
