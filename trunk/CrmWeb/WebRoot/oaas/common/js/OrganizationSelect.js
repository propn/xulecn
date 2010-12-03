/**
 * 组织架构为  集团公司0 - > 省公司1 -> 市公司2 -> 营业区4 -> 分公司3 -> 部门5 -> 班组6(注意4表示营业区,3表示分公司,但是营业区的级别高于分公司)
 * 传递给OrganizationSelect.jsp页面的参数对象:
 * 	para["staffCode"] : 工号;
 * para["privilegeType"] : 权限条件类型：0-权限ID，1-权限编码，2-菜单ID，3-菜单编码；
 * para["privilegeCode"] :权限条件编码:根据编码类型传对应的值，例如当编码类型为1时，传权限编码,当编码类型为3时,传菜单编码,尽量使用编码.
 * para["orgType"] : 组织类型Id,0表示集团公司,1表示省公司,2表示市公司,3表示分公司,4表示营业区,5表示部门,6表示班组,7表示可以选择班组或者部门(用于选择员工所属组织),9表示其他组织.
 * para["orgIds"] : OrganizationSelect.jsp页面打开是默认钩选上的组织ID
 * para["selectType"] : 单选/多选标识; 1表示当选,2表示多选
 * para["checkChildren"] :1表示当钩选当前组织的时候自动钩选下级节点,2表示不自动钩选
 * para["uncheckedParent"] : 1表示钩选下级节点时自动取消钩选上级节点.2表示不作处理 
 * para["downloadWhenChecked"] : 1表示当钩选记录的时候下载下级节点,2表示钩选记录的时候不下载下级节点(点击记录的时候才下载下级节点)
 * para["selectParent"] :1表示不能选择上级组织,2表示可以选择上级组织,默认不可以选择上级组织
 * para["selectDistinctOrgType"] : 1表示只能选择相同级别的组织,2表示可以选择不同级别的组织
 */

var staffCode = "";
var privType = "";
var privCode = "";
var orgType = "" ;
var oldIds = "" ;
var selectType = "" ;
var checkChildren = "";
var ifPrivLimit = true;//是否进行权限控制
var uncheckedParent = "";//钩选下级节点的时候是否自动取消钩选上级节点,1.True ,2.false
var downloadWhenChecked = "";
var selectParent = "";//1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域
var selectDistinctOrgType = "1" ;//1表示只能选择相同级别的组织,2表示可以选择不同级别的组织

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
			selectDistinctOrgType = "1" ;//默认为只能选择相同类型的组织
		}
		if( selectParent == null || selectParent == "undefined" || selectParent == "" ){
			selectParent = "1" ;//默认不能选择上级组织,只能选择orgType参数指定的组织.
		}
		if( staffCode == null ){
			staffCode = "" ;//默认为空
		}
		if( privType == null ){
			privType = "" ;//默认为空
			ifPrivLimit = false ;
		}		
		if( privCode == null ){
			privCode = "" ;//默认为空
		}
		if( orgType == null ){
			orgType = "" ;//默认为空
		}
		if( oldIds == null ){
			oldIds = "" ;//默认为空
		}
		if( selectType == null ){
			selectType = "1" ;//默认为单选
		}
		if( checkChildren == null ){
			checkChildren = "2" ;//默认当钩选当前组织的时候不自动钩选下级节点
		}
		if( uncheckedParent == null ){
			uncheckedParent = "2" ;//默认不作处理
		}
		if( downloadWhenChecked == null ){
			downloadWhenChecked = "1" ;//默认下载子节点
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
			alert("您没有任何数据权限!");
			return ;
		}	
		//根据传递进来的组织ID参数,将ID对应的记录钩选上	
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
//点击组织记录的时候,根据被点击的组织ID到服务器端查询以该ID为上级ID的组织.
function clickOrganization(paramItem){
	var selItem = null ;
	if( paramItem != null ){
		selItem = paramItem ;
	}else{
		selItem = organizationTreeView.selectedItem ;
	}
	if( selItem.items ){//如果被点击的节点已经有下级节点,则无需再次查询下级组织,直接返回
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
		
		if( result != "<items/>" ){//如果存在下级节点
			selItem.insertByXML( result ) ;
			
			selItem.expand(true);//展开
			
			//如果新下载的节点和oldIds参数一致,则将该节点默认钩选上.
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
	if( parameter == null ){//如果没有传递参数,则按照没有权限控制的形式获取下级.
		ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
	}else{ 
		if( privType == "" ){//如果没有传递权限信息,也按照没有权限控制的形式获取下级
			ajaxCall.remoteCall("PartyService","getTelecomOrganizationListByParentId",[regionId], callBack);
		}else{
			if(orgType == "" ){
				orgType = "99" ;
			}
			if( selItem.privilegeFlag == "T" ){//当前节点是权限范围节点
				//查询组织区域当前节点下的所有节点,并为这些下级节点加上权限标志.
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListWithPrivFlag",[regionId],callBack); 
			}else {
				//查询组织区域,并过滤掉没有权限的区域 
				ajaxCall.remoteCall("RegionService","getOrganizationRegionListByFilter",[regionId,privType,privCode,orgType],callBack); 	
			}
		}
	}
}

//返回用户钩选的记录,如果传递的selectType为当选,则只能选择一个记录,否则可以多选; 
//无论当选还是多选,返回的都是包含VO的数组. 
function btn_Confirm_onClick(){
	var orgArray = new Array();
	var items = organizationTreeView.checkedItems;
	var paramObj = window.dialogArguments;
	if( paramObj != null ){
		var selectType = paramObj["selectType"] ;
		if( selectType != "" && selectType != null ){
			if( paramObj["selectType"] == "1" && items.length > 1 ){
				alert("只能选择一个组织!");
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
					alert("您在组织" + items[i].orgName + "上没有权限!");
					return ;
				}
			}
			if( orgType == "5" && items[i].orgTypeId == "6" ){
				alert("只能选择部门!") ;
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
 *currentSelectedOrgType变量用于保存用户钩选的第一个组织的组织类型,用于保证用户选择的多个
 *组织的组织类型是统一的,即在一次选择中,不能选择多个不同类型的组织.
 */
var currentSelectedOrgType = "";

/**
 * para["orgType"] : 组织类型Id,0表示集团公司,1表示省公司,2表示市公司,3表示分公司,5表示部门,6表示班组,9表示其他组织.
 * para["selectType"] : 单选/多选标识; 1表示当选,2表示多选
 * para["checkChildren"] :1表示当钩选当前组织的时候自动钩选下级节点,2表示不自动钩选
 * para["uncheckedParent"] : 1表示钩选下级节点时自动取消钩选上级节点.2表示不作处理 
 * para["downloadWhenChecked"] : 1表示当钩选记录的时候下载下级节点,2表示钩选记录的时候不下载下级节点(点击记录的时候才下载下级节点)
 * para["selectParent"] :1表示不能选择上级地域,2表示可以选择上级地域,默认不可以选择上级地域
 * para["selectDistinctOrgType"] : 1表示只能选择相同级别的组织,2表示可以选择不同级别的组织
 */
function organizationChecked(){
	if( organizationTreeView.selectedItem == null ) {
		return ;
	}
	
	if( downloadWhenChecked == "1" ) {
		clickOrganization();
	}
	//保存第一个被选中的组织类型
	if( currentSelectedOrgType == "" ){
		var checkedItem = organizationTreeView.checkedItems[0] ;
		if( checkedItem != null && typeof( checkedItem ) != "undefined" ){
			currentSelectedOrgType = checkedItem.orgTypeId ;
		}
	}
	
	var canCheckCurrentItem = true ;//判断当前节点是否能被钩选的变量
	var selItem = organizationTreeView.selectedItem ;
	
	if( !selItem.getChecked() ){
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "" ;
		}
		return ;
	}
	
	//selectType参数指定单选而实际选择了多个组织
	if( selectType == "1" && organizationTreeView.checkedItems.length > 1 ){//单选条件不符合
		alert("只能选择一个组织!") ;
		selItem.setChecked(false) ;
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "";
		}		
		return ;
	}
	//没有权限的组织
	/**
	if( selItem.privilegeFlag != "T" ){//权限条件不符合
		alert("你在当前组织没有选择权限!");
		selItem.setChecked(false) ;
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "";
		}		
		return ;
	}
	//组织类型不对且不能选择最低组织类型以上的上级组织.
	if( selItem.orgTypeId != orgType && selectParent == "1" ){//组织类型条件不符合
		if( orgType == "7" ){
			//orgType 为7标识可以选择部门和班组
			if( selItem.orgTypeId != "5" && selItem.orgTypeId != "6" ){
				alert("只能选择部门或者班组!") ;
				canCheckCurrentItem = false ;
			}
		}else if( orgType == "99" ){
		
		}else{
			canCheckCurrentItem = false ;
			showErrorMsg(orgType) ;
		}
	}**/
	if( selectParent == "2" ){//可以选择orgType参数指定的组织类型的所有上级组织
		//要求多选的多个组织的类型必须一致,而实际的选择不一致.
		var currentCheckedItems = organizationTreeView.checkedItems ;
		for( var n = 0 ; n < currentCheckedItems.length ; n ++ ){
			if( selectDistinctOrgType == "1" && currentSelectedOrgType != "" && currentSelectedOrgType != currentCheckedItems[n].orgTypeId ){//如果要求选择的组织类型都一致,判断是否符合条件
				canCheckCurrentItem = false ;
				alert("你选择的多个组织的组织类型必须一致!");
			}
		}
	}
	
	//确认是否钩选当前组织节点
	if( !canCheckCurrentItem ){
		selItem.setChecked(false) ;
		if( organizationTreeView.checkedItems.length == 0 ){
			currentSelectedOrgType = "";
		}
	}
	
	//确定是否要取消钩选上级组织
	if( uncheckedParent == "1" && canCheckCurrentItem ){//如果参数要求选择组织的时候自动将上级组织的钩选取消.
		var parentItem = selItem.getParentItem();
		while( parentItem ){
			parentItem.setChecked( false ) ;
			parentItem = parentItem.getParentItem();
		}		
	}
	
	//确定是否钩选下级组织
	if( checkChildren == "1" && selectType == "2" ){//必须是多选条件下才能自动钩选下级组织
		var subItems = selItem.items ;
		if( subItems ){//如果有下级节点才处理
			//如果当前节点的组织类型为orgType参数指定的类型
			if( selItem.orgTypeId == orgType ){
				//处理组织类型为部门和其他组织的记录,部门的下级可能存在部门,其他组织的下级肯定是其他组织
				if( selItem.orgTypeId == "5" || selItem.orgTypeId == "9" ){
					for( var i = 0 ; i < subItems.length ; i ++ ){
						if( subItems[i].orgTypeId == orgType && subItems[i].privilegeFlag == "T"){//如果下级组织的组织类型等于参数要求的组织类型,则自动钩选
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
				//如果钩选的不是传递参数所指定的组织类型.
				if( (( selItem.orgTypeId == "0" ) && ( orgType == "1" )) ||	//如果钩选的是集团公司而要选择的是省公司
					((selItem.orgTypeId == "1" ) && ( orgType == "2" )) ||		//如果钩选的是省公司而要选择的是市公司
					((selItem.orgTypeId == "2" ) && ( orgType == "4" )) ||		//如果钩选的是市公司而要选择的是营业区
					((selItem.orgTypeId == "4" ) && ( orgType == "3" )) ||		//如果钩选的是营业区而要选择的是分公司
					((selItem.orgTypeId == "3" ) && ( orgType == "5" )) ||		//如果钩选的是分公司而要选择的是部门
					((selItem.orgTypeId == "5" ) && ( orgType == "6" ))) {	//如果钩选的是部门而要选择的是班组
					//则钩选下级等于要选择的组织.
					for( var i = 0 ; i < subItems.length ; i ++ ){
						if( subItems[i].privilegeFlag != "T" ){//如果没有权限,则忽略掉
							continue ;
						}
						if( subItems[i].orgTypeId == orgType ){//如果下级组织的类型是orgType参数指定的组织,则自动钩选
							if( selectDistinctOrgType == "1" ){//如果要求选择的组织类型必须一致,则再加下面的判断
								if( subItems[i].orgTypeId == currentSelectedOrgType || currentSelectedOrgType == "" ){
									subItems[i].setChecked(true) ;
								}
							}
						}else{
							//如果下级组织的类型不是orgType参数指定的组织,但是selectParent参数指定可以选择选择orgType的上级组织
							if( selectParent == "2" ){
								if( selectDistinctOrgType == "1" ){//如果要求选择的组织类型必须一致,则再加下面的判断
									if( subItems[i].orgTypeId == currentSelectedOrgType || currentSelectedOrgType == "" ){
										subItems[i].setChecked( true ) ;
									}
								}else{//否则无需判断
									subItems[i].setChecked( true ) ;
								}
							}
						}
					}
				}else{
					//如果下级组织的类型不是orgType参数指定的组织,但是selectParent参数指定可以选择选择orgType的上级组织
					if( selectParent == "2" ){
						for( var i = 0 ; i < subItems.length ; i ++ ){
							if( subItems[i].privilegeFlag != "T" ){
								continue ;
							}
							if( selectDistinctOrgType == "1" ){//如果要求选择的组织类型必须一致,则再加下面的判断
								if( subItems[i].orgTypeId == currentSelectedOrgType ){
									subItems[i].setChecked( true ) ;
								}
							}else{//否则无需判断
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
		alert("只能选择中国电信集团!");
	}else if( orgType == "1" ){
		alert("只能选择本地网!");
	}else if( orgType == "2" ){
		alert("只能选择市公司!");
	}else if( orgType == "4" ){
		alert("只能选择营业区!");
	}else if( orgType == "3" ){
		alert("只能选择分公司!");
	}else if( orgType == "5" ){
		alert("只能选择部门!");
	}else if( orgType == "6" ){
		alert("只能选择班组!") ;
	}else if( orgType == "9" ){
		alert("只能选择其他组织!");
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
//响应记录上的check box的点击事件
/*
function organizationChecked_bak(){
	if( downloadWhenChecked == "1" ) {
		clickOrganization();
	}

	if( !ifPrivLimit ){
		return ;
	}
	var selItem = organizationTreeView.selectedItem ;//当前用户钩选的记录
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
			alert("只能选择一个记录!");
			selItem.setChecked( false ) ;
			return ;
		}
	}
	
	 //* 如果钩选的记录等于传递进来的参数所指定的组织类型,或者不要求指定组织类型(可以选择任何组织类型)
	if( selItem.orgTypeId == orgType || orgType == "99"){
		if( selItem.orgTypeId == "5" || selItem.orgTypeId == "9" || orgType == "99"){//如果市部门或者是其他组织,则将其下面的属于部门或者组织的子组织也钩选上
			var subItems = selItem.items ; 
			if( !subItems ){
				return ;
			}
			
			if( selectType != "1" && checkChildren == "1" ){//如果是多选且要求自动钩选下级节点
				 //*钩选下级组织,下级组织必须是和上级组织相等类型的,所有的组织类型中,只有部门和其他组织这
				 //*两种类型会有上级组织和下级组织类型一致的情况发生;另一种情况是不限制组织类型,可以选择
				 //*任何组织类型的情况也要钩选下级组织
				for( var i = 0 ; i < subItems.length ; i ++ ){ 
					if( subItems[i].orgTypeId == orgType || orgType == "99" ){
						subItems[i].setChecked(true) ;
					}
				}
			}
		}
	}else{
		if( orgType == "99" ){//如果组织类型为99,则不限制用户选择的组织类型,用户可以选择任意类型的组织.
			return ;
		}
		//如果钩选的不是传递参数所指定的组织类型.
		if( (( selItem.orgTypeId == "0" ) && ( orgType == "1" )) ||	//如果钩选的是集团公司而要选择的是省公司
			((selItem.orgTypeId == "1" ) && ( orgType == "2" )) ||		//如果钩选的是省公司而要选择的是市公司
			((selItem.orgTypeId == "2" ) && ( orgType == "3" )) ||		//如果钩选的是市公司而要选择的是分公司
			((selItem.orgTypeId == "3" ) && ( orgType == "5" )) ||		//如果钩选的是分公司而要选择的是部门
			((selItem.orgTypeId == "5" ) && ( orgType == "6" ))) {	//如果钩选的是部门而要选择的是班组
			var subItems = selItem.items ;
			if( !subItems ){
				return ;
			}
			
			if( selectType != "1" && checkChildren == "1"){//如果是多选且要求自动钩选下级节点
				//则钩选下级等于要选择的组织.
				for( var i = 0 ; i < subItems.length ; i ++ ){
					if( subItems[i].orgTypeId == orgType ){
						subItems[i].setChecked(true) ;
					}
				}
			}
			selItem.setChecked( false ) ;	//取消掉不合理的钩选记录
			if( selectType != "1" ){//如果是多选
				if( orgType == "0" ){
					alert("只能选择中国电信集团!");
				}else if( orgType == "1" ){
					alert("只能选择本地网!");
				}else if( orgType == "2" ){
					alert("只能选择市公司!");
				}else if( orgType == "3" ){
					alert("只能选择分公司!");
				}else if( orgType == "5" ){
					alert("只能选择部门!");
				}else if( orgType == "6" ){
					alert("只能选择班组!") ;
				}else if( orgType == "9" ){
					alert("只能选择其他组织!");
				}			
			}
		}else{
			if( orgType == "0" ){
				alert("只能选择中国电信集团!");
			}else if( orgType == "1" ){
				alert("只能选择本地网!");
			}else if( orgType == "2" ){
				alert("只能选择市公司!");
			}else if( orgType == "3" ){
				alert("只能选择分公司!");
			}else if( orgType == "5" ){
				alert("只能选择部门!");
			}else if( orgType == "6" ){
				alert("只能选择班组!") ;
			}else if( orgType == "9" ){
				alert("只能选择其他组织!");
			}
			selItem.setChecked( false ) ;
		}
	}
}*/