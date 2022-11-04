package org.clinic.project.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.clinic.project.model.Doctor;
// import org.clinic.project.model.HealthTicket;
import org.clinic.project.service.DoctorService;
// import org.clinic.project.service.HealthTicketService;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // @Autowired
    // private HealthTicketService healthTicketService;

    /*-------------------------- DOCTOR REGISTRATION --------------------------*/
    /*---- REGISTER DOCTOR ----*/
    @RequestMapping("/doctor_register")
    public String showDoctorRegistrationForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor_register";
    }

    /*---- PROCESS REGISTER ----*/
    @PostMapping("/process_doctor_register")
    public String processDoctorRegister(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult bindingResult) {
    	if (doctor.getDob() == null) {
			bindingResult.rejectValue("dob", "doctor.dob", "Birthday cannot be blank!");
			return "doctor_register";
		}
		if (!(doctor.getPlainPassword().contentEquals(doctor.getPassword()))) {
			bindingResult.rejectValue("password", "doctor.password", "Passwords do not match!");
		}
        if (bindingResult.hasErrors()) {
            return "doctor_register";
        } else {
        	doctor.setPassword(doctor.getPlainPassword());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(doctor.getPassword());
            doctor.setPassword(encodedPassword);
            doctorService.save(doctor);
            return "redirect:/doctor/login";
        }
    }

    @RequestMapping("/doctor/login")
    public String viewDoctorloginpage() {
        return "doctor_login";
    }

     /*-------------------------- Doctor (SIGNED IN) --------------------------*/
     @RequestMapping("/doctor/homepage")
     public String welcomePatient(Doctor doctor) {
 
         return "doctor_homepage";
     }
 
}
