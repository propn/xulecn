<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,treeList,validator">
<title></title>
<script language="javascript" src="../public/components/common2.js"></script>
<script language="javascript" src="../public/components/encrypt.js"></script>
<ui:import library=""></ui:import>
<script>
  function gotoPage(page){
    if(page!=null && page!=""){
    	window.open(page, "favorite", "");
  	}
  }
function getDateTime(){
   var s = "";
   var d = new Date();
   s += d.getYear()+"-";
   s += (d.getMonth() + 1) + "-";
   s += d.getDate() + " ";
   s += d.getHours()+":";
   s += d.getMinutes()+":";
   s += d.getSeconds(); 
   return(s);
} 
  
  var userCode ;
  var userName ;
function getUserInfo(){
	var ajaxCall = new NDAjaxCall(false);
	var callBack = function( reply ){
		var loginInfo = reply.getResult() ;
		userCode = loginInfo["staffCode"];
		userName = loginInfo["partyName"];
	}
	ajaxCall.remoteCall("LoginService","getLoginInfo",[],callBack);
}
		
  function page_onLoad(){
  	getUserInfo();
  	staffInfo.innerHTML = "工号：" + userCode + "<br/>" + "姓名：" + userName + "<br/>";
  	loginInfo.innerHTML = "您已经成功登陆<br/>VSOP系统<br/>";
  }
  
  function newWin(URL){
  			var width = screen.availWidth-3;
			var height = screen.availHeight-20;
			var left = -4;
			var top = -4;  
			var loginMainWin = window.open(URL,"",'toolbar=no,status=no,location=no,scrollbars=no,resizable=yes,width='+width+',height='+height+',top=0,left=0');
			//loginMainWin.moveTo(left, top);
			loginMainWin.focus();
			self.blur();
  	}
  
</script>
<style type="text/css">

<!--

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 9pt; font-family:宋体;
}

table,td,tr {font-size:9pt;font-family:宋体}
a:link       { color: #102879; text-decoration: none }
a:visited    { color: #102879; text-decoration: none }
a:hover      { color: #ff0000; text-decoration: underline }
a:active     { color: #0066CB; text-decoration: none }
a.inverse:link { color: #FFFFFF; text-decoration: none }
a.inverse:visited { color: #FFFFFF; text-decoration: none }
a.inverse:hover { color: #FFFF00; text-decoration: underline }
a.inverse:active { color: #FFFF00; text-decoration: none }
.btn_108 {	height:20px;
	width:108px;
	background-image: url(../public/skins/bsn/button/btn_108.gif);
	layer-background-image: url(../public/skins/bsn/button/btn_108.gif);
	border: 0px none #000000;
	padding-top: 4px;
	color: #006699;
	font-size: 12px;
	cursor: hand;
}
-->
</style>
</HEAD>

<BODY style="background:#ffffff;filter:;">

<TABLE width="100%" height="100%" border=0 cellPadding=0 cellSpacing=0>
  <TBODY>
    <TR>
      <TD height=12><IMG height=12 alt="" src="../public/skins/bsn/bulletin/msg_01.gif" width=11></TD>
      <TD background=../public/skins/bsn/bulletin/msg_02.gif colSpan=3><IMG height=12 alt="" src="../public/skins/bsn/bulletin/msg_02.gif" width=15></TD>
      <TD><IMG height=12 alt="" src="../public/skins/bsn/bulletin/msg_03.gif" width=11></TD>
    </TR>
    <TR>
      <TD width=11 background=../public/skins/bsn/bulletin/msg_04.gif><IMG height=10 alt="" src="../public/skins/bsn/bulletin/msg_04.gif" width=11></TD>
      <TD vAlign=top><table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="100%" align="center" valign="middle" style="background:url(../public/skins/bsn/menu/index_welcome.gif) no-repeat center center"></td>
        </tr>
        
      </table></TD>
      <TD vAlign=top width=29 background=../public/skins/bsn/bulletin/msg_08.gif><IMG height=419 alt="" src="../public/skins/bsn/bulletin/msg_08.gif" width=29></TD>
      <TD width=216 vAlign=top bgcolor="#FAFAFA" style="background-image: url(../public/skins/bsn/bulletin/main_bg.gif);background-repeat: no-repeat;background-position: right bottom;"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="../public/skins/bsn/bulletin/top002.gif" width="216" height="47"></TD>
          </tr>
          <tr>
            <td height="100" align="center" background="../public/skins/bsn/bulletin/rightbar_di.gif">
            	<table width="90%" border="0" cellspacing="2" cellpadding="2" style="color:#0066CC">
            		<tr>
            			<td align="center" id="loginInfo">
  						</td>
  					</tr>
  					<tr>
          				<td id="divPasswordRemindInfo">&nbsp;
          				</td>   
          			</tr>
            	</table>
            </td>
          </tr>
          <tr>
            <td><img src="../public/skins/bsn/bulletin/top001.gif" width="216" height="47"></TD>
          </tr>
          <tr>
            <td height="100" background="../public/skins/bsn/bulletin/rightbar_di.gif">&nbsp;
            <table width="90%" border="0" cellspacing="2" cellpadding="2" style="color:#0066CC">
  				<td align="center" id="staffInfo">&nbsp;</td>
            </table>
            </td>
          </tr>
          <tr>
            <td><img src="../public/skins/bsn/bulletin/top003.gif" width="216" height="47"></TD>
          </tr> 
          <tr>
            <td><img src="../public/skins/bsn/bulletin/dxfs_bottom.gif" width="216" height="13"></TD>
          </tr>
          
        </table>
        <!-- 右边显示区域 -->
        <TABLE id=contentArea style="DISPLAY: none" height="100%" cellSpacing=1 cellPadding=1 width="100%" align=center border=0>
          <TBODY>
            <TR>
              <TD vAlign=top align=middle></TD>
            </TR>
          </TBODY>
        </TABLE></TD>
      <TD width=11 background=../public/skins/bsn/bulletin/msg_10.gif><IMG height=10 alt="" src="../public/skins/bsn/bulletin/msg_10.gif" width=11></TD>
    </TR>
    <TR>
      <TD height=13><IMG height=13 alt="" src="../public/skins/bsn/bulletin/msg_16.gif" width=11></TD>
      <TD background=../public/skins/bsn/bulletin/msg_17_bg.gif colSpan=3 height="13"></TD>
      <TD><IMG height=13 alt="" src="../public/skins/bsn/bulletin/msg_18.gif" width=11></TD>
    </TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
