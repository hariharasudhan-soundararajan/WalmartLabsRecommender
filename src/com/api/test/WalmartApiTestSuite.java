package com.api.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
	WalmartApiResultsTest.class,
	WalmartApiResponseTest.class,
	WalmartApiSearchTest.class,
	WalmartItemTest.class
})
public class WalmartApiTestSuite {

}
