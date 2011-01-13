/*  pArray */

var pArray = Class.create();
pArray.prototype = {
  initialize : function(){
		this.firstUnit=null;
		this.lastUnit=null;
		this.length=0;
  },
	getFirstUnit : function() { return this.firstUnit; },
	getLastUnit : function() {	return this.lastUnit; },
	getLength : function() {	return this.length; },
	clearAll : function(){  pArray._clearAll(this); },

	//在链表结构中插入一个对象
	insertUnit : function(mode, unit, newUnit){
		var u1, u2;
		//插入位置
		switch (mode){
			case "begin":{
				u1=null;
				u2=this.firstUnit;
				break;
			}
			case "before":{
				u1=(unit)?unit.prevUnit:null;
				u2=unit;
				break;
			}
			case "after":{
				u1=unit;
				u2=(unit)?unit.nextUnit:null;
				break;
			}
			default:{
				u1=this.lastUnit;
				u2=null;
				break;
			}
		}

		newUnit.prevUnit=u1;
		newUnit.nextUnit=u2;
		if (u1)
			u1.nextUnit=newUnit;
		else
			this.firstUnit=newUnit;
		if (u2)
			u2.prevUnit=newUnit;
		else
			this.lastUnit=newUnit;
		this.length++;
	},
	insertArray : function(mode, unit, subArray){
		if (!subArray || !subArray.firstUnit) return;

		var u1, u2;
		switch (mode){
			case "begin":{
				u1=null;
				u2=this.firstUnit;
				break;
			}
			case "before":{
				u1=(unit)?unit.prevUnit:null;
				u2=unit;
				break;
			}
			case "after":{
				u1=unit;
				u2=(unit)?unit.nextUnit:null;
				break;
			}
			default:{
				u1=this.lastUnit;
				u2=null;
				break;
			}
		}

		subArray.firstUnit.prevUnit=u1;
		subArray.lastUnit.nextUnit=u2;
		if (u1) u1.nextUnit=subArray.firstUnit; else this.firstUnit=subArray.firstUnit;
		if (u2) u2.prevUnit=subArray.lastUnit; else this.lastUnit=subArray.lastUnit;
		this.length+=subArray.length;
	},
	deleteUnit : function(unit){
		var u1, u2;
		u1=unit.prevUnit;
		u2=unit.nextUnit;
		if (u1) u1.nextUnit=u2; else this.firstUnit=u2;
		if (u2) u2.prevUnit=u1; else this.lastUnit=u1;
		this.length--;
		delete unit;
	},
	insertWithData : function(data){
		var found=false;
		var _unit=this.firstUnit;
		while (_unit){
			if (_unit.data==data){
				found=true;
				break;
			}
			_unit=_unit.nextUnit;
		}

		var newUnit={};
		newUnit.data=data;
		if (!found) this.insertUnit("end", null, newUnit);
	},
	deleteByData : function(data){
		var _unit=this.firstUnit;
		while (_unit){
			if (_unit.data==data){
				this.deleteUnit(_unit);
				break;
			}
			_unit=_unit.nextUnit;
		}
	}
}

pArray._clearAll = function(pArr){
	var unit=pArr.firstUnit;
	var _unit;
	while (unit){
		_unit=unit;
		unit=unit.nextUnit;
		if (_unit.data) delete _unit.data;
		delete _unit;
	}
	pArr.firstUnit=null;
	pArr.lastUnit=null;
	pArr.length=0;
}

/*  ParameterSet  */

var ParameterSet = Class.create();

ParameterSet.prototype = {
	initialize : function() {
		this._parameters = [];
	},

	//增加一个参数对象
	_addParameter : function(name) {
		parameter = {};
		parameter.dataType = "string";
		parameter.name = name;
		var property = "__" + name;
		var _parameters = this._parameters;
		_parameters[property] = parameter;
		_parameters[_parameters.length] = parameter;
		return parameter;
	},

	//删掉一个参数对象
	delParameter : function(name) {
	    var startIndex = null;
	    var old_parameters = this._parameters;
	    this._parameters = [];
	    for(var i=0; i<old_parameters.length; i++){
	      if(old_parameters[i].name!=name){
	        this.setDataType(old_parameters[i].name, old_parameters[i].dataType);
	        this.setValue(old_parameters[i].name, old_parameters[i].value);
	      }
	    }
	},

	//获取一个参数对象
	_getParameter : function(name) {
		var _parameters = this._parameters;
		if (typeof(name) == "number"){
			var index = System.getInt(name);
			var parameter = _parameters[index];
			return parameter;
		}
		else{
			var property = "__" + name;
			var parameter = _parameters[property];
			return parameter;
		}
	},

	//返回参数的个数
	count : function() {
		return this._parameters.length;
	},

	//通过参数的index获取参数的名称
	indexToName : function(index) {
		var parameter = this._getParameter(index);
		if (parameter) {
			return parameter.name;
		}
	},

	//给指定参数设置参数值
	setValue : function(name, value) {
		var parameter = this._getParameter(name);
		if (!parameter && typeof(name) != "number") {
			parameter = this._addParameter(name);
		}
		if (parameter){
			parameter.value = value;
		}
	},

	//获取指定参数的值
	getValue : function(name) {
		var parameter = this._getParameter(name);
		if (parameter) {
			return parameter.value;
		}
	},

	/*
	date:
	number:
	boolean:
	string:
	*/
	//指定参数名称和参数的数据类型增加一个参数（增加参数以后必须通过setValue给参数设置参数值）
	setDataType : function(name, dataType) {
		var parameter = this._getParameter(name);
		if (!parameter && typeof(name) != "number") {
			parameter = this._addParameter(name);
		}
		if (parameter){
			parameter.dataType = dataType;
		}
	},

	//获取指定参数的数据类型
	getDataType : function(name) {
		var parameter = this._getParameter(name);
		if (parameter) {
			return parameter.dataType;
		}
	}
}

/* Field */

var Field = Class.create();
Field.prototype = {

  initialize : function(){},

	getName : function() {
		return this.name;
	},

	getLabel : function() {
		return this.label;
	},

	getDataType : function() {
		return this.dataType;
	},

	getEditorType : function() {
		return this.editorType;
	},

	isReadOnly : function() {
		return this.readOnly;
	},

	setReadOnly : function(readOnly) {
		var dataset = this.dataset;
		dataset.setFieldReadOnly(this.name, readOnly);
	},

	getDefaultValue : function() {
		return this.defauleValue;
	},

	setDefaultValue : function(defauleValue) {
		this.defauleValue=defauleValue;
	},

	isRequired : function() {
		return this.required;
	},

	setRequired : function(required) {
		this.required=required;
	},


	getFormat : function() {
		return this.format;
	},
	setFormat : function(format) {
		this.format=format;
	},

	isValueProtected : function() {
		return this.valueProtected;
	},

	setValueProtected : function(valueProtected) {
		this.valueProtected=valueProtected;
	},

	isVisible : function() {
		return this.visible;
	},

	getDropDown : function() {
		return this.dropDown;
	},

	getTag : function() {
		return this.tag;
	},

	setTag : function(tag) {
		this.tag = tag;
	},

	getToolTip : function() {
		return this.toolTip;
	},

	setToolTip : function(toolTip) {
		this.toolTip = toolTip;
	},

	getAlign : function() {
		return this.align;
	},

	setAlign : function(align) {
		this.align = align;
	},

	getVAlign : function() {
		return this.valign;
	},
	setVAlign : function(valign) {
		this.valign = valign;
	},	
	getIsResult : function() {
		return this.isResult;
	},
	setResult : function(isResult) {
		this.isResult = isResult;
	},
	getResultCode:function(){
		return this.resultCode;
	},
	setResultCode : function(resultCode) {
		this.resultCode = resultCode;
	}
	
	
	
}

/* Record */


Record = {

	getValue : function(fieldName){
		var record = this;
		var dataset=record.dataset;
		var fields=record.fields;
		var fieldIndex=-1;
		var value;
		if (typeof(fieldName)=="number"){
			fieldIndex=fieldName;
		}
		else if (typeof(fieldName)=="string"){
			fieldIndex=fields["_index_"+fieldName];
		}
		var field=fields[fieldIndex];
		if (typeof(field)=="undefined"){
			return "";
		}
		value=record[fieldIndex];
		if (typeof(value)=="undefined" || value==null || (typeof(value)=="number" && isNaN(value))) {
			value="";
		}
		return value;
	},

	getString : function(fieldName){
		var record=this, field, value="";
		var field=record.dataset.getField(fieldName);

		if (field){
			value=record.getValue(fieldName);

			if (value!="") {
				switch (field.dataType){
					case "int":{
						if (!isNaN(value)) value=value+"";
						break;
					}
					default:{
						value=System.getValidStr(value);
						break;
					}
				}
			}
		}
		return value;
	},

	_setValue : function(fieldName, value){
		var record = this;

		var dataset=record.dataset;
		var fields=record.fields;
		var fieldIndex=-1;

		if (typeof(fieldName)=="number"){
			fieldIndex=fieldName;
		}
		else if (typeof(fieldName)=="string"){
			fieldIndex=fields["_index_"+fieldName];
		}

		if (typeof(fields[fieldIndex])=="undefined"){
			throw Const.ErrCantFindField.replace("%s", record.dataset.id+"."+fieldName);
		}

		var field=fields[fieldIndex];
		if(field.dataType=="int")
		{
			if (typeof(value)!="number") {
				value = System.getInt(value);
			}
		}

		record[fieldIndex]=value;
	},

	setValue : function(fieldName, value){
		var record = this;

		var dataset=record.dataset;
		var fields=record.fields;
		var fieldIndex=-1;

		if (typeof(fieldName)=="number"){
			fieldIndex=fieldName;
		}
		else if (typeof(fieldName)=="string"){
			fieldIndex=fields["_index_"+fieldName];
		}

		if (typeof(fields[fieldIndex])=="undefined"){
			throw Const.ErrCantFindField.replace("%s", record.dataset.id+"."+fieldName);
		}

		var field=fields[fieldIndex];
		if(field.dataType=="int")
		{
			if (typeof(value)!="number") {
				value = System.getInt(value);
			}
		}


		record[fieldIndex]=value;
		dataset.modified=true;

		if (dataset.state=="none"){
			dataset.state="modify";
			Dataset.broadcastDatasetMsg(Dataset.notifyStateChanged, dataset, dataset.record);
		}
		Dataset.broadcastFieldMsg(Dataset.notifyFieldDataChanged, dataset, record, field);
	},

	getPrevRecord : function(){
		var record=this;
		while (record){
			record=record.prevUnit;
			if (Dataset.isRecordValid(record)) return record;
		}
	},

	getNextRecord : function(){
		var record=this;
		while (record){
			record=record.nextUnit;
			if (Dataset.isRecordValid(record)) return record;
		}
	},

	getDataset : function(){
		return this.dataset;
	},

	getPageIndex : function(){
		return this.pageIndex;
	},

	getState : function(){
		return this.recordState;
	},

	setState : function(state) {
		this.recordState = state;
	},

    resetState : function(record){
		if (record.recordState=="delete") {
			record.recordState="discard";
		}
		else if (record.recordState!="discard") {
			record.recordState="none";
		}
	},
  
  //设置表格行记录字段的只读属性
  setFieldReadOnly: function(table,cellName,readOnly){
    var cell = this.getCell(table,cellName);
    if(cell){
      cell.disabled=readOnly;
     
    }
  },
  
  //获取表格行记录字段的索引
  getCellIndex: function(table,cellName){
    var cellIndex=null;
    var record=this;
    
    var row=Datatable.getTableRowByRecord(table,record);
    if (typeof(cellName)=="number"){
      cellIndex=cellName;
    }
    else if (typeof(cellName)=="string"){
      for(var i=0; (row) && (row.cells) && i<row.cells.length; i++){
        if(row.cells[i].name==cellName){
          cellIndex=i;
          break;
        }
      }
    }else{
      alert("参数[cellName]类型错误");
    }
    return cellIndex;
  },
  
  //获取表格行单元格
  getCell: function(table,cellName){
    var cell=null;
    var record=this;
    
    var row=Datatable.getTableRowByRecord(table,record);
    if (typeof(cellName)=="number"){
      cell=row.cell[cellName];
    }
    else if (typeof(cellName)=="string"){
      for(var i=0; (row) && (row.cells) && i<row.cells.length; i++){
        if(row.cells[i].name==cellName){
          cell=row.cells[i];
          break;
        }
      }
    }else{
      alert("参数[cellName]类型错误");
    }
    return cell;
  }
  
}

