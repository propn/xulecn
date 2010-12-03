
package com.ztesoft.crm.business.common.sem.kernel;





public class PropertyParameter implements Parameter {


  private String name;

  private Object value;

  public PropertyParameter(String name, Object value) {
    this.name = name;
    this.value = value;
  }


  public String getName() {
    return name;
  }

 
  public void setName(String name) {
    this.name = name;
  }

  public Object getValue() {
    return value;
  }


  public void setValue(Object value) {
    this.value = value;
  }


  public String toString() {
    return "property name:" + name + " value:  " + value;
  }
}