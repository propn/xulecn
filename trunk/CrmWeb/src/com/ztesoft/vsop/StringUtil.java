package com.ztesoft.vsop;

public class StringUtil {
	private static StringUtil stringUtil=new StringUtil();
	
	public static StringUtil getInstance(){
		return stringUtil;
	}
	
	/**
	 * ͨ���ڵ�����ȡ�ڵ�ֵ
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
	 * ��װ����XML
	 * @param responseParamName �ӿڷ��ز����ڵ���
	 * @param streamNo ��ˮ��
	 * @param resultCode �����
	 * @param resultDesc �������
	 * @param moreResultXmls ����Ľ�����綩����ϵ��ѯ��ResultInfo�ȵ�
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
	 * ��װ����XML��Ϣ
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
	 * ��װ����XML
	 * @param streamNo ��ˮ��
	 * @param timeStamp  ��ǰϵͳʱ���
	 * @param requestBodytXmls ������Ϣ�壬����<�ӿ����������>....</�ӿ����������>
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
	 * ��ȡϵͳ��ǰʱ���
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
