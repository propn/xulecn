/**
 * BrandDisctDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class BrandDisctDto  implements java.io.Serializable {
    private java.lang.String brandName;

    private java.lang.String disctAmount;

    private java.lang.String disctDealedAmount;

    private java.lang.String disctUndealedAmount;

    private java.lang.String overAmount;

    public BrandDisctDto() {
    }

    public BrandDisctDto(
           java.lang.String brandName,
           java.lang.String disctAmount,
           java.lang.String disctDealedAmount,
           java.lang.String disctUndealedAmount,
           java.lang.String overAmount) {
           this.brandName = brandName;
           this.disctAmount = disctAmount;
           this.disctDealedAmount = disctDealedAmount;
           this.disctUndealedAmount = disctUndealedAmount;
           this.overAmount = overAmount;
    }


    /**
     * Gets the brandName value for this BrandDisctDto.
     * 
     * @return brandName
     */
    public java.lang.String getBrandName() {
        return brandName;
    }


    /**
     * Sets the brandName value for this BrandDisctDto.
     * 
     * @param brandName
     */
    public void setBrandName(java.lang.String brandName) {
        this.brandName = brandName;
    }


    /**
     * Gets the disctAmount value for this BrandDisctDto.
     * 
     * @return disctAmount
     */
    public java.lang.String getDisctAmount() {
        return disctAmount;
    }


    /**
     * Sets the disctAmount value for this BrandDisctDto.
     * 
     * @param disctAmount
     */
    public void setDisctAmount(java.lang.String disctAmount) {
        this.disctAmount = disctAmount;
    }


    /**
     * Gets the disctDealedAmount value for this BrandDisctDto.
     * 
     * @return disctDealedAmount
     */
    public java.lang.String getDisctDealedAmount() {
        return disctDealedAmount;
    }


    /**
     * Sets the disctDealedAmount value for this BrandDisctDto.
     * 
     * @param disctDealedAmount
     */
    public void setDisctDealedAmount(java.lang.String disctDealedAmount) {
        this.disctDealedAmount = disctDealedAmount;
    }


    /**
     * Gets the disctUndealedAmount value for this BrandDisctDto.
     * 
     * @return disctUndealedAmount
     */
    public java.lang.String getDisctUndealedAmount() {
        return disctUndealedAmount;
    }


    /**
     * Sets the disctUndealedAmount value for this BrandDisctDto.
     * 
     * @param disctUndealedAmount
     */
    public void setDisctUndealedAmount(java.lang.String disctUndealedAmount) {
        this.disctUndealedAmount = disctUndealedAmount;
    }


    /**
     * Gets the overAmount value for this BrandDisctDto.
     * 
     * @return overAmount
     */
    public java.lang.String getOverAmount() {
        return overAmount;
    }


    /**
     * Sets the overAmount value for this BrandDisctDto.
     * 
     * @param overAmount
     */
    public void setOverAmount(java.lang.String overAmount) {
        this.overAmount = overAmount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BrandDisctDto)) return false;
        BrandDisctDto other = (BrandDisctDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brandName==null && other.getBrandName()==null) || 
             (this.brandName!=null &&
              this.brandName.equals(other.getBrandName()))) &&
            ((this.disctAmount==null && other.getDisctAmount()==null) || 
             (this.disctAmount!=null &&
              this.disctAmount.equals(other.getDisctAmount()))) &&
            ((this.disctDealedAmount==null && other.getDisctDealedAmount()==null) || 
             (this.disctDealedAmount!=null &&
              this.disctDealedAmount.equals(other.getDisctDealedAmount()))) &&
            ((this.disctUndealedAmount==null && other.getDisctUndealedAmount()==null) || 
             (this.disctUndealedAmount!=null &&
              this.disctUndealedAmount.equals(other.getDisctUndealedAmount()))) &&
            ((this.overAmount==null && other.getOverAmount()==null) || 
             (this.overAmount!=null &&
              this.overAmount.equals(other.getOverAmount())));
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
        if (getBrandName() != null) {
            _hashCode += getBrandName().hashCode();
        }
        if (getDisctAmount() != null) {
            _hashCode += getDisctAmount().hashCode();
        }
        if (getDisctDealedAmount() != null) {
            _hashCode += getDisctDealedAmount().hashCode();
        }
        if (getDisctUndealedAmount() != null) {
            _hashCode += getDisctUndealedAmount().hashCode();
        }
        if (getOverAmount() != null) {
            _hashCode += getOverAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BrandDisctDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BrandDisctDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brandName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disctAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disctAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disctDealedAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disctDealedAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disctUndealedAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "disctUndealedAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("overAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "overAmount"));
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
