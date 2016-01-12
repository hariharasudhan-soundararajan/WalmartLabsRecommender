package com.walmart.api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Walmart {

	public static void main(String[] args) {

		StringBuilder sb=new StringBuilder();
		String apiKey="sd7bsmjwfcbfgjgxx8jjq44q";
		System.out.println("Enter search query: ");

		Scanner in = new Scanner(System.in);
		String query=in.next();

		String webServiceURI="http://api.walmartlabs.com/v1/search?apiKey="+apiKey+"&query="+query;

		URL url = null;
		try {
			url = new URL(webServiceURI);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {

				sb.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		String json=sb.toString();
		if(json.equals("[]")){
			System.out.println("No results found");
			return;
		}
		JsonParser parser = new JsonParser();
		JsonObject jo = parser.parse(json).getAsJsonObject();
		JsonArray ja=(JsonArray) jo.get("items");
		JsonObject jobject = ja.get(0).getAsJsonObject();
		String itemId = jobject.get("itemId").toString();

		searchItem(itemId,apiKey);

	}

	static void searchItem(String itemId,String apiKey){

		StringBuilder sb=new StringBuilder();
		ArrayList<String> al=new ArrayList<String>();
		String webServiceURI="http://api.walmartlabs.com/v1/nbp?apiKey="+apiKey+"&itemId="+itemId;
		URL url = null;
		try {
			url = new URL(webServiceURI);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
			for (String line; (line = reader.readLine()) != null;) {

				sb.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String json=sb.toString();
		if(json.equals("[]")){
			System.out.println("No results found");
			return;
		}
		JsonParser parser = new JsonParser();
		JsonArray ja = parser.parse(json).getAsJsonArray();

		for(int i=0;i<10;i++)
		{JsonObject jobject = ja.get(i).getAsJsonObject();
		al.add(jobject.get("itemId").toString());
		}
		//for(String s:al)
			//System.out.println(s);

		reviewItem(al,apiKey);
	}

	static void reviewItem(ArrayList<String> itemList,String apiKey){
		//http://api.walmartlabs.com/v1/reviews/33093101?apiKey={apiKey}&lsPublisherId={Your LinkShare Publisher Id}&format=json

		Map<String, Double> map = new HashMap<String, Double>();

		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<Item>> futureList = new ArrayList<Future<Item>>();
		for(String itemId:itemList){
			Callable<Item> itemCallable = new RatingCalc(itemId);
			Future<Item> future = executor.submit(itemCallable);
			futureList.add(future);
		}
		
		for(Future<Item> fut : futureList){
            try {
    			map.put(fut.get().itemName, fut.get().avgRating);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
		
		/*for(String itemId:itemList){
			String webServiceURI="http://api.walmartlabs.com/v1/reviews/"+itemId+"?apiKey="+apiKey+"&format=json";
			StringBuilder sb=new StringBuilder();
			JsonParser parser = new JsonParser();
			URL url = null;
			try {
				url = new URL(webServiceURI);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
				for (String line; (line = reader.readLine()) != null;) {

					sb.append(line);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String json=sb.toString();
			JsonObject jo = parser.parse(json).getAsJsonObject();
			String itemName = jo.get("name").toString();
			JsonObject jA= jo.getAsJsonObject("reviewStatistics");
			String averageRating=jA.get("averageOverallRating").toString();
			double avgRating=Float.parseFloat(averageRating.substring(1, averageRating.length()-1));
			//System.out.println(itemName);
			//System.out.println(avgRating);

			map.put(itemName, avgRating);



		}*/
        Iterator<Entry<String, Double>> iterator = map.entrySet().iterator();


		Map<String,Double> sortedMap = sortByValue(map);

		for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
			System.out.println("Item :" + entry.getKey() + " with rating:"
					+ entry.getValue());
		}
	}


	public static Map<String, Double> sortByValue(Map<String, Double> map) {
		List<Object> list = new LinkedList<Object>(map.entrySet());
		Collections.sort(list, new Comparator<Object>() {

			@Override
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		Map<String, Double> result = new LinkedHashMap<>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put((String)entry.getKey(), (double)entry.getValue());
		}
		return result;
	}
}






