package klu.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import klu.model.Payroll;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    List<Payroll> findByEmpid(Long empid); // ✅ exact match with entity field
}
