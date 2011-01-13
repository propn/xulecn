<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<script>
	function updatePassword(){
	   openDialog("updatePassword.jsp", window, "", "400px", "240px");
	   return false;
	}
	
	function updateabout(){
	   openDialog("about.htm", window, "", "505px", "390px");
	   return false;
	}
	
	function backHome(){
	   //AddWin("../pbnews/PbNews.jsp", "首页")
	   AddWin("PbNews.jsp", "首页")
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
		top.location = "../index.html" ;
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
	
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="Bar">
  <tr>
    <td width="520" height="46"><img src="../public/skins/bsn/menu/index_logo.jpg" width="520" height="47" /></td>
    <td background="../public/skins/bsn/menu/index_bg1.gif">&nbsp;</td>
    <td width="251" valign="top" background="../public/skins/bsn/menu/index_bg_right.gif"><table border="0" cellspacing="3" cellpadding="0" align="right" valign="top">
      <tr>
        <td><table  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="4"><img src="../public/skins/bsn/menu/box/topright_left.gif" width="4" height="19"></td>
              <td background="../public/skins/bsn/menu/box/topright_bg.gif" class="hand" style="color:#FFFFFF;"><img src="../public/skins/bsn/menu/icon_aboutus.gif" width="12" height="15" align="absmiddle"><a id="modifyPassword" hideFocus="true" target="blank" href="#" onclick="return updatePassword();"><span class="hand" style="color:#FFFFFF;text-decoration: none;">修改密码</span></a></td>
              <td width="4"><img src="../public/skins/bsn/menu/box/topright_right.gif" width="4" height="19"></td>
            </tr>
        </table></td>
        <td><table  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="4"><img src="../public/skins/bsn/menu/box/topright_left.gif" width="4" height="19"></td>
              <td background="../public/skins/bsn/menu/box/topright_bg.gif" class="hand" style="color:#FFFFFF;"><img src="../public/skins/bsn/menu/icon_logout.gif" width="12" height="15" align="absmiddle"><a id="exit" hideFocus="true" target="" href="#" onclick="return logout();"><span class="hand" style="color:#FFFFFF;text-decoration: none;">退 出</span></a></td>
              <td width="4"><img src="../public/skins/bsn/menu/box/topright_right.gif" width="4" height="19"></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td colspan="3" background="../public/skins/bsn/menu/index_bg_topbg.gif"><img src="../public/skins/bsn/menu/shim.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td colspan="3" bgcolor="#FF9D5F" height="3"><img src="images/shim.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td colspan="3">
</td>
  </tr>
</table>


