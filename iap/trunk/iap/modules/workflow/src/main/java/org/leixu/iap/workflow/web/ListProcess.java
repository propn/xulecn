package org.leixu.iap.workflow.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.leixu.iap.workflow.domain.ProcessResult;
import org.leixu.iap.workflow.domain.logic.ProcessService;
import org.leixu.iap.workflow.util.SimpleXMLWorkShop;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ListProcess implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("list process.");

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		List list = this.getProcessService().listProcess();

		SimpleXMLWorkShop.outputXML(ProcessResult.convertFilesToXml(list),
				response.getOutputStream());

		// not redirect to other view,it processed on response
		return null;
	}

	/**
	 * @return Returns the processService.
	 */
	public ProcessService getProcessService() {
		return processService;
	}

	/**
	 * @param processService
	 *            The processService to set.
	 */
	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	private Log log = LogFactory.getLog(ListProcess.class);

	private ProcessService processService;

}
