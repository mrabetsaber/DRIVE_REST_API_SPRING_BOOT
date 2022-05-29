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
@Table(name = IConstante.TABLE_GP_ORDREFABRICATION)
public class OrdreFabricationEntite implements Serializable {

	private static final long serialVersionUID = 1550579276203730476L;

	/** Id. */
	@Id
	@SequenceGenerator(name = "GP_ORDREFABRICATION_ID_GENERATOR", sequenceName = IConstante.SEQUENCE_GP_ORDREFABRICATION, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GP_ORDREFABRICATION_ID_GENERATOR")
	private Long id;

	/** The numero. */
	@Column(name = "numero")
	private String numero;

	/** The date introduction. */
	@Column(name = "date_introduction")
	private Calendar dateIntroduction;

}
