package com.hansson.rentit.apartments;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gag.annotation.disclaimer.CarbonFootprint;
import com.google.gag.enumeration.CO2Units;
import com.hansson.rentit.entitys.Apartment;
import com.hansson.rentit.utils.HtmlUtil;

public class HeimstadenApartments implements ApartmentsInterface {

	private final static String LANDLORD = "Heimstaden";
	private final static String BASE_URL = "http://www.heimstaden.com";

	@Override
	@CarbonFootprint(units = CO2Units.FIRKINS_PER_FORTNIGHT, value = 443.4)
	public List<Apartment> getAvailableApartments() {
		List<Apartment> apartmentLIst = new LinkedList<Apartment>();
		try {
			// Get html for first page
			Document doc = Jsoup.connect(BASE_URL + "/For_sokande/Lediga_bostader").get();
			Elements elementsByClass = doc.getElementsByClass("listItem");
			for (Element element : elementsByClass) {
				try {
					Apartment apartment = new Apartment(LANDLORD);
					apartment.setIdentifier(element.attr("id"));
					String imageUrl = element.getElementsByTag("img").attr("src");
					int indexOf = imageUrl.indexOf("&w=");
					if (imageUrl != null && indexOf > -1) {
						imageUrl = imageUrl.substring(0, indexOf);
						// TODO change width and height when they are decided
						imageUrl += "&w=128&h=128&q=100q&ScaleAndClipMode=FitAndClip";
					} else {
						apartment.setImageUrl(LOGO);
					}
					apartment.setImageUrl(imageUrl);
					apartment.setUrl(BASE_URL + element.getElementsByTag("a").attr("href"));
					apartment.setAddress(HtmlUtil.textToHtml(element.getElementsByTag("h5").text()));
					apartment.setRooms(Integer.valueOf(element.getElementsByClass("listItemLeftBottom").text().replaceAll("\\D", "")));
					apartment.setRent(Integer.valueOf(element.getElementsByClass("listItemRightBottom").text().replaceAll("\\D", "")));
					doc = Jsoup.connect(apartment.getUrl()).get();
					String[] headerSplit = doc.getElementsByTag("h1").text().split(",");
					for (String s : headerSplit) {
						if (s.contains("m2")) {
							apartment.setSize(Integer.valueOf(s.replace("m2", "").replaceAll("\\D", "")));
							break;
						}
					}
					Elements elementsByTag = doc.getElementsByTag("h3");
					for (Element infoElement : elementsByTag) {
						if (infoElement.text().equalsIgnoreCase("Omr�de")) {
							apartment.setArea(HtmlUtil.textToHtml(infoElement.nextElementSibling().text()));
						} else if (infoElement.text().equalsIgnoreCase("Ort")) {
							apartment.setCity(HtmlUtil.textToHtml(WordUtils.capitalize(infoElement.nextElementSibling().text().toLowerCase())));
						}
					}
					apartment.setSummary(HtmlUtil.textToHtml(doc.getElementsByClass("saljbesk").text()));
					apartmentLIst.add(apartment);
				} catch (Exception e) {
					// To make sure that even if one apartment listing fails it will continue.
					// Log error to be able to fix it.
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apartmentLIst;
	}
}
