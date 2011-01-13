function page_onLoad(){
	initRegion();
}
	
//初始化TreeList组件的数据显示
function initRegion(){
 	var queryResult = null ;

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		regionTreeView.loadByXML(queryResult);
	}
	ajaxCall.remoteCall("RegionService","getBillRegionList",["-1"],callBack);
}

//表格的点击事件，在详细面板中显示当前被点中的记录的详细信息
function clickRegion(){
	var regionTree = document.all.regionTreeView;
	var selItem = regionTree.selectedItem;
	//获取当前节点下的所有下级节点.
	downloadSubItems() ;
}

//当用户点击区域树表的时候,判断当前节点是否有下级节点,如果没有,
//到服务器端查看是否有下级节点,如果服务器端返回了该节点的下级
//节点数据,则将这些数据解析成为树表的下级节点,添加到树表上.
function downloadSubItems(){
	var selItem = regionTreeView.selectedItem ; 
	if( selItem.items ){ 
		return ; 
	} 
	if( selItem.regionLevel == "97D" ){ 
		return ;
	}
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ){
		var result = reply.getResult() ;
		if( result != "<items/>" ){
			selItem.insertByXML( result ) ;
			selItem.expand(true);
			if( selItem.items ){
				for( var i = 0; i < selItem.items.length; i ++ ){
					var item = selItem.items[i] ;
					if ( item.regionLevel == "97D" ){
						item.setBgColor( "#3399FF" ) ;
						//item.setFontColor( "red" ) ;
					}
				}
			}
		}
	}
	
	var regionId = selItem.regionId ;
	ajaxCall.remoteCall("RegionService","getBillRegionList",[regionId], callBack);
}

function btn_Confirm_onClick(){
	var selItem = regionTreeView.selectedItem ;
	if( selItem.regionLevel != "97D" ){
		alert("您选择的区域不是营业区,请重新选择!");
		return ;
	}
	
	var returnArray = new Array() ;
	returnArray[0] = selItem.regionId ;
	returnArray[1] = selItem.regionName ;
	window.returnValue = returnArray ;
	window.close() ;
}

function btn_Cancel_onClick(){
	window.returnValue = null ;
	window.close() ;
}