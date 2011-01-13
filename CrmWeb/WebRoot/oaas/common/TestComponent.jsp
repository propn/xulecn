<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library"
			CONTENT="kernel,treeList,customerpilot,calendar">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="js/TestComponent.js"></script>
		<ui:import library=""></ui:import>
		<script>
</script>
	<body>
		<ui:dataset datasetId="regionList" loadDataAction="TestService"
			paginate="true" loadDataActionMethod="getRoleListPage" async="false"
			pageIndex="1" pageSize="30" autoLoadPage="true"
			staticDataSource="true" editable="true">
			<ui:field field="select" label="选中"></ui:field>
			<ui:field field="roleName" label="<b>roleName</b>" visible="true"></ui:field>
			<ui:field field="roleDesc" label="roleDesc" visible="true"></ui:field>
			<ui:field field="roleNameShort" label="<a href=http://www.sina.com.cn target=blank >roleNameShort</a>" visible="true"></ui:field>
			<ui:field field="roleId" label="<font color=red size=6 ><b><i>roleId</i></b></font>" visible="true"></ui:field>
			<ui:field field="state" label="state" visible="true"></ui:field>
			<ui:field field="stateDate" label="stateDate" visible="true"
				type="datetime"></ui:field>
		</ui:dataset>
		<ui:tabpane>
			<ui:tabpage id="page1" desc="Event测试">
				<ui:button id="invokeObjectParam">Click</ui:button>
				<ui:button id="invokeException">invokeException</ui:button>
			</ui:tabpage>
			<ui:tabpage id="page1" desc="BindTable测试">
				<INPUT TYPE="button" NAME="" value="Bind Table"
					onclick="bindTable('testTbl')">
				<TABLE id="testTbl" border="1" jheight="0">
					<TR bgcolor="#FFCC00">
						<TD jtext="roleName">
							roleName
						</TD>
						<TD jtext="roleDesc">
							roleDesc
						</TD>
						<TD jtext="roleNameShort">
							roleNameShort
						</TD>
						<TD jtext="roleId">
							roleId
						</TD>
						<TD jtext="state">
							state
						</TD>
						<TD jtext="stateDate">
							stateDate
						</TD>
					</TR>
					<TR bgcolor="#FFFF66">
						<TD>
							a
						</TD>
						<TD>
							d
						</TD>
						<TD>
							c
						</TD>
						<TD>
							d
						</TD>
						<TD>
							e
						</TD>
						<TD>
							f
						</TD>
					</TR>
				</TABLE>
			</ui:tabpage>
			<ui:tabpage id="page1" desc="TreeList测试">
				<ui:button id="initRolesTree">Click</ui:button>
				<ZTESOFT:treelist id="roleTreeView" class="treelist"
					onItemClick="clickRole()" showBorder="false" contBorder="true"
					showHead="true">
					<ZTESOFT:columns>
						<ZTESOFT:column width="15%" display="true" displayText="角色名称"
							propertyName="roleName" />
						<ZTESOFT:column width="45%" display="true" displayText="角色描述"
							propertyName="roleDesc" />
						<ZTESOFT:column width="10%" display="true" displayText="缩写"
							propertyName="roleNameShort" />
						<ZTESOFT:column width="10%" display="true" displayText="角色标识"
							propertyName="roleId" />
						<ZTESOFT:column width="5%" display="true" displayText="状态"
							propertyName="state" />
						<ZTESOFT:column width="15%" display="true" displayText="状态时间"
							propertyName="stateDate" />
					</ZTESOFT:columns>
				</ZTESOFT:treelist>
			</ui:tabpage>
			<ui:tabpage id="page1" desc="DataTable测试">
				<ui:button id='Btn_Output_excel'>结果导出Excel文件</ui:button>&nbsp;
					<ui:button id="initRegionDataSet">Click</ui:button>
				<div class="customerpilot" extra="customerpilot"
					id="regionListPilot" dataset="regionList"></div>
				<ui:table dataset="regionList" id="tableList"></ui:table>
			</ui:tabpage>
			<ui:tabpage id="page1" desc="DataForm测试">
				<ui:form dataset="regionList"></ui:form>
			</ui:tabpage>
			<ui:tabpage id="page1" desc="Layout测试">
				<ui:layout type="border">
					<ui:pane position="top">
						<ui:layout type="border">
							<ui:pane position="left">
									Name:<input type="text"></input>
							</ui:pane>
						</ui:layout>
					</ui:pane>
					<ui:pane position="center">
								Memo:<textarea rows="30" cols="100"></textarea>
					</ui:pane>
					<ui:pane position="bottom">
						<input type="button" value="HTML Button"></input>
					</ui:pane>
				</ui:layout>
			</ui:tabpage>
		</ui:tabpane>
	</body>

</html>
