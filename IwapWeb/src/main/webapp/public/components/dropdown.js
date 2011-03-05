/*dropdown的对象数组*/
var _array_dropdown = [];

var _calendarControl=null;
var _tmp_dataset_date=null;
//将一个html元素生成一个dropdown
function makeDropDown(dropdown){
  if(!dropdown.showColumnHeader)
    dropdown.showColumnHeader = "false";
  if(!dropdown.extra)
    dropdown.extra = "dropdown";
  if(!dropdown.type)
    dropdown.type = "list";
  if(!dropdown.mapValue)
    dropdown.mapValue = "true";
  if(!dropdown.staticDataSource)
    dropdown.staticDataSource = "true";
  if(!dropdown.fixed)
    dropdown.fixed = "true";
  if(!dropdown.maxHeight)
    dropdown.maxHeight = "220";   
  if(!dropdown.cachable)
    dropdown.cachable = "true";  
  if(!dropdown.attrParamValue)
    dropdown.attrParamValue = "";
  dropdown.clearCache=dropdown_clearCache;
	//_array_dropdown[_array_dropdown.length]=dropdown;
	initDropDown(dropdown);	
  if (!dropdown.className) dropdown.className=dropdown.extra;
	dropdown.window=window; 	
  LocalAction.saveDropdownData(dropdown.id);
  
}
function createDropDown(id) {
	var dropdown={};
	dropdown.id=id;
	dropdown.clearCache=dropdown_clearCache;
	dropdown.fixed="true";
	return dropdown;
}
//初始化设置一个dropdown组件。
function initDropDown(dropdown){
	_array_dropdown[_array_dropdown.length]=dropdown;
}

function dropdown_parameters(){
	return this._parameters;
}

function dropdown_clearCache(){
	var dropdown=this;
	dropdown.dropdownbox=null;
}

