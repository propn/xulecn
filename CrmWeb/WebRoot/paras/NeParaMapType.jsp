<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
		var actionType = "insertNeParaMapType";
			function page_onLoad(){
				var map_type_id = window.dialogArguments;
				if(null != map_type_id && '' != map_type_id){
					catgActionType="updateNeParaMapType";
					var para = dsNeParaMapTypeForm.parameters() ; 
					para.setValue('map_type_id' ,map_type_id );
					dsNeParaMapTypeForm.reloadData() ;
					actionType = "updateNeParaMapType";
				}
			}
		function NeParaMapTypeCheck(){
			return true ;
		}
		function saveNeParaMapType_onClick(){
			if (!$("form_dsNeParaMapTypeForm").validate()) return;
			if(!NeParaMapTypeCheck()) return ;
			var NeParaMapTypeVO = extractBeanFromDataset(dsNeParaMapTypeForm, 'java.util.HashMap');
			
			NDAjaxCall.getSyncInstance().remoteCall('NeParaMapTypeService',actionType,[NeParaMapTypeVO],
					function(replay){
				var result = replay.getResult() ;
				if(result == true ){
				   alert('操作成功');
				   window.returnValue = true;
				}else{
				   alert('操作失败');
				}
				window.close();
			});
		}
		//取消
		function cancelNeParaMapType_onClick(){
			window.close();
		}
		</script>
	</head>
	<body>
		<div id="datasetDefine">	
			<ui:dataset datasetId="dsNeParaMapTypeForm"  readOnly="true" 
				loadDataAction="NeParaMapTypeService" loadDataActionMethod="getNeParaMapTypeById" 
				readOnly="false" autoLoadPage="true" async="true" loadAsNeeded="true" >
				<ui:field label="map_type_id" field="map_type_id"  visible="false" required="false"  validMsg="请输入...!" ></ui:field>										
				<ui:field label="名称" field="name"  visible="true"></ui:field>			
				<ui:parameter parameterId="map_type_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				
				<ui:pane position="center">
					<ui:panel type="titleList" desc="映射类型管理">
						<ui:content>
							<ui:form dataset="dsNeParaMapTypeForm"  labelLayout="30%" inputLayout="60%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="saveNeParaMapType">保存</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelNeParaMapType">取消</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
