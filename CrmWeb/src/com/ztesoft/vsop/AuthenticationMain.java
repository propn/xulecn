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
		ArrayList spProductLst = new ArrayList(); //��¼��ֵ��Ʒ��Ӧ��spcp״̬����Ʒ״̬
		ArrayList productInfoLst = new ArrayList();//��¼�û���Ϣ��������״̬��������Ʒҵ�������б�������ϵʵ��
		ArrayList bizRestraintLst = new ArrayList();//��¼��ֵ��Ʒ��Ӧ�Ĳ�Ʒ��ϵ
		//�����ж�
		if(productInfo==null||productInfo.size()==0){
			String result = "1";//0�ɹ���1ʧ��
			HashMap map = new HashMap();
			map.put("Result","1");
			map.put("ProductNo","-1");
			map.put("SPProdSpecId","-1");
			map.put("SPProdSpecName","-1");
			map.put("FailureNote","����ĺ�����ϢΪ�գ���Ȩʧ��");
			lst.add(map);
			return lst;
		}
		if(systemId==null||systemId.equals("")){
			String result = "1";//0�ɹ���1ʧ��
			HashMap map = new HashMap();
			map.put("Result","1");
			map.put("ProductNo","-1");
			map.put("SPProdSpecId","-1");
			map.put("SPProdSpecName","-1");
			map.put("FailureNote","��Դϵͳ��ʶΪ�գ���Ȩʧ��");
			lst.add(map);
			return lst;
		}
		
		//Ҫ�������ֵ��Ʒ��
		String SPProdSpecIDStr = "";
		ArrayList spProdActionLst = new ArrayList();//��¼ÿ����ֵ��Ʒ�Ķ���
		ArrayList incrProductLst = new ArrayList();//��¼ÿ��������Ʒ
		//��ȡ��Ҫ��Ȩ����ֵ��Ʒ���ö��ŷָ
		for(int i=0;i<productInfo.size();i++){
			HashMap productMap = (HashMap)productInfo.get(i);
			String productNo = (String)productMap.get("ProductNo");//�û�����
			String productId= (String)productMap.get("ProductId");//�û���ʶ
			String lanId = (String)productMap.get("LanId");//��������ʶ
			ArrayList SPProdInfoLst = (ArrayList)productMap.get("SPProdInfo");//��ֵ��Ʒ�б�
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
			incrProductLst = (ArrayList)productMap.get("IncrProdInfo");//������Ʒ�б�
			if(incrProductLst!=null&&incrProductLst.size()>0){
				for(int j=0;j<incrProductLst.size();j++){
					HashMap incrProductMap = (HashMap)incrProductLst.get(j);
					incrProductMap.put("ProductNo",productNo);
				}
			}
		}
		
		if(!SPProdSpecIDStr.equals("")){//ȥ������
			SPProdSpecIDStr = SPProdSpecIDStr.substring(0,SPProdSpecIDStr.length()-1);
		}
		Connection conn=null;
		try{
			conn = LegacyDAOUtil.getConnection();
			//��ѯ��Ʒ״̬��������ҵ�������Լ�SP/CP״̬
//			SpProductVo spProductVo= new SpProductVo(); 
			CommonDAO commonDAO = new CommonDAO();
			s = System.currentTimeMillis();
			//cooltan��д�ӻ����ȡ����
			//spProductLst = commonDAO.getSpInfo(conn," PRODUCT_ID in ("+SPProdSpecIDStr+")");
			spProductLst = commonDAO.getSpInfoFromCache(SPProdSpecIDStr);
			logger.info("#commonDAO.getSpInfo cost->" + (System.currentTimeMillis()-s));
			//��ѯԼ����ϵ
			s = System.currentTimeMillis();
			//cooltan��д�ӻ����ȡ����
			//bizRestraintLst = commonDAO.getBizRestraint(conn,SPProdSpecIDStr);
			bizRestraintLst = commonDAO.getBizRestraintFromCache(SPProdSpecIDStr);
			logger.info("#commonDAO.getBizRestraint cost->" + (System.currentTimeMillis()-s));
			
			
			//��ѯ�û�ʵ������ֵҵ��ʵ����ҵ������ʵ����
			for(int i=0;i<productInfo.size();i++){
				HashMap productMap = (HashMap)productInfo.get(i);
				String productNo = (String)productMap.get("ProductNo");//�û�����
				String productId= (String)productMap.get("ProductId");//�û���ʶ
				String lanId = (String)productMap.get("LanId");//��������ʶ
				String userState =(String)productMap.get("UserState");
			    String payMode=(String)productMap.get("PayMode");
			    String prodInstId = (String)productMap.get("ProdInstId");
				s = System.currentTimeMillis();
				ProductVo productVo =null;
				if("201".equals(systemId)){
					 productVo = commonDAO.getProductInfo(conn,productNo, productId);
				}else{//�������crm�����ģ�����Ҫ�ٲ�ѯ����Ʒʵ��
					productVo=new ProductVo();
					productVo.setProductNo(productNo);
					productVo.setLanId(lanId);
					productVo.setUserState(userState);
					productVo.setPaymentModeCd(payMode);
					productVo.setProductSpec(productId);
					productVo.setProductId(prodInstId);
				}
				
				logger.info("#commonDAO.getProductInfo cost->" + (System.currentTimeMillis()-s));
				
				if(productVo.getProductId()==null||productVo.getProductId().equals("")){//��װ���û�
					productVo.setProductId("-1");
					productVo.setProductNo(productNo);
					productVo.setLanId(lanId);
					productVo.setUserState("00A");
				}else{//���û�����ҵ������ʵ���Լ���ֵҵ������
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
			//����׼����Ͻ��м�Ȩ����
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
//		�ع����ؽ��
		lst = getResultLst(lst,systemId);
		return lst;
	}
	/*
	�������Ϊ����Ȩ��ֵ��Ʒ�б�������ֵ��Ʒ�б�����ĸ�����Ʒ�����ò�Ʒ�б���ֵ��Ʒ��ϵ�б�
	*/
	public static ArrayList authentication(String systemId,ArrayList spProdActionLst,ArrayList incrProductLst,ArrayList spProductLst,ArrayList productInfoLst,ArrayList bizRestraintLst){
		ArrayList lst = new ArrayList();
		//����Ҫ��Ȩ����ֵ��ƷĬ�ϼ�Ȩ�ɹ����������ȷŵ�������ֵ��Ʒ�У�ɾ�������ȴ����ò�Ʒ��ɾ����
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
		
		//������ĸ�����Ʒд�뵽������Ʒ�б���
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
		
		
		//����׼����ɣ�����һ��ѭ�������и�����Ȩ����ĵ���
		
		//cooltan ���õļ�Ȩ��Ҫ��������������Ҫÿ����ֵ��Ʒ��ȥ��Ȩ
		HashMap ProductMap = new HashMap();
		for(int i=0;i<spProdActionLst.size();i++){
			SpProductVo spProductVo= null;//new SpProductVo();
			spProductVo = (SpProductVo)spProdActionLst.get(i);
			ArrayList lst1 = new ArrayList();
			if(spProductVo.getType().equalsIgnoreCase("ADD")||spProductVo.getType().equalsIgnoreCase("0")){
				if(ProductMap.get(spProductVo.getServiceId())!=null&&!ProductMap.get(spProductVo.getServiceId()).equals("")){
					HashMap resultMap = new HashMap();
					resultMap.put("Result","11");//ʧ��
					resultMap.put("ProductNo",spProductVo.getProductNo());//����
					resultMap.put("SPProdSpecId",spProductVo.getServiceId());
					resultMap.put("SPProdSpecName",spProductVo.getNameCn());
					resultMap.put("FailureNote","����ͬʱ������ͬ����ֵ��Ʒ"+spProductVo.getNameCn());
					lst.add(resultMap);
					return lst;
				}
				else{
					ProductMap.put(spProductVo.getServiceId(),"add");
				}
				//������ȡ�����ļ�Ȩ˳��
				String rule = DcSystemParamManager.getParameter("RULE_ADD");
				if(rule==null||"".equals(rule))	
					rule = CrmParamsConfig.getInstance().getParamValue(
				"RULE_ADD");
				lst1 = authenticationProcess(systemId,spProductVo,rule,spProductLst,productInfoLst,bizRestraintLst);
			}
			else if(spProductVo.getType().equalsIgnoreCase("DEL")||spProductVo.getType().equalsIgnoreCase("1")){
				//�˶���ȡ�˶��ļ�Ȩ˳��		
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
					
					//���ϵͳ��ʶ��CRM(����201),�򲻴���ruleId��1,2,3,4,5�Ķ�����Ȩ
					if("201".equals(systemId)){
						int idx = "1,2,3,4,5".indexOf(ruleId);
						if(idx != -1) continue;
					}
					
					//���������Ǻ󸶷�,Ҳ������5(��Ҫ�ڲ�ѯ�û�״̬�Ľṹ������һ�����������ֶηŵ�VO��) ����֤��ʵ����rule5�������
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
		String ResultCode = "0";//������
		String ResultDesc = "";//���ؽ��
		if(lst!=null&&lst.size()>0){
			for(int i=0;i<lst.size();i++){
				HashMap map = (HashMap)lst.get(i);
				String Result = (String)map.get("Result");
				String ProductNo = (String)map.get("ProductNo");
				String SPProdSpecName = (String)map.get("SPProdSpecName");
				String FailureNote = (String)map.get("FailureNote");
				
				int res = Integer.parseInt(Result);
				if(res>0){//��Ȩʧ��
					ResultCode = "1";
					ResultDesc = ResultDesc + FailureNote+"|";
				}
				
			}
		}
		else{
			ResultCode = "0";//������
			ResultDesc = "�ɹ�";//���ؽ��
		}
		if(!ResultDesc.equals("")&&!ResultCode.equals("0")){//ȥ������
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
