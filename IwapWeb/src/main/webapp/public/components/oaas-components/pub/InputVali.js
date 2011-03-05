/*************************************************************************
* Copyright by BSN Dept. of ZTEsoft
* File Name:InputVali.js
* Create Date:2005-01-22
* Author:ying.rui@zte.com.cn
* create Version:01.00.001
* create Version Date:2005-01-23
* modify Version:01.00.002
* modify Version Date:2005-02-25
* modify Version:01.00.006
* modify Version Date:2005-03-04
* Description:
* property Lists:
* Function Lists:
*************************************************************************/

/** IP地址验证正则表达式 */
var REGEXP_IP = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;

/** MAC地址验证正则表达式 */
var REGEXP_MAC = /^[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}$/;

/** email地址验证正则表达式 */
var REGEXP_EMAIL = /^[\w]+(\.[\w]+)*@[\w]+(\.[\w]+)+$/;

/** 小数验证 */
var REGEXP_DECIMAL1 = /^-?([\d]+(\.[\d]{1,2})?)$/;

/** 整数验证*/
var REGEXP_INTEGER = /^[\d]{1,12}$/;

/** 英文字母和数字验证*/
var REGEXP_LETTERANDNUMBER = /[a-z]|[A-Z]|[0-9]/;

/**权限编码验证*/
var REGEXP_PRIVILEGECODE = /[A-Z]{2}\d{4}/;
/*************************************************************
* 函数名：inputLengthVali
* 函数功能：表单控件输入字符输入长度验证
* 输入参数：
*  aInputIdList      : 表单控件ID数组
*  aInputNameList    : 表单控件别名数组
*  aInputLengthList  : 表单控件验证长度数组
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function inputLengthVali(aInputIdList,aInputNameList,aInputLengthList){
    var Bool = false;
    var x = aInputIdList.length;
    for(var y=0;y<x;y++){
	var oinput = document.getElementById(aInputIdList[y]);
	var strvalue = oinput.value;
	var il = strvalue.length;
	var iltrue = strvalue.length;
	for(var it=0;it<il;it++){
	    var chari = strvalue.charCodeAt(it);
	    if(chari>255){//AskII码大于255
		iltrue++;
	    }
	}
     	if (iltrue>aInputLengthList[y]){
          ErrorHandle(null,2,1,aInputNameList[y]+LENGTH_LIMIT+aInputLengthList[y],null);
	  Bool = false;
          oinput.focus();
          break;
     	}else{
          Bool = true;
        }
    }
    return Bool;
}

/*************************************************************
* 函数名：nullVali
* 函数功能：表单控件输入字符非空验证
* 输入参数：
*  aInputIdList      : 表单控件ID数组
*  aInputNameList    : 表单控件别名数组
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function nullVali(aInputIdList, aInputNameList){
    var Bool = false;
    var x = aInputIdList.length;
     for(var i=0; i<x; i++){
        var oinput = document.getElementById(aInputIdList[i]);
     	if (oinput.value==''){
          ErrorHandle(null,2,1,aInputNameList[i]+INPUT_NOUTNULL,null);
	  			Bool = false;
	  			//delete by zhangjing on 2005-03-21
	  			//oinput.focus();
	  			//add by zhangjing on 2005-03-21
	  			if (oinput.type != "hidden" && oinput.disabled != true && oinput.txtEnable != false)
	  			{
          	oinput.focus();
          }
          //add end
          break;
     	}else{
          Bool = true;
        }
     }
     return Bool;
}

/*************************************************************
* 函数名：typeNumberVali
* 函数功能：表单控件输入数字型证
* 输入参数：
*  aInputIdList      : 表单控件ID数组
*  aInputNameList    : 表单控件别名数组
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function typeNumberVali(aInputIdList, aInputNameList){
    var Bool = false;
    var x = aInputIdList.length;
    for(var i=0; i<x; i++){
      var oinput = document.getElementById(aInputIdList[i]);
      if (isNaN(oinput.value)){
        var in_name = aInputNameList[i];
        ErrorHandle(null,2,1,in_name+TYPE_NUMBERT,null)
	Bool = false;
        oinput.focus();
        break;
      }else{
          Bool = true;
      }
    }
     return Bool;
}


/*************************************************************
* 函数名：inputKeywordVali
* 函数功能：表单控件输入关键字验证
* 输入参数：
*  aInputIdList      : 表单控件ID数组
*  aInputNameList    : 表单控件别名数组
*  keyword           : 关键字符
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function inputKeywordVali(aInputIdList,aInputNameList,keyword){
    var bool = false;
    var x = aInputIdList.length;
    for (var i=0; i<x ;i++){
        var oinput = document.getElementById(aInputIdList[i]);
        if (!haveKeyword(oinput.value, keyword)){
          ErrorHandle(null,2,1,INPUT_FORMAT+aInputNameList[i],null);
          bool = false;
          oinput.focus();
          break;
        }else{
          bool = true;
        }
    }
    return bool;
}

/*************************************************************
* 函数名：haveKeyword
* 函数功能：输入字符是否有关键字
* 输入参数：
*  inputWord         : 输入字符
*  keyword           : 关键字符
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function haveKeyword(inputWord, keyword){
  if(inputWord.length > 0){
    var i = inputWord.indexOf(keyword);
    if(i==-1){
	return false;
    }else{
	return true;
    }
  }else{
    return true;
  }
}

/*************************************************************
* 函数名：regExpValidate
* 函数功能：使用正则表达式验证输入
* 输入参数：
*  regExp       : 标准的正则表达式
*  input        : 要被验证的字符串
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function regExpValidate(regExp, input){
    var Bool = false;
    var arr = input.match(regExp);
    if(arr != null){
        Bool = true;
    }
    return Bool;
}


/*************************************************************
* 函数名：formatRegExpValidate
* 函数功能：表单控件输入字符格式正则表达式验证
* 输入参数：
*  aInputIdList      : 表单控件ID数组
*  aInputNameList    : 表单控件别名数组
*  aRegExp           : 标准的正则表达式数组
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function formatRegExpValidate(aInputIdList,aInputNameList,aRegExp){
    var Bool = false;
    var x = aInputIdList.length;
    for(var i=0; i<x ;i++){
	var oinput = document.getElementById(aInputIdList[i]);
	var strvalue = oinput.value;
     	if (regExpValidate(aRegExp[i], strvalue)){
          Bool = true;
     	}else{
          ErrorHandle(null,2,1,INPUT_FORMAT+aInputNameList[i],null);
	  Bool = false;
          oinput.focus();
          break;
        }
    }
    return Bool;
}



/*************************************************************
* 函数名：betweenDateVali
* 函数功能：对输入的起始时间和结束时间进行比较判断
* 输入参数：
*  inputStartDate      : 起始时间
*  inputEndDate        : 结束时间
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function betweenDateVali(inputStartDate,inputEndDate){
	var tempDateBegin = this.formatDateStr(inputStartDate);
	var tempDateEnd = this.formatDateStr(inputEndDate);
	tempDateBegin = tempDateBegin.replace(/[-: ]/g,"");
	tempDateEnd = tempDateEnd.replace(/[-: ]/g,"");
	if(tempDateBegin > tempDateEnd){		
		ErrorHandle(null,2,1,ERROR_ORDER_DATE,"");
		return false;
	}
	return true;
}

/*************************************************************
* 函数名：betweenDateLimit
* 函数功能：对输入的起始时间和结束时间之间的间隔进行比较
* 输入参数：
*  inputStartDate      : 起始时间
*  inputEndDate        : 结束时间
*  days                ：允许最大间隔时间
* 返 回 值：验证是否通过，通过true,不通过false
* 函数说明:
*************************************************************/
function betweenDateLimit(inputStartDate,inputEndDate,days){
	var inputStartDate_day = inputStartDate.split(" ")[0];
	var inputStartDate_time = inputStartDate.split(" ")[1]; 
	var inputStartDate_day_year = inputStartDate_day.split("-")[0];
	var inputStartDate_day_mon = inputStartDate_day.split("-")[1];
	var inputStartDate_day_day = inputStartDate_day.split("-")[2];
	var inputStartDate_time_hour = inputStartDate_time.split(":")[0];
	var inputStartDate_time_min = inputStartDate_time.split(":")[1];
	var inputStartDate_time_sec = inputStartDate_time.split(":")[2];
	
	var inputEndDate_day = inputEndDate.split(" ")[0];
	var inputEndDate_time = inputEndDate.split(" ")[1]; 
	var inputEndDate_day_year = inputEndDate_day.split("-")[0];
	var inputEndDate_day_mon = inputEndDate_day.split("-")[1];
	var inputEndDate_day_day = inputEndDate_day.split("-")[2];
	var inputEndDate_time_hour = inputEndDate_time.split(":")[0];
	var inputEndDate_time_min = inputEndDate_time.split(":")[1];
	var inputEndDate_time_sec = inputEndDate_time.split(":")[2];
	
	var inputStartDate_date = new Date(inputStartDate_day_year,inputStartDate_day_mon,inputStartDate_day_day,inputStartDate_time_hour,inputStartDate_time_min,inputStartDate_time_sec);
	var inputEndDate_date = new Date(inputEndDate_day_year,inputEndDate_day_mon,inputEndDate_day_day,inputEndDate_time_hour,inputEndDate_time_min,inputEndDate_time_sec);
	if(inputEndDate_date.getTime()-inputStartDate_date.getTime() > days*86400000){		
		ErrorHandle(null,2,1,ERROR_LIMIT_DAYS+days+TEXT_DAYS,"");
		return false;
	}
	return true;
}



