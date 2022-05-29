package net.javaguides.springboot.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name=IConstante.TABLE_GC_LIVRAISONVENTE)
public class LivraisonVenteEntity implements Serializable{

	private static final long serialVersionUID = 7019588587457251275L;

	@Id
	@SequenceGenerator(name="LIVRAISONVENTE_ID_GENERATOR", sequenceName=IConstante.SEQUENCE_GC_CLV_SEQ, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LIVRAISONVENTE_ID_GENERATOR")
    private Long id;
	
	@Column(name="REFERENCE")
	private String reference;
	
	@Column(name="DATE")
	private Calendar date;
}

