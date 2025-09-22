package klu.repository;

import klu.model.EmployeePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePerformanceRepository extends JpaRepository<EmployeePerformance, Long> {
}
