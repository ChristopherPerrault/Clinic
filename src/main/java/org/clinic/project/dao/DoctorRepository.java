package org.clinic.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.clinic.project.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query("SELECT d FROM Doctor d WHERE d.DoctorID = ?1")
    public Doctor findByDoctorID(int doctorID);
}