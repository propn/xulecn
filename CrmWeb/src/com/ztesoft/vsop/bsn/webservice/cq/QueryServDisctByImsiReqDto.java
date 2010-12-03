/**
 * QueryServDisctByImsiReqDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryServDisctByImsiReqDto  implements java.io.Serializable {
    private java.lang.String imsiAccNbr;

    private java.lang.String queryType;

    public QueryServDisctByImsiReqDto() {
    }

    public QueryServDisctByImsiReqDto(
           java.lang.String imsiAccNbr,
           java.lang.String queryType) {
           this.imsiAccNbr = imsiAccNbr;
           this.queryType = queryType;
    }


    /**
     * Gets the imsiAccNbr value for this QueryServDisctByImsiReqDto.
     * 
     * @return imsiAccNbr
     */
    public java.lang.String getImsiAccNbr() {
        return imsiAccNbr;
    }


    /**
     * Sets the imsiAccNbr value for this QueryServDisctByImsiReqDto.
     * 
     * @param imsiAccNbr
     */
    public void setImsiAccNbr(java.lang.String imsiAccNbr) {
        this.imsiAccNbr = imsiAccNbr;
    }


    /**
     * Gets the queryType value for this QueryServDisctByImsiReqDto.
     * 
     * @return queryType
     */
    public java.lang.String getQueryType() {
        return queryType;
    }


    /**
     * Sets the queryType value for this QueryServDisctByImsiReqDto.
     * 
     * @param queryType
     */
    public void setQueryType(java.lang.String queryType) {
        this.queryType = queryType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryServDisctByImsiReqDto)) return false;
        QueryServDisctByImsiReqDto other = (QueryServDisctByImsiReqDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.imsiAccNbr==null && other.getImsiAccNbr()==null) || 
             (this.imsiAccNbr!=null &&
              this.imsiAccNbr.equals(other.getImsiAccNbr()))) &&
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
        if (getImsiAccNbr() != null) {
            _hashCode += getImsiAccNbr().hashCode();
        }
        if (getQueryType() != null) {
            _hashCode += getQueryType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryServDisctByImsiReqDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctByImsiReqDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imsiAccNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "imsiAccNbr"));
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
