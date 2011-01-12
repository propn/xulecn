/*************************************************************************
		
		trim				:	将传入的字符串的前后空格去掉
		sizeOfString		:	返回字符串的字节数
		checkTextIsEmpty	:	判断输入对象是否为空
		
		checkStrNull        :	判断输入对象是否为空,提示信息并聚焦
	    checkInt			:	校验数字
	    checkIsFloat		:	验证输入的值是否为浮点数
	    checkFloat          :	校验输入值，为浮点数
	    checkNumberAndChar	:	校验只允许输入字母和数字
	    checkRegExp			:	对值进行正则表达式校验
	    
	    addEndZero          :	增加小数点[保持小数点后,有几位小数)]
        formatFloat			:	对输入的参数进行格式化（包含两位小数点的浮点数）          
        deleteRadixPoint	:	去掉小数点 
        shrRadixPoint		:	左移小数点 
        
        nextCursor          :	界面控件输入回车后，能自动聚焦到下一个对象控件
		focusTextEnd        :	光标,停在[文本编辑框/文本输入框]文字的最后,方便用户输入
		selectText			:	将[文本编辑框/文本输入框]文字都选中,方便用户输入
				
		formatDateStr				:	将传入的时间类型变量转换成指定的字符串形式,返回的格式为：YYYY-MM-DD HH:MI:SS		 
		getDateStrFromDateTimeStr	:	从后台返回的标准时间格式(YYYY-MM-DD HH:MI:SS)中，取日期YYYY-MM-DD 		
	    
	    Select_Init			:	根据传入的数组对象生成下拉筐
	    Select_Init_No_Id	:	根据传入的数组对象生成下拉筐(数据后没有"[id]")	   	   
	    addOptionToSelect	:	根据传入的参数构造select的选项，并添加到下拉列表中
	    
	    copyObject				:	将传入的参数进行拷贝       
        removeNodeSelfAndChild	:	删除树的节点，包括子节点      
        
        ClickCheckBoxForTextBoxClick	:	通用checkBox约束testBox点击事件处理函数
        ClickCheckBoxForSelectClick		:	通用checkBox约束select点击事件处理函数
        ClickCheckBoxForTimePickerClick	:	通用checkBox约束time picker点击事件处理函数
        ClickCheckBoxForPopEditClick	:	通用checkBox约束pop edit点击事件处理函数 
        
        refreshIndepProperList	:	根据传入的xml值对产品属性动态控件进行刷新   
        
        gotoQuerySubmit		:	界面控件输入回车后，自动执行"Query"处理
        CreateTable			:	根据传入的对象数组参数生成DataList信息，若信息条数为一，则直接退出界面
        
        selDropDownList		:	根据传入的参数值，比较下拉框每一格option和参数值，确定下拉框被选中的项
        CertType_Init		:	根据传入的数组对象生成下拉筐
        AdjustCertnbr		:	根据传入的身份证号码验证号码的有效性
        getMonthDay			:	根据传入的年、月得到这个月最大的日期 
        isCharsInBag		:	检查数据，是否全部为 规定的格式数据
        show_props			:	显示一个对象的所有的字段以及字段值
        moveitemsbetween	:	从原列表选择列表选择选项到目标列表中，并且可以把目标列表选项取消返回原列表         
        
        SubtractLargeNumber :   计算超过16位的号码相减
  
*************************************************************************/
/*************************************************************************
1）	Copyright by OSS R&D Dept. of ZTEsoft,2004-04-27
	File Name:helper.js
	Create Date:2004-04-27
	Author:Jin.XiangGuo
	create Version:0.0.0.1
	Version:0.0.0.1
	Create Version Date:2004-04-27
	Description:提供给前台（javasctipt/htc）一些公用函数，方便大家共享资源
	Function Lists:	（在添加了函数之后在 Function Lists中也要相应的添加）
		Tree_Init			:	根据传入的对象数组生成树
		GetTreeNodeById		:	根据传入的Id提取Node
		TreeRemove			:	根据传入的TreeName删除下面的节点
		CertType_Init		:	根据传入的数组对象生成下拉筐
		selDropDownList		:	根据传入的参数值，比较下拉框每一格option和参数值，确定下拉框被选中的项
		addOptionToSelect	:	根据传入的参数构造select的选项，并添加到下拉列表中
		moveitemsbetween	:	从原列表选择列表选择选项到目标列表中，并且可以把目标列表选项取消返回原列表
		formatDateStr		:	将传入的时间类型变量转换成指定的字符串形式,返回的格式为：YYYY-MM-DD HH:MI:SS
		trim				:	将传入的字符串的前后空格去掉
		Tree_Init:树没有初始化
		TreeRemoved:在提取子接点的时候处理有点问题			

2）
	Modified By Name Of Programmer:    su.weiqing
	Modified Date:    2005-01-28
	Current Version:    0.0.0.5	
	
			checkStrNull: 判断输入对象是否为空,提示信息并聚焦
			nextCursor:   界面控件输入回车后，能自动聚焦到下一个对象控件
		        
			//控件控制通过方法
			ClickCheckBoxForTextBoxClick         :   通用check box约束text
			ClickCheckBoxForSelectClick			 :   通用check box约束select
			ClickCheckBoxForTimePickerClick      :   通用checkBox约束time picker
			ClickCheckBoxForPopEditClick         :   通用checkBox约束pop edit
			formatFloat                          :   对输入的参数进行格式化,输出包含两位小数点的浮点数
			checkIsFloat                         :   校验输入值是否为浮点数(正则表达式判断)	
3）
	Modified By Name Of Programmer:    yu.qianli
	Modified Date:    2005-04-19
	Current Version:    0.0.0.5	
	
			SubtractLargeNumber :   计算超过16位的号码相减
*************************************************************************/


