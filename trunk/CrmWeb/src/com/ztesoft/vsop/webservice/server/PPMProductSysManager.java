package com.ztesoft.vsop.webservice.server;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.util.Log;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.ztesoft.crm.product.dao.ProdOffDAO;
import com.ztesoft.crm.product.dao.ProdOffDetaRoleDAO;
import com.ztesoft.crm.product.dao.RoleProdRelaDao;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.ismp.IsmpServiceVo;
import com.ztesoft.vsop.ismp.dao.IsmpSyncDao;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.product.dao.ProdAccNbrTypeDAO;
import com.ztesoft.vsop.product.dao.ProdPlatformDAO;
import com.ztesoft.vsop.product.dao.ProductDAO;
import com.ztesoft.vsop.product.dao.ProductRelationDAO;
import com.ztesoft.vsop.product.dao.ProductServAbilityRelDAO;
import com.ztesoft.vsop.product.dao.ProductSystemInfoDAO;
import com.ztesoft.vsop.product.vo.ProductSysInfo;
import com.ztesoft.vsop.spManage.dao.PartnerDAO;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 产品同步处理逻辑
 * @author easonwu 2010-4-12 下午07:27:00
 * 
 * cooltan modify on 20100604 增加增量刷新缓存操作
 *
 */
public class PPMProductSysManager extends DictAction{
	private static Logger logger = Logger.getLogger(PPMProductSysManager.class);
	//操作常量
	private static final String ADD = "1";

	private static final String UPDATE = "2";

	private static final String DELETE = "3";

	private static final String REL_ADD = "0";

	private static final String REL_UPDATE = "2";

	private static final String REL_DELETE = "1";

	private static final String ResultCode_SUCESS = "0";

	private static final String ResultCode_FAIL = "-1";

	/**
	 * 产品同步处理接口(X平台同步过来的)
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map productInfoSys(DynamicDict dto) throws Exception {
		//boolean result = false;
		Map param = Const.getParam(dto);
		String xml = (String) param.get("xml");
		logger.info("productInfoSys==========>" + xml);
		ProductSysInfo sysInfo = parseProductXml(xml);
		Map product = sysInfo.getProduct();
		String OPFlag = Const.getStrValue(product, "OPFlag");

		List relList = sysInfo.getRel();
		List accNbrList = sysInfo.getAccNbr();
		List platformList = sysInfo.getPlatForm();
		List servAbilityList = sysInfo.getServAbility();
		List systemList = sysInfo.getSystems();
		
		param.put("Product", product);
		param.put("systemList", systemList);
		param.put("platformList", platformList);
		param.put("relList", relList);
		param.put("accNbrList", accNbrList);
		param.put("servAbilityList", servAbilityList);
		dto.setValueByName(Const.ACTION_PARAMETER, param);

		String ret=null;
		String product_id=null;
		try {
			if (ADD.equals(OPFlag)) {
				ret= this.insertProduct(dto);
				product_id=Const.getStrValue(product, "product_id");
			} else if (DELETE.equals(OPFlag)) {
				cancelProductByProductNbr(dto);
				
				StringUtil su = StringUtil.getInstance();
				ret=su.getVsopResponse("ProductSyncToVSOPResp", 
						 Const.getStrValue(product, "StreamingNo"),
				   		 ResultCode_SUCESS,
				   		 "同步成功", 
				   		 null);
				//再查找一下productId，为更新缓存使用，实为无奈之举。。
				try{
					String product_nbr = Const.getStrValue(product, "product_nbr");
					product_id= getProductIdByProductNbr(product_nbr);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			} else if (UPDATE.equals(OPFlag)) {
				ret= this.updateProduct(dto);
				product_id=Const.getStrValue(product, "product_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
		Map map=new HashMap();
		map.put("responseXml", ret);
		map.put("product_id", product_id);
		return map;
	}
	/**
	 * 产品数据同步处理接口 （来自ISMP平台的数据同步调用）
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map productInfoSysIsmp(DynamicDict dto) throws Exception {

		Map param = Const.getParam(dto);
		String xml = (String) param.get("xml");
		
		logger.info("productInfoSysIsmp===========>" + xml);
		
		ProductSysInfo sysInfo = parseProductXmlIsmp(xml);
		// 判断操作类型
		Map product = sysInfo.getProduct();
		/*
		 * 1：增加 2：修改 3：删除(注销)
		 */
		String OPFlag = Const.getStrValue(product, "OPFlag");

		List relList = sysInfo.getRel();
		List accNbrList = sysInfo.getAccNbr();
		List platformList = sysInfo.getPlatForm();
		List servAbilityList = sysInfo.getServAbility();
		List systemList = sysInfo.getSystems();
		param.put("Product", product);
		param.put("systemList", systemList);
		param.put("platformList", platformList);
		param.put("relList", relList);
		param.put("accNbrList", accNbrList);
		param.put("servAbilityList", servAbilityList);
		param.put("ismpFlag", "ISMP");//标志位，在与X平台共用的insertProduct,updateProduct方法中区别是ISMP在调用
		dto.setValueByName(Const.ACTION_PARAMETER, param);
		
		String ret=null;
		String product_id=null;
		try {
			if (ADD.equals(OPFlag)) {
				ret= this.insertProduct(dto);
				product_id=Const.getStrValue(product, "product_id");
				
				insertInfProdToOCS(dto, ADD);//ISMP往VSOP同步增值产品时往表INF_PROD_TO_OCS加记录,2010-10-11 qin.guoquan
				
			} else if (DELETE.equals(OPFlag)) {
				cancelProductByProductNbr(dto);
				StringUtil su = StringUtil.getInstance();
				ret= su.getVsopResponse("ProductSyncFromISMPResp", 
						 Const.getStrValue(product, "StreamingNo"),
				   		 ResultCode_SUCESS,
				   		 "同步成功", 
				   		 null);
				//再查找一下productId，为更新缓存使用，实为无奈之举。。
				try{
					String product_nbr = Const.getStrValue(product, "product_nbr");
					product_id= getProductIdByProductNbr(product_nbr);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				insertInfProdToOCS(dto, DELETE);//ISMP往VSOP同步增值产品时往表INF_PROD_TO_OCS加记录,2010-10-11 qin.guoquan
				
			} else if (UPDATE.equals(OPFlag)) {
				ret= this.updateProduct(dto);
				product_id=Const.getStrValue(product, "product_id");
				
				insertInfProdToOCS(dto, UPDATE);//ISMP往VSOP同步增值产品时往表INF_PROD_TO_OCS加记录,2010-10-11 qin.guoquan
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		Map map=new HashMap();
		map.put("responseXml", ret);
		map.put("product_id", product_id);
		return map;
	}
	
	public String updateProduct(DynamicDict dto) throws Exception {
		
		StringUtil su = StringUtil.getInstance();
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");
		Object ismp = param.get("ismpFlag");
		
		String productNbr = Const.getStrValue(Product, "product_nbr");
		String system_code = Const.getStrValue(Product, "system_code");
		List systemList = (List) param.get("systemList");
		List platformList = (List) param.get("platformList");
		List relList = (List) param.get("relList");
		List accNbrList = (List) param.get("accNbrList");
		List servAbilityList = (List) param.get("servAbilityList");

		ProductDAO dao = new ProductDAO();
		String productId = getProductIdByProductNbr(productNbr);
		if (null == productId || "".equals(productId)) {//修改时，根据用户输入的编码查找的产品不存在时则退出。
			
			return su.getVsopResponse((null != ismp && "ISMP".equals(ismp.toString().trim())) ? "ProductSyncFromISMPResp" : "ProductSyncToVSOPResp", 
					Const.getStrValue(Product, "StreamingNo"), ResultCode_FAIL,
			   		 "同步失败,不存在要修改的编码为 " + productNbr + " 的增值产品.", null);
		}
		//boolean existsRow = dao.checkExistsRow(productNbr, system_code, productId);
		//if (existsRow) return false;

		Product.put("state_date", DateUtil.getFormatedDateTime());
		//Map keyCondMap = Const.getMapForTargetStr(Product, productId);
		Product.put("product_id", productId);
		Product.put("product_code", productNbr);
		Map keyCondMap = new HashMap();
		keyCondMap.put("product_id", productId);
		boolean result = dao.updateByKey(Product, keyCondMap);
		
		// 2.判断是否存在产品关系，有，则根据操作类型进行处理
		if (relList != null && !relList.isEmpty()) {
			Iterator relIterator = relList.iterator();
			while (relIterator.hasNext()) {
				Map relMap = (Map) relIterator.next();
				String OPType = Const.getStrValue(relMap, "OPType");
				if (REL_ADD.equals(OPType)) {
					relMap.put("product_id", productId);
					param.put("ProductRelation", relMap);
					dto.setValueByName(Const.ACTION_PARAMETER, param);
					this.insertProductRelation(dto);
				} else if (REL_DELETE.equals(OPType)) {
					List para = new ArrayList();// p.pro_product_id=?
												// and
												// p.relation_type_cd=?
												// and p.product_id=?
					para.add(Const.getStrValue(relMap, "pro_product_id"));
					para.add(Const.getStrValue(relMap, "relation_type_cd"));
					para.add(productId);
					new ProductRelationDAO().delByInterface(para);
				}
			}
		}
		// 3.删除除产品类型外：业务平台、业务能力、接入号码等列表数据
		if (accNbrList != null && !accNbrList.isEmpty()) {
			List resultParam = new ArrayList();
			ProdAccNbrTypeDAO pa = new ProdAccNbrTypeDAO();
			pa.batchDelete(productId);
			Iterator it = accNbrList.iterator();
			while (it.hasNext()) {
				// prod_acc_nbr_type_id, acc_nbr_type_cd, product_id,
				// access_no, feature_no, match_mode
				Map m = (Map) it.next();
				List accNbr = new ArrayList();
				accNbr.add(getSequence("seq_prod_acc_nbr_type_id"));
				accNbr.add(Const.getStrValue(m, "acc_nbr_type_cd"));
				accNbr.add(productId);
				accNbr.add(Const.getStrValue(m, "access_no"));
				accNbr.add(Const.getStrValue(m, "feature_no"));
				accNbr.add(Const.getStrValue(m, "match_mode"));

				resultParam.add(accNbr);
			}
			pa.batchInsert(resultParam);
		}

		if (platformList != null && !platformList.isEmpty()) {//添加增值产品对应的平台信息
			
			ProdPlatformDAO platDao = new ProdPlatformDAO();
			platDao.batchDelete(productId);
			for (int i = 0; i < platformList.size(); i++) {
				Map platform = (Map) platformList.get(i);
				Map platformMap = new HashMap();
				String platformId = Const.getStrValue(platform, "platform_id");
				platformMap.put("prod_platform_id",getSequence("SEQ_PROD_PLATFORM"));
				platformMap.put("product_id", productId);
				platformMap.put("platform_id", platformId);
				platDao.insert(platformMap);
			}
		}

		if (servAbilityList != null && !servAbilityList.isEmpty()) {//添加增值产品对应的业务能力信息
			// del
			List resultPara = new ArrayList();
			ProductServAbilityRelDAO pr = new ProductServAbilityRelDAO();
			pr.batchDelete(productId);
			Iterator it = servAbilityList.iterator();
			while (it.hasNext()) {
				Map m = (Map) it.next();
				List servList = new ArrayList();// product_service_ability_rel_id,
												// product_id,
												// service_code,
												// rel_type
				servList.add(getSequence("seq_service_ability_rel_id"));
				servList.add(productId);
				servList.add(Const.getStrValue(m, "service_code"));
				servList.add("0");

				resultPara.add(servList);
			}
			pr.batchInsert(resultPara);
		}
		
		if(null != systemList){
			ProductSystemInfoDAO sysDao = new ProductSystemInfoDAO();  //添加增值产品对应的系统平台信息
			sysDao.batchDelete(productId);
			for (int i = 0; i < systemList.size(); i++) {
				Map platSysMap = (Map) systemList.get(i);
				Map sysMap = new HashMap();
				sysMap.put("partner_system_info_id", getSequence("SEQ_PRODUCT_SYSTEM_INFO"));
				sysMap.put("product_id", productId);
				sysMap.put("system_code", platSysMap.get("system_id"));
				sysMap.put("create_date", DateUtil.getFormatedDateTime());
				sysMap.put("state","0");
				sysMap.put("state_date", DateUtil.getFormatedDateTime());
				sysDao.insert(sysMap);
			}
		}
		
		//ISMP同步时，同时修改相对应的销售品(“订购”时同时生成的基础销售品)
		if (null != ismp && "ISMP".equals(ismp.toString().trim()) && result == true) {
			//要同时修改的销售品id就是同步的产品id ==== productId
			Map ProdOffParam = new HashMap();
			ProdOffParam.put("prod_offer_id", productId);
			ProdOffParam.put("state_date", DateUtil.getFormatedDateTime());
			ProdOffParam.put("prod_offer_name", Const.getStrValue(Product, "product_name"));
			ProdOffParam.put("scope", Const.getStrValue(Product, "scope"));
			ProdOffParam.put("offer_nbr", Const.getStrValue(Product, "product_nbr"));
			ProdOffParam.put("package_host", system_code);//要改为systemId
			ProdOffParam.put("state", Const.getStrValue(Product, "product_state_cd"));
			ProdOffParam.put("chargingpolicy_cn", Const.getStrValue(Product, "charging_policy_cn"));
			
			Map keyMap = new HashMap();
			keyMap.put("prod_offer_id", productId);
			
			ProdOffDAO dao1 = new ProdOffDAO();
			dao1.updateByKey(ProdOffParam, keyMap);
		}

		return su.getVsopResponse((null != ismp && "ISMP".equals(ismp.toString().trim())) ? "ProductSyncFromISMPResp" : "ProductSyncToVSOPResp", 
				Const.getStrValue(Product, "StreamingNo"), ResultCode_SUCESS,
		   		 "同步成功", null);
	}
	public boolean deleteProductById(DynamicDict dto) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		ProductDAO dao = new ProductDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}
	
	/**
	 * 同步时删除产品的操作，把产品状态改为注销
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean cancelProductByProductNbr(DynamicDict dto) throws Exception {
		
		Map param = Const.getParam(dto);
		Map product = (Map) param.get("Product");
		
		String product_nbr = Const.getStrValue(product, "product_nbr");
		List list = new ArrayList();
		list.add("4");//4为注销
		list.add(product_nbr);
		ProductDAO dao = new ProductDAO();
		boolean r = false;
		try {
			r = dao.update(dao.getCalcelSql(), list);
			
			
			
		} catch (Exception e) {
			Log.info("删除失败!", e);
			e.printStackTrace();
		}
		return r;
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
		return result;
	}
	
	private String insertProduct(DynamicDict dto) throws Exception {
		
		StringUtil su = StringUtil.getInstance();
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");
		Object ismp = param.get("ismpFlag");
		
		String product_nbr = Const.getStrValue(Product, "product_nbr");
		String system_code = Const.getStrValue(Product, "system_code");
		List systemList = (List) param.get("systemList");
		List platforms = (List) param.get("platformList");
		List relList = (List) param.get("relList");
		List servAbilityList = (List) param.get("servAbilityList");
		List accNbrList = (List) param.get("accNbrList");
		
		ProductDAO dao = new ProductDAO();
		boolean existsRow = dao.checkExistsRow(product_nbr, system_code, null);
		//该验证页面会做，现在防止测试的人乱按。
		if (existsRow) {
			return su.getVsopResponse((null != ismp && "ISMP".equals(ismp.toString().trim())) ? "ProductSyncFromISMPResp" : "ProductSyncToVSOPResp", 
					Const.getStrValue(Product, "StreamingNo"), ResultCode_FAIL,
			   		 "同步失败,已存在编码为 " + product_nbr + " 的增值产品,不允许重复订购.", null);
		}
		
		String productId = getSequence("seq_product_id");
		Product.put("product_id", productId);
		//Product.put("product_state_cd", "0");//正常状态
		Product.put("product_code", product_nbr);//product_code的取值和产品编码一样
		Product.put("create_date", DateUtil.getFormatedDateTime());
		Product.put("state_date", DateUtil.getFormatedDateTime());
		
		if(null != platforms){
			ProdPlatformDAO platDao = new ProdPlatformDAO(); //添加增值产品对应的平台信息
			platDao.batchDelete(productId);
			for (int i = 0; i < platforms.size(); i++) {
				Map platform = (Map) platforms.get(i);
				Map platformMap = new HashMap();
				String platformId = Const.getStrValue(platform, "platform_id");
				platformMap.put("prod_platform_id",getSequence("SEQ_PROD_PLATFORM"));
				platformMap.put("product_id", productId);
				platformMap.put("platform_id", platformId);
				platDao.insert(platformMap);
			}
		}
		if(null != systemList){
			ProductSystemInfoDAO sysDao = new ProductSystemInfoDAO();  //添加增值产品对应的系统平台信息
			sysDao.batchDelete(productId);
			for (int i = 0; i < systemList.size(); i++) {
				Map platSysMap = (Map) systemList.get(i);
				Map sysMap = new HashMap();
				sysMap.put("partner_system_info_id", getSequence("SEQ_PRODUCT_SYSTEM_INFO"));
				sysMap.put("product_id", productId);
				sysMap.put("system_code", platSysMap.get("system_id"));
				sysMap.put("create_date", DateUtil.getFormatedDateTime());
				sysMap.put("state","0");
				sysMap.put("state_date", DateUtil.getFormatedDateTime());
				sysDao.insert(sysMap);
			}
		}
		
		if (relList != null && !relList.isEmpty()) {//添加增值产品对应的产品关系信息
			Iterator relIterator = relList.iterator();
			while (relIterator.hasNext()) {
				Map relMap = (Map) relIterator.next();
				relMap.put("product_id", productId);
				String OPType = Const.getStrValue(relMap, "OPType");
				if (REL_ADD.equals(OPType)) {//订购增值产品时，当产品关系选的是非订购操作时，不做任何其他操作。
					param.put("ProductRelation", relMap);
					dto.setValueByName(Const.ACTION_PARAMETER, param);
					this.insertProductRelation(dto);
				} 
			}
		}
		
		// 3.删除除产品类型外：业务平台、业务能力、接入号码等列表数据
		if (accNbrList != null && !accNbrList.isEmpty()) {
			List resultParam = new ArrayList();
			ProdAccNbrTypeDAO pa = new ProdAccNbrTypeDAO();
			pa.batchDelete(productId);
			Iterator it = accNbrList.iterator();
			while (it.hasNext()) {
				// prod_acc_nbr_type_id, acc_nbr_type_cd, product_id,
				// access_no, feature_no, match_mode
				Map m = (Map) it.next();
				List accNbr = new ArrayList();
				accNbr.add(getSequence("seq_prod_acc_nbr_type_id"));
				accNbr.add(Const.getStrValue(m, "acc_nbr_type_cd"));
				accNbr.add(productId);
				accNbr.add(Const.getStrValue(m, "access_no"));
				accNbr.add(Const.getStrValue(m, "feature_no"));
				accNbr.add(Const.getStrValue(m, "match_mode"));

				resultParam.add(accNbr);
			}
			pa.batchInsert(resultParam);
		}		
		
		if (servAbilityList != null && !servAbilityList.isEmpty()) { //添加增值产品对应的业务能力信息
			List resultPara = new ArrayList();
			ProductServAbilityRelDAO d = new ProductServAbilityRelDAO();
			d.batchDelete(productId);
			Iterator it = servAbilityList.iterator();
			while (it.hasNext()) {
				Map m = (Map) it.next();
				List servList = new ArrayList();
				servList.add(getSequence("seq_service_ability_rel_id"));
				servList.add(productId);
				servList.add(Const.getStrValue(m, "service_code"));
				servList.add("0");
				resultPara.add(servList);
			}
			d.batchInsert(resultPara);
		}
		
		boolean result = dao.insert(Product);
		
		//ISMP同步时需要同时生成基础销售品(产品插入成功才生成基础销售品)
		if (null != ismp && "ISMP".equals(ismp.toString().trim()) && result == true) {
			//新增产品的时候，同时对库表PROD_OFFER新增一条记录，也就是同时新增销售品
			Map ProdOffParam = new HashMap();
			ProdOffParam.put("prod_offer_id", productId);
			ProdOffParam.put("fee_set_flag", "03");
			ProdOffParam.put("prod_offer_sub_type", "0");
			ProdOffParam.put("state_date", DateUtil.getFormatedDateTime());
			ProdOffParam.put("state", Const.getStrValue(Product, "product_state_cd"));
			ProdOffParam.put("offer_nbr", product_nbr);
			ProdOffParam.put("prod_offer_name", Const.getStrValue(Product, "product_name"));
			ProdOffParam.put("scope", Const.getStrValue(Product, "scope"));
			ProdOffParam.put("package_host", system_code);//要改为systemId
			ProdOffParam.put("chargingpolicy_cn", Const.getStrValue(Product, "charging_policy_cn"));
			
			ProdOffDAO dao1 = new ProdOffDAO();
			dao1.insert(ProdOffParam);
			
			//往库表PROD_OFFER_DETAIL_ROLE新增一条记录,主键用序列生成,销售品id和product_id都用增值产品的id. 
			Map ProdOffDetailParam = new HashMap();
			String prodOfferRoleCd = SeqUtil.getInst().getNext("SEQ_PROD_OFFER_ROLE_CD");
			ProdOffDetailParam.put("prod_offer_role_cd", prodOfferRoleCd);
			ProdOffDetailParam.put("prod_offer_id", productId);
			ProdOffDetailParam.put("product_id", productId);
			ProdOffDetaRoleDAO dao2 = new ProdOffDetaRoleDAO();
			dao2.insertForIsmp(ProdOffDetailParam);
			
			//往角色产品成员关系 ROLE_PROD_RELA 新增记录
			Map rprMap = new HashMap();
			rprMap.put("prod_offer_role_cd", prodOfferRoleCd);
			rprMap.put("product_id", productId);
			RoleProdRelaDao dao3 = new RoleProdRelaDao();
			dao3.insert(rprMap);
		}
		
		return su.getVsopResponse((null != ismp && "ISMP".equals(ismp.toString().trim())) ? "ProductSyncFromISMPResp" : "ProductSyncToVSOPResp", 
				Const.getStrValue(Product, "StreamingNo"), ResultCode_SUCESS,
		   		 "同步成功", null);
	}
	
	/**
	 * xml > 返回产品对象
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static ProductSysInfo parseProductXml(String xmlStr)
			throws DocumentException, Exception {
		
		ProductSysInfo sysInfo = new ProductSysInfo();
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		Element sessionBody = root.element("SessionBody");
		Element req = sessionBody.element("ProductSyncToVSOPReq");

		//String ProductID = req.element("ProductID").getStringValue();
		// 获取产品Map
		Map product = getProduct(req);
		// 业务平台 ArrayList
		List platformLst = getPlatFormList(req);
		
		//系统平台
		List systemList = getSystemList(req);
		// 接入号 List
		List accNbrList = getAccNbrList(req);

		// 业务能力 List
		List servAbilityLst = getServAbilityList(req);

		// 产品关系 List
		List refLst = getRefList(req);
		// 设置到Map返回
		sysInfo.setProduct(product);
		sysInfo.setAccNbr(accNbrList);
		sysInfo.setPlatForm(platformLst);
		sysInfo.setRel(refLst);
		sysInfo.setServAbility(servAbilityLst);
		sysInfo.setSystems(systemList);
		return sysInfo;
	}
	/**
	 * xml > 返回产品对象 ISMP发起调用报文解析
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static ProductSysInfo parseProductXmlIsmp(String xmlStr)
			throws DocumentException, Exception {
		ProductSysInfo sysInfo = new ProductSysInfo();

		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		Element sessionBody = root.element("SessionBody");
		Element req = sessionBody.element("ProductSyncFromISMPReq");

		// 获取产品Map
		Map product = getProductIsmp(req);
		// 业务平台 ArrayList
		List platformLst = getPlatFormList(req);

		// 接入号 List
		List accNbrList = getAccNbrList(req);

		// 业务能力 List
		List servAbilityLst = getServAbilityList(req);

		// 产品关系 List
		List refLst = getRefList(req);
		
		//系统平台
		List systemList = getSystemList(req);
		// 设置到Map返回
		sysInfo.setProduct(product);
		sysInfo.setAccNbr(accNbrList);
		sysInfo.setPlatForm(platformLst);
		sysInfo.setRel(refLst);
		sysInfo.setServAbility(servAbilityLst);
		sysInfo.setSystems(systemList);
		return sysInfo;
	}
	/**
	 * 解释产品关系xml > Map
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static List getRefList(Element root)
			throws Exception {
		ArrayList refLst = new ArrayList();
		List ProdRelationElementLst = root.elements("ProdRelation");
		if (ProdRelationElementLst != null) {
			for (int i = 0; i < ProdRelationElementLst.size(); i++) {
				Element subElement = (Element) ProdRelationElementLst.get(i);
				String OPType = subElement.element("OPType").getStringValue();
				String RelationType = subElement.element("RelationType")
						.getStringValue();
				String RProductID = subElement.element("RProductID").getStringValue();

				Map refMap = new HashMap();
				refMap.put("relation_type_cd", RelationType);
				refMap.put("pro_product_id", getProductIdByProductNbr(RProductID));
				refMap.put("OPType", OPType);
				refMap.put("state_cd", "00A");
				refMap.put("create_date", new Date().toString());
				refMap.put("state_date", new Date().toString());

				refLst.add(refMap);
			}
		}
		return refLst;
	}

	/**
	 * 解释接入号码xml > Map
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static List getAccNbrList(Element root)
			throws Exception {
		// 接入号 List
		ArrayList accNbrList = new ArrayList();
		List ProductOPCode = root.elements("ProductOPCode");
		if (ProductOPCode != null) {
			for (int i = 0; i < ProductOPCode.size(); i++) {
				Element subElement = (Element) ProductOPCode.get(i);
				String feature_no = subElement.element("FeatureStr").getStringValue();
				String access_no = subElement.element("AccessNO").getStringValue();
				String acc_nbr_type_cd = subElement.element("OPType").getStringValue();
				String match_mode = subElement.element("MatchMode").getStringValue();

				Map accNbr = new HashMap();
				accNbr.put("feature_no", feature_no);
				accNbr.put("access_no", access_no);
				accNbr.put("acc_nbr_type_cd", acc_nbr_type_cd);
				accNbr.put("match_mode", match_mode);

				accNbrList.add(accNbr);
			}
		}
		return accNbrList;
	}

	/**
	 * 解释业务能力xml > Map
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static List getServAbilityList(Element root)
			throws Exception {
		// 业务能力 List
		ArrayList servAbilityLst = new ArrayList();
		List ServiceCapabilityID = root.elements("ServiceCapabilityID");
		if (ServiceCapabilityID != null) {
			for (int i = 0; i < ServiceCapabilityID.size(); i++) {
				Element subElement = (Element) ServiceCapabilityID.get(i);
				Map servMap = new HashMap();
				String service_code = subElement.getStringValue();
				servMap.put("service_code", service_code);
				servAbilityLst.add(servMap);
			}
		}
		return servAbilityLst;
	}

	/**
	 * 解释业务平台xml > Map
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static List getPlatFormList(Element root)
			throws Exception {
		ArrayList platformLst = new ArrayList();
		List PlatForm = root.elements("PlatForm");
		if (PlatForm != null) {
			for (int i = 0; i < PlatForm.size(); i++) {
				Element subElement = (Element) PlatForm.get(i);
				//根据ALLOW_SYSTEM_CODE 过滤平台编码
				String allowSystemCode = DcSystemParamManager.getParameter("ALLOW_SYSTEM_CODE");
				String platform_id = subElement.getStringValue();
				if (null != allowSystemCode && !"".equals(allowSystemCode)) {
					if (allowSystemCode.indexOf(platform_id) > -1) {
						Map pf = new HashMap();
						pf.put("platform_id", platform_id);
						platformLst.add(pf);
					}
				} else {
					Map pf = new HashMap();
					pf.put("platform_id", platform_id);
					platformLst.add(pf);
				}
			}
		}
		return platformLst;
	}
	private static List getSystemList(Element root)
	throws Exception {
		List PlatForm = root.elements("PlatForm");
		String plats ="";
		if (null != PlatForm && !PlatForm.isEmpty()) {
			for (int i = 0; i < PlatForm.size(); i++) {
				Element subElement = (Element) PlatForm.get(i);
				String platId = subElement.getStringValue();
				//根据ALLOW_SYSTEM_CODE 过滤平台编码
				String allowSystemCode = DcSystemParamManager.getParameter("ALLOW_SYSTEM_CODE");
				if (null != allowSystemCode && !"".equals(allowSystemCode)) {
					if (allowSystemCode.indexOf(platId) > -1) {
						plats += platId + ",";
					}
				} else {
					plats += platId + ",";
				}
			}
		}
		List sysTemLst = new ArrayList();
		if (null !=plats && !"".equals(plats)) {
			ProductDAO dao = new ProductDAO();
			String platIds = plats.substring(0, plats.length()-1);
			String sql = "select PLATFORM_ID,SYSTEM_ID from PLATFORM_SYSTEM_MAP where PLATFORM_ID in ("+platIds+")";
			if (null != platIds && 0 != platIds.trim().length()) sysTemLst = dao.findBySql(sql);
		}
	 return sysTemLst;
}
	/**
	 * 解释产品xml > Map 来自ISMP平台调用报文解析
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static Map getProductIsmp(Element root) throws Exception {//这里要一个个对应R8.5搞翻来
		
		String StreamingNo = XMLUtils.getElementStringValue(root,"StreamingNo");
		String TimeStamp = XMLUtils.getElementStringValue(root,"TimeStamp");
		String SystemId = root.element("SystemId").getStringValue();
		String SPID = XMLUtils.getElementStringValue(root,"SPID");
		String OPFlag = XMLUtils.getElementStringValue(root,"OPFlag");
		String ProductID = XMLUtils.getElementStringValue(root,"ProductID");
		String PnameCN = XMLUtils.getElementStringValue(root,"PnameCN");
		String PnameEN = XMLUtils.getElementStringValue(root,"PnameEN");
		String PdescriptionCN = XMLUtils.getElementStringValue(root,"PdescriptionCN");
		String PdescriptionEn = XMLUtils.getElementStringValue(root,"PdescriptionEn");
		String Status =  XMLUtils.getElementStringValue(root,"Status");
		String ChargingPolicyCN = XMLUtils.getElementStringValue(root,"ChargingPolicyCN");
		String ChargingPolicyID = XMLUtils.getElementStringValue(root,"ChargingPolicyID");
		String SubOption = XMLUtils.getElementStringValue(root,"SubOption");
		String Present = XMLUtils.getElementStringValue(root,"Present");
		String CorpOnly = XMLUtils.getElementStringValue(root,"CorpOnly");
		String PackageOnly = XMLUtils.getElementStringValue(root,"PackageOnly");
		String SettlementCycle = XMLUtils.getElementStringValue(root,"SettlementCycle");
		String SettlementPayType = XMLUtils.getElementStringValue(root,"SettlementPayType");
		String SettlementPercent = XMLUtils.getElementStringValue(root,"SettlementPercent");
		String Scope = XMLUtils.getElementStringValue(root,"Scope");
		String ProductHost = XMLUtils.getElementStringValue(root,"ProductHost");


		Map product = new HashMap();
		// 产品设置
		product.put("StreamingNo", StreamingNo);
		product.put("TimeStamp", TimeStamp);
		product.put("product_provider_id", new PartnerDAO().getKey(SPID));
		product.put("OPFlag", OPFlag);
		product.put("product_name", PnameCN);
		product.put("PnameEN", PnameEN);
		product.put("product_desc", PdescriptionCN);
		product.put("PdescriptionEn", PdescriptionEn);
		
		product.put("charging_policy_cn", ChargingPolicyCN);
		product.put("charging_policy_id", ChargingPolicyID);
		product.put("sub_option", SubOption);
		product.put("present", Present);
		product.put("corp_only", CorpOnly);
		product.put("package_only", PackageOnly);
		product.put("settlement_cycle", SettlementCycle);
		product.put("settlement_paytype", SettlementPayType);
		product.put("settlement_percent", SettlementPercent);
		
		product.put("product_state_cd", Status);
		product.put("scope", Scope);
		product.put("order_host", ProductHost);
		product.put("system_code", SystemId);
		
		//默认是写增值产品(1)
		product.put("prod_func_type", "1");
		product.put("product_nbr", ProductID);//ProductID为产品编码
		return product;
	}
	/**
	 * 解释产品xml > 来自X平台
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static Map getProduct(Element root)
			throws Exception {
		String StreamingNo = root.element("StreamingNo").getStringValue();
		String TimeStamp = root.element("TimeStamp").getStringValue();
		String SystemId = root.element("SystemId").getStringValue();
		String SPID = root.element("SPID").getStringValue();
		String OPFlag = root.element("OPFlag").getStringValue();
		String ProductType = root.element("ProductType").getStringValue();//这个type暂时没什么用
		String ProductID = root.element("ProductID").getStringValue();
		String PnameCN = root.element("PnameCN").getStringValue();
		String PnameEN = root.element("PnameEN").getStringValue();
		String PdescriptionCN = root.element("PdescriptionCN").getStringValue();
		String PdescriptionEn = root.element("PdescriptionEn").getStringValue();
		String Status = root.element("Status").getStringValue();
		String Scope = root.element("Scope").getStringValue();
		String ProductHost = root.element("ProductHost").getStringValue();

		Map product = new HashMap();
		product.put("StreamingNo", StreamingNo);
		product.put("TimeStamp", TimeStamp);
		product.put("system_code", SystemId);
		product.put("product_provider_id", new PartnerDAO().getKey(SPID));
		product.put("OPFlag", OPFlag);
		product.put("ProductType", ProductType);
		product.put("product_nbr", ProductID);
		product.put("product_name", PnameCN);
		product.put("PnameEN", PnameEN);
		product.put("product_desc", PdescriptionCN);
		product.put("PdescriptionEn", PdescriptionEn);
		product.put("product_state_cd", Status);
		product.put("scope", Scope);
		product.put("order_host", ProductHost);

		//默认是写增值产品(1)
		product.put("prod_func_type", "1");

		return product;
	}
	/**
	 * 获取序列
	 * @param sequenceCode
	 * @return
	 */
	private String getSequence(String sequenceCode) {
		SequenceManageDAOImpl sequenceManageDAO = new SequenceManageDAOImpl();
		return sequenceManageDAO.getNextSequenceWithDB(sequenceCode,
				JNDINames.VSOP_DATASOURCE);
	}
	
	/**
	 * 根据产品编码获取产品ID
	 * @param productNbr
	 * @return
	 * @throws Exception
	 */
	private static String getProductIdByProductNbr(String productNbr) throws Exception {
		String productId = "";
		if (null != productNbr) {
			ProductDAO dao = new ProductDAO();
			List l = new ArrayList();
			l.add(productNbr);
			List f = dao.findBySql(dao.getProductIdByProductNbr(), l);
			if (null != f && !f.isEmpty()) {
				productId = ((HashMap) f.get(0)).get("product_id").toString();
			}
		}
		return productId;
	}
	public boolean ismpServiceSync(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		IsmpServiceVo serviceVo = (IsmpServiceVo) param.get("serviceVo");
		IsmpSyncDao dao = new IsmpSyncDao();
		dao.deleteServiceAblRel(serviceVo);
		dao.deleteService(serviceVo);
		dao.addService(serviceVo);
		return true;
	}
	
	public List getIsmpServiceCapabilityIdList(DynamicDict dto) throws Exception{
		List ret = null;
		Map param = Const.getParam(dto);
		String serviceId = (String) param.get("serviceId");
		IsmpSyncDao dao = new IsmpSyncDao();
		ret = dao.getIsmpServiceCapabilityIdList(serviceId);
		return ret;
	}
	public String getPNameCn(DynamicDict dto) throws Exception{
		String ret = "";
		Map param = Const.getParam(dto);
		String serviceId = (String) param.get("serviceId");
		IsmpSyncDao dao = new IsmpSyncDao();
		ret = dao.getPNameCn(serviceId);
		return ret;
	}
	

	/**
	 * ISMP往VSOP同步增值产品时对表INF_PROD_TO_OCS作插入操作  2010-10-11 added by qin.guoquan
	 * @param dto
	 * @throws Exception
	 */
	private void insertInfProdToOCS(DynamicDict dto, String opFlag) throws Exception {
		
		Log.info("PPMProductSysManager.insertInfProdToOCS start========================= ");
		
		Map param = Const.getParam(dto);
		ProductDAO pDao = new ProductDAO();
		Map ocsMap = new HashMap();
		Map product = (Map) param.get("Product");
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		
		ocsMap.put("inf_prod_to_ocs_id", seqDao.getNextSequence("SEQ_INF_PROD_TO_OCS_ID"));//序列值
		ocsMap.put("prod_msg", param.get("xml"));//ISMP往VSOP同步增值产品的请求报文
		ocsMap.put("prod_sub_type", "0");//产品类型为增值产品
		ocsMap.put("prod_code", Const.getStrValue(product, "product_nbr"));////产品编码
		ocsMap.put("op_flag", opFlag);//动作
		ocsMap.put("prod_system", Const.getStrValue(product, "system_code"));//来源系统
		ocsMap.put("create_date", DateUtil.getFormatedDateTime());//创建时间
		ocsMap.put("state", "U");//U待处理，D处理中，S处理成功，F处理失败
		ocsMap.put("state_date", DateUtil.getFormatedDateTime());//更新时间
		ocsMap.put("send_times", 1);//发送次数
		ocsMap.put("result_msg", "");//失败原因
		
		boolean result = pDao.insertInfProdToOCS(ocsMap);
		
		Log.info("PPMProductSysManager.insertInfProdToOCS end========================= " + result);
	}	
	
}













