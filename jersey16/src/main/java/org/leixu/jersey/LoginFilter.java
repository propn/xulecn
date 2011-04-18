package org.leixu.jersey;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class LoginFilter implements ContainerRequestFilter,
		ContainerResponseFilter {

	private static String loginPath = "/MmtWeb/rest/login/";
	private static String updatePath = "/MmtWeb/rest/version/";

	private static final Logger LOGGER = Logger.getLogger(LoginFilter.class
			.getName());

	private final Logger logger;

	private @Context
	HttpContext hc;

	// private @Context
	// ResourceConfig rc;

	private long id = 0;

	public LoginFilter() {
		this(LOGGER);
	}

	public LoginFilter(Logger logger) {
		this.logger = logger;
	}

	private synchronized void setId() {
		if (hc.getProperties().get("request-id") == null) {
			hc.getProperties().put("request-id", Long.toString(++id));
		}
	}

	public ContainerRequest filter(ContainerRequest request) {
		setId();

		Map<String, Cookie> cookies = request.getCookies();

		for (Iterator<Cookie> iterator = cookies.values().iterator(); iterator
				.hasNext();) {
			Cookie cookie = iterator.next();
			logger.info(cookie.getName() + " == : " + cookie.getValue());
		}

		MultivaluedMap<String, String> headers = request.getRequestHeaders();
		for (Map.Entry<String, List<String>> e : headers.entrySet()) {
			String header = e.getKey();
			for (String value : e.getValue()) {
				logger.info(header + " == : " + value);
			}
		}

		// 登录和版本更新
		if (request.getRequestUri().getPath().equals(loginPath)
				|| request.getRequestUri().getPath().equals(updatePath)) {
			return request;
		}

		String userName = headers.getFirst("userName");
		String passWord = headers.getFirst("passWord");
		// String lanId = headers.getFirst("lanId");

		if (null == userName || null == passWord || "".endsWith(userName)
				|| "".endsWith(passWord)) {
			URI url = request.getRequestUri();
			URI url2 = null;
			try {
				url2 = new URI(url.getScheme(), null, url.getHost(),
						url.getPort(), loginPath, url.getQuery(),
						url.getFragment());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			request.setUris(request.getBaseUri(), url2);
		}
		return request;
	}

	public ContainerResponse filter(ContainerRequest request,
			ContainerResponse response) {
		if (loginPath.equals(request.getRequestUri().getPath())
				&& null == response.getEntity()) {
			response.setStatus(401);// 未授权
			response.setEntity(null);
			return response;
		}
		return response;
	}

}