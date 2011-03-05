/*************************************************************************
  File Name:BankMutilPop.js
	Author:zhang.jing
	create Version:01.00.001
	Create Version Date:2005-03-17
	modify Version:
	modify Version Date:
	Description:
*************************************************************************/
var g_currentNodeName = "";
var g_bankIds = "";
function initPage()
{
	initTrees();
	
	var para = window.dialogArguments;
	if (para.Id != null)
	{
		g_bankIds = para.Id;
		var items = document.all("bankTreeView").items;
		var arr = g_bankIds.split(",");
		for(var i=0;i<arr.length;i++)
		{
			for (var j=0;j<items.length;j++)	
			{
				if (items[j].branchFlag == "BANKBRANCH" && arr[i] == items[j].bankId)	
				{
						items[j].setChecked(true);
						items[j].setSelected();
				}
			}
		}
	}	
	
	
/*	var items = document.all("bankTreeView").items;
	for(var i=0;i<items.length;i++)
	{
		
		if (items[i].branchFlag != "BANKBRANCH")	
		{
			items[i].setCheckDisabled(true);	
		}
	}
*/	
}



function fncOk()
{
		g_bankIds = "";
		var items = document.all("bankTreeView").checkedItems;
		for (var i=0;i<items.length;i++)
		{
				if (items[i].branchFlag == "BANKBRANCH")
				{	
					g_bankIds = g_bankIds + items[i].bankId + ",";
					g_currentNodeName = items[i].bankName;
				}
		}
		
		if (g_bankIds == "")
		{
			ErrorHandle("",2, 1, ERROR_BANKSELECT,"");
			return false;			
		}
		
		var arr = g_bankIds.split(",");
		if (arr.length > 2)
		{
				g_currentNodeName = MSG_MUTILBANKSELECT;
		}
			
		var obj = new Object();
		obj.DisplayName = g_currentNodeName;
		obj.Id=g_bankIds;
		returnValue=obj;
		window.opener=null;
		window.close(); 
}
 
 
function fncCancel()
{
	window.opener=null;
	window.close();
}