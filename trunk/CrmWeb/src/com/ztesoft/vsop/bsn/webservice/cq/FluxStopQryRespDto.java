/**
 * FluxStopQryRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class FluxStopQryRespDto  implements java.io.Serializable {
    private java.lang.String custName;

    private java.lang.String retCode;

    private java.lang.String retDesc;

    private java.lang.String stopTime;

    private java.lang.String useState;

    public FluxStopQryRespDto() {
    }

    public FluxStopQryRespDto(
           java.lang.String custName,
           java.lang.String retCode,
           java.lang.String retDesc,
           java.lang.String stopTime,
           java.lang.String useState) {
           this.custName = custName;
           this.retCode = retCode;
           this.retDesc = retDesc;
           this.stopTime = stopTime;
           this.useState = useState;
    }


    /**
     * Gets the custName value for this FluxStopQryRespDto.
     * 
     * @return custName
     */
    public java.lang.String getCustName() {
        return custName;
    }


    /**
     * Sets the custName value for this FluxStopQryRespDto.
     * 
     * @param custName
     */
    public void setCustName(java.lang.String custName) {
        this.custName = custName;
    }


    /**
     * Gets the retCode value for this FluxStopQryRespDto.
     * 
     * @return retCode
     */
    public java.lang.String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this FluxStopQryRespDto.
     * 
     * @param retCode
     */
    public void setRetCode(java.lang.String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retDesc value for this FluxStopQryRespDto.
     * 
     * @return retDesc
     */
    public java.lang.String getRetDesc() {
        return retDesc;
    }


    /**
     * Sets the retDesc value for this FluxStopQryRespDto.
     * 
     * @param retDesc
     */
    public void setRetDesc(java.lang.String retDesc) {
        this.retDesc = retDesc;
    }


    /**
     * Gets the stopTime value for this FluxStopQryRespDto.
     * 
     * @return stopTime
     */
    public java.lang.String getStopTime() {
        return stopTime;
    }


    /**
     * Sets the stopTime value for this FluxStopQryRespDto.
     * 
     * @param stopTime
     */
    public void setStopTime(java.lang.String stopTime) {
        this.stopTime = stopTime;
    }


    /**
     * Gets the useState value for this FluxStopQryRespDto.
     * 
     * @return useState
     */
    public java.lang.String getUseState() {
        return useState;
    }


    /**
     * Sets the useState value for this FluxStopQryRespDto.
     * 
     * @param useState
     */
    public void setUseState(java.lang.String useState) {
        this.useState = useState;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FluxStopQryRespDto)) return false;
        FluxStopQryRespDto other = (FluxStopQryRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custName==null && other.getCustName()==null) || 
             (this.custName!=null &&
              this.custName.equals(other.getCustName()))) &&
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.retDesc==null && other.getRetDesc()==null) || 
             (this.retDesc!=null &&
              this.retDesc.equals(other.getRetDesc()))) &&
            ((this.stopTime==null && other.getStopTime()==null) || 
             (this.stopTime!=null &&
              this.stopTime.equals(other.getStopTime()))) &&
            ((this.useState==null && other.getUseState()==null) || 
             (this.useState!=null &&
              this.useState.equals(other.getUseState())));
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
        if (getCustName() != null) {
            _hashCode += getCustName().hashCode();
        }
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getRetDesc() != null) {
            _hashCode += getRetDesc().hashCode();
        }
        if (getStopTime() != null) {
            _hashCode += getStopTime().hashCode();
        }
        if (getUseState() != null) {
            _hashCode += getUseState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FluxStopQryRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "FluxStopQryRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "custName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stopTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stopTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "useState"));
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
