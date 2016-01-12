package com.api.walmart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Walmart {

	public static final String API_KEY="sd7bsmjwfcbfgjgxx8jjq44q";
	public static void main(String[] args) {

		System.out.println("Enter search query: ");
		Scanner in = new Scanner(System.in);
		String queryName=in.next();
		String itemID=searchQuery(queryName);
		if(itemID==null)
			return;
		ArrayList<String> itemList=getRecommendation(itemID);
		if(itemList==null)
			return;
		Map<String, Double> sortedMap = reviewItem(itemList);
		if(sortedMap==null)
			return;
		for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
			System.out.println("Item :" + entry.getKey() + " with rating:"
					+ entry.getValue());
		}
	}
	
	/**
	 * Returns the first item id of the search response produced based on the input query
	 * 
	 *
	 * @param  Query name
	 * @return  Item ID
	 * 
	 */

	public static String searchQuery(String queryName)

	{
		String webServiceURI="http://api.walmartlabs.com/v1/search?apiKey="+API_KEY+"&query="+queryName;
		String json= WalmartApiResponse.getResponse(webServiceURI);
		if(json.equals("[]")){
			System.out.println("No results found");
			return null;
		}
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray jsonArray=(JsonArray) jsonObject.get("items");
		JsonObject jobject = jsonArray.get(0).getAsJsonObject();
		String itemId = jobject.get("itemId").toString();
		return itemId;

	}
	
	/**
	 * Retrieve reviews of the first 10 product recommendations
	 * 
	 * @param  Item ID
	 * @return  Item List
	 * 
	 */

	public static ArrayList<String> getRecommendation(String itemId){

		int itemSize=10;
		ArrayList<String> itemList=new ArrayList<String>();
		String webServiceURI="http://api.walmartlabs.com/v1/nbp?apiKey="+API_KEY+"&itemId="+itemId;

		String json= WalmartApiResponse.getResponse(webServiceURI);
		if(json.equals("[]")){
			System.out.println("No results found");
			return null;
		}

		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(json).getAsJsonArray();

		for(int i=0;i<itemSize;i++)
		{JsonObject jobject = jsonArray.get(i).getAsJsonObject();
		itemList.add(jobject.get("itemId").toString());
		}
		return itemList;
	}

	/**
	 * Method to find out the average rating of respective Item ID in the list
	 * 
	 * @param  List of items
	 * @return HashMap of recommendations sorted based on average rating
	 * 
	 */
	public static Map<String, Double> reviewItem(ArrayList<String> itemList){

		Map<String, Double> map = new HashMap<String, Double>();
		for(String itemId:itemList){
			String webServiceURI="http://api.walmartlabs.com/v1/reviews/"+itemId+"?apiKey="+API_KEY+"&format=json";
			JsonParser parser = new JsonParser();
			String json= WalmartApiResponse.getResponse(webServiceURI);
			JsonObject jsonObject = parser.parse(json).getAsJsonObject();
			String itemName = jsonObject.get("name").toString().substring(1, jsonObject.get("name").toString().length()-1);
			JsonObject jObject= jsonObject.getAsJsonObject("reviewStatistics");
			String averageRating=jObject.get("averageOverallRating").toString();
			double avgRating=Float.parseFloat(averageRating.substring(1, averageRating.length()-1));

			map.put(itemName, avgRating);
		}

        Map<String,Double> sortedMap = sortByValue(map);
        return sortedMap;

	}
	
	/**
	 * Method to sort the recommendations based on average rating
	 * 
	 * @param  List of items
	 * @return HashMap of recommendations sorted based on average rating
	 * 
	 */
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






