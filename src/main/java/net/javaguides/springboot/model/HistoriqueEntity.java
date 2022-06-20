package net.javaguides.springboot.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HistoriqueEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String time;
	private String status;
	private String exception;
	
	public HistoriqueEntity( String time, String status, String exception) {
		super();
		this.time = time;
		this.status = status;
		this.exception = exception;
		
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public HistoriqueEntity( String time, String status) {
		
		
		this.time = time;
		this.status = status;
	}
	public HistoriqueEntity() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