function initRecord(record, dataset){
	record.dataset=dataset;
	record.fields=dataset.fields;
	record.pageIndex=dataset.pageIndex;
	record.visible=true;

	record.getValue=Record.getValue;
	record.getString=Record.getString;
	record.setValue=Record.setValue;

	record.getPrevRecord=Record.getPrevRecord;
	record.getNextRecord=Record.getNextRecord;

	record.getDataset=Record.getDataset;
	record.getPageIndex=Record.getPageIndex;
	record.getState=Record.getState;
	record.setState=Record.setState;

	record._setValue=Record._setValue;
  
  record.getCell=Record.getCell;
  record.getCellIndex=Record.getCellIndex;
  record.setFieldReadOnly=Record.setFieldReadOnly;
  
	for(var j=0; j<record.length-1; j++){
		if (record[j] && record[j]!="") {
			switch (dataset.getField(j).dataType){
				case "int":{
					record[j]=System.getInt(record[j]);
					break;
				}
				default:{
					record[j]=System.getDecodeStr(record[j]);
					break;
				}
			}
		}
	}
}

function createDataset(opt) {
    var dataset = new pArray();

		dataset.type = opt.t || 'reference' ;
		dataset.readOnly = opt.r || false ;
		dataset.editable = opt.e || false ;
		
		if(typeof(opt.async) == 'undefined' )
    		dataset.async=  true;
    	else
        	dataset.async=  opt.async;	
    
		dataset.loadDataAction = opt.la || '' ;
		dataset._queryType = opt.qt || 'dataset' ;
		dataset.paginate = opt.p || false ;
		if(typeof(opt.sd) == 'undefined' )
    		dataset.staticDataSource=  true;
    	else
        	dataset.staticDataSource=  opt.sd;	
        	
		dataset.xmlFormat = opt.x || 'records' ;
		dataset.loadAsNeeded = opt.ln || false ;
		dataset.masterDataset = opt.md || '' ;
		dataset.masterKeyFields = opt.mf || '' ;
		dataset.detailKeyFields = opt.df || '' ;
		
		dataset.loadDataActionMethod = opt.lm || '' ;
				
    dataset.fields = [];
    dataset.fields.count = Dataset.field_count;
    dataset._parameters = new ParameterSet();

    dataset._queryFields = [];//用于向服务器端传递需要获取的数据字段

    dataset.updateItems = [];
    dataset.fields.fieldCount = 0;
    dataset.pageSize = opt.ps || 9999;
    dataset.pageCount = opt.pc || 1;
    dataset.pageIndex = opt.pi || 1;
    dataset.recordCount = opt.rc || 0 ;
    dataset.autoLoadPage = opt.al || false;
    dataset.disableControlCount = 0;
    dataset.disableEventCount = 0;

    dataset.maskControl = opt.mc || false;

	dataset.addField=Dataset.prototype.addField;

	dataset.getPrevRecord=Record.getPrevRecord;
	dataset.getNextRecord=Record.getNextRecord;

	dataset.getId=Dataset.prototype.getId;
	dataset.isFirst=Dataset.prototype.isFirst;
	dataset.isLast=Dataset.prototype.isLast;
	dataset.isAutoLoadPage=Dataset.prototype.isAutoLoadPage;
	dataset.getDetailDatasets=Dataset.prototype.getDetailDatasets;
	dataset.getDisableControlCount=Dataset.prototype.getDisableControlCount;
	dataset.getDisableEventCount=Dataset.prototype.getDisableEventCount;
	dataset.getEditors=Dataset.prototype.getEditors;
	dataset.fieldSet=Dataset.prototype.fieldSet;
	dataset.isModified=Dataset.prototype.isModified;
	dataset.getPageCount=Dataset.prototype.getPageCount;
	dataset.getPageSize=Dataset.prototype.getPageSize;
	dataset.setPageIndex=Dataset.prototype.setPageIndex;
	dataset.getPageIndex=Dataset.prototype.getPageIndex;
	dataset.getCurrent=Dataset.prototype.getCurrent;
	dataset.setCurrent=Dataset.prototype.setCurrent;
	dataset.getState=Dataset.prototype.getState;
	dataset.getMasterDataset=Dataset.prototype.getMasterDataset;
	dataset.getTag=Dataset.prototype.getTag;
	dataset.setTag=Dataset.prototype.setTag;
	dataset.getWindow=Dataset.prototype.getWindow;

	dataset.getField=Dataset.prototype.getField;
	dataset.getFieldCount=Dataset.prototype.getFieldCount;
	dataset.getValue=Dataset.prototype.getValue;
	dataset.getString=Dataset.prototype.getString;
	dataset.setValue=Dataset.prototype.setValue;

	dataset.disableControls=Dataset.prototype.disableControls;
	dataset.enableControls=Dataset.prototype.enableControls;
	dataset.disableEvents=Dataset.prototype.disableEvents;
	dataset.enableEvents=Dataset.prototype.enableEvents;
	dataset.refreshControls=Dataset.prototype.refreshControls;
	dataset.setRecord=Dataset.prototype.setRecord;
	dataset.setReadOnly=Dataset.prototype.setReadOnly;
	dataset.setFieldReadOnly=Dataset.prototype.setFieldReadOnly;
	dataset.getFirstRecord=Dataset.prototype.getFirstRecord;
	dataset.getLastRecord=Dataset.prototype.getLastRecord;
	dataset.move=Dataset.prototype.move;
	dataset.movePrev=Dataset.prototype.movePrev;
	dataset.moveNext=Dataset.prototype.moveNext;
	dataset.moveFirst=Dataset.prototype.moveFirst;
	dataset.moveLast=Dataset.prototype.moveLast;
	dataset.find=Dataset.prototype.find;
	dataset.locate=Dataset.prototype.locate;
	//dataset.postRecord=Dataset.prototype.postRecord;

	dataset.insertRecord=Dataset.prototype.insertRecord;
	dataset.deleteRecord=Dataset.prototype.deleteRecord;
	dataset.copyRecord=Dataset.prototype.copyRecord;
	dataset.loadPage=Dataset.prototype.loadPage;
	dataset.loadDetail=Dataset.prototype.loadDetail;
	dataset.isPageLoaded=Dataset.prototype.isPageLoaded;
	dataset.moveToPage=Dataset.prototype.moveToPage;
	dataset.setMasterDataset=Dataset.prototype.setMasterDataset;
	dataset.flushData=Dataset.prototype.flushData;
	dataset.clearData=Dataset.prototype.clearData;

	dataset.parameters=Dataset.prototype.parameters;

	dataset.getCount=Dataset.prototype.getCount;
	dataset.selectAllData=Dataset.prototype.selectAllData;
	dataset.reloadData=Dataset.prototype.reloadData;
	dataset.fetchData=Dataset.prototype.fetchData;
	dataset.getRecordCount=Dataset.prototype.getRecordCount;
	dataset.setPopupEnable=Dataset.prototype.setPopupEnable;
	dataset.setFieldPopupEnable=Dataset.prototype.setFieldPopupEnable;
	dataset.insertRecordWithValidate=Dataset.prototype.insertRecordWithValidate;
	dataset.validDataset=Dataset.prototype.validDataset;
	dataset.clearParameters=Dataset.prototype.clearParameters;
	dataset.setLoadDataAction=Dataset.prototype.setLoadDataAction;
	dataset.setLoadDataActionMethod=Dataset.prototype.setLoadDataActionMethod;

	dataset._setValue=Dataset.prototype._setValue;

    dataset.paginateFlushData=Dataset.prototype.paginateFlushData;
    dataset.isFieldMaskControl=Dataset.prototype.isFieldMaskControl;
    dataset.refreshFieldMaskControls=Dataset.prototype.refreshFieldMaskControls;
    dataset.judgeFieldMaskControl=Dataset.prototype.judgeFieldMaskControl;
    dataset.callFieldMaskControl=Dataset.prototype.callFieldMaskControl;

    if (opt.id) {
        dataset.id = opt.id;
        Document.array_dataset[Document.array_dataset.length] = dataset;
    }
    return dataset;
}
/**
function createDataset(ID) {
    var dataset = new pArray();

    dataset.fields = [];
    dataset.fields.count = Dataset.field_count;
    dataset._parameters = new ParameterSet();

    dataset._queryFields = [];//用于向服务器端传递需要获取的数据字段

    dataset.updateItems = [];
    dataset.fields.fieldCount = 0;
    dataset.pageSize = 9999;
    dataset.pageCount = 1;
    dataset.pageIndex = 1;
    dataset.recordCount = 0 ;
    dataset.autoLoadPage = false;
    dataset.disableControlCount = 0;
    dataset.disableEventCount = 0;

    dataset.maskControl = false;

	dataset.addField=Dataset.prototype.addField;

	dataset.getPrevRecord=Record.getPrevRecord;
	dataset.getNextRecord=Record.getNextRecord;

	dataset.getId=Dataset.prototype.getId;
	dataset.isFirst=Dataset.prototype.isFirst;
	dataset.isLast=Dataset.prototype.isLast;
	dataset.isAutoLoadPage=Dataset.prototype.isAutoLoadPage;
	dataset.getDetailDatasets=Dataset.prototype.getDetailDatasets;
	dataset.getDisableControlCount=Dataset.prototype.getDisableControlCount;
	dataset.getDisableEventCount=Dataset.prototype.getDisableEventCount;
	dataset.getEditors=Dataset.prototype.getEditors;
	dataset.fieldSet=Dataset.prototype.fieldSet;
	dataset.isModified=Dataset.prototype.isModified;
	dataset.getPageCount=Dataset.prototype.getPageCount;
	dataset.getPageSize=Dataset.prototype.getPageSize;
	dataset.setPageIndex=Dataset.prototype.setPageIndex;
	dataset.getPageIndex=Dataset.prototype.getPageIndex;
	dataset.getCurrent=Dataset.prototype.getCurrent;
	dataset.setCurrent=Dataset.prototype.setCurrent;
	dataset.getState=Dataset.prototype.getState;
	dataset.getMasterDataset=Dataset.prototype.getMasterDataset;
	dataset.getTag=Dataset.prototype.getTag;
	dataset.setTag=Dataset.prototype.setTag;
	dataset.getWindow=Dataset.prototype.getWindow;

	dataset.getField=Dataset.prototype.getField;
	dataset.getFieldCount=Dataset.prototype.getFieldCount;
	dataset.getValue=Dataset.prototype.getValue;
	dataset.getString=Dataset.prototype.getString;
	dataset.setValue=Dataset.prototype.setValue;

	dataset.disableControls=Dataset.prototype.disableControls;
	dataset.enableControls=Dataset.prototype.enableControls;
	dataset.disableEvents=Dataset.prototype.disableEvents;
	dataset.enableEvents=Dataset.prototype.enableEvents;
	dataset.refreshControls=Dataset.prototype.refreshControls;
	dataset.setRecord=Dataset.prototype.setRecord;
	dataset.setReadOnly=Dataset.prototype.setReadOnly;
	dataset.setFieldReadOnly=Dataset.prototype.setFieldReadOnly;
	dataset.getFirstRecord=Dataset.prototype.getFirstRecord;
	dataset.getLastRecord=Dataset.prototype.getLastRecord;
	dataset.move=Dataset.prototype.move;
	dataset.movePrev=Dataset.prototype.movePrev;
	dataset.moveNext=Dataset.prototype.moveNext;
	dataset.moveFirst=Dataset.prototype.moveFirst;
	dataset.moveLast=Dataset.prototype.moveLast;
	dataset.find=Dataset.prototype.find;
	dataset.locate=Dataset.prototype.locate;
	//dataset.postRecord=Dataset.prototype.postRecord;

	dataset.insertRecord=Dataset.prototype.insertRecord;
	dataset.deleteRecord=Dataset.prototype.deleteRecord;
	dataset.copyRecord=Dataset.prototype.copyRecord;
	dataset.loadPage=Dataset.prototype.loadPage;
	dataset.loadDetail=Dataset.prototype.loadDetail;
	dataset.isPageLoaded=Dataset.prototype.isPageLoaded;
	dataset.moveToPage=Dataset.prototype.moveToPage;
	dataset.setMasterDataset=Dataset.prototype.setMasterDataset;
	dataset.flushData=Dataset.prototype.flushData;
	dataset.clearData=Dataset.prototype.clearData;

	dataset.parameters=Dataset.prototype.parameters;

	dataset.getCount=Dataset.prototype.getCount;
	dataset.selectAllData=Dataset.prototype.selectAllData;
	dataset.reloadData=Dataset.prototype.reloadData;
	dataset.fetchData=Dataset.prototype.fetchData;
	dataset.getRecordCount=Dataset.prototype.getRecordCount;
	dataset.setPopupEnable=Dataset.prototype.setPopupEnable;
	dataset.setFieldPopupEnable=Dataset.prototype.setFieldPopupEnable;
	dataset.insertRecordWithValidate=Dataset.prototype.insertRecordWithValidate;
	dataset.validDataset=Dataset.prototype.validDataset;
	dataset.clearParameters=Dataset.prototype.clearParameters;
	dataset.setLoadDataAction=Dataset.prototype.setLoadDataAction;
	dataset.setLoadDataActionMethod=Dataset.prototype.setLoadDataActionMethod;

	dataset._setValue=Dataset.prototype._setValue;

    dataset.paginateFlushData=Dataset.prototype.paginateFlushData;
    dataset.isFieldMaskControl=Dataset.prototype.isFieldMaskControl;
    dataset.refreshFieldMaskControls=Dataset.prototype.refreshFieldMaskControls;
    dataset.judgeFieldMaskControl=Dataset.prototype.judgeFieldMaskControl;
    dataset.callFieldMaskControl=Dataset.prototype.callFieldMaskControl;

    if (ID) {
        dataset.id = ID;
        Document.array_dataset[Document.array_dataset.length] = dataset;
    }
    return dataset;
}

*/

