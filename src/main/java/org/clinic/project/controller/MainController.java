package org.clinic.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.clinic.project.model.HealthTicket;
import org.clinic.project.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    /*-------------------------- SERVICES  --------------------------*/
    // @Autowired
    // private PatientService patientService;

    // @Autowired
    // private DoctorService doctorService;

    // @Autowired
    // private HealthTicketService healthTicketService;

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
	public String processPatientRegister(@Valid Patient patient, BindingResult bindingResult) {

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

    /*---- VIEW ALL PATIENT TICKETS ----*/
	@RequestMapping("/view_patient_tickets")
	public String listPatientTickets(Model model) {
		//List<HealthTicket> listPatientTickets = healthTicketService.listAllPatientTickets();
		//model.addAttribute("listPatientTickets", listPatientTickets);
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
			//healthTicketService.save(healthTicket);
			return "redirect:view_patient_tickets";
		}
	}

    /*---- DELETE PATIENT TICKET ----*/
	@RequestMapping("/view_patient_tickets/delete/{id}")
	public String deletePatientTicket(@PathVariable(name = "id") int id) {
		//healthTicketService.delete(id);
		return "redirect:/view_patient_tickets";
	}

    /*---- EDIT PATIENT TICKET ----*/
	@RequestMapping("/view_patient_tickets/edit/{id}")
	public ModelAndView showEditPatientTicket(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("templatePlaceholder");
        //HealthTicket healthTicket = healthTicketService.get.(id);
        //mav.addObject("healthTicket", healthTicket);
		return mav;
	}

	/*---- PROCESS EDITED PATIENT TICKET ----*/
	@PostMapping("/view_patient_tickets/process_update_ticket")
	public String processUpdateTicket(@Valid HealthTicket healthTicket, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "templatePlaceholder";

		} else {
			//healthTicketService.save(healthTicket);
			return "redirect:view_patient_tickets";
		}
	}
}

