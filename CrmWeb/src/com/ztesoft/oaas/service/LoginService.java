package com.ztesoft.oaas.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;
import com.ztesoft.oaas.struct.EncryptRequest;
import com.ztesoft.oaas.struct.EncryptRespond;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.utils.OAASProxy;
import com.ztesoft.oaas.vo.LoginInfoVO;

public class LoginService extends DictService {

	private static Logger logger = Logger.getLogger(LoginService.class);

	public ArrayList getLogInRolesInfo() throws Exception {
		LoginRespond respond = (LoginRespond) RequestContext.getContext()
				.getHttpSession().getAttribute("LoginRespond");
		RoleState[] roleStates = respond.getRoleState();
		ArrayList returnList = new ArrayList();

		for (int i = 0; i < roleStates.length; i++) {
			RoleStateBsnStrucVO roleStateBsnStrucVO = new RoleStateBsnStrucVO();
			RoleState roleState = roleStates[i];
			if (!"0".equals(roleState.getPermitionFlag())) {
				continue;
			}

			roleStateBsnStrucVO.setPrivilegeCode(roleState.getPrivilegeCode());
			roleStateBsnStrucVO.setPrivilegeID(roleState.getPrivilegeId());
			roleStateBsnStrucVO.setRegionID(roleState.getRegionId());

			roleStateBsnStrucVO.setPartyRoleSchema("98A");
			roleStateBsnStrucVO.setPermitionFlag("T");
			roleStateBsnStrucVO.setValidTime("10080");

			returnList.add(roleStateBsnStrucVO);
		}

		return returnList;
	}

	public LoginInfoVO getLoginInfo() throws Exception {
		LoginInfoVO loginInfoVO = new LoginInfoVO();

		// HttpServletRequest req = getRequest();

		HttpServletRequest req = RequestContext.getContext().getHttpRequest();
		Cookie[] ck = req.getCookies();

		for (int i = 0; i < ck.length; i++) {
			Cookie c = ck[i];
			logger.info("invalidSession Cookie domain:" + c.getDomain()
					+ " ,path:" + c.getPath() + ", name:" + c.getName()
					+ ",value:" + c.getValue());
		}
		if (req.getSession().getAttribute("LoginRtnParamsHashMap") == null) {
			logger.info("loginService#getLoginInfo null");
		} else {
			logger.info("loginService#getLoginInfo not null");
		}
		GlobalVariableHelper helper = new GlobalVariableHelper(req);

		loginInfoVO.setBusinessId(helper
				.getVariable(GlobalVariableHelper.BUSINESS_ID));
		loginInfoVO.setBusinessName(helper
				.getVariable(GlobalVariableHelper.BUSINESS_NAME));
		loginInfoVO
				.setPartyId(helper.getVariable(GlobalVariableHelper.OPER_ID));

		loginInfoVO.setPartyName(helper
				.getVariable(GlobalVariableHelper.OPER_NAME));
		loginInfoVO.setStaffCode(helper
				.getVariable(GlobalVariableHelper.OPER_CODE));
		loginInfoVO.setLanId(helper.getVariable(GlobalVariableHelper.LAN_ID));
		loginInfoVO.setLanName(helper
				.getVariable(GlobalVariableHelper.LAN_NAME));
		loginInfoVO.setIp(helper.getVariable(GlobalVariableHelper.TERMINAL_IP));

		loginInfoVO.setOperOrgId(helper
				.getVariable(GlobalVariableHelper.OPER_ORG_ID));
		loginInfoVO.setOperOrgName(helper
				.getVariable(GlobalVariableHelper.OPER_ORG_NAME));
		loginInfoVO.setOperOrgLevel(helper
				.getVariable(GlobalVariableHelper.OPER_ORG_LEVEL));
		loginInfoVO.setChannelSegmentId(helper
				.getVariable(GlobalVariableHelper.CHANNEL_SEGMENT_ID));

		return loginInfoVO;
	}

	/**
	 * 加密
	 * 
	 * @param request
	 *            加密解密请求数据
	 * @return 操作结果, EncryptRespond对象
	 */
	public EncryptRespond encrypt(EncryptRequest request) throws Exception {
		return OAASProxy.encrypt(request);
	}

	public String getStaffPassword(String staffCode) throws Exception {
		DynamicDict dto = getServiceDTO("LoginBO", "getStaffPassword");
		dto.setValueByName("parameter", staffCode);
		dto = ActionDispatch.dispatch(dto);
		return ((String) dto.getValueByName("result"));
	}

	private String getMenus(String parentMenuId) throws Exception {
		LoginRespond respond = (LoginRespond) RequestContext.getContext()
				.getHttpSession().getAttribute("LoginRespond");
		// 获取菜单
		RoleState[] arrRoleState = respond.getRoleState();

		if (arrRoleState != null && arrRoleState.length > 0) {
			HashSet privilegeHashSet = new HashSet();
			for (int i = 0; i < arrRoleState.length; i++) {
				if ("99B".equals(arrRoleState[i].getPartyRoleSchema())) {
					privilegeHashSet.add(arrRoleState[i].getPrivilegeId());
				}
			}

			if (privilegeHashSet.size() == 0 && !respond.isSuperStaff()) {
				return "<items/>";
			}
			StringBuffer str = new StringBuffer();
			Iterator it = privilegeHashSet.iterator();
			while (it.hasNext()) {
				str.append(",");
				str.append(it.next());
			}
			if (str.length() > 0)
				str.deleteCharAt(0);

			DynamicDict dto = getServiceDTO("LoginBO", "getMenus");
			Map m = new HashMap();
			m.put("privset", str.toString());
			m.put("parentMenuId", parentMenuId);
			m.put("staffCode", respond.getStaffCode());

			dto.setValueByName("parameter", m);
			dto = ActionDispatch.dispatch(dto);
			return ((String) dto.getValueByName("result"));

		} else {
			return "<items/>";
		}
	}

	public String getRootMenuTree() throws Exception {
		return getMenus("-1");
	}

	public String getSubMenus(String menuId) throws Exception {
		return getMenus(menuId);
	}

	// 使session失效
	public void invalidSession() throws Exception {
		// HttpSession session = getSession();
		HttpServletRequest req = RequestContext.getContext().getHttpRequest();
		Cookie[] ck = req.getCookies();
		for (int i = 0; i < ck.length; i++) {
			Cookie c = ck[i];
			logger.info("invalidSession Cookie path:" + c.getPath() + ", name:"
					+ c.getName() + ",value:" + c.getValue());
		}
		HttpSession session = req.getSession();
		// 使session失效
		if (session != null) {
			session.invalidate();
		}

	}
}
