package com.ztesoft.vsop;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.vsop.web.DcSystemParamManager;


public class AuthenticationMain {
	private static Logger logger = Logger.getLogger(AuthenticationMain.class);
	public static ArrayList process(ArrayList productInfo,String systemId) throws Exception{
		logger.info("start authentication");
		long s = System.currentTimeMillis();
		ArrayList lst = new ArrayList();
		ArrayList spProductLst = new ArrayList(); //记录增值产品对应的spcp状态、产品状态
		ArrayList productInfoLst = new ArrayList();//记录用户信息包括号码状态、附属产品业务能力列表、订购关系实例
		ArrayList bizRestraintLst = new ArrayList();//记录增值产品对应的产品关系
		//数据判断
		if(productInfo==null||productInfo.size()==0){
			String result = "1";//0成功，1失败
			HashMap map = new HashMap();
			map.put("Result","1");
			map.put("ProductNo","-1");
			map.put("SPProdSpecId","-1");
			map.put("SPProdSpecName","-1");
			map.put("FailureNote","传入的号码信息为空，鉴权失败");
			lst.add(map);
			return lst;
		}
		if(systemId==null||systemId.equals("")){
			String result = "1";//0成功，1失败
			HashMap map = new HashMap();
			map.put("Result","1");
			map.put("ProductNo","-1");
			map.put("SPProdSpecId","-1");
			map.put("SPProdSpecName","-1");
			map.put("FailureNote","来源系统标识为空，鉴权失败");
			lst.add(map);
			return lst;
		}
		
		//要处理的增值产品串
		String SPProdSpecIDStr = "";
		ArrayList spProdActionLst = new ArrayList();//记录每个增值产品的动作
		ArrayList incrProductLst = new ArrayList();//记录每个附属产品
		//获取需要鉴权的增值产品，用逗号分割。
		for(int i=0;i<productInfo.size();i++){
			HashMap productMap = (HashMap)productInfo.get(i);
			String productNo = (String)productMap.get("ProductNo");//用户号码
			String productId= (String)productMap.get("ProductId");//用户标识
			String lanId = (String)productMap.get("LanId");//本地网标识
			ArrayList SPProdInfoLst = (ArrayList)productMap.get("SPProdInfo");//增值产品列表
			if(SPProdInfoLst!=null&&SPProdInfoLst.size()>0){
				for(int j=0;j<SPProdInfoLst.size();j++){
					HashMap spProductInfoMap = (HashMap)SPProdInfoLst.get(j);
					String SPProdSpecID = (String)spProductInfoMap.get("SPProdSpecID");
					String action = (String)spProductInfoMap.get("ActionType");
					String  ProductOfferId= (String)spProductInfoMap.get("ProductOfferId");
					String  ProductOfferType= (String)spProductInfoMap.get("ProductOfferType");

					SPProdSpecIDStr = SPProdSpecIDStr + SPProdSpecID +",";
					
					SpProductVo spProductVo= new SpProductVo();
					spProductVo.setServiceId(SPProdSpecID);
					spProductVo.setType(action);
					spProductVo.setProductNo(productNo);
					spProductVo.setNewProductOfferId(ProductOfferId);
					spProductVo.setNewProductOfferType(ProductOfferType);
					spProdActionLst.add(spProductVo);
				}
			}
			incrProductLst = (ArrayList)productMap.get("IncrProdInfo");//附属产品列表
			if(incrProductLst!=null&&incrProductLst.size()>0){
				for(int j=0;j<incrProductLst.size();j++){
					HashMap incrProductMap = (HashMap)incrProductLst.get(j);
					incrProductMap.put("ProductNo",productNo);
				}
			}
		}
		
		if(!SPProdSpecIDStr.equals("")){//去掉逗号
			SPProdSpecIDStr = SPProdSpecIDStr.substring(0,SPProdSpecIDStr.length()-1);
		}
		Connection conn=null;
		try{
			conn = LegacyDAOUtil.getConnection();
			//查询产品状态，依赖的业务能力以及SP/CP状态
//			SpProductVo spProductVo= new SpProductVo(); 
			CommonDAO commonDAO = new CommonDAO();
			s = System.currentTimeMillis();
			//cooltan改写从缓存读取数据
			//spProductLst = commonDAO.getSpInfo(conn," PRODUCT_ID in ("+SPProdSpecIDStr+")");
			spProductLst = commonDAO.getSpInfoFromCache(SPProdSpecIDStr);
			logger.info("#commonDAO.getSpInfo cost->" + (System.currentTimeMillis()-s));
			//查询约束关系
			s = System.currentTimeMillis();
			//cooltan改写从缓存读取数据
			//bizRestraintLst = commonDAO.getBizRestraint(conn,SPProdSpecIDStr);
			bizRestraintLst = commonDAO.getBizRestraintFromCache(SPProdSpecIDStr);
			logger.info("#commonDAO.getBizRestraint cost->" + (System.currentTimeMillis()-s));
			
			
			//查询用户实例，增值业务实例，业务能力实例。
			for(int i=0;i<productInfo.size();i++){
				HashMap productMap = (HashMap)productInfo.get(i);
				String productNo = (String)productMap.get("ProductNo");//用户号码
				String productId= (String)productMap.get("ProductId");//用户标识
				String lanId = (String)productMap.get("LanId");//本地网标识
				String userState =(String)productMap.get("UserState");
			    String payMode=(String)productMap.get("PayMode");
			    String prodInstId = (String)productMap.get("ProdInstId");
				s = System.currentTimeMillis();
				ProductVo productVo =null;
				if("201".equals(systemId)){
					 productVo = commonDAO.getProductInfo(conn,productNo, productId);
				}else{//如果不是crm过来的，不需要再查询主产品实例
					productVo=new ProductVo();
					productVo.setProductNo(productNo);
					productVo.setLanId(lanId);
					productVo.setUserState(userState);
					productVo.setPaymentModeCd(payMode);
					productVo.setProductSpec(productId);
					productVo.setProductId(prodInstId);
				}
				
				logger.info("#commonDAO.getProductInfo cost->" + (System.currentTimeMillis()-s));
				
				if(productVo.getProductId()==null||productVo.getProductId().equals("")){//新装的用户
					productVo.setProductId("-1");
					productVo.setProductNo(productNo);
					productVo.setLanId(lanId);
					productVo.setUserState("00A");
				}else{//老用户查找业务能力实例以及增值业务能力
					s = System.currentTimeMillis();
					//sproductVo = 
					commonDAO.getServiceCapability(conn,productVo);
					logger.info("#commonDAO.getServiceCapability cost->" + (System.currentTimeMillis()-s));
					s = System.currentTimeMillis();
					//productVo =
					commonDAO.getIncrProdInst(conn,productVo,productNo);
					logger.info("#commonDAO.getIncrProdInst cost->" + (System.currentTimeMillis()-s));
				}	
				productInfoLst.add(productVo);
			}
			logger.info("auth data prepare cost " + (System.currentTimeMillis() - s));
			s = System.currentTimeMillis();
			//数据准备完毕进行鉴权调用
			lst = authentication(systemId,spProdActionLst,incrProductLst,spProductLst,productInfoLst,bizRestraintLst);
			logger.info("authentication cost " + (System.currentTimeMillis() - s));
		}
		catch(Exception se){
			se.printStackTrace();
			logger.error("#process ex.", se);
			throw se;
		}
		finally{
			LegacyDAOUtil.releaseConnection(conn);
		}
		logger.info("end authentication.");
//		重构返回结果
		lst = getResultLst(lst,systemId);
		return lst;
	}
	/*
	传入参数为：鉴权增值产品列表，规格层增值产品列表，变更的附属产品，在用产品列表，增值产品关系列表
	*/
	public static ArrayList authentication(String systemId,ArrayList spProdActionLst,ArrayList incrProductLst,ArrayList spProductLst,ArrayList productInfoLst,ArrayList bizRestraintLst){
		ArrayList lst = new ArrayList();
		//将需要鉴权的增值产品默认鉴权成功，新增的先放到在用增值产品中，删除的则先从在用产品中删除。
		for(int i=0;i<spProdActionLst.size();i++){
			SpProductVo spProductVo=null;// new SpProductVo();
			spProductVo = (SpProductVo)spProdActionLst.get(i);
			for(int j=0;j<spProductLst.size();j++){
				SpProductVo spProductSpcVo= null;//new SpProductVo();
				spProductSpcVo = (SpProductVo)spProductLst.get(j);
				if(spProductVo.getServiceId().equals(spProductSpcVo.getServiceId())){
					spProductVo.setNameCn(spProductSpcVo.getNameCn());
					spProductVo.setServiceCapabilityId(spProductSpcVo.getServiceCapabilityId());
					spProductVo.setSpState(spProductSpcVo.getSpState());
					spProductVo.setState(spProductSpcVo.getState());
				}
				for(int k=0;k<productInfoLst.size();k++){
					ProductVo productVo = (ProductVo)productInfoLst.get(k);
					
					
					if(productVo.getProductNo().equals(spProductVo.getProductNo())){
						ArrayList spInfoLst = null;//new  ArrayList();
						spInfoLst = productVo.getSpProductInfo();
						if(spProductVo.getType().equalsIgnoreCase("ADD")||spProductVo.getType().equalsIgnoreCase("0")){
							spInfoLst.add(spProductVo);
						}
						else if(spProductVo.getType().equalsIgnoreCase("DEL")||spProductVo.getType().equalsIgnoreCase("1")){
							for(int m=0;m<spInfoLst.size();m++){
								SpProductVo spProductInstVo= null;//new SpProductVo();
								spProductInstVo = (SpProductVo)spInfoLst.get(i);
								if(spProductInstVo.getServiceId().equals(spProductVo.getServiceId())){
									spProductInstVo.setState("00X");
								}
							}
						}
					}
				}
			}
		}
		
		//将变更的附属产品写入到附属产品列表中
		if(incrProductLst!=null){
			for(int i=0;i<incrProductLst.size();i++){
				HashMap incrProductMap = (HashMap)incrProductLst.get(i);
				String productNo = (String)incrProductMap.get("ProductNo");
				String productId = (String)incrProductMap.get("ProductId");
				String actionType = (String)incrProductMap.get("ActionType");
				for(int k=0;k<productInfoLst.size();k++){
					ProductVo productVo = (ProductVo)productInfoLst.get(k);
					if(productVo.getProductNo().equals(productNo)){
						ArrayList productLst = productVo.getServiceCapability();
						if(productLst==null){
							ArrayList lst1 = new ArrayList();
							if(actionType.equalsIgnoreCase("ADD")||actionType.equalsIgnoreCase("0")){
								HashMap map = new HashMap();
								map.put("productId",productId);
								map.put("productName","");
								lst1.add(map);
								productVo.setServiceCapability(lst1);
							}
						}
						else{
							if(actionType.equalsIgnoreCase("ADD")||actionType.equalsIgnoreCase("0")){
								HashMap map = new HashMap();
								map.put("productId",productId);
								map.put("productName","");
								productLst.add(map);
								productVo.setServiceCapability(productLst);
							}
							else if(actionType.equalsIgnoreCase("DEL")||actionType.equalsIgnoreCase("1")){
								for(int a=0;a<productLst.size();a++){
									HashMap map = (HashMap)productLst.get(a);
									if(productId.equals((String)map.get("productId"))){
										productLst.remove(a);
									}
								}
							}
						}
						
					}
				}
				
			}
		}
		
		
		//数据准备完成，再做一次循环，进行各个鉴权组件的调用
		
		//cooltan 公用的鉴权需要独立出来，不需要每个增值产品都去鉴权
		HashMap ProductMap = new HashMap();
		for(int i=0;i<spProdActionLst.size();i++){
			SpProductVo spProductVo= null;//new SpProductVo();
			spProductVo = (SpProductVo)spProdActionLst.get(i);
			ArrayList lst1 = new ArrayList();
			if(spProductVo.getType().equalsIgnoreCase("ADD")||spProductVo.getType().equalsIgnoreCase("0")){
				if(ProductMap.get(spProductVo.getServiceId())!=null&&!ProductMap.get(spProductVo.getServiceId()).equals("")){
					HashMap resultMap = new HashMap();
					resultMap.put("Result","11");//失败
					resultMap.put("ProductNo",spProductVo.getProductNo());//号码
					resultMap.put("SPProdSpecId",spProductVo.getServiceId());
					resultMap.put("SPProdSpecName",spProductVo.getNameCn());
					resultMap.put("FailureNote","不能同时订购相同的增值产品"+spProductVo.getNameCn());
					lst.add(resultMap);
					return lst;
				}
				else{
					ProductMap.put(spProductVo.getServiceId(),"add");
				}
				//订购获取订购的鉴权顺序
				String rule = DcSystemParamManager.getParameter("RULE_ADD");
				if(rule==null||"".equals(rule))	
					rule = CrmParamsConfig.getInstance().getParamValue(
				"RULE_ADD");
				lst1 = authenticationProcess(systemId,spProductVo,rule,spProductLst,productInfoLst,bizRestraintLst);
			}
			else if(spProductVo.getType().equalsIgnoreCase("DEL")||spProductVo.getType().equalsIgnoreCase("1")){
				//退订获取退订的鉴权顺序		
				String rule = DcSystemParamManager.getParameter("RULE_DEL");
				if(rule==null||"".equals(rule))	
					rule = CrmParamsConfig.getInstance().getParamValue(
				"RULE_DEL");
				lst1 = authenticationProcess(systemId,spProductVo,rule,spProductLst,productInfoLst,bizRestraintLst);
			}
			if(lst1!=null){
				for(int a=0;a<lst1.size();a++){
					lst.add((HashMap)lst1.get(a));
				}
			}
		}
		
		
		return lst;
	}
	
