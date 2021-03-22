package ru.vohmin.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vohmin.springboot.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}