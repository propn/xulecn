<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,customerpilot,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<script language="javascript" src="../../common/script/CommDbOperat.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/BillRegion.js"></script>
	</head>
	<body>
		<div id="datasetDefine">

            <xml id="__ACTUALREG_FLAG">
            <items>
            <item label="真实区域" value="T" />
            <item label="虚拟区域" value="F" />
            </items>
            </xml>
            <code id="ACTUALREG_FLAG"></code>

			<!-- 区域表对应的数据源-->
			<ui:dataset datasetId="regionInfo" readOnly="true">
				<ui:field field="regionId" label="区域标识" visible="false" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="上级区域标识" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="上级区域名称" visible="false"></ui:field>
				<ui:field field="regionLevel" label="区域级别" visible="false" required="true" dropDown="regionLevelSelect"></ui:field>
				<ui:field field="regionName" label="区域名称" required="true" validType="require" size="20" validMsg="请输入区域名称!"></ui:field>
				<ui:field field="regionDesc" label="区域描述"></ui:field>
				<ui:field field="regionCode" label="区域编码" required="true" validType="require" size="20" validMsg="请输入20位以内区域编码!"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="是否实际区域" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="省编码" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- 省份名称表对应的数据源-->
			<ui:dataset datasetId="provinceInfo" readOnly="true">
				<ui:field field="provId" label="省份编号" visible="false" visible="false"></ui:field>
			 	<ui:field field="prvCode" label="省份代码" required="true" validType="require" size="5" validMsg="请输入5位以内的省份代码!"></ui:field>
				<ui:field field="prvName" label="省份名称" required="true" validType="require" size="20" validMsg="请输入省份名称!"></ui:field>
				<ui:field field="prvDesc" label="描述"></ui:field>
				<ui:field field="prvFlag" label="本省标志" required="true" validType="require" validMsg="请选择本省标志!" dropDown="prvFlagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="是否实际区域" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="省编码" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- 本地网表对应的数据源-->
			<ui:dataset datasetId="lanInfo" readOnly="true">
				<ui:field field="provId" label="所属省份" visible="false"></ui:field>
				<ui:field field="provName" label="所属省份" required="true"></ui:field>
				<ui:field field="lanId" label="本地网编号" visible="false"></ui:field>
				<ui:field field="lanCode" label="本地网编码" required="true" validType="require" size="6" validMsg="请输入6位以内本地网编码!"></ui:field>
				<ui:field field="lanName" label="本地网名称" required="true" validType="require" size="20" validMsg="请输入本地网名称!"></ui:field>
				<ui:field field="lanDesc" label="本地网描述"></ui:field>
				<ui:field field="areaCode" label="电话区号" required="true" size="4" validType="number" validMsg="请输入数字类型的电话区号!"></ui:field>
				<ui:field field="flag" label="本地标志" required="true" validType="require" validMsg="请选择本地标识!" dropDown="flagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="是否实际区域" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="省编码" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- 营业区表对应的数据源-->
			<ui:dataset datasetId="businessInfo" readOnly="true">
				<ui:field field="lanId" label="所属本地网ID" visible="false"></ui:field>
				<ui:field field="lanName" label="所属本地网" required="true"></ui:field>
				<ui:field field="businessId" label="营业区编号" visible="false"></ui:field>
				<ui:field field="businessCode" label="营业区编码" required="true" validType="require" validMsg="请输入营业区编码!"></ui:field>
				<ui:field field="businessName" label="营业区名称" required="true" validType="require" size="20" validMsg="请输入营业区名称!"></ui:field>
				<ui:field field="businessDesc" label="营业区描述"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="isActualRegion" label="是否实际区域" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="省编码" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<!-- 区域表对应的数据源(局向级别的区域专用的数据集)-->
			<ui:dataset datasetId="exchangeInfo" readOnly="true">
				<ui:field field="regionId" label="局向标识" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="上级区域标识" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="所属营业区" required="true"></ui:field>
				<ui:field field="configId" label="唯一标识" visible="false"></ui:field>
				<ui:field field="regionLevel" label="区域级别" visible="false"></ui:field>
				<ui:field field="regionName" label="交换局名称" required="true" size="20" validType="require" validMsg="请输入交换局名称!"></ui:field>
				<ui:field field="regionDesc" label="交换局描述"></ui:field>
				<ui:field field="regionCode" label="交换局编码" required="true" size="8" validType="require" validMsg="请输入交换局编码!"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT" visible="false"></ui:field>				
				<ui:field field="isActualRegion" label="是否实际区域" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="省编码" blankValue="true"  visible="false"></ui:field>				
			</ui:dataset>
			<ui:dataset datasetId="accNbrList" readOnly="true" loadDataAction="RegionService" loadDataActionMethod="getAccNbrNodesByExchRegionId" staticDataSource="true">
				<ui:field field="exchName" label="交换局名称" visible="false"></ui:field>
				<ui:field field="accNbrBegin" label="起始号码" visible="true"></ui:field>
				<ui:field field="accNbrEnd" label="终止号码"></ui:field>
				<ui:field field="regionId" label="区域标识" visible="false"></ui:field>
				<ui:field field="exchCode" label="局向代码" visible="false"></ui:field>
				<ui:field field="exchId" label="交换局标识" visible="false"></ui:field>
				<ui:field field="state" label="状态" attrCode="COMM_RECORD_STATE"></ui:field>
				<ui:field field="effDate" label="生效时间" visible="true"></ui:field>
				<ui:field field="expDate" label="失效时间" visible="true"></ui:field>
				<ui:field field="areaId" label="营业区标识" visible="false"></ui:field>
				<ui:field field="prodFamilyId" label="产品家族标识" visible="false"></ui:field>
				<ui:field field="prodFamilyName" label="产品家族名称"></ui:field>
				<ui:field field="isActualRegion" label="是否实际区域" blankValue="true" dropDown="ACTUALREG_FLAG"></ui:field>				
				<ui:field field="provinceCode" label="省编码" blankValue="true"  visible="false"></ui:field>				
		</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="regionLevelSelect" attrCode="BILL_REGION_LEVEL" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="prvFlagSelect" attrCode="PROVINCE_FLAG" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="flagSelect" attrCode="LAN_FLAG" staticDataSource="false"></ui:dropdown>
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="计费区域管理">
						<ui:content>
							<ui:button id="addRootRegion">新建根区域</ui:button>
							<ui:button id="addSubRegion">新建子区域</ui:button>
							<ui:button id="editRegion">编辑</ui:button>
							<ui:button id="deleteRegion">删除</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="regionTreeView" class="treelist" onItemClick="clickRegion()" showImage="false" showBorder="false" contBorder="true" showImage=false showHead=true>
						<ZTESOFT:columns>
							<ZTESOFT:column width="55%" display="true" displayText="区域名称" propertyName="regionName" />
							<ZTESOFT:column width="10%" display="true" displayText="区域编码" propertyName="regionCode" />
							<ZTESOFT:column width="35%" display="true" displayText="区域描述" propertyName="regionDesc" />
							<ZTESOFT:column width="" display="false" displayText="ngnFlag" propertyName="ngnFlag" />							
							<ZTESOFT:column width="" display="false" displayText="区域标识" propertyName="regionId" />
							<ZTESOFT:column width="" display="false" displayText="上级区域标识" propertyName="parentRegionId" />
							<!-- ZTESOFT:column width="" display="false" displayText="唯一标识" propertyName="configId" /-->
							<ZTESOFT:column width="" display="false" displayText="区域级别" propertyName="regionLevel" />
							<ZTESOFT:column width="" display="false" displayText="是否实际区域" propertyName="isActualRegion" />
							<ZTESOFT:column width="" display="false" displayText="省编码" propertyName="provinceCode" />

						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height:260px;">
					<div id="groupPannel" style="display:block">
						<!-- 集团公司详细信息面板-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="集团信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="regionInfo" id="regionInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="provincePannel" style="display:none">
						<!-- 省公司详细信息面板-->
						<ui:tabpane id="pane2">
							<ui:tabpage desc="省公司信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="provinceInfo" id="provinceInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="lanPannel" style="display:none">
						<!-- 本地网详细信息面板-->
						<ui:tabpane id="pane3">
							<ui:tabpage desc="本地网信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="lanInfo" id="lanInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="businessPannel" style="display:none">
						<!-- 营业区详细信息面板-->
						<ui:tabpane id="pane4">
							<ui:tabpage desc="营业区信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="businessInfo" id="businessInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="exchangePannel" style="display:none;">
						<!-- 局向详细信息面板(显示名称改为"交换局" - - 2006-04-25)-->
						<ui:tabpane id="exchangePage">
							<ui:tabpage desc="交换局信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form dataset="exchangeInfo" id="exchangeInfoForm" submit="btn_commitRegion" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
							<ui:tabpage desc="号段管理">
								<!-- 号码段的增加删除编辑管理界面-->
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:table dataset="accNbrList" id="accNbrListTable"></ui:table>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="addAccNbr">添加</ui:button>
										<ui:button id="editAccNbr">编辑</ui:button>
										<ui:button id="deleteAccNbr">删除</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
