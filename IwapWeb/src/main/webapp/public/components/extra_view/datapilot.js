var DataPilot = {

	getDataset : function() {
		return this.dataset;
	},
	
	//按钮的点击响应函数
	btn_onclick : function(){
		if (event.srcElement.disabled)
			return;
		var datapiolt=event.srcElement.datapiolt;
		var dataset=Control.getElementDataset(datapiolt);
	
		//如果已经定义了自定义的响应函数，则触发该响应函数。函数的名称命名规则
		//为 pilotid + _onButtonClick()
		var eventName=Document.getElementEventName(datapiolt, "onButtonClick");
		if (Document.isUserEventDefined(eventName)){
				//用户自定义的触发事件如果返回false，表示还要继续调用datapilot组件的默认处理方式，
				//如果返回true，表示事件已经处理完成了，不需要调用datapilot组件的默认功能，函数在
				//这里return，不再往下处理。
				var event_result=Document.fireUserEvent(eventName, [datapiolt, event.srcElement, event.srcElement.buttonType]);
				if (!event_result) return;
		}
		
		var pageSize=datapiolt.getAttribute("pageSize");
	
		switch(event.srcElement.buttonType){//获取事件源的按钮类型，根据不同的按钮类型做不同的处理
			case "movefirst":{//第一个记录
				dataset.moveFirst();
				break;
			}
			case "prevpage":{//前一页
				var pageIndex=(dataset.record)?dataset.record.pageIndex-1:1;//调用dataset的前一页或者第一页
				dataset.moveToPage(pageIndex);
				break;
			}
			case "moveprev":{//前一个记录
				dataset.movePrev();
				break;
			}
			case "movenext":{//下一个记录
				dataset.moveNext();
				break;
			}
			case "nextpage":{//下一页
				var pageIndex=(dataset.record)?dataset.record.pageIndex+1:1;
				dataset.moveToPage(pageIndex);
				break;
			}
			case "movelast":{//最后一个记录
				dataset.moveLast();
				break;
			}
			case "insertrecord":{//插入一个记录
				dataset.insertRecord("before");
				break;
			}
			case "appendrecord":{//增加一个记录
				dataset.insertRecord("end");
				break;
			}
			case "editrecord":{//编辑一个记录
				Dataset.setState(dataset, "modify");
				break;
			}
			case "deleterecord":{
				if (System.isTrue(datapiolt.getAttribute("confirmDelete"))){
						if (confirm(Const.DatasetDeleteRecord)) dataset.deleteRecord();
				}
				else
						dataset.deleteRecord();
				break;
			}
			case "cancelrecord":{
				if (System.isTrue(datapiolt.getAttribute("confirmCancel"))){
						if (confirm(Const.DatasetCancelRecord)) dataset.cancelRecord();
				}
				else
						dataset.cancelRecord();
				break;
			}
			case "postrecord":{
				dataset.postRecord();
				break;
			}
		}
	},
	
	refreshDataPilot : function(dataPilot){
	
		function refreshButton(btn, enable){
				btn.disabled=!enable;
				Button.refreshButtonColor(btn);
		}
	
		var dataset=Control.getElementDataset(dataPilot);
		var row=dataPilot.rows[0];
		for(var i=0; i<row.cells.length; i++){
			var btn=row.cells[i].children[0];
			switch(btn.buttonType){
				case "movefirst":;
				case "moveprev":{
					refreshButton(btn, (dataset && !dataset.isFirst()));
					break;
				}
				case "prevpage":{
					refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex>1));
					break;
				}
				case "movenext":;
				case "movelast":{
					refreshButton(btn, (dataset && !dataset.isLast()));
					break;
				}
				case "nextpage":{
					refreshButton(btn, (dataset && dataset.record && dataset.record.pageIndex<dataset.pageCount));
					break;
				}
				case "insertrecord":;
				case "appendrecord":{
					refreshButton(btn, (dataset && !dataset.readOnly));
					break;
				}
				case "editrecord":{
					refreshButton(btn, (dataset && !(dataset.isFirst() && dataset.isLast()) && !dataset.readOnly));
					break;
				}
				case "deleterecord":{
					refreshButton(btn, (dataset && !(dataset.isFirst() && dataset.isLast()) && !dataset.readOnly));
					break;
				}
				case "cancelrecord":;
				case "postrecord":{
					refreshButton(btn, (dataset && (dataset.state=="insert" || dataset.state=="modify") && !dataset.readOnly));
					break;
				}
			}
	
			Document.fireUserEvent(Document.getElementEventName(dataPilot, "onRefreshButton"), [dataPilot, btn, btn.buttonType]);
		}
	}	
}

function initDataPilot(dataPilot){
	
	dataPilot.extra = "datapilot";
	dataPilot.cellSpacing = "1";
	dataPilot.cellPadding = "0";
	Control.initElementDataset(dataPilot);
	dataPilot.window = window;

	Object.extend(dataPilot, DataPilot);

	var dataset=Control.getElementDataset(dataPilot);
	//设置DataPilot的pageSize属性
	if (!dataPilot.getAttribute("pageSize")){
		if (dataset){
			dataPilot.pageSize=dataset.pageSize;
		}
	}
	
	var pageSize=dataPilot.getAttribute("pageSize");
	for(var i=0; i<dataPilot.tBodies[0].rows.length; i++){
		var row=dataPilot.tBodies[0].rows[i];
		row.removeNode(true);
	}
	
	var buttons_str=System.getValidStr(dataPilot.getAttribute("buttons"));
	//初始化DataPilot的按钮
	if (buttons_str=="" || System.compareText(buttons_str, "default"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast,appendrecord,deleterecord,cancelrecord,postrecord";
	else if (System.compareText(buttons_str, "readonly"))
		buttons_str="movefirst,prevpage,moveprev,movenext,nextpage,movelast";
	buttons_str=buttons_str.toLowerCase();
	var buttons=buttons_str.split(",");

	var row=dataPilot.tBodies[0].insertRow();
	row.align="center";
	for(var i=0; i<buttons.length; i++){
		btn=document.createElement("<input type=button class=button hideFocus=true style=\"height: 22px\">");
		row.insertCell().appendChild(btn);
		btn.style.backgroundImage = "url("+Global.theme_root+"/button.gif)";
		btn.tabIndex=-1;
		btn.onmouseover=Button.onmouseover;
		btn.onmouseout=Button.onmouseout;
		btn.onclick=DataPilot.btn_onclick;//每一个按钮的点击响应事件

		btn.dataset=dataPilot.getAttribute("dataset");
		btn.buttonType=buttons[i];
		btn.datapiolt=dataPilot;

		switch(buttons[i]){
			case "movefirst":{
				btn.style.fontFamily="Webdings";
				btn.value="9";//按钮的标题
				btn.title=Const.DatasetMoveFirst;//按钮的提示信息
				btn.style.width=30;
				break;
			}
			case "prevpage":{
				btn.style.fontFamily="Webdings";
				btn.value="7";
				btn.title=Const.DatasetPrevPage;
				btn.style.width=30;
				break;
			}
			case "moveprev":{
				btn.style.fontFamily="Webdings";
				btn.value="3";
				btn.title=Const.DatasetMovePrev;
				btn.style.width=30;
				break;
			}
			case "movenext":{
				btn.style.fontFamily="Webdings";
				btn.value="4";
				btn.title=Const.DatasetMoveNext;
				btn.style.width=30;
				break;
			}
			case "nextpage":{
				btn.style.fontFamily="Webdings";
				btn.value="8";
				btn.title=Const.DatasetNextPage;
				btn.style.width=30;
				break;
			}
			case "movelast":{
				btn.style.fontFamily="Webdings";
				btn.value=":";
				btn.title=Const.DatasetMoveLast;
				btn.style.width=30;
				break;
			}
			case "insertrecord":{
				btn.value=Const.BtnInsertRecord;
				btn.title=Const.DatasetInsertRecord;
				btn.style.width=45;
				break;
			}
			case "appendrecord":{
				btn.value=Const.BtnAppendRecord;
				btn.title=Const.DatasetAppendRecord;
				btn.style.width=45;
				break;
			}
			case "deleterecord":{
				btn.value=Const.BtnDeleteRecord;
				btn.title=Const.DatasetDeleteRecord;
				btn.style.width=45;
				break;
			}
			case "editrecord":{
				btn.value=Const.BtnEditRecord;
				btn.title=Const.DatasetEditRecord;
				btn.style.width=45;
				break;
			}
			case "cancelrecord":{
				btn.value=Const.BtnCancelRecord;;
				btn.title=Const.DatasetCancelRecord;
				btn.style.width=45;
				break;
			}
			case "postrecord":{
				btn.value=Const.BtnPostRecord;
				btn.title=Const.DatasetPostRecord;
				btn.style.width=45;
				break;
			}
		}
		btn.id=dataPilot.id+"_"+btn.buttonType;	
		btn=null;	
	}

	DataPilot.refreshDataPilot(dataPilot);
}

function setDataPilotButtons(dataPilot, buttons){
	dataPilot.buttons=buttons;
	initDataPilot(dataPilot);
}