package com.ztesoft.component.common.signon;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.oaas.struct.ChangePasswordRequest;
import com.ztesoft.oaas.struct.ChangePasswordRespond;
import com.ztesoft.oaas.struct.LoginRequest;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;

public class SignOnService extends DictService { 

	private static Logger logger = Logger.getLogger(SignOnService.class);
	
	//modify by easonwu 2009-12-09 buffalo2.0
	public String getPasswordRemindInfo() throws Exception {
		
//		LoginRespond respond = (LoginRespond) getRequest().getSession()
//				.getAttribute("LoginRespond");
		LoginRespond respond = (LoginRespond) RequestContext.getContext().
			getHttpSession().getAttribute("LoginRespond");
		return respond.getReason();
	}

	/**
	 * ��¼����¼�ɹ���ͬʱ����ȫ�ֱ�������ȡ�˵�
	 * 
	 * @param request
	 *            ��¼��������
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪLoginRespond����
	 */
	public LoginRespond login(LoginRequest request) throws Exception {
		LoginRespond respond = new LoginRespond();
		System.out.println("enter login method....");
		try {
			DynamicDict dto = getServiceDTO("LoginBO", "simulateLoginNotNeedProxy");
			dto.setValueByName("parameter", request) ;
			dto = ActionDispatch.dispatch(dto);
			respond =  ((LoginRespond)dto.getValueByName("result")) ;
			
//			LoginBO loginUtil = new LoginBO();
//			respond = loginUtil.simulateLoginNotNeedProxy(request);
		} catch (Exception e) {
			e.printStackTrace();
			// �����������Ķ��󵽿ͻ���
			LoginRespond returnRespond = new LoginRespond();
			returnRespond.setSuccess(respond.getSuccess());
			returnRespond.setReason(respond.getReason());
			return returnRespond;
		}

//		getRequest().getSession().setMaxInactiveInterval(5000) ;
		
//		modify by easonwu 2009-12-09 buffalo2.0
		//getRequest().getSession().setAttribute("LoginRespond", respond);
		HttpSession session = RequestContext.getContext().getHttpRequest().getSession();
		session.setAttribute("LoginRespond", respond);
//		logger.info("login session:" + RequestContext.getContext().getHttpSession().hashCode());
//		System.out.println("isOK? "+ "0".equals(respond.getSuccess())) ;
		if ("0".equals(respond.getSuccess())) {
			respond.setSuccess(respond.getSuccess().trim());
			respond.setReason(respond.getReason().trim());
			return respond;
		}

		// ����ȫ�ֱ���
		HashMap rtnHashMap = new HashMap();

		rtnHashMap.put("vg_oper_name", respond.getPartyName());
		rtnHashMap.put("vg_oper_code", respond.getStaffCode());
		rtnHashMap.put("vg_oper_id", respond.getPartyRoleId());
//System.out.println("Tddddd............<"+ respond.getPartyRoleId()) ;
//this.getRequest().getSession().setAttribute("STAFF_CODE", respond.getPartyRoleId());
		rtnHashMap.put("vg_terminal_ip", request.getIpAddress());
		rtnHashMap.put("vg_accept_source", "0");
		rtnHashMap.put("vg_staff_code", request.getStaffCode());
		rtnHashMap.put("vg_ip_address", request.getIpAddress());
		//TODO ADD BY NIK ΪӪ����Դ����ʡ�ݣ����������Ƿ�����������
		rtnHashMap.put("prov_id", "20");//����
		rtnHashMap.put("vg_lan_id", respond.getLanId());
		rtnHashMap.put("vg_oper_lan", "1");//��������
		
		
//		modify by easonwu 2009-12-09 buffalo2.0
//		getRequest().getSession().setAttribute("LoginRtnParamsHashMap",
//				rtnHashMap);

		session.setAttribute("LoginRtnParamsHashMap",rtnHashMap);
		// �����������Ķ���
		LoginRespond returnRespond = new LoginRespond();
		returnRespond.setSuccess(respond.getSuccess().trim());
		returnRespond.setReason(respond.getReason().trim());
		returnRespond.setSuperStaffFlag(respond.isSuperStaff());
//		HashMap rtnHashMap2 =(HashMap) RequestContext.getContext().
//		getHttpSession().getAttribute("LoginRtnParamsHashMap") ;
		System.out.println("donelogin.");
		return returnRespond;
	}

	// ʹsessionʧЧ
	public void invalidSession() throws Exception {
//		modify by easonwu 2009-12-09 buffalo2.0
		HttpServletRequest req = RequestContext.getContext().getHttpRequest() ;
		HttpServletResponse resp = RequestContext.getContext().getHttpResponse();
		Cookie[] ck = req.getCookies();
		for (int i = 0; i < ck.length; i++) {
			Cookie c = ck[i];
//			Cookie bd = new Cookie("","");
			logger.info("invalidSession Cookie domain:"+c.getDomain()+" ,path:"+c.getPath()+", name:" + c.getName() + ",value:" + c.getValue());
			c.setMaxAge(0);
			resp.addCookie(c);
		}
		HttpSession session = req.getSession();
		// ʹsessionʧЧ
		if (session != null) {
			session.removeAttribute("LoginRtnParamsHashMap");
			session.invalidate();
			logger.info("invalidSession:" + session.hashCode());
		}else{
			logger.info("invalidSession: null" );
		}
	}
	/**
	 * ˢ�¸�����������������
	 * @return
	 * @throws Exception
	 */
	public String refreshAllServerCaches()throws Exception {
		RefreshCacheHttpClient.getInstance().refreshAllServerCaches("", "");
		return "0";
	}
	

	/**
	 * �޸�����
	 * 
	 * @param request
	 *            �޸�������������
	 * @return �������, �ɹ�ʱServiceResult.resultObjectΪChangePasswordRespond����
	 */
	public ChangePasswordRespond changePassword(String staffCode,
												String oldPassword,
												String newPassword,
												String preemption) throws Exception {
		//�����޸Ĺ������  liuyuming2010-09-21
		ChangePasswordRespond respond = new ChangePasswordRespond();
		DynamicDict dto = getServiceDTO("LoginBO", "updatePassword");
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setStaffCode(staffCode);
		request.setOldPassword(oldPassword);
		request.setNewPassword(newPassword);
		request.setPreemption(preemption);
		dto.setValueByName("parameter", request) ;
		dto = ActionDispatch.dispatch(dto);
		respond =  ((ChangePasswordRespond)dto.getValueByName("result")) ;
		return respond;
	}
}