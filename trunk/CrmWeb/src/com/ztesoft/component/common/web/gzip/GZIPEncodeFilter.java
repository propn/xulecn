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
 * GZipѹ��������
 * ������Ҫ�����������Դ����ѹ��
 * css/js/jsp �ȣ�ֻ����web.xmlȥ����Ӧע��
 * 
 *
 */

public class GZIPEncodeFilter implements Filter {
	public void init(FilterConfig filterConfig) {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String transferEncoding = getGZIPEncoding((HttpServletRequest) request);
		//��֧�֣�����ͨ����
		if (transferEncoding == null) {
			chain.doFilter(request, response);

		} else {
			//�����֧��gzip����
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
	 * �ж�������Ƿ�֧��gzip ѹ��
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
