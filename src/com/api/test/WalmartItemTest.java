package com.api.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.api.walmart.Walmart;

public class WalmartItemTest {

	@Test
	public void test() {
		System.out.println("first");
		String checkItem="27608624";
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
		ArrayList<String> itemName=walmart.getRecommendation(checkItem);
		assertEquals(checkList, itemName);
		
		
	}
	
	@Test
	public void testNull() {
		System.out.println("nulltest");
		String checkItem="38764442";
		Walmart walmart=new Walmart();
		ArrayList<String> checkList=new ArrayList<String>();
	
		ArrayList<String> itemName=walmart.getRecommendation(checkItem);
		assertEquals(itemName, null);
		
		
	}

}
