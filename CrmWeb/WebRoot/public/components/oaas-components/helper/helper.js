/*************************************************************************
1）	Copyright by OSS R&D Dept. of ZTEsoft,2004-04-27
	File Name:helper.js
	Create Date:2004-04-27
	Author:Jin.XiangGuo
	create Version:0.0.0.1
	Version:0.0.0.1
	Create Version Date:2004-04-27
	Description:提供给前台（javasctipt/htc）一些公用函数，方便大家共享资源
	Function Lists:	（在添加了函数之后在 Fun?tion Lists中也要相应的添加）
		Tree_Init			:	根据传入的对象数组生成树
		GetTreeNodeById		:	根据传入的Id提取Node
		TreeRemove			:	根据传入的TreeName删除下面的节点
		CertType_Init		:	根据传入的数组对象生成下拉筐
		selDropDownList		:	根据传入的参数值，比较下拉框每一格option和参数值，确定下拉框被选中的项
		addOptionToSelect	:	根据传入的参数构造select的选项，并添加到下拉列表中
		moveitemsbetween	:	从原列表选择列表选择选项到目标列表中，并且可以把目标列表选项取消返回原列表
		formatDateStr		:	将传入的时间类型变量转换成指定的字符串形式,返回的格式为：YYYY-MM-DD HH:MI:SS
		trim				:	   将传入的字符串的前后空格去掉
		Tree_Init:      树没有初始化
		TreeRemoved:    在提取子接点的时候处理有点问题


2）	Modified By Name Of Programmer:yang.jingyun
	Modified Date:2004-08-06
	Current Version:0.0.0.2
	Function Added:
				removeAllOption: 删除一个下拉框所有选项
				nullRepl(str,strReplace): 如果字符为null，替换
				show_props(obj)：先是一个对象的所有字段名称和值
				objclone(obj): 对象克隆方法
	Function Removed:
				
	Function Changed:

	Other Changes:

*************************************************************************/


//定义的全局变量
var g_nMin = Number.MIN_VALUE;
var g_nMax = Number.MAX_VALUE;
var INT_NUM="0123456789";
var FLOAT_NUM="0123456789.";

/*************************************************************************
函数说明：根据传入的对象数组生成树

函数要求：要求各个节点的ID都具有唯一性

输入参数：
	treeName：树名
	arrOjb  ：对象数组名(一维数组）
			对象属性包括：id		：需要生成的树节点的ID值
						  text		：树节点的text属性值
						  level		：树节点的层次值
						  upNodeId	：上级树节点的ID值
			如：
				arr[0].level=0;
				arr[0].id="市话业务";
				arr[0].text="市话业务";
				arr[0].upNodeId="";

输出参数：
		无；
************************************************************************/
function Tree_Init(treeName,arrObj)
{

	//2004-04-28:arrOjb的level属性可不用
	for(var i = 0; i < arrObj.length; i++)
	{
		if(arrObj[i].upNodeId == "")
		{
			var node=treeName.createTreeNode();
			node.setAttribute("ID",arrObj[i].id);
			node.setAttribute("TEXT",arrObj[i].text);
			//node.setAttribute("hasGetInfoFromServer",false);
			treeName.add(node);
		}
		else
		{
			var node1=GetTreeNodeById(arrObj[i].upNodeId,treeName);
			if (node1==null)
			{
				ErrorHandle("提示", 2, 1,arrObj[i].text+"在寻找上级树节点时报错", null);
				break;
			}
			else
			{
				var node=treeName.createTreeNode();
				node.setAttribute("ID",arrObj[i].id);
				node.setAttribute("TEXT",arrObj[i].text);
				node1.add(node);
			}
		}
	}
}

/*************************************************************************
函数说明：根据传入的对象数组生成树

函数要求：要求各个节点的ID都具有唯一性

输入参数：
	treeName：树名
	arrOjb  ：对象数组名(一维数组）
			对象属性包括：id		：需要生成的树节点的ID值
						  text		：树节点的text属性值
						  child		: 子节点
			如：
				arr[0].id="0";
				arr[0].text="市话业务";
				arr[0].child;

输出参数：
		无；
************************************************************************/
function GetTreeNodeText(model)
{
	var arrHtml = new Array();
	arrHtml[arrHtml.length]="<TVNS:treenode";
	arrHtml[arrHtml.length]=" id="+model.id+" >";
	arrHtml[arrHtml.length]=model.text;
	if(model.child!=null)
	{
		 	for(var i=0;i<model.child.length;i++)
		 		 	arrHtml[arrHtml.length]= GetTreeNodeText(model.child[i]);
	}
	arrHtml[arrHtml.length]="</TVNS:treenode>";
	return arrHtml.join("");
}

/*************************************************************************
函数说明：根据传入的Id提取Node

函数要求：要求各个节点的ID都具有唯一性

输入参数：
	treeName：树节点
	id  ：需要查询Node的节点的Id值

输出参数：
		node：查询到的节点；若为null则说明未查询到符合条件的节点
************************************************************************/
function GetTreeNodeById(id,treeName)
{
	var arrNode=new Array();
	var  node=null;
	arrNode=treeName.getChildren();

	for (var i=0;i<arrNode.length;i++)
	{
		if(arrNode[i].getAttribute("ID")==id)
		{
			node=arrNode[i];
			break;
		}
		else
		{
			node= GetTreeNodeById(id,arrNode[i]);
			if (node==null)
				continue
			else
				return node;
		}
	}
	return node;
}

/*************************************************************************
函数说明：根据传入的TreeName删除下面的节点

函数要求：串入一个存在的树的name

输入参数：
	treeName：树节点

输出参数：
		无
************************************************************************/
function TreeRemove(treeName)
{
	var arrNode=treeName.getChildren();
	for (var i=0;i<arrNode.length;i++)
	{
		arrNode[i].remove();
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
function Select_Init(drpId,arrObj,colarr)
{
	if (drpId.tagName!="SELECT")
		return false;
	drpId.length=0;
	for (i = 0; i < arrObj.length; i++)
	{
		var option = document.createElement("OPTION");
		option.text = eval("arrObj[i]."+colarr[0]);
		option.value = eval("arrObj[i]."+colarr[1]);
		drpId.add(option);
	}
}



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
函数说明：清除select对象的数据

输入参数：
	select对象名：oSelect

输出参数：


************************************************************************/

function removeAllOption(oSelect){
	var oOption = oSelect.options;
        var j=oOption.length;
        for(i=0;i<j;i++)
             oSelect.remove(0);
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

/* 获得月份天数 */
function getRealDays(year,month){
  var daysInMonth = new Array(31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
  if (2 == month)
    return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
  else
    return daysInMonth[month];
}

/* 月份，获得月份的数组
输入参数：  weekNum 周几 周日为0
返回值： 一个月周几的数组

测试代码：
    var tmpArr = getDayofWeekArr(2004,9,2);
    for(var i=0;i<tmpArr.length;i++){
      document.write(tmpArr[i] +"<br>");
    }
*/
function getDayofWeekArr(year1,month1,weekNum){
  var dayofWeekArr = new Array();
  var numofMonth = parseInt(getRealDays(year1,month1))+1;
  var firstDayWeek = (new Date(year1,(month1-1),1)).getDay();
  var firstWeekNum =((firstDayWeek-weekNum)>0)?(8+weekNum-firstDayWeek):(1+weekNum-firstDayWeek);
  for(var i=firstWeekNum;i<numofMonth;i+=7)
    dayofWeekArr[dayofWeekArr.length]=i;

  return dayofWeekArr;
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
	函数名称：checkInt
	函数说明：检查输入是否为数字（已包含去除字符串前后的空格的功能）
	传入参数：numString：需要转换的字符串

	输出参数：格式正确：整数；格式错误：NaN（可在程序中用 if(isNaN(val)) return; 判断）
	建立日期：2004-06-17
	作    者：高 技
*********************************************************************************/
function checkInt(numString)
{
	//去除前后空格
	numString=trim(numString);
	if(numString == "")
	{
		ErrorHandle("输入错误", 2, 1, "请输入整数！", null);
		return NaN;
	}
	if(!isCharsInBag(numString, INT_NUM))
	{
		ErrorHandle("输入错误", 2, 1, "请输入整数！", null);
		return NaN;
	}
	var val = parseInt(numString);
	if(val>g_nMax || val<g_nMin)
	{
		ErrorHandle("输入错误", 2, 1, "输入值必须大于"+g_nMin+"并小于"+g_nMax, null);
		return NaN;
	}
	return val;
}

/*********************************************************************************
	函数名称：CheckFloat
	函数说明：检查输入是否为浮点数（已包含去除字符串前后的空格的功能）
	传入参数：numString：需要转换的字符串

	输出参数：格式正确：浮点数；格式错误：NaN（可在程序中用 if(isNaN(val)) return; 判断）
	建立日期：2004-06-17
	作    者：高 技
*********************************************************************************/
function CheckFloat(numString)
{
	//去除前后空格
	numString=trim(numString);
	if(numString == "")
	{
		ErrorHandle("输入错误", 3, 1, "请检查一下您输入的是否为浮点数！", null);
		return NaN;
	}
	if(!isCharsInBag(numString, FLOAT_NUM))
	{
		ErrorHandle("输入错误", 3, 1, "请检查一下您输入的是否为浮点数！", null);
		return NaN;
	}
	var val = parseFloat(numString);
	if(val>g_nMax || val<g_nMin)
	{
		ErrorHandle("输入错误", 3, 1, "输入值必须大于"+g_nMin+"并小于"+g_nMax, null);
		return NaN;

	}
	return val;
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
输入参数：year：输入的年  mon	：输入的月
************************************************************************/
function getMonthDay(year,mon){
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
		  dayNum = ((year % 400==0) || ((year % 4==0) && (year % 100!=0)))?29:28;
			break;
		default:
		  dayNum =31;
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
函数说明：日期处理函数，比较两个日期的相差天数

函数要求：

输入参数：
	startDay：date
        endDay:date


输出参数：
	无
************************************************************************/
function elapsedTime(startDay,endDay){
   dStartDay = new Date(startDay);
   dEndDay = new Date(endDay);

   interval = dEndDay.getTime() - dStartDay.getTime(); // Difference in ms.

   // Establish larger units based on milliseconds.
   msecondsPerMinute = 1000 * 60;
   msecondsPerHour = msecondsPerMinute * 60;
   msecondsPerDay = msecondsPerHour * 24;

   // Calculate how many days the interval contains, then subtract that
   // many days from the interval to come up with a remainder.
   days = Math.floor( interval / msecondsPerDay );
   interval = interval - (days * msecondsPerDay );

   // Repeat the previous calculation on the remainder using hours,
   // then subtract the hours from the remainder.
   hours = Math.floor( interval / msecondsPerHour );
   interval = interval - (hours * msecondsPerHour );

   minutes = Math.floor( interval / msecondsPerMinute );
   interval = interval - (minutes * msecondsPerMinute );

   seconds = Math.floor( interval / 1000 );

   msg = "Total = " + days + " days, " + hours + " hours, " + minutes +
      " minutes, and " + seconds + " seconds.";
 // alert(days);
   return days;
}

/*************************************************************************
函数说明：日期处理函数，获得日期的字符串表达式
函数要求：
输入参数：
	today：date


输出参数：
	无
************************************************************************/
function getDateString(d){
	/*
   todayStr = "" + today.getFullYear()+"-";
   if (today.getMonth() < 9)
      todayStr += "0";
   todayStr +=  (today.getMonth() + 1)+"-";
   if (today.getDate() < 10)
      todayStr += "0";
   todayStr += today.getDate();
   return todayStr;*/
   var strMonth=getFormatDateTime(d.getMonth()+1);
	var strDay=getFormatDateTime(d.getDate());
	var  strHours=getFormatDateTime(d.getHours());
	var strMinutes=getFormatDateTime(d.getMinutes());
	var strSeconds=getFormatDateTime(d.getSeconds());
	strSeconds=strSeconds.substr(strSeconds.length-2,2);
	return d.getFullYear()+"-"+strMonth+"-"+strDay+" "+strHours+":"+strMinutes+":"+strSeconds;

}
//获取两位的时间格式
function getFormatDateTime(strDate)
{
	var result='00'+strDate;
	return result.substr(result.length-2,2);
}
/*************************************************************************
函数说明：日期处理函数，parseDate
函数要求：
输入参数：
	str：2004-02-01


输出参数：
	无
************************************************************************/

function exParseDate(str){
  var yearStr =	str.substring(0,4);
  var monthStr  = str.substring(5,7);
  var dayStr  =  str.substring(8,10);
  var dDay = new Date(yearStr,monthStr-1,dayStr);
   return dDay;
}

/*************************************************************************
函数说明：日期处理函数，exParseDateTime
函数要求：
输入参数：
	str：2004-02-01 11:12:11


输出参数：
	日期和时间
************************************************************************/
function exParseDateTime(str)
{
  var yearStr =	str.substring(0,4);
  var monthStr  = str.substring(5,7);
  var dayStr  =  str.substring(8,10);
  var hourStr = str.substring(11,13);
  var minStr = str.substring(14,16);
  var secStr = str.substring(17,19);
  var dDay = new Date(yearStr,monthStr-1,dayStr,hourStr,minStr,secStr);
   return dDay;
}


/*************************************************************************
函数说明：日期处理函数，获得日期的字符串表达式
函数要求：
输入参数：
	today：date


输出参数：
	无
************************************************************************/
function dateToString(d)
{
	if(d==null) return "";
	//return d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	var strMonth=getFormatDateTime(d.getMonth()+1);
	var strDay=getFormatDateTime(d.getDate());
	var  strHours=getFormatDateTime(d.getHours());
	var strMinutes=getFormatDateTime(d.getMinutes());
	var strSeconds=getFormatDateTime(d.getSeconds());
	strSeconds=strSeconds.substr(strSeconds.length-2,2);
	return d.getFullYear()+"-"+strMonth+"-"+strDay+" "+strHours+":"+strMinutes+":"+strSeconds;

}

/*********************************************************************************
函数名称：setNode
函数说明：treeview树定位某一节点（本函数只适用于两层树节点定位）
传入参数：
        treeViewDiv：treeView的Id名称
        propertyName:treeView某一节点的某一属性值
        sValue：定位节点的propertyName值
输出参数：
	无
*********************************************************************************/
  function setNode(treeViewDiv,propertyName,sValue)
  {
    var sNode = eval(treeViewDiv).getChildren();
    if(sNode.length>0)
    {
      for(var i=0;i<sNode.length;i++)
      {
        var sChildNode=sNode[i].getChildren();
        if(sChildNode.length>0)
        {
          for(var j=0;j<sChildNode.length;j++)
          {
            if(sChildNode[j].getAttribute(propertyName)==sValue)
            {
              sChildNode[j].refresh();
              sChildNode[j].setSelected();
              break;
            }
          }
        }
      }
    }
  }

/*
弹出窗口 w h 无名字
参数：url 弹出地址
      height 高度
      width  宽度
*/
function openWin(url,height,width){
 window.open(url,'',"height="+ height +",width="+ width +",left=100,top=70,resizable=yes,scrollbars=yes,status=no,toolbar=no,menubar=no,location=no");
}

/*弹出窗口窗口，有名字 */
function openNameWin(url,height,width,winName){
 window.open(url,winName,"height="+ height +",width="+ width +",left=100,top=70,resizable=yes,scrollbars=yes,status=no,toolbar=no,menubar=no,location=no");
}

/*获取查询字符串参数值
param 参数
*/
function getUrlParameter(param){
	var url=window.location.search;
	var pos1=0,pos2=0;
	var strReturn="";
	pos1=url.indexOf("&"+param+"=");
	if(pos1==-1)pos1=url.indexOf("?"+param+"=");
	if(pos1==-1){
    return;
	}else{
		pos2=url.indexOf("&",pos1+1);
		if(pos2==-1)pos2=url.length;
		var strReturn=url.substring(pos1+param.length+2,pos2);
		strReturn=unescape(strReturn);
		return strReturn;
	}
}
/*********************************************************************************
函数名称：nullRepl
函数说明：如果字符为null，替换
传入参数：
       
输出参数：
	无
*********************************************************************************/

function nullRepl(inStr, repStr){
	return (inStr==null)?repStr:inStr;
}

function htmlEncoding(str){

    var dq = "\u0022";//双引号
    var sq = "\u0027";//单引号
    var tq = "\u005c";//反斜杠
    var eq = "\u003d";//等于
    var lq = "\u003c";//小于
    var gq = "\u003e";//大于

    str = str.replace("\"", dq);
    str = str.replace("'", sq);
    str = str.replace("\\", tq);
    str = str.replace("=", eq);
    str = str.replace("<", "&lt;");
    str = str.replace(">", "&gt;");
    return str;

}
//-->

//对象克隆方法
function objclone(obj){
	  var returnObj = new Object();
		for(var key in obj)
		{
			returnObj[key] =obj[key];


		}
		return returnObj;
	}

/*********************************************************************************
函数名称：showNextWeek,showNumMonth，showNextYear
函数说明：
传入参数：
       
输出参数：
	无
*********************************************************************************/

function showNextWeek(){
dtNextWeek = new Date();
dtNextWeek.setDate( dtNextWeek.getDate()+7 );
return dtNextWeek;

}


function showNumMonth(monthNum){
   currentDate = new Date();
   currentDate.setMonth(currentDate.getMonth() + monthNum );
   return currentDate;
}

function showNextYear(){
currentDate = new Date();
currentDate.setYear( currentDate.getFullYear() +1 ) ;
return currentDate;
}


