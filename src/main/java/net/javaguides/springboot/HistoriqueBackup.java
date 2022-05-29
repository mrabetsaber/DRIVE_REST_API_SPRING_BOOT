package net.javaguides.springboot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class HistoriqueBackup {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	public int Production;
	public int Vignette;
	public int SORTIE;
	public int ENGAGEMENT;
	public int ENTREE_Tissu ;
	public int ENTREE_Fourniture ;
	public int SORTIE_Tissu;
	public int SORTIE_Fourniture;
	public int INVENTAIRE_INTERNE_Tissu;
	public int INVENTAIRE_INTERNE_Fourniture;
	public int RETOUR_Tissu;
	public int RETOUR_Fourniture;
	public int RESERVATION_Tissu;
	public int RESERVATION_Fourniture;
	public String time;
	
	
	
	
	
	public HistoriqueBackup(int production, int vignette, int sORTIE, int eNGAGEMENT, int eNTREE_Tissu,
			int eNTREE_Fourniture, int sORTIE_Tissu, int sORTIE_Fourniture, int iNVENTAIRE_INTERNE_Tissu,
			int iNVENTAIRE_INTERNE_Fourniture, int rETOUR_Tissu, int rETOUR_Fourniture, int rESERVATION_Tissu,
			int rESERVATION_Fourniture, String time) {
		super();
		Production = production;
		Vignette = vignette;
		SORTIE = sORTIE;
		ENGAGEMENT = eNGAGEMENT;
		ENTREE_Tissu = eNTREE_Tissu;
		ENTREE_Fourniture = eNTREE_Fourniture;
		SORTIE_Tissu = sORTIE_Tissu;
		SORTIE_Fourniture = sORTIE_Fourniture;
		INVENTAIRE_INTERNE_Tissu = iNVENTAIRE_INTERNE_Tissu;
		INVENTAIRE_INTERNE_Fourniture = iNVENTAIRE_INTERNE_Fourniture;
		RETOUR_Tissu = rETOUR_Tissu;
		RETOUR_Fourniture = rETOUR_Fourniture;
		RESERVATION_Tissu = rESERVATION_Tissu;
		RESERVATION_Fourniture = rESERVATION_Fourniture;
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public HistoriqueBackup() {
		super();
	}
	public HistoriqueBackup(int production, int vignette, int sORTIE, int eNGAGEMENT, int eNTREE_Tissu,
			int eNTREE_Fourniture, int sORTIE_Tissu, int sORTIE_Fourniture, int iNVENTAIRE_INTERNE_Tissu,
			int iNVENTAIRE_INTERNE_Fourniture, int rETOUR_Tissu, int rETOUR_Fourniture, int rESERVATION_Tissu,
			int rESERVATION_Fourniture) {
		super();
		Production = production;
		Vignette = vignette;
		SORTIE = sORTIE;
		ENGAGEMENT = eNGAGEMENT;
		ENTREE_Tissu = eNTREE_Tissu;
		ENTREE_Fourniture = eNTREE_Fourniture;
		SORTIE_Tissu = sORTIE_Tissu;
		SORTIE_Fourniture = sORTIE_Fourniture;
		INVENTAIRE_INTERNE_Tissu = iNVENTAIRE_INTERNE_Tissu;
		INVENTAIRE_INTERNE_Fourniture = iNVENTAIRE_INTERNE_Fourniture;
		RETOUR_Tissu = rETOUR_Tissu;
		RETOUR_Fourniture = rETOUR_Fourniture;
		RESERVATION_Tissu = rESERVATION_Tissu;
		RESERVATION_Fourniture = rESERVATION_Fourniture;
	}
	public int getProduction() {
		return Production;
	}
	public void setProduction(int production) {
		Production = production;
	}
	public int getVignette() {
		return Vignette;
	}
	public void setVignette(int vignette) {
		Vignette = vignette;
	}
	public int getSORTIE() {
		return SORTIE;
	}
	public void setSORTIE(int sORTIE) {
		SORTIE = sORTIE;
	}
	public int getENGAGEMENT() {
		return ENGAGEMENT;
	}
	public void setENGAGEMENT(int eNGAGEMENT) {
		ENGAGEMENT = eNGAGEMENT;
	}
	public int getENTREE_Tissu() {
		return ENTREE_Tissu;
	}
	public void setENTREE_Tissu(int eNTREE_Tissu) {
		ENTREE_Tissu = eNTREE_Tissu;
	}
	public int getENTREE_Fourniture() {
		return ENTREE_Fourniture;
	}
	public void setENTREE_Fourniture(int eNTREE_Fourniture) {
		ENTREE_Fourniture = eNTREE_Fourniture;
	}
	public int getSORTIE_Tissu() {
		return SORTIE_Tissu;
	}
	public void setSORTIE_Tissu(int sORTIE_Tissu) {
		SORTIE_Tissu = sORTIE_Tissu;
	}
	public int getSORTIE_Fourniture() {
		return SORTIE_Fourniture;
	}
	public void setSORTIE_Fourniture(int sORTIE_Fourniture) {
		SORTIE_Fourniture = sORTIE_Fourniture;
	}
	public int getINVENTAIRE_INTERNE_Tissu() {
		return INVENTAIRE_INTERNE_Tissu;
	}
	public void setINVENTAIRE_INTERNE_Tissu(int iNVENTAIRE_INTERNE_Tissu) {
		INVENTAIRE_INTERNE_Tissu = iNVENTAIRE_INTERNE_Tissu;
	}
	public int getINVENTAIRE_INTERNE_Fourniture() {
		return INVENTAIRE_INTERNE_Fourniture;
	}
	public void setINVENTAIRE_INTERNE_Fourniture(int iNVENTAIRE_INTERNE_Fourniture) {
		INVENTAIRE_INTERNE_Fourniture = iNVENTAIRE_INTERNE_Fourniture;
	}
	public int getRETOUR_Tissu() {
		return RETOUR_Tissu;
	}
	public void setRETOUR_Tissu(int rETOUR_Tissu) {
		RETOUR_Tissu = rETOUR_Tissu;
	}
	public int getRETOUR_Fourniture() {
		return RETOUR_Fourniture;
	}
	public void setRETOUR_Fourniture(int rETOUR_Fourniture) {
		RETOUR_Fourniture = rETOUR_Fourniture;
	}
	public int getRESERVATION_Tissu() {
		return RESERVATION_Tissu;
	}
	public void setRESERVATION_Tissu(int rESERVATION_Tissu) {
		RESERVATION_Tissu = rESERVATION_Tissu;
	}
	public int getRESERVATION_Fourniture() {
		return RESERVATION_Fourniture;
	}
	public void setRESERVATION_Fourniture(int rESERVATION_Fourniture) {
		RESERVATION_Fourniture = rESERVATION_Fourniture;
	}
	
	
	
	
	
	
	
	
	
	
	
}
