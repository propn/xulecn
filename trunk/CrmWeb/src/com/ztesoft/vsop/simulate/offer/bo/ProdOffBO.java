package com.ztesoft.vsop.simulate.offer.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.simulate.offer.dao.ProdOffDAO;
import com.ztesoft.vsop.simulate.offer.dao.ProdOffDetaRoleDAO;
import com.ztesoft.vsop.simulate.offer.dao.ProdOffRelDAO;
import com.ztesoft.vsop.webservice.client.SoapClient;
import com.ztesoft.vsop.product.dao.ProductDAO;

public class ProdOffBO extends DictAction  {
	
	private static final int ADD = 1 ;
	private static final int UPDATE = 2 ;
	private static final int DELETE = 3 ;
	
	private static final int REL_ADD = 0 ;
	private static final int REL_UPDATE = 2 ;
	private static final int REL_DELETE = 1 ;
	
	
	private static  String offerXml(int actionType ,Map relMap, Map partnerMap) throws Exception{
		Map prodOffer = (Map)partnerMap.get("prodOffer") ;
		List productList = (List)partnerMap.get("productList") ;
		List relList = (List)partnerMap.get("relList") ;
		
		String ProductOfferType =(String)prodOffer.get("prod_offer_sub_type");
		String PNameEN = (String)prodOffer.get("pname_en");
		String ProductOfferID = (String)prodOffer.get("offer_nbr");
		String PNameCN =(String)prodOffer.get("pname_cn");
		String PDesEn = (String)prodOffer.get("pdes_en");
		
		String PDesCN = (String)prodOffer.get("pdes_cn");
		String Status = (String)prodOffer.get("state");
		String ChargingPolicyCN = (String)prodOffer.get("chargingpolicy_cn");
		String Scope = (String)prodOffer.get("scope");
		String system_code = (String)prodOffer.get("package_host");

		VsopStreamNoHelper vs = VsopStreamNoHelper.getInstance();
		String streamNo = vs.genReqStreamNo();
		StringUtil su = StringUtil.getInstance();
		String timeStamp = su.getCurrentTimeStamp();
		StringBuffer xml = new StringBuffer("");
		xml.append("<ProdOfferSyncToVSOPReq>")
			.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
			.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
			.append("<OPFlag>").append(actionType).append("</OPFlag>")
			.append("<SystemId>").append(system_code).append("</SystemId>")
			.append("<ProductOfferType>").append(ProductOfferType).append("</ProductOfferType>")
			.append("<ProductOfferID>").append(ProductOfferID).append("</ProductOfferID>")
			.append("<PNameCN>").append(PNameCN).append("</PNameCN>")
			.append("<PDesCN>").append(PDesCN).append("</PDesCN>")
			.append("<PNameEN>").append(PNameEN).append("</PNameEN>")
			.append("<PDesEn>").append(PDesEn).append("</PDesEn>")
			.append("<Status>").append(Status).append("</Status>")
			.append("<ChargingPolicyCN>").append(ChargingPolicyCN).append("</ChargingPolicyCN>")
			.append("<Scope>").append(Scope).append("</Scope>") ;
			//包含增值产品列表
			if(productList != null && !productList.isEmpty()){
				Iterator it = productList.iterator() ;
				while(it.hasNext()){
					Map product = (Map)it.next() ;
					String productId = Const.getStrValue(product, "product_nbr") ;
					xml.append("<ProductID>").append(productId).append("</ProductID>") ;
				}
			}
			
			//销售品关系xml
//			if(relList != null && !relList.isEmpty()){
//				Iterator it = relList.iterator() ;
//				while(it.hasNext()){
//					Map m = (Map) it.next() ;
//					xml.append("<ProdOfferRelation>");
//					xml.append("<OPType>").append(reLAction).append("</OPType>")
//					.append("<RelationType>").append(Const.getStrValue(m, "relation_type_id")).append("</RelationType>")
//					.append("<RProdOfferID>").append(Const.getStrValue(m, "offer_code_z")).append("</RProdOfferID>");
//				xml.append("</ProdOfferRelation>") ;
//				}
//			}
			if(relMap != null && !relMap.isEmpty()){
					xml.append("<ProdOfferRelation>");
					xml.append("<OPType>").append(Const.getStrValue(relMap, "OPType")).append("</OPType>")
					.append("<RelationType>").append(Const.getStrValue(relMap, "relation_type_id")).append("</RelationType>")
					.append("<RProdOfferID>").append(new ProdOffDAO().getNbrById(Const.getStrValue(relMap, "offer_z_id"))).append("</RProdOfferID>");
				xml.append("</ProdOfferRelation>") ;
			}
				
		xml.append("</ProdOfferSyncToVSOPReq>");
		
//		System.out.println("-------ProdOfferSyncToVSOPReq------------="+xml.toString());
//		StringUtil su = StringUtil.getInstance();
//		VsopStreamNoHelper vs = VsopStreamNoHelper.getInstance();
//		String streamNo = vs.genReqStreamNo();
		String resXml = su.getVsopRequest(streamNo, timeStamp, xml.toString());
		System.out.println("-------ProdOfferSyncToVSOPReq------------="+resXml);
		return resXml;
//		return xml.toString();
	}
	
