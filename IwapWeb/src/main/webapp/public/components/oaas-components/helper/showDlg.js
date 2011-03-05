
/*************************************************************************************************
  通用对话框显示函数showDlg();
  传入参数：
  aURL： 必选参数，类型：字符串。用来指定对话框要显示的文档的URL。
  vArguments：可选参数，类型：变体。用来向对话框传递参数。传递的参数类型不限，包括数组等。
              对话框通过window.dialogArguments来取得传递进来的参数。
  sFeatures：可选参数，类型：字符串。用来描述对话框的外观等信息，
			 可以使用以下的一个或几个，用分号“;”隔开。
				dialogHeight: 对话框高度
				dialogWidth: 对话框宽度。
				dialogLeft: 距离桌面左的距离。
				dialogTop: 离桌面上的距离。　　
				center: {yes | no | 1 | 0 }：窗口是否居中，默认yes，但仍可以指定高度和宽度。
				help: {yes | no | 1 | 0 }：是否显示帮助按钮，默认yes。
				resizable: {yes | no | 1 | 0 } ［IE5+］：是否可被改变大小。默认no。
				status: {yes | no | 1 | 0 } ［IE5+］：是否显示状态栏。默认为yes[Modeless]或no[Modal]。
				scroll:{ yes | no | 1 | 0 | on | off }：指明对话框是否显示滚动条。默认为yes。
***************************************************************************************************/

function showDlg(aURL,vArguments,sFeatures)
{
	window.showModalDialog(aURL,vArguments,sFeatures);
}