var g_bMultipleChoice;
var selectedAdv=null;//记录现在正在使用的adv
var dataOnChange=false;//用来记录选中记录时候发生了变化
var backColor="white";
var backSelectColor="#AFDAFF";
var tbl=null;
var g_userData;
function clearDiv(divEle)
{
	var tblId="tbl"+divEle.id;
	var strArr="<table width='100%'  id="+tblId+" border='0' cellPadding='0' cellSpacing='0'	onmouseover='tbl_onmouseover();' ";
	strArr+="	onmouseout='tbl_onmouseout();' onclick='tbl_onclick();' onblur='tbl_onblur();'";
	strArr+="style='BORDER-BOTTOM:1px solid; BORDER-LEFT:1px solid; BORDER-RIGHT:1px solid; BORDER-TOP:1px solid;background-color : #FFFFFF;'></table>";
	
	divEle.innerHTML=strArr;
	divEle.onscroll=divScroll;
	with(divEle.style)
	{
		display = "none";
		position = "absolute";
	}
}
function setItems(divId)
{
	var items=event.getAttribute("items");
	selectedAdv=event.srcElement;
	g_bMultipleChoice=selectedAdv.multipleChoice;
	var divEle=document.all(divId);
	clearDiv(divEle);
	g_userData=new Array();
	if (items!=null)
	{
		tbl=divEle.children[0];
		for(var i=0;i<items.length;i++)
		{
			var row= tbl.insertRow();
			row._index=i;
			row.style.backgroundColor=backColor;
			var cell = row.insertCell();
			cell.height="10px";
			
			fillCellWithOptionItem(cell,items[i]);
		}
		
		selectedAdv.userData=g_userData;
		
		with(divEle.style)
		{
			display = "";
			position = "absolute";
			width=event.srcElement.offsetParent.clientWidth;
		}
		
		//选中的记录需要被选中
		if (g_bMultipleChoice)
		{
			var selectedItem=selectedAdv.value;
			if (selectedItem!=null && items!=null)
			{
				for (var i=0;i<selectedItem.length;i++)
				{
					for (var j=0;j<items.length;j++)
					{
						if (selectedItem[i].value==items[j].value)
						{
							tbl.rows[j].cells[0].children[0].checked=true;
							break;
						}
					}
				}
			}
		}
		tbl.focus();
	}
}

function fillCellWithOptionItem(cell,item)
{
	cell.innerHTML="<input type=checkbox class='checkbox'>"+item.text;
	cell.children[0].style.display= (g_bMultipleChoice==true?'':'none')
	cell._item  = item;
	if (item.affectProperties != null)
	{
		var arr = item.affectProperties.split(',');
		
		for (var i=0;i<arr.length;i++)
		{		
			g_userData[g_userData.length] = arr[i];
		}
		
	}	
}

function tbl_onmouseover()
{
	var row = GetRow(event.srcElement);
	if(row!=null && tbl == GetTable(row))
	{
		//row.style.backgroundColor="blue";
		row.style.backgroundColor=backSelectColor;
	}
}

function GetTable(elm)
{
	if(elm==null||elm.tagName=="TABLE")
		return elm;
	else
	{
		return GetTable(elm.parentElement);
	}
}

function GetRow(elm)
{
	if(elm==null||elm.tagName=="TR")
		return elm;
	else
	{
		return GetRow(elm.parentElement);
	}
}
function tbl_onmouseout()
{
	var row = GetRow(event.srcElement);
	if(row!=null && tbl == GetTable(row))
	{	
		row.style.backgroundColor=backColor;
	}
}

function tbl_onblur()
{
	var elm = document.elementFromPoint(event.clientX,event.clientY);
	if((elm==null || !tbl.contains(elm)) && elm!=tbl.parentElement)
	{
		tbl.style.display="none";
		if(g_bMultipleChoice)
		{
			//多选的值更改为[value1][value2]...的形式
			//.innerText=values;
			//var orgVal = getValue();//array
			var orgVal = selectedAdv.value;
			var curVal = collectMutlipleValues();
			
			if(!compareOptionArray(orgVal,curVal))
			{
				selectedAdv.value = curVal;
				selectedAdv.onEventChange(orgVal,curVal);
			}
			setMultiValues();
		}
	}
	//verifyComboBox();
}

function tbl_onclick()
{
	var row= GetRow(event.srcElement);
	if(!g_bMultipleChoice)
	{
		if(row!=null && GetTable(row)==tbl)
		{
			var cell = row.cells[0];
			var orgVal = selectedAdv.value;
			var curVal = optionItemFromCell(cell);
			if(!compareOption(orgVal,curVal))
			{
				selectedAdv.showTxt= getDisplayText(cell.innerText);
				selectedAdv.value = curVal.value;
				dataOnChange=true;
			}
			tbl.style.display="none";
		}
	}
	else
	{
		//var scrollTop=tbl.parentElement.scrollTop;
		//tbl.focus();
		//tbl.parentElement.scrollTop=scrollTop;
		tbl.setActive();
		
	}	
}

function collectMutlipleValues()
{
	var arr = new Array();
	for(var i=0;i<tbl.rows.length;i++)
	{
		var cell = tbl.rows[i].cells[0];
		if(cell.children[0].checked)
			arr[arr.length] = optionItemFromCell(cell);
	}
	return arr;
}

function optionItemFromCell(cell)
{
	return cell._item;
}

function compareOptionArray(arr1,arr2)
{
	if(arr1==null )
	{
		if(arr2 ==null||arr2.length==0) 
			return true;
		else
			return false;
	}
	else
	{
		if(arr1.length==0 && arr2==null)
			return true;
		if(arr1.length==arr2.length)
		{
			for(var i=0;i<arr1.length;i++)
				for(var j=0;i<arr2.length;j++)
			{
				//if(arr1[i].value!=arr2[i].value)
				//compareOption
				//	return false;
				if(!compareOption(arr1[i],arr2[j]))
					return false;
			}
			return true;
		}
		return false;
	}
}

function setMultiValues()
{
	var values="";
	for (var i=0;i<tbl.rows.length;i++)
	{
		if (tbl.rows[i].cells[0].children[0].checked)
			values+="["+tbl.rows[i].cells[0].innerText+"]";
	}
	selectedAdv.showTxt = getDisplayText(values);
}
/*
function verifyComboBox()
{
	if ( g_bNullable == false )
	{
		if ( tbl.style.display =="none")
		{	
			if ( (g_selectedItems == "")||(g_selectedItems == null) )
			{				
				ErrorHandle("警告", 2, 1, "该项不能为空，请选择", null);
			}
		}
	}
}
*/

function compareOption(opt1,opt2)
{
	if(opt1 == null)
	{
		if(opt2!=null) return false;
		else return true;
	}
	else
	{
		if(opt2==null) return false;
		return (opt1.value == opt2.value);	
	}
}

/************************************************************
 函数名称：getDisplayText
 函数功能：根据界面的长度，判断是否隐藏文本
 输入参数：wholeText -- 完整的字符串
 输出参数：无 
 返 回 值：能正常显示的文本字符串
 函数说明：
 ************************************************************/
function getDisplayText(wholeText)
{	
	try 
	{
		//一个全角(汉字)14个象素,一个半角(英文)7个象素		
		var textWidth = 0;
		var clientWidth = selectedAdv.width;
				
		for (var i=0;i<wholeText.length;i++)
		{
			var charUnit = wholeText.substring(i,i+1);
			
			if (escape(charUnit).length >= 6)  //汉字
			{
				textWidth += 12;
			}
			else  //非汉字  "a[@ #"
			{
				textWidth += 6;
			}			
		}			
		
		if (textWidth > clientWidth-6)
		{		
			var subsText = "";
			
			//最后要加上3个点号，先减去3位
			clientWidth = clientWidth - (6);
			textWidth = 0;						
					
			for (var i=0;i<wholeText.length;i++)
			{
				var charUnit = wholeText.substring(i,i+1);				
				
				if (escape(charUnit).length >= 6)  //汉字
				{
					textWidth += 12;
				}
				else  //非汉字  "a[@ #"
				{
					textWidth += 6;
				}
				
				if (textWidth > clientWidth-6 )
				{
					subsText += "...";
					return subsText;
				}				
				subsText += charUnit;
			}			
		}
		else
		{		
			return wholeText;
		}
	}
	catch (e)		
	{
		//异常，返回原字符串
		return wholeText;
	}
}

function showText(control)
{

	var g_bMultipleChoice=control.multipleChoice;
	var values="";
	var gVal=control.value;
	if (g_bMultipleChoice)
	{
		if (gVal!=null)
		{
			for (var j=0;j<gVal.length;j++)
			{
					values+="["+gVal[j].text+"]";
			}
			control.showTxt = getDisplayText(values);
		}
		else
		{
			control.showTxt ="";
		}
	}
	else
	{
		if (gVal!=null)
		{
			control.showTxt =gVal;
		}
		else
		{
			control.showTxt ="";
		}
	}
}

function initAdv()
{
	selectedAdv=event.srcElement;
	g_bMultipleChoice=selectedAdv.multipleChoice;
	var orgVal = selectedAdv.value;
	var values="";
	if (g_bMultipleChoice)
	{
		if (selectedAdv.value!=null)
		{
			for (var i=0;i<orgVal.length;i++)
			{
					values+="["+orgVal[i].text+"]";
			}
			selectedAdv.showTxt = getDisplayText(values);
		}
		else
		{
			selectedAdv.showTxt ="";
		}
	}
	else
	{
		if (orgVal!=null)
		{
			selectedAdv.showTxt =orgVal;
		}
		else
		{
			selectedAdv.showTxt ="";
		}
	}	
}

function divScroll()
{
	//alert("dddddddd");
	tbl.setActive();
}