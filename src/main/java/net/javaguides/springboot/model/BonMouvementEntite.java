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
@Table(name = IConstante.TABLE_BON_MOUVEMENT)
public class BonMouvementEntite implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3188463288940118609L;

	/** The id. */
	@Id
	@SequenceGenerator(name = "GS_BONMOUVEMENT_ID_GENERATOR", sequenceName = IConstante.SEQUENCE_BON_MOUVEMENT)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GS_BONMOUVEMENT_ID_GENERATOR")
	private Long id;

	/** The date. */
	@Column(name = "date")
	private Calendar date;


	/** The type. */
	@Column(name = "type")
	private String type;

      @Column(name = "magasin_id")
	private Long magasinId;
}

