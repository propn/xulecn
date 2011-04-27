package org.leixu.jersey.resources;

import java.io.InputStream;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.leixu.jersey.bean.Customer;
import org.leixu.jersey.storage.CustomerStore;
import org.leixu.jersey.util.JsonUtil;
import org.leixu.jersey.util.StringUtil;

@Path("/customer")
public class CustomerResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCustomer(InputStream customerData) {

		String jsonData = StringUtil.convertStreamToString(customerData);
		System.out.println("######jsonData############");
		System.out.println(jsonData);
		System.out.println("######jsonData############");
		Customer cust = JsonUtil.fromJson(jsonData, Customer.class);
		try {
			CustomerStore.getStore().put(cust.getCustomerId(), cust);
			long customerId = cust.getCustomerId();
			return Response.created(URI.create("/" + customerId)).build();

		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@DELETE
	@Path("{id}")
	public void deleteCustomer(@PathParam("id") String customerId) {
		try {
			Customer cust = CustomerStore.getStore().get(customerId);
			if (cust == null) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}
			CustomerStore.getStore().remove(customerId);

		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomer(@PathParam("id") String customerId,
			InputStream input) {

		try {
			Customer cust = CustomerStore.getStore().get(customerId);
			if (cust == null) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}

			String jsonData = StringUtil.convertStreamToString(input);
			Customer cust2 = JsonUtil.fromJson(jsonData, Customer.class);

			cust.setFirstName(cust2.getFirstName());
			cust.setLastName(cust2.getLastName());

		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCustomer(@PathParam("id") int customerId,
			@Context HttpServletRequest request) {
		String rst = "";
		try {
			Customer cust = CustomerStore.getStore().get(customerId);
			rst = JsonUtil.toJson(cust, Customer.class);
		} catch (Exception e) {
			throw new WebApplicationException(e,
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		return rst;
	}

}