//通过QueryCommand对象获取XML数据，并将数据填充到Dataset中
function fillDataset(dataset , masterDataset){
	if (dataset.prepared) {
    return;
  }
  dataset.window = window;
  dataset._bof = true;
  dataset._eof = true;
  dataset.state = "none";
  dataset.readOnly = System.isTrue(dataset.readOnly);

  dataset.loadedDetail = [];
  dataset.loadedPage = [];

  if (dataset.pageIndex > 0) {
      dataset.loadedPage[dataset.pageIndex - 1] = true;
  }

  if( dataset.paginate ){
		//当前的页数
		var	pageIndex = dataset.pageIndex;
		if( !pageIndex ){
			pageIndex = 1;
		}
		//获取每一页的大小
		var pageSize = dataset.pageSize;
		if( !pageSize ){
			pageSize = 0;
		}
		//将页数作为参数传递给服务器端
		dataset.parameters().delParameter("pageIndex");
		dataset.parameters().delParameter("pageSize");
		dataset.parameters().setDataType("pageIndex", "int");
		dataset.parameters().setValue("pageIndex",pageIndex);
		dataset.parameters().setDataType("pageSize", "int");
		dataset.parameters().setValue("pageSize",pageSize);
	}

  //创建QueryCommand对象，并通过QueryCommand对象执行查询操作
  if( Global.onServer && !dataset.staticDataSource && dataset.loadDataAction!=undefined){//如果是在服务器端运行，则获取服务器端的数据。
		var cmd = new QueryCommand();
		cmd.setDataset( dataset ) ;//将dataset组件传递进cmd对象，在cmd的回调函数中对dataset进行初始化
		cmd.execute();//执行查询
	}
	else
	{
		eval("var isXmlExist=(typeof(__" + dataset.id + ")==\"object\")");
		if (isXmlExist) {
			eval("var xmlIsland=__" + dataset.id);//获取页面上的XML数据岛，要求在页面上必须提供一个空的XML数据岛，id名称为"__" + dataset的ID
		}
		else{
		  if(dataset.xmlFormat == "items"){
		    var dropdownObjects = eval("___"+dataset.id);
		  }
		}

		if( (typeof(xmlIsland)=="undefined" || xmlIsland==null)
		 && (typeof(dropdownObjects)=="undefined" || dropdownObjects==null) )
		  return;

		dataset.disableControls();

		if (dataset.xmlFormat == "records" && isXmlExist) {//如果是<records>格式的XML
			var current = Dataset.appendFromXml(dataset, xmlIsland.documentElement, true);
			dataset.prepared = true;
	 	}else if (dataset.xmlFormat == "items") {//如果是<items>格式的XML
			//如果是客户端，直接读xml
			if(typeof(dropdownObjects)!="undefined" && dropdownObjects!=null){
			  for(var i=0; i<dropdownObjects.length; i++){
			    var record = dropdownObjects[i];
			    dataset.insertUnit("end", null, record);
			    initRecord(record, dataset);
			  }
			}
			else{
				root=xmlIsland.documentElement;
				if (root) {
					var itemNodes = root.childNodes;
					for (var i=0; i<itemNodes.length; i++) {
						
						var itemNode = itemNodes.item(i);
						
						var v = itemNode.getAttribute("value") || itemNode.getAttribute("v") ;
						var l = itemNode.getAttribute("label") || itemNode.getAttribute("l") ;
						var vi = itemNode.getAttribute("valueId") || itemNode.getAttribute("vi") ;
						
						var record = [v,vi,l];
						dataset.insertUnit("end", null, record);
						initRecord(record, dataset);
					}
				}
			}
		}
		//释放内存
		if( !(typeof(xmlIsland)=="undefined" || xmlIsland==null) )
		{
			if(xmlIsland.removeNode)
				xmlIsland.removeNode(true);
			xmlIsland = null;
		}
		if (current) {
			dataset.setRecord(current);
		} else {
			if (dataset.pageIndex == 1 || !dataset.autoLoadPage) {
				dataset.moveFirst();
			} else {
				dataset.setRecord(dataset.getFirstRecord());
			}
		}

		dataset.enableControls();

	}
	
	if (masterDataset) {
	       dataset.setMasterDataset(dataset.masterDataset, dataset.masterKeyFields, dataset.detailKeyFields);
	}
}
//对fillDataset 函数简写
function fd(dataset , masterDataset){
	fillDataset(dataset , masterDataset) ;
}
var Dataset = Class.create();

