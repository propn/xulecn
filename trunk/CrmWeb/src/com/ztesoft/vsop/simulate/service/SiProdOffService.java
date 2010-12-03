package com.ztesoft.vsop.simulate.service;


import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.PageModel;


public class SiProdOffService  {
	
	//原装方法
//	public boolean insertProdOff(HashMap ProdOff ) throws Exception {
//		Map param = new HashMap() ;
//		param.put("ProdOff", ProdOff) ;
//		boolean result = DataTranslate._boolean(
//				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
//						"insertProdOff" ,param)) ;
//		return result ;
//	}
	
	/**
	 * 改装方法，底层新增成功后返回的是新增对象的主键id
	 */
	public boolean insertProdOff(HashMap ProdOff ) throws Exception {
		Map param = new HashMap();
		param.put("ProdOff", ProdOff);

		String prodOfferId = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"insertProdOffSelf" ,param));
		System.out.println("所新增销售品的id为============>" + prodOfferId);
		ProdOff.put("prod_offer_id", prodOfferId);
		
		//成功增加了才执行写xml的操作
		if (null != prodOfferId) {
			writeProdOffToXML(ProdOff, "1");
		}
		
		return prodOfferId != null ;
	}
	
	public boolean updateProdOff(HashMap ProdOff ) throws Exception {
		Map param = new HashMap();
		param.put("ProdOff", ProdOff);
		System.out.println("所更新销售品的id为============>" + ProdOff.get("prod_offer_id"));
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"updateProdOff" ,param));
		
		if (result) {
			writeProdOffToXML(ProdOff, "2");
		}
		
		return result;
	}
	
	/**
	 * 根据查询条件查找所有销售品
	 * @param offer_nbr
	 * @param prod_offer_name
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOffData(String offer_nbr , String prod_offer_name , 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("offer_nbr", offer_nbr) ;
		param.put("prod_offer_name", prod_offer_name) ;
		//param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProdOffData" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 新增销售品关系时，点击Z销售品时Z销售品的选择范围
	 * @param prod_off_id
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProdOff(String offer_nbr, String prod_offer_name, 
			String prod_off_id, int pageIndex, int pageSize) throws Exception {
		
		Map param = new HashMap();
		param.put("offer_nbr", offer_nbr);
		param.put("prod_offer_name", prod_offer_name);
		param.put("prod_off_id", prod_off_id);
		param.put("pageIndex", new Integer(pageIndex));
		param.put("pageSize", new Integer(pageSize));
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProdOff" ,param));
		
		return result ;
	}	
	
	/**
	 * 销售品中的tab，销售品构成中调用到
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProduct(int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		//param.put("iParam3", iParam3) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"searchProduct" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 根据主键 prod_offer_id 获取销售品
	 * @param prod_offer_id
	 * @return
	 * @throws Exception
	 */
	public Map getProdOffById(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id) ;
		
		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"getProdOffById" ,param)) ;
		
		return result ;
	}
	

	public List findProdOffByCond(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id) ;
		
		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"findProdOffByCond" ,param)) ;
		
		return result ;
	}
	
	/**
	 * 根据主键 prod_offer_id 删除销售品
	 * @param prod_offer_id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteProdOffById(String prod_offer_id) throws Exception {
		Map param = getProdOffKeyMap(prod_offer_id) ;

		HashMap ProdOff = (HashMap) getProdOffById(prod_offer_id);
		
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"deleteProdOffById" ,param)) ;
		if (result) {
			writeProdOffToXML(ProdOff, "3");
		}
		
		return result ;
	}
	
	/**
	 * 用Map封装参数
	 * @param prod_offer_id
	 * @return
	 */
	private Map getProdOffKeyMap(String prod_offer_id){
		Map param = new HashMap() ;
		param.put("prod_offer_id", prod_offer_id) ;
		return param ;
	}
	
	/**
	 * 根据销售品id查找销售品关系列表
	 * @param offerId
	 * @return
	 * @throws Exception
	 */
	public List getProdOffRelByOfferId(String offerId) throws Exception {
		List list = null;
		Map map = new HashMap();
		map.put("prod_offer_id", offerId);
		list = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffRelBO,
						"getProdOffRelByOfferId", map)) ;
		return list;
	}
	
	/**
	 * 根据销售品id查找销售品构成列表
	 * @param offerId
	 * @return
	 * @throws Exception
	 */
	public List getProdOffDetaRoleByOfferId(String offerId) throws Exception {
		List list = null;
		Map map = new HashMap();
		map.put("prod_offer_id", offerId);
		list = DataTranslate._List(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffDetaRoleBO,
						"getProdOffDetaRoleByOfferId", map)) ;
		return list;
	}
	
	/**
	 * 把要同步的销售品写到XML中
	 * @param prodOff
	 * @param opFlag
	 * @return
	 */
	public String writeProdOffToXML(HashMap prodOff, String opFlag) throws Exception {
		
		String prodOfferId = String.valueOf(prodOff.get("prod_offer_id"));
		//创建一个xml文档
		Document doc = DocumentHelper.createDocument();
		//xml的第一级节点
		Element PackageInfoSyncReq = doc.addElement("PackageInfoSyncReq");
		
		/* xml第一级节点中的第一级子节点  Start */
		Element streamingNo = PackageInfoSyncReq.addElement("streamingNo");//流水号
		streamingNo.setText(getStreamingNo());
		
		Element OPFlag = PackageInfoSyncReq.addElement("OPFlag");//操作符
		OPFlag.setText(opFlag);
		
		Element PackageID = PackageInfoSyncReq.addElement("PackageID");//增值业务捆绑销售品ID
		PackageID.setText(prodOfferId);
		
		Element PNameCN = PackageInfoSyncReq.addElement("PNameCN");//中文产品组合名称
		PNameCN.setText(handleValue(prodOff.get("pname_cn")));
		
		Element PNameEN = PackageInfoSyncReq.addElement("PNameEN");//英文产品组合名称
		PNameEN.setText(handleValue(prodOff.get("pname_en")));
		
		Element PDesCN = PackageInfoSyncReq.addElement("PDesCN");//产品组合中文描述
		PDesCN.setText(handleValue(prodOff.get("pdes_cn")));
		
		Element PDesEn = PackageInfoSyncReq.addElement("PDesEn");//产品组合英文描述
		PDesEn.setText(handleValue(prodOff.get("pdes_en")));
		
		Element Status = PackageInfoSyncReq.addElement("Status");//状态
		Status.setText(handleValue(prodOff.get("state")));
		
		Element chargingPolicyCN = PackageInfoSyncReq.addElement("chargingPolicyCN");//计费策略描述
		chargingPolicyCN.setText(handleValue(prodOff.get("chargingpolicy_cn")));
		
		Element chargingPolicyID = PackageInfoSyncReq.addElement("chargingPolicyID");//计费策略标识
		chargingPolicyID.setText(handleValue(prodOff.get("chargingpolicy_id")));
		
		Element subOption = PackageInfoSyncReq.addElement("subOption");//定购选项
		subOption.setText(handleValue(prodOff.get("sub_option")));
		
		Element presentOption = PackageInfoSyncReq.addElement("presentOption");//是否可赠送
		presentOption.setText(handleValue(prodOff.get("present_option")));
		
		Element corpOnly = PackageInfoSyncReq.addElement("corpOnly");//是否集团专用
		corpOnly.setText(handleValue(prodOff.get("corp_only")));
		
		Element Scope = PackageInfoSyncReq.addElement("Scope");//业务适用范围
		Scope.setText(handleValue(prodOff.get("scope")));
		
		Element PackageHost = PackageInfoSyncReq.addElement("PackageHost");//产品组合属主
		PackageHost.setText(handleValue(prodOff.get("package_host")));
		
		//新增时也还没有销售品构成列表，也不会有所谓的产品id，故没有 productID 节点,在update或delete时才有该节点
		if (!"1".equals(opFlag.trim())) {
			List prodOffDetaRoleList = getProdOffDetaRoleByOfferId(prodOfferId);
			if (null != prodOffDetaRoleList && !prodOffDetaRoleList.isEmpty()) {
				for (int i = 0; i < prodOffDetaRoleList.size(); i++) {
					Object productId = ((HashMap) prodOffDetaRoleList.get(i)).get("product_id");//增值产品ID列表
					if (null != productId) {
						Element productID = PackageInfoSyncReq.addElement("productID");
						productID.setText(String.valueOf(productId));
					}
				}
			} else {//因为 productID 至少得出现一次，所以当销售品构成为空时，也加上一个空的 productID 节点
				Element productID = PackageInfoSyncReq.addElement("productID");
				productID.setText("");
			}
		} else {
			Element productID = PackageInfoSyncReq.addElement("productID");
			productID.setText("");
		}

		
		//当动作不为（3：删除）的时候,才增加 ProdOfferRelation 节点
		//if (!"3".equals(opFlag.trim())) {
		if ("2".equals(opFlag.trim())) {//新增时还没有销售品关系列表，所以没有下面这些节点
			
			List prodOffRelList = getProdOffRelByOfferId(prodOfferId);
			if (null != prodOffRelList && !prodOffRelList.isEmpty()) {
				for (int i = 0; i < prodOffRelList.size(); i++) {//因为销售品关系可能多个，所以节点 ProdOfferRelation 的出现次数也不能只是0或1个，待确定
					Map prodOffRel = (HashMap) prodOffRelList.get(i);
					Element ProdOfferRelation = PackageInfoSyncReq.addElement("ProdOfferRelation");//关联的增值销售品关系列表
					
					/* xml第一级节点中的第二级子节点  Start  */
					Element opType = ProdOfferRelation.addElement("opType");
					opType.setText(opFlag);//opType 节点的值来源待解决，暂设为销售品的操作标识
					
					Element RelationType = ProdOfferRelation.addElement("ProdOfferRelation");
					RelationType.setText(handleValue(prodOffRel.get("relation_type_id")));
					
					Element RProdOfferID = ProdOfferRelation.addElement("ProdOfferRelation");
					if (null != prodOffRel.get("offer_a_id") && prodOfferId.trim().equals(prodOffRel.get("offer_a_id"))) {
						RProdOfferID.setText(handleValue(prodOffRel.get("offer_z_id")));
					} else if (null != prodOffRel.get("offer_z_id") && prodOfferId.trim().equals(prodOffRel.get("offer_z_id"))) {
						RProdOfferID.setText(handleValue(prodOffRel.get("offer_a_id")));
					} else {
						RProdOfferID.setText("");
					}
					/* xml第一级节点中的第二级子节点  Start  */
				}
			}
		}
		
		/* xml第一级节点中的第一级子节点  Start */
		
		//测试用，把xml文档写到本地D盘目录下
		OutputFormat format = OutputFormat.createPrettyPrint();   
		format.setEncoding("utf-8");
		System.out.println("XML文档的内容为================>" + doc.asXML());
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("D:/musoon.xml"), format);   
		xmlWriter.write(doc);
		xmlWriter.close();
		
		return doc.asXML();
	}
	
	/**
	 * 以系统当前时间为流水线编号(yyyyMMddHHmmss),确保不会重复
	 * @return
	 * @throws Exception
	 */
	private String getStreamingNo() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT_14);
		return sdf.format(DAOUtils.getCurrentTimestamp());
	}
	
	/**
	 * 处理空值
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private String handleValue(Object obj) throws Exception {
		String str = "";
		if (null != obj) {
			str = String.valueOf(obj);
		}
		return str;
	}
	
}
