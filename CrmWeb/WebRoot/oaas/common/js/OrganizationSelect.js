/**
 * ��֯�ܹ�Ϊ  ���Ź�˾0 - > ʡ��˾1 -> �й�˾2 -> Ӫҵ��4 -> �ֹ�˾3 -> ����5 -> ����6(ע��4��ʾӪҵ��,3��ʾ�ֹ�˾,����Ӫҵ���ļ�����ڷֹ�˾)
 * ���ݸ�OrganizationSelect.jspҳ��Ĳ�������:
 * 	para["staffCode"] : ����;
 * para["privilegeType"] : Ȩ���������ͣ�0-Ȩ��ID��1-Ȩ�ޱ��룬2-�˵�ID��3-�˵����룻
 * para["privilegeCode"] :Ȩ����������:���ݱ������ʹ���Ӧ��ֵ�����統��������Ϊ1ʱ����Ȩ�ޱ���,����������Ϊ3ʱ,���˵�����,����ʹ�ñ���.
 * para["orgType"] : ��֯����Id,0��ʾ���Ź�˾,1��ʾʡ��˾,2��ʾ�й�˾,3��ʾ�ֹ�˾,4��ʾӪҵ��,5��ʾ����,6��ʾ����,7��ʾ����ѡ�������߲���(����ѡ��Ա��������֯),9��ʾ������֯.
 * para["orgIds"] : OrganizationSelect.jspҳ�����Ĭ�Ϲ�ѡ�ϵ���֯ID
 * para["selectType"] : ��ѡ/��ѡ��ʶ; 1��ʾ��ѡ,2��ʾ��ѡ
 * para["checkChildren"] :1��ʾ����ѡ��ǰ��֯��ʱ���Զ���ѡ�¼��ڵ�,2��ʾ���Զ���ѡ
 * para["uncheckedParent"] : 1��ʾ��ѡ�¼��ڵ�ʱ�Զ�ȡ����ѡ�ϼ��ڵ�.2��ʾ�������� 
 * para["downloadWhenChecked"] : 1��ʾ����ѡ��¼��ʱ�������¼��ڵ�,2��ʾ��ѡ��¼��ʱ�������¼��ڵ�(�����¼��ʱ��������¼��ڵ�)
 * para["selectParent"] :1��ʾ����ѡ���ϼ���֯,2��ʾ����ѡ���ϼ���֯,Ĭ�ϲ�����ѡ���ϼ���֯
 * para["selectDistinctOrgType"] : 1��ʾֻ��ѡ����ͬ�������֯,2��ʾ����ѡ��ͬ�������֯
 */

var staffCode = "";
var privType = "";
var privCode = "";
var orgType = "" ;
var oldIds = "" ;
var selectType = "" ;
var checkChildren = "";
var ifPrivLimit = true;//�Ƿ����Ȩ�޿���
var uncheckedParent = "";//��ѡ�¼��ڵ��ʱ���Ƿ��Զ�ȡ����ѡ�ϼ��ڵ�,1.True ,2.false
var downloadWhenChecked = "";
var selectParent = "";//1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����
var selectDistinctOrgType = "1" ;//1��ʾֻ��ѡ����ͬ�������֯,2��ʾ����ѡ��ͬ�������֯

var pathCode = "";
function page_onLoad(){
	var paramObj = window.dialogArguments;
	if( paramObj != null ){
		staffCode = paramObj["staffCode"] ;
		privType = paramObj["privilegeType"] ;
		privCode = paramObj["privilegeCode"] ;
		orgType = paramObj["orgType"] ;
		oldIds = paramObj["oldIds"];
		selectType = paramObj["selectType"];
		checkChildren = paramObj["checkChildren"];
		uncheckedParent = paramObj["uncheckedParent"];
		downloadWhenChecked = paramObj["downloadWhenChecked"];
		selectParent = paramObj["selectParent"];
		selectDistinctOrgType = paramObj["selectDistinctOrgType"] ;
		
		if( selectDistinctOrgType == null || selectDistinctOrgType == "undefined" || selectDistinctOrgType == "" ){
			selectDistinctOrgType = "1" ;//Ĭ��Ϊֻ��ѡ����ͬ���͵���֯
		}
		if( selectParent == null || selectParent == "undefined" || selectParent == "" ){
			selectParent = "1" ;//Ĭ�ϲ���ѡ���ϼ���֯,ֻ��ѡ��orgType����ָ������֯.
		}
		if( staffCode == null ){
			staffCode = "" ;//Ĭ��Ϊ��
		}
		if( privType == null ){
			privType = "" ;//Ĭ��Ϊ��
			ifPrivLimit = false ;
		}		
		if( privCode == null ){
			privCode = "" ;//Ĭ��Ϊ��
		}
		if( orgType == null ){
			orgType = "" ;//Ĭ��Ϊ��
		}
		if( oldIds == null ){
			oldIds = "" ;//Ĭ��Ϊ��
		}
		if( selectType == null ){
			selectType = "1" ;//Ĭ��Ϊ��ѡ
		}
		if( checkChildren == null ){
			checkChildren = "2" ;//Ĭ�ϵ���ѡ��ǰ��֯��ʱ���Զ���ѡ�¼��ڵ�
		}
		if( uncheckedParent == null ){
			uncheckedParent = "2" ;//Ĭ�ϲ�������
		}
		if( downloadWhenChecked == null ){
			downloadWhenChecked = "1" ;//Ĭ�������ӽڵ�
		}
		if( oldIds != "" ){
			var arr = oldIds.split(",") ;
			pathCode = getPathCode( arr[0] ) ;
		}
	}
	initOrganization();
}

function getPathCode( orgId ) {
	var ajaxCall = new NDAjaxCall( false ) ;
	var returnValue = "";
	var callBack = function( reply ) {
		returnValue = reply.getResult() ;
	}
	ajaxCall.remoteCall("PartyService","getPathCode",[orgId,"2"],callBack);
	return returnValue ;
}

function initOrganization(){
 	var queryResult = null ; 

	var ajaxCall = new NDAjaxCall(true);
	var callBack = function( reply ){
		queryResult = reply.getResult() ;
		organizationTreeView.loadByXML( queryResult);
		if( !organizationTreeView.items || organizationTreeView.items.length == 0 ){
			alert("��û���κ�����Ȩ��!");
			return ;
		}	
		//���ݴ��ݽ�������֯ID����,��ID��Ӧ�ļ�¼��ѡ��	
		var paramObj = window.dialogArguments;
		if( paramObj != null ){
			if( oldIds != "" ){
				var oldIdList = oldIds.split(",");
			
				for( var i = 0 ; i < oldIdList.length ; i ++ ){
					for( var j = 0; j < organizationTreeView.items.length; j ++ ){
						if( organizationTreeView.items[j].partyId == oldIdList[i] ){
							organizationTreeView.items[j].setChecked("true") ;
						}
					}
				}
			}
		}
		if( pathCode != "" ){
			getCheckedItem( organizationTreeView.selectedItem ) ;
		}
	}
	
	if( !ifPrivLimit ){
		ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",["-1"],callBack);	
	}else{
		ajaxCall.remoteCall("RegionService","getOrganizationRegionListByFilter",["-1",privType,privCode,orgType],callBack); 
	}
}

function getCheckedItem( item ){
	clickOrganization( item ) ;
	var subItems = item.items; 
	if( subItems ){
		for( var i = 0 ; i < subItems.length ; i ++ ){
			var arr = pathCode.split(".");
			for( var j = 0 ; j < arr.length ; j ++ ){
				if( arr[j] == subItems[i].partyId ){
					getCheckedItem( subItems[i] );
				}
			}
		}
	}
}
//�����֯��¼��ʱ��,���ݱ��������֯ID���������˲�ѯ�Ը�IDΪ�ϼ�ID����֯.
function clickOrganization(paramItem){
	var selItem = null ;
	if( paramItem != null ){
		selItem = paramItem ;
	}else{
		selItem = organizationTreeView.selectedItem ;
	}
	if( selItem.items ){//���������Ľڵ��Ѿ����¼��ڵ�,�������ٴβ�ѯ�¼���֯,ֱ�ӷ���
		return ;
	}
	if( orgType != "5" && orgType != "9" ){
		if( selItem.orgTypeId == orgType ){
			return ;
		}
	}
	var ajaxCall = new NDAjaxCall( false ) ;
	var callBack = function( reply ){
		var result = reply.getResult() ;
		
		if( result != "<items/>" ){//��������¼��ڵ�
			selItem.insertByXML( result ) ;
			
			selItem.expand(true);//չ��
			
			//��������صĽڵ��oldIds����һ��,�򽫸ýڵ�Ĭ�Ϲ�ѡ��.
			if( oldIds != "" ){ 
				var oldIdList = oldIds.split(",");
				var childItems = selItem.items ;
				if( childItems ){
					for( var j = 0; j < childItems.length; j ++ ){
						for( var i = 0 ; i < oldIdList.length ; i ++ ){
							if( childItems[j].partyId == oldIdList[i] ){
									childItems[j].setChecked("true") ;
							}
						}
					}
				}
			}
		}
	}
	var regionId = selItem.partyId ;
	
	var parameter = window.dialogArguments;
	if( parameter == null ){//���û�д��ݲ���,����û��Ȩ�޿��Ƶ���ʽ��ȡ�¼�.
		ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
	}else{ 
		if( privType == "" ){//���û�д���Ȩ����Ϣ,Ҳ����û��Ȩ�޿��Ƶ���ʽ��ȡ�¼�
			ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
		}else{
			if(orgType == "" ){
				orgType = "99" ;
			}
			if( selItem.privilegeFlag == "T" ){//��ǰ�ڵ���Ȩ�޷�Χ�ڵ�
				//��ѯ��֯����ǰ�ڵ��µ����нڵ�,��Ϊ��Щ�¼��ڵ����Ȩ�ޱ�־.
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListWithPrivFlag",[regionId],callBack); 
			}else {
				//��ѯ��֯����,�����˵�û��Ȩ�޵����� 
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListByFilter",[regionId,privType,privCode,orgType],callBack); 	
			}
		}
	}
}

//�����û���ѡ�ļ�¼,������ݵ�selectTypeΪ��ѡ,��ֻ��ѡ��һ����¼,������Զ�ѡ; 
//���۵�ѡ���Ƕ�ѡ,���صĶ��ǰ���VO������. 
function btn_Confirm_onClick(){
	var orgArray = new Array();
	var items = organizationTreeView.checkedItems;
	var paramObj = window.dialogArguments;
	if( paramObj != null ){
		var selectType = paramObj["selectType"] ;
		if( selectType != "" && selectType != null ){
			if( paramObj["selectType"] == "1" && items.length > 1 ){
				alert("ֻ��ѡ��һ����֯!");
				return;
			}
		}
	}
	var vo = null ;
	for( var i = 0; i < items.length ; i ++ ){
		vo = new Object();	
		vo.orgId = items[i].partyId ;
		vo.orgCode = items[i].orgCode ;
		vo.orgTypeId = items[i].orgTypeId ;
		vo.orgName = items[i].orgName;
		/**if( paramObj != null ){
			if( paramObj["privilegeCode"] != "" && paramObj["privilegeCode"] != null 
			&& paramObj["privilegeType"] != "" && paramObj["privilegeType"] != null ){
				if( items[i].privilegeFlag !="T"){
					alert("������֯" + items[i].orgName + "��û��Ȩ��!");
					return ;
				}
			}
			if( orgType == "5" && items[i].orgTypeId == "6" ){
				alert("ֻ��ѡ����!") ;
				return ;
			}
		}
		*/
		orgArray[i] = vo ;
	}
	window.returnValue = orgArray;
	window.close();
}

function btn_Cancel_onClick(){
	window.returnValue = null ;
	window.close();
}

/*
 *currentSelectedOrgType�������ڱ����û���ѡ�ĵ�һ����֯����֯����,���ڱ�֤�û�ѡ��Ķ��
 *��֯����֯������ͳһ��,����һ��ѡ����,����ѡ������ͬ���͵���֯.
 */
var currentSelectedOrgType = "";

/**
 * para["orgType"] : ��֯����Id,0��ʾ���Ź�˾,1��ʾʡ��˾,2��ʾ�й�˾,3��ʾ�ֹ�˾,5��ʾ����,6��ʾ����,9��ʾ������֯.
 * para["selectType"] : ��ѡ/��ѡ��ʶ; 1��ʾ��ѡ,2��ʾ��ѡ
 * para["checkChildren"] :1��ʾ����ѡ��ǰ��֯��ʱ���Զ���ѡ�¼��ڵ�,2��ʾ���Զ���ѡ
 * para["uncheckedParent"] : 1��ʾ��ѡ�¼��ڵ�ʱ�Զ�ȡ����ѡ�ϼ��ڵ�.2��ʾ�������� 
 * para["downloadWhenChecked"] : 1��ʾ����ѡ��¼��ʱ�������¼��ڵ�,2��ʾ��ѡ��¼��ʱ�������¼��ڵ�(�����¼��ʱ��������¼��ڵ�)
 * para["selectParent"] :1��ʾ����ѡ���ϼ�����,2��ʾ����ѡ���ϼ�����,Ĭ�ϲ�����ѡ���ϼ�����
 * para["selectDistinctOrgType"] : 1��ʾֻ��ѡ����ͬ�������֯,2��ʾ����ѡ��ͬ�������֯
 */
function organizationChecked(){
	if( organizationTreeView.selectedItem == null ) {
		return ;
	}
	
	if( downloadWhenChecked == "1" ) {
		clickOrganization();
	}
	//�����һ����ѡ�е���֯����
	if( currentSelectedOrgType == "" ){
		var checkedItem = organizationTreeView.checkedItems[0] ;
		if( checkedItem != null && typeof( checkedItem ) != "undefined" ){
			currentSelectedOrgType = checkedItem.orgTypeId ;
		}
	}
	
	var canCheckCurrentItem = true ;//�жϵ�ǰ�ڵ��Ƿ��ܱ���ѡ�ı���
	var selItem = organizationTreeView.selectedItem ;
	
	if( !selItem.getChecked() ){
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "" ;
		}
		return ;
	}
	
	//selectType����ָ����ѡ��ʵ��ѡ���˶����֯
	if( selectType == "1" && organizationTreeView.checkedItems.length > 1 ){//��ѡ����������
		alert("ֻ��ѡ��һ����֯!") ;
		selItem.setChecked(false) ;
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "";
		}		
		return ;
	}
	//û��Ȩ�޵���֯
	/**
	if( selItem.privilegeFlag != "T" ){//Ȩ������������
		alert("���ڵ�ǰ��֯û��ѡ��Ȩ��!");
		selItem.setChecked(false) ;
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "";
		}		
		return ;
	}
	//��֯���Ͳ����Ҳ���ѡ�������֯�������ϵ��ϼ���֯.
	if( selItem.orgTypeId != orgType && selectParent == "1" ){//��֯��������������
		if( orgType == "7" ){
			//orgType Ϊ7��ʶ����ѡ���źͰ���
			if( selItem.orgTypeId != "5" && selItem.orgTypeId != "6" ){
				alert("ֻ��ѡ���Ż��߰���!") ;
				canCheckCurrentItem = false ;
			}
		}else if( orgType == "99" ){
		
		}else{
			canCheckCurrentItem = false ;
			showErrorMsg(orgType) ;
		}
	}**/
	if( selectParent == "2" ){//����ѡ��orgType����ָ������֯���͵������ϼ���֯
		//Ҫ���ѡ�Ķ����֯�����ͱ���һ��,��ʵ�ʵ�ѡ��һ��.
		var currentCheckedItems = organizationTreeView.checkedItems ;
		for( var n = 0 ; n < currentCheckedItems.length ; n ++ ){
			if( selectDistinctOrgType == "1" && currentSelectedOrgType != "" && currentSelectedOrgType != currentCheckedItems[n].orgTypeId ){//���Ҫ��ѡ�����֯���Ͷ�һ��,�ж��Ƿ��������
				canCheckCurrentItem = false ;
				alert("��ѡ��Ķ����֯����֯���ͱ���һ��!");
			}
		}
	}
	
	//ȷ���Ƿ�ѡ��ǰ��֯�ڵ�
	if( !canCheckCurrentItem ){
		selItem.setChecked(false) ;
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "";
		}
	}
	
	//ȷ���Ƿ�Ҫȡ����ѡ�ϼ���֯
	if( uncheckedParent == "1" && canCheckCurrentItem ){//�������Ҫ��ѡ����֯��ʱ���Զ����ϼ���֯�Ĺ�ѡȡ��.
		var parentItem = selItem.getParentItem();
		while( parentItem ){
			parentItem.setChecked( false ) ;
			parentItem = parentItem.getParentItem();
		}		
	}
	
	//ȷ���Ƿ�ѡ�¼���֯
	if( checkChildren == "1" && selectType == "2" ){//�����Ƕ�ѡ�����²����Զ���ѡ�¼���֯
		var subItems = selItem.items ;
		if( subItems ){//������¼��ڵ�Ŵ���
			//�����ǰ�ڵ����֯����ΪorgType����ָ��������
			if( selItem.orgTypeId == orgType ){
				//������֯����Ϊ���ź�������֯�ļ�¼,���ŵ��¼����ܴ��ڲ���,������֯���¼��϶���������֯
				if( selItem.orgTypeId == "5" || selItem.orgTypeId == "9" ){
					for( var i = 0 ; i < subItems.length ; i ++ ){
						if( subItems[i].orgTypeId == orgType && subItems[i].privilegeFlag == "T"){//����¼���֯����֯���͵��ڲ���Ҫ�����֯����,���Զ���ѡ
							if( selectDistinctOrgType == "1" ){
								if( currentSelectedOrgType == subItems[i].orgTypeId ){
									subItems[i].setChecked( true ) ;
								}
							}else{
								subItems[i].setChecked(true) ;
							}
						}
					}
				}
			}else {
				//�����ѡ�Ĳ��Ǵ��ݲ�����ָ������֯����.
				if( (( selItem.orgTypeId == "0" ) && ( orgType == "1" )) ||	//�����ѡ���Ǽ��Ź�˾��Ҫѡ�����ʡ��˾
					((selItem.orgTypeId == "1" ) && ( orgType == "2" )) ||		//�����ѡ����ʡ��˾��Ҫѡ������й�˾
					((selItem.orgTypeId == "2" ) && ( orgType == "4" )) ||		//�����ѡ�����й�˾��Ҫѡ�����Ӫҵ��
					((selItem.orgTypeId == "4" ) && ( orgType == "3" )) ||		//�����ѡ����Ӫҵ����Ҫѡ����Ƿֹ�˾
					((selItem.orgTypeId == "3" ) && ( orgType == "5" )) ||		//�����ѡ���Ƿֹ�˾��Ҫѡ����ǲ���
					((selItem.orgTypeId == "5" ) && ( orgType == "6" ))) {	//�����ѡ���ǲ��Ŷ�Ҫѡ����ǰ���
					//��ѡ�¼�����Ҫѡ�����֯.
					for( var i = 0 ; i < subItems.length ; i ++ ){
						if( subItems[i].privilegeFlag != "T" ){//���û��Ȩ��,����Ե�
							continue ;
						}
						if( subItems[i].orgTypeId == orgType ){//����¼���֯��������orgType����ָ������֯,���Զ���ѡ
							if( selectDistinctOrgType == "1" ){//���Ҫ��ѡ�����֯���ͱ���һ��,���ټ�������ж�
								if( subItems[i].orgTypeId == currentSelectedOrgType || currentSelectedOrgType == "" ){
									subItems[i].setChecked(true) ;
								}
							}
						}else{
							//����¼���֯�����Ͳ���orgType����ָ������֯,����selectParent����ָ������ѡ��ѡ��orgType���ϼ���֯
							if( selectParent == "2" ){
								if( selectDistinctOrgType == "1" ){//���Ҫ��ѡ�����֯���ͱ���һ��,���ټ�������ж�
									if( subItems[i].orgTypeId == currentSelectedOrgType || currentSelectedOrgType == "" ){
										subItems[i].setChecked( true ) ;
									}
								}else{//���������ж�
									subItems[i].setChecked( true ) ;
								}
							}
						}
					}
				}else{
					//����¼���֯�����Ͳ���orgType����ָ������֯,����selectParent����ָ������ѡ��ѡ��orgType���ϼ���֯
					if( selectParent == "2" ){
						for( var i = 0 ; i < subItems.length ; i ++ ){
							if( subItems[i].privilegeFlag != "T" ){
								continue ;
							}
							if( selectDistinctOrgType == "1" ){//���Ҫ��ѡ�����֯���ͱ���һ��,���ټ�������ж�
								if( subItems[i].orgTypeId == currentSelectedOrgType ){
									subItems[i].setChecked( true ) ;
								}
							}else{//���������ж�
								subItems[i].setChecked( true ) ;
							}
						}
					}
				}
			}
		}
	}
}

function showErrorMsg(orgType){
	if( orgType == "0" ){
		alert("ֻ��ѡ���й����ż���!");
	}else if( orgType == "1" ){
		alert("ֻ��ѡ�񱾵���!");
	}else if( orgType == "2" ){
		alert("ֻ��ѡ���й�˾!");
	}else if( orgType == "4" ){
		alert("ֻ��ѡ��Ӫҵ��!");
	}else if( orgType == "3" ){
		alert("ֻ��ѡ��ֹ�˾!");
	}else if( orgType == "5" ){
		alert("ֻ��ѡ����!");
	}else if( orgType == "6" ){
		alert("ֻ��ѡ�����!") ;
	}else if( orgType == "9" ){
		alert("ֻ��ѡ��������֯!");
	}
}

/*function ReturnVO(){
	this.orgId = "";
	this.orgName = "";
	this.orgCode = "";
}

function Parameter(){
	this.staffCode = "";
	this.privilegeCode = "";
	this.orgType = "";
	this.orgIds = "";
}
*/
//��Ӧ��¼�ϵ�check box�ĵ���¼�
/*
function organizationChecked_bak(){
	if( downloadWhenChecked == "1" ) {
		clickOrganization();
	}

	if( !ifPrivLimit ){
		return ;
	}
	var selItem = organizationTreeView.selectedItem ;//��ǰ�û���ѡ�ļ�¼
	if( !selItem ){
		return ;
	}
			
	if( uncheckedParent == "1" ){
		var parentItem = selItem.getParentItem();
		while( parentItem ){
			parentItem.setChecked( false ) ;
			parentItem = parentItem.getParentItem();
		}
	}
	
	if( selectType == "1" ){
		var checkedItems = organizationTreeView.checkedItems ;
		if( checkedItems.length > 1 ){
			alert("ֻ��ѡ��һ����¼!");
			selItem.setChecked( false ) ;
			return ;
		}
	}
	
	 //* �����ѡ�ļ�¼���ڴ��ݽ����Ĳ�����ָ������֯����,���߲�Ҫ��ָ����֯����(����ѡ���κ���֯����)
	if( selItem.orgTypeId == orgType || orgType == "99"){
		if( selItem.orgTypeId == "5" || selItem.orgTypeId == "9" || orgType == "99"){//����в��Ż�����������֯,������������ڲ��Ż�����֯������֯Ҳ��ѡ��
			var subItems = selItem.items ; 
			if( !subItems ){
				return ;
			}
			
			if( selectType != "1" && checkChildren == "1" ){//����Ƕ�ѡ��Ҫ���Զ���ѡ�¼��ڵ�
				 //*��ѡ�¼���֯,�¼���֯�����Ǻ��ϼ���֯������͵�,���е���֯������,ֻ�в��ź�������֯��
				 //*�������ͻ����ϼ���֯���¼���֯����һ�µ��������;��һ������ǲ�������֯����,����ѡ��
				 //*�κ���֯���͵����ҲҪ��ѡ�¼���֯
				for( var i = 0 ; i < subItems.length ; i ++ ){ 
					if( subItems[i].orgTypeId == orgType || orgType == "99" ){
						subItems[i].setChecked(true) ;
					}
				}
			}
		}
	}else{
		if( orgType == "99" ){//�����֯����Ϊ99,�������û�ѡ�����֯����,�û�����ѡ���������͵���֯.
			return ;
		}
		//�����ѡ�Ĳ��Ǵ��ݲ�����ָ������֯����.
		if( (( selItem.orgTypeId == "0" ) && ( orgType == "1" )) ||	//�����ѡ���Ǽ��Ź�˾��Ҫѡ�����ʡ��˾
			((selItem.orgTypeId == "1" ) && ( orgType == "2" )) ||		//�����ѡ����ʡ��˾��Ҫѡ������й�˾
			((selItem.orgTypeId == "2" ) && ( orgType == "3" )) ||		//�����ѡ�����й�˾��Ҫѡ����Ƿֹ�˾
			((selItem.orgTypeId == "3" ) && ( orgType == "5" )) ||		//�����ѡ���Ƿֹ�˾��Ҫѡ����ǲ���
			((selItem.orgTypeId == "5" ) && ( orgType == "6" ))) {	//�����ѡ���ǲ��Ŷ�Ҫѡ����ǰ���
			var subItems = selItem.items ;
			if( !subItems ){
				return ;
			}
			
			if( selectType != "1" && checkChildren == "1"){//����Ƕ�ѡ��Ҫ���Զ���ѡ�¼��ڵ�
				//��ѡ�¼�����Ҫѡ�����֯.
				for( var i = 0 ; i < subItems.length ; i ++ ){
					if( subItems[i].orgTypeId == orgType ){
						subItems[i].setChecked(true) ;
					}
				}
			}
			selItem.setChecked( false ) ;	//ȡ����������Ĺ�ѡ��¼
			if( selectType != "1" ){//����Ƕ�ѡ
				if( orgType == "0" ){
					alert("ֻ��ѡ���й����ż���!");
				}else if( orgType == "1" ){
					alert("ֻ��ѡ�񱾵���!");
				}else if( orgType == "2" ){
					alert("ֻ��ѡ���й�˾!");
				}else if( orgType == "3" ){
					alert("ֻ��ѡ��ֹ�˾!");
				}else if( orgType == "5" ){
					alert("ֻ��ѡ����!");
				}else if( orgType == "6" ){
					alert("ֻ��ѡ�����!") ;
				}else if( orgType == "9" ){
					alert("ֻ��ѡ��������֯!");
				}			
			}
		}else{
			if( orgType == "0" ){
				alert("ֻ��ѡ���й����ż���!");
			}else if( orgType == "1" ){
				alert("ֻ��ѡ�񱾵���!");
			}else if( orgType == "2" ){
				alert("ֻ��ѡ���й�˾!");
			}else if( orgType == "3" ){
				alert("ֻ��ѡ��ֹ�˾!");
			}else if( orgType == "5" ){
				alert("ֻ��ѡ����!");
			}else if( orgType == "6" ){
				alert("ֻ��ѡ�����!") ;
			}else if( orgType == "9" ){
				alert("ֻ��ѡ��������֯!");
			}
			selItem.setChecked( false ) ;
		}
	}
}*/