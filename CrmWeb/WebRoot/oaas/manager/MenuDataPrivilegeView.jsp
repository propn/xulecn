<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		
	<script>
		var privType = "";
		var privId = "";
		var addDataPrivilegeDialogURL = "";
		
		//ҳ�����
		function page_onLoad(){
			privType = GetArgsFromHrefs(location.search,"privilegeType");
			privId = GetArgsFromHrefs(location.search,"privilegeId");
			addDataPrivilegeDialogURL = GetArgsFromHrefs(location.search,"addDataPrivilegeDialogURL");
			queryData();//��ѯ��ǰȨ�޶�Ӧ�Ĳ˵���Ϣ
		}
		
		//����Ȩ�޲˵�
		function btn_addMenuDataPrivilege_onClick(){
			var returnValue = window.showModalDialog(addDataPrivilegeDialogURL,[privId,privType],"dialogHeight: 408pt; dialogWidth: 344pt;");
			if( returnValue  == 0 ){
				queryData();
			}
		}
		
		//��ѯ��ǰȨ�޶�Ӧ�Ĳ˵���Ϣ
		function queryData(){
		 	var queryResult = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				queryResult = reply.getResult() ;
				menuTreeView.loadByXML(queryResult);
			}
			ajaxCall.remoteCall("PrivilegeService","getMenuDataPrivilege",[privId],callBack);		
		}
		

		//ɾ��Ȩ�޹����Ĳ˵�
		function btn_deleteMenuDataPrivilege_onClick(){
			var checkedItems = menuTreeView.checkedItems;
			var objList = new Array();
			for(var i=0;i<checkedItems.length;i++){
						var obj = new MmDataPrivVO() ;
						obj["privId"] = privId ;
						obj["dataPkey1"]= checkedItems[i].menuId;
						obj["dataPkey2"] = "-1" ; 
						obj["dataPkey3"] = "-1"; 
						obj["dataPkey4"] = ""; 
						obj["name"] = "" ; 
						obj["datasetName"] = "b"; 
						obj["fieldName"] = "" ; 
						obj["offerName"] = ""; 
						obj["serviceOfferName"] = "";
				
				objList[objList.length] = obj ;
			}
			if( objList.length == 0 ){
				alert("��ѡ����Ҫɾ���ļ�¼��");
				return ;
			}
			if( !confirm("��ȷ��Ҫɾ����ǰ��¼��?")){
				return ;
			}
			
			var ajaxCall = new NDAjaxCall( true ) ;
			var callBack = function( reply ) {
				alert("ɾ���ɹ�!");
				queryData();
			}
			ajaxCall.remoteCall("PrivilegeService","deleteDataPrivilege",[objList],callBack);
		}
		function MmDataPrivVO(){
				this.privId = "";
				this.dataPkey1 = "";
				this.dataPkey2 = "";
				this.dataPkey3 = "";
				this.dataPkey4 = "";
				this.name = "";
				this.datasetName = "";
				this.fieldName = "";
				this.offerName = "";
				this.serviceOfferName = "";
				this._BUFFALO_OBJECT_CLASS_='com.ztesoft.oaas.vo.MmDataPrivVO' ;	
			}
	</script>
	
	</head>
	<body>
		<ui:layout type="border">
			<ui:pane position="center">
				<ui:panel type="titleList" desc="Ȩ�޹�������">
					<ui:content>
						<ZTESOFT:treelist id="menuTreeView" class="treelist" showImage="true" showBorder="false" showCheck="true" contBorder="true" width="100%" height="100%" showHead="true">
							<ZTESOFT:columns>
								<ZTESOFT:column width="70%" display="true" displayText="�˵�����" propertyName="menuName" />
								<ZTESOFT:column width="30%" display="true" displayText="�˵�����" propertyName="menuCode" />							
								<ZTESOFT:column width="" display="false" displayText="�˵�ID" propertyName="menuId" />								
								<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="targetName" />								
								<ZTESOFT:column width="" display="false" displayText="����" propertyName="para" />
								<ZTESOFT:column width="" display="false" displayText="Ȩ������" propertyName="privilegeName" />
								<ZTESOFT:column width="" display="false" displayText="�˵��򿪱�־" propertyName="openFlag" />
								<ZTESOFT:column width="" display="false" displayText="Ȩ���жϱ�־" propertyName="privilegeFlag" />
								<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="menuType" />
								<ZTESOFT:column width="" display="false" displayText="ϵͳ��ʶ" propertyName="systemId" />
								<ZTESOFT:column width="" display="false" displayText="ͬ�����" propertyName="orderId" />
								<ZTESOFT:column width="" display="false" displayText="��Ч��־" propertyName="validFlag" />
								<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="menuGrade" />
								<ZTESOFT:column width="" display="false" displayText="�ϼ��˵���ʶ" propertyName="superId" />
								<ZTESOFT:column width="" display="false" displayText="�˵�ͼƬ·��" propertyName="imagePath" />
								<ZTESOFT:column width="" display="false" displayText="��ע" propertyName="comments" />
							</ZTESOFT:columns>
						</ZTESOFT:treelist>
					</ui:content>
				</ui:panel>
			</ui:pane>
			<ui:pane position="bottom">
				<ui:button id="btn_addMenuDataPrivilege">����</ui:button>
				<ui:button id="btn_deleteMenuDataPrivilege">ɾ��</ui:button>
			</ui:pane>
		</ui:layout>
	</body>
</html>
