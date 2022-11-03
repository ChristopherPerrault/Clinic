package org.clinic.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.clinic.project.dao.DoctorRepository;
import org.clinic.project.model.CustomDoctorDetails;
import org.clinic.project.model.Doctor;

public class CustomDoctorDetailsService implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepo.findByDoctorID(username);
        if (doctor == null) {
            throw new UsernameNotFoundException("Doctor not found");
        }
        return new CustomDoctorDetails(doctor);
    }
}
