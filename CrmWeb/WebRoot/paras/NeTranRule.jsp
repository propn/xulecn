<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/NeTranRule.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
		
			<ui:dataset datasetId="dsQuery">
				<ui:field field="name" label="����" visible="true"></ui:field>
				<ui:field field="create_date" label="��ֹ��������" visible="true" type="date"></ui:field>
				<ui:field field="end_date" label="��ֹ��������" visible="true" type="date"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="dsNeTranRuleParaList" staticDataSource="true" 
				loadDataAction="NeTranRuleParaService" loadDataActionMethod="searchNeTranRuleParaData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">			
				<ui:field label="ת�������ʶ" field="tran_rule_id"  visible="false"></ui:field>
				<ui:field label="����������" field="para_id"  visible="false"></ui:field>
				<ui:field label="����������" field="para_name"  visible="true"></ui:field>
				<ui:field label="������˳��" field="seq"  visible="true"></ui:field>
				<ui:field label="���������" field="code"  visible="false"></ui:field>
			    <ui:parameter parameterId="tran_rule_id" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsNeTranRuleList" staticDataSource="true" 
				loadDataAction="NeTranRuleService" loadDataActionMethod="searchNeTranRuleData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
				<ui:field label="tran_rule_id" field="tran_rule_id"  visible="false" ></ui:field>
				<ui:field label="����" field="name"  visible="true"></ui:field>
				<ui:field label="ת������" field="get_method"  visible="true" attrCode="tran_rule_get_method"></ui:field>
				<ui:field label="��������" field="create_date"  visible="true"></ui:field>
				<ui:field label="ӳ������id" field="map_type_id"  visible="false"></ui:field>
				<ui:field label="ӳ������" field="map_type_name"  visible="true"></ui:field>
				<ui:field label="��̬���id" field="business_obj_id"  visible="false"></ui:field>
				<ui:field label="��̬���" field="business_obj_name"  visible="true"></ui:field>
				<ui:field label="��̬���" field="id"  visible="false"></ui:field>
				<ui:field label="��̬���" field="engine_name"  visible="true"></ui:field>
				<ui:field label="ϵͳ��ʶ" field="int_sys_id"  visible="false"></ui:field>
				<ui:field label="��̬sql" field="execute_sql"  visible="true" ></ui:field>

			    <ui:parameter parameterId="name" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="create_date" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="end_date" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsNeTranRuleForm"  readOnly="true" 
				loadDataAction="NeTranRuleService" loadDataActionMethod="getNeTranRuleById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="tran_rule_id" field="tran_rule_id"  visible="false" required="false"  validMsg="������...!" ></ui:field>
				<ui:field label="����" field="name"  visible="true" required="true" validMsg="����������"></ui:field>
				<ui:field label="ת������" field="get_method"  visible="true" attrCode="tran_rule_get_method" required="true" validMsg="��ѡ��ת������"></ui:field>
				<ui:field label="ӳ������id" field="map_type_id"  visible="false" ></ui:field>
				<ui:field label="ӳ������" field="map_type_name"  visible="true" keyField="map_type_id" popup="true"></ui:field>
				<ui:field label="��̬���id" field="business_obj_id"  visible="false"></ui:field>
				<ui:field label="��̬���" field="business_obj_name"  visible="true" keyField="business_obj_id" popup="true"></ui:field>
				<ui:field label="��̬���" field="id"  visible="false"></ui:field>
				<ui:field label="��̬���" field="engine_name"  visible="true" keyField="id" popup="true"></ui:field>
				<ui:field label="ϵͳ��ʶ" field="int_sys_id"  visible="false"></ui:field>
				<ui:field label="��������" field="create_date"  visible="false"></ui:field>
				<ui:field label="��̬sql" field="execute_sql"  visible="true" textarea="true" size="500" textareaHeight="50px"></ui:field>
								
				<ui:parameter parameterId="tran_rule_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
    <div id="layoutDefine">
    	<ui:layout type="border">
    	
    		<ui:pane position="left" style="width:230px;">
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
					<ui:bar type="search">
					  <ui:content>
					    <ui:layout type="border">
					      <ui:pane position="center">
					        <ui:form dataset="dsQueryPricePlanParam" submit="btn_queryPricePlanParam"></ui:form>
					      </ui:pane>
					      <ui:pane position="right" style="width:80px;">
					        <ui:button id="btn_queryPricePlanParam">��ѯ</ui:button>
					      </ui:pane>
					    </ui:layout>
					  </ui:content>
					</ui:bar>

					<ui:pane position="center">
						
								<ui:layout type="border">
									<ui:pane position="top" style="height:20px">
										<ui:bar type="search" desc="ת�������б�">
											<ui:content>
												<ui:button id="insertNeTranRule">����</ui:button>
												<ui:button id="updateNeTranRule">�޸�</ui:button>
												<ui:button id="deleteNeTranRule">ɾ��</ui:button>
											</ui:content>
										</ui:bar>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="dsNeTranRuleList" ></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<div class="customerpilot" extra="customerpilot" dataset="dsNeTranRuleList"></div>
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
												<ui:form dataset="dsNeTranRuleForm" fields="name,get_method,map_type_name,business_obj_name,engine_name" labelLayout="18%" inputLayout="31%"></ui:form>
												<ui:form dataset="dsNeTranRuleForm" fields="execute_sql"  labelLayout="18%" inputLayout="31%"></ui:form>
											</ui:content>
										</ui:panel>
									</ui:pane>	
									
									<ui:pane position="bottom"  style="height:25px;">
										<div style="clear:both;text-align:center;">
											<ui:button id="saveNeTranRule">����</ui:button>&nbsp;&nbsp;
											<ui:button id="cancelNeTranRule">ȡ��</ui:button>
										</div>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="ת������Դ������">
								<ui:layout type="border">
									<ui:pane position="center">
										
												<ui:layout type="border">
													
													<ui:pane position="top" style="height:20px">
														<ui:bar type="search" desc="ת������Դ�������б�">
															<ui:content>
																<ui:button id="insertNeTranRulePara">���</ui:button>
																<ui:button id="updateNeTranRulePara">�޸�</ui:button>
																<ui:button id="deleteNeTranRulePara">ɾ��</ui:button>
															</ui:content>
														</ui:bar>
													</ui:pane>
													<ui:pane position="center">
														<ui:table dataset="dsNeTranRuleParaList" ></ui:table>
													</ui:pane>
													<ui:pane position="bottom">
														<div class="customerpilot" extra="customerpilot" dataset="dsNeTranRuleParaList"></div>
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
