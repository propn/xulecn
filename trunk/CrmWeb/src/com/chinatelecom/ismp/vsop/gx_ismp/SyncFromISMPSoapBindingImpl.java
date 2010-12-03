/**
 * SyncFromISMPSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.vsop.DateUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;
import com.ztesoft.vsop.webservice.bo.SPCPSyncServiceBo;

public class SyncFromISMPSoapBindingImpl implements com.chinatelecom.ismp.vsop.gx_ismp.SyncFromISMP_PortType{
	private static Logger logger = Logger.getLogger(SyncFromISMPSoapBindingImpl.class);
	/**
     * 广西ismp spcp同步本地化
     * Auto generated method signature
     * @param syncCSPInfoToVSOP
     */
	public com.chinatelecom.ismp.vsop.gx_ismp.CSPInfoSyncToVSOPResp syncCSPInfoToVSOP(com.chinatelecom.ismp.vsop.gx_ismp.CSPInfoSyncToVSOPReq req) throws java.rmi.RemoteException {
		logger.info("SyncFromISMPSoapBindingImpl.syncCSPInfoToVSOP start");
    	String requestXML = ConvertCSPReqToXML(req);
    	String respXml = "";
    	try {
			respXml = SPCPSyncServiceBo.getInstance().CPSPInfoSync(requestXML );
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringUtil su = StringUtil.getInstance();
			respXml=su.getVsopResponse("CSPInfoSyncToVSOPResp", 
									   su.getTagValue(requestXML, "StreamingNo"),
									   "-1",
									   e.getMessage().substring(0, 100), 
									   null);
		}
		CSPInfoSyncToVSOPResp respParam = new CSPInfoSyncToVSOPResp();
		StringUtil su = StringUtil.getInstance();
		String resultCode=su.getTagValue("ResultCode", respXml);
		respParam.setResultCode(Integer.valueOf(resultCode));
		respParam.setResultDesc(su.getTagValue("ResultDesc", respXml));
		respParam.setStreamingNo(su.getTagValue("StreamingNo", respXml));
    	logger.info("SyncFromISMPSoapBindingImpl.syncCSPInfoToVSOP end");
        return respParam;
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.ProductSyncFromISMPResp syncProductFromISMP(com.chinatelecom.ismp.vsop.gx_ismp.ProductSyncFromISMPReq req) throws java.rmi.RemoteException {
    	logger.info("SyncFromISMPSoapBindingImpl.syncProductFromISMP start");
    	String requestXML = ConvertProdSyncReqToXML(req);
    	String respXml = "";
    	System.out.println("Server receive xml ========================\n"+requestXML) ;
    	//2具体业务处理
    	Map cparam = new HashMap() ;
    	cparam.put("xml", requestXML) ;
		Map map=null;
		try {
			 map = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
							"productInfoSysIsmp" ,cparam));
			 respXml=(String)map.get("responseXml");
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		//2.2缓存处理
		String product_id=(String)map.get("product_id");
		RefreshCacheHttpClient.getInstance().refreshAllServerCaches("0", product_id);
		ProductSyncFromISMPResp respParam = new ProductSyncFromISMPResp();
		StringUtil su = StringUtil.getInstance();
		String resultCode=su.getTagValue("ResultCode", respXml);
		respParam.setResultCode(Integer.valueOf(resultCode));
		respParam.setResultDesc(su.getTagValue("ResultDesc", respXml));
		respParam.setStreamingNo(su.getTagValue("StreamingNo", respXml));
    	logger.info("SyncFromISMPSoapBindingImpl.syncProductFromISMP end");
        return respParam;
    }
    /**
     * 广西ismp销售品同步本地化
     * Auto generated method signature
     * @param syncPackageFromISMP
     */
    public com.chinatelecom.ismp.vsop.gx_ismp.PackageSyncFromISMPResp syncPackageFromISMP(com.chinatelecom.ismp.vsop.gx_ismp.PackageSyncFromISMPReq req) throws java.rmi.RemoteException {
    	logger.info("SyncFromISMPSoapBindingImpl.syncPackageFromISMP start");
    	//1获取请求内容
    	String requestXml= ConvertOfferSyncReqToXML(req);
    	//2具体业务处理

    	Map xparam = new HashMap() ;
    	xparam.put("xml", requestXml) ;
    	xparam.put("sourceSystem", "3") ;
		//System.out.println("*************************HashMap Product************************"+Product.toString());
		String responseXml = null;
		Map map=null;
		try {
			map = DataTranslate._Map(
					ServiceManager.callJavaBeanService("PPMOfferSynManager",
							"prodOffInfoSysnFromIsmp" ,xparam));
			responseXml=(String)map.get("responseXml");
		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
		}
		//2.2缓存处理
		String prod_offer_id=(String)map.get("prod_offer_id");
		RefreshCacheHttpClient.getInstance().refreshAllServerCaches("1", prod_offer_id);
		PackageSyncFromISMPResp param = new PackageSyncFromISMPResp();
		StringUtil su = StringUtil.getInstance();
		String resultCode=su.getTagValue("ResultCode", responseXml);
		param.setResultCode(Integer.valueOf(resultCode));
		param.setResultDesc(su.getTagValue("ResultDesc", responseXml));
		param.setStreamingNo(su.getTagValue("StreamingNo", responseXml));
    	logger.info("SyncFromISMPSoapBindingImpl.syncPackageFromISMP end");
		return param;
    }
    /**
     * 转换spcp复杂对象成xml字符串
     * @param cspReq
     * @return
     */
    private String ConvertCSPReqToXML(CSPInfoSyncToVSOPReq cspReq){
    	String streamNo = getString(cspReq.getStreamingNo());
    	String sysId = getString(cspReq.getSystemId());
    	java.util.Date dat= cspReq.getTimeStamp().getTime();
    	
    	String timeStamp = getString(DateUtil.formatDate(dat,DateUtil.DATE_TIME_FORMAT_14));
    	String opFlag = getString(cspReq.getOpFlag()+"");
    	SPInfo sp = cspReq.getSpInfo();
    	if(sp == null)return null;
    	StringBuffer reqXml = new StringBuffer();
    	reqXml.append("<Request><SessionBody><CSPInfoSyncToVSOPReq>");
    	reqXml.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
    		  .append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
    		  .append("<SystemId>").append(sysId).append("</SystemId>")
    		  .append("<OPFlag>").append(opFlag).append("</OPFlag>")
    		  .append("<SPInfo>")
    		  .append("<SPID>").append(getString(sp.getSpId())).append("</SPID>")
    		  .append("<Type>").append(getString(sp.getType()+"")).append("</Type>")
    		  .append("<NameCN>").append(getString(sp.getNameCN())).append("</NameCN>")
    		  .append("<NameEN>").append(getString(sp.getNameEN())).append("</NameEN>")
    		  .append("<DescriptionCN>").append(getString(sp.getDescriptionCN())).append("</DescriptionCN>")
    		  .append("<DescriptionEN>").append(getString(sp.getDescriptionEN())).append("</DescriptionEN>")
    		  .append("<CustomerCare>").append(getString(sp.getCustomerCare())).append("</CustomerCare>")
    		  .append("<WebsiteURL>").append(getString(sp.getWebsiteURL())).append("</WebsiteURL>")
    		  .append("<ProvinceID>").append(getString(sp.getProvinceID())).append("</ProvinceID>")
    		  .append("<RoamProperty>").append(getString(sp.getRoamProperty()+"")).append("</RoamProperty>")
    		  .append("<CompanyAddress>").append(getString(sp.getCompanyAddress())).append("</CompanyAddress>")
    		  .append("<LegalRepresentative>").append(getString(sp.getLegalRepresentative())).append("</LegalRepresentative>")
    		  .append("<Principal>").append(getString(sp.getPrincipal())).append("</Principal>")
    		  .append("<PrincipalTel>").append(getString(sp.getPrincipalTel())).append("</PrincipalTel>")
    		  .append("<PrincipalEmail>").append(getString(sp.getPrincipalEmail())).append("</PrincipalEmail>")
    		  .append("<ServiceManager>").append(getString(sp.getServiceManager())).append("</ServiceManager>")
    		  .append("<ServiceManagerTel>").append(getString(sp.getServiceManagerTel())).append("</ServiceManagerTel>")
    		  .append("<ServiceManagerEmail>").append(getString(sp.getServiceManagerEmail())).append("</ServiceManagerEmail>")
    		  .append("<ServiceManagerAddr>").append(getString(sp.getServiceManagerAddr())).append("</ServiceManagerAddr>")
    		  .append("<ServiceManagerPC>").append(getString(sp.getServiceManagerPC())).append("</ServiceManagerPC>")
    		  .append("<ServiceManagerFax>").append(getString(sp.getServiceManagerFax())).append("</ServiceManagerFax>")
    		  .append("<License>").append(getString(sp.getLicense())).append("</License>")
    		  .append("<ContractExpireDate>").append(getUTCString(sp.getContractExpireDate())).append("</ContractExpireDate>")
    		  .append("<AccessNO>").append(getString(sp.getAccessNO())).append("</AccessNO>")
    		  .append("<SettlementCycle>").append(getString(sp.getSettlementCycle()+"")).append("</SettlementCycle>")
    		  .append("<SettlementPayType>").append(getString(sp.getSettlementPayType()+"")).append("</SettlementPayType>")
    		  .append("<SettlementPercent>").append(getString(sp.getSettlementPercent())).append("</SettlementPercent>")
    		  .append("<CSWebsite>").append(getString(sp.getCsWebsite())).append("</CSWebsite>")
    		  .append("<Status>").append(getString(sp.getStatus()+"")).append("</Status>");
    	String _value = sp.getPlatForm();//spcp所属管理平台编码列表，以逗号分隔
    	if(_value != null && !"".equals(_value)){
    		String[] platForms = _value.split(",");
    		for(int i=0;i<platForms.length;i++){
    			reqXml.append("<PlatForm>").append(platForms[i]).append("</PlatForm>");
    		}
    	}
        reqXml.append("</SPInfo>");
    	reqXml.append("</CSPInfoSyncToVSOPReq></SessionBody></Request>");
    	return reqXml.toString();
    }
    /**
     * 解析ismp的纯增值销售品
     * @param cspReq
     * @return
     */
    private String ConvertProdSyncReqToXML(ProductSyncFromISMPReq cspReq){
    	String streamNo = getString(cspReq.getStreamingNo());
    	String sysId = getString(cspReq.getSystemId());
    	String timeStamp=getUTCString(cspReq.getTimeStamp());
//    	String opFlag = getString(cspReq.getOpFlag()+"");
    	StringBuffer reqXml = new StringBuffer();
    	reqXml.append("<Request><SessionBody><ProductSyncFromISMPReq>");
    	reqXml.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
    		  .append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
    		  .append("<SystemId>").append(sysId).append("</SystemId>")
    		  .append("<SPID>").append(getString(cspReq.getSpID())).append("</SPID>")
    		  .append("<OPFlag>").append(getString(cspReq.getOpFlag()+"")).append("</OPFlag>")
    		  .append("<ProductID>").append(getString(cspReq.getProductID())).append("</ProductID>")
    		  .append("<PnameCN>").append(getString(cspReq.getPnameCN())).append("</PnameCN>")
    		  .append("<PnameEN>").append(getString(cspReq.getPnameEN())).append("</PnameEN>")
    		  .append("<PdescriptionCN>").append(getString(cspReq.getPdescriptionCN())).append("</PdescriptionCN>")
    		  .append("<PdescriptionEn>").append(getString(cspReq.getPdescriptionEn())).append("</PdescriptionEn>")
    		  .append("<ServiceCapabilityID>").append(getString(cspReq.getServiceCapabilityID())).append("</ServiceCapabilityID>")
    		  .append("<Status>").append(getString(cspReq.getStatus()+"")).append("</Status>")
    		  .append("<ChargingPolicyCN>").append(getString(cspReq.getChargingPolicyCN())).append("</ChargingPolicyCN>")
    		  .append("<ChargingPolicyID>").append(getString(cspReq.getChargingPolicyID())).append("</ChargingPolicyID>")
    		  .append("<SubOption>").append(getString(cspReq.getSubOption()+"")).append("</SubOption>")
    		  .append("<Present>").append(getString(cspReq.getPresent()+"")).append("</Present>")
    		  .append("<CorpOnly>").append(getString(cspReq.getCorpOnly()+"")).append("</CorpOnly>")
    		  .append("<PackageOnly>").append(getString(cspReq.getPackageOnly()+"")).append("</PackageOnly>");
    	ProductOPCode[] prL = cspReq.getProductOPCode();
    	if(prL != null && prL.length > 0){
        	for(int i = 0; i < prL.length; i++){
    			reqXml.append("<ProductOPCode><FeatureStr>").append(getString(prL[i].getFeatureStr())).append("</FeatureStr>")
    				  .append("<AccessNO>").append(getString(prL[i].getAccessNO())).append("</AccessNO>")
    				  .append("<OPType>").append(prL[i].getOpType()).append("</OPType>")
    				  .append("<MatchMode>").append(prL[i].getMatchMode()).append("</MatchMode></ProductOPCode>");
    		}
    	}
    	reqXml.append("<SettlementCycle>").append(cspReq.getSettlementCycle()).append("</SettlementCycle>")
    		  .append("<SettlementPayType>").append(cspReq.getSettlementPayType()).append("</SettlementPayType>")
    		  .append("<SettlementPercent>").append(cspReq.getSettlementPercent()).append("</SettlementPercent>")
    		  .append("<Scope>").append(cspReq.getScope()).append("</Scope>")
    		  .append("<ProductHost>").append(cspReq.getProductHost()).append("</ProductHost>");
    	String _value = getString(cspReq.getPlatForm());
    	if(!"".equals(_value)){
    		String[] platForms = _value.split(",");
    		for(int i = 0 ; i<platForms.length; i++){
    			reqXml.append("<PlatForm>").append(platForms[i]).append("</PlatForm>");
    		}
    	}
    	ProdRelation prod = cspReq.getProdRelation();
    	if(prod != null){
    		reqXml.append("<ProdRelation>")
    			  .append("<OPType>").append(prod.getOpType()).append("</OPType>")
    			  .append("<RelationType>").append(prod.getRelationType()).append("</RelationType>")
    			  .append("<RProductID>").append(prod.getRproductID()).append("</RProductID>")
    			  .append("</ProdRelation>");
    	}
    	reqXml.append("</ProductSyncFromISMPReq></SessionBody></Request>");
    	return reqXml.toString();
    }
    /**
     * 解析ismp的捆绑销售品
     * @param offerReq
     * @return
     */
    private String ConvertOfferSyncReqToXML(PackageSyncFromISMPReq offerReq){
    	String streamNo = getString(offerReq.getStreamingNo());
    	String sysId = getString(offerReq.getSystemId());
//    	String timeStamp = getString(offerReq.getTimeStamp().toString());
    	String timeStamp=getUTCString(offerReq.getTimeStamp());
    	String opFlag = getString(offerReq.getOpFlag()+"");
    	StringBuffer reqXml = new StringBuffer();
    	reqXml.append("<Request><SessionBody><PackageSyncFromISMPReq>");
    	reqXml.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
    		  .append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
    		  .append("<SystemId>").append(sysId).append("</SystemId>")
    		  .append("<OPFlag>").append(opFlag).append("</OPFlag>")
    		  .append("<PackageID>").append(offerReq.getPackageID()).append("</PackageID>")
    		  .append("<PNameCN>").append(getString(offerReq.getPnameCN())).append("</PNameCN>")
    		  .append("<PNameEN>").append(getString(offerReq.getPnameEN())).append("</PNameEN>")
    		  .append("<PDesCN>").append(getString(offerReq.getPdesCN())).append("</PDesCN>")
    		  .append("<PDesEn>").append(getString(offerReq.getPdesEn())).append("</PDesEn>")
    		  .append("<Status>").append(offerReq.getStatus()).append("</Status>")
    		  .append("<ChargingPolicyCN>").append(getString(offerReq.getChargingPolicyCN())).append("</ChargingPolicyCN>")
    		  .append("<ChargingPolicyID>").append(getString(offerReq.getChargingPolicyID())).append("</ChargingPolicyID>")
    		  .append("<SubOption>").append(offerReq.getSubOption()).append("</SubOption>")
    		  .append("<PresentOption>").append(offerReq.getPresentOption()).append("</PresentOption>")
    		  .append("<CorpOnly>").append(offerReq.getCorpOnly()).append("</CorpOnly>");
    	String _value = offerReq.getProductID();
    	if(_value != null && !"".equals(_value)){
    		String[] prodIds = _value.split(",");
    		for(int i = 0; i<prodIds.length;i++){
    			reqXml.append("<ProductID>").append(prodIds[i]).append("</ProductID>");
    		}
    	}
    	reqXml.append("<Scope>").append(offerReq.getScope()).append("</Scope>");
    	ProdOfferRelation offerRela = offerReq.getProdOfferRelation();
    	if(offerRela != null){
    		reqXml.append("<ProdOfferRelation>")
    			  .append("<OPType>").append(offerRela.getOpType()).append("</OPType>")
    			  .append("<RelationType>").append(offerRela.getRelationType()).append("</RelationType>")
    			  .append("<RProdOfferID>").append(offerRela.getRprodOfferID()).append("</RProdOfferID>")
    			  .append("</ProdOfferRelation>");
    	}
    	reqXml.append("</PackageSyncFromISMPReq></SessionBody></Request>");
    	return reqXml.toString();
    }
    private String getString(Object value){
    	if(value == null)return "";
    	return value.toString();
    }
	private  String getUTCString(Calendar ca) {
		Date da=ca.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		String time=sdf.format(da);
		return time;
	}
}
