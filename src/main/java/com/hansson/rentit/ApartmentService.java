package com.hansson.rentit;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hansson.rentit.apartments.ApartmentsInterface;
import com.hansson.rentit.apartments.BengtAkessonsApartments;
import com.hansson.rentit.apartments.CAFastigheterApartments;
import com.hansson.rentit.apartments.HeimstadenApartments;
import com.hansson.rentit.apartments.KSFastigheterApartments;
import com.hansson.rentit.apartments.KarlskronahemApartments;
import com.hansson.rentit.apartments.MagistratusFastigheterApartments;
import com.hansson.rentit.apartments.PBAApartments;
import com.hansson.rentit.apartments.SBFApartments;
import com.hansson.rentit.apartments.TrossoWamoApartments;
import com.hansson.rentit.apartments.UtklippanApartments;
import com.hansson.rentit.entitys.Apartment;

@Service
public class ApartmentService {
	
	// * ********CAUTION********
	// * Entering html scraping area.. prepare yourself for some nasty stuff!
	// * ********CAUTION********
	private static final Logger mLog = LoggerFactory.getLogger("RENTIT");
	private List<ApartmentsInterface> landlords = new LinkedList<ApartmentsInterface>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2411798345463453006L;
		// Add new implementations of the ApartmentsInterface here to include them in the scan loop
		{
			add(new HeimstadenApartments());
			add(new CAFastigheterApartments());
			add(new TrossoWamoApartments());
			add(new KarlskronahemApartments());
			add(new BengtAkessonsApartments());
			add(new KSFastigheterApartments());
			add(new MagistratusFastigheterApartments());
			add(new PBAApartments());
			add(new SBFApartments());
			add(new UtklippanApartments());

		}
	};

	@Scheduled(fixedDelayString = "3600")
	public void updateApartmentList() {
		List<Apartment> apartmentList = new LinkedList<Apartment>();
		for (ApartmentsInterface currentLandlord : landlords) {
			apartmentList.addAll(currentLandlord.getAvailableApartments());
		}
	}
}
