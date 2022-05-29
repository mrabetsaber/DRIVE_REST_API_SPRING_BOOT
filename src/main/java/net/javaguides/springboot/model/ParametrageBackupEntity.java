package net.javaguides.springboot.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table( name = "backups")
public class ParametrageBackupEntity {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	private String dataBaseName;
	private String clientName;
	private String schedule;
	private String emailSender;
	private String emailReceiver;


	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      }, mappedBy = "backups")
	@JsonIgnore
	private Set<Server> servers = new HashSet<>();
	
	
	
	public ParametrageBackupEntity(String dataBaseName, String clientName, String schedule) {
		super();
		this.dataBaseName = dataBaseName;
		this.clientName = clientName;
		this.schedule = schedule;
	}

	public ParametrageBackupEntity() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	

	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public String getEmailReceiver() {
		return emailReceiver;
	}

	public void setEmailReceiver(String emailReceiver) {
		this.emailReceiver = emailReceiver;
	}

	public ParametrageBackupEntity(String dataBaseName, String clientName, String schedule, String emailSender,
			String emailReceiver) {
		super();
		this.dataBaseName = dataBaseName;
		this.clientName = clientName;
		this.schedule = schedule;
		this.emailSender = emailSender;
		this.emailReceiver = emailReceiver;
	}

	 public Set<Server> getServers() {
		return servers;
	}

	public void setServers(Set<Server> servers) {
		this.servers = servers;
	}

	// getters and setters
	  public void addServer(Server tag) {
	    this.servers.add(tag);
	    tag.getBackups().add(this);
	  }
	  
	  public void removeTag(long tagId) {
	    Server tag = this.servers.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
	    if (tag != null) this.servers.remove(tag);
	    tag.getBackups().remove(this);
	  }

	
	
}