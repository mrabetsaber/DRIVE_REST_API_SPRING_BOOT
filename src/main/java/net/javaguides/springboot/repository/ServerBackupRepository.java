package net.javaguides.springboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.model.Server_Backups;
@Transactional(readOnly = true)
@Repository
public interface ServerBackupRepository  extends JpaRepository<Server_Backups,Long> {

	
}
