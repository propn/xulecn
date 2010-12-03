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
 * 订单后台服务的统一入口
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
	
	//组装完成的销售品明细对应的产品实例ID数据容器
	public  static  HashMap servIds = new HashMap();//存放ID
	
	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL查询工具
	/**
	 * 前台数据组装公共入口方法，通过不同的服务签名执行不同的方法
	 * @dto里面封装的是buffalo请求过来的参数封装
	 * */
	public Object execute(DynamicDict dto) throws Exception {
		//在buffalo中组装的hashmap
		Map parameterMap= Const.getParam(dto);	
		Object busiObject = new Object();//返回业务对象
		//获取业务服务
		String methodName = Const.getStrValue(parameterMap, Actions.EXECUTE_METHOD);
		//根据不同的服务执行相应的业务动作
		if(Actions.CREATE_OFFER_BACK_DATA.equals(methodName)){
			busiObject = this.createOffer(parameterMap);
		}else if(Actions.CREATE_SERV_DATA.equals(methodName)){
			//组装产品数据。
			busiObject = this.createServ(parameterMap);
		}else if(Actions.INIT.equals(methodName)) {
			//this.init(parameterMap);
		}
		return busiObject;
	}
	
	
	/**
	 * 创建组装销售品数据
	 * @param parameterMap界面组装的参数
	 * */
	public VAcceptRequest createOffer(Map parameterMap) throws Exception {
		//创建后台需要的业务请求
		VAcceptRequest request = new VAcceptRequest();
		//获取参数
		OfferData offerData = (OfferData)parameterMap.get(Actions.PARAMETER);//获取前台传入的业务数据
		servIds.clear();
		// custInfo = offerData.getCustInfo();
		//获取销售品包本身实例数据
		 offers = offerData.getOffers();
		//获取包外优惠实例数据
		 offerSales = offerData.getOfferSales(); 
		//获取销售品构成明细
		 offersDetail = offerData.getOffersDetail();
		//获取销售品属性数据
		 offersAttr = offerData.getOffersAttr();

		custId = offerData.getCust_id();//获取客户ID
		cust_ord_id = offerData.getCust_ord_id();//获取客户订单标志
		//组装完成的销售品实例数据容器
		List offersInst = new ArrayList();
		//组装完成的销售品属性实例数据容器
		List offersInstAttrs = new ArrayList();
		//组装完成的销售品实例构成数据容器
		List OfferInstDetails = new ArrayList();
	
		
		HashMap respond = offerData.getLoginInfo();
		/***********组装销售品实例信息***********/
		initOfferInst(offerData,offersInst);
		/***********组装销售品构成明细信息***********/
		initOfferInstDetail(offerData,offersInst,OfferInstDetails);
		/***********组装销售品实例属性信息***********/
		initOfferInstAttr(offerData,offersInstAttrs,offersInst);
		/***************刷新实例业务动作**********************/
		DataUtil.getInst().refreshActionType(offersInst,"OfferInst");//刷新销售品实例动作
		
		DataUtil.getInst().refreshActionType(OfferInstDetails,"OfferDetail");//刷新销售品明细实例动作
		
		/*************放入实例明细对应主产品实例信息************/
		servs = DataUtil.getInst().parseMapToList(servIds, "VServ");
		initServs(servs,respond,custId);
		request.setServs(servs);
		/***********设置请求基本数据***********/
		request.setCust_id(custId);
		request.setAsk_id(offerData.getAsk_id());//同笔订单号
		request.setCust_ord_id(cust_ord_id);
		request.setOfferInsts(offersInst);
		request.setOfferInstAttrs(offersInstAttrs);
		request.setOfferInstDetails(OfferInstDetails);
		/***********设置登陆数据***********/
		//设置部门ID
		request.setSite_no((String)respond.get("vg_depart_id"));
		//设置操作员工号
		request.setStaff_no((String)respond.get("vg_oper_id"));
		request.setBusiness_id((String)respond.get("vg_business_id"));
		//设置本地网ID
		request.setLan_id((String)respond.get("vg_lan_id"));
		//设置业务类型 决定后台要执行的业务引擎链 此处为销售品受理保存
		request.setBusi_type(Services.OFFER_ACCEPT);
		
		
		return request;//返回业务对象
	}
	
	
	
	
	
	
	/**
	 * 组装销售品实例数据
	 * @param offerData
	 * @param offersInst
	 */
	public void initOfferInst(OfferData offerData,List offersInst)
	{
		/****************开始组装销售品实例信息************************/
		/****************组装已选销售品包************************/
		if(offers!=null&&offers.size()!=0){
			HashMap offerMap = new HashMap();
			HashMap offerBackMap = new HashMap();// 后台使用的格式
			HashMap offerComp = DataUtil.getInst().getOfferCompInst(offers);
			String comp_inst_id = (String)offerComp.get("productOfferInstanceId");//获取已选销售品包父销售品实例ID
			
			for(int i = 0;i<offers.size();i++){
				offerMap  = (HashMap)offers.get(i);
				String offer_kind = (String)offerMap.get("offerKind");//获取第一行记录的销售品类型
				if("0".equals(offer_kind)){//如果为0 表示为基础销售品业务 否则 为 组合销售品业务
					offerData.setOfferType("0");
				}else{
					offerData.setOfferType("1"); 
				}
				//转换格式
				offerBackMap = DataUtil.getInst().parseOffer(offerMap, offerData.getLoginInfo(), comp_inst_id, custId,cust_ord_id);
				offerBackMap.put("offer_type", "0");//表示为基础包可选包 
				offersInst.add(offerBackMap);
			}
		}
		/****************组装已选包外优惠************************/
		if(offerSales!=null&&offerSales.size()!=0){
			for(int i = 0;i<offerSales.size();i++){
				HashMap offerSaleMap = new HashMap();
				HashMap offerSaleBackMap = new HashMap();// 后台使用的格式
				offerSaleMap  = (HashMap)offerSales.get(i);
				//转换格式
				offerSaleBackMap = DataUtil.getInst().parseOfferSale(offerSaleMap, offerData.getLoginInfo(), custId,cust_ord_id);
				offerSaleBackMap.put("offer_type", "1");//表示为包外优惠
				offersInst.add(offerSaleBackMap);
			}
		}
		/****************组装组装已选基础销售品************************/
		List baseOffers = new ArrayList();//创建待组装基础销售品列表 
		
		if(offersDetail!=null&&offersDetail.size()!=0){
			/*********************获取待组装基础销售品列表*******************************/
			for(int i = 0;i<offersDetail.size();i++){
				HashMap offersDetailMap = new HashMap();
				HashMap offerBackMap = new HashMap();// 后台使用的格式
				offersDetailMap  = (HashMap)offersDetail.get(i);
				String comp_inst_id = (String)offersDetailMap.get("product_offer_instance_id");
				String rela_offer_inst_id = (String)offersDetailMap.get("rela_offer_inst_id");//基础销售品实例ID
				String productId = (String)offersDetailMap.get("product");
				
				HashMap servInfo = new HashMap();//主产品的一些基本信息
				servInfo.put(SERV.comp_inst_id, rela_offer_inst_id);//对应基础销售品实例ID
				servInfo.put(SERV.product_id, productId);//对应的规格ID数据
				
				String serv_id = (String)offersDetailMap.get("serv_id");
				servIds.put(serv_id, servInfo);//把主产品对应的基本信息放入MAP中
	
				
				
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
				/**********因为传入的成员基础销售品实例会有相同的 所以要剔除已经增加的相同的基础销售品实例***********************/
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
			/*********************开始组装基础销售品*******************************/
			
			if(baseOffers!=null&&baseOffers.size()!=0&&"1".equals(offerData.offerType)){
				for(int i = 0;i<baseOffers.size();i++){
					HashMap baseOffersMap = new HashMap();
					HashMap baseBackMap = new HashMap();// 后台使用的格式
					baseOffersMap  = (HashMap)baseOffers.get(i);
					//转换格式
					baseBackMap = DataUtil.getInst().parseBaseOffer(baseOffersMap, offerData.getLoginInfo(), custId,cust_ord_id);
					baseBackMap.put("offer_type", "2");//表示为销售品成员明细产品关联的基础销售品
					offersInst.add(baseBackMap);
				}
			}
		}
		/***************销售品实例信息部分组装完成************************/
	}
	
	
	/**
	 * 初始化销售品构成明细
	 * @param offerData
	 * @param offersInst
	 * @param OfferInstDetails
	 */
	public void initOfferInstDetail(OfferData offerData,List offersInst,List OfferInstDetails){
		/****************组装销售品构成明细************************/
		if(offersInst!=null&&offersInst.size()!=0){
			for(int i = 0;i<offersInst.size();i++){
				HashMap offerInstMap = new HashMap();
				HashMap offerInstDetailBackMap = new HashMap();// 后台使用的格式
				offerInstMap  = (HashMap)offersInst.get(i);
				String offer_kind = (String)offerInstMap.get("offer_kind");
				String offer_type = (String)offerInstMap.get("offer_type");
				offerInstMap.remove("offer_type");
				if("1".equals(offer_type)){//包外优惠
					String product_offer_instance_id = (String)offerInstMap.get("product_offer_instance_id");
					String offer_id = (String)offerInstMap.get("product_offer_id");
					
					HashMap saleMap = DataUtil.getInst().getBaseInstBySaleInst(product_offer_instance_id, offerSales);
					
					String rela_offer_instance_id =  (String)saleMap.get("rela_offer_instance_id");//获取关联基础销售品实例
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
				else if ("0".equals(offer_type)){//基础销售包或可选包
					
						HashMap basePack = (HashMap)offers.get(0);//获取基础包
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
				else if ("2".equals(offer_type)&&"1".equals(offerData.offerType)){//基础销售品
					
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
	
	
	
	/** 初始化销售品属性
	 * @param offerData
	 * @param offersInstAttrs
	 * @param offersInst
	 */
	public void initOfferInstAttr(OfferData offerData,List offersInstAttrs,List offersInst)
	{
		/****************组装销售品属性信息************************/
		if(offersAttr!=null&&offersAttr.size()!=0){
			for(int i = 0;i<offersAttr.size();i++){
				HashMap offerAttrMap = new HashMap();
				HashMap offerAttrBackMap = new HashMap();// 后台使用的格式
				offerAttrMap  = (HashMap)offersAttr.get(i);
				String prodct_offer_inst_id =(String) offerAttrMap.get(BusiTables.OFFER_INST_ATTR.product_offer_instance_id);
				//转换格式
				String comp_inst_id = DataUtil.getInst().getOfferCompInstId(offersInst, prodct_offer_inst_id);
				offerAttrMap.put("comp_inst_id", comp_inst_id);
				offerAttrBackMap = DataUtil.getInst().parseOfferAttr(offerAttrMap, offerData.getLoginInfo(),cust_ord_id);
				offersInstAttrs.add(offerAttrBackMap);
			}
		}
	}

	/**
	 *  初始化主产品信息
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
				serv_attr.put(SERV.product_family_id, "10");//表示为主产品
				serv_attr.put(SERV.area_code, (String)loginInfo.get("vg_lan_code"));
				serv_attr.put(SERV.rent_date, DAOUtils.getFormatedDbDate());
				serv_attr.put(SERV.state, "00N");//表示在途
				//if(ServConsts.ACTION_TYPE_A.equals(serv_attr.))
				serv.setServ_attrs(serv_attr);
				
			/*	
				
				//设置部门ID
				request.setSite_no((String)respond.get("vg_depart_id"));
				//设置操作员工号
				request.setStaff_no((String)respond.get("vg_oper_id"));
				request.setBusiness_id((String)respond.get("vg_business_id"));
				//设置本地网ID
				request.setLan_id((String)respond.get("vg_lan_id"));
				*/
				
			}
		}
	}
	
	/**
	 * 根据页面传入的产品数据，组装成给后台用于保存的数据。
	 * @param parameterMap
	 * @return
	 */
	public VAcceptRequest createServ(Map parameterMap) throws Exception{
		//获取页面传入的对象集
		ServData servData = (ServData) parameterMap.get(Actions.PARAMETER);
		//组装后的数据。
		VAcceptRequest vrequest = new VAcceptRequest();
		//获取公共信息，包括工号、客户、订单。
		initCommonData(servData,vrequest);
		
		//组装产品实例的数据。
		List servs = initServs(servData,vrequest);
		vrequest.setServs(servs);
		
		//组装产品属性的数据。
		List servAttrs = initServAttrs(servData);
		vrequest.setServAttrs(servAttrs);
		
		//组装附属产品实例的数据
		List servProducts = initServProducts(servData);
		vrequest.setServProducts(servProducts);
		
		//组装附属产品实例属性的数据
		List servProductAttrs = initServProductAttrs(servData);
		vrequest.setServProductAttrs(servProductAttrs);
		
		//组装帐务定制关系的数据。
		List servAccts = initServAccts(servData);
		vrequest.setServAccts(servAccts);
		
		//保存产品信息数据。busi_type 为servAccept
		vrequest.setBusi_type(Services.SERV_ACCEPT);
		
		return vrequest;
	}
	
	/**
	 * 获取主产品实例
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServs(ServData servData,VAcceptRequest vrequest) throws Exception{
		
		ArrayList servs = new ArrayList(); //用于记录返回的主产品实例。
		
		List inServs = servData.getServs();
		Iterator itra = inServs.iterator();
		//循环每个主产品。
		while (itra.hasNext()){
			Map voMap = (Map) itra.next();
			
			//将页面传入的serv转成Vserv
			VServ vserv = changeToVServ(voMap,servData,vrequest);
			servs.add(vserv);
		}
		return servs;
	}
	
	/**
	 * 将页面传入的主产品转成 VServ
	 * @param servMap
	 * @return
	 * @throws Exception
	 */
	public VServ changeToVServ(Map servMap,ServData servData,VAcceptRequest vrequest) throws Exception{
		VServ vserv = new VServ();
		if(servMap != null){
			//判断是否存在“附属产品实例加载标志”
			if(servMap.containsKey("serv_product_loaded_flag") && servMap.get("serv_product_loaded_flag") != null){
				vserv.setServ_product_loaded_flag((String)servMap.get("serv_product_loaded_flag"));
			}else{
				vserv.setServ_product_loaded_flag("F");
			}
			//判断是否存在“帐务定制信息”
			if(servMap.containsKey("serv_acct_loaded_flag") && servMap.get("serv_acct_loaded_flag")!= null){
				vserv.setServ_acct_loaded_flag((String) servMap.get("serv_acct_loaded_flag"));
			}else{
				vserv.setServ_acct_loaded_flag("F");
			}
			//判断是否存在“附加号码加载”
			if(servMap.containsKey("serv_bill_post_loaded_flag") && servMap.get("serv_bill_post_loaded_flag")!= null){
				vserv.setServ_bill_post_loaded_flag((String) servMap.get("serv_bill_post_loaded_flag"));
			}else{
				vserv.setServ_bill_post_loaded_flag("F");
			}
			//判断是否存在“主产品状态”
			if(servMap.containsKey("serv_state_loaded_flag") && servMap.get("serv_state_loaded_flag")!= null){
				vserv.setServ_state_loaded_flag((String) servMap.get("serv_state_loaded_flag"));
			}else{
				vserv.setServ_state_loaded_flag("F");
			}
			
			//转换前台传入的主产品数据。
			HashMap servAttrs = changeServ((HashMap) servMap,servData,vrequest);
			vserv.setServ_attrs(servAttrs);
		}
		return vserv;
	}
	
	/**
	 * 转换前台传入的主产品数据
	 * @param servMap
	 * @param servData
	 * @return
	 */
	public HashMap changeServ(HashMap servMap,ServData servData,VAcceptRequest vrequest)throws Exception{
		HashMap servAttrs = new HashMap();
		//将页面传入的属性设置到 serv_attrs 中。
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
			//产品状态
			if(servMap.containsKey(SERV.state) && servMap.get(SERV.state) != null){
				servAttrs.put(SERV.state, servMap.get(SERV.state));
			}else{
				servAttrs.put(SERV.state, "00A");
			}
			
			
			//起租日期
			servAttrs.put(SERV.rent_date, DAOUtils.getFormatedDate());
			
			//取客户标识
			servAttrs.put(SERV.cust_id, vrequest.getCust_id());
			
			//取产品的家族
			//根据产品ID取规格属性
			Product pro = SpecsData.getProduct((String) servMap.get(Keys.PRODUCT_ID));
			servAttrs.put(SERV.product_family_id, pro.getProduct_family_id());
			
			//去区号、本地网等信息。从session中取
			GlobalVariableHelper helper = servData.getHelper();
			//员工工号和部门
			servAttrs.put(SERV.area_code, helper.getVariable(helper.LAN_CODE));
			servAttrs.put(SERV.lan_id, helper.getVariable(helper.LAN_ID));
			servAttrs.put(SERV.business_id, helper.getVariable(helper.BUSINESS_ID));
			
		}
		return servAttrs;
	}
	
	/**
	 * 获取页面传入的主产品属性。
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServAttrs(ServData servData) throws Exception{
		ArrayList servAttrs = new ArrayList(); //用于记录返回的主产品属性实例。

		//取出产品属性，进行数据转换
		List inServAttrs = servData.getServAttrs();
		Iterator attrItra = inServAttrs.iterator();
		//循环主产品的属性数据。可以在中间进行补数据处理。
		while(attrItra.hasNext()){
			Map attrMap = (Map) attrItra.next();
			//主产品实例标识
			String serv_id = (String) attrMap.get(SERV.serv_id);
			Iterator attrIter = attrMap.entrySet().iterator();
			while(attrIter.hasNext()){
				Entry entry = (Entry) attrIter.next();
				if( entry.getValue()!= null&& !"".equals(entry.getValue())){
					//转换页面传入的主产品属性
					HashMap servAttrMap = changeServAttr(entry,serv_id);
					servAttrs.add(servAttrMap);
				}
			}
		}

		return servAttrs;
	}


	/**
	 * 转换一个主产品属性。
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
	 * 获取从页面返回的附属产品实例
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServProducts(ServData servData) throws Exception{
		ArrayList servProducts = new ArrayList(); //用于记录返回的附属产品实例。
		
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
			//状态
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
	 * 获取从页面返回的附属产品实例属性
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServProductAttrs(ServData servData) throws Exception{
		ArrayList servProductAttrs = new ArrayList(); //用于记录返回的附属产品实例。
		//属性名要转一下，如果attr_value 要改写成 attr_val
		ArrayList inProductAttrs = (ArrayList) servData.getServProductAttrs();
		Iterator productAttrIter = inProductAttrs.iterator();
		while(productAttrIter.hasNext()){
			Map attrMap = (Map) productAttrIter.next();
			//转换页面传入的附属产品属性
			HashMap productAttrMap = changeProductAttr((HashMap) attrMap);
			
			servProductAttrs.add(productAttrMap);
		}

		return servProductAttrs;
	}
	
	
	/**
	 * 转换一个附属产品属性。
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
	 * 获取从页面传入的帐务关系，进行组装数据。
	 * @param servData
	 * @return
	 * @throws Exception
	 */
	public ArrayList initServAccts(ServData servData)throws Exception{
		ArrayList servAccts = new ArrayList(); //用于记录返回的帐务关系实例。
		
		ArrayList inAcctsList = (ArrayList) servData.getServAccts();
		Iterator servAcctIter = inAcctsList.iterator();
		while(servAcctIter.hasNext()){
			Map acctMap = (Map) servAcctIter.next();
			//转换页面传入的附属产品属性
			HashMap servAcctMap = changeServAcctMap((HashMap) acctMap);
			
			servAccts.add(servAcctMap);
		}
		
		return servAccts;
	}
	
	/**
	 * 转换一个帐务关系。
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
			//临时写入。
			returnMap.put(SERV_ACCT.invoice_require_id,"1");
		}
		if(acctMap.containsKey(SERV_ACCT.bill_require_id) && acctMap.get(SERV_ACCT.bill_require_id) != null){
			returnMap.put(SERV_ACCT.bill_require_id, acctMap.get(SERV_ACCT.bill_require_id));
		}else{
			//临时写入。
			returnMap.put(SERV_ACCT.bill_require_id,"1");
		}
		if(acctMap.containsKey(SERV_ACCT.self_flag) && acctMap.get(SERV_ACCT.self_flag) != null){
			returnMap.put(SERV_ACCT.self_flag, acctMap.get(SERV_ACCT.self_flag));
		}else{
			//临时写入。
			returnMap.put(SERV_ACCT.self_flag,"0");
		}
		
		return returnMap;
	}
	
	/**
	 * 从页面传入的公共信息中获取工号、客户标识、客户订单标识等信息。
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
			//设置客户标识、客户订单标识
			vrequest.setCust_id(cust_id);
			vrequest.setCust_ord_id(cust_ord_id);
		}
		
		GlobalVariableHelper helper = servData.getHelper();
		//员工工号和部门
		vrequest.setStaff_no(helper.getVariable(helper.OPER_ID));
		vrequest.setSite_no(helper.getVariable(helper.DEPART_ID));
		
		//设置本地网，营业区
		vrequest.setLan_id(helper.getVariable(helper.LAN_ID));
		vrequest.setBusiness_id(helper.getVariable(helper.BUSINESS_ID));
	}
}
