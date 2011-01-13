package com.ztesoft.uiext.mztree;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 简单树，对性能要求比较高的场景，可选择MZTreeView
 * @author easonwu 2009-12-31
 *
 */
public class MZTreeTag extends TagSupport{
	//属性
	private String useCheckbox;
	private String autoFocused ;
	private String canOperate;
	private String autoSort ;
	private String isend;
	private String dataSource;
	private String id ;
	private String useContextMenu ;
	//方法
	private String onrender;
	private String onclick;
	private String ondblclick;
	private String onappendnode;
	private String onexpand;
	private String oncollapse;
	private String onfocus;
	private String onremovenode;
	
	private String loadDataMethod ;
	
	public String getUseContextMenu() {
		return useContextMenu == null || "".equals(useContextMenu) ? "false" : useContextMenu;
	}
	public void setUseContextMenu(String useContextMenu) {
		this.useContextMenu = useContextMenu;
	}
	public String getLoadDataMethod() throws JspException {
		if(this.getLoadDataMethod() == null || "".equals(this.getLoadDataMethod()))
			throw new JspException("数据加载方法不能为空!") ;
		return loadDataMethod;
	}
	public void setLoadDataMethod(String loadDataMethod) {
		this.loadDataMethod = loadDataMethod;
	}
	public String getAutoFocused() {
		return autoFocused == null || "".equals(autoFocused) ? "false" : autoFocused;
	}
	public void setAutoFocused(String autoFocused) {
		this.autoFocused = autoFocused;
	}
	public String getAutoSort() {
		return autoSort == null || "".equals(autoSort) ? "false" : autoSort;
	}
	public void setAutoSort(String autoSort) {
		this.autoSort = autoSort;
	}
	public String getCanOperate() {
		return canOperate == null || "".equals(canOperate) ? "false" : canOperate;
	}
	public void setCanOperate(String canOperate) {
		this.canOperate = canOperate;
	}
	private String getDataSource() {
		return dataSource;
	}
	private void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsend() {
		return isend;
	}
	public void setIsend(String isend) {
		this.isend = isend;
	}
	public String getOnappendnode() {
		return onappendnode;
	}
	public void setOnappendnode(String onappendnode) {
		this.onappendnode = onappendnode;
	}
	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public String getOncollapse() {
		return oncollapse;
	}
	public void setOncollapse(String oncollapse) {
		this.oncollapse = oncollapse;
	}
	public String getOndblclick() {
		return ondblclick;
	}
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}
	public String getOnexpand() {
		return onexpand;
	}
	public void setOnexpand(String onexpand) {
		this.onexpand = onexpand;
	}
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	public String getOnremovenode() {
		return onremovenode;
	}
	public void setOnremovenode(String onremovenode) {
		this.onremovenode = onremovenode;
	}
	public String getOnrender() {
		return onrender;
	}
	public void setOnrender(String onrender) {
		this.onrender = onrender;
	}
	public String getUseCheckbox() {
		return useCheckbox == null || "".equals(useCheckbox) ? "false" : useCheckbox;
	}
	public void setUseCheckbox(String useCheckbox) {
		this.useCheckbox = useCheckbox;
	}

	private String getJsTreeVarDefine()throws JspException{
		return " var " +getJsTreeVar() +" = new MzTreeView();"   ;
	}
	
	private String getJsTreeVar() throws JspException{
		if(this.getId() == null || "".equals(this.getId()))
			throw new JspException("树标签ID属性不能缺失或者为空!") ;
		return "tree_"+this.getId()  ;
	}
	
	public int doEndTag() throws JspException {
		String treeVarDefine = this.getJsTreeVarDefine() ;
		String treevar = this.getJsTreeVar() ;
		String dataMethod = this.getLoadDataMethod() ;
		
		StringBuffer treeHtml =  new StringBuffer() ;
		treeHtml.append(getJavascriptStartTag()) ;
		
		treeHtml.append(treeVarDefine).
			append(treevar+".dataSource = ").append(dataMethod).
			append(treevar+".autoSort="+this.getAutoSort()).
			append(treevar+".canOperate="+this.getCanOperate()).
			append(treevar+".useCheckbox="+this.getUseCheckbox()).
			append(treevar+".useContextMenu="+this.getUseContextMenu());
		
		handleActionMethod( treeHtml ,  treevar ) ;
		
		treeHtml.append("document.getElementById(\""+this.getId()+"\").innerHTML="+treevar+".render();") 
			.append(treevar+".expandLevel(1);") 
			;
		
		treeHtml.append(getJavascriptEndTag()) ;
		return super.doEndTag()  ;
	}
	
	
	private String getJavascriptStartTag(){
		return "<script type=\"text/javascript\">" ;
	}
	private String getJavascriptEndTag(){
		return "</script>" ;
	}
	
	private void handleActionMethod(StringBuffer treeHtml , String treeVar ) {
//		private String onrender;
//		private String onclick;
//		private String ondblclick;
//		private String onappendnode;
//		private String onexpand;
//		private String oncollapse;
//		private String onfocus;
//		private String onremovenode;
		if(this.getOnclick() != null && !"".equals(this.getOnclick()))
			treeHtml.append(treeVar+".onclick="+getOnclick()) ;
		if(this.getOnrender() != null && !"".equals(this.getOnrender()))
			treeHtml.append(treeVar+".onrender="+getOnrender()) ;
		if(this.getOndblclick() != null && !"".equals(this.getOndblclick()))
			treeHtml.append(treeVar+".ondblclick="+getOndblclick()) ;
		if(this.getOnappendnode() != null && !"".equals(this.getOnappendnode()))
			treeHtml.append(treeVar+".onappendnode="+getOnappendnode()) ;
		if(this.getOnexpand() != null && !"".equals(this.getOnexpand()))
			treeHtml.append(treeVar+".onexpand="+getOnexpand()) ;
		if(this.getOncollapse() != null && !"".equals(this.getOncollapse()))
			treeHtml.append(treeVar+".oncollapse="+getOncollapse()) ;
		if(this.getOnfocus() != null && !"".equals(this.getOnfocus()))
			treeHtml.append(treeVar+".onfocus="+getOnfocus()) ;
		if(this.getOnremovenode() != null && !"".equals(this.getOnremovenode()))
			treeHtml.append(treeVar+".onremovenode="+getOnremovenode()) ;
		
	}
	
	public void release() {
		super.release() ;

		useCheckbox = null;
		autoFocused  = null;
		canOperate = null;
		autoSort  = null;
		isend = null;
		dataSource = null;
		id  = null;
		useContextMenu = null ;

		onrender = null;
		onclick = null;
		ondblclick = null;
		onappendnode = null;
		onexpand = null;
		oncollapse = null;
		onfocus = null;
		onremovenode = null;
		this.loadDataMethod = null ;
	}
	
	
}
