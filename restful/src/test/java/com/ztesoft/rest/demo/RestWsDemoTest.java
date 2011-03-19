//package com.ztesoft.rest.demo;
//
//import static org.junit.Assert.fail;
//
//import javax.ws.rs.core.MultivaluedMap;
//
//import org.junit.Test;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.core.util.MultivaluedMapImpl;
//
//public class RestWsDemoTest {
//	private String url = "http://localhost:8080/jerseywstest/rest/students";
//
//	@Test
//	public void testGetMetadata() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAllStudents() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddStudent() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveStudent() {
//		Client client = Client.create();
//		WebResource webResource = client.resource(url + "/delete/1");
//		ClientResponse response = webResource.delete(ClientResponse.class);
//		System.out.println("Response for delete request: " + response.getStatus());
//	}
//
//	@Test
//	public void testPutStudent() {
//		Client client = Client.create();
//		WebResource webResource = client.resource(url + "/put");
//		MultivaluedMap queryParams = new MultivaluedMapImpl();
//		queryParams.add("studentid", "2");
//		queryParams.add("name", "nametest");
//		queryParams.add("dept", "depttest");
//		ClientResponse response = webResource.queryParams(queryParams).put(
//				ClientResponse.class, "foo:test");
//		System.out.println("Response for put request: " + response.getStatus());
//	}
//
//}
