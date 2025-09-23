package klu.repository;

import klu.model.AddEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddEmployeeRepository extends JpaRepository<AddEmployee, String> {
}
