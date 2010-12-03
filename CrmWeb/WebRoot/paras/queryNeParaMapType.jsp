<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/NeParaMapType.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
		
			<ui:dataset datasetId="dsQuery">
				<ui:field field="name" label="名称" visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsNeParaMapTypeList" staticDataSource="true" 
				loadDataAction="NeParaMapTypeService" loadDataActionMethod="searchNeParaMapTypeData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
				<ui:field label="map_type_id" field="map_type_id"  visible="false" ></ui:field>
				<ui:field label="映射类型名称" field="name"  visible="true"></ui:field>
			    <ui:parameter parameterId="name" type="string" value=""></ui:parameter>
			</ui:dataset>
			<ui:dataset datasetId="dsNeValueMapListList" staticDataSource="true" 
				loadDataAction="NeValueMapListService" loadDataActionMethod="searchNeValueMapListData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
				<ui:field label="map_type_id" field="map_type_id"  visible="false"></ui:field>
				<ui:field label="源值" field="para_value"  visible="true"></ui:field>
				<ui:field label="映射值" field="map_value"  visible="true"></ui:field>
			    <ui:parameter parameterId="map_type_id" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
    <div id="layoutDefine">
    	<ui:layout type="border">

			<ui:pane position="center">
				<ui:layout type="border">
				
					<ui:pane position="top" style="height:300px">
						<ui:panel type="titleList" desc="映射类型列表">
							<ui:content>
								<ui:layout type="border">
									<ui:pane position="top" style="height:28px">
										<ui:layout type="border">
											<ui:pane position="center" >
												<ui:form dataset="dsQuery" labelLayout="20%" inputLayout="30%"></ui:form>
											</ui:pane>
											<ui:pane position="right" style="width:150px">
												<div style="text-align:right;">
													<ui:button id="btn_query">查询</ui:button>
													<ui:button id="insertNeParaMapType">新增</ui:button>
													<ui:button id="updateNeParaMapType">修改</ui:button>
													<ui:button id="deleteNeParaMapType">删除</ui:button>
												</div>
											</ui:pane>
										</ui:layout>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="dsNeParaMapTypeList" ></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<div class="customerpilot" extra="customerpilot" dataset="dsNeParaMapTypeList"></div>
										<ui:button id="btn_confirm">确认</ui:button>
										<ui:button id="btn_reset">取消</ui:button>
									</ui:pane>
								</ui:layout>
							
							</ui:content>
						</ui:panel>
					</ui:pane>
					
					<ui:pane position="center" style="height:300px;">
						<ui:panel type="titleList" desc="映射类型映射值列表">
							<ui:content>
								<ui:layout type="border">
									<ui:pane position="top" style="height:20px">
										<div  style="text-align:right;">
											<ui:button id="insertNeValueMapList">新增</ui:button>
											<ui:button id="updateNeValueMapList">修改</ui:button>
											<ui:button id="deleteNeValueMapList">删除</ui:button>
										</div>
									</ui:pane>
									<ui:pane position="center">
										<ui:table dataset="dsNeValueMapListList" ></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<div class="customerpilot" extra="customerpilot" dataset="dsNeValueMapListList"></div>
									</ui:pane>
								</ui:layout>
							</ui:content>
						</ui:panel>
					</ui:pane>
					
				</ui:layout>
			</ui:pane>

    	</ui:layout>
    </div>

	</body>
</html>
