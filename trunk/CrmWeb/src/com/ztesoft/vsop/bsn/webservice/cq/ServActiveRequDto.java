/**
 * ServActiveRequDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServActiveRequDto  implements java.io.Serializable {
    private java.lang.String accNbr;

    private java.lang.String brandId;

    private java.lang.String effDate;

    private java.lang.String usingDays;

    public ServActiveRequDto() {
    }

    public ServActiveRequDto(
           java.lang.String accNbr,
           java.lang.String brandId,
           java.lang.String effDate,
           java.lang.String usingDays) {
           this.accNbr = accNbr;
           this.brandId = brandId;
           this.effDate = effDate;
           this.usingDays = usingDays;
    }


    /**
     * Gets the accNbr value for this ServActiveRequDto.
     * 
     * @return accNbr
     */
    public java.lang.String getAccNbr() {
        return accNbr;
    }


    /**
     * Sets the accNbr value for this ServActiveRequDto.
     * 
     * @param accNbr
     */
    public void setAccNbr(java.lang.String accNbr) {
        this.accNbr = accNbr;
    }


    /**
     * Gets the brandId value for this ServActiveRequDto.
     * 
     * @return brandId
     */
    public java.lang.String getBrandId() {
        return brandId;
    }


    /**
     * Sets the brandId value for this ServActiveRequDto.
     * 
     * @param brandId
     */
    public void setBrandId(java.lang.String brandId) {
        this.brandId = brandId;
    }


    /**
     * Gets the effDate value for this ServActiveRequDto.
     * 
     * @return effDate
     */
    public java.lang.String getEffDate() {
        return effDate;
    }


    /**
     * Sets the effDate value for this ServActiveRequDto.
     * 
     * @param effDate
     */
    public void setEffDate(java.lang.String effDate) {
        this.effDate = effDate;
    }


    /**
     * Gets the usingDays value for this ServActiveRequDto.
     * 
     * @return usingDays
     */
    public java.lang.String getUsingDays() {
        return usingDays;
    }


    /**
     * Sets the usingDays value for this ServActiveRequDto.
     * 
     * @param usingDays
     */
    public void setUsingDays(java.lang.String usingDays) {
        this.usingDays = usingDays;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServActiveRequDto)) return false;
        ServActiveRequDto other = (ServActiveRequDto) obj;
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
            ((this.brandId==null && other.getBrandId()==null) || 
             (this.brandId!=null &&
              this.brandId.equals(other.getBrandId()))) &&
            ((this.effDate==null && other.getEffDate()==null) || 
             (this.effDate!=null &&
              this.effDate.equals(other.getEffDate()))) &&
            ((this.usingDays==null && other.getUsingDays()==null) || 
             (this.usingDays!=null &&
              this.usingDays.equals(other.getUsingDays())));
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
        if (getBrandId() != null) {
            _hashCode += getBrandId().hashCode();
        }
        if (getEffDate() != null) {
            _hashCode += getEffDate().hashCode();
        }
        if (getUsingDays() != null) {
            _hashCode += getUsingDays().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServActiveRequDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServActiveRequDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brandId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usingDays");
        elemField.setXmlName(new javax.xml.namespace.QName("", "usingDays"));
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
