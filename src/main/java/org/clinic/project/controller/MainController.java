package org.clinic.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.clinic.project.model.Doctor;
import org.clinic.project.model.HealthTicket;
import org.clinic.project.model.Patient;
import org.clinic.project.service.DoctorService;
//import org.clinic.project.service.DoctorService;
import org.clinic.project.service.HealthTicketService;
import org.clinic.project.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    /*-------------------------- SERVICES  --------------------------*/
    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private HealthTicketService healthTicketService;

    /*-------------------------- HOMEPAGE  --------------------------*/
	@RequestMapping("")
	public String viewHomePageSignedOut() {
		return "index";
	}

    /*-------------------------- PATIENT REGISTRATION --------------------------*/
	/*---- REGISTER PATIENT ----*/
	@RequestMapping("/patient_register")
	public String showPatientRegistrationForm(Model model) {
		model.addAttribute("patient", new Patient());
		return "patient_register";
	}

	/*---- PROCESS REGISTER ----*/
	@PostMapping("/process_patient_register")
	public String processPatientRegister(@ModelAttribute("patient") Patient patient) {

		// if (bindingResult.hasErrors()) {
		// 	return "patient_register";
		// } else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(patient.getPassword());
			patient.setPassword(encodedPassword);
			patientService.save(patient);
			return "redirect:/patient/login";
		//}
	}

	@RequestMapping("/patient/login")
	public String viewPatientloginpage() {
		return "patient_login";
	}

	/*-------------------------- PATIENT (SIGNED IN) --------------------------*/
    @RequestMapping("/patient/homepage")
	public String welcomePatient(Patient patient) {

		return "patient_homepage";
	}

	 /*-------------------------- DOCTOR REGISTRATION --------------------------*/
	/*---- REGISTER DOCTOR ----*/
	@RequestMapping("/doctor_register")
	public String showDoctorRegistrationForm(Model model) {
		model.addAttribute("doctor", new Doctor());
		return "doctor_register";
	}

	/*---- PROCESS REGISTER ----*/
	@PostMapping("/process_doctor_register")
	public String processDoctorRegister(@ModelAttribute("doctor") Doctor doctor) {

		// if (bindingResult.hasErrors()) {
		// 	return "patient_register";
		// } else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(doctor.getPassword());
			doctor.setPassword(encodedPassword);
			doctorService.save(doctor);
			return "redirect:/doctor/login";
		//}
	}

	@RequestMapping("/doctor/login")
	public String viewDoctorloginpage() {
		return "doctor_login";
	}



    /*-------------------------- DOCTOR (SIGNED IN) --------------------------*/
    @RequestMapping("/doctor/homepage")
	public String welcomeDoctor(Doctor doctor) {

		return "doctor_homepage";
	}

    /*---- VIEW ALL PATIENT TICKETS ----*/
	@RequestMapping("/view_patient_tickets")
	public String listPatientTickets(Model model) {
		List<HealthTicket> listPatientTickets = healthTicketService.findAll();
		model.addAttribute("listPatientTickets", listPatientTickets);
		return "templatePlaceholder";
	}

    /*---- ADD PATIENT TICKET ----*/
	@RequestMapping("/view_patient_tickets/add_ticket")
	public String addPatientTicket(Patient patient) {

		return "templatePlaceholder";
	}

    /*---- PROCESS ADDED PATIENT TICKET ----*/
	@PostMapping("/view_patient_tickets/process_ticket")
	public String processTeacher(@Valid HealthTicket healthTicket, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "templatePlaceholder";

		} else {
			healthTicketService.save(healthTicket);
			return "redirect:view_patient_tickets";
		}
	}

    /*---- DELETE PATIENT TICKET ----*/
	@RequestMapping("/view_patient_tickets/delete/{id}")
	public String deletePatientTicket(@PathVariable(name = "id") int id) {
		healthTicketService.delete(id);
		return "redirect:/view_patient_tickets";
	}

    /*---- EDIT PATIENT TICKET ----*/
	@RequestMapping("/view_patient_tickets/edit/{id}")
	public ModelAndView showEditPatientTicket(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("templatePlaceholder");
        HealthTicket healthTicket = healthTicketService.get(id);
        mav.addObject("healthTicket", healthTicket);
		return mav;
	}

	/*---- PROCESS EDITED PATIENT TICKET ----*/
	@PostMapping("/view_patient_tickets/process_update_ticket")
	public String processUpdateTicket(@Valid HealthTicket healthTicket, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "templatePlaceholder";

		} else {
			healthTicketService.save(healthTicket);
			return "redirect:view_patient_tickets";
		}
	}
}

