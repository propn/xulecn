<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<div id="secondLevelMenuDiv" style="height:100%;width:180px;">
	<ui:layout type="border" style="border-right:1px solid #97C4F4;background-image: url(../public/skins/bsn/menu/left_bg.jpg);background-repeat: no-repeat;background-position: left bottom;">
		<ui:pane position="top" withSlider="false" style="background-image: url(../public/skins/bsn/menu/menu_bg.gif);background-repeat: repeat-x;height:23px;">
		</ui:pane>
		<ui:pane position="center">
			<ZTESOFT:treelist id="menuTreeView" class="treelist" onItemClick="clickMenu();" onItemDblClick="dbclickMenu();" showBorder="false" contBorder="false" showImage=true showImage="true" showHead="false">
				<ZTESOFT:columns>
					<ZTESOFT:column width="100%" display="true" displayText="�˵�����" propertyName="menuName" />
					<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="menuCode" />
					<ZTESOFT:column width="" display="false" displayText="�˵�����" propertyName="targetName" />
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
	</ui:layout>
</div>

