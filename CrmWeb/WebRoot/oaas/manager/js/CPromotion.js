var menuCode = "";
var retCode="";
var retName="";
function page_onLoad(){
	//��ȡ�˵�����,������Ȩ�޿���(ͨ���˵������ȡ�Ͳ˵���Ӧ��Ȩ��)
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
         /*��ȡ�������ư�ť����,ֻ��ȡ������*/
         function selectLanId(type){
            if( type != "query"){
		       if( actionType == "" ){//������ǲ�ѯģʽ,�ҵ�ǰ��actionType����insert����update,�򷵻�
			   return ;
		       }
	          }
            
            var obj = new Object();
			/*
			 *privilegeType -- Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵�����
			 */
			obj["privilegeType"] = "3" ;
			/*
			 *privilegeCode -- 
			 *Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
			 */
			 var menuCode = Global.getCurrentMenuCode();
			obj["privilegeCode"] = menuCode;
				
			obj["selectDistinctRegionLevel"] = 1 ;//1��ʾֻ��ѡ����ͬ����ĵ���,2��ʾ����ѡ��ͬ����ĵ���
			
			/*selectParent :1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����,�ڿ�
			 * ��ѡ���ϼ������������,ѡ��Ķ�������������ͬ�����,�紫�ݵ�regionLevel����Ϊ98F,��
			 * selectParent Ϊ2,�����ѡ���վ�;�վ����������Ȩ�޵ĵ���,���Ƕ�ѡ�������������ͬ�����,��
			 * �粻��ѡ��һ���ֹ�˾��һ���й�˾.
			 */
			obj["selectParent"] = "1";
			obj["checkChildren"] = "2";//1��ʾ��ѡ�¼��ڵ�,2��ʾ����ѡ
			
			/*
			 * ���򼶱�,97A��ʾ���Ź�˾,97E��ʾʡ��˾,97B��ʾ������,97D��ʾӪҵ��,98D
			 * ��ʾ�����,98E��ʾĸ��,98F��ʾ��վ
			 */
			obj["regionLevel"] = "97C" ;	
			var regionIds = queryInfo.getValue("lanId");
			obj["regionIds"] = regionIds;
			obj["selectType"] = "1" ;//��ѡ��ѡ��־,1 Ϊ��ѡ,2 Ϊ��ѡ
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
//���Ӱ�ť����Ӧ�¼�
function addCPromotion_onClick(){
	if( actionType != "" ){
		alert("���ȱ��浱ǰ����������!");
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
//ȷ����ť����Ӧ�¼�
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

	//�����ϵͳԱ��,�����Ҫ��Ա����������ID��ͨ��ѡ��ť��ϵͳ��ѡ���
	if( CPromotionInfo.getValue("ifSysOper") == "T" ) {
		if( CPromotionInfo.getValue("partyRoleId") == "" ){
			alert("�����ӵķ�չ����ϵͳԱ��,����ͨ��ѡ��ť�ڶԻ�����ѡ��ϵͳԱ��!");
			return ;
		}
		else if( (actionType=="insert") && (CPromotionInfo.getValue("promotionName") != retName) ){
			alert("�����ӵķ�չ����ϵͳԱ��,����ͨ��ѡ��ť�ڶԻ�����ѡ��ϵͳԱ��!");
			return ;
		}
		else if((actionType=="update") && (CPromotionInfo.getValue("promotionName") != CPromotionList.getValue("promotionName")) &&(CPromotionInfo.getValue("promotionName") !=retName)){
			alert("���޸ĵķ�չ����ϵͳԱ��,����ͨ��ѡ��ť�ڶԻ�����ѡ��ϵͳԱ��!");
			return ;
		}
		else if((actionType=="update") && (CPromotionInfo.getValue("promotionName") == CPromotionList.getValue("promotionName")) && (CPromotionList.getValue("ifSysOper")=="F")&& (CPromotionInfo.getValue("promotionName") != retName)){
			alert("���޸ĵķ�չ����ϵͳԱ��,����ͨ��ѡ��ť�ڶԻ�����ѡ��ϵͳԱ��!");
			return ;
		}
	}
	
	if( CPromotionInfo.getValue("ifSysOper") == "F") {
		CPromotionInfo.setValue("partyRoleId","");
	}
	//����У��
	           var contactName=CPromotionInfo.getValue('contactName');
	           var contactPhone=CPromotionInfo.getValue('contactPhone');
	           var shortName=CPromotionInfo.getValue('shortName');
	           var comments=CPromotionInfo.getValue('comments');
	           var teamName=CPromotionInfo.getValue('teamName');
	           var postName=CPromotionInfo.getValue('postName');
	           
	           if(getStrLength(contactName)>=30)
	              {window.alert('��ϵ���ַ����ܳ���30����');
	                   return;
	           }
	           
	            if(getStrLength(contactPhone)>=30)
	              {window.alert('��ϵ�绰�ַ����ܳ���30����');
	                   return;
	           }
	           
	            if(getStrLength(shortName)>=30)
	              {window.alert('����ַ����ܳ���30����');
	                   return;
	           }
	           
	            if(getStrLength(comments)>=250)
	              {window.alert('�����ַ����ܳ���250����');
	                   return;
	           }
	           
	            if(getStrLength(teamName)>=250)
	              {window.alert('�����ַ����ܳ���250����');
	                   return;
	           }
	           
	            if(getStrLength(postName)>=250)
	              {window.alert('��λ�ַ����ܳ���250����');
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
				alert("�����ӵ�Ա��:" + CPromotionInfo.getValue("promotionName") + "�Ѿ��Ƿ�չ��,�����ظ�����!");
				return ;
			}
			alert("���ӷ�չ�˳ɹ�!");
		}else if( actionType == "update" ){
			alert("�༭��չ�˳ɹ�!");
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
	
	if( !confirm( "ȷ��Ҫɾ����ǰ��չ����?") ){
		return ;
	}
	var ajaxCall = new NDAjaxCall( true ) ;
	
	var callBack = function( reply ) {
		/*var paramObj = new CPromotionVO();
		CPromotionList.parameters().setValue("cond",paramObj);
		Dataset.reloadData( CPromotionList );*/
		if( reply.getResult() == 0 ){
			doQuery_onClick();
			alert("ɾ����չ�˳ɹ�!") ;
		}else if( reply.getResult() == -1 ){
			alert("��չ���Ѿ�ʹ��,����ɾ��!");
		}
	}
	
	ajaxCall.remoteCall("PartyService","deleteCPromotion",[CPromotionList.getValue("promotionId")], callBack ) ;
}

//�༭��ť����Ӧ�¼�
function editCPromotion_onClick(){
	if( actionType != "" ){
		alert("���ȱ��浱ǰ�����ٱ༭!");
		return ;
	}
	
	CPromotionInfo.setReadOnly(false);
	CPromotionInfo.setFieldReadOnly("orgName",true);
	var proType=CPromotionInfo.getValue("promotionType") ;
      	//old_alert(proType) ;
      	if (proType == "10" ||proType == "20" ||proType == "30" ||proType == "40"){
			CPromotionInfo.setFieldReadOnly("promotionName",true);
		}
		//ΪӪҵԱ������˲��ɱ༭
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

//��ѯForm�е��������������ֶεĵ�����ť��Ӧ�¼�
function button_queryInfo_orgName_onClick(){
	selectOrganization("query");//ѡ��������֯��Ϊ��ѯ����
}

//��ѯForm�еĴ��������Ƶĵ�����ť��Ӧ�¼�
function button_queryInfo_promotionName_onClick(){
	button_CPromotionInfo_promotionName_onClick("query") ;//ѡ���������Ϊ��ѯ����
}

function button_CPromotionInfo_orgName_onClick(){
	selectOrganization("");//ѡ��������֯(����)
}

function selectOrganization(type){
	if( type != "query"){
		if( actionType == "" ){//������ǲ�ѯģʽ,�ҵ�ǰ��actionType����insert����update,�򷵻�
			return ;
		}
	}
	var para = new Object();
	/*
	 *privilegeType -- Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵�����
	 */
	para["privilegeType"] = "3" ;
	/*
	 *privilegeCode -- 
	 *Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ0ʱ����Ȩ��ID��
	 */
	para["privilegeCode"] = menuCode;
	/*
	 *orgType -- 
	 *��֯����ID,5��ʾ����,0��ʾ���Ź�˾,1��ʾʡ��˾,2��ʾ�й�˾,3��ʾ�ֹ�˾,6��ʾ����,
	 *9��ʾ������֯,99��ʾ������������,����ѡ���κ�һ����֯����
	 */
	para["orgType"] = "6" ;//������֯����
	para["selectType"] = "1" ;//1��ʾ��ѡ,2��ʾ��ѡ
	para["oldIds"] = getStaffCompanyId() ;

	para["checkChildren"] = "2";//1��ʾ��ѡ�¼��ڵ�,2��ʾ����ѡ
	para["uncheckedParent"] = "2";//1��ʾ��ѡ�¼��ڵ�ʱ�Զ�ȡ����ѡ�ϼ��ڵ�.2��ʾ�������� 
	para["downloadWhenChecked"] = "1";//1��ʾ����ѡ��¼��ʱ�������¼��ڵ�,2��ʾ��ѡ��¼��ʱ�������¼��ڵ�
	para["selectParent"] = "1";//1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����
	para["selectDistinctOrgType"] = "1" ;//1��ʾֻ��ѡ����ͬ�������֯,2��ʾ����ѡ��ͬ�������֯
	

	var returnValue = window.showModalDialog("../../oaas/common/OrganizationSelect.jsp",para,"dialogHeight: 450pt; dialogWidth: 450pt; edge: Raised; help: no; resizable: no; status: no; scrolling:no");
	if( returnValue == null || returnValue.length == 0) return ;
	if( type == "query" ){//����ѯDataset��ֵ
		queryInfo.setValue("orgId", returnValue[0]["orgId"]);
		queryInfo.setValue("orgName", returnValue[0]["orgName"]);		
	}else{//��channelManagerInfor Dataset��ֵ
		
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
		if( actionType != "insert" && actionType != "update" ){//ֻ�������ӵ�ʱ�����ѡ��ͻ�����,�༭��ʱ��������ѡ��ͻ�����.
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
		alert("����ѡ����֯!") ;
		return ;
	}
	
	//add by lpy 071008 ���Ӵ����������ж�
	var proType= CPromotionInfo.getValue("promotionType") ;
	//old_alert(proType) ;
	//old_alert(type) ;
	if (type !="query"){
		if (proType == ""){
			alert("����ѡ��չ������!") ;
			return ;
		}
		//���Ϊ�ͻ�����ȵ����������һ������������˲��ɱ༭
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
	this.promotionId = "";//������ID
	this.promotionCode = "";//�����˱���
	this.promotionName = "";//����������
	this.partyRoleId = "";//���������˱�ʶ
	this.promotionType = "";//����������
	this.lanId ="";// ������ID
	this.lanName ="";// ������
	this.orgId = "";//��������ID
	this.orgName = "";//������������	
	this.operId = "";//¼����
	this.createDate = "";//¼������
	this.ifSysOper = "";//�Ƿ�ϵͳ����
	this.state = "" ;//״̬
	this.contactName = "";//��ϵ��
	this.contactPhone = "";
	this.shortName = "";
	this.comments = "";
	this.teamName = "";//����
	this.postName = "";//��λ
}


    var callBackPromotionDirec= function(reply){
	      var backMessage= reply.getResult();
	          window.alert('ɾ����¼�ɹ���');
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
	            window.alert('û��ѡ��Ҫ�޸ĵļ�¼��');
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
                var bConfirmed = window.confirm('ȷ��ɾ���ü�¼?');
	            if (bConfirmed){
	            var ajaxCall = new NDAjaxCall(false); 
	            var paramObj=extractBeanFromDataset(Ds_PromotionDirec,"com.ztesoft.crm.ss.pm.vo.PromotionDirecVO");
		        ajaxCall.remoteCall("PromoServService", "removePromotionDirec", [paramObj],callBackPromotionDirec); 
	           }
            }
              else
                  window.alert("û��ѡ��Ҫɾ���ļ�¼!");
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
		//ΪӪҵԱ������˲��ɱ༭
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