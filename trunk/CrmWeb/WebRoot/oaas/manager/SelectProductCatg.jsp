<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<%--
	�滻�޸�˵�� ��
	1.dsQuery���ݼ��������ֶΡ�����
	2.dsProductCatgList ����
	3.dsProductCatgList �ֶ��������ֶ�����
	4.dsProductCatgForm �ֶ��������ֶ����͡���֤��Ϣ
	5. �滻��ϣ�ɾ���˶����֣�
 --%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,calendar,validator">
		<title></title>
		<script language="javascript" src="<%=request.getContextPath()%>/public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/ProductCatg.js"></script>
	</head>
	<body>
		<div id="datasetDefine">	
		
			<ui:dataset datasetId="dsQuery">
				<%--need-replace ��ѯ����,��Ҫ����ҵ��ʵ����Ҫ�趨�� --%>		
				<ui:field field="catalogName" label="��Ʒ����" visible="true"></ui:field>
				<ui:field field="catalogCode" label="��Ʒ����" visible="true"></ui:field>
			</ui:dataset>
			
			<ui:dataset datasetId="dsProductCatgList" staticDataSource="true" 
				loadDataAction="ProductCatgService" loadDataActionMethod="searchProductCatgData" 
				readOnly="true" pageIndex="1" pageSize="10" autoLoadPage="true" async="true" loadAsNeeded="true" paginate="true">
												
				<ui:field label="catalog_id" field="catalog_id"  visible="false" ></ui:field>
				<ui:field label="ѡ��" field="catalog_select"  visible="true"></ui:field>
				<ui:field label="��Ʒ����" field="catalog_name"  visible="true"></ui:field>
				<ui:field label="��Ʒ����" field="catalog_code"  visible="true"></ui:field>
				<ui:field label="party_id" field="party_id"  visible="false"></ui:field>
				<ui:field label="party_role_id" field="party_role_id"  visible="false"></ui:field>
				<ui:field label="oper_region_id" field="oper_region_id"  visible="false"></ui:field>
											   
			    <%--need-replace ��ѯ����,��Ҫ����ҵ��ʵ����Ҫ�趨�� --%>		
			    <ui:parameter parameterId="param1" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param2" type="string" value=""></ui:parameter>
			    <ui:parameter parameterId="param3" type="string" value=""></ui:parameter>
			</ui:dataset>

		</div>
				
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top" style="height:280px;">
					<ui:panel type="titleList" desc="��ѯ���">
						<ui:content>
							<ui:layout type="border">
								<ui:pane position="top" style="height:55px;">
									<ui:layout type="border">
									<ui:pane position="center" style="width:100%">
										<ui:form dataset="dsQuery" labelLayout="15%" inputLayout="15%"></ui:form>
									</ui:pane>
									<ui:pane position="right" style="width:50px;margin-top:25px;margin-right:5px;">
										<ui:button id="btn_query">��ѯ</ui:button>
									</ui:pane>
									</ui:layout>
								</ui:pane>
								<ui:pane position="center" style="width:100%">
									<ui:table dataset="dsProductCatgList"></ui:table>
								</ui:pane>
								<ui:pane position="bottom" style="width:100%">
									<div style="clear:both;text-align:center;">
										<ui:button id="selectPartyRole">ѡ��</ui:button>&nbsp;
										<ui:button id="backMainPage">����</ui:button>
									</div>
								</ui:pane>
							</ui:layout>
						</ui:content>
					</ui:panel>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>