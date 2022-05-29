package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.springboot.model.HistoriqueUtilisation;

public interface HistoriqueUtilisationRepository extends  JpaRepository<HistoriqueUtilisation,Long> {

}
