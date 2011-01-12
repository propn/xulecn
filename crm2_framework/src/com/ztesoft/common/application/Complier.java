package com.ztesoft.common.application;

import java.util.HashMap;

public class Complier {
	
public static void main(String[]args){
	Complier bo=new Complier();
//	System.out.println(bo.trimSpace("   public    int a   =  12345   "));
	try {
		bo.getFieldInfo("   public    int a   =  12345   ");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
private String strTwoSpace="  ";
private String strOneSpace=" ";
private String strSpaceEquals=" = ";
private String strEquals="=";
private String strSpaceLK=" (";
private String strSpaceRK="( ";
private String strK="(";
public String trimSpace(String inputVal){
	String strNewVal=inputVal;
	//ȥ��2���ո�
	while(strNewVal.indexOf(strTwoSpace)>=0){
		strNewVal=strNewVal.replaceAll(strTwoSpace, strOneSpace);
	}
	//ȥ���ո�=�ո� 
	while(strNewVal.indexOf(strSpaceEquals)>=0){
		strNewVal=strNewVal.replaceAll(strSpaceEquals, strEquals);
	}
	/*//ȥ��������Ŀո�
	while(strNewVal.indexOf(strSpaceLK)>=0){
		strNewVal=strNewVal.replaceAll(strSpaceLK, strK);
	}
	//ȥ�������ҵĿո�
	while(strNewVal.indexOf(strSpaceRK)>=0){
		strNewVal=strNewVal.replaceAll(strSpaceLK, strK);
	}*/
	//ȥ����β�ո�
	return strNewVal.trim();
}

public HashMap getFieldInfo(String inFieldInfo) throws Exception{
	HashMap hsFieldsInfo=new HashMap();
	String fieldInfo=this.trimSpace(inFieldInfo);
	if(fieldInfo!=null && !fieldInfo.equals("")){
		String[]_strFieldInfo=fieldInfo.split(" ");
		//�ж���Ч��
		if(_strFieldInfo==null || _strFieldInfo.length==0)throw new Exception("fieldDefinedError:non");
		//�������private,public��ͷ��
		String strPrivilege=_strFieldInfo[0].trim();
		String[]_strSubFieldInfo=null;
		if(strPrivilege.equals("private") || strPrivilege.equals("public")){
			//set field_privilege,field_type
			hsFieldsInfo.put("fieldPrivilege", strPrivilege);
			String strFieldType=_strFieldInfo[1].trim();
			hsFieldsInfo.put("fieldType", strFieldType);
			//���ú���
			_strSubFieldInfo=_strFieldInfo[2].trim().split("=");
		}else{
			//set field_privilege,field_type
			hsFieldsInfo.put("fieldPrivilege", "private");
			String strFieldType=_strFieldInfo[0].trim();
			hsFieldsInfo.put("fieldType", strFieldType);
			//���ú���
			_strSubFieldInfo=_strFieldInfo[1].trim().split("=");
		}

		//��ʼ�����ֶ�����Ĭ��ֵ
		if(_strSubFieldInfo==null || _strSubFieldInfo.length==0)throw new Exception("subfieldDefinedError:non");
		if(_strSubFieldInfo.length==2){
			hsFieldsInfo.put("fieldName", _strSubFieldInfo[0]);
			String strDefaultVal=_strSubFieldInfo[1];
			hsFieldsInfo.put("defaultValue", strDefaultVal);
		}else{
			if(_strSubFieldInfo.length==1){
				hsFieldsInfo.put("fieldName", _strSubFieldInfo[0].trim());
			}
		}
	}
	return hsFieldsInfo;
}


}
