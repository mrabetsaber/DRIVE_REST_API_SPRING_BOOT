package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.HistoriqueEntity;

public interface HistoriqueRepository extends JpaRepository<HistoriqueEntity,Long> {

}
