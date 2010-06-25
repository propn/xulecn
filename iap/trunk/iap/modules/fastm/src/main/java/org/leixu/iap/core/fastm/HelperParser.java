package org.leixu.iap.core.fastm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;


public class HelperParser {
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static DynamicPart parse(String str){
		StringReader strReader = new StringReader(str);
		BufferedReader reader = new BufferedReader(strReader);
		return parse(reader);
	}

	/**
	 * 
	 * @param reader
	 * @return
	 */
	public static DynamicPart parse(BufferedReader reader){
		try{
			DynamicPart template = (DynamicPart)Parser.parse(reader);
			return template;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param reader
	 * @return
	 */
	public static DynamicPart parse(InputStream inputStream){
		try{
			DynamicPart template = (DynamicPart)Parser.parse(inputStream, "utf-8");
			return template;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
