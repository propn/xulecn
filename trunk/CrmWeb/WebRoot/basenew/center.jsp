<%@ page language="java" pageEncoding="GBK"%>
 
<script>
function switchSysBar(){ 
	var leftSide = document.getElementById('frmTitle');
	var displayStype = leftSide.style.display;
	if(displayStype == '') {
		document.getElementById("img1").src="images/main_30_1.gif";
		leftSide.style.display = 'none';
	}else{
		leftSide.style.display = '';
		document.getElementById("img1").src="images/main_30.gif";
	}
} 

</script>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="156" id="frmTitle" noWrap name="fmTitle"  align="center" valign="top">
		<table width="150" height="32px" border="0" cellpadding="0" cellspacing="0">
		  <tr>
		        <td align="center" valign="top" height="20" background="images/main_21.gif">&nbsp;</td>
		  </tr>
		</table>
		<div id="secondLevelMenuDiv" style="height:100%;width:150px;">
			<ui:layout type="border" style="border-right:1px solid #97C4F4;background-image: url(../public/skins/bsn/menu/left_bg.jpg);background-repeat: no-repeat;background-position: left bottom;">
				<ui:pane position="top" withSlider="false" style="background-image: url(../public/skins/bsn/menu/menu_bg.gif);background-repeat: repeat-x;height:23px;">
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="menuTreeView" class="treelist" onItemClick="clickMenu();" onItemDblClick="dbclickMenu();" showBorder="false" contBorder="false" showImage=true showImage="true" showHead="false">
						<ZTESOFT:columns>
							<ZTESOFT:column width="100%" display="true" displayText="菜单名称" propertyName="menuName" />
							<ZTESOFT:column width="" display="false" displayText="菜单编码" propertyName="menuCode" />
							<ZTESOFT:column width="" display="false" displayText="菜单连接" propertyName="targetName" />
							<ZTESOFT:column width="" display="false" displayText="参数" propertyName="para" />
							<ZTESOFT:column width="" display="false" displayText="权限名称" propertyName="privilegeName" />
							<ZTESOFT:column width="" display="false" displayText="菜单打开标志" propertyName="openFlag" />
							<ZTESOFT:column width="" display="false" displayText="权限判断标志" propertyName="privilegeFlag" />
							<ZTESOFT:column width="" display="false" displayText="菜单类型" propertyName="menuType" />
							<ZTESOFT:column width="" display="false" displayText="菜单ID" propertyName="menuId" />
							<ZTESOFT:column width="" display="false" displayText="系统标识" propertyName="systemId" />
							<ZTESOFT:column width="" display="false" displayText="同级序号" propertyName="orderId" />
							<ZTESOFT:column width="" display="false" displayText="有效标志" propertyName="validFlag" />
							<ZTESOFT:column width="" display="false" displayText="菜单级别" propertyName="menuGrade" />
							<ZTESOFT:column width="" display="false" displayText="上级菜单标识" propertyName="superId" />
							<ZTESOFT:column width="" display="false" displayText="菜单图片路径" propertyName="imagePath" />
							<ZTESOFT:column width="" display="false" displayText="备注" propertyName="comments" />
							<ZTESOFT:column width="" display="false" displayText="权限标识" propertyName="privilegeId" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
			</ui:layout>
		</div>
	</td>
    <td width="4" valign="middle" background="images/main_27.gif" onclick=switchSysBar()><SPAN class=navPoint 
id=switchPoint title=关闭/打开左栏><img src="images/main_30.gif" name="img1" width="4" height="47" id="img1"></SPAN></td>
    <td align="center" valign="top">
    	
		<TABLE id="workspaceTitleTable" height="23" width="100%" border=0 cellspacing=0 cellpadding=0 onselectstart="return false" style="table-layout: fixed" background="../public/skins/bsn/multiwin/multi_bg.gif">
			<TR height="23">
				<TD valign="bottom" nowrap>
					<div style="overflow:hidden;width:100%">
						<div id="titlelist" style="margin-left:0;z-index:-1"></div>
					</div>
				</TD>
				<TD width="25" style="color:green;cursor:hand;">
					<span title="左移工作区" onmousedown="tabScroll('left')" onmouseup="tabScrollStop()">&lt;</span> <span title="右移工作区" onmousedown="tabScroll('right')" onmouseup="tabScrollStop()">&gt; 
				</TD>
				<TD width="25">
					<IMG title="关闭全部工作区" SRC="../public/skins/bsn/multiwin/close_all.gif" WIDTH="21" HEIGHT="21" BORDER=0 ALT="" onclick="win.removeall()" align="center" onmouseover="this.src='../public/skins/bsn/multiwin/close_all_over.gif'"
						onmouseout="this.src='../public/skins/bsn/multiwin/close_all.gif'" vspace=2>
				</TD>
				<TD width="25">
					<IMG title="刷新当前工作区" SRC="../public/skins/bsn/multiwin/refresh1.gif" WIDTH="16" HEIGHT="20" BORDER=0 ALT="" onclick="win.refreshwin(win.currentwin)" align="center" onmouseover="this.src='../public/skins/bsn/multiwin/refresh.gif'"
						onmousedown="this.src='../public/skins/bsn/multiwin/refresh.gif'" onmouseout="this.src='../public/skins/bsn/multiwin/refresh1.gif'" vspace=2>
				</TD>
				<TD width="25">
					<IMG title="关闭当前工作区" SRC="../public/skins/bsn/multiwin/close.gif" WIDTH="21" HEIGHT="21" BORDER=0 ALT="" onclick="win.removewin(win.currentwin)" align="center" onmouseover="this.src='../public/skins/bsn/multiwin/close_over.gif'"
						onmousedown="this.src='../public/skins/bsn/multiwin/close_down.gif'" onmouseout="this.src='../public/skins/bsn/multiwin/close.gif'" vspace=2>
				</TD>
			</TR>
		</TABLE>
		<div id="mywindows" style="margin:0;padding:0;text-align:left">
		  <div id="mywindowsLayer">
		  	<iframe src="layer.htm" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
		  </div>
		</div>
    </td>
  </tr>
</table>
