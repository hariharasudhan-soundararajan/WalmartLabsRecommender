package com.api.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.api.walmart.Walmart;

public class WalmartApiResultsTest {

	@Test
	public void test() {
		System.out.println("Running test for WalmartApiResultsTest");
		Walmart walmart=new Walmart();
		ArrayList<String> checkList=new ArrayList<String>();
		checkList.add("21557001");
		checkList.add("45825930");
		checkList.add("21557007");
		checkList.add("45162254");
		checkList.add("25059351");
		checkList.add("45924345");
		checkList.add("21853465");
		checkList.add("21853463");
		checkList.add("38236250");
		checkList.add("27678567");
		
		Map<String,Double> mapCheck=new HashMap<String,Double>();
		mapCheck.put("@.com Tilting Wall Mount for 37\\\" to 84\\\" TVs and HDMI Cable",4.760000228881836);
		mapCheck.put("@.com 6ft HDMI Cable",4.75);
		mapCheck.put("@.com Full Motion Wall Mount for 10\\\" to 42\\\"  TVs with Tilt and Swivel Articulating Arm and HDMI Cable",4.539999961853027);
		mapCheck.put("3 Yr Plan for TVs $200-299.99",4.429999828338623);
		mapCheck.put("4 Yr Plan for TVs $200-299.99",4.429999828338623);
		mapCheck.put("3 Year Service Plan for Televisions $200 - $299.99",4.260000228881836);
		mapCheck.put("4 Year Service Plan for Televisions $200 - $299.99",4.260000228881836);
		mapCheck.put("Tilting TV Wall Mount for 24\\\"-84\\\" TVs with HDMI Cable, UL Certified",4.179999828338623);
		mapCheck.put("Sceptre X505BV-F 50\\\" 1080p 60Hz LED HDTV",4.139999866485596);
		mapCheck.put("SCEPTRE X322BV-M 32\\\" LED Class 720P HDTV with ultra slim metal brush bezel, 60Hz",4.139999866485596);
		
	
		Map<String,Double> sortedMapCheck=walmart.sortByValue(mapCheck);
		for (Map.Entry<String, Double> entry : sortedMapCheck.entrySet()) {
			System.out.println("Item :" + entry.getKey() + " with rating:"
					+ entry.getValue());
		}
		
		Map<String,Double> mapResults=walmart.reviewItem(checkList);
		
		for (Map.Entry<String, Double> entry : mapResults.entrySet()) {
			System.out.println("Item :" + entry.getKey() + " with rating:"
					+ entry.getValue());
		}
			
			assertEquals(sortedMapCheck,mapResults);
		}
		
	}

