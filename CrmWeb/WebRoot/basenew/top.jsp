<%@ page language="java" pageEncoding="GBK"%>
<%
String topLogoImg = "top01.gif";
String provinceCode = com.ztesoft.vsop.web.DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
String JX_PROV_CODE = com.ztesoft.common.util.CrmConstants.JX_PROV_CODE;
if(provinceCode.equals(JX_PROV_CODE)){
	topLogoImg = "top01_jx.gif";
}
%>
<style>
.STYLE1{font-size:12px;color:#FFF;}
</style>
<table width="100%" height="88" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="210" height="88" rowspan="3" valign="top"><img src="images/new/<%=topLogoImg %>" width="210" height="88" /></td>
    <td height="28"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="40" height="28"><img src="images/new/top02.jpg" width="40" height="28" /></td>
        <td bgcolor="#0084CF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
            <td width="70"><table width="45" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="22"><div align="center"><img src="images/top_6.gif" width="14" height="14" /></div></td>
                <td width="35"><div align="center" class="STYLE1"><a href="#" onclick="backHome();">首页</a></div></td>
              </tr>
            </table></td>
            <td width="80"><table width="60" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="21"><div align="center"><img src="images/top_5.gif" width="14" height="14" /></div></td>
                <td width="24"><div align="center" class="STYLE1"><a href="#" onclick="return updatePassword();">修改密码</a></div></td>
              </tr>
            </table></td>
            <td width="80"><table width="60" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="21"><div align="center"><img src="images/top_5.gif" width="14" height="14" /></div></td>
                <td width="24"><div align="center" class="STYLE1"><a href="#" onclick="return updateCache();">刷新缓存</a></div></td>
              </tr>
            </table></td>
            <td width="80"><table width="45" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="21"><div align="center"><img src="images/top_7.gif" width="14" height="14" /></div></td>
                <td width="24"><div align="center" class="STYLE1"><a href="#" onclick="return logout();">退出</a></div></td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="35">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="35">
		        <table width="100%" height="35px" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td style="background:url(images/new/top05_bg.gif) top repeat-x">
		            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
			              <tr>
			                <td width="40" valign="top"><img src="images/new/top04.gif" width="40" height="35" /></td>
			                <td>
			                	<div id="top_menu" style="height:28px;width:100%;margin-top:2px;padding:0;"></div>
			                </td>
			              </tr>
		            	</table>
		            </td>
		            </tr>
		        </table>
	         </td>
	        </tr>
	    </table>
    </td>
  </tr>
  <tr>
    <td height="25" background="images/new/top06_bg.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30"><div align="center"><img src="images/top_1.gif" width="14" height="14" /></div></td>
        <td  nowrap="nowrap" style="color:#FFF">当前登录工号：<span id="login_code"></span>&nbsp;&nbsp;姓名:<span id="login_name"></span></td> 
      </tr>
    </table></td>
  </tr>
</table>
<script>
	function updatePassword(){
	   openDialog("../base/updatePassword.jsp", window, "", "400px", "240px");
	   return false;
	}
	
	function updateabout(){
	   openDialog("about.htm", window, "", "505px", "390px");
	   return false;
	}
	
	function backHome(){
	   //AddWin("../pbnews/PbNews.jsp", "首页")
	   AddWin("../base/PbNews.jsp", "首页")
	   return false;
	}
	
	function logout(){
		////收银台有费用时可以关闭窗口
		
		var url = win.currentwin.url;
		if( url.indexOf( "FeeManagement.jsp") > 0 ){
			var currWin = window.frames[win.currentwin.index];
			var objText = currWin.document.getElementById('text_datasetChargeValitor_text_Fee');
			if (objText!=null){
				var textFee = objText.value;
				if (textFee!=null&&textFee!=""){
					objText.value="";
				}
			}
		}
		
		
		invalidSession();
		//top.location = "../index.html" ;
		window.top.close();
	}
	
	
   var  callBackSwitch = function(reply){
	    var result=reply.getResult();
	    if (result!=null){
	        parent.window.frames[0].location.reload();
	    }
   }


   function LoginRequest(){
		this.staffCode = ""; //工号
		this.password = ""; //口令
		this.mac = ""; //MAC地址
	}
	
	function LoginRespond(){
		this.strMsgNo = "";
		this.success = ""; //返回信息是否成功: 0 失败 1 成功
		this.reason = ""; //失败原因
	}


    function newWin(){
        alert(9);
		var width = screen.availWidth-3;
		alert(10);
		var height = screen.availHeight-20;
		alert(11);
		var left = -4;
		var top = -4;  
		var loginMainWin = window.open('../base/frame.htm',"",'toolbar=no,status=no,location=no,scrollbars=no,resizable=yes,width='+width+',height='+height+',top=0,left=0');
		alert(12);
		loginMainWin.moveTo(left, top);
		loginMainWin.focus();
		self.blur();
	}
	function updateCache(){
		//AddWin("<%=request.getContextPath()%>/servlet/InitConfigServlet", "刷新缓存");
		//win.removewin(win.currentwin);
			NDAjaxCall.getSyncInstance().remoteCall('SignOnService','refreshAllServerCaches',[], 
		function(reply){
			var result = reply.getResult() ;
			if('0'== result){
				window.alert('刷新缓存成功!');
			}else {
				window.alert("刷新缓存失败 ");
			}
		 
		}
	);
	}
</script>