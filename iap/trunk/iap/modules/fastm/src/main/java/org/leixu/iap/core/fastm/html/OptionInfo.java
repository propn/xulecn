package org.leixu.iap.core.fastm.html;

public class OptionInfo implements IOptionInfo{
	private int startIndex;
	private Object[] values;
	private Object[] displays;
	private String valueName = "value";
	private String displayName = "display";

	public OptionInfo(){}
	public OptionInfo(Object[] values){
		setValues(values);
	}
	public OptionInfo(Object[] values, Object[] displays){
		setValues(values);
		setDisplays(displays);
	}

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Object[] getDisplays() {
		if(displays == null) return values;
		return displays;
	}
	public void setDisplays(Object[] displays) {
		this.displays = displays;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

}
