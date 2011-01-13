

//获得TABLE的头HTML（get the table head）
function GetTableHeadHtml(aTab){
  var sHtml = aTab.outerHTML;
  var indexOfBody   = sHtml.indexOf("<TBODY");
  if(indexOfBody!=-1){
     return(sHtml.substring(0,indexOfBody));
  }
  return(null);
}

//获得TABLE的记录HTML
function GetTableBodyHtml(aTab){
  var sHtml = aTab.outerHTML;
  var indexOfBody   = sHtml.indexOf("<TBODY>");
  //可能会出现多个</TBODY>，取最后一个
  var indexOfBodyEnd   = sHtml.lastIndexOf("</TBODY>");
  if(indexOfBody!=-1){
     return(sHtml.substring(indexOfBody+7,indexOfBodyEnd));
  }
  return(null);
}

//重新画TABLE
function RepaintTable(aTab,aRecordHtml){
  var sHtml= GetTableHeadHtml(aTab);
  sHtml =sHtml+GetTableBodyHtml(aTab)+aRecordHtml;
  //TABLE的结束符
  sHtml += " </table>";
  //重新设置TABLE
  aTab.outerHTML = sHtml;
}

//重新画TABLE
function RepaintTableByBody(aTab,aBodyHtml){
  var sHtml= GetTableHeadHtml(aTab);
  sHtml =sHtml+aBodyHtml;
  //TABLE的结束符
  sHtml += " </table>";
  //重新设置TABLE
  aTab.outerHTML = sHtml;
}

//删除记录
function d_DelRecord(aTabName,aRecordId){
    if (aTabName==null) return;
    if (aRecordId==null) return;
	var TabHtml=document.all(aTabName).outerHTML;
	var RecHtml=document.all(aRecordId).outerHTML;
    var indexOfRecordId = TabHtml.indexOf("id="+aRecordId);
    var SubTabHtml = TabHtml.substring(0,indexOfRecordId);
    var indexOfRec = SubTabHtml.lastIndexOf("<TR");
	//var indexOfRec=TabHtml.indexOf("<TR id="+aRecordId);
	var RecLength=RecHtml.length;
	var headPart=TabHtml.substring(0,indexOfRec);
	var endPart=TabHtml.substring(indexOfRec+RecLength,TabHtml.length);
	var newTabHtml=headPart+endPart;
	document.all(aTabName).outerHTML=newTabHtml;
}

//动态生成多条记录
function AddMultRecordWithoutId(aTabName,aRowHtml,aRecNum){
  if(!aRowHtml||!aTabName) return;
  var tab=document.all(aTabName);
  var newRowHtml="";
  for(var i=0;i<aRecNum;i++){
        newRowHtml += aRowHtml;
  }
  RepaintTableByBody(tab,newRowHtml);
}


//动态生成多条记录(保持原有一条记录再多加N条记录)
//注意：由于ID下标是从1开始的，所以要注意原来的一条记录是否ID下标为1--aRecNum
//适合有一条隐藏记录ID为0，再多加N条时的情况
function AddMultRecord(aTabName,aRowId,aPreName,aRowHtml,aRecNum){
  if(!aRowHtml||!aTabName||!aRowId||!aPreName) return;
  var tab=document.all(aTabName);
  var newRowHtml=aRowHtml; //保持原有一条记录
  for(var i=1;i<=aRecNum;i++){
        var reStr="id="+aRowId;
       // var reExp=/id=d_RowName/g;
        var replaceHtml= "id='"+aPreName+i+"'";
        newRowHtml=newRowHtml+aRowHtml.replace(reStr,replaceHtml);
  }
  RepaintTableByBody(tab,newRowHtml);
}

//动态生成多条记录(保持原有记录再多加N条记录)  aBeginRecIndex:记录下标开始号；aRecNum：新生成的记录数
function AddMultRecordWithOldBody(aTabName,aRowId,aPreName,aRowHtml,aRecNum,aBeginRecIndex){
  if(!aRowHtml||!aTabName||!aRowId||!aPreName) return;
  var tab=document.all(aTabName);
  var newRowHtml=GetTableBodyHtml(tab);//保持原有记录
  for(var i=aBeginRecIndex;i<aRecNum+aBeginRecIndex;i++){
        var reStr="id="+aRowId;
       // var reExp=/id=d_RowName/g;
        var replaceHtml= "id='"+aPreName+i+"'";
        newRowHtml=newRowHtml+aRowHtml.replace(reStr,replaceHtml);

  }
  RepaintTableByBody(tab,newRowHtml);
}


//动态生成多条记录(不保持原有记录，多加N条记录)  aBeginRecIndex:记录下标开始号；aRecNum：新生成的记录数
function AddMultRecordWithoutOldBody(aTabName,aRowId,aPreName,aRowHtml,aRecNum,aBeginRecIndex){
  if(!aRowHtml||!aTabName||!aRowId||!aPreName) return;
  var tab=document.all(aTabName);
  var newRowHtml="";//不保持原有记录
  for(var i=aBeginRecIndex;i<aRecNum+aBeginRecIndex;i++){
        var reStr="id="+aRowId;
       // var reExp=/id=d_RowName/g;
        var replaceHtml= "id='"+aPreName+i+"'";
        newRowHtml=newRowHtml+aRowHtml.replace(reStr,replaceHtml);
  }
  RepaintTableByBody(tab,newRowHtml);
}

  // 设置新的选定行的状态
function SetSelectRowStatus(oRow){
  if(!oRow) return;
  oRow.style.backgroundColor = '#EBF8FF';
  oRow.cells(0).innerHTML = "<img src='../../img/top_v1.gif' width='6' height='7'>";
}

  // 恢复行的状态
function ResetSelectRowStatus(oRow){
  if(!oRow) return;
  oRow.style.backgroundColor="";
  oRow.cells(0).innerHTML = "  ";
}


//隐藏某元素
function SetElementHidden(aId){
   if(document.all(aId))
     document.all(aId).style.display="none";
}

//解释数据串  约定：记录间以“|”结束，字段间以“^”结束
function ParseDataArray(aDataStr){
    //拆aDataStr
    var dataArray = new Array();
    if(aDataStr == "0" ) return dataArray; //无记录
    var Records = aDataStr.split("|");
    //由于aDataStr以"|"结束，拆分时最后一个为空，不应该取用，故Records.length-1
    for(var i=0; i< Records.length-1; i++){
        dataArray[i] = Records[i].split("^");
    }
    return dataArray;
}
