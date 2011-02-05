package org.json.simple.compliance;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.json.test.compliance.std.DecodeException;
import org.json.test.compliance.std.JSONDecoder;
import org.leixu.iwap.json.JSONArray;
import org.leixu.iwap.json.JSONObject;
import org.leixu.iwap.json.parser.ContentHandler;
import org.leixu.iwap.json.parser.JSONParser;
import org.leixu.iwap.json.parser.ParseException;

public class StreamDecoderImpl implements JSONDecoder {
	static class Transformer implements ContentHandler {
		private Stack valueStack;

		public Object getResult() {
			if (valueStack == null || valueStack.size() == 0)
				return null;
			return valueStack.peek();
		}

		public boolean endArray() throws ParseException, IOException {
			trackBack();
			return true;
		}

		public void endJSON() throws ParseException, IOException {
		}

		public boolean endObject() throws ParseException, IOException {
			trackBack();
			return true;
		}

		public boolean endObjectEntry() throws ParseException, IOException {
			Object value = valueStack.pop();
			Object key = valueStack.pop();
			Map parent = (Map) valueStack.peek();
			parent.put(key, value);
			return true;
		}

		private void trackBack() {
			if (valueStack.size() > 1) {
				Object value = valueStack.pop();
				Object prev = valueStack.peek();
				if (prev instanceof String) {
					valueStack.push(value);
				}
			}
		}

		private void consumeValue(Object value) {
			if (valueStack.size() == 0)
				valueStack.push(value);
			else {
				Object prev = valueStack.peek();
				if (prev instanceof List) {
					List array = (List) prev;
					array.add(value);
				} else {
					valueStack.push(value);
				}
			}
		}

		public boolean primitive(Object value) throws ParseException,
				IOException {
			consumeValue(value);
			return true;
		}

		public boolean startArray() throws ParseException, IOException {
			List array = new JSONArray();
			consumeValue(array);
			valueStack.push(array);
			return true;
		}

		public void startJSON() throws ParseException, IOException {
			valueStack = new Stack();
		}

		public boolean startObject() throws ParseException, IOException {
			Map object = new JSONObject();
			consumeValue(object);
			valueStack.push(object);
			return true;
		}

		public boolean startObjectEntry(String key) throws ParseException,
				IOException {
			valueStack.push(key);
			return true;
		}

	};

	public Object fromJSONString(String jsonString) throws DecodeException {
		Object value = null;
		JSONParser parser = new JSONParser();
		Transformer transformer = new Transformer();

		try {
			parser.parse(jsonString, transformer);
			value = transformer.getResult();
			return Mapper.transformToStd(value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DecodeException(e);
		}
	}

}
