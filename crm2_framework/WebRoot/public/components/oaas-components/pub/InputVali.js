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

/** IP��ַ��֤������ʽ */
var REGEXP_IP = /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;

/** MAC��ַ��֤������ʽ */
var REGEXP_MAC = /^[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}\-[0-9A-F]{2}$/;

/** email��ַ��֤������ʽ */
var REGEXP_EMAIL = /^[\w]+(\.[\w]+)*@[\w]+(\.[\w]+)+$/;

/** С����֤ */
var REGEXP_DECIMAL1 = /^-?([\d]+(\.[\d]{1,2})?)$/;

/** ������֤*/
var REGEXP_INTEGER = /^[\d]{1,12}$/;

/** Ӣ����ĸ��������֤*/
var REGEXP_LETTERANDNUMBER = /[a-z]|[A-Z]|[0-9]/;

/**Ȩ�ޱ�����֤*/
var REGEXP_PRIVILEGECODE = /[A-Z]{2}\d{4}/;
/*************************************************************
* ��������inputLengthVali
* �������ܣ����ؼ������ַ����볤����֤
* ���������
*  aInputIdList      : ���ؼ�ID����
*  aInputNameList    : ���ؼ���������
*  aInputLengthList  : ���ؼ���֤��������
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
	    if(chari>255){//AskII�����255
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
* ��������nullVali
* �������ܣ����ؼ������ַ��ǿ���֤
* ���������
*  aInputIdList      : ���ؼ�ID����
*  aInputNameList    : ���ؼ���������
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
* ��������typeNumberVali
* �������ܣ����ؼ�����������֤
* ���������
*  aInputIdList      : ���ؼ�ID����
*  aInputNameList    : ���ؼ���������
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
* ��������inputKeywordVali
* �������ܣ����ؼ�����ؼ�����֤
* ���������
*  aInputIdList      : ���ؼ�ID����
*  aInputNameList    : ���ؼ���������
*  keyword           : �ؼ��ַ�
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
* ��������haveKeyword
* �������ܣ������ַ��Ƿ��йؼ���
* ���������
*  inputWord         : �����ַ�
*  keyword           : �ؼ��ַ�
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
* ��������regExpValidate
* �������ܣ�ʹ��������ʽ��֤����
* ���������
*  regExp       : ��׼��������ʽ
*  input        : Ҫ����֤���ַ���
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
* ��������formatRegExpValidate
* �������ܣ����ؼ������ַ���ʽ������ʽ��֤
* ���������
*  aInputIdList      : ���ؼ�ID����
*  aInputNameList    : ���ؼ���������
*  aRegExp           : ��׼��������ʽ����
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
* ��������betweenDateVali
* �������ܣ����������ʼʱ��ͽ���ʱ����бȽ��ж�
* ���������
*  inputStartDate      : ��ʼʱ��
*  inputEndDate        : ����ʱ��
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
* ��������betweenDateLimit
* �������ܣ����������ʼʱ��ͽ���ʱ��֮��ļ�����бȽ�
* ���������
*  inputStartDate      : ��ʼʱ��
*  inputEndDate        : ����ʱ��
*  days                �����������ʱ��
* �� �� ֵ����֤�Ƿ�ͨ����ͨ��true,��ͨ��false
* ����˵��:
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
�������ƣ�formatDateStr
����˵�����������ʱ�����ͱ���ת����ָ�����ַ�����ʽ,���صĸ�ʽΪ��YYYY-MM-DD HH:MI:SS

���������
	date�������ʱ�����ͱ���
	
	
���������
	str���������ַ������ͱ��� 

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