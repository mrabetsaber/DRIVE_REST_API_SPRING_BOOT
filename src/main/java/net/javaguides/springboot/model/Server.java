package net.javaguides.springboot.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.appuser.AppUser;


@Entity
@Table(name = "servers")
@Getter
@Setter

public class Server {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
    private String host;
	
	@Column(name = "user_name")
    private String userName;
	
	@Column(name = "password")
    private String password;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private AppUser user;
	
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "server_backups",
		joinColumns = { @JoinColumn(name = "server_id")},
		inverseJoinColumns = { @JoinColumn (name = "backup_id")})
	private Set<ParametrageBackupEntity> backups = new HashSet<>();
    
    public Server() {
    	
    }
    

	public Server(String host, String userName, String password) {
		super();
		this.host = host;
		this.userName = userName;
		this.password = password;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ParametrageBackupEntity> getBackups() {
		return backups;
	}

	public void setBackups(Set<ParametrageBackupEntity> backups) {
		this.backups = backups;
	}


	 public void addBackup(ParametrageBackupEntity _server) {
	    this.backups.add(_server);
	    _server.getServers().add(this);
	  }
	  
	  public void removeTag(long tagId) {
		  ParametrageBackupEntity tag = this.backups.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
	    if (tag != null) this.backups.remove(tag);
	    tag.getServers().remove(this);
	  }
    
	
}