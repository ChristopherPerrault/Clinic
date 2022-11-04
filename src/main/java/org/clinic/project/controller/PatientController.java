package org.clinic.project.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.clinic.project.model.CustomPatientDetails;
import org.clinic.project.model.Patient;
import org.clinic.project.service.HealthTicketService;
import org.clinic.project.service.PatientService;

@Controller
public class PatientController {

	/*---- SERVICES ----*/
	@Autowired
	private PatientService patientService;

	@Autowired
	private HealthTicketService healthTicketService;

	/*-------------------------- PATIENT REGISTRATION --------------------------*/

	/*---- REGISTER PATIENT ----*/
	@GetMapping("/patient_register")
	public String showPatientRegistrationForm(Model model) {
		model.addAttribute("patient", new Patient());
		return "patient_register";
	}

	/*---- PROCESS REGISTER ----*/
	@PostMapping("/process_patient_register")
	public String processPatientRegister(@Valid Patient patient, BindingResult bindingResult) {
		if (patient.getDob() == null) {
			bindingResult.rejectValue("dob", "patient.dob", "Birthday cannot be blank!");
			return "patient_register";
		}
		if (!(patient.getPlainPassword().contentEquals(patient.getPassword()))) {
			bindingResult.rejectValue("password", "patient.password", "Passwords do not match!");
		}
		if (bindingResult.hasErrors()) {
			return "patient_register";
		} else {
			patient.setPassword(patient.getPlainPassword());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(patient.getPassword());
			patient.setPassword(encodedPassword);
			patientService.save(patient);
			return "redirect:patient/login";
		}
	}

	/*-------------------------- PATIENT (LOGIN && LOGOUT PAGE) --------------------------*/
	@RequestMapping("/patient/login")
	public String patientLogInPage(Patient patient) {

		return "patient_login";
	}

	@RequestMapping("/patient/logout")
	public String patientLogoutPage(Patient patient) {

		return "logged_out";
	}

	/*-------------------------- PATIENT (SIGNED IN) --------------------------*/
	/*---- VIEW PATIENT HOMEPAGE ----*/
	@RequestMapping("/patient/homepage")
	public String welcomePatient(Model model) {
		CustomPatientDetails patientInfo = (CustomPatientDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Patient patient = patientService.getLoggedInPatient(patientInfo.getPatientID());
		model.addAttribute("patient", patient);
		return "patient_homepage";
	}

	/*---- EDIT PATIENT ACCOUNT INFO----*/
	@RequestMapping("/patient_edit/{patientID}")
	public ModelAndView showEditUserPage(@PathVariable(name = "patientID") String patientID) {
		ModelAndView mav = new ModelAndView("patient_edit");
		Patient patient = patientService.get(patientID);
		mav.addObject("patient", patient);
		return mav;
	}

	/*---- PROCESS ACCOUNT EDIT ----*/
	@PostMapping("/process_patient_edit")
	public String processAccountUpdate(@Valid Patient patient, BindingResult bindingResult) {
		if (patient.getDob() == null) {
			bindingResult.rejectValue("dob", "patient.dob", "Birthday cannot be blank!");
			return "patient_edit";
		}
		if (!(patient.getPlainPassword().contentEquals(patient.getPassword()))) {
			bindingResult.rejectValue("password", "patient.password", "Passwords do not match!");
		}
		if (bindingResult.hasErrors()) {
			return "patient_edit";
		} else {
			patient.setPassword(patient.getPlainPassword());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(patient.getPassword());
			patient.setPassword(encodedPassword);
			patientService.save(patient);
			return "redirect:/patient/homepage";
		}
	}

	/*---- DELETE ACCOUNT ----*/
	@RequestMapping("/delete/{patientID}")
	public String deletePatient(@PathVariable(name = "patientID") String patientID) {
		patientService.delete(patientID);
		return "redirect:/patient/logout";
	}

	/*-------------------------- PATIENT TICKETS --------------------------*/
	/*---- VIEW ALL PATIENT TICKETS ----*/
	// @RequestMapping("/view_patient_tickets")
	// public String listPatientTickets(Model model) {
	// List<HealthTicket> listPatientTickets = healthTicketService.findAll();
	// model.addAttribute("listPatientTickets", listPatientTickets);
	// return "templatePlaceholder";
	// }

	/*---- ADD PATIENT TICKET ----*/
	// @RequestMapping("/view_patient_tickets/add_ticket")
	// public String addPatientTicket(Patient patient) {
	//
	// return "templatePlaceholder";
	// }

	/*---- PROCESS ADDED PATIENT TICKET ----*/
	// @PostMapping("/view_patient_tickets/process_ticket")
	// public String processTeacher(@Valid HealthTicket healthTicket, BindingResult
	// bindingResult) {
	//
	// if (bindingResult.hasErrors()) {
	// return "templatePlaceholder";
	//
	// } else {
	// healthTicketService.save(healthTicket);
	// return "redirect:view_patient_tickets";
	// }
	// }

	/*---- DELETE PATIENT TICKET ----*/
	// @RequestMapping("/view_patient_tickets/delete/{id}")
	// public String deletePatientTicket(@PathVariable(name = "id") int id) {
	// healthTicketService.delete(id);
	// return "redirect:/view_patient_tickets";
	// }

	/*---- EDIT PATIENT TICKET ----*/
	// @RequestMapping("/view_patient_tickets/edit/{id}")
	// public ModelAndView showEditPatientTicket(@PathVariable(name = "id") int id)
	// {
	// ModelAndView mav = new ModelAndView("templatePlaceholder");
	// HealthTicket healthTicket = healthTicketService.get(id);
	// mav.addObject("healthTicket", healthTicket);
	// return mav;
	// }

	/*---- PROCESS EDITED PATIENT TICKET ----*/
	// @PostMapping("/view_patient_tickets/process_update_ticket")
	// public String processUpdateTicket(@Valid HealthTicket healthTicket,
	// BindingResult bindingResult) {
	//
	// if (bindingResult.hasErrors()) {
	// return "templatePlaceholder";
	//
	// } else {
	// healthTicketService.save(healthTicket);
	// return "redirect:view_patient_tickets";
	// }
	// }
}
