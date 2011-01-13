<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,customerpilot">
		<title>��;����ѯ</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
<script>
function btn_order_onClick(){
	var record = ds_order.getCurrent();
	if(!record){	
		alert("��ѡ��һ����¼");
		return;
	}	
	if(record.getValue("dealstate_crm")=="�Ѵ���"){
		alert("�ù����Ѿ���¼����ѡ����������");
		return;
	}	
	var orderId = record.getValue("order_97");
	var operId = record.getValue("worker_code");
	
	//alert("97���ӵ��ţ�"+orderId + ", " + "�����ţ�"+ operId);
	if(confirm("ȷ����¼97���ӵ��ţ�"+orderId + ", " + "�����ţ�"+ operId)){
	
			var callBack = function(reply){
		   		var result = reply.getResult();
		   		if(result){
		   			alert("������¼�ɹ���");
		   			ds_order.deleteRecord();
		   			//ds_order.flushData();
		   		}else{
		   			alert("������¼ʧ�ܣ�");
		   			return ;
		   		}	
		   	}
	   	
		NDAjaxCall.getSyncInstance().remoteCall("OnRoadOrderService", "update", [orderId,operId], callBack);
	}
	else{
		return;
	}
}

function btn_close_onClick(){
	window.close();
}

function search_onClick(){
	
	ds_order.parameters().setValue("order_97", ds_searchInfo.getValue("sorder_97"));
    ds_order.parameters().setValue("worker_code", ds_searchInfo.getValue("sworker_code"));
    ds_order.parameters().setValue("procju_name", ds_searchInfo.getValue("sprocju_name"));
    ds_order.parameters().setValue("dealstate_crm", ds_searchInfo.getValue("sdealstate_crm"));
    ds_order.parameters().setValue("isfinish_97", ds_searchInfo.getValue("sisfinish_97"));
    
    ds_order.reloadData();
}
  function btn_reset_onClick(){
   ds_searchInfo.setValue("sorder_97", "");
   ds_searchInfo.setValue("sworker_code", "");
   ds_searchInfo.setValue("sprocju_name", "");
   ds_searchInfo.setValue("sdealstate_crm", "");
   ds_searchInfo.setValue("sisfinish_97", "");
 }
</script>
<body>
<div id="datasetDefine">

	<xml id="__sdealstate_crm">
	<items>
	  	<item label="δ����" value="0" />
	  	<item label="�Ѵ���" value="1" />
	</items>
	</xml>
	<code id="sdealstate_crm"></code>
	
	<xml id="__sisfinish_97">
	<items>
		<item label="�ѿ���" value="1" />
		<item label="δ����" value="0" />
	  
	</items>
	</xml>
	<code id="sisfinish_97"></code>
	
	<ui:dataset datasetId="ds_order" loadDataAction="OnRoadOrderService" loadDataActionMethod="getAllOrder" pageIndex="1" pageSize="25" autoLoadPage="true" loadAsNeeded="true" paginate="true" paginate="true">				
		<ui:field field="order_97" label="97���ӵ���" readOnly="true"></ui:field>
		<ui:field field="worker_code" label="������" readOnly="true" ></ui:field>
		<ui:field field="procju_name" label="�����" visible="true" readOnly="true"></ui:field>
		<ui:field field="finishtime_97" label="97����ʱ��" readOnly="true"></ui:field>
		<ui:field field="dealstate_crm" label="crm����״̬" readOnly="true"></ui:field>
		<ui:field field="isfinish_97" label="97����״̬" readOnly="true"></ui:field>
	</ui:dataset>
		<!-- ��ѯ�������ݼ�-->
	<ui:dataset datasetId="ds_searchInfo">
		<ui:field field="sorder_97" label="97���ӵ���" ></ui:field>
		<ui:field field="sworker_code" label="������" ></ui:field>
		<ui:field field="sprocju_name" label="�����" ></ui:field>
		<ui:field field="sdealstate_crm" label="crm����״̬" dropDown="sdealstate_crm" blankValue="true"></ui:field>
		<ui:field field="sisfinish_97" label="97����״̬" dropDown="sisfinish_97" blankValue="true"></ui:field>		
	</ui:dataset>
<!--		<ui:parameter parameterId="orderState" type="string" value="3"></ui:parameter>-->

<!--		<ui:field field="procju_name" label="�����" attrCode="PUT_METHOD" visible="true" readOnly="true"></ui:field>-->
	
	
</div>
<div id="layoutDefine">
	<ui:layout type="border">
		<ui:pane position="center">
			<ui:panel type="titleList" desc="������¼">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="left" style="width:250px">
							<ui:panel type="titleList" desc="ȡ����ѯ����">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="top">
											<ui:form dataset="ds_searchInfo" submit="search"></ui:form>
											<div style="text-align: center;">
												<ui:button id="search">��ѯ</ui:button>
												<ui:button id="btn_reset">����</ui:button>
											</div>
										</ui:pane>
										<ui:pane position="center">
											<div style="text-align: center;">
											
												����δ�����Ĺ����ݲ�����
												��������Ų�ȡ������ʽ��¼��
												
											</div>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>
						<ui:pane position="center">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:table dataset="ds_order"></ui:table>
								</ui:pane>
								<ui:pane position="bottom">
										<div class="customerpilot" extra="customerpilot" id="pilotAttribute" dataset="ds_order">
									</div>
									<div>
										<ui:button id="btn_order">������¼</ui:button>
<!--										<ui:button id="btn_close">�˳�</ui:button>-->
									</div>
								</ui:pane>
							</ui:layout>
						</ui:pane>
					</ui:layout>
				</ui:content>
			</ui:panel>
		</ui:pane>
	</ui:layout>
</div>
</body>
</html>