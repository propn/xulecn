package com.ztesoft.component.ui.taglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;

public class PrivilegeService {
//	public PrivilegeService(){
//		
//	}
	public static void main() {
		String PrivilegeServiceName = "com.ztesoft.component.ui.taglib.PrivilegeService" ;//CrmParamsConfig.getInstance().getParamValue("PrivilegeService") ;//ÏµÍ³Ãû³Æ
		Class c;
		try {
			c = Class.forName(PrivilegeServiceName);
			Class[] params = new Class[1] ;
			params[0] = String[].class;
//			params[1] = HttpServletRequest.class ;
			Method m = c.getMethod("privilegesCheckWithSession", params) ;
			Object[] objs = new Object[1] ;
			String[] xyz = new String[2] ;
			xyz[0] = "a" ; 
			xyz[0] = "b" ;
			objs[0] = xyz;
//			objs[1] = null ;
			ArrayList targetCodes =(ArrayList)m.invoke(c.newInstance(), objs ) ;
			int i = 0 ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}
	
	public ArrayList privilegesCheckWithSession(String[] privilegesToCheck) {
//		ArrayList list = new ArrayList();
//		Object obj = session.getAttribute("LoginRespond");
//		LoginRespond loginRespond = (LoginRespond) obj;
//
//		RoleState[] roleStates = loginRespond.getRoleState();
//
//		for (int j = 0; j < privilegesToCheck.length; j++) {
//			boolean found = false;
//			for (int i = 0; i < roleStates.length; i++) {
//				RoleState roleState = roleStates[i];
//				if (privilegesToCheck[j].equals(roleState.getPrivilegeCode())) {
//					found = true;
//					break;
//				}
//			}
//			if (!found) {
//				list.add(privilegesToCheck[j]);
//				found = false;
//			}
//		}
		String[] bb = privilegesToCheck ;
		ArrayList x = new ArrayList() ;
		x.add("1") ;
		x.add("abc") ;
		return x;
	}
}
