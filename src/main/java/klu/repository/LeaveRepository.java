package klu.repository;

import klu.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {
    List<Leave> findByUserEmpid(Long empId);
 // in LeaveRepository.java
    List<Leave> findByAppliedDate(LocalDate appliedDate);

}
