<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="xa.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
		
			<ui:dataset datasetId="dsQuery">
				<ui:field field="mName" label="����-����" visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsList" staticDataSource="true" loadDataAction="XAService" loadDataActionMethod="searchData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" 
				loadAsNeeded="true" paginate="true">

				<ui:field field="id" label="����ID" visible="false"></ui:field>
				<ui:field field="sid" label="�ӱ�ID" visible="false"></ui:field>
				<ui:field field="fId" label="�ӱ����" visible="false"></ui:field>
					
			    <ui:field field="mName" label="����-����" visible="true"></ui:field>
				<ui:field field="mDesc" label="����-������Ϣ" visible="true"></ui:field>
				<ui:field field="sName" label="�ӱ�-����" visible="true"></ui:field>
				<ui:field field="sDesc" label="�ӱ�-������Ϣ" visible="true"></ui:field>		
			</ui:dataset>

			<ui:dataset datasetId="dsSecondForm"  readOnly="true" >
				<ui:field field="sid" label="�ӱ�ID" visible="false"></ui:field>
				<ui:field field="fId" label="�ӱ����" visible="false"></ui:field>
				<ui:field field="sName" label="�ӱ�-����" visible="true"></ui:field>
				<ui:field field="sDesc" label="�ӱ�-������Ϣ" visible="true"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="dsMasterForm"  readOnly="true" >
				<ui:field field="id" label="����ID" visible="false"></ui:field>			
				<ui:field field="mName" label="����-����" visible="true"></ui:field>
				<ui:field field="mDesc" label="����-������Ϣ" visible="true"></ui:field>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:280px;">
					<ui:panel type="titleList" desc="�б�����">
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
									<ui:table dataset="dsList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom" style="width:100%">
									<div class="customerpilot" dataset="dsList">
									</div>
								</ui:pane>
																
							</ui:layout>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="center">
					<ui:panel type="titleList" desc="����Ϣ">
						<ui:content>
							<ui:form dataset="dsMasterForm"  labelLayout="12%" inputLayout="20%"></ui:form>
							<ui:form dataset="dsSecondForm" labelLayout="12%" inputLayout="20%"></ui:form>
						</ui:content>
					</ui:panel>
				</ui:pane>
				<ui:pane position="bottom"  style="height:25px;">
					<div style="clear:both;text-align:center;">
						<ui:button id="insert">����</ui:button>&nbsp;
						<ui:button id="update">�޸�</ui:button>&nbsp;&nbsp;
						<ui:button id="save">����</ui:button>&nbsp;&nbsp;
						<ui:button id="cancel">ȡ��</ui:button>
						<ui:button id="delete">ɾ��</ui:button>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
