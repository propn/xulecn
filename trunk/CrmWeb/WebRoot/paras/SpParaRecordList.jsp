<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<%--
	�滻�޸�˵�� ��
	1.dsQuery���ݼ��������ֶΡ�����
	2.dsSpParaRecordListList ����
	3.dsSpParaRecordListList �ֶ��������ֶ�����
	4.dsSpParaRecordListForm �ֶ��������ֶ����͡���֤��Ϣ
	5. �滻��ϣ�ɾ���˶����֣�
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
				<%--need-replace ��ѯ����,��Ҫ����ҵ��ʵ����Ҫ�趨�� --%>		
				<ui:field field="param1" label="��ѯ����1" visible="true"></ui:field>
				<ui:field field="param2" label="��ѯ����2" visible="true"></ui:field>
				<ui:field field="param3" label="��ѯ����3" attrCode="DC_LAN_CODE" visible="true"  blankValue="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsSpParaRecordListList" staticDataSource="true" 
				loadDataAction="SpParaRecordListService" loadDataActionMethod="searchSpParaRecordListData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="record_id��������" field="record_id"  visible="true"></ui:field>
																
				<ui:field label="command_id��������" field="command_id"  visible="true"></ui:field>
											   
			    <%--need-replace ��ѯ����,��Ҫ����ҵ��ʵ����Ҫ�趨�� --%>		
			    <ui:parameter parameterId="param1" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param2" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param3" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsSpParaRecordListForm"  readOnly="true" 
				loadDataAction="SpParaRecordListService" loadDataActionMethod="getSpParaRecordListById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="record_id��������" field="record_id"  visible="true"></ui:field>
																
				<ui:field label="command_id��������" field="command_id"  visible="true"></ui:field>
												
							</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:280px;">
					<ui:panel type="titleList" desc="SpParaRecordList�б�����">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="top" style="height:55px;">
									<ui:layout type="border">
									<ui:pane position="center" style="width:100%">
										<ui:form dataset="dsQuery" labelLayout="15%" inputLayout="15%"></ui:form>
									</ui:pane>
									<ui:pane position="right" style="width:50px;margin-top:25px;margin-right:5px;">
										<ui:button id="btn_query">��ѯ</ui:button>
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
					<ui:panel type="titleList" desc="SpParaRecordList������Ϣ">
						<ui:content>
							<ui:form dataset="dsSpParaRecordListForm"  labelLayout="12%" inputLayout="20%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="insertSpParaRecordList">����</ui:button>&nbsp;
						<ui:button id="updateSpParaRecordList">�޸�</ui:button>&nbsp;&nbsp;
						<ui:button id="saveSpParaRecordList">����</ui:button>&nbsp;&nbsp;
						<ui:button id="cancelSpParaRecordList">ȡ��</ui:button>
						<ui:button id="deleteSpParaRecordList">ɾ��</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>