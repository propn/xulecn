package com.ztesoft.crm.business.accept.offer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.crm.business.common.cache.PropCache;
import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.consts.OfferActions;
import com.ztesoft.crm.business.common.consts.Services;
import com.ztesoft.crm.business.common.specs.vo.DcProdPropVO;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;
import com.ztesoft.oaas.struct.EncryptRequest;
import com.ztesoft.oaas.struct.EncryptRespond;
import com.ztesoft.oaas.struct.LoginRespond;
import com.ztesoft.oaas.struct.RoleState;
import com.ztesoft.oaas.utils.OAASProxy;
import com.ztesoft.oaas.vo.LoginInfoVO;

/**
 * 完成选购台 销售品信息页面 的 基础销售品、组合销售品订购、退订、变更等业务的查询 校验等功能
 * */
import fortest.nxa.service.NXAService;
public class UasSellService
{

	
	//获取销售品目录树
    public ArrayList getSeriral(String params) throws Exception{   
    	String parentMenuId = params;
    	LoginRespond respond = (LoginRespond)RequestContext.getContext()
    		.getHttpSession().getAttribute("LoginRespond");
    	String seriralXml = "<items/>";
    	//获取菜单
        RoleState[] arrRoleState = respond.getRoleState();
        Map param = new HashMap() ;
		param.put("parentMenuId", parentMenuId) ;
        if(parentMenuId!=null&&!"".equals(parentMenuId))
        {
        	Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY ,param) ;
    		return result == null ? null : (ArrayList) result ;
    	}else{
        	return new ArrayList();
        }
    }
    
    public ArrayList fetchAllSeriral() throws Exception {
    	return getSeriral("-1");
    }
    //查询基础包 可选包
    public List queryPackageList(String offerId) throws Exception {
    	List retList = new ArrayList();
    	Date s = new Date() ;
    	Map param = new HashMap() ;
 		param.put("offerId", offerId) ;
    	if(offerId!=null&&!"".equals(offerId))
        {
        	Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY2 ,param) ;
        	return retList == null ? null : (List) result ;
    	}else{
    		return retList;
        }
    }
    
    //查询包外优惠
    public List queryOuterPackageList(String product_id,String rela_offer_instance_id) throws Exception {
    	List retList = new ArrayList();
    	Date s = new Date() ;
    	Map param = new HashMap() ;
 		param.put("product_id", product_id) ;
 		param.put("rela_offer_instance_id", rela_offer_instance_id) ;
    	if(product_id!=null&&!"".equals(product_id))
        {
        	Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY8 ,param) ;
        	return retList == null ? null : (List) result ;
    	}else{
    		return retList;
        }
    }
    
    
    
    //模糊查询受理销售品
    public PageModel fetchOfferListByNameAll(String offerName,int pageIndex, int pageSize) throws Exception 
    {
    	List retList = new ArrayList();
    	Date s = new Date() ;
    	Map param = new HashMap() ;
 		param.put("offerName", offerName) ;
    	if(offerName!=null&&!"".equals(offerName))
        {
	        Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY4 ,param) ;
	    	
	    	retList = retList == null ? null : (List) result ;
    	}
    	return PageHelper.popupPageModel(retList, pageIndex, pageSize);
    }
    
    //加载热卖数据
    public PageModel fetchHotSaleOffer(int pageIndex, int pageSize) throws Exception 
    {
    	List retList = new ArrayList();
    	Date s = new Date() ;
    	LoginRespond respond = (LoginRespond)RequestContext.getContext()
		.getHttpSession().getAttribute("LoginRespond");
    	
    	String partyRoleId = respond.getPartyRoleId();//获取登录的操作员工号
    	
    	Map param = new HashMap() ;
 		param.put("partyRoleId", partyRoleId) ;
    	if(partyRoleId!=null&&!"".equals(partyRoleId))
        {
	        Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY5 ,param) ;
	    
	    	retList = retList == null ? null : (List) result ;
    	}
    	return PageHelper.popupPageModel(retList, pageIndex, pageSize);
    }
    
	
	//增加热卖销售品
	public String addPopDef(String offerId) throws Exception {
		String retMsg = "处理成功";
		
		LoginRespond respond = (LoginRespond)RequestContext.getContext()
		.getHttpSession().getAttribute("LoginRespond");
    	
    	String partyRoleId = respond.getPartyRoleId();//获取登录的操作员工号
    	
    	
		HashMap checkMap = new HashMap();
		Map param = new HashMap() ;
 		param.put("offerId", offerId) ;
 		param.put("partyRoleId", partyRoleId) ;
 		
 		String checkPass = "false";
 		//调用方法判断是否热卖套餐已经存在
		Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEVALID ,param) ;
		checkMap = (HashMap)result;
		checkPass = (String)checkMap.get("checkPass");
		//判斷是否已經存在
		if("false".equals(checkPass)){
			retMsg = "热卖套餐已经存在";
		}else{		
			//调用增加方法
			Object resultAdd = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.PERFORMADD ,param) ;

		}
		return retMsg;
	}
	
	//删除热卖销售品
	public String delPopDef(String offerId) throws Exception {
		String retMsg = "处理成功";
		
		LoginRespond respond = (LoginRespond)RequestContext.getContext()
		.getHttpSession().getAttribute("LoginRespond");
		String partyRoleId = respond.getPartyRoleId();//获取登录的操作员工号
		HashMap checkMap = new HashMap();
		Map param = new HashMap() ;
 		param.put("offerId", offerId) ;
 		param.put("partyRoleId", partyRoleId) ;
 		
 		
 		String checkPass = "false";
		Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEVALID ,param) ;
		checkMap = (HashMap)result;
		checkPass = (String)checkMap.get("checkPass");
		
		
 		if("true".equals(checkPass)){
			retMsg = "热卖套餐已经删除";
		}else{		
			Object resultAdd = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.PERFORMDEL ,param) ;
			//refreshHotBagList(vo);
		}
		return retMsg;
	}
	
	//获取基础包
	public HashMap getBasePacket(String offerId) throws Exception {
		
		LoginRespond respond = (LoginRespond)RequestContext.getContext()
		.getHttpSession().getAttribute("LoginRespond");
		String partyRoleId = respond.getPartyRoleId();//获取登录的操作员工号
		HashMap retMap = new HashMap();
		Map param = new HashMap() ;
 		param.put("offerId", offerId) ;

		Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY6 ,param) ;
		retMap = (HashMap)result;
	
		return retMap;
	}
	
	//获取基础包
	//查询销售品属性信息,需要考虑最新表的属性信息（该处暂不处理）
	public List findOfferProp(String packageId,String product_offer_instance_id,String rela_offer_instance_id) throws Exception  {
		
		if(packageId==null||"".equals(packageId))
			return new ArrayList();
		
		Map param = new HashMap() ;
 		param.put("packageId", packageId) ;
 		param.put("product_offer_instance_id", product_offer_instance_id) ;
 		param.put("rela_offer_instance_id", rela_offer_instance_id) ;
		Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY3 ,param) ;
		List resultList = result==null?null:(List)result;
		
		if(resultList==null||resultList.size()==0)
			return new ArrayList();
		
		for(int i=0;i<resultList.size();i++){		
			
			HashMap vo =(HashMap)resultList.get(i);
			String inputMethod=(String)vo.get("input_method");
			inputMethod = inputMethod.trim();
			if(PropCache.dropDown.equals(inputMethod)){
				
				vo.put("attrValues", StaticAttrCache.getSplitString((String)vo.get("attr_code"), vo.get("attr_code")+PropCache.valueMapKey));
				vo.put("attrValuesDesc",StaticAttrCache.getSplitString((String)vo.get("attr_code"), vo.get("attr_code")+PropCache.descMapKey));
			}
			if("98H".equals(inputMethod)||"98K".equals(inputMethod)){
					vo.put("attr_value","后台自动生成");
				}
			
			String defualtValue = (String)vo.get("default_value");
			
			if(defualtValue!=null&&!"".equals(defualtValue)){
				vo.put("attr_value",defualtValue);
			}	
			
		}

		return resultList;	
	}
	//查询销售品成员构成
	public ArrayList getProductOfferDetail(String offer_id,String product_offer_instance_id,String cust_id) throws Exception 
	{
		HashMap respond = (HashMap)RequestContext.getContext()
		.getHttpSession().getAttribute("LoginRtnParamsHashMap");
	
		
		
		ArrayList aList = new ArrayList();
		Map param = new HashMap() ;
		param.put("offer_id", offer_id) ;
		param.put("product_offer_instance_id", product_offer_instance_id) ;
		param.put("cust_id", cust_id);
		param.put("lan_id", respond.get("vg_lan_id"));
		Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY7 ,param) ;
		return aList == null ? null : (ArrayList) result ;
	}
	
	//获取实例ID
	public String getInstSeq(String tableCode,String fieldCode) throws Exception 
	{
		String instSeq = "";
		Map param = new HashMap() ;
		param.put("tableCode", tableCode) ;
		param.put("fieldCode", fieldCode) ;
		
		Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.GETSEQ ,param) ;
		return instSeq == null ? null : (String) result ;
	}
	
	//查询销售品成员构成
	public PageModel getUseOfferList(String lanId,String prodNo,String custId,String prodId,int pageIndex,int pageSize ) throws Exception 
	{
		ArrayList aList = new ArrayList();
		Map param = new HashMap() ;
		param.put("lanId", lanId) ;
		param.put("prodNo", prodNo) ;
		param.put("custId", custId) ;
		param.put("prodId", prodId) ;
		Object result = ServiceManager.callJavaBeanService(OfferActions.OFFER_SELL_ACTION,OfferActions.EXECUTEQUERY9 ,param) ;
		aList = (aList == null ? new ArrayList() : (ArrayList) result);
		return PageHelper.popupPageModel(aList, pageIndex, pageSize);
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
