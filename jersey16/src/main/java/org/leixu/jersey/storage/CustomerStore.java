package org.leixu.jersey.storage;

import java.util.HashMap;
import java.util.Map;

import org.leixu.jersey.bean.Customer;

public class CustomerStore {
	private static Map<Integer, Customer> store;
	private static CustomerStore instance = null;

	private CustomerStore() {
		store = new HashMap<Integer, Customer>();
		initOneCustomer();
	}

	public static Map<Integer, Customer> getStore() {
		if (instance == null) {
			instance = new CustomerStore();
		}
		return store;
	}

	private static void initOneCustomer() {
		Customer cust = new Customer();
		cust.setCustomerId(1);
		cust.setFirstName("Thunder");
		cust.setLastName("xu");
		cust.setZipcode("511430");

		store.put(cust.getCustomerId(), cust);
	}
}
