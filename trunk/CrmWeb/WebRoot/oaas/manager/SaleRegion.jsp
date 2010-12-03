<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/crm-ui.tld" prefix="ui"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<META HTTP-EQUIV="library" CONTENT="kernel,treeList,validator">
		<title></title>
		<script language="javascript" src="../../public/components/common2.js"></script>
		<ui:import library=""></ui:import>
		<script language="javascript" src="js/SaleRegion.js"></script>
	</head>
	<body>
		<div id="datasetDefine">
			<!-- 区域表对应的数据源-->
			<ui:dataset datasetId="regionInfo" readOnly="true">
				<ui:field field="regionId" label="区域标识" visible="false" visible="false"></ui:field>
				<ui:field field="parentRegionId" label="上级区域标识" visible="false"></ui:field>
				<ui:field field="parentRegionName" label="上级区域名称" visible="false"></ui:field>
				<ui:field field="configId" label="唯一标识" visible="false"></ui:field>
				<ui:field field="regionLevel" label="区域级别" required="true" validType="require" validMsg="请选择区域级别!" dropDown="regionLevelSelect"></ui:field>
				<ui:field field="regionName" label="区域名称" required="true" validType="require" validMsg="请输入区域名称!"></ui:field>
				<ui:field field="regionDesc" label="区域描述"></ui:field>
				<ui:field field="regionCode" label="区域编码" required="true" validType="require" validMsg="请输入区域编码!"></ui:field>
			</ui:dataset>
			<!-- 省份名称表对应的数据源-->
			<ui:dataset datasetId="provinceInfo" readOnly="true">
				<ui:field field="provId" label="省份编号" visible="false" visible="false"></ui:field>
				<ui:field field="prvCode" label="省份代码" required="true" validType="require" validMsg="请输入省份代码!"></ui:field>
				<ui:field field="prvName" label="省份名称" required="true" validType="require" validMsg="请输入省份名称!"></ui:field>
				<ui:field field="prvDesc" label="描述"></ui:field>
				<ui:field field="prvFlag" label="本省标志" dropDown="prvFlagSelect"></ui:field>
			</ui:dataset>
			<!-- 本地网表对应的数据源-->
			<ui:dataset datasetId="lanInfo" readOnly="true">
				<ui:field field="provId" label="所属省份" visible="false"></ui:field>
				<ui:field field="provName" label="所属省份" required="true" validType="require" validMsg="请输入所属省份!"></ui:field>
				<ui:field field="lanId" label="本地网编号" visible="false"></ui:field>
				<ui:field field="lanCode" label="本地网编码" required="true" validType="require" validMsg="请输入本地网编码!"></ui:field>
				<ui:field field="lanName" label="本地网名称" required="true" validType="require" validMsg="请输入本地网名称!"></ui:field>
				<ui:field field="areaCode" label="电话区号"  required="true" validType="number" validMsg="请输入数字类型的电话区号!"></ui:field>
				<ui:field field="lanDesc" label="本地网描述"></ui:field>
				<ui:field field="flag" label="本地标志" required="true" validType="require" validMsg="请选择本地标识!" dropDown="flagSelect"></ui:field>
			</ui:dataset>
			<!-- 营销片区信息表对应的数据源-->
			<ui:dataset datasetId="saleAreaInfo" readOnly="true">
				<ui:field field="areaId" label="营销区域标识" visible="false"></ui:field>
				<ui:field field="lanId" label="本地网编号" visible="false"></ui:field>
				<ui:field field="lanName" label="所属本地网" required="true" validType="require" validMsg="请输入所属本地网!"></ui:field>
				<ui:field field="areaName" label="营销区域名称"  required="true" validType="require" validMsg="请输入营销区域名称!"></ui:field>
				<ui:field field="areaCode" label="营销区域编码"  required="true" validType="require" validMsg="请输入营销区域编码!"></ui:field>
				<ui:field field="areaDesc" label="营销区域描述"></ui:field>
				<ui:field field="validFlag" label="有效标志" required="true" validType="require" validMsg="请选择有效标志!" dropDown="validFlagSelect"></ui:field>
				<ui:field field="remark" label="备注"></ui:field>
			</ui:dataset>
			<!-- 营销社区表对应的数据源-->
			<ui:dataset datasetId="saleCommInfo" readOnly="true">
				<ui:field field="commId" label="社区序号" visible="false"></ui:field>
				<ui:field field="commName" label="社区名称"  required="true" validType="require" validMsg="请输入社区名称!"></ui:field>
				<ui:field field="commCode" label="社区编码"  required="true" validType="require" validMsg="请输入社区编码!"></ui:field>
				<ui:field field="commDesc" label="社区描述"></ui:field>
				<ui:field field="areaId" label="归属营销区域编号" visible="false"></ui:field>
				<ui:field field="areaName" label="归属营销区域" required="true"></ui:field>
				<ui:field field="manager" label="社区经理"  required="true" validType="number" validMsg="请输入社区经理!"></ui:field>
				<ui:field field="validFlag" label="有效标志" required="true" validType="require" validMsg="请选择有效标志!" dropDown="validFlagSelect"></ui:field>
				<ui:field field="remark" label="备注"></ui:field>
			</ui:dataset>
		</div>
		<div id="dropdownDefine">
			<!-- xml id="__regionLevelSelect">
			<items>
			<item label="集团" value="97A" />
			<item label="省公司" value="97B" />
			<item label="本地网" value="97D" />
			<item label="营销片区" value="99D" />
			<item label="营销社区" value="99E" />
			</items>
			</xml>
			<code id="regionLevelSelect" staticDataSource="true"></code-->
			<ui:dropdown id="regionLevelSelect" attrCode="SALE_REGION_LEVEL" staticDataSource="false"></ui:dropdown>

			<!-- xml id="__prvFlagSelect">
			<items>
			<item label="本省" value="0" />
			<item label="非本省" value="1" />
			</items>
			</xml>
			<code id="prvFlagSelect"></code-->
			<ui:dropdown id="prvFlagSelect" attrCode="PROVINCE_FLAG" staticDataSource="false"></ui:dropdown>

			<!-- xml id="__flagSelect">
			<items>
			<item label="本地编码" value="0" />
			<item label="外地编码" value="1" />
			</items>
			</xml>
			<code id="flagSelect"></code-->
			<ui:dropdown id="flagSelect" attrCode="LAN_FLAG" staticDataSource="false"></ui:dropdown>

			<!-- xml id="__validFlagSelect">
			<items>
			<item label="有效" value="0" />
			<item label="无效" value="1" />
			</items>
			</xml>
			<code id="validFlagSelect"></code-->
			<ui:dropdown id="validFlagSelect" attrCode="VALID_FLAG" staticDataSource="false"></ui:dropdown>
			
		</div>
		<div id="layoutDefine">
			<ui:layout type="border">
				<ui:pane position="top">
					<ui:bar type="search" desc="营销区域管理">
						<ui:content>
							<ui:button id="addRootRegion">新建根区域</ui:button>
							<ui:button id="addSubRegion">新建子区域</ui:button>
							<ui:button id="editRegion">编辑</ui:button>
							<ui:button id="deleteRegion">删除</ui:button>
						</ui:content>
					</ui:bar>
				</ui:pane>
				<ui:pane position="center">
					<ZTESOFT:treelist id="regionTreeView" class="treelist" onItemClick="clickRegion()" showImage="false" showBorder="false" contBorder="false" showImage=false width="100%" height="315" showHead=true>
						<ZTESOFT:columns>
							<ZTESOFT:column width="25%" display="true" displayText="区域名称" propertyName="regionName" />
							<ZTESOFT:column width="25%" display="true" displayText="区域标识" propertyName="regionId" />
							<ZTESOFT:column width="25%" display="true" displayText="区域编码" propertyName="regionCode" />
							<ZTESOFT:column width="25%" display="true" displayText="区域描述" propertyName="regionDesc" />

							<ZTESOFT:column width="" display="false" displayText="上级区域标识" propertyName="parentRegionId" />
							<ZTESOFT:column width="" display="false" displayText="唯一标识" propertyName="configId" />
							<ZTESOFT:column width="" display="false" displayText="区域级别" propertyName="regionLevel" />

							<ZTESOFT:column width="" display="false" displayText="下载标志" propertyName="downloadFlag" />
						</ZTESOFT:columns>
					</ZTESOFT:treelist>
				</ui:pane>
				<ui:pane position="bottom" style="height: 250px">
					<div id="groupPannel" style="display:block">
						<ui:bar type="pageTitle" desc="集团信息"></ui:bar>
						<ui:form dataset="regionInfo" id="regionInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">确定</ui:button>
							<ui:button id="btn_cancel">取消</ui:button>
						</div>
					</div>

					<div id="provincePannel" style="display:none">
						<!-- 省公司详细信息面板-->
						<ui:bar type="pageTitle" desc="省公司信息">
						</ui:bar>
						<ui:form dataset="provinceInfo" id="provinceInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">确定</ui:button>
							<ui:button id="btn_cancel">取消</ui:button>
						</div>
					</div>
					<div id="lanPannel" style="display:none">
						<!-- 本地网详细信息面板-->
						<ui:bar type="pageTitle" desc="本地网信息"></ui:bar>
						<ui:form dataset="lanInfo" id="lanInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">确定</ui:button>
							<ui:button id="btn_cancel">取消</ui:button>
						</div>
					</div>
					<div id="saleAreaPannel" style="display:none">
						<ui:bar type="pageTitle" desc="营销区域信息"></ui:bar>
						<ui:form dataset="saleAreaInfo" id="saleAreaInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">确定</ui:button>
							<ui:button id="btn_cancel">取消</ui:button>
						</div>
					</div>
					<div id="saleCommPannel" style="display:none">
						<ui:bar type="pageTitle" desc="营销社区信息"></ui:bar>
						<ui:form dataset="saleCommInfo" id="saleCommInfoForm" labelLayout="15%" inputLayout="35%"></ui:form>
						<div>
							<ui:button id="btn_commitRegion">确定</ui:button>
							<ui:button id="btn_cancel">取消</ui:button>
						</div>
					</div>
				</ui:pane>
			</ui:layout>
		</div>
	</body>
</html>