Dataset.prototype = {

getId:function () {
    return this.id;
},

isFirst:function () {
    return this._bof;
},

isLast:function () {
    return this._eof;
},

isAutoLoadPage:function () {
    return this.autoLoadPage;
},

getDetailDatasets:function () {
    return this.detailDatasets;
},

getDisableControlCount:function () {
    return this.disableControlCount;
},

getDisableEventCount:function () {
    return this.disableEventCount;
},

getEditors:function () {
    return this.editors;
},

fieldSet:function () {
    return this.fields;
},

isModified:function () {
    return this.modified;
},

getPageCount:function () {
    return this.pageCount;
},

/**
这个函数在分页的情况下使用,表示符合查询条件的记录的总数,而不是当前
Dataset里面的记录总数,在分页的情况下,数据库里面符合查询条件的记录可能
有10000条,但是如果定义Dataset的pageSize为30的话,Dataset里其实只有30个记
录,这个方法返回的不是Dataset里当前的记录数,而是数据库里符合查询条件的
记录数
*/
getRecordCount:function(){
	return this.recordCount;
},

/*
Add 2006-04-20
这个函数返回当前Dataset里的记录总数.
*/
getCount:function(){
    var count = 0;
	var record=this.firstUnit;
	if(Dataset.isRecordValid(record)) count++;
	while (record){
		record=record.nextUnit;
		if (Dataset.isRecordValid(record)) count++;
	}
	return count;
},

getPageSize:function () {
    return this.pageSize;
},

setPageIndex:function (pageIndex) {
    this.pageIndex = pageIndex;
},

setAsync:function( async ){
	this.async = async ;
},

getAsync:function(){
	return this.async ;
},

getPageIndex:function () {
    return this.pageIndex;
},

getCurrent:function () {
    return this.record;
},

setCurrent:function (record) {
    record.dataset.setRecord(record);
},

getState:function () {
    return this.state;
},

getMasterDataset:function () {
    return this.masterDataset;
},

getTag:function () {
    return this.tag;
},

setTag:function (tag) {
    this.tag = tag;
},

getWindow:function () {
    return this.window;
},

getLoadDataAction:function () {
    return this.loadDataAction;
},

setLoadDataAction:function (loadDataAction) {
    this.loadDataAction = loadDataAction;
},

getLoadDataActionMethod:function(){
	return this.loadDataActionMethod;
},

setLoadDataActionMethod:function( loadDataActionMethod ){
	this.loadDataActionMethod = loadDataActionMethod ;
},

getField:function (name) {
    var dataset = this;
    return Dataset.getField(dataset.fields, name);
},

getFieldCount:function () {
    var dataset = this;
    return dataset.fields.count();
},

getValue:function (fieldName) {
    var dataset = this;
    if (dataset.record) {
        return dataset.record.getValue(fieldName);
    } else {
        return "";
    }
},

getString:function (fieldName) {
    var dataset = this;
    if (dataset.record) {
        return dataset.record.getString(fieldName);
    } else {
        return "";
    }
},

//给Dataset的当前记录的指定的字段名的字段设置值。
_setValue:function (fieldName, value) {

        var dataset = this;
        if (!dataset.record) {
        	var newRecord=[];
		    //在Dataset的链表结构中的当前记录插入一个记录
		    dataset.insertUnit("end", null, newRecord);
		    initRecord(newRecord, dataset);
		    dataset.record = newRecord;
        }
        if (dataset.record) {
            dataset.record._setValue(fieldName, value);
        } else {
            throw Const.ErrNoCurrentRecord;
        }

},

//给Dataset的当前记录的指定的字段名的字段设置值。
setValue:function (fieldName, value) {

        var dataset = this;
        if (!dataset.record) {
        	var newRecord=[];
		    //在Dataset的链表结构中的当前记录插入一个记录
		    dataset.insertUnit("end", null, newRecord);
		    initRecord(newRecord, dataset);
		    dataset.record = newRecord;
        }
        if (dataset.record) {
            dataset.record.setValue(fieldName, value);
        } else {
            throw Const.ErrNoCurrentRecord;
        }

},

disableControls:function () {
    var dataset = this;
    dataset.disableControlCount = dataset.disableControlCount + 1;
},

enableControls:function () {
    var dataset = this;
    dataset.disableControlCount = (dataset.disableControlCount > 0) ? dataset.disableControlCount - 1 : 0;
    dataset.refreshControls();
},

disableEvents:function () {
    var dataset = this;
    dataset.disableEventCount = dataset.disableEventCount + 1;
},

enableEvents:function () {
    var dataset = this;
    dataset.disableEventCount = (dataset.disableEventCount > 0) ? dataset.disableEventCount - 1 : 0;
},

refreshControls:function () {
    var dataset = this;
    //页面初始化完毕前，非下拉动态数据集不用刷新
    if(!Control.documentInitialized && !dataset.staticDataSource && dataset.xmlFormat=="records")
      return;
    Dataset.validateCursor(dataset);
    dataset.loadDetail();
    //var startDateF = new Date();
    Dataset.broadcastDatasetMsg(Dataset.notifyRefresh, dataset);
	//jsDebug.debug("..3333333333...... :  "+dataset.id+" "+((new Date())-startDateF));
},

setRecord:function (record) {

    Dataset.setRecord(this, record);

},

setReadOnly:function (readOnly) {
    var dataset = this;
    dataset.readOnly = readOnly;
    for(var i=0; i<dataset.fields.fieldCount; i++){
        if(dataset.isFieldMaskControl(dataset.fields[i].getName())){
          	dataset.fields[i].readOnly = true;
        }else{
	        if(Control.before_Page_onLoad){
	          	if(!dataset.fields[i].readOnly){
	            	dataset.fields[i].readOnly = readOnly;
	          	}
	        }
	        else{
	          	dataset.fields[i].readOnly = readOnly;
	        }
        }
    }
    Dataset.broadcastDatasetMsg(Dataset.notifyStateChanged, dataset);
},

refreshFieldMaskControls:function(){
	var dataset = this;
	if(dataset.maskControl && dataset.maskControlFields){
		for(var i=0; i<dataset.maskControlFields.length; i++){
			dataset.setFieldPopupEnable(dataset.maskControlFields[i], false);
		}
	}
},

isFieldMaskControl:function(fieldName){
	var result = false;
	var dataset = this;
	if(dataset.maskControl && dataset.maskControlFields){
		for(var i=0; i<dataset.maskControlFields.length; i++){
			if(fieldName==dataset.maskControlFields[i]){
				result = true;
				break;
			}
		}
	}
	return result;
},

judgeFieldMaskControl:function(){
	var result = true;
	var dataset = this;
	if(dataset.maskControl && dataset.maskControlFields && dataset.maskControlFields.length>0){
		dataset.setReadOnly(true);
		dataset.setPopupEnable(true);
		alert("对不起，您的数据权限不足,无法操作记录。");
		result = false;
	}
	return result;
},

callFieldMaskControl:function(async, callback){
	var dataset = this;
	var ajaxCallForMaskFields = new NDAjaxCall(async);
	ajaxCallForMaskFields.remoteCall("PrivilegeService", "getCustCtrlDataInfo", [Global.pageId, dataset.id, Global.custTypeId], function(reply){
		dataset.maskControlFields = reply.getResult();
		//dataset.maskControlFields = ["custName", "custDeptType", "typeFlag", "contactName","acctId", "custLocation", "importanceLevel", "latentVipFlag", "certificateNo"];
		dataset.refreshFieldMaskControls();
		if(callback)
			callback();
	});
},

setFieldReadOnly:function (fieldName, readOnly) {
    var dataset = this;
    var field = dataset.getField(fieldName);
    if (field) {
    	if(dataset.isFieldMaskControl(fieldName)){
          	dataset.fields[i].readOnly = true;
        }
        else{
        	field.readOnly = readOnly;
        }
        Dataset.broadcastFieldMsg(Dataset.notifyFieldStateChanged, dataset, dataset.record, field);
    } else {
        throw Const.ErrCantFindField.replace("%s", dataset.id + "." + fieldName);
    }
},

setPopupEnable:function (enable) {
    var dataset = this;
    for(var i=0; i<dataset.fields.fieldCount; i++){
      var field = dataset.fields[i];
      dataset.setFieldPopupEnable(field.getName(), enable);
    }
},

setFieldPopupEnable:function (fieldName, enable) {
    var dataset = this;
    var field = dataset.getField(fieldName);
    if (field) {
    	if(enable){
    		if(dataset.isFieldMaskControl(fieldName))
    		    return;
    	}
        var popup = $("button_"+dataset.id+"_"+fieldName);
        if(popup){
            popup.disabled = !System.isTrue(enable);
            if(popup.className!="coolButton2")
            	popup.className = (System.isTrue(enable) ? "popupButton" : "popupButton_disable");
        }
        var reset = $("button_reset_"+dataset.id+"_"+fieldName);
        if(reset){
            reset.disabled = !System.isTrue(enable);
            if(reset.className!="coolButton2")
            	reset.className = (System.isTrue(enable) ? "resetButton" : "resetButton_disable");
        }
    }
},

getFirstRecord:function () {
    return Dataset.getFirstRecord(this);
},

getLastRecord:function () {
    return Dataset.getLastRecord(this);
},

move:function (count) {
    var dataset = this;
    Dataset.move(dataset, count);
},

movePrev:function () {
    var dataset = this;
    Dataset.move(dataset, -1);
},

moveNext:function () {
    var dataset = this;
    Dataset.move(dataset, 1);
},

moveFirst:function () {
    var dataset = this;
    if (!dataset.isPageLoaded(1)) {
        Dataset.loadPage(dataset, 1);
    }
    Dataset.do_setRecord(dataset, dataset.getFirstRecord());
    Dataset.setBofnEof(dataset, true, (!Dataset.isRecordValid(dataset.record)));
},

moveLast:function () {
    var dataset = this;
    if (!dataset.isPageLoaded(dataset.pageCount)) {
        Dataset.loadPage(dataset, dataset.pageCount);
    }
    Dataset.do_setRecord(dataset, dataset.getLastRecord());
    Dataset.setBofnEof(dataset, (!Dataset.isRecordValid(dataset.record)), true);
},

find:function (fieldNames, values, startRecord) {
    function isMatching(fieldNames, values, record) {
        var result = true;
        for (var j = 0; j < fieldNames.length && j < values.length; j++) {
            if (record.getString(fieldNames[j])!=values[j]) {
                result = false;
                break;
            }
        }
        return result;
    }
    if (!fieldNames || !values) {
        return false;
    }
    var dataset = this;
    if (!dataset.record) {
        return;
    }
    if (isMatching(fieldNames, values, dataset.record)) {
        return dataset.record;
    }
    var record = (startRecord) ? startRecord : dataset.getFirstRecord();
    while (record) {
        if (isMatching(fieldNames, values, record)) {
            return record;
        }
        record = record.getNextRecord();
    }
},

locate:function (fieldName, value, startRecord) {
    function isMatching(fieldName, value, record) {
        var tmpValue = record.getString(fieldName);
        return (tmpValue && System.compareText(tmpValue.substr(0, len), value));
    }
    if (!value) {
        return false;
    }
    var dataset = this;
    if (!dataset.record) {
        return;
    }

    //和当前记录进行比较
    if (isMatching(fieldName, value, dataset.record)) {
        return dataset.record;
    }
    var len = value.length;
    var record = (startRecord) ? startRecord : dataset.getFirstRecord();
    //循环比较
    while (record) {
        if (isMatching(fieldName, value, record)) {
            return record;
        }
        record = record.getNextRecord();
    }
},

/*
postRecord:function () {
    Dataset.postRecord(this);
    return true;
},
*/

validDataset : function(){
	return Dataset.validAllRecord(this);
},

insertRecordWithValidate:function (mode) {
    Dataset.insertRecord(this, mode, true);
},

insertRecord:function (mode) {
    Dataset.insertRecord(this, mode);
},

deleteRecord:function () {
    Dataset.deleteRecord(this);
},

copyRecord:function (record, fieldMap) {
    var dataset = this;
    Dataset.copyRecord(dataset, record, fieldMap);
},

loadPage:function (pageIndex) {
    var dataset = this;
    Dataset.loadPage(dataset, pageIndex);
},

loadDetail:function () {
    var dataset = this;
    Dataset.loadDetail(dataset);
},

isPageLoaded:function (pageIndex) {
    var dataset = this;
    return dataset.loadedPage[pageIndex - 1];
},

moveToPage:function (pageIndex) {
    var dataset = this;
    if (!dataset.isPageLoaded(pageIndex)) {
        Dataset.loadPage(dataset, pageIndex);
    }
    var record = dataset.getFirstRecord();
    while (record) {
        if (record.pageIndex >= pageIndex) {
            Dataset.setRecord(dataset, record);
            break;
        }
        record = record.getNextRecord();
    }
},

setMasterDataset:function (masterDataset, masterKeyFields, detailKeyFields) {
    var dataset = this;
    Dataset.setMasterDataset(dataset, masterDataset, masterKeyFields, detailKeyFields);
},
reloadData:function (callback) {
    var dataset = this;
    if(System.isTrue(dataset.paginate)){
      dataset.fetchData(null, callback);
    }
    else{
      Dataset.reloadData(dataset, callback);
    }
},
flushData:function (pageIndex) {
    var dataset = this;
    Dataset.flushData(dataset, pageIndex);
},
paginateFlushData:function (pageIndex) {
    var dataset = this;
    Dataset.paginateFlushData(dataset, pageIndex);
},
fetchData:function (pageIndex, callback) {
    var dataset = this;
    Dataset.fetchData(dataset, pageIndex, callback);
},
selectAllData:function(select){
	  var dataset = this;
	  //dataset.disableControls();

	  var record = dataset.getFirstRecord();
	  while(record){
	    record.setValue("select", System.isTrue(select));
	    record = record.getNextRecord();
	  }
	  //dataset.enableControls();
},

clearData:function () {
    var dataset = this;
    Dataset.clearData(dataset);
},

parameters:function () {
    return this._parameters;
},

clearParameters:function(){
    this._parameters = new ParameterSet();
},
addField:function (opt) {
    var dataset = this;

    dataset._queryFields[dataset._queryFields.length] = opt.fn;

    var field = new Field();
    var i = dataset.fields.length;
    dataset.fields["_index_" + opt.fn] = i;
    dataset.fields[i] = field;
    dataset.fields.fieldCount++;
    field.index = i;
    field.dataset = dataset;
    field.fields = dataset.fields;
    field.name = opt.fn;
    field.label = opt.l || opt.fn;
    field.fieldName = opt.fn;
    field.dataType = opt.dt;
    field.toolTip = "";

    field.extra = "field";

    field.format= opt.f || "";
  
    if(typeof(opt.v) == 'undefined' )
    	field.visible=  true;
    else
        field.visible=  opt.v;	
        
    field.readOnly= opt.r || false;
    field.editable= opt.e || false;
    field.required= opt.req || false;
    field.dropDown= opt.dd || "";

    field.validType=  opt.vt || "";
    field.validMsg= opt.vm || "";

    field.keyField= opt.kf || "";
    
    if(opt.df)
    	field.dbField = opt.df ;
    	
   if(opt.df)
    	field.dbField = opt.df ;
    if(opt.dv)
    	field.defaultValue = opt.dv ;
    	
    if(opt.s)
    	field.size = opt.s ;
    	
    if(opt.cb)
    	field.checkbox = opt.cb ;
    if(opt.rb)
    	field.radiobox = opt.rb ;
    						
    						
    if(opt.ca)
    	field.checkboxAttrCode = opt.ca ;
    if(opt.ra)
    	field.radioboxAttrCode = opt.ra ;
    	
    if(opt.d2)
    	field.dataType2 = opt.d2 ;
    	
    if(opt.cb)
    	field.checkbox = opt.cb ;
    if(opt.rb)
    	field.radiobox = opt.rb ;
    						
    field.subList="";
    field.isResult= opt.ir || "";
    field.resultCode="";

    switch (opt.dt) {
      case "int":
        field.editorType = "text";
        field.align = "right";
        field.vAlign = "top";
        break;
      default:
        field.editorType = "text";
        field.align = "left";
        field.vAlign = "top";
        break;
    }
    return field;

}
};
/**
addField:function (fieldName, dataType) {
    var dataset = this;

    dataset._queryFields[dataset._queryFields.length] = fieldName;

    var field = new Field();
    var i = dataset.fields.length;
    dataset.fields["_index_" + fieldName] = i;
    dataset.fields[i] = field;
    dataset.fields.fieldCount++;
    field.index = i;
    field.dataset = dataset;
    field.fields = dataset.fields;
    field.name = fieldName;
    field.label = fieldName;
    field.fieldName = fieldName;
    field.dataType = dataType;
    field.toolTip = "";

    field.extra = "field";

    field.format="";
    field.visible=true;
    field.readOnly=false;
    field.editable=false;
    field.required=false;
    field.dropDown="";

    field.validType="";
    field.validMsg="";

    field.keyField="";
    field.subList="";
    field.isResult="";
    field.resultCode="";

    switch (dataType) {
      case "int":
        field.editorType = "text";
        field.align = "right";
        field.vAlign = "top";
        break;
      default:
        field.editorType = "text";
        field.align = "left";
        field.vAlign = "top";
        break;
    }
    return field;

}
};
*/

/*  DatasetUtil */

Dataset.maxAutoGenID = 0;

Dataset.getAutoGenID = function(){
	Dataset.maxAutoGenID++;
	return "__"+Dataset.maxAutoGenID;
}

