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
				<ui:field label="目录Id" field="command_catalog_id"  visible="false" ></ui:field>									
				<ui:field label="目录名称" field="name"  visible="true"></ui:field>
			</ui:dataset>

			
			<ui:dataset datasetId="dsQuery">
				<ui:field field="name" label="名称" visible="true"></ui:field>
				<ui:field field="para_code" label="编码" visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsNeCommandParaList" staticDataSource="true" 
				loadDataAction="NeCommandParaService" loadDataActionMethod="searchNeCommandParaData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="目标数据项" field="command_id"  visible="false" ></ui:field>
									
				<ui:field label="转换规则" field="tran_rule_id"  visible="false"></ui:field>
																
				<ui:field label="名称" field="name"  visible="true" ></ui:field>
																
				<ui:field label="编码" field="para_code"  visible="true" ></ui:field>
																
				<ui:field label="取值方式" field="get_method"  visible="true" attrCode="get_method"></ui:field>
				<ui:field label="是否关键数据项" field="is_key"  visible="true" attrCode="is_key"></ui:field>	
				<ui:field label="数据项节点" field="node_path"  visible="true" ></ui:field>
																
				<ui:field label="节点属性" field="node_attr"  visible="true"></ui:field>
				<ui:field label="数据项目录" field="command_catalog_id"  visible="false"></ui:field>
				<ui:field label="数据项目录" field="catalogname"  visible="true"></ui:field>
						
				<ui:field label="源数据项" field="para_id"  visible="false"></ui:field>
																
				<ui:field label="数据项类型" field="para_type"  visible="false"></ui:field>
																
				<ui:field label="指令反馈正则" field="cmd_regexp"  visible="false"></ui:field>
																
				<ui:field label="静态值" field="default_value"  visible="false"></ui:field>
				   
			    <ui:parameter parameterId="name" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="para_code" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="command_catalog_id" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsNeCommandParaForm"  readOnly="true" 
				loadDataAction="NeCommandParaService" loadDataActionMethod="getNeCommandParaById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="目标数据项" field="command_id"  visible="false" required="false"  validMsg="请输入...!" ></ui:field>
																
				<ui:field label="数据项目录" field="command_catalog_id"  visible="false"></ui:field>
																
																
				<ui:field label="名称" field="name"  visible="true" required="true" validMsg="请输入名称"></ui:field>
																
				<ui:field label="编码" field="para_code"  visible="true" required="true" validMsg="请输入编码"></ui:field>
																
				<ui:field label="取值方式" field="get_method"  visible="true" attrCode="get_method" blankValue="true" required="true" validMsg="请选择取值方式"></ui:field>
																
				<ui:field label="源数据项ID" field="para_id"  visible="false"></ui:field>
				<ui:field field="paraname" label="源数据项" popup="true" keyField="para_id" visible="true" ></ui:field>
																
				<ui:field label="数据项类型" field="para_type"  visible="true"></ui:field>
																
				<ui:field label="指令反馈正则" field="cmd_regexp"  visible="true"></ui:field>
																
				<ui:field label="静态值" field="default_value"  visible="true"></ui:field>
																
				<ui:field label="数据项节点" field="node_path"  visible="true" required="true" validMsg="请输入数据项节点内容" size="128"></ui:field>
																
				<ui:field label="节点属性" field="node_attr"  visible="true"></ui:field>
																
				<ui:field label="是否关键数据项" field="is_key"  visible="true" required="true" attrCode="is_key" blankValue="true" validMsg="请选择是否为关键数据项"></ui:field>
												
				<ui:field label="转换规则ID" field="tran_rule_id"  visible="false"></ui:field>	
		   	 	<ui:field field="rulename" label="转换规则" popup="true" keyField="tran_rule_id"  visible="true"></ui:field>
		 	
				<ui:parameter parameterId="command_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
   <div id="layoutDefine">
    	<ui:layout type="border">
    	
    		<ui:pane position="left" style="width:200px;">
					<ui:panel type="titleList" desc="目录列表">
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
						<ui:panel type="titleList" desc="目标数据项">
						
							<ui:content>
								<ui:layout type="border">
									<ui:pane position="top" style="height:30px">
										<ui:layout type="border">
											<ui:pane position="center" >
												<ui:form dataset="dsQuery" labelLayout="20%" inputLayout="30%"></ui:form>
											</ui:pane>
											<ui:pane position="right" style="width:340px">
												<div style="text-align:right;">
													<ui:button id="btn_query">查询</ui:button>
													<ui:button id="insertNeCommandCatg">新增目录</ui:button>
													<ui:button id="updateNeCommandCatg">修改目录</ui:button>
													<ui:button id="deleteNeCommandCatg">删除目录</ui:button>
													<ui:button id="insertNeCommandPara">新增</ui:button>
													<ui:button id="updateNeCommandPara">修改</ui:button>
													<ui:button id="deleteNeCommandPara">删除</ui:button>
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
							<ui:tabpage desc="基本信息">
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
											<ui:button id="saveNeCommandPara">保存</ui:button>&nbsp;&nbsp;
											<ui:button id="cancelNeCommandPara">取消</ui:button>
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
