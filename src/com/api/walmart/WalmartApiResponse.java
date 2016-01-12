package com.api.walmart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class WalmartApiResponse {

	/**
	 * Returns the Json response from Walmart Labs API call
	 * 
	 *
	 * @param  Web service URI
	 * @return  JSON Response
	 * 
	 */
	
	public static String getResponse(String webServiceURI) {
		URL url = null;
		String json;
		StringBuilder sb=new StringBuilder();
		try {
			url = new URL(webServiceURI);
		} catch (MalformedURLException e) {
			System.out.println("Error occurred. Try Again!");
			e.printStackTrace();
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {

				sb.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error occurred. Try Again!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error occurred. Try Again!");
			e.printStackTrace();
		}

		json=sb.toString();
		return json;


	}
}
