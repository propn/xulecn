<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<%--
	替换修改说明 ：
	1.dsQuery数据集，参数字段、名称
	2.dsSpParaRecordListList 参数
	3.dsSpParaRecordListList 字段显隐，字段类型
	4.dsSpParaRecordListForm 字段显隐，字段类型、验证信息
	5. 替换完毕，删除此段文字！
 --%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/SpParaRecordList.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
		
			<ui:dataset datasetId="dsQuery">
				<%--need-replace 查询参数,需要根据业务实际需要设定！ --%>		
				<ui:field field="param1" label="查询参数1" visible="true"></ui:field>
				<ui:field field="param2" label="查询参数2" visible="true"></ui:field>
				<ui:field field="param3" label="查询参数3" attrCode="DC_LAN_CODE" visible="true"  blankValue="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsSpParaRecordListList" staticDataSource="true" 
				loadDataAction="SpParaRecordListService" loadDataActionMethod="searchSpParaRecordListData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="record_id中文描述" field="record_id"  visible="true"></ui:field>
																
				<ui:field label="command_id中文描述" field="command_id"  visible="true"></ui:field>
											   
			    <%--need-replace 查询参数,需要根据业务实际需要设定！ --%>		
			    <ui:parameter parameterId="param1" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param2" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param3" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsSpParaRecordListForm"  readOnly="true" 
				loadDataAction="SpParaRecordListService" loadDataActionMethod="getSpParaRecordListById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="record_id中文描述" field="record_id"  visible="true"></ui:field>
																
				<ui:field label="command_id中文描述" field="command_id"  visible="true"></ui:field>
												
							</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:280px;">
					<ui:panel type="titleList" desc="SpParaRecordList列表数据">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="top" style="height:55px;">
									<ui:layout type="border">
									<ui:pane position="center" style="width:100%">
										<ui:form dataset="dsQuery" labelLayout="15%" inputLayout="15%"></ui:form>
									</ui:pane>
									<ui:pane position="right" style="width:50px;margin-top:25px;margin-right:5px;">
										<ui:button id="btn_query">查询</ui:button>
									</ui:pane>
									</ui:layout>
								</ui:pane>
								<ui:pane position="center" style="width:100%">
									<ui:table dataset="dsSpParaRecordListList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom" style="width:100%">
									<div class="customerpilot" dataset="dsSpParaRecordListList">
									</div>
								</ui:pane>
																
							</ui:layout>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="center">
					<ui:panel type="titleList" desc="SpParaRecordList表单信息">
						<ui:content>
							<ui:form dataset="dsSpParaRecordListForm"  labelLayout="12%" inputLayout="20%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="insertSpParaRecordList">新增</ui:button>&nbsp;
						<ui:button id="updateSpParaRecordList">修改</ui:button>&nbsp;&nbsp;
						<ui:button id="saveSpParaRecordList">保存</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelSpParaRecordList">取消</ui:button>
						<ui:button id="deleteSpParaRecordList">删除</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
