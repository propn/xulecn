<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar">
		<title>静态数据重新装载</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="queryCom" staticDataSource="true">			    					  			  						
				<ui:field field="dcName" label="静态数据名称" visible="true" readOnly="false"></ui:field>									
			</ui:dataset>

			<ui:dataset datasetId="dsStaticDataList" staticDataSource="true" loadDataAction="StaticAttrService" loadDataActionMethod="listDataOfDcSql" readOnly="true" pageIndex="1" pageSize="20" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">	
				<ui:field field="select" label="" ></ui:field>
				<ui:field field="dc_name" label="静态数据编码" visible="true"></ui:field>	
				<ui:field field="dc_desc" label="静态数据描述" visible="true"></ui:field>						
			    <ui:field field="dc_sql" label="扩展信息" visible="true"></ui:field>		    
				
							              			
                <ui:parameter parameterId="dcName" type="string" value=""></ui:parameter>				              								                          								
			</ui:dataset>
											        
									
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">

				<ui:pane position="left" style="width:230px;">
					<ui:panel type="titleList" desc="查询条件">
						<ui:content>
							<ui:form dataset="queryCom" labelLayout="40%" inputLayout="60%"></ui:form>
							<div style="clear:both;text-align:center;">
								<ui:button id="query">查询</ui:button>
								<ui:button id="reset">重置</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>

				<ui:pane position="center">
					<ui:layout type="border">

						<ui:pane position="center">
							<ui:layout type="border">

								<ui:pane position="center" >
									<ui:panel type="titleList" desc="静态数据信息列表">
										<ui:content>
											<ui:table dataset="dsStaticDataList"></ui:table>
										</ui:content>
									</ui:panel>
								</ui:pane>
								<ui:pane position="bottom" >
								  <div class="customerpilot" dataset="dsStaticDataList">
									</div>	
								</ui:pane>					
							</ui:layout>
						</ui:pane>
                       <ui:pane position="bottom">										
						 <ui:button id="btn_Reload">重新加载</ui:button>																																		
					   </ui:pane>
					</ui:layout>										
				</ui:pane>
              </ui:layout>
				
		</div>
		 <script language="javascript" src="<%=request.getContextPath()%>/coop/common/script/AgentCommon.js"></script>				
		<script language="javascript" src="<%=request.getContextPath()%>/coop/common/script/GetMenuName.js"></script>
	</body>
</html>
<<script type="text/javascript">
<!--
  //
  function page_onLoad()
  {
       
  }
  //
  function query_onClick()
  {
       var rc = queryCom.getCurrent();
	   if (!rc || rc==null) return;
	   
	   var dcName = rc.getValue("dcName");
	   /*if(!dcName) {
	     alert("请输入静态数据名称!");
	     return;
	   }
	   */
	   
	   dsStaticDataList.parameters().setValue("dcName",dcName);	
	  	      
	   dsStaticDataList.reloadData();  
  }
  //
  function reset_onClick() {
    queryCom.clearData();   
  }
  
  //
  function btn_Reload_onClick()
  {
     var record = dsStaticDataList.getFirstRecord();
     var dcNames = [];
     var num = 0;
	 while(record){
	  if( System.isTrue(record.getValue("select")) ){
	  
	      var dcName = record.getValue("dc_name");
	      
          dcNames.push(dcName);	
          num = num + 1;		  			  
	   }		    
	  record = record.getNextRecord();			
    }
    
    if(num ==0) {
       alert("请选择要重新装载的静态数据!");
      return;
    }
    var tmpDcNames = dcNames.join(",");
      //保存
    NDAjaxCall.getAsyncInstance().remoteCall("StaticAttrService", "clearStaticData", [tmpDcNames], function(reply){
		   var result = reply.getResult();
		   if(!result) {
		      alert("操作失败!");
		      
		   }else {
		     alert("操作成功!");
		     query_onClick();
		   }
		    
		 });         
    
    
  }
   
//-->
</script>

