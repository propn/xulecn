package com.ztesoft.framework;

import java.io.OutputStreamWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.buffalo.request.RequestContext;
import net.buffalo.request.RequestContextUtil;
import net.buffalo.request.ValidationException;
import net.buffalo.service.BuffaloWorker;
import net.buffalo.service.ServiceRepository;
import net.buffalo.service.ServiceRepositoryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ztesoft.common.exception.CommonError;
import com.ztesoft.common.exception.CommonException;
import com.ztesoft.common.exception.CrmException;
import com.ztesoft.common.exception.ErrorLevel;
//import com.ztesoft.component.common.signon.SignOnService;

public class CRMFrameworkBuffaloWorker implements CRMRequestWorker {

	private static final String SignOnService = "com.ztesoft.component.common.signon.SignOnService" ;
	private static final String OUTPUT_ENCODING = "utf-8";

	private static final Log LOGGER = LogFactory.getLog(BuffaloWorker.class);

	private Object service = null;

	protected RequestContext requestContext;

	public void initRequest(ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		String requestService = getWorkerRelativePath(request.getPathInfo());
		ServiceRepository repository = ServiceRepositoryUtil
				.getServiceRepository(context);

		service = repository.get(requestService);
	}

	public void validate(HttpServletRequest request,
			HttpServletResponse response) throws ValidationException,
			CrmException {
		// 其它的登陆判断在这里加。
		// session失效提示控制 2010-09-12
		if (service == null) {
			CommonError cerr = new CommonError(CommonError.INVALID_REQ_ERROR);
			cerr.setLevel(ErrorLevel.ERROR);
			throw new CommonException(cerr);

		} else if (service.getClass().getName().equals(SignOnService) ) { // SignOnService可以直接访问
			return;
		}
//		System.out.println(request.getSession().getId()+">>"+request.getSession().getAttribute("LoginRtnParamsHashMap")) ;
		if (request.getSession() == null
				|| request.getSession().getAttribute("LoginRtnParamsHashMap") == null) {
			CommonError cerr = new CommonError(CommonError.LOGIN_TIMEOUT_ERROR);
			cerr.setLevel(ErrorLevel.WARN);
			cerr.setErrorMessage("登陆超时，请重新登陆!");
			throw new CommonException(cerr);
		}
	}

	public void processRequest(ServletContext context,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable, CrmException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("easonwu tt invoking buffalo worker");
		}

		if (service == null) {
			throw new CommonException(new CommonError(
					CommonError.INVALID_REQ_ERROR));
		}
		response.setHeader("content-type", "text/xml;charset="
				+ OUTPUT_ENCODING);
		ServletInputStream inputStream = request.getInputStream();

		RequestContextUtil.createRequestContext(context, request, response);

		CRMFrameworkBuffaloInvoker.getInstance().invoke(
				service,
				inputStream,
				new OutputStreamWriter(response.getOutputStream(),
						OUTPUT_ENCODING));
	}

	protected String getWorkerRelativePath(String pathInfo) {
		String[] terms = pathInfo.split("/");
		String prefix = "/" + terms[1] + "/";

		String relative = pathInfo.substring(prefix.length());

		return relative;

	}
}
