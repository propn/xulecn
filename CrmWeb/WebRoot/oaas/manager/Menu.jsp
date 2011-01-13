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
				<ui:field field="menuId" label="�˵�ID" visible="false"></ui:field>
				<ui:field field="menuName" size="10" label="�˵�����" required="true" validType="require" validMsg="������˵�����!"></ui:field>
				<ui:field field="systemId" label="ϵͳ��ʶ" required="true" validType="require" validMsg="��ѡ��ϵͳ��ʶ!" dropDown="systemIdSelect"></ui:field>
				<ui:field field="menuCode" size="10" label="�˵�����" required="true" validType="require" validMsg="������˵�����!"></ui:field>
				<ui:field field="orderId" label="ͬ�����" visible="false"></ui:field>
				<ui:field field="targetName" label="�˵�����"></ui:field>
				<ui:field field="para" label="����"></ui:field>
				<ui:field field="openFlag" label="�˵��򿪱�־" required="true" validType="require" validMsg="��ѡ��˵��򿪱�־!" dropDown="openFlagSelect"></ui:field>
				<ui:field field="privilegeFlag" label="Ȩ���жϱ�־" visible="false" dropDown="privilegeFlagSelect"></ui:field>
				<ui:field field="validFlag" label="��Ч��־" required="true" validType="require" validMsg="��ѡ����Ч��־!" dropDown="validFlagSelect"></ui:field>
				<ui:field field="menuType" label="�˵�����" visible="false" dropDown="menuTypeSelect"></ui:field>
				<ui:field field="menuGrade" label="�˵�����" visible="false"></ui:field>
				<ui:field field="superId" label="�ϼ��˵���ʶ" visible="false"></ui:field>
				<ui:field field="superName" label="�ϼ��˵�����"></ui:field>
				<ui:field field="imagePath" label="�˵�ͼƬ·��" visible="false"></ui:field>
				<ui:field field="comments" label="��ע"></ui:field>
				<ui:field field="privilegeId" label="Ȩ�ޱ�ʶ" visible="false"></ui:field>
				<ui:field field="privilegeName" label="Ȩ������" visible="false"></ui:field>
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
					<ui:bar type="search" desc="�˵�����">
						<ui:content>
							<ui:button id="addRootMenu">��Ӹ��˵�</ui:button>
							<ui:button id="addSubMenu">����Ӳ˵�</ui:button>
							<ui:button id="editMenu">�༭</ui:button>
							<ui:button id="deleteMenu">ɾ��</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="menuTreeView" class="treelist" onItemClick="clickMenu()" showImage="true" showBorder="false" contBorder="true" width="100%" height="100%" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="40%" display="true" displayText="�˵�����" propertyName="menuName" />
							<ZTESOFT:column width="50%" display="true" displayText="�˵�����" propertyName="targetName" />
							<ZTESOFT:column width="10%" display="true" displayText="�˵�����" propertyName="menuCode" />							
							<ZTESOFT:column width="" display="false" displayText="����" propertyName="para" />
							<ZTESOFT:column width="" display="false" displayText="Ȩ������" propertyName="privilegeName" />
							<ZTESOFT:column width="" display="false" displayText="�˵��򿪱�־" propertyName="openFlag" />
							<ZTESOFT:column width="" display="false" displayText="Ȩ���жϱ�־" propertyName="privilegeFlag" />
							<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="menuType" />
							<ZTESOFT:column width="" display="false" displayText="�˵�ID" propertyName="menuId" />
							<ZTESOFT:column width="" display="false" displayText="ϵͳ��ʶ" propertyName="systemId" />
							<ZTESOFT:column width="" display="false" displayText="ͬ�����" propertyName="orderId" />
							<ZTESOFT:column width="" display="false" displayText="��Ч��־" propertyName="validFlag" />
							<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="menuGrade" />
							<ZTESOFT:column width="" display="false" displayText="�ϼ��˵���ʶ" propertyName="superId" />
							<ZTESOFT:column width="" display="false" displayText="�˵�ͼƬ·��" propertyName="imagePath" />
							<ZTESOFT:column width="" display="false" displayText="��ע" propertyName="comments" />
							<ZTESOFT:column width="" display="false" displayText="Ȩ�ޱ�ʶ" propertyName="privilegeId" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height:200px;">
					<ui:tabpane>
						<ui:tabpage desc="�˵��༭">
							<ui:layout type="border">
								<ui:pane position="center">
									<ui:form submit="btn_commitMenu" id="formMenuInfo" dataset="menuInfo" labelLayout="15%" inputLayout="35%"></ui:form>
								</ui:pane>
								<ui:pane position="bottom">
									<ui:button id="btn_commitMenu">ȷ��</ui:button>
									<ui:button id="btn_cancel">ȡ��</ui:button>
								</ui:pane>
							</ui:layout>
						</ui:tabpage>
					</ui:tabpane>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
