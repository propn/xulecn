/**
 * QueryServStateRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryServStateRespDto  implements java.io.Serializable {
    private java.lang.String desc;

    private java.lang.String oweBusinessTypeId;

    private java.lang.String oweBusinessTypeName;

    private int result;

    public QueryServStateRespDto() {
    }

    public QueryServStateRespDto(
           java.lang.String desc,
           java.lang.String oweBusinessTypeId,
           java.lang.String oweBusinessTypeName,
           int result) {
           this.desc = desc;
           this.oweBusinessTypeId = oweBusinessTypeId;
           this.oweBusinessTypeName = oweBusinessTypeName;
           this.result = result;
    }


    /**
     * Gets the desc value for this QueryServStateRespDto.
     * 
     * @return desc
     */
    public java.lang.String getDesc() {
        return desc;
    }


    /**
     * Sets the desc value for this QueryServStateRespDto.
     * 
     * @param desc
     */
    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }


    /**
     * Gets the oweBusinessTypeId value for this QueryServStateRespDto.
     * 
     * @return oweBusinessTypeId
     */
    public java.lang.String getOweBusinessTypeId() {
        return oweBusinessTypeId;
    }


    /**
     * Sets the oweBusinessTypeId value for this QueryServStateRespDto.
     * 
     * @param oweBusinessTypeId
     */
    public void setOweBusinessTypeId(java.lang.String oweBusinessTypeId) {
        this.oweBusinessTypeId = oweBusinessTypeId;
    }


    /**
     * Gets the oweBusinessTypeName value for this QueryServStateRespDto.
     * 
     * @return oweBusinessTypeName
     */
    public java.lang.String getOweBusinessTypeName() {
        return oweBusinessTypeName;
    }


    /**
     * Sets the oweBusinessTypeName value for this QueryServStateRespDto.
     * 
     * @param oweBusinessTypeName
     */
    public void setOweBusinessTypeName(java.lang.String oweBusinessTypeName) {
        this.oweBusinessTypeName = oweBusinessTypeName;
    }


    /**
     * Gets the result value for this QueryServStateRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryServStateRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryServStateRespDto)) return false;
        QueryServStateRespDto other = (QueryServStateRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.desc==null && other.getDesc()==null) || 
             (this.desc!=null &&
              this.desc.equals(other.getDesc()))) &&
            ((this.oweBusinessTypeId==null && other.getOweBusinessTypeId()==null) || 
             (this.oweBusinessTypeId!=null &&
              this.oweBusinessTypeId.equals(other.getOweBusinessTypeId()))) &&
            ((this.oweBusinessTypeName==null && other.getOweBusinessTypeName()==null) || 
             (this.oweBusinessTypeName!=null &&
              this.oweBusinessTypeName.equals(other.getOweBusinessTypeName()))) &&
            this.result == other.getResult();
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
        if (getDesc() != null) {
            _hashCode += getDesc().hashCode();
        }
        if (getOweBusinessTypeId() != null) {
            _hashCode += getOweBusinessTypeId().hashCode();
        }
        if (getOweBusinessTypeName() != null) {
            _hashCode += getOweBusinessTypeName().hashCode();
        }
        _hashCode += getResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryServStateRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServStateRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oweBusinessTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oweBusinessTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oweBusinessTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oweBusinessTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
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
