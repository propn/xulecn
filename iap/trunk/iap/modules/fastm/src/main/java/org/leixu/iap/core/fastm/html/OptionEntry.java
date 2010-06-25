package org.leixu.iap.core.fastm.html;

public class OptionEntry extends Entry implements IOptionEntry{
	private IOptionInfo optionInfo;
	
	public OptionEntry(){}
	public OptionEntry(String propertyName, OptionInfo optionInfo){
		setPropertyName(propertyName);
		setOptionInfo(optionInfo);
	}

	public IOptionInfo getOptionInfo() {
		return optionInfo;
	}
	public void setOptionInfo(IOptionInfo optionInfo) {
		this.optionInfo = optionInfo;
	}
}
