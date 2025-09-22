package dto;

import java.time.LocalDate;
import java.util.UUID;

public class LeaveRequestDTO {
    private long empId;
    private String type;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private int days;

    // ✅ Getters & Setters
    public long getEmpId() { return empId; }
    public void setEmpId(long empId) { this.empId = empId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }
}
