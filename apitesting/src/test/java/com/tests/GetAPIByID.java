package com.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.client.RestClient;
import com.util.TestUtil;

public class GetAPIByID extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url, IDurl;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	JSONObject responseJson, initialResponse;
	int id, Totalcount;

	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");

		url = serviceUrl + apiUrl; // Endpoint URL All
		restClient = new RestClient();
		closebaleHttpResponse = restClient.get(url);
		String initialResponseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		initialResponse = new JSONObject(initialResponseString);
		Totalcount = Integer.parseInt(TestUtil.getValueByJPath(initialResponse, "/count"));
		Random rn = new Random();
		id = rn.nextInt((Totalcount - 1) + 1) + 1;
		IDurl = serviceUrl + apiUrl + id; // Endpoint URL with random ID
		System.out.println(IDurl);
		closebaleHttpResponse = restClient.get(IDurl);
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		responseJson = new JSONObject(responseString);
	}

	@Test(description = "Status")
	public void validateStatus() throws ClientProtocolException, IOException {

		// a. Status Code:
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

		// b. Json String:
		System.out.println("Response JSON from API---> " + responseJson);

		// c. All Headers
		Header[] headersArray = closebaleHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array-->" + allHeaders);
	}

	@Test(description = "Name should not be null")
	public void validateNameIsNotNull() throws ClientProtocolException, IOException {
		String result = TestUtil.getValueByJPath(responseJson, "/name");
		System.out.println(result);
		Assert.assertTrue(result != null);
	}

	@Test(description = "Mandatory fields are present")
	public void validateMandatoryFields() throws ClientProtocolException, IOException {
		Assert.assertTrue(responseJson.has("name"), "Name");
		Assert.assertTrue(responseJson.has("rotation_period"), "rotation_period");
		Assert.assertTrue(responseJson.has("orbital_period"), "orbital_period");
		Assert.assertTrue(responseJson.has("diameter"), "diameter");
		Assert.assertTrue(responseJson.has("climate"), "climate");
		Assert.assertTrue(responseJson.has("gravity"), "gravity");
		Assert.assertTrue(responseJson.has("terrain"), "terrain");
		Assert.assertTrue(responseJson.has("surface_water"), "surface_water");
		Assert.assertTrue(responseJson.has("population"), "population");
		Assert.assertTrue(responseJson.has("residents"), "residents");
		Assert.assertTrue(responseJson.has("films"), "films");
		Assert.assertTrue(responseJson.has("created"), "created");
		Assert.assertTrue(responseJson.has("edited"), "edited");
		Assert.assertTrue(responseJson.has("url"), "url");
	}

	@Test(description = "Url data is correct")
	public void validateUrlData() throws ClientProtocolException, IOException {
		String url = TestUtil.getValueByJPath(responseJson, "/url");
		Assert.assertEquals(url, IDurl + '/');
	}

	@Test(description = "Error displayed if ID not available")
	public void validateIDNotAvailable() throws ClientProtocolException, IOException {

		IDurl = serviceUrl + apiUrl + (Totalcount + 1); // Endpoint URL with ID which is not available
		System.out.println(IDurl);
		closebaleHttpResponse = restClient.get(IDurl);
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		responseJson = new JSONObject(responseString);

		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
		Assert.assertEquals(statusCode, 404, "Status code is not 404");

		String detail = TestUtil.getValueByJPath(responseJson, "/detail");
		System.out.println(detail);
		Assert.assertEquals(detail, "Not found");
	}

	@Test(description = "Error displayed if ID has invalid character")
	public void validateIDWithInvalid() throws ClientProtocolException, IOException {

		IDurl = serviceUrl + apiUrl + "ab$"; // Endpoint URL with ID which is not available
		System.out.println(IDurl);
		closebaleHttpResponse = restClient.get(IDurl);
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		responseJson = new JSONObject(responseString);

		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_404, "Status code is not 404");

		String detail = TestUtil.getValueByJPath(responseJson, "/detail");
		System.out.println(detail);
		Assert.assertEquals(detail, "Not found");
	}

	@AfterMethod
	public void close() throws IOException {
		closebaleHttpResponse.close();
	}
}
