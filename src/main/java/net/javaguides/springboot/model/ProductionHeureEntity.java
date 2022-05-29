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
@Table(name=IConstante.TABLE_GP_PRODUCTIONHEURE)
public class ProductionHeureEntity implements Serializable {
	private static final long serialVersionUID = 5055372248116517982L;


	@Id
	@SequenceGenerator(name="PRODUCTIONHEURE_ID_GENERATOR", sequenceName=IConstante.SEQUENCE_GP_PRODUCTIONHEURE, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCTIONHEURE_ID_GENERATOR")
    private Long id;
	
		@Column(name = "type")
	private String type ;
		
	
	@Column(name = "DATE")
	private Calendar date;

}

