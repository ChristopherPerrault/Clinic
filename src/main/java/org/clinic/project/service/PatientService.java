package org.clinic.project.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.clinic.project.dao.PatientRepository;
import org.clinic.project.model.Patient;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    // READ ALL
    public List<Patient> findAll() {
        return patientRepo.findAll();
    }

    // READ BY ID
    public Patient get(int patientID) {
        return patientRepo.findById(patientID).get();
    }

    // CREATE
    public void save(Patient patient) {
        patientRepo.save(patient);
    }

    // DELETE
    public void delete(int patientID) {
        patientRepo.deleteById(patientID);
    }
}