Dataset.downloadData2 = function(dataset, pageIndex, loadCallback){

	if (typeof(pageIndex)=="undefined") {
		pageIndex=dataset.pageIndex;
	}
    dataset.clearData();

    if( dataset.paginate ){
		//获取当前的页数
		if (!pageIndex)
			pageIndex = "1";

		//获取每一页的大小
	    var pageSize = dataset.pageSize;
	    if (!pageSize)
			pageSize = 0;
		//将页数作为参数传递给服务器端
		//alert("pageIndex number");
		dataset.parameters().delParameter("pageIndex");
		dataset.parameters().delParameter("pageSize");
		dataset.parameters().setDataType("pageIndex", "int");
		dataset.parameters().setValue("pageIndex", pageIndex);
		dataset.parameters().setDataType("pageSize", "int");
		dataset.parameters().setValue("pageSize",pageSize);
	}

	var callback = function(xml){
		var dataDoc = new ActiveXObject("Microsoft.XMLDOM");
		dataDoc.async=false;
	    dataDoc.loadXML( xml ) ;
	    var root = dataDoc.documentElement;
	    //
	    if(!root)
	    	return ;
		var totalCount = root.getAttribute("totalCount");
		var pageIndex = root.getAttribute("pageIndex");
		var pageCount = root.getAttribute("pageCount");
		dataset.recordCount = totalCount ;
		//更新dataset当前的索引页数
		dataset.pageIndex=System.getInt(pageIndex);
		//更新dataset的总页数
		dataset.pageCount=System.getInt(pageCount);
		dataset.disableControls();
		dataset.clearData();
		if (dataDoc.documentElement){
			Dataset.appendFromXml(dataset, dataDoc.documentElement, true);
		}
		dataset.setRecord(dataset.getFirstRecord());
		dataset.enableControls();
		dataset.loadDetail();

		if(typeof(loadCallback)!="undefined" && loadCallback!=null){
		  loadCallback();
		}
		dataDoc = null;
		root = null;
	}

	var cmd = new DownLoadDataCommand();
	cmd.setDataset( dataset ) ;
	cmd.execute(callback);

}

Dataset.downloadData = function(dataset, pageIndex){

    if( dataset.paginate ){
		//获取当前的页数
		if (!pageIndex)
			pageIndex = "1";

		//获取每一页的大小
	    var pageSize = dataset.pageSize;
	    if (!pageSize)
			pageSize = 0;
		//将页数作为参数传递给服务器端
		//alert("pageIndex number");
		dataset.parameters().delParameter("pageIndex");
		dataset.parameters().delParameter("pageSize");
		dataset.parameters().setDataType("pageIndex", "int");
		dataset.parameters().setValue("pageIndex", pageIndex);
		dataset.parameters().setDataType("pageSize", "int");
		dataset.parameters().setValue("pageSize",pageSize);
	}
	var cmd = new DownLoadDataCommand();
	cmd.setDataset( dataset ) ;
	var xml = cmd.execute();
	var dataDoc = new ActiveXObject("Microsoft.XMLDOM");
	dataDoc.async=false;
    dataDoc.loadXML( xml ) ;
    var root = dataDoc.documentElement;
    dataDoc = null;
	var totalCount = root.getAttribute("totalCount");
	var pageIndex = root.getAttribute("pageIndex");
	var pageCount = root.getAttribute("pageCount");
	dataset.recordCount = totalCount ;
	//更新dataset当前的索引页数
	dataset.pageIndex=System.getInt(pageIndex);
	//更新dataset的总页数
	dataset.pageCount=System.getInt(pageCount);
	return root;
}

Dataset.getDatasetByID = function(ID){
    return eval(ID);
    /*
	for(var i=0; i<Document.array_dataset.length; i++){
		if (Document.array_dataset[i].id==ID) return Document.array_dataset[i];
	}

	var result=null;
	eval("if (typeof("+ID+")!=\"undefined\") result="+ID+";");
	return result;
	*/
}

Dataset.setElementDataset = function(element, dataset){
	var _dataset;
	if (typeof(dataset)=="string"){
		_dataset=Dataset.getDatasetByID(dataset);
	}
	else{
		_dataset=dataset;
	}
	var old_dataset=element.getAttribute("dataset");

	if (old_dataset){
		var array=old_dataset.editors;
		if (array) {
		  array.deleteByData(element);
		  if(array.length==0)
		    old_dataset.editors = null;
		}
	}

	if (_dataset){
		var array=_dataset.editors;
		if (!array){
			array=new pArray();
			_dataset.editors=array;
		}
		if (array){
      array.insertWithData(element);
	  }
	}
	element.dataset=_dataset;
}

Dataset.getField = function(fields, name){
	var field=null;
	if (typeof(name)=="number"){
		field=fields[name];
	}
	else if (typeof(name)=="string"){
		var fieldIndex=fields["_index_"+name];
		if (!isNaN(fieldIndex)) field=fields[fieldIndex];
	}
	return field;
}

Dataset.appendFromXml = function(dataset, root, init){
	if (!root) return;

	var current;
	if (root) {
		var recordNodes=root.childNodes;
		for (var i=0; i<recordNodes.length; i++) {
			var recordNode=recordNodes.item(i);
			var newData=recordNode.selectSingleNode("new");
			var record=newData.text.split(",");
			record.recordState="none";
			dataset.insertUnit("end", null, record);
			if (init) initRecord(record, dataset);
		}
	}
	return current;
}

Dataset.setRecord = function(dataset, record){
	Dataset.do_setRecord(dataset, record);
	if (record){
		Dataset.setBofnEof(dataset, false, false);
		Dataset.setBofnEof(dataset, false, false);
	}
}
Dataset.getFirstRecord = function(dataset){
	var record=dataset.firstUnit;
	if (record && !Dataset.isRecordValid(record)) record=record.getNextRecord();
	return record;
}

Dataset.getLastRecord = function(dataset){
	var record=dataset.lastUnit;
	if (!Dataset.isRecordValid(record) && record) record=record.getPrevRecord();
	return record;
}

//移动记录游标
Dataset.move = function(dataset, count){
	var _record=dataset.record;//dataset的当前记录
	if (!_record)
		_record=dataset.getFirstRecord();//如果没有当前记录，则获取第一个记录
	if (!_record) return;
	var record=_record;

	if (count>0){//往前移动count个记录
		var old_pageIndex=record.pageIndex;//当前的页面索引数
		var _eof=false;
		for(var i=0; i<count; i++){
			var pageIndex=0;
			_record=record.getNextRecord();//获取下一个记录
			if (!_record || (_record && _record.pageIndex!=old_pageIndex)){//如果下一个记录为空或者下一个记录不在当前页
				if (old_pageIndex<dataset.pageCount){
					if (!dataset.isPageLoaded(old_pageIndex+1)){//如果数据还没有被下载
						if ((i+dataset.pageSize<count) && (old_pageIndex+1<dataset.pageCount)){
							i+=dataset.pageSize-1;
							_record=record;
						}
						else{
							Dataset.loadPage(dataset, old_pageIndex+1);
							_record=record.getNextRecord();
						}
					}
				}
				old_pageIndex++;
			}

			if (_record){
				record=_record;
			}
			else{
				_eof=true;
				break;
			}
		}
		Dataset.setBofnEof(dataset, (!Dataset.isRecordValid(dataset.record)), _eof);
	}
	else{//往后移动count个记录
		var old_pageIndex=record.pageIndex
		var _bof=false;
		for(var i=count; i<0; i++){
			var pageIndex=0;

			_record=record.getPrevRecord();
			if (!_record || (_record && _record.pageIndex!=old_pageIndex)){
				if (old_pageIndex>1){
					if (!dataset.isPageLoaded(old_pageIndex-1)){
						if ((i+dataset.pageSize<0) && (old_pageIndex>1)){
							i+=dataset.pageSize-1;
							_record=record;
						}
						else{
							Dataset.loadPage(dataset, old_pageIndex-1);
							_record=record.getPrevRecord();
						}
					}
				}
				old_pageIndex--;
			}

			if (_record){
				record=_record;
			}
			else{
				_bof=true;
				break;
			}
		}
		Dataset.setBofnEof(dataset, _bof, (!Dataset.isRecordValid(dataset.record)));
	}
	if (record) Dataset.do_setRecord(dataset, record);
}

/*
Dataset.postRecord = function(dataset){
    return;
	if (!dataset.record) return;

	if (!Dataset.isRecordValid(dataset.record)) return;

	Dataset.broadcastDatasetMsg(Dataset.notifyBeforeUpdate, dataset, dataset.record);

	if (dataset.modified){

		var fieldCount=dataset.fields.fieldCount;


		//如果该数据集有关联的子数据集
		var detaildatasets=[];
		if (dataset.detailDatasets){
			var unit=dataset.detailDatasets.firstUnit;
			while (unit && unit.data){//循环每一个子数据集
				var detail_dataset=unit.data;
				if (detail_dataset.references.length>0) {
					var disableCount=detail_dataset.disableControlCount;
					detail_dataset.disableControlCount=1;
					try{
						var changed=false;
						Dataset.postRecord(detail_dataset);
						detail_dataset.moveFirst();
						while (!detail_dataset._eof){
							for(var i=0; i<detail_dataset.references.length; i++){
								var detailIndex=detail_dataset.references[i].detailIndex;
								var masterIndex=detail_dataset.references[i].masterIndex;
								if (detail_dataset.getValue(detailIndex)!=dataset.getValue(masterIndex)){
									detail_dataset.setValue(detailIndex, dataset.getValue(masterIndex));
									changed=true;
								}
							}
							Dataset.postRecord(detail_dataset);
							detail_dataset.moveNext();
						}
					}
					finally{
						detail_dataset.disableControlCount=disableCount;
					}

					if (changed){
						detaildatasets[detaildatasets.length]=detail_dataset;
					}
				}
				unit=unit.nextUnit;
			}
		}

		switch (dataset.record.recordState){
			case "none":{
				dataset.record.recordState="modify";
				Dataset.changeMasterRecordState(dataset);
				break;
			}
			case "new":{
				dataset.record.recordState="insert";
				Dataset.changeMasterRecordState(dataset);
				break;
			}
		}

		dataset.modified=false;

		Dataset.setState(dataset, "none");

		for (var i=0;i<detaildatasets.length;i++){
			detail_dataset.refreshControls();
			Dataset.validateCursor(detail_dataset);
		}
	}
	else{
		if (dataset.record.recordState=="new"){
			dataset.record.recordState="discard";
			Dataset.setState(dataset, "none");
			Dataset.broadcastDatasetMsg(Dataset.notifyDelete, dataset, dataset.record);
			Dataset.validateCursor(dataset);
		}
	}
}
*/

Dataset.validRecord = function(dataset, targetRecord){

	var fieldCount=dataset.fields.fieldCount;
	var _record = null;
	if(targetRecord)
	  _record = targetRecord;
	else
	  _record = dataset.getCurrent();

	if(!_record) return;
	var ErrorItem = [];
	var ErrorMessage = [];

	ErrorMessage[0] = Validator.ErrorMessage;
	ErrorItem[0] = null;

	for (var i=0; i<fieldCount; i++){

		var validObj = dataset.fields[i];
		//如果该字段不是只读的，并且该字段是必须不为空
		if (dataset.fields[i] && !System.isTrue(dataset.fields[i].readOnly) ){

			//通过validType验证
			var _dataType = validObj.validType;
			if(typeof(_dataType) == "object" || typeof(Validator[_dataType]) == "undefined")   continue;
			var validResult = true;

			if((validObj.required == false) && _record.getValue(validObj.name) == "") continue;

			switch(_dataType){
				//js函数的验证的dataType

				case "repeat" :
				case "limit" :
				case "limitB" :
				case "safeString" :
				case "filter" :
					//使用js函数的验证
					validResult = eval(Validator[_dataType]);
					break;

				default :
					//使用正则表达式的验证
					validResult = Validator[_dataType].test(_record.getValue(validObj.name));
					break;
			}

			if(!validResult)
			{
				var msg = validObj.validMsg;

				if( msg==undefined || msg==null || msg == ""){
					msg=ErrMsgs[_dataType];
				}
				ErrorItem[ErrorItem.length] = validObj;
				ErrorMessage[ErrorMessage.length] = ErrorMessage.length + ":" + msg;
			}
		}
	}

	if(ErrorMessage.length > 1){
		MessageBox.Show(null, ErrorMessage.join(Global.lineDelimiter), null, 'OK', 'Warning');
		return false;
	}

	return true;
}

