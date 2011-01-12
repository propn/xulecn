/* 年的数据 */
function SetYearToSel(oSel){
  for(var i=2004; i<2016; i++){
    oSel.add(new Option(i,i));
  }
  oSel.value = (new Date()).getUTCFullYear();
}

function SetMonthToSel(oSel){
  for(var i=1; i<13; i++){
    oSel.add(new Option(i,i));
  }
  oSel.value = (new Date()).getMonth() + 1;
}

/* 获得月份天数 */
function GetRealDays(year,month){
  var daysInMonth = new Array(31, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
  if (2 == month)
    return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
  else
    return daysInMonth[month];
}

/* 设置id值  */
function HtmlSetData(pNameArr,pDto){
  for(var i=0; i<pNameArr.length; i++)
    document.all(pNameArr[i]).innerText = pDto[pNameArr[i]]||" ";
}
/* 替换html字符 */
function HtmlEncode(s){
}
/* 取消body鼠标等待 */
function SetCursorDefault(){
  document.body.style.cursor = 'default';
}

/* 按钮鼠标等待 */
function CursorWait(ObjP){
 ObjP.style.cursor = "wait";
 ObjP.onmouseup = function(){this.style.cursor='default'};
}

/* 获得元素所在的行 */
function GetRow(elm){
  return (elm)? ((elm.tagName=="TR")? elm : GetRow(elm.parentElement)) : elm;
}
/* 获得元素所在单元格 */
function GetCell(elm){
  return (elm)? ((elm.tagName=="TD")? elm : getCell(elm.parentElement)) : elm;
}

/* 获得绝对座标 */
function GetAbsPosition(obj,offsetObj){
  var _offsetObj=(offsetObj)?offsetObj:document.body;
  var x=obj.offsetLeft;
  var y=obj.offsetTop;
  var tmpObj=obj.offsetParent;
  while ((tmpObj!=_offsetObj) && tmpObj){
    x += tmpObj.offsetLeft-tmpObj.scrollLeft + tmpObj.clientLeft;
    y += tmpObj.offsetTop-tmpObj.scrollTop + tmpObj.clientTop;
    tmpObj=tmpObj.offsetParent;
  }
  return ([x, y]);
}

/* 获得固定长度字符串 */
function getFileNameByByte(str, bytes){
	if(!bytes)bytes =20;
	var preString = str;
	var postfix = "";
	var dotIndex = str.lastIndexOf("\.");
	if(dotIndex > 0){
		preString = str.substring(0, dotIndex);
		postfix = str.substring(dotIndex);
	}
	var len = 0;
	for(var i = 0;i < preString.length; i++){
		if(str.charCodeAt(i) > 255){
			len += 2;
		}
		else{
			len ++;
		}
		if(len > bytes){
			return preString.substring(0,i) + "…" + postfix;
		}
	}
	return str;
}