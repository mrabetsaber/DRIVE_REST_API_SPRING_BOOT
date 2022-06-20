package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.ErrorReceiver;

@Repository
public interface ErrorReceiverRepository extends JpaRepository<ErrorReceiver,Long> {

}
