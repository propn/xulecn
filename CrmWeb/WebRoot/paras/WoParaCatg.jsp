<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" >
			var catgActionType = "insertWoParaCatg";
			function page_onLoad(){
				var catgId = window.dialogArguments;
				if(null != catgId && '' != catgId){
					catgActionType="updateWoParaCatg";
					var para = dsWoParaCatgForm.parameters() ; 
					para.setValue('para_dir_id' ,catgId );
					dsWoParaCatgForm.reloadData() ;
				}
			}
			function WoParaCatgCheck(){
				return true ;
			}
			function saveWoParaCatg_onClick(){
				if(catgActionType == '' ) return ;
				if (!$("form_dsWoParaCatgForm").validate()) return;
				if(!WoParaCatgCheck()) return ;
				var WoParaCatgVO = extractBeanFromDataset(dsWoParaCatgForm, 'java.util.HashMap');
				
				NDAjaxCall.getSyncInstance().remoteCall('WoParaCatgService',catgActionType,[WoParaCatgVO],
						function(replay){
					var result = replay.getResult() ;
					catgActionType = '';
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
			function cancelWoParaCatg_onClick(){
				window.close();
			}

		</script>
	</head>
	<body>
		<div id="datasetDefine">	
			<ui:dataset datasetId="dsWoParaCatgForm"  readOnly="true" 
				loadDataAction="WoParaCatgService" loadDataActionMethod="getWoParaCatgById" 
				readOnly="false" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="para_dir_id" field="para_dir_id"  visible="false" required="false"  validMsg="请输入...!" ></ui:field>
								
				<ui:field label="目录名称" field="dir_name"  visible="true"></ui:field>
	
				<ui:parameter parameterId="para_dir_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
					<ui:panel type="titleList" desc="源数据项目录管理">
						<ui:content>
							<ui:form dataset="dsWoParaCatgForm"  labelLayout="30%" inputLayout="50%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="saveWoParaCatg">保存</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelWoParaCatg">取消</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
