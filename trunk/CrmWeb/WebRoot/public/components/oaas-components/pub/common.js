//�������λ��
var MONNEY_INTEGER_LENGTH = 11;
//����������λ��
var FLOAT_INTEGER_LENGTH = 7;



/**
 * ҳ��װ��
 */
function addloading() {
  var loadingDiv = document.createElement("<div id =\"loadingDiv\""
  +"style=\"position:absolute; width:100%; height:100%; z-index:0; left:0px; top:0px; background:FAFAFA;\" >");
  var swf =
  "<table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
   "<tr><td align=\"center\">"+
      "<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\""+
        "codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\""+
        "width=\"420\" height=\"260\">"+
        "<param name=\"movie\" value=\"/BSNWeb/public/images/loading.swf\">"+
        "<param name=\"quality\" value=\"high\">"+
        "<embed src=\"/BSNWeb/public/images/loading.swf\" quality=\"high\""+
          "pluginspage=\"http://www.macromedia.com/go/getflashplayer\""+
          "type=\"application/x-shockwave-flash\" width=\"420\" height=\"260\">"+
        "</embed></object></td></tr></table>";
  loadingDiv.innerHTML = swf;
  document.body.appendChild(loadingDiv);
}

/**
 * ת��ҳ����ʾ
 */
function loading() {
  loadingDiv.style.display = "";
}

/**
 * ���װ��
 */
function clearloading() {
  loadingDiv.style.display = "none";
}


/************************************************************
�������ƣ�switchPage()
�������ܣ��л�ҳ��
�����������
�����������
�� �� ֵ����
����˵����
************************************************************/

function SwitchPage(id){
  multiFramePortal.selectedIndex = 1;
  var divs = multiFramePortal.children;
  var count = divs.length;
  for (var i=0;i<count;i++){
    if (divs[i].id == id) {
	setTimeout('multiFramePortal.selectedIndex='+i,2000);
	//multiFramePortal.selectedIndex = i;
	}
    }
}

/*************************************************************
* ������ : isHaveRole
* �������� : �Ƿ���Ȩ��
* ������� :
* roleCodeList: Ȩ�ޱ����б�
* roleCode : ��Ҫ��֤��Ȩ�ޱ���
* �� �� ֵ : ��֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵�� :
*************************************************************/
function isHaveRole(roleCodeList, roleCode){
  return (roleCodeList.indexOf(roleCode)>-1)? true : false;
}

/*************************************************************
* ������ : getRoleCodeList
* �������� : ��ȡ�û�Ȩ���б�
* ������� :
* winObject : MainFrame.jsp�� ModelFrame(IFRAME) js����
* �� �� ֵ : �û�Ȩ���б�
* ����˵�� :
*************************************************************/
function getRoleCodeList(winObject){
		var list = "";
		for (var i=0;i<roleStateBsnStrucArr.length;i++)
		{
			list = list + roleStateBsnStrucArr[i].PrivilegeCode;
			if (i<roleStateBsnStrucArr.length -1)
				list = list + ",";
		}
 		return list;
}



/**
* ������ : validatePagePrivilege
* �������� : ��֤��ҳ��Ȩ��
* ������� : privilegeCode: Ȩ�ޱ����б��磺"QY1001,QY1002"
* �� �� ֵ : privilegeList:Ȩ���б�
* ����˵�� :
*/
function validatePagePrivilege(privilegeCode)
{
		var list = "";
		for (var i=0;i<roleStateBsnStrucArr.length;i++)
		{
			list = list + roleStateBsnStrucArr[i].PrivilegeCode;
			if (i<roleStateBsnStrucArr.length -1)
				list = list + ",";
		}

		if (!isHaveOneRole(list,privilegeCode))
		{
			ErrorHandle("", 2, 1, FAIL_VALID_PRIVILEGE,"");
			window.location.href = g_GlobalInfo.WebRoot + "/ModBoard.jsp";
			return null;
		}
		return list;
}
/*************************************************************
* ������ : isHaveOneRole
* �������� : �Ƿ���Ȩ��
* ������� :
* roleCodeList: Ȩ�ޱ����б�
* roleCode : ��Ҫ��֤��Ȩ�ޱ����б�
* �� �� ֵ : ��֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵�� :
*************************************************************/
function isHaveOneRole(roleCodeList, roleCode){
  		var arrRoleCode=roleCode.split(",");
  		for(var i=0;i<arrRoleCode.length;i++)
  		{
  			if(roleCodeList.indexOf(arrRoleCode[i])!=-1)
  			{
  				return true;
  			}
  		}
  		return false;
}

