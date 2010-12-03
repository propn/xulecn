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
				<ui:field label="目录名称" field="dir_name"  visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsSpParaInfoList" staticDataSource="true" 
				loadDataAction="SpParaInfoService" loadDataActionMethod="searchSpParaInfoData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="para_id中文描述" field="para_id"  visible="false" ></ui:field>

																
				<ui:field label="名称" field="name"  visible="true"></ui:field>
																
				<ui:field label="编码" field="para_code"  visible="true"></ui:field>
				
				<ui:field label="类型" field="para_type"  visible="true"></ui:field>
				
				<ui:field label="是否关键数据项" field="is_key"  visible="true" attrCode="para_info_is_key"></ui:field>
																
				<ui:field label="数据项节点" field="node_path"  visible="true"></ui:field>
																
				<ui:field label="节点属性" field="node_attr"  visible="true"></ui:field>
																
				
			 	<ui:parameter parameterId="para_dir_id" type="string" value=""></ui:parameter>
			</ui:dataset>

			<ui:dataset datasetId="dsSpParaInfoForm"  readOnly="true" 
				loadDataAction="SpParaInfoService" loadDataActionMethod="getSpParaInfoById" 
				readOnly="true" autoLoadPage="true" async="true" loadAsNeeded="true" >
												
				<ui:field label="para_id" field="para_id"  visible="false" required="false"  validMsg="请输入...!" ></ui:field>
				<ui:field label="para_dir_id" field="para_dir_id"  visible="false"></ui:field>
																
				<ui:field label="数据项名称" field="name"  visible="true" required="true" validMsg="请输入数据项名称"></ui:field>
																
				<ui:field label="数据项编码" field="para_code"  visible="true" required="true" validMsg="请输入数据项编码"></ui:field>
																
				<ui:field label="数据项节点" field="node_path"  visible="true" required="true" validMsg="请输入数据项节点"></ui:field>
																
				<ui:field label="节点属性" field="node_attr"  visible="true"></ui:field>						
																
				<ui:field label="数据项类型" field="para_type"  visible="true"></ui:field>
																
				<ui:field label="是否关键数据项" field="is_key"  visible="true" attrCode="para_info_is_key" blankValue="true" required="true"  validMsg="请选择是否关键属性项!"></ui:field>
												
				<ui:field label="备注" field="comments"  visible="true" textarea="true" textareaHeight="50px"></ui:field>			
				<ui:parameter parameterId="para_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
    <div id="layoutDefine">
    	<ui:layout type="border">
    	
    		<ui:pane position="left" style="width:200px;">
					<ui:panel type="titleList" desc="目录列表">
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
										<ui:bar type="search" desc="源数据项列表">
											<ui:content>
												<ui:button id="insertWoParaCatg">新增目录</ui:button>
												<ui:button id="updateWoParaCatg">修改目录</ui:button>
												<ui:button id="deleteWoParaCatg">删除目录</ui:button>
												<ui:button id="insertSpParaInfo">新增</ui:button>
												<ui:button id="updateSpParaInfo">修改</ui:button>
												<ui:button id="deleteSpParaInfo">删除</ui:button>
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
							<ui:tabpage desc="基本信息">
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
											<ui:button id="saveSpParaInfo">保存</ui:button>&nbsp;&nbsp;
											<ui:button id="cancelSpParaInfo">取消</ui:button>
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
