package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.model.ParametrageBackupEntity;
@Repository
public interface ParametrageBackupRepository extends  JpaRepository<ParametrageBackupEntity,Long>  {
	
	


}