/*************************************************************************
����˵����ȡ��Ȩ����Ϣ�еĸ�����б�
����Ҫ��
���������roleItemName
        "PrivilegeCode" Ȩ��
        "PrivilegeID"   Ȩ��id
        "RegionID"      ��������
        "PartyRoleSchema"
        "ValidTime"
���������list
************************************************************************/
function getRoleList(roleItemName)
{
  var list = new Array();
  var listItemNameStr = "";
  for (var i=0;i<roleStateBsnStrucArr.length;i++)
  {
      var listItemName = eval("roleStateBsnStrucArr[i]." + roleItemName);
      if(listItemNameStr.indexOf(listItemName) == -1){
      	if(listItemNameStr == ""){
      		listItemNameStr = listItemName;
      	}
      	else{
      		listItemNameStr = listItemNameStr + "," + listItemName;
      	}
    	}
  }
  return listItemNameStr.split(",");
}




/*************************************************************************
����˵����1�����ݾ������Ȩ��ȡ��Ȩ����Ϣ�еĸ�����б�
					���練����Ȩ���� PM1014,��Ӧ�Ĺ�������ID��23��
					���ʵ�Ȩ���� PM1002��PM5000����Ӧ�Ĺ�������ID�� 55��
					�����Ҫȡ���ʲ�����Ӧ�Ĺ�������Ȩ�ޣ���ô�������ø÷�����
					getRoleListByPrivCode("RegionID","PM1002,PM5000")��
					2������ڶ�������Ȩ���ַ���û��д����Ϊ"",�򷵻ص�һ�����ַ���
����Ҫ��
���������1��roleItemName
        "PrivilegeCode" Ȩ��
        "PrivilegeID"   Ȩ��id
        "RegionID"      ��������
        "PartyRoleSchema"
        "ValidTime"        
        2��privCodeStr   ��Ҫ��Ȩ���б����Ȩ����","����
���������list ���������Id�ַ���
************************************************************************/
function getRoleListStrByPrivCode(roleItemName,privCodeStr)
{
	var list = new Array();
  var listItemNameStr = "";
  if(privCodeStr == null || privCodeStr == ""){
  	return list;
  }
  var privCodeArr = privCodeStr.split(",");
  for (var i=0;i<roleStateBsnStrucArr.length;i++)
  {
  	for(var j=0;j<privCodeArr.length;j++){
  		if(privCodeArr[j] == roleStateBsnStrucArr[i].PrivilegeCode){//�ж�������¼�Ƿ���ָ����Ȩ��
      	var listItemName = eval("roleStateBsnStrucArr[i]." + roleItemName);
	      if(listItemNameStr.indexOf(listItemName) == -1){
	      	if(listItemNameStr == ""){
	      		listItemNameStr = listItemName;
	      	}
	      	else{
	      		listItemNameStr = listItemNameStr + "," + listItemName;
	      	}
	    	}
	    }
    }
  }
  return listItemNameStr;

}

/*************************************************************************
����˵����1�����ݾ������Ȩ��ȡ��Ȩ����Ϣ�еĸ�����б�
					���練����Ȩ���� PM1014,��Ӧ�Ĺ�������ID��23��
					���ʵ�Ȩ���� PM1002��PM5000����Ӧ�Ĺ�������ID�� 55��
					�����Ҫȡ���ʲ�����Ӧ�Ĺ�������Ȩ�ޣ���ô�������ø÷�����
					getRoleListByPrivCode("RegionID","PM1002,PM5000")��
					2������ڶ�������Ȩ���ַ���û��д����Ϊ"",�򷵻ص�һ��null
����Ҫ��
���������1��roleItemName
        "PrivilegeCode" Ȩ��
        "PrivilegeID"   Ȩ��id
        "RegionID"      ��������
        "PartyRoleSchema"
        "ValidTime"        
        2��privCodeStr   ��Ҫ��Ȩ���б����Ȩ����","����
���������list ���������Id����
************************************************************************/
function getRoleListByPrivCode(roleItemName,privCodeStr)
{ 
	var listItemStr = "";
	
  listItemStr=getRoleListStrByPrivCode(roleItemName,privCodeStr);
  if(listItemStr != ""){
  	return listItemStr.split(",");
	}
	else{
		return null;
	}

}

