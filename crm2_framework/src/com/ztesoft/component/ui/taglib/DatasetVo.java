package com.ztesoft.component.ui.taglib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import net.buffalo.request.RequestContext;
import net.buffalo.request.RequestContextUtil;

import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.staticdata.StaticAttrCache;

public class DatasetVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6352989327662113420L;

	private String datasetId;

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

	private ArrayList fields;

	private ArrayList parameters;

	private String visibleDBFields;

	private String[] dbFields;

	private String maskControl;

	private long timeLastModifyed = 0;

	private String dbObject;

	/**
	 * @return Returns the dbObject.
	 */
	public String getDbObject() {
		return dbObject;
	}

	/**
	 * @param dbObject
	 *            The dbObject to set.
	 */
	public void setDbObject(String dbObject) {
		this.dbObject = dbObject;
	}

	public String getMaskControl() {
		return maskControl;
	}

	public void setMaskControl(String maskControl) {
		this.maskControl = maskControl;
	}

	public String[] getDbFields() {
		if (this.getVisibleDBFields() != null) {
			dbFields = this.getVisibleDBFields().split("/");
		}
		return dbFields;
	}

	public void setDbFields(String[] dbFields) {
		this.dbFields = dbFields;
	}

	public boolean isDBFieldExist(String dbField) {
		boolean result = false;

		if (dbField != null) {
			String[] dbFields = this.getDbFields();

			if (dbFields != null) {
				for (int i = 0; i < dbFields.length; i++) {
					if (dbField.equalsIgnoreCase(dbFields[i])) {
						result = true;
						break;
					}
				}
			}
		}

		return result;
	}

	public DatasetVo() {
	}

	public DatasetVo(String datasetId, String type, String readOnly, String editable, String autoLoadPage,
			String pageSize, String pageIndex, String pageCount, String recordCount, String async,
			String loadDataAction, String loadDataActionMethod, String _queryType, String paginate,
			String staticDataSource, String xmlFormat, String loadAsNeeded, String masterDataset,
			String masterKeyFields, String detailKeyFields) {
		super();
		// TODO Auto-generated constructor stub
		this.datasetId = datasetId;
		this.type = type;
		this.readOnly = readOnly;
		this.editable = editable;
		this.autoLoadPage = autoLoadPage;
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.pageCount = pageCount;
		this.recordCount = recordCount;
		this.async = async;
		this.loadDataAction = loadDataAction;
		this.loadDataActionMethod = loadDataActionMethod;
		this._queryType = _queryType;
		this.paginate = paginate;
		this.staticDataSource = staticDataSource;
		this.xmlFormat = xmlFormat;
		this.loadAsNeeded = loadAsNeeded;
		this.masterDataset = masterDataset;
		this.masterKeyFields = masterKeyFields;
		this.detailKeyFields = detailKeyFields;
	}

	public String getVisibleDBFields() {
		return visibleDBFields;
	}

	public void setVisibleDBFields(String visibleDBFields) {
		this.visibleDBFields = visibleDBFields;
	}

	public String isDBFieldReadOnly(String dbField, String dbFieldControl) {
		String result = "false";
		if (this.getVisibleDBFields() == null) {
			// ��װҵ��ʱ��visibleDBFieldsΪ��ָ��
			result = "false";
			if ("reverse".equalsIgnoreCase(dbFieldControl)) {
				result = "true";
			}
		} else if ("ALL".equalsIgnoreCase(this.getVisibleDBFields())) {
			// ���ҵ��ʱ��ĳһ�����ȫ�����Ծ����޸�
			result = "false";
		} else if ("NONE".equalsIgnoreCase(this.getVisibleDBFields())) {
			// ���ҵ��ʱ��ĳһ���󲻿��޸ģ���ȫ�����Բ����޸ģ�
			result = "true";
		} else if ("OTHER".equalsIgnoreCase(this.getVisibleDBFields())) {
			// ���ҵ��ʱ��ĳһ���󲻿��޸ģ���ȫ�����Բ����޸ģ�
			if ("control".equalsIgnoreCase(dbFieldControl) || "reverse".equalsIgnoreCase(dbFieldControl))
				result = "true";
		} else {
			// ���ҵ��ʱ��ĳһ�����ĳЩ���Բ����޸�
			if (dbField != null) {
				if (this.isDBFieldExist(dbField)) {
					result = "false";
				} else {
					result = "true";
				}
			}
		}

		return result;
	}

	public String get_queryType() {
		return _queryType;
	}

	public void set_queryType(String type) {
		_queryType = type;
	}

	public String getAsync() {
		return async;
	}

	public void setAsync(String async) {
		this.async = async;
	}

	public String getAutoLoadPage() {
		return autoLoadPage;
	}

	public void setAutoLoadPage(String autoLoadPage) {
		this.autoLoadPage = autoLoadPage;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getDetailKeyFields() {
		return detailKeyFields;
	}

	public void setDetailKeyFields(String detailKeyFields) {
		this.detailKeyFields = detailKeyFields;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getLoadAsNeeded() {
		return loadAsNeeded;
	}

	public void setLoadAsNeeded(String loadAsNeeded) {
		this.loadAsNeeded = loadAsNeeded;
	}

	public String getLoadDataAction() {
		return loadDataAction;
	}

	public void setLoadDataAction(String loadDataAction) {
		this.loadDataAction = loadDataAction;
	}

	public String getLoadDataActionMethod() {
		return loadDataActionMethod;
	}

	public void setLoadDataActionMethod(String loadDataActionMethod) {
		this.loadDataActionMethod = loadDataActionMethod;
	}

	public String getMasterDataset() {
		return masterDataset;
	}

	public void setMasterDataset(String masterDataset) {
		this.masterDataset = masterDataset;
	}

	public String getMasterKeyFields() {
		return masterKeyFields;
	}

	public void setMasterKeyFields(String masterKeyFields) {
		this.masterKeyFields = masterKeyFields;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPaginate() {
		return paginate;
	}

	public void setPaginate(String paginate) {
		this.paginate = paginate;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	public String getStaticDataSource() {
		return staticDataSource;
	}

	public void setStaticDataSource(String staticDataSource) {
		this.staticDataSource = staticDataSource;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getXmlFormat() {
		return xmlFormat;
	}

	public void setXmlFormat(String xmlFormat) {
		this.xmlFormat = xmlFormat;
	}

	public ArrayList getFields() {
		if (fields == null) {
			fields = new ArrayList();
		}
		return fields;
	}

	public void setFields(ArrayList fields) {
		if (this.getFields() == null || this.getFields().size() == 0) {
			this.fields = fields;
		} else {
			this.fields.addAll(0,fields); // ����ⲿ��field����
		}

	}

	public ArrayList getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList parameters) {
		this.parameters = parameters;
	}

	public void setField(Object field) {
		if (fields == null) {
			fields = new ArrayList();
		}
		fields.add(field);
	}

	public FieldVo getField(String field) {
		FieldVo vo = null;
		if (field != null && !"".equalsIgnoreCase(field)) {
			for (int i = 0; i < this.getFields().size(); i++) {
				FieldVo temp = (FieldVo) this.getFields().get(i);
				if (field.equalsIgnoreCase(temp.getField())) {
					vo = temp;
					break;
				}
			}
		}
		return vo;
	}

	public void setParameter(Object parameter) {
		if (parameters == null) {
			parameters = new ArrayList();
		}
		parameters.add(parameter);
	}

	private static String capitalizePropertyName(String s) {
		if (s.length() == 0) {
			return s;
		}
		char chars[] = s.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	public void toExcel2(PageContext pageContext) {
		String strFileName = "EXCEL�ļ�.xls";
		pageContext.getResponse().reset();
		pageContext.getResponse().setContentType("application/vnd.ms-excel;charset=GB2312");

		try {
			// ����HttpServletResponse����
			((HttpServletResponse) pageContext.getResponse()).setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(strFileName.getBytes("GBK"), "ISO-8859-1") + "\"");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		ArrayList fieldVos = this.getFields();

		if (fieldVos != null) {
			// ��PageContext�л�ȡHttpServletResponse
			HttpServletResponse response = null;
			response = (HttpServletResponse) (pageContext.getResponse());

			ExportToExcelFile excleFile = null;
			try {
				excleFile = new ExportToExcelFile(response.getOutputStream(), "Excel");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			/* ��ͷ */
			int col = 0;
			for (int i = 0; i < fieldVos.size(); i++) {
				FieldVo fieldVo = ((FieldVo) fieldVos.get(i));
				if ("false".equalsIgnoreCase(fieldVo.getVisible()))
					continue;
				if (fieldVo.getLabel() != null && !fieldVo.getLabel().equalsIgnoreCase("")) {
					excleFile.createExcelContent(fieldVo.getLabel(), col, 0);
					col++;
				}
			}

			/* ������ */
			try {
				RequestContext context = null;
				HttpServletRequest request = (HttpServletRequest) (pageContext.getRequest());
				if (response == null) {
					response = (HttpServletResponse) (pageContext.getResponse());
				}

				/*
				 * try { context = new RequestContext(null, request, response); } catch (IOException e1) {
				 * e1.printStackTrace(); }
				 */
				initContext(pageContext, context, request, response);

				Object service = Class.forName(this.getLoadDataAction()).newInstance();

				/*
				 * if (service instanceof BuffaloService) { ((BuffaloService) service).init(context); }
				 */

				try {
					boolean paginate = ("true".equalsIgnoreCase(this.getPaginate()));
					int paramSize = 0;
					int paramClassSize = 0;
					if (this.getParameters() != null)
						paramSize = this.getParameters().size();
					paramClassSize = paramSize;
					if (paginate) {
						paramClassSize = paramSize + 2;
					}
					Class[] paramClass = null;
					if (paramClassSize > 0)
						paramClass = new Class[paramClassSize];

					for (int i = 0; i < paramSize; i++) {
						ParameterVo parameterVo = (ParameterVo) this.getParameters().get(i);
						if ("int".equalsIgnoreCase(parameterVo.getType()))
							paramClass[i] = Integer.TYPE;
						else
							paramClass[i] = String.class;
					}
					if (paginate) {
						paramClass[paramClassSize - 2] = Integer.TYPE;
						paramClass[paramClassSize - 1] = Integer.TYPE;
					}
					Method method = service.getClass().getMethod(this.getLoadDataActionMethod(), paramClass);

					try {
						Object[] paramObject = null;
						if (paramClassSize > 0) {
							paramObject = new Object[paramClassSize];
						}

						for (int i = 0; i < paramSize; i++) {
							ParameterVo parameterVo = (ParameterVo) this.getParameters().get(i);
							String paramValue = request.getParameter(parameterVo.getParameterId());

							if (paramValue == null)
								paramValue = "";
							// System.out.println("paramValue:"+paramValue);
							if ("int".equalsIgnoreCase(parameterVo.getType()))
								paramObject[i] = new Integer(paramValue);
							else
								paramObject[i] = paramValue;
						}
						if (paginate) {
							paramObject[paramClassSize - 2] = new Integer("1");
							paramObject[paramClassSize - 1] = new Integer("30000");
						}
						Object result = method.invoke(service, paramObject);

						List resultList = null;
						if (paginate) {
							resultList = ((PageModel) result).getList();
						} else {
							resultList = (List) result;
						}

						int row = 1;
						for (int i = 0; i < resultList.size(); i++) {
							Object fieldObject = resultList.get(i);
							col = 0;
							for (int j = 0; j < fieldVos.size(); j++) {
								FieldVo fieldVo = ((FieldVo) fieldVos.get(j));
								if ("false".equalsIgnoreCase(fieldVo.getVisible()))
									continue;

								Method fieldObjectMethod = fieldObject.getClass().getMethod(
										"get" + this.capitalizePropertyName(fieldVo.getField()), null);
								String fieldObjectValue = (String) fieldObjectMethod.invoke(fieldObject,
										new Object[] {});

								String attrCode = fieldVo.getAttrCode();
								if (attrCode != null && !"".equalsIgnoreCase(attrCode)) {
									try {
										fieldObjectValue = StaticAttrCache.getInstance().getAttrDesc(attrCode,
												fieldObjectValue);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								if (fieldObjectValue == null) {
									fieldObjectValue = "";
								}
								if (fieldVo.getLabel() != null && !fieldVo.getLabel().equalsIgnoreCase("")) {
									excleFile.createExcelContent(fieldObjectValue, col, row);
									col++;
								}
							}
							row++;
						}

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				if (excleFile != null) {
					excleFile.writeExcel();
					excleFile.closeExcel();
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public String toExcel(PageContext pageContext) {

		String strFileName = "EXCEL�ļ�.xls";
		pageContext.getResponse().setContentType("application/vnd.ms-excel;charset=GB2312");

		try {
			((HttpServletResponse) pageContext.getResponse()).setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(strFileName.getBytes("GBK"), "ISO-8859-1") + "\"");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		StringBuffer sbuffer = new StringBuffer();

		ArrayList fieldVos = this.getFields();

		if (fieldVos != null) {
			sbuffer.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
			sbuffer.append("xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
			sbuffer.append("xmlns=\"http://www.w3.org/TR/REC-html40\">");
			sbuffer.append("<head>");
			sbuffer.append("<meta http-equiv=Content-Type content=\"text/html; charset=gbk\">");
			sbuffer.append("</head>");
			sbuffer.append("<table border='1'>");

			/* ��ͷ */
			sbuffer.append("<thead>");
			sbuffer.append("<tr>");
			boolean isFirst = true;
			for (int i = 0; i < fieldVos.size(); i++) {
				FieldVo fieldVo = ((FieldVo) fieldVos.get(i));
				if ("false".equalsIgnoreCase(fieldVo.getVisible()))
					continue;
				sbuffer.append("<td>");
				sbuffer.append(fieldVo.getLabel());
				sbuffer.append("</td>");
			}
			sbuffer.append("</tr>");
			sbuffer.append("</thead>");

			/* ������ */
			sbuffer.append("<tbody>");

			try {
				RequestContext context = null;
				HttpServletRequest request = (HttpServletRequest) (pageContext.getRequest());
				try {
					request.setCharacterEncoding("GB2312");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());
				// ������ easonwu ,ע��������룬���Ϊ initContext( context , request , response);
				// try {
				// context = new RequestContext(null, request, response);
				// } catch (IOException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				initContext(pageContext, context, request, response);

				Object service = Class.forName(this.getLoadDataAction()).newInstance();

				// ������ easonwu ,ע��������� buffalo2.0ͨ��RequestContextUtil��ȡrequest��session�ȶ���
				// if (service instanceof BuffaloService) {
				// ((BuffaloService)service).init(context);
				// }

				try {
					boolean paginate = ("true".equalsIgnoreCase(this.getPaginate()));
					int paramSize = 0;
					int paramClassSize = 0;
					if (this.getParameters() != null)
						paramSize = this.getParameters().size();
					paramClassSize = paramSize;
					if (paginate) {
						paramClassSize = paramSize + 2;
					}
					Class[] paramClass = null;
					if (paramClassSize > 0)
						paramClass = new Class[paramClassSize];

					for (int i = 0; i < paramSize; i++) {
						ParameterVo parameterVo = (ParameterVo) this.getParameters().get(i);
						if ("int".equalsIgnoreCase(parameterVo.getType()))
							paramClass[i] = Integer.TYPE;
						else
							paramClass[i] = String.class;
					}
					if (paginate) {
						paramClass[paramClassSize - 2] = Integer.TYPE;
						paramClass[paramClassSize - 1] = Integer.TYPE;
					}
					Method method = service.getClass().getMethod(this.getLoadDataActionMethod(), paramClass);

					try {
						Object[] paramObject = null;
						if (paramClassSize > 0) {
							paramObject = new Object[paramClassSize];
						}

						for (int i = 0; i < paramSize; i++) {
							ParameterVo parameterVo = (ParameterVo) this.getParameters().get(i);
							String paramValue = request.getParameter(parameterVo.getParameterId());

							if (paramValue == null)
								paramValue = "";

							if ("int".equalsIgnoreCase(parameterVo.getType()))
								paramObject[i] = new Integer(paramValue);
							else
								paramObject[i] = paramValue;
						}
						if (paginate) {
							paramObject[paramClassSize - 2] = new Integer("1");
							paramObject[paramClassSize - 1] = new Integer("10000");
						}

						Object result = method.invoke(service, paramObject);

						List resultList = null;
						if (paginate) {
							resultList = ((PageModel) result).getList();
						} else {
							resultList = (List) result;
						}
						boolean isMap = false;
						for (int i = 0; i < resultList.size(); i++) {
							Object fieldObject = resultList.get(i);
							sbuffer.append("<tr>");
							for (int j = 0; j < fieldVos.size(); j++) {
								FieldVo fieldVo = ((FieldVo) fieldVos.get(j));
								if ("false".equalsIgnoreCase(fieldVo.getVisible()))
									continue;
								sbuffer.append("<td x:str>");// modify by AyaKoizumi 100901 ���ֶ�תΪ�ַ���������excel�Ŀ�ѧ������

								/*
								 * if("sequence".equalsIgnoreCase(fieldVo.getField())){ sbuffer.append(""+(i+1)); }
								 * else{
								 */
								if ("iccid".equalsIgnoreCase(fieldVo.getField())
										|| "psid".equalsIgnoreCase(fieldVo.getField())
										|| "acceptId".equalsIgnoreCase(fieldVo.getField())
										|| "orderId".equalsIgnoreCase(fieldVo.getField())) {
									sbuffer.append("&nbsp;");
								}
								String fieldObjectValue = "";
								// �жϼ�����������
								if (!isMap(fieldObject)) {
									Method fieldObjectMethod = fieldObject.getClass().getMethod(
											"get" + this.capitalizePropertyName(fieldVo.getField()), null);
									fieldObjectValue = (String) fieldObjectMethod.invoke(fieldObject, new Object[] {});
								} else {
									Map mfo = ((Map) fieldObject);
									fieldObjectValue = (String) mfo.get(fieldVo.getField());
								}
								if (fieldObjectValue == null)
									fieldObjectValue = "";// add by AyaKoizumi 100901
								String attrCode = fieldVo.getAttrCode();
								if (attrCode != null && !"".equalsIgnoreCase(attrCode)) {
									try {
										fieldObjectValue = StaticAttrCache.getInstance().getAttrDesc(attrCode,
												fieldObjectValue);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								sbuffer.append(fieldObjectValue);
								// }
								sbuffer.append("</td>");
							}
							sbuffer.append("</tr>");
						}

					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sbuffer.append("</tbody>");

			sbuffer.append("</table>");
			sbuffer.append("</html>");
		}

		return sbuffer.toString();
	}

	// add by easonwu 2010-03-16 ��֤���������Ƿ�ΪMap����
	private boolean isMap(Object obj) {
		return obj instanceof java.util.Map;
	}

	// add by easonwu 20091208 buffalo upgrade
	private void initContext(PageContext pageContext, RequestContext context, HttpServletRequest request,
			HttpServletResponse response) {
		context = RequestContext.getContext();
		RequestContextUtil.createRequestContext(pageContext.getServletContext(), request, response);
	}

	public void toExcelByFile(PageContext pageContext) {

		FileOutputStream fout = null;
		FileInputStream fin = null;
		File efile = null;
		RequestContext context = null;
		HttpServletRequest request = (HttpServletRequest) (pageContext.getRequest());
		HttpServletResponse response = (HttpServletResponse) (pageContext.getResponse());

		String pageName = parsePageName(pageContext);
		String strFileName = "./upload/" + pageName + "_" + System.currentTimeMillis() + ".xls";
		pageContext.getResponse().setContentType("application/vnd.ms-excel;charset=GB2312");

		try {
			/*
			 * ((HttpServletResponse)pageContext.getResponse()).setHeader("Content-Disposition",
			 * "attachment;filename=\"" + new String(strFileName.getBytes("GBK"), "ISO-8859-1") + "\"");
			 */
			response.setHeader("Content-Disposition", "attachment;filename=\"expData.xls\"");
			response.setDateHeader("Expires", 0);
			response.setHeader("Content-Type", "application/vnd.ms-excel;charset=GB2312");
			// response.setHeader("Content-Type", "application/force-download");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			// response.setHeader("Content-Transfer-Encoding", "binary");
			/*
			 * header("Pragma: public"); header("Expires: 0"); header("Cache-Control: must-revalidate, post-check=0,
			 * pre-check=0"); header("Content-Type: application/force-download"); header("Content-Type:
			 * application/download"); header("Content-Disposition: attachment; filename=$FileName");
			 * header("Content-Transfer-Encoding: binary"); header( "Content-Description: File Transfer");
			 * 
			 */
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		StringBuffer sbuffer1 = new StringBuffer();
		StringBuffer sbuffer2 = new StringBuffer();

		ArrayList fieldVos = this.getFields();

		if (fieldVos == null)
			return;

		try {
			efile = new File(strFileName);
			fout = new FileOutputStream(efile);

			sbuffer1.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
			sbuffer1.append("xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
			sbuffer1.append("xmlns=\"http://www.w3.org/TR/REC-html40\">");
			sbuffer1.append("<head>");
			sbuffer1.append("<meta http-equiv=Content-Type content=\"text/html; charset=gbk\">");
			sbuffer1.append("</head>");
			sbuffer1.append("<table border='1'>");

			/* ��ͷ */
			sbuffer1.append("<thead>");
			sbuffer1.append("<tr>");
			boolean isFirst = true;
			for (int i = 0; i < fieldVos.size(); i++) {
				FieldVo fieldVo = ((FieldVo) fieldVos.get(i));
				if ("false".equalsIgnoreCase(fieldVo.getVisible()))
					continue;
				sbuffer1.append("<td>");
				sbuffer1.append(fieldVo.getLabel());
				sbuffer1.append("</td>");
			}
			sbuffer1.append("</tr>");
			sbuffer1.append("</thead>");
			fout.write(sbuffer1.toString().getBytes());

			/* ������ */
			sbuffer2.append("<tbody>");

			// ������ easonwu ,ע��������룬���Ϊ initContext( context , request , response);
			// try {
			// context = new RequestContext(null, request, response);
			// } catch (IOException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			initContext(pageContext, context, request, response);

			Object service = Class.forName(this.getLoadDataAction()).newInstance();

			// ������ easonwu ,ע��������� buffalo2.0ͨ��RequestContextUtil��ȡrequest��session�ȶ���
			// if (service instanceof BuffaloService) {
			// ((BuffaloService)service).init(context);
			// }

			try {
				boolean paginate = ("true".equalsIgnoreCase(this.getPaginate()));
				int paramSize = 0;
				int paramClassSize = 0;
				if (this.getParameters() != null)
					paramSize = this.getParameters().size();
				paramClassSize = paramSize;
				if (paginate) {
					paramClassSize = paramSize + 2;
				}
				Class[] paramClass = null;
				if (paramClassSize > 0)
					paramClass = new Class[paramClassSize];

				for (int i = 0; i < paramSize; i++) {
					ParameterVo parameterVo = (ParameterVo) this.getParameters().get(i);
					if ("int".equalsIgnoreCase(parameterVo.getType()))
						paramClass[i] = Integer.TYPE;
					else
						paramClass[i] = String.class;
				}
				if (paginate) {
					paramClass[paramClassSize - 2] = Integer.TYPE;
					paramClass[paramClassSize - 1] = Integer.TYPE;
				}
				Method method = service.getClass().getMethod(this.getLoadDataActionMethod(), paramClass);

				try {
					Object[] paramObject = null;
					if (paramClassSize > 0) {
						paramObject = new Object[paramClassSize];
					}

					for (int i = 0; i < paramSize; i++) {
						ParameterVo parameterVo = (ParameterVo) this.getParameters().get(i);
						String paramValue = request.getParameter(parameterVo.getParameterId());

						if (paramValue == null)
							paramValue = "";

						if ("int".equalsIgnoreCase(parameterVo.getType()))
							paramObject[i] = new Integer(paramValue);
						else
							paramObject[i] = paramValue;
					}
					if (paginate) {
						paramObject[paramClassSize - 2] = new Integer("1");
						paramObject[paramClassSize - 1] = new Integer("100000");
					}

					Object result = method.invoke(service, paramObject);

					List resultList = null;
					if (paginate) {
						resultList = ((PageModel) result).getList();
					} else {
						resultList = (List) result;
					}

					for (int i = 0; i < resultList.size(); i++) {
						Object fieldObject = resultList.get(i);

						// sbuffer.append("<tr>");
						fout.write("<tr>".getBytes());
						for (int j = 0; j < fieldVos.size(); j++) {
							FieldVo fieldVo = ((FieldVo) fieldVos.get(j));
							if ("false".equalsIgnoreCase(fieldVo.getVisible()))
								continue;
							// sbuffer.append("<td>");
							fout.write("<td>".getBytes());

							if ("iccid".equalsIgnoreCase(fieldVo.getField())
									|| "acceptId".equalsIgnoreCase(fieldVo.getField())
									|| "orderId".equalsIgnoreCase(fieldVo.getField())) {
								// sbuffer.append("&nbsp;");
								fout.write("&nbsp;".getBytes());
							}

							Method fieldObjectMethod = fieldObject.getClass().getMethod(
									"get" + this.capitalizePropertyName(fieldVo.getField()), null);
							String fieldObjectValue = (String) fieldObjectMethod.invoke(fieldObject, new Object[] {});

							String attrCode = fieldVo.getAttrCode();
							if (attrCode != null && !"".equalsIgnoreCase(attrCode)) {
								try {
									fieldObjectValue = StaticAttrCache.getInstance().getAttrDesc(attrCode,
											fieldObjectValue);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							// sbuffer.append(fieldObjectValue);
							if (fieldObjectValue != null)
								fout.write(fieldObjectValue.getBytes());

							// sbuffer.append("</td>");
							fout.write("</td>".getBytes());
						}
						// sbuffer.append("</tr>");
						fout.write("</tr>".getBytes());
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			sbuffer2.append("</tbody>");
			sbuffer2.append("</table>");
			sbuffer2.append("</html>");
			fout.write(sbuffer2.toString().getBytes());
			fout.close();

			// put file to ServletOutputStream
			ServletOutputStream sout = response.getOutputStream();
			fin = new FileInputStream(efile);

			byte[] buf = new byte[512];
			int len = 0;
			while ((len = fin.read(buf)) > 0) {
				sout.write(buf, 0, len);
			}
			fin.close();
			sout.flush();
			sout.close();

			efile.delete();

		} catch (Exception e) {
			e.printStackTrace();

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException ie) {
					e.printStackTrace();
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException ie) {
					e.printStackTrace();
				}
			}

			/*
			 * if(efile!=null && efile.exists()){ efile.delete(); }
			 */
		}

		return;
	}

	private String parsePageName(PageContext pageContext) {
		String pageUrl = ((HttpServletRequest) pageContext.getRequest()).getRequestURL().toString();
		String pageName = pageUrl.substring(pageUrl.lastIndexOf("/") + 1, pageUrl.lastIndexOf("."));

		return pageName;
	}

	private String parseExcelFileName(PageContext pageContext) {
		String pageUrl = ((HttpServletRequest) pageContext.getRequest()).getRequestURL().toString();
		String pageName = pageUrl.substring(pageUrl.lastIndexOf("/") + 1, pageUrl.lastIndexOf("."));

		String fileName = "./upload/" + pageName + "_" + System.currentTimeMillis() + ".xls";

		return fileName;
	}

	// �漰�ļ�dataset.js
	public String toString(PageContext pageContext) throws Exception {

		StringBuffer sbuffer = new StringBuffer();

		sbuffer.append("<xml id='__" + this.getDatasetId() + "'></xml>");

		sbuffer.append("<script>");

		StringBuffer d = new StringBuffer("{");

		MyUtil.wrapFieldAttrWithCheck(d, "t", this.getType(), "reference");// type

		MyUtil.wrapFieldAttrWithCheck(d, "r", this.getReadOnly(), "false", MyUtil.NO_NEED);// readOnly

		MyUtil.wrapFieldAttrWithCheck(d, "e", this.getEditable(), "false", MyUtil.NO_NEED);// editable

		MyUtil.wrapFieldAttrWithCheck(d, "al", this.getAutoLoadPage(), "false", MyUtil.NO_NEED);// autoLoadPage

		MyUtil.wrapFieldAttrWithCheck(d, "ps", this.getPageSize(), "9999", MyUtil.NO_NEED);// pageSize

		MyUtil.wrapFieldAttrWithCheck(d, "pi", this.getPageIndex(), "1", MyUtil.NO_NEED);// pageIndex

		MyUtil.wrapFieldAttrWithCheck(d, "pc", this.getPageCount(), "1", MyUtil.NO_NEED);// pageCount

		MyUtil.wrapFieldAttrWithCheck(d, "rc", this.getRecordCount(), "0", MyUtil.NO_NEED);// recordCount

		MyUtil.wrapFieldAttrWithCheck(d, "async", this.getAsync(), "true", MyUtil.NO_NEED);// async

		MyUtil.wrapFieldAttrWithCheck(d, "la", this.getLoadDataAction(), "");// loadDataAction

		MyUtil.wrapFieldAttrWithCheck(d, "lm", this.getLoadDataActionMethod(), "");// loadDataActionMethod

		MyUtil.wrapFieldAttrWithCheck(d, "qt", this.get_queryType(), "dataset");// queryType

		MyUtil.wrapFieldAttrWithCheck(d, "p", this.getPaginate(), "false", MyUtil.NO_NEED);// paginate

		MyUtil.wrapFieldAttrWithCheck(d, "sd", this.getStaticDataSource(), "true", MyUtil.NO_NEED);// staticDataSource

		MyUtil.wrapFieldAttrWithCheck(d, "x", this.getXmlFormat(), "records");// xmlFormat

		MyUtil.wrapFieldAttrWithCheck(d, "ln", this.getLoadAsNeeded(), "false", MyUtil.NO_NEED);// loadAsNeeded

		MyUtil.wrapFieldAttrWithCheck(d, "md", this.getMasterDataset(), "");// masterDataset

		MyUtil.wrapFieldAttrWithCheck(d, "mf", this.getMasterKeyFields(), "");// masterKeyFields

		MyUtil.wrapFieldAttrWithCheck(d, "df", this.getDetailKeyFields(), "");// detailKeyFields

		MyUtil.wrapFieldAttrWithCheck(d, "mc", this.getMaskControl(), "false", MyUtil.NO_NEED);// maskControl

		d.append("id:'" + this.getDatasetId() + "'}");

		sbuffer.append(" var __t=createDataset(" + d.toString() + "); ");

		sbuffer.append(" " + this.getDatasetId() + "=__t; ");

		if (this.getFields() != null) {
			for (int i = 0; i < this.getFields().size(); i++) {
				sbuffer.append(((FieldVo) this.getFields().get(i)).toString());
			}
		}
		if (this.getParameters() != null) {
			for (int i = 0; i < this.getParameters().size(); i++) {
				sbuffer.append(((ParameterVo) this.getParameters().get(i)).toString());
			}
		}

		sbuffer.append(" fd(__t , __t.masterDataset); ");

		// sbuffer.append(" jsDebug.debug('.....'+((new Date())-start)); ");

		sbuffer.append("</script>");

		if (this.getFields() != null) {
			for (int i = 0; i < this.getFields().size(); i++) {
				FieldVo fieldVo = (FieldVo) this.getFields().get(i);
				String attrCode = fieldVo.getAttrCode();
				if (attrCode != null && !"".equalsIgnoreCase(attrCode)) {
					String parentValue = fieldVo.getParentValue(pageContext);
					if (pageContext.getRequest().getAttribute(attrCode) == null) {
						sbuffer.append(DropdownTag.getDropdownControl(pageContext, attrCode, parentValue, fieldVo
								.getBlankId(), fieldVo.getBlankValue(), fieldVo.getMaxHeight(), fieldVo
								.getConfigParams()));
						pageContext.getRequest().setAttribute(attrCode, "true");
					}
				}
			}
		}

		return sbuffer.toString();

	}
	//PPMWeb ����
	public String toExcel(PageContext pageContext,String excelName){
		
		String strFileName = "EXCEL�ļ�.xls";
		//edit by li.jiahong  --2010.7.27
		if(excelName != null && !"".equals(excelName)){
			if(excelName.indexOf(".xls") == -1){
			  strFileName = excelName + ".xls";
			}
			else{
			  strFileName = excelName;
			}
		}	
		pageContext.getResponse().setContentType("application/vnd.ms-excel;charset=GB2312");
		
		try {
			((HttpServletResponse)pageContext.getResponse()).setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(strFileName.getBytes("GBK"), "ISO-8859-1") + "\"");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		StringBuffer sbuffer = new StringBuffer();			
		
		ArrayList fieldVos = this.getFields();
		
		if(fieldVos!=null){
			sbuffer.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
			sbuffer.append("xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
			sbuffer.append("xmlns=\"http://www.w3.org/TR/REC-html40\">");
			sbuffer.append("<head>");
			sbuffer.append("<meta http-equiv=Content-Type content=\"text/html; charset=gbk\">");
			sbuffer.append("</head>");
			sbuffer.append("<table border='1'>");
			
			/*��ͷ*/
			sbuffer.append("<thead>");
			sbuffer.append("<tr>");
			boolean isFirst = true;
			for(int i=0; i<fieldVos.size(); i++){
				FieldVo fieldVo = ((FieldVo)fieldVos.get(i));
				if("false".equalsIgnoreCase(fieldVo.getVisible()))
					continue;
				sbuffer.append("<td>");
				sbuffer.append(fieldVo.getLabel());
				sbuffer.append("</td>");
			}
			sbuffer.append("</tr>");
			sbuffer.append("</thead>");						
			
			/*������*/
			sbuffer.append("<tbody>");
			
			try {
				RequestContext context = null;
				HttpServletRequest request = (HttpServletRequest)(pageContext.getRequest());
				try
				{
					request.setCharacterEncoding("GB2312");
				}
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HttpServletResponse response = (HttpServletResponse)(pageContext.getResponse());
				//������ easonwu ,ע��������룬���Ϊ initContext( context  ,  request ,  response);
//				try {
//					context = new RequestContext(null, request, response);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				initContext(pageContext ,context  ,  request ,  response);
				
				
				Object service = Class.forName(this.getLoadDataAction()).newInstance();
				
				//������ easonwu ,ע��������� buffalo2.0ͨ��RequestContextUtil��ȡrequest��session�ȶ���
//				if (service instanceof BuffaloService) {
//					((BuffaloService)service).init(context);
//				}
				
				try {					
					boolean paginate = ("true".equalsIgnoreCase(this.getPaginate()));
					int paramSize = 0;
					int paramClassSize = 0;
					if(this.getParameters()!=null)
						paramSize = this.getParameters().size();
					paramClassSize = paramSize;
					if(paginate){
						paramClassSize = paramSize+2;
					}				
					Class[] paramClass = null;
					if(paramClassSize>0)
						paramClass = new Class[paramClassSize];
						
					for(int i=0; i<paramSize; i++){
						ParameterVo parameterVo = (ParameterVo)this.getParameters().get(i);
						if("int".equalsIgnoreCase(parameterVo.getType()))
							paramClass[i] = Integer.TYPE;
						else
							paramClass[i] = String.class;							
					}				
					if(paginate){
						paramClass[paramClassSize-2] = Integer.TYPE;
						paramClass[paramClassSize-1] = Integer.TYPE;
					}
					Method method = service.getClass().getMethod(this.getLoadDataActionMethod(), paramClass);
					
					try {
						Object[] paramObject = null;
						if(paramClassSize>0){
							paramObject = new Object[paramClassSize];
						}
												
						for(int i=0; i<paramSize; i++){
							ParameterVo parameterVo = (ParameterVo)this.getParameters().get(i);
							String paramValue = request.getParameter(parameterVo.getParameterId());

							if(paramValue==null)
								paramValue = "";
 
							if("int".equalsIgnoreCase(parameterVo.getType()))
								paramObject[i] = new Integer(paramValue); 				
							else
								paramObject[i] = paramValue;
						}						
						if(paginate){
							paramObject[paramClassSize-2] = new Integer("1");
							paramObject[paramClassSize-1] = new Integer("10000");
						}
	 
						Object result = method.invoke(service, paramObject);
						
						List resultList = null;
						if(paginate){
							resultList = ((PageModel)result).getList();
						}
						else{
							resultList = (List)result;
						}
						boolean isMap = false ;
						for(int i=0; i<resultList.size(); i++){
							Object fieldObject = resultList.get(i);
							sbuffer.append("<tr>");
							for(int j=0; j<fieldVos.size(); j++){
								FieldVo fieldVo = ((FieldVo)fieldVos.get(j));
								if("false".equalsIgnoreCase(fieldVo.getVisible()))
									continue;
								sbuffer.append("<td>");
								
								/*
								if("sequence".equalsIgnoreCase(fieldVo.getField())){
									sbuffer.append(""+(i+1));									
								}
								else{
								*/
									if("iccid".equalsIgnoreCase(fieldVo.getField()) || "psid".equalsIgnoreCase(fieldVo.getField()) || "acceptId".equalsIgnoreCase(fieldVo.getField()) || "orderId".equalsIgnoreCase(fieldVo.getField())){
										sbuffer.append("&nbsp;");
									}
									String fieldObjectValue = "";
									//�жϼ�����������
									if(!isMap(fieldObject)){
										Method fieldObjectMethod = fieldObject.getClass().getMethod("get"+this.capitalizePropertyName(fieldVo.getField()), null);
										fieldObjectValue = (String)fieldObjectMethod.invoke(fieldObject, new Object[]{});
									}else{
										Map mfo = ((Map)fieldObject);
										fieldObjectValue = (String) mfo.get(fieldVo.getField()) ;
									}
									
									String attrCode = fieldVo.getAttrCode();
									if(attrCode!=null && !"".equalsIgnoreCase(attrCode)){
										try {
											fieldObjectValue = StaticAttrCache.getInstance().getAttrDesc(attrCode, fieldObjectValue);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									sbuffer.append(fieldObjectValue);	
								//}
								sbuffer.append("</td>");
							}
							sbuffer.append("</tr>");					
						}
						
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sbuffer.append("</tbody>");						
		
			
			sbuffer.append("</table>");
			sbuffer.append("</html>");
		}	
		
		return sbuffer.toString();
	}
	

	public long getTimeLastModifyed() {
		return timeLastModifyed;
	}

	public void setTimeLastModifyed(long timeLastModifyed) {
		this.timeLastModifyed = timeLastModifyed;
	}

}
