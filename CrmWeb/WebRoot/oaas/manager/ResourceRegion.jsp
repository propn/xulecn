<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/ResourceRegion.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- 区域表对应的数据源-->
			<ui:dataset datasetId="regionInfo" readOnly="true">
				<ui:field field="regionId" label="区域标识" visible="false" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="上级区域标识" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="上级区域名称" visible="false"></ui:field>
				<ui:field field="configId" label="唯一标识" visible="false"></ui:field>
				<ui:field field="regionLevel" label="区域级别" visible="false" required="true" dropDown="regionLevelSelect"></ui:field>
				<ui:field field="regionName" label="区域名称" required="true" validType="require" size="70" validMsg="请输入区域名称!"></ui:field>
				<ui:field field="regionDesc" label="区域描述"></ui:field>
				<ui:field field="regionCode" label="区域编码" required="true" size="24" validMsg="请输入24位以内区域编码!"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>
				<ui:field field="virtualDealFlag" label="虚拟标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>
				<ui:field field="partyId" label="共建局ID" visible="false"></ui:field>
				<ui:field field="partyName" label="共建局" popup="true" keyField="partyId"></ui:field>
				<ui:field field="countryType" label="城乡属性" blankValue="true" attrCode="REPORT_COUNTY_TYPE"></ui:field>
			</ui:dataset>
			<!-- 省份名称表对应的数据源-->
			<ui:dataset datasetId="provinceInfo" readOnly="true">
				<ui:field field="provId" label="省份编号" visible="false" visible="false"></ui:field>
				<ui:field field="prvCode" label="省份代码" required="true" validType="require" size="20" validMsg="请输入5位以内省份代码!"></ui:field>
				<ui:field field="prvName" label="省份名称" required="true" validType="require"  size="20" validMsg="请输入省份名称!"></ui:field>
				<ui:field field="prvDesc" label="描述"></ui:field>
				<ui:field field="prvFlag" label="本省标志" required="true" validType="require" validMsg="请选择本省标志!" dropDown="prvFlagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>
				<ui:field field="partyId" label="共建局ID" visible="false"></ui:field>
				<ui:field field="partyName" label="共建局" popup="true" keyField="partyId"></ui:field>
			</ui:dataset>
			<!-- 本地网表对应的数据源-->
			<ui:dataset datasetId="lanInfo" readOnly="true">
				<ui:field field="provId" label="所属省份" visible="false"></ui:field>
				<ui:field field="provName" label="所属省份" required="true"></ui:field>
				<ui:field field="lanId" label="本地网编号" visible="false"></ui:field>
				<ui:field field="lanCode" label="本地网编码" required="true" validType="require"  size="6" validMsg="请输入6位以内的本地网编码!"></ui:field>
				<ui:field field="lanName" label="本地网名称" required="true" validType="require"  size="20" validMsg="请输入本地网名称!"></ui:field>
				<ui:field field="lanDesc" label="本地网描述"></ui:field>
				<ui:field field="areaCode" label="电话区号" required="true" validType="number" validMsg="请输入数字类型的电话区号!"></ui:field>
				<ui:field field="flag" label="本地标志" required="true" validType="require" validMsg="请选择本地标志!" dropDown="flagSelect"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT" ></ui:field>
				<ui:field field="partyId" label="共建局ID" visible="false"></ui:field>
				<ui:field field="partyName" label="共建局" popup="true" keyField="partyId"></ui:field>
			</ui:dataset>
			<!-- 营业区表对应的数据源-->
			<ui:dataset datasetId="businessInfo" readOnly="true">
				<ui:field field="lanId" label="所属本地网ID" visible="false"></ui:field>
				<ui:field field="lanName" label="所属本地网" required="true"></ui:field>
				<ui:field field="businessId" label="营业区编号" visible="false"></ui:field>
				<ui:field field="businessCode" label="营业区编码" required="true" validType="require" size="3" validMsg="请输入3位以内的营业区编码!"></ui:field>
				<ui:field field="businessName" label="营业区名称" required="true" validType="require" size="20" validMsg="请输入营业区名称!"></ui:field>
				<ui:field field="businessDesc" label="营业区描述"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT"></ui:field>				
				<ui:field field="partyId" label="共建局ID" visible="false"></ui:field>
				<ui:field field="partyName" label="共建局" popup="true" keyField="partyId"></ui:field>
			</ui:dataset>			
			<!-- 辖区信息表对应的数据源-->
			<ui:dataset datasetId="ppdomInfo" readOnly="true">
				<ui:field field="lanId" label="所属本地网ID" visible="false"></ui:field>
				<ui:field field="lanName" label="所属本地网" required="true"></ui:field>
				<ui:field field="ppdomId" label="辖区编号" visible="false"></ui:field>
				<ui:field field="ppdomCode" label="辖区编码" required="true" validType="require" size="6" validMsg="请输入辖区编码!"></ui:field>
				<ui:field field="ppdomName" label="辖区名称" required="true" validType="require" size="6" validMsg="请输入辖区名称!"></ui:field>
				<ui:field field="ppdomDesc" label="辖区描述"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT" required="true"></ui:field>
			</ui:dataset>
			<!-- 资源管理局信息表对应的数据源-->
			<ui:dataset datasetId="exchInfo" readOnly="true">
				<ui:field field="lanId" label="所属本地网ID" visible="false"></ui:field>
				<ui:field field="lanName" label="所属本地网" required="true" validType="require" size="6"></ui:field>
				<ui:field field="ppdomId" label="所属辖区ID" visible="false"></ui:field>
				<ui:field field="ppdomName" label="所属辖区" required="true" validType="require" size="6"></ui:field>
				<ui:field field="exchId" label="管理局编号" visible="false"></ui:field>
				<ui:field field="exchCode" label="管理局编码" required="true" validType="require" size="6" validMsg="请输入管理局编码!"></ui:field>
				<ui:field field="exchDesc" label="管理局描述"></ui:field>
				<ui:field field="exchType" label="管理局类型" required="true" validType="require" validMsg="请输入管理局类型!"></ui:field>
				<ui:field field="exchName" label="管理局名称" required="true" validType="require" validMsg="请输入管理局名称!"></ui:field>
				<ui:field field="superId" label="上级区域编号" visible="false"></ui:field>
				<ui:field field="comments" label="备注"></ui:field>
				<ui:field field="ngnFlag" label="NGN标志" blankValue="true" attrCode="BOOLEAN_SELECT" required="true"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<ui:dropdown id="regionLevelSelect" attrCode="RESOURCE_REGION_LEVEL" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="prvFlagSelect" attrCode="PROVINCE_FLAG" staticDataSource="false"></ui:dropdown>
			<ui:dropdown id="flagSelect" attrCode="LAN_FLAG" staticDataSource="false"></ui:dropdown>
		</div>

		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="资源区域管理">
						<ui:content>
							<ui:button id="addRootRegion">新建根节点</ui:button>
							<ui:button id="addSubRegion">新建子区域</ui:button>
							<ui:button id="editRegion">编辑</ui:button>
							<ui:button id="deleteRegion">删除</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="regionTreeView" class="treelist" onItemClick="clickRegion()" showImage="false" showBorder="false" contBorder="false" showImage="false" showHead="true">
						<ZTESOFT:columns>
							<ZTESOFT:column width="55%" display="true" displayText="区域名称" propertyName="regionName" />
							<ZTESOFT:column width="10%" display="true" displayText="区域编码" propertyName="regionCode" />
							<ZTESOFT:column width="35%" display="true" displayText="区域描述" propertyName="regionDesc" />
							<ZTESOFT:column width="" display="false" displayText="虚拟标志" propertyName="virtualDealFlag" />							
							<ZTESOFT:column width="" display="false" displayText="区域标识" propertyName="regionId" />							
							<ZTESOFT:column width="" display="false" displayText="NGN Flag" propertyName="ngnFlag" />							
							<ZTESOFT:column width="" display="false" displayText="区域标识" propertyName="regionId" />
							<ZTESOFT:column width="" display="false" displayText="上级区域标识" propertyName="parentRegionId" />
							<ZTESOFT:column width="" display="false" displayText="唯一标识" propertyName="configId" />
							<ZTESOFT:column width="" display="false" displayText="区域级别" propertyName="regionLevel" />
							<ZTESOFT:column width="" display="false" displayText="路径编码" propertyName="pathCode" />
							<ZTESOFT:column width="" display="false" displayText="路径名称" propertyName="pathName" />
							<ZTESOFT:column width="" display="false" displayText="城乡属性" propertyName="countryType" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height: 250px">
					<div id="groupPannel" style="display:block">
						<!-- 集团公司详细信息面板-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="集团信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
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
										<ui:form submit="btn_commitRegion" dataset="provinceInfo" id="provinceInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
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
										<ui:form submit="btn_commitRegion" dataset="lanInfo" id="lanInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
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
										<ui:form submit="btn_commitRegion" dataset="businessInfo" id="businessInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					
					
					<div id="ppdomPannel" style="display:none">
						<!-- 处理局详细信息面板-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="处理局信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="exchPannel" style="display:none">
						<!-- 母局详细信息面板-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc="母局信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
									</ui:pane>
								</ui:layout>
							</ui:tabpage>
						</ui:tabpane>
					</div>
					<div id="stationPannel" style="display:none">
						<!--  局站详细信息面板-->
						<ui:tabpane id="pane1">
							<ui:tabpage desc=" 局站信息">
								<ui:layout type="border">
									<ui:pane position="center">
										<ui:form submit="btn_commitRegion" dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
									</ui:pane>
									<ui:pane position="bottom">
										<ui:button id="btn_commitRegion">确定</ui:button>
										<ui:button id="btn_cancel">取消</ui:button>
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
