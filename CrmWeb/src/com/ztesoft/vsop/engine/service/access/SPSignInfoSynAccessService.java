
package com.ztesoft.vsop.engine.service.access;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztesoft.vsop.engine.ErrorCode;
import com.ztesoft.vsop.engine.bo.SPSignInfoSynBO;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.IAccess;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.SPSignInfoVO;
import com.ztesoft.vsop.order.XMLUtils;

/**
 * <pre>
 * Title:SP/CP业务能力同步
 * Description: SP/CP业务能力同步 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class SPSignInfoSynAccessService extends CommonAccessService implements IAccess{
	
	private static Logger logger = Logger.getLogger(SPSignInfoSynAccessService.class);
	public  Map concreteInOpertion(Map in) throws Exception{
		in.put("resultCode", "0");
		in.put("resultDesc", "成功");
		CustomerOrder order = new CustomerOrder();
		in.put("busiObject", order);
		String requestXML = (String) in.get("accessInObject");
		//格式化报文
		SPSignInfoVO vo ;
		try{
			vo = parseXml(requestXML);
		}catch(Exception e){
			//如果格式化报文异常  说明上送报文有异常，不错后续处理 直接返回
			in.put("resultCode", "1001");
			in.put("resultDesc", ErrorCode.PARSEXML_ERROR);
			logger.error(e);
			return in;
		}
		//检测上送报文是否异常
		if(!checkReqData(vo)){
			in.put("resultCode", "1002");
			in.put("resultDesc", ErrorCode.DATA_ERROR);
			logger.error(ErrorCode.DATA_ERROR);
			return in;
		}
		//业务逻辑实现
		SPSignInfoSynBO  bo = new SPSignInfoSynBO();
		in = bo.execute(vo, in);
		
		in.put("accessObject", vo);
		return in;
		
	}
	/**
	 * 后续处理
	 */
	public  Map concreteOutOpertion(Map in) throws Exception {
		SPSignInfoVO vo  = (SPSignInfoVO) in.get("accessObject");
		String streamingNo = vo==null?"":vo.getStreamingNo();
		String resultCode = (String) in.get("resultCode");
		String resultDesc = (String) in.get("resultDesc");
		String responseXML = getResponseXML(streamingNo,resultCode,resultDesc);
		in.put("accessOutObject", responseXML);
		
		return null;
	}
	
	/**
	 * 格式化XML报文 并组装成VO
	 * @param requestXML
	 * @return
	 */
	public  SPSignInfoVO parseXml(String requestXML)throws Exception{
		SPSignInfoVO vo = new SPSignInfoVO();
		SAXReader saxReader = new SAXReader(false);
		StringReader reader = new StringReader(requestXML);
		Document doc = saxReader.read(reader);
		Element requestEle = doc.getRootElement();
		Element serssionBodyEle = requestEle.element("SessionBody");
		Element root = serssionBodyEle.element("SPSignInfoSyncToVSOPReq");
		vo.setStreamingNo(XMLUtils.getElementStringValue(root,"StreamingNo"));
		vo.setOPFlag(XMLUtils.getElementStringValue(root,"OPFlag"));
		vo.setTimeStamp(XMLUtils.getElementStringValue(root,"TimeStamp"));
		ArrayList spSignList = new ArrayList();
		List spSignInfoList = root.elements("SPSignInfo");
		if(spSignInfoList!=null){
			for(int i = 0;i<spSignInfoList.size();i++){
				Map map = new HashMap();
				Element spSignElement = (Element)spSignInfoList.get(i);
				map.put("SPSignID", XMLUtils.getElementStringValue(spSignElement,"SPSignID"));
				map.put("SPID", XMLUtils.getElementStringValue(spSignElement,"SPID"));
				map.put("serviceCapabilityID", XMLUtils.getElementStringValue(spSignElement,"serviceCapabilityID"));
				map.put("effectiveTime", XMLUtils.getElementStringValue(spSignElement,"effectiveTime"));
				map.put("expiryTime", XMLUtils.getElementStringValue(spSignElement,"expiryTime"));
				spSignList.add(map);
			}
		}
		vo.setSPSignInfo(spSignList);
		return vo;
		
	}
	/**
	 * 檢測上送報文是否異常
	 * @param vo
	 * @return
	 */
	public boolean checkReqData(SPSignInfoVO vo){
		if(vo==null){
			return false;
		}
		if(vo.getStreamingNo()==null||"".equals(vo.getStreamingNo())){
			return false;
		}
		if(vo.getOPFlag()==null||"".equals(vo.getOPFlag())){
			return false;
		}
		if(vo.getTimeStamp()==null||"".equals(vo.getTimeStamp())){
			return false;
		}
		if(vo.getSPSignInfo()==null||vo.getSPSignInfo().size()==0){
			return false;
		}
		return  true;
	}
	public String getResponseXML(String streamingNo,String resultCode,String resultDesc){
		String retXml = "";
		StringBuffer bf = new StringBuffer("");
		bf.append("<Response>");
		bf.append("<SPSignInfoSynToVSOPResp>")
		.append("<StreamingNo>").append(streamingNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultDesc).append("</ResultDesc>")
		.append("</SPSignInfoSynToVSOPResp>");
		bf.append("</Response>");
		
		return bf.toString();
		
	}
}
