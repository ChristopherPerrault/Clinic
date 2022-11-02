package org.clinic.project.controller;

import org.clinic.project.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    /*-------------------------- SERVICES  --------------------------*/
    // @Autowired
    // PatientService patientService;

    // @Autowired
    // DoctorService doctorService;

    // @Autowired
    // HealthTicketService healthTicketService;

    /*-------------------------- HOMEPAGE  --------------------------*/
	@GetMapping("")
	public String viewHomePageSignedOut() {
		return "templatePlaceholder";
	}

    /*-------------------------- PATIENT REGISTRATION --------------------------*/
	/*---- REGISTER PATIENT ----*/
	@GetMapping("/register_patient")
	public String showPatientRegistrationForm(Patient patient) {

		return "templatePlaceholder";
	}

	/*---- PROCESS REGISTER ----*/
	@PostMapping("/process_patient_register")
	public String processPatientRegister(Patient patient, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "templatePlaceholder";
		} else {
			// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			// String encodedPassword = passwordEncoder.encode(patient.getPassword());
			// patient.setPassword(encodedPassword);
			// patientService.save(patient);
			return "redirect:patient_homepage";
		}
	}

    /*-------------------------- PATIENT (SIGNED IN) --------------------------*/
    @RequestMapping("/patient_homepage")
	public String welcomePatient(Patient patient) {

		return "templatePlaceholder";
	}
}
