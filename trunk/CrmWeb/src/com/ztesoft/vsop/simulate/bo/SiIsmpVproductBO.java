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
import com.ztesoft.vsop.simulate.dao.SiIsmpVproductDAO ;
import com.ztesoft.vsop.webservice.client.SoapClient;

public class SiIsmpVproductBO extends DictAction  {
	/**
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchSiIsmpVproductData �ķ����Ĳ�������
 		3. findSiIsmpVproductByCond() ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */
	
	public boolean insertSiIsmpVproduct(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiIsmpVproduct = (Map) param.get("SiIsmpVproduct") ;
		
		SiIsmpVproductDAO dao = new SiIsmpVproductDAO();
		boolean result = dao.insert(SiIsmpVproduct) ;
		return result ;
	}

	
	public boolean updateSiIsmpVproduct(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiIsmpVproduct = (Map) param.get("SiIsmpVproduct") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(SiIsmpVproduct,  keyStr) ;
		SiIsmpVproductDAO dao = new SiIsmpVproductDAO();
		boolean result = dao.updateByKey( SiIsmpVproduct, keyCondMap );
		
		return result ;
	}
	public String syncVproduct(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SiIsmpVproduct = (Map) param.get("SiIsmpVproduct") ;
		//String result =null;
		
		//���ú�̨�ӿ�
		String ret=SoapClient.getInstance().subsInstSynToVSOP(this.createSubsInstSynToVSOPXml(SiIsmpVproduct));
		ret=StringUtil.getInstance().getTagValue("ResultCode", ret);
		return ret ;
	}
	private String createSubsInstSynToVSOPXml(Map SiIsmpVproduct){
		String ProdSpecCode = (String)SiIsmpVproduct.get("user_type");
		String ProductNo = (String)SiIsmpVproduct.get("product_no");
		String productID = (String)SiIsmpVproduct.get("vproduct_id");
		String oldProductID= (String)SiIsmpVproduct.get("old_vproduct_id");
		String packageID= (String)SiIsmpVproduct.get("package_id");
		String oldpackageID= (String)SiIsmpVproduct.get("old_package_id");
		String PProductOfferID= (String)SiIsmpVproduct.get("pprodoffer_id");
		String oldPProductOfferID= (String)SiIsmpVproduct.get("old_pprodoffer_id");
		String OPType= (String)SiIsmpVproduct.get("sync_type");
		String effectiveTime= (String)SiIsmpVproduct.get("eff_time");
		String expireTime= (String)SiIsmpVproduct.get("exp_time");

		StringBuffer bf = new StringBuffer("");
		bf.append("<SubsInstSynFromISMPReq>")
			.append("<StreamingNo>").append("123456").append("</StreamingNo>")
			.append("<TimeStamp>").append("20100412235959").append("</TimeStamp>")
			.append("<SystemId>").append("203").append("</SystemId>")
			.append("<UserIDType>").append(ProdSpecCode).append("</UserIDType>")
			.append("<UserID>").append(ProductNo).append("</UserID>")
			.append("<ProductID>").append(productID).append("</ProductID>")
			.append("<OldProductID>").append(oldProductID).append("</OldProductID>")
			.append("<PackageID>").append(packageID).append("</PackageID>")
			.append("<OldpackageID>").append(oldpackageID).append("</OldpackageID>")
			.append("<PProductOfferID>").append(PProductOfferID).append("</PProductOfferID>")
			.append("<OldPProductOfferID>").append(oldPProductOfferID).append("</OldPProductOfferID>")
			.append("<OPType>").append(OPType).append("</OPType>")
			.append("<EffDate>").append(effectiveTime).append("</EffDate>")
			.append("<ExpDate>").append(expireTime).append("</ExpDate>")
			.append("<VerUserId>").append("").append("</VerUserId>")
			;

			
		bf.append("</SubsInstSynFromISMPReq>");
		
		String xml=StringUtil.getInstance().getVsopRequest("123456","20100412235959", bf.toString());
		
		System.out.println("-------SubsInstSynFromISMPReq------------="+xml);
		return xml;
	}
	
	
	
	public PageModel searchSiIsmpVproductData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "user_type")){
			whereCond.append(" and user_type = ? ");
			para.add(Const.getStrValue(param , "user_type")) ;
		}
		if(Const.containStrValue(param , "product_no")){
			whereCond.append(" and product_no = ? ");
			para.add(Const.getStrValue(param , "product_no")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		SiIsmpVproductDAO dao = new SiIsmpVproductDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getSiIsmpVproductById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		SiIsmpVproductDAO dao = new SiIsmpVproductDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSiIsmpVproductByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		SiIsmpVproductDAO dao = new SiIsmpVproductDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSiIsmpVproductById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		SiIsmpVproductDAO dao = new SiIsmpVproductDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
