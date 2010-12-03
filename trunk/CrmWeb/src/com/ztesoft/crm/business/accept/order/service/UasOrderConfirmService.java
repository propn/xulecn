package com.ztesoft.crm.business.accept.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;


import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

import com.ztesoft.crm.business.common.consts.OrderConfirmActions;

import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;


import fortest.nxa.service.NXAService;
/**
 * 完成订单确认  等功能
 * */
public class UasOrderConfirmService
{

	
	
    public PageModel getServOrder(String custId,int pageIndex,int pageSize) throws Exception{   

    	LoginRespond respond = (LoginRespond)RequestContext.getContext()
    		.getHttpSession().getAttribute("LoginRespond");
    	PageModel resList= new PageModel();
    	//获取菜单
        RoleState[] arrRoleState = respond.getRoleState();
        Map param = new HashMap() ;
		param.put("custId", custId) ;
		param.put("pageIndex", Integer.toString(pageIndex)) ;
		param.put("pageSize", Integer.toString(pageSize)) ;
        if(custId!=null&&!"".equals(custId))
        {
        	Object result = ServiceManager.callJavaBeanService(OrderConfirmActions.ORDER_CONFIRM_ACTION,OrderConfirmActions.EXECUTEQUERY ,param) ;
    		resList = result == null ? null : (PageModel) result ;
    	}
        return resList;
    }
    
   
    // 使session失效
    public void invalidSession() throws Exception {
//        HttpSession session = getSession();

    	HttpSession session = RequestContext.getContext()
    		.getHttpSession() ;
        // 使session失效
        if (session != null) {
            session.invalidate();
        }
    }
}
