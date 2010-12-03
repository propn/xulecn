/**
 * CodeDatePageRequDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class CodeDatePageRequDto  extends CodeDateRequDto  implements java.io.Serializable {
    private java.lang.String pageNo;

    private java.lang.String pageSize;

    public CodeDatePageRequDto() {
    }

    public CodeDatePageRequDto(
           java.lang.String queryCode,
           java.lang.String queryType,
           java.lang.String userType,
           java.lang.String beginDate,
           java.lang.String endDate,
           java.lang.String queryMonth,
           java.lang.String pageNo,
           java.lang.String pageSize) {
        super(
            queryCode,
            queryType,
            userType,
            beginDate,
            endDate,
            queryMonth);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }


    /**
     * Gets the pageNo value for this CodeDatePageRequDto.
     * 
     * @return pageNo
     */
    public java.lang.String getPageNo() {
        return pageNo;
    }


    /**
     * Sets the pageNo value for this CodeDatePageRequDto.
     * 
     * @param pageNo
     */
    public void setPageNo(java.lang.String pageNo) {
        this.pageNo = pageNo;
    }


    /**
     * Gets the pageSize value for this CodeDatePageRequDto.
     * 
     * @return pageSize
     */
    public java.lang.String getPageSize() {
        return pageSize;
    }


    /**
     * Sets the pageSize value for this CodeDatePageRequDto.
     * 
     * @param pageSize
     */
    public void setPageSize(java.lang.String pageSize) {
        this.pageSize = pageSize;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CodeDatePageRequDto)) return false;
        CodeDatePageRequDto other = (CodeDatePageRequDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.pageNo==null && other.getPageNo()==null) || 
             (this.pageNo!=null &&
              this.pageNo.equals(other.getPageNo()))) &&
            ((this.pageSize==null && other.getPageSize()==null) || 
             (this.pageSize!=null &&
              this.pageSize.equals(other.getPageSize())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getPageNo() != null) {
            _hashCode += getPageNo().hashCode();
        }
        if (getPageSize() != null) {
            _hashCode += getPageSize().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CodeDatePageRequDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDatePageRequDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageSize"));
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
