package org.clinic.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	/*-------------------------- HOMEPAGE  --------------------------*/
	@GetMapping("")
	public String viewHomePageSignedOut(Model model) {
		model.addAttribute("pageTitle", "Home");
		return "index";
	}
}
