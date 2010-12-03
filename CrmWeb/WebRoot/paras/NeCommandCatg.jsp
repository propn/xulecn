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
			var actionType = "insertNeCommandCatg";
			function page_onLoad(){
				var catgId = window.dialogArguments;
				if(null != catgId && '' != catgId){
					var para = dsNeCommandCatgForm.parameters() ; 
					para.setValue('command_catalog_id' ,catgId );
					dsNeCommandCatgForm.reloadData() ;
					actionType = "updateNeCommandCatg";
				}
			}
		function NeCommandCatgCheck(){
			return true ;
		}
		function saveNeCommandCatg_onClick(){
			if (!$("form_dsNeCommandCatgForm").validate()) return;
			if(!NeCommandCatgCheck()) return ;
			var NeCommandCatgVO = extractBeanFromDataset(dsNeCommandCatgForm, 'java.util.HashMap');
			
			NDAjaxCall.getSyncInstance().remoteCall('NeCommandCatgService',actionType,[NeCommandCatgVO],
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
		function cancelNeCommandCatg_onClick(){
			window.close();
		}
		</script>
	</head>
	<body>
		<div id="datasetDefine">	
			<ui:dataset datasetId="dsNeCommandCatgForm"  readOnly="true" 
				loadDataAction="NeCommandCatgService" loadDataActionMethod="getNeCommandCatgById" 
				readOnly="false" autoLoadPage="true" async="true" loadAsNeeded="true" >							
				<ui:field label="id" field="command_catalog_id"  visible="false"></ui:field>											
				<ui:field label="目录名称" field="name"  visible="true"></ui:field>
				<ui:parameter parameterId="command_catalog_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				
				<ui:pane position="center">
					<ui:panel type="titleList" desc="目标数据项目录管理">
						<ui:content>
							<ui:form dataset="dsNeCommandCatgForm"  labelLayout="30%" inputLayout="60%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="saveNeCommandCatg">保存</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelNeCommandCatg">取消</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
