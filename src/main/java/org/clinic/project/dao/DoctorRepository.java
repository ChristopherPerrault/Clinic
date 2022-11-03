package org.clinic.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.clinic.project.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
    @Query("SELECT d FROM Doctor d WHERE d.doctorID = ?1")
    public Doctor findByDoctorID(String doctorID);
}