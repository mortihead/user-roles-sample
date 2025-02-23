package ru.ibs.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.ibs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @EntityGraph(attributePaths = {"roles"}) // Загрузить роли вместе с пользователем
    Optional<User> findByLogin(String login);
    Optional<User> findByUid(String uid);
}