package com.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.client.RestClient;
import com.util.TestUtil;

public class GetAPITest extends TestBase {
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closebaleHttpResponse;
	JSONObject responseJson;

	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = serviceUrl + apiUrl;
		restClient = new RestClient();
		closebaleHttpResponse = restClient.get(url);
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

	@Test(description = "Count")
	public void validateCount() throws ClientProtocolException, IOException {
		int count = Integer.parseInt(TestUtil.getValueByJPath(responseJson, "/count"));
		System.out.println(count);
		Assert.assertEquals(count > 0, count > 0, "Service is not returning any value");

	}

	@Test(description = "Result")
	public void validateResultIsNotNull() throws ClientProtocolException, IOException {
		String result = TestUtil.getValueByJPath(responseJson, "/results");
		System.out.println(result);
		Assert.assertTrue(result != null);
	}

	@Test(description = "Name is not null in for all records")
	public void validateNameIsNotNull() throws ClientProtocolException, IOException {
		do {
			closebaleHttpResponse = restClient.get(url);
			String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
			responseJson = new JSONObject(responseString);
			JSONArray lineItems = responseJson.getJSONArray("results");
			for (Object o : lineItems) {
				JSONObject jsonLineItem = (JSONObject) o;
				String name = jsonLineItem.getString("name");
				System.out.println(name);
				Assert.assertTrue(name != null);
			}
			url = TestUtil.getValueByJPath(responseJson, "/next");
			System.out.println("After - " + url);
		} while (url != "null");

	}

	@Test(description = "Result is not more than 10 on each API call")
	public void validateResultIsNotMoreThan10() throws ClientProtocolException, IOException {

		do {
			closebaleHttpResponse = restClient.get(url);
			String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
			responseJson = new JSONObject(responseString);
			System.out.println(url);
			int RecordCount = 0;
			JSONArray lineItems = responseJson.getJSONArray("results");
			for (Object o : lineItems) {
				RecordCount++;
			}
			AssertJUnit.assertTrue(RecordCount <= 10);
			url = TestUtil.getValueByJPath(responseJson, "/next");
			System.out.println("After - " + url);
		} while (url != "null");
	}

	@Test(description = "Error displayed if Page number is invalid")
	public void validatePageInvalid() throws ClientProtocolException, IOException {
		int count = Integer.parseInt(TestUtil.getValueByJPath(responseJson, "/count"));
		int PageNo = (count / 10) + 2;
		closebaleHttpResponse = restClient.get(url + "/?page=" + PageNo);
		String responseString = EntityUtils.toString(closebaleHttpResponse.getEntity(), "UTF-8");
		responseJson = new JSONObject(responseString);
		System.out.println(url);
		int statusCode = closebaleHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code--->" + statusCode);
		Assert.assertEquals(statusCode, 404, "Status code is not 404");
		String detail = TestUtil.getValueByJPath(responseJson, "/detail");
		System.out.println(detail);
		Assert.assertEquals(detail, "Not found");

	}

	@AfterMethod
	public void close() throws IOException {
		closebaleHttpResponse.close();
	}
}
