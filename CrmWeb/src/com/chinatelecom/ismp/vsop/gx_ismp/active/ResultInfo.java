/**
 * ResultInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public class ResultInfo  implements java.io.Serializable {
    private java.util.Calendar effDate;

    private java.util.Calendar expDate;

    private java.lang.String opDesc;

    private java.lang.String opResult;

    private java.lang.String productID;

    public ResultInfo() {
    }

    public ResultInfo(
           java.util.Calendar effDate,
           java.util.Calendar expDate,
           java.lang.String opDesc,
           java.lang.String opResult,
           java.lang.String productID) {
           this.effDate = effDate;
           this.expDate = expDate;
           this.opDesc = opDesc;
           this.opResult = opResult;
           this.productID = productID;
    }


    /**
     * Gets the effDate value for this ResultInfo.
     * 
     * @return effDate
     */
    public java.util.Calendar getEffDate() {
        return effDate;
    }


    /**
     * Sets the effDate value for this ResultInfo.
     * 
     * @param effDate
     */
    public void setEffDate(java.util.Calendar effDate) {
        this.effDate = effDate;
    }


    /**
     * Gets the expDate value for this ResultInfo.
     * 
     * @return expDate
     */
    public java.util.Calendar getExpDate() {
        return expDate;
    }


    /**
     * Sets the expDate value for this ResultInfo.
     * 
     * @param expDate
     */
    public void setExpDate(java.util.Calendar expDate) {
        this.expDate = expDate;
    }


    /**
     * Gets the opDesc value for this ResultInfo.
     * 
     * @return opDesc
     */
    public java.lang.String getOpDesc() {
        return opDesc;
    }


    /**
     * Sets the opDesc value for this ResultInfo.
     * 
     * @param opDesc
     */
    public void setOpDesc(java.lang.String opDesc) {
        this.opDesc = opDesc;
    }


    /**
     * Gets the opResult value for this ResultInfo.
     * 
     * @return opResult
     */
    public java.lang.String getOpResult() {
        return opResult;
    }


    /**
     * Sets the opResult value for this ResultInfo.
     * 
     * @param opResult
     */
    public void setOpResult(java.lang.String opResult) {
        this.opResult = opResult;
    }


    /**
     * Gets the productID value for this ResultInfo.
     * 
     * @return productID
     */
    public java.lang.String getProductID() {
        return productID;
    }


    /**
     * Sets the productID value for this ResultInfo.
     * 
     * @param productID
     */
    public void setProductID(java.lang.String productID) {
        this.productID = productID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResultInfo)) return false;
        ResultInfo other = (ResultInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.effDate==null && other.getEffDate()==null) || 
             (this.effDate!=null &&
              this.effDate.equals(other.getEffDate()))) &&
            ((this.expDate==null && other.getExpDate()==null) || 
             (this.expDate!=null &&
              this.expDate.equals(other.getExpDate()))) &&
            ((this.opDesc==null && other.getOpDesc()==null) || 
             (this.opDesc!=null &&
              this.opDesc.equals(other.getOpDesc()))) &&
            ((this.opResult==null && other.getOpResult()==null) || 
             (this.opResult!=null &&
              this.opResult.equals(other.getOpResult()))) &&
            ((this.productID==null && other.getProductID()==null) || 
             (this.productID!=null &&
              this.productID.equals(other.getProductID())));
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
        if (getEffDate() != null) {
            _hashCode += getEffDate().hashCode();
        }
        if (getExpDate() != null) {
            _hashCode += getExpDate().hashCode();
        }
        if (getOpDesc() != null) {
            _hashCode += getOpDesc().hashCode();
        }
        if (getOpResult() != null) {
            _hashCode += getOpResult().hashCode();
        }
        if (getProductID() != null) {
            _hashCode += getProductID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResultInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "ResultInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
