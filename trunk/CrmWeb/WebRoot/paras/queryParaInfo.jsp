<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot">
		<title>选择源数据项</title>
		<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
		<script>
    var upStorageId = "";

    function page_onLoad()
    {

       DS_INFO.reloadData();
    } 

  function btn_confirm_onClick()
  {
    var  record=DS_INFO.getCurrent();
    if (!record) return;
    var back={};
    back['para_id']=record.getValue("para_id");
    back['paraname']=record.getValue("name");
    back['node_path']=record.getValue("node_path");
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
	<body submit="btn_confirm">
		<div id="datasetdefine">
			<ui:dataset datasetId="DS_INFO" staticDataSource="true" 
				loadDataAction="SpParaInfoService" loadDataActionMethod="searchSpParaInfoData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">

												
				<ui:field label="para_id中文描述" field="para_id"  visible="false" ></ui:field>

																
				<ui:field label="名称" field="name"  visible="true"></ui:field>
																
				<ui:field label="编码" field="para_code"  visible="true"></ui:field>
				
				<ui:field label="类型" field="para_type"  visible="true"></ui:field>
				
				<ui:field label="是否关键数据项" field="is_key"  visible="true" attrCode="para_info_is_key"></ui:field>
																
				<ui:field label="数据项节点" field="node_path"  visible="true"></ui:field>
																
				<ui:field label="节点属性" field="node_attr"  visible="true"></ui:field>
																
				
			 	<ui:parameter parameterId="para_id" type="string" value=""></ui:parameter>
			</ui:dataset>

	</div>
<div id="layoutDefine">
	<ui:panel type="modalDialog" desc="源数据项">
            <ui:content>
		 <ui:layout type="border">
               <ui:pane position="center">
			  	<ui:table dataset="DS_INFO"></ui:table>
		      </ui:pane>
		      <ui:pane position="bottom">
			  <div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset=DS_INFO>
			  </div>
			       <ui:button id="btn_confirm">确定</ui:button>
			       <ui:button id="btn_cancle">取消</ui:button>
		     </ui:pane>
		</ui:layout>
		</ui:content>
	     </ui:panel>
	  </div>
	</body>
</html>
