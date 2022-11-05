package org.clinic.project.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.clinic.project.dao.DoctorRepository;
import org.clinic.project.model.Doctor;

@Service
@Transactional
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    // READ ALL
    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }

    // READ BY ID
    public Doctor get(String doctorID) {
        return doctorRepo.findById(doctorID).get();
    }

    // GET LOGGED IN PATIENT
    public Doctor getLoggedInDoctor(String doctorID) {
        return doctorRepo.findByDoctorID(doctorID);
    }

    // CREATE
    public void save(Doctor doctor) {
        doctorRepo.save(doctor);
    }

    // DELETE
    public void delete(String doctorID) {
        doctorRepo.deleteById(doctorID);
    }

    public boolean doctorExists(String doctorID) {
        return doctorRepo.existsById(doctorID);
    }
}
