package com.ztesoft.vsop.simulate.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.simulate.dao.SiCapabilityDAO;
import com.ztesoft.vsop.simulate.dao.SiVproductDAO ;
import com.ztesoft.vsop.webservice.client.SoapClient;

public class SiVproductBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchSiVproductData 改方法的参数名称
 		3. findSiVproductByCond(String id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiVproduct(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiVproduct = (Map) param.get("SiVproduct") ;
		
		SiVproductDAO dao = new SiVproductDAO();
		boolean result = dao.insert(SiVproduct) ;
		return result ;
	}

	
	public boolean updateSiVproduct(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiVproduct = (Map) param.get("SiVproduct") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(SiVproduct,  keyStr) ;
		SiVproductDAO dao = new SiVproductDAO();
		boolean result = dao.updateByKey( SiVproduct, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSiVproductData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "app_id")){
			whereCond.append(" and app_id = ? ");
			para.add(Const.getStrValue(param , "app_id")) ;
		}
/*		if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}*/
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SiVproductDAO dao = new SiVproductDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSiVproductById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SiVproductDAO dao = new SiVproductDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSiVproductByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SiVproductDAO dao = new SiVproductDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSiVproductById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SiVproductDAO dao = new SiVproductDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	

	public String productSysnToVSOP(DynamicDict dto ) throws Exception {
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
		String ret=SoapClient.getInstance().prodSynToVSOP(this.productToVSOPXml(result1, result2,SiAccpet));
		ret=StringUtil.getInstance().getTagValue("resultCode", ret);
		return ret ;
	}
	
	private String productToVSOPXml(List cas,List vs,Map SiAccpet){
		String SystemId="1";
		String OrderId =(String)SiAccpet.get("app_id");
		String ProdSpecCode = (String)SiAccpet.get("prod_type");
		String ProductNo = (String)SiAccpet.get("product_no");
		String OldProductNo = (String)SiAccpet.get("old_no");
		//捆绑套餐与纯增值套餐只能有一种，复用同个字段
		String ProductOfferId =(String)SiAccpet.get("product_offer_id");
		String PackageId =(String)SiAccpet.get("product_offer_id");
		
		String ActionType = "ADD";//(String)SiAccpet.get("app_type");
		String UserState = (String)SiAccpet.get("user_state");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bf.append("<SubscribeToVSOPReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20100412").append("</TimeStamp>")
			.append("<OrderId>").append(OrderId).append("</OrderId>")
			.append("<SystemId>").append(SystemId).append("</SystemId>")
			
			.append("<ActionType>").append(ActionType).append("</ActionType>")
			.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
			.append("<ProductNo>").append(ProductNo).append("</ProductNo>")
			//.append("<OldProductNo>").append(OldProductNo).append("</OldProductNo>")
			.append("<ProductOfferId>").append(ProductOfferId).append("</ProductOfferId>")
			.append("<PackageId>").append(PackageId).append("</PackageId>");

			for(int i=0;i<vs.size();i++){
				Map mp=(Map)vs.get(i);
				String ActionType2 = (String)mp.get("act_type");
				String VProductID = (String)mp.get("vproduct_id");
				String eff_time = (String)mp.get("eff_time");
				String exp_time = (String)mp.get("exp_time");
			bf.append("<VProductInfo>")
			.append("<ActionType>").append(ActionType2).append("</ActionType>")
			.append("<VProductID>").append(VProductID).append("</VProductID>")
			.append("<EffDate>").append(eff_time).append("</EffDate>")
			.append("<ExpDate>").append(exp_time).append("</ExpDate>")
			 
			.append("</VProductInfo>")	;
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
		
		System.out.println("-------subscribeToVSOPXml------------="+bf.toString());
		return bf.toString();
	}
}