function StrZeroAdd(s){ s ="0"+s; return s.substr(s.length-2);};Date.prototype.toString=function(){return this.getFullYear() +"-"+ StrZeroAdd(this.getMonth()+1) +"-"+ StrZeroAdd(this.getDate())+" "+StrZeroAdd(this.getHours())+":"+StrZeroAdd(this.getMinutes())+":"+StrZeroAdd(this.getSeconds());}
/*************************************************************************
����˵����ȡ��Ȩ����Ϣ�еĸ���Ķ��ŷָ����ִ�
����Ҫ��
���������roleItemName
        "PrivilegeCode" Ȩ��
        "PrivilegeID"   Ȩ��id
        "RegionID"      ��������
        "PartyRoleSchema"
        "ValidTime"
���������list string
************************************************************************/
function getRoleListStr(roleItemName)
{
  var list = new Array();
  var listItemNameStr = "";
  for (var i=0;i<roleStateBsnStrucArr.length;i++)
  {
      var listItemName = eval("roleStateBsnStrucArr[i]." + roleItemName);
      if(listItemNameStr.indexOf(listItemName) == -1){
      	if(listItemNameStr == ""){
      		listItemNameStr = listItemName;
      	}
      	else{
      		listItemNameStr = listItemNameStr + "," + listItemName;
      	}
    	}
  }
  return listItemNameStr;
}

//Ȩ������ ÿ��Ȩ�����鶼�и��û���� RoleStateBsnStruc����ÿ����������������
/** Ȩ�ޱ�ʶ String PrivilegeID */
/** Ȩ�ޱ��� String PrivilegeCode */
/** �����ʶ String RegionID */
/** �����־ String PermitionFlag */
/** �����˽�ɫȨ������" 98A" ���б������Ȩ��"98B" ���뱾����ͬ��������Ȩ�� String partyRoleSchema */
/** Ȩ����Чʱ�䣨��λΪ���ӣ����һ���� String ValidTime*/
var roleStateBsnStrucArr = new Array();

//�û����� ÿ���û����Զ�������������
/**�û����� String UserCode */
/**�ͻ���IP int UserId */
/**Ա������ String UserName */
var userInfoBean = new Object();

/*************************************************************
* ������ : getRoleStateArr
* �������� : �õ��û���Ȩ���������
* ������� :
* winObject : MainFrame.jsp�� ModelFrame(IFRAME) js����
* �� �� ֵ : roleStateBsnStrucArr
* ����˵�� :
*************************************************************/
function getRoleStateArr(winObject){
  roleStateBsnStrucArr = winObject.TopBar.document.getElementsByName("RoleState");
}

/** */
/*************************************************************
* ������ : getUserInfoBean
* �������� : �õ��û���Ϣ����
* ������� :
* winObject : MainFrame.jsp�� ModelFrame(IFRAME) js����
* �� �� ֵ : userInfoBean
* ����˵�� :
*************************************************************/
function getUserInfoBean(winObject){
  userInfoBean = winObject.TopBar.document.getElementById("UserInfo");
}
/*************************************************************
* ������ : getRegionIdList
* �������� : ��ȡ�û����������б�
* ������� :
* winObject : MainFrame.jsp�� ModelFrame(IFRAME) js����
* �� �� ֵ : �û����������б�
* ����˵�� :
*************************************************************/
function getRegionIdList(privilegeCode){
		var list = "";
		for (var i=0;i<roleStateBsnStrucArr.length;i++)
		{
		  if(roleStateBsnStrucArr[i].PrivilegeCode==privilegeCode){
			list = list + roleStateBsnStrucArr[i].RegionID+ ",";
		        if(list.length>=1){
		          list=list.substring(0,list.length-1);
		        }
		  }
		}
 		return list;
}



/************************************************************
 *�������ܣ�����̨��������long��ֵת��Ϊfloat��ֵ
 *���������iValue : long��ֵ
 *�� �� ֵ��oValue : ����ת�����
************************************************************/
function divLongToFloat(iValue)
{
	if (iValue == null || iValue+"" == "")
		return "";
	/*var iTempValue = parseInt(iValue/100);
	var oValue = iTempValue/100;*/

	var oValue = iValue /10000;
	return oValue;
}
Date.prototype.toString=function(){return this.getFullYear() +"-"+ StrZeroAdd(this.getMonth()+1) +"-"+ StrZeroAdd(this.getDate())+" "+StrZeroAdd(this.getHours())+":"+StrZeroAdd(this.getMinutes())+":"+StrZeroAdd(this.getSeconds());}

/************************************************************
 *�������ܣ���ǰ̨������̨�ĵ�float��ֵת��Ϊlong��ֵ
 *���������iValue : float��ֵ
 *�� �� ֵ��oValue : ����ת�����
************************************************************/
function mulFloatToLong(iValue)
{
//	var tempData = parseInt((iValue-0)*100);
//	return tempData * 100;
		var tempData = deleteRadixPoint(dealMoneyFormat(iValue));
		return eval(tempData)*100;
}
/************************************************************
 *�������ܣ��ı�����ֻ������������(0-9)
 *�����������
 *�� �� ֵ����
 *���������ı�����ʹ��onKeypress=""���ü��ɡ�
************************************************************/
function onKeypressForInteger()
{
	var eKeyCode = window.event.keyCode;
	if ((eKeyCode >= 48 && eKeyCode <= 57) || eKeyCode == 13)
	{
		window.event.keyCode = eKeyCode;
	}
	else
	{
		window.event.keyCode = 0 ;
	}
}

/************************************************************
 *�������ܣ�ʧȥ�����ʱ��У���ǲ�������
 *�����������
 *�� �� ֵ����
 *���������ı�����ʹ��onblur="checkInteger();"����,prompt="�ؼ�����" ���ɡ�
************************************************************/
function checkInteger()
{
		var val = window.event.srcElement.value;
	  if (val == "")
	  	return;
    if (regExpValidate(REGEXP_INTEGER, val) && val.length <= 9)
    {
    	return true;
    }
    else
    {
		ErrorHandle(null,2,1,INPUT_FORMAT+window.event.srcElement.prompt,null);
    		
    		var newVal = val.substring(0,9);
			var dotIndex = newVal.indexOf(".");
			if(dotIndex >= 0) 
		    {
				newVal = newVal.substring(0,dotIndex);
		    }
    		window.event.srcElement.value = newVal;
    		window.event.srcElement.focus();
    		window.event.keyCode = 0;
    		return false;
    }
}

/************************************************************
 *�������ܣ��ı�����ֻ����������(��������"0"-"9","-","."),����Ϊ��
 *���������isPositiveNumber ����
 *�� �� ֵ����
 *���������ı�����ʹ��onKeypress=""���ü��ɡ�
************************************************************/
function onKeypressForMoney(isPositiveNumber)
{
	var eKeyCode = window.event.keyCode;
	var val = window.event.srcElement.value;
	if ((eKeyCode >= 48 && eKeyCode <= 57) || eKeyCode == 13 )
	{
		window.event.keyCode = eKeyCode;
	}
	else if (eKeyCode == 45 && isPositiveNumber == true)//����"-"
 	{
  		window.event.keyCode = 0;
 	}
	else if (eKeyCode == 45 && isPositiveNumber == false)//����"-"
 	{
  		var val=window.event.srcElement.value;
  		if(val.trim()=="")
  		{
  			window.event.keyCode =eKeyCode;
  		}
  		else
  		{
  			window.event.keyCode = 0;
  		}
 	}

  else if (eKeyCode == 46)//����"."
  {
  	var val = window.event.srcElement.value;
  	if (val.indexOf(".") > -1)
  	{
  		window.event.keyCode = 0 ;
  	}
  	if (val == "" || val == "-")
  	{
  		window.event.srcElement.value = window.event.srcElement.value + "0";
  	}
  }
	else
	{
		window.event.keyCode = 0;
	}
}

/************************************************************
 *�������ܣ�ʧȥ�����ʱ��У���ǲ��ǽ����������
 *�����������
 *�� �� ֵ����
 *���������ı�����ʹ��onblur="checkMoney();"����,prompt="�ؼ�����" ���ɡ�
************************************************************/
function checkMoney()
{
	try
	{
		var val = window.event.srcElement.value;
		if (val == "")
		{
			return true;
		}
		if (val.substring(val.length-1) == '.')
		{
			window.event.srcElement.value = val;
		}

		val = window.event.srcElement.value;
		
		var dotIndex = val.indexOf(".");
		if (dotIndex == val.length -1)
		{
				val = val + "00";
		}
		var intLength = 0;//��������
    if (dotIndex == -1)
    {
				intLength = val.length;
  	}	
  	else
  	{
  			intLength = dotIndex;
  	}	
		//ֻ��С��λ�쳣
    if (!regExpValidate(REGEXP_DECIMAL1, val) && intLength <= MONNEY_INTEGER_LENGTH)
 		{   
    	ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    			+ window.event.srcElement.prompt + "'!"+MONEY_LASTBIT,null);
    	
    	window.event.srcElement.value = val.substring(0,dotIndex + 3);
    	window.event.srcElement.focus();
    	window.event.keyCode = 0;
    	return false;
    }
    //ֻ������λ�쳣
    if (regExpValidate(REGEXP_DECIMAL1, val) && intLength > MONNEY_INTEGER_LENGTH) 	
  	{
  		  ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    				+ window.event.srcElement.prompt + "'!"+MONEY_BEGOREBIT,null);
    		
    		var newVal = val.substring(0,11);
    		if (dotIndex != -1)
    		{
    				newVal = newVal + val.substring(dotIndex,dotIndex + 3);
    		} 
    		window.event.srcElement.value = newVal;
    		window.event.srcElement.focus();
    		window.event.keyCode = 0;
    		return false;
  	}
  	//С��λ������λ�����쳣
  	if (!regExpValidate(REGEXP_DECIMAL1, val) && intLength > MONNEY_INTEGER_LENGTH) 	
  	{
  		  ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    				+ window.event.srcElement.prompt + "'!"+MONEY_BEGOREBIT+","
    				+ MONEY_LASTBIT,null);
    		
    		window.event.srcElement.value = val.substring(0,dotIndex + 3);
    		val = window.event.srcElement.value;
    		window.event.srcElement.value = val.substring(0,11) 
    				+ val.substring(dotIndex,dotIndex + 3);		
    		window.event.srcElement.focus();
    		window.event.keyCode = 0;
    		return false;
  	}
  }catch(Exception){}
  	
  return true;
}

/************************************************************
 *�������ܣ�ʧȥ�����ʱ��У���ǲ��Ǹ�����������
 *�����������
 *�� �� ֵ����
 *���������ı�����ʹ��onblur="checkFloat();"����,prompt="�ؼ�����" ���ɡ�
************************************************************/
function checkFloat()
{
	try
	{
		var val = window.event.srcElement.value;
		if (val == "")
		{
			return true;
		}
		if (val.substring(val.length-1) == '.')
		{
			window.event.srcElement.value = val;
		}

		val = window.event.srcElement.value;
		
		var dotIndex = val.indexOf(".");
		if (dotIndex == val.length -1)
		{
				val = val + "00";
		}
		var intLength = 0;//��������
    if (dotIndex == -1)
    {
				intLength = val.length;
  	}	
  	else
  	{
  			intLength = dotIndex;
  	}	
		//ֻ��С��λ�쳣
    if (!regExpValidate(REGEXP_DECIMAL1, val) && intLength <= FLOAT_INTEGER_LENGTH)
 		{   
    	ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    			+ window.event.srcElement.prompt + "'!"+MONEY_LASTBIT,null);
    	
    	window.event.srcElement.value = val.substring(0,dotIndex + 3);
    	window.event.srcElement.focus();
    	window.event.keyCode = 0;
    	return false;
    }
    //ֻ������λ�쳣
    if (regExpValidate(REGEXP_DECIMAL1, val) && intLength > FLOAT_INTEGER_LENGTH) 	
  	{
  		  ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    				+ window.event.srcElement.prompt + "'!"+FLOAT_BEGOREBIT,null);
    		
    		var newVal = val.substring(0,7);
    		if (dotIndex != -1)
    		{
    				newVal = newVal + val.substring(dotIndex,dotIndex + 3);
    		} 
    		window.event.srcElement.value = newVal;
    		window.event.srcElement.focus();
    		window.event.keyCode = 0;
    		return false;
  	}
  	//С��λ������λ�����쳣
  	if (!regExpValidate(REGEXP_DECIMAL1, val) && intLength > FLOAT_INTEGER_LENGTH) 	
  	{
  		  ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    				+ window.event.srcElement.prompt + "'!"+FLOAT_BEGOREBIT+","
    				+ MONEY_LASTBIT,null);
    		
    		window.event.srcElement.value = val.substring(0,dotIndex + 3);
    		val = window.event.srcElement.value;
    		window.event.srcElement.value = val.substring(0,7) 
    				+ val.substring(dotIndex,dotIndex + 3);		
    		window.event.srcElement.focus();
    		window.event.keyCode = 0;
    		return false;
  	}
  }catch(Exception){}
  	
  return true;
}



