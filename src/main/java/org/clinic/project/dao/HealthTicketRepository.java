package org.clinic.project.dao;

import java.util.List;

import org.clinic.project.model.Doctor;
import org.clinic.project.model.HealthTicket;
import org.clinic.project.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface HealthTicketRepository extends JpaRepository<HealthTicket, Integer>{
    @Query("SELECT h FROM HealthTicket h WHERE h.patientID = ?1")
    public List<HealthTicket> findByPatientID(Patient patient);
    public List<HealthTicket> deleteAllByPatientID(Patient patient); 

    @Query("SELECT h FROM HealthTicket h WHERE h.doctorID = ?1")
    public List<HealthTicket> findByDoctorID(Doctor doctor);
    public List<HealthTicket> deleteAllByDoctorID(Doctor doctor);
}
