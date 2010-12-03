package com.ztesoft.vsop.engine.help;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.vsop.BizRestraintVo;
import com.ztesoft.vsop.PartRuleFactory;
import com.ztesoft.vsop.ProductVo;
import com.ztesoft.vsop.SpProductVo;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.vo.BizCapabilityInstVO;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdRelationVO;

/**
 * ������Ȩ�����ڲ������� ��ֲAuthenticationMain
 * @author cooltan
 * 
 */
public class AuthenticationMainInner {
	private static Logger logger = Logger.getLogger(AuthenticationMainInner.class);
	private static AuthenticationMainInner aAuthenticationMain=new AuthenticationMainInner();
	public static AuthenticationMainInner getInstance(){
		return aAuthenticationMain;
	}
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//ʡ�ݴ���

	/**
	 * ��Ȩ���� ׼������2
	 * @param productInfo
	 *            �û���Ϣ���������롢������ֵ��Ʒ�б�
	 * @param systemId
	 *            ����ϵͳ
	 * @return
	 * @throws Exception
	 */
	public ArrayList process(ArrayList productInfo, String systemId)throws Exception {
		logger.info("start authentication");
		long s = System.currentTimeMillis();
		ArrayList lst = new ArrayList();//��Ȩ���ؽ�� List<Map>
		ArrayList vProductWithSpCpProductStateList = new ArrayList(); // ��¼��ֵ��Ʒ��Ӧ��spcp״̬����Ʒ״̬
		ArrayList userinfoList = new ArrayList();// ��¼�û���Ϣ��������״̬��������Ʒҵ�������б�������ϵʵ��
		ArrayList vProductRelationList = new ArrayList();// ��¼��ֵ��Ʒ��Ӧ�Ĳ�Ʒ��ϵ
		String vProductIds = "";// Ҫ�������ֵ��Ʒ�� ,�����ֵ��Ʒ�ö��ŷָ�
		ArrayList vProductInCurrentOrder = new ArrayList();// ��¼ÿ����ֵ��Ʒ�Ķ���
		ArrayList bizCapInstList = new ArrayList();// ��¼ÿ��������Ʒ
		// ��������Ϸ���У��
		if (productInfo == null || productInfo.size() == 0) {
			String result = "1";// 0�ɹ���1ʧ��
			HashMap map = new HashMap();
			map.put("ResultCode", "1");
			map.put("ProductNo", "-1");
			map.put("SPProdSpecId", "-1");
			map.put("SPProdSpecName", "-1");
			map.put("ResultDesc", "����ĺ�����ϢΪ�գ���Ȩʧ��");
			lst.add(map);
			return lst;
		}
		if (systemId == null || systemId.equals("")) {
			String result = "1";// 0�ɹ���1ʧ��
			HashMap map = new HashMap();
			map.put("ResultCode", "1");
			map.put("ProductNo", "-1");
			map.put("SPProdSpecId", "-1");
			map.put("SPProdSpecName", "-1");
			map.put("ResultDesc", "��Դϵͳ��ʶΪ�գ���Ȩʧ��");
			lst.add(map);
			return lst;
		}
		// ��ȡ��Ҫ��Ȩ����ֵ��Ʒ���ö��ŷָ
		for (int i = 0; i < productInfo.size(); i++) {
			HashMap productMap = (HashMap) productInfo.get(i);
			String productNo = (String) productMap.get("ProductNo");// �û�����
			String productId = (String) productMap.get("ProductId");// �û���ʶ
			String lanId = (String) productMap.get("LanId");// ��������ʶ
			ArrayList SPProdInfoLst = (ArrayList) productMap.get("SPProdInfo");// ��ֵ��Ʒ�б�
			if (SPProdInfoLst != null && SPProdInfoLst.size() > 0) {
				for (int j = 0; j < SPProdInfoLst.size(); j++) {
					HashMap spProductInfoMap = (HashMap) SPProdInfoLst.get(j);
					String vproductId = (String) spProductInfoMap
							.get("SPProdSpecID");
					String vproductActionType = (String) spProductInfoMap
							.get("ActionType");
					String offerId = (String) spProductInfoMap
							.get("ProductOfferId");
					String offerType = (String) spProductInfoMap
							.get("ProductOfferType");
					String productNbr=(String)spProductInfoMap.get("VProductNbr");//��ֵ��Ʒ�����   --add 20101009������rule12ʹ��
					
					if (vproductId == null || "".equals(vproductId)) {
						String result = "1";// 0�ɹ���1ʧ��
						HashMap map = new HashMap();
						map.put("ResultCode", "1");
						map.put("ProductNo", productNo);
						map.put("SPProdSpecId", "-1");
						map.put("SPProdSpecName", "-1");
						map.put("ResultDesc", "��ֵ��Ʒ�����ڣ���Ȩʧ��");
						lst.add(map);
						return lst;
					}
					
					vProductIds = vProductIds + vproductId + ",";
					SpProductVo spProductVo = new SpProductVo();
					spProductVo.setServiceId(vproductId);
					spProductVo.setType(vproductActionType);
					spProductVo.setProductNo(productNo);
					spProductVo.setNewProductOfferId(offerId);
					spProductVo.setNewProductOfferType(offerType);
					spProductVo.setProductNbr(productNbr);//��ֵ��Ʒ�����   --add 20101009������rule12ʹ��
					vProductInCurrentOrder.add(spProductVo);
				}
			}
			bizCapInstList = (ArrayList) productMap.get("IncrProdInfo");// ������Ʒ�б�
			if (bizCapInstList != null && bizCapInstList.size() > 0) {
				for (int j = 0; j < bizCapInstList.size(); j++) {
					HashMap incrProductMap = (HashMap) bizCapInstList.get(j);
					incrProductMap.put("ProductNo", productNo);
				}
			}
		}
		if (!vProductIds.equals("")) {// ȥ������
			vProductIds = vProductIds.substring(0, vProductIds.length() - 1);
		}
		try {
			// ��ѯ��Ʒ״̬��������ҵ�������Լ�SP/CP״̬
			vProductWithSpCpProductStateList = this.getSpInfoFromCache(vProductIds);
			// ��ѯԼ����ϵ
			vProductRelationList = this.getBizRestraintFromCache(vProductIds);
			// ��ѯ�û�ʵ������ֵҵ��ʵ����ҵ������ʵ����
			for (int i = 0; i < productInfo.size(); i++) {
				HashMap productMap = (HashMap) productInfo.get(i);
				String productNo = (String) productMap.get("ProductNo");// �û�����
				String productId = (String) productMap.get("ProductId");// �û���ʶ
				String lanId = (String) productMap.get("LanId");// ��������ʶ
				String userState = (String) productMap.get("UserState");
				String payMode = (String) productMap.get("PayMode");
				String prodInstId = (String) productMap.get("ProdInstId");
				s = System.currentTimeMillis();
				ProductVo productVo = new ProductVo();
				if ("201".equals(systemId)) {// crm������Ҫ��ѯ��Ʒʵ��
					ProdInstHelpDao aProdInstHelpDao = new ProdInstHelpDao();
					ProdInstVO prodInstvo = null;
					// �������ػ��������00X���û�ʵ��
					if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
						// ��trueʱ��00X״̬�������
						prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndProductId(productNo,
										productId, true);
					} else {
						// ��falseʱֻ��00A��������00B��00C״̬Ϊ��Ч״̬��
						prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndProductId(productNo,
										productId, false);
					}
					if(null!=prodInstvo && null!=prodInstvo.getProdInstId()
							&& !"".equals(prodInstvo.getProdInstId())){//����vsop���������û�ʵ������ʱ������
						lanId = prodInstvo.getLanId();
						productNo = prodInstvo.getAccNbr();
						productId = prodInstvo.getProdId();
						userState = prodInstvo.getStateCd();
						payMode = prodInstvo.getPaymentModeCd();
					}
				} else {// �������crm�����ģ�����Ҫ�ٲ�ѯ����Ʒʵ��
				}
				productVo.setProductNo(productNo);
				productVo.setLanId(lanId);
				productVo.setUserState(userState);
				productVo.setPaymentModeCd(payMode);
				productVo.setProductSpec(productId);
				productVo.setProductId(prodInstId);
				logger.info("#auth.qryProdInstByAccNbrAndProductId cost->"
						+ (System.currentTimeMillis() - s));
				if (productVo.getProductId() == null
						|| productVo.getProductId().equals("")) {// ��װ���û�
					productVo.setProductId("-1");
					productVo.setProductNo(productNo);
					productVo.setLanId(lanId);
					productVo.setUserState("00A");
					productVo.setPaymentModeCd("0");//��װʱĬ��ΪԤ�����û�
					//�������û�ҵ������������Ĭ�ϾͿ�ͨ�ģ���Ҫ���⴦��
					if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
						handleDefaultBizCapForIsmp(productVo,productId);
					}
					//end �������û�ҵ������
				} else {// ���û�����ҵ������ʵ���Լ����Ҷ�����ϵʵ��
					// ����ҵ������ʵ��
					s = System.currentTimeMillis();
					BizCapInstHelpDao aBizCapInstHelpDao = new BizCapInstHelpDao();
					List tmpBizCapInstList = aBizCapInstHelpDao
							.qryBizCapInstsByProdInstId(prodInstId);
					productVo.setServiceCapability(this
							.findBizCapService(tmpBizCapInstList));
					logger.info("#qryBizCapInstsByProdInstId cost->"
							+ (System.currentTimeMillis() - s));
					//�������û�ҵ������������Ĭ�ϾͿ�ͨ�ģ���Ҫ���⴦��
					if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
						handleDefaultBizCapForIsmp(productVo,productId);
					}
					//end �������û�ҵ������
					// ���Ҷ�����ϵʵ��
					s = System.currentTimeMillis();
					OrderRelationHelpDao aOrderRelationHelpDao = new OrderRelationHelpDao();
					List orderRelationVoList = aOrderRelationHelpDao
							.qryORSByProdInstId(prodInstId);
					productVo.setSpProductInfo(this.findAuthOrderRelation(
							orderRelationVoList, productNo));
					logger.info("#qryORSByProdInstId cost->"
							+ (System.currentTimeMillis() - s));
				}
				userinfoList.add(productVo);
			}
			logger.info("auth data prepare cost "
					+ (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			// ����׼����Ͻ��м�Ȩ����
			lst = this.authentication(systemId, vProductInCurrentOrder,
					bizCapInstList, vProductWithSpCpProductStateList,
					userinfoList, vProductRelationList);
			logger.info("authentication cost "
					+ (System.currentTimeMillis() - s));
		} catch (Exception se) {
			se.printStackTrace();
			logger.error("#process ex.", se);
			throw se;
		} finally {
		}
		logger.info("end authentication.");
		// �ع����ؽ��
		lst = this.getResultLst(lst, systemId);
		return lst;
	}

	/**
	 * �������û�ҵ������������Ĭ�ϾͿ�ͨ�ģ���Ҫ���⴦��
	 * @param productVo  �û�ʵ��
	 * @param productId  �û�����
	 * @author yi.chengfeng 2010-8-26
	 */
	private void handleDefaultBizCapForIsmp(ProductVo productVo,
			String productId) {
		String msisdn = DcSystemParamManager.getParameter(VsopConstants.DC_MSISDN);
		String ismpDefault = "";
		//������ֻ�������c���û���Ĭ��ҵ������
		if(msisdn.indexOf(productId) != -1){
			ismpDefault = DcSystemParamManager.getParameter(VsopConstants.ISMP_MSISDN_DEF_SERVICE_CODE);
		}else{
			//�������p����ҵ������
			ismpDefault = DcSystemParamManager.getParameter(VsopConstants.ISMP_PSTN_DEF_SERVICE_CODE);
		}
		if(ismpDefault == null || "".equals(ismpDefault)) return;
		List servList = new ArrayList();
		char[] serviceCodes = ismpDefault.toCharArray();
		for (int i = 0; i < serviceCodes.length; i++) {
			char c = serviceCodes[i];
			if(c == '1'){
				String serviceCode = String.valueOf(i+1);
				HashMap map = new HashMap();
				map.put("productId", serviceCode);
				map.put("productName", DcSystemParamManager.getInstance()
						.getServicenameByCode(serviceCode));
				servList.add(map);
			}
		}
		if(servList.size()>0){
			productVo.getServiceCapability().addAll(servList);
		}
	}

	private ArrayList findAuthOrderRelation(List orderRelationVoList,
			String prductNo) {
		ArrayList lst = new ArrayList();
		for (java.util.Iterator it = orderRelationVoList.iterator(); it.hasNext();) {
			OrderRelationVO aOrderRelationVO = (OrderRelationVO) it.next();
			SpProductVo spProductVo = new SpProductVo();
			String productId = aOrderRelationVO.getProdId();
			spProductVo.setServiceId(productId);
			String tmp = this.getProdServiceAbilitysSpliByComma(productId);
			spProductVo.setServiceCapabilityId(tmp);
			spProductVo.setState(aOrderRelationVO.getState());
			String productName = DcSystemParamManager.getInstance().getProductnameById(productId);
			spProductVo.setNameCn(productName);
			if(aOrderRelationVO.getPprodOffId()!=null&&
					!"".equals(aOrderRelationVO.getPprodOffId())){
				spProductVo.setProductOfferId(aOrderRelationVO.getPprodOffId());
			}else{
				spProductVo.setProductOfferId("");
			}
			//spProductVo.setProductOfferId(aOrderRelationVO.getProdOffId());
			spProductVo.setPackageId(aOrderRelationVO.getPackageId());
			spProductVo.setType("M");
			spProductVo.setProductNo(prductNo);
			lst.add(spProductVo);
		}
		return lst;
	}

	private ArrayList findBizCapService(List bizCapInstList) {
		ArrayList lst = new ArrayList();
		for (java.util.Iterator it = bizCapInstList.iterator(); it.hasNext();) {
			BizCapabilityInstVO aBizCapabilityInstVO = (BizCapabilityInstVO) it.next();
			String productId = aBizCapabilityInstVO.getProdId();
			List tmp = DcSystemParamManager.getInstance().
				getProductServiceAbilitysByid(productId);
			if (tmp != null) {
				for (int i = 0; i < tmp.size(); i++) {
					HashMap map = new HashMap();
					String serviceCode = (String) tmp.get(i);
					map.put("productId", serviceCode);
					map.put("productName", DcSystemParamManager.getInstance()
							.getServicenameByCode(serviceCode));
					lst.add(map);
				}
			}
		}
		return lst;
	}
	
	
	/*
	 * �����ֵ��Ʒ��Ȩ
	 * 
	 */
	public  ArrayList authentication(String systemId,
			ArrayList vProductInCurrentOrder, ArrayList bizCapInstList,
			ArrayList vProductWithSpCpProductStateList, ArrayList userinfoList,
			ArrayList vProductRelationList) {
		ArrayList lst = new ArrayList();
		// ����Ҫ��Ȩ����ֵ��ƷĬ�ϼ�Ȩ�ɹ����������ȷŵ�������ֵ��Ʒ�У�ɾ�������ȴ����ò�Ʒ��ɾ����
		for (int i = 0; i < vProductInCurrentOrder.size(); i++) {
			SpProductVo spProductVo = null;// new SpProductVo();
			spProductVo = (SpProductVo) vProductInCurrentOrder.get(i);
			for (int j = 0; j < vProductWithSpCpProductStateList.size(); j++) {
				SpProductVo spProductSpcVo = null;// new SpProductVo();
				spProductSpcVo = (SpProductVo) vProductWithSpCpProductStateList
						.get(j);
				if (spProductVo.getServiceId().equals(spProductSpcVo.getServiceId())) {
					spProductVo.setNameCn(spProductSpcVo.getNameCn());
					spProductVo.setServiceCapabilityId(spProductSpcVo.getServiceCapabilityId());
					spProductVo.setSpState(spProductSpcVo.getSpState());
					spProductVo.setState(spProductSpcVo.getState());
				}
				for (int k = 0; k < userinfoList.size(); k++) {
					ProductVo productVo = (ProductVo) userinfoList.get(k);
					if (productVo.getProductNo().equals(spProductVo.getProductNo())) {
						ArrayList spInfoLst =  productVo.getSpProductInfo();
						if (spProductVo.getType().equalsIgnoreCase("ADD")
								|| spProductVo.getType().equalsIgnoreCase("0")) {
							spInfoLst.add(spProductVo);
						} else if (spProductVo.getType().equalsIgnoreCase("DEL")
								|| spProductVo.getType().equalsIgnoreCase("1")) {
							for (int m = 0; m < spInfoLst.size(); m++) {
								SpProductVo spProductInstVo = (SpProductVo) spInfoLst.get(m);
								if (spProductInstVo.getServiceId().equals(spProductVo.getServiceId())) {
									spProductInstVo.setState("00X");
								}
							}
						}
					}
				}
			}
		}
		// ������ĸ�����Ʒд�뵽������Ʒ�б���
		if (bizCapInstList != null) {
			for (int i = 0; i < bizCapInstList.size(); i++) {
				HashMap incrProductMap = (HashMap) bizCapInstList.get(i);
				String productNo = (String) incrProductMap.get("ProductNo");
				String productId = (String) incrProductMap.get("ProductId");
				String actionType = (String) incrProductMap.get("ActionType");
				for (int k = 0; k < userinfoList.size(); k++) {
					ProductVo productVo = (ProductVo) userinfoList.get(k);
					if (productVo.getProductNo().equals(productNo)) {
						ArrayList productLst = productVo.getServiceCapability();
						if (productLst == null) {
							ArrayList lst1 = new ArrayList();
							if (actionType.equalsIgnoreCase("ADD")
									|| actionType.equalsIgnoreCase("0")) {
								HashMap map = new HashMap();
								map.put("productId", productId);
								map.put("productName", "");
								lst1.add(map);
								productVo.setServiceCapability(lst1);
							}
						} else {
							if (actionType.equalsIgnoreCase("ADD")
									|| actionType.equalsIgnoreCase("0")) {
								HashMap map = new HashMap();
								map.put("productId", productId);
								map.put("productName", "");
								productLst.add(map);
								productVo.setServiceCapability(productLst);
							} else if (actionType.equalsIgnoreCase("DEL")
									|| actionType.equalsIgnoreCase("1")) {
								for (int a = 0; a < productLst.size(); a++) {
									HashMap map = (HashMap) productLst.get(a);
									if (productId.equals((String) map
											.get("productId"))) {
										productLst.remove(a);
									}
								}
							}
						}

					}
				}

			}
		}
		// ����׼����ɣ�����һ��ѭ�������и�����Ȩ����ĵ���
		// cooltan ���õļ�Ȩ��Ҫ��������������Ҫÿ����ֵ��Ʒ��ȥ��Ȩ
		HashMap ProductMap = new HashMap();
		for (int i = 0; i < vProductInCurrentOrder.size(); i++) {
			SpProductVo spProductVo = (SpProductVo) vProductInCurrentOrder.get(i);
			ArrayList lst1 = new ArrayList();
			if (spProductVo.getType().equalsIgnoreCase("ADD")
					|| spProductVo.getType().equalsIgnoreCase("0")) {
				if (ProductMap.get(spProductVo.getServiceId()) != null
						&& !ProductMap.get(spProductVo.getServiceId()).equals(
								"")) {
					HashMap resultMap = new HashMap();
					resultMap.put("Result", "11");// ʧ��
					resultMap.put("ProductNo", spProductVo.getProductNo());// ����
					resultMap.put("SPProdSpecId", spProductVo.getServiceId());
					resultMap.put("SPProdSpecName", spProductVo.getNameCn());
					resultMap.put("FailureNote", "����ͬʱ������ͬ����ֵ��Ʒ"
							+ spProductVo.getNameCn());
					lst.add(resultMap);
					return lst;
				} else {
					ProductMap.put(spProductVo.getServiceId(), "add");
				}
				// ������ȡ�����ļ�Ȩ˳��
				String rule = CrmParamsConfig.getInstance().getParamValue("RULE_ADD");
				lst1 = this.authenticationProcess(systemId, spProductVo, rule,
						vProductWithSpCpProductStateList, userinfoList,
						vProductRelationList);
			} else if (spProductVo.getType().equalsIgnoreCase("DEL")
					|| spProductVo.getType().equalsIgnoreCase("1")) {
				// �˶���ȡ�˶��ļ�Ȩ˳��
				String rule = CrmParamsConfig.getInstance().getParamValue("RULE_DEL");
				lst1 = this.authenticationProcess(systemId, spProductVo, rule,
						vProductWithSpCpProductStateList, userinfoList,
						vProductRelationList);
			}
			if (lst1 != null) {
				for (int a = 0; a < lst1.size(); a++) {
					lst.add((HashMap) lst1.get(a));
				}
			}
		}

		return lst;
	}
	/**
	 * ������ֵ��Ʒ��Ȩ
	 * @param systemId
	 * @param spProductVo
	 * @param rule
	 * @param vProductWithSpCpProductStateList
	 * @param userinfoList
	 * @param vProductRelationList
	 * @return
	 */
	public  ArrayList authenticationProcess(String systemId,
			SpProductVo spProductVo, String rule, ArrayList vProductWithSpCpProductStateList,
			ArrayList userinfoList, ArrayList vProductRelationList) {
		ArrayList lst = new ArrayList();

		if (rule != null && !rule.equals("")) {
			String ruleStr[] = rule.split(",");
			for (int i = 0; i < ruleStr.length; i++) {
				String ruleId = ruleStr[i];
				if (!ruleId.equals("")) {
//					 ���ϵͳ��ʶ��CRM(����201),�򲻴���ruleId��4�Ķ�����Ȩ �����Ĵ���
					if ("201".equals(systemId) && this.provinceCode.equals(CrmConstants.GX_PROV_CODE)) {
						int idx = "4".indexOf(ruleId);
						if (idx != -1)
							continue;
					}
					//�������ػ�����crm��Ȩ�����û�״̬�ļ�Ȩ coolan modify
					if ("201".equals(systemId) && CrmConstants.JX_PROV_CODE.equals(this.provinceCode)) {
						int idx = "3".indexOf(ruleId);
						if (idx != -1)
							continue;
					}
					HashMap map = new HashMap();
					HashMap inMap = new HashMap();
					inMap.put("SpProductVo", spProductVo);
					inMap.put("systemId", systemId);
					inMap.put("spProductLst", vProductWithSpCpProductStateList);
					inMap.put("productInfoLst", userinfoList);
					inMap.put("bizRestraintLst", vProductRelationList);
					PartRuleFactory ruleFactory = new PartRuleFactory();
					map = ruleFactory.match(ruleId, inMap);
					if ((String) map.get("Result") != null
							&& !((String) map.get("Result")).equals("0")) {
						lst.add(map);
						// break;
					}
				}
			}
		}

		return lst;
	}

	public  ArrayList getResultLst(ArrayList lst, String systemId) {
		ArrayList retLst = new ArrayList();
		String ResultCode = "0";// ������
		String ResultDesc = "";// ���ؽ��
		if (lst != null && lst.size() > 0) {
			for (int i = 0; i < lst.size(); i++) {
				HashMap map = (HashMap) lst.get(i);
				String Result = (String) map.get("Result");
				String ProductNo = (String) map.get("ProductNo");
				String SPProdSpecName = (String) map.get("SPProdSpecName");
				String FailureNote = (String) map.get("FailureNote");
				int res = Integer.parseInt(Result);
				if (res > 0) {// ��Ȩʧ��
					ResultCode = "1";
					ResultDesc = ResultDesc + FailureNote + "|";
				}
			}
		} else {
			ResultCode = "0";// ������
			ResultDesc = "�ɹ�";// ���ؽ��
		}
		if (!ResultDesc.equals("") && !ResultCode.equals("0")) {// ȥ������
			ResultDesc = ResultDesc.substring(0, ResultDesc.length() - 1);
		}

		HashMap retMap = new HashMap();
		retMap.put("ResultCode", ResultCode);
		retMap.put("ResultDesc", ResultDesc);
		retMap.put("lst", lst);
		retLst.add(retMap);

		return retLst;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ArrayList lst = new ArrayList();
		HashMap spProductInfoMap = new HashMap();
		spProductInfoMap.put("SPProdSpecID", "1");
		spProductInfoMap.put("ActionType", "DEL");
		lst.add(spProductInfoMap);
		HashMap spProductInfoMap1 = new HashMap();
		spProductInfoMap1.put("SPProdSpecID", "2000040");
		spProductInfoMap1.put("ActionType", "ADD");
		// lst.add(spProductInfoMap1);

		ArrayList incrLst = new ArrayList();
		HashMap incrMap = new HashMap();
		incrMap.put("ProductId", "1");
		incrMap.put("ActionType", "ADD");
		incrLst.add(incrMap);

		HashMap productMap = new HashMap();
		productMap.put("SPProdInfo", lst);
		productMap.put("IncrProdInfo", incrLst);
		productMap.put("ProductNo", "2688640");
		productMap.put("ProductId", "2603929943");
		productMap.put("LanId", "1100");
		ArrayList productLst = new ArrayList();
		productLst.add(productMap);
		AuthenticationMainInner aAuthenticationMain=new AuthenticationMainInner();
		aAuthenticationMain.process(productLst, "1");
	}

	private ArrayList getSpInfoFromCache(String productids) {
		// cooltan��д�ӻ����ȡ���� �������Ҳ�ı�
		ArrayList lst = new ArrayList();
		String[] products = productids.split(",");
		if (products != null && products.length > 0) {
			for (int i = 0; i < products.length; i++) {
				String productId = products[i];
				SpProductVo spProductVo = new SpProductVo();
				spProductVo.setServiceId(productId);
				// ��ȡ��Ʒ����ҵ������
				String serviceCapabilitys = this
						.getProdServiceAbilitysSpliByComma(productId);
				spProductVo.setServiceCapabilityId(serviceCapabilitys);
				spProductVo.setState(DcSystemParamManager.getInstance()
						.getProductStateById(productId));
				spProductVo.setSpState(DcSystemParamManager.getInstance()
						.getSpcpStateById(productId));
				spProductVo.setNameCn(DcSystemParamManager.getInstance()
						.getProductnameById(productId));
				lst.add(spProductVo);
			}
		}
		return lst;

	}

	private String getProdServiceAbilitysSpliByComma(String productId) {
		StringBuffer serviceCapabilitys = new StringBuffer("");
		List prodServiceAbilitys = DcSystemParamManager.getInstance()
				.getProductServiceAbilitysByid(productId);
		if (prodServiceAbilitys != null) {
			for (int j = 0; j < prodServiceAbilitys.size(); j++) {
				String tmp = (String) prodServiceAbilitys.get(j);
				serviceCapabilitys.append(tmp + ",");
			}
		}
		return serviceCapabilitys.toString();
	}

	private ArrayList getBizRestraintFromCache(String productids)
			throws SQLException {
		// cooltan��д�ӻ����ȡ����
		ArrayList lst = new ArrayList();
		String[] products = productids.split(",");
		if (products != null && products.length > 0) {
			// ����ѭ��
			for (int i = 0; i < products.length; i++) {
				String productId = products[i];
				List prodRelations = DcSystemParamManager.getInstance()
						.getProdRelationById(productId);
				if (prodRelations != null) {
					for (int j = 0; j < prodRelations.size(); j++) {
						ProdRelationVO vo = (ProdRelationVO) prodRelations
								.get(j);
						BizRestraintVo bizRestraintVo = new BizRestraintVo();
						bizRestraintVo.setaObjectId(vo.getProdId());
						bizRestraintVo.setzObjectId(vo.getProProdId());
						String aObjectName = DcSystemParamManager.getInstance()
								.getProductnameById(vo.getProdId());
						String zObjectName = DcSystemParamManager.getInstance()
								.getProductnameById(vo.getProProdId());
						bizRestraintVo.setaObjectName(aObjectName);
						bizRestraintVo.setzObjectName(zObjectName);
						bizRestraintVo.setRestraintType(vo.getRelationTypeCd());
						lst.add(bizRestraintVo);
					}
				}

			}
		}
		return lst;

	}
}
