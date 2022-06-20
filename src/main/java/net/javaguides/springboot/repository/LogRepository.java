package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.model.Log;

public interface LogRepository extends  JpaRepository<Log,Long>  {
	@Transactional
    @Modifying
    @Query("UPDATE Log a " +
            "SET a.sent = TRUE WHERE a.id = ?1")
    int enableSent(Long id);

}
