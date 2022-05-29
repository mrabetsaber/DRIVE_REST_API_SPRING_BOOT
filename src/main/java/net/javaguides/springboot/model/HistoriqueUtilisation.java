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
	private String lineNumber;
	@ManyToMany(fetch = FetchType.LAZY, cascade = {
	          CascadeType.PERSIST,
	          CascadeType.MERGE
	      }, mappedBy = "historiqueUtilisation")
	@JsonIgnore
	
	private Set<ParametrageHistoriqueEntity> parametrageHistoriques= new HashSet<>();
	public long getId() {
		return id;
	}
	public HistoriqueUtilisation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HistoriqueUtilisation(long id, String lineNumber, Set<ParametrageHistoriqueEntity> parametrage_id) {
		super();
		this.id = id;
		this.lineNumber = lineNumber;
		this.parametrageHistoriques = parametrage_id;
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
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	 public void addParametrageHistorique(ParametrageHistoriqueEntity _server) {
		    this.parametrageHistoriques.add(_server);
		    _server.getHistoriqueUtilisation().add(this);
		  }
}
