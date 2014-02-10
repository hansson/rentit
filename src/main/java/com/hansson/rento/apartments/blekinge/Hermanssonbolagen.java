package com.hansson.rento.apartments.blekinge;

import java.util.List;

import org.jsoup.nodes.Document;

import com.hansson.rento.apartments.ApartmentUtils;
import com.hansson.rento.apartments.ApartmentsInterface;
import com.hansson.rento.apartments.Method;
import com.hansson.rento.entities.Apartment;

public class Hermanssonbolagen extends ApartmentUtils implements ApartmentsInterface {

	private final static String LANDLORD = "JN von Bergen &amp; Son";
	private final static String BASE_URL = "http://www.jnvonbergen.se/von_Bergen300Sida6.htm";
	
	@Override
	public List<Apartment> getAvailableApartments() {
		List<Apartment> apartmentList = null;
		// Get html for first page
		Document doc = connect(BASE_URL);
		if (doc != null) {
			apartmentList = getApartmentsMultiPage(doc, BASE_URL, LANDLORD, Method.BLOCKET);
		}
		return apartmentList;
	}

	@Override
	public String getLandlord() {
		return LANDLORD;
	}
}
