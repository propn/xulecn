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
import com.ztesoft.vsop.simulate.dao.SiSmsDAO ;
import com.ztesoft.vsop.webservice.client.SoapClient;

public class SiSmsBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchSiSmsData 改方法的参数名称
 		3. findSiSmsByCond(String radom_code) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertSiSms(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiSms = (Map) param.get("SiSms") ;
		
		SiSmsDAO dao = new SiSmsDAO();
		boolean result = dao.insert(SiSms) ;
		return result ;
	}

	
	public boolean updateSiSms(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiSms = (Map) param.get("SiSms") ;
		String keyStr = "radom_code";
		Map keyCondMap  = Const.getMapForTargetStr(SiSms,  keyStr) ;
		SiSmsDAO dao = new SiSmsDAO();
		boolean result = dao.updateByKey( SiSms, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSiSmsData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "product_no")){
			whereCond.append(" and product_no = ? ");
			para.add(Const.getStrValue(param , "product_no")) ;
		}
		if(Const.containStrValue(param , "sms_type")){
			whereCond.append(" and sms_type = ? ");
			para.add(Const.getStrValue(param , "sms_type")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SiSmsDAO dao = new SiSmsDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSiSmsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SiSmsDAO dao = new SiSmsDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSiSmsByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SiSmsDAO dao = new SiSmsDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSiSmsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SiSmsDAO dao = new SiSmsDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	public String confirmAccept(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiSms = (Map) param.get("SiSms") ;
		
		//String app_id = (String)SiAccpet.get("app_id");
		
		
		//调用后台接口
		String ret=SoapClient.getInstance().recvRQMessage(this.createRecvRQMessageReqXml(SiSms,"1"));
		ret=StringUtil.getInstance().getTagValue("ResultCode", ret);
		return ret ;
	}
	public String cancelAccept(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiSms = (Map) param.get("SiSms") ;
		
		//String app_id = (String)SiAccpet.get("app_id");
		
		
		//调用后台接口
		String ret=SoapClient.getInstance().recvRQMessage(this.createRecvRQMessageReqXml(SiSms,"0"));
		ret=StringUtil.getInstance().getTagValue("ResultCode", ret);
		return ret ;
	}
	private String createRecvRQMessageReqXml(Map SiSms,String ret){
		String RQCode =(String)SiSms.get("radom_code");
		String ProdSpecCode = (String)SiSms.get("prod_type");
		String ProductNo = (String)SiSms.get("product_no");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		//bf.append("<ReplyRQMsgToVSOPReq>")
			//.append("<StreamingNo>").append("123456").append("</StreamingNo>")

		bf.append("<RecvRQMessageReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")

			.append("<TimeStamp>").append("20100412").append("</TimeStamp>")
			.append("<RQCode>").append(RQCode).append("</RQCode>")
			.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
			.append("<ProductNo>").append(ProductNo).append("</ProductNo>")
			.append("<RQResult>").append(ret).append("</RQResult>");

			
		bf.append("</ReplyRQMsgToVSOPReq>");
		
		System.out.println("-------RecvRQMessageReqXml------------="+bf.toString());
		return bf.toString();
	}
}
