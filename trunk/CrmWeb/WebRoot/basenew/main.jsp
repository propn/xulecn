<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Calendar" %>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<META HTTP-EQUIV="library" CONTENT="kernel,treeListImg">
		<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
		<link type='text/css' rel='stylesheet' href='../public/skins/bsn/main.css' charset='GBK'/>
		<link type='text/css' rel='stylesheet' href='css/topmenu.css' charset='GBK'/>
		<script language="JavaScript" src="../public/components/mdiWin.js" charset="gb2312"></script>
		<script language="JavaScript" src="script/AlternateData.js" charset="gb2312"></script>
		<script language="JavaScript" src="../cs/cc/script/DualScreen.js" charset="gb2312"></script>
		<script type="text/javascript">
		function page_onLoad(){
			//initMenu();
			getUserInfo();
			initTopMenu();
			AddWin("../base/PbNews.jsp", "首页");
		}
		function getUserInfo(){
			var ajaxCall = new NDAjaxCall(false);
			var callBack = function( reply ){
				var loginInfo = reply.getResult() ;
				userCode = loginInfo["staffCode"];
				userName = loginInfo["partyName"];
				$('login_code').innerText = userCode;
				$('login_name').innerText = userName;
				
			}
			ajaxCall.remoteCall("LoginService","getLoginInfo",[],callBack);
		}
		
		function initTopMenu(){
			var oXmlDom = new ActiveXObject("MSXML2.DOMDocument.5.0");
			var ajaxCall = new NDAjaxCall(true);
			
			ajaxCall.remoteCall("LoginService","getRootMenuTree",[],function(reply){
				var xml = reply.getResult();
				oXmlDom.loadXML(xml);
				var items = oXmlDom.selectNodes('/items/item');
				var topMenu = $('top_menu');
				for(var i=items.length-1;i>=0;i--){
					var item = items[i];
					var ali = document.createElement('li');
					
					ali.innerHTML = '<a href="#" onclick="loadSubMenu('+item.getAttribute('menuId')+') " > <span > <img src="'+item.getAttribute('imagePath')+ '"  class="topmenu" align="absmiddle" /> ' +item.getAttribute('menuName') + '</span></a>';
					
					//alert(ali.innerHTML);
					
					topMenu.appendChild(ali);
				}
			});
		}
		
		function loadSubMenu(superId){
			var queryResult = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				queryResult = reply.getResult() ;
				menuTreeView.loadByXML(queryResult);
				
				var items = menuTreeView.items ;
				for( var i = 0 ; i < items.length ; i ++ ){
					items[i].setImage("../public/skins/bsn/xtree/xp/flod1.gif");//根节点的图片
				}
			}
			ajaxCall.remoteCall("LoginService","getSubMenus",[''+superId],callBack);
		}
		
		function initMenu(){
		 	var queryResult = null ;
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				queryResult = reply.getResult() ;
				menuTreeView.loadByXML(queryResult);
				
				var items = menuTreeView.items ;
				for( var i = 0 ; i < items.length ; i ++ ){
					items[i].setImage("../public/skins/bsn/xtree/xp/flod1.gif");//根节点的图片
				}
			}
			ajaxCall.remoteCall("LoginService","getRootMenuTree",[],callBack);
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
			//initMarquee();//初始化走马灯信息
			
			if( targetName != "" && targetName != null ){
				//如果是BSN的页面,则需要为BSN页面初始化权限相关的数据
				if( targetName.indexOf( "http://") == 0 ){
					setRoleInfo();
				}
								
				//CRM通过固定访问链接的方式将应用跳转到EDA渠道应用
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
		function downloadSubMenu(){
			var selItem = menuTreeView.selectedItem ;
			if( selItem == null ){
				return ;
			}
			if( selItem.items ){
				return ;
			}
	
			if( selItem.openFlag == "0" ){//当前菜单是叶子菜单
				selItem.setImage("../public/skins/bsn/xtree/xp/file.png");//叶子节点的图片
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
					selItem.setImage("../public/skins/bsn/xtree/xp/flodopen.gif");//非叶子节点图片
				}else{
					selItem.setImage("../public/skins/bsn/xtree/xp/file.png");//叶子节点的图片
					if(selItem.targetName==""){
					    AddWin("construct.htm?" + selItem.para,selItem.menuName,selItem.menuCode);
					}
				}
			}
			
			var menuId = selItem.menuId ;
			ajaxCall.remoteCall("LoginService","getSubMenus",[menuId ], callBack);		
		}
		</script>
			<% String provinceCode = com.ztesoft.vsop.web.DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
		   String staticValue = com.ztesoft.common.util.CrmConstants.JX_PROV_CODE;
		   if(provinceCode.equals(staticValue)){
		%>
		
		<title>江西电信增值业务订购管理系统</title>
		 <%
		   }else{
		%>
		<title>中国电信增值业务订购管理系统</title>
		<%
		   }
		%>
		<style type="text/css">
<!-- top.jsp
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE2 {
	color: #03515d;
	font-size: 12px;
}
-->
a:link {font-size:12px; text-decoration:none; color:#fff;}
a:visited {font-size:12px; text-decoration:none; color:#fff;}
a:hover {font-size:12px; text-decoration:none; color:#FF0000;}

a.v1:link {font-size:12px; text-decoration:none; color:#03515d;}
a.v1:visited {font-size:12px; text-decoration:none; color:#03515d;}
</style>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<style> 
.navPoint { 
COLOR: white; CURSOR: hand; FONT-FAMILY: Webdings; FONT-SIZE: 9pt 
} 
</style>
<style>
<!--   multiwin
.wintitle{
	position:relative;
	left:-10px;
	z-index:0;
	background: url(../public/skins/bsn/multiwin/tab1.gif);
	background-repeat: no-repeat;
	width:150px;
	height:23px;
	padding-left:25px;
	padding-top:6px;
	cursor:hand;
}
.win1{
	width:100%; 
	height:100%; 
}
.win{
	/*position:absolute; */
	width:100%; 
	/*height:100%; */
}
.close{
	border:1pt solid red;
	width:15px;
	height:15px;
	cursor:hand;
}
-->
</style>
	</head>
<body>
	<img id="slider" src="../public/skins/bsn/menu/menu_off2.gif" style="position:absolute;left:170px;display:none;" onClick="switchMenu(this);" />
		
		<div id="datasetDefine">
			<ui:dataset datasetId="chargeFeeNotesDs" loadDataAction="FeeManagetService" loadDataActionMethod="chargeFeeNotes" staticDataSource="true">
				<ui:field field="orderId" label="订单编号"></ui:field>
				<ui:field field="partyName" label="受理部门"></ui:field>
				<ui:field field="partyRoleName" label="受理员工"></ui:field>
				<ui:field field="notes" label="提示内容"></ui:field>
			</ui:dataset>
		</div>
		
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" withSlider="false">
				  <jsp:include page="top.jsp"></jsp:include>
				</ui:pane>
				<ui:pane position="left" withSlider="false">
				  <jsp:include page="left.jsp"></jsp:include>
				</ui:pane>
				<ui:pane position="center">
					<jsp:include page="multiwin.jsp"></jsp:include>
				</ui:pane>
				<ui:pane position="bottom" withSlider="false">
					<div>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						  <tr>
						    <td width="22" height="30"><img src="images/main_38.gif" width="22" height="30" /></td>
						    <td background="images/main_40.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
						      <tr>
						        <td width="200" height="30">&nbsp;</td>
						        <td><div align="center" class="STYLE1">版权所有：中兴软创科技股份有限公司 2010 </div></td>
						        <%
						        Calendar calendar = Calendar.getInstance();
						        String year = String.valueOf(calendar.get(Calendar.YEAR));
						        String mon = String.valueOf(calendar.get(Calendar.MONTH)+1);
						        String day = String.valueOf(calendar.get(Calendar.DATE));
						        String dayw = "";
						        int dayweek = calendar.get(Calendar.DAY_OF_WEEK);
						        if(dayweek == 1) dayw = "日";
						        if(dayweek == 2) dayw = "一";
						        if(dayweek == 3) dayw = "二";
						        if(dayweek == 4) dayw = "三";
						        if(dayweek == 5) dayw = "四";
						        if(dayweek == 6) dayw = "五";
						        if(dayweek == 7) dayw = "六";
						        %>
						        <td width="200"><div align="right" class="STYLE1"><%=year %>年<%=mon %>月<%=day %>日 星期<%=dayw %></div></td>
						      </tr>
						    </table></td>
						    <td width="28"><img src="images/main_43.gif" width="28" height="30" /></td>
						  </tr>
						</table>
					</div>					
				</ui:pane>
			</ui:layout>
		</div>
</body>
</html>