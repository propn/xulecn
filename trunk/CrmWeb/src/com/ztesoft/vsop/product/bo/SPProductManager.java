package com.ztesoft.vsop.product.bo;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.ztesoft.crm.product.dao.ProdOffDAO;
import com.ztesoft.crm.product.dao.ProdOffDetaRoleDAO;
import com.ztesoft.crm.product.vo.ProdCatgItemVO;
import com.ztesoft.vsop.product.dao.PartnerDAO;
import com.ztesoft.vsop.product.dao.ProdAccNbrTypeDAO;
import com.ztesoft.vsop.product.dao.ProdPlatformDAO;
import com.ztesoft.vsop.product.dao.ProductDAO;
import com.ztesoft.vsop.product.dao.ProductRelationDAO;
import com.ztesoft.vsop.product.dao.ProductServAbilityRelDAO;
import com.ztesoft.vsop.product.dao.ProductSystemInfoDAO;
import com.ztesoft.vsop.product.dao.prodOffer.ProdOfferDAO;
import com.ztesoft.vsop.product.dao.prodOffer.ProdOfferDAOFactory;
import com.ztesoft.vsop.product.vo.ProductSysInfo;

public class SPProductManager extends DictAction {

	private static final String ADD = "1";

	private static final String UPDATE = "2";

	private static final String DELETE = "3";

	private static final String REL_ADD = "0";

	private static final String REL_UPDATE = "2";

	private static final String REL_DELETE = "1";

	private static final String ResultCode_SUCESS = "0";
	private static final String ResultCode_FAIL = "-1";

	public String productInfoSys(DynamicDict dto) throws Exception {
		boolean result = false;

		Map param = Const.getParam(dto);
		String xml = (String) param.get("xml");
		ProductSysInfo sysInfo = parseProductXml(xml);
		// �жϲ�������
		Map product = sysInfo.getProduct();
		/*
		 * 1������ 2���޸� 3��ɾ��(ע��)
		 */
		String OPFlag = Const.getStrValue(product, "OPFlag");
		String product_id = Const.getStrValue(product, "product_id");

		List relList = sysInfo.getRel();
		List accNbrList = sysInfo.getAccNbr();
		List platformList = sysInfo.getPlatForm();
		List servAbilityList = sysInfo.getServAbility();

		try {
			if (ADD.equals(OPFlag)) {
				// insert product ������
				param.put("Product", product);
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				result = this.insertProduct(dto, false);
			} else if (DELETE.equals(OPFlag)) {
				// delete product ������
				dto.setValueByName(Const.ACTION_PARAMETER, product);
				result = this.deleteProductById(dto);
			} else if (UPDATE.equals(OPFlag)) {
				// ��������Ƚϸ��ӣ����������
				// 1.���²�Ʒ��
				param.put("Product", product);
				dto.setValueByName(Const.ACTION_PARAMETER, param);
				this.updateProduct(dto);
				// 2.�ж��Ƿ���ڲ�Ʒ��ϵ���У�����ݲ������ͽ��д���
				if (relList != null && !relList.isEmpty()) {
					Iterator relIterator = relList.iterator();
					while (relIterator.hasNext()) {
						Map relMap = (Map) relIterator.next();
						/**
						 * 0������ 1��ɾ��
						 */
						String OPType = Const.getStrValue(relMap, "OPType");
						if (REL_ADD.equals(OPType)) {
							param.put("ProductRelation", relMap);
							dto.setValueByName(Const.ACTION_PARAMETER, param);
							this.insertProductRelation(dto);
						} else if (REL_DELETE.equals(OPType)) {
							List para = new ArrayList();// p.pro_product_id=?
							// and
							// p.relation_type_cd=?
							// and p.product_id=?
							para.add(Const
									.getStrValue(relMap, "pro_product_id"));
							para.add(Const.getStrValue(relMap,
									"relation_type_cd"));
							para.add(product_id);
							boolean result1 = new ProductRelationDAO()
									.delByInterface(para);
						}
					}
				}
				// 3.ɾ������Ʒ�����⣺ҵ��ƽ̨��ҵ�����������������б�����
				if (accNbrList != null && !accNbrList.isEmpty()) {
					// del
					List resultParam = new ArrayList();
					ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
					boolean temp = dao.batchDelete(product_id);
					// batch insert
					Iterator it = accNbrList.iterator();
					while (it.hasNext()) {
						// prod_acc_nbr_type_id, acc_nbr_type_cd, product_id,
						// access_no, feature_no, match_mode
						Map m = (Map) it.next();
						List accNbr = new ArrayList();
						accNbr.add(getSequence("seq_prod_acc_nbr_type_id"));
						accNbr.add(Const.getStrValue(m, "acc_nbr_type_cd"));
						accNbr.add(product_id);
						accNbr.add(Const.getStrValue(m, "access_no"));
						accNbr.add(Const.getStrValue(m, "feature_no"));
						accNbr.add(Const.getStrValue(m, "match_mode"));

						resultParam.add(accNbr);
					}
					int[] tempInts = dao.batchInsert(resultParam);
				}

				if (platformList != null && !platformList.isEmpty()) {
					// del
					ProdPlatformDAO dao = new ProdPlatformDAO();
					boolean temp = dao.batchDelete(product_id);
					List resultParam = new ArrayList();
					Iterator it = platformList.iterator();
					while (it.hasNext()) {
						// prod_platform_id, product_id, platform_id
						Map m = (Map) it.next();
						List platform = new ArrayList();
						platform.add(getSequence("seq_platform_id"));
						platform.add(product_id);
						platform.add(Const.getStrValue(m, "platform_id"));

						resultParam.add(platform);
					}
					int[] tempInts = dao.batchInsert(resultParam);
				}

				if (servAbilityList != null && !servAbilityList.isEmpty()) {
					// del
					List resultPara = new ArrayList();
					ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
					boolean temp = dao.batchDelete(product_id);
					Iterator it = servAbilityList.iterator();
					while (it.hasNext()) {
						Map m = (Map) it.next();
						List servList = new ArrayList();// product_service_ability_rel_id,
						// product_id,
						// service_code,
						// rel_type
						servList.add(getSequence("seq_service_ability_rel_id"));
						servList.add(product_id);
						servList.add(Const.getStrValue(m, "service_code"));
						servList.add("0");

						resultPara.add(servList);
					}
					int[] tempInts = dao.batchInsert(resultPara);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

			// return getSysReturnXML( Const.getStrValue(product, "StreamingNo")
			// ,ResultCode_FAIL , e.getMessage()) ;
		}

		return getSysReturnXML(Const.getStrValue(product, "StreamingNo"),
				ResultCode_SUCESS, "ͬ���ɹ�");
	}

	private String getSysReturnXML(String StreamingNo, String ResultCode,
			String ResultDesc) {
		StringBuffer xml = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<ProductInfoSyncReq>").append("<StreamingNo>").append(
				StreamingNo).append("</StreamingNo>").append("<ResultCode>")
				.append(ResultCode).append("</ResultCode>").append(
						"<ResultDesc>").append(ResultDesc).append(
						"</ResultDesc>").append("</ProductInfoSyncReq>");
		return xml.toString();
	}

	/**
	 * ��Ʒͬ������
	 * 
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

		String ProductID = root.element("ProductID").getStringValue();
		// ��ȡ��ƷMap
		Map product = getProduct(root, ProductID);
		// ҵ��ƽ̨ ArrayList
		List platformLst = getPlatFormList(root, ProductID);

		// ����� List
		List accNbrList = getAccNbrList(root, ProductID);

		// ҵ������ List
		List servAbilityLst = getServAbilityList(root, ProductID);

		// ��Ʒ��ϵ List
		List refLst = getRefList(root, ProductID);
		// ���õ�Map����
		sysInfo.setProduct(product);
		sysInfo.setAccNbr(accNbrList);
		sysInfo.setPlatForm(platformLst);
		sysInfo.setRel(refLst);
		sysInfo.setServAbility(servAbilityLst);
		return sysInfo;
	}

	private static List getRefList(Element root, String ProductID)
			throws Exception {

		ArrayList refLst = new ArrayList();
		List ProdRelationElementLst = root.elements("ProdRelation");
		if (ProdRelationElementLst != null) {
			for (int i = 0; i < ProdRelationElementLst.size(); i++) {
				Element subElement = (Element) ProdRelationElementLst.get(i);
				String OPType = subElement.element("OPType").getStringValue();
				String RelationType = subElement.element("RelationType")
						.getStringValue();
				String RProductID = subElement.element("RProductID")
						.getStringValue();

				Map refMap = new HashMap();
				refMap.put("product_id", ProductID);
				refMap.put("relation_type_cd", RelationType);
				refMap.put("pro_product_id", RProductID);
				refMap.put("OPType", OPType);
				refMap.put("state_cd", "00A");
				refMap.put("create_date", new Date().toString());
				refMap.put("state_date", new Date().toString());

				refLst.add(refMap);
			}
		}
		return refLst;
	}

	private static List getAccNbrList(Element root, String ProductID)
			throws Exception {

		// ����� List
		ArrayList accNbrList = new ArrayList();
		List ProductOPCode = root.elements("ProductOPCode");
		if (ProductOPCode != null) {
			for (int i = 0; i < ProductOPCode.size(); i++) {
				Element subElement = (Element) ProductOPCode.get(i);
				String feature_no = subElement.element("FeatureStr")
						.getStringValue();
				String access_no = subElement.element("AccessNO")
						.getStringValue();
				String acc_nbr_type_cd = subElement.element("OPType")
						.getStringValue();
				String match_mode = subElement.element("MatchMode")
						.getStringValue();

				Map accNbr = new HashMap();
				accNbr.put("feature_no", feature_no);
				accNbr.put("access_no", access_no);
				accNbr.put("acc_nbr_type_cd", acc_nbr_type_cd);
				accNbr.put("match_mode", match_mode);
				accNbr.put("product_id", ProductID);

				accNbrList.add(accNbr);
			}
		}
		return accNbrList;
	}

	private static List getServAbilityList(Element root, String ProductID)
			throws Exception {
		// ҵ������ List
		ArrayList servAbilityLst = new ArrayList();
		List ServiceCapabilityID = root.elements("ServiceCapabilityID");
		if (ServiceCapabilityID != null) {
			for (int i = 0; i < ServiceCapabilityID.size(); i++) {
				Element subElement = (Element) ServiceCapabilityID.get(i);
				Map servMap = new HashMap();
				String service_code = subElement.getStringValue();
				servMap.put("service_code", service_code);
				servMap.put("product_id", ProductID);
				servAbilityLst.add(servMap);
			}
		}
		return servAbilityLst;
	}

	private static List getPlatFormList(Element root, String ProductID)
			throws Exception {
		ArrayList platformLst = new ArrayList();
		List PlatForm = root.elements("PlatForm");
		if (PlatForm != null) {
			for (int i = 0; i < PlatForm.size(); i++) {
				Element subElement = (Element) PlatForm.get(i);
				String platform_id = subElement.getStringValue();
				Map pf = new HashMap();
				pf.put("platform_id", platform_id);
				pf
						.put("product_id", root.element("ProductID")
								.getStringValue());
				platformLst.add(pf);
			}
		}
		return platformLst;
	}

	private static Map getProduct(Element root, String ProductID)
			throws Exception {
		String StreamingNo = root.element("StreamingNo").getStringValue();
		String TimeStamp = root.element("TimeStamp").getStringValue();
		String SPID = root.element("SPID").getStringValue();

		String OPFlag = root.element("OPFlag").getStringValue();

		String PnameCN = root.element("PnameCN").getStringValue();

		String PnameEN = root.element("PnameEN").getStringValue();
		String PdescriptionCN = root.element("PdescriptionCN").getStringValue();
		String PdescriptionEn = root.element("PdescriptionEn").getStringValue();
		String Status = root.element("Status").getStringValue();
		String Scope = root.element("Scope").getStringValue();
		String ProductHost = root.element("ProductHost").getStringValue();

		Map product = new HashMap();
		// ��Ʒ����
		product.put("StreamingNo", StreamingNo);
		product.put("TimeStamp", TimeStamp);
		product.put("product_provider_id", SPID);
		product.put("OPFlag", OPFlag);
		product.put("product_id", ProductID);
		product.put("product_name", PnameCN);
		product.put("PnameEN", PnameEN);
		product.put("product_desc", PdescriptionCN);
		product.put("PdescriptionEn", PdescriptionEn);
		product.put("product_state_cd", Status);
		product.put("scope", Scope);
		product.put("order_host", ProductHost);

		return product;
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
		return result;
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

		return result;
	}

	public PageModel searchProdAccNbrTypeData(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// ����DAO����
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProdAccNbrTypeById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProdAccNbrTypeByCond(DynamicDict dto) throws Exception {
		// ��������
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// ��֯����DAO���������
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// ����DAO����
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProdAccNbrTypeById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProdAccNbrTypeDAO dao = new ProdAccNbrTypeDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	//����ƽ̨�߼�
	
	public boolean insertProdPlatform(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProdPlatform = (Map) param.get("ProdPlatform");

		String platform_id = Const.getStrValue(ProdPlatform, "system_code");
		String product_id = Const.getStrValue(ProdPlatform, "product_id");
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		boolean existsRow = dao.checkExistsRow(platform_id, product_id, null);
		if (existsRow)
			return false;

		ProdPlatform.put("partner_system_info_id", getSequence("seq_platform_id"));
		ProdPlatform.put("create_date", DAOUtils.getShortFormatedDate());
		ProdPlatform.put("state", "0");
		ProdPlatform.put("state_date", DAOUtils.getShortFormatedDate());
		
		boolean result = dao.insert(ProdPlatform);
		return result;
	}

	public boolean updateProdPlatform(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProdPlatform = (Map) param.get("ProdPlatform");

		String platform_id = Const.getStrValue(ProdPlatform, "system_code");
		String product_id = Const.getStrValue(ProdPlatform, "product_id");
		String prod_platform_id = Const.getStrValue(ProdPlatform,
				"partner_system_info_id");
		ProdPlatform.put("state_date", DAOUtils.getShortFormatedDate());
		
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		boolean existsRow = dao.checkExistsRow(platform_id, product_id,
				prod_platform_id);
		if (existsRow)
			return false;

		String keyStr = "partner_system_info_id";
		Map keyCondMap = Const.getMapForTargetStr(ProdPlatform, keyStr);
		return dao.updateByKey(ProdPlatform, keyCondMap);
	}

	public PageModel searchProdPlatformData(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// ����DAO����
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProdPlatformById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProdPlatformByCond(DynamicDict dto) throws Exception {
		// ��������
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// ��֯����DAO���������
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// ����DAO����
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProdPlatformById(DynamicDict dto) throws Exception {
		// ����DAO����
		ProductSystemInfoDAO dao=new ProductSystemInfoDAO();
		Map keyCondMap = Const.getParam(dto);
		//ProdPlatformDAO dao = new ProdPlatformDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	public boolean insertProduct(DynamicDict dto) throws Exception {
		return insertProduct(dto, true);
	}

	private boolean insertProduct(DynamicDict dto, boolean createSeq)
			throws Exception {
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");

		ProductDAO dao = new ProductDAO();
		String product_nbr = Const.getStrValue(Product, "product_nbr");
		String system_code = Const.getStrValue(Product, "system_code");

		boolean existsRow = dao.checkExistsRow(product_nbr, system_code,null);
		if (existsRow)
			return false;

		if (createSeq)
			Product.put("product_id", getSequence("seq_product_id"));
		Product.put("product_state_cd", "0");// ����״̬
		Product.put("create_date", DAOUtils.getShortFormatedDate());
		Product.put("state_date", DAOUtils.getShortFormatedDate());
		Product.put("product_code", product_nbr);
		
		boolean result = dao.insert(Product);
		
		//������Ʒ��ʱ��ͬʱ�Կ��PROD_OFFER����һ����¼��Ҳ����ͬʱ��������Ʒ
		Map ProdOffParam=new HashMap();
		ProdOffParam.put("prod_offer_id", Const.getStrValue(Product, "product_id"));
		ProdOffParam.put("fee_set_flag", "03");
		ProdOffParam.put("prod_offer_sub_type", "0");
		ProdOffParam.put("state_date", DAOUtils.getShortFormatedDate());
		ProdOffParam.put("state", Const.getStrValue(Product, "product_state_cd"));
		ProdOffParam.put("offer_nbr", Const.getStrValue(Product, "product_nbr"));
		ProdOffParam.put("prod_offer_name", Const.getStrValue(Product, "product_name"));
		ProdOffParam.put("sub_option", Const.getStrValue(Product, "sub_option"));
		ProdOffParam.put("present_option", Const.getStrValue(Product, "present"));
		ProdOffParam.put("corp_only", Const.getStrValue(Product, "corp_only"));
		ProdOffParam.put("scope", Const.getStrValue(Product, "scope"));
		ProdOffParam.put("package_host", Const.getStrValue(Product, "system_code"));
		
		ProdOffDAO dao1=new ProdOffDAO();
		dao1.insert(ProdOffParam);
		
		//�����PROD_OFFER_DETAIL_ROLE����һ����¼,��������������,����Ʒid��product_id������ֵ��Ʒ��id.
		Map ProdOffDetailParam=new HashMap();
		ProdOffDetailParam.put("prod_offer_id", Const.getStrValue(Product, "product_id"));
		ProdOffDetailParam.put("product_id", Const.getStrValue(Product, "product_id"));
		ProdOffDetaRoleDAO dao2 = new ProdOffDetaRoleDAO();
		result = dao2.insertProdOffRel(ProdOffDetailParam) ;
		
		return result;
	}

	public boolean updateProduct(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map Product = (Map) param.get("Product");

		ProductDAO dao = new ProductDAO();
		String product_nbr = Const.getStrValue(Product, "product_nbr");
		String system_code = Const.getStrValue(Product, "system_code");
		String product_id = Const.getStrValue(Product, "product_id");
		String product_code = Const.getStrValue(Product, "product_code");
		
		// boolean existsRow = dao.checkExistsRow(product_nbr, system_code,
		// product_id);
		// if (existsRow)
		// return false;

		boolean updateSelf = dao.checkUpdateSelf(product_nbr, system_code,// �Ƿ�����Լ�id��
				product_id,product_code);
		if (!updateSelf) {
			// ���Ǹ����Լ���id�У������Ƿ���������г�ͻ
			boolean existsRow = dao.checkExistsRow(product_nbr, system_code,
					product_id);
			if (existsRow)
				return false;
		}
		Product.put("state_date", DAOUtils.getShortFormatedDate());
		String keyStr = "product_id";
		Map keyCondMap = Const.getMapForTargetStr(Product, keyStr);
		boolean result = dao.updateByKey(Product, keyCondMap);
		
		//���²�Ʒ��ʱ��ͬʱ�Կ��PROD_OFFER���£�Ҳ����ͬʱ��������Ʒ
		Map ProdOffParam=new LinkedHashMap();
		ProdOffParam.put("prod_offer_name", Const.getStrValue(Product, "product_name"));
		ProdOffParam.put("state_date",DAOUtils.getCurrentDate());
		ProdOffParam.put("state", Const.getStrValue(Product, "product_state_cd"));
		ProdOffParam.put("prod_offer_id", Const.getStrValue(Product, "product_id"));
		String updateStr="update PROD_OFFER set prod_offer_name=?, state_date=?, state=? where prod_offer_id=?";
		ProdOffDAO  dao1=new ProdOffDAO();
		dao1.updateBySQL(updateStr, ProdOffParam);
		return result;
	}

	public PageModel searchProductData(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
        
		if (Const.containStrValue(param, "product_name")) {
			whereCond.append(" and p.product_name like ? ");
			para.add("%" + Const.getStrValue(param, "product_name") + "%");
		}
		
		if (Const.containStrValue(param, "product_nbr")) {
			whereCond.append(" and p.product_nbr like ? ");
			para.add("%" + Const.getStrValue(param, "product_nbr") + "%");
		}
		
		if (Const.containStrValue(param, "product_code")) {
			whereCond.append(" and p.product_code like ? ");
			para.add("%" + Const.getStrValue(param, "product_code") + "%");
		}
		
		if(Const.containStrValue(param, "product_provider_id")){
			whereCond.append(" and p.product_provider_id = ? ");
			para.add(Const.getStrValue(param, "product_provider_id"));
		}
		
		if(Const.containStrValue(param, "order_host")){
			whereCond.append(" and p.order_host = ? ");
			para.add(Const.getStrValue(param, "order_host"));
		}
		
		if (Const.containStrValue(param, "fileterProductId")) {
			whereCond
					.append(" and p.product_id <> ? and p.product_id not in(select pr.pro_product_id from product_relation pr where pr.state_cd='00A' and pr.product_id=? ) and p.PRODUCT_STATE_CD='0' ");
			para.add(Const.getStrValue(param, "fileterProductId"));
			para.add(Const.getStrValue(param, "fileterProductId"));
		}
		
		whereCond.append(" and p.PROD_FUNC_TYPE=1 ");  //1Ϊ��ֵ��Ʒ��
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchProductsByAblitity(whereCond.toString(), para, param, pageIndex, pageSize);

		return result;
	}
	
	
	public PageModel searchProductData2Select(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
        /*String catlgItemId=Const.getStrValue(param, "catalog_item_id");
		if (Const.containStrValue(param, "catalog_item_id")) {
				whereCond.append(" and product_id in (select distinct element_id as product_id from PRODUCT_CATALOG_ITEM_ELEMENT  where catalog_item_id in " +
						"(select catalog_item_id from Product_Catalog_Item where parent_catalog_item_id=?" +
						"union select catalog_item_id from Product_Catalog_Item where catalog_item_id=?))");
			
			//whereCond.append(" and product_id in (select distinct element_id as product_id from PRODUCT_CATALOG_ITEM_ELEMENT where catalog_item_id=?) ");
			para.add(Const.getStrValue(param, "catalog_item_id"));
			para.add(Const.getStrValue(param, "catalog_item_id"));
		}*/
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id like ''||?||'%' ");
			para.add(Const.getStrValue(param, "product_id"));
		}
		if (Const.containStrValue(param, "product_name")) {
			whereCond.append(" and product_name like ? ");
			para.add("%" + Const.getStrValue(param, "product_name") + "%");
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// ����DAO����
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}
	
	public PageModel searchProductOfferData2Select(DynamicDict dto) throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "prod_offer_id")) {
			whereCond.append(" and prod_offer_id like ''||?||'%' ");
			para.add(Const.getStrValue(param, "prod_offer_id"));
		}
		if (Const.containStrValue(param, "prod_offer_name")) {
			whereCond.append(" and prod_offer_name like ? ");
			para.add("%" + Const.getStrValue(param, "prod_offer_name") + "%");
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		// ����DAO����
		ProdOfferDAO dao = ProdOfferDAOFactory.getProdOfferDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}
	
	//���ݲ�Ʒ���ṩ��ID�����Ҳ�Ʒ���ڲ�Ʒ��ѯͳ��
	public PageModel searchProductByProviderID(DynamicDict dto) throws Exception{
		Map param=Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_provider_id")) {
			whereCond.append(" and product_provider_id=? ");
			para.add(Const.getStrValue(param, "product_provider_id"));
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchProductByProviderId(whereCond.toString(), para,
				pageIndex, pageSize);
		return result;
	}
	
	//���ݲ�Ʒ���ṩ��ID������
	public PageModel searchProductDataByProviderID(DynamicDict dto) throws Exception{
		Map param=Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_provider_id")) {
			whereCond.append(" and product_provider_id=? ");
			para.add(Const.getStrValue(param, "product_provider_id"));
		}
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);
		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProductById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProductDAO dao = new ProductDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}
	
	public PageModel getProductsById(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		
		if(Const.containStrValue(param, "product_id")){
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		ProductDAO dao = new ProductDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para, pageIndex, pageSize);

		return result;
	}
	

	public List findProductByCond(DynamicDict dto) throws Exception {
		// ��������
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// ��֯����DAO���������
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// ����DAO����
		ProductDAO dao = new ProductDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProductById(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto);
		String product_id = (String) keyCondMap.get("product_id");
		
		String updateStr = "update PRODUCT set PRODUCT_STATE_CD=4, STATE_DATE=sysdate where PRODUCT_ID=" + product_id;
		
		//֧��INFROMIX
		String updateStr_informix = "update PRODUCT set PRODUCT_STATE_CD=4, STATE_DATE=current where PRODUCT_ID=" + product_id;
		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			
		  updateStr=updateStr_informix;
		}
		
		ProdOffDAO dao = new ProdOffDAO();
		boolean result=dao.updateBySQL(updateStr, new HashMap());
		
		Map ProdOffParam=new LinkedHashMap();
		ProdOffParam.put("prod_offer_id", product_id);
		String updateStr1="update PROD_OFFER set state=4 where prod_offer_id=?";
		ProdOffDAO dao1=new ProdOffDAO();
		result=dao1.updateBySQL(updateStr1, ProdOffParam);
		return result;
	}

	public boolean insertProductRelation(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		Map ProductRelation = (Map) param.get("ProductRelation");
		ProductRelation
				.put("product_rel_id", getSequence("seq_product_rel_id"));
		ProductRelation.put("state_cd", "00A");
		ProductRelation.put("create_date", DAOUtils.getShortFormatedDate());
        ProductRelation.put("state_date", DAOUtils.getShortFormatedDate());
		ProductRelationDAO dao = new ProductRelationDAO();
		boolean result = dao.insert(ProductRelation);
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
		// ��������
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

		// ����DAO����
		ProductRelationDAO dao = new ProductRelationDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProductRelationById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProductRelationDAO dao = new ProductRelationDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProductRelationByCond(DynamicDict dto) throws Exception {
		// ��������
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// ��֯����DAO���������
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// ����DAO����
		ProductRelationDAO dao = new ProductRelationDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProductRelationById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProductRelationDAO dao = new ProductRelationDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	public boolean insertProductServAbilityRel(DynamicDict dto)
			throws Exception {
		Map param = Const.getParam(dto);
		Map ProductServAbilityRel = (Map) param.get("ProductServAbilityRel");
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		String service_code = Const.getStrValue(ProductServAbilityRel,
				"service_code");
		String product_id = Const.getStrValue(ProductServAbilityRel,
				"product_service_ability_rel_id");

		boolean existsRow = dao.checkExistsRow(service_code, product_id, null);
		if (existsRow)
			return false;

		ProductServAbilityRel.put("product_service_ability_rel_id",
				getSequence("seq_service_ability_rel_id"));
		ProductServAbilityRel.put("rel_type", "0");

		return dao.insert(ProductServAbilityRel);
	}

	public boolean updateProductServAbilityRel(DynamicDict dto)
			throws Exception {
		Map param = Const.getParam(dto);
		Map ProductServAbilityRel = (Map) param.get("ProductServAbilityRel");

		String service_code = Const.getStrValue(ProductServAbilityRel,
				"service_code");
		String product_id = Const.getStrValue(ProductServAbilityRel,
				"product_service_ability_rel_id");
		String product_service_ability_rel_id = Const.getStrValue(
				ProductServAbilityRel, "product_service_ability_rel_id");

		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		boolean existsRow = dao.checkExistsRow(service_code, product_id,
				product_service_ability_rel_id);
		if (existsRow)
			return false;

		String keyStr = "product_service_ability_rel_id";
		Map keyCondMap = Const
				.getMapForTargetStr(ProductServAbilityRel, keyStr);

		return dao.updateByKey(ProductServAbilityRel, keyCondMap);
	}

	public PageModel searchProductServAbilityRelData(DynamicDict dto)
			throws Exception {
		// ��������
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if (Const.containStrValue(param, "product_id")) {
			whereCond.append(" and product_id = ? ");
			para.add(Const.getStrValue(param, "product_id"));
		}

		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);

		// ����DAO����
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getProductServAbilityRelById(DynamicDict dto) throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		Map result = dao.findByPrimaryKey(keyCondMap);

		return result;
	}

	public List findProductServAbilityRelByCond(DynamicDict dto)
			throws Exception {
		// ��������
		String filterStr = "";
		Map changeName = null;
		SQLWhereClause where = new SQLWhereClause(dto, filterStr, changeName,
				SQLWhereClause.NO_PAGING);

		// ��֯����DAO���������
		String whereCond = where.getWhereCond();
		List para = where.getPara();
		// ����DAO����
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		List result = dao.findByCond(whereCond, para);

		return result;
	}

	public boolean deleteProductServAbilityRelById(DynamicDict dto)
			throws Exception {
		// ����DAO����
		Map keyCondMap = Const.getParam(dto);
		ProductServAbilityRelDAO dao = new ProductServAbilityRelDAO();
		boolean result = dao.deleteByKey(keyCondMap) > 0;

		return result;
	}

	/**
	 * 
	 * ���ز�Ʒ������
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 * 
	 */
	public String loadProductType(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto);
		String typeId = (String) keyCondMap.get("typeId");
		String orderId = (String) keyCondMap.get("orderId");
		ProductDAO dao = new ProductDAO();
		ArrayList treeList = ptMap2VO(dao.loadProductType(typeId, orderId));
		String result = XMLSegBuilder.toXmlItems(treeList);
		return result;
	}

	
	
	private ArrayList ptMap2VO(ArrayList treeList) {
		if (treeList == null || treeList.isEmpty())
			return null;

		ArrayList result = null;
		for (int i = 0; i < treeList.size(); i++) {
			Object o = treeList.get(i);
			if (o instanceof ProdCatgItemVO)
				return treeList;

			if (result == null) {
				result = new ArrayList();
			}

			HashMap m = (HashMap) o;
			ProdCatgItemVO type = new ProdCatgItemVO();
			type.loadFromHashMap(m);
			result.add(type);
		}
		return result;
	}

	public PageModel searchPartnerData(DynamicDict dto) throws Exception {
		// ��������
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

		// ����DAO����
		PartnerDAO dao = new PartnerDAO();
		PageModel result = dao.searchByCond(whereCond.toString(), para,
				pageIndex, pageSize);

		return result;
	}

	/**
	 * �û�(��Ʒʵ��)��Ϣ��ѯ
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductInsts(DynamicDict dto) throws Exception {
		List pv = new ArrayList();
		Map param = Const.getParam(dto);
		StringBuffer where = new StringBuffer();
		
		if (Const.containStrValue(param, "accNbr")) {
			where.append("and c.ACC_NBR=? ");
			pv.add(Const.getStrValue(param, "accNbr"));
		}
		
		if (Const.containStrValue(param, "lanCode")) {
			where.append("and c.LAN_ID=? ");
			pv.add(Const.getStrValue(param, "lanCode"));
		}
		
		if(Const.containStrValue(param , "productId")){
			where.append(" and c.PRODUCT_ID = ? ");
			pv.add(Const.getStrValue(param , "productId")) ;
		}

		if (Const.containStrValue(param, "prodInstId")) {
			where.append("and c.PROD_INST_ID = ? ");
			pv.add(Const.getStrValue(param, "prodInstId"));
		}
		
		int pageIndex = Const.getPageIndex(param);
		int pageSize = Const.getPageSize(param);

		ProductDAO dao = new ProductDAO();
		PageModel pageModel = dao.searchProductInsts(where.toString(), pv, pageIndex, pageSize);

		return pageModel;
	}
	
	public List getAbilityById(DynamicDict dto) throws Exception{
		Map param=Const.getParam(dto);
		Map result;
		ProductDAO dao= new ProductDAO();
		if(Const.containStrValue(param, "prod_inst_id")){
			return dao.getAbilityById(Const.getStrValue(param, "prod_inst_id"));
		}else{
			return null;
		}
	
	}
	
	public PageModel searchProducts(DynamicDict dto) throws Exception {
		List pv = new ArrayList();
		Map param = Const.getParam(dto);
		
		StringBuffer where = new StringBuffer();
		if (Const.containStrValue(param, "productName")) {
			where.append(" and (product_name like ? or product_name like ?)");
			pv.add("%" + Const.getStrValue(param, "productName") + "%");
			pv.add("%" + Const.getStrValue(param, "productName").toUpperCase() + "%");
		}
		if (Const.containStrValue(param, "prodFuncType")) {
			where.append(" and PROD_FUNC_TYPE= ? ");
			pv.add(Const.getStrValue(param, "prodFuncType"));
		}
		where.append("and PROD_TYPE=1 "); //1��ʾ����ֵ��Ʒ
		int pageIndex = Const.getPageIndex(param);
		int pageSize = Const.getPageSize(param);
		
		ProductDAO dao = new ProductDAO();
		PageModel pageModel = dao.searchProducts(where.toString(), pv, pageIndex, pageSize);
		return pageModel;
	}

}
