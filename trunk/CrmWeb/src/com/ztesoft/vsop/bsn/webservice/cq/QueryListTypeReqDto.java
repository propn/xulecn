/**
 * QueryListTypeReqDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryListTypeReqDto  implements java.io.Serializable {
    private java.lang.String accNbr;

    private java.lang.String callType;

    private java.lang.String listType;

    private java.lang.String otherNumber;

    private java.lang.String productId;

    private java.lang.String queryDate;

    private java.lang.String roveType;

    public QueryListTypeReqDto() {
    }

    public QueryListTypeReqDto(
           java.lang.String accNbr,
           java.lang.String callType,
           java.lang.String listType,
           java.lang.String otherNumber,
           java.lang.String productId,
           java.lang.String queryDate,
           java.lang.String roveType) {
           this.accNbr = accNbr;
           this.callType = callType;
           this.listType = listType;
           this.otherNumber = otherNumber;
           this.productId = productId;
           this.queryDate = queryDate;
           this.roveType = roveType;
    }


    /**
     * Gets the accNbr value for this QueryListTypeReqDto.
     * 
     * @return accNbr
     */
    public java.lang.String getAccNbr() {
        return accNbr;
    }


    /**
     * Sets the accNbr value for this QueryListTypeReqDto.
     * 
     * @param accNbr
     */
    public void setAccNbr(java.lang.String accNbr) {
        this.accNbr = accNbr;
    }


    /**
     * Gets the callType value for this QueryListTypeReqDto.
     * 
     * @return callType
     */
    public java.lang.String getCallType() {
        return callType;
    }


    /**
     * Sets the callType value for this QueryListTypeReqDto.
     * 
     * @param callType
     */
    public void setCallType(java.lang.String callType) {
        this.callType = callType;
    }


    /**
     * Gets the listType value for this QueryListTypeReqDto.
     * 
     * @return listType
     */
    public java.lang.String getListType() {
        return listType;
    }


    /**
     * Sets the listType value for this QueryListTypeReqDto.
     * 
     * @param listType
     */
    public void setListType(java.lang.String listType) {
        this.listType = listType;
    }


    /**
     * Gets the otherNumber value for this QueryListTypeReqDto.
     * 
     * @return otherNumber
     */
    public java.lang.String getOtherNumber() {
        return otherNumber;
    }


    /**
     * Sets the otherNumber value for this QueryListTypeReqDto.
     * 
     * @param otherNumber
     */
    public void setOtherNumber(java.lang.String otherNumber) {
        this.otherNumber = otherNumber;
    }


    /**
     * Gets the productId value for this QueryListTypeReqDto.
     * 
     * @return productId
     */
    public java.lang.String getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this QueryListTypeReqDto.
     * 
     * @param productId
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }


    /**
     * Gets the queryDate value for this QueryListTypeReqDto.
     * 
     * @return queryDate
     */
    public java.lang.String getQueryDate() {
        return queryDate;
    }


    /**
     * Sets the queryDate value for this QueryListTypeReqDto.
     * 
     * @param queryDate
     */
    public void setQueryDate(java.lang.String queryDate) {
        this.queryDate = queryDate;
    }


    /**
     * Gets the roveType value for this QueryListTypeReqDto.
     * 
     * @return roveType
     */
    public java.lang.String getRoveType() {
        return roveType;
    }


    /**
     * Sets the roveType value for this QueryListTypeReqDto.
     * 
     * @param roveType
     */
    public void setRoveType(java.lang.String roveType) {
        this.roveType = roveType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryListTypeReqDto)) return false;
        QueryListTypeReqDto other = (QueryListTypeReqDto) obj;
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
            ((this.callType==null && other.getCallType()==null) || 
             (this.callType!=null &&
              this.callType.equals(other.getCallType()))) &&
            ((this.listType==null && other.getListType()==null) || 
             (this.listType!=null &&
              this.listType.equals(other.getListType()))) &&
            ((this.otherNumber==null && other.getOtherNumber()==null) || 
             (this.otherNumber!=null &&
              this.otherNumber.equals(other.getOtherNumber()))) &&
            ((this.productId==null && other.getProductId()==null) || 
             (this.productId!=null &&
              this.productId.equals(other.getProductId()))) &&
            ((this.queryDate==null && other.getQueryDate()==null) || 
             (this.queryDate!=null &&
              this.queryDate.equals(other.getQueryDate()))) &&
            ((this.roveType==null && other.getRoveType()==null) || 
             (this.roveType!=null &&
              this.roveType.equals(other.getRoveType())));
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
        if (getCallType() != null) {
            _hashCode += getCallType().hashCode();
        }
        if (getListType() != null) {
            _hashCode += getListType().hashCode();
        }
        if (getOtherNumber() != null) {
            _hashCode += getOtherNumber().hashCode();
        }
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getQueryDate() != null) {
            _hashCode += getQueryDate().hashCode();
        }
        if (getRoveType() != null) {
            _hashCode += getRoveType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryListTypeReqDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeReqDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("callType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "callType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otherNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "otherNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roveType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roveType"));
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
