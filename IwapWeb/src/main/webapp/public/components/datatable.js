var Table = {
	getRowByCell : function(cell){
		return cell.parentElement;
	},	
	
	getTableByCell : function(cell){
		if (cell.table) return cell.table;
		var tbody=Table.getRowByCell(cell).parentElement;
		if (tbody) return tbody.parentElement;
	},	
	
	getTableByRow : function(row){
		var tbody=row.parentElement;
		if (tbody) return tbody.parentElement;
	}
}

var Datatable = {
	activeRow : null,
	activeRowIndex : null,
	_activeCell : null,
	_activeCellIndex : null,
	activeCellIndex : null,
	hoverRow : null,
	hoverRowIndex : null,

	getActiveRow : function() {	return this.activeRow; },
	getActiveRowIndex : function() { return this.activeRowIndex; },
	getActiveCell : function() { return this._activeCell; },
	getActiveCellIndex : function() { return this.activeCellIndex; },
	isReadOnly : function() {	return this.readOnly; },
	setReadOnly : function(readOnly) { this.readOnly=readOnly; },
	isEditable : function() {	return this.editable;	},	
	setEditable : function(editable) { this.editable=editable; },
	getMaxRow : function() { return this.maxRow; },
	getDataset : function() { return this.dataset; },
	getEditor : function() { return this.editor; },
	
	getTableRowStyle : function(row){
		var table=Table.getTableByRow(row);
		if (row.rowIndex % 2)
			return "row_odd";
		else
			return "row_even";
	},
	
	refreshTableRowStyle : function(row){
		var table=Table.getTableByRow(row);
		if (row==table.activeRow && System.isTrue(table.highlightSelection)){
			if (table.focused && !(Document.isDropDownPage || System.isTrue(table.isDropDownTable)))
				//row.className="row_active";
				row.style.backgroundColor="#AFDAFF";
			else
				//row.className="row_selected";
				row.style.backgroundColor="#AFDAFF";
		}
		else{
			//row.className=Datatable.getTableRowStyle(row);
			if (row.rowIndex % 2)
				row.style.backgroundColor="#ECF6FF";
			else
				row.style.backgroundColor="#fcfafb";		
		}
	},
	
	getTableRowByRecord : function(table, record){
		if (record){
			if (table._activeRow && table._activeRow.record==record)
				return table._activeRow;

			var row=table.rows[table.activeRowIndex+1];
			if (row && row.record==record)
				return row;

			var row=table.rows[table.activeRowIndex-1];
			if (row && row.record==record)
				return row;
		}

		for(var i=0; i<table.tBodies[0].rows.length; i++){
			var row=table.tBodies[0].rows[i];
			if (row.record==record)
				return row;
		}
	},
	
	refreshTableRowIndicate : function(row){
		var table=Table.getTableByRow(row);
		if (!System.isTrue(table.showIndicator)) return;
	
		var cell=row.firstChild;
		if (table.activeRow==row){
			var record=row.record;
			if (record && (record.dataset.state=="insert" || record.dataset.state=="modify"))
				cell.innerHTML="<label style=\"font-family: Webdings; font-size: 7pt; color: red\"><</label>";
			else
				cell.innerHTML="<label style=\"font-family: Webdings; font-size: 7pt\">4</label>";
			//cell.className="indicate";
			cell.style.backgroundColor = "#f0f0f0";
			cell.style.borderRight = "1px solid #A9BDE9";
		}
		else{
			cell.innerHTML="&nbsp;";
			//cell.className="indicate";
			cell.style.backgroundColor = "#f0f0f0";
			cell.style.borderRight = "1px solid #A9BDE9";
		}
	},
	
	setActiveTableCell : function(row, cellIndex){
	  
		var table=Table.getTableByRow(row);
		var index=Datatable.checkTableCellIndex(table, row.rowIndex, cellIndex);

		cell=row.cells[index[1]];
		var oldcell=table.activeCell;

		if (oldcell!=cell && table.focused){
			if (Document.activeEditor && Document.activeEditor.getAttribute("extra")=="dockeditor"){
				Editor.hideDockEditor(Document.activeEditor);
			}
		}
		
		/*
		var table_holder=table.parentElement;
		if (table_holder.scrollWidth>table_holder.offsetWidth || table_holder.scrollHeight>table_holder.offsetHeight){
			var pos=Document.getAbsPosition(cell, table_holder);
	
			if (pos[0]<table_holder.scrollLeft)
				table_holder.scrollLeft=pos[0];
			else if ((pos[0]+cell.offsetWidth)>(table_holder.scrollLeft+table_holder.offsetWidth))
				table_holder.scrollLeft=pos[0]+cell.offsetWidth-table_holder.offsetWidth;
	
			if (pos[1]<table_holder.scrollTop)
				table_holder.scrollTop=pos[1];
			else if ((pos[1]+cell.offsetHeight)>(table_holder.scrollTop+table_holder.offsetHeight))
				table_holder.scrollTop=pos[1]+cell.offsetHeight-table_holder.offsetHeight;
		}
		*/
		
		if (table.focused){
			if (!System.isTrue(table.getAttribute("readOnly")) && System.isTrue(table.getAttribute("editable")) && cell.getAttribute("field") && (cell.getAttribute("field")!="select")
			&& !System.isTrue(cell.getAttribute("readOnly"))){
				Document.stored_element=cell;
				var size = cell.dataset.getField(cell.field).size;
				if(size){
				    cell.setAttribute("maxLength",cell.dataset.getField(cell.field).size);
				}
				Editor.showDockEditor(Document.stored_element);
				need_processElementBlur = true;
			}
			else{
			  	var eventName=Document.getElementEventName(table, "onCellEdit");
				var canEditable=(Document.isUserEventDefined(eventName) && Document.fireUserEvent(eventName, [table, cell, row.record, cell.field]));
				if (canEditable) {
					Document.stored_element=cell;
					Editor.showDockEditor(Document.stored_element, true);
					need_processElementBlur = true;
				}
			}
		}

		table.activeCell=cell;

		table.activeCellIndex=cell.cellIndex;
		return true;
	},
	
	setActiveTableRow : function(row){
		var table=Table.getTableByRow(row);
		var oldrow=table.activeRow;
	
		table.activeRow=row;
		table.activeRowIndex=row.rowIndex;
	
		if (oldrow){
			Datatable.refreshTableRowStyle(oldrow);
			Datatable.refreshTableRowIndicate(oldrow);
		}
		Datatable.refreshTableRowStyle(row);
		Datatable.refreshTableRowIndicate(row);
	
		var cellIndex=table._activeCellIndex;
		if (!cellIndex) cellIndex=table.activeCellIndex;
	
		Datatable.setActiveTableCell(row, cellIndex);
		table._activeCell=null;
		table._activeCellIndex=null;
	},
	
	cell_getField : function() {
		return this.field;
	},
	
	cell_getEditorType : function() {
		return this.editorType;
	},
	
	cell_getDropDown : function() {
		return this.dropDown;
	},
	
	row_getRecord : function() {
		return this.record;
	},
	
	insertTableRow : function(table, mode, row, empty) {
		if (!row) row=table.tBodies[0].rows[0];
	    
	    if (!row) mode="begin";
	    
		var newRow=table.repeatRow.cloneNode(!empty);
		switch (mode){
			case "begin":{
				table.tBodies[0].insertAdjacentElement("afterBegin", newRow);
				break;
			}
			case "before":{
				row.insertAdjacentElement("beforeBegin", newRow);
				break;
			}
			case "after":{
				row.insertAdjacentElement("afterEnd", newRow);
				break;
			}
			default:{
				table.tBodies[0].insertAdjacentElement("beforeEnd", newRow);
				break;
			}
		}
		
		if(!row) row=table.tBodies[0].rows[0];		
		
		newRow.onmouseover=Datatable.onmouseover;
    	newRow.onmouseout=Datatable.onmouseout;
		
		for (var i=0; i<row.cells.length; i++){
			var cell=row.cells[i];
			cell.getEditorType=Datatable.cell_getEditorType;
			cell.getDropDown=Datatable.cell_getDropDown;      			
		}
		if (!Document.loading && table.tBodies[0].rows.length) Datatable.resetDataTableStyle(table, newRow.rowIndex);
		return newRow;
	},	
	
	getDropDownId: function(record){
		var attrID = record.getValue("attrID");
		var attrValues=record.getValue("attrValues");
		if(!attrValues){
			attrValues="";
		}

		var dropId="_"+attrValues.replace(/\//g,"_")+"_"+attrID;
		dropId=dropId.replace(/\-/g,"$");
	    
	    return dropId;		
	},
	
	servCreateDropDownList: function(cell, record) {
	//alert(record.getValue("attrValues"));
	    if(record.getValue("attrValues")=="")
	      return false;
	    var attrValuesList = record.getValue("attrValues").split("/");
	    var attrValuesDescList=record.getValue("attrValuesDesc").split("/");
	    var attrID = record.getValue("attr_id");
	    var dropId=record.getValue("attr_id")+record.getValue("attr_code");
	    var dropdown = $("dropdownList"+dropId);
	    if(!dropdown)
	    {
		    if (attrValuesList.length > 0) {
		        var inHtml = "<xml id=\"__dropdownList" + dropId + "\">";
		        inHtml += "<items>";
		        for (var i = 0; i < attrValuesList.length; i++) {
		            inHtml += "<item label=\"" + attrValuesDescList[i] + "\" value=\"" + attrValuesList[i] + "\"></item>";
		        }
		        inHtml += "</items>";
		        inHtml += "</xml>";
		        inHtml += "<code id=\"dropdownList" + dropId + "\"></code>";
		        document.body.insertAdjacentHTML("beforeEnd", inHtml);
		        makeDropDown($("dropdownList" + dropId));
		    } 
	    } 
	    return true;
	},
	servCreateDropDownListOfferMem: function(cell, record,type) {//销售品成员用下拉
		var attrId =  record.getValue("comp_role_id")+"_"+record.getValue("product_offer_instance_id");
		
	    var dropDownStr = "dropdownList";
	    if(type=="1")
	    {	
	    	dropDownStr = "dropdownList_rela";
	    }
	    var dropdown = $(dropDownStr+attrId);
	    
		    if (DS_PRODUCT_OFFER_DETAIL.getCount()>0) {
		        var inHtml = "<xml id=\"__"+dropDownStr+attrId+"\">";
		        inHtml += "<items>";
		        var _record=DS_PRODUCT_OFFER_DETAIL.getFirstRecord();
		        
		        while(_record){
		          if((_record.getValue("comp_role_id")+"_"+_record.getValue("product_offer_instance_id"))==attrId){
		           var attrValuesDescList =  "";
		           var attrValuesList ="";
		           if(type=="0")
		           {
						attrValuesDescList = 'CrmWeb' == SystemContext.appName ? 
		         	 		_record.getValue("product_names").split("/") : 
		         	 		_record.getValue("product").split("/");		         	 
		         	 	attrValuesList=_record.getValue("element_id").split("/");
		           }
		           else
		           {	
		           	 attrValuesDescList = _record.getValue("rela_offer_id").split("/");
		           	 attrValuesList=_record.getValue("element_id").split("/");
		           }
		          	if(attrValuesList.length > 0)
		          	{
		          		
		          		 for (var i = 0; i < attrValuesList.length; i++) {
		          		 	
		             		inHtml += "<item label=\"" + attrValuesDescList[i] + "\" value=\"" + attrValuesList[i] + "\"></item>";
						}	          
		          	}
		          	
		          }
		          _record=_record.getNextRecord(); 
		        }
		        inHtml += "</items>";
		        inHtml += "</xml>";
		        inHtml += "<code id=\""+dropDownStr+attrId+"\"></code>";
		        if(!dropdown){
			        document.body.insertAdjacentHTML("beforeEnd", inHtml);
			        makeDropDown($(dropDownStr + attrId));
		        }
		        var is_refresh = record.getValue("is_refresh");
		        if(attrValuesList.length > 0 && (!is_refresh||is_refresh=="0"))//0为需要刷新
		        {
		        	
		       		if(type=="1")
		       		{
		       			
		       		//	record.setValue("rela_offer_id",attrValuesList[0]);
		       			
		       		}
		       		else
		       		{
		       			record.setValue("product",attrValuesList[0]);
		       			record.setValue("is_refresh","1");//置成已经刷新过
		       		}
		       		
		       		if(Document.activeEditor){
						Editor.updateEditorInput(Document.activeEditor);
					}
		        }
		        return true;
		    } 
		    else
		    	return false;
	   
	},
	servCreateDropDownList2: function(cell, record) {
		var attrId = record.getValue("attrId");
	    var dropdown = $("dropdownList"+attrId);
	    if(!dropdown){
		    if (DS_attribute_value.getCount()>0) {
		        var inHtml = "<xml id=\"__dropdownList" + attrId + "\">";
		        inHtml += "<items>";
		        var _record=DS_attribute_value.getFirstRecord();
		        while(_record){
		          if(_record.getValue("attrId")==attrId){
		             inHtml += "<item label=\"" + _record.getValue("attrValueDesc") + "\" value=\"" + _record.getValue("attrValue") + "\"></item>";
		          }
		          _record=_record.getNextRecord(); 
		        }
		        inHtml += "</items>";
		        inHtml += "</xml>";
		        inHtml += "<code id=\"dropdownList" + attrId + "\"></code>";
		        document.body.insertAdjacentHTML("beforeEnd", inHtml);
		        makeDropDown($("dropdownList" + attrId));
		    } 
		    else
		    	return false;
	    } 	
	    return true;
	},
	
	//根据属性表取值类型动态生成输入控件
    extraOnInitCell: function(table, row, cell, field){
    	
         /*
		Value	Label
		98A	单选下拉框
		//98B	多选下拉框
		98C	文本输入
		98D	日期控件
		98E	密码输入
		//98F	邮件地址输入
		98G	IP输入
		//98H	后台自动生成
		98I	整数输入
		98J	浮点数输入
		//98K	后台序列生成
		98L	MAC地址
		//98M	弹出控件
		//98N	CHECKBOX
      */
		if( table.id=="table_DS_SERV_PRODUCT_ATTR"){
		 	var record = row.record;
			//对必须填写的字段字体颜色做特殊处理
			if(record.getValue("is_null")=="F"){//T代表允许为空
	        	row.cells[1].style.color="red";
	        }else{
	        	row.cells[1].style.color="black";
	        }
			if ( field.getName() == "attr_value" ) {
				var inputMethod = System.trimStr(record.getValue("input_method"));
				//如果为零代表不可编辑
				if(record.getValue("is_edit")=="F"||inputMethod=="98H"||inputMethod=="98K"){
					cell.disabled=true;
				}else{
					cell.disabled=false;
				}
		        cell.editorType="";
		        cell.dropDown="";
		     
		        switch(inputMethod){
		          case "98A":{
		            var dropDownId =record.getValue("attr_id")+record.getValue("attr_code");
		            if(dropDownId && Datatable.servCreateDropDownList(cell, record))
		            	cell.dropDown = "dropdownList" + dropDownId;
		            break;
		          }
		          case "98D":{
		          	cell.editorType="date";
		          	break;
		          }
		          case "98E":{
		          	cell.editorType="password";
		          	break;
		          }case "98M":{
		          	cell.editorType="pop";
		          	break;
		          }          
		          case "98G":{
		          	cell.editorType="ipeditor";
	        		//record.setValue("showValue", "   .   .   .   ");
	        		break;
		          }
		          case "98I":{
		          	cell.editorType="integer";
		          	break;
		          }
		          case "98J":{
		          	cell.editorType="double";
		          	break;
		          }
	        	}
	   	 	}
		}else if(table.id=="table_DS_PRODUCT_OFFER_DETAIL"){//销售品构成动态显示配置
		 		
		 		var record = row.record;
		 		if ( field.getName() == "product" ){
		 			 cell.editorType="";
		        	 cell.dropDown="";
		        	 var cur_prod = record.getValue("product");//先获取默认的ID
		        	 var dropDownId = record.getValue("comp_role_id")+"_"+record.getValue("product_offer_instance_id");
		             if(dropDownId && Datatable.servCreateDropDownListOfferMem(cell, record,"0"))
		             		 cell.dropDown = "dropdownList" + dropDownId;
							//强更新活动编辑框的数据到数据集中
							
					var prodDropDown = Dropdown.getDataset($("dropdownList" + dropDownId));
					var prodName =  prodDropDown.find(["value"],[record.getValue("product")]).getValue("label");
					if(cur_prod!=null && cur_prod!=""){
						record.setValue("product",cur_prod);//如果默认ID不为空 那么就设置成默认ID
					}
					
					record.setValue("product_name",prodName);//设置产品名称
					
					if(Document.activeEditor){
						Editor.updateEditorInput(Document.activeEditor);
					}
	
		 		}else if ( field.getName() == "acc_nbr" ){	 
		 		 	var dropDownId = record.getValue("rela_offer_inst_id");
		 		 	var cur_value = record.getValue("acc_nbr");
		 		 	var nbr_use = record.getValue("nbr_use");//获取在用号码串
		 		 	var nbr_useValues = record.getValue("nbr_useValues");//获取在用号码串对应实例
		 		 	var product_id = record.getValue("product");//获取当前产品ID
		 		 	var total_nbrs = "";
		 		 	var total_nbrValues = "";
		 		 	var attrValuesDescList = "";
		 		 	var attrValuesList = "";
		 		 	if(nbr_use){
		 		 	 if(nbr_use.indexOf("|")>-1)
		 		 	 {		
		 		 		 total_nbrs = nbr_use.split("|");
		 		 		 attrValuesDescList = total_nbrs[0].split("/"); 
		 		 	 }
		 		 	 else
		 		 	 {	
		 		 	 	attrValuesDescList = nbr_use.split("/");
		 		 	 }
		 		 	}
		 		 	if(nbr_useValues){		
			 		 	 if(nbr_useValues.indexOf("|")>-1)
			 		 	 {		
			 		 		 total_nbrValues = nbr_useValues.split("|");
			 		 		 attrValuesList = total_nbrValues[0].split("/"); 
			 		 	 }
			 		 	 else
			 		 	 {	
			 		 	 	attrValuesList = nbr_useValues.split("/");
			 		 	 }
		 		 	}
		 		 	var dropdown = $("dropdownList_nbr_"+dropDownId);
		 			if(!dropdown){
				        var inHtml = "<xml id=\"__dropdownList_nbr_" + dropDownId + "\">";
				        inHtml += "<items>";
				        inHtml += "<item label=\"--请选择--\" value=\"\"></item>";
				         inHtml += "<item label=\"更多...\" value=\"-999\"></item>";
				        if(nbr_use&&nbr_useValues)//组装在用号码下拉
				        {
				        	
						   for (var i = 0; i < attrValuesList.length; i++) {
					          	inHtml += "<item label=\"" + attrValuesDescList[i] + "\" value=\"" + attrValuesList[i] + "\"></item>";
							}	
						}
				       
				        inHtml += "</items>";
				        inHtml += "</xml>";
				        inHtml += "<code id=\"dropdownList_nbr_" + dropDownId + "\"></code>";
				        document.body.insertAdjacentHTML("beforeEnd", inHtml);
				        makeDropDown($("dropdownList_nbr_" + dropDownId));
			    	 } 	
					 cell.dropDown = "dropdownList_nbr_" + dropDownId;
					 var tt = cell.dropDown+'_onSelect = function(dropdown,record,editor){  return qryUsingOffer(dropdown,record,editor);   }' ;
					 eval(tt);
					 record.setValue("nbr_dropdown",cell.dropDown);//存放下拉框ID
					 
 					//2.0 特殊处理
					 if(SystemContext.appName == 'CrmWeb'){
					 	var currNbrDropDown = Dropdown.getDataset($(cell.dropDown));//获取号码下拉框的数据集
					 	currNbrDropDown.clearData();
					 	currNbrDropDown.insertRecord(); 
					 	currNbrDropDown.setValue('value','');
					 	currNbrDropDown.setValue('label','--请选择--');
					 	currNbrDropDown.insertRecord(); 
					 	currNbrDropDown.setValue('value','-999');
					 	currNbrDropDown.setValue('label','更多...');
					 	//currRecord.setValue("acc_nbr","");
					 	packageDealer.dealUsdNbrDropDwon(record,currNbrDropDown,product_id);//根据产品的ID获取对应号码cur_value
					 	if(cur_value){//如果存在在用号码 需要设置成在用号码
					 	 	record.setValue("acc_nbr",cur_value);
					 	}else{	//否则 为新装 默认为 请选择
					 	 	record.setValue("acc_nbr","");
					 	}
					 }
		 		}
		 		else if ( field.getName() == "rela_offer_id" )
		 		{	 
		 		 	 cell.editorType="";
		        	 cell.dropDown="";
		        	 var dropDownId = record.getValue("comp_role_id");
		             if(dropDownId && Datatable.servCreateDropDownListOfferMem(cell, record,"1"))
		             		 cell.dropDown = "dropdownList_rela" + dropDownId;
							//强更新活动编辑框的数据到数据集中
					if(Document.activeEditor){
						Editor.updateEditorInput(Document.activeEditor);
					}
		 		}
		 }else if(table.id=="table_packageLists"){//销售品构成动态显示配置
		 }


        	
    },
    cell_ondblclick : function(){
	  var pos=Document.getAbsPosition(this);;
	  with(_popup_texteditor){
    	  style.display = "";
    	  style.width = this.offsetWidth;
    	  style.height = this.offsetHeight;
    	  style.left = pos[0];
    	  style.top = pos[1];
    	  value = this.innerText;
    	  select();
	  }	    
    },
	//refreshTableData函数由Dataset类的refreshControls()函数调用触发
	//在notifyCursorChanged函数中也会触发这个函数重新使用数据填充表格
	refreshTableData : function(table, startRecord){
		
		var dataset=Control.getElementDataset(table);//和表格关联的Dataset对象
		if (!dataset){
			alert("no dataset return");
			return;
		}
	
		var count=0, maxRow=table.getAttribute("maxRow");
		var _record=(startRecord)?startRecord:dataset.getFirstRecord();//起始记录
		var count=0;
		while (_record && count<maxRow){
		    if(_record.getValue("visible")=="false"){
		      _record = _record.getNextRecord();
		      continue;
		    }
		     
			if (count>(table.tBodies[0].rows.length-1)){//如果是一个空表格，则将数据插入到末尾
				Datatable.insertTableRow(table, "end");
			}
				
			row=table.tBodies[0].rows[count];
			Datatable.refreshTableRowStyle(row);//更新表格行的显示风格
			row.extra="tablerow";
			row.record=_record;
			for (var j=0; j<row.cells.length; j++){
				cell=row.cells[j];		
				
				if(cell.field && cell.dataset){
				  var field = cell.dataset.getField(cell.field);
				  if(field){
				    Datatable.extraOnInitCell(table, row, cell, field);
				    Document.fireUserEvent(Document.getElementEventName(table, "onInitCell"), [table, row, cell, field]);
				  }
				}		
				if(cell.className!="indicate" && cell.field!="select" && !Document.isUserEventDefined(table.id+"_ondblclick")){
					cell.ondblclick=Datatable.cell_ondblclick;
		    	}
				Control.refreshElementValue(cell);//将record里的数据填充到单元格中				
			}
			count++;
			_record=_record.getNextRecord();
			
		}

		for(var i=table.tBodies[0].rows.length-1; i>=count; i--){
			var tmpRow=table.tBodies[0].rows[i];
			//if (table.tBodies[0].rows.length!=1)
				Datatable.deleteTableRow(tmpRow);
			/*
			else{
				tmpRow.record=null;
				for (var j=0; j<tmpRow.cells.length; j++){
					var cell=tmpRow.cells[j];
					if (cell.getAttribute("extra")=="tablecell")
						Control.refreshElementValue(cell);
				}
			}
			*/
		}

		var row=Datatable.getTableRowByRecord(table, dataset.record);
		if(!row){
		    row = table.tBodies[0].rows[0];
		  table.activeRow = null;
	      table.activeRowIndex = null;
	      table._activeCell = null;
	      table._activeCellIndex = null;
	      table.activeCellIndex = null;
	    }
		if (row){

			Datatable.setActiveTableRow(row);
		}
		table.needRefresh=false;
	},
	
	isTableRecordSelected : function(table, record){
		return record.getValue("select");
	},	
	selectTableRecord : function(table, record){
	
		if (record)	{
			record.setValue("select", true);
		}
	},
	unselectTableRecord : function(table, record){
		if (record)	{
			record.setValue("select", false);
		}
	},
	selectTableRecord2 : function(table, record){
	
		if (record)	{
			record.setValue("select", true);
			Dataset.setRecord(table.dataset, record);
		}
	},
	unselectTableRecord2 : function(table, record){
		if (record)	{
			record.setValue("select", false);
			Dataset.setRecord(table.dataset, record);
		}
	},	
		
	refreshData : function(startRecord){
		Datatable.refreshTableData(this, startRecord);
	},
			
	isRecordSelected : function(record){
		return Datatable.isTableRecordSelected(this, record);
	},
		
	selectRecord : function(record){
		Datatable.selectTableRecord(this, record);
	},
		
	unselectRecord : function(record){
		Datatable.unselectTableRecord(this, record);
	},
	
	checkTableCellIndex : function(table, rowIndex, cellIndex){

		var r_rowIndex=rowIndex;
		var r_cellIndex=cellIndex;
		var minRowIndex=(table.tHead)?table.tHead.rows.length:0;
		minRowIndex=(minRowIndex<0)?0:minRowIndex;	  		
		var maxRowIndex=(table.tBodies[0])?(minRowIndex+table.tBodies[0].rows.length-1):-1;
		var minCellIndex=table.minCellIndex;		
		var maxCellIndex=table.tBodies[0].rows[0].cells.length-1;
	
		if ((!r_cellIndex)||(r_cellIndex<minCellIndex)) r_cellIndex=minCellIndex
		else if (r_cellIndex>maxCellIndex) r_cellIndex=maxCellIndex;
		if ((!r_rowIndex)||(r_rowIndex<minRowIndex)) r_rowIndex=minRowIndex
		else if (r_rowIndex>maxRowIndex) r_rowIndex=maxRowIndex;
	
		return ([r_rowIndex, r_cellIndex]);
	},
	
	resetDataTableStyle : function(table, startIndex){
		var row;
		var maxIndex=Datatable.checkTableCellIndex(table, 9999, 9999);
		for(var i=startIndex; i<=maxIndex[0]; i++){
			row=table.rows[i];
			Datatable.refreshTableRowStyle(row);
		}
	},
	
	deleteTableRow : function(row){
		var table=Table.getTableByRow(row);
		with (table){
			if (table.activeRow==row){
				setAttribute("activeRow", null);
				setAttribute("activeCell", null);
			}
			var rowIndex=row.rowIndex;
			row.removeNode(true);
			if (!Document.loading && table.tBodies[0].rows.length) Datatable.resetDataTableStyle(table, rowIndex);
		}
	},	
	
	getTableFirstRecord : function(table){
		if (table.tBodies[0].rows.length>0)
			return table.tBodies[0].rows[0].record;
		else
			return null;
	},
			
	getTableLastRecord : function(table){
		var rowLength=table.tBodies[0].rows.length;
		if (rowLength>0)
			return table.tBodies[0].rows[rowLength-1].record;
		else
			return null;
  },
  
	refreshTableRowData : function(row){
		for(var i=0; i<row.cells.length; i++){
			Control.refreshElementValue(row.cells[i]);
		}
	},
	
	refreshTableRecord : function(row){
		Datatable.refreshTableRowData(row);
	},	
	
	deleteTableRecord : function(row) {
		var table=Table.getTableByRow(row);
		var editor=table.editor;
		if (editor) Editor.hideDockEditor(editor);
	
		if (table.tBodies[0].rows.length>0){
			var nextRow=table.tBodies[0].rows[row.rowIndex+1];
			Datatable.deleteTableRow(row);
			if (nextRow) Datatable.refreshTableRowData(nextRow);
		}
		else{
			row.record=null;
			for (var i=0; i<row.cells.length; i++){
				Control.refreshElementValue(row.cells[i]);
			}
		}
	}, 
	
	insertTableRecord : function(table, mode, row, record) {
		if (!row) row=table.tBodies[0].rows[0];
	
		var newRow;
		if (row && !row.getAttribute("record")){
			newRow=row;
		}
		else{
			newRow=Datatable.insertTableRow(table, mode, row);
		}
		newRow.record=record;
		for (var i=0; i<newRow.cells.length; i++){
			Control.refreshElementValue(newRow.cells[i]);
			newRow.cells[i].ondblclick=Datatable.cell_ondblclick;
		}
		return newRow;
	},
	
	getCheckboxByRecord : function(table, record){
		var cells=document.body.all(table.id+"_select");
		if (cells){
			for (var i=0; i<cells.length; i++) {
				var row=Table.getRowByCell(cells[i]);
				if (row.record==record){
					var checkbox=cells[i].firstChild;
					return checkbox;
				}
			}
		}
	},
	
	getRecordByCell : function(cell){
		return Table.getRowByCell(cell).record;
	},
	
	onclick : function(){
	    if(event.srcElement.className!="indicate"){
	    	Document.fireUserEvent(Document.getElementEventName(this, "onclick"), [this]);
		}
	},
	
	ondblclick : function(){
		if(event.srcElement.className!="indicate"){
	    	Document.fireUserEvent(Document.getElementEventName(this, "ondblclick"), [this]);
		}
	},
	
	onmouseover : function(){
		var color = this.style.backgroundColor;
	    if(color!="#afdaff"){
	    	this.style.backgroundColor="#D9F5FF";
	    }
	    
	},	
	
	onmouseout : function(){
		var color = this.style.backgroundColor;
	    if(color!="#afdaff"){
		  if(this.rowIndex%2==0){
		    this.style.backgroundColor="#FCFAFB";
		  }
		  else{
		    this.style.backgroundColor="#ECF6FF";
		  }
		}
	}
}

var TableHead = {	
	onclick : function(){
		var cell=this;
		var table=Table.getTableByCell(cell);
		var eventName=table.id + "_onTableHeaderClick";
		if(Document.fireUserEvent(eventName,[this])){
			return;
		}	
		if (System.compareText(cell.name, "select")) {
			TableSelect.onHeaderClick(table, cell);
			return;
		}		
	}
}

var TableCheckbox = {	
	onclick : function(){

		var checkbox = event.srcElement;
		var cell = checkbox.parentElement;
		var table=Table.getTableByCell(cell);	
		
		var eventName=table.id + "_select_onClick";
		if (Document.isUserEventDefined(eventName)){
			var record=Datatable.getRecordByCell(cell);
			if (record)
			  Document.fireUserEvent(eventName, [cell, cell.value, record]);
		}	 			
			
    if(cell){
			var row=Table.getRowByCell(cell);
			var record=row.getAttribute("record");
			if (!record) event.returnValue=false;		
			if (checkbox.checked)
				Datatable.selectTableRecord2(Table.getTableByRow(row), record);
			else
				Datatable.unselectTableRecord2(Table.getTableByRow(row), record);  
	  }
	}
}


var TableSelect = {
    	
	onHeaderClick : function(table, cell) {
	    
	    
	    if(typeof(TableSelect_beforeHeaderClick)!="undefined")
	    {
	       if(TableSelect_beforeHeaderClick()==false)
	         return ;
	     
	    }
	    var eventName=table.id + "_beforeHeaderClick";
		if(Document.fireUserEvent(eventName,[this])==false){
			return; 
		}
		var dataset=Control.getElementDataset(table);
		if (!dataset) return;
	
		//dataset.disableControls();
		try{
			var record=dataset.getFirstRecord();
			while (record){
				if(!record.getCell(table,"select").disabled){
					if (table.isRecordSelected(record)) {
						table.unselectTableRecord(table, record);
					}
					else{
						table.selectTableRecord(table, record);
					}
				}
				record=record.getNextRecord();
			}
		if(typeof(TableSelect_afterHeaderClick)!="undefined")
	    {
	       TableSelect_afterHeaderClick()
	       
	    }
		}
		
		finally{
			//dataset.enableControls();
		}
	},
	onHeaderDblClick : function() {
		var cell=this;
		var table=Table.getTableByCell(cell);
		
		var dataset=Control.getElementDataset(table);
		if (!dataset) return;
	
		//dataset.disableControls();
		try{
			var record=dataset.getFirstRecord();
			while (record){
				table.selectTableRecord(record);
				record=record.getNextRecord();
			}
		}
		finally{
			//dataset.enableControls();
		}
	}
}

//初始化datatable组建
function initDataTable(table, resetColumns){
	function setElementAttribute(element, attr, value){
		if (System.getValidStr(element.getAttribute(attr))=="")
			element.setAttribute(attr, value);
	}
	
	table.refreshData=Datatable.refreshData;
	table.isRecordSelected=Datatable.isRecordSelected;
	table.selectRecord=Datatable.selectRecord;
	table.unselectRecord=Datatable.unselectRecord;
	table.selectTableRecord=Datatable.selectTableRecord;
    table.unselectTableRecord=Datatable.unselectTableRecord;
    
	table.activeRow=null;
	table.activeRowIndex=null;
	table._activeCellIndex=null;
	table.activeCellIndex=null;
	table._activeCell=null;
	table._activeCellIndex=null;
	
	table.getActiveRow=Datatable.getActiveRow;
	table.getActiveRowIndex=Datatable.getActiveRowIndex;
	table.getActiveCell=Datatable.getActiveCell;
	table.getActiveCellIndex=Datatable.getActiveCellIndex;
	table.isReadOnly=Datatable.isReadOnly;
	table.setReadOnly=Datatable.setReadOnly;
	table.isEditable=Datatable.isEditable;
	table.getMaxRow=Datatable.getMaxRow;
	table.getDataset=Datatable.getDataset;
	table.getEditor=Datatable.getEditor;	
	
	if (Document.isDropDownPage || System.isTrue(table.isDropDownTable))
		table.onclick=Dropdown.dropdown_onclick;
    else{
      if(Document.isUserEventDefined(Document.getElementEventName(table, "onclick"))){
        table.onclick = Datatable.onclick;
      }
    }
  
  if(!table.ondblclick){
    table.ondblclick = Datatable.ondblclick;
  }
  
  if(Global.onServer && System.isTrue(table.initialized)){
    var dataset=Control.getElementDataset(table);//获取table关联的Dataset对象
	if(dataset){
	  table.readOnly = dataset.readOnly;
	  table.editable = dataset.editable;
    }
        
    for(var i=0; i<table.rows.length; i++){
      var row = table.rows[i];
      for(var j=0; j<row.cells.length; j++){
        var cell = row.cells[j];
        cell.getField=Datatable.cell_getField;	
    	cell.dataset=dataset;
    	cell.window=window;
    	if(cell.extra=="columnheader"){
    	  if(cell.name=="select"){
    	    cell.ondblclick=TableSelect.onHeaderDblClick;
    	  }
    	  cell.onclick=TableHead.onclick;
    	}
      }
    }
    
    tBodyRow=table.tBodies[0].rows[0];    
    table.repeatRow=tBodyRow.cloneNode(true);
    table.tBodies[0].deleteRow(0);
	table.selectedRecords=new pArray();
	if (System.getInt(table.getAttribute("maxRow"))==0) 
		table.maxRow=99999;	
  }
  else{
  
	var dataset=Control.getElementDataset(table);//获取table关联的Dataset对象
	
	if(dataset){
	  table.readOnly = dataset.readOnly;
	  table.editable = dataset.editable;
    }
	if (resetColumns && dataset){//如果存在Dataset并且要求重新初始化表格字段
		var arrayField, arrayLabel=[]; //表字段和标题标签数组
		var fields=table.fields;
		if (fields){  //如果table有定义fields属性，则通过该属性来重新初始化表格字段列 
			arrayField=fields.split(","); 
			for(var i=0; i<arrayField.length; i++){
				index=arrayField[i].indexOf("=");
				if (index>=0){
					arrayLabel[i]=arrayField[i].substr(index+1);
					arrayField[i]=arrayField[i].substr(0, index);
				}
			}
		}
		else{//否则，通过dataset对象获取字段对象
			arrayField=[];
			for(var i=0; i<dataset.fields.fieldCount; i++){
				arrayField[i]=dataset.fields[i].name;
			}
		}

		for (var i=table.children.length-1; i>=0; i--)//清空表格的所有子节点
			table.children[i].removeNode(true);
		
		table.appendChild(document.createElement("<tbody></tbody>"));//为表格增加一个<tbody>标签

		var row, cell;
		if (System.isTrue(table.showHeader)){//插入表头
			row=table.createTHead().insertRow();//为表格增加一个<thead>标签，插入一个表头行
			for(var i=0; i<arrayField.length; i++){//根据表格字段给表格行增加多个单元格\
			  if(arrayField[i]=="") continue;
				if( !dataset.getField(arrayField[i]).visible ) continue ;
				cell=row.insertCell();
				cell.name=arrayField[i];
				cell.field=arrayField[i];
				if(cell.field=="select"){
					cell.style.width = "10px";
				}
				cell.style.border = "1px solid #A9BDE9";
				if (arrayLabel[i])
					cell.label=arrayLabel[i];
			}
		}

		row=table.tBodies[0].insertRow();//在表格的tbody标签上增加一个空行。
		for(var i=0; i<arrayField.length; i++){//根据表格字段给这个空行插入多个单元格
			if(arrayField[i]=="") continue;
			if(!System.isTrue(table.isDropDownTable) && !dataset.getField(arrayField[i]).visible ) continue ;//Added by xuruihao 2006-03-14
			cell=row.insertCell();
			cell.name=arrayField[i];
			cell.field=arrayField[i];
			cell.getField=Datatable.cell_getField;		
			cell.editable=dataset.getField(arrayField[i]).editable;
			cell.dropDown=dataset.getField(arrayField[i]).dropDown;	
			if(!System.isTrue(table.isDropDownTable))		
				cell.style.border = "1px solid #A9BDE9";			
		}

		if (System.isTrue(table.showFooter)){//插入表脚
			row=table.createTFoot().insertRow();
			for(var i=0; i<arrayField.length; i++){
				if(arrayField[i]=="") continue;
				if( !dataset.fields[i].visible ) continue ;
				cell=row.insertCell();
				cell.name=arrayField[i];
				cell.field=arrayField[i];
				if (arrayLabel[i]) cell.label=arrayLabel[i];
			}
		}
		delete arrayField;
	}
 
	var tHeadRow, tBodyRow, tFootRow;
	if (table.tHead && table.tHead.rows[0])
		tHeadRow=table.tHead.rows[0];//表头行

	if (table.tFoot && table.tFoot.rows[0])//表脚行
		tFootRow=table.tFoot.rows[0];

	tBodyRow=table.tBodies[0].rows[0];//tbody行
  
	if (System.isTrue(table.showIndicator)) {
		table.minCellIndex=1;
		if (!tBodyRow.firstChild || (tBodyRow.firstChild && !tBodyRow.firstChild.isIndicate)){
			cell=tBodyRow.insertCell(0);
			cell.width="9px";
			cell.align="center";
			cell.isIndicate=true;
			cell.className="indicate";
			cell.style.backgroundColor = "#f0f0f0";
			cell.style.border = "1px solid #A9BDE9";
			if (tHeadRow) {
				cell=tHeadRow.insertCell(0);
				cell.align="center";
			}

			if (tFootRow) {
				cell=tFootRow.insertCell(0);
				cell.align="center";
			}
		}
	}
	else{
		table.minCellIndex=0;
	}

	for(var i=table.minCellIndex; i<tBodyRow.children.length; i++) {
		var cell=tBodyRow.children[i];
		//cell.defaultValue = dataset.fields[i].defaultValue ;
		if(!cell.name || cell.name=="")
		  cell.name = cell.field;
		var name=cell.name;//单元格名称
		var dataField=cell.field;//字段名称
		if (!dataField)
			dataField=name;
		cell.dataset=dataset;//给单元格对象的dataset赋值
		cell.field=dataField;//给单元格对象的field赋值

		//初始化单元格的toolTip属性
		var toolTip=cell.getAttribute("toolTip");
		cell.removeAttribute("toolTip");
		var field=null;
		if (dataset)
			field=dataset.getField(dataField);
		if (field && !toolTip)
			toolTip=field.toolTip;

		cell.id=table.id+"_"+dataField;

		cell.extra="tablecell";//继承自tablecell类
		
		if (Document.isDropDownPage || System.isTrue(table.isDropDownTable)) 
			cell.noWrap=true;
			
    	cell.noWrap = true;
    
		if (name=="select"){
			cell.align="center";
			cell.vAlign="center";
			cell.innerHTML="<input type=checkbox onclick=\"return TableCheckbox.onclick();\" style=\"height:16\">";
			cell.readOnly=true;
		}
		else{
			if (field){
				setElementAttribute(cell, "readOnly", field.readOnly);//字段只读属性
				setElementAttribute(cell, "dataType", field.dataType);//字段类型
				//对齐方式
				setElementAttribute(cell, "align", field.align);
				setElementAttribute(cell, "vAlign", field.vAlign);
			}
			else{
				setElementAttribute(cell, "readOnly", true);
			}
		}

		//根据字段类型给单元格设置编辑器类型
		if (System.getValidStr(cell.getAttribute("editorType"))==""){
			switch (cell.getAttribute("dataType")){
				case "date":;
				case "datetime":{
					cell.editorType="date";
					break;
				}
				case "boolean":{
					if (!cell.dropDown)	{
						cell.editorType="checkbox";
					}
					break;
				}
			}
		}

		//Document.fireUserEvent(Document.getElementEventName(table, "onInitCell"), [table, row, cell, field]);
		if (!cell.className) cell.className=cell.extra;
		cell.window=window;

		if (tHeadRow){//给表格头设置属性
			var cell=tHeadRow.children[i];
			cell.id=table.id+"_header_"+dataField;
			cell.name=name;
			cell.field=dataField;			
			cell.extra="columnheader";
			cell.align="center";
			cell.getField=Datatable.cell_getField;

			if (System.compareText(name, "select")){
				if (!cell.getAttribute("label")) cell.label="<font face=Marlett size=2>a</font>";
				cell.ondblclick=TableSelect.onHeaderDblClick;
			}
			else{
				cell.title=toolTip;
			}			
			cell.dataset=dataset;			
			
			if (!cell.className) cell.className=cell.extra;
			cell.noWrap=true;//这个可以在样式表中处理，以后解决
			cell.onclick=TableHead.onclick;
			
			Control.refreshElementValue(cell);//刷新单元格的值==================================
			cell.window=window;
		}

		if (tFootRow){
			var cell=tFootRow.children[i];
			cell.id=table.id+"_footer_"+dataField;
			cell.name=name;
			cell.field=dataField;
			cell.extra="columnfooter";
			cell.getField=Datatable.cell_getField;

			if (System.compareText(name, "select")){
				if (!cell.getAttribute("label")) cell.label="<font face=Marlett size=2>a</font>";
				cell.align="center";		
			}
			else{
				if (field){
					setElementAttribute(cell, "align", field.align);
				}
				else {				
					cell.align="center";
				}
			}
			cell.dataset=dataset;
			
			if (!cell.className) cell.className=cell.extra;
			Control.refreshElementValue(cell);
			cell.window=window;
		}
	}

	tBodyRow.extra="tablerow";
	table.repeatRow=tBodyRow.cloneNode(true);
    table.tBodies[0].deleteRow(0);
	table.selectedRecords=new pArray();
	if (System.getInt(table.getAttribute("maxRow"))==0) 
		table.maxRow=99999;	
  }	
}

function resetDataTable(table){
	initDataTable(table, true);
	Datatable.refreshTableData(table);
}

function makeDataTable(element, init){

  if(init){
      	if(!element.initialized)
      	  element.initialized = "false";
      	if(!element.extra)
      	  element.extra = "datatable";
      	if(!element.readOnly)
      	  element.readOnly = "false";
      	if(!element.editable)
      	  element.editable = "false";
      	if(!element.showHeader)
      	  element.showHeader = "true";
      	if(!element.showFooter)
      	  element.showFooter = "false";
      	if(!element.showIndicator)
      	  element.showIndicator = "true";    
      	  
     	if(!element.highlightSelection)
      	  element.highlightSelection = "true";
      	if(!element.maxRow)
      	  element.maxRow = "0";
      	if(!element.skipRebuild)
      	  element.skipRebuild = "false";
      	if(!element.confirmCancel)
      	  element.confirmCancel = "false";
      	if(!element.confirmDelete)
      	  element.confirmDelete = "false";        	      	        	        	        	  
      	  
		if( element.width == "" )
		  element.style.width = "100%";	  
		element.cellPadding = "2";
		element.cellSpacing = "0";
		element.rules = "all"; 
	
	    if(element.id=="")
	      element.id = "table_"+element.dataset;  
  }

		if (Document.isDropDownPage || System.isTrue(element.isDropDownTable)){
			if (!element.className)
				element.className="dropdowntable";
		}
		else{
			if (!element.className)
				element.className="datatable";
		}
		
		//特殊处理为：
		Control.initElementDataset(element);
		initDataTable(element, !System.isTrue(element.getAttribute("skipRebuild")));
		element.onkeydown=Document.control_onkeydown;	
			
		element.window=window;	
}
