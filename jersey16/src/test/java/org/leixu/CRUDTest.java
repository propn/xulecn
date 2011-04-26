package org.leixu;

import static org.junit.Assert.fail;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.leixu.jersey.bean.Address;
import org.leixu.jersey.bean.Contact;
import org.leixu.jersey.util.JsonUtil;
import org.leixu.jersey.util.Caller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CRUDTest {

	@Test
	public void testGetJson() {
		String url = "/contacts";
		try {
			System.out.println(Caller.getJson(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testGetText() {
		String url = "/hello";
		try {
			System.out.println(Caller.getText(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPutJson() {

		Gson gson = new Gson();
		String str = "{\"name\":\"Huang Yi Ming\",\"id\":\"1\",\"addresses\":[{\"city\":\"Shanghai\",\"street\":\"Long Hua Street\"},{\"city\":\"Shanghai\",\"street\":\"Dong Quan Street\"}]}";
		Contact ls;
		ls = gson.fromJson(str, Contact.class);
		String s = gson.toJson(ls, Contact.class);
		System.out.println(s);

		Address[] addrs = { new Address("广州", "广州天河"),
				new Address("上海", "上海浦东") };
		Contact cHuang = new Contact("2", "大众", Arrays.asList(addrs));

		List<Contact> lss = new ArrayList<Contact>();

		lss.add(ls);
		lss.add(cHuang);
		System.out.println("@########");
		System.out.println(gson.toJson(lss, List.class));

		System.out.println("########");

		Type targetType = new TypeToken<List<Contact>>() {
		}.getType();

		String str2 = "[{\"id\":\"1\",\"name\":\"Huang Yi Ming\",\"addresses\":[{\"city\":\"Shanghai\",\"street\":\"Long Hua Street\"},{\"city\":\"Shanghai\",\"street\":\"Dong Quan Street\"}]},{\"id\":\"2\",\"name\":\"大众\",\"addresses\":[{\"city\":\"广州\",\"street\":\"广州天河\"},{\"city\":\"上海\",\"street\":\"上海浦东\"}]}]";

		lss = gson.fromJson(str2, new TypeToken<List<Contact>>() {
		}.getType());

		String sUserList1 = gson.toJson(lss, targetType);
		String sUserList2 = JsonUtil.toJson(lss, targetType);

		System.out.println(sUserList1);
		System.out.println(sUserList2);
		
		String url="";
		
		
	}

	@Test
	public void testPostJosn() {
		fail("Not yet implemented");
	}

	@Test
	public void testURLreader() {
		fail("Not yet implemented");
	}

}
