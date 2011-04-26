package org.leixu.jersey.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONObject;

public class Caller {

	private static String serverUrl = "http://localhost:8080/jersey16/rest";
	private static DefaultHttpClient httpclient = null;

	private static String userName = "userName";
	private static String passWord = "passWord";

	/**
	 * Singleton HttpClient
	 * 
	 * @return
	 */
	private static DefaultHttpClient getHttpClient() {
		if (null == httpclient) {

			int timeoutConnection = 50000;// millisecond
			int timeoutSocket = 120000;

			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters,
					timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			HttpClientParams.setRedirecting(httpParameters, true);
			HttpClientParams.setCookiePolicy(httpParameters,
					CookiePolicy.BROWSER_COMPATIBILITY);

			HttpProtocolParams.setUserAgent(httpParameters,
					"apache.http.client");

			httpclient = new DefaultHttpClient(httpParameters);

			// CookieStore cookieStore = httpclient.getCookieStore();

			// HttpHost proxy = new HttpHost("someproxy", 8080);
			// httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
			// proxy);

		}
		return httpclient;
	}

	/**
	 * HttpGet JSON
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getJson(String url) throws Exception {
		url = serverUrl + url;

		HttpGet httpget = new HttpGet(url);
		httpget.addHeader("userName", userName);
		httpget.addHeader("passWord", passWord);
		httpget.setHeader("Accept", "application/json");

		// httpget.getParams().setParameter(arg0, arg1);

		HttpResponse response;
		response = getHttpClient().execute(httpget);

		final int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("HttpStatusCode:" + statusCode);
		}

		HttpEntity entity = response.getEntity();

		JSONObject json = null;
		if (entity != null) {
			InputStream instream = entity.getContent();
			String result = convertStreamToString(instream);
			json = new JSONObject(result);
			instream.close();
			return json;
		}
		return json;
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getText(String url) throws Exception {
		url = serverUrl + url;
		String result = "";
		HttpGet httpget = new HttpGet(url);

		httpget.addHeader("userName", "userName");
		httpget.addHeader("passWord", "passWord");
		httpget.setHeader("Accept", "text/plain");

		HttpResponse response;
		response = getHttpClient().execute(httpget);
		final int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("HttpStatusCode:" + statusCode);
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			result = convertStreamToString(instream);
			instream.close();
		}
		return result;
	}

	/**
	 * HttpPut
	 * 
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 * @throws ClientProtocolException
	 */
	public static HttpResponse putJson(String url, JSONObject data)
			throws Exception, ClientProtocolException {

		HttpPut httpPut = new HttpPut(serverUrl + url);

		httpPut.addHeader("userName", userName);
		httpPut.addHeader("passWord", passWord);
		httpPut.setHeader("Accept", "application/json");

		HttpEntity entity = new StringEntity(data.toString());
		httpPut.setEntity(entity);

		HttpResponse response;
		response = httpclient.execute(httpPut);

		final int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("HttpStatusCode:" + statusCode);
		}

		return response;
	}

	public HttpResponse postJosn(String url, String jsonData) throws Exception {
		HttpPost httpPost = new HttpPost("url");
		httpPost.addHeader("userName", userName);
		httpPost.addHeader("passWord", passWord);
		httpPost.setHeader("Accept", "application/json");
		httpPost.addHeader("content-type", "application/json");
		HttpEntity entity = new StringEntity(jsonData);
		httpPost.setEntity(entity);
		HttpResponse response = getHttpClient().execute(httpPost);
		return response;
	}


	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
