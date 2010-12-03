package com.ztesoft.vsop.simulate.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.OrderConstant;
import com.ztesoft.vsop.simulate.dao.SiAccpetDAO ;
import com.ztesoft.vsop.simulate.dao.SiCapabilityDAO;
import com.ztesoft.vsop.simulate.dao.SiVproductDAO;
import com.ztesoft.vsop.webservice.client.SoapClient;

public class SiAccpetBO extends DictAction  {
	private static Logger logger = Logger.getLogger(SiAccpetBO.class);
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchSiAccpetData 改方法的参数名称
 		3. findSiAccpetByCond(String app_id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiAccpet(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiAccpet = (Map) param.get("SiAccpet") ;
		
		SiAccpetDAO dao = new SiAccpetDAO();
		boolean result = dao.insert(SiAccpet) ;
		return result ;
	}

	
	public boolean updateSiAccpet(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiAccpet = (Map) param.get("SiAccpet") ;
		String keyStr = "app_id";
		Map keyCondMap  = Const.getMapForTargetStr(SiAccpet,  keyStr) ;
		SiAccpetDAO dao = new SiAccpetDAO();
		boolean result = dao.updateByKey( SiAccpet, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSiAccpetData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "product_no")){
			whereCond.append(" and product_no = ? ");
			para.add(Const.getStrValue(param , "product_no")) ;
		}
		if(Const.containStrValue(param , "app_type")){
			whereCond.append(" and app_type = ? ");
			para.add(Const.getStrValue(param , "app_type")) ;
		}
		if(Const.containStrValue(param , "auth_state")){
			whereCond.append(" and auth_state = ? ");
			para.add(Const.getStrValue(param , "auth_state")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SiAccpetDAO dao = new SiAccpetDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSiAccpetById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SiAccpetDAO dao = new SiAccpetDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSiAccpetByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SiAccpetDAO dao = new SiAccpetDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSiAccpetById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SiAccpetDAO dao = new SiAccpetDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	public String authSiAccpet(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiAccpet = (Map) param.get("SiAccpet") ;
		
		String app_id = (String)SiAccpet.get("app_id");
		
		//调用DAO代码
		String whereCond1=" and app_id=? ";
		List para1=new ArrayList();
		para1.add( app_id);
		SiCapabilityDAO dao1 = new SiCapabilityDAO();
		List result1 = dao1.findByCond( whereCond1, para1) ;
		
		//调用DAO代码
		String whereCond2=" and app_id=? ";
		List para2=new ArrayList();
		para2.add( app_id);
		SiVproductDAO dao2 = new SiVproductDAO();
		List result2 = dao2.findByCond( whereCond2, para2) ;
		
		//调用后台接口
		String ret=SoapClient.getInstance().subscribeAuth(this.createAuthXml(result1, result2,SiAccpet));
		logger.debug("subscribeAuth response:" + ret);
		String resultDesc = StringUtil.getInstance().getTagValue("ResultDesc", ret);
		ret=StringUtil.getInstance().getTagValue("ResultCode", ret);
		logger.debug("resultCode:" + ret);
		String resultVal = ret;
		if("0".equals(ret)){
			ret="1";
		}else{
			ret="2";
		}
		SiAccpet.put("auth_state", ret);
		
		String keyStr = "app_id";
		Map keyCondMap  = Const.getMapForTargetStr(SiAccpet,  keyStr) ;
		SiAccpetDAO dao = new SiAccpetDAO();
		boolean result = dao.updateByKey( SiAccpet, keyCondMap );
		if(resultVal.equals("0"))return "0";
		else {
			return resultDesc ;
		}
	}
	
	public String workListFKToVSOP(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiAccpet = (Map) param.get("SiAccpet") ;
		
		String app_id = (String)SiAccpet.get("app_id");
		
		//调用DAO代码
		String whereCond1=" and app_id=? ";
		List para1=new ArrayList();
		para1.add( app_id);
		SiCapabilityDAO dao1 = new SiCapabilityDAO();
		List result1 = dao1.findByCond( whereCond1, para1) ;
		
		//调用DAO代码
		String whereCond2=" and app_id=? ";
		List para2=new ArrayList();
		para2.add( app_id);
		SiVproductDAO dao2 = new SiVproductDAO();
		List result2 = dao2.findByCond( whereCond2, para2) ;
		
		//调用后台接口
		String ret=SoapClient.getInstance().workListFKToVSOP(this.createWorkListToVSOPXml(result1, result2,SiAccpet));
		ret=StringUtil.getInstance().getTagValue("ResultCode", ret);
		return ret ;
	}
	
	public String subscribeToVSOP(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiAccpet = (Map) param.get("SiAccpet") ;
		
		String app_id = (String)SiAccpet.get("app_id");
		
		//调用DAO代码
		String whereCond1=" and app_id=? ";
		List para1=new ArrayList();
		para1.add( app_id);
		SiCapabilityDAO dao1 = new SiCapabilityDAO();
		List result1 = dao1.findByCond( whereCond1, para1) ;
		
		//调用DAO代码
		String whereCond2=" and app_id=? ";
		List para2=new ArrayList();
		para2.add( app_id);
		SiVproductDAO dao2 = new SiVproductDAO();
		List result2 = dao2.findByCond( whereCond2, para2) ;
		
		//调用后台接口
		String ret=SoapClient.getInstance().subscribeToVSOP(this.createsubscribeToVSOPXml(result1, result2,SiAccpet));
		String res = "";
		res=StringUtil.getInstance().getTagValue("ResultCode", ret);
		res+=","+StringUtil.getInstance().getTagValue("ResultDesc", ret);
		return res ;
	}
	
	private String createsubscribeToVSOPXml(List cas,List vs,Map SiAccpet){
		String SystemId="1";
		String OrderId =(String)SiAccpet.get("app_id");
		String ProdSpecCode = (String)SiAccpet.get("prod_type");//其实应该是SiAccpet.get("prod_spec_code")
		String ProductNo = (String)SiAccpet.get("product_no");
		String OldProductNo = (String)SiAccpet.get("old_no");
		//捆绑套餐与纯增值套餐只能有一种，复用同个字段
		String ProductOfferId =(String)SiAccpet.get("product_offer_id");
		String PackageId =(String)SiAccpet.get("product_offer_id");
		
		String ActionType = OrderConstant.orderTypeOfAdd;//(String)SiAccpet.get("app_type");
		String UserState = (String)SiAccpet.get("user_state");
		StringBuffer bf = new StringBuffer("");
		bf.append("<SubscribeToVSOPReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20100412235959").append("</TimeStamp>")
			.append("<OrderId>").append(OrderId).append("</OrderId>")
			.append("<SystemId>").append(SystemId).append("</SystemId>")
			
			.append("<ActionType>").append(ActionType).append("</ActionType>")
			.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
			.append("<ProductNo>").append(ProductNo).append("</ProductNo>");
			//.append("<OldProductNo>").append(OldProductNo).append("</OldProductNo>")
			//.append("<ProductOfferId>").append(ProductOfferId).append("</ProductOfferId>")
			//.append("<PackageId>").append(PackageId).append("</PackageId>");

			for(int i=0;i<vs.size();i++){
				Map mp=(Map)vs.get(i);
				String ActionType2 = (String)mp.get("act_type");
				String VProductID = (String)mp.get("vproduct_id");
				String eff_time = (String)mp.get("eff_time");
				String exp_time = (String)mp.get("exp_time");
				bf.append("<ProductOfferInfo>")
				.append("<ProductOfferType>").append("0").append("</ProductOfferType>")
				.append("<ProductOfferID>").append(ProductOfferId).append("</ProductOfferID>")
				.append("<VSubProdInfo>")//.append(ProductOfferId)
				.append("<VProductID>").append(VProductID).append("</VProductID>")
				.append("<EffDate>").append(eff_time).append("</EffDate>")
				.append("<ExpDate>").append(exp_time).append("</ExpDate>")
				.append("</VSubProdInfo>")
				.append("<EffDate>").append(eff_time).append("</EffDate>")
				.append("<ExpDate>").append(exp_time).append("</ExpDate>")
				 
				.append("</ProductOfferInfo>")	;
			}
			
			/*for(int i=0;i<cas.size();i++){
				Map mp=(Map)cas.get(i);
				String ActionType2 = (String)mp.get("act_type");
				String AProductID = (String)mp.get("product_id");
			bf.append("<AProductInfo>")
			.append("<ActionType>").append(ActionType2).append("</ActionType>")
			.append("<AProductID>").append(AProductID).append("</AProductID>")
			.append("</AProductInfo>");
			}
			bf.append("<UserState>").append(UserState).append("</UserState>");*/
			
			//bf.append("</ProductInfo>");
			
		bf.append("</SubscribeToVSOPReq>");
		String xml=StringUtil.getInstance().getVsopRequest("123456","20100412235959", bf.toString());
		
		System.out.println("-------subscribeToVSOPXml------------="+xml);
		return xml;
	}
	
	private String createWorkListToVSOPXml(List cas,List vs,Map SiAccpet){
		String SystemId="0";
		String OrderId =(String)SiAccpet.get("app_id");
		String ProdSpecCode = (String)SiAccpet.get("prod_type");
		String ProductNo = (String)SiAccpet.get("product_no");
		String OldProductNo = (String)SiAccpet.get("old_no");
		//捆绑套餐与纯增值套餐只能有一种，复用同个字段
		String ProductOfferId =(String)SiAccpet.get("product_offer_id");
		String PackageId =(String)SiAccpet.get("product_offer_id");
		
		String ActionType = (String)SiAccpet.get("app_type");
		String UserState = (String)SiAccpet.get("user_state");
		StringBuffer bf = new StringBuffer("");
		String prod_inst_id = "";
		bf.append("<WorkListFKToVSOPReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20100412235959").append("</TimeStamp>")
			.append("<OrderId>").append(OrderId).append("</OrderId>")
			.append("<SystemId>").append(SystemId).append("</SystemId>")
			.append("<PorductInstID>").append(prod_inst_id).append("</PorductInstID>")
			.append("<ActionType>").append(ActionType).append("</ActionType>")
			.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
			.append("<ProductNo>").append(ProductNo).append("</ProductNo>")
			.append("<OldProductNo>").append(OldProductNo).append("</OldProductNo>")
			.append("<ProductOfferId>").append(ProductOfferId).append("</ProductOfferId>")
			.append("<PackageId>").append(PackageId).append("</PackageId>")
			.append("<ReginID>").append("").append("</ReginID>")
			.append("<UserPayType>").append("0").append("</UserPayType>")
			.append("<UserState>").append(UserState).append("</UserState>");
		
			for(int i=0;i<vs.size();i++){
				Map mp=(Map)vs.get(i);
				String ActionType2 = (String)mp.get("act_type");
				String VProductID = (String)mp.get("vproduct_id");
				String eff_time = (String)mp.get("eff_time");
				String exp_time = (String)mp.get("exp_time");
			bf.append("<VProductInfo>")
			.append("<ActionType>").append(ActionType2).append("</ActionType>")
			.append("<VProductID>").append(VProductID).append("</VProductID>")
			.append("<VProductInstID>").append(prod_inst_id).append("</VProductInstID>")
			.append("<EffDate>").append(eff_time).append("</EffDate>")
			.append("<ExpDate>").append(exp_time).append("</ExpDate>")
			 
			.append("</VProductInfo>")	;
			}
			
			for(int i=0;i<cas.size();i++){
				Map mp=(Map)cas.get(i);
				String ActionType2 = (String)mp.get("act_type");
				String AProductID = (String)mp.get("product_id");
			bf.append("<AProductInfo>")
			.append("<ActionType>").append(ActionType2).append("</ActionType>")
			.append("<AProductID>").append(AProductID).append("</AProductID>")
			.append("<AProductInstID>").append(prod_inst_id).append("</AProductInstID>")
			.append("</AProductInfo>");
			}
			
			//bf.append("</ProductInfo>");
			
		bf.append("</WorkListFKToVSOPReq>");
		String xml=StringUtil.getInstance().getVsopRequest("123456","20100412235959", bf.toString());
		System.out.println("-------WorkListToVSOPXml------------="+xml);
		return xml;
	}
	private String createAuthXml(List cas,List vs,Map SiAccpet){
		String ProductOfferId =(String)SiAccpet.get("product_offer_id");
		String ProdSpecCode = (String)SiAccpet.get("prod_type");
		String ProductNo = (String)SiAccpet.get("product_no");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<SubscribeAuthReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20100412").append("</TimeStamp>")
			.append("<SystemId>").append("201").append("</SystemId>")
			.append("<ProductOfferId>").append(ProductOfferId).append("</ProductOfferId>")
			
			.append("<ProductInfo>")
			.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
			.append("<ProductNo>").append(ProductNo).append("</ProductNo>");
			
			for(int i=0;i<vs.size();i++){
				Map mp=(Map)vs.get(i);
				String ActionType = (String)mp.get("act_type");
				String VProductID = (String)mp.get("vproduct_id");
			bf.append("<VProductInfo>")
			.append("<ActionType>").append(ActionType).append("</ActionType>")
			.append("<VProductID>").append(VProductID).append("</VProductID>")
			.append("</VProductInfo>")	;
			}
			
			for(int i=0;i<cas.size();i++){
				Map mp=(Map)cas.get(i);
				String ActionType = (String)mp.get("act_type");
				String AProductID = (String)mp.get("product_id");
			bf.append("<AProductInfo>")
			.append("<ActionType>").append(ActionType).append("</ActionType>")
			.append("<AProductID>").append(AProductID).append("</AProductID>")
			.append("</AProductInfo>");
			}
			
			bf.append("</ProductInfo>");
			
		bf.append("</SubscribeAuthReq>");
		
		System.out.println("-------SubscribeAuthReq------------="+bf.toString());
		return bf.toString();
	}
}
