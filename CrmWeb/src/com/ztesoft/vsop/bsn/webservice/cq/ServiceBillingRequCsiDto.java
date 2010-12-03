/**
 * ServiceBillingRequCsiDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServiceBillingRequCsiDto  implements java.io.Serializable {
    private java.lang.String beginDate;

    private java.lang.String endDate;

    private java.lang.String oweBusinessTypeId;

    private java.lang.String productId;

    private java.lang.String queryCode;

    public ServiceBillingRequCsiDto() {
    }

    public ServiceBillingRequCsiDto(
           java.lang.String beginDate,
           java.lang.String endDate,
           java.lang.String oweBusinessTypeId,
           java.lang.String productId,
           java.lang.String queryCode) {
           this.beginDate = beginDate;
           this.endDate = endDate;
           this.oweBusinessTypeId = oweBusinessTypeId;
           this.productId = productId;
           this.queryCode = queryCode;
    }


    /**
     * Gets the beginDate value for this ServiceBillingRequCsiDto.
     * 
     * @return beginDate
     */
    public java.lang.String getBeginDate() {
        return beginDate;
    }


    /**
     * Sets the beginDate value for this ServiceBillingRequCsiDto.
     * 
     * @param beginDate
     */
    public void setBeginDate(java.lang.String beginDate) {
        this.beginDate = beginDate;
    }


    /**
     * Gets the endDate value for this ServiceBillingRequCsiDto.
     * 
     * @return endDate
     */
    public java.lang.String getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this ServiceBillingRequCsiDto.
     * 
     * @param endDate
     */
    public void setEndDate(java.lang.String endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the oweBusinessTypeId value for this ServiceBillingRequCsiDto.
     * 
     * @return oweBusinessTypeId
     */
    public java.lang.String getOweBusinessTypeId() {
        return oweBusinessTypeId;
    }


    /**
     * Sets the oweBusinessTypeId value for this ServiceBillingRequCsiDto.
     * 
     * @param oweBusinessTypeId
     */
    public void setOweBusinessTypeId(java.lang.String oweBusinessTypeId) {
        this.oweBusinessTypeId = oweBusinessTypeId;
    }


    /**
     * Gets the productId value for this ServiceBillingRequCsiDto.
     * 
     * @return productId
     */
    public java.lang.String getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this ServiceBillingRequCsiDto.
     * 
     * @param productId
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }


    /**
     * Gets the queryCode value for this ServiceBillingRequCsiDto.
     * 
     * @return queryCode
     */
    public java.lang.String getQueryCode() {
        return queryCode;
    }


    /**
     * Sets the queryCode value for this ServiceBillingRequCsiDto.
     * 
     * @param queryCode
     */
    public void setQueryCode(java.lang.String queryCode) {
        this.queryCode = queryCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceBillingRequCsiDto)) return false;
        ServiceBillingRequCsiDto other = (ServiceBillingRequCsiDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.beginDate==null && other.getBeginDate()==null) || 
             (this.beginDate!=null &&
              this.beginDate.equals(other.getBeginDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.oweBusinessTypeId==null && other.getOweBusinessTypeId()==null) || 
             (this.oweBusinessTypeId!=null &&
              this.oweBusinessTypeId.equals(other.getOweBusinessTypeId()))) &&
            ((this.productId==null && other.getProductId()==null) || 
             (this.productId!=null &&
              this.productId.equals(other.getProductId()))) &&
            ((this.queryCode==null && other.getQueryCode()==null) || 
             (this.queryCode!=null &&
              this.queryCode.equals(other.getQueryCode())));
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
        if (getBeginDate() != null) {
            _hashCode += getBeginDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getOweBusinessTypeId() != null) {
            _hashCode += getOweBusinessTypeId().hashCode();
        }
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getQueryCode() != null) {
            _hashCode += getQueryCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceBillingRequCsiDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingRequCsiDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endDate"));
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
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryCode"));
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
