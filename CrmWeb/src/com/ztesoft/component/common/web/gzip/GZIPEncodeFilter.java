package com.ztesoft.component.common.web.gzip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.*;

/**
 * 
 * GZip压缩过滤器
 * 根据需要开启过相关资源进行压缩
 * css/js/jsp 等，只需在web.xml去掉相应注释
 * 
 *
 */

public class GZIPEncodeFilter implements Filter {
	public void init(FilterConfig filterConfig) {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String transferEncoding = getGZIPEncoding((HttpServletRequest) request);
		//不支持，按普通处理
		if (transferEncoding == null) {
			chain.doFilter(request, response);

		} else {
			//浏览器支持gzip处理
			((HttpServletResponse) response).setHeader("Content-Encoding",
					transferEncoding);
			GZIPEncodableResponse wrappedResponse = new GZIPEncodableResponse(
					(HttpServletResponse) response);
			chain.doFilter(request, wrappedResponse);
			wrappedResponse.flush();

		}
	}

	public void destroy() {
	}

	/**
	 * 判断浏览器是否支持gzip 压缩
	 * @param request
	 * @return
	 */
	private static String getGZIPEncoding(HttpServletRequest request) {
		String acceptEncoding = request.getHeader("Accept-Encoding");
		if (acceptEncoding == null)
			return null;
		acceptEncoding = acceptEncoding.toLowerCase();
		if (acceptEncoding.indexOf("x-gzip") >= 0) {
			return "x-gzip";
		}
		if (acceptEncoding.indexOf("gzip") >= 0) {
			return "gzip";
		}
		return null;

	}

	
}
