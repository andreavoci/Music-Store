package com.example.musicstore.repository;

import com.example.musicstore.entity.Role;
import com.example.musicstore.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Boolean existsByRole(UserRole role);
    Optional<Object> findByRole(UserRole role);
}
