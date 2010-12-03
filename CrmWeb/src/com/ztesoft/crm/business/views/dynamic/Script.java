/**
 * 
 */
package com.ztesoft.crm.business.views.dynamic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;


/**
 * @author Aries
 * 
 */
public class Script {

	
	
	protected boolean scriptLanguage = true;



	public String get(ServletContext context,String path) {

		String content="";
		try {
			 content = this.renderJavascript(context,path);

		} catch (Exception e) {
			 e.printStackTrace();
		}
		return content;
	}
	private String renderJavascript(ServletContext context,String path) throws JspException {
		StringBuffer results = new StringBuffer();

		results.append(this.renderStartElement());

		results.append(this.createDynamicJavascript(context,path));

		results.append(getJavascriptEnd());
		return results.length() == 0 ? "" : results.toString();
	}

	private String getJavascriptEnd() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("</script>\n\n");

		return sb.toString();
	}

	private String renderStartElement() {
		StringBuffer start = new StringBuffer(
				"<script type=\"text/javascript\"");

		if (this.scriptLanguage) {
			start.append(" language=\"Javascript1.1\"");
		}

		start.append("> \n");
		return start.toString();
	}

	private String createDynamicJavascript(ServletContext context,String path) {
		String string="";
		File file = new File(context.getRealPath("/") + path);
		synchronized (this) {
			FileReader reader = null;
			try {
				reader = new FileReader(file);
				string = getStringFromReader(reader);

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		return string;
	}

	private String getStringFromReader(FileReader reader) throws IOException {
		char[] buffer = new char[4096];
		int len = 0;
		StringBuffer content = new StringBuffer(4096);
		try {
			while ((len = reader.read(buffer)) > -1) {
				content.append(buffer, 0, len);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (reader != null)
				reader.close();
		}
		return new String(content.toString().getBytes(), "GBK");
	}

}
