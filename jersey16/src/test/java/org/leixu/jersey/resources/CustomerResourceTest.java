package org.leixu.jersey.resources;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.leixu.jersey.bean.Customer;
import org.leixu.jersey.util.JsonUtil;

public class CustomerResourceTest {

	@Test
	public void testAddCustomer() throws Exception {

		Customer cust = new Customer();
		cust.setCustomerId(2);
		cust.setFirstName("ease");
		cust.setLastName("wu");
		cust.setZipcode("511400");

		String addCustomerJson = JsonUtil.toJson(cust);

		System.out.println(addCustomerJson);

		HttpPost httpPost = new HttpPost(
				"http://localhost:8080/jersey16/rest/customer");

		httpPost.addHeader("content-type", "application/json");

		HttpEntity entity = new StringEntity(addCustomerJson);
		httpPost.setEntity(entity);

		DefaultHttpClient client = new DefaultHttpClient();

		HttpResponse result = client.execute(httpPost);

		System.out.println("Response status code: "
				+ result.getStatusLine().getStatusCode());
		System.out.println("Response headers:");
		Header[] headers = httpPost.getAllHeaders();
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i].toString());
		}
	}

	@Test
	public void testDeleteCustomer() throws Exception {

	}

	@Test
	public void testUpdateCustomer() throws Exception {

	}

	@Test
	public void testGetCustomer() throws Exception {

	}

}
