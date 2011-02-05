package org.json.simple.compliance;

import org.json.test.compliance.std.JSONEncoder;

public class EncoderImpl implements JSONEncoder {

	public String toJSONString(Object obj) {
		return Mapper.transformToImpl(obj).toString();
	}

}
