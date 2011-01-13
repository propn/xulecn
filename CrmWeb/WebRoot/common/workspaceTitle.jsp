<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<title></title>
		<script language="JavaScript" src="../public/components/common2.js" charset="gb2312"></script>
		<script language="JavaScript" src="./script/AlternateDat.js" charset="gb2312"></script>
		<ui:import library=""></ui:import>
		<script>  
		Global.disableSystemContextMenu=true;
 		Global.disableContextSelect=true;
		Global.disableContextRefresh=true;	
		Global.disableAsyncRemoteCallProcessingBar=true;
		var userInfoBean = new Object();
		
		function closeWorkspace_onClick(){
		  top.closeWin();    
		}
		function closeAllWorkspace_onClick(){
		  top.closeAllWin();    
		}  
		function refreshWorkspace_onClick(){
		  top.refreshWin();
		}
		function page_onLoad(){
		  for(var i=0; i<3; i++){
		    var title = $("workspaceTitle"+(i+1));
		    title.ondblclick = top.closeWin;
		    
		    title.onclick = function(){
		      if(this.parentElement.className=="selected") return false;
		      top.currentWinIndex = this.winIndex;
		      top.switchPage(this.winIndex);
		      return false;
		    }
		    top.winListTitle[top.winListTitle.length] = title;	
		  }	  
		  init();
		  
		}

	
		function init(){
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
				var loginInfo = reply.getResult() ;
				spanBusiness.innerText = loginInfo["businessName"] ;
				spanStaff.innerText = loginInfo["partyName"];
				
				userInfoBean.userId = loginInfo["partyId"];
				userInfoBean.userCode = loginInfo["staffCode"];
				userInfoBean.userName = loginInfo["partyName"];
				userInfoBean.ip = loginInfo["ip"];
				userInfoBean.macAddress = "";
				userInfoBean.agentFlag = "0";
				
			}
			ajaxCall.remoteCall("LoginService","getLoginInfo",[],callBack);
		}
		
		
		function setRoleInfo(){
			var ajaxCall = new NDAjaxCall(true);
			var callBack = function( reply ){
			    var result = reply.getResult() ;
			    userInfoBean.roleStateBsnStrucArr = result;
				var altData = new AlternateData();
    			altData.setValue(userInfoBean);

			}
			ajaxCall.remoteCall("LoginService","getLoginRolesInfo",[],callBack);
		}
		</script>
		<style>
		  td.invisible{width:0}
		  td.unselected, td.selected{width:150px;}
		  td.unselected a, td.selected a{width:100%;display:block;}
		  td.invisible a{display:none;}
		  td.unselected{background:url(../public/skins/bsn/multiwin/tabwin_off.gif) no-repeat left center;}
		  td.unselected a{margin-left:10px;color:black;padding-left:20px;background: url(../public/skins/bsn/multiwin/ico_1.gif) no-repeat left center;}
		  td.selected{background:url(../public/skins/bsn/multiwin/tabwin_on.gif) no-repeat left center;}
		  td.selected a{margin-left:10px;color:#080CBD;font-weight: bold;padding-left:20px;background:url(../public/skins/bsn/multiwin/ico_on.gif) no-repeat left center;}
		</style>
	</head>
	<body scroll="no">

		      <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0" background="../public/skins/bsn/multiwin/multi_bg.gif">
		      <tr>
		        <td nowrap id="title" class="invisible">
		        	<a id="workspaceTitle1"></a>
		        </td>
		        <td nowrap id="title" class="invisible">
		        	<a id="workspaceTitle2"></a>
		        </td>
		        <td nowrap id="title" class="invisible">
		        	<a id="workspaceTitle3"></a>
		        </td>
		        <td width="100%">
		          	<marquee direction=left onmouseover='this.stop()' onmouseout='this.start()' scrollAmount=2>            
			            <span style="font-size:12px;">
			            	<font color="#666666">欢迎您<font color="#0099CC">[<span id="spanBusiness"></span>]</font>营业厅<font color="#0099CC">[<span id="spanStaff"></span>]</font>本地网用户登录</font>
			            </span>
		          	</marquee>        
		        </td>
		        <td nowrap width="70">
						<a hideFocus="true" id="refreshWorkspace" onclick="refreshWorkspace_onClick();return false;">&nbsp;</a>
						<a hideFocus="true" id="closeWorkspace" onclick="closeWorkspace_onClick();return false;">&nbsp;</a>
						<a hideFocus="true" id="closeAllWorkspace" onclick="closeAllWorkspace_onClick();return false;">&nbsp;</a>
		        </td>
		      </tr>
		      </table>
		    
	</body>
</html>
