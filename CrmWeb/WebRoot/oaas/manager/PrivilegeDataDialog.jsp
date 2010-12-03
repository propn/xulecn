<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
			function page_onLoad(){
				var privId = window.dialogArguments[0] ;
				var privType = window.dialogArguments[1] ;
				dataPrivilegeList.parameters().setValue("privType",privType);
				dataPrivilegeList.parameters().setValue("privId",privId);
				Dataset.reloadData( dataPrivilegeList ) ;
				
				var record = dataPrivilegeList.getFirstRecord() ;
				while( record ) {
					if( record.getValue("isChecked") == "1" ){
						record.setValue("select","true");
					}
					record = record.getNextRecord();
				}
			}
			
			function btnConfirm_onClick(){
				var record = dataPrivilegeList.getFirstRecord() ;
				var saveObjList = new Array() ;
				var i = 0 ;
				while( record ) {
					if( System.isTrue( record.getValue("select"))){
						var obj = new Object() ;
						obj.privId = window.dialogArguments[0] ;
						obj.dataPkey1 = record.getValue("dataId"); 
						obj.dataPkey2 = "-1" ; 
						obj.dataPkey3 = "-1"; 
						saveObjList[i] = obj ;
						i ++ ;
					}
					record = record.getNextRecord() ;
				}
				if( saveObjList.length == 0 ){
					alert("请选择记录!");
					return ;
				}
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					alert("增加成功!") ;
					window.returnValue = 0 ;
					window.close();
				}
				var privType = window.dialogArguments[1] ;
				ajaxCall.remoteCall("PrivilegeService","addDataPrivileges",[saveObjList,privType],callBack);
			}
			function btnCancel_onClick(){
				window.returnValue = -1 ;
				window.close() ;
			}
			function DataPrivilegeVO(){
				this.privId = "";
				this.dataPkey1 = "";
				this.dataPkey2 = "";
				this.dataPkey3 = "";
			}
		</script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="dataPrivilegeList" readOnly="true" loadDataAction="PrivilegeService" 
			loadDataActionMethod="getDataPrivilegeByPrivilegeType" staticDataSource="true" async="false">
				<ui:field field="select"></ui:field>
				<ui:field field="isChecked" label="isChecked" visible="false"></ui:field>
				<ui:field field="dataId" label="dataId" visible="false"></ui:field>
				<ui:field field="dataName" label="名称" visible="true"></ui:field>
				<ui:parameter parameterId="privType" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="privId" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
		
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:panel type="titleList" desc="权限关联数据">
					<ui:content>
						<ui:table dataset="dataPrivilegeList"></ui:table>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btnConfirm">确定</ui:button>
				<ui:button id="btnCancel">取消</ui:button>
			</ui:pane>
		</ui:layout>		
	
	</body>
</html>