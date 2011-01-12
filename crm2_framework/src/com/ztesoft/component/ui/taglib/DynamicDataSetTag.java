package com.ztesoft.component.ui.taglib;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

import com.ztesoft.crm.business.views.dynamic.DataSet;

public class DynamicDataSetTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DatasetTag.class);

	private String datasetId;

	private String scope;

	private String stype;

	private String type;

	private String readOnly;

	private String editable;

	private String autoLoadPage;

	private String pageSize;

	private String pageIndex;

	private String pageCount;

	private String recordCount;

	private String async;

	private String loadDataAction;

	private String loadDataActionMethod;

	private String _queryType;

	private String paginate;

	private String staticDataSource;

	private String xmlFormat;

	private String loadAsNeeded;

	private String masterDataset;

	private String masterKeyFields;

	private String detailKeyFields;

	private String needFetchDBField;

	private String dbObject;

	private String excel;

	private String maskControl;

	private DataSet dataset;

	private String dynaControlId;

	private static String scopeRequest = "request";

	public int getTagScope() {

		if (scopeRequest.equals(scope))
			return PageContext.REQUEST_SCOPE;

		return PageContext.PAGE_SCOPE;
	}

	public String getScope() {
		return scope;
	}

	public int doStartTag() throws JspException {

		this.stype = request().getParameter("stype");

		// 中文，编码转换
		String viewCnField = request().getParameter("viewCnField") == null ? "" : request().getParameter("viewCnField");
		;
		try {
			viewCnField = new String(viewCnField.getBytes("iso-8859-1"), "gbk");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		String viewEnField = request().getParameter("viewEnField") == null ? "" : request().getParameter("viewEnField");

		this.dataset = new DataSet(this.stype, this.getDatasetId(), this.getType(), this.getReadOnly(), this
				.getEditable(), this.getAutoLoadPage(), this.getPageSize(), this.getPageIndex(), this.getPageCount(),
				this.getRecordCount(), this.getAsync(), this.getLoadDataAction(), this.getLoadDataActionMethod(), this
						.get_queryType(), this.getPaginate(), this.getStaticDataSource(), this.getXmlFormat(), this
						.getLoadAsNeeded(), this.getMasterDataset(), this.getMasterKeyFields(), this
						.getDetailKeyFields(), viewCnField, viewEnField);

		return super.doStartTag();
	}

	public int doEndTag() throws JspException {

		try {
			// String result = this.dataset.getScript(pageContext);

			String result = this.dataset.toString(pageContext);
			getBodyContent().getEnclosingWriter().println(result);

			pageContext.setAttribute(dataset.getDatasetId(), dataset, this.getTagScope());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return super.doEndTag();
	}

	public static String getScopeRequest() {
		return scopeRequest;
	}

	public static void setScopeRequest(String scopeRequest) {
		DynamicDataSetTag.scopeRequest = scopeRequest;
	}

	/**
	 * @return Returns the dynaControlId.
	 */
	public String getDynaControlId() {
		return dynaControlId;
	}

	/**
	 * @param dynaControlId
	 *            The dynaControlId to set.
	 */
	public void setDynaControlId(String dynaControlId) {
		this.dynaControlId = dynaControlId;
	}

	public String getNeedFetchDBField() {
		return (needFetchDBField != null ? needFetchDBField : "false");
	}

	public void setNeedFetchDBField(String needFetchDBField) {
		this.needFetchDBField = needFetchDBField;
	}

	public String getDbObject() {
		return dbObject;
	}

	public void setDbObject(String object) {
		dbObject = object;
	}

	public String get_queryType() {
		return (_queryType != null ? _queryType : "dataset");
	}

	public void set_queryType(String type) {
		_queryType = type;
	}

	public String getAsync() {
		return (async != null ? async : "true");
	}

	public void setAsync(String async) {
		this.async = async;
	}

	public String getAutoLoadPage() {
		return (autoLoadPage != null ? autoLoadPage : "false");
	}

	public void setAutoLoadPage(String autoLoadPage) {
		this.autoLoadPage = autoLoadPage;
	}

	public String getDetailKeyFields() {
		return (detailKeyFields != null ? detailKeyFields : "");
	}

	public void setDetailKeyFields(String detailKeyFields) {
		this.detailKeyFields = detailKeyFields;
	}

	public String getEditable() {
		return (editable != null ? editable : "false");
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getLoadAsNeeded() {
		return (loadAsNeeded != null ? loadAsNeeded : "false");
	}

	public void setLoadAsNeeded(String loadAsNeeded) {
		this.loadAsNeeded = loadAsNeeded;
	}

	public String getLoadDataAction() {
		return (loadDataAction != null ? loadDataAction : "");
	}

	public void setLoadDataAction(String loadDataAction) {
		this.loadDataAction = loadDataAction;
	}

	public String getLoadDataActionMethod() {
		return (loadDataActionMethod != null ? loadDataActionMethod : "");
	}

	public void setLoadDataActionMethod(String loadDataActionMethod) {
		this.loadDataActionMethod = loadDataActionMethod;
	}

	public String getMasterDataset() {
		return (masterDataset != null ? masterDataset : "");
	}

	public void setMasterDataset(String masterDataset) {
		this.masterDataset = masterDataset;
	}

	public String getMasterKeyFields() {
		return (masterKeyFields != null ? masterKeyFields : "");
	}

	public void setMasterKeyFields(String masterKeyFields) {
		this.masterKeyFields = masterKeyFields;
	}

	public String getPageCount() {
		return (pageCount != null ? pageCount : "1");
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getPageIndex() {
		return (pageIndex != null ? pageIndex : "1");
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return (pageSize != null ? pageSize : "30");
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPaginate() {
		return (paginate != null ? paginate : "false");
	}

	public void setPaginate(String paginate) {
		this.paginate = paginate;
	}

	public String getReadOnly() {
		return (readOnly != null ? readOnly : "false");
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getRecordCount() {
		return (recordCount != null ? recordCount : "0");
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public String getStaticDataSource() {
		return (staticDataSource != null ? staticDataSource : "true");
	}

	public void setStaticDataSource(String staticDataSource) {
		this.staticDataSource = staticDataSource;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getType() {
		return (type != null ? type : "reference");
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getXmlFormat() {
		return (xmlFormat != null ? xmlFormat : "records");
	}

	public void setXmlFormat(String xmlFormat) {
		this.xmlFormat = xmlFormat;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	private HttpServletRequest request() {
		return (HttpServletRequest) super.pageContext.getRequest();
	}

	public void release() {

		super.release();

		this.datasetId = null;

		this.scope = null;
		this.stype = null;
		this.datasetId = null;
		this.type = null;
		this.readOnly = null;
		this.editable = null;
		this.autoLoadPage = null;
		this.pageSize = null;

		this.pageIndex = null;
		this.pageCount = null;
		this.recordCount = null;
		this.async = null;
		this.loadDataAction = null;

		this.loadDataActionMethod = null;
		this._queryType = null;
		this.paginate = null;
		this.staticDataSource = null;
		this.xmlFormat = null;

		this.loadAsNeeded = null;
		this.masterDataset = null;
		this.masterKeyFields = null;
		this.detailKeyFields = null;

		this.dataset = null;

		this.needFetchDBField = null;
		this.dbObject = null;

		this.excel = null;
		this.maskControl = null;
		this.scope = null;
	}

	public void setField(Object field) {

		// 此处用覆盖的方法
		if (dataset != null) {
			ArrayList fields = dataset.getFields();
			if (fields == null) {
				fields = new ArrayList();
			}
			fields.add(0, field);

		}
		if (field != null && field instanceof FieldVo) {
			((FieldVo) field).setDataset(dataset);
		}
	}

}
