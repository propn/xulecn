<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/SpParaInfo.js"></script>
		<script language="javascript" src="js/WoParaCatg.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			
			<ui:dataset datasetId="dsWoParaCatgList" staticDataSource="true" 
				loadDataAction="WoParaCatgService" loadDataActionMethod="searchWoParaCatgData" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
				<ui:field label="paraDirId" field="para_dir_id"  visible="false" ></ui:field>
				<ui:field label="Ŀ¼����" field="dir_name"  visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsSpParaInfoList" staticDataSource="true" 
				loadDataAction="SpParaInfoService" loadDataActionMethod="searchSpParaInfoData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="para_id��������" field="para_id"  visible="false" ></ui:field>

																
				<ui:field label="����" field="name"  visible="true"></ui:field>
																
				<ui:field label="����" field="para_code"  visible="true"></ui:field>
				
				<ui:field label="����" field="para_type"  visible="true"></ui:field>
				
				<ui:field label="�Ƿ�ؼ�������" field="is_key"  visible="true" attrCode="para_info_is_key"></ui:field>
																
				<ui:field label="������ڵ�" field="node_path"  visible="true"></ui:field>
																
				<ui:field label="�ڵ�����" field="node_attr"  visible="true"></ui:field>
																
				
			 	<ui:parameter parameterId="para_dir_id" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsSpParaInfoForm"  readOnly="true" 
				loadDataAction="SpParaInfoService" loadDataActionMethod="getSpParaInfoById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="para_id" field="para_id"  visible="false" required="false"  validMsg="������...!" ></ui:field>
				<ui:field label="para_dir_id" field="para_dir_id"  visible="false"></ui:field>
																
				<ui:field label="����������" field="name"  visible="true" required="true" validMsg="����������������"></ui:field>
																
				<ui:field label="���������" field="para_code"  visible="true" required="true" validMsg="���������������"></ui:field>
																
				<ui:field label="������ڵ�" field="node_path"  visible="true" required="true" validMsg="������������ڵ�"></ui:field>
																
				<ui:field label="�ڵ�����" field="node_attr"  visible="true"></ui:field>						
																
				<ui:field label="����������" field="para_type"  visible="true"></ui:field>
																
				<ui:field label="�Ƿ�ؼ�������" field="is_key"  visible="true" attrCode="para_info_is_key" blankValue="true" required="true"  validMsg="��ѡ���Ƿ�ؼ�������!"></ui:field>
												
				<ui:field label="��ע" field="comments"  visible="true" textarea="true" textareaHeight="50px"></ui:field>			
				<ui:parameter parameterId="para_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
    <div id="layoutDefine">
    	<ui:layout type="border">
    	
    		<ui:pane position="left" style="width:200px;">
					<ui:panel type="titleList" desc="Ŀ¼�б�">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="dsWoParaCatgList" ></ui:table>
								</ui:pane>
							</ui:layout>	
						</ui:content>
					</ui:panel>
				</ui:pane>

			<ui:pane position="center">
				<ui:layout type="border">
					<ui:pane position="center">
								<ui:layout type="border">
									<ui:pane position="top" style="height:20px">
										<ui:bar type="search" desc="Դ�������б�">
											<ui:content>
												<ui:button id="insertWoParaCatg">����Ŀ¼</ui:button>
												<ui:button id="updateWoParaCatg">�޸�Ŀ¼</ui:button>
												<ui:button id="deleteWoParaCatg">ɾ��Ŀ¼</ui:button>
												<ui:button id="insertSpParaInfo">����</ui:button>
												<ui:button id="updateSpParaInfo">�޸�</ui:button>
												<ui:button id="deleteSpParaInfo">ɾ��</ui:button>
											</ui:content>
										</ui:bar>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="dsSpParaInfoList" ></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<div class="customerpilot" extra="customerpilot" dataset="dsSpParaInfoList"></div>
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
												<ui:form dataset="dsSpParaInfoForm" labelLayout="18%" inputLayout="31%"></ui:form>
											</ui:content>
										</ui:panel>
									</ui:pane>	
									<ui:pane position="bottom"  style="height:25px;">
										<div style="clear:both;text-align:center;">
											<ui:button id="saveSpParaInfo">����</ui:button>&nbsp;&nbsp;
											<ui:button id="cancelSpParaInfo">ȡ��</ui:button>
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