//去掉了只读不进行校验的处理
Dataset.validNewRecord = function(dataset, targetRecord){

	var fieldCount=dataset.fields.fieldCount;
	var _record = null;
	if(targetRecord)
	  _record = targetRecord;
	else
	  _record = dataset.getCurrent();

	if(!_record) return;
	var ErrorItem = [];
	var ErrorMessage = [];

	ErrorMessage[0] = Validator.ErrorMessage;
	ErrorItem[0] = null;

	for (var i=0; i<fieldCount; i++){

		var validObj = dataset.fields[i];
		//如果该字段不是只读的，并且该字段是必须不为空
		if (dataset.fields[i]){

			//通过validType验证
			var _dataType = validObj.validType;
			if(typeof(_dataType) == "object" || typeof(Validator[_dataType]) == "undefined")   continue;
			var validResult = true;

			if((validObj.required == false) && _record.getValue(validObj.name) == "") continue;

			switch(_dataType){
				//js函数的验证的dataType

				case "repeat" :
				case "limit" :
				case "limitB" :
				case "safeString" :
				case "filter" :
					//使用js函数的验证
					validResult = eval(Validator[_dataType]);
					break;

				default :
					//使用正则表达式的验证
					validResult = Validator[_dataType].test(_record.getValue(validObj.name));
					break;
			}

			if(!validResult)
			{
				var msg = validObj.validMsg;

				if( msg==undefined || msg==null || msg == ""){
					msg=ErrMsgs[_dataType];
				}
				ErrorItem[ErrorItem.length] = validObj;
				ErrorMessage[ErrorMessage.length] = ErrorMessage.length + ":" + msg;
			}
		}
	}

	if(ErrorMessage.length > 1){
		MessageBox.Show(null, ErrorMessage.join(Global.lineDelimiter), null, 'OK', 'Warning');
		return false;
	}

	return true;
}

Dataset.validAllRecord = function(dataset){

	var record = dataset.getFirstRecord();

   	while(record){
         if(!Dataset.validRecord(dataset, record)){
         	return false;
         }
         record = record.getNextRecord();
   	}
   	return true;
}

Dataset.insertRecord = function(dataset, mode, withValidate){

	//通过Validator验证dataset的内容。
	if(withValidate && dataset.record && !Dataset.validRecord(dataset)) return;

	//判断是否有非可读字段
	if(!dataset.judgeFieldMaskControl()) return;

	//Dataset.postRecord(dataset);


	var pageIndex=(dataset.record)?dataset.record.pageIndex:1;

	var newRecord=[];
	//在Dataset的链表结构中的当前记录插入一个记录
	dataset.insertUnit(mode, dataset.record, newRecord);
	initRecord(newRecord, dataset);

	switch (mode){
		case "begin":{
			newRecord.pageIndex=1;
			break;
		}
		case "end":{
			newRecord.pageIndex=dataset.pageCount;
			break;
		}
		default:{
			newRecord.pageIndex=pageIndex;
			break;
		}
	}

	newRecord.recordState="new";
	newRecord.recordno=9999;

	var _masterDataset=dataset.masterDataset;
	if (_masterDataset){
		if (_masterDataset.record){
			for(var i=0; i<dataset.references.length; i++){
				var fieldIndex=dataset.references[i].masterIndex;
				if (_masterDataset.getString(fieldIndex)==""){
					var field=_masterDataset.getField(fieldIndex);
					switch (field.dataType) {
					case "string":
						_masterDataset.setValue(fieldIndex, Dataset.getAutoGenID());
						break;
					case "int":
						var maxnum=0;
						var record=_masterDataset.firstUnit;
						while (record){
							if (record.getValue(fieldIndex)>maxnum) {
								maxnum=record.getValue(fieldIndex);
							}
							record=record.nextUnit;
						}
						_masterDataset.setValue(fieldIndex, maxnum+1);
						break;
					}
				}
			}
			//Dataset.postRecord(_masterDataset);


			for(var i=0; i<dataset.references.length; i++){
				var reference=dataset.references[i];
				newRecord[reference.detailIndex]=
					_masterDataset.getValue(reference.masterIndex);
			}

		}
		else{
			throw Const.ErrNoMasterRecord;
		}
	}

	dataset.state="insert";
	Dataset.broadcastDatasetMsg(Dataset.notifyStateChanged, dataset, dataset.record);
	Dataset.broadcastDatasetMsg(Dataset.notifyInsert, dataset, dataset.record, [mode, newRecord]);
	Dataset.setRecord(dataset, newRecord);

	dataset.modified=false;
}

Dataset.deleteRecord = function(dataset){
	if (!dataset.record) return;

	//判断是否有非可读字段
	if(!dataset.judgeFieldMaskControl()) return;

	Editor.needUpdateEditor=false;
	try{
		if (dataset.record.recordState=="new" || dataset.record.recordState=="insert"){

			dataset.record.recordState="discard";
		}
		else{

			dataset.record.recordState="delete";
			Dataset.changeMasterRecordState(dataset);
		}

		dataset.modified=false;

		dataset.state="none";
		Dataset.broadcastDatasetMsg(Dataset.notifyStateChanged, dataset, dataset.record);
		Dataset.broadcastDatasetMsg(Dataset.notifyDelete, dataset, dataset.record);
		Dataset.validateCursor(dataset);
	}
	finally{
		Editor.needUpdateEditor=true;
	}
}

Dataset.copyRecord = function(dataset, record, fieldMap){
	if (fieldMap){
		var fieldmaps=[];
		var fields=fieldMap.split(";");
		var field1="", field2="";
		for(var i=0; i<fields.length; i++){
			fieldmaps[i]={};
			var index=fields[i].indexOf("=");
			if (index>=0){
				field1=fields[i].substr(0, index);
				field2=fields[i].substr(index+1);
			}
			else{
				field1=fields[i];
				field2=fields[i];
			}

			var value=record.getValue(field2);
			if (typeof(value)!="undefined") dataset.setValue(field1, value);
		}
	}
	else{
		for(var i=0; i<dataset.fields.fieldCount; i++){
			var fieldName=dataset.getField(i).name;
			var field=record.dataset.getField(fieldName);
			if (field) {
				var value=record.getValue(fieldName);
				if (typeof(value)!="undefined") dataset.setValue(fieldName, value);
			}
		}
	}
}
Dataset.loadPage = function(dataset, pageIndex){

	if (!dataset.autoLoadPage || pageIndex<1 || pageIndex>dataset.pageCount || dataset.isPageLoaded(pageIndex)) return;
	if (dataset.masterDataset) throw Const.ErrLoadPageOnDetailDataset;

	var xmlNode=Dataset.downloadData(dataset, pageIndex);
	if (xmlNode){
		var tmpArray=new pArray();
		tmpArray.fields=dataset.fields;

    	dataset.disableControls();

		Dataset.appendFromXml(tmpArray, xmlNode);

		var record=tmpArray.lastUnit;
		while (record){
			initRecord(record, dataset);
			record.pageIndex=pageIndex;
			record=record.prevUnit;
		}

		var inserted=false;
		var record=dataset.lastUnit;
		while (record){
			if (record.pageIndex<pageIndex){
				dataset.insertArray("after", record, tmpArray);
				inserted=true;
				break;
			}
			record=record.prevUnit;
		}
		if (!inserted) dataset.insertArray("begin", null, tmpArray);
		delete tmpArray;

		dataset.loadedPage[pageIndex-1]=true;
		dataset.enableControls();
	}
	xmlNode = null;
}
Dataset.loadDetail = function(dataset){

	if (dataset.detailDatasets){
		var unit=dataset.detailDatasets.firstUnit;
		while (unit && unit.data){
			var detail_dataset=unit.data;
			if (dataset.record && dataset.record.recordState!="insert" &&
				dataset.record.recordState!="new"){

					Dataset.validateCursor(detail_dataset);
					//if (detail_dataset.loadAsNeeded && detail_dataset._bof && detail_dataset._eof) {
					if (detail_dataset.loadAsNeeded) {
						var keycode_founded=false;

						if (dataset.record) {
							var keycode="";
							for(var i=0; i<detail_dataset.references.length; i++){
								keycode+=dataset.record[detail_dataset.references[i].masterIndex];
							}

							for(var i=0; i<detail_dataset.loadedDetail.length; i++){
								if (detail_dataset.loadedDetail[i]==keycode){
									keycode_founded=true;
									break;
								}
							}
						}

						if (!keycode_founded){
							var dataset_inserted=false;

							if (detail_dataset.references.length>0) {
								var parameters = detail_dataset.parameters();
								for(var i=0; i<detail_dataset.references.length; i++){
									parameters.setValue(detail_dataset.references[i].detailField,
										dataset.getValue(detail_dataset.references[i].masterIndex));
								}

								var xmlNode=Dataset.downloadData(detail_dataset);
								if (xmlNode){
									Dataset.appendFromXml(detail_dataset, xmlNode, true);
								}
								delete result;
							}

							detail_dataset.loadedDetail[detail_dataset.loadedDetail.length]=keycode;
						}
					}

			}

			detail_dataset.refreshControls();
			detail_dataset.moveFirst();
			unit=unit.nextUnit;
		}
	}
}

Dataset.setMasterDataset = function(dataset, masterDataset, masterKeyFields, detailKeyFields){
	if (dataset.masterDataset){
		var array=dataset.masterDataset.detailDatasets;
		if (array) array.deleteByData(dataset);
	}

	if (typeof(masterDataset)=="string") masterDataset=Dataset.getDatasetByID(masterDataset);
	dataset.masterDataset=masterDataset;
	if (masterDataset){
		var array=masterDataset.detailDatasets;
		if (!array){
			array=new pArray();
			masterDataset.detailDatasets=array;
		}
		array.insertWithData(dataset);

		dataset.references=[];
		var fields=masterKeyFields.split(",");
		for(var i=0; i<fields.length; i++){
			var field=masterDataset.getField(fields[i]);

			if (field){
				var reference={};
				dataset.references[i]=reference;
				reference.masterField=field.name;
				reference.masterIndex=field.index;
			}
			else
				throw Const.ErrCantFindMasterField.replace("%s", fields[i]);
		}

		var fields=detailKeyFields.split(",");
		for(var i=0; i<fields.length; i++){
			var field=dataset.getField(fields[i]);

			if (field){
				dataset.references[i].detailField=field.name;
				dataset.references[i].detailIndex=field.index;
			}
			else
				throw Const.ErrCantFindDetailField.replace("%s", fields[i]);
		}
		delete fields;

		masterDataset.loadDetail();
	}
}

Dataset.fetchData = function(dataset, pageIndex, callback){
    Dataset.downloadData2(dataset, pageIndex, callback);
}

Dataset.flushData = function(dataset, pageIndex){

	dataset.disableControls();
	try{
		if (typeof(pageIndex)=="undefined") {
			pageIndex=dataset.pageIndex;
		}
		dataset.clearData();

		var xmlNode=Dataset.downloadData(dataset, pageIndex);
		if (xmlNode){
			Dataset.appendFromXml(dataset, xmlNode, true);
		}
		delete result;
	}
	finally{
		dataset.setRecord(dataset.getFirstRecord());
		dataset.enableControls();
		dataset.loadDetail();
	}

	Dataset.fireEvent(dataset, "afterLoadData", [dataset]);

}

Dataset.paginateFlushData = function(dataset, pageIndex){

	if( !dataset.loadDataAction  || dataset.loadDataAction == "" ||
		dataset.loadDataAction == "undifined" ){
		return "";
	}
	if( !dataset.loadDataActionMethod  || dataset.loadDataActionMethod == "" ||
		dataset.loadDataActionMethod == "undifined" ){
		return "";
	}
  Dataset.fireEvent(dataset, "beforeLoadData", [dataset]);
	dataset.disableControls();

	if (typeof(pageIndex)=="undefined") {
		pageIndex=dataset.pageIndex;
	}
	dataset.clearData();

	try{

	   if( dataset.paginate ){
			//获取当前的页数
			if (pageIndex==null)
				pageIndex = "1";

			//获取每一页的大小
		    var pageSize = dataset.pageSize;
		    if (!pageSize)
				pageSize = 0;
			//将页数作为参数传递给服务器端
			//alert("pageIndex number");
			dataset.parameters().delParameter("pageIndex");
			dataset.parameters().delParameter("pageSize");
			dataset.parameters().setDataType("pageIndex", "int");
			dataset.parameters().setValue("pageIndex", pageIndex);
			dataset.parameters().setDataType("pageSize", "int");
			dataset.parameters().setValue("pageSize",pageSize);
		}

		//获取所有的参数，参数通过dataset组件提供的方法设置，可以设置无限个
		//参数，但是参数的次序必须保证和服务器组件提供服务的方法的参数一致。
	    var parameters = [];
	    var ps = dataset._parameters;
	    for( var i = 0; i < ps.count(); i ++ ){
	        var parameterName = ps.indexToName(i);
	    	var parameterType = ps.getDataType( parameterName );
	    	var parameterValue = ps.getValue( parameterName );
	    	if( parameterType == "object" ){
	    		parameters[i] = parameterValue ;
	    	}else{
		    	var value = parseValue( parameterValue, parameterType );
	    		parameters[i] = value ;
	    	}
	    }



	    //==================================Call Back Function Start Here
	    var callBack = function (reply) {
			//获取服务器端返回的XML格式的字符串
			var xml = reply.getResult();
			var dataDoc = new ActiveXObject("Microsoft.XMLDOM");
			dataDoc.async=false;
		    dataDoc.loadXML( xml ) ;
		    var root = dataDoc.documentElement;
		    dataDoc = null;
			var totalCount = root.getAttribute("totalCount");
			var pageIndex = root.getAttribute("pageIndex");
			var pageCount = root.getAttribute("pageCount");
			dataset.recordCount = totalCount ;
			//更新dataset当前的索引页数
			dataset.pageIndex=System.getInt(pageIndex);
			//更新dataset的总页数
			dataset.pageCount=System.getInt(pageCount);

			if (root){
				Dataset.appendFromXml(dataset, root, true);
			}

			dataset.setRecord(dataset.getFirstRecord());
			dataset.enableControls();
			dataset.loadDetail();
			Dataset.fireEvent(dataset, "afterLoadData", [dataset]);
	    }

    	var ajaxCall = new NDAjaxCall(true);
    	ajaxCall.remoteCall(dataset.loadDataAction, dataset.loadDataActionMethod, parameters, callBack, dataset._queryType,dataset._queryFields);
	}catch(e){
		dataset.setRecord(dataset.getFirstRecord());
		dataset.enableControls();
		dataset.loadDetail();
	}
}

Dataset.clearData = function(dataset){
	dataset.disableControls();
	try{
		if (dataset.loadedDetail) delete dataset.loadedDetail;
		if (dataset.loadedPage) delete dataset.loadedPage;
		dataset.loadedDetail=[];
		dataset.loadedPage=[];
		if (dataset.pageIndex>0) dataset.loadedPage[dataset.pageIndex-1]=true;
		dataset.clearAll();
		dataset.setRecord(null);
	}
	finally{
		dataset.enableControls();
	}
}

Dataset.fireEvent = function(dataset, eventName, param){
	if (dataset.disableEventCount>0) return;
	var result;
	result=Document.fireUserEvent(Document.getElementEventName(dataset, eventName), param);
	return result;
}

Dataset.isRecordValid = function(record){
	if (!record)
		return false;
	else{
		var result=(record.recordState!="delete" && record.recordState!="discard" && record.visible);
		var dataset=record.dataset;
		var masterDataset=dataset.masterDataset;
		if (result){
			if (masterDataset){
				if (!masterDataset.record) return false;

				for(var i=0; i<dataset.references.length; i++){
					if (masterDataset.getValue(dataset.references[i].masterIndex)!=
						record.getValue(dataset.references[i].detailIndex)){
							result=false;
							break;
					}
				}

			}


		}
		return result;
	}
}

Dataset.isFieldEditable = function(dataset, field){
    var editable=!(dataset.readOnly || field.readOnly);
    return editable;
    /*  保留,以后实现 */
    /*
	if (field){
		var editable=!(dataset.readOnly || field.readOnly);
		if (dataset.record){
			var recordState=dataset.record.recordState;
			editable=(editable &&
				!((recordState=="none" || recordState=="modify") && field.valueProtected));
		}
	}
	else{
		var editable=true;
	}
	return editable;
	*/
}

Dataset.freeDataset = function(dataset){
	if (dataset.detailDatasets) dataset.detailDatasets.clearAll();
	if (dataset.editors) dataset.editors.clearAll();
	delete dataset.references;

	pArray._clearAll(dataset.fields);
	dataset.clearData();
	delete dataset;
}

Dataset.setBofnEof = function(dataset, BofValue, EofValue){
	if (dataset._bof!=BofValue || dataset._eof!=EofValue){
		dataset._bof=BofValue;
		dataset._eof=EofValue;
		Dataset.broadcastDatasetMsg(Dataset.notifyStateChanged, dataset, dataset.record);
	}
}

Dataset.do_setRecord = function(dataset, record){
	if (dataset.record!=record){
		/*
		if (dataset.record){
			Dataset.postRecord(dataset);
		}
		if (dataset.detailDatasets){
			var unit=dataset.detailDatasets.firstUnit;
			while (unit){
				var detailDataset=unit.data;
				Dataset.postRecord(detailDataset);
				unit=unit.nextUnit;
			}
		}
		*/


		var event_result=Dataset.fireEvent(dataset, "beforeScroll", [dataset]);
		if (event_result){
			throw event_result;
		}
		dataset.record=record;
		dataset.modified=false;
		if (dataset.disableControlCount<1){
			dataset.loadDetail();
		}
		Dataset.fireEvent(dataset, "afterScroll", [dataset]);
		Dataset.broadcastDatasetMsg(Dataset.notifyStateChanged, dataset, record);
		Dataset.broadcastDatasetMsg(Dataset.notifyCursorChanged, dataset, record);
	}

}


Dataset.validateCursor = function(dataset){
	var down_found=false, up_found=false;
	var curRecord=(dataset.record)?dataset.record:dataset.firstUnit;

	var record=curRecord;
	while (record){
		if (Dataset.isRecordValid(record)){
			Dataset.do_setRecord(dataset, record);
			up_found=true;
			break;
		}
		record=record.getPrevRecord();
	}

	var record=curRecord;
	while (record){
		if (Dataset.isRecordValid(record)){
			Dataset.do_setRecord(dataset, record);
			down_found=true;
			break;
		}
		record=record.getNextRecord();
	}

	if (!up_found && !down_found)
		Dataset.do_setRecord(dataset, null);

	Dataset.setBofnEof(dataset, (!up_found), (!down_found));
}


Dataset.broadcastDatasetMsg = function(proc, dataset, record, reserved){
	if (dataset.disableControlCount>0) return;
	var pArray=dataset.editors;
	if (pArray){
		var unit=pArray.firstUnit;
		while (unit && unit.data){
			proc(unit.data, dataset, record, reserved);
			unit=unit.nextUnit;
		}
	}
}

Dataset.broadcastFieldMsg = function(proc, dataset, record, field, reserved){
	if (dataset.disableControlCount>0) return;
	var pArray=dataset.editors;

	if (pArray){
		var unit=pArray.firstUnit;
		while (unit && unit.data){
			proc(unit.data, dataset, record, field, reserved);
			unit=unit.nextUnit;
		}
	}
}

Dataset.notifyCursorChanged = function(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (!record) break;

			var maxRow=element.getAttribute("maxRow");
			if (element.tBodies[0].rows.length>=maxRow){
				var needRefresh=true;
				var firstRecord=_window.Datatable.getTableFirstRecord(element);
				var lastRecord=_window.Datatable.getTableLastRecord(element);

				var _record=firstRecord;
				while (_record){
					if (_record==record){
						needRefresh=false;
						break;
					}

					if (_record==lastRecord) break;
					_record=_record.nextUnit;
				}

				if (needRefresh){
					var counter=maxRow;
					var tmpRecord=record;

					for(var i=0; i<counter; i++){
						tmpRecord=tmpRecord.getNextRecord();
						if (!tmpRecord) break;
					}

					var startRecord=record;
					tmpRecord=record;
					counter=maxRow-i-1;
					for(var i=0; i<counter; i++){
						tmpRecord=tmpRecord.getPrevRecord();
						if (tmpRecord)
							startRecord=tmpRecord;
						else
							break;
					}

					_window.Datatable.refreshTableData(element, startRecord);
				}
			}

			var row=_window.Datatable.getTableRowByRecord(element, record);
			if (row){
				_window.Datatable.setActiveTableRow(row);
			}
			break;
		}
		case "datalabel":{
			_window.Control.refreshElementValue(element);
			break;
		}
		case "editor":;
		case "dockeditor":{
			_window.Control.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
		case "datapilot":{
			_window.DataPilot.refreshDataPilot(element);
			break;
		}
		case "pagepilot":{
			_window.PagePilot.refreshPagePilot(element);
			break;
		}
	}
}

Dataset.notifyBeforeUpdate = function(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "dockeditor":{
			_window.Editor.updateEditorInput(element);
			break;
		}
	}
}

Dataset.notifyStateChanged = function(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "editor":;
		case "dockeditor":{
			var field=_window.Control.getElementField(element);
			element.setReadOnly(!Dataset.isFieldEditable(dataset, field));
			break;
		}
		case "datapilot":{
			_window.DataPilot.refreshDataPilot(element);
			break;
		}
		case "datatable":{
			if (element.activeRow) _window.Datatable.refreshTableRowIndicate(element.activeRow);
			break;
		}
	}
}

Dataset.notifyInsert = function(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			var row;
			if (record) row=_window.Datatable.getTableRowByRecord(element, record);

			_window.Datatable.insertTableRecord(element, reserved[0], row, reserved[1]);
			if (element.tBodies[0].rows.length>element.getAttribute("maxRow")){
				var lastRecord=_window.Datatable.getTableLastRecord(element);
				if (lastRecord!=reserved[1]){
					_window.Datatable.deleteTableRecord(element.tBodies[0].rows[element.tBodies[0].rows.length-1]);
				}
				else{
					_window.Datatable.deleteTableRecord(element.tBodies[0].rows[0]);
				}
			}
			break;
		}
	}
}

Dataset.notifyDelete = function(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (record){
				var row=_window.Datatable.getTableRowByRecord(element, record);
				if (row){
					if (element.tBodies[0].rows.length<=element.getAttribute("maxRow")){
						var firstRecord=_window.Datatable.getTableFirstRecord(element);
						var lastRecord=_window.Datatable.getTableLastRecord(element);
						if (firstRecord){
							var _record=lastRecord.getNextRecord();
							if (_record){
								_window.Datatable.insertTableRecord(element, "end", row, _record);
							}
							else{
								var _record=firstRecord.getPrevRecord();
								if (_record) _window.Datatable.insertTableRecord(element, "begin", row, _record);
							}
						}
					}

					_window.Datatable.deleteTableRecord(row);
				}
			}
			break;
		}
	}
}

Dataset.notifyRefreshRecord = function(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (record){
				var row=_window.Datatable.getTableRowByRecord(element, record);
				if (row) _window.Datatable.refreshTableRecord(row);
			}
			break;
		}
		case "editor":;
		case "dockeditor":{
			_window.Control.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
	}

	if (_window.Editor) _window.Editor.sizeDockEditor();

}

Dataset.notifyRefresh = function(element, dataset, record, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			if (!System.compareText(element.style.visibility, "hidden")) {
			    if(Control.documentInitialized)
					Datatable.refreshTableData(element);
			}
			else {
				element.needRefresh=true;
			}
			break;
		}
		case "datalabel":;
		case "editor":;
		case "dockeditor":{
            if(dataset.getLength()==0){
                //dataset.insertRecord(null);
                var newRecord=[];
				//在Dataset的链表结构中的当前记录插入一个记录
				dataset.insertUnit("end", null, newRecord);
				initRecord(newRecord, dataset);
              	dataset.record = newRecord;
            }
            _window.Control.refreshElementValue(element);


            //if(!dataset.staticDataSource || Control.documentInitialized)
			    //_window.Control.refreshElementValue(element);
			element.isUserInput=false;
			break;
		}
		case "datapilot":{
			_window.DataPilot.refreshDataPilot(element);
			break;
		}
		case "pagepilot":{
			_window.PagePilot.refreshPagePilot(element);
			break;
		}
		case "customerpilot":{
			_window.refreshCustomerPilot(element);
		  break;
		}
	}
	Dataset.notifyStateChanged(element, dataset, record, reserved);
	if (_window.Editor) _window.Editor.sizeDockEditor();
}

