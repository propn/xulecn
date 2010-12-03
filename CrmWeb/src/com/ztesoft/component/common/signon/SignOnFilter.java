package com.ztesoft.component.common.signon;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class SignOnFilter implements Filter {
	private static Logger logger = Logger.getLogger(SignOnFilter.class);
	private static String signOnErrorPage = "index.html";
	private static String signOnPage = "index.html";
	private static String loginWan="LoginWan.jsp";
	private static String[] noSignOnPages = CrmConstants.NOT_FILTER_PAFGES;//{"base/updatePassword.jsp","VsopWebFor10000.jsp"};
	
	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse  response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest hreq = (HttpServletRequest) request;
		String currentURL = hreq.getRequestURI();

		request.setCharacterEncoding("GBK");
		
        int firstSlash = currentURL.indexOf("/",1); // jump past the starting slash
        String targetURL = null;
        if (firstSlash != -1) targetURL = currentURL.substring(firstSlash + 1, currentURL.length());
        System.out.println("targetUrl:" + targetURL + ", ref:" + hreq.getHeader("referer"));

        boolean signedOn = false;
        if (hreq.getSession().getAttribute("LoginRtnParamsHashMap") != null) {
            signedOn = true;
        } 

        logger.info("signedOn:" + signedOn);
        if (signedOn) {
             chain.doFilter(request,response);
             return;
        }else if (signOnPage.equals(targetURL)) {           
            chain.doFilter(request,response);
            return;
        }else if (checkNoSignOnPages(targetURL)){
        	chain.doFilter(request,response);
            return;
		}else if(targetURL.equals( loginWan) ){//10000号用户登陆
			chain.doFilter(request,response);
            return;
		}else if(validateKey(request)){
			chain.doFilter(request,response);
            return;
		}else{
        	HttpServletResponse hrep = (HttpServletResponse) response;        	
        	hrep.sendRedirect(hreq.getContextPath()+"/" + signOnErrorPage);
            return;
        }
		
	}
	
	/**
	 * 根据url somePage.jsp?staff=staffCode&key=xxxx 
	 * staff和key验证是否能够通过
	 * key = md5(工号 + 年月日(yymmdd)+小时(HH24)+A)
	 * append为可配置
	 * 江西crm集成VSOP界面用
	 * @param request
	 * @return
	 */
    private boolean validateKey(ServletRequest request) {
		String staff = request.getParameter("staff");
		String key = request.getParameter("key");
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(null != staff && !"".equals(staff) && null != key && !"".equals(key)){
			//江西本地化修改
			if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
				return true;
			}
			
			SimpleDateFormat sd = new SimpleDateFormat("yyMMddHH");
			String now = sd.format(Calendar.getInstance().getTime());
			try {
				String append = DcSystemParamManager.getParameter("SIGN_ON_APPEND");
				if(null == append || "".equals(append)) append = "A";
				String tmpKey = MD5Util.MD5(staff + now + append);
				System.out.println("key:" + key + ",scret:" + tmpKey);
				if(key.equalsIgnoreCase(tmpKey)) return true;
			} catch (NoSuchAlgorithmException e) {
			}
		}
		
		return false;
	}

	public  void validateSignOn(ServletRequest request, ServletResponse  response, FilterChain chain)
    throws IOException, ServletException {
	        
    }
    
    /**
     * 不需要登陆的页面简单检查方法。
     * @param targetURL
     * @return
     */
    private boolean checkNoSignOnPages(String targetURL){
    	
    	if(noSignOnPages.length==0)
    	   noSignOnPages=CrmConstants.getNotFilterPages();
    	
    	boolean needed = false;
    	for (int i = 0; i < noSignOnPages.length; i++) {
			if(noSignOnPages[i].equals(targetURL)){
				needed = true;
				break;
			}
		}    	
    	return needed;
    }

	public void destroy() {
	}

}