/**
*	Dropdown 下拉显示组件
*/
var Dropdown = {

  parentwindow : null,
  parentbox : null,
  box : null,
  table : null,
  frame : null,
  window : null,
  dataset : null,
  date_box : null,
  
	initDropDownBox : function(dropDownType){
		try{
			Document.isDropDownPage=true;
			if (typeof(_dropdown_succeed)!="undefined" && !System.isTrue(_dropdown_succeed)){
				throw System.getDecodeStr(_dropdown_error);
			}
			else{

				Dropdown._initDropDownBox(dropDownType);		
				
			}
			return true;
		}
		catch(e){
			System.processException(e);
			Dropdown.hideDropDown();
			//StatusLabel.hide(window.parent);
			return false;
		}
	},
	
	_initDropDownBox : function(dropDownType){
		Document.loading=true;
	
		switch (dropDownType){		
	
			case "date":{
				Dropdown.parentwindow=window;
				Dropdown.parentbox=Dropdown.parentwindow.Dropdown.box;
				Dropdown.parentwindow.Dropdown.window=window;
				Dropdown.sizeDropDownBox();
				break;
			}
	
			default:{
				Dropdown.parentwindow=window;
				Dropdown.parentbox=Dropdown.parentwindow.Dropdown.box;
				Dropdown.parentwindow.Dropdown.window=window;
				Dropdown.dataset=Control.getElementDataset(Dropdown.table);
				Dropdown.sizeDropDownBox();

				break;
			}
		}
		Dropdown.parentbox.prepared=true;
		var editor=Dropdown.parentbox.editor;
		if (editor) Dropdown.locate();
		Document.loading=false;
	},
	
	sizeDropDownBox : function(){

		function _sizeDropDownBox(new_width, new_height){
			with (Dropdown.box){
				var editor=Dropdown.box.editor;
				var dropdown=Dropdown.box.dropDown;
				var maxHeight=parseInt(dropdown.maxHeight);
				if (isNaN(maxHeight) || maxHeight<20) maxHeight=220;
	
				var pos=Document.getAbsPosition(editor, document.body);
				var _posLeft=pos[0];
				var _posTop=pos[1]+editor.offsetHeight+1;
	      
				if (new_height>maxHeight &&
					!(dropdown.type=="dynamic" && System.getInt(dropdown.pageSize)>0)){
					new_height=maxHeight;
					if(!getAttribute("add16px")){
						new_width+=16;
						setAttribute("add16px", true);
					}
					style.overflowY="scroll";
				}
				else{
					style.overflowY="hidden";
				}
				var document_width=document.body.clientWidth;// + document.body.scrollLeft;
				var document_height=document.body.clientHeight;// + document.body.scrollTop;
	
				if (_posLeft+new_width>document_width && document_width>new_width) _posLeft=document_width-new_width;
				if (_posTop+new_height>document_height && pos[1]>new_height) _posTop=pos[1]-new_height-5;
				style.posLeft=_posLeft;
				style.posTop=_posTop;
				style.posHeight=new_height+4;
				if (Math.abs(new_width+4-style.posWidth)>4) style.posWidth=new_width+4;
				style.borderWidth="0px";
			}
		}

		if (!Dropdown.isDropdownBoxVisible()) return;
		
		var _width, _height;
		switch (Dropdown.box.dropDown.type){	
			case "date":{
				_width=CalendarTable.offsetWidth;
				_height=CalendarTable.offsetHeight;
				break;
			}

			default:{
				_width=Dropdown.table.offsetWidth;
				_height=Dropdown.table.offsetHeight;
				break;
			}
		}
		_sizeDropDownBox(_width, _height);
		
	},
	
	canDropDown : function(editor){
		var field=Control.getElementField(editor);
		var hasDropDown = ((field && field.dropDown) || editor.getAttribute("dropDown"));
		return (!editor.isReadOnly() && hasDropDown);
	},
	
	getDropDownBox : function(dropdown){
		var box=null;
		if (System.isTrue(dropdown.cachable)){
			box=dropdown.dropdownbox;
		}
	
		if (!box){
			box=document.createElement("<DIV class=\"dropdown_frame\" style=\"overflow-X: hidden; position: absolute; display: none; z-index: 10000\"></DIV>");
			document.body.appendChild(box);
			document.close();
			box.dropDown = dropdown;
			if (System.isTrue(dropdown.cachable)){
			  dropdown.dropdownbox=box;
			}
		//if(dropdown.id == 'countyType' || dropdown.id == 'SEX' ) alert(dropdown.id+'<<<'+dropdown.cachable +'>>>'+box)
		}	
		Dropdown.box=box;
	},
	
	getDropDownBtn : function(){
		if  (typeof(_dropdown_btn)=="undefined"){
			obj=document.createElement("<INPUT class=\"dropdown_button\" id=_dropdown_btn type=button tabindex=-1 value=6 hidefocus=true"+
				" style=\"position: absolute; border-left: 1px solid silver; display: none; z-index: 9999\""+
				" LANGUAGE=javascript onmousedown=\"return Dropdown.btn_onmousedown(this)\" onfocus=\"return Dropdown.btn_onfocus(this)\">");
			obj.style.background = "url("+Global.theme_root+"/dropdown_button.gif)";
			document.body.appendChild(obj);
			return obj
		}
		else{
			return _dropdown_btn;
		}
		
	},
	//显示下拉内容。
	showDropDownBox : function(_editor){			
		if (!Dropdown.canDropDown(_editor)) return;
		if (!Dropdown.isDropdownBoxVisible()){
			var dropDownId=_editor.getAttribute("dropDown");
			if (!dropDownId) {
				var field=Control.getElementField(_editor);
				if (field) dropDownId=field.dropDown;
			}

			eval("var dropdown=" + dropDownId);
			
			if (dropdown.filterOnOpen && dropdown.filterParameter) {
				dropdown.parameters().setValue(dropdown.filterParameter, _editor.value);
			}

			Dropdown.getDropDownBox(dropdown);
			Dropdown.box.editor=_editor;
			Dropdown.box.prepared=false;

			var dataset=Control.getElementDataset(_editor);
			if (dataset){
				if (!dataset.record) dataset.insertRecord();
			}

			with (Dropdown.box){
				style.overflowY="hidden";

				style.display = "";

				if (!Dropdown.box.cachable||dropdown.type=="date"){
					
					switch (dropdown.type){
		
						case "date":{							    
						  	makeCalendar();
						  	Calendar.old_control = _editor ;
						  	createCalendar(Dropdown.box,Dropdown.box.editor.dataType,Dropdown.box.editor.dataType2); //modify by wq 2007-11-01
							Dropdown._initDropDownBox(dropdown.type);
							Dropdown.box.onkeydown=Calendar.onkeydown;
							break;
						}

						default:{
							
							style.width=_editor.offsetWidth;
							
							
							Dropdown.createListTable(Dropdown.box);
							Dropdown.table.onkeydown=Dropdown.dropdown_onkeydown;
							
						
							Dropdown.table.parentElement.setAttribute("defaultWidth",style.width);
							
							//Dropdown.table.onmouseover=Dropdown.dropdown_onmouseover;

							var _dataset;
							if (dropdown.type=="list"){
								//test
								//alert("showDropDownBox Dropdown.getDropDownItems");
								_dataset=Dropdown.getDropDownItems(dropdown, _editor);
								if (!dropdown.visibleFields){
									if (System.isTrue(dropdown.mapValue))
										dropdown.visibleFields="label";
									else
										dropdown.visibleFields="value";
								}
							}
							else{
								_dataset=dropdown.dataset;
								if (typeof(_dataset)=="string") _dataset=Dataset.getDatasetByID(_dataset);
								
								if(_dataset&&dropdown.blankValue=="true"){
									_dataset.insertRecord("begin");
									for(var i = 0;i<_dataset.fields.fieldCount;i++){
										var fieldName=_dataset.getField(i).name;
										var field=_dataset.getField(fieldName);
										if (field) {
											if(dropdown.visibleFields==fieldName)
												_dataset.setValue(fieldName," ");
											else
												_dataset.setValue(fieldName,"");
										}
									}
								}
							}

							if (_dataset){
							    
								Dataset.setElementDataset(Dropdown.table, _dataset);
								Dropdown.table.fields=dropdown.visibleFields;
								Dropdown.table.highlightSelection=true;
								Dropdown.table.showHeader=dropdown.showColumnHeader;

								makeDataTable(Dropdown.table);
								Datatable.refreshTableData(Dropdown.table);

							}
							
							Dropdown._initDropDownBox(dropdown.type);
							break;
						}
					}
				}
				else{
					

						for (var i=0; i<Dropdown.box.children.length; i++){
							var obj=Dropdown.box.children[i];
							obj.style.display = "";
							if (System.compareText(obj.getAttribute("extra"), "datatable")){									
								if (obj.needRefresh) {
									obj.refreshData();
								}			
							}
						}
						Dropdown.table=dropdown.dropdown_table;
					
						if(Dropdown.interceptPx(Dropdown.table.parentElement.style.width)<Dropdown.interceptPx(Dropdown.table.parentElement.getAttribute("defaultWidth"))){
							Dropdown.table.parentElement.style.width = Dropdown.table.parentElement.getAttribute("defaultWidth");
							
							
						}
						
						Dropdown._initDropDownBox(dropdown.type);
				
	
				}
				
			}
			_editor.dropDownVisible=true;
			if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="5";
		}
			

	},
	interceptPx:function(str){
		if(str && str.indexOf("px")>-1){
			
			str  =str.substring(0,str.length-2);
		}
		
		return new Number(str,10);
		
	},
	//隐藏下拉内容。
	hideDropDownBox : function(){
		if (!Dropdown.box) return;
		if (Dropdown.isDropdownBoxVisible()){
			Document.skip_activeChanged=true;
			var editor=Dropdown.box.editor;
			var dropdown=Dropdown.box.dropDown;
			if (Dropdown.box.prepared && System.isTrue(dropdown.cachable)){
				dropdown.dropdown_box=Dropdown.box;
				Dropdown.box.cachable=true;
				switch (dropdown.type){
					case "list":;
					case "dataset":{
						dropdown.dropdown_table=Dropdown.table;
						break;
					}
				}
	
				for (var i=0; i<Dropdown.box.children.length; i++){
					Dropdown.box.children[i].style.display = "none";
				}
				Dropdown.box.style.display = "none";
				Dropdown.window=null;
			}
			else{
				Dropdown.box.editor=null;
				switch (Dropdown.box.dropDown.type){
					case "list":
					case "dataset":{
						Dataset.setElementDataset(Dropdown.table, null);
						break;
					}
				}
				Dropdown.window=null;
	
				for (var i=0; i<Dropdown.box.children.length; i++){
					Dropdown.box.children[i].style.display="none"
				}
				Dropdown.box.style.display="none";
				Dropdown.box.removeNode(true);
				Dropdown.box=null;
			}
	
			editor.dropDownVisible=false;
			if  (typeof(_dropdown_btn)!="undefined") _dropdown_btn.value="6";
		}
	},
	//下拉内容是否可见
	isDropdownBoxVisible : function(){
	  if (typeof(Dropdown.box)!="undefined" && Dropdown.box)
	    return (Dropdown.box.style.display=="")
	  else
	    return false;
	},	
	
	isDropDownBtnVisible : function(){
		if  (typeof(_dropdown_btn)!="undefined")
			return (_dropdown_btn.style.display=="")
		else
			return false;
	},
	
	sizeDropDownBtn : function(_editor){
		if (!Dropdown.isDropDownBtnVisible()) return;
		with (_dropdown_btn){
			var pos=Document.getAbsPosition(_editor);
			if(_editor.offsetHeight<2){
			 style.height=0;
			}else{
			style.height=_editor.offsetHeight-2;}
			style.width=16;
			style.posLeft=pos[0]+_editor.offsetWidth-offsetWidth-1;
			style.posTop=pos[1]+1;
		}
	},
	//显示下拉按钮。
	showDropDownBtn : function(_editor){
		if (!Dropdown.canDropDown(_editor)) return;
		Dropdown.getDropDownBtn();
		if (typeof(_dropdown_btn)=="undefined") return;

		with (_dropdown_btn){
			if (!Dropdown.isDropDownBtnVisible()){
				setAttribute("editor", _editor);
				style.display="";
				
				Dropdown.sizeDropDownBtn(_editor);
	
				//var oldWidth=_editor.offsetWidth;
				//_editor.style.borderRightWidth=18;
				//_editor.style.width=oldWidth;
				
			}
		}
	},
	//隐藏下拉按钮。
	hideDropDownBtn : function(){
		if  (typeof(_dropdown_btn)=="undefined") return;
	
		if (Dropdown.isDropDownBtnVisible()){
			var editor=_dropdown_btn.editor;
			
			/*if(editor){
				var oldWidth=editor.offsetWidth;
				editor.style.borderRightWidth=1;
				editor.style.width=oldWidth;
			}*/
			_dropdown_btn.style.display="none";
			_dropdown_btn.editor=null;
		}
	},
	
	btn_onmousedown : function(button){
		var obj=button.editor;
		if (!Dropdown.canDropDown(obj)) return;
		if (!Dropdown.isDropdownBoxVisible()){
			if (obj) Dropdown.showDropDownBox(obj);
		}
		else
			Dropdown.hideDropDownBox();
	},
	
	btn_onmousedown2 : function(editor){
	    if (!Dropdown.canDropDown(editor)) return;
		if (!Dropdown.isDropdownBoxVisible()){
			if (editor) Dropdown.showDropDownBox(editor);
		}
		else
			Dropdown.hideDropDownBox();
	},	
	
	btn_onfocus : function(button){
		var obj=button.editor;
		if (obj) obj.focus();
	},
	
	createListTable : function(parent_element){
		Dropdown.table=document.createElement("<table extra=datatable isDropDownTable=true readOnly=true width=100% "+
			" cellspacing=0 cellpadding=2 rules=none></table>");
	
		if (parent_element)
			parent_element.appendChild(Dropdown.table);
		else
			document.body.appendChild(Dropdown.table);
	},
	
	locate : function(){
		var editor=Dropdown.parentbox.editor;
		var dropdown=Dropdown.parentbox.dropDown;
		switch (dropdown.type){
			case "date":{
				var _date=new Date(editor.value);
				if (!isNaN(_date)) setCalendarDate(_date);
				break;
			}
			default:{
				if (Dropdown.dataset){
					var fieldName;
	
					if (dropdown.type=="list"){
						fieldName=(System.isTrue(dropdown.mapValue))?"label":"value";
					}
					else{
						fieldName=dropdown.field;
						if (!fieldName) fieldName=editor.getAttribute("field");
					}
	
					var value=editor.value;
					var record=Dropdown.dataset.locate(fieldName, value);
					if (record) Dropdown.dataset.setRecord(record);
				}
				break;
			}
		}
	},
	
	hideDropDown : function() {
		var editor=Dropdown.parentbox.editor;
		Dropdown.parentwindow.Dropdown.hideDropDownBox();
		if(editor){
		  	if(editor.style.display=="none")
		  		editor.style.display="";
		  	editor.focus();
		}
	},
	
	standard_keyDown : function(keycode){
		switch(keycode){
			//PageUp
			case 33:{
				if (Dropdown.dataset){
					var pageIndex=(Dropdown.dataset.record)?Dropdown.dataset.record.pageIndex-1:1;
					Dropdown.dataset.moveToPage(pageIndex);
				}
				break;
			}
			//PageDown
			case 34:{
				if (Dropdown.dataset){
					var pageIndex=(Dropdown.dataset.record)?Dropdown.dataset.record.pageIndex+1:1;
					Dropdown.dataset.moveToPage(pageIndex);
				}
				break;
			}
			//Up
			case 38:{
				if (Dropdown.dataset){
					Dropdown.dataset.movePrev();
				}
				break;
			}
			//Down
			case 40:{
				if (Dropdown.dataset){
					Dropdown.dataset.moveNext();
				}
				break;
			}
		}
	},
	processDropDownMouseOver : function(){
	  Dropdown.choose();
	},
	processDropDownKeyDown : function(keycode) {
		switch(keycode){
			//Enter
			case 13:{
				Dropdown.selected();
				break;
			}
			//ESC
			case 27:{
				Dropdown.hideDropDown();
				break;
			}
			//F2
			case 113:{
				Dropdown.hideDropDown();
				break;
			}
			//F7
			case 118:{
				Dropdown.hideDropDown();
				break;
			}
			default:{
				switch (Dropdown.parentbox.dropDown.type){
					case "list":
					case "dataset":{
						Dropdown.standard_keyDown(keycode);
						break;
					}
					case "date":{
						Calendar.onkeydown();
						break;
					}
					default:{
						if (typeof(Dropdown.dropDown_onKeyDown)!="undefined") Dropdown.dropDown_onKeyDown(keycode);
						break;
					}
				}
			}
		}
	},
	
	selected : function(value){
		var record;
		switch (Dropdown.parentbox.dropDown.type){
			case "list":
			case "dataset":{  
				if (Dropdown.dataset) record=Dropdown.dataset.record;
				break;
			}
			case "date":{
				_tmp_dataset_date=createDataset({'id':"_tmp_dataset_date"});
				_tmp_dataset_date.addField({'fn':"value"});
				//initDataset(_tmp_dataset_date);
				fillDataset( _tmp_dataset_date);
				_tmp_dataset_date.insertRecord();
				if(value==""){
					_tmp_dataset_date.setValue("value", value);
				}
				else{
//					_tmp_dataset_date.setValue("value", new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day));
_tmp_dataset_date.setValue("value", new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day,_calendarControl.hour,_calendarControl.minute,_calendarControl.second));
				}
				//_tmp_dataset_date.postRecord();
				record=_tmp_dataset_date.record;
				break;
			}
		}
	
		if (record){
			Dropdown.parentwindow.Editor.processDropDownSelected(Dropdown.parentbox.editor, record, false);
			Dropdown.hideDropDown();
		}
		if (_tmp_dataset_date) Dataset.freeDataset(_tmp_dataset_date);

	},
	choose : function(){
		
		var record;
		switch (Dropdown.parentbox.dropDown.type){
			case "list":
			case "dataset":{  
				if (Dropdown.dataset) record=Dropdown.dataset.record;
				break;
			}
			case "date":{
				_tmp_dataset_date=createDataset({'id':"_tmp_dataset_date"});
				_tmp_dataset_date.addField({'fn':"value"});
				//initDataset(_tmp_dataset_date);
				fillDataset( _tmp_dataset_date);
				_tmp_dataset_date.insertRecord();
				_tmp_dataset_date.setValue("value", new Date(_calendarControl.year, _calendarControl.month, _calendarControl.day));
				//_tmp_dataset_date.postRecord();
				record=_tmp_dataset_date.record;
				break;
			}
		}
	
		if (record){
			Dropdown.parentwindow.Editor.processDropDownSelected(Dropdown.parentbox.editor, record, false);
			//Dropdown.hideDropDown();
		}
		if (_tmp_dataset_date) Dataset.freeDataset(_tmp_dataset_date);
	},	
	
	//keydown的事件响应方法。
	dropdown_onkeydown : function(){
		Dropdown.processDropDownKeyDown(event.keyCode);
	},
	
	//mouseover的事件响应方法。
	dropdown_onmouseover : function(){
		Dropdown.processDropDownMouseOver();
	},	
	
	//click的事件响应方法。
	dropdown_onclick : function(){
		Dropdown.selected();
	},
	
	//获得list类型dropdown的dataset
	getDropDownItems : function(dropdown, _editor){
        //var startDate = new Date();
		var items=dropdown._items;
		
		//数据上级结点代码
		var parentValueId = dropdown.getAttribute("parentValueId"); 
		var attrParamValue = dropdown.attrParamValue;
		var configParams = dropdown.getAttribute("configParams");
		
		if("true" != dropdown.dataloaded ) {
			
			if(items)
				items.prepared = false;

			if(!items){
				
				if("USER_DEFINE" == dropdown.dataloaded){
					if(configParams == null || typeof(configParams) == "undefined") return items;
					
					items=createDataset({'id':dropdown.id});
					items.async = false;
					items.addField({'fn':"value"});
					items.addField({'fn':"valueId"});
					items.addField({'fn':"label"});
					items.xmlFormat="items";//标注生成的xml类型。					
					items._queryType = "vo";  //获取数据的方式 vo	
					items.loadDataAction = configParams.loadDataAction;
					items.loadDataActionMethod = configParams.loadDataActionMethod;
					if(configParams.parameters.length>0){
						for(var i=0;i<configParams.parameters.length;i++){
							items._parameters._addParameter(configParams.parameters[i]);
							items._parameters.setValue(configParams.parameters[i],configParams.paraValue[i]);  
						}
					}
					fillDataset(items);		
					dropdown._items=items;
					dropdown.dataloaded = "true";
					return items;
				}
				items=createDataset({'id':dropdown.id});
				//true 异步 false 同步				
				items.async = false;
				items.addField({'fn':"value"});
				items.addField({'fn':"valueId"});
				items.addField({'fn':"label"});	
				items.xmlFormat="items";//标注生成的xml类型。					
				items._queryType = "vo";  //获取数据的方式 vo						
			
				items.loadDataAction = "StaticAttrService"; 
				// 数据字典名称，固定第一个参数。
				items._parameters._addParameter("name"); 
				
				if(dropdown.attrCode==undefined){
					items._parameters.setValue("name","");  
				}else{
					items._parameters.setValue("name",dropdown.attrCode);  
				}	
								
				if(parentValueId != null){
					//数据上级结点代码
					items.loadDataActionMethod = "getSubStaticAttr";
					items._parameters._addParameter("parentValueId"); 
					items._parameters.setValue("parentValueId", parentValueId);
				}else if (attrParamValue != null && attrParamValue != ""){
					//过滤条件
					items.loadDataActionMethod = "getFilteredStaticAttr";
					items._parameters._addParameter("attrParamValue"); 
					items._parameters.setValue("attrParamValue", attrParamValue);
				}else if(configParams != null && configParams != "") {
					items.loadDataActionMethod = "loadDataByConfigParams";					
					items._parameters._addParameter("configParams"); 
					items._parameters.setValue("configParams", configParams);
				}else {
					items.loadDataActionMethod = "getStaticAttr";
				}
			
			}else{
			
				//改变getSubStaticAttr、parentValueId的值。
				if(parentValueId != null && parentValueId != ""){ 
					if(!items._parameters.getValue("parentValueId"))
					{							
						items.loadDataActionMethod = "getSubStaticAttr";
						items._parameters._addParameter("parentValueId");
					}
					items._parameters.setValue("parentValueId", parentValueId);		
				}
				
				//过滤条件
				if (attrParamValue != null && attrParamValue != ""){
					if(!items._parameters.getValue("attrParamValue"))
					{
						items.loadDataActionMethod = "getFilteredStaticAttr";
						items._parameters._addParameter("attrParamValue");
					}
					items._parameters.setValue("attrParamValue", attrParamValue);
				}
				
				//自定义参数
				if(configParams != null && configParams != "") {
					items.loadDataActionMethod = "loadDataByConfigParams";
					if(!items._parameters.getValue("configParams"))
                    {
					   items._parameters._addParameter("configParams");
                    }
					items._parameters.setValue("configParams", configParams);
				}
				
			}
			
			if("true"==dropdown.staticDataSource){
				items.staticDataSource=true;
			}else{
				items.staticDataSource=false;
			}
			//blankValue
			if(dropdown.blankValue!=null && ("false"!=dropdown.blankValue)){

				items.insertRecord();
				
				if(dropdown.blankId){
					items.setValue("value", dropdown.blankId);
					items.setValue("valueId", "");
					items.setValue("label","");
				}else{
					items.setValue("value", "");
					items.setValue("valueId", "");
					items.setValue("label","");
				}					
				
				if("true"==dropdown.blankValue)
					items.setValue("label", "");
				else
					items.setValue("label", dropdown.blankValue);

			}
							
			fillDataset(items);		
			dropdown._items=items;
			dropdown.dataloaded = "true";

		}
		//jsDebug.debug(".................getDropdownItems():"+dropdown.id+"  :  "+((new Date())-startDate));
		return items;
	},
	
	getDataset : function (dropdown){
	    if(!dropdown._items){
	      Dropdown.getDropDownItems(dropdown);
	    }
		if(dropdown._items)
			return dropdown._items;
		else
			return null;
	},
	
	initDropDownItems : function(dropdown){
		Dropdown.getDropDownItems(dropdown);
	}	

}