	public static ArrayList authenticationProcess(String systemId,SpProductVo spProductVo,String rule,ArrayList spProductLst,ArrayList productInfoLst,ArrayList bizRestraintLst){
		ArrayList lst = new ArrayList();
		
		if(rule!=null&&!rule.equals("")){
			String ruleStr[] = rule.split(",");
			for(int i=0;i<ruleStr.length;i++){
				String ruleId = ruleStr[i];
				if(!ruleId.equals("")){
					
					//如果系统标识是CRM(编码201),则不处理ruleId是1,2,3,4,5的订购鉴权
					if("201".equals(systemId)){
						int idx = "1,2,3,4,5".indexOf(ruleId);
						if(idx != -1) continue;
					}
					
					//付费类型是后付费,也不调用5(需要在查询用户状态的结构中增加一个付费类型字段放到VO中) 该验证的实现在rule5里面搞了
					//if ("0".equals(spProductVo.getPaymentModeCd())) continue;
					
					Rule1 rule1 = new Rule1();
					HashMap map = new HashMap();
					HashMap inMap = new HashMap();
					inMap.put("SpProductVo",spProductVo);
					inMap.put("systemId",systemId);
					inMap.put("spProductLst",spProductLst);
					inMap.put("productInfoLst",productInfoLst);
					inMap.put("bizRestraintLst",bizRestraintLst);
					PartRuleFactory ruleFactory = new PartRuleFactory();
					map = ruleFactory.match(ruleId,inMap);
					if((String)map.get("Result")!=null&&!((String)map.get("Result")).equals("0")){
						lst.add(map);
						//break;
					}
				}
			}
		}
		
		return lst;
	}
	
