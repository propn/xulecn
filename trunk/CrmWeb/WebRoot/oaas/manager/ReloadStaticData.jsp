<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar">
		<title>��̬��������װ��</title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="queryCom" staticDataSource="true">			    					  			  						
				<ui:field field="dcName" label="��̬��������" visible="true" readOnly="false"></ui:field>									
			</ui:dataset>

			<ui:dataset datasetId="dsStaticDataList" staticDataSource="true" loadDataAction="StaticAttrService" loadDataActionMethod="listDataOfDcSql" readOnly="true" pageIndex="1" pageSize="20" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">	
				<ui:field field="select" label="" ></ui:field>
				<ui:field field="dc_name" label="��̬���ݱ���" visible="true"></ui:field>	
				<ui:field field="dc_desc" label="��̬��������" visible="true"></ui:field>						
			    <ui:field field="dc_sql" label="��չ��Ϣ" visible="true"></ui:field>		    
				
							              			
                <ui:parameter parameterId="dcName" type="string" value=""></ui:parameter>				              								                          								
			</ui:dataset>
											        
									
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">

				<ui:pane position="left" style="width:230px;">
					<ui:panel type="titleList" desc="��ѯ����">
						<ui:content>
							<ui:form dataset="queryCom" labelLayout="40%" inputLayout="60%"></ui:form>
							<div style="clear:both;text-align:center;">
								<ui:button id="query">��ѯ</ui:button>
								<ui:button id="reset">����</ui:button>
							</div>
						</ui:content>
					</ui:panel>
				</ui:pane>

				<ui:pane position="center">
					<ui:layout type="border">

						<ui:pane position="center">
							<ui:layout type="border">

								<ui:pane position="center" >
									<ui:panel type="titleList" desc="��̬������Ϣ�б�">
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
						 <ui:button id="btn_Reload">���¼���</ui:button>																																		
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
	     alert("�����뾲̬��������!");
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
       alert("��ѡ��Ҫ����װ�صľ�̬����!");
      return;
    }
    var tmpDcNames = dcNames.join(",");
      //����
    NDAjaxCall.getAsyncInstance().remoteCall("StaticAttrService", "clearStaticData", [tmpDcNames], function(reply){
		   var result = reply.getResult();
		   if(!result) {
		      alert("����ʧ��!");
		      
		   }else {
		     alert("�����ɹ�!");
		     query_onClick();
		   }
		    
		 });         
    
    
  }
   
//-->
</script>

