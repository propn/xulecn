/**
 * QueryFZBillCountRequDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryFZBillCountRequDto  extends QueryMXBillCountRequDto  implements java.io.Serializable {
    private int fzType;

    public QueryFZBillCountRequDto() {
    }

    public QueryFZBillCountRequDto(
           java.lang.String beginMonth,
           java.lang.String endMonth,
           java.lang.String queryCode,
           java.lang.String queryType,
           java.lang.String querytype,
           int usertype,
           int fzType) {
        super(
            beginMonth,
            endMonth,
            queryCode,
            queryType,
            querytype,
            usertype);
        this.fzType = fzType;
    }


    /**
     * Gets the fzType value for this QueryFZBillCountRequDto.
     * 
     * @return fzType
     */
    public int getFzType() {
        return fzType;
    }


    /**
     * Sets the fzType value for this QueryFZBillCountRequDto.
     * 
     * @param fzType
     */
    public void setFzType(int fzType) {
        this.fzType = fzType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryFZBillCountRequDto)) return false;
        QueryFZBillCountRequDto other = (QueryFZBillCountRequDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.fzType == other.getFzType();
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
        _hashCode += getFzType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryFZBillCountRequDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryFZBillCountRequDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fzType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fzType"));
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
