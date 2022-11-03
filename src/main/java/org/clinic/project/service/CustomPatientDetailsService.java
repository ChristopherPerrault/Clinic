package org.clinic.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.clinic.project.dao.PatientRepository;
import org.clinic.project.model.CustomPatientDetails;
import org.clinic.project.model.Patient;

public class CustomPatientDetailsService implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = patientRepo.findByPatientID(username);
        if (patient == null) {
            throw new UsernameNotFoundException("Patient not found");
        }
        return new CustomPatientDetails(patient);
    }
}
