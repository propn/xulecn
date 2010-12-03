/**
 * ListTypeDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ListTypeDto  implements java.io.Serializable {
    private int patiTypeId;

    private java.lang.String patiTypeName;

    public ListTypeDto() {
    }

    public ListTypeDto(
           int patiTypeId,
           java.lang.String patiTypeName) {
           this.patiTypeId = patiTypeId;
           this.patiTypeName = patiTypeName;
    }


    /**
     * Gets the patiTypeId value for this ListTypeDto.
     * 
     * @return patiTypeId
     */
    public int getPatiTypeId() {
        return patiTypeId;
    }


    /**
     * Sets the patiTypeId value for this ListTypeDto.
     * 
     * @param patiTypeId
     */
    public void setPatiTypeId(int patiTypeId) {
        this.patiTypeId = patiTypeId;
    }


    /**
     * Gets the patiTypeName value for this ListTypeDto.
     * 
     * @return patiTypeName
     */
    public java.lang.String getPatiTypeName() {
        return patiTypeName;
    }


    /**
     * Sets the patiTypeName value for this ListTypeDto.
     * 
     * @param patiTypeName
     */
    public void setPatiTypeName(java.lang.String patiTypeName) {
        this.patiTypeName = patiTypeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListTypeDto)) return false;
        ListTypeDto other = (ListTypeDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.patiTypeId == other.getPatiTypeId() &&
            ((this.patiTypeName==null && other.getPatiTypeName()==null) || 
             (this.patiTypeName!=null &&
              this.patiTypeName.equals(other.getPatiTypeName())));
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
        _hashCode += getPatiTypeId();
        if (getPatiTypeName() != null) {
            _hashCode += getPatiTypeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListTypeDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patiTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patiTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patiTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patiTypeName"));
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
