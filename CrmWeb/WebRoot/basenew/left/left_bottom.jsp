<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
<head>
<script src="TaskMenu.js"></script>
<script>
var taskMenu1;
var taskMenu2;
var taskMenu3;
var taskMenu4;

var item1;
var item2;
var item3;
var item4;
var item5;
var item6;
var item7;
var item8;
TaskMenu.setStyle("Blue/BlueStyle.css"); 

window.onload = function()
{
	TaskMenu.setHeadMenuSpecial(true);
	//TaskMenu.setScrollbarEnabled(true);
	//TaskMenu.setAutoBehavior(false);
	////////////////////////////////////////////////
	item1 = new TaskMenuItem("VSOP ��ʾ","Image/demo.gif","parent.window.frames[1].location.href='TaskMenu_Demo.html'");
	item2 = new TaskMenuItem("VSOP 1.0 API","Image/api.gif","parent.window.frames[1].location.href='TaskMenu_API.html'");
	item3 = new TaskMenuItem("һЩ����˵��","Image/copy.gif","parent.window.frames[1].location.href='TaskMenu_readme.html'");
	item4 = new TaskMenuItem("div+cssģ��3","Image/friends.gif","addWin('#','title','code')");
	item5 = new TaskMenuItem("VSOP 3.0 ����","Image/dload.gif","parent.window.frames[1].location.href='#'");
	item6 = new TaskMenuItem("��̨VSOP","Image/update.gif","parent.window.frames[1].location.href='#'");
	item7 = new TaskMenuItem("<B>TaskMenu</B>");
	item8 = new TaskMenuItem();
	item8.setLabel("��ǰ�汾 <b style='color:purple'>1.0</b>");

	////////////////////////////////////////////////
	taskMenu1 = new TaskMenu("VSOP���ܲ˵�");
	taskMenu1.add(item1);
	taskMenu1.add(item2,1);
	//taskMenu1.setBackground("Image/bg.gif");
	taskMenu1.init();
	taskMenu2 = new TaskMenu("VSOP ����");
	taskMenu2.add(item3);
	taskMenu2.add(item4);
	taskMenu2.add(item5);
	taskMenu2.add(item6);
	taskMenu2.init();
	taskMenu3 = new TaskMenu("���Բ˵�");
	taskMenu3.init();
	taskMenu4 = new TaskMenu("VSOP �汾");
	taskMenu4.init();
	taskMenu4.add(item7);
	taskMenu4.add(item8);
}
</script>
</head>
</html>