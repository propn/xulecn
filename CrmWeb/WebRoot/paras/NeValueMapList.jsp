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
		var actionType = "insertNeValueMapList";
		function page_onLoad(){
			var args = window.dialogArguments;
			dsNeValueMapListForm.setValue("map_type_id",args['map_type_id'])
			if('2' == args['type']){
				var param = dsNeValueMapListForm.parameters() ;
				param.setValue("map_type_id",args['map_type_id']);
				param.setValue('para_value',args['para_value']);
				param.setValue('map_value',args['map_value']);
				dsNeValueMapListForm.reloadData();
				actionType = 'updateNeValueMapList';
			}
		}
		function NeValueMapListCheck(){
			return true ;
		}
		function saveNeValueMapList_onClick(){
			if (!$("form_dsNeValueMapListForm").validate()) return;
			if(!NeValueMapListCheck()) return ;
			var NeValueMapListVO = extractBeanFromDataset(dsNeValueMapListForm, 'java.util.HashMap');
			var paramMap = {};
			var param = dsNeValueMapListForm.parameters() ;
			paramMap['map_type_id'] = param.getValue("map_type_id");
			paramMap['para_value'] = param.getValue("para_value");
			paramMap['map_value'] = param.getValue("map_value");
			if(!validateRepart(NeValueMapListVO)) return
			NDAjaxCall.getSyncInstance().remoteCall('NeValueMapListService',actionType,[NeValueMapListVO,paramMap],
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
		function cancelNeValueMapList_onClick(){
			window.close();
		}
		function validateRepart(paramMap){
			var flag = true;
			NDAjaxCall.getSyncInstance().remoteCall('NeValueMapListService',"validateRepart",[paramMap],
					function(replay){
				var result = replay.getResult() ;
				if(result){
				   flag= false;
				   alert('此原值映射值已存在');
				}
			});
			return flag;
		}	
		</script>
	</head>
	<body>
		<div id="datasetDefine">	
			<ui:dataset datasetId="dsNeValueMapListForm"  readOnly="true" 
				loadDataAction="NeValueMapListService" loadDataActionMethod="getNeValueMapListById" 
				readOnly="false" autoLoadPage="true" async="true" loadAsNeeded="true" >
				<ui:field label="map_type_id" field="map_type_id"  visible="false"></ui:field>
				<ui:field label="源值" field="para_value"  visible="true" required="true" validMsg="源值不能为空"></ui:field>
				<ui:field label="映射值" field="map_value"  visible="true" required="true" validMsg="映射值不能为空"></ui:field>								
				<ui:parameter parameterId="map_type_id" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="para_value" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="map_value" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				
				<ui:pane position="center">
					<ui:panel type="titleList" desc="映射类型管理">
						<ui:content>
							<ui:form dataset="dsNeValueMapListForm"  labelLayout="30%" inputLayout="60%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="saveNeValueMapList">保存</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelNeValueMapList">取消</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
