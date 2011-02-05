package org.json.simple.compliance;

import java.io.IOException;
import java.io.StringWriter;

import org.json.test.compliance.std.JSONEncoder;

public class StreamEncoderImpl implements JSONEncoder {

	public String toJSONString(Object obj) {
		try {
			StringWriter out = new StringWriter();
			((org.leixu.iwap.json.JSONStreamAware) Mapper.transformToImpl(obj))
					.writeJSONString(out);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}
