package klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import klu.model.AssignRole;

@Repository
public interface AssignRoleRepository extends JpaRepository<AssignRole, Long> {
}
