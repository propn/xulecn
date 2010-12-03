package com.ztesoft.vsop.engine.service.access;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.AproductInfo;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.OrderRelationVO;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.order.XMLUtils;
import com.ztesoft.vsop.provinceUtil.AppendLanCode;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.web.vo.ProdOffVO;

/**
 * 服务开通工单处理定时任务接入组件
 * @author cooltan
 *
 */
public class WorkSheetAcceptJobAccessService extends CommonAccessService
		implements IAccess {

	public WorkSheetAcceptJobAccessService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 个性化接入操作  由每个子类重写
	 * @param in
	 * @return
	 */
	public  Map concreteInOpertion(Map in) throws Exception{
		String requestXml=(String)in.get("accessInObject");
		CustomerOrder order=new CustomerOrder();
		this.loadFromXml(requestXml, order);
		in.put("busiObject", order);
		in.put("serviceCode",order.getCustOrderType());
		return in;
	}
	/**
	 * 个性化返回操作  由每个子类重写
	 * @param in
	 * @return
	 */
	public  Map concreteOutOpertion(Map in) throws Exception{
		StringBuffer bf = new StringBuffer();
		String resultCode=(String)in.get("resultCode");
		String resultMsg=(String)in.get("resultMsg");
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		bf.append("<").append("ResultInfo").append(">")
		.append("<orderId>").append(order.getCustOrderId()).append("</orderId>")
		.append("<resultCode>").append(resultCode).append("</resultCode>")
		.append("<resultMessage>").append(resultMsg).append("</resultMessage>")
		.append("</").append("ResultInfo").append(">");
		in.put("accessOutObject", bf.toString());
		return in;
	}
	private void loadFromXml(String inXML,CustomerOrder order) throws  Exception {
		order.setCustSoNumber(XMLUtils.getSingleTagValue(inXML,"StreamingNo"));
		order.setReceiveDate(XMLUtils.getSingleTagValue(inXML,"TimeStamp"));
		order.setOrderSystem(XMLUtils.getSingleTagValue(inXML,"SystemId"));
		order.setOrderChn(XMLUtils.getSingleTagValue(inXML,"SystemId"));
		order.setOtherSysOrderId(XMLUtils.getSingleTagValue(inXML,"OrderId"));
		order.setCustOrderType(XMLUtils.getSingleTagValue(inXML,"ActionType"));
		order.setProdInstId(XMLUtils.getSingleTagValue(inXML,"PorductInstID"));
		order.setLanId(XMLUtils.getSingleTagValue(inXML,"ReginID"));
		//order.setProdId(XMLUtils.getSingleTagValue(inXML,"ProdSpecCode"));
		order.setAccNbr(XMLUtils.getSingleTagValue(inXML,"ProductNo"));
		order.setOldAccNbr(XMLUtils.getSingleTagValue(inXML,"OldProductNo"));
		order.setStatus(OrderConstant.orderStateOfCreated);
		
		//userName UIMNO oldUIMNO
		order.setUserName(XMLUtils.getSingleTagValue(inXML,"UserName"));
		order.setUimNO(XMLUtils.getSingleTagValue(inXML,"UIMNo"));
		order.setOldUimNO(XMLUtils.getSingleTagValue(inXML,"OldUIMNo"));
		
		String prodSpecCode= XMLUtils.getSingleTagValue(inXML,"ProdSpecCode");
		//江西本地化处理
		//panshaohua 2010-8-9 根据接口的主产品编码，得到产品id
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			if(!("C7".equals(prodSpecCode) || "I8".equals(prodSpecCode)) ){  //如果不是手机，则区号+号码作为产品号码
				order.setAccNbr(order.getLanId()+order.getAccNbr());
				order.setOldAccNbr(order.getLanId()+order.getOldAccNbr());
			}
			prodSpecCode=DcSystemParamManager.getInstance().getProductIdByNbr(prodSpecCode);
			order.setProdId(prodSpecCode);
		}else 
			order.setProdId(prodSpecCode);
		//本地化处理，如广西需要将区号拼凑在号码前面
		String pAccNbr=AppendLanCode.getInstance().appendAccNbrLan(order.getAccNbr(), order.getProdId(), order.getLanId());
		order.setAccNbr(pAccNbr);
		
		String seq = XMLUtils.getSingleTagValue(inXML,"InOrderId");
		if(!"".equals(seq)){
			order.setCustOrderId(seq);
		}
		String pStateCd=XMLUtils.getSingleTagValue(inXML,"UserState");
		String pPaymentModeCd=XMLUtils.getSingleTagValue(inXML,"UserPayType");
		//广西本地化处理，把服开从CRM传过来付费类型转换成VSOP识别的付费类型(crm的-1)
		if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode) && null!=pPaymentModeCd 
				&& !"".equals(pPaymentModeCd)){
			int  intpayMentCd=Integer.parseInt(pPaymentModeCd);
			pPaymentModeCd=String.valueOf(intpayMentCd-1);
		}
		String fkProdInstid=order.getProdInstId();
		//如果是新增产品则新建一个产品实例 获取产品实例标识序列
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType())){
				String prodInstId = null;
				SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
				if("".equals(order.getProdInstId()))
				prodInstId= aSequenceManageDAOImpl.getNextSequence("seq_prod_inst_id");
				else prodInstId = order.getProdInstId();
				order.setProdInstId(prodInstId);
				ProdInstVO aProdInstVO=new ProdInstVO();
				aProdInstVO.setProdInstId(prodInstId);
				aProdInstVO.setAccNbr(order.getAccNbr());
				aProdInstVO.setProdId(order.getProdId());
				aProdInstVO.setPaymentModeCd(pPaymentModeCd);
				aProdInstVO.setStateCd(ProdInstVO.StateOfEffective);
				aProdInstVO.setLanId(order.getLanId());
				order.setProdInst(aProdInstVO);
				////如果服开传送产品实例标识，以产品实例标识查询产实例 江西本地化
		}else if(fkProdInstid!=null&&!"".equals(fkProdInstid)
				&&CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
			ProdInstVO aProdInstVO = null;
			aProdInstVO = aProdInstHelpDao.qryProdInstByProdInstId(fkProdInstid);
			aProdInstVO.setStateCd(pStateCd);
			aProdInstVO.setPaymentModeCd(pPaymentModeCd);
			order.setProdInstId(aProdInstVO.getProdInstId());
			order.setProdInst(aProdInstVO);
			
		}else{//否则通过号码、产品类型产品实例
			ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
			String accNbr=order.getAccNbr();
			if(OrderConstant.orderTypeOfModifyNo.equals(order.getCustOrderType())
					||OrderConstant.orderTypeOfTransferUser.equals(order.getCustOrderType())){
				if(null != order.getOldAccNbr() || !"".equals(order.getOldAccNbr()))
					accNbr=order.getOldAccNbr();
			}
			String productId=order.getProdId();
			String actType = order.getCustOrderType();
			ProdInstVO aProdInstVO = null;
			//改用户状态、拆机、改号、改付费类型动作只要是非00X的用户都可操作
			if(actType != null && 
			   ("11".equals(actType) || "15".equals(actType) 
			    || "12".equals(actType) || "16".equals(actType) || "20".equals(actType))){
				//当true时除00X（拆机）状态的都为有效状态。
				aProdInstVO=aProdInstHelpDao.qryProdInstByAccNbrAndProductId(accNbr, productId, true);
			}else{
				//当false时只有00A（正常）00B，00C状态为有效状态。
				aProdInstVO=aProdInstHelpDao.qryProdInstByAccNbrAndProductId(accNbr, productId, false);
			}
			//江西本地化
			//江西的固话移机业务的时候(改号码和改服务功能),如果先送改服务功能,就需要用oldAccNbr
			if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode) &&
				OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())){
				if(null != order.getOldAccNbr() || !"".equals(order.getOldAccNbr())){
					if(null == aProdInstVO.getProdInstId() || "".equals(aProdInstVO.getProdInstId())){
						accNbr = order.getOldAccNbr();
						aProdInstVO=aProdInstHelpDao.qryProdInstByAccNbrAndProductId(accNbr, productId, false);
						order.setAccNbr(accNbr);
					}
				}
			}
			aProdInstVO.setStateCd(pStateCd);
			aProdInstVO.setPaymentModeCd(pPaymentModeCd);
			order.setProdInstId(aProdInstVO.getProdInstId());
			order.setProdInst(aProdInstVO);
		}
		//广西本地化：广西服开由于不能区分附属产品和增值产品，服开会将附属和增值产品都以增值产品的形式（因增值产品节点有有效和失效时间）
		//提供给vsop，动作：20，vsop在解析xml处理，划分出附属产品和增值产品
		//解析订单销售品信息
		//江西本地化处理
		//panshaohua 2010-8-14 
		if(CrmConstants.JX_PROV_CODE.equals(this.provinceCode)){
			this.jxParseVProductInfoList(inXML,order);
		}else 
			this.parseVProductInfoList(inXML,order);
		//广西服开送过来的xml中是没有附属产品节点vproductinfo
		//解析订单业务能力附属产品信息
		this.parseAProductInfoList(inXML,order);
	}
	/**
	 * 获取订单业务能力附属产品列表对象
	 * @param inXML
	 * @param order
	 */
	private void parseAProductInfoList(String inXML,CustomerOrder order) {
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType()) 
				|| OrderConstant.orderTypeOfModifyAProduct.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfUninstall.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfTransferUser.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyPayType.equals(order.getCustOrderType())){
			List aproductInfoList = new ArrayList();
			
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "AProductInfo";
			bf.append("<").append(tagName ).append(">([\\s\\S]*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			while(matcher.find()){
				result = matcher.group(1);
				String aProductActionType=XMLUtils.getSingleTagValue(result,"ActionType");
				String aProductID=XMLUtils.getSingleTagValue(result,"AProductID");
				if(null == aProductID || "".equals(aProductID))
					continue;
				String aProductInstID=XMLUtils.getSingleTagValue(result,"AProductInstID");
				//业务动作是订购，并且没有传实例id，用序列生成一个
				if(OrderConstant.orderTypeOfAdd.equals(aProductActionType) && "".equals(aProductInstID)){
					SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
					aProductInstID= aSequenceManageDAOImpl
							.getNextSequence("SEQ_BIZ_CAPABILITY_INST_ID");
				}
					
				AproductInfo aproduct=new AproductInfo();
				aproduct.setActionType(aProductActionType);
				aproduct.setAProductID(aProductID);
				aproduct.setAProductInstID(aProductInstID);
				aproduct.setLanCode(order.getLanId());
				aproductInfoList.add(aproduct);
			}
			if(null!=order.getAproductInfoList()){//广西在前面已经解析出了能力附属产品
				aproductInfoList.addAll(order.getAproductInfoList());
			}
			order.setAproductInfoList(aproductInfoList);
		}else{
			order.setAproductInfoList(new ArrayList());
		}
	}
	/**
	 * 获取订单增值产品列表信息
	 * @param inXML
	 * @param order
	 * @throws SQLException
	 */
	private void parseVProductInfoList(String inXML,CustomerOrder order) throws SQLException {
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType())
				|| OrderConstant.orderTypeOfModifyVProduct.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())){
			List productOfferInfoList = new ArrayList();
			String offNbr = XMLUtils.getSingleTagValue(inXML, "OffNbr");
			String offerId=null;
			if("".equals(offNbr)){
				offNbr=XMLUtils.getSingleTagValue(inXML, "ProductOfferId");
				offerId=DcSystemParamManager.getInstance().getProdOfferIdByNbr(offNbr);
			}else{
				offerId=XMLUtils.getSingleTagValue(inXML, "ProductOfferId");
			}
			ProductOfferInfo aProductOfferInfo=new ProductOfferInfo();
			aProductOfferInfo.setOfferId(offerId);
			aProductOfferInfo.setOfferNbr(offNbr);
			String offerType=OrderConstant.PROD_OFFER_TYPE_PPROD_OFFER_ID;
			//广西从crm过来的销售品类型也是纯增值的，如传统+增值送给ismp接口是不兼容的 liuyuming 20100926
			if(CrmConstants.GX_PROV_CODE.equals(this.provinceCode)){
				offerType=OrderConstant.PROD_OFFER_TYPE_PRODUCT_OFFER_ID;
			}
			aProductOfferInfo.setOfferType(offerType);
			/*aProductOfferInfo.setExpDate(expDate);
			aProductOfferInfo.setEffDate(effDate);
			aProductOfferInfo.setActioType(actioType);*/
			
			List vproductInfoList=new ArrayList();
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "VProductInfo";
			bf.append("<").append(tagName ).append(">([\\s\\S]*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			//广西本地化：广西服开由于不能区分附属产品和增值产品，服开会将附属和增值产品都以增值产品的形式（因增值产品节点有有效和失效时间）
			//提供给vsop，动作：20，vsop在解析xml处理，划分出附属产品和增值产品  start
			String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
			if(CrmConstants.GX_PROV_CODE.equals(provinceCode)){
				//附属产品列表
				List aproductInfoList = new ArrayList();
				while(matcher.find()){
					result = matcher.group(1);
					//取出VProductID增值产品id如果在缓存中serviceAbility业务能力规格编码存在，则是附属产品，反之则是增值产品
					String vProdId = XMLUtils.getSingleTagValue(result,"VProductID");//广西能力附属产品的id和nbr一致
					String _value = (String)DcSystemParamManager.getInstance().getCrmCProdByCode(vProdId);
					if(_value == null || "".equals(_value)){
						//找不到附属产品匹配的编码则就是增值产品
						String isActive = (String)DcSystemParamManager.getParameter("IS_ACTIVE");
						//广西VSOP对接服开分步上线，先不处理增值产品（还是由服开送业务平台激活），之后再改成VSOP送激活
						//liuyuming 20100913
						String accNbrStr = (String)DcSystemParamManager.getParameter("IS_FK_TEST_NBR");
						if(isActive == null || "T".equals(isActive) || order.getAccNbr().indexOf(accNbrStr)>-1){
							//isActive：T送激活，F不送激活
							VproductInfo vproduct = new VproductInfo();
							this.parseAVproductInfo(result, vproduct, order,offerId,offNbr,offerType);
							vproductInfoList.add(vproduct);
						}
						//end
					}else{
						//否则就是附属产品节点数据
						String aProductActionType=XMLUtils.getSingleTagValue(result,"ActionType");
						String aProductID=vProdId;//直接用服开送来的能力附属产品ID
						String aProductInstID=XMLUtils.getSingleTagValue(result,"VProductInstID");
						//业务动作是订购，并且没有传实例id，用序列生成一个
						if(OrderConstant.orderTypeOfAdd.equals(aProductActionType) && "".equals(aProductInstID)){
							SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
							aProductInstID= aSequenceManageDAOImpl
									.getNextSequence("SEQ_BIZ_CAPABILITY_INST_ID");
						}
							
						AproductInfo aproduct=new AproductInfo();
						aproduct.setActionType(aProductActionType);
						aproduct.setAProductID(aProductID);
						aproduct.setAProductInstID(aProductInstID);
						aproduct.setLanCode(order.getLanId());
						aproductInfoList.add(aproduct);
					}
				}
				order.setAproductInfoList(aproductInfoList);
				//广西本地化 end;
			}else{
				while(matcher.find()){
					result = matcher.group(1);
					VproductInfo vproduct = new VproductInfo();
					this.parseAVproductInfo(result, vproduct, order,offerId,offNbr,offerType);
					vproductInfoList.add(vproduct);
				}
			}
			if(vproductInfoList.size() > 0){
				aProductOfferInfo.setVproductInfoList(vproductInfoList);
				productOfferInfoList.add(aProductOfferInfo);
				order.setProductOfferInfoList(productOfferInfoList);
			}else{
				order.setProductOfferInfoList(productOfferInfoList);
			}
		}else{
			order.setProductOfferInfoList(new ArrayList());
		}
		
	}
	/**
	 * 通过xml构造单个订单增值产品信息
	 * @param xml
	 * @param vproduct
	 * @param order
	 * @param offerId
	 * @param offerNbr
	 * @param offerType
	 * @throws SQLException
	 */
	public void parseAVproductInfo(String xml, 
			VproductInfo vproduct,CustomerOrder order,String offerId,String offerNbr,
			String offerType) throws SQLException {
		
		vproduct.setActionType(XMLUtils.getSingleTagValue(xml, "ActionType"));
		String vproductNbr = XMLUtils.getSingleTagValue(xml, "VProductNbr");
		if("".equals(vproductNbr)){ //若没有VProductNbr节点则取VProductID，然后转换
			String tmpNbr = "";
			String tmpId = "";
			//广西从服开送过来的增值产品的编码是crm编码，应转换成业务平台外码和vsop的内部
			String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
			if(CrmConstants.GX_PROV_CODE.equals(provinceCode)){
				String id = XMLUtils.getSingleTagValue(xml,"VProductID");//服开过来的对应CRM_PRODUCT_CODE
				tmpNbr = DcSystemParamManager.getInstance().getCrmCodeIsmpNbrByCode(id);//ISMP_PRODUCT_NBR
				tmpId = DcSystemParamManager.getInstance().getProductIdByNbr(tmpNbr);//VSOP_PRODUCT_ID
				//服开送过来crm订购的增值产品对应的纯增值销售品
				if(offerId == null || "".equals(offerId))
					offerId = DcSystemParamManager.getInstance().getofferIdByProductId(tmpId);//VSOP_OFFER_ID
				if(offerNbr == null || "".equals(offerNbr))
					offerNbr = DcSystemParamManager.getInstance().getProdOfferNbrById(offerId);//ISMP_OFFER_NBR
			}else{
				tmpNbr=XMLUtils.getSingleTagValue(xml,"VProductID");
				tmpId=DcSystemParamManager.getInstance().getProductIdByNbr(tmpNbr);
			}
			vproduct.setVProductNbr(tmpNbr);
			vproduct.setVProductId(tmpId);
		}else{  //下面是服务开通定时任务的时候解析报文用的
			vproduct.setVProductNbr(vproductNbr);
			String tmpId=XMLUtils.getSingleTagValue(xml, "VProductID");
			vproduct.setVProductId(tmpId);
		}
		String vProductInstID=XMLUtils.getSingleTagValue(xml,"VProductInstID");
		//业务动作是订购，并且没有传实例id，用序列生成一个
		if(OrderConstant.orderTypeOfAdd.equals(vproduct.getActionType()) 
				&& "".equals(vProductInstID)){
			SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
			String s= aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
			vproduct.setVProductInstID(s);
		}else if(OrderConstant.orderTypeOfDel.equals(vproduct.getActionType())){
			OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
			//查询数据库获取订购关系实例标识
			OrderRelationVO orderVO=aOrderRelationHelpDao.qryORByProdInstIdAndProductId(order.getProdInstId(),
					vproduct.getVProductId());
			String vprodInstId="";
			if(null!=orderVO){
				vprodInstId =orderVO.getOrderRelationId();
			}
			vproduct.setVProductInstID(vprodInstId);
		}
		vproduct.setEffDate(XMLUtils.getSingleTagValue(xml,"EffDate"));
		vproduct.setExpDate(XMLUtils.getSingleTagValue(xml,"ExpDate"));
		vproduct.setOfferId(offerId);
		vproduct.setOfferNbr(offerNbr);
		vproduct.setOfferType(offerType);
	}

	
	
	//江西本地化
	/**
    1：productOfferId 不为空
      vprocuctinfo  全为上面销售品   VProductID增值产品编码
     2:productOfferId 为空
    2.1  VProductID 为销售品编码 判断 （offer_sub_type 为1 或者为2） 查销售品（判断是否是纯增值捆绑销售品或者传统—+增值捆绑销售品，
       如果是，按照捆绑销售品处理逻辑进行处理； 如果不是，当做增值产品处理） 
       2.2  VproductId 是纯增值产品，根据增值产品去查相应的纯增值销售品
	
	 * 获取订单增值产品列表信息
	 * @param inXML
	 * @param order
	 * @throws SQLException
	 */
	private void jxParseVProductInfoList(String inXML,CustomerOrder order) throws SQLException {
		if(OrderConstant.orderTypeOfInstall.equals(order.getCustOrderType())
				|| OrderConstant.orderTypeOfModifyVProduct.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyServiceFunction.equals(order.getCustOrderType())
				||OrderConstant.orderTypeOfModifyPayType.equals(order.getCustOrderType())
//				||OrderConstant.orderTypeOfUninstall.equals(order.getCustOrderType())  拆机不需要解析，不送激活 yi.chengfeng @2010-9-29 22:38
				||OrderConstant.orderTypeOfTransferUser.equals(order.getCustOrderType())){
			List productOfferInfoList = new ArrayList();
			
			String offNbr=XMLUtils.getSingleTagValue(inXML, "ProductOfferId");
			String offerId=DcSystemParamManager.getInstance().getProdOfferIdByNbr(offNbr);
			ProductOfferInfo aProductOfferInfo=new ProductOfferInfo();
			if(null != offerId && !"".equals(offerId)){
				ProdOffVO offer = DcSystemParamManager.getInstance().getProdOffVOById(offerId);
				aProductOfferInfo.setOfferId(offerId);
				aProductOfferInfo.setOfferNbr(offNbr);
				String offerType=offer.getProdOffSubType();
				aProductOfferInfo.setOfferType(offerType);
			}
			
			List vproductInfoList=new ArrayList();
			String result  = "";
			StringBuffer bf = new StringBuffer();
			String tagName = "VProductInfo";
			bf.append("<").append(tagName ).append(">([\\s\\S]*?)</").append(tagName).append(">");
			Pattern pattern = Pattern.compile(bf.toString());
			Matcher matcher = pattern.matcher(inXML);
			//服开由于不能区分附属产品和增值产品，服开会将附属和增值产品都以增值产品的形式（因增值产品节点有有效和失效时间）
			//提供给vsop，动作：20，vsop在解析xml处理，划分出附属产品和增值产品 
				//附属产品列表
				List aproductInfoList = new ArrayList();
				while(matcher.find()){
					result = matcher.group(1);
					//取出VProductID增值产品id如果在缓存中serviceAbility业务能力规格编码存在，则是附属产品，反之则是增值产品
					String vProdId = XMLUtils.getSingleTagValue(result,"VProductID");//广西能力附属产品的id和nbr一致
					String _value = (String)DcSystemParamManager.getInstance().getCrmCProdByCode(vProdId);
					if(_value == null || "".equals(_value)){
						//找不到附属产品匹配的编码则就是增值产品或者销售品
						if(null == offerId || "".equals(offerId)){//没传销售品id，就判断VproductId 是增值产品还是销售品
							String productOfferId = DcSystemParamManager.getInstance().getProdOfferIdByNbr(vProdId);
							if(null == productOfferId || "".equals(productOfferId)){//vprodId为增值产品id，需要根据增值产品id去查找纯增值销售品
								String productId = DcSystemParamManager.getInstance().getProductIdByNbr(vProdId);
								productOfferId = DcSystemParamManager.getInstance().getofferIdByProductId(productId);
							}
							ProductOfferInfo productOfferInfo=new ProductOfferInfo();
							ProdOffVO offer = DcSystemParamManager.getInstance().getProdOffVOById(productOfferId);
							productOfferInfo.setOfferId(productOfferId);
							productOfferInfo.setOfferNbr(offer.getOffNbr());
							String offerType=offer.getProdOffSubType();
							productOfferInfo.setOfferType(offerType);
							productOfferInfo.setActioType(XMLUtils.getSingleTagValue(result, "ActionType"));
							this.getProductByOfferId(productOfferInfo,order,result);//得到销售品对应的所有增值产品
							productOfferInfoList.add(productOfferInfo);
						}else {// 如果传了销售品id,vproductId就是 增值产品
							VproductInfo vproduct = new VproductInfo();
							this.parseAVproductInfo(result, vproduct, order,offerId,offNbr,aProductOfferInfo.getOfferType());
							vproductInfoList.add(vproduct);
						}
					}else{
						//否则就是附属产品节点数据
						String aProductActionType=XMLUtils.getSingleTagValue(result,"ActionType");
						String aProductID=vProdId;//直接用服开送来的能力附属产品ID
						String aProductInstID=XMLUtils.getSingleTagValue(result,"VProductInstID");
						//业务动作是订购，并且没有传实例id，用序列生成一个
						if(OrderConstant.orderTypeOfAdd.equals(aProductActionType) && "".equals(aProductInstID)){
							SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
							aProductInstID= aSequenceManageDAOImpl
									.getNextSequence("SEQ_BIZ_CAPABILITY_INST_ID");
						}
							
						AproductInfo aproduct=new AproductInfo();
						aproduct.setActionType(aProductActionType);
						aproduct.setAProductID(_value);
						aproduct.setAProductInstID(aProductInstID);
						aproduct.setLanCode(order.getLanId());
						aproductInfoList.add(aproduct);
					}
				}
				order.setAproductInfoList(aproductInfoList);
				
			if(vproductInfoList.size() > 0){
				aProductOfferInfo.setVproductInfoList(vproductInfoList);
				productOfferInfoList.add(aProductOfferInfo);
				order.setProductOfferInfoList(productOfferInfoList);
			}else{
				order.setProductOfferInfoList(productOfferInfoList);
			}
		}else{
			order.setProductOfferInfoList(new ArrayList());
		}
	}
	private void  getProductByOfferId(ProductOfferInfo prodOfferInfo,CustomerOrder order,String xml) throws SQLException{
		List vproductList = new ArrayList();
		List vProductIdlist  =DcSystemParamManager.getInstance().getVproductIdsByOfferId(prodOfferInfo.getOfferId());
		for (int i = 0; i < vProductIdlist.size(); i++) {
			VproductInfo vprodInfo = new VproductInfo();
			vprodInfo.setOfferId(prodOfferInfo.getOfferId());
			vprodInfo.setVProductId((String) vProductIdlist.get(i));
			vprodInfo.setActionType(prodOfferInfo.getActioType());
			vprodInfo.setOfferType(prodOfferInfo.getOfferType());
			
			String vProductInstID=XMLUtils.getSingleTagValue(xml,"VProductInstID");
			//业务动作是订购，并且没有传实例id，用序列生成一个
			if(OrderConstant.orderTypeOfAdd.equals(vprodInfo.getActionType()) 
					&& "".equals(vProductInstID)){
				SequenceManageDAOImpl aSequenceManageDAOImpl = new SequenceManageDAOImpl();
				String s= aSequenceManageDAOImpl.getNextSequence("SEQ_ORDER_RELATION_ID");
				vprodInfo.setVProductInstID(s);
			}else if(OrderConstant.orderTypeOfDel.equals(vprodInfo.getActionType())){
				OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
				//查询数据库获取订购关系实例标识
				OrderRelationVO orderVO=aOrderRelationHelpDao.qryORByProdInstIdAndProductId(order.getProdInstId(),
						vprodInfo.getVProductId());
				String vprodInstId="";
				if(null!=orderVO){
					vprodInstId =orderVO.getOrderRelationId();
				}
				vprodInfo.setVProductInstID(vprodInstId);
			}
			vprodInfo.setEffDate(XMLUtils.getSingleTagValue(xml,"EffDate"));
			vprodInfo.setExpDate(XMLUtils.getSingleTagValue(xml,"ExpDate"));

			//增值产品外码
			vprodInfo.setVProductNbr(DcSystemParamManager.getInstance().getProductNbrById((String) vProductIdlist.get(i)));
			//销售品外码
			vprodInfo.setOfferNbr(prodOfferInfo.getOfferNbr());
			vproductList.add(vprodInfo);
		}
		prodOfferInfo.setVproductInfoList(vproductList);
	}
}
