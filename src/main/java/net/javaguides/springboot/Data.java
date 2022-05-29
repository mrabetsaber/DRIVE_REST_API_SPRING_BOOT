package net.javaguides.springboot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Data {
	 @Select("select COUNT(type) from GP_PRODUCTIONJOUR where type = 'Production' limit 1")
	  int  getProduction();
	 @Select("select COUNT(type) from GP_PRODUCTIONJOUR where type = 'Vignette' limit 1")
	  int  getVignette();
	 @Select("select COUNT(type) from GP_PRODUCTIONHEURE where type = 'SORTIE' limit 1")
	  int  getSORTIE();
	 @Select("select COUNT(type) from GP_PRODUCTIONHEURE where type = 'ENGAGEMENT' limit 1")
	  int  getENGAGEMENT();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_tissu = gs_bonmouvement.magasin_id where type = 'ENTREE'  limit 1")
	  int  getEntreTissu();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_fourniture = gs_bonmouvement.magasin_id where type = 'ENTREE'  limit 1")
	  int  getEntreForniture();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_fourniture = gs_bonmouvement.magasin_id where type = 'SORTIE'  limit 1")
	  int  getSortieeForniture();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_tissu = gs_bonmouvement.magasin_id where type = 'SORTIE'  limit 1")
	  int  getSortieeTissu();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_tissu = gs_bonmouvement.magasin_id where type = 'INVENTAIRE_INTERNE'  limit 1")
	  int  getINVENTAIRE_INTERNETissu();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_fourniture = gs_bonmouvement.magasin_id where type = 'INVENTAIRE_INTERNE'  limit 1")
	  int  getINVENTAIRE_INTERNEFourniture();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_fourniture = gs_bonmouvement.magasin_id where type = 'RETOUR'  limit 1")
	  int  getRETOURFourniture();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_tissu = gs_bonmouvement.magasin_id where type = 'RETOUR'  limit 1")
	  int  getRETOURTissu();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_tissu = gs_bonmouvement.magasin_id where type = 'RESERVATION'  limit 1")
	  int  getRESERVATIONTissu();
	 @Select("select COUNT(type) from backup join gs_bonmouvement on backup.magasin_fourniture = gs_bonmouvement.magasin_id where type = 'RESERVATION'  limit 1")
	  int  getRESERVATIONFourniture();
	 
	 
	 
	 
	 

}
