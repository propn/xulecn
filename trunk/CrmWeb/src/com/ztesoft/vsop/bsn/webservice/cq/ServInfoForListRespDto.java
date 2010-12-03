/**
 * ServInfoForListRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServInfoForListRespDto  implements java.io.Serializable {
    private java.lang.String acctId;

    private java.lang.String billingModeId;

    private java.lang.String desc;

    private java.lang.String isQueryControl;

    private int result;

    private java.lang.String servId;

    public ServInfoForListRespDto() {
    }

    public ServInfoForListRespDto(
           java.lang.String acctId,
           java.lang.String billingModeId,
           java.lang.String desc,
           java.lang.String isQueryControl,
           int result,
           java.lang.String servId) {
           this.acctId = acctId;
           this.billingModeId = billingModeId;
           this.desc = desc;
           this.isQueryControl = isQueryControl;
           this.result = result;
           this.servId = servId;
    }


    /**
     * Gets the acctId value for this ServInfoForListRespDto.
     * 
     * @return acctId
     */
    public java.lang.String getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this ServInfoForListRespDto.
     * 
     * @param acctId
     */
    public void setAcctId(java.lang.String acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the billingModeId value for this ServInfoForListRespDto.
     * 
     * @return billingModeId
     */
    public java.lang.String getBillingModeId() {
        return billingModeId;
    }


    /**
     * Sets the billingModeId value for this ServInfoForListRespDto.
     * 
     * @param billingModeId
     */
    public void setBillingModeId(java.lang.String billingModeId) {
        this.billingModeId = billingModeId;
    }


    /**
     * Gets the desc value for this ServInfoForListRespDto.
     * 
     * @return desc
     */
    public java.lang.String getDesc() {
        return desc;
    }


    /**
     * Sets the desc value for this ServInfoForListRespDto.
     * 
     * @param desc
     */
    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }


    /**
     * Gets the isQueryControl value for this ServInfoForListRespDto.
     * 
     * @return isQueryControl
     */
    public java.lang.String getIsQueryControl() {
        return isQueryControl;
    }


    /**
     * Sets the isQueryControl value for this ServInfoForListRespDto.
     * 
     * @param isQueryControl
     */
    public void setIsQueryControl(java.lang.String isQueryControl) {
        this.isQueryControl = isQueryControl;
    }


    /**
     * Gets the result value for this ServInfoForListRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this ServInfoForListRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the servId value for this ServInfoForListRespDto.
     * 
     * @return servId
     */
    public java.lang.String getServId() {
        return servId;
    }


    /**
     * Sets the servId value for this ServInfoForListRespDto.
     * 
     * @param servId
     */
    public void setServId(java.lang.String servId) {
        this.servId = servId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServInfoForListRespDto)) return false;
        ServInfoForListRespDto other = (ServInfoForListRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acctId==null && other.getAcctId()==null) || 
             (this.acctId!=null &&
              this.acctId.equals(other.getAcctId()))) &&
            ((this.billingModeId==null && other.getBillingModeId()==null) || 
             (this.billingModeId!=null &&
              this.billingModeId.equals(other.getBillingModeId()))) &&
            ((this.desc==null && other.getDesc()==null) || 
             (this.desc!=null &&
              this.desc.equals(other.getDesc()))) &&
            ((this.isQueryControl==null && other.getIsQueryControl()==null) || 
             (this.isQueryControl!=null &&
              this.isQueryControl.equals(other.getIsQueryControl()))) &&
            this.result == other.getResult() &&
            ((this.servId==null && other.getServId()==null) || 
             (this.servId!=null &&
              this.servId.equals(other.getServId())));
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
        if (getAcctId() != null) {
            _hashCode += getAcctId().hashCode();
        }
        if (getBillingModeId() != null) {
            _hashCode += getBillingModeId().hashCode();
        }
        if (getDesc() != null) {
            _hashCode += getDesc().hashCode();
        }
        if (getIsQueryControl() != null) {
            _hashCode += getIsQueryControl().hashCode();
        }
        _hashCode += getResult();
        if (getServId() != null) {
            _hashCode += getServId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServInfoForListRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServInfoForListRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingModeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "billingModeId"));
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
        elemField.setFieldName("isQueryControl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isQueryControl"));
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
        elemField.setFieldName("servId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servId"));
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
