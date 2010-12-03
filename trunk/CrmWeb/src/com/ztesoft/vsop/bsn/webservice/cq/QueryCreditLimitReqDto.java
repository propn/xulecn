/**
 * QueryCreditLimitReqDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryCreditLimitReqDto  implements java.io.Serializable {
    private java.lang.String accNbr;

    private java.lang.String qryType;

    private java.lang.String queryTime;

    public QueryCreditLimitReqDto() {
    }

    public QueryCreditLimitReqDto(
           java.lang.String accNbr,
           java.lang.String qryType,
           java.lang.String queryTime) {
           this.accNbr = accNbr;
           this.qryType = qryType;
           this.queryTime = queryTime;
    }


    /**
     * Gets the accNbr value for this QueryCreditLimitReqDto.
     * 
     * @return accNbr
     */
    public java.lang.String getAccNbr() {
        return accNbr;
    }


    /**
     * Sets the accNbr value for this QueryCreditLimitReqDto.
     * 
     * @param accNbr
     */
    public void setAccNbr(java.lang.String accNbr) {
        this.accNbr = accNbr;
    }


    /**
     * Gets the qryType value for this QueryCreditLimitReqDto.
     * 
     * @return qryType
     */
    public java.lang.String getQryType() {
        return qryType;
    }


    /**
     * Sets the qryType value for this QueryCreditLimitReqDto.
     * 
     * @param qryType
     */
    public void setQryType(java.lang.String qryType) {
        this.qryType = qryType;
    }


    /**
     * Gets the queryTime value for this QueryCreditLimitReqDto.
     * 
     * @return queryTime
     */
    public java.lang.String getQueryTime() {
        return queryTime;
    }


    /**
     * Sets the queryTime value for this QueryCreditLimitReqDto.
     * 
     * @param queryTime
     */
    public void setQueryTime(java.lang.String queryTime) {
        this.queryTime = queryTime;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryCreditLimitReqDto)) return false;
        QueryCreditLimitReqDto other = (QueryCreditLimitReqDto) obj;
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
            ((this.qryType==null && other.getQryType()==null) || 
             (this.qryType!=null &&
              this.qryType.equals(other.getQryType()))) &&
            ((this.queryTime==null && other.getQueryTime()==null) || 
             (this.queryTime!=null &&
              this.queryTime.equals(other.getQueryTime())));
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
        if (getQryType() != null) {
            _hashCode += getQryType().hashCode();
        }
        if (getQueryTime() != null) {
            _hashCode += getQueryTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryCreditLimitReqDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditLimitReqDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qryType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "qryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryTime"));
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
