var menuCode = "";
var retCode="";
var retName="";
function page_onLoad(){
	//获取菜单编码,用于做权限控制(通过菜单编码获取和菜单对应的权限)
	menuCode = Global.getCurrentMenuCode();
	/*var paramObj = new CPromotionVO();
	CPromotionList.parameters().setValue("cond", paramObj) ;
	Dataset.reloadData( CPromotionList ) ;*/
	
	queryInfo.setFieldReadOnly("orgName",true);
	
	queryInfo.setValue("orgId",getStaffCompanyId());
	queryInfo.setValue("orgName",getStaffCompanyName());
}

function button_queryInfo_lanName_onClick()
{
selectLanId("query");
}

function button_CPromotionInfo_lanName_onClick(){
	selectLanId("");//
}
         /*获取区域名称按钮操作,只能取本地网*/
         function selectLanId(type){
            if( type != "query"){
		       if( actionType == "" ){//如果不是查询模式,且当前的actionType不是insert或者update,则返回
			   return ;
		       }
	          }
            
            var obj = new Object();
			/*
			 *privilegeType -- 权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码
			 */
			obj["privilegeType"] = "3" ;
			/*
			 *privilegeCode -- 
			 *权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
			 */
			 var menuCode = Global.getCurrentMenuCode();
			obj["privilegeCode"] = menuCode;
				
			obj["selectDistinctRegionLevel"] = 1 ;//1表示只能选择相同级别的地域,2表示可以选择不同级别的地域
			
			/*selectParent :1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域,在可
			 * 以选择上级地域的条件下,选择的多个区域必须是相同级别的,如传递的regionLevel参数为98F,且
			 * selectParent 为2,则可以选择局站和局站以上所有有权限的地域,但是多选的区域必须是相同级别额,比
			 * 如不能选择一个分公司和一个市公司.
			 */
			obj["selectParent"] = "1";
			obj["checkChildren"] = "2";//1表示钩选下级节点,2表示不钩选
			
			/*
			 * 区域级别,97A表示集团公司,97E表示省公司,97B表示本地网,97D表示营业区,98D
			 * 表示处理局,98E表示母局,98F表示局站
			 */
			obj["regionLevel"] = "97C" ;	
			var regionIds = queryInfo.getValue("lanId");
			obj["regionIds"] = regionIds;
			obj["selectType"] = "1" ;//单选多选标志,1 为单选,2 为多选
			var returnValue=window.showModalDialog("../../oaas/common/ResourceRegionSelect.jsp",obj,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
             
            var masterExchId = "";
            var masterExchName = "";
            if(returnValue!=null && returnValue.length!=0){ 
                
                if(returnValue.length==1)
                {
                  masterExchId =   returnValue[0]["regionId"];
	              masterExchName =   returnValue[0]["regionName"];
                }
                else
                {
	            for(var i=0; i<returnValue.length; i++){
	              masterExchId =   returnValue[i]["regionId"] + "," + masterExchId;
	              masterExchName =   returnValue[i]["regionName"] + "," + masterExchName;
				 }
				 }
				if(type=="query")
				{
				queryInfo.setValue("lanId", masterExchId);
				queryInfo.setValue("lanName", masterExchName);
				}
				else
				{
				CPromotionInfo.setValue("lanId", masterExchId);
				CPromotionInfo.setValue("lanName", masterExchName);
				}
				//queryInfo.setValue("regionLevel", returnValue[0]["regionLevel"]);
			}
			else{
			  return;
			}
         }

function doReset_onClick(){
	queryInfo.clearData() ;
}

function getStrLength(str){
		  return str.replace(/[^\x00-\xff]/g,"aa").length;
		  
     }

function doQuery_onClick(){
	var paramObj = new CPromotionVO();
	for( var ele in paramObj ){
		paramObj[ele] = queryInfo.getValue( ele ) ;
	}
	CPromotionList.parameters().setValue("cond", paramObj) ;
	Dataset.reloadData( CPromotionList ) ;
	//queryInfo.clearData() ;
}

var actionType = "" ;
//增加按钮的响应事件
function addCPromotion_onClick(){
	if( actionType != "" ){
		alert("请先保存当前操作再增加!");
		return ;
	}
	CPromotionInfo.clearData() ;
	CPromotionInfo.setReadOnly(false);
	CPromotionInfo.setFieldReadOnly("orgName",true);
	CPromotionInfo.setFieldReadOnly("lanName",true);
	//add by lpy 071009
	CPromotionInfo.setFieldReadOnly("promotionName",true);
	CPromotionInfo.setFieldReadOnly("promotionCode",true);
	actionType = "insert" ;
}
//确定按钮的响应事件
function btnSave_onClick(){
	if( actionType == "" ){
		return ;
	}
	
	if( !$("CPromotionInfoForm").validate()){
		return ;
	}
	if( CPromotionInfo.getValue("ifSysOper") == "F") {
		CPromotionInfo.setValue("partyRoleId","");
	}

	//如果是系统员工,则必须要求员工的姓名和ID是通过选择按钮在系统中选择的
	if( CPromotionInfo.getValue("ifSysOper") == "T" ) {
		if( CPromotionInfo.getValue("partyRoleId") == "" ){
			alert("您增加的发展人是系统员工,必须通过选择按钮在对话框中选择系统员工!");
			return ;
		}
		else if( (actionType=="insert") && (CPromotionInfo.getValue("promotionName") != retName) ){
			alert("您增加的发展人是系统员工,必须通过选择按钮在对话框中选择系统员工!");
			return ;
		}
		else if((actionType=="update") && (CPromotionInfo.getValue("promotionName") != CPromotionList.getValue("promotionName")) &&(CPromotionInfo.getValue("promotionName") !=retName)){
			alert("您修改的发展人是系统员工,必须通过选择按钮在对话框中选择系统员工!");
			return ;
		}
		else if((actionType=="update") && (CPromotionInfo.getValue("promotionName") == CPromotionList.getValue("promotionName")) && (CPromotionList.getValue("ifSysOper")=="F")&& (CPromotionInfo.getValue("promotionName") != retName)){
			alert("您修改的发展人是系统员工,必须通过选择按钮在对话框中选择系统员工!");
			return ;
		}
	}
	
	if( CPromotionInfo.getValue("ifSysOper") == "F") {
		CPromotionInfo.setValue("partyRoleId","");
	}
	//数据校验
	           var contactName=CPromotionInfo.getValue('contactName');
	           var contactPhone=CPromotionInfo.getValue('contactPhone');
	           var shortName=CPromotionInfo.getValue('shortName');
	           var comments=CPromotionInfo.getValue('comments');
	           var teamName=CPromotionInfo.getValue('teamName');
	           var postName=CPromotionInfo.getValue('postName');
	           
	           if(getStrLength(contactName)>=30)
	              {window.alert('联系人字符不能超过30个！');
	                   return;
	           }
	           
	            if(getStrLength(contactPhone)>=30)
	              {window.alert('联系电话字符不能超过30个！');
	                   return;
	           }
	           
	            if(getStrLength(shortName)>=30)
	              {window.alert('简称字符不能超过30个！');
	                   return;
	           }
	           
	            if(getStrLength(comments)>=250)
	              {window.alert('描述字符不能超过250个！');
	                   return;
	           }
	           
	            if(getStrLength(teamName)>=250)
	              {window.alert('班组字符不能超过250个！');
	                   return;
	           }
	           
	            if(getStrLength(postName)>=250)
	              {window.alert('岗位字符不能超过250个！');
	                   return;
	           }
	
	
	var saveObj = new CPromotionVO();
	for( var ele in saveObj ){
		saveObj[ele] = CPromotionInfo.getValue(ele) ;
	}
	
	var ajaxCall = new NDAjaxCall( true ) ;
	var callBack = function( reply ) {
		if( actionType == "insert" ){
			if( reply.getResult() == -1 ){
				alert("您增加的员工:" + CPromotionInfo.getValue("promotionName") + "已经是发展人,不能重复增加!");
				return ;
			}
			alert("增加发展人成功!");
		}else if( actionType == "update" ){
			alert("编辑发展人成功!");
		}
		actionType = "" ;
		CPromotionInfo.setReadOnly( true ) ;
		doQuery_onClick();
		/*var paramObj = new CPromotionVO();
		CPromotionList.parameters().setValue("cond",paramObj);
		Dataset.reloadData( CPromotionList );*/
	}
	
	if( actionType == "insert") {
		ajaxCall.remoteCall("PartyService","insertCPromotion", [ saveObj ] , callBack ) ;
	}else if( actionType == "update" ){
		ajaxCall.remoteCall("PartyService","updateCPromotion", [ saveObj ] , callBack ) ;
	}
}
function btnCancel_onClick(){
	if( actionType == "" ){
		return ;
	}
	CPromotionInfo.setReadOnly(true) ;
	CPromotionList_afterScroll();
	actionType = "" ;
}

function CPromotionList_afterScroll(dataset){
	actionType = "";
	CPromotionInfo.setReadOnly(true) ;
	for( var i = 0 ; i < CPromotionList.fields.length; i ++ ){
		CPromotionInfo.setValue( i, CPromotionList.getValue(i) ) ;
	}
}

function deleteCPromotion_onClick(){
	if( CPromotionList.getValue("promotionId") == "" ){
		return ;
	}
	
	if( !confirm( "确定要删除当前发展人吗?") ){
		return ;
	}
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		/*var paramObj = new CPromotionVO();
		CPromotionList.parameters().setValue("cond",paramObj);
		Dataset.reloadData( CPromotionList );*/
		if( reply.getResult() == 0 ){
			doQuery_onClick();
			alert("删除发展人成功!") ;
		}else if( reply.getResult() == -1 ){
			alert("发展人已经使用,不能删除!");
		}
	}
	
	ajaxCall.remoteCall("PartyService","deleteCPromotion",[CPromotionList.getValue("promotionId")], callBack ) ;
}

//编辑按钮的响应事件
function editCPromotion_onClick(){
	if( actionType != "" ){
		alert("请先保存当前操作再编辑!");
		return ;
	}
	
	CPromotionInfo.setReadOnly(false);
	CPromotionInfo.setFieldReadOnly("orgName",true);
	var proType=CPromotionInfo.getValue("promotionType") ;
      	//old_alert(proType) ;
      	if (proType == "10" ||proType == "20" ||proType == "30" ||proType == "40"){
			CPromotionInfo.setFieldReadOnly("promotionName",true);
		}
		//为营业员则促销人不可编辑
		else if (proType == "25"){
			CPromotionInfo.setFieldReadOnly("promotionName",true);
		}
		else{
			CPromotionInfo.setFieldReadOnly("promotionName",false);
		}
	//CPromotionInfo.setFieldReadOnly("promotionName",true);
	CPromotionInfo.setFieldReadOnly("promotionCode",true);
	actionType = "update" ;
}

//查询Form中的所属部门名称字段的弹出按钮响应事件
function button_queryInfo_orgName_onClick(){
	selectOrganization("query");//选择所在组织作为查询条件
}

//查询Form中的促销人名称的弹出按钮响应事件
function button_queryInfo_promotionName_onClick(){
	button_CPromotionInfo_promotionName_onClick("query") ;//选择促销人作为查询条件
}

function button_CPromotionInfo_orgName_onClick(){
	selectOrganization("");//选择所在组织(部门)
}

function selectOrganization(type){
	if( type != "query"){
		if( actionType == "" ){//如果不是查询模式,且当前的actionType不是insert或者update,则返回
			return ;
		}
	}
	var para = new Object();
	/*
	 *privilegeType -- 权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码
	 */
	para["privilegeType"] = "3" ;
	/*
	 *privilegeCode -- 
	 *权限条件编码:根据编码类型传对应的值，例如当编码类型为0时，传权限ID；
	 */
	para["privilegeCode"] = menuCode;
	/*
	 *orgType -- 
	 *组织类型ID,5表示部门,0表示集团公司,1表示省公司,2表示市公司,3表示分公司,6表示班组,
	 *9表示其他组织,99表示不作类型限制,可以选择任何一种组织类型
	 */
	para["orgType"] = "6" ;//所属组织类型
	para["selectType"] = "1" ;//1表示当选,2表示多选
	para["oldIds"] = getStaffCompanyId() ;

	para["checkChildren"] = "2";//1表示钩选下级节点,2表示不钩选
	para["uncheckedParent"] = "2";//1表示钩选下级节点时自动取消钩选上级节点.2表示不作处理 
	para["downloadWhenChecked"] = "1";//1表示当钩选记录的时候下载下级节点,2表示钩选记录的时候不下载下级节点
	para["selectParent"] = "1";//1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域
	para["selectDistinctOrgType"] = "1" ;//1表示只能选择相同级别的组织,2表示可以选择不同级别的组织
	

	var returnValue = window.showModalDialog("../../oaas/common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0) return ;
	if( type == "query" ){//给查询Dataset赋值
		queryInfo.setValue("orgId", returnValue[0]["orgId"]);
		queryInfo.setValue("orgName", returnValue[0]["orgName"]);		
	}else{//给channelManagerInfor Dataset赋值
		
		if(returnValue[0]["orgId"]!=CPromotionInfo.getValue("orgId")){
			CPromotionInfo.setValue("promotionName","");
			//modify by lpy 071102
			//CPromotionInfo.setValue("promotionCode","");
		}	
		CPromotionInfo.setValue("orgId", returnValue[0]["orgId"]);
		CPromotionInfo.setValue("orgName", returnValue[0]["orgName"]);		
	}
}

function button_CPromotionInfo_promotionName_onClick(type){
	if( type != "query" ){
		if( actionType != "insert" && actionType != "update" ){//只有在增加的时候可以选择客户经理,编辑的时候不能重新选择客户经理.
			return ;
		}
	}
	var orgId = "";
	if( type == "query" ){
		orgId = queryInfo.getValue("orgId");
	}else {
		orgId = CPromotionInfo.getValue("orgId") ;
	}
	if( orgId == "" ){
		alert("请先选择组织!") ;
		return ;
	}
	
	//add by lpy 071008 增加促销人类型判断
	var proType= CPromotionInfo.getValue("promotionType") ;
	//old_alert(proType) ;
	//old_alert(type) ;
	if (type !="query"){
		if (proType == ""){
			alert("请先选择发展人类型!") ;
			return ;
		}
		//如果为客户经理等的需调用另外一个界面则促销人不可编辑
		if (proType == "10" ||proType == "20" ||proType == "30" ||proType == "40"){
			//CPromotionInfo.setFieldReadOnly("promotionName",true);
			var returnValue=window.showModalDialog("../../oaas/common/PromotionStaff.jsp",[orgId,"1","1",proType],"dialogHeight: 458pt; dialogWidth: 544pt");
		}
		else{
			var returnValue=window.showModalDialog("../../oaas/common/StaffSelect.jsp",[orgId,"1","1"],"dialogHeight: 458pt; dialogWidth: 544pt");
		}
	}
	else{	
		var returnValue=window.showModalDialog("../../oaas/common/StaffSelect.jsp",[orgId,"1","1"],"dialogHeight: 458pt; dialogWidth: 544pt");
	}
	if( returnValue == null || returnValue.length ==0  ){
		//CPromotionInfo.setValue("partyRoleId","");
		//CPromotionInfo.setValue("promotionName","");
		return ;
	}
	if( type == "query" ){
		queryInfo.setValue("partyRoleId",returnValue[0].id);
		queryInfo.setValue("promotionName",returnValue[0].name);	
	}else{
		CPromotionInfo.setValue("partyRoleId",returnValue[0].id);
		CPromotionInfo.setValue("promotionName",returnValue[0].name);
		CPromotionInfo.setValue("ifSysOper","T");
		retCode=returnValue[0].id;
		retName=returnValue[0].name;
	}
}
function CPromotionVO(){
	this.promotionId = "";//促销人ID
	this.promotionCode = "";//促销人编码
	this.promotionName = "";//促销人姓名
	this.partyRoleId = "";//关联参与人标识
	this.promotionType = "";//促销人类型
	this.lanId ="";// 本地网ID
	this.lanName ="";// 本地网
	this.orgId = "";//所属部门ID
	this.orgName = "";//所属部门名称	
	this.operId = "";//录入人
	this.createDate = "";//录入日期
	this.ifSysOper = "";//是否系统工号
	this.state = "" ;//状态
	this.contactName = "";//联系人
	this.contactPhone = "";
	this.shortName = "";
	this.comments = "";
	this.teamName = "";//班组
	this.postName = "";//岗位
}


    var callBackPromotionDirec= function(reply){
	      var backMessage= reply.getResult();
	          window.alert('删除记录成功！');
	          Ds_PromotionDirec.deleteRecord();
	  } 
      
    function add_PromotionDirec_onClick(){
          var result = openDialog("/VsopWeb/ss/pm/PromotionDirecInfo.jsp",['add',window], "", "300px", "300px");
          if (result){
              qry_PromotionDirec(result);
             }
    }
      
    function update_PromotionDirec_onClick(){
	        var currentRecord=Ds_PromotionDirec.getCurrent();
	        if(currentRecord){
	           var result = openDialog("/VsopWeb/ss/pm/PromotionDirecInfo.jsp",['update',window], "", "300px", "300px");
	           if  (result)
	               qry_PromotionDirec(result);
	        }
	        else
	            window.alert('没有选中要修改的记录！');
     }
      
      function       qry_PromotionDirec(result){
	                 Ds_PromotionDirec.clearParameters();
			         var ps = Ds_PromotionDirec.parameters();
			         ps.setValue("promotionId",result);
			         Ds_PromotionDirec.staticDataSource="false";
			         Ds_PromotionDirec.loadDataAction="PromoServService"; 
			         Ds_PromotionDirec.loadDataActionMethod="qryPromotionDirec";
			         Ds_PromotionDirec.reloadData();
      }
     
     function delete_PromotionDirec_onClick(){
          var currentRecord=Ds_PromotionDirec.getCurrent();
              if (currentRecord){
                var bConfirmed = window.confirm('确认删除该记录?');
	            if (bConfirmed){
	            var ajaxCall = new NDAjaxCall(false); 
	            var paramObj=extractBeanFromDataset(Ds_PromotionDirec,"com.ztesoft.crm.ss.pm.vo.PromotionDirecVO");
		        ajaxCall.remoteCall("PromoServService", "removePromotionDirec", [paramObj],callBackPromotionDirec); 
	           }
            }
              else
                  window.alert("没有选中要删除的记录!");
      }
      
      function tableProm_onclick(){
               var current=CPromotionList.getCurrent();
               if (current)
                   qry_PromotionDirec(current.getValue('promotionId')); 
      }
      
      //add by lpy 071009
      function PROMOTION_TYPE_onSelect(cell, value, record){
      if (actionType !=""){
      	var proType=Dropdown.getDataset(PROMOTION_TYPE).getCurrent().getValue("value"); 
      	//CPromotionInfo.getValue("promotionType") ;
      	//old_alert(proType) ;
      	if (proType == "10" ||proType == "20" ||proType == "30" ||proType == "40"){
			CPromotionInfo.setFieldReadOnly("promotionName",true);
		}
		//为营业员则促销人不可编辑
		else if (proType == "25"){
			CPromotionInfo.setFieldReadOnly("promotionName",true);
		}
		else{
			CPromotionInfo.setFieldReadOnly("promotionName",false);
		}
		if (actionType =="update"){
			if (proType!=CPromotionList.getValue("promotionType")){
				CPromotionInfo.setValue("promotionName","");
			}
			else{
				CPromotionInfo.setValue("promotionName",CPromotionList.getValue("promotionName"));
			}
		}
		}
		
			CPromotionInfo.setValue("promotionName","");
		
		return true;
		
      }