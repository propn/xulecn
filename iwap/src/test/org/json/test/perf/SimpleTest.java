/**
 * Base on the code from qrtt1.
 */
package org.json.test.perf;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.LinkedHashMap;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.test.perf.std.Executable;
import org.leixu.iwap.json.parser.ContentHandler;
import org.leixu.iwap.json.parser.ParseException;

public class SimpleTest extends TestCase {
	static int LOOP = 20;

	static String get() {
		return String.valueOf(Math.random() * Integer.MAX_VALUE);
	}

	static String getTestData() {
		org.json.test.compliance.DataGenerator dataGen = new org.json.test.compliance.DataGenerator();
		dataGen.minNodeCount = 1500;
		dataGen.maxNodeCount = 1500;

		org.json.JSONObject o = null;

		try {
			String s = dataGen.generateJSONString();
			if (s.trim().charAt(0) == '[') {
				o = new org.json.JSONObject();
				o.put("v", new org.json.JSONArray(s));

			} else
				o = new org.json.JSONObject(s);
		} catch (Exception e) {
			System.out.println(e);
			o = new org.json.JSONObject();
		}

		org.json.JSONObject o2 = new org.json.JSONObject();
		org.json.JSONObject o3 = new org.json.JSONObject(new LinkedHashMap());

		try {
			o2.put("key", "wrong value");
			o.put("obj", o2);
			o3.put("a", o);
			o3.put("key", get());
			o3.put("z", o);
		} catch (JSONException e) {
		}
		try {
			return o3.toString(1);
		} catch (Exception e) {
			return "{}";
		}
	}

	static void runTest(String message, Executable e) {
		long t1 = System.currentTimeMillis();
		String s = null;
		for (int i = 0; i < LOOP; i++) {
			s = e.run();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(message + " :: " + ((t2 - t1) / (LOOP + 0D))
				+ " ms\t\t" + s);
	}

	public void testPerf() throws JSONException {

		final String data = getTestData();
		final String key = "key";

		System.out.println("==sample==");
		System.out.println("len:" + data.length());

		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream("sample.json"),
					"utf-8");
			out.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}

		LOOP = 20;
		run(data, key);

	}

	private static void gc() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException ie) {
		}
		System.gc();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException ie) {
		}
	}

	private static void run(final String data, final String key) {
		gc();
		runTest("org.json.JSONObject", new Executable() {
			public String run() {
				try {
					return (String) new org.json.JSONObject(data).get(key);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		gc();
		runTest("org.json.simple.JSONParser", new Executable() {
			public String run() {
				try {
					return (String) ((org.leixu.iwap.json.JSONObject) new org.leixu.iwap.json.parser.JSONParser()
							.parse(new StringReader(data))).get(key);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		gc();
		runTest("org.json.simple.JSONParser with ContentHandler",
				new Executable() {
					class MyHandler implements ContentHandler {
						private String key;
						private Object value;
						private String tmpKey;
						private int level = 0;

						public void setKey(String key) {
							this.key = key;
						}

						public Object getValue() {
							return value;
						}

						public boolean endArray() throws ParseException {
							level--;
							return true;
						}

						public void endJSON() throws ParseException {
						}

						public boolean endObject() throws ParseException {
							level--;
							return true;
						}

						public boolean endObjectEntry() throws ParseException {
							return true;
						}

						public boolean primitive(Object value)
								throws ParseException {
							if (tmpKey != null && tmpKey.equals(key)
									&& level == 1) {
								this.value = value;
								return false;
							}
							return true;
						}

						public boolean startArray() throws ParseException {
							level++;
							return true;
						}

						public void startJSON() throws ParseException {
							level = 0;
							tmpKey = null;
						}

						public boolean startObject() throws ParseException {
							level++;
							return true;
						}

						public boolean startObjectEntry(String key)
								throws ParseException {
							if (key.equals(this.key)) {
								tmpKey = key;
							}
							return true;
						}
					};

					public String run() {
						MyHandler handler = new MyHandler();
						try {
							handler.setKey(key);
							new org.leixu.iwap.json.parser.JSONParser().parse(
									data, handler);
							return String.valueOf(handler.getValue());
						} catch (org.leixu.iwap.json.parser.ParseException e) {
							e.printStackTrace();
						}
						return null;
					}
				});

		/*
		 * gc(); runTest("org.codehaus.jettison.json.JSONObject", new
		 * Executable() { public String run() { try { return new
		 * org.codehaus.jettison.json.JSONObject(data) .getString(key); } catch
		 * (org.codehaus.jettison.json.JSONException e) { e.printStackTrace(); }
		 * return null; } });
		 */

		/*
		 * gc(); runTest("com.sdicons.json.model.JSONObject", new Executable() {
		 * public String run() { try { return
		 * ((com.sdicons.json.model.JSONObject) new
		 * com.sdicons.json.parser.JSONParser( new
		 * java.io.StringReader(data)).nextValue()).get(key).strip().toString();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return null; } });
		 */

		/*
		 * gc(); runTest("net.sf.json.JSONObject", new Executable() { public
		 * String run() { return (String)
		 * net.sf.json.JSONObject.fromObject(data) .get(key); } });
		 */

		/*
		 * gc(); runTest("org.codehaus.jackson", new Executable() {
		 * 
		 * @Override public String run() { try { org.codehaus.jackson.JsonParser
		 * jp = new org.codehaus.jackson.JsonFactory() .createJsonParser(new
		 * StringReader(data)); org.codehaus.jackson.JsonToken t = null; int
		 * level = 0; while ((t = jp.nextToken()) != null) {
		 * if(t.equals(org.codehaus.jackson.JsonToken.START_ARRAY) ||
		 * t.equals(org.codehaus.jackson.JsonToken.START_OBJECT)){ level++;
		 * continue; } if(t.equals(org.codehaus.jackson.JsonToken.END_ARRAY) ||
		 * t.equals(org.codehaus.jackson.JsonToken.END_OBJECT)){ level--;
		 * continue; } if (level == 1 &&
		 * org.codehaus.jackson.JsonToken.FIELD_NAME.equals(t) &&
		 * key.equals(jp.getCurrentName())) { jp.nextToken(); return
		 * jp.getText(); } }
		 * 
		 * } catch (org.codehaus.jackson.JsonParseException e) {
		 * e.printStackTrace(); } catch (java.io.IOException e) {
		 * e.printStackTrace(); } return null; } });
		 */

	}
}
