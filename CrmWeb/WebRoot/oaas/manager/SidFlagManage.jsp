<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui" %>
<html>
<head>
<META HTTP-EQUIV="library" CONTENT="kernel,calendar">
<title>SID归档开关管理</title>
<script language="JavaScript" src="../../public/components/common2.js" charset="gb2312"></script>
<ui:import library=""></ui:import>
<script>

var callBack=function(reply) {
	var backMessage= reply.getResult();
    if(backMessage==null){
    	alert("设置SID归档开关出错！");
    	return false;
    } 	  
    else{
      alert("设置成功！");
      return true;
    }
}


function btn_confirm_onClick(){
   var mantinceOperId = sidFlag.getValue("maintanceOperId");
   var localSidFlag = sidFlag.getValue("localSidFlag");
   var remoterSidFlag = sidFlag.getValue("sidFlag");
   NDAjaxCall.getAsyncInstance().remoteCall("SidFlagManageService", "setSidFlag", [remoterSidFlag,localSidFlag,mantinceOperId], callBack); 
}

function page_onLoad(){

      NDAjaxCall.getAsyncInstance().remoteCall("SidFlagManageService", "getSidFlag", [], 
		  function back(reply){
		    var result=reply.getResult();
		    //alert(result);
			sidFlag.setValue("sidFlag",result[0]);
			sidFlag.setValue("localSidFlag",result[1]);
			sidFlag.setValue("maintanceOperId",result[2]);
		  }
  );
}
</script>
</head>
<body>
<div id="datasetDefine">

	<ui:dataset datasetId="sidFlag" staticDataSource="true" editable="true">
		<ui:field field="sidFlag"  label="是否远程SID归档" dropDown="sidFlagDropdown"></ui:field>
		<ui:field field="localSidFlag"  label="是否本地SID归档" dropDown="sidFlagDropdown"></ui:field>
		<ui:field field="maintanceOperId"  label="资料维护特别工号" required="true"></ui:field>
	</ui:dataset>

</div>

<div id="dropdownDefine">
    <xml id="__sidFlagDropdown">
	<items>
	<item label="否" value="0"></item>
	<item label="是" value="1"></item>
	</items>
	</xml>
	<code id="sidFlagDropdown" ></code>
</div>

<div id="contentDef">
	<ui:layout type="border">
		<ui:pane position="top" style="height:20px"></ui:pane>
		<ui:pane position="center">
	<ui:layout type="border">
		<ui:pane position="center" style="width:40%">
			  <ui:form dataset="sidFlag" >
			  </ui:form>
			  <div style="text-align:center;">
			  	<ui:button id="btn_confirm">确  定</ui:button>
			  </div>
		</ui:pane>
	</ui:layout>
		</ui:pane>
	</ui:layout>
</div>
</body>