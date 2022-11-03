package org.clinic.project.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.clinic.project.dao.HealthTicketRepository;
import org.clinic.project.model.HealthTicket;

@Service
@Transactional
public class HealthTicketService {

    @Autowired
    private HealthTicketRepository healthRepo;

    // READ ALL
    public List<HealthTicket> findAll() {
        return healthRepo.findAll();
    }

    // READ BY ID
    public HealthTicket get(int ticketID) {
        return healthRepo.findById(ticketID).get();
    }

    // CREATE
    public void save(HealthTicket healthTicket) {
        healthRepo.save(healthTicket);
    }

    // DELETE
    public void delete(int ticketID) {
        healthRepo.deleteById(ticketID);
    }
}

