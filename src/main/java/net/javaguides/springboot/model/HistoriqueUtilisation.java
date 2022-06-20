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
@Table( name = "HistoriqueUtilisation")
public class HistoriqueUtilisation {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	private Integer PostNumber;
	private Integer deleteNumber;
	private Integer updateNumber;
	private Integer getNumber;
	private String tableName;
	private String date;
	@ManyToMany(fetch = FetchType.LAZY, cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      }, mappedBy = "historiqueUtilisation")
	@JsonIgnore
	
	private Set<ParametrageHistoriqueEntity> parametrageHistoriques= new HashSet<>();
	public HistoriqueUtilisation(int postNumber, int deleteNumber, int updateNumber, int getNumber, String tableName,
			String date, Set<ParametrageHistoriqueEntity> parametrageHistoriques) {
		super();
		PostNumber = postNumber;
		this.deleteNumber = deleteNumber;
		this.updateNumber = updateNumber;
		this.getNumber = getNumber;
		this.tableName = tableName;
		this.date = date;
		this.parametrageHistoriques = parametrageHistoriques;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public long getId() {
		return id;
	}
	public HistoriqueUtilisation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HistoriqueUtilisation(int postNumber, int deleteNumber, int updateNumber, int getNumber, String date,
			Set<ParametrageHistoriqueEntity> parametrageHistoriques) {
		super();
		PostNumber = postNumber;
		this.deleteNumber = deleteNumber;
		this.updateNumber = updateNumber;
		this.getNumber = getNumber;
		this.date = date;
		this.parametrageHistoriques = parametrageHistoriques;
	}
	public int getPostNumber() {
		return PostNumber;
	}
	public void setPostNumber(int postNumber) {
		PostNumber = postNumber;
	}
	public int getDeleteNumber() {
		return deleteNumber;
	}
	public void setDeleteNumber(int deleteNumber) {
		this.deleteNumber = deleteNumber;
	}
	public int getUpdateNumber() {
		return updateNumber;
	}
	public void setUpdateNumber(int updateNumber) {
		this.updateNumber = updateNumber;
	}
	public int getGetNumber() {
		return getNumber;
	}
	public void setGetNumber(int getNumber) {
		this.getNumber = getNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Set<ParametrageHistoriqueEntity> getParametrageHistoriques() {
		return parametrageHistoriques;
	}
	public void setParametrageHistoriques(Set<ParametrageHistoriqueEntity> parametrage_id) {
		this.parametrageHistoriques = parametrage_id;
	}
	public void setId(long id) {
		this.id = id;
	}


	 public void addParametrageHistorique(ParametrageHistoriqueEntity _server) {
		    this.parametrageHistoriques.add(_server);
		    _server.getHistoriqueUtilisation().add(this);
		  }
}
