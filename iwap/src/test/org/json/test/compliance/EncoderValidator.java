package org.json.test.compliance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.test.compliance.std.JSONDecoder;
import org.json.test.compliance.std.JSONEncoder;

public class EncoderValidator {
	private JSONDecoder riDecoder = new org.json.compliance.DecoderImpl();

	private JSONDecoder simpleDecoder;
	private JSONEncoder simpleEncoder;

	public boolean isValidated(File file, JSONEncoder testee) throws Exception {
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];

		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "utf-8"));
			int len = 0;

			do {
				len = in.read(buf);
				if (len == -1)
					break;
				sb.append(buf, 0, len);
			} while (len >= 0);
			return isValidated(sb.toString(), testee);
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public boolean isValidated(String jsonString, JSONEncoder testee)
			throws Exception {
		String s0 = null;
		String s = null;

		if (jsonString.trim().charAt(0) == '[')
			s0 = new JSONArray(jsonString).toString(0);
		else
			s0 = new JSONObject(jsonString).toString(0);

		Object metaGraph = riDecoder.fromJSONString(jsonString);
		String resultString = testee.toJSONString(metaGraph);
		if (resultString.trim().charAt(0) == '[')
			s = new JSONArray(resultString).toString(0);
		else
			s = new JSONObject(resultString).toString(0);

		return s0.equals(s);
	}

	/**
	 * Validate encoder with JSON RI library and optional JSON.simple library.
	 * 
	 * @param jsonString
	 * @param testee
	 * @param enableSimple
	 *            If check testee with JSON.simple in addition to JSON RI
	 *            library.
	 * @return
	 * @throws Exception
	 */
	public boolean isValidated(String jsonString, JSONEncoder testee,
			boolean enableSimple) throws Exception {
		boolean result = isValidated(jsonString, testee);

		if (!result)
			return false;

		if (result && !enableSimple)
			return true;

		String s0 = null;
		String s = null;
		if (simpleDecoder == null)
			simpleDecoder = new org.json.simple.compliance.DecoderImpl();
		if (simpleEncoder == null)
			simpleEncoder = new org.json.simple.compliance.EncoderImpl();

		Object metaGraph = simpleDecoder.fromJSONString(jsonString);
		s0 = simpleEncoder.toJSONString(metaGraph);
		String resultString = testee.toJSONString(metaGraph);
		s = simpleEncoder.toJSONString(simpleDecoder
				.fromJSONString(resultString));

		return s0.equals(s);
	}
}
