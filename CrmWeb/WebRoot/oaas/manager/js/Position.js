function page_onLoad(){
	initPostion();
}

//初始化TreeList组件的数据显示
function initPostion(){
	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		var queryResult = reply.getResult() ;
		document.all.positionTreeView.loadByXML(queryResult);

		//在详细面板对应的Dataset上插入一个空记录
		positionInfo.insertRecord(null);
		globleItem = document.all.positionTreeView.items[0]; 
		fillValues(globleItem);
	}
	
	ajaxCall.remoteCall("PrivilegeService","getPositionList",[],callBack);
}

//填充第一行的信息到详细信息面板
function fillValues(item){
	for(var ele in item){
		if(positionInfo.getField(ele)){
			positionInfo.setValue(ele, item[ele]);
		}
	}
}

//表格的点击事件，在详细面板中显示当前被点中的记录的详细信息
function clickPosition(){
	var positionTree = document.all.positionTreeView;
	var item = positionTree.selectedItem;
	
	cleanValues() ;
	fillValues(item);
	positionInfo.setReadOnly("true");
	actionType = "";
}

//清楚详细面板上的信息
function cleanValues(){
	positionInfo.clearData();
	positionInfo.insertRecord(null);
}

var actionType = "";

//增加岗位
function addPosition_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再增加岗位!") ;
		return ;
	}
	actionType = "insert" ;
	positionInfo.setReadOnly(false);	
	cleanValues();
}

//编辑岗位信息
function editPosition_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再编辑员工!") ;
		return ;
	}
	actionType = "update" ;
	positionInfo.setReadOnly(false);
}

//获取当前被选中的权限记录的id
function getCurrentPositionId(){
	var positionTree = document.all.positionTreeView;
	var selItem = positionTree.selectedItem;
	if( selItem != null ){
		return selItem.positionId ;
	}else{
		return null ;
	}
}

//删除岗位信息
function deletePosition_onClick(){
	if( actionType != "" ){
		alert("请先保存或者取消当前的操作再删除员工!") ;
		return ;
	}
	if( !confirm( "您确信要删除当前岗位记录吗?" )){
		return ;
	}
		var currentPositionId = getCurrentPositionId();
		
		var ajaxCall = new NDAjaxCall(true);
		
		var callBack = function( reply ){
			var deleteResult = reply.getResult() ;
			if( deleteResult ){
				deletePositionFromTreeList();
				alert("删除岗位成功！");
			}else{
				alert("岗位已经被分配使用,删除岗位失败!" );
			}
		}
		
		ajaxCall.remoteCall("PrivilegeService","deletePosition",[currentPositionId],callBack);
}

//从TreeList上删除一个记录
function deletePositionFromTreeList(){
	var positionTree = document.all.positionTreeView;
	var selItem = positionTree.selectedItem;
	selItem.remove();
}

function btn_cancel_onClick(){
	actionType = "";
	positionInfo.setReadOnly(true);	
	clickPosition();
}

function btn_commitPosition_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if( !$("positionForm" ).validate()){
		return ;
	}
	var position = new Position();

	//将Dataset里面的数据赋值给菜单对象
	for(var ele in position){  
		if(positionInfo.getField(ele)){
			position[ele] = positionInfo.getValue( ele );
		} 
	}
	
	var ajaxCall = new NDAjaxCall(true);
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( actionType == "insert" ){//增加操作
			//服务器端返回新增加的权限的ID
			refreshDatainfo(result);//完成增加以后，必须重新初始化关联的Dataset里面的响应信息				
			addPositionToTreeList();	 
			alert("增加岗位成功！");	 
		}else if ( actionType == "update" ){//编辑操作
			refreshTreeList();
			alert("编辑岗位成功!");
		}
		positionInfo.setReadOnly(true);	
		actionType = "";
	}
	
	var arr = new Array();
	arr[0] = position;
	
	if( actionType == "insert" ){//增加操作
		ajaxCall.remoteCall("PrivilegeService","insertPosition",arr,callBack);
	}else if ( actionType == "update" ){//编辑操作
		ajaxCall.remoteCall("PrivilegeService","updatePosition",arr,callBack);
	}
}

function refreshDatainfo(id){
	positionInfo.setValue("positionId", id ) ;
}

//在Update当前的岗位以后，更新TreeList上对应的记录
function refreshTreeList(){
	var positionTree = document.all.positionTreeView;
	var position = positionTree.selectedItem;
	
	for(var ele in position){
		if(positionInfo.getField(ele)){
			position[ele] = positionInfo.getValue( ele );
		} 
	}
	
	position.refresh();
}

//在TreeList上增加一个记录 
function addPositionToTreeList(){
	var positionTree = document.all.positionTreeView;
	var position = positionTree.createTreeNode();
	var position1 = new Position() ;
	
	for( var ele in position1 ){
		if(positionInfo.getField(ele)){
			position[ele] = positionInfo.getValue( ele ) ;
		}
	}
	positionTree.add(position);
	position.setSelected(); 
}

function Position(){
	this.positionId = "";
	this.positionName  = "";
	this.positionDesc = "";
	this.state = "";
	this.stateDate  = "";
}