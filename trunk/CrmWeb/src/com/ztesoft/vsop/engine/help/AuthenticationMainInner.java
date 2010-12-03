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
 * 订购鉴权主控内部处理类 移植AuthenticationMain
 * @author cooltan
 * 
 */
public class AuthenticationMainInner {
	private static Logger logger = Logger.getLogger(AuthenticationMainInner.class);
	private static AuthenticationMainInner aAuthenticationMain=new AuthenticationMainInner();
	public static AuthenticationMainInner getInstance(){
		return aAuthenticationMain;
	}
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码

	/**
	 * 鉴权主控 准备数据2
	 * @param productInfo
	 *            用户信息：包括号码、订单增值产品列表
	 * @param systemId
	 *            订购系统
	 * @return
	 * @throws Exception
	 */
	public ArrayList process(ArrayList productInfo, String systemId)throws Exception {
		logger.info("start authentication");
		long s = System.currentTimeMillis();
		ArrayList lst = new ArrayList();//鉴权返回结果 List<Map>
		ArrayList vProductWithSpCpProductStateList = new ArrayList(); // 记录增值产品对应的spcp状态、产品状态
		ArrayList userinfoList = new ArrayList();// 记录用户信息包括号码状态、附属产品业务能力列表、订购关系实例
		ArrayList vProductRelationList = new ArrayList();// 记录增值产品对应的产品关系
		String vProductIds = "";// 要处理的增值产品串 ,多个增值产品用逗号分隔
		ArrayList vProductInCurrentOrder = new ArrayList();// 记录每个增值产品的动作
		ArrayList bizCapInstList = new ArrayList();// 记录每个附属产品
		// 传入参数合法性校验
		if (productInfo == null || productInfo.size() == 0) {
			String result = "1";// 0成功，1失败
			HashMap map = new HashMap();
			map.put("ResultCode", "1");
			map.put("ProductNo", "-1");
			map.put("SPProdSpecId", "-1");
			map.put("SPProdSpecName", "-1");
			map.put("ResultDesc", "传入的号码信息为空，鉴权失败");
			lst.add(map);
			return lst;
		}
		if (systemId == null || systemId.equals("")) {
			String result = "1";// 0成功，1失败
			HashMap map = new HashMap();
			map.put("ResultCode", "1");
			map.put("ProductNo", "-1");
			map.put("SPProdSpecId", "-1");
			map.put("SPProdSpecName", "-1");
			map.put("ResultDesc", "来源系统标识为空，鉴权失败");
			lst.add(map);
			return lst;
		}
		// 获取需要鉴权的增值产品，用逗号分割。
		for (int i = 0; i < productInfo.size(); i++) {
			HashMap productMap = (HashMap) productInfo.get(i);
			String productNo = (String) productMap.get("ProductNo");// 用户号码
			String productId = (String) productMap.get("ProductId");// 用户标识
			String lanId = (String) productMap.get("LanId");// 本地网标识
			ArrayList SPProdInfoLst = (ArrayList) productMap.get("SPProdInfo");// 增值产品列表
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
					String productNbr=(String)spProductInfoMap.get("VProductNbr");//增值产品外编码   --add 20101009新增，rule12使用
					
					if (vproductId == null || "".equals(vproductId)) {
						String result = "1";// 0成功，1失败
						HashMap map = new HashMap();
						map.put("ResultCode", "1");
						map.put("ProductNo", productNo);
						map.put("SPProdSpecId", "-1");
						map.put("SPProdSpecName", "-1");
						map.put("ResultDesc", "增值产品不存在，鉴权失败");
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
					spProductVo.setProductNbr(productNbr);//增值产品外编码   --add 20101009新增，rule12使用
					vProductInCurrentOrder.add(spProductVo);
				}
			}
			bizCapInstList = (ArrayList) productMap.get("IncrProdInfo");// 附属产品列表
			if (bizCapInstList != null && bizCapInstList.size() > 0) {
				for (int j = 0; j < bizCapInstList.size(); j++) {
					HashMap incrProductMap = (HashMap) bizCapInstList.get(j);
					incrProductMap.put("ProductNo", productNo);
				}
			}
		}
		if (!vProductIds.equals("")) {// 去掉逗号
			vProductIds = vProductIds.substring(0, vProductIds.length() - 1);
		}
		try {
			// 查询产品状态，依赖的业务能力以及SP/CP状态
			vProductWithSpCpProductStateList = this.getSpInfoFromCache(vProductIds);
			// 查询约束关系
			vProductRelationList = this.getBizRestraintFromCache(vProductIds);
			// 查询用户实例，增值业务实例，业务能力实例。
			for (int i = 0; i < productInfo.size(); i++) {
				HashMap productMap = (HashMap) productInfo.get(i);
				String productNo = (String) productMap.get("ProductNo");// 用户号码
				String productId = (String) productMap.get("ProductId");// 用户标识
				String lanId = (String) productMap.get("LanId");// 本地网标识
				String userState = (String) productMap.get("UserState");
				String payMode = (String) productMap.get("PayMode");
				String prodInstId = (String) productMap.get("ProdInstId");
				s = System.currentTimeMillis();
				ProductVo productVo = new ProductVo();
				if ("201".equals(systemId)) {// crm过来的要查询产品实例
					ProdInstHelpDao aProdInstHelpDao = new ProdInstHelpDao();
					ProdInstVO prodInstvo = null;
					// 广西本地化处理，查非00X的用户实例
					if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode)) {
						// 当true时非00X状态都查出来
						prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndProductId(productNo,
										productId, true);
					} else {
						// 当false时只有00A（正常）00B，00C状态为有效状态。
						prodInstvo = aProdInstHelpDao.qryProdInstByAccNbrAndProductId(productNo,
										productId, false);
					}
					if(null!=prodInstvo && null!=prodInstvo.getProdInstId()
							&& !"".equals(prodInstvo.getProdInstId())){//广西vsop里查出来的用户实例不空时才置入
						lanId = prodInstvo.getLanId();
						productNo = prodInstvo.getAccNbr();
						productId = prodInstvo.getProdId();
						userState = prodInstvo.getStateCd();
						payMode = prodInstvo.getPaymentModeCd();
					}
				} else {// 如果不是crm过来的，不需要再查询主产品实例
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
						|| productVo.getProductId().equals("")) {// 新装的用户
					productVo.setProductId("-1");
					productVo.setProductNo(productNo);
					productVo.setLanId(lanId);
					productVo.setUserState("00A");
					productVo.setPaymentModeCd("0");//新装时默认为预付费用户
					//江西的用户业务能力可以是默认就开通的，需要特殊处理
					if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
						handleDefaultBizCapForIsmp(productVo,productId);
					}
					//end 江西的用户业务能力
				} else {// 老用户查找业务能力实例以及查找订购关系实例
					// 查找业务能力实例
					s = System.currentTimeMillis();
					BizCapInstHelpDao aBizCapInstHelpDao = new BizCapInstHelpDao();
					List tmpBizCapInstList = aBizCapInstHelpDao
							.qryBizCapInstsByProdInstId(prodInstId);
					productVo.setServiceCapability(this
							.findBizCapService(tmpBizCapInstList));
					logger.info("#qryBizCapInstsByProdInstId cost->"
							+ (System.currentTimeMillis() - s));
					//江西的用户业务能力可以是默认就开通的，需要特殊处理
					if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
						handleDefaultBizCapForIsmp(productVo,productId);
					}
					//end 江西的用户业务能力
					// 查找订购关系实例
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
			// 数据准备完毕进行鉴权调用
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
		// 重构返回结果
		lst = this.getResultLst(lst, systemId);
		return lst;
	}

	/**
	 * 江西的用户业务能力可以是默认就开通的，需要特殊处理
	 * @param productVo  用户实例
	 * @param productId  用户类型
	 * @author yi.chengfeng 2010-8-26
	 */
	private void handleDefaultBizCapForIsmp(ProductVo productVo,
			String productId) {
		String msisdn = DcSystemParamManager.getParameter(VsopConstants.DC_MSISDN);
		String ismpDefault = "";
		//如果是手机，加载c网用户的默认业务能力
		if(msisdn.indexOf(productId) != -1){
			ismpDefault = DcSystemParamManager.getParameter(VsopConstants.ISMP_MSISDN_DEF_SERVICE_CODE);
		}else{
			//否则加载p网的业务能力
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
	 * 多个增值产品鉴权
	 * 
	 */
	public  ArrayList authentication(String systemId,
			ArrayList vProductInCurrentOrder, ArrayList bizCapInstList,
			ArrayList vProductWithSpCpProductStateList, ArrayList userinfoList,
			ArrayList vProductRelationList) {
		ArrayList lst = new ArrayList();
		// 将需要鉴权的增值产品默认鉴权成功，新增的先放到在用增值产品中，删除的则先从在用产品中删除。
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
		// 将变更的附属产品写入到附属产品列表中
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
		// 数据准备完成，再做一次循环，进行各个鉴权组件的调用
		// cooltan 公用的鉴权需要独立出来，不需要每个增值产品都去鉴权
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
					resultMap.put("Result", "11");// 失败
					resultMap.put("ProductNo", spProductVo.getProductNo());// 号码
					resultMap.put("SPProdSpecId", spProductVo.getServiceId());
					resultMap.put("SPProdSpecName", spProductVo.getNameCn());
					resultMap.put("FailureNote", "不能同时订购相同的增值产品"
							+ spProductVo.getNameCn());
					lst.add(resultMap);
					return lst;
				} else {
					ProductMap.put(spProductVo.getServiceId(), "add");
				}
				// 订购获取订购的鉴权顺序
				String rule = CrmParamsConfig.getInstance().getParamValue("RULE_ADD");
				lst1 = this.authenticationProcess(systemId, spProductVo, rule,
						vProductWithSpCpProductStateList, userinfoList,
						vProductRelationList);
			} else if (spProductVo.getType().equalsIgnoreCase("DEL")
					|| spProductVo.getType().equalsIgnoreCase("1")) {
				// 退订获取退订的鉴权顺序
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
	 * 单个增值产品鉴权
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
//					 如果系统标识是CRM(编码201),则不处理ruleId是4的订购鉴权 广西的处理
					if ("201".equals(systemId) && this.provinceCode.equals(CrmConstants.GX_PROV_CODE)) {
						int idx = "4".indexOf(ruleId);
						if (idx != -1)
							continue;
					}
					//江西本地化处理，crm鉴权不做用户状态的鉴权 coolan modify
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
		String ResultCode = "0";// 返回码
		String ResultDesc = "";// 返回结果
		if (lst != null && lst.size() > 0) {
			for (int i = 0; i < lst.size(); i++) {
				HashMap map = (HashMap) lst.get(i);
				String Result = (String) map.get("Result");
				String ProductNo = (String) map.get("ProductNo");
				String SPProdSpecName = (String) map.get("SPProdSpecName");
				String FailureNote = (String) map.get("FailureNote");
				int res = Integer.parseInt(Result);
				if (res > 0) {// 鉴权失败
					ResultCode = "1";
					ResultDesc = ResultDesc + FailureNote + "|";
				}
			}
		} else {
			ResultCode = "0";// 返回码
			ResultDesc = "成功";// 返回结果
		}
		if (!ResultDesc.equals("") && !ResultCode.equals("0")) {// 去掉逗号
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
		// cooltan改写从缓存读取数据 传入参数也改变
		ArrayList lst = new ArrayList();
		String[] products = productids.split(",");
		if (products != null && products.length > 0) {
			for (int i = 0; i < products.length; i++) {
				String productId = products[i];
				SpProductVo spProductVo = new SpProductVo();
				spProductVo.setServiceId(productId);
				// 获取产品关联业务能力
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
		// cooltan改写从缓存读取数据
		ArrayList lst = new ArrayList();
		String[] products = productids.split(",");
		if (products != null && products.length > 0) {
			// 二层循环
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