//定义的全局变量
var g_nMin = Number.MIN_VALUE;
var g_nMax = Number.MAX_VALUE;
var INT_NUM="0123456789";
var FLOAT_NUM="0123456789.";

/*************************************************************************
函数说明：根据传入的数组对象生成下拉筐
函数要求：要求各个节点的ID都具有唯一性

输入参数：
	drpId	：下拉筐名称
	arrObj	：传入的下拉筐对象数组
	
输出参数：
		无；
************************************************************************/
function Select_Init(drpId,arrObj,colarr)
{
	if (drpId.tagName!="SELECT")
		return false;
	drpId.length=0;
	for (i = 0; i < arrObj.length; i++) 
	{
		var option = document.createElement("OPTION");
		option._text = eval("arrObj[i]."+colarr[0]);
		option.text = eval("arrObj[i]."+colarr[0])+"    ["+eval("arrObj[i]."+colarr[1])+"]";
		option.value = eval("arrObj[i]."+colarr[1]);
		if ( (option.value == "")||(option.value == null) )
		{			
			option.text	= " ";
		}
		drpId.add(option);
	}
}

/*************************************************************************
函数说明：根据传入的数组对象生成下拉筐

函数要求：要求各个节点的ID都具有唯一性

输入参数：
	drpId	：下拉筐名称
	arrObj	：传入的下拉筐对象数组
	
输出参数：
		无；
************************************************************************/
function Select_Init_No_Id(drpId,arrObj,colarr)
{
	if (drpId.tagName!="SELECT")
		return false;
	drpId.length=0;
	for (i = 0; i < arrObj.length; i++) 
	{
		var option = document.createElement("OPTION");
		option._text = eval("arrObj[i]."+colarr[0]);
		option.text = eval("arrObj[i]."+colarr[0]);
		option.value = eval("arrObj[i]."+colarr[1]);
		if ( (option.value == "")||(option.value == null) )
		{
			option.text	= " ";
		}
		drpId.add(option);
	}
}

/*************************************************************************
函数说明：去掉字符串前后的空格

函数要求：

输入参数：
	
	
输出参数：
		无；
************************************************************************/
/*
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
*/
/*************************************************************************
函数说明：根据传入的参数值，比较下拉框每一格option和参数值，确定下拉框被选中的项

输入参数：
	下拉框的id
	传入参数值
	
	
输出参数：


************************************************************************/
function selDropDownList(sellist,selvalue){
		var k;
		for( k=0;k<sellist.options.length;k++){
			if(sellist.options[k].value==selvalue){
				sellist.options[k].selected = true;
				break;
				
			}
		}
		if(k==sellist.options.length)
			sellist.options[0].selected=true;
	}
	
/*************************************************************************
函数说明：根据传入的参数构造select的选项，并添加到下拉列表中

输入参数：
	option的value
	option的text
	
	
输出参数：


************************************************************************/

function addOptionToSelect(oSelect,optionValue,optionText){
	var oOption = document.createElement("OPTION");
	oOption.text=optionText;
	oOption.value=optionValue;
	oSelect.add(oOption);

}

/*************************************************************************
函数说明：从原列表选择列表选择选项到目标列表中，并且可以把目标列表选项取消返回原列表

输入参数：

	sourceobj：原列表
	destinationobj：目标列表
	
	
输出参数：


************************************************************************/

function moveitemsbetween(sourceobj,destinationobj)
{
	var counter
	for (counter=0;counter<sourceobj.length;counter++)
	{
		if (sourceobj.options[counter].selected)
		{
			destinationobj.options[destinationobj.length]= new Option(sourceobj.options[counter].text, sourceobj.options[counter].value);
		}
	}

	for (counter=sourceobj.length-1;counter>=0;counter--)
	{
		if ( sourceobj.options[counter].selected)
		{
			sourceobj.options[counter] = null
		}
	}
}

/*************************************************************************
函数名称：formatDateStr
函数说明：将传入的时间类型变量转换成指定的字符串形式,返回的格式为：YYYY-MM-DD HH:MI:SS

输入参数：
	date：传入的时间类型变量
	
	
输出参数：
	str：传出的字符串类型变量 

************************************************************************/
function formatDateStr(date)
{
	try
	{
	var str="";
	
	str=date.getFullYear()+"-";
	str+=(((date.getMonth()+1)>=10)? (date.getMonth()+1):"0"+(date.getMonth()+1))+"-";
	str+=(date.getDate()>=10)? date.getDate():"0"+date.getDate();
	str+=" ";
	str+=((date.getHours()>=10)? date.getHours():"0"+date.getHours())+":";
	str+=((date.getMinutes()>=10)? date.getMinutes():"0"+date.getMinutes())+":";
	str+=(date.getSeconds()>=10)? date.getSeconds():"0"+date.getSeconds();
	return str;
	}
	catch(e)
	{
		throw e;
	}
}

/*************************************************************************
函数名称：trim
函数说明：将传入的字符串的前后空格去掉

输入参数：
	str：传入的字符串
	
	
输出参数：
	
返回值：
		去掉了前后空格的字符串 

************************************************************************/
function trim(str)
{
	
	return str.replace(/(^\s*)|(\s*$)/g, "");
	/*
	var j=0;
	for(var i=0;i<=str.length;i++)
	{
		j=i;
		if (str.substring(i,i+1)!=" ")
		{
			break;
		}
	}
	var strHead=str.substring(j,str.length);
	j=0;
	for (var i=0;i<strHead.length;i++)
	{
		j=i;
		if (strHead.substring(strHead.length-1-i,strHead.length-i)!=" ")
		{
			break;
		}
	}
	strHead=strHead.substring(0,strHead.length-j);
	return strHead;
	*/
}

/************************************************************
 函数说明：返回字符串的字节数
 函数名称：sizeOfString
 输入参数：
		   str : 字符串
 输出参数：无
 返 回 值：字节数
 ************************************************************/
function sizeOfString(str)
{
	return str.replace(/[^\x00-\xff]/g,"**").length;
}

/*************************************************************************
函数名称：show_props
函数说明：显示一个对象的所有的字段以及字段值

输入参数：
	obj：对象
	
	
输出参数：
	
返回值：
		对象的每个字段值
************************************************************************/
function show_props(obj, obj_name) {
	var result = ""
	for (var i in obj)
	result += obj_name + "." + i + " = " + obj[i] + "\n"
	return result
}

/*********************************************************************************
	函数名称：CreateTable
	函数说明：根据传入的对象数组参数生成DataList信息，若信息条数为一，则直接退出界面
	传入参数：传入的参数包括：
					arr：包括属性：QryType;QryTypeId,QryName
					tblname:传入的DataList类的htc的名称
					showFields:要显示的所有属性。
					certTypeName,certNbr,certTypeId,custName,addressId,areaId,areaId,custId
					
	输出参数：无
	*********************************************************************************/
	function CreateTable(tblname,arr,showFields)
	{
		try
		{	fields="";
			for(j=0;j<showFields.length;j++)
			{
				
				fields+="arr[i]."+showFields[j];
				if(j!=showFields.length-1)
				{
					fields+=",";
				}
			}
			for(var i=0;i<arr.length;i++)
			{	
				
				tblname.items.addItem(eval(fields));
			}
			if (arr.length<=1)
			{
				getSelectedvalue(tblname);
			}
		}
		catch(e)
		{
			ErrorHandle("提示", 1, 2, "生成显示表出错!", e);
		}
	}

 /************************************************************
 函数名称：checkInt
 函数功能：校验数字
 输入参数：无
 输出参数：无 
 返 回 值：无
 函数说明：
 ************************************************************/
function checkInt(str)
{	
	//检查数据
	var bag = "0123456789";

	for(var i=0; i<str.length; i++)
	{
		var c = str.charAt(i);
		if (bag.indexOf(c) == -1)
		{
			return false;
		}
	}
		
	//首位不能含有“0”,如 0123,0234
	if ( (str - 0) != 0 )
	{
		var index = str.indexOf("0");
		if (index == 0)
		{
			return false;
		}
	}		
	return true;	
}

//检查数据，是否全部为 规定的格式数据
function isCharsInBag(s,bag)
{
	var i;

	for(i=0; i<s.length; i++)
	{
		var c = s.charAt(i);
		if(bag.indexOf(c)==-1) 
		{
			return false;
		}
	}
	return true;
}

/*************************************************************************
函数说明：根据传入的xml值对产品属性动态控件进行刷新
函数要求：
输入参数：
	xml:xml串
	dp:动态属性控件名		
输出参数：
	无
************************************************************************/
function refreshIndepProperList(xml,dp)
{
	try
	{
		var xmlDoc = new ActiveXObject("msxml2.Domdocument");
		xmlDoc.loadXML(xml);
		dp.loadXmlDesc(xmlDoc.documentElement);	
	}
	catch(e)
	{
		throw "调refreshIndepProperList里面的动态属性时出错!";
	}
}

/*************************************************************************
函数说明：根据传入的数组对象生成下拉筐

函数要求：要求各个节点的ID都具有唯一性

输入参数：
	drpId	：下拉筐名称
	arrObj	：传入的下拉筐对象数组
	
输出参数：
		无；
************************************************************************/
function CertType_Init(drpId,arrObj)
{
	if (drpId.tagName!="SELECT")
		return false;
	drpId.length=0;
	for (i = 0; i < arrObj.length; i++) 
	{
		var option = document.createElement("OPTION");
		option.text = arrObj[i].text;
		option.value = arrObj[i].id;
		drpId.add(option);
	}
}

/*************************************************************************
函数说明：根据传入的身份证号码验证号码的有效性

函数要求：目前只对身份证进行验证

输入参数：
	certNbr：身份证号码
	
输出参数：
		-1:身份证长度不对；
		-2:提取的日期有误
		-3:日期有效性有误
		-4:函数执行异常
************************************************************************/
function AdjustCertnbr(certNbr)
{
	try
	{
		if (certNbr.length!=15 && certNbr.length!=18)
		{
			return -1;
		}
		var birYear,birMonth,birDay,certDate;
		if (certNbr.length==15)
		{
			certDate="19"+certNbr.substr(6,6);
		}
		else
		{
			certDate=certNbr.substr(6,8);
		}
		
		try
		{
			if (isNaN(certDate.substr(0,4)))
			{
				throw "";
			}
			if (isNaN(certNbr.substr(4,2)))
			{
				throw "";
			}
			if (isNaN(certNbr.substr(6,2)))
			{
				throw "";
			} 
			var birYear=parseInt(certDate.substr(0,4),10);
			var birMonth=parseInt(certDate.substr(4,2),10);
			var birDay=parseInt(certDate.substr(6,2),10);
		}
		catch(e)
		{
			return -2;
		}
		
		try
		{
			var date1=new Date();
			var nowDate=formatDateStr(date1);
			//验证日期时候有误
			var birDate=""+birYear;
			if (birMonth<10)
			{
				birDate+="-0"+birMonth;
			}
			else
			{
				birDate+="-"+birMonth;
			}
			
			if (birDay<10)
			{
				birDate+="-0"+birDay+" 00:00:00";
			}
			else
			{
				birDate+="-"+birDay+" 00:00:00";
			}
			
			if (birDate>nowDate)
			{
				throw "";
			}
			
			var nowYear=date1.getFullYear();
			//验证年时候有误
			if (birYear>nowYear || birYear<nowYear-100)
			{
				throw "";
			}
			
			//验证月时候有误
			if (birMonth>12 || birMonth<1)
			{
				throw "";
			}

			var maxDay=getMonthDay(birYear,birMonth);
			if (birDay>maxDay || birDay <1)
			{
				throw "";
			}
		}
		catch(e)
		{
			return -3;
		}
		return 0;//正常返回
			
	}
	catch(e1)
	{
		return -4;
	}
}

/*************************************************************************
函数说明：根据传入的年、月得到这个月最大的日期

函数要求：目前只对身份证进行验证

输入参数：
	year：输入的年
	mon	：输入的月
	
输出参数：
		-1:身份证长度不对；
		-2:提取的日期有误
		-3:日期有效性有误
		-4:函数执行异常
************************************************************************/
function getMonthDay(year,mon)
{
	var dayNum;
	switch(mon)
	{
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			dayNum=31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			dayNum=30;
			break;
		case 2:
			if ((year % 400==0) || ((year % 4==0) && (year % 100!=0)))
				dayNum=29;
			else
				dayNum=28;
			break;
		default:
			break;
	}
	return dayNum;
}

/*************************************************************************
函数说明：将传入的参数进行拷贝
函数要求：
输入参数：
	objFrom：需要拷贝的输入参数	
输出参数：
		objTo：输入值的备份
************************************************************************/
function copyObject(objFrom)
{
	var objTo=null;
	if (objFrom==null)
	{
		return objTo;
	}
	switch(objFrom.typeName)
	{
		case "number":
		case "boolean":
		case "string":
		case "date":
			objTo=objFrom;
			break;
		case "object":
			objTo=new Object();
			for(var key in objFrom)
			{
				objTo[key]=copyObject(objFrom[key]);
			}
			break;
		case "array":
		objTo=new Array();
		for(var i=0;i<objFrom.length;i++)
		{
			objTo[objTo.length]=copyObject(objFrom[i]);
		}
		break;
	}
	return objTo;
}

/*************************************************************************
函数名称：checkRegExp
函数功能：对值进行正则表达式校验
输入参数：
	val:需要比较的字符串
	exp:正则表达式
输出参数：
	无
函数说明：正则表达式

************************************************************************/
function checkRegExp(val,exp)
{
	var reg = new RegExp(exp);
	
	if(!val.match(reg))
	{
		return false;
	}
	else
	{
		return true;
	}
}

/************************************************************
 函数名称：removeNodeSelfAndChild
 函数功能：删除树的节点，包括子节点
 输入参数：无
 输出参数：无 
 返 回 值：无
 函数说明：
 ************************************************************/ 
 function removeNodeSelfAndChild(nodeSrc)
 {
	if (nodeSrc == null)
	{
		return;
	}		
	var nodeChildren = nodeSrc.getChildren();
	
	if (nodeChildren == null)
	{		
		nodeSrc.remove();
	}
	else
	{
		for (var i=0;i<nodeChildren.length;i++)
		{
			removeNodeSelfAndChild(nodeChildren[i]);						
		}
		nodeSrc.remove();	
	} 
 }
 
  /**************************************************************************
 函数名称：checkStrNull
 函数功能：判断输入对象是否为空
 输入参数：
          obj :  判断对象
          promptInfo : 若为空的提示信息
 输出参数：无 
 返 回 值：无
 函数说明：
 **************************************************************************/
function checkStrNull(obj,promptInfo)
{
	var val = obj.value;
	if(val.replace(/(^\s*)|(\s*$)/g,"") == "")
	{		
		ErrorHandle("", 4, 1, promptInfo, null);
		obj.focus();
		return false;
	}
	else
	    return true;
}

/**************************************************************************
 函数名称：nextCursor
 函数功能：界面控件输入回车后，能自动聚焦到下一个对象控件
 输入参数：
           eve   :  触发对象
           nextobj : 回车后聚焦对象
 输出参数：无 
 返 回 值：无
 函数说明：
 **************************************************************************/
function nextCursor(eve,nextobj)
{	
    if (eve.keyCode==13)
	{
		document.all.item(nextobj).focus();
		eve.keyCode=0;
	}
	return;
}

/**************************************************************************
 函数名称：focusTextEnd
 函数功能：光标,停在[文本编辑框/文本输入框]文字的最后,方便用户输入
 输入参数：
 输出参数：无
 返 回 值：无
 函数说明：           onfocus="focusTextEnd();"
 **************************************************************************/
function focusTextEnd()
{
	var e = event.srcElement;
	var r =e.createTextRange();
	r.moveStart('character',e.value.length);
	r.collapse(true);
	r.select();
}

/**************************************************************************
 函数名称：selectText
 函数功能：将[文本编辑框/文本输入框]文字都选中,方便用户输入
 输入参数：
 输出参数：无
 返 回 值：无
 函数说明：
 **************************************************************************/
function selectText(txtId)    
{
	txtId.focus();
	var rng = document.selection.createRange();
	txtId.select();
}

/**************************************************************************
 函数名称：gotoQuerySubmit
 函数功能：界面控件输入回车后，自动执行"Query"处理
 输入参数：
           eve   :  触发对象
 输出参数：无 
 返 回 值：无
 函数说明：
 **************************************************************************/
function gotoQuerySubmit(eve)
{	
    if (eve.keyCode==13)
	{
		queryOnClick();
	}
	return;
}

/**************************************************************************
 函数名称：checkTextIsEmpty
 函数功能：判断输入对象是否为空
 输入参数：
          obj :  判断对象
 输出参数：无 
 返 回 值：无
 函数说明：
 **************************************************************************/
function checkTextIsEmpty(obj)
{
	var val = obj.value;
	if(val.replace(/(^\s*)|(\s*$)/g,"") == "")	
		return false;	
	else
	    return true;
}

/**************************************************************************
 函数名称：getDateStrFromDateTimeStr
 函数功能：从后台返回的标准时间格式(YYYY-MM-DD HH:MI:SS)中，取日期YYYY-MM-DD
 输入参数：字符串
 输出参数：无 
 返 回 值：字符串
 函数说明：
 **************************************************************************/
function getDateStrFromDateTimeStr(strDateTime)
{	
	var strDate = "";
	if ( (strDateTime != null)&&(strDateTime != "") )
	{
		strDate = strDateTime.substr(0,10);
	}
	return strDate;
}

/************************************************************
 函数名称：checkFloat
 函数功能：校验输入值，为浮点数
 输入参数：无
 输出参数：无 
 返 回 值：无
 函数说明：
 ************************************************************/
function checkFloat(str)
{	
	//保证为字符串
	var strCurrent = str + "";
		
	//本身为空串直接返回
	if (strCurrent.length == 0)
	{
		return true;
	}
	
	//删除首位的负号"-"
	strCurrent = strCurrent.replace(/(^-)/, "");
	
	//空串直接返回 (边界:只有一个减号，也不通过)
	if (strCurrent.length == 0)
	{
		return false;	
	}
	 
	//只能包含有一个小数点
	var arrStr = strCurrent.split(/[\/\. ]/);
	if (arrStr.length > 2)
	{
		return false;
	}
	
	//0.00的判断
	if ( (0 - strCurrent ) == 0 )
	{
		return true;
	}
		
	//遗留问题 00.00
	
	
	//检查数据
	var bag = "0123456789.";
		
	for(var i=0; i<strCurrent.length; i++)
	{
		var c = strCurrent.charAt(i);
		
		/* 暂不校验
		//首位不能含有“0”,如 0123,0234;允许单独一个的“0”
		if ( (i == 0)&&(c == '0')&&(strCurrent.length != 1) )
		{			
			return false;
		}
		*/
		if (bag.indexOf(c) == -1)
		{
			return false;
		}
	}
	return true;
}


//su weiqing add start

/***********************************************************************
函数名称：ClickCheckBoxForTextBoxClick
函数说明：通用checkBox约束testBox点击事件处理函数
传入参数：
传出参数：
************************************************************************/                        
function ClickCheckBoxForTextBoxClick(chkID,elementID)
{
	var checkBox=document.all(chkID);
	var element= document.all(elementID);
	
	if(checkBox.checked==true)
	{
		element.disabled=false;
		element.focus();
	}
	else
	{
		element.disabled=true;
		element.value="";
	}
}   

/**********************************************************
函数名称：ClickCheckBoxForSelectClick
函数说明：通用checkBox约束select点击事件处理函数
传入参数：
传出参数：
***********************************************************/                        
function ClickCheckBoxForSelectClick(chkID,elementID)
{
	var checkBox=document.all(chkID);
	var element= document.all(elementID);
	
	if(checkBox.checked==true)
	{
		element.disabled=false;
		element.focus();
	}
	else
	{
		element.disabled=true;
	}
}  

/**********************************************************
函数名称：ClickCheckBoxForTimePickerClick 
函数说明：通用checkBox约束time picker点击事件处理函数
传入参数：
传出参数：
***********************************************************/  
function ClickCheckBoxForTimePickerClick(chkID,timePickerID)
{
	var checkBox=document.all(chkID);
	var timePicker= document.all(timePickerID);	
	if(checkBox.checked==true)
	{
		timePicker.btnEnable=true;
	}
	else
	{
		timePicker.btnEnable=false;
		timePicker.value="";
	}
}

/**********************************************************
函数名称：ClickCheckBoxForPopEditClick
函数说明：通用checkBox约束pop edit点击事件处理函数
传入参数：
传出参数：
***********************************************************/  
function ClickCheckBoxForPopEditClick(chkID,popEditorID)
{
	var checkBox=document.all(chkID);
	var popEditor= document.all(popEditorID);	
	if(checkBox.checked==true)
	{
		popEditor.btnDisabled=false;
	}
	else
	{
		popEditor.btnDisabled=true;
		var obj=new Object();
		obj.DisplayName="";
		popEditor.value=obj;
	}
} 


/************************************************************
 函数名称：formatFloat
 函数功能：对输入的参数进行格式化（包含两位小数点的浮点数）
 输入参数：
			str   数值
 输出参数：
			包含两位小数点的格式
 返 回 值：无
 函数说明：
 ************************************************************/
function formatFloat(str)
{	
	var strCurrent = str + "";
    var pos = strCurrent.indexOf(".");
    if(pos>0)
    {
		//strCurrent = strCurrent+"00";
		//strCurrent = strCurrent.substr(0,pos+2);
		
		if(strCurrent.length-pos<=2)
			strCurrent=strCurrent+"0";
		else
			strCurrent=strCurrent.substr(0,pos+3);		
    }
    else
    {
		strCurrent = strCurrent+".00";
    }
    
    if(strCurrent==".00")
		strCurrent="0.00";		
    
	return strCurrent;
}


/************************************************************
 函数名称：checkIsFloat
 函数功能：验证输入的值是否为浮点数
 输入参数：无
 输出参数：无 
 返 回 值：无
 函数说明：
 ************************************************************/
function checkIsFloat(strCurrent)
{		
	if(strCurrent == '' || strCurrent == null) 
		return false;

	var strCurrent = strCurrent + "";
		
	//验证格式是否正确(允许输入整数或浮点数)
    var regInt = /^[0-9]+$/; 
	var regFloat = /^[0-9]*.?[0-9]{1,}$/; 
	
	if(!regFloat.test(strCurrent))
	{
		if(!regInt.test(strCurrent))
		{
			return false;
		}
	}
	
	return true;
}

/************************************************************
 函数名称：checkNumberAndChar
 函数功能：校验只允许输入字母和数字
 输入参数：无
 输出参数：无 
 返 回 值：无
 函数说明：
 ************************************************************/
function checkNumberAndChar(strCurrent)
{		
	if(strCurrent == '' || strCurrent == null) 
		return false;

	if( /[^0-9a-zA-Z]/g.test(strCurrent) ) 
	{
		return false;
	}
	
	return true;
}
//su weiqing add end

/************************************************************
 函数名称：addEndZero 
 函数功能：增加小数点[保持小数点后，一定有几位小数,如 (1.00)]
 输入参数：
			strVal       需要校验的字符串或数
			intAmount    几位
			strIntercept 是否截取,如2位小数，输入0.1234; 如截取，返回0.12  					
 输出参数：无 
 返 回 值：无
 函数说明：        如： addEndZero(intCurrencyBalanceNew + "",2,"Y")
 
 ************************************************************/ 
function addEndZero(strVal,intAmount,strIntercept)
{
	var curVal = strVal + "";
	
	var ifraction = intAmount; //小数位数(浮点数)
	
	//浮点数需要自动补齐位数--先补齐，再作长度判断
	if ( curVal.length !=0 )
	{
		do
		{
			var index = curVal.indexOf(".");
			if(index == -1)
			{
				if(ifraction==0) break;
				curVal += '.';
				index = curVal.length - 1;
			}
			else
			{
				if(index==0)
				{
					curVal = "0"+curVal;
					index++;				
				}	
				if(ifraction==0)
				{
					curVal = curVal.substring(0,index);
					break;
				}
			}
			
			
			if(curVal.length-index-1>ifraction)
			{
				//截取小数位数
				if (strIntercept == "Y")
				{
					curVal = curVal.substring(0,index+ifraction+1);
				}
			}
			else
			{
				//补齐小数位数
				for(var i=curVal.length-index-1;i<ifraction;i++)
				{
					curVal +='0';
				}
			}
		}while(false);
		
	}
	
	return curVal;	
}


/************************************************************
 函数名称：deleteRadixPoint
 函数功能：去掉小数点
 输入参数：无
 输出参数：无 
 返 回 值：无
 函数说明：
 ************************************************************/
function deleteRadixPoint(str)
{  
	//先匹配输入的开头为 "0." , 再匹配小数点
    return str.replace(/(^0\.)|(\.)/, "");
}

/************************************************************
 函数名称：shrRadixPoint
 函数功能：左移小数点
 输入参数：
			str	: 字符串或整数,数值必须为整数
			n	: 左移几位小数点	
			
 输出参数：无 
 返 回 值：字符串
 函数说明：		左移2位小数点 		shrRadixPoint(strCharge,2);
 
 ************************************************************/
function shrRadixPoint(str,n)
{
	if ( (str == null)||(str == "")||(str == "null") )
	{
		return "";
	}
	
	//保证为字符串
	var strCurr = str + "";
	var bFlag = false;
	
	//判断前面是否有负号"-"
	var index = strCurr.indexOf("-");
	if(index != -1)
	{
		bFlag = true;
	}	
	//删除首位的负号"-"
	strCurr = strCurr.replace(/(^-)/, "");
	
	if (strCurr.length <= n)
	{
		//位数不足，左补零
		for(var i=strCurr.length;i<n;i++)
		{
			strCurr = '0' + strCurr;
		}		
		strCurr = '0.' + strCurr;		
	}
	else
	{
		//直接左移小数点
		var strBegin = strCurr.substring(0,strCurr.length-n);
		var strEnd = strCurr.substring(strCurr.length-n,strCurr.length);		
		strCurr = strBegin+'.'+strEnd;
	}	
	if (bFlag)
	{
		strCurr = '-' + strCurr;	
	}	
	return strCurr;
}


/************************************************************************
 函数名称：nullToEmptyStr
 函数功能：null转换为空串
 输入参数：对象
 输出参数：无 
 返 回 值：无
 函数说明：
 ***********************************************************************/
function nullToEmptyStr(objVal)
{		
        if(objVal==null || objVal=="undefined")    
			return "";
		else
			return objVal+"";
}
/**********************************************************
函数名称：SubtractLargeNumber
函数说明：计算beginNum和endNum的差值(endNum-beginNum),调用之前保证传入的
传入参数：beginNum   ：起始号码（字符串）
		  endNum     ：终止号码（字符串）
		  calcuLength: 指定可以进行计算的长度,不输入，默认为16
返回：   正确：返回两字符串转换为数字后的差值
         错误：返回null
***********************************************************/
function SubtractLargeNumber(beginNum,endNum,calcuLength)
{
	//参数不能为空
	if(beginNum==null||endNum==null)
	{
		return null;
	}
	//终止号码长度不能小于起始号码长度
	if(beginNum.length > endNum.length)
	{
		return null;
	}
	//没有指定长度或长度超过16,设置为16。
	if(calcuLength==null||calcuLength.length>16||calcuLength.length<=0)
	{
		calcuLength=16;
	}
	//如果起始号码长度超过指定 长度。将其截断
	if(beginNum.length>calcuLength)
	{
		beginNum=(beginNum.substring(beginNum.length-calcuLength,beginNum.length));
	}
	//如果终止号码长度超过指定 长度。将其截断
	if(endNum.length>calcuLength)
	{
		endNum=(endNum.substring(endNum.length-calcuLength,endNum.length));
	}	
	//返回差值
	return (endNum-beginNum)+1;
}