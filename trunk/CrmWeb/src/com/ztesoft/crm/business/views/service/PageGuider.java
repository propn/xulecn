package com.ztesoft.crm.business.views.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.Const;
import com.ztesoft.crm.business.views.wizard.BusiWizard;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class PageGuider {

	public List getPages(String serviceOfferId) throws Exception {

		//"-3" serviceOfferId 取消受理
		//"-2" serviceOfferId 撤单受理
	/*	List pages=new ArrayList();
		
		if(serviceOfferId.equals("取消受理"))
			return null;
		if(serviceOfferId.equals("撤单"))
			return new ArrayList();*/
		
		Map arguments=new HashMap();
		//获取工号

		GlobalVariableHelper globalVariableHelper=new GlobalVariableHelper(RequestContext.getContext().getHttpRequest());
		
		arguments.put("service_offer_id", serviceOfferId);
		arguments.put("operId", globalVariableHelper.getVarialbe(GlobalVariableHelper.OPER_ID));
		
/*		pages= BusiWizard.getInst().generate(arguments);
		
		if(pages==null||pages.isEmpty())
			throw new BusiException("未查询到相应的业务流程[service_id:"+serviceOfferId+"]");*/
		
		return BusiWizard.getInst().generate(arguments);

	}
	
	public List getCodes(String serviceOfferId) throws Exception {

		List list = getPages(serviceOfferId);

		List codes = new ArrayList();

		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				codes.add(Const.getStrValue(map, "code"));
			}
		return codes;
	}
	
	public List getTagNames(String serviceOfferId) throws Exception {

		List list = getPages(serviceOfferId);

		List tagNames = new ArrayList();

		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				tagNames.add(Const.getStrValue(map, "comments"));
			}
		return tagNames;
	}

	public List getUrls(String serviceOfferId) throws Exception {

		List list = getPages(serviceOfferId);

		List urls = new ArrayList();

		if (list != null)
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				urls.add(Const.getStrValue(map, "url"));
			}
		return urls;
	}

}
