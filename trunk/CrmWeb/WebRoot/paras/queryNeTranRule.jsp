<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" >
		function page_onLoad(){
			queryData() ;
		}
		function queryData(){
			DS_INFO.reloadData();
		}
	  function btn_confirm_onClick()
	  {
	    var  record=DS_INFO.getCurrent();
	    if (!record) return;
	    var back={};
	    back['tran_rule_id']=record.getValue("tran_rule_id");
	    back['rulename']=record.getValue("name");
	    window.returnValue = back;
	    window.close();
	  }
	
	  function table_DS_INFO_ondblclick()
	  {
	     btn_confirm_onClick();
	  }
	
	  function btn_cancle_onClick(){
	    window.close();
	  }

		</script>
	</head>
	<body>
		<div id="datasetDefine">				
			<ui:dataset datasetId="DS_INFO" staticDataSource="true" 
				loadDataAction="NeTranRuleService" loadDataActionMethod="searchNeTranRuleData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="tran_rule_id" field="tran_rule_id"  visible="false" ></ui:field>
												
				<ui:field label="静态组件" field="business_obj_id"  visible="true"></ui:field>
																
				<ui:field label="动态组件" field="id"  visible="true"></ui:field>
																
				<ui:field label="映射类型" field="map_type_id"  visible="true"></ui:field>
																
				<ui:field label="系统标识" field="int_sys_id"  visible="true"></ui:field>
																
				<ui:field label="名称" field="name"  visible="true"></ui:field>
																
				<ui:field label="转换类型" field="get_method"  visible="true"></ui:field>
																
				<ui:field label="动态sql" field="execute_sql"  visible="true"></ui:field>
																
				<ui:field label="生成日期" field="create_date"  visible="true"></ui:field>
			    <ui:parameter parameterId="name" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="create_date" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="end_date" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="center">
			  		<ui:table dataset="DS_INFO"></ui:table>
		        </ui:pane>
				<ui:pane position="bottom">
			  		<div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="DS_INFO">
			  		</div>
			       <ui:button id="btn_confirm">确定</ui:button>
			       <ui:button id="btn_cancle">取消</ui:button>
		     	</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