/*************************************************************************
函数名称：formatDateStr
函数说明：将传入的时间类型变量转换成指定的字符串形式,返回的格式为：YYYY-MM-DD HH:MI:SS

输入参数：
	date：传入的时间类型变量
	
	
输出参数：
	str：传出的字符串类型变量 

************************************************************************/
function formatDateStr(inputDate)
{
	if(inputDate.length <= 10){
		inputDate = inputDate + " 00:00:00";
	}
	var tempDate = parseDate(inputDate);
	try
	{
	var str="";
	str=tempDate.getFullYear()+"-";
	str+=(((tempDate.getMonth()+1)>=10)? (tempDate.getMonth()+1):"0"+(tempDate.getMonth()+1))+"-";
	str+=(tempDate.getDate()>=10)? tempDate.getDate():"0"+tempDate.getDate();
	str+=" ";
	str+=((tempDate.getHours()>=10)? tempDate.getHours():"0"+tempDate.getHours())+":";
	str+=((tempDate.getMinutes()>=10)? tempDate.getMinutes():"0"+tempDate.getMinutes())+":";
	str+=(tempDate.getSeconds()>=10)? tempDate.getSeconds():"0"+tempDate.getSeconds();
	return str;
	}
	catch(e)
	{
		throw e;
	}
}