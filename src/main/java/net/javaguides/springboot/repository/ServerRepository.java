package net.javaguides.springboot.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.model.Server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


@Repository
@Transactional(readOnly = true)
public interface ServerRepository extends JpaRepository<Server,Long> {

	
}
