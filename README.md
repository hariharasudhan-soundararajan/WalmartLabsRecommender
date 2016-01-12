# WalmartLabsRecommender
Product recommendation system using walmartlabs API

This application has the following functionality:

1)Search for products based upon a user-provided search string

2)Use the first item in the search response as input for a product recommendation search

3)Retrieve reviews of the first 10 product recommendations

4)Rank order the recommended products based upon the average review score



To run the application via command line:

1)  Place the gson Jar in the class path
    Path/gson-2.3.1.jar:$CLASSPATH

2) Go to the Walmart directory and run the javac command
   javac Walmart.java 
 
3) Run the program using Java command
   java Walmart


To run Test Runner:

Compile all java test classes using javac

C:\JUNIT_WORKSPACE>javac WalmartApiResultsTest.java,
	WalmartApiResponseTest.java,
	WalmartApiSearchTest.java,
	WalmartItemTest.java
	
Now run the Test Runner program below to run the WalmartApiTestSuite

C:\JUNIT_WORKSPACE>java WalmartTestRunner
