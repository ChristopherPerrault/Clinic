package org.clinic.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.clinic.project.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query("SELECT p FROM Patient p WHERE p.patientID = ?1")
    public Patient findByPatientID(int patientID);
}