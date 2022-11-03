package org.clinic.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	/*-------------------------- HOMEPAGE  --------------------------*/
	@GetMapping("")
	public String viewHomePageSignedOut() {
		return "index";
	}

}
