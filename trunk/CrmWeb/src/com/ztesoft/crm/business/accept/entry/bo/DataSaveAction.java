package com.ztesoft.crm.business.accept.entry.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.crm.business.common.cache.Product;
import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.Actions;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.Keys;
import com.ztesoft.crm.business.common.consts.Services;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST;
import com.ztesoft.crm.business.common.consts.BusiTables.ORD_CUSTOMER_ORDER;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV_ACCT;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV_ATTR;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV_PRODUCT;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV_PRODUCT_ATTR;
import com.ztesoft.crm.business.common.model.OfferData;
import com.ztesoft.crm.business.common.model.ServData;
import com.ztesoft.crm.business.common.model.VAcceptRequest;
import com.ztesoft.crm.business.common.model.VServ;
import com.ztesoft.crm.business.common.query.SqlMapExe;
import com.ztesoft.crm.business.common.utils.DataUtil;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;
/**
 * ������̨�����ͳһ���
 * */
public class DataSaveAction extends DictAction {

	public  static  String custId  = "";
	
	public  static  String cust_ord_id  = "";
	
	public  static  List custInfo  = new ArrayList();
	
	public  static  List offers  = new ArrayList();
	
	public  static  List offerSales  = new ArrayList();
	
	public  static  List offersDetail  = new ArrayList();
	
	public  static  List offersAttr  = new ArrayList();
	
	public  static  List servs  = new ArrayList();
	
	//��װ��ɵ�����Ʒ��ϸ��Ӧ�Ĳ�Ʒʵ��ID��������
	public  static  HashMap servIds = new HashMap();//���ID
	
	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL��ѯ����
	/**
	 * ǰ̨������װ������ڷ�����ͨ����ͬ�ķ���ǩ��ִ�в�ͬ�ķ���
	 * @dto�����װ����buffalo��������Ĳ�����װ
	 * */
	public Object execute(DynamicDict dto) throws Exception {
		//��buffalo����װ��hashmap
		Map parameterMap= Const.getParam(dto);	
		Object busiObject = new Object();//����ҵ�����
		//��ȡҵ�����
		String methodName = Const.getStrValue(parameterMap, Actions.EXECUTE_METHOD);
		//���ݲ�ͬ�ķ���ִ����Ӧ��ҵ����
		if(Actions.CREATE_OFFER_BACK_DATA.equals(methodName)){
			busiObject = this.createOffer(parameterMap);
		}else if(Actions.CREATE_SERV_DATA.equals(methodName)){
			//��װ��Ʒ���ݡ�
			busiObject = this.createServ(parameterMap);
		}else if(Actions.INIT.equals(methodName)) {
			//this.init(parameterMap);
		}
		return busiObject;
	}
	
	
	/**
	 * ������װ����Ʒ����
	 * @param parameterMap������װ�Ĳ���
	 * */
	public VAcceptRequest createOffer(Map parameterMap) throws Exception {
		//������̨��Ҫ��ҵ������
		VAcceptRequest request = new VAcceptRequest();
		//��ȡ����
		OfferData offerData = (OfferData)parameterMap.get(Actions.PARAMETER);//��ȡǰ̨�����ҵ������
		servIds.clear();
		// custInfo = offerData.getCustInfo();
		//��ȡ����Ʒ������ʵ������
		 offers = offerData.getOffers();
		//��ȡ�����Ż�ʵ������
		 offerSales = offerData.getOfferSales(); 
		//��ȡ����Ʒ������ϸ
		 offersDetail = offerData.getOffersDetail();
		//��ȡ����Ʒ��������
		 offersAttr = offerData.getOffersAttr();

		custId = offerData.getCust_id();//��ȡ�ͻ�ID
		cust_ord_id = offerData.getCust_ord_id();//��ȡ�ͻ�������־
		//��װ��ɵ�����Ʒʵ����������
		List offersInst = new ArrayList();
		//��װ��ɵ�����Ʒ����ʵ����������
		List offersInstAttrs = new ArrayList();
		//��װ��ɵ�����Ʒʵ��������������
		List OfferInstDetails = new ArrayList();
	
		
		HashMap respond = offerData.getLoginInfo();
		/***********��װ����Ʒʵ����Ϣ***********/
		initOfferInst(offerData,offersInst);
		/***********��װ����Ʒ������ϸ��Ϣ***********/
		initOfferInstDetail(offerData,offersInst,OfferInstDetails);
		/***********��װ����Ʒʵ��������Ϣ***********/
		initOfferInstAttr(offerData,offersInstAttrs,offersInst);
		/***************ˢ��ʵ��ҵ����**********************/
		DataUtil.getInst().refreshActionType(offersInst,"OfferInst");//ˢ������Ʒʵ������
		
		DataUtil.getInst().refreshActionType(OfferInstDetails,"OfferDetail");//ˢ������Ʒ��ϸʵ������
		
		/*************����ʵ����ϸ��Ӧ����Ʒʵ����Ϣ************/
		servs = DataUtil.getInst().parseMapToList(servIds, "VServ");
		initServs(servs,respond,custId);
		request.setServs(servs);
		/***********���������������***********/
		request.setCust_id(custId);
		request.setAsk_id(offerData.getAsk_id());//ͬ�ʶ�����
		request.setCust_ord_id(cust_ord_id);
		request.setOfferInsts(offersInst);
		request.setOfferInstAttrs(offersInstAttrs);
		request.setOfferInstDetails(OfferInstDetails);
		/***********���õ�½����***********/
		//���ò���ID
		request.setSite_no((String)respond.get("vg_depart_id"));
		//���ò���Ա����
		request.setStaff_no((String)respond.get("vg_oper_id"));
		request.setBusiness_id((String)respond.get("vg_business_id"));
		//���ñ�����ID
		request.setLan_id((String)respond.get("vg_lan_id"));
		//����ҵ������ ������̨Ҫִ�е�ҵ�������� �˴�Ϊ����Ʒ������
		request.setBusi_type(Services.OFFER_ACCEPT);
		
		
		return request;//����ҵ�����
	}
	
	
	
	
	
	
	/**
	 * ��װ����Ʒʵ������
	 * @param offerData
	 * @param offersInst
	 */
	public void initOfferInst(OfferData offerData,List offersInst)
	{
		/****************��ʼ��װ����Ʒʵ����Ϣ************************/
		/****************��װ��ѡ����Ʒ��************************/
		if(offers!=null&&offers.size()!=0){
			HashMap offerMap = new HashMap();
			HashMap offerBackMap = new HashMap();// ��̨ʹ�õĸ�ʽ
			HashMap offerComp = DataUtil.getInst().getOfferCompInst(offers);
			String comp_inst_id = (String)offerComp.get("productOfferInstanceId");//��ȡ��ѡ����Ʒ��������Ʒʵ��ID
			
			for(int i = 0;i<offers.size();i++){
				offerMap  = (HashMap)offers.get(i);
				String offer_kind = (String)offerMap.get("offerKind");//��ȡ��һ�м�¼������Ʒ����
				if("0".equals(offer_kind)){//���Ϊ0 ��ʾΪ��������Ʒҵ�� ���� Ϊ �������Ʒҵ��
					offerData.setOfferType("0");
				}else{
					offerData.setOfferType("1"); 
				}
				//ת����ʽ
				offerBackMap = DataUtil.getInst().parseOffer(offerMap, offerData.getLoginInfo(), comp_inst_id, custId,cust_ord_id);
				offerBackMap.put("offer_type", "0");//��ʾΪ��������ѡ�� 
				offersInst.add(offerBackMap);
			}
		}
		/****************��װ��ѡ�����Ż�************************/
		if(offerSales!=null&&offerSales.size()!=0){
			for(int i = 0;i<offerSales.size();i++){
				HashMap offerSaleMap = new HashMap();
				HashMap offerSaleBackMap = new HashMap();// ��̨ʹ�õĸ�ʽ
				offerSaleMap  = (HashMap)offerSales.get(i);
				//ת����ʽ
				offerSaleBackMap = DataUtil.getInst().parseOfferSale(offerSaleMap, offerData.getLoginInfo(), custId,cust_ord_id);
				offerSaleBackMap.put("offer_type", "1");//��ʾΪ�����Ż�
				offersInst.add(offerSaleBackMap);
			}
		}
		/****************��װ��װ��ѡ��������Ʒ************************/
		List baseOffers = new ArrayList();//��������װ��������Ʒ�б� 
		
		if(offersDetail!=null&&offersDetail.size()!=0){
			/*********************��ȡ����װ��������Ʒ�б�*******************************/
			for(int i = 0;i<offersDetail.size();i++){
				HashMap offersDetailMap = new HashMap();
				HashMap offerBackMap = new HashMap();// ��̨ʹ�õĸ�ʽ
				offersDetailMap  = (HashMap)offersDetail.get(i);
				String comp_inst_id = (String)offersDetailMap.get("product_offer_instance_id");
				String rela_offer_inst_id = (String)offersDetailMap.get("rela_offer_inst_id");//��������Ʒʵ��ID
				String productId = (String)offersDetailMap.get("product");
				
				HashMap servInfo = new HashMap();//����Ʒ��һЩ������Ϣ
				servInfo.put(SERV.comp_inst_id, rela_offer_inst_id);//��Ӧ��������Ʒʵ��ID
				servInfo.put(SERV.product_id, productId);//��Ӧ�Ĺ��ID����
				
				String serv_id = (String)offersDetailMap.get("serv_id");
				servIds.put(serv_id, servInfo);//������Ʒ��Ӧ�Ļ�����Ϣ����MAP��
	
				
				
				HashMap relaOfferMap  = (HashMap)DataUtil.getInst().getRelaOffer(productId);
				String rela_offer_id = (String)relaOfferMap.get("offer_id");
				String rela_offer_kind = (String)relaOfferMap.get("offer_kind");
				offersDetailMap.remove("rela_offer_id");
				offersDetailMap.put("rela_offer_id", rela_offer_id);
				
				HashMap baseOfferMap = new HashMap();
				
				baseOfferMap.put("offer_id", rela_offer_id);
				baseOfferMap.put("offer_kind",rela_offer_kind) ;
				baseOfferMap.put("comp_inst_id", comp_inst_id);
				baseOfferMap.put("product_offer_instance_id", offersDetailMap.get("rela_offer_inst_id"));
				baseOfferMap.put("action_type", "M");
				/**********��Ϊ����ĳ�Ա��������Ʒʵ��������ͬ�� ����Ҫ�޳��Ѿ����ӵ���ͬ�Ļ�������Ʒʵ��***********************/
				if(baseOffers!=null&&baseOffers.size()!=0){
					for(int j = 0;j<baseOffers.size();j++){
						HashMap curMap = (HashMap)baseOffers.get(j);
						String curInstId = (String)curMap.get("product_offer_instance_id");
						if(((String)offersDetailMap.get("rela_offer_inst_id")).equals(curInstId)){
							baseOffers.remove(j);
						}
					}
				}
				baseOffers.add(baseOfferMap);
				
			}
			/*********************��ʼ��װ��������Ʒ*******************************/
			
			if(baseOffers!=null&&baseOffers.size()!=0&&"1".equals(offerData.offerType)){
				for(int i = 0;i<baseOffers.size();i++){
					HashMap baseOffersMap = new HashMap();
					HashMap baseBackMap = new HashMap();// ��̨ʹ�õĸ�ʽ
					baseOffersMap  = (HashMap)baseOffers.get(i);
					//ת����ʽ
					baseBackMap = DataUtil.getInst().parseBaseOffer(baseOffersMap, offerData.getLoginInfo(), custId,cust_ord_id);
					baseBackMap.put("offer_type", "2");//��ʾΪ����Ʒ��Ա��ϸ��Ʒ�����Ļ�������Ʒ
					offersInst.add(baseBackMap);
				}
			}
		}
		/***************����Ʒʵ����Ϣ������װ���************************/
	}
	
	
	/**
	 * ��ʼ������Ʒ������ϸ
	 * @param offerData
	 * @param offersInst
	 * @param OfferInstDetails
	 */
	public void initOfferInstDetail(OfferData offerData,List offersInst,List OfferInstDetails){
		/****************��װ����Ʒ������ϸ************************/
		if(offersInst!=null&&offersInst.size()!=0){
			for(int i = 0;i<offersInst.size();i++){
				HashMap offerInstMap = new HashMap();
				HashMap offerInstDetailBackMap = new HashMap();// ��̨ʹ�õĸ�ʽ
				offerInstMap  = (HashMap)offersInst.get(i);
				String offer_kind = (String)offerInstMap.get("offer_kind");
				String offer_type = (String)offerInstMap.get("offer_type");
				offerInstMap.remove("offer_type");
				if("1".equals(offer_type)){//�����Ż�
					String product_offer_instance_id = (String)offerInstMap.get("product_offer_instance_id");
					String offer_id = (String)offerInstMap.get("product_offer_id");
					
					HashMap saleMap = DataUtil.getInst().getBaseInstBySaleInst(product_offer_instance_id, offerSales);
					
					String rela_offer_instance_id =  (String)saleMap.get("rela_offer_instance_id");//��ȡ������������Ʒʵ��
					HashMap servMap = DataUtil.getInst().getServIdByBaseOff(rela_offer_instance_id, offersDetail);
					String serv_id = (String)servMap.get("serv_id");
					String product_id = (String)servMap.get("product");
					HashMap offerDetailsMap = DataUtil.getInst().getOfferDetail(offer_id,product_id,(String)servMap.get("comp_role_id"));
					offerDetailsMap.put(OFFER_INST.comp_inst_id, rela_offer_instance_id);
					offerDetailsMap.put(OFFER_INST.action_type, "M");
					HashMap offerDetailBackMap  = DataUtil.getInst().parseOfferDetail(offerDetailsMap, offerData.getLoginInfo(), product_offer_instance_id, serv_id,cust_ord_id);
					OfferInstDetails.add(offerDetailBackMap);
					//String 
					//if()
				}
				else if ("0".equals(offer_type)){//�������۰����ѡ��
					
						HashMap basePack = (HashMap)offers.get(0);//��ȡ������
						String comp_inst_id = (String)basePack.get("productOfferInstanceId");
						
						String product_offer_instance_id = (String)offerInstMap.get("product_offer_instance_id");
						String offer_id = (String)offerInstMap.get("product_offer_id");
						HashMap detailMap = DataUtil.getInst().getServIdByOff(product_offer_instance_id, offersDetail);
						String serv_id = (String)detailMap.get("serv_id");
						String product_id = (String)detailMap.get("product");
						HashMap offerDetailsMap = DataUtil.getInst().getOfferDetail(offer_id,product_id,(String)detailMap.get("comp_role_id"));
						offerDetailsMap.put("comp_inst_id", comp_inst_id);
						offerDetailsMap.put(OFFER_INST.action_type, detailMap.get(OFFER_INST.action_type));
						HashMap offerDetailBackMap  = DataUtil.getInst().parseOfferDetail(offerDetailsMap, offerData.getLoginInfo(), product_offer_instance_id, serv_id,cust_ord_id);
						OfferInstDetails.add(offerDetailBackMap);
				}
				else if ("2".equals(offer_type)&&"1".equals(offerData.offerType)){//��������Ʒ
					
						String product_offer_instance_id = (String)offerInstMap.get("product_offer_instance_id");
						String offer_id = (String)offerInstMap.get("product_offer_id");
						
						HashMap detailMap = DataUtil.getInst().getServIdByBaseOff(product_offer_instance_id, offersDetail);
						String serv_id = (String)detailMap.get("serv_id");
						String product_id = (String)detailMap.get("product");
						HashMap offerDetailsMap = DataUtil.getInst().getOfferDetail(offer_id,product_id,(String)detailMap.get("comp_role_id"));
						offerDetailsMap.put("comp_inst_id", product_offer_instance_id);
						offerDetailsMap.put(OFFER_INST.action_type, "M");
						HashMap offerDetailBackMap  = DataUtil.getInst().parseOfferDetail(offerDetailsMap,  offerData.getLoginInfo(),product_offer_instance_id, serv_id,cust_ord_id);
						OfferInstDetails.add(offerDetailBackMap);
			    }
			}
		}
	}
	
	
	
	/** ��ʼ������Ʒ����
	 * @param offerData
	 * @param offersInstAttrs
	 * @param offersInst
	 */
	public void initOfferInstAttr(OfferData offerData,List offersInstAttrs,List offersInst)
	{
		/****************��װ����Ʒ������Ϣ************************/
		if(offersAttr!=null&&offersAttr.size()!=0){
			for(int i = 0;i<offersAttr.size();i++){
				HashMap offerAttrMap = new HashMap();
				HashMap offerAttrBackMap = new HashMap();// ��̨ʹ�õĸ�ʽ
				offerAttrMap  = (HashMap)offersAttr.get(i);
				String prodct_offer_inst_id =(String) offerAttrMap.get(BusiTables.OFFER_INST_ATTR.product_offer_instance_id);
				//ת����ʽ
				String comp_inst_id = DataUtil.getInst().getOfferCompInstId(offersInst, prodct_offer_inst_id);
				offerAttrMap.put("comp_inst_id", comp_inst_id);
				offerAttrBackMap = DataUtil.getInst().parseOfferAttr(offerAttrMap, offerData.getLoginInfo(),cust_ord_id);
				offersInstAttrs.add(offerAttrBackMap);
			}
		}
	}

	/**
	 *  ��ʼ������Ʒ��Ϣ
	 * @param servs
	 * @param loginInfo
	 * @param custId
	 */
	public void initServs(List servs,HashMap loginInfo,String custId) throws Exception
	{
		if(servs!=null && servs.size()!=0){
			for(int i = 0;i<servs.size();i++){
				VServ serv = (VServ)servs.get(i);
				HashMap serv_attr = serv.getServ_attrs();
				serv_attr.put(SERV.cust_id, custId);
				serv_attr.put(SERV.lan_id, (String)loginInfo.get("vg_lan_id"));
				serv_attr.put(SERV.business_id, (String)loginInfo.get("vg_business_id"));
				serv_attr.put(SERV.product_family_id, "10");//��ʾΪ����Ʒ
				serv_attr.put(SERV.area_code, (String)loginInfo.get("vg_lan_code"));
				serv_attr.put(SERV.rent_date, DAOUtils.getFormatedDbDate());
				serv_attr.put(SERV.state, "00N");//��ʾ��;
				//if(ServConsts.ACTION_TYPE_A.equals(serv_attr.))
				serv.setServ_attrs(serv_attr);
				
			/*	
				
				//���ò���ID
				request.setSite_no((String)respond.get("vg_depart_id"));
				//���ò���Ա����
				request.setStaff_no((String)respond.get("vg_oper_id"));
				request.setBusiness_id((String)respond.get("vg_business_id"));
				//���ñ�����ID
				request.setLan_id((String)respond.get("vg_lan_id"));
				*/
				
			}
		}
	}
	
	/**
	 * ����ҳ�洫��Ĳ�Ʒ���ݣ���װ�ɸ���̨���ڱ�������ݡ�
	 * @param parameterMap
	 * @return
	 */
	public VAcceptRequest createServ(Map parameterMap) throws Exception{
		//��ȡҳ�洫��Ķ���
		ServData servData = (ServData) parameterMap.get(Actions.PARAMETER);
		//��װ������ݡ�
		VAcceptRequest vrequest = new VAcceptRequest();
		//��ȡ������Ϣ���������š��ͻ���������
		initCommonData(servData,vrequest);
		
		//��װ��Ʒʵ�������ݡ�
		List servs = initServs(servData,vrequest);
		vrequest.setServs(servs);
		
		//��װ��Ʒ���Ե����ݡ�
		List servAttrs = initServAttrs(servData);
		vrequest.setServAttrs(servAttrs);
		
		//��װ������Ʒʵ��������
		List servProducts = initServProducts(servData);
		vrequest.setServProducts(servProducts);
		
		//��װ������Ʒʵ�����Ե�����
		List servProductAttrs = initServProductAttrs(servData);
		vrequest.setServProductAttrs(servProductAttrs);
		
		//��װ�����ƹ�ϵ�����ݡ�
		List servAccts = initServAccts(servData);
		vrequest.setServAccts(servAccts);
		
		//�����Ʒ��Ϣ���ݡ�busi_type ΪservAccept
		vrequest.setBusi_type(Services.SERV_ACCEPT);
		
		return vrequest;
	}
	
	/**
	 * ��ȡ����Ʒʵ��
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServs(ServData servData,VAcceptRequest vrequest) throws Exception{
		
		ArrayList servs = new ArrayList(); //���ڼ�¼���ص�����Ʒʵ����
		
		List inServs = servData.getServs();
		Iterator itra = inServs.iterator();
		//ѭ��ÿ������Ʒ��
		while (itra.hasNext()){
			Map voMap = (Map) itra.next();
			
			//��ҳ�洫���servת��Vserv
			VServ vserv = changeToVServ(voMap,servData,vrequest);
			servs.add(vserv);
		}
		return servs;
	}
	
	/**
	 * ��ҳ�洫�������Ʒת�� VServ
	 * @param servMap
	 * @return
	 * @throws Exception
	 */
	public VServ changeToVServ(Map servMap,ServData servData,VAcceptRequest vrequest) throws Exception{
		VServ vserv = new VServ();
		if(servMap != null){
			//�ж��Ƿ���ڡ�������Ʒʵ�����ر�־��
			if(servMap.containsKey("serv_product_loaded_flag") && servMap.get("serv_product_loaded_flag") != null){
				vserv.setServ_product_loaded_flag((String)servMap.get("serv_product_loaded_flag"));
			}else{
				vserv.setServ_product_loaded_flag("F");
			}
			//�ж��Ƿ���ڡ���������Ϣ��
			if(servMap.containsKey("serv_acct_loaded_flag") && servMap.get("serv_acct_loaded_flag")!= null){
				vserv.setServ_acct_loaded_flag((String) servMap.get("serv_acct_loaded_flag"));
			}else{
				vserv.setServ_acct_loaded_flag("F");
			}
			//�ж��Ƿ���ڡ����Ӻ�����ء�
			if(servMap.containsKey("serv_bill_post_loaded_flag") && servMap.get("serv_bill_post_loaded_flag")!= null){
				vserv.setServ_bill_post_loaded_flag((String) servMap.get("serv_bill_post_loaded_flag"));
			}else{
				vserv.setServ_bill_post_loaded_flag("F");
			}
			//�ж��Ƿ���ڡ�����Ʒ״̬��
			if(servMap.containsKey("serv_state_loaded_flag") && servMap.get("serv_state_loaded_flag")!= null){
				vserv.setServ_state_loaded_flag((String) servMap.get("serv_state_loaded_flag"));
			}else{
				vserv.setServ_state_loaded_flag("F");
			}
			
			//ת��ǰ̨���������Ʒ���ݡ�
			HashMap servAttrs = changeServ((HashMap) servMap,servData,vrequest);
			vserv.setServ_attrs(servAttrs);
		}
		return vserv;
	}
	
	/**
	 * ת��ǰ̨���������Ʒ����
	 * @param servMap
	 * @param servData
	 * @return
	 */
	public HashMap changeServ(HashMap servMap,ServData servData,VAcceptRequest vrequest)throws Exception{
		HashMap servAttrs = new HashMap();
		//��ҳ�洫����������õ� serv_attrs �С�
		if(servMap.containsKey(SERV.serv_id) && servMap.get("serv_id") != null){
			servAttrs.put(SERV.serv_id, servMap.get(SERV.serv_id));
			
			if(servMap.containsKey(SERV.product_id) && servMap.get(SERV.product_id) != null){
				servAttrs.put(SERV.product_id, servMap.get(SERV.product_id));
			}
			if(servMap.containsKey(SERV.comp_inst_id) && servMap.get(SERV.comp_inst_id) != null){
				servAttrs.put(SERV.comp_inst_id, servMap.get(SERV.comp_inst_id));
			}
			if(servMap.containsKey(SERV.action_type) && servMap.get(SERV.action_type) != null){
				servAttrs.put(SERV.action_type, servMap.get(SERV.action_type));
			}
			//��Ʒ״̬
			if(servMap.containsKey(SERV.state) && servMap.get(SERV.state) != null){
				servAttrs.put(SERV.state, servMap.get(SERV.state));
			}else{
				servAttrs.put(SERV.state, "00A");
			}
			
			
			//��������
			servAttrs.put(SERV.rent_date, DAOUtils.getFormatedDate());
			
			//ȡ�ͻ���ʶ
			servAttrs.put(SERV.cust_id, vrequest.getCust_id());
			
			//ȡ��Ʒ�ļ���
			//���ݲ�ƷIDȡ�������
			Product pro = SpecsData.getProduct((String) servMap.get(Keys.PRODUCT_ID));
			servAttrs.put(SERV.product_family_id, pro.getProduct_family_id());
			
			//ȥ���š�����������Ϣ����session��ȡ
			GlobalVariableHelper helper = servData.getHelper();
			//Ա�����źͲ���
			servAttrs.put(SERV.area_code, helper.getVariable(helper.LAN_CODE));
			servAttrs.put(SERV.lan_id, helper.getVariable(helper.LAN_ID));
			servAttrs.put(SERV.business_id, helper.getVariable(helper.BUSINESS_ID));
			
		}
		return servAttrs;
	}
	
	/**
	 * ��ȡҳ�洫�������Ʒ���ԡ�
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServAttrs(ServData servData) throws Exception{
		ArrayList servAttrs = new ArrayList(); //���ڼ�¼���ص�����Ʒ����ʵ����

		//ȡ����Ʒ���ԣ���������ת��
		List inServAttrs = servData.getServAttrs();
		Iterator attrItra = inServAttrs.iterator();
		//ѭ������Ʒ���������ݡ��������м���в����ݴ���
		while(attrItra.hasNext()){
			Map attrMap = (Map) attrItra.next();
			//����Ʒʵ����ʶ
			String serv_id = (String) attrMap.get(SERV.serv_id);
			Iterator attrIter = attrMap.entrySet().iterator();
			while(attrIter.hasNext()){
				Entry entry = (Entry) attrIter.next();
				if( entry.getValue()!= null&& !"".equals(entry.getValue())){
					//ת��ҳ�洫�������Ʒ����
					HashMap servAttrMap = changeServAttr(entry,serv_id);
					servAttrs.add(servAttrMap);
				}
			}
		}

		return servAttrs;
	}


	/**
	 * ת��һ������Ʒ���ԡ�
	 * @param attrMap
	 * @return
	 */
	public HashMap changeServAttr(Entry entry, String serv_id){
		HashMap returnMap = new HashMap();
		returnMap.put(SERV_ATTR.serv_id, serv_id);
		returnMap.put(SERV_ATTR.field_name, entry.getKey());
		returnMap.put(SERV_ATTR.attr_val, entry.getValue());
		return returnMap;
	}
	
	/**
	 * ��ȡ��ҳ�淵�صĸ�����Ʒʵ��
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServProducts(ServData servData) throws Exception{
		ArrayList servProducts = new ArrayList(); //���ڼ�¼���صĸ�����Ʒʵ����
		
		ArrayList inServProducts = (ArrayList) servData.getServProducts();
		Iterator inServProdIter = inServProducts.iterator();
		while (inServProdIter.hasNext()){
			Map voMap = (Map) inServProdIter.next();
			
			HashMap servProductMap = new HashMap(); 
			servProductMap.put(SERV_PRODUCT.serv_id, voMap.get(SERV_PRODUCT.serv_id));
			servProductMap.put(SERV_PRODUCT.serv_product_id, voMap.get(SERV_PRODUCT.serv_product_id));
			servProductMap.put(SERV_PRODUCT.product_id, voMap.get(SERV_PRODUCT.product_id));
			servProductMap.put(SERV_PRODUCT.action_type, voMap.get(SERV_PRODUCT.action_type));
			servProductMap.put(SERV_PRODUCT.rent_date, DAOUtils.getFormatedDate());
			//״̬
			if(voMap.containsKey(SERV_PRODUCT.state) && voMap.get(SERV_PRODUCT.state) != null){
				servProductMap.put(SERV_PRODUCT.state, voMap.get(SERV_PRODUCT.state));
			}else{
				servProductMap.put(SERV_PRODUCT.state, "00A");
			}
			servProducts.add(servProductMap);
		}
		
		return servProducts;
	}
	
	/**
	 * ��ȡ��ҳ�淵�صĸ�����Ʒʵ������
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServProductAttrs(ServData servData) throws Exception{
		ArrayList servProductAttrs = new ArrayList(); //���ڼ�¼���صĸ�����Ʒʵ����
		//������Ҫתһ�£����attr_value Ҫ��д�� attr_val
		ArrayList inProductAttrs = (ArrayList) servData.getServProductAttrs();
		Iterator productAttrIter = inProductAttrs.iterator();
		while(productAttrIter.hasNext()){
			Map attrMap = (Map) productAttrIter.next();
			//ת��ҳ�洫��ĸ�����Ʒ����
			HashMap productAttrMap = changeProductAttr((HashMap) attrMap);
			
			servProductAttrs.add(productAttrMap);
		}

		return servProductAttrs;
	}
	
	
	/**
	 * ת��һ��������Ʒ���ԡ�
	 * @param attrMap
	 * @return
	 */
	public HashMap changeProductAttr(HashMap attrMap){
		HashMap returnMap = new HashMap();
		returnMap.put(SERV_PRODUCT.serv_id, attrMap.get("serv_id"));
		returnMap.put(SERV_PRODUCT_ATTR.serv_product_id,SERV_PRODUCT_ATTR.serv_product_id);
		returnMap.put(SERV_PRODUCT_ATTR.attr_id, attrMap.get(SERV_PRODUCT_ATTR.attr_id));
		returnMap.put(SERV_PRODUCT_ATTR.field_name, attrMap.get(SERV_PRODUCT_ATTR.field_name));
		returnMap.put(SERV_PRODUCT_ATTR.attr_val, attrMap.get("attr_value"));
		
		return returnMap;
	}
	
	
	/**
	 * ��ȡ��ҳ�洫��������ϵ��������װ���ݡ�
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServAccts(ServData servData)throws Exception{
		ArrayList servAccts = new ArrayList(); //���ڼ�¼���ص������ϵʵ����
		
		ArrayList inAcctsList = (ArrayList) servData.getServAccts();
		Iterator servAcctIter = inAcctsList.iterator();
		while(servAcctIter.hasNext()){
			Map acctMap = (Map) servAcctIter.next();
			//ת��ҳ�洫��ĸ�����Ʒ����
			HashMap servAcctMap = changeServAcctMap((HashMap) acctMap);
			
			servAccts.add(servAcctMap);
		}
		
		return servAccts;
	}
	
	/**
	 * ת��һ�������ϵ��
	 * @param attrMap
	 * @return
	 */
	public HashMap changeServAcctMap(HashMap acctMap){
		HashMap returnMap = new HashMap();
		if(acctMap.containsKey(SERV_ACCT.serv_id) && acctMap.get(SERV_ACCT.serv_id) != null){
			returnMap.put(SERV_ACCT.serv_id, acctMap.get(SERV_ACCT.serv_id));
		}
		if(acctMap.containsKey(SERV_ACCT.acct_item_group_id) && acctMap.get(SERV_ACCT.acct_item_group_id) != null){
			returnMap.put(SERV_ACCT.acct_item_group_id, acctMap.get(SERV_ACCT.acct_item_group_id));
		}
		if(acctMap.containsKey("comp_inst_id") && acctMap.get("comp_inst_id") != null){
			returnMap.put("comp_inst_id", acctMap.get("comp_inst_id"));
		}
		if(acctMap.containsKey(SERV_ACCT.acct_id) && acctMap.get(SERV_ACCT.acct_id) != null){
			returnMap.put(SERV_ACCT.acct_id, acctMap.get(SERV_ACCT.acct_id));
		}
		if(acctMap.containsKey(SERV_ACCT.action_type) && acctMap.get(SERV_ACCT.action_type) != null){
			returnMap.put(SERV_ACCT.action_type, acctMap.get(SERV_ACCT.action_type));
		}
		
		if(acctMap.containsKey(SERV_ACCT.invoice_require_id) && acctMap.get(SERV_ACCT.invoice_require_id) != null){
			returnMap.put(SERV_ACCT.invoice_require_id, acctMap.get(SERV_ACCT.invoice_require_id));
		}else{
			//��ʱд�롣
			returnMap.put(SERV_ACCT.invoice_require_id,"1");
		}
		if(acctMap.containsKey(SERV_ACCT.bill_require_id) && acctMap.get(SERV_ACCT.bill_require_id) != null){
			returnMap.put(SERV_ACCT.bill_require_id, acctMap.get(SERV_ACCT.bill_require_id));
		}else{
			//��ʱд�롣
			returnMap.put(SERV_ACCT.bill_require_id,"1");
		}
		if(acctMap.containsKey(SERV_ACCT.self_flag) && acctMap.get(SERV_ACCT.self_flag) != null){
			returnMap.put(SERV_ACCT.self_flag, acctMap.get(SERV_ACCT.self_flag));
		}else{
			//��ʱд�롣
			returnMap.put(SERV_ACCT.self_flag,"0");
		}
		
		return returnMap;
	}
	
	/**
	 * ��ҳ�洫��Ĺ�����Ϣ�л�ȡ���š��ͻ���ʶ���ͻ�������ʶ����Ϣ��
	 * @param servData
	 * @param vrequest
	 * @throws Exception
	 */
	public void initCommonData(ServData servData,VAcceptRequest vrequest)throws Exception{
		ArrayList commonData = (ArrayList) servData.getCommonData();
		Iterator commonIter = commonData.iterator();
		if(commonIter.hasNext()){
			Map voMap = (Map) commonIter.next();
			String cust_id = (String) voMap.get(ORD_CUSTOMER_ORDER.cust_id);
			String cust_ord_id = (String) voMap.get(ORD_CUSTOMER_ORDER.cust_ord_id);
			//���ÿͻ���ʶ���ͻ�������ʶ
			vrequest.setCust_id(cust_id);
			vrequest.setCust_ord_id(cust_ord_id);
		}
		
		GlobalVariableHelper helper = servData.getHelper();
		//Ա�����źͲ���
		vrequest.setStaff_no(helper.getVariable(helper.OPER_ID));
		vrequest.setSite_no(helper.getVariable(helper.DEPART_ID));
		
		//���ñ�������Ӫҵ��
		vrequest.setLan_id(helper.getVariable(helper.LAN_ID));
		vrequest.setBusiness_id(helper.getVariable(helper.BUSINESS_ID));
	}
}
