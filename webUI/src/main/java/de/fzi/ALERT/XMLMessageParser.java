package de.fzi.ALERT;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class XMLMessageParser {
	static String tempXMLPath = "ALERT.ALL.LoginVerifyRequest.xml";

	// static String tempXMLPath = "ALERT.Stardom.LoginVerify.xml";

	public static String createVertifyRequest(String username, String email) {
		// Using class loader to get the template XML file
		InputStream in = XMLMessageParser.class.getClassLoader()
				.getResourceAsStream(tempXMLPath);
		try {
			String inputString = convertStreamToString(in);
			// System.out.println("Input String: " + inputString);
			Document doc = Jsoup.parse(inputString);
			// System.out.println(doc.text());

			// find elements with node name
			Elements usernames = doc.select("as|username");
			// reset the username
			usernames.get(0).text(username);

			Elements emails = doc.select("as|email");
			emails.get(0).text(email);

			// System.out.println(doc.toString());
			return doc.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// TODO: if the uid is null
	/*
	 * To find the first value of the input attribute name.
	 * 
	 * @param message input message
	 * 
	 * @param attributeName the name of the attribute, which contains the
	 * samespace too
	 */
	public static String findAttribute(String message, String attributeName) {

		try {
			String msg = message.replaceAll("[\n\r]", "");
			Document doc = Jsoup.parse(msg);
			Elements uids = doc.select(attributeName);
			return uids.get(0).text();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;

		}
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {

		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

}