	public static ArrayList getResultLst(ArrayList lst,String systemId){
		ArrayList retLst = new ArrayList();
		String ResultCode = "0";//返回码
		String ResultDesc = "";//返回结果
		if(lst!=null&&lst.size()>0){
			for(int i=0;i<lst.size();i++){
				HashMap map = (HashMap)lst.get(i);
				String Result = (String)map.get("Result");
				String ProductNo = (String)map.get("ProductNo");
				String SPProdSpecName = (String)map.get("SPProdSpecName");
				String FailureNote = (String)map.get("FailureNote");
				
				int res = Integer.parseInt(Result);
				if(res>0){//鉴权失败
					ResultCode = "1";
					ResultDesc = ResultDesc + FailureNote+"|";
				}
				
			}
		}
		else{
			ResultCode = "0";//返回码
			ResultDesc = "成功";//返回结果
		}
		if(!ResultDesc.equals("")&&!ResultCode.equals("0")){//去掉逗号
			ResultDesc = ResultDesc.substring(0,ResultDesc.length()-1);
		}
		
		HashMap retMap = new HashMap();
		retMap.put("ResultCode",ResultCode);
		retMap.put("ResultDesc",ResultDesc);
		retMap.put("lst",lst);
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
		spProductInfoMap.put("SPProdSpecID","1");
		spProductInfoMap.put("ActionType","DEL");
		lst.add(spProductInfoMap);
		HashMap spProductInfoMap1 = new HashMap();
		spProductInfoMap1.put("SPProdSpecID","2000040");
		spProductInfoMap1.put("ActionType","ADD");
		//lst.add(spProductInfoMap1);
		
		ArrayList incrLst = new ArrayList();
		HashMap incrMap = new HashMap();
		incrMap.put("ProductId","1");
		incrMap.put("ActionType","ADD");
		incrLst.add(incrMap);
		
		HashMap productMap = new HashMap();
		productMap.put("SPProdInfo",lst);
		productMap.put("IncrProdInfo",incrLst);
		productMap.put("ProductNo","2688640");
		productMap.put("ProductId","2603929943");
		productMap.put("LanId","1100");
		ArrayList productLst = new ArrayList();
		productLst.add(productMap);
		process(productLst,"1");
	}
}
