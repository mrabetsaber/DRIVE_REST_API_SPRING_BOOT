package net.javaguides.springboot.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name = "ParametrageHistorique")
public class ParametrageHistoriqueEntity {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	private String tableName;
	private String time;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "HistoriqueUtilisation_ParametrageHistorique",
		joinColumns = { @JoinColumn(name = "parametrage_id")},
		inverseJoinColumns = { @JoinColumn (name = "historique_id")})
	@JsonIgnore
	private Set<HistoriqueUtilisation> historiqueUtilisation = new HashSet<>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Set<HistoriqueUtilisation> getHistoriqueUtilisation() {
		return historiqueUtilisation;
	}
	public void setHistoriqueUtilisation(Set<HistoriqueUtilisation> historiqueUtilisation) {
		this.historiqueUtilisation = historiqueUtilisation;
	}
	public ParametrageHistoriqueEntity(long id, String tableName, String time,
			Set<HistoriqueUtilisation> historiqueUtilisation) {
		super();
		this.id = id;
		this.tableName = tableName;
		this.time = time;
		this.historiqueUtilisation = historiqueUtilisation;
	}
	public ParametrageHistoriqueEntity() {
			}
	
	 public void addHistorique(HistoriqueUtilisation _server) {
		    this.historiqueUtilisation.add(_server);
		    _server.getParametrageHistoriques().add(this);
		  }
	

}

