<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,customerpilot">
		<title>在途单查询</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
<script>
function btn_order_onClick(){
	var record = ds_order.getCurrent();
	if(!record){	
		alert("请选择一条记录");
		return;
	}	
	if(record.getValue("dealstate_crm")=="已处理"){
		alert("该工单已经补录！请选择其他工单");
		return;
	}	
	var orderId = record.getValue("order_97");
	var operId = record.getValue("worker_code");
	
	//alert("97电子单号："+orderId + ", " + "受理工号："+ operId);
	if(confirm("确定补录97电子单号："+orderId + ", " + "受理工号："+ operId)){
	
			var callBack = function(reply){
		   		var result = reply.getResult();
		   		if(result){
		   			alert("工单补录成功！");
		   			ds_order.deleteRecord();
		   			//ds_order.flushData();
		   		}else{
		   			alert("工单补录失败！");
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
	  	<item label="未处理" value="0" />
	  	<item label="已处理" value="1" />
	</items>
	</xml>
	<code id="sdealstate_crm"></code>
	
	<xml id="__sisfinish_97">
	<items>
		<item label="已竣工" value="1" />
		<item label="未竣工" value="0" />
	  
	</items>
	</xml>
	<code id="sisfinish_97"></code>
	
	<ui:dataset datasetId="ds_order" loadDataAction="OnRoadOrderService" loadDataActionMethod="getAllOrder" pageIndex="1" pageSize="25" autoLoadPage="true" loadAsNeeded="true" paginate="true" paginate="true">				
		<ui:field field="order_97" label="97电子单号" readOnly="true"></ui:field>
		<ui:field field="worker_code" label="受理工号" readOnly="true" ></ui:field>
		<ui:field field="procju_name" label="处理局" visible="true" readOnly="true"></ui:field>
		<ui:field field="finishtime_97" label="97竣工时间" readOnly="true"></ui:field>
		<ui:field field="dealstate_crm" label="crm处理状态" readOnly="true"></ui:field>
		<ui:field field="isfinish_97" label="97竣工状态" readOnly="true"></ui:field>
	</ui:dataset>
		<!-- 查询条件数据集-->
	<ui:dataset datasetId="ds_searchInfo">
		<ui:field field="sorder_97" label="97电子单号" ></ui:field>
		<ui:field field="sworker_code" label="受理工号" ></ui:field>
		<ui:field field="sprocju_name" label="处理局" ></ui:field>
		<ui:field field="sdealstate_crm" label="crm处理状态" dropDown="sdealstate_crm" blankValue="true"></ui:field>
		<ui:field field="sisfinish_97" label="97竣工状态" dropDown="sisfinish_97" blankValue="true"></ui:field>		
	</ui:dataset>
<!--		<ui:parameter parameterId="orderState" type="string" value="3"></ui:parameter>-->

<!--		<ui:field field="procju_name" label="处理局" attrCode="PUT_METHOD" visible="true" readOnly="true"></ui:field>-->
	
	
</div>
<div id="layoutDefine">
	<ui:layout type="border">
		<ui:pane position="center">
			<ui:panel type="titleList" desc="工单补录">
				<ui:content>
					<ui:layout type="border">
						<ui:pane position="left" style="width:250px">
							<ui:panel type="titleList" desc="取单查询条件">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="top">
											<ui:form dataset="ds_searchInfo" submit="search"></ui:form>
											<div style="text-align: center;">
												<ui:button id="search">查询</ui:button>
												<ui:button id="btn_reset">重置</ui:button>
											</div>
										</ui:pane>
										<ui:pane position="center">
											<div style="text-align: center;">
											
												九七未竣工的工单暂不受理，
												待竣工后才采取后受理方式补录。
												
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
										<ui:button id="btn_order">工单补录</ui:button>
<!--										<ui:button id="btn_close">退出</ui:button>-->
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