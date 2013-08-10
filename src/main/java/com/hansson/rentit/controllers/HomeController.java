package com.hansson.rentit.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gag.annotation.remark.Win;
import com.google.gag.enumeration.Outcome;
import com.google.gson.Gson;
import com.hansson.rentit.apartments.ApartmentsInterface;
import com.hansson.rentit.apartments.KarlskronahemApartments;
import com.hansson.rentit.apartments.TrossoWamoApartments;
import com.hansson.rentit.entitys.Apartment;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Win(Outcome.EPIC)
	private List<ApartmentsInterface> landlords = new LinkedList<ApartmentsInterface>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2411798345463453006L;
		// Add new implementations of the ApartmentsInterface here to include them in the scan loop
		{
			add(new TrossoWamoApartments());
			add(new KarlskronahemApartments());
		}
	};

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		List<Apartment> apartmentList = new LinkedList<Apartment>();
		for (ApartmentsInterface currentLandlord : landlords) {
			apartmentList.addAll(currentLandlord.getAvailableApartments());
		}
		model.addAttribute("apartments", new Gson().toJson(apartmentList));
		return "home";
	}

	@RequestMapping(value = "/flat", method = RequestMethod.GET)
	public String flat(Locale locale, Model model) {
		return "flat";
	}
}
