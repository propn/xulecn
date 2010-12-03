/**
 * QueryCreditForComptDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class QueryCreditForComptDto  implements java.io.Serializable {
    private java.lang.String accNbr;

    private java.lang.String brandName;

    private java.lang.String creditAmount;

    private java.lang.String currentCreditAmount;

    private java.lang.String desc;

    private int result;

    private java.lang.String servName;

    public QueryCreditForComptDto() {
    }

    public QueryCreditForComptDto(
           java.lang.String accNbr,
           java.lang.String brandName,
           java.lang.String creditAmount,
           java.lang.String currentCreditAmount,
           java.lang.String desc,
           int result,
           java.lang.String servName) {
           this.accNbr = accNbr;
           this.brandName = brandName;
           this.creditAmount = creditAmount;
           this.currentCreditAmount = currentCreditAmount;
           this.desc = desc;
           this.result = result;
           this.servName = servName;
    }


    /**
     * Gets the accNbr value for this QueryCreditForComptDto.
     * 
     * @return accNbr
     */
    public java.lang.String getAccNbr() {
        return accNbr;
    }


    /**
     * Sets the accNbr value for this QueryCreditForComptDto.
     * 
     * @param accNbr
     */
    public void setAccNbr(java.lang.String accNbr) {
        this.accNbr = accNbr;
    }


    /**
     * Gets the brandName value for this QueryCreditForComptDto.
     * 
     * @return brandName
     */
    public java.lang.String getBrandName() {
        return brandName;
    }


    /**
     * Sets the brandName value for this QueryCreditForComptDto.
     * 
     * @param brandName
     */
    public void setBrandName(java.lang.String brandName) {
        this.brandName = brandName;
    }


    /**
     * Gets the creditAmount value for this QueryCreditForComptDto.
     * 
     * @return creditAmount
     */
    public java.lang.String getCreditAmount() {
        return creditAmount;
    }


    /**
     * Sets the creditAmount value for this QueryCreditForComptDto.
     * 
     * @param creditAmount
     */
    public void setCreditAmount(java.lang.String creditAmount) {
        this.creditAmount = creditAmount;
    }


    /**
     * Gets the currentCreditAmount value for this QueryCreditForComptDto.
     * 
     * @return currentCreditAmount
     */
    public java.lang.String getCurrentCreditAmount() {
        return currentCreditAmount;
    }


    /**
     * Sets the currentCreditAmount value for this QueryCreditForComptDto.
     * 
     * @param currentCreditAmount
     */
    public void setCurrentCreditAmount(java.lang.String currentCreditAmount) {
        this.currentCreditAmount = currentCreditAmount;
    }


    /**
     * Gets the desc value for this QueryCreditForComptDto.
     * 
     * @return desc
     */
    public java.lang.String getDesc() {
        return desc;
    }


    /**
     * Sets the desc value for this QueryCreditForComptDto.
     * 
     * @param desc
     */
    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }


    /**
     * Gets the result value for this QueryCreditForComptDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this QueryCreditForComptDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the servName value for this QueryCreditForComptDto.
     * 
     * @return servName
     */
    public java.lang.String getServName() {
        return servName;
    }


    /**
     * Sets the servName value for this QueryCreditForComptDto.
     * 
     * @param servName
     */
    public void setServName(java.lang.String servName) {
        this.servName = servName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryCreditForComptDto)) return false;
        QueryCreditForComptDto other = (QueryCreditForComptDto) obj;
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
            ((this.brandName==null && other.getBrandName()==null) || 
             (this.brandName!=null &&
              this.brandName.equals(other.getBrandName()))) &&
            ((this.creditAmount==null && other.getCreditAmount()==null) || 
             (this.creditAmount!=null &&
              this.creditAmount.equals(other.getCreditAmount()))) &&
            ((this.currentCreditAmount==null && other.getCurrentCreditAmount()==null) || 
             (this.currentCreditAmount!=null &&
              this.currentCreditAmount.equals(other.getCurrentCreditAmount()))) &&
            ((this.desc==null && other.getDesc()==null) || 
             (this.desc!=null &&
              this.desc.equals(other.getDesc()))) &&
            this.result == other.getResult() &&
            ((this.servName==null && other.getServName()==null) || 
             (this.servName!=null &&
              this.servName.equals(other.getServName())));
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
        if (getBrandName() != null) {
            _hashCode += getBrandName().hashCode();
        }
        if (getCreditAmount() != null) {
            _hashCode += getCreditAmount().hashCode();
        }
        if (getCurrentCreditAmount() != null) {
            _hashCode += getCurrentCreditAmount().hashCode();
        }
        if (getDesc() != null) {
            _hashCode += getDesc().hashCode();
        }
        _hashCode += getResult();
        if (getServName() != null) {
            _hashCode += getServName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryCreditForComptDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditForComptDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brandName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brandName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentCreditAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentCreditAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servName"));
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
