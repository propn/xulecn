/**
 * QueryMXBillCountRequDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryMXBillCountRequDto  implements java.io.Serializable {
    private java.lang.String beginMonth;

    private java.lang.String endMonth;

    private java.lang.String queryCode;

    private java.lang.String queryType;

    private java.lang.String querytype;

    private int usertype;

    public QueryMXBillCountRequDto() {
    }

    public QueryMXBillCountRequDto(
           java.lang.String beginMonth,
           java.lang.String endMonth,
           java.lang.String queryCode,
           java.lang.String queryType,
           java.lang.String querytype,
           int usertype) {
           this.beginMonth = beginMonth;
           this.endMonth = endMonth;
           this.queryCode = queryCode;
           this.queryType = queryType;
           this.querytype = querytype;
           this.usertype = usertype;
    }


    /**
     * Gets the beginMonth value for this QueryMXBillCountRequDto.
     * 
     * @return beginMonth
     */
    public java.lang.String getBeginMonth() {
        return beginMonth;
    }


    /**
     * Sets the beginMonth value for this QueryMXBillCountRequDto.
     * 
     * @param beginMonth
     */
    public void setBeginMonth(java.lang.String beginMonth) {
        this.beginMonth = beginMonth;
    }


    /**
     * Gets the endMonth value for this QueryMXBillCountRequDto.
     * 
     * @return endMonth
     */
    public java.lang.String getEndMonth() {
        return endMonth;
    }


    /**
     * Sets the endMonth value for this QueryMXBillCountRequDto.
     * 
     * @param endMonth
     */
    public void setEndMonth(java.lang.String endMonth) {
        this.endMonth = endMonth;
    }


    /**
     * Gets the queryCode value for this QueryMXBillCountRequDto.
     * 
     * @return queryCode
     */
    public java.lang.String getQueryCode() {
        return queryCode;
    }


    /**
     * Sets the queryCode value for this QueryMXBillCountRequDto.
     * 
     * @param queryCode
     */
    public void setQueryCode(java.lang.String queryCode) {
        this.queryCode = queryCode;
    }


    /**
     * Gets the queryType value for this QueryMXBillCountRequDto.
     * 
     * @return queryType
     */
    public java.lang.String getQueryType() {
        return queryType;
    }


    /**
     * Sets the queryType value for this QueryMXBillCountRequDto.
     * 
     * @param queryType
     */
    public void setQueryType(java.lang.String queryType) {
        this.queryType = queryType;
    }


    /**
     * Gets the querytype value for this QueryMXBillCountRequDto.
     * 
     * @return querytype
     */
    public java.lang.String getQuerytype() {
        return querytype;
    }


    /**
     * Sets the querytype value for this QueryMXBillCountRequDto.
     * 
     * @param querytype
     */
    public void setQuerytype(java.lang.String querytype) {
        this.querytype = querytype;
    }


    /**
     * Gets the usertype value for this QueryMXBillCountRequDto.
     * 
     * @return usertype
     */
    public int getUsertype() {
        return usertype;
    }


    /**
     * Sets the usertype value for this QueryMXBillCountRequDto.
     * 
     * @param usertype
     */
    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryMXBillCountRequDto)) return false;
        QueryMXBillCountRequDto other = (QueryMXBillCountRequDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.beginMonth==null && other.getBeginMonth()==null) || 
             (this.beginMonth!=null &&
              this.beginMonth.equals(other.getBeginMonth()))) &&
            ((this.endMonth==null && other.getEndMonth()==null) || 
             (this.endMonth!=null &&
              this.endMonth.equals(other.getEndMonth()))) &&
            ((this.queryCode==null && other.getQueryCode()==null) || 
             (this.queryCode!=null &&
              this.queryCode.equals(other.getQueryCode()))) &&
            ((this.queryType==null && other.getQueryType()==null) || 
             (this.queryType!=null &&
              this.queryType.equals(other.getQueryType()))) &&
            ((this.querytype==null && other.getQuerytype()==null) || 
             (this.querytype!=null &&
              this.querytype.equals(other.getQuerytype()))) &&
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
        if (getBeginMonth() != null) {
            _hashCode += getBeginMonth().hashCode();
        }
        if (getEndMonth() != null) {
            _hashCode += getEndMonth().hashCode();
        }
        if (getQueryCode() != null) {
            _hashCode += getQueryCode().hashCode();
        }
        if (getQueryType() != null) {
            _hashCode += getQueryType().hashCode();
        }
        if (getQuerytype() != null) {
            _hashCode += getQuerytype().hashCode();
        }
        _hashCode += getUsertype();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryMXBillCountRequDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryMXBillCountRequDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("querytype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "querytype"));
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
