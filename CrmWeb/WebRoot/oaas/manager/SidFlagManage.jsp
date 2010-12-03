<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui" %>
<html>
<head>
<META HTTP-EQUIV="library" CONTENT="kernel,calendar">
<title>SID�鵵���ع���</title>
<script language="JavaScript" src="../../public/components/common2.js" charset="gb2312"></script>
<ui:import library=""></ui:import>
<script>

var callBack=function(reply) {
	var backMessage= reply.getResult();
    if(backMessage==null){
    	alert("����SID�鵵���س���");
    	return false;
    } 	  
    else{
      alert("���óɹ���");
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
		<ui:field field="sidFlag"  label="�Ƿ�Զ��SID�鵵" dropDown="sidFlagDropdown"></ui:field>
		<ui:field field="localSidFlag"  label="�Ƿ񱾵�SID�鵵" dropDown="sidFlagDropdown"></ui:field>
		<ui:field field="maintanceOperId"  label="����ά���ر𹤺�" required="true"></ui:field>
	</ui:dataset>

</div>

<div id="dropdownDefine">
    <xml id="__sidFlagDropdown">
	<items>
	<item label="��" value="0"></item>
	<item label="��" value="1"></item>
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
			  	<ui:button id="btn_confirm">ȷ  ��</ui:button>
			  </div>
		</ui:pane>
	</ui:layout>
		</ui:pane>
	</ui:layout>
</div>
</body>