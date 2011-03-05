<%@ Page language="c#" Codebehind="testadvcombobox.aspx.cs" AutoEventWireup="false" Inherits="ccbweb.htc.AdvComboBox.testadvcombobox" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" > 

<html dir=ltr>
  <head>
    <title>testadvcombobox</title>
    <meta name="GENERATOR" Content="Microsoft Visual Studio .NET 7.1">
    <meta name="CODE_LANGUAGE" Content="C#">
    <meta name=vs_defaultClientScript content="JavaScript">
    <meta name=vs_targetSchema content="http://schemas.microsoft.com/intellisense/ie5">
    <?XML:NAMESPACE PREFIX=AdvComboBox />
		<?IMPORT NAMESPACE=AdvComboBox IMPLEMENTATION="AdvComboBox.htc">	
	<script language="javascript">
	function onInit()
	{
			
	}

	function reset_onclick()
	{
		document.all("a2").defaultValue=null;
	}

	function a1_onchanged()
	{
			var curVal = event.curValue;
			var control =event.srcElement;
			
			if(control.controlType=="ComboBox")		
			{			
				//alert(control.value);
			}		
	}
	</script>	
  </head>
  <body onload="onInit();">
  <table>
  <tr>
  <td>
  <table border=1>
	<tr>
		<td>dddddddddddddddsfsdfs</td><td width="200px">
			<AdvComboBox:AdvComboBox id="a2" htmlDir="ltr" multipleChoice="true" defaultValue='002,003'onchanged="a1_onchanged();">			
				<AdvComboBox:Property>
					<AdvComboBox:Item text="16岁以下" value="001"></AdvComboBox:Item>
					<AdvComboBox:Item text="16岁～28岁" value="002"></AdvComboBox:Item>
					<AdvComboBox:Item text="28岁～40岁" value="003"></AdvComboBox:Item>
					<AdvComboBox:Item text="40岁以上" value="004"></AdvComboBox:Item>
				</AdvComboBox:Property>				
			</AdvComboBox:AdvComboBox>&nbsp;</td>
		</tr>
		<tr>
			<td><input type=button value="ddd"></td>
			<td><input type=text value="sssssssssssssssssssss"></td>
		</tr>
		<tr>
		<td>sjjjjjjjjjjjjsdfsf</td>
		<td><div><AdvComboBox:AdvComboBox id="a1" htmlDir="ltr" multipleChoice="true" defaultValue='002,003'onchanged="a1_onchanged();">			
				<AdvComboBox:Property>
					<AdvComboBox:Item text="16岁以下" value="001"></AdvComboBox:Item>
					<AdvComboBox:Item text="16岁～28岁" value="002"></AdvComboBox:Item>
					<AdvComboBox:Item text="28岁～40岁" value="003"></AdvComboBox:Item>
					<AdvComboBox:Item text="40岁以上" value="004"></AdvComboBox:Item>
				</AdvComboBox:Property>				
			</AdvComboBox:AdvComboBox></div>&nbsp;</td>
		</tr>
	</table>
</td>
</tr>
<tr>
	<td>
	<table border="1" borderColorDark="lavender" borderColorLight="black" cellPadding="0" cellSpacing="0" width="50%" name="tblProperty" ID="tblProperty">
		<tr>
			<td>
				aaaa
			</td>
		</tr>
	</table>
</td>
</tr>
<tr>
<td>
	<input type="button" id="reset" value="清空" onclick="reset_onclick();" NAME="reset">
</td>
</tr>
</table>
</body>
</html>
