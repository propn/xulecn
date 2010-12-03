package com.ztesoft.vsop.simulate.bo;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.simulate.dao.PartnerDAO;
import com.ztesoft.vsop.simulate.dao.ProdAccNbrTypeDAO;
import com.ztesoft.vsop.simulate.dao.ProdPlatformDAO;
import com.ztesoft.vsop.simulate.dao.ProductDAO;
import com.ztesoft.vsop.simulate.dao.ProductRelationDAO;
import com.ztesoft.vsop.simulate.dao.ProductServAbilityRelDAO;
import com.ztesoft.vsop.webservice.client.SoapClient;
import com.ztesoft.vsop.simulate.dao.ProductSystemInfoDAO;
import com.ztesoft.vsop.product.vo.ProductType;

public class SiSPProductManager extends DictAction {

	/**
	 * 
	 * 定义操作类型
	 */
	private static final int PRODUCT_INSERT = 0 ;
	private static final int PRODUCT_UPDATE = 1 ;
	private static final int PRODUCT_DELETE = 2 ;
	
	private static final int SERV_ABILITY_INSERT = 3 ;
	private static final int SERV_ABILITY_UPDATE = 4 ;
	private static final int SERV_ABILITY_DELETE = 5 ;
	
	private static final int PLATFORM_INSERT = 6 ;
	private static final int PLATFORM_UPDATE = 7 ;
	private static final int PLATFORM_DELETE = 8 ;
	
	private static final int REL_INSERT = 9 ;
	private static final int REL_DELETE = 11 ;
	

	private static final int ACC_NBR_INSERT = 12 ;
	private static final int ACC_NBR_UPDATE = 13 ;
	private static final int ACC_NBR_DELETE = 14 ;
	
	private static final int I_INSERT = 1 ;//增加
	private static final int I_UPDATE = 2 ;//修改
	private static final int I_DELETE = 3 ;//删除
	
	/**
	 * 调用webservice接口
	 * @param actionType
	 * @param result
	 * @return
	 */
	private static String callRemoteService(int actionType ,String product_id ) throws Exception{
		//调用后台接口
		Map result = getInterfaceMap(actionType ,  product_id ) ;
		String ret=SoapClient.getInstance().prodSynToVSOP(productSysXML(actionType ,  result));
		System.out.println("server return xml ======="+ret) ;
//		ret=StringUtil.getInstance().getTagValue("ResultCode", ret);
		
		//System.out.println("actionType==="+actionType+
		//		",ResultCode="+StringUtil.getInstance().getTagValue("ResultCode", ret)+
		//		",ResultDesc="+StringUtil.getInstance().getTagValue("ResultDesc", ret)) ;
		return ret ;
	}
	/**
	 * 接口操作类型
	 * @param actionType
	 * @return
	 */
	private static int getOPFlag(int actionType){
		if(actionType == PRODUCT_INSERT)
			return 1 ;
		if(actionType == PRODUCT_DELETE)
			return 3 ;
		return 2 ;//其它为修改操作
	}
	
	/**
	 * 根据页面按钮类型生成相应xml
	 * @param actionType
	 * @param result
	 * @return
	 */
	private static String productSysXML(int actionType ,Map result){
		
		 Map product = (Map)result.get("product") ;
		 List servAbility = result.get("servAbility") != null ? (List)result.get("servAbility") : null;
		 List platForm  =  result.get("platForm") != null ? (List)result.get("platForm") : null ;
		 List rel  =  result.get("rel") != null ? (List)result.get("rel") : null;
		 List accNbr  =  result.get("accNbr") != null ? (List)result.get("accNbr") : null;
		
		 
		 
		StringBuffer xml = new StringBuffer("");
		int opflag = getOPFlag( actionType) ;

		VsopStreamNoHelper vs = VsopStreamNoHelper.getInstance();
		String streamNo = vs.genReqStreamNo();
		StringUtil su = StringUtil.getInstance();
		String timeStamp = su.getCurrentTimeStamp();
		xml.append("<ProductSyncToVSOPReq>")
		.append("<StreamingNo>"+streamNo+"</StreamingNo>")
		.append("<TimeStamp>"+timeStamp+"</TimeStamp>")
		//.append("<SystemId>201</SystemId>")  //交易发起系统标识，暂时写死为1
		.append("<SPID>"+Const.getStrValue(product, "product_provider_id")+"</SPID>")
		.append("<OPFlag>"+opflag+"</OPFlag>")
		.append("<ProductID>"+Const.getStrValue(product, "product_id")+"</ProductID>")
		.append("<ProductType>").append(Const.getStrValue(product, "prod_func_type")).append("</ProductType>")
		.append("<ProductNbr>").append(Const.getStrValue(product, "product_nbr")).append("</ProductNbr>")
		.append("<PnameCN>"+Const.getStrValue(product, "product_name")+"</PnameCN>")
		.append("<PnameEN>"+"english"+"</PnameEN>")
		.append("<PdescriptionCN>"+Const.getStrValue(product, "product_desc")+"</PdescriptionCN>")
		.append("<PdescriptionEn>"+"english desc"+"</PdescriptionEn>") ;
		
		//业务能力编码,新增时无，删除时没必要，只有修改时才需用到 >>> 服务器端，判断是否存在节点，有则先删除后insert
		if(opflag == I_UPDATE && servAbility != null && !servAbility.isEmpty()){
			Iterator sit = servAbility.iterator() ;
			while( sit.hasNext() ){
				Map ability = (Map) sit.next() ;
				xml.append("<ServiceCapabilityID>"+Const.getStrValue(ability, "service_code")+"</ServiceCapabilityID>") ;
			}
			
		}
			
		xml.append("<Status>"+Const.getStrValue(product, "product_state_cd")+"</Status>")
		.append("<Scope>"+Const.getStrValue(product, "scope")+"</Scope>")
		.append("<ProductHost>"+Const.getStrValue(product, "order_host")+"</ProductHost>") ;
		
		//平台编码,新增时有，删除时没必要， >>> 服务器端，判断是否存在节点
		if(opflag == I_INSERT && platForm != null && !platForm.isEmpty()){
			Iterator pit = platForm.iterator() ;
			while( pit.hasNext() ){
				Map pfm = (Map) pit.next() ;
				xml.append("<PlatForm>"+Const.getStrValue(pfm, "platform_id")+"</PlatForm>") ;
			}
			
		}
		
		//接入号码,新增时无，删除时没必要，只有修改时才需用到 >>> 服务器端，判断是否存在节点，有则先删除后insert
		if(opflag == I_UPDATE && accNbr != null && !accNbr.isEmpty()){
			Iterator ait = accNbr.iterator() ;
			while( ait.hasNext() ){
				Map accm = (Map) ait.next() ;
				xml.append("<ProductOPCode>")
				    .append("<FeatureStr>"+Const.getStrValue(accm, "feature_no")+"</FeatureStr>")
					.append("<AccessNO>"+Const.getStrValue(accm, "access_no")+"</AccessNO>")
					.append("<OPType>"+Const.getStrValue(accm, "acc_nbr_type_cd")+"</OPType>")
					.append("<MatchMode>"+Const.getStrValue(accm, "match_mode")+"</MatchMode>")
					.append("</ProductOPCode>");
			}
			
		}
		//产品关系,新增时无，删除时没必要，只有修改时才需用到 >>> 服务器端，判断是否存在节点，有责根据操作类型进行处理
		if(opflag == I_UPDATE && (actionType == REL_DELETE || actionType == REL_INSERT)){
			Iterator rit = rel.iterator() ;
			while( rit.hasNext() ){
				Map rm = (Map) rit.next() ;
				xml.append("<ProdRelation>")
					.append("<OPType>"+(actionType == REL_DELETE ? 1 : 0)+"</OPType>")
					.append("<RelationType>"+Const.getStrValue(rm, "relation_type_cd")+"</RelationType>")
					.append("<RProductID>"+Const.getStrValue(rm, "pro_product_id")+"</RProductID>")
					.append("</ProdRelation>") ;
			}
			
		}		
		xml.append("</ProductSyncToVSOPReq>");
		
		System.out.println("-------product xml ------------\n"+xml.toString());
		String resXml = su.getVsopRequest(streamNo, timeStamp, xml.toString());
		System.out.println("-------ProductSyncToVSOPReq------------="+resXml);
		return resXml;
//		return xml.toString();
	}
	
	private String getSequence(String sequenceCode) {
		SequenceManageDAOImpl sequenceManageDAO = new SequenceManageDAOImpl();
		return sequenceManageDAO.getNextSequenceWithDB(sequenceCode,
				JNDINames.VSOP_DATASOURCE);
	}

	public boolean insertProdAccNbrType(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProdAccNbrType = (Map) param.get("ProdAccNbrType");

		String product_id = Const.getStrValue(ProdAccNbrType, "product_id");
		String access_no = Const.getStrValue(ProdAccNbrType, "access_no");

		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		boolean existsRow = dao.checkExistsRow(product_id, access_no, null);
		if (existsRow)
			return false;

		ProdAccNbrType.put("prod_acc_nbr_type_id",
				getSequence("seq_prod_acc_nbr_type_id"));

		boolean result = dao.insert(ProdAccNbrType);
		//webservice调用
		callRemoteService(ACC_NBR_INSERT, product_id ) ;
		return result;
	}

	private static Map getInterfaceMap(int type , String product_id )throws Exception {
		Map result = new HashMap() ;
		
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		
		whereCond.append(" and product_id = ? ");
		para.add(product_id);
		
		//Map product
		Map keyCondMap = new HashMap() ;
		keyCondMap.put("product_id", product_id) ;
		ProductDAO productDao = new ProductDAO();
		Map product = productDao.findByPrimaryKey(keyCondMap);
		result.put("product", product) ;
		
		//List servAbility
		ProductServAbilityRelDAO abilityDao = new ProductServAbilityRelDAO();
		List servAbility = abilityDao.findByCond(whereCond.toString(), para) ;
		result.put("servAbility", servAbility) ;
		
		//List platForm
		ProdPlatformDAO platformDao = new ProdPlatformDAO();
		//ProductSystemInfoDAO platformDao=new ProductSystemInfoDAO();
		List platForm = platformDao.findByCond(whereCond.toString(), para) ;
		result.put("platForm", platForm) ;
		
		//List rel
		if(type == REL_INSERT || type == REL_DELETE){
			ProductRelationDAO relDao = new ProductRelationDAO();
			List rel = relDao.findByCond(whereCond.toString(), para) ;
			result.put("rel", rel) ;
		}
		 
		//List accNbr 
		ProdAccNbrTypeDAO typeDao = new ProdAccNbrTypeDAO();
		List accNbr = typeDao.findByCond(whereCond.toString(), para);
		result.put("accNbr", accNbr) ;
		
		return result ;
	}
	
	public boolean updateProdAccNbrType(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProdAccNbrType = (Map) param.get("ProdAccNbrType");

		String product_id = Const.getStrValue(ProdAccNbrType, "product_id");
		String access_no = Const.getStrValue(ProdAccNbrType, "access_no");
		String prod_acc_nbr_type_id = Const.getStrValue(ProdAccNbrType,
				"prod_acc_nbr_type_id");

		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		boolean existsRow = dao.checkExistsRow(product_id, access_no,
				prod_acc_nbr_type_id);
		if (existsRow)
			return false;

		String keyStr = "prod_acc_nbr_type_id";
		Map keyCondMap = Const.getMapForTargetStr(ProdAccNbrType, keyStr);
		boolean result = dao.updateByKey(ProdAccNbrType, keyCondMap);

		//webservice调用
		callRemoteService(ACC_NBR_UPDATE, product_id ) ;
		return result;
	}

	public PageModel searchProdAccNbrTypeData(DynamicDict dto) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		//调用DAO代码
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdAccNbrTypeById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProdAccNbrTypeByCond(DynamicDict dto) throws Exception {
		//条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		//调用DAO代码
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProdAccNbrTypeById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		
		Map m = dao.findByPrimaryKey(keyCondMap) ;
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		//webservice调用
		callRemoteService(ACC_NBR_DELETE,(String) m.get("product_id") ) ;
		return result;
	}

	public boolean insertProdPlatform(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProdPlatform = (Map) param.get("ProdPlatform");

		String system_code = Const.getStrValue(ProdPlatform, "system_code");
		String product_id = Const.getStrValue(ProdPlatform, "product_id");

		//ProdPlatformDAO dao = new ProdPlatformDAO();
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		boolean existsRow = dao.checkExistsRow(system_code, product_id, null);
		if (existsRow)
			return false;

		ProdPlatform.put("partner_system_info_id", getSequence("seq_platform_id"));

		boolean result = dao.insert(ProdPlatform);
		
		//webservice调用
		callRemoteService(PLATFORM_INSERT, product_id ) ;
		return result;
	}

	public boolean updateProdPlatform(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProdPlatform = (Map) param.get("ProdPlatform");

		String system_code = Const.getStrValue(ProdPlatform, "system_code");
		String product_id = Const.getStrValue(ProdPlatform, "product_id");
		String partner_system_info_id = Const.getStrValue(ProdPlatform, "partner_system_info_id");

		//ProdPlatformDAO dao = new ProdPlatformDAO();
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		boolean existsRow = dao.checkExistsRow(system_code, product_id,
				partner_system_info_id);
		if (existsRow)
			return false;

		String keyStr = "partner_system_info_id";
		Map keyCondMap = Const.getMapForTargetStr(ProdPlatform, keyStr);
		boolean result =  dao.updateByKey(ProdPlatform, keyCondMap);
		
//		webservice调用
		callRemoteService(PLATFORM_UPDATE, product_id ) ;
		return result ;
	}

	public PageModel searchProdPlatformData(DynamicDict dto) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		//调用DAO代码
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdPlatformById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProdPlatformByCond(DynamicDict dto) throws Exception {
		//条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		//调用DAO代码
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProdPlatformById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		Map m = dao.findByPrimaryKey(keyCondMap) ;
		boolean result = dao.deleteByKey(keyCondMap) > 0;

//		webservice调用
		callRemoteService(PLATFORM_DELETE, (String) m.get("product_id") ) ;
		return result;
	}

	public boolean insertProduct(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");

		ProductDAO dao = new ProductDAO();
		String product_nbr = Const.getStrValue(Product, "product_nbr");
		String system_code = Const.getStrValue(Product, "system_code");

		boolean existsRow = dao.checkExistsRow(product_nbr, system_code, null);
		if (existsRow)
			return false;

		Product.put("product_id", getSequence("seq_product_id"));
		Product.put("product_state_cd", "0");//正常状态
		Product.put("create_date", DAOUtils.getShortFormatedDate());

		boolean result = dao.insert(Product);
		
		//webservice调用
		callRemoteService(PRODUCT_INSERT,(String) Product.get("product_id") ) ;
		return result;
	}

	public boolean updateProduct(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");

		ProductDAO dao = new ProductDAO();
		String product_nbr = Const.getStrValue(Product, "product_nbr");
		String system_code = Const.getStrValue(Product, "system_code");

		boolean existsRow = dao.checkExistsRow(product_nbr, system_code,
				null);
		if (existsRow)
			return false;

		Product.put("state_date", DAOUtils.getShortFormatedDate());
		String keyStr = "product_id";
		Map keyCondMap = Const.getMapForTargetStr(Product, keyStr);
		boolean result = dao.updateByKey(Product, keyCondMap);

//		webservice调用
		callRemoteService(PRODUCT_UPDATE, (String)Product.get("product_id") ) ;
		return result;
	}

	public PageModel searchProductData(DynamicDict dto) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		if (Const.containStrValue(param, "service_code")) {
			whereCond
					.append(" and product_id in (select distinct sa.product_id from PRODUCT_SERVICE_ABILITY_REL sa where sa.service_code=?) ");
			para.add(Const.getStrValue(param, "service_code"));
		}
		if (Const.containStrValue(param, "product_name")) {
			whereCond.append(" and product_name like ? ");
			para.add("%" + Const.getStrValue(param, "product_name") + "%");
		}
		if (Const.containStrValue(param, "product_nbr")) {
			whereCond.append(" and product_nbr like ? ");
			para.add("%" + Const.getStrValue(param, "product_nbr") + "%");
		}

		if (Const.containStrValue(param, "fileterProductId")) {
			whereCond
					.append(" and product_id <> ? and product_id not in(select pr.pro_product_id from product_relation pr where pr.state_cd='00A' and pr.product_id=? ) and PRODUCT_STATE_CD='0' ");
			para.add(Const.getStrValue(param, "fileterProductId"));
			para.add(Const.getStrValue(param, "fileterProductId"));
		}
		//whereCond.append(" and PROD_FUNC_TYPE=1 ");  //1为增值产品类
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		//调用DAO代码
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductDAO dao = new ProductDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProductByCond(DynamicDict dto) throws Exception {
		//条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		//调用DAO代码
		ProductDAO dao = new ProductDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProductById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductDAO dao = new ProductDAO();
		Map m = dao.findByPrimaryKey(keyCondMap);
		
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		//webservice调用
		callRemoteService(PRODUCT_DELETE, (String) m.get("product_id") ) ;
		return result;
	}

	public boolean insertProductRelation(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProductRelation = (Map) param.get("ProductRelation");
		ProductRelation
				.put("product_rel_id", getSequence("seq_product_rel_id"));
		ProductRelation.put("state_cd", "00A");
		ProductRelation.put("create_date", DAOUtils.getShortFormatedDate());

		ProductRelationDAO dao = new ProductRelationDAO();
		boolean result = dao.insert(ProductRelation);
		
		//webservice调用
		callRemoteService(REL_INSERT, (String)ProductRelation.get("product_id") ) ;

		return result;
	}

	public boolean updateProductRelation(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProductRelation = (Map) param.get("ProductRelation");
		ProductRelation.put("state_date", DAOUtils.getShortFormatedDate());
		String keyStr = "product_rel_id";
		Map keyCondMap = Const.getMapForTargetStr(ProductRelation, keyStr);
		ProductRelationDAO dao = new ProductRelationDAO();
		boolean result = dao.updateByKey(ProductRelation, keyCondMap);

		return result;
	}

	public PageModel searchProductRelationData(DynamicDict dto)
			throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}
		whereCond.append(" and STATE_CD = ? ");
		para.add("00A");

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		//调用DAO代码
		ProductRelationDAO dao = new ProductRelationDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductRelationById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductRelationDAO dao = new ProductRelationDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProductRelationByCond(DynamicDict dto) throws Exception {
		//条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		//调用DAO代码
		ProductRelationDAO dao = new ProductRelationDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProductRelationById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductRelationDAO dao = new ProductRelationDAO();
		
		Map m = dao.findByPrimaryKey(keyCondMap) ;
		

		//webservice调用
		callRemoteService(REL_DELETE,(String) m.get("product_id") ) ;
		boolean result = dao.deleteByKey(keyCondMap) > 0;
		return result;
	}

	public boolean insertProductServAbilityRel(DynamicDict dto)
			throws Exception {
		Map param = Const.getParam(dto);
		Map ProductServAbilityRel = (Map) param.get("ProductServAbilityRel");
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		String service_code = Const.getStrValue(ProductServAbilityRel, "service_code");
		String product_id = Const.getStrValue(ProductServAbilityRel,
				"product_id");

		boolean existsRow = dao.checkExistsRow(service_code, product_id, null);
		if (existsRow)
			return false;

		ProductServAbilityRel.put("product_service_ability_rel_id",
				getSequence("seq_service_ability_rel_id"));
		ProductServAbilityRel.put("rel_type", "0");

		boolean result =  dao.insert(ProductServAbilityRel);
		//webservice调用
		callRemoteService(SERV_ABILITY_INSERT, product_id ) ;
		return result ;
	}

	public boolean updateProductServAbilityRel(DynamicDict dto)
			throws Exception {
		Map param = Const.getParam(dto);
		Map ProductServAbilityRel = (Map) param.get("ProductServAbilityRel");

		String service_code = Const.getStrValue(ProductServAbilityRel, "service_code");
		String product_id = Const.getStrValue(ProductServAbilityRel,
				"product_id");
		String product_service_ability_rel_id = Const.getStrValue(ProductServAbilityRel,
				"product_service_ability_rel_id");

		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		boolean existsRow = dao.checkExistsRow(service_code, product_id,
				product_service_ability_rel_id);
		if (existsRow)
			return false;

		String keyStr = "product_service_ability_rel_id";
		Map keyCondMap = Const
				.getMapForTargetStr(ProductServAbilityRel, keyStr);

		boolean result =  dao.updateByKey(ProductServAbilityRel, keyCondMap);
		
		//webservice调用
		callRemoteService(SERV_ABILITY_UPDATE, product_id ) ;
		return result  ;
	}

	public PageModel searchProductServAbilityRelData(DynamicDict dto)
			throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		//调用DAO代码
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductServAbilityRelById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProductServAbilityRelByCond(DynamicDict dto)
			throws Exception {
		//条件处理
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		//调用DAO代码
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProductServAbilityRelById(DynamicDict dto)
			throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		
		Map m = dao.findByPrimaryKey(keyCondMap) ;
		
		boolean result = dao.deleteByKey(keyCondMap) > 0;
//		webservice调用
		callRemoteService(SERV_ABILITY_DELETE, (String)m.get("product_id") ) ;
		return result;
	}

	/**
	 * 
	 * 加载产品类型树
	 * @param dto
	 * @return
	 * @throws Exception
	 * 
	 */
	public String loadProductType(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto);
		String typeId = (String) keyCondMap.get("typeId");
		ProductDAO dao = new ProductDAO();
		ArrayList treeList = ptMap2VO(dao.loadProductType(typeId));
		String result = XMLSegBuilder.toXmlItems(treeList);
		return result;
	}

	private ArrayList ptMap2VO(ArrayList treeList) {
		if (treeList == null || treeList.isEmpty())
			return null;

		ArrayList result = null;
		for (int i = 0; i < treeList.size(); i++) {
			Object o = treeList.get(i);
			if (o instanceof ProductType)
				return treeList;

			if (result == null) {
				result = new ArrayList();
			}

			HashMap m = (HashMap) o;
			ProductType type = new ProductType();
			type.loadFromHashMap(m);
			result.add(type);
		}
		return result;
	}

	public PageModel searchPartnerData(DynamicDict dto) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "partner_code")) {
			whereCond.append(" and partner_code = ? ");
			para.add(Const.getStrValue(param, "partner_code"));
		}
		if (Const.containStrValue(param, "partner_type")) {
			whereCond.append(" and partner_type = ? ");
			para.add(Const.getStrValue(param, "partner_type"));
		}
		if (Const.containStrValue(param, "partner_name")) {
			whereCond.append(" and partner_name like ? ");
			para.add("%" + Const.getStrValue(param, "partner_name") + "%");
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		//调用DAO代码
		PartnerDAO dao = new PartnerDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}
}
