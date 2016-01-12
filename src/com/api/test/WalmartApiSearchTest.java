package com.api.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.api.walmart.Walmart;

public class WalmartApiSearchTest {
	

	@Test
	public void test() {
		System.out.println("Running test for WalmartApiSearchTest");
		String checkItem="27608624";
		Walmart walmart=new Walmart();
		String itemName=walmart.searchQuery("tv");
		assertEquals(itemName,checkItem);
	}

}
