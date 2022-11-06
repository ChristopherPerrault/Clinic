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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.clinic.project.model.CustomDoctorDetails;
import org.clinic.project.model.Doctor;
import org.clinic.project.model.HealthTicket;
import org.clinic.project.model.Patient;
// import org.clinic.project.model.HealthTicket;
import org.clinic.project.service.DoctorService;
// import org.clinic.project.service.HealthTicketService;
import org.clinic.project.service.HealthTicketService;
import org.clinic.project.service.PatientService;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private HealthTicketService healthTicketService;

    @Autowired
    private PatientService patientService;

    /*-------------------------- DOCTOR REGISTRATION --------------------------*/
    /*---- REGISTER DOCTOR ----*/
    @RequestMapping("/doctor_register")
    public String showDoctorRegistrationForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("pageTitle", "Regsiter Doctor");
        return "doctor_register";
    }

    /*---- PROCESS REGISTER ----*/
    @PostMapping("/process_doctor_register")
    public String processDoctorRegister(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult bindingResult,
            Model model) {
        boolean exists = doctorService.doctorExists(doctor.getDoctorID());
        if(exists == true) {
            model.addAttribute("pageTitle", "Registration Error");
			bindingResult.rejectValue("doctorID", "doctor.doctorID", "ID already exists");
            return "doctor_register";
        }
        if (doctor.getDob() == null) {
            model.addAttribute("pageTitle", "Registration Error");
            bindingResult.rejectValue("dob", "doctor.dob", "Date of birth required");
            return "doctor_register";
        }
        if (!(doctor.getPlainPassword().contentEquals(doctor.getPassword()))) {
            model.addAttribute("pageTitle", "Registration Error");
            bindingResult.rejectValue("password", "doctor.password", "Passwords do not match!");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Registration Error");
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
    public String viewDoctorloginpage(Model model) {
        model.addAttribute("pageTitle", "Doctor Log In");
        return "doctor_login";
    }

    /*---- EDIT Doctor ACCOUNT INFO----*/
    @RequestMapping("/doctor/edit/{doctorID}")
    public ModelAndView showEditUserPage(@PathVariable(name = "doctorID") String doctorID, Model model) {
        ModelAndView mav = new ModelAndView("doctor_edit");
        Doctor doctor = doctorService.get(doctorID);
        mav.addObject("doctor", doctor);
        model.addAttribute("pageTitle", "Doctor Edit");
        return mav;
    }

    /*---- PROCESS ACCOUNT EDIT ----*/
    @PostMapping("/process_doctor_edit")
    public String processAccountUpdate(@Valid @ModelAttribute("doctor") Doctor doctor, BindingResult bindingResult) {
        if (doctor.getDob() == null) {
            bindingResult.rejectValue("dob", "doctor.dob", "Birthday cannot be blank!");
            return "doctor_edit";
        }
        if (!(doctor.getPlainPassword().contentEquals(doctor.getPassword()))) {
            bindingResult.rejectValue("password", "doctor.password", "Passwords do not match!");
        }
        if (bindingResult.hasErrors()) {
            return "doctor_edit";
        } else {
            doctor.setPassword(doctor.getPlainPassword());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(doctor.getPassword());
            doctor.setPassword(encodedPassword);
            doctorService.save(doctor);
            return "redirect:/doctor/homepage";
        }
    }

    /*---- DELETE ACCOUNT ----*/
    @RequestMapping("/doctor/delete/{doctorID}")
    public String deleteDoctor(@PathVariable(name = "doctorID") String doctorID) {
        Doctor doctor = doctorService.get(doctorID);

        healthTicketService.deleteAllByDoctorID(doctor);
        doctorService.delete(doctorID);
        return "redirect:/doctor/logout";
    }

    @RequestMapping("/doctor/logout")
    public String doctorLogoutPage(Model model) {
        model.addAttribute("pageTitle", "Doctor Logged Out");
        return "logged_out";
    }

    /*-------------------------- Doctor (SIGNED IN) --------------------------*/
    @RequestMapping("/doctor/homepage")
    public String welcomeDoctor(Model model) {
        CustomDoctorDetails doctorInfo = (CustomDoctorDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Doctor doctor = doctorService.getLoggedInDoctor(doctorInfo.getDoctorID());
        model.addAttribute("doctor", doctor);
        model.addAttribute("pageTitle", "Doctor Homepage");
        return "doctor_homepage";
    }

    /*-------------------------- DOCTOR TICKET MANAGEMENT --------------------------*/
    @RequestMapping("/doctor/viewTickets")
    public String viewTickets(Model model) {
        List<HealthTicket> listHealthtickets = healthTicketService.findAll();
        model.addAttribute("listHealthtickets", listHealthtickets);
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("pageTitle", "Doctor View Tickets");
        return "doctor_view_tickets";
    }

    @RequestMapping("doctor/view-patient/{patientID}")
    public String doctorViewPatient(@PathVariable(name = "patientID") Patient patient, Model model) {
        String patientID = patient.getPatientID();
        patient = patientService.get(patientID);
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("patient", patient);

        return "doctor_view_patient";
    }

    @RequestMapping("/doctor/edit-ticket/{ticketID}")
    public ModelAndView showDiagnosisPage(@PathVariable(name = "ticketID") int ticketID, Model model) {
        ModelAndView mav = new ModelAndView("doctor_edit_ticket");
        HealthTicket healthTicket = healthTicketService.get(ticketID);
        mav.addObject("healthTicket", healthTicket);
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("pageTitle", "Edit Ticket");

        return mav;
    }

    @PostMapping("/doctor/processTicket/{ticketID}")
    public String processDiagnosis(@PathVariable(name = "ticketID") int ticketID,
            @ModelAttribute("healthTicket") HealthTicket healthTicket, Model model) {
        CustomDoctorDetails doctorInfo = (CustomDoctorDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Doctor doctor = doctorService.getLoggedInDoctor(doctorInfo.getDoctorID());
        healthTicket.setDoctorID(doctor);

        LocalDate localdate = LocalDate.now();
        healthTicket.setDateSubmitted(localdate);

        healthTicket.setTicketID(ticketID);
        healthTicketService.update(healthTicket);

        return "redirect:/doctor/viewTickets";
    }

    /*---- DELETE TICKET ----*/
    @RequestMapping("/doctor/delete-ticket/{ticketID}")
    public String deleteTicket(@PathVariable(name = "ticketID") int ticketID, Model model) {
        healthTicketService.delete(ticketID);
        model.addAttribute("doctor", new Doctor());
        return "redirect:/doctor/viewTickets";
    }
}