	private static  String iSMP2VSOPOfferXML(int actionType ,Map relMap, Map partnerMap) throws Exception{
		Map prodOffer = (Map)partnerMap.get("prodOffer") ;
		List productList = (List)partnerMap.get("productList") ;
//		List relList = (List)partnerMap.get("relList") ;
		
		String ProductOfferType =(String)prodOffer.get("prod_offer_sub_type");
		String PNameEN = (String)prodOffer.get("pname_en");
		String ProductOfferID = (String)prodOffer.get("offer_nbr");//ChargingPolicyID
		String PNameCN =(String)prodOffer.get("pname_cn");
		String PDesEn = (String)prodOffer.get("pdes_en");
		
		String PDesCN = (String)prodOffer.get("pdes_cn");
		String Status = (String)prodOffer.get("state");
		String ChargingPolicyCN = (String)prodOffer.get("chargingpolicy_cn");
		String Scope = (String)prodOffer.get("scope");
		String system_code = (String)prodOffer.get("package_host");
		String ChargingPolicyID = (String)prodOffer.get("chargingpolicy_id");
		String SubOption = (String)prodOffer.get("sub_option");
		String PresentOption = (String)prodOffer.get("present_option");
		String CorpOnly = (String)prodOffer.get("corp_only");
		String PackageHost = (String)prodOffer.get("package_host");
		
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<PackageInfoSyncReq>")
			.append("<StreamingNo>").append("56").append("</StreamingNo>")
			.append("<TimeStamp>").append(new java.util.Date()).append("</TimeStamp>")
			.append("<OPFlag>").append(actionType).append("</OPFlag>")
			.append("<SystemId>").append(system_code).append("</SystemId>")
			.append("<PackageID>").append(ProductOfferID).append("</PackageID>")
			.append("<SubOption>").append(SubOption).append("</SubOption>")
			.append("<PresentOption>").append(PresentOption).append("</PresentOption>")
			.append("<CorpOnly>").append(CorpOnly).append("</CorpOnly>")
			.append("<PackageHost>").append(PackageHost).append("</PackageHost>")
			.append("<ProductOfferType>").append(ProductOfferType).append("</ProductOfferType>")
			
			.append("<PNameCN>").append(PNameCN).append("</PNameCN>")
			.append("<PDesCN>").append(PDesCN).append("</PDesCN>")
			.append("<PNameEN>").append(PNameEN).append("</PNameEN>")
			.append("<PDesEn>").append(PDesEn).append("</PDesEn>")
			.append("<Status>").append(Status).append("</Status>")
			.append("<ChargingPolicyCN>").append(ChargingPolicyCN).append("</ChargingPolicyCN>")
			.append("<ChargingPolicyID>").append(ChargingPolicyID).append("</ChargingPolicyID>")
			.append("<Scope>").append(Scope).append("</Scope>") ;
		
			//包含增值产品列表
			if(productList != null && !productList.isEmpty()){
				Iterator it = productList.iterator() ;
				while(it.hasNext()){
					Map product = (Map)it.next() ;
					String productId = Const.getStrValue(product, "product_nbr") ;
					xml.append("<ProductID>").append(productId).append("</ProductID>") ;
				}
			}
			
			//销售品关系xml
//			if(relList != null && !relList.isEmpty()){
//				Iterator it = relList.iterator() ;
//				while(it.hasNext()){
//					Map m = (Map) it.next() ;
//					xml.append("<ProdOfferRelation>");
//					xml.append("<OPType>").append(reLAction).append("</OPType>")
//					.append("<RelationType>").append(Const.getStrValue(m, "relation_type_id")).append("</RelationType>")
//					.append("<RProdOfferID>").append(Const.getStrValue(m, "offer_code_z")).append("</RProdOfferID>");
//				xml.append("</ProdOfferRelation>") ;
//				}
//			}
			if(relMap != null && !relMap.isEmpty()){
					xml.append("<ProdOfferRelation>");
					xml.append("<OPType>").append(Const.getStrValue(relMap, "OPType")).append("</OPType>")
					.append("<RelationType>").append(Const.getStrValue(relMap, "relation_type_id")).append("</RelationType>")
					.append("<RProdOfferID>").append(new ProdOffDAO().getNbrById(Const.getStrValue(relMap, "offer_z_id"))).append("</RProdOfferID>");
				xml.append("</ProdOfferRelation>") ;
			}
				
		xml.append("</PackageInfoSyncReq>");
		
		System.out.println("-------PackageInfoSyncReq------------="+xml.toString());
		return xml.toString();
	}
	
