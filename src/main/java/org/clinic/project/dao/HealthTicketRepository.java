package org.clinic.project.dao;

import org.clinic.project.model.HealthTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthTicketRepository extends JpaRepository<HealthTicket, Integer>{
    
}
