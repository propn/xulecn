//金额整数位数
var MONNEY_INTEGER_LENGTH = 11;
//浮点数整数位数
var FLOAT_INTEGER_LENGTH = 7;



/**
 * 页面装载
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
 * 转载页面显示
 */
function loading() {
  loadingDiv.style.display = "";
}

/**
 * 清除装载
 */
function clearloading() {
  loadingDiv.style.display = "none";
}


/************************************************************
函数名称：switchPage()
函数功能：切换页面
输入参数：无
输出参数：无
返 回 值：无
函数说明：
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
* 函数名 : isHaveRole
* 函数功能 : 是否有权限
* 输入参数 :
* roleCodeList: 权限编码列表
* roleCode : 需要验证的权限编码
* 返 回 值 : 验证是否通过，通过true,不通过false
* 函数说明 :
*************************************************************/
function isHaveRole(roleCodeList, roleCode){
  return (roleCodeList.indexOf(roleCode)>-1)? true : false;
}

/*************************************************************
* 函数名 : getRoleCodeList
* 函数功能 : 获取用户权限列表
* 输入参数 :
* winObject : MainFrame.jsp中 ModelFrame(IFRAME) js对象
* 返 回 值 : 用户权限列表
* 函数说明 :
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
* 函数名 : validatePagePrivilege
* 函数功能 : 验证本页的权限
* 输入参数 : privilegeCode: 权限编码列表，如："QY1001,QY1002"
* 返 回 值 : privilegeList:权限列表
* 函数说明 :
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
* 函数名 : isHaveOneRole
* 函数功能 : 是否有权限
* 输入参数 :
* roleCodeList: 权限编码列表
* roleCode : 需要验证的权限编码列表
* 返 回 值 : 验证是否通过，通过true,不通过false
* 函数说明 :
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
函数说明：取得权限信息中的各项的列表
函数要求：
输入参数：roleItemName
        "PrivilegeCode" 权限
        "PrivilegeID"   权限id
        "RegionID"      管理区域
        "PartyRoleSchema"
        "ValidTime"
输出参数：list
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
函数说明：1、根据具体操作权限取得权限信息中的各项的列表
					比如反销的权限是 PM1014,对应的管理区域ID是23；
					销帐的权限是 PM1002和PM5000，对应的管理区域ID是 55；
					如果你要取销帐操作对应的管理区域权限，那么这样调用该方法：
					getRoleListByPrivCode("RegionID","PM1002,PM5000")。
					2、如果第二个参数权限字符串没有写或者为"",则返回的一个空字符串
函数要求：
输入参数：1、roleItemName
        "PrivilegeCode" 权限
        "PrivilegeID"   权限id
        "RegionID"      管理区域
        "PartyRoleSchema"
        "ValidTime"        
        2、privCodeStr   需要的权限列表，多个权限用","隔开
输出参数：list 管理区域的Id字符串
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
  		if(privCodeArr[j] == roleStateBsnStrucArr[i].PrivilegeCode){//判断这条记录是否是指定的权限
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
函数说明：1、根据具体操作权限取得权限信息中的各项的列表
					比如反销的权限是 PM1014,对应的管理区域ID是23；
					销帐的权限是 PM1002和PM5000，对应的管理区域ID是 55；
					如果你要取销帐操作对应的管理区域权限，那么这样调用该方法：
					getRoleListByPrivCode("RegionID","PM1002,PM5000")。
					2、如果第二个参数权限字符串没有写或者为"",则返回的一个null
函数要求：
输入参数：1、roleItemName
        "PrivilegeCode" 权限
        "PrivilegeID"   权限id
        "RegionID"      管理区域
        "PartyRoleSchema"
        "ValidTime"        
        2、privCodeStr   需要的权限列表，多个权限用","隔开
输出参数：list 管理区域的Id数组
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
函数说明：取得权限信息中的各项的逗号分隔的字串
函数要求：
输入参数：roleItemName
        "PrivilegeCode" 权限
        "PrivilegeID"   权限id
        "RegionID"      管理区域
        "PartyRoleSchema"
        "ValidTime"
输出参数：list string
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

//权限数组 每个权限数组都有改用户多个 RoleStateBsnStruc对象，每个对象有以下属性
/** 权限标识 String PrivilegeID */
/** 权限编码 String PrivilegeCode */
/** 区域标识 String RegionID */
/** 允许标志 String PermitionFlag */
/** 参与人角色权限类型" 98A" 仅有本区域的权限"98B" 有与本区域同级的所有权限 String partyRoleSchema */
/** 权限有效时间（单位为分钟），最长一星期 String ValidTime*/
var roleStateBsnStrucArr = new Array();

//用户属性 每个用户属性对象有以下属性
/**用户编码 String UserCode */
/**客户端IP int UserId */
/**员工姓名 String UserName */
var userInfoBean = new Object();

/*************************************************************
* 函数名 : getRoleStateArr
* 函数功能 : 得到用户的权限数组对象
* 输入参数 :
* winObject : MainFrame.jsp中 ModelFrame(IFRAME) js对象
* 返 回 值 : roleStateBsnStrucArr
* 函数说明 :
*************************************************************/
function getRoleStateArr(winObject){
  roleStateBsnStrucArr = winObject.TopBar.document.getElementsByName("RoleState");
}

