<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/NeCommandPara.js"></script>
		<script language="javascript" src="js/NeCommandCatg.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
			
			<ui:dataset datasetId="dsNeCommandCatgList" staticDataSource="true" 
				loadDataAction="NeCommandCatgService" loadDataActionMethod="searchNeCommandCatgData" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >						
				<ui:field label="Ŀ¼Id" field="command_catalog_id"  visible="false" ></ui:field>									
				<ui:field label="Ŀ¼����" field="name"  visible="true"></ui:field>
			</ui:dataset>

			
			<ui:dataset datasetId="dsQuery">
				<ui:field field="name" label="����" visible="true"></ui:field>
				<ui:field field="para_code" label="����" visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsNeCommandParaList" staticDataSource="true" 
				loadDataAction="NeCommandParaService" loadDataActionMethod="searchNeCommandParaData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="Ŀ��������" field="command_id"  visible="false" ></ui:field>
									
				<ui:field label="ת������" field="tran_rule_id"  visible="false"></ui:field>
																
				<ui:field label="����" field="name"  visible="true" ></ui:field>
																
				<ui:field label="����" field="para_code"  visible="true" ></ui:field>
																
				<ui:field label="ȡֵ��ʽ" field="get_method"  visible="true" attrCode="get_method"></ui:field>
				<ui:field label="�Ƿ�ؼ�������" field="is_key"  visible="true" attrCode="is_key"></ui:field>	
				<ui:field label="������ڵ�" field="node_path"  visible="true" ></ui:field>
																
				<ui:field label="�ڵ�����" field="node_attr"  visible="true"></ui:field>
				<ui:field label="������Ŀ¼" field="command_catalog_id"  visible="false"></ui:field>
				<ui:field label="������Ŀ¼" field="catalogname"  visible="true"></ui:field>
						
				<ui:field label="Դ������" field="para_id"  visible="false"></ui:field>
																
				<ui:field label="����������" field="para_type"  visible="false"></ui:field>
																
				<ui:field label="ָ�������" field="cmd_regexp"  visible="false"></ui:field>
																
				<ui:field label="��ֵ̬" field="default_value"  visible="false"></ui:field>
				   
			    <ui:parameter parameterId="name" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="para_code" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="command_catalog_id" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsNeCommandParaForm"  readOnly="true" 
				loadDataAction="NeCommandParaService" loadDataActionMethod="getNeCommandParaById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="Ŀ��������" field="command_id"  visible="false" required="false"  validMsg="������...!" ></ui:field>
																
				<ui:field label="������Ŀ¼" field="command_catalog_id"  visible="false"></ui:field>
																
																
				<ui:field label="����" field="name"  visible="true" required="true" validMsg="����������"></ui:field>
																
				<ui:field label="����" field="para_code"  visible="true" required="true" validMsg="���������"></ui:field>
																
				<ui:field label="ȡֵ��ʽ" field="get_method"  visible="true" attrCode="get_method" blankValue="true" required="true" validMsg="��ѡ��ȡֵ��ʽ"></ui:field>
																
				<ui:field label="Դ������ID" field="para_id"  visible="false"></ui:field>
				<ui:field field="paraname" label="Դ������" popup="true" keyField="para_id" visible="true" ></ui:field>
																
				<ui:field label="����������" field="para_type"  visible="true"></ui:field>
																
				<ui:field label="ָ�������" field="cmd_regexp"  visible="true"></ui:field>
																
				<ui:field label="��ֵ̬" field="default_value"  visible="true"></ui:field>
																
				<ui:field label="������ڵ�" field="node_path"  visible="true" required="true" validMsg="������������ڵ�����" size="128"></ui:field>
																
				<ui:field label="�ڵ�����" field="node_attr"  visible="true"></ui:field>
																
				<ui:field label="�Ƿ�ؼ�������" field="is_key"  visible="true" required="true" attrCode="is_key" blankValue="true" validMsg="��ѡ���Ƿ�Ϊ�ؼ�������"></ui:field>
												
				<ui:field label="ת������ID" field="tran_rule_id"  visible="false"></ui:field>	
		   	 	<ui:field field="rulename" label="ת������" popup="true" keyField="tran_rule_id"  visible="true"></ui:field>
		 	
				<ui:parameter parameterId="command_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
   <div id="layoutDefine">
    	<ui:layout type="border">
    	
    		<ui:pane position="left" style="width:200px;">
					<ui:panel type="titleList" desc="Ŀ¼�б�">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="dsNeCommandCatgList" ></ui:table>
								</ui:pane>
							</ui:layout>	
						</ui:content>
					</ui:panel>
				</ui:pane>

			<ui:pane position="center">
				<ui:layout type="border">
				
					<ui:pane position="center">
						<ui:panel type="titleList" desc="Ŀ��������">
						
							<ui:content>
								<ui:layout type="border">
									<ui:pane position="top" style="height:30px">
										<ui:layout type="border">
											<ui:pane position="center" >
												<ui:form dataset="dsQuery" labelLayout="20%" inputLayout="30%"></ui:form>
											</ui:pane>
											<ui:pane position="right" style="width:340px">
												<div style="text-align:right;">
													<ui:button id="btn_query">��ѯ</ui:button>
													<ui:button id="insertNeCommandCatg">����Ŀ¼</ui:button>
													<ui:button id="updateNeCommandCatg">�޸�Ŀ¼</ui:button>
													<ui:button id="deleteNeCommandCatg">ɾ��Ŀ¼</ui:button>
													<ui:button id="insertNeCommandPara">����</ui:button>
													<ui:button id="updateNeCommandPara">�޸�</ui:button>
													<ui:button id="deleteNeCommandPara">ɾ��</ui:button>
												</div>
											</ui:pane>
										</ui:layout>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="dsNeCommandParaList" ></ui:table>
									</ui:pane>
									<ui:pane position="bottom" style="height:20px">
										<div class="customerpilot" extra="customerpilot" dataset="dsNeCommandParaList"></div>
									</ui:pane>
									
								</ui:layout>
							</ui:content>
						</ui:panel>
					</ui:pane>
					
					<ui:pane position="bottom" style="height:300px">
						<ui:tabpane id="tab1" maximizable="true" minimizable="true">
							<ui:tabpage desc="������Ϣ">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:panel type="titleList">
											<ui:content>
												<ui:form dataset="dsNeCommandParaForm" labelLayout="18%" inputLayout="31%"></ui:form>
											</ui:content>
										</ui:panel>
									</ui:pane>	
									<ui:pane position="bottom"  style="height:25px;">
										<div style="clear:both;text-align:center;">
											<ui:button id="saveNeCommandPara">����</ui:button>&nbsp;&nbsp;
											<ui:button id="cancelNeCommandPara">ȡ��</ui:button>
										</div>
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
