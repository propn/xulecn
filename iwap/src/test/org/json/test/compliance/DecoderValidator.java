package org.json.test.compliance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.test.compliance.std.JSONDecoder;
import org.json.test.compliance.std.JSONEncoder;

public class DecoderValidator {
	private JSONEncoder riEncoder = new org.json.compliance.EncoderImpl();
	private JSONDecoder riDecoder = new org.json.compliance.DecoderImpl();

	public boolean isValidated(File file, JSONDecoder testee) throws Exception {
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

	public boolean isValidated(String jsonString, JSONDecoder testee)
			throws Exception {
		String s0 = null;
		String s = null;

		s0 = riEncoder.toJSONString(riDecoder.fromJSONString(jsonString));

		Object resultGraph = testee.fromJSONString(jsonString);
		s = riEncoder.toJSONString(resultGraph);

		return s0.equals(s);
	}
}
