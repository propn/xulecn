/**
 * ServDisctDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServDisctDto  implements java.io.Serializable {
    private BrandDisctDto[] brandDisctDtoArr;

    private java.lang.String disctAmount;

    private java.lang.String disctDealedAmount;

    private java.lang.String disctUndealedAmount;

    private java.lang.String overAmount;

    private java.lang.String queryType;

    private java.lang.String totalDesc;

    public ServDisctDto() {
    }

    public ServDisctDto(
           BrandDisctDto[] brandDisctDtoArr,
           java.lang.String disctAmount,
           java.lang.String disctDealedAmount,
           java.lang.String disctUndealedAmount,
           java.lang.String overAmount,
           java.lang.String queryType,
           java.lang.String totalDesc) {
           this.brandDisctDtoArr = brandDisctDtoArr;
           this.disctAmount = disctAmount;
           this.disctDealedAmount = disctDealedAmount;
           this.disctUndealedAmount = disctUndealedAmount;
           this.overAmount = overAmount;
           this.queryType = queryType;
           this.totalDesc = totalDesc;
    }


    /**
     * Gets the brandDisctDtoArr value for this ServDisctDto.
     * 
     * @return brandDisctDtoArr
     */
    public BrandDisctDto[] getBrandDisctDtoArr() {
        return brandDisctDtoArr;
    }


    /**
     * Sets the brandDisctDtoArr value for this ServDisctDto.
     * 
     * @param brandDisctDtoArr
     */
    public void setBrandDisctDtoArr(BrandDisctDto[] brandDisctDtoArr) {
        this.brandDisctDtoArr = brandDisctDtoArr;
    }


    /**
     * Gets the disctAmount value for this ServDisctDto.
     * 
     * @return disctAmount
     */
    public java.lang.String getDisctAmount() {
        return disctAmount;
    }


    /**
     * Sets the disctAmount value for this ServDisctDto.
     * 
     * @param disctAmount
     */
    public void setDisctAmount(java.lang.String disctAmount) {
        this.disctAmount = disctAmount;
    }


    /**
     * Gets the disctDealedAmount value for this ServDisctDto.
     * 
     * @return disctDealedAmount
     */
    public java.lang.String getDisctDealedAmount() {
        return disctDealedAmount;
    }


    /**
     * Sets the disctDealedAmount value for this ServDisctDto.
     * 
     * @param disctDealedAmount
     */
    public void setDisctDealedAmount(java.lang.String disctDealedAmount) {
        this.disctDealedAmount = disctDealedAmount;
    }


    /**
     * Gets the disctUndealedAmount value for this ServDisctDto.
     * 
     * @return disctUndealedAmount
     */
    public java.lang.String getDisctUndealedAmount() {
        return disctUndealedAmount;
    }


    /**
     * Sets the disctUndealedAmount value for this ServDisctDto.
     * 
     * @param disctUndealedAmount
     */
    public void setDisctUndealedAmount(java.lang.String disctUndealedAmount) {
        this.disctUndealedAmount = disctUndealedAmount;
    }


    /**
     * Gets the overAmount value for this ServDisctDto.
     * 
     * @return overAmount
     */
    public java.lang.String getOverAmount() {
        return overAmount;
    }


    /**
     * Sets the overAmount value for this ServDisctDto.
     * 
     * @param overAmount
     */
    public void setOverAmount(java.lang.String overAmount) {
        this.overAmount = overAmount;
    }


    /**
     * Gets the queryType value for this ServDisctDto.
     * 
     * @return queryType
     */
    public java.lang.String getQueryType() {
        return queryType;
    }


    /**
     * Sets the queryType value for this ServDisctDto.
     * 
     * @param queryType
     */
    public void setQueryType(java.lang.String queryType) {
        this.queryType = queryType;
    }


    /**
     * Gets the totalDesc value for this ServDisctDto.
     * 
     * @return totalDesc
     */
    public java.lang.String getTotalDesc() {
        return totalDesc;
    }


    /**
     * Sets the totalDesc value for this ServDisctDto.
     * 
     * @param totalDesc
     */
    public void setTotalDesc(java.lang.String totalDesc) {
        this.totalDesc = totalDesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServDisctDto)) return false;
        ServDisctDto other = (ServDisctDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.brandDisctDtoArr==null && other.getBrandDisctDtoArr()==null) || 
             (this.brandDisctDtoArr!=null &&
              java.util.Arrays.equals(this.brandDisctDtoArr, other.getBrandDisctDtoArr()))) &&
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
              this.overAmount.equals(other.getOverAmount()))) &&
            ((this.queryType==null && other.getQueryType()==null) || 
             (this.queryType!=null &&
              this.queryType.equals(other.getQueryType()))) &&
            ((this.totalDesc==null && other.getTotalDesc()==null) || 
             (this.totalDesc!=null &&
              this.totalDesc.equals(other.getTotalDesc())));
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
        if (getBrandDisctDtoArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getBrandDisctDtoArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getBrandDisctDtoArr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        if (getQueryType() != null) {
            _hashCode += getQueryType().hashCode();
        }
        if (getTotalDesc() != null) {
            _hashCode += getTotalDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServDisctDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServDisctDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandDisctDtoArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brandDisctDtoArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BrandDisctDto"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totalDesc"));
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
