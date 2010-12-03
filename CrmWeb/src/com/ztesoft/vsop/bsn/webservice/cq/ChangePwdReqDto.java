/**
 * ChangePwdReqDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ChangePwdReqDto  implements java.io.Serializable {
    private java.lang.String action;

    private java.lang.String newpassword;

    private java.lang.String oldpassword;

    private java.lang.String phone;

    public ChangePwdReqDto() {
    }

    public ChangePwdReqDto(
           java.lang.String action,
           java.lang.String newpassword,
           java.lang.String oldpassword,
           java.lang.String phone) {
           this.action = action;
           this.newpassword = newpassword;
           this.oldpassword = oldpassword;
           this.phone = phone;
    }


    /**
     * Gets the action value for this ChangePwdReqDto.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this ChangePwdReqDto.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the newpassword value for this ChangePwdReqDto.
     * 
     * @return newpassword
     */
    public java.lang.String getNewpassword() {
        return newpassword;
    }


    /**
     * Sets the newpassword value for this ChangePwdReqDto.
     * 
     * @param newpassword
     */
    public void setNewpassword(java.lang.String newpassword) {
        this.newpassword = newpassword;
    }


    /**
     * Gets the oldpassword value for this ChangePwdReqDto.
     * 
     * @return oldpassword
     */
    public java.lang.String getOldpassword() {
        return oldpassword;
    }


    /**
     * Sets the oldpassword value for this ChangePwdReqDto.
     * 
     * @param oldpassword
     */
    public void setOldpassword(java.lang.String oldpassword) {
        this.oldpassword = oldpassword;
    }


    /**
     * Gets the phone value for this ChangePwdReqDto.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this ChangePwdReqDto.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangePwdReqDto)) return false;
        ChangePwdReqDto other = (ChangePwdReqDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.newpassword==null && other.getNewpassword()==null) || 
             (this.newpassword!=null &&
              this.newpassword.equals(other.getNewpassword()))) &&
            ((this.oldpassword==null && other.getOldpassword()==null) || 
             (this.oldpassword!=null &&
              this.oldpassword.equals(other.getOldpassword()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getNewpassword() != null) {
            _hashCode += getNewpassword().hashCode();
        }
        if (getOldpassword() != null) {
            _hashCode += getOldpassword().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangePwdReqDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangePwdReqDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newpassword");
        elemField.setXmlName(new javax.xml.namespace.QName("", "newpassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldpassword");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oldpassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
