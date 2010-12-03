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
				<ui:field field="name" label="名称" visible="true"></ui:field>
				<ui:field field="create_date" label="起止生成日期" visible="true" type="date"></ui:field>
				<ui:field field="end_date" label="终止生成日期" visible="true" type="date"></ui:field>
			</ui:dataset>
			<ui:dataset datasetId="dsNeTranRuleParaList" staticDataSource="true" 
				loadDataAction="NeTranRuleParaService" loadDataActionMethod="searchNeTranRuleParaData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">			
				<ui:field label="转换规则标识" field="tran_rule_id"  visible="false"></ui:field>
				<ui:field label="数据项名称" field="para_id"  visible="false"></ui:field>
				<ui:field label="数据项名称" field="para_name"  visible="true"></ui:field>
				<ui:field label="数据项顺序" field="seq"  visible="true"></ui:field>
				<ui:field label="数据项编码" field="code"  visible="false"></ui:field>
			    <ui:parameter parameterId="tran_rule_id" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsNeTranRuleList" staticDataSource="true" 
				loadDataAction="NeTranRuleService" loadDataActionMethod="searchNeTranRuleData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
				<ui:field label="tran_rule_id" field="tran_rule_id"  visible="false" ></ui:field>
				<ui:field label="名称" field="name"  visible="true"></ui:field>
				<ui:field label="转换类型" field="get_method"  visible="true" attrCode="tran_rule_get_method"></ui:field>
				<ui:field label="生成日期" field="create_date"  visible="true"></ui:field>
				<ui:field label="映射类型id" field="map_type_id"  visible="false"></ui:field>
				<ui:field label="映射类型" field="map_type_name"  visible="true"></ui:field>
				<ui:field label="静态组件id" field="business_obj_id"  visible="false"></ui:field>
				<ui:field label="静态组件" field="business_obj_name"  visible="true"></ui:field>
				<ui:field label="动态组件" field="id"  visible="false"></ui:field>
				<ui:field label="动态组件" field="engine_name"  visible="true"></ui:field>
				<ui:field label="系统标识" field="int_sys_id"  visible="false"></ui:field>
				<ui:field label="动态sql" field="execute_sql"  visible="true" ></ui:field>

			    <ui:parameter parameterId="name" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="create_date" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="end_date" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsNeTranRuleForm"  readOnly="true" 
				loadDataAction="NeTranRuleService" loadDataActionMethod="getNeTranRuleById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="tran_rule_id" field="tran_rule_id"  visible="false" required="false"  validMsg="请输入...!" ></ui:field>
				<ui:field label="名称" field="name"  visible="true" required="true" validMsg="请输入名称"></ui:field>
				<ui:field label="转换类型" field="get_method"  visible="true" attrCode="tran_rule_get_method" required="true" validMsg="请选择转换类型"></ui:field>
				<ui:field label="映射类型id" field="map_type_id"  visible="false" ></ui:field>
				<ui:field label="映射类型" field="map_type_name"  visible="true" keyField="map_type_id" popup="true"></ui:field>
				<ui:field label="静态组件id" field="business_obj_id"  visible="false"></ui:field>
				<ui:field label="静态组件" field="business_obj_name"  visible="true" keyField="business_obj_id" popup="true"></ui:field>
				<ui:field label="动态组件" field="id"  visible="false"></ui:field>
				<ui:field label="动态组件" field="engine_name"  visible="true" keyField="id" popup="true"></ui:field>
				<ui:field label="系统标识" field="int_sys_id"  visible="false"></ui:field>
				<ui:field label="生成日期" field="create_date"  visible="false"></ui:field>
				<ui:field label="动态sql" field="execute_sql"  visible="true" textarea="true" size="500" textareaHeight="50px"></ui:field>
								
				<ui:parameter parameterId="tran_rule_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
    <div id="layoutDefine">
    	<ui:layout type="border">
    	
    		<ui:pane position="left" style="width:230px;">
					<ui:panel type="titleList" desc="查询条件">
						<ui:content>
							<ui:form dataset="dsQuery" labelLayout="40%" inputLayout="60%"></ui:form>
							<div style="clear:both;text-align:center;">
								<ui:button id="btn_query">查询</ui:button>
								<ui:button id="btn_reset">重置</ui:button>
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
					        <ui:button id="btn_queryPricePlanParam">查询</ui:button>
					      </ui:pane>
					    </ui:layout>
					  </ui:content>
					</ui:bar>

					<ui:pane position="center">
						
								<ui:layout type="border">
									<ui:pane position="top" style="height:20px">
										<ui:bar type="search" desc="转换规则列表">
											<ui:content>
												<ui:button id="insertNeTranRule">新增</ui:button>
												<ui:button id="updateNeTranRule">修改</ui:button>
												<ui:button id="deleteNeTranRule">删除</ui:button>
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
							<ui:tabpage desc="基本信息">
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
											<ui:button id="saveNeTranRule">保存</ui:button>&nbsp;&nbsp;
											<ui:button id="cancelNeTranRule">取消</ui:button>
										</div>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="转换规则源数据项">
								<ui:layout type="border">
									<ui:pane position="center">
										
												<ui:layout type="border">
													
													<ui:pane position="top" style="height:20px">
														<ui:bar type="search" desc="转换规则源数据项列表">
															<ui:content>
																<ui:button id="insertNeTranRulePara">添加</ui:button>
																<ui:button id="updateNeTranRulePara">修改</ui:button>
																<ui:button id="deleteNeTranRulePara">删除</ui:button>
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
