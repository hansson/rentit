package com.hansson.rentit.utils;

public class HtmlUtil {

	public static String htmlToText(String html) {
		html = html.replace("&aring;", "�");
		html = html.replace("&auml;", "�");
		html = html.replace("&ouml;", "�");
		html = html.replace("&Aring;", "�");
		html = html.replace("&Auml;", "�");
		html = html.replace("&Ouml;", "�");
		return html;
	}
}
