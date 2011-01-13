<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar" />
		<title>定价参数目录权限配置</title>
		<script type="text/javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script>
		  var privilegeId;
		  function page_onLoad(){
		       if(window.dialogArguments){
		           privilegeId = window.dialogArguments;
		       }
		  }
      
		  function btn_query_onClick(){
		     var productCode = queryCond.getValue("productCode");
		     var productName = queryCond.getValue("productName");

		     productList.parameters().setValue("privilegeId", privilegeId);
		     productList.parameters().setValue("productCode", productCode);
		     productList.parameters().setValue("productName", productName);
		     productList.reloadData();
		  }
		  
		  function btn_reset_onClick(){
		    queryCond.setValue("productName","");
		    queryCond.setValue("productCode","");
		  }
		  
		  function btn_add_onClick(){
		    var record = newProductList.getFirstRecord();
		    var i = 0;
		    var saveObjList = new Array();
			while( record ){
					var obj = new Object() ;
					obj.privId = window.dialogArguments[0] ;
					obj.dataPkey1 = record.getValue("productId");
					obj.dataPkey2 = "-1";
					obj.dataPkey3 = "-1";
					saveObjList[i] = obj ;
					i ++ ;
				record = record.getNextRecord() ;
			}
			if( saveObjList.length > 0 ){
				var ajaxCall = new NDAjaxCall( true ) ;
				var callBack = function( reply ) {
					alert("增加成功!") ;
					window.close();
				}
				ajaxCall.remoteCall("PrivilegeService","addAttachProductPrivileges",[saveObjList],callBack);
			}
		}
		  
		function btn_cancel_onClick(){
			window.close();
		}
		
		function btn_addOne_onClick(){
		 var record = productList.getCurrent();
		 if(!record)return;
		 if(record && !isSelected(record)){
		    newProductList.insertRecord();
			newProductList.copyRecord(record);
			productList.moveNext();
		 }
		 else{
		    alert("该记录已在新添的权限列表中!");
		 }
		} 
		
		function btn_addAll_onClick(){
		 var record = productList.getFirstRecord();
		 var count = productList.getCount();
		 var i = 0;
		 if(!record)return;
		 while(record){
		    if(isSelected(record)){
		      record = record.getNextRecord();
		      i++;
		      continue;
		     }
		    newProductList.insertRecord();
			newProductList.copyRecord(record);
			record = record.getNextRecord();
		 }
		 if(i==count){
		   alert("你已全部添加!");
		   return;
		 }
		} 
		
		function btn_removeOne_onClick(){
		 if(newProductList.getCurrent()){
		     newProductList.deleteRecord();
		 }
		} 
		
		function isSelected(rec){
		  	var record=newProductList.getFirstRecord();
		  	while(record){
		  		if(rec.getValue("productId")==record.getValue("productId"))
		  		return true;
		  		record=record.getNextRecord();
		  	}
		  	return false;
	   }
	   
	function btn_removeAll_onClick(){
		newProductList.clearData() ;
	}
		</script>
	</head>
	<body>

		<div id="datasetDefine">
			<ui:dataset datasetId="queryCond">
				<ui:field field="productName" label="产品名称"></ui:field>
				<ui:field field="productCode" label="产品编码"></ui:field>
			</ui:dataset>

			<ui:dataset datasetId="productList" loadDataAction="PrivilegeService" 
			loadDataActionMethod="queryAttachProductList"
			staticDataSource="true" pageIndex="1" pageSize="15" autoLoadPage="true" 
			async="true" loadAsNeeded="true" paginate="true">
				<ui:field field="productId" label="产品标识" visible="false"></ui:field>
				<ui:field field="productProviderId" label="产品提供者标识" visible="false"></ui:field>
				<ui:field field="productName" label="产品名称"></ui:field>
				<ui:field field="productCode" label="产品编码"></ui:field>
				<ui:field field="productComments" label="产品描述"></ui:field>
				<ui:parameter parameterId="privilegeId" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="productCode" type="string" value=""></ui:parameter>
				<ui:parameter parameterId="productName" type="string" value=""></ui:parameter>
			</ui:dataset>
			
			<ui:dataset datasetId="newProductList" staticDataSource="true">
				<ui:field field="productId" label="产品标识" visible="false"></ui:field>
				<ui:field field="productProviderId" label="产品提供者标识" visible="false"></ui:field>
				<ui:field field="productName" label="产品名称"></ui:field>
				<ui:field field="productCode" label="产品编码"></ui:field>
				<ui:field field="productComments" label="产品描述"></ui:field>
			</ui:dataset>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:80px">
					<ui:panel type="titleTable" desc="查询条件">
						<ui:content>
							<ui:form dataset="queryCond" submit="btn_query" inputLayout="23%" labelLayout="26%"></ui:form>
							<div style="text-align:center">
								<ui:button id="btn_query">查询</ui:button>
								<ui:button id="btn_reset">重置</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>

				<ui:pane position="center">
					<ui:layout type="border">
						<ui:pane position="left" style="width:300px">
							<ui:panel type="titleTable" desc="附属产品列表">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:table dataset="productList"></ui:table>
												</ui:pane>
												<ui:pane position="bottom">
													<div class="customerpilot" id="productListPilot" dataset="productList" type="short" ></div>
												</ui:pane>
											</ui:layout>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>

						<ui:pane position="center" style="width:50px">
						  <div align="center"><br/><br/><br/><br/><br/><br/><br/>
								<table>
									<tr><td><ui:button id="btn_addOne" style="display:block;">&nbsp&nbsp&gt&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="btn_addAll" style="display:block;">&nbsp&gt&gt&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="btn_removeOne" style="display:block;">&nbsp&lt&nbsp&nbsp</ui:button></td></tr>
									<tr><td><ui:button id="btn_removeAll" style="display:block;">&nbsp&lt&lt&nbsp</ui:button></td></tr>
								</table>
							</div>
						</ui:pane>

						<ui:pane position="right" style="width:300px">
							<ui:panel type="titleTable" desc="新添的附属产品列表">
								<ui:content>
									<ui:layout type="border">
										<ui:pane position="center">
											<ui:layout type="border">
												<ui:pane position="center">
													<ui:table dataset="newProductList"></ui:table>
												</ui:pane>
												<ui:pane position="bottom">
												</ui:pane>
											</ui:layout>
										</ui:pane>
									</ui:layout>
								</ui:content>
							</ui:panel>
						</ui:pane>
					  <ui:pane position="bottom" style="height:30px">
					        <ui:button id="btn_add">确定</ui:button>
							<ui:button id="btn_cancel">取消</ui:button>
					  </ui:pane>
					</ui:layout>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
