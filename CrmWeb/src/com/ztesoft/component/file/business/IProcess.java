package com.ztesoft.component.file.business;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.ztesoft.component.file.response.UploadResponse;

public interface IProcess {
	public UploadResponse process(HttpServletRequest request,
			HttpServlet servlet) throws ServletException, IOException,
			Exception;
}
