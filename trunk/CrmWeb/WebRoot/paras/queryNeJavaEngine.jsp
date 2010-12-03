<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
    function page_onLoad()
    {
       DS_Info.reloadData();
    } 
	function btn_query_onClick(){
		queryData() ;
	}
	function queryData(){
		var params = DS_Info.parameters() ;
		params.setValue('name',dsQuery.getValue('name'));
		DS_Info.reloadData();
	}
  function btn_confirm_onClick()
  {
    var  record=DS_Info.getCurrent();
    if (!record) return;
    var back = {};
    back['id'] = record.getValue("id");
     back['name'] = record.getValue("name");
	window.returnValue = back;		
    window.close();
  }

  function table_DS_Info_ondblclick()
  {
     btn_confirm_onClick();
  }

  function btn_cancle_onClick(){
    window.close();
  }

	function insertNeJavaEngine_onClick(){
		var back = openDialog("NeJavaEngine.jsp","","","400px","300px");
        if(back!=null){
			DS_Info.reloadData();
		}
	}
	function updateNeJavaEngine_onClick(){
		var  record=DS_Info.getCurrent();
	    if (!record) return;
	    var id = record.getValue("id");
		var back = openDialog("NeJavaEngine.jsp",id,"","400px","300px");
        if(back!=null){
			DS_Info.reloadData();
		}
	}
	function deleteNeJavaEngine_onClick(){
		var crt = DS_Info.getCurrent();
		if(!crt){
			return;
		}
		var para = DS_Info.parameters();
		var id =crt.getValue("id");
		if(!confirm("ȷ��ɾ����ǰ��¼��")) return;
		NDAjaxCall.getAsyncInstance().remoteCall("NeJavaEngineService", "deleteNeJavaEngineById",[id],function(r){
			var result = r.getResult() ;
			if(result == true ){
				alert('�����ɹ��� ') ;
				DS_Info.reloadData();
			}else{
				alert('����ʧ�� ,������������ϵ����Ա ��');
			}
			
		});
	}
</script>
	</head>
	<body>
		<div id="datasetDefine">	
		
			<ui:dataset datasetId="dsQuery">
				<ui:field field="name" label="����" visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="DS_Info" staticDataSource="true" 
				loadDataAction="NeJavaEngineService" loadDataActionMethod="searchNeJavaEngineData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
				<ui:field label="id" field="id"  visible="false" ></ui:field>
				<ui:field label="��̬�������" field="name"  visible="true"></ui:field>
				<ui:field label="��̬�������" field="rule_desc"  visible="true"></ui:field>
				<ui:field label="��̬�������" field="rule_content"  visible="true"></ui:field>
				<ui:field label="״̬" field="state"  visible="false"></ui:field>
				<ui:field label="class_name" field="class_name"  visible="false"></ui:field>
				<ui:field label="����ʱ��" field="ceate_date"  visible="false"></ui:field>
			    <ui:parameter parameterId="name" type="string" value=""></ui:parameter>
			</ui:dataset>
		</div>
				
	<div id="layoutDefine">
	<ui:panel type="modalDialog" desc="��̬���">
        <ui:content>
		 <ui:layout type="border">
				<ui:pane position="top" style="height:28px">
					<ui:layout type="border">
						<ui:pane position="center" >
							<ui:form dataset="dsQuery" labelLayout="20%" inputLayout="30%"></ui:form>
						</ui:pane>
						<ui:pane position="right" style="width:150px">
							<div style="text-align:right;">
								<ui:button id="btn_query">��ѯ</ui:button>
								<ui:button id="insertNeJavaEngine">����</ui:button>
								<ui:button id="updateNeJavaEngine">�޸�</ui:button>
								<ui:button id="deleteNeJavaEngine">ɾ��</ui:button>
							</div>
						</ui:pane>
					</ui:layout>
				</ui:pane>
               <ui:pane position="center">
			  	<ui:table dataset="DS_Info"></ui:table>
		      </ui:pane>
		      <ui:pane position="bottom">
			  <div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="DS_Info">
			  </div>
			       <ui:button id="btn_confirm">ȷ��</ui:button>
			       <ui:button id="btn_cancle">ȡ��</ui:button>
		     </ui:pane>
		</ui:layout>
		</ui:content>
	     </ui:panel>
	  </div>
	</body>
</html>
