package org.json.simple.compliance;

import org.json.test.compliance.std.DecodeException;
import org.json.test.compliance.std.JSONDecoder;
import org.leixu.iwap.json.JSONValue;

public class DecoderImpl implements JSONDecoder {

	public Object fromJSONString(String jsonString) throws DecodeException {
		Object value = null;

		try {
			value = JSONValue.parseWithException(jsonString);
		} catch (Exception e) {
			throw new DecodeException(e);
		}

		return Mapper.transformToStd(value);
	}

}
