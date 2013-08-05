package com.hansson.rentit.apartments;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gag.annotation.disclaimer.CarbonFootprint;
import com.google.gag.enumeration.CO2Units;
import com.hansson.rentit.entitys.Apartment;

public class KarlskronahemApartments implements ApartmentsInterface {

	public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36";
	public final static String LANDLORD = "Karlskronahem";
	public final static String BASE_URL = "http://marknad.karlskronahem.se/HSS/Object";

	@Override
	@CarbonFootprint(units = CO2Units.FIRKINS_PER_FORTNIGHT, value = 167.5)
	public List<Apartment> getAvailableApartments() {
		List<Apartment> apartmentLIst = new LinkedList<Apartment>();
		try {
			// Get html for first page
			Document doc = Jsoup.connect(BASE_URL + "/object_list.aspx?objectgroup=1").get();
			int currentPage = 1;
			// Find number of pages
			Elements pageSwitcher = doc.select(".right").get(0).getElementsByTag("a");
			int pages = pageSwitcher.size();
			// Handle and iterate all pages
			do {
				// Parse data from html
				// Get all <tr> tags from html with listitem-even or listitem-odd class
				Elements elementsByTag = doc.getElementsByTag("tr.listitem-even, tr.listitem-odd");
				// Iterate all elements
				for (Element element : elementsByTag) {
						Apartment apartment = new Apartment(LANDLORD);
						Element address = element.child(1).getElementsByTag("a").get(0);
						apartment.setUrl(BASE_URL + "/" + address.attr("href"));
						apartment.setAddress(address.childNode(0).toString());
						apartment.setIdentifier(address.attr("href").split("[&=]")[3]);
				}
				// If there are more pages, prepare the next post
				if (pages > currentPage) {
					Elements viewState = doc.select("#__VIEWSTATE");
					Elements eventValidation = doc.select("#__EVENTVALIDATION");
					String eventTarget = pageSwitcher.get(currentPage).attr("id");
					eventTarget = eventTarget.replace("_", "$");
					Map<String, String> postData = new HashMap<String, String>();
					postData.put("__EVENTTARGET", eventTarget);
					postData.put("__EVENTARGUMENT", "");
					postData.put("__VIEWSTATE", viewState.get(0).attr("value"));
					postData.put("__EVENTVALIDATION", eventValidation.get(0).attr("value"));
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnRentMin", "0");
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnRentMax", "15000");
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnRoomMin", "1");
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnRoomMax", "6");
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnSearchedRentMin", "0");
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnSearchedRentMax", "15000");
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnSearchedRoomMin", "1");
					postData.put("ctl00$ctl01$DefaultSiteContentPlaceHolder1$Col1$hdnSearchedRoomMax", "6");
					postData.put("__ASYNCPOST", "true");
					doc = Jsoup.connect("http://marknad.karlskronahem.se/HSS/Object/object_list.aspx?objectgroup=1").data(postData).userAgent(USER_AGENT).header("Content-Type",
							"application/x-www-form-urlencoded; charset=utf-8").post();
				}
				currentPage++;
			} while (currentPage <= pages);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return apartmentLIst;
	}
}
