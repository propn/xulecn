/*************************************************************************
  File Name:BillingCyclePopByRegionAndAvail.js
	Author:zhang.jing
	create Version:01.00.001
	Create Version Date:2005-03-17
	modify Version:01.01.010
	modify Version Date:2005-06-08
	Description:
*************************************************************************/
var g_checkFlag = false;
var g_regionId = "";


/*******************************************
 *函数功能：初始化页面(byRegion)
 *输入参数：无
 *返 回 值：无
*******************************************/
function initPageByRegion()
{	
	
	var today = new Date();
 	var sToday = dateToString(today).split(" ")[0];
 	var sYear = sToday.split("-")[0];
 	document.all("billingCycleQryDateBegin").value = sYear + "-01-01";
 	document.all("billingCycleQryDateEnd").value = sYear + "-12-31";
 	
 	this.initBillingCycleTreeByRegion();
}



/*******************************************
 *函数功能：初始化页面(by Region)
 *输入参数：无
 *返 回 值：无
*******************************************/
function initBillingCycleTreeByRegion()
{
	//取得regionId
	var parentObj = window.dialogArguments;
	g_regionId = parentObj.regionId;
	
	var sQryDateBegin = document.all("billingCycleQryDateBegin").value;
	var sQryDateEnd = document.all("billingCycleQryDateEnd").value;
	if(sQryDateBegin == "" || sQryDateEnd == ""){
		ErrorHandle(null,2,1,ERROR_NO_DATE,"");
		return;
	}
	
	if(g_regionId==""){
		ErrorHandle(null,2,1,ERROR_NO_REGIONID,"");
		document.all("billingCycleTree").clear();
		return;
	}
	
	//对输入的起始时间和结束时间进行比较判断
	if(!betweenDateVali(sQryDateBegin,sQryDateEnd)){
		document.all("billingCycleTree").clear();
		return;
	}
	
	
	var dQryDateBegin = parseDate(sQryDateBegin + " 00:00:00");
	var dQryDateEnd = parseDate(sQryDateEnd + " 00:00:00");
	var iBillingCycleTypeId = 1;
	var list = findAvailNotStrikeByRegionId(g_regionId,dQryDateBegin,dQryDateEnd);
	if(list == null) {
		document.all("billingCycleTree").clear();
		return;
	}
  printBillingCycleTree(list);
  var allItems = document.all("billingCycleTree").items;
  if(allItems.length == 0) return;
  if(typeof(parentObj.Id) != "undefined"){
  	for(var i=0;i<allItems.length;i++){
  		if(parentObj.Id == allItems[i].billingCycleId){
  			allItems[i].setSelected(true);
  			break;
  		}
  	}  	
  }
  else{
      allItems[0].setChecked(true);
      allItems[0].setSelected(true);
	}
  
}


function printBillingCycleTree(list)
{
  if (list == null)
  {
  	document.all("billingCycleTree").loadByXML('<items></items>');
  	return;
  }
	
	var oSTATESET = new staticPropertyList("STATE_SET");
  var str = new Array();
  str.push('<items>');
  for(var i=0;i<list.length;i++)
  {
    
    var temp = "<item billingCycleTypeName='" + list[i].billingCycleTypeName + "'"
    	+	" cycleBeginDate='" + dateToString(list[i].cycleBeginDate) + "'"
    	+	" cycleEndDate='" + dateToString(list[i].cycleEndDate) + "'"
    	+	" dueDate='" + dateToString(list[i].dueDate) + "'"
    	+	" blockDate='" + dateToString(list[i].blockDate) + "'"
    	+ " stateName='" + oSTATESET.getItemCode(list[i].state) + "'"   	  
    	+	" stateDate='" + dateToString(list[i].stateDate) + "'"
    	+ " billingCycleId='" + list[i].billingCycleId + "'"  	    
    	+	" billingCycleTypeId='" + list[i].billingCycleTypeId + "'" 	    	    	    	
    	+	" state='" + list[i].state + "'"
    	+	">"
    	+ "</item>";
    str.push(temp);
    
  }
  str.push('</items>');

  document.all("billingCycleTree").loadByXML(str.join(""));
  str = null;
}

/*******************************************
 *函数功能：选择一条记录返回
 *输入参数：无
 *返 回 值：无
*******************************************/
function itemClicked()
{
		var node = document.all("billingCycleTree").selectedItem;
    if (node == null)
    	return;
    	
  	if (node.getChecked() == true)
  	{
  		node.setChecked(false);
  	}
  	else
  	{
  		var nodes = document.all("billingCycleTree").items;
  		for (var i=0;i<nodes.length;i++)
  		{
  				nodes[i].setChecked(false);
  		}
  		node.setChecked(true);
  	}
}
 

/*******************************************
 *函数功能：点击树节点的选择框触发事件
 *输入参数：无
 *返 回 值：无
*******************************************/
function CheckedClick()
{
			var node = document.all("billingCycleTree").selectedItem;
    	if (node.getChecked() == true)
    	{
    		var nodes = document.all("billingCycleTree").items;
      	for (var i=0;i<nodes.length;i++)
      	{
      			nodes[i].setChecked(false);
      	}
    		node.setChecked(true);
    	}	
    	else
    	{
    		node.setChecked(false);
    	}
} 
 
/*******************************************
 *函数功能：关闭窗口
 *输入参数：无
 *返 回 值：无
*******************************************/ 
function closeWinClick()
{
    window.opener = null;
    window.close();
}

/*******************************************
 *函数功能：选择一条记录返回
 *输入参数：无
 *返 回 值：无
*******************************************/  
function confirmClick()
{

  var node = document.all("billingCycleTree").selectedItem;
  if (node == null)
  {
  	ErrorHandle("",1, 1, ERROR_BILLINGCYCLESELECT,"");
  	return;
  }
  var obj = new Object();
  obj.Id = node.billingCycleId;
  obj.DisplayName = node.cycleBeginDate + "  至  " + node.cycleEndDate;
	obj.regionId = g_regionId;
	
	window.returnValue=obj;
  window.opener=null;
  window.close();
}