/************************************************************
 *�������ܣ��Ƚ�������������ڴ�С
 *���������objBeginDate
 						objEndDate
 						flag (1 ���ڱȽ�,0 ����ʱ��Ƚ�)
 *�� �� ֵ��isValid (1 beginDate < endDate;
 										-1 beginDate > endDate;
 										0 beginDate = endDate)
 ************************************************************/
function compareTwoDate(objBeginDate,objEndDate,flag)
{
	var isValid = 0;
	var beginDateValue = objBeginDate.value;
	var endDateValue = objEndDate.value;
	if (flag == 1)
	{
		beginDateValue = parseDate(beginDateValue + " 00:00:00");
		endDateValue = parseDate(endDateValue + " 00:00:00");
	}
	else
	{
		beginDateValue = parseDate(beginDateValue );
		endDateValue = parseDate(endDateValue );
	}
	if (beginDateValue.getTime() > endDateValue.getTime())
		isValid = -1;
	else
	if (beginDateValue.getTime() == endDateValue.getTime())
		isValid = 0;
	else
	if (beginDateValue.getTime() < endDateValue.getTime())
		isValid = 1;

	return isValid;
}


/************************************************************
 *�������ܣ��Ƚ�������������ڴ�С
 *���������objBeginDate
 						objEndDate
 						flag (1 ���ڱȽ�,0 ����ʱ��Ƚ�)
 *�� �� ֵ��isValid (true ��Ч��
 										 false ��Ч��)
 ************************************************************/
function isValidTwoDate(objBeginDate,objEndDate,flag)
{
		var result = compareTwoDate(objBeginDate,objEndDate,flag);
		if (result == -1)
		{
			ErrorHandle(null,2,1,objEndDate.title + ALERT_BIGGEROREQURE + objBeginDate.title,null);
			return false;
		}
		return true;
}




/************************************************************
 *�������ܣ��ı�����ֻ����Ӣ����ĸ������(��������"0"-"9","a"-"z","A","Z")
 *�����������
 *�� �� ֵ����
 *���������ı�����ʹ��onKeypress=""���ü��ɡ�
************************************************************/
function onKeypressForLetterAndNumber()
{

	var eKeyCode = window.event.keyCode;
	var val = window.event.srcElement.value;
	if (   (eKeyCode >= 48 && eKeyCode <= 57)
			|| eKeyCode == 13
			|| (eKeyCode >= 65 && eKeyCode <= 90)
			|| (eKeyCode >= 97 && eKeyCode <= 122) )
	{
		window.event.keyCode = eKeyCode;
	}
	else
	{
		window.event.keyCode = 0;
	}
}

/************************************************************
 *�������ܣ�ʧȥ�����ʱ��У���ǲ���Ӣ����ĸ�����ֵ����
 *�����������
 *�� �� ֵ����
 *���������ı�����ʹ��onblur="checkLetterAndNumber();"����,prompt="�ؼ�����" ���ɡ�
************************************************************/
function checkLetterAndNumber()
{
		var val = window.event.srcElement.value;
	  if (val == "")
	  	return;
    if (regExpValidate(REGEXP_LETTERANDNUMBER, val))
    {
    	return true;
    }
    else
    {
    	ErrorHandle(null,2,1,INPUT_FORMAT
    				+ window.event.srcElement.prompt,null);
    	window.event.srcElement.focus();
    	window.event.keyCode = 0;
    	return false;
    }
}

/************************************************************
 *�������ܣ�ҳ��Loadװ�ز�
 *���������functionName �� ʵ����Ҫ���õķ�����
 *�� �� ֵ����
 *����������ҳ��Loadװ�ز�
************************************************************/
/*
function execWaitResult(functionName) {
	document.all.wait_message.style.visibility = "visible";
	try{
          eval(functionName);
	} catch(e) {
		document.all.wait_message.style.visibility = "hidden";
	}
	document.all.wait_message.style.visibility = "hidden";
}*/
var WaitIntervalID=-1;
function execWaitResult(func,vflag)
{
        document.all.wait_message.style.visibility="visible";
        WaitIntervalID = setInterval("setFunc("+func+","+vflag+")", 1000); 
        
}

function setFunc(func,vflag)
{ 
	clearInterval(WaitIntervalID);
	
	try
	{
		eval(func);	
		
	}
	catch(e)
	{       
		document.all.wait_message.style.visibility="hidden";
	}
	finally{
	 clearInterval(WaitIntervalID);
	}
	if(vflag==1){
	  document.all.wait_message.style.visibility="hidden";
	}
	
	
}