package com.ztesoft.component.ui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class ZXTag extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImportTag.class);

	private String library;

	private String root;

	private String css = "true";

	private String tree="false";

	private String window="false";

	private String contextmenu="false";


	private String core="true";

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public String getWindow() {
		return window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	public String getTree() {
		return tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getRoot() {
		return (root != null ? root : ((HttpServletRequest) (this.pageContext.getRequest())).getContextPath() + "/");
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String _import(String type, String path, String className) {
		String result = "";

		if (path != null)
			path = this.getRoot() + path;

		if ("js".equalsIgnoreCase(type))
			result = "<script type='text/javascript' language='JavaScript' src='" + path + "' charset='GBK'></script>";
		else if ("css".equalsIgnoreCase(type))
			result = "<link type='text/css' rel='stylesheet' href='" + path + "' charset='GBK'/>";
		else if ("behavior".equalsIgnoreCase(type)) {
			result = "<style>" + className + "{behavior:url(" + path + "); }</style>";
		} else if ("namespace".equalsIgnoreCase(type)) {
			result = "<?import namespace='" + className + "' implementation='" + path + "' />";
		}
		return result;
	}

	public int doEndTag() throws JspException {
		StringBuffer sbuffer = new StringBuffer();

/*		<link rel="stylesheet" type="text/css" href="/CrmWeb/public/widgets/window/codebase/dhtmlxwindows.css">
		<link rel="stylesheet" type="text/css" href="/CrmWeb/public/widgets/window/codebase/skins/dhtmlxwindows_dhx_skyblue.css">
		<script src="/CrmWeb/public/widgets/window/codebase/dhtmlxcommon.js"></script>
		<script src="/CrmWeb/public/widgets/window/codebase/dhtmlxwindows.js"></script>
		<script src="/CrmWeb/public/widgets/window/codebase/dhtmlxcontainer.js"></script>*/



		sbuffer.append(this._import("js", "public/components/jquery-1.4.1.min.js", ""));
		sbuffer.append("<script> jQ = jQuery.noConflict(); </script>");

		if("true".equals(this.getCore())){
			sbuffer.append(this._import("js", "public/zx/ZX.js", ""));
			sbuffer.append(this._import("js", "public/zx/Array.js", ""));
			sbuffer.append(this._import("js", "public/zx/Event.js", ""));
			sbuffer.append(this._import("js", "public/zx/Basis.js", ""));
			sbuffer.append(this._import("js", "public/zx/Element.js", ""));
			sbuffer.append(this._import("js", "public/zx/Bean.js", ""));
			sbuffer.append(this._import("js", "public/zx/Field.js", ""));
			sbuffer.append(this._import("js", "public/zx/Action.js", ""));
			sbuffer.append(this._import("js", "public/zx/VTypes.js", ""));
			sbuffer.append(this._import("js", "public/zx/Proto.js", ""));

			// sbuffer.append(this._import("js", "public/components/prototype.js", ""));
			sbuffer.append(this._import("js", "public/zx/Ajax.js", ""));
			sbuffer.append(this._import("js", "public/components/messageBox/messageBox.js", ""));
			sbuffer.append(this._import("js", "public/components/messageBox/dialog.js", ""));


			sbuffer.append(this._import("js", "public/components/html.js", ""));
			sbuffer.append(this._import("js", "public/components/select.js", ""));

			//easyui的动态加载器
			sbuffer.append(this._import("js", "public/widgets/easyui/easyloader.js", ""));
		}
		if("true".equals(this.getWindow())){

			sbuffer.append(this._import("css", "public/widgets/window/codebase/dhtmlxwindows.css", ""));
			sbuffer.append(this._import("css", "public/widgets/window/codebase/skins/dhtmlxwindows_dhx_skyblue.css", ""));

			sbuffer.append(this._import("js", "public/widgets/window/codebase/dhtmlxcommon.js", ""));
			sbuffer.append(this._import("js", "public/widgets/window/codebase/dhtmlxwindows.js", ""));
			sbuffer.append(this._import("js", "public/widgets/window/codebase/dhtmlxcontainer.js", ""));
		}

		if("true".equals(this.getTree())){
			sbuffer.append(this._import("css", "public/widgets/tree/codebase/dhtmlxtree.css", ""));
			sbuffer.append(this._import("js", "public/widgets/tree/codebase/dhtmlxcommon.js", ""));
			sbuffer.append(this._import("js", "public/widgets/tree/codebase/dhtmlxtree.js", ""));
			sbuffer.append(this._import("js", "public/zx/Tree.js", ""));
		}

		if("true".equals(this.getContextmenu())){
			sbuffer.append(this._import("css", "public/widgets/rightmenu/Themes/Default/contextmenu.css", ""));

			sbuffer.append(this._import("js", "public/widgets/rightmenu/Javascripts/Plugins/jquery.contextmenu.js", ""));
			sbuffer.append(this._import("js", "public/widgets/rightmenu/Javascripts/contextmenu.js", ""));
		}
		try {
			String result = sbuffer.toString();
			if (!ContentManage.isSetParentContent(this, result)) {
				pageContext.getOut().println(result);
			}
		} catch (Exception e) {
			logger.error("Import标签解释失败", e);
		}
		return super.doEndTag();
	}

	public void release() {
		// TODO Auto-generated method stub
		super.release();

		this.library = null;

	}

	public String getContextmenu() {
		return contextmenu;
	}

	public void setContextmenu(String contextmenu) {
		this.contextmenu = contextmenu;
	}

}
