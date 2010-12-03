package com.ztesoft.vsop.webservice.server;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.util.Log;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.crm.product.dao.ProdOffDAO;
import com.ztesoft.crm.product.dao.ProdOffDetaRoleDAO;
import com.ztesoft.crm.product.dao.ProdOffRelDAO;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.product.dao.ProductDAO;
import com.ztesoft.vsop.product.vo.ProdOffInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * cooltan modify on 20100604 增加增量刷新缓存操作
 *
 *
 */
public class PPMOfferSynManager  extends DictAction{
	private static final String ADD = "1";

	private static final String UPDATE = "2";

	private static final String DELETE = "3";

	private static final String ResultCode_SUCESS = "0";

	private static final String ResultCode_FAIL = "-1";
	
	private static final String REL_ADD = "0";

	private static final String REL_DELETE = "1";
	
	public static final  int SOURCE_SYSTEM_X = 0 ;//X平台
	public static final  int SOURCE_SYSTEM_PPM = 1 ;//PPM系统
	
	/**
	 * X平台销售品同步处理接口
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map prodOffInfoSysn(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		String xml = (String) param.get("xml");
		String sourceSystemStr = Const.getStrValue(param, "sourceSystem");
		int sourceSystem = Integer.parseInt(sourceSystemStr) ;//标识系统来源，不同的系统xml窜不一样
		System.out.println("reqXml="+xml);
		ProdOffInfo sysInfo = parseProdOffXml(xml , sourceSystem);
		Map prodOffer = sysInfo.getProdOff();
		
		boolean b = dealDatas(sysInfo, dto);
		StringUtil su = StringUtil.getInstance();
		
		String responseXml=null;
		Map map=new HashMap();
		if (b) {
			responseXml= su.getVsopResponse("ProdOfferSyncToVSOPResp", 
				 Const.getStrValue(prodOffer, "StreamingNo"),
		   		 ResultCode_SUCESS,
		   		 "同步成功", 
		   		 null);
		} else {
			responseXml= su.getVsopResponse("ProdOfferSyncToVSOPResp", 
				 Const.getStrValue(prodOffer, "StreamingNo"),
		   		 ResultCode_FAIL,
		   		 "同步失败!", 
		   		 null);
		}
		
		//返回map 包括返回xml与offerid
		String OPFlag = Const.getStrValue(prodOffer, "OPFlag");
		String prod_offer_id=null;
		if (ADD.equals(OPFlag)) {
			Map tmpParam = Const.getParam(dto) ;
			Map ProdOff = (Map) tmpParam.get("ProdOff");
			prod_offer_id=(String) ProdOff.get("prod_offer_id");
		} else if (DELETE.equals(OPFlag)) {
			prod_offer_id=(String)prodOffer.get("prod_offer_id");
		} else if (UPDATE.equals(OPFlag)) {
			prod_offer_id=(String)prodOffer.get("prod_offer_id");
		}
		map.put("responseXml", responseXml);
		map.put("prod_offer_id", prod_offer_id);
		return map;
		
	}
	/**
	 * ISMP销售品同步处理接口
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map prodOffInfoSysnFromIsmp(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		String xml = (String) param.get("xml");
		String sourceSystemStr = Const.getStrValue(param, "sourceSystem");
		int sourceSystem = Integer.parseInt(sourceSystemStr) ;//标识系统来源，不同的系统xml窜不一样
		ProdOffInfo sysInfo = parseProdOffXmlIsmp(xml , sourceSystem);
		Map prodOffer = sysInfo.getProdOff();
		
		boolean b = dealDatas(sysInfo, dto);
		String responseXml=null;
		Map map=new HashMap();
		if (b) {
			StringUtil su = StringUtil.getInstance();
			responseXml= su.getVsopResponse("PackageSyncFromISMPResp", 
				 Const.getStrValue(prodOffer, "StreamingNo"),
		   		 ResultCode_SUCESS,
		   		 "同步成功", 
		   		 null);
		} else {
			StringUtil su = StringUtil.getInstance();
			responseXml= su.getVsopResponse("PackageSyncFromISMPResp", 
				 Const.getStrValue(prodOffer, "StreamingNo"),
		   		 ResultCode_FAIL,
		   		 "同步失败!", 
		   		 null);
		}
		
		//返回map 包括返回xml与offerid
		String OPFlag = Const.getStrValue(prodOffer, "OPFlag");
		String prod_offer_id=null;
		if (ADD.equals(OPFlag)) {
			Map tmpParam = Const.getParam(dto) ;
			Map ProdOff = (Map) tmpParam.get("ProdOff");
			prod_offer_id=(String) ProdOff.get("prod_offer_id");
		} else if (DELETE.equals(OPFlag)) {
			prod_offer_id=(String)prodOffer.get("prod_offer_id");
		} else if (UPDATE.equals(OPFlag)) {
			prod_offer_id=(String)prodOffer.get("prod_offer_id");
		}
		map.put("responseXml", responseXml);
		map.put("prod_offer_id", prod_offer_id);
		return map;
		
	}
	
	/**
	 * X平台和ISMP的销售品统一处理.
	 * 
	 * @param sysInfo
	 * @param dto
	 * @throws Exception
	 */
	private boolean dealDatas(ProdOffInfo sysInfo, DynamicDict dto) throws Exception {
		boolean b = true;
		Map param = Const.getParam(dto);
		Map prodOffer = sysInfo.getProdOff();
		
		// 1：增加 2：修改 3：删除(注销)
		String OPFlag = Const.getStrValue(prodOffer, "OPFlag");
		String prod_offer_id = Const.getStrValue(prodOffer, "prod_offer_id");
		//根据销售品编码判断销售品是否已存在
		String pOfferId  =DcSystemParamManager.getInstance().getProdOfferIdByNbr(prod_offer_id);
		if(null != pOfferId && !"".equals(pOfferId)){
			return false;
		}
		List productList = sysInfo.getProductList() ;
		List relList = sysInfo.getRelList() ;
		try {
			if (ADD.equals(OPFlag)) {
				
				insertInfProdToOCS(dto, ADD);//ISMP往VSOP同步增值销售品时往表INF_PROD_TO_OCS加记录,2010-10-11 qin.guoquan
				
				// 插入销售品数据
				param.put("ProdOff", prodOffer);
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				String prodOfferId = this.insertProdOff(dto);
				//prodOfferId 为空，则新增销售品数据失败，很有可能是因为已经存在该销售品了。
				if("".equals(prodOfferId)) return false;
				
				
				// 插入PROD_OFFER_DETAIL_ROLE关系
				ProdOffDetaRoleDAO prodOfferDao = new ProdOffDetaRoleDAO();
				for (Iterator ite = productList.iterator(); ite.hasNext();) {
					Map product = (Map) ite.next();
					Map prodOffDetaRole = new HashMap();
					prodOffDetaRole.put("prod_offer_id", prodOfferId);
					prodOffDetaRole.put("product_id", product.get("product_id"));
					prodOfferDao.insertProdOffRel(prodOffDetaRole);
				}
				
				// 插入销售品关系
				ProdOffRelDAO prodOfferRelDao = new ProdOffRelDAO();
				for (Iterator iter = relList.iterator(); iter.hasNext();) {
					Map rel = (Map) iter.next();
					String aid = (String) rel.get("offer_a_id");
					if (aid == null || aid.equals("")) {	// 新增的销售品则用新的销售品ID
						aid = prodOfferId;
					}
					Map prodOffRel = new HashMap();
					prodOffRel.put("offer_a_id", aid);
					prodOffRel.put("offer_z_id", rel.get("offer_z_id"));
					prodOffRel.put("relation_type_id", rel.get("relation_type_id"));
					prodOfferRelDao.insert(prodOffRel) ;
				} 
				
			} else if (DELETE.equals(OPFlag)) {
				// 将销售品注销即可
				new ProdOffDAO().cancelProdOfferById(prod_offer_id);
				
				insertInfProdToOCS(dto, DELETE);//ISMP往VSOP同步增值销售品时往表INF_PROD_TO_OCS加记录,2010-10-11 qin.guoquan
				
			} else if (UPDATE.equals(OPFlag)) {
				
				insertInfProdToOCS(dto, UPDATE);//ISMP往VSOP同步增值销售品时往表INF_PROD_TO_OCS加记录,2010-10-11 qin.guoquan
				
				// 更新销售品信息
				param.put("ProdOff", prodOffer);
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				this.updateProdOff(dto);
				
				// 更新PROD_OFFER_DETAIL_ROLE关系
				// 先删除后插入数据
				ProdOffDetaRoleDAO prodOfferDao = new ProdOffDetaRoleDAO();
				Map prodOffDetaRole = new HashMap();
				prodOffDetaRole.put("prod_offer_id", prod_offer_id);
				prodOfferDao.deleteProdOffDetaRoleById(prodOffDetaRole);
				for (Iterator ite = productList.iterator(); ite.hasNext();) {
					Map product = (Map) ite.next();
					Map sprodOffDetaRole = new HashMap();
					sprodOffDetaRole.put("prod_offer_id", prod_offer_id);
					sprodOffDetaRole.put("product_id", product.get("product_id"));
					prodOfferDao.insertProdOffRel(sprodOffDetaRole);
				}
				
				// 更新销售品关系
				for (Iterator iter = relList.iterator(); iter.hasNext();) {
					Map relMap = (Map) iter.next();
					String OPType = Const.getStrValue(relMap, "OPType");
					if (REL_ADD.equals(OPType)) {
						param.put("ProdOffRel", relMap);
						dto.setValueByName(Const.ACTION_PARAMETER, param);
						this.insertProdOffRel(dto);
					} else if (REL_DELETE.equals(OPType)) {
						List para = new ArrayList();
						para.add(Const.getStrValue(relMap, "offer_z_id"));
						para.add(Const.getStrValue(relMap,"relation_type_id"));
						para.add(prod_offer_id);
						new ProdOffRelDAO().delByInterface(para);
					}
				}
			}
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
			throw e;
		}
		return b;
	}
	
	public boolean updateProdOff(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOff = (Map) param.get("ProdOff") ;
		String keyStr = "prod_offer_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOff,  keyStr) ;
		ProdOffDAO dao = new ProdOffDAO();
		boolean result = dao.updateByKey( ProdOff, keyCondMap );
		return result ;
	}
	
	public boolean insertProdOffRel(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffRel = (Map) param.get("ProdOffRel") ;
		
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.insert(ProdOffRel) ;
		
		return result ;
	}
	
	public boolean deleteProdOffRelById(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		return result ;
	}
	
	
	public boolean deleteProdOffById(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffDAO dao = new ProdOffDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		return result ;
	}
	
	public String insertProdOff(DynamicDict dto  ) throws Exception {
		ProdOffDAO dao = new ProdOffDAO();
		Map param = Const.getParam(dto) ;
		Map ProdOff = (Map) param.get("ProdOff");
		String offerCode = (String)ProdOff.get("offer_nbr");
		//检查是否已经存在该编码的销售品
		String offerId = dao.getKey("", offerCode );
		if(offerId != null && !"".equals(offerId)){
			return "";
		}
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOffId = seqDao.getNextSequence("SEQ_PROD_OFFER_ID");
		ProdOff.put("prod_offer_id", prodOffId);
		
		boolean b = dao.insert(ProdOff) ;
		if (b) {
			return prodOffId;
		} else {
			return "";
		}
	}

	private static String getProductOfferCode(Element root , int sourceSystem){
		return ( sourceSystem == SOURCE_SYSTEM_PPM||sourceSystem==SOURCE_SYSTEM_X )? root.element("ProductOfferID").getStringValue() : 
			root.element("PackageID").getStringValue()  ;
	}
	
	private static Map getProdOffer(Element root, String ProductOfferCode ,String SystemId , String prodOffId ,int sourceSystem ) throws Exception{
		return ( sourceSystem == SOURCE_SYSTEM_PPM )?
				getProdOffer(root, ProductOfferCode , SystemId , prodOffId)
				: getXProdOffer( root, ProductOfferCode , SystemId , prodOffId) ;
	}
	/**
	 * xml > 返回产品对象
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static ProdOffInfo parseProdOffXml(String xmlStr , int sourceSystem)
			throws DocumentException, Exception {
		ProdOffInfo synInfo = new ProdOffInfo();

		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		Element sessionBody = root.element("SessionBody");
		Element req = sessionBody.element("ProdOfferSyncToVSOPReq");
		String StreamingNo = req.element("StreamingNo").getStringValue();
		String SystemId = req.element("SystemId").getStringValue();
		String ProductOfferCode =getProductOfferCode( req ,  sourceSystem);
		String OPFlag = req.element("OPFlag").getStringValue();
		
		String prodOffId = new ProdOffDAO().getKey( SystemId ,  ProductOfferCode) ;
		// 获取产品Map
		Map prodOffer =getProdOffer( req,  ProductOfferCode , SystemId ,  prodOffId , sourceSystem ) ;
		prodOffer.put("OPFlag", OPFlag) ;
		prodOffer.put("StreamingNo", StreamingNo) ;
		
		// 产品组成列表 List
		List productList = getProductList( req,  prodOffId ,  SystemId );

		//销售品关系 List
		List relList = getRelList( req, prodOffId ,SystemId );
		
		// 设置到Map返回
		synInfo.setProdOff(prodOffer);
		synInfo.setProductList(productList);
		synInfo.setRelList(relList) ;
		return synInfo;
	}
	
	/**
	 *  返回产品对象 ISMP纯增值捆绑销售品
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	public static ProdOffInfo parseProdOffXmlIsmp(String xmlStr , int sourceSystem)
			throws DocumentException, Exception {
		ProdOffInfo synInfo = new ProdOffInfo();
		
		System.out.print("reqXml="+xmlStr);
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(xmlStr);
		Document doc = saxReader.read(reader);
		Element root = doc.getRootElement();
		Element sessionBody = root.element("SessionBody");
		Element req = sessionBody.element("PackageSyncFromISMPReq");
		String StreamingNo = req.element("StreamingNo").getStringValue();
		String SystemId = req.element("SystemId").getStringValue();
		String ProductOfferCode =req.element("PackageID").getStringValue();
		String OPFlag = req.element("OPFlag").getStringValue();
		
		String prodOffId = new ProdOffDAO().getKey( SystemId,  ProductOfferCode) ;
		// 获取产品Map
		Map prodOffer =getProdOfferIsmp(req, ProductOfferCode, SystemId, prodOffId,"1") ;
		prodOffer.put("OPFlag", OPFlag) ;
		prodOffer.put("StreamingNo", StreamingNo) ;
		
		// 产品组成列表 List
		List productList = getProductList(req, prodOffId, SystemId );

		//销售品关系 List
		List relList = getRelList(req, prodOffId, SystemId );
		
		// 设置到Map返回
		synInfo.setProdOff(prodOffer);
		synInfo.setProductList(productList);
		synInfo.setRelList(relList) ;
		return synInfo;
	}
	
	/**
	 * 解释业务能力xml > Map
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static List getRelList(Element root, String offerAId  , String systemId )
			throws Exception {
		ArrayList refList = new ArrayList();
		List ProdOfferRelation = root.elements("ProdOfferRelation");
		if (ProdOfferRelation != null) {
			for (int i = 0; i < ProdOfferRelation.size(); i++) {
				Element subElement = (Element) ProdOfferRelation.get(i);
				String RelationType = subElement.element("RelationType").getStringValue();
				String RProdOfferID = subElement.element("RProdOfferID").getStringValue();
				String OPType = subElement.element("OPType").getStringValue();
				Map relMap = new HashMap();
				relMap.put("OPType", OPType);
				relMap.put("offer_a_id", offerAId);
				relMap.put("offer_z_id", new ProdOffDAO().getKey(systemId, RProdOfferID));
				relMap.put("relation_type_id", RelationType);
				refList.add(relMap);
			}
		}
		return refList;
	}

	
	/**
	 * 组成产品列表xml > List
	 * @param root
	 * @param offerId
	 * @param systemId
	 * @return
	 * @throws Exception
	 */
	private static List getProductList(Element root, String offerId , String systemId )
			throws Exception {
		ArrayList platformLst = new ArrayList();
		List productNbrs = root.elements("ProductID");
		if (productNbrs != null) {
			for (int i = 0; i < productNbrs.size(); i++) {
				Element subElement = (Element) productNbrs.get(i);
				String product_nbr = subElement.getStringValue();
				Map pf = new HashMap();
				pf.put("product_id", new ProductDAO().getKey(systemId , product_nbr));
				pf.put("prod_offer_id", offerId);
				platformLst.add(pf);
			}
		}
		return platformLst;
	}
	
	
	/**
	 * 解释产品xml > Map
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static Map getXProdOffer(Element root, String offerCode ,String SystemId , String offerId)
			throws Exception {
		String ProductOfferType = root.element("ProductOfferType").getStringValue();
		
		String PNameCN = root.element("PNameCN").getStringValue();
		String PNameEN = root.element("PNameEN").getStringValue();
		
		String PDesCN = root.element("PDesCN").getStringValue();
		String PDesEn = root.element("PDesEn").getStringValue();
		
		String Status = root.element("Status").getStringValue();
		String ChargingPolicyCN = root.element("ChargingPolicyCN").getStringValue();
		String Scope = root.element("Scope").getStringValue();

		//x平台新增字段
		/*String SubOption = root.element("SubOption").getStringValue();
		String PresentOption = root.element("PresentOption").getStringValue();
		
		String CorpOnly = root.element("CorpOnly").getStringValue();
		String PackageHost = root.element("PackageHost").getStringValue();
		
		String ChargingPolicyID = root.element("ChargingPolicyID").getStringValue();
       */
		
		Map prodOff = new HashMap();
		prodOff.put("prod_offer_id", offerId);
		prodOff.put("offer_nbr", offerCode);
		prodOff.put("pname_cn", PNameCN);
		prodOff.put("pdes_en", PDesEn);
		prodOff.put("prod_offer_sub_type", ProductOfferType);
		prodOff.put("fee_set_flag", "03");//X平台送过来的，默认为03
		prodOff.put("package_host", SystemId);
		prodOff.put("pdes_cn", PDesCN);
		prodOff.put("state", Status);
		
		prodOff.put("pname_en", PNameEN);
		prodOff.put("chargingpolicy_cn", ChargingPolicyCN);
		prodOff.put("pricing_desc", ChargingPolicyCN);
		prodOff.put("scope", Scope);

		prodOff.put("prod_offer_name", PNameCN);
		prodOff.put("offer_desc", PDesCN);
		return prodOff;
	}
	
	/**
	 * 解释产品xml > Map
	 * @param root
	 * @param ProductID
	 * @return
	 * @throws Exception
	 */
	private static Map getProdOffer(Element root, String offerCode ,String SystemId , String offerId)
			throws Exception {
		String ProductOfferType = root.element("ProductOfferType").getStringValue();
		
		String PNameCN = root.element("PNameCN").getStringValue();
		String PNameEN = root.element("PNameEN").getStringValue();
		
		String PDesCN = root.element("PDesCN").getStringValue();
		String PDesEn = root.element("PDesEn").getStringValue();
		
		String Status = root.element("Status").getStringValue();
		String ChargingPolicyCN = root.element("ChargingPolicyCN").getStringValue();
		String Scope = root.element("Scope").getStringValue();
		
		Map prodOff = new HashMap();
		prodOff.put("prod_offer_id", offerId);
		prodOff.put("offer_nbr", offerCode);
		prodOff.put("pname_cn", PNameCN);
		prodOff.put("pdes_en", PDesEn);
		prodOff.put("prod_offer_sub_type", ProductOfferType);
		prodOff.put("fee_set_flag", "0".equals(ProductOfferType) ? "03" : "02");//0 纯增值，1捆绑类
		prodOff.put("package_host", SystemId);
		prodOff.put("pdes_cn", PDesCN);
		prodOff.put("state", Status);
		
		prodOff.put("pname_en", PNameEN);
		prodOff.put("chargingpolicy_cn", ChargingPolicyCN);
		prodOff.put("scope", Scope);

		prodOff.put("prod_offer_name", PNameCN);
		prodOff.put("offer_desc", PDesCN);
		return prodOff;
	}

	/**
	 * 解释产品xml > Map
	 * @param root
	 * @param ProductID
	 * @param offerType 销售品类型
	 * @return
	 * @throws Exception
	 */
	private static Map getProdOfferIsmp(Element root, String offerCode ,String SystemId , String offerId,String offerType)
			throws Exception {
		//String ProductOfferType = root.element("ProductOfferType").getStringValue();
		
		String PNameCN = root.element("PNameCN").getStringValue();
		String PNameEN = root.element("PNameEN").getStringValue();
		
		String PDesCN = root.element("PDesCN").getStringValue();
		String PDesEn = root.element("PDesEn").getStringValue();
		
		String Status = root.element("Status").getStringValue();
		String ChargingPolicyCN = root.element("ChargingPolicyCN").getStringValue();
		String ChargingPolicyID = root.element("ChargingPolicyID").getStringValue();
		String SubOption = root.element("SubOption").getStringValue();
		String CorpOnly = root.element("CorpOnly").getStringValue();
		String PresentOption = root.element("PresentOption").getStringValue();
//		String ProductID = root.element("ProductID").getStringValue();
		String Scope = root.element("Scope").getStringValue();
//		String ProdOfferRelation = root.element("ProdOfferRelation").getStringValue();
		
		Map prodOff = new HashMap();
		// 产品设置
		
		prodOff.put("prod_offer_id", offerId);
		prodOff.put("offer_nbr", offerCode);
		prodOff.put("pname_cn", PNameCN);
		prodOff.put("pdes_en", PDesEn);
		prodOff.put("prod_offer_sub_type", offerType);//先写死为单增值销售品
//		prodOff.put("fee_set_flag", "0".equals(ProductOfferType) ? "03" : "02");//0 纯增值，1捆绑类
		prodOff.put("fee_set_flag", "01");//先写死为基础销售品
		prodOff.put("package_host", SystemId);
		prodOff.put("pdes_cn", PDesCN);
		prodOff.put("state", Status);
		
		prodOff.put("pname_en", PNameEN);
		prodOff.put("chargingpolicy_cn", ChargingPolicyCN);
		prodOff.put("chargingpolicy_id", ChargingPolicyID);
		prodOff.put("sub_option", SubOption);
		prodOff.put("corp_only", CorpOnly);
		prodOff.put("present_option", PresentOption);
//		prodOff.put("ProductID", ProductID);
//		prodOff.put("ProdOfferRelation", ProdOfferRelation);
		prodOff.put("scope", Scope);

		prodOff.put("prod_offer_name", PNameCN);
		prodOff.put("offer_desc", PDesCN);
		return prodOff;
	}
	
	/**
	 * ISMP往VSOP同步增值产品时对表INF_PROD_TO_OCS作插入操作  2010-10-11 added by qin.guoquan
	 * @param dto
	 * @throws Exception
	 */
	private void insertInfProdToOCS(DynamicDict dto, String opFlag) throws Exception {
		
		Log.info("PPMOfferSynManager.insertInfProdToOCS start========================= ");
		
		Map param = Const.getParam(dto);
		ProdOffDAO pDao = new ProdOffDAO();
		Map ocsMap = new HashMap();
		Map product = (Map) param.get("Product");
		
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		
		ocsMap.put("inf_prod_to_ocs_id", seqDao.getNextSequence("SEQ_INF_PROD_TO_OCS_ID"));//序列值
		ocsMap.put("prod_msg", param.get("xml"));//ISMP往VSOP同步增值产品的请求报文
		ocsMap.put("prod_sub_type", "1");//产品类型为销售品
		ocsMap.put("prod_code", Const.getStrValue(product, "product_nbr"));////产品编码
		ocsMap.put("op_flag", opFlag);//动作
		ocsMap.put("prod_system", Const.getStrValue(product, "system_code"));//来源系统
		ocsMap.put("create_date", DateUtil.getFormatedDateTime());//创建时间
		ocsMap.put("state", "U");//U待处理，D处理中，S处理成功，F处理失败
		ocsMap.put("state_date", DateUtil.getFormatedDateTime());//更新时间
		ocsMap.put("send_times", 1);//发送次数
		ocsMap.put("result_msg", "");//失败原因
		
		boolean result = pDao.insertInfProdToOCS(ocsMap);
		
		Log.info("PPMOfferSynManager.insertInfProdToOCS end========================= " + result);
	}	
	
	
}
