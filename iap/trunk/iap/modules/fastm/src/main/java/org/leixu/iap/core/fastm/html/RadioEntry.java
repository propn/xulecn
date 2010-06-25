package org.leixu.iap.core.fastm.html;

public class RadioEntry extends Entry implements IRadioEntry{
	private Object[] values;

	public RadioEntry() {}
	public RadioEntry(String propertyName, Object[] values) {
		setPropertyName(propertyName);
		setValues(values);
	}

	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
}
