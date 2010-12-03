/**
 * QueryActiveBalanceRequDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryActiveBalanceRequDto  implements java.io.Serializable {
    private java.lang.String accNbr;

    private java.lang.String queryType;

    public QueryActiveBalanceRequDto() {
    }

    public QueryActiveBalanceRequDto(
           java.lang.String accNbr,
           java.lang.String queryType) {
           this.accNbr = accNbr;
           this.queryType = queryType;
    }


    /**
     * Gets the accNbr value for this QueryActiveBalanceRequDto.
     * 
     * @return accNbr
     */
    public java.lang.String getAccNbr() {
        return accNbr;
    }


    /**
     * Sets the accNbr value for this QueryActiveBalanceRequDto.
     * 
     * @param accNbr
     */
    public void setAccNbr(java.lang.String accNbr) {
        this.accNbr = accNbr;
    }


    /**
     * Gets the queryType value for this QueryActiveBalanceRequDto.
     * 
     * @return queryType
     */
    public java.lang.String getQueryType() {
        return queryType;
    }


    /**
     * Sets the queryType value for this QueryActiveBalanceRequDto.
     * 
     * @param queryType
     */
    public void setQueryType(java.lang.String queryType) {
        this.queryType = queryType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryActiveBalanceRequDto)) return false;
        QueryActiveBalanceRequDto other = (QueryActiveBalanceRequDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accNbr==null && other.getAccNbr()==null) || 
             (this.accNbr!=null &&
              this.accNbr.equals(other.getAccNbr()))) &&
            ((this.queryType==null && other.getQueryType()==null) || 
             (this.queryType!=null &&
              this.queryType.equals(other.getQueryType())));
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
        if (getAccNbr() != null) {
            _hashCode += getAccNbr().hashCode();
        }
        if (getQueryType() != null) {
            _hashCode += getQueryType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryActiveBalanceRequDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalanceRequDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryType"));
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
