function page_onLoad(){
	initRegion();
}
function initRegion(){
	var parameter = window.dialogArguments[0];
 	var queryResult = null ;
	var ajaxCall = new NDAjaxCall(true); 
	
	var callBack = function( reply ){
		queryResult = reply.getResult() ; 
		regionTreeView.loadByXML(queryResult );
			
		var oldIds = new Array();
		oldIds = parameter["regionIds"].split(",");
	
		for( var i = 0 ; i < oldIds.length ; i ++ ){
			for( var j = 0; j < regionTreeView.items.length; j ++ ){
				if( regionTreeView.items[j].regionId == oldIds[i] ){
					regionTreeView.items[j].setChecked("true") ;
				}
			}
		}		
	}
	
	var arr = new Array(); 
	arr[0] = "" + parameter["staffCode"] ;//工号;
	arr[1] = "" +  parameter["privilegeCode"];//权限编码
	arr[2] = "" +  parameter["regionLevel"]; //最低区域级别
	arr[3] = "" +  parameter["regionType"];//区域列表串
	
	/**
	 *采用分层下载的方式实现.如果当前的层次在regionLevel只上,则允许通过点击下载被点击的区域的下级
	 *区域,如果当前的层次已经是regionLevel所指定的区域了,则不需要再次向服务器端请求下载往下层次的
	 *区域.下载层次等于regionLevel的区域的时候,需要再传递一个权限编码参数,服务器端过滤掉不属于这个
	 *权限所控制的区域的记录,只把用户有权限的区域下载下来.
	**/
	ajaxCall.remoteCall("RegionService","getRegionByCond", arr, callBack); 

}
function btn_Confirm_onClick(){
	var regionArray = new Array();
	var items = regionTreeView.checkedItems;
	for( var i = 0; i < items.length ; i ++ ){
		regionArray[i] = items[i].regionId ;
	}
	window.returnValue = regionArray.join(",");
	window.close();
}
function btn_Cancel_onClick(){
	window.returnValue = "" ;
	window.close();
}
function Parameter(){
	this.staffCode = "";
	this.privilegeCode = "";
	this.regionLevel = "";
	this.regionType = "";
	this.regionIds = "";
	this.checkBox = "";
}

//响应记录上的check box的点击事件
function regionChecked(){
	var selItem = regionTreeView.selectedItem;
	var parentItem = getRegionItemById( selItem.parentRegionId ) ;
	if( parentItem != null ){
		uncheckParentItem( selItem ) ;//取消所有上级节点的选中状态
	}
	if( selItem.getChecked()){
		uncheckChildren( selItem ) ;//取消所有下级节点的选中状态
	}
}

//递归取消所有的子节点的选中状态
function uncheckChildren( item ){
	if( item.items != null ){
		for( var i = 0 ; i < item.items.length ; i ++ ){
			var child = item.items[i] ;
			child.setChecked( false ) ;
			uncheckChildren( child ) ;
		}
	}
}

//递归取消所有的上级节点的选中状态
function uncheckParentItem( item ){
	var parentItem = null ;
	parentItem = getRegionItemById( item.parentRegionId ) ;
	if( parentItem != null ){
		parentItem.setChecked( false ) ;
		uncheckParentItem( parentItem ) ;
	}
}
function getRegionItemById( regionId ){
	for( var i = 0; i < regionTreeView.items.length; i ++ ){
		var item = regionTreeView.items[i] ;
		if( item.regionId == regionId ){
			return item ;
		}
	}
}