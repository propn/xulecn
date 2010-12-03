<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<%--
	替换修改说明 ：
	1.dsQuery数据集，参数字段、名称
	2.dsProductCatgList 参数
	3.dsProductCatgList 字段显隐，字段类型
	4.dsProductCatgForm 字段显隐，字段类型、验证信息
	5. 替换完毕，删除此段文字！
 --%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/ProductCatg.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
		
			<ui:dataset datasetId="dsQuery">
				<%--need-replace 查询参数,需要根据业务实际需要设定！ --%>		
				<ui:field field="catalogName" label="产品名称" visible="true"></ui:field>
				<ui:field field="catalogCode" label="产品编码" visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsProductCatgList" staticDataSource="true" 
				loadDataAction="ProductCatgService" loadDataActionMethod="searchProductCatgData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
												
				<ui:field label="catalog_id" field="catalog_id"  visible="false" ></ui:field>
				<ui:field label="选择" field="catalog_select"  visible="true"></ui:field>
				<ui:field label="产品名称" field="catalog_name"  visible="true"></ui:field>
				<ui:field label="产品编码" field="catalog_code"  visible="true"></ui:field>
				<ui:field label="party_id" field="party_id"  visible="false"></ui:field>
				<ui:field label="party_role_id" field="party_role_id"  visible="false"></ui:field>
				<ui:field label="oper_region_id" field="oper_region_id"  visible="false"></ui:field>
											   
			    <%--need-replace 查询参数,需要根据业务实际需要设定！ --%>		
			    <ui:parameter parameterId="param1" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param2" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param3" type="string" value=""></ui:parameter>
			</ui:dataset>

		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:280px;">
					<ui:panel type="titleList" desc="查询结果">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="top" style="height:55px;">
									<ui:layout type="border">
									<ui:pane position="center" style="width:100%">
										<ui:form dataset="dsQuery" labelLayout="15%" inputLayout="15%"></ui:form>
									</ui:pane>
									<ui:pane position="right" style="width:50px;margin-top:25px;margin-right:5px;">
										<ui:button id="btn_query">查询</ui:button>
									</ui:pane>
									</ui:layout>
								</ui:pane>
								<ui:pane position="center" style="width:100%">
									<ui:table dataset="dsProductCatgList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom" style="width:100%">
									<div style="clear:both;text-align:center;">
										<ui:button id="selectPartyRole">选择</ui:button>&nbsp;
										<ui:button id="backMainPage">返回</ui:button>
									</div>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:panel>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>