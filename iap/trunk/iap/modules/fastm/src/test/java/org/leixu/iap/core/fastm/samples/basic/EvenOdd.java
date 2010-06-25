package org.leixu.iap.core.fastm.samples.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.leixu.iap.core.fastm.ITemplate;
import org.leixu.iap.core.fastm.Parser;
import org.leixu.iap.core.fastm.samples.Common;

public class EvenOdd {
	public static Map makeMapModel(){
		Map map = new HashMap();
		int count = 1000;

		List lines = new ArrayList();
		for(int row = 0; row < 5; row++){
			List line = new ArrayList();
			for(int col = 0; col < 5; col++){
				count++;

				Map data = new HashMap();
				String type = count % 2 == 0 ? "even" : "odd";
				data.put(type, data);
				data.put("value", new Integer(count));
				
				line.add(data);
			}
			lines.add(line);
		}
		
		map.put("title", "Map Model");
		map.put("row", lines);

		return map;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ITemplate template = Parser.parse(ClassLoader.getSystemResource("").getPath().substring(1)+"/template/basic/evenOdd.html");
		Object mapModel = makeMapModel();
		Common.write(template, mapModel, ClassLoader.getSystemResource("").getPath().substring(1)+"/template/basic/evenOdd.m.html");
	}

}
