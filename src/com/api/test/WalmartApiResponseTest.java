/**
 * 
 */
package com.api.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.api.walmart.WalmartApiResponse;


public class WalmartApiResponseTest {
	
	public static final String API_KEY="sd7bsmjwfcbfgjgxx8jjq44q";
	String checkResponse="{\"query\":\"ipod\",\"sort\":\"relevance\",\"responseGroup\":\"base\",";
	@Test
	public void test() {
		System.out.println("Running test for WalmartApiResponseTest");

		String webServiceURI="http://api.walmartlabs.com/v1/search?apiKey="+API_KEY+"&query=ipod";
		String jsonResp=WalmartApiResponse.getResponse(webServiceURI);
		System.out.println(checkResponse);
		System.out.println(jsonResp);
		assertTrue(jsonResp.startsWith(checkResponse));
	}

}
