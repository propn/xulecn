<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,treeListImg">
		<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
		<link type='text/css' rel='stylesheet' href='../public/skins/bsn/main.css' charset='GBK'/>
		<script language="JavaScript" src="../public/components/mdiWin.js" charset="gb2312"></script>
		<script language="JavaScript" src="script/AlternateData.js" charset="gb2312"></script>
		<script language="JavaScript" src="../cs/cc/script/DualScreen.js" charset="gb2312"></script>

		
		<title>�й�������ֵҵ�񶩹�����ϵͳ</title>
		<style>
		  table td{padding:0;}
		  body{fileter:;};
		</style> 
		<style media="print" type="text/css">
		  #firstMenu{display:none;}
		  #secondLevelMenuDiv{display:none;}
		  #workspaceTitleTable{display:none;}
		  #copyright{display:none;}
		  #slider{display:none;}
		  body{background:white;filter:;}
		  #layoutDefine{background:white;filter:;}
		  #mywindowsLayer{background:white;filter:;}
		</style>
		<script>		
		Global.disableAsyncRemoteCallProcessingBar=true;
		Global.disableSystemContextMenu=true;
		Global.disableContextSelect=true;
		Global.disableContextRefresh=true;
		var console = null; //����˫��ȫ��ʵ����������CRM�ͻ���ȫ��	
		document.onpropertychange = function(){}
		document.oncontextmenu = function(){
		  event.returnValue=false;
		}		
		document.onselectstart = function(){
			event.returnValue=false;
		}
		function page_onUnload(){
		}
		function page_onLoad(){
		  var top = (window.document.body.clientHeight-80)/2;
		  if(top>100){
		    $("slider").style.display = "";
		    $("slider").style.top = top;
		  }
		  else{
		    $("slider").style.display = "none";
		  }		  
		  
		  initMenu();
		  //AddWin("../pbnews/PbNews.jsp", "��ҳ") 
		  AddWin("PbNews.jsp", "��ҳ");
		  initMarquee();
		  //add by huangf 12.19 ��½֮���ж��Ƿ���Ҫ���Ŵ����һ���Է��� -- shield by wzg 
		  //chargeFeeNotes();
		  
		}
		
		function getConsole(){ 
		  	var param_code = "DUAL_URL" ; 
			if(console == null){
			   try{
			  	var dsIP = null ;
				NDAjaxCall.getSyncInstance().remoteCall("CcQueryService", "getSwitchDualScreen", [param_code], function callback(reply){
					var dsIP = reply.getResult();
			    	if(dsIP!=null){
			    		console = new DualScreen({root:dsIP});
			      	}
			      	else{
			      		alert("ȡ˫������������ȷ�ϣ�");
			      	}
				}); 
				}
				catch(e){
				//alert(e.name + ": " + e.message); 
				}	
			} 
		 	return console;
       }
		function switchMenu(img){
		  var menu = $("secondLevelMenuDiv");
		  menu.style.display = (menu.style.display == "" ? "none" : "");
		  if(menu.style.display=="none"){
		  	img.src = "../public/skins/bsn/menu/menu_on2.gif";
		  	img.style.left = 0;
		  }
		  else{
		  	img.src = "../public/skins/bsn/menu/menu_off2.gif";
		  	img.style.left = 170;		  
		  }
		}
		
		function initMenu(){
		 	var queryResult = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				queryResult = reply.getResult() ;
				menuTreeView.loadByXML(queryResult);
				
				var items = menuTreeView.items ;
				for( var i = 0 ; i < items.length ; i ++ ){
					items[i].setImage("../public/skins/bsn/xtree/xp/flod1.gif");//���ڵ��ͼƬ
				}
			}
			ajaxCall.remoteCall("LoginService","getRootMenuTree",[],callBack);
		}
		
		function downloadSubMenu(){
			var selItem = menuTreeView.selectedItem ;
			if( selItem == null ){
				return ;
			}
			if( selItem.items ){
				return ;
			}
	
			if( selItem.openFlag == "0" ){//��ǰ�˵���Ҷ�Ӳ˵�
				selItem.setImage("../public/skins/bsn/xtree/xp/file.png");//Ҷ�ӽڵ��ͼƬ
				if(selItem.targetName==""){
				    AddWin("construct.htm?" + selItem.para,selItem.menuName,selItem.menuCode);
				}
				return ;
			}
			
			var ajaxCall = new NDAjaxCall( false ) ;
			
			var callBack = function( reply ){
				var result = reply.getResult() ;
				if( result != "<items/>" ){
					if( !selItem.items ) {
						selItem.insertByXML( result ) ;
					}
					selItem.expand(true);
					selItem.setImage("../public/skins/bsn/xtree/xp/flodopen.gif");//��Ҷ�ӽڵ�ͼƬ
				}else{
					selItem.setImage("../public/skins/bsn/xtree/xp/file.png");//Ҷ�ӽڵ��ͼƬ
					if(selItem.targetName==""){
					    AddWin("construct.htm?" + selItem.para,selItem.menuName,selItem.menuCode);
					}
				}
			}
			
			var menuId = selItem.menuId ;
			ajaxCall.remoteCall("LoginService","getSubMenus",[menuId ], callBack);		
		}
		//��ȡһ��ŵ�½֪ʶ��Ĺ��ź�����
		function getUserOf10000(type){
			var ajaxCall = new NDAjaxCall( false ) ;
			var userOf10000 = new Array;
			var callBack = function( reply ){
				userOf10000 = reply.getResult() ;
			}
			
			ajaxCall.remoteCall("PartyService","getUserOf10000",[type ], callBack);
			return userOf10000;
		}
		function dbclickMenu(){
			clickMenu() ;
		}
		function clickMenu(){
			var loginInfo=getStaffLoginInfo();
			
			var staffCode=loginInfo.staffCode;
			
			var staffCodeKey=staffCode+"232";
			downloadSubMenu();
			var selItem = menuTreeView.selectedItem;
			if( selItem == null ){
				return ;
			}
			var targetName = selItem.targetName ;
			var menuName = selItem.menuName ;
			var menuId = selItem.menuId ;
			var para = selItem.para ;
			var menuCode = selItem.menuCode ;
			//initMarquee();//��ʼ���������Ϣ
			
			if( targetName != "" && targetName != null ){
				//�����BSN��ҳ��,����ҪΪBSNҳ���ʼ��Ȩ����ص�����
				if( targetName.indexOf( "http://") == 0 ){
					setRoleInfo();
				}
								
				//CRMͨ���̶��������ӵķ�ʽ��Ӧ����ת��EDA����Ӧ��
				if( targetName.indexOf("EDAPageController") > 0 ){
					var width = document.body.clientWidth;
					window.open(targetName + "?" + para,"","toolbar=no,status=no,location=no,scrollbars=no,resizable=yes,width=650px,height=420px,top=180,left=150");
					return ;					
				}
				if( targetName.indexOf("guideMain.jsp") > 0 ){
					var width = document.body.clientWidth;
					window.open(targetName,"","fullscreen=yes");
					return ;					
				}
				AddWin(targetName+"?" + para,menuName,menuCode);
				if( targetName.indexOf("/business/accept/base/main.jsp") >-1){
					switchMenu($("slider"));
				}
			}
		}
	
		var userInfoBean = new Object();
		
		function initMarquee(){
			var ajaxCall = new NDAjaxCall(false);
			var callBack = function( reply ){
				var loginInfo = reply.getResult() ;
	//			spanBusiness.innerText = loginInfo["businessName"] ;//��ʾԱ�����ڵ�Ӫҵ��
				spanOrgName.innerText = loginInfo["operOrgName"];//��ʾԱ����������֯
				spanStaff.innerText = loginInfo["partyName"];//��ʾԱ������
				
				//��ʼ��Ա����һЩ������Ϣ
				userInfoBean.operOrgId = loginInfo["operOrgId"];
				userInfoBean.operOrgName = loginInfo["operOrgName"];
				
				userInfoBean.userId = loginInfo["partyId"];
				userInfoBean.userCode = loginInfo["staffCode"];
				userInfoBean.userName = loginInfo["partyName"];
				userInfoBean.ip = loginInfo["ip"];
				userInfoBean.macAddress = "";
				userInfoBean.agentFlag = "0";
				
				Global.userInfoBean = userInfoBean;
			}
			ajaxCall.remoteCall("LoginService","getLoginInfo",[],callBack);
		}
		
		//����BSNҳ����Ҫʹ�õ�Ȩ������
		function setRoleInfo(){
			var ajaxCall = new NDAjaxCall(false);
			var callBack = function( reply ){
			    var result = reply.getResult() ;
			    userInfoBean.roleStateBsnStrucArr = result;
				
				altData = new AlternateData();
	    		altData.setValue(userInfoBean);
			}
			ajaxCall.remoteCall("LoginService","getLogInRolesInfo",[],callBack);
		}		
		
		//���ѵ�½Ա��Ҫ��ȡһ���Է���
		function chargeFeeNotes(){
			chargeFeeNotesDs.clearData();
			chargeFeeNotesDs.staticDataSource="false";
			chargeFeeNotesDs.reloadData(
				function(){
					if (chargeFeeNotesDs.getCount()>0){
						var ls_windowinfo1 = "dialogWidth: 720px; dialogHeight: 500px; help: no; status: no";
						var url = "../ss/fm/ChargeFeeNotes.jsp";
						showModalDialog(url, [chargeFeeNotesDs], ls_windowinfo1);
					}
				}
			);
		}
		</script>
	</head>
	<body>
		<img id="slider" src="../public/skins/bsn/menu/menu_off2.gif" style="position:absolute;left:170px;display:none;" onClick="switchMenu(this);" />
		
		<div id="datasetDefine">
			<ui:dataset datasetId="chargeFeeNotesDs" loadDataAction="FeeManagetService" loadDataActionMethod="chargeFeeNotes" staticDataSource="true">
				<ui:field field="orderId" label="�������"></ui:field>
				<ui:field field="partyName" label="������"></ui:field>
				<ui:field field="partyRoleName" label="����Ա��"></ui:field>
				<ui:field field="notes" label="��ʾ����"></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" withSlider="false">
					<jsp:include page="firstLevelMenu.jsp"></jsp:include>
				</ui:pane>
				<ui:pane position="left" withSlider="false">
					<jsp:include page="secondLevelMenu.jsp"></jsp:include>
				</ui:pane>
				<ui:pane position="center">
					<jsp:include page="multiwin.jsp"></jsp:include>
				</ui:pane>
				<ui:pane position="bottom" withSlider="false">
					<div>
						<table width="100%" height="21" border="0" cellspacing="0" cellpadding="0">
  <tr class="copyright">
    <td align="center">�������Ƽ��������ι�˾���� 2002-2010</td>
    <td width="220px" background="../public/skins/bsn/menu/bottomright.gif">&nbsp;</td>
  </tr>
</table>

					</div>					
				</ui:pane>
			</ui:layout>
		</div>
	</body>

</html>
