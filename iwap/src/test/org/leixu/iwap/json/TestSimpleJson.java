/**
 * 
 */
package org.leixu.iwap.json;


import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


public class TestSimpleJson {
	public static void testEncodeJSONObject(){
		JSONObject obj = new JSONObject();
		obj.put("name", "foo");
		obj.put("num", 100);
		obj.put("balace", new Double(1000.23));
		obj.put("is_vip", true);
		obj.put("nickname",null);
		System.out.println(obj);
	}
	
	public static void testStreamingEncodeJSONObject(){
		JSONObject obj = new JSONObject();
		obj.put("name", "foo");
		obj.put("num", 100);
		obj.put("balace", new Double(1000.23));
		obj.put("is_vip", true);
		obj.put("nickname",null);
		try {
			StringWriter out = new StringWriter();
			obj.writeJSONString(out);
			System.out.println(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testEncodeJSONObjectUseMap(){
		Map map = new LinkedHashMap();
		map.put("name", "foo");
		map.put("num", 100);
		map.put("balace", new Double(1000.23));
		map.put("is_vip", true);
		map.put("nickname",null);
		String jsonText = JSONValue.toJSONString(map);
		System.out.println(jsonText);
	}
	
	public static void testEncodeJSONObjectUseMapAndStreaming(){
		Map map = new LinkedHashMap();
		map.put("name", "foo");
		map.put("num", 100);
		map.put("balace", new Double(1000.23));
		map.put("is_vip", true);
		map.put("nickname",null);
		try {
			StringWriter out = new StringWriter();
			JSONValue.writeJSONString(map, out);
			System.out.println(out.toString());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void testEncodeJSONArray(){
		JSONArray array = new JSONArray();
		array.add("foo");
		array.add(100);
		array.add(new Double(1000.21));
		array.add(new Boolean(true));
		array.add(null);
		System.out.println(array);
	}
	
	public static void testEncodeJSONArrayUseStreaming(){
		JSONArray array = new JSONArray();
		array.add("foo");
		array.add(100);
		array.add(new Double(1000.21));
		array.add(new Boolean(true));
		array.add(null);
		try {
			StringWriter out = new StringWriter();
			array.writeJSONString(out);
			System.out.println(out.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testEncodeJSONArrayUseList(){
		LinkedList list = new LinkedList();
		list.add("foo");
		list.add(11);
		list.add(new Double(1000.21));
		list.add(null);
		String jsonText = JSONValue.toJSONString(list);
		System.out.println(jsonText);
	}
	
	public static void testEncodeJSONArrayUseListAndStreaming(){
		LinkedList list = new LinkedList();
		  list.add("foo");
		  list.add(new Integer(100));
		  list.add(new Double(1000.21));
		  list.add(new Boolean(true));
		  list.add(null);
		  try {
			  StringWriter out = new StringWriter();
			  JSONValue.writeJSONString(list, out);
			  String jsonText = out.toString();
			  System.out.print(jsonText);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void testMergeTwoJSONObjects(){
		JSONObject obj1 = new JSONObject();
		obj1.put("name", "foo");
		obj1.put("num", 100);
		obj1.put("balace", new Double(1000.23));
		
		JSONObject obj2 = new JSONObject();
		obj2.put("is_vip",new Boolean(true));
		obj2.put("nickname",null);
		
		obj2.putAll(obj1);
	}
	
	public static void testMergeTwoJSONArray(){
		 JSONArray list1 = new JSONArray();
		  list1.add("foo");
		  list1.add(new Integer(100));
		  list1.add(new Double(1000.21));
		  
		  JSONArray list2 = new JSONArray();
		  list2.add(new Boolean(true));
		  list2.add(null);
		  list2.addAll(list1);
		  System.out.print(list2);
	}
	
	public static void testCombinationMerge(){
		 JSONArray list1 = new JSONArray();
		  list1.add("foo");
		  list1.add(new Integer(100));
		  list1.add(new Double(1000.21));
		  
		  JSONArray list2 = new JSONArray();
		  list2.add(new Boolean(true));
		  list2.add(null);
		                
		  JSONObject obj = new JSONObject();
		  obj.put("name","foo");
		  obj.put("num",new Integer(100));
		  obj.put("balance",new Double(1000.21));
		  obj.put("is_vip",new Boolean(true));
		  obj.put("nickname",null);
		    
		  obj.put("list1", list1);
		  obj.put("list2", list2);
		                
		  System.out.println(obj);
	}
	public static void main(String[] args) {
		testEncodeJSONObject();
		testStreamingEncodeJSONObject();
		testEncodeJSONObjectUseMap();
		testEncodeJSONObjectUseMapAndStreaming();
		testEncodeJSONArray();
		testEncodeJSONArrayUseStreaming();
		testEncodeJSONArrayUseList();
		testEncodeJSONArrayUseListAndStreaming();
		testMergeTwoJSONObjects();
		testMergeTwoJSONArray();
		testCombinationMerge();
	}
}