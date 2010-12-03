<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<style>
<!-- 
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
	position:absolute; 
	width:100%; 
	height:100%; 
}
.close{
	border:1pt solid red;
	width:15px;
	height:15px;
	cursor:hand;
}
-->
</style>

<ui:layout type="border">
	<ui:pane position="top" withSlider="false">
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
	</ui:pane>
	<ui:pane position="center">
		<div id="mywindows">
		  <div id="mywindowsLayer">
		  	<iframe src="layer.htm" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
		  </div>
		</div>
	</ui:pane>
</ui:layout>
