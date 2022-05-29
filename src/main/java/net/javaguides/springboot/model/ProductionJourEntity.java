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
@Table(name=IConstante.TABLE_GP_PRODUCTIONJOUR)
public class ProductionJourEntity implements Serializable {

	private static final long serialVersionUID = 8173475315443424028L;

	@Id
	@SequenceGenerator(name="PRODUCTIONJOUR_ID_GENERATOR", sequenceName=IConstante.SEQUENCE_GP_PRODUCTIONJOUR, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRODUCTIONJOUR_ID_GENERATOR")
    private Long id;
	
	
	
		@Column(name = "type")
	private String type ;
		
	
	@Column(name = "DATE")
	private Calendar date;
		
}