/** */
/*************************************************************
* 函数名 : getUserInfoBean
* 函数功能 : 得到用户信息对象
* 输入参数 :
* winObject : MainFrame.jsp中 ModelFrame(IFRAME) js对象
* 返 回 值 : userInfoBean
* 函数说明 :
*************************************************************/
function getUserInfoBean(winObject){
  userInfoBean = winObject.TopBar.document.getElementById("UserInfo");
}
/*************************************************************
* 函数名 : getRegionIdList
* 函数功能 : 获取用户管理区域列表
* 输入参数 :
* winObject : MainFrame.jsp中 ModelFrame(IFRAME) js对象
* 返 回 值 : 用户管理区域列表
* 函数说明 :
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
 *函数功能：将后台传过来的long型值转换为float型值
 *输入参数：iValue : long型值
 *返 回 值：oValue : 返回转换结果
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
 *函数功能：将前台传给后台的的float型值转换为long型值
 *输入参数：iValue : float型值
 *返 回 值：oValue : 返回转换结果
************************************************************/
function mulFloatToLong(iValue)
{
//	var tempData = parseInt((iValue-0)*100);
//	return tempData * 100;
		var tempData = deleteRadixPoint(dealMoneyFormat(iValue));
		return eval(tempData)*100;
}
/************************************************************
 *函数功能：文本框中只允许输入整数(0-9)
 *输入参数：无
 *返 回 值：无
 *描述：在文本框中使用onKeypress=""调用即可。
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
 *函数功能：失去焦点的时候校验是不是整数
 *输入参数：无
 *返 回 值：无
 *描述：在文本框中使用onblur="checkInteger();"调用,prompt="控件名称" 即可。
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
 *函数功能：文本框中只允许输入金额(允许输入"0"-"9","-","."),可以为负
 *输入参数：isPositiveNumber 正数
 *返 回 值：无
 *描述：在文本框中使用onKeypress=""调用即可。
************************************************************/
function onKeypressForMoney(isPositiveNumber)
{
	var eKeyCode = window.event.keyCode;
	var val = window.event.srcElement.value;
	if ((eKeyCode >= 48 && eKeyCode <= 57) || eKeyCode == 13 )
	{
		window.event.keyCode = eKeyCode;
	}
	else if (eKeyCode == 45 && isPositiveNumber == true)//键入"-"
 	{
  		window.event.keyCode = 0;
 	}
	else if (eKeyCode == 45 && isPositiveNumber == false)//键入"-"
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

  else if (eKeyCode == 46)//键入"."
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
 *函数功能：失去焦点的时候校验是不是金额类型数据
 *输入参数：无
 *返 回 值：无
 *描述：在文本框中使用onblur="checkMoney();"调用,prompt="控件名称" 即可。
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
		var intLength = 0;//整数长度
    if (dotIndex == -1)
    {
				intLength = val.length;
  	}	
  	else
  	{
  			intLength = dotIndex;
  	}	
		//只有小数位异常
    if (!regExpValidate(REGEXP_DECIMAL1, val) && intLength <= MONNEY_INTEGER_LENGTH)
 		{   
    	ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    			+ window.event.srcElement.prompt + "'!"+MONEY_LASTBIT,null);
    	
    	window.event.srcElement.value = val.substring(0,dotIndex + 3);
    	window.event.srcElement.focus();
    	window.event.keyCode = 0;
    	return false;
    }
    //只有整数位异常
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
  	//小数位和整数位都有异常
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
 *函数功能：失去焦点的时候校验是不是浮点类型数据
 *输入参数：无
 *返 回 值：无
 *描述：在文本框中使用onblur="checkFloat();"调用,prompt="控件名称" 即可。
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
		var intLength = 0;//整数长度
    if (dotIndex == -1)
    {
				intLength = val.length;
  	}	
  	else
  	{
  			intLength = dotIndex;
  	}	
		//只有小数位异常
    if (!regExpValidate(REGEXP_DECIMAL1, val) && intLength <= FLOAT_INTEGER_LENGTH)
 		{   
    	ErrorHandle(null,2,1,INPUT_FORMAT + "'"
    			+ window.event.srcElement.prompt + "'!"+MONEY_LASTBIT,null);
    	
    	window.event.srcElement.value = val.substring(0,dotIndex + 3);
    	window.event.srcElement.focus();
    	window.event.keyCode = 0;
    	return false;
    }
    //只有整数位异常
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
  	//小数位和整数位都有异常
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
 *函数功能：比较两个对象的日期大小
 *输入参数：objBeginDate
 						objEndDate
 						flag (1 日期比较,0 日期时间比较)
 *返 回 值：isValid (1 beginDate < endDate;
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
 *函数功能：比较两个对象的日期大小
 *输入参数：objBeginDate
 						objEndDate
 						flag (1 日期比较,0 日期时间比较)
 *返 回 值：isValid (true 有效的
 										 false 无效的)
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
 *函数功能：文本框中只允许英文字母和数字(允许输入"0"-"9","a"-"z","A","Z")
 *输入参数：无
 *返 回 值：无
 *描述：在文本框中使用onKeypress=""调用即可。
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
 *函数功能：失去焦点的时候校验是不是英文字母和数字的组合
 *输入参数：无
 *返 回 值：无
 *描述：在文本框中使用onblur="checkLetterAndNumber();"调用,prompt="控件名称" 即可。
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
 *函数功能：页面Load装载层
 *输入参数：functionName ： 实际需要调用的方法名
 *返 回 值：无
 *描述：调用页面Load装载层
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