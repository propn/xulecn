

//���TABLE��ͷHTML��get the table head��
function GetTableHeadHtml(aTab){
  var sHtml = aTab.outerHTML;
  var indexOfBody   = sHtml.indexOf("<TBODY");
  if(indexOfBody!=-1){
     return(sHtml.substring(0,indexOfBody));
  }
  return(null);
}

//���TABLE�ļ�¼HTML
function GetTableBodyHtml(aTab){
  var sHtml = aTab.outerHTML;
  var indexOfBody   = sHtml.indexOf("<TBODY>");
  //���ܻ���ֶ��</TBODY>��ȡ���һ��
  var indexOfBodyEnd   = sHtml.lastIndexOf("</TBODY>");
  if(indexOfBody!=-1){
     return(sHtml.substring(indexOfBody+7,indexOfBodyEnd));
  }
  return(null);
}

//���»�TABLE
function RepaintTable(aTab,aRecordHtml){
  var sHtml= GetTableHeadHtml(aTab);
  sHtml =sHtml+GetTableBodyHtml(aTab)+aRecordHtml;
  //TABLE�Ľ�����
  sHtml += " </table>";
  //��������TABLE
  aTab.outerHTML = sHtml;
}

//���»�TABLE
function RepaintTableByBody(aTab,aBodyHtml){
  var sHtml= GetTableHeadHtml(aTab);
  sHtml =sHtml+aBodyHtml;
  //TABLE�Ľ�����
  sHtml += " </table>";
  //��������TABLE
  aTab.outerHTML = sHtml;
}

//ɾ����¼
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

//��̬���ɶ�����¼
function AddMultRecordWithoutId(aTabName,aRowHtml,aRecNum){
  if(!aRowHtml||!aTabName) return;
  var tab=document.all(aTabName);
  var newRowHtml="";
  for(var i=0;i<aRecNum;i++){
        newRowHtml += aRowHtml;
  }
  RepaintTableByBody(tab,newRowHtml);
}


//��̬���ɶ�����¼(����ԭ��һ����¼�ٶ��N����¼)
//ע�⣺����ID�±��Ǵ�1��ʼ�ģ�����Ҫע��ԭ����һ����¼�Ƿ�ID�±�Ϊ1--aRecNum
//�ʺ���һ�����ؼ�¼IDΪ0���ٶ��N��ʱ�����
function AddMultRecord(aTabName,aRowId,aPreName,aRowHtml,aRecNum){
  if(!aRowHtml||!aTabName||!aRowId||!aPreName) return;
  var tab=document.all(aTabName);
  var newRowHtml=aRowHtml; //����ԭ��һ����¼
  for(var i=1;i<=aRecNum;i++){
        var reStr="id="+aRowId;
       // var reExp=/id=d_RowName/g;
        var replaceHtml= "id='"+aPreName+i+"'";
        newRowHtml=newRowHtml+aRowHtml.replace(reStr,replaceHtml);
  }
  RepaintTableByBody(tab,newRowHtml);
}

//��̬���ɶ�����¼(����ԭ�м�¼�ٶ��N����¼)  aBeginRecIndex:��¼�±꿪ʼ�ţ�aRecNum�������ɵļ�¼��
function AddMultRecordWithOldBody(aTabName,aRowId,aPreName,aRowHtml,aRecNum,aBeginRecIndex){
  if(!aRowHtml||!aTabName||!aRowId||!aPreName) return;
  var tab=document.all(aTabName);
  var newRowHtml=GetTableBodyHtml(tab);//����ԭ�м�¼
  for(var i=aBeginRecIndex;i<aRecNum+aBeginRecIndex;i++){
        var reStr="id="+aRowId;
       // var reExp=/id=d_RowName/g;
        var replaceHtml= "id='"+aPreName+i+"'";
        newRowHtml=newRowHtml+aRowHtml.replace(reStr,replaceHtml);

  }
  RepaintTableByBody(tab,newRowHtml);
}


//��̬���ɶ�����¼(������ԭ�м�¼�����N����¼)  aBeginRecIndex:��¼�±꿪ʼ�ţ�aRecNum�������ɵļ�¼��
function AddMultRecordWithoutOldBody(aTabName,aRowId,aPreName,aRowHtml,aRecNum,aBeginRecIndex){
  if(!aRowHtml||!aTabName||!aRowId||!aPreName) return;
  var tab=document.all(aTabName);
  var newRowHtml="";//������ԭ�м�¼
  for(var i=aBeginRecIndex;i<aRecNum+aBeginRecIndex;i++){
        var reStr="id="+aRowId;
       // var reExp=/id=d_RowName/g;
        var replaceHtml= "id='"+aPreName+i+"'";
        newRowHtml=newRowHtml+aRowHtml.replace(reStr,replaceHtml);
  }
  RepaintTableByBody(tab,newRowHtml);
}

  // �����µ�ѡ���е�״̬
function SetSelectRowStatus(oRow){
  if(!oRow) return;
  oRow.style.backgroundColor = '#EBF8FF';
  oRow.cells(0).innerHTML = "<img src='../../img/top_v1.gif' width='6' height='7'>";
}

  // �ָ��е�״̬
function ResetSelectRowStatus(oRow){
  if(!oRow) return;
  oRow.style.backgroundColor="";
  oRow.cells(0).innerHTML = "  ";
}


//����ĳԪ��
function SetElementHidden(aId){
   if(document.all(aId))
     document.all(aId).style.display="none";
}

//�������ݴ�  Լ������¼���ԡ�|���������ֶμ��ԡ�^������
function ParseDataArray(aDataStr){
    //��aDataStr
    var dataArray = new Array();
    if(aDataStr == "0" ) return dataArray; //�޼�¼
    var Records = aDataStr.split("|");
    //����aDataStr��"|"���������ʱ���һ��Ϊ�գ���Ӧ��ȡ�ã���Records.length-1
    for(var i=0; i< Records.length-1; i++){
        dataArray[i] = Records[i].split("^");
    }
    return dataArray;
}
