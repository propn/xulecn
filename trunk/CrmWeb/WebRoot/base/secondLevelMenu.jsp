<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<div id="secondLevelMenuDiv" style="height:100%;width:180px;">
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

