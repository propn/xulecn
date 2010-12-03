/**
 * QueryPayRecordReqDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryPayRecordReqDto  implements java.io.Serializable {
    private java.lang.String action;

    private java.lang.String begindate;

    private java.lang.String enddate;

    private java.lang.String phone;

    private int usertype;

    public QueryPayRecordReqDto() {
    }

    public QueryPayRecordReqDto(
           java.lang.String action,
           java.lang.String begindate,
           java.lang.String enddate,
           java.lang.String phone,
           int usertype) {
           this.action = action;
           this.begindate = begindate;
           this.enddate = enddate;
           this.phone = phone;
           this.usertype = usertype;
    }


    /**
     * Gets the action value for this QueryPayRecordReqDto.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this QueryPayRecordReqDto.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the begindate value for this QueryPayRecordReqDto.
     * 
     * @return begindate
     */
    public java.lang.String getBegindate() {
        return begindate;
    }


    /**
     * Sets the begindate value for this QueryPayRecordReqDto.
     * 
     * @param begindate
     */
    public void setBegindate(java.lang.String begindate) {
        this.begindate = begindate;
    }


    /**
     * Gets the enddate value for this QueryPayRecordReqDto.
     * 
     * @return enddate
     */
    public java.lang.String getEnddate() {
        return enddate;
    }


    /**
     * Sets the enddate value for this QueryPayRecordReqDto.
     * 
     * @param enddate
     */
    public void setEnddate(java.lang.String enddate) {
        this.enddate = enddate;
    }


    /**
     * Gets the phone value for this QueryPayRecordReqDto.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this QueryPayRecordReqDto.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the usertype value for this QueryPayRecordReqDto.
     * 
     * @return usertype
     */
    public int getUsertype() {
        return usertype;
    }


    /**
     * Sets the usertype value for this QueryPayRecordReqDto.
     * 
     * @param usertype
     */
    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryPayRecordReqDto)) return false;
        QueryPayRecordReqDto other = (QueryPayRecordReqDto) obj;
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
            ((this.begindate==null && other.getBegindate()==null) || 
             (this.begindate!=null &&
              this.begindate.equals(other.getBegindate()))) &&
            ((this.enddate==null && other.getEnddate()==null) || 
             (this.enddate!=null &&
              this.enddate.equals(other.getEnddate()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            this.usertype == other.getUsertype();
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
        if (getBegindate() != null) {
            _hashCode += getBegindate().hashCode();
        }
        if (getEnddate() != null) {
            _hashCode += getEnddate().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        _hashCode += getUsertype();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryPayRecordReqDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordReqDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("begindate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "begindate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enddate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enddate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usertype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usertype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
