		package klu.model;
		
		import jakarta.persistence.*;
		import java.time.LocalDate;
		import java.util.UUID;
		
		@Entity
		@Table(name = "leaves")
		public class Leave {
		
		    @Id
		    @GeneratedValue(strategy = GenerationType.UUID)
		    private UUID leaveId;   
		
		    @ManyToOne(fetch = FetchType.LAZY)
		    @JoinColumn(name = "empid", referencedColumnName = "empid", nullable = false)
		    private Users user;
		    
		    @Column(name = "type")
		    private String type;
		
		    @Column(name = "reason")
		    private String reason;
		
		    private LocalDate startDate;
		    private LocalDate endDate;
		    private int days;
		
		    private String status = "Pending"; // default
		
		    private LocalDate appliedDate = LocalDate.now();
		
		    // ✅ Getters & Setters
		    public UUID getLeaveId() { return leaveId; }
		    public void setLeaveId(UUID leaveId) { this.leaveId = leaveId; }
		
		    public Users getUser() { return user; }
		    public void setUser(Users user) { this.user = user; }
		
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
		
		    public String getStatus() { return status; }
		    public void setStatus(String status) { this.status = status; }
		
		    public LocalDate getAppliedDate() { return appliedDate; }
		    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }
		}
