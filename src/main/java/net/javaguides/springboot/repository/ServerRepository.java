package net.javaguides.springboot.repository;

import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Server;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository

public interface ServerRepository extends JpaRepository<Server,Long> {

}
