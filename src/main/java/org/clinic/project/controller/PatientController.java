package org.clinic.project.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.clinic.project.model.CustomPatientDetails;
import org.clinic.project.model.Doctor;
import org.clinic.project.model.HealthTicket;
import org.clinic.project.model.Patient;
import org.clinic.project.service.DoctorService;
import org.clinic.project.service.HealthTicketService;
import org.clinic.project.service.PatientService;

@Controller
public class PatientController {

	/*---- SERVICES ----*/
	@Autowired
	private PatientService patientService;

	@Autowired
	private HealthTicketService healthTicketService;

	@Autowired
	private DoctorService doctorService;
	/*-------------------------- PATIENT REGISTRATION --------------------------*/

	/*---- REGISTER PATIENT ----*/
	@GetMapping("/patient_register")
	public String showPatientRegistrationForm(Model model) {
		model.addAttribute("patient", new Patient());
		model.addAttribute("pageTitle", "Register Patient");
		return "patient_register";
	}

	/*---- PROCESS REGISTER ----*/
	@PostMapping("/process_patient_register")
	public String processPatientRegister(@Valid @ModelAttribute("patient") Patient patient, BindingResult bindingResult, Model model) {
		boolean exists = patientService.patientExists(patient.getPatientID());
		if(exists == true) {
			model.addAttribute("pageTitle", "Registration Error");
			bindingResult.rejectValue("patientID", "patient.patientID", "ID already exists");
			return "patient_register";
		}
		if(patient.getDob() == null) {
			model.addAttribute("pageTitle", "Registration Error");
			bindingResult.rejectValue("dob", "patient.dob", "Birthday cannot be blank!");
			return "patient_register";
		}
		if(!(patient.getPlainPassword().contentEquals(patient.getPassword()))) {
			model.addAttribute("pageTitle", "Registration Error");
			bindingResult.rejectValue("password", "patient.password", "Passwords do not match!");
		}
		if(bindingResult.hasErrors()) {
			model.addAttribute("pageTitle", "Registration Error");
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
	public String patientLogInPage(Patient patient, Model model) {
		model.addAttribute("pageTitle", "Patient Log In");
		return "patient_login";
	}

	@RequestMapping("/patient/logout")
	public String patientLogoutPage(Patient patient, Model model) {
		model.addAttribute("pageTitle", "Patient Logged Out");
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
		model.addAttribute("pageTitle", "Patient Homepage");
		return "patient_homepage";
	}

	/*---- EDIT PATIENT ACCOUNT INFO----*/
	@RequestMapping("/patient/edit/{patientID}")
	public ModelAndView showEditUserPage(@PathVariable(name = "patientID") String patientID, Model model) {
		ModelAndView mav = new ModelAndView("patient_edit");
		Patient patient = patientService.get(patientID);
		mav.addObject("patient", patient);
		model.addAttribute("pageTitle", "Edit Account");
		return mav;
	}

	/*---- PROCESS ACCOUNT EDIT ----*/
	@PostMapping("/process_patient_edit")
	public String processAccountUpdate(@Valid Patient patient, BindingResult bindingResult) {
		if (patient.getDob() == null) {
			bindingResult.rejectValue("dob", "patient.dob", "Date of birth required");
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
	@RequestMapping("/patient/delete/{patientID}")
	public String deletePatient(@PathVariable(name = "patientID") String patientID) {
		Patient patient = patientService.get(patientID);

		healthTicketService.deleteAllByPatientID(patient);
		patientService.delete(patientID);
		return "redirect:/patient/logout";
	}

	/*-------------------------- PATIENT TICKETS --------------------------*/
	/*---- ADD PATIENT TICKET ----*/
	@RequestMapping("/patient/addTicket")
	public String addPatientTicket(Model model) {
		CustomPatientDetails patientInfo = (CustomPatientDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Patient patient = patientService.getLoggedInPatient(patientInfo.getPatientID());
		model.addAttribute("healthTicket", new HealthTicket());
		model.addAttribute("patient", patient);
		model.addAttribute("pageTitle", "Create Ticket");
		return "patient_add_ticket";
	}

	@PostMapping("/patient/processTicket")
	public String processTeacher(@ModelAttribute("healthTicket") HealthTicket healthTicket, Model model) {
		CustomPatientDetails patientInfo = (CustomPatientDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Patient patient = patientService.getLoggedInPatient(patientInfo.getPatientID());
		healthTicket.setPatientID(patient);

		LocalDate localdate = LocalDate.now();
        healthTicket.setDateSubmitted(localdate);

		healthTicketService.save(healthTicket);
		return "redirect:/patient/homepage";

	}

	/*---- VIEW ALL PATIENT TICKETS ----*/
	@RequestMapping("/patient/viewTickets")
	public String listPatientTickets(Model model) {
		CustomPatientDetails patientInfo = (CustomPatientDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Patient patient = patientService.getLoggedInPatient(patientInfo.getPatientID());

		List<HealthTicket> healthTicketList = healthTicketService.getByPatient(patient);

		model.addAttribute("healthTicketList", healthTicketList);
		model.addAttribute("patient", patient);
		model.addAttribute("pageTitle", "View Tickets");
		return "patient_view_tickets";
	}

	@RequestMapping("patient/view-doctor/{doctorID}")
    public String viewPatient(@PathVariable(name = "doctorID") Doctor doctor, Model model) {
		CustomPatientDetails patientInfo = (CustomPatientDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Patient patient = patientService.getLoggedInPatient(patientInfo.getPatientID());
        String doctorID = doctor.getDoctorID();
        doctor = doctorService.get(doctorID);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);

        return "patient_view_doctor";
    }

	/*---- EDIT PATIENT TICKET ----*/
	@RequestMapping("/patient/edit-ticket/{ticketID}")
	public ModelAndView showEditPatientTicket(@PathVariable(name = "ticketID") int ticketID, Model model) {
		ModelAndView mav = new ModelAndView("patient_edit_ticket");
		HealthTicket healthTicket = healthTicketService.get(ticketID);
		mav.addObject("healthTicket", healthTicket);
		model.addAttribute("patient", new Patient());
		model.addAttribute("pageTitle", "Edit Ticket");
		return mav;
	}

	@PostMapping("/patient/processTicket/{ticketID}")
	public String processDiagnosis(@PathVariable(name = "ticketID") int ticketID,
			@ModelAttribute("healthTicket") HealthTicket healthTicket, Model model) {
		CustomPatientDetails patientInfo = (CustomPatientDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Patient patient = patientService.getLoggedInPatient(patientInfo.getPatientID());
		healthTicket.setPatientID(patient);

		LocalDate localdate = LocalDate.now();
        healthTicket.setDateSubmitted(localdate);

		healthTicket.setTicketID(ticketID);
		healthTicketService.update(healthTicket);

		return "redirect:/patient/viewTickets";
	}

	/*---- DELETE PATIENT TICKET ----*/
	@RequestMapping("/patient/delete-ticket/{ticketID}")
	public String deletePatientTicket(@PathVariable(name = "ticketID") int ticketID, Model model) {
		model.addAttribute("patient", new Patient());
		healthTicketService.delete(ticketID);
		return "redirect:/patient/viewTickets";
	}

}
