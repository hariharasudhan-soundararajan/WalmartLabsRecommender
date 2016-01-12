package com.walmart.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RatingCalc implements Callable<Item>{

	String itemId;
	String apiKey="sd7bsmjwfcbfgjgxx8jjq44q";
	
	public RatingCalc(String itemId) {
        this.itemId = itemId;	
    }
	
	@Override
	public Item call() throws Exception {
		Item item = new Item();
		String webServiceURI="http://api.walmartlabs.com/v1/reviews/"+itemId+"?apiKey="+apiKey+"&format=json";
		
		StringBuilder sb=new StringBuilder();
		JsonParser parser = new JsonParser();
		/*URL url = null;
		try {
			url = new URL(webServiceURI);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String line = null; 
			while (( line = reader.readLine() ) != null) {
				sb.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		StringBuffer response = null;
		
		URL obj = new URL(webServiceURI);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK || 1 == 1) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
 
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
 
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
		
		String json=response.toString();
		JsonObject jo = parser.parse(json).getAsJsonObject();
		String itemName = jo.get("name").toString();
		JsonObject jA= jo.getAsJsonObject("reviewStatistics");
		String averageRating=jA.get("averageOverallRating").toString();
		double avgRating=Float.parseFloat(averageRating.substring(1, averageRating.length()-1));
		
		item.itemName = itemName;
		item.avgRating = avgRating;
		
		return item;
	}

}
