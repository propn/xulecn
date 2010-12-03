<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		
	<script>
		var privType = "";
		var privId = "";
		var addDataPrivilegeDialogURL = "";
		
		//页面加载
		function page_onLoad(){
			privType = GetArgsFromHrefs(location.search,"privilegeType");
			privId = GetArgsFromHrefs(location.search,"privilegeId");
			addDataPrivilegeDialogURL = GetArgsFromHrefs(location.search,"addDataPrivilegeDialogURL");
			queryData();//查询当前权限对应的菜单信息
		}
		
		//增加权限菜单
		function btn_addMenuDataPrivilege_onClick(){
			var returnValue = window.showModalDialog(addDataPrivilegeDialogURL,[privId,privType],"dialogHeight: 408pt; dialogWidth: 344pt;");
			if( returnValue  == 0 ){
				queryData();
			}
		}
		
		//查询当前权限对应的菜单信息
		function queryData(){
		 	var queryResult = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				queryResult = reply.getResult() ;
				menuTreeView.loadByXML(queryResult);
			}
			ajaxCall.remoteCall("PrivilegeService","getMenuDataPrivilege",[privId],callBack);		
		}
		

		//删除权限关联的菜单
		function btn_deleteMenuDataPrivilege_onClick(){
			var checkedItems = menuTreeView.checkedItems;
			var objList = new Array();
			for(var i=0;i<checkedItems.length;i++){
						var obj = new MmDataPrivVO() ;
						obj["privId"] = privId ;
						obj["dataPkey1"]= checkedItems[i].menuId;
						obj["dataPkey2"] = "-1" ; 
						obj["dataPkey3"] = "-1"; 
						obj["dataPkey4"] = ""; 
						obj["name"] = "" ; 
						obj["datasetName"] = "b"; 
						obj["fieldName"] = "" ; 
						obj["offerName"] = ""; 
						obj["serviceOfferName"] = "";
				
				objList[objList.length] = obj ;
			}
			if( objList.length == 0 ){
				alert("请选择您要删除的记录！");
				return ;
			}
			if( !confirm("您确定要删除当前记录吗?")){
				return ;
			}
			
			var ajaxCall = new NDAjaxCall( true ) ;
			var callBack = function( reply ) {
				alert("删除成功!");
				queryData();
			}
			ajaxCall.remoteCall("PrivilegeService","deleteDataPrivilege",[objList],callBack);
		}
		function MmDataPrivVO(){
				this.privId = "";
				this.dataPkey1 = "";
				this.dataPkey2 = "";
				this.dataPkey3 = "";
				this.dataPkey4 = "";
				this.name = "";
				this.datasetName = "";
				this.fieldName = "";
				this.offerName = "";
				this.serviceOfferName = "";
				this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.MmDataPrivVO' ;	
			}
	</script>
	
	</head>
	<body>
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:panel type="titleList" desc="权限关联数据">
					<ui:content>
						<ZTESOFT:treelist id="menuTreeView" class="treelist" showImage="true" showBorder="false" showCheck="true" contBorder="true" width="100%" height="100%" showHead="true">
							<ZTESOFT:columns>
								<ZTESOFT:column width="70%" display="true" displayText="菜单名称" propertyName="menuName" />
								<ZTESOFT:column width="30%" display="true" displayText="菜单编码" propertyName="menuCode" />							
								<ZTESOFT:column width="" display="false" displayText="菜单ID" propertyName="menuId" />								
								<ZTESOFT:column width="" display="false" displayText="菜单连接" propertyName="targetName" />								
								<ZTESOFT:column width="" display="false" displayText="参数" propertyName="para" />
								<ZTESOFT:column width="" display="false" displayText="权限名称" propertyName="privilegeName" />
								<ZTESOFT:column width="" display="false" displayText="菜单打开标志" propertyName="openFlag" />
								<ZTESOFT:column width="" display="false" displayText="权限判断标志" propertyName="privilegeFlag" />
								<ZTESOFT:column width="" display="false" displayText="菜单类型" propertyName="menuType" />
								<ZTESOFT:column width="" display="false" displayText="系统标识" propertyName="systemId" />
								<ZTESOFT:column width="" display="false" displayText="同级序号" propertyName="orderId" />
								<ZTESOFT:column width="" display="false" displayText="有效标志" propertyName="validFlag" />
								<ZTESOFT:column width="" display="false" displayText="菜单级别" propertyName="menuGrade" />
								<ZTESOFT:column width="" display="false" displayText="上级菜单标识" propertyName="superId" />
								<ZTESOFT:column width="" display="false" displayText="菜单图片路径" propertyName="imagePath" />
								<ZTESOFT:column width="" display="false" displayText="备注" propertyName="comments" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btn_addMenuDataPrivilege">增加</ui:button>
				<ui:button id="btn_deleteMenuDataPrivilege">删除</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
