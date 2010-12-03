
package com.ztesoft.crm.business.common.sem.kernel;

/**
 * 构造函数参数
 * */
public class ConstructorParameter implements Parameter {


  private Object value;


  public ConstructorParameter(Object value) {
    this.value = value;
  }


  public Object getValue() {
    return value;
  }


  public void setValue(Object value) {
    this.value = value;
  }

  public String toString() {
    return "value: " + value;
  }
}