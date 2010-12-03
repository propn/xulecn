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
	
	//ԭװ����
//	public boolean insertProdOff(HashMap ProdOff ) throws Exception {
//		Map param = new HashMap() ;
//		param.put("ProdOff", ProdOff) ;
//		boolean result = DataTranslate._boolean(
//				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
//						"insertProdOff" ,param)) ;
//		return result ;
//	}
	
	/**
	 * ��װ�������ײ������ɹ��󷵻ص����������������id
	 */
	public boolean insertProdOff(HashMap ProdOff ) throws Exception {
		Map param = new HashMap();
		param.put("ProdOff", ProdOff);

		String prodOfferId = DataTranslate._String(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"insertProdOffSelf" ,param));
		System.out.println("����������Ʒ��idΪ============>" + prodOfferId);
		ProdOff.put("prod_offer_id", prodOfferId);
		
		//�ɹ������˲�ִ��дxml�Ĳ���
		if (null != prodOfferId) {
			writeProdOffToXML(ProdOff, "1");
		}
		
		return prodOfferId != null ;
	}
	
	public boolean updateProdOff(HashMap ProdOff ) throws Exception {
		Map param = new HashMap();
		param.put("ProdOff", ProdOff);
		System.out.println("����������Ʒ��idΪ============>" + ProdOff.get("prod_offer_id"));
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService(ServiceList.SiProdOffBO,
						"updateProdOff" ,param));
		
		if (result) {
			writeProdOffToXML(ProdOff, "2");
		}
		
		return result;
	}
	
	/**
	 * ���ݲ�ѯ����������������Ʒ
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
	 * ��������Ʒ��ϵʱ�����Z����ƷʱZ����Ʒ��ѡ��Χ
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
	 * ����Ʒ�е�tab������Ʒ�����е��õ�
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
	 * �������� prod_offer_id ��ȡ����Ʒ
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
	 * �������� prod_offer_id ɾ������Ʒ
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
	 * ��Map��װ����
	 * @param prod_offer_id
	 * @return
	 */
	private Map getProdOffKeyMap(String prod_offer_id){
		Map param = new HashMap() ;
		param.put("prod_offer_id", prod_offer_id) ;
		return param ;
	}
	
	/**
	 * ��������Ʒid��������Ʒ��ϵ�б�
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
	 * ��������Ʒid��������Ʒ�����б�
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
	 * ��Ҫͬ��������Ʒд��XML��
	 * @param prodOff
	 * @param opFlag
	 * @return
	 */
	public String writeProdOffToXML(HashMap prodOff, String opFlag) throws Exception {
		
		String prodOfferId = String.valueOf(prodOff.get("prod_offer_id"));
		//����һ��xml�ĵ�
		Document doc = DocumentHelper.createDocument();
		//xml�ĵ�һ���ڵ�
		Element PackageInfoSyncReq = doc.addElement("PackageInfoSyncReq");
		
		/* xml��һ���ڵ��еĵ�һ���ӽڵ�  Start */
		Element streamingNo = PackageInfoSyncReq.addElement("streamingNo");//��ˮ��
		streamingNo.setText(getStreamingNo());
		
		Element OPFlag = PackageInfoSyncReq.addElement("OPFlag");//������
		OPFlag.setText(opFlag);
		
		Element PackageID = PackageInfoSyncReq.addElement("PackageID");//��ֵҵ����������ƷID
		PackageID.setText(prodOfferId);
		
		Element PNameCN = PackageInfoSyncReq.addElement("PNameCN");//���Ĳ�Ʒ�������
		PNameCN.setText(handleValue(prodOff.get("pname_cn")));
		
		Element PNameEN = PackageInfoSyncReq.addElement("PNameEN");//Ӣ�Ĳ�Ʒ�������
		PNameEN.setText(handleValue(prodOff.get("pname_en")));
		
		Element PDesCN = PackageInfoSyncReq.addElement("PDesCN");//��Ʒ�����������
		PDesCN.setText(handleValue(prodOff.get("pdes_cn")));
		
		Element PDesEn = PackageInfoSyncReq.addElement("PDesEn");//��Ʒ���Ӣ������
		PDesEn.setText(handleValue(prodOff.get("pdes_en")));
		
		Element Status = PackageInfoSyncReq.addElement("Status");//״̬
		Status.setText(handleValue(prodOff.get("state")));
		
		Element chargingPolicyCN = PackageInfoSyncReq.addElement("chargingPolicyCN");//�ƷѲ�������
		chargingPolicyCN.setText(handleValue(prodOff.get("chargingpolicy_cn")));
		
		Element chargingPolicyID = PackageInfoSyncReq.addElement("chargingPolicyID");//�ƷѲ��Ա�ʶ
		chargingPolicyID.setText(handleValue(prodOff.get("chargingpolicy_id")));
		
		Element subOption = PackageInfoSyncReq.addElement("subOption");//����ѡ��
		subOption.setText(handleValue(prodOff.get("sub_option")));
		
		Element presentOption = PackageInfoSyncReq.addElement("presentOption");//�Ƿ������
		presentOption.setText(handleValue(prodOff.get("present_option")));
		
		Element corpOnly = PackageInfoSyncReq.addElement("corpOnly");//�Ƿ���ר��
		corpOnly.setText(handleValue(prodOff.get("corp_only")));
		
		Element Scope = PackageInfoSyncReq.addElement("Scope");//ҵ�����÷�Χ
		Scope.setText(handleValue(prodOff.get("scope")));
		
		Element PackageHost = PackageInfoSyncReq.addElement("PackageHost");//��Ʒ�������
		PackageHost.setText(handleValue(prodOff.get("package_host")));
		
		//����ʱҲ��û������Ʒ�����б�Ҳ��������ν�Ĳ�Ʒid����û�� productID �ڵ�,��update��deleteʱ���иýڵ�
		if (!"1".equals(opFlag.trim())) {
			List prodOffDetaRoleList = getProdOffDetaRoleByOfferId(prodOfferId);
			if (null != prodOffDetaRoleList && !prodOffDetaRoleList.isEmpty()) {
				for (int i = 0; i < prodOffDetaRoleList.size(); i++) {
					Object productId = ((HashMap) prodOffDetaRoleList.get(i)).get("product_id");//��ֵ��ƷID�б�
					if (null != productId) {
						Element productID = PackageInfoSyncReq.addElement("productID");
						productID.setText(String.valueOf(productId));
					}
				}
			} else {//��Ϊ productID ���ٵó���һ�Σ����Ե�����Ʒ����Ϊ��ʱ��Ҳ����һ���յ� productID �ڵ�
				Element productID = PackageInfoSyncReq.addElement("productID");
				productID.setText("");
			}
		} else {
			Element productID = PackageInfoSyncReq.addElement("productID");
			productID.setText("");
		}

		
		//��������Ϊ��3��ɾ������ʱ��,������ ProdOfferRelation �ڵ�
		//if (!"3".equals(opFlag.trim())) {
		if ("2".equals(opFlag.trim())) {//����ʱ��û������Ʒ��ϵ�б�����û��������Щ�ڵ�
			
			List prodOffRelList = getProdOffRelByOfferId(prodOfferId);
			if (null != prodOffRelList && !prodOffRelList.isEmpty()) {
				for (int i = 0; i < prodOffRelList.size(); i++) {//��Ϊ����Ʒ��ϵ���ܶ�������Խڵ� ProdOfferRelation �ĳ��ִ���Ҳ����ֻ��0��1������ȷ��
					Map prodOffRel = (HashMap) prodOffRelList.get(i);
					Element ProdOfferRelation = PackageInfoSyncReq.addElement("ProdOfferRelation");//��������ֵ����Ʒ��ϵ�б�
					
					/* xml��һ���ڵ��еĵڶ����ӽڵ�  Start  */
					Element opType = ProdOfferRelation.addElement("opType");
					opType.setText(opFlag);//opType �ڵ��ֵ��Դ�����������Ϊ����Ʒ�Ĳ�����ʶ
					
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
					/* xml��һ���ڵ��еĵڶ����ӽڵ�  Start  */
				}
			}
		}
		
		/* xml��һ���ڵ��еĵ�һ���ӽڵ�  Start */
		
		//�����ã���xml�ĵ�д������D��Ŀ¼��
		OutputFormat format = OutputFormat.createPrettyPrint();   
		format.setEncoding("utf-8");
		System.out.println("XML�ĵ�������Ϊ================>" + doc.asXML());
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream("D:/musoon.xml"), format);   
		xmlWriter.write(doc);
		xmlWriter.close();
		
		return doc.asXML();
	}
	
	/**
	 * ��ϵͳ��ǰʱ��Ϊ��ˮ�߱��(yyyyMMddHHmmss),ȷ�������ظ�
	 * @return
	 * @throws Exception
	 */
	private String getStreamingNo() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT_14);
		return sdf.format(DAOUtils.getCurrentTimestamp());
	}
	
	/**
	 * �����ֵ
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
