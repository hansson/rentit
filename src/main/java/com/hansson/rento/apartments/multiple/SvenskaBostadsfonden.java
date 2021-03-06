package com.hansson.rento.apartments.multiple;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hansson.rento.apartments.ApartmentUtils;
import com.hansson.rento.apartments.ApartmentsInterface;
import com.hansson.rento.entities.Apartment;

public class SvenskaBostadsfonden extends ApartmentUtils implements ApartmentsInterface {

	private static final String LANDLORD = "Svenska Bostadsfonden";
	private static final String BASE_URL = "http://www.sbfbostad.se";
	private static final Logger mLog = LoggerFactory.getLogger("rento");

	@Override
	public List<Apartment> getAvailableApartments() {
		List<Apartment> apartmentList = new LinkedList<Apartment>();
		Document doc = connect(BASE_URL + "/pages/Lediga_lagenheter.aspx");
		if (doc != null) {
			Elements apartments = doc.getElementsByClass("listRow");
			apartments.addAll(doc.getElementsByClass("listRowGray"));
			for (Element element : apartments) {
				try {
					Apartment apartment = new Apartment(LANDLORD);
					apartment.setCity(element.getElementsByClass("colOrt").text());
					apartment.setAddress(element.getElementsByClass("colAdress").text());
					apartment.setRooms(Double.valueOf(element.getElementsByClass("colRum").text().replaceAll(",", ".")));
					apartment.setRent(Integer.valueOf(element.getElementsByClass("colHyra").text().replaceAll("kr| ", "")));
					apartment.setSize(Integer.valueOf(element.getElementsByClass("colArea").text()));
					apartment.setUrl(BASE_URL + element.getElementsByTag("a").attr("href"));
					apartment.setIdentifier(element.getElementsByTag("a").attr("href").split("\\?")[1].split("=")[1]);
					apartmentList.add(apartment);
				} catch (Exception e) {
					mLog.error(LANDLORD + " error on element #" + apartments.indexOf(element));
					e.printStackTrace();
				}
			}
		}
		return apartmentList;
	}

	@Override
	public String getLandlord() {
		return LANDLORD;
	}
}
