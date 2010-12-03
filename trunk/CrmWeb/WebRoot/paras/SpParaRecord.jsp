<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/SpParaRecord.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
			
			<ui:dataset datasetId="dsQuery">	
				<ui:field field="record_name" label="��¼����" visible="true"></ui:field>
				<ui:field field="record_path" label="��¼·��" visible="true"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="dsSpParaRecordListList" staticDataSource="true" 
				loadDataAction="SpParaRecordListService" loadDataActionMethod="searchSpParaRecordListData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="record_id" field="record_id"  visible="false"></ui:field>
																
				<ui:field label="command_id" field="command_id"  visible="false"></ui:field>
				<ui:field field="name" label="����"  visible="true"></ui:field>
				<ui:field field="para_code" label="����"  visible="true"></ui:field>
				<ui:field field="get_method" label="ȡֵ��ʽ"  visible="true" attrCode="get_method"></ui:field>
				<ui:field field="is_key" label="�Ƿ�ؼ�������"  visible="true" attrCode="is_key"></ui:field>
				<ui:field field="node_path" label="������ڵ�"  visible="true"></ui:field>
				<ui:field field="node_attr" label="�ڵ�����"  visible="true"></ui:field>
											   	
			    <ui:parameter parameterId="record_id" type="string" value=""></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="dsSpParaRecordList" staticDataSource="true" 
				loadDataAction="SpParaRecordService" loadDataActionMethod="searchSpParaRecordData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="record_id" field="record_id"  visible="false" ></ui:field>
				
																
				<ui:field label="��¼����" field="record_name"  visible="true"></ui:field>
																
				<ui:field label="��¼·��" field="record_path"  visible="true"></ui:field>
																
				<ui:field label="��¼����" field="rec_type"  visible="false"></ui:field>

				<ui:field label="������������" field="group_order_type"  visible="true" attrCode="group_order_type"></ui:field>
						
				<ui:field label="�������" field="groupby_codes"  visible="false"></ui:field>	
													
				<ui:field label="Ŀ��·��" field="target_path"  visible="true"></ui:field>
										
				<ui:field label="�����������" field="orderby_asc_codes"  visible="false"></ui:field>
																
				<ui:field label="�����������" field="orderby_desc_codes"  visible="false"></ui:field>
																
											   	
			    <ui:parameter parameterId="record_name" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="record_path" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsSpParaRecordForm"  readOnly="true" 
				loadDataAction="SpParaRecordService" loadDataActionMethod="getSpParaRecordById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="record_id" field="record_id"  visible="false" required="false"  validMsg="������...!" ></ui:field>
																
				<ui:field label="��¼����" field="record_name"  visible="true" required="true" validMsg="��������д��¼����"></ui:field>
																
				<ui:field label="��¼·��" field="record_path"  visible="true" required="true" validMsg="��������д��¼·��"></ui:field>
																
				<ui:field label="��¼����" field="rec_type" visible="false" size="9"></ui:field>
																
				<ui:field label="�������" field="groupby_codes"  visible="true"></ui:field>
																
				<ui:field label="�����������" field="orderby_asc_codes"  visible="true"></ui:field>
																
				<ui:field label="�����������" field="orderby_desc_codes"  visible="true"></ui:field>
																
				<ui:field label="������������" field="group_order_type"  visible="true" attrCode="group_order_type" blankValue="true" required="true" validMsg="��ѡ�������������"></ui:field>
																
				<ui:field label="Ŀ��·��" field="target_path"  visible="true" required="true" validMsg="Ŀ��·������Ϊ��!"></ui:field>
			
				<ui:parameter parameterId="record_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
    <div id="layoutDefine">
    	<ui:layout type="border">
    	
    		<ui:pane position="left" style="width:200px;">
					<ui:panel type="titleList" desc="��ѯ����">
						<ui:content>
							<ui:form dataset="dsQuery" labelLayout="40%" inputLayout="60%"></ui:form>
							<div style="clear:both;text-align:center;">
								<ui:button id="btn_query">��ѯ</ui:button>
								<ui:button id="btn_reset">����</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>

			<ui:pane position="center">
				<ui:layout type="border">
				
					<ui:pane position="center">
								<ui:layout type="border">
									<ui:pane position="top" style="height:20px">
										<ui:bar type="search" desc="�������¼�б�">
											<ui:content>
												<ui:button id="insertSpParaRecord">����</ui:button>
												<ui:button id="updateSpParaRecord">�޸�</ui:button>
												<ui:button id="deleteSpParaRecord">ɾ��</ui:button>
											</ui:content>
										</ui:bar>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="dsSpParaRecordList" ></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<div class="customerpilot" extra="customerpilot" dataset="dsSpParaRecordList"></div>
									</ui:pane>
								</ui:layout>
					</ui:pane>
					
					<ui:pane position="bottom" style="height:300px">
						<ui:tabpane id="tab1" maximizable="true" minimizable="true">
							<ui:tabpage desc="������Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:panel type="titleList">
											<ui:content>
												<ui:form dataset="dsSpParaRecordForm" labelLayout="18%" inputLayout="31%"></ui:form>
											</ui:content>
										</ui:panel>
									</ui:pane>	
									<ui:pane position="bottom"  style="height:25px;">
										<div style="clear:both;text-align:center;">
											<ui:button id="saveSpParaRecord">����</ui:button>&nbsp;&nbsp;
											<ui:button id="cancelSpParaRecord">ȡ��</ui:button>
										</div>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="Ŀ���������б�">
								<ui:layout type="border">
									<ui:pane position="center">
										
												<ui:layout type="border">
													<ui:pane position="top" style="height:20px">
														<ui:bar type="search" desc="Ŀ���������б�">
															<ui:content>
																<ui:button id="insertParaRecordList">���Ŀ��������</ui:button>
													<ui:button id="deleteParaRecordList">ɾ��Ŀ��������</ui:button>
															</ui:content>
														</ui:bar>
													</ui:pane>
													<ui:pane position="center">
														<ui:table dataset="dsSpParaRecordListList" ></ui:table>
													</ui:pane>
													<ui:pane position="bottom">
														<div class="customerpilot" extra="customerpilot" dataset="dsSpParaRecordListList"></div>
													</ui:pane>
												</ui:layout>
									</ui:pane>
								</ui:layout>	
							</ui:tabpage>
						
					</ui:tabpane>
					</ui:pane>
				</ui:layout>
			</ui:pane>

    	</ui:layout>
    </div>
	</body>
</html>
