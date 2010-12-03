package com.ztesoft.vsop;

public class StringUtil {
	private static StringUtil stringUtil=new StringUtil();
	
	public static StringUtil getInstance(){
		return stringUtil;
	}
	
	/**
	 * 通过节点名获取节点值
	 * @param tagName
	 * @param xml
	 * @return
	 */
	public String getTagValue(String tagName,String xml){
		String tagValue=null;
		String tag="<"+tagName+">";
		if(xml.indexOf(tag) == -1 && xml.indexOf("<"+tagName+"/>")!=-1||xml.indexOf(tag)==-1)
			return null;
		else if(xml.indexOf(tag) != -1){
			int star=xml.indexOf(tag);
			int end=xml.indexOf("</"+tagName+">");
			tagValue=xml.substring(star+tag.length(),end).trim();
		}
		return tagValue;
	}
	
	/**
	 * 组装返回XML
	 * @param responseParamName 接口返回参数节点名
	 * @param streamNo 流水号
	 * @param resultCode 结果码
	 * @param resultDesc 结果描述
	 * @param moreResultXmls 更多的结果，如订购关系查询的ResultInfo等等
	 * @return
	 */
	public String getVsopResponse(String responseParamName,String streamNo,
			String resultCode,String resultDesc,String moreResultXmls){
		StringBuffer sb=new StringBuffer("");
		sb.append("<Response>")
		.append("<"+responseParamName+">")
		.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
		.append("<ResultCode>").append(resultCode).append("</ResultCode>")
		.append("<ResultDesc>").append(resultDesc).append("</ResultDesc>");
		
		if(!"".equals(moreResultXmls)&&moreResultXmls!=null){
			sb.append(moreResultXmls);
		}
		sb.append("</"+responseParamName+">")
		.append("</Response>");
		
		return sb.toString();
	}
	/**
	 * 组装返回XML信息
	 * @param responseBodyXml
	 * @return
	 */
	public String getVsopResponse(String responseBodyXml){
		StringBuffer sb=new StringBuffer("");
		sb.append("<Response>")
		.append(responseBodyXml)
		.append("</Response>");
		
		return sb.toString();
	}
	
	/**
	 * 组装请求XML
	 * @param streamNo 流水号
	 * @param timeStamp  当前系统时间戳
	 * @param requestBodytXmls 请求消息体，形如<接口请求参数名>....</接口请求参数名>
	 * @return
	 */
	public String getVsopRequest(String streamNo,
								 String timeStamp,
								 String requestBodytXmls){
		StringBuffer sb=new StringBuffer("");
		sb.append("<Request>")
		
/*		.append("<SessionHeader>")
		.append("<Version>").append(VsopConstants.VSOP_REQUESTMESSAGEVersion).append("</Version>")
		.append("<TID>").append(streamNo).append("</TID>")
		.append("<SendAddress>").append(VsopConstants.VSOP_SYSTEMID).append("</SendAddress>")
		.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
		.append("</SessionHeader>")*/
		
		.append("<SessionBody>")
		.append(requestBodytXmls)
		.append("</SessionBody>")
		
		.append("</Request>");
		
		return sb.toString();
	}
	/**
	 * 获取系统当前时间戳
	 * @return
	 */
	public String getCurrentTimeStamp(){
		return DateUtil.getVSOPDateTime14();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
