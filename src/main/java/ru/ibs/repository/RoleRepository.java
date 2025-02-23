package ru.ibs.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.ibs.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ibs.entity.User;

import java.util.Optional;

@Repository
public interface RoleRepository extends  JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByName(String name);
}