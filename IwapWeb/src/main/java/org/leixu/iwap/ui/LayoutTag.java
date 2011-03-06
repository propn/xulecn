package org.leixu.iwap.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


public class LayoutTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
  
	private String type;
	private String style;
	
	private String topPane;
	private String leftPane;
	private String centerPane;
	private String rightPane;
	private String bottomPane;
	
	private boolean topSpliter;
	private boolean leftSpliter;
	private boolean rightSpliter;
	private boolean bottomSpliter;
	
	private boolean topSlider;
	private boolean leftSlider;
	private boolean rightSlider;
	private boolean bottomSlider;	
		
	private boolean topFolded;
	private boolean leftFolded;
	private boolean rightFolded;
	private boolean bottomFolded;	
	
	public boolean isBottomFolded() {
		return bottomFolded;
	}
	public void setBottomFolded(boolean bottomFolded) {
		this.bottomFolded = bottomFolded;
	}
	public boolean isLeftFolded() {
		return leftFolded;
	}
	public void setLeftFolded(boolean leftFolded) {
		this.leftFolded = leftFolded;
	}
	public boolean isRightFolded() {
		return rightFolded;
	}
	public void setRightFolded(boolean rightFolded) {
		this.rightFolded = rightFolded;
	}
	public boolean isTopFolded() {
		return topFolded;
	}
	public void setTopFolded(boolean topFolded) {
		this.topFolded = topFolded;
	}
	public boolean isBottomSpliter() {
		return bottomSpliter;
	}
	public void setBottomSpliter(boolean bottomSpliter) {
		this.bottomSpliter = bottomSpliter;
	}
	public boolean isLeftSpliter() {
		return leftSpliter;
	}
	public void setLeftSpliter(boolean leftSpliter) {
		this.leftSpliter = leftSpliter;
	}
	public boolean isRightSpliter() {
		return rightSpliter;
	}
	public void setRightSpliter(boolean rightSpliter) {
		this.rightSpliter = rightSpliter;
	}
	public boolean isTopSpliter() {
		return topSpliter;
	}
	public void setTopSpliter(boolean topSpliter) {
		this.topSpliter = topSpliter;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyle() {
		return style;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return (type!=null ? type : "");
	}
	
	public void setTopPane(String topPane) {
		this.topPane = topPane;
	}
	public String getTopPane() {
		return topPane;
	}

	public void setLeftPane(String leftPane) {
		this.leftPane = leftPane;
	}
	public String getLeftPane() {
		return leftPane;
	}

	public void setCenterPane(String centerPane) {
		this.centerPane = centerPane;
	}
	public String getCenterPane() {
		return centerPane;
	}

	public void setRightPane(String rightPane) {
		this.rightPane = rightPane;
	}
	public String getRightPane() {
		return rightPane;
	}

	public void setBottomPane(String bottomPane) {
		this.bottomPane = bottomPane;
	}
	public String getBottomPane() {
		return bottomPane;
	}	
	
	
	public void release() {
	    super.release();

		this.setTopPane(null);
		this.setLeftPane(null);
		this.setCenterPane(null);
		this.setRightPane(null);
		this.setBottomPane(null);
		
		this.setTopSpliter(false);
		this.setLeftSpliter(false);
		this.setRightSpliter(false);
		this.setBottomSpliter(false);
		
		this.setTopSlider(false);
		this.setLeftSlider(false);
		this.setRightSlider(false);
		this.setBottomSlider(false);		
		
		this.setTopFolded(false);
		this.setLeftFolded(false);
		this.setRightFolded(false);
		this.setBottomFolded(false);		
	}
	
	public String getSpliter(String direction){
		
		StringBuffer sbuffer = new StringBuffer();
	    
		String root = ((HttpServletRequest)(this.pageContext.getRequest())).getContextPath();
		String image = root+"/public/skins/bsn/layout/spliter.gif";
		
		String folded = "";
		if("top".equalsIgnoreCase(direction) && this.isTopFolded()
		 ||"left".equalsIgnoreCase(direction) && this.isLeftFolded()
		 ||"right".equalsIgnoreCase(direction) && this.isRightFolded()
		 ||"bottom".equalsIgnoreCase(direction) && this.isBottomFolded()
		) 
			folded="_folded";
		
		if("top".equalsIgnoreCase(direction) && this.isTopSpliter()
		 ||"left".equalsIgnoreCase(direction) && this.isLeftSpliter()
		 ||"right".equalsIgnoreCase(direction) && this.isRightSpliter()
		 ||"bottom".equalsIgnoreCase(direction) && this.isBottomSpliter()
		) 
			sbuffer.append("<img src='"+image+"' class='spliter_"+direction+folded+"' onclick='spliter_onClick()' />");
		
		return sbuffer.toString();
	}
	
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub

		StringBuffer sbuffer = new StringBuffer();

		if(this.getType()=="border"){
			int colspan = 0;
			if(this.getLeftPane()!=null) colspan++;
			if(this.getCenterPane()!=null) colspan++;
			if(this.getRightPane()!=null) colspan++;	
			
			if(this.isLeftSlider()) colspan++;
			if(this.isRightSlider()) colspan++;
			
			String folded = "";			
			
			sbuffer.append("<table id='layoutPanel' cellpadding='0' cellspacing='0' ");
			if(this.getStyle()!=null){
				sbuffer.append(" style='"+this.getStyle()+";");
				if((this.getLeftPane()==null) && (this.getRightPane()==null)){
					sbuffer.append("table-layout:fixed;");
				}
				sbuffer.append("'");
			}
			else{
				if((this.getLeftPane()==null) && (this.getRightPane()==null)){
					sbuffer.append(" style='table-layout:fixed;'");
				}				
			}
			
			sbuffer.append(">");
			
			//Top���
			if(this.getTopPane()!=null)    
				sbuffer.append("<tr><td colspan='"+colspan+"'>"+this.getTopPane()+"</td></tr>");
			
			if(this.isTopFolded()) 
				folded="style='display:none;'";
			if(this.isTopSlider())		   
				sbuffer.append("<tr "+folded+"><td colspan='"+colspan+"' class='spliterDiv_top' onmousedown='fnGrap(\"top\");'>"+this.getSpliter("top")+"</td></tr>");
			
			sbuffer.append("<tr height='100%'>");	
			
			//Left���
			if(this.isLeftFolded()) 
				folded="style='display:none;'";
			if(this.getLeftPane()!=null)   
				sbuffer.append("<td "+folded+">"+this.getLeftPane()+"</td>");		
			
			if(this.isLeftSlider())	   
				sbuffer.append("<td class='spliterDiv_left' onmousedown='fnGrap(\"left\");'>"+this.getSpliter("left")+"</td>");
			
			//Center���				
			if(this.getCenterPane()!=null) 
				sbuffer.append("<td width='100%'>"+this.getCenterPane()+"</td>");					
			
			//Right���					
			if(this.isRightFolded()) 
				folded="style='display:none;'";
			if(this.isRightSlider())	   
				sbuffer.append("<td class='spliterDiv_right' onmousedown='fnGrap(\"right\");'>"+this.getSpliter("right")+"</td>");

			if(this.getRightPane()!=null)  
				sbuffer.append("<td "+folded+">"+this.getRightPane()+"</td>");				
			
			sbuffer.append("</tr>");
			
			//Bottom���
			if(this.isBottomFolded()) 
				folded="style='display:none;'";	 
            if(this.isBottomSlider())	   
            	sbuffer.append("<tr><td colspan='"+colspan+"' class='spliterDiv_bottom' onmousedown='fnGrap(\"bottom\");'>"+this.getSpliter("bottom")+"</td></tr>");
 
				if(this.getBottomPane()!=null) 
					sbuffer.append("<tr "+folded+"><td colspan='"+colspan+"'>"+this.getBottomPane()+"</td></tr>");           				
			
			sbuffer.append("</table>");
			
			try{		
				String result = sbuffer.toString();
//				if(!ContentManage.isSetParentContent(this, result)){
//					getBodyContent().getEnclosingWriter().println(result);
//				}
			}catch(Exception e){
			}
		}		
		return super.doAfterBody();
	}
	public boolean isBottomSlider() {
		return bottomSlider;
	}
	public void setBottomSlider(boolean bottomSlider) {
		this.bottomSlider = bottomSlider;
	}
	public boolean isLeftSlider() {
		return leftSlider;
	}
	public void setLeftSlider(boolean leftSlider) {
		this.leftSlider = leftSlider;
	}
	public boolean isRightSlider() {
		return rightSlider;
	}
	public void setRightSlider(boolean rightSlider) {
		this.rightSlider = rightSlider;
	}
	public boolean isTopSlider() {
		return topSlider;
	}
	public void setTopSlider(boolean topSlider) {
		this.topSlider = topSlider;
	}
	
}
