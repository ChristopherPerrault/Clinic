package org.clinic.project.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.clinic.project.dao.HealthTicketRepository;
import org.clinic.project.model.Doctor;
import org.clinic.project.model.HealthTicket;
import org.clinic.project.model.Patient;

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

    // READ BY ID
    public List<HealthTicket> getByPatient(Patient patient) {
        return healthRepo.findByPatientID(patient);
    }

    // CREATE
    public void save(HealthTicket healthTicket) {
        healthRepo.save(healthTicket);
    }

    // DELETE
    public void delete(int ticketID) {
        healthRepo.deleteById(ticketID);
    }

    // DELETE BY PatientID
    public List<HealthTicket> deleteAllByPatientID(Patient patient) {
        return healthRepo.deleteAllByPatientID(patient);
    }

    // DELETE BY DoctorID
    public List<HealthTicket> deleteAllByDoctorID(Doctor doctor) {
        return healthRepo.deleteAllByDoctorID(doctor);
    }

    // update
    public void update(HealthTicket healthTicket) {
        healthTicket.setTicketID(healthTicket.getTicketID());
        healthRepo.save(healthTicket);
    }
}
