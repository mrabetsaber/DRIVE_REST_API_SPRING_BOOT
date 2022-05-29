package net.javaguides.springboot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper

public interface CronMapper {
	

  @Select("select schedule from backups ")

  String[] getCron();
  
  @Select("select email_sender from backups ")

  String[] getSender();
  
  @Select("select email_receiver from backups ")
  String [] getReciver();
  
  
  
 
  

  @Select("select client_name from backups ")
  String [] getClient();
  
  @Select("select data_base_name from backups")
  String [] getsDataBaseName();
  
  



}