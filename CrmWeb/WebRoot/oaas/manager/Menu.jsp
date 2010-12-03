<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,validator,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/Menu.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<ui:dataset datasetId="menuInfo" readOnly="true">
				<ui:field field="menuId" label="菜单ID" visible="false"></ui:field>
				<ui:field field="menuName" size="10" label="菜单名称" required="true" validType="require" validMsg="请输入菜单名称!"></ui:field>
				<ui:field field="systemId" label="系统标识" required="true" validType="require" validMsg="请选择系统标识!" dropDown="systemIdSelect"></ui:field>
				<ui:field field="menuCode" size="10" label="菜单编码" required="true" validType="require" validMsg="请输入菜单编码!"></ui:field>
				<ui:field field="orderId" label="同级序号" visible="false"></ui:field>
				<ui:field field="targetName" label="菜单连接"></ui:field>
				<ui:field field="para" label="参数"></ui:field>
				<ui:field field="openFlag" label="菜单打开标志" required="true" validType="require" validMsg="请选择菜单打开标志!" dropDown="openFlagSelect"></ui:field>
				<ui:field field="privilegeFlag" label="权限判断标志" visible="false" dropDown="privilegeFlagSelect"></ui:field>
				<ui:field field="validFlag" label="有效标志" required="true" validType="require" validMsg="请选择有效标志!" dropDown="validFlagSelect"></ui:field>
				<ui:field field="menuType" label="菜单类型" visible="false" dropDown="menuTypeSelect"></ui:field>
				<ui:field field="menuGrade" label="菜单级别" visible="false"></ui:field>
				<ui:field field="superId" label="上级菜单标识" visible="false"></ui:field>
				<ui:field field="superName" label="上级菜单名称"></ui:field>
				<ui:field field="imagePath" label="菜单图片路径" visible="false"></ui:field>
				<ui:field field="comments" label="备注"></ui:field>
				<ui:field field="privilegeId" label="权限标识" visible="false"></ui:field>
				<ui:field field="privilegeName" label="权限名称" visible="false"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="privilegeFlagSelect" attrCode="MENU_PRIVILEGE_FLAG" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="systemIdSelect" attrCode="SYSTEM_ID" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="openFlagSelect" attrCode="MENU_OPEN_FLAG" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="validFlagSelect" attrCode="MENU_VALID_FLAG" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="menuTypeSelect" attrCode="MENU_TYPE" staticDataSource="false"></ui:dropdown>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="菜单管理">
						<ui:content>
							<ui:button id="addRootMenu">添加根菜单</ui:button>
							<ui:button id="addSubMenu">添加子菜单</ui:button>
							<ui:button id="editMenu">编辑</ui:button>
							<ui:button id="deleteMenu">删除</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="menuTreeView" class="treelist" onItemClick="clickMenu()" showImage="true" showBorder="false" contBorder="true" width="100%" height="100%" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="40%" display="true" displayText="菜单名称" propertyName="menuName" />
							<ZTESOFT:column width="50%" display="true" displayText="菜单连接" propertyName="targetName" />
							<ZTESOFT:column width="10%" display="true" displayText="菜单编码" propertyName="menuCode" />							
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
				<ui:pane position="bottom" style="height:200px;">
					<ui:tabpane>
						<ui:tabpage desc="菜单编辑">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_commitMenu" id="formMenuInfo" dataset="menuInfo" labelLayout="15%" inputLayout="35%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_commitMenu">确定</ui:button>
									<ui:button id="btn_cancel">取消</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
