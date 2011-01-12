package com.ztesoft.common.valueobject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * dhtmlXtree���νڵ�
 * @author liupeidawn
 *
 */
public class TreeItem implements java.io.Serializable{


/*	��Ҫ������:
		text - �����ʾ�ı�ǩ
		id - ���id 

		��ѡ������:
		tooltip - �����ڽ������ʾ����Ϣ
		im0 - û���ӽ��Ľ����ʾ��ͼƬ(�����setImagePath(url)����ָ����·��ȥ��ȡͼƬ)
		im1 - �����ӽ��Ľ��չ��ʱ��ʾ��ͼƬ
		im2 - �����ӽ��Ľ��ر�ʱ��ʾ��ͼƬ
		aCo1 - û��ѡ�еĽ�����ɫ
		sCol - ѡ�еĽ�����ɫ
		select - �ڼ���ʱѡ��˽��(����Ϊ����ֵ)
		style - ����ı����
		open - չ���˽��(����Ϊ����ֵ)
		call - ѡ��ʱ���ú���(����Ϊ����ֵ)
		checked - ������ڵĻ�,ѡ��˽��Ķ�ѡ��(����Ϊ����ֵ)
		child - ָ������Ƿ����ӽ��(1:��,0:��)
		imheight - ͼ��ĸ߶�
		imwidth - ͼ��Ŀ��
		topoffset - ���ý����ϲ�����ƫ����
		radio - ����ǿ� ��˽����ӽ����е�ѡ��ť */ 
	private String text=""; 
	
	private String id="";
	
	private String tooltip="";
	
	private String im0="";
	
	private String im1="";
	
	private String im2="";
	
	private String aCo1="";
	
	private String sCol="";
	
	private String select="";
	
	private String style="";
	
	private String open="";
	
	private String call="";
	
	private String checked="";
	
	private String imheight="";
	
	private String imwidth="";
	
	private String topoffset="";
	
	private String radio=""; 
	
	private String isLeaf = "false";
	
	private List  userDatas=new ArrayList(); 
	
	public void loadUserDataFromMap(Map map){
		if(map!=null){
			Iterator it = map.entrySet().iterator();
			while(it.hasNext()){
				Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				String value  = (String) entry.getValue();
				
				userDatas.add(new TreeUserData(key,value));
			}
		} 
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getIm0() {
		return im0;
	}

	public void setIm0(String im0) {
		this.im0 = im0;
	}

	public String getIm1() {
		return im1;
	}

	public void setIm1(String im1) {
		this.im1 = im1;
	}

	public String getIm2() {
		return im2;
	}

	public void setIm2(String im2) {
		this.im2 = im2;
	}

	public String getACo1() {
		return aCo1;
	}

	public void setACo1(String co1) {
		aCo1 = co1;
	}

	public String getSCol() {
		return sCol;
	}

	public void setSCol(String col) {
		sCol = col;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getImheight() {
		return imheight;
	}

	public void setImheight(String imheight) {
		this.imheight = imheight;
	}

	public String getImwidth() {
		return imwidth;
	}

	public void setImwidth(String imwidth) {
		this.imwidth = imwidth;
	}

	public String getTopoffset() {
		return topoffset;
	}

	public void setTopoffset(String topoffset) {
		this.topoffset = topoffset;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public List getUserDatas() {
		return userDatas;
	}

	public void setUserDatas(List userDatas) {
		this.userDatas = userDatas;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	} 

	
	
}