Dataset.notifyFieldDataChanged = function(element, dataset, record, field, reserved){
	var _window=element.window;
	switch (element.getAttribute("extra")){
		case "datatable":{
			var row=_window.Datatable.getTableRowByRecord(element, record);
			if(row && row.cells){
				for(var i=0; i<row.cells.length; i++){
					var cell=row.cells[i];
					if (System.compareText(cell.getAttribute("field"), field.name)){
						_window.Control.refreshElementValue(cell);
					}
				}
			}
			break;
		}
		case "editor":;
		case "dockeditor":{
			if (System.compareText(element.getAttribute("field"), field.name)){
				_window.Control.refreshElementValue(element);
				element.isUserInput=false;
			}
			break;
		}
		case "datalabel":{
			if (System.compareText(element.getAttribute("field"), field.name)){
				_window.Control.refreshElementValue(element);
			}
			break;
		}
	}

	if (_window.Editor) _window.Editor.sizeDockEditor();
}

Dataset.notifyFieldStateChanged = function(element, dataset, record, field, reserved){
	switch (element.getAttribute("extra")){
		case "editor":;
		case "dockeditor":{
			var elmtField=Control.getElementField(element);
			if (elmtField==field) {
				element.setReadOnly(!Dataset.isFieldEditable(dataset, field));
			}
			break;
		}
	}
}

Dataset.changeMasterRecordState = function(dataset){
	var masterDataset=dataset.masterDataset;
	if (masterDataset) {
		if (masterDataset.record.recordState=="none") {
			masterDataset.record.recordState="modify";
			Dataset.changeMasterRecordState(masterDataset);
		}
	}
}

Dataset.field_count = function() {
	return this.fieldCount;
}

Dataset.addUpdateItem = function(dataset) {
	var item={};
	dataset.updateItems[dataset.updateItems.length]=item;
	return item;
}

Dataset.reloadData = function(dataset, callback) {
	var datacmd = new QueryCommand();
	dataset.staticDataSource=false;
	datacmd.setDataset( dataset );
	datacmd.execute(callback);
}

Dataset.cloneDataset = function(sourceDataset, targetDataset){
  targetDataset.disableControls();
  targetDataset.clearData();
  targetDataset.disableEvents();
  var record = sourceDataset.getFirstRecord();
  while(record){
    targetDataset.insertRecord();
		for(var i=0; i<sourceDataset.fields.fieldCount; i++){
			var fieldName=sourceDataset.getField(i).name;
			var field=targetDataset.getField(fieldName);
			if (field) {
				var value=record.getValue(fieldName);
				if (typeof(value)!="undefined") targetDataset.setValue(fieldName, value);
			}
		}
    record = record.getNextRecord();
  }
  targetDataset.enableEvents();
  targetDataset.moveFirst();
  targetDataset.enableControls();
}

Dataset.cloneRecordToDataset = function(record, targetDataset){
  targetDataset.disableControls();
  targetDataset.clearData();

  targetDataset.insertRecord();
	for(var i=0; i<record.dataset.fields.fieldCount; i++){
		var fieldName=record.dataset.getField(i).name;
		var field=targetDataset.getField(fieldName);
		if (field) {
			var value=record.getValue(fieldName);
			if (typeof(value)!="undefined") targetDataset.setValue(fieldName, value);
		}
	}
  targetDataset.enableControls();
}


Dataset.appendRecordToDataset = function(record, targetDataset){
  //targetDataset.disableControls();

  targetDataset.insertRecord();
	for(var i=0; i<record.dataset.fields.fieldCount; i++){
		var fieldName=record.dataset.getField(i).name;
		var field=targetDataset.getField(fieldName);
		if (field) {
			var value=record.getValue(fieldName);
			if (typeof(value)!="undefined") targetDataset.setValue(fieldName, value);
		}
	}
  //targetDataset.enableControls();
}

Dataset.updateRecordToDataset = function(record, targetDataset){
  //targetDataset.disableControls();

	for(var i=0; i<record.dataset.fields.fieldCount; i++){
		var fieldName=record.dataset.getField(i).name;
		var field=targetDataset.getField(fieldName);
		if (field) {
			var value=record.getValue(fieldName);
			if (typeof(value)!="undefined") targetDataset.setValue(fieldName, value);
		}
	}
  //targetDataset.enableControls();
}

Dataset.cloneRecord = function(sourceRecord, targetRecord){
	for(var i=0; i<sourceRecord.dataset.fields.fieldCount; i++){
		var fieldName=sourceRecord.dataset.getField(i).name;
		var field=targetRecord.dataset.getField(fieldName);
		if (field) {
			var value=sourceRecord.getValue(fieldName);
			if (typeof(value)!="undefined") targetRecord.setValue(fieldName, value);
		}
	}
}

Date.prototype.toString = function() {
	return this.getTime().toString();
}


// Command
function Command(id) {
	this._id = id;
}

Command.prototype.execute = function() {}

Command.prototype.getId = function() {
	return this._id;
}

// QueryCommand
function DownLoadDataCommand(id) {
	this._dataset = null;//和数据查询绑定的Dataset对象
}

DownLoadDataCommand.prototype = new Command();//继承自Command类

DownLoadDataCommand.prototype.execute = function(_callBack){
	if( !this._dataset.loadDataAction  || this._dataset.loadDataAction == "" ||
		this._dataset.loadDataAction == "undifined" ){
		return "";
	}
	if( !this._dataset.loadDataActionMethod  || this._dataset.loadDataActionMethod == "" ||
		this._dataset.loadDataActionMethod == "undifined" ){
		return "";
	}
	var async = false;
	if(typeof(_callBack)!="undefined" && _callBack!=null)
	  async = true;
	var ajaxCall = new NDAjaxCall(async);//false同步

	//获取所有的参数，参数通过dataset组件提供的方法设置，可以设置无限个
	//参数，但是参数的次序必须保证和服务器组件提供服务的方法的参数一致。
    var parameters = [];
    var ps = this._dataset._parameters;
    for( var i = 0; i < ps.count(); i ++ ){
        var parameterName = ps.indexToName(i);
    	var parameterType = ps.getDataType( parameterName );
    	var parameterValue = ps.getValue( parameterName );
    	if( parameterType == "object" ){
    		parameters[i] = parameterValue ;
    	}else{
	    	var value = parseValue( parameterValue, parameterType );
    		parameters[i] = value ;
    	}
    }

    var returnXML = "";
    //==================================Call Back Function Start Here
    var callBack = function (reply) {
		//获取服务器端返回的XML格式的字符串
		returnXML = reply.getResult();
		if(typeof(_callBack)!="undefined" && _callBack!=null){
		  _callBack(returnXML);
		}
    }
    ajaxCall.remoteCall(this._dataset.loadDataAction, this._dataset.loadDataActionMethod, parameters, callBack,this._dataset._queryType,this._dataset._queryFields);
	return returnXML;
}

DownLoadDataCommand.prototype.getDataset = function(){
	return this._dataset ;
}

DownLoadDataCommand.prototype.setDataset = function( dataset ){
	this._dataset = dataset ;
}
function parseValue( value, type ){
	if( type == "int" ){
		return parseInt( value ) ;
	}else{
	    return "" + value ;
	}
}

// QueryCommand
function QueryCommand(id) {
	this._dataset = null;//和数据查询绑定的Dataset对象
}

QueryCommand.prototype = new Command();//继承自Command类

QueryCommand.prototype.execute = function(__callback){
	if( !this._dataset.loadDataAction  || this._dataset.loadDataAction == "" ||
		this._dataset.loadDataAction == "undefined" ){
		return "";
	}
	if( !this._dataset.loadDataActionMethod  || this._dataset.loadDataActionMethod == "" ||
		this._dataset.loadDataActionMethod == "undefined" ){
		return "";
	}
	var ajaxCall = new NDAjaxCall(this._dataset.async);//true为异步
    var parameters = "";
	//获取所有的参数，参数通过dataset组件提供的方法设置，可以设置无限个
	//参数，但是参数的次序必须保证和服务器组件提供服务的方法的参数一致。
    var parameters = [];
    var ps = this._dataset._parameters;

    for( var i = 0; i < ps.count(); i ++ ){
    	var parameterName = ps.indexToName(i);
    	var parameterType = ps.getDataType( parameterName );
    	var parameterValue = ps.getValue( parameterName );
    	if( parameterType == "object" ){
    		parameters[i] = parameterValue ;
    	}	else{
	    	var value = parseValue( parameterValue, parameterType );
	    	parameters[i] = value ;
    	}
    }
    //==================================Call Back Function Start Here
    var returnXML = "";
    var tempDataset = this._dataset ;

    var callBack = function (reply) {
		//获取服务器端返回的XML格式的字符串
		returnXML = reply.getResult();

		tempDataset.disableControls();

		if (tempDataset.xmlFormat == "records") {//如果是<records>格式的XML
			var dataDoc = XmlDocument.create();

			if( !tempDataset.staticDataSource ){//如果是在服务器端运行
					dataDoc.async = false;
					dataDoc.loadXML(returnXML);
					var root = dataDoc.documentElement;
					var totalCount = root.getAttribute("totalCount");
					var pageIndex = root.getAttribute("pageIndex");
					var pageCount = root.getAttribute("pageCount");
					tempDataset.recordCount = totalCount ;
					//更新dataset当前的索引页数
					tempDataset.pageIndex=System.getInt(pageIndex);
					//更新dataset的总页数
					tempDataset.pageCount=System.getInt(pageCount);
					tempDataset.clearData();
			}
			var current = Dataset.appendFromXml(tempDataset, dataDoc.documentElement, true);
			tempDataset.prepared = true;
		}else if (tempDataset.xmlFormat == "items") {//如果是<items>格式的XML
				if( !tempDataset.staticDataSource ){
					//如果是在服务器端运行 拆分returnValues
					var returnValues = returnXML;
					for (i = 0; i < returnValues.length; i++) {
					    var record = [returnValues[i].attrValue,returnValues[i].attrValueId,returnValues[i].attrValueDesc];
						tempDataset.insertUnit("end", null, record);
						initRecord(record, tempDataset);
					}
				}
			}
			if (current) {
				tempDataset.setRecord(current);
			} else {
				if (tempDataset.pageIndex == 1 || !tempDataset.autoLoadPage) {
					tempDataset.moveFirst();
				} else {
					tempDataset.setRecord(tempDataset.getFirstRecord());
				}
			}
			if (!tempDataset.record) {
				if (tempDataset.insertOnEmpty && !tempDataset.readOnly) {
					tempDataset.insertRecord();
				}
			}
			tempDataset.enableControls();

	  if(typeof(__callback)!="undefined" && __callback!=null){
	    __callback();
	  }
    }

    if(this._dataset.maskControl){
    	var ajaxCallForMaskFields = new NDAjaxCall(this._dataset.async);
    	ajaxCallForMaskFields.remoteCall("PrivilegeService", "getCustCtrlDataInfo", [Global.pageId, this._dataset.id, Global.custTypeId], function(reply){
    		tempDataset.maskControlFields = reply.getResult();
    		//tempDataset.maskControlFields = ["custName", "custDeptType", "typeFlag", "contactName","acctId", "custLocation", "importanceLevel", "latentVipFlag", "certificateNo"];
    		//tempDataset.refreshFieldMaskControls();
    		ajaxCall.remoteCall(tempDataset.loadDataAction, tempDataset.loadDataActionMethod, parameters, callBack,tempDataset._queryType,tempDataset._queryFields);
    	});
    }
    else{
    	ajaxCall.remoteCall(this._dataset.loadDataAction, this._dataset.loadDataActionMethod, parameters, callBack,this._dataset._queryType,this._dataset._queryFields);
	}
}


QueryCommand.prototype.getDataset = function(){
	return this._dataset;
}

QueryCommand.prototype.setDataset = function(dataset) {
	this._dataset = dataset;
}
