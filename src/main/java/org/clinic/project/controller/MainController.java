package org.clinic.project.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// import org.clinic.project.model.Doctor;
import org.clinic.project.model.HealthTicket;
import org.clinic.project.model.Patient;
// import org.clinic.project.service.DoctorService;
import org.clinic.project.service.HealthTicketService;
import org.clinic.project.service.PatientService;

@Controller
public class MainController {

	/*-------------------------- HOMEPAGE  --------------------------*/
	@GetMapping("")
	public String viewHomePageSignedOut() {
		return "index";
	}
}