	/**
	 * 调用webservice接口
	 * @param actionType
	 * @param result
	 * @return
	 */
	private static String callRemoteService(int actionType , Map relMap ,String offer_id ) throws Exception{
		//调用后台接口
		Map result = getInterfaceMap(actionType ,  offer_id ) ;
		String ret=SoapClient.getInstance().offerSynToVSOP(offerXml(actionType  , relMap,  result) );
		System.out.println("server return xml ======="+ret) ;
		
		System.out.println("actionType==="+actionType+
				",ResultCode="+StringUtil.getInstance().getTagValue("ResultCode", ret)+
				",ResultDesc="+StringUtil.getInstance().getTagValue("ResultDesc", ret)) ;
		return ret ;
	}
	
	private static Map getInterfaceMap(int type , String offer_id )throws Exception {
		Map result = new HashMap() ;
		
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		
		whereCond.append(" and prod_offer_id = ? ");
		para.add(offer_id);
		
		//销售品信息
		Map keyCondMap = new HashMap() ;
		keyCondMap.put("prod_offer_id", offer_id) ;
		ProdOffDAO prodOffDao = new ProdOffDAO();
		Map prodOffer = prodOffDao.findByPrimaryKey(keyCondMap);
		result.put("prodOffer", prodOffer) ;
		
		//产品组成列表
		ProdOffDetaRoleDAO productDao = new ProdOffDetaRoleDAO();
		List productList = productDao.findByCond(whereCond.toString(), para) ;
		result.put("productList", productList) ;
		
		//产品组成列表
		ProdOffRelDAO relDao = new ProdOffRelDAO();
		List relList = relDao.findByCond(" and offer_a_id=? ", para) ;
		result.put("relList", relList) ;
		
		
		return result ;
	}
	
	
	/**
	 * 新增销售品关系时，点击Z销售品时Z销售品的选择范围
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOff(DynamicDict dto) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		List para = new ArrayList();
		String prodOffId = Const.getStrValue(param , "prod_off_id");
		StringBuffer sb = new StringBuffer();
		
		if(Const.containStrValue(param , "offer_nbr")) {
			String offerNbr = Const.getStrValue(param, "offer_nbr");
			sb.append(" and p.offer_nbr like'%" + offerNbr + "%'");
		}
		if(Const.containStrValue(param , "prod_offer_name")) {
			String name = Const.getStrValue(param , "prod_offer_name");
			sb.append(" and p.prod_offer_name like'%" + name + "%'");
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		ProdOffDAO dao = new ProdOffDAO();
		PageModel result = dao.searchByCond(sb.toString(), para, pageIndex, pageSize);
		
		return result ;
	}	
	
	
	public boolean insertProdOff(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		//到时可能要设定一些字段的默认值
		Map ProdOff = (Map) param.get("ProdOff") ;
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOffId = seqDao.getNextSequence("SEQ_PROD_OFFER_ID");
		ProdOff.put("prod_offer_id", prodOffId);
		
		ProdOffDAO dao = new ProdOffDAO();
		boolean result = dao.insert(ProdOff) ;
		
//		webservice 调用
		callRemoteService(ADD, null ,prodOffId ) ;
		return result ;
	}

	
	public boolean updateProdOff(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOff = (Map) param.get("ProdOff") ;
		String keyStr = "prod_offer_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOff,  keyStr) ;
		ProdOffDAO dao = new ProdOffDAO();
		boolean result = dao.updateByKey( ProdOff, keyCondMap );

		//webservice 调用
		callRemoteService(UPDATE, null,Const.getStrValue(ProdOff, keyStr) ) ;
		return result ;
	}
	
	
	public PageModel searchProdOffData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "offer_nbr")){
			//whereCond.append(" and offer_nbr = ? ");
			//para.add(Const.getStrValue(param , "offer_nbr")) ;
			String offerNbr = Const.getStrValue(param, "offer_nbr");
			whereCond.append(" and offer_nbr like'%" + offerNbr + "%'");
		}
		if(Const.containStrValue(param , "prod_offer_name")){
			//whereCond.append(" and prod_offer_name = ? ");
			//para.add(Const.getStrValue(param , "prod_offer_name")) ;
			String name = Const.getStrValue(param , "prod_offer_name");
			whereCond.append(" and prod_offer_name like'%" + name + "%'");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		ProdOffDAO dao = new ProdOffDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 销售品中的tab，销售品构成中调用到
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProduct(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param, "package_host")){
			whereCond.append(" and system_code=?");
			para.add(Const.getStrValue(param, "package_host"));
		}
		if(Const.containStrValue(param, "product_name")){
			whereCond.append(" and product_name like ?");
			para.add("%" + Const.getStrValue(param, "product_name") +"%");
		}
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		//调用DAO代码
		ProductDAO dao=new ProductDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProdOffDAO dao = new ProdOffDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProdOffDAO dao = new ProdOffDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffDAO dao = new ProdOffDAO();
		//webservice 调用
		callRemoteService(DELETE, null,Const.getStrValue(keyCondMap, "prod_offer_id") ) ;
		
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}

	public boolean insertProdOffDetaRole(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffDetaRole = (Map) param.get("ProdOffDetaRole") ;
		
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		boolean result = dao.insert(ProdOffDetaRole) ;
		

		//webservice 调用
		callRemoteService(UPDATE, null,Const.getStrValue(ProdOffDetaRole, "prod_offer_id") ) ;
		return result ;
	}

	
	public boolean updateProdOffDetaRole(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffDetaRole = (Map) param.get("ProdOffDetaRole") ;
		String keyStr = "prod_offer_role_cd";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOffDetaRole,  keyStr) ;
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		boolean result = dao.updateByKey( ProdOffDetaRole, keyCondMap );
		


		//webservice 调用
		callRemoteService(UPDATE,null,Const.getStrValue(ProdOffDetaRole, "prod_offer_id") ) ;
		return result ;
	}
	
	
	public PageModel searchProdOffDetaRoleData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		
		if(Const.containStrValue(param , "prod_offer_id")){
			whereCond.append(" and prod_offer_id = ? ");
			para.add(Const.getStrValue(param , "prod_offer_id")) ;
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		//调用DAO代码
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize);
		
		return result;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffDetaRoleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffDetaRoleByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffDetaRoleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		
		Map ProdOffDetaRole = dao.findByPrimaryKey(keyCondMap) ;
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		//webservice 调用
		callRemoteService(UPDATE, null,Const.getStrValue(ProdOffDetaRole, "prod_offer_id") ) ;
		return result ;
	}
	public boolean insertProdOffRel(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffRel = (Map) param.get("ProdOffRel") ;
		ProdOffRel.put("OPType", ""+REL_ADD) ;

		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOfferRelId = seqDao.getNextSequence("SEQ_prod_offer_rel_id");
		ProdOffRel.put("prod_offer_rel_id", prodOfferRelId) ;
		
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.insert(ProdOffRel) ;
		

		//webservice 调用
		callRemoteService(UPDATE, ProdOffRel,Const.getStrValue(ProdOffRel, "offer_a_id") ) ;
		return result ;
	}

	
	public boolean updateProdOffRel(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffRel = (Map) param.get("ProdOffRel") ;
		String keyStr = "prod_offer_rel_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOffRel,  keyStr) ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.updateByKey( ProdOffRel, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProdOffRelData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if(Const.containStrValue(param , "offer_a_id")){
			whereCond.append(" and offer_a_id = ? ");
			para.add(Const.getStrValue(param , "offer_a_id"));
		}
		if(Const.containStrValue(param , "offer_z_id")){
			whereCond.append(" or offer_z_id = ? ");
			para.add(Const.getStrValue(param , "offer_z_id"));
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		
		//调用DAO代码
		ProdOffRelDAO dao = new ProdOffRelDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffRelById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffRelByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProdOffRelDAO dao = new ProdOffRelDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffRelById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		Map ProdOffRel = dao.findByPrimaryKey(keyCondMap)  ;
		ProdOffRel.put("OPType", ""+REL_DELETE) ;
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		//webservice 调用
		callRemoteService(UPDATE, ProdOffRel,Const.getStrValue(ProdOffRel, "offer_a_id") ) ;
		return result ;
	}
	
	public boolean deleteProdOffRel(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map map = Const.getParam(dto);
		
		List list = new ArrayList();
		list.add(map.get("offer_a_id"));
		list.add(map.get("relation_type_id"));
		list.add(map.get("offer_z_id"));
		
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.delete("", list) > 0;
		
		return result ;
	}
	
	public String getProdOffNameByKey(DynamicDict dto ) throws Exception {
		String productName = "";
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("prod_offer_id"));
		ProdOffRelDAO dao = new ProdOffRelDAO();
		List l = dao.findBySql(dao.getSQLForProdOffName(), list);
		if (null != l && !l.isEmpty()) {
			productName = ((HashMap) l.get(0)).get("prod_offer_name").toString();
		}
		return productName;
	}
}
