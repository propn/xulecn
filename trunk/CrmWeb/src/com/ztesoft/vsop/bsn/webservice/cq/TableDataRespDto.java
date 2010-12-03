/**
 * TableDataRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class TableDataRespDto  implements java.io.Serializable {
    private long adjustCharge;

    private java.lang.String adjustChargeStr;

    private long afterAdjustCharge;

    private java.lang.String afterAdjustChargeStr;

    private long beforeAdjustCharge;

    private java.lang.String beforeAdjustChargeStr;

    private java.lang.String errdescription;

    private java.lang.String result;

    private TableResultDto tableResultDto;

    public TableDataRespDto() {
    }

    public TableDataRespDto(
           long adjustCharge,
           java.lang.String adjustChargeStr,
           long afterAdjustCharge,
           java.lang.String afterAdjustChargeStr,
           long beforeAdjustCharge,
           java.lang.String beforeAdjustChargeStr,
           java.lang.String errdescription,
           java.lang.String result,
           TableResultDto tableResultDto) {
           this.adjustCharge = adjustCharge;
           this.adjustChargeStr = adjustChargeStr;
           this.afterAdjustCharge = afterAdjustCharge;
           this.afterAdjustChargeStr = afterAdjustChargeStr;
           this.beforeAdjustCharge = beforeAdjustCharge;
           this.beforeAdjustChargeStr = beforeAdjustChargeStr;
           this.errdescription = errdescription;
           this.result = result;
           this.tableResultDto = tableResultDto;
    }


    /**
     * Gets the adjustCharge value for this TableDataRespDto.
     * 
     * @return adjustCharge
     */
    public long getAdjustCharge() {
        return adjustCharge;
    }


    /**
     * Sets the adjustCharge value for this TableDataRespDto.
     * 
     * @param adjustCharge
     */
    public void setAdjustCharge(long adjustCharge) {
        this.adjustCharge = adjustCharge;
    }


    /**
     * Gets the adjustChargeStr value for this TableDataRespDto.
     * 
     * @return adjustChargeStr
     */
    public java.lang.String getAdjustChargeStr() {
        return adjustChargeStr;
    }


    /**
     * Sets the adjustChargeStr value for this TableDataRespDto.
     * 
     * @param adjustChargeStr
     */
    public void setAdjustChargeStr(java.lang.String adjustChargeStr) {
        this.adjustChargeStr = adjustChargeStr;
    }


    /**
     * Gets the afterAdjustCharge value for this TableDataRespDto.
     * 
     * @return afterAdjustCharge
     */
    public long getAfterAdjustCharge() {
        return afterAdjustCharge;
    }


    /**
     * Sets the afterAdjustCharge value for this TableDataRespDto.
     * 
     * @param afterAdjustCharge
     */
    public void setAfterAdjustCharge(long afterAdjustCharge) {
        this.afterAdjustCharge = afterAdjustCharge;
    }


    /**
     * Gets the afterAdjustChargeStr value for this TableDataRespDto.
     * 
     * @return afterAdjustChargeStr
     */
    public java.lang.String getAfterAdjustChargeStr() {
        return afterAdjustChargeStr;
    }


    /**
     * Sets the afterAdjustChargeStr value for this TableDataRespDto.
     * 
     * @param afterAdjustChargeStr
     */
    public void setAfterAdjustChargeStr(java.lang.String afterAdjustChargeStr) {
        this.afterAdjustChargeStr = afterAdjustChargeStr;
    }


    /**
     * Gets the beforeAdjustCharge value for this TableDataRespDto.
     * 
     * @return beforeAdjustCharge
     */
    public long getBeforeAdjustCharge() {
        return beforeAdjustCharge;
    }


    /**
     * Sets the beforeAdjustCharge value for this TableDataRespDto.
     * 
     * @param beforeAdjustCharge
     */
    public void setBeforeAdjustCharge(long beforeAdjustCharge) {
        this.beforeAdjustCharge = beforeAdjustCharge;
    }


    /**
     * Gets the beforeAdjustChargeStr value for this TableDataRespDto.
     * 
     * @return beforeAdjustChargeStr
     */
    public java.lang.String getBeforeAdjustChargeStr() {
        return beforeAdjustChargeStr;
    }


    /**
     * Sets the beforeAdjustChargeStr value for this TableDataRespDto.
     * 
     * @param beforeAdjustChargeStr
     */
    public void setBeforeAdjustChargeStr(java.lang.String beforeAdjustChargeStr) {
        this.beforeAdjustChargeStr = beforeAdjustChargeStr;
    }


    /**
     * Gets the errdescription value for this TableDataRespDto.
     * 
     * @return errdescription
     */
    public java.lang.String getErrdescription() {
        return errdescription;
    }


    /**
     * Sets the errdescription value for this TableDataRespDto.
     * 
     * @param errdescription
     */
    public void setErrdescription(java.lang.String errdescription) {
        this.errdescription = errdescription;
    }


    /**
     * Gets the result value for this TableDataRespDto.
     * 
     * @return result
     */
    public java.lang.String getResult() {
        return result;
    }


    /**
     * Sets the result value for this TableDataRespDto.
     * 
     * @param result
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }


    /**
     * Gets the tableResultDto value for this TableDataRespDto.
     * 
     * @return tableResultDto
     */
    public TableResultDto getTableResultDto() {
        return tableResultDto;
    }


    /**
     * Sets the tableResultDto value for this TableDataRespDto.
     * 
     * @param tableResultDto
     */
    public void setTableResultDto(TableResultDto tableResultDto) {
        this.tableResultDto = tableResultDto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TableDataRespDto)) return false;
        TableDataRespDto other = (TableDataRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.adjustCharge == other.getAdjustCharge() &&
            ((this.adjustChargeStr==null && other.getAdjustChargeStr()==null) || 
             (this.adjustChargeStr!=null &&
              this.adjustChargeStr.equals(other.getAdjustChargeStr()))) &&
            this.afterAdjustCharge == other.getAfterAdjustCharge() &&
            ((this.afterAdjustChargeStr==null && other.getAfterAdjustChargeStr()==null) || 
             (this.afterAdjustChargeStr!=null &&
              this.afterAdjustChargeStr.equals(other.getAfterAdjustChargeStr()))) &&
            this.beforeAdjustCharge == other.getBeforeAdjustCharge() &&
            ((this.beforeAdjustChargeStr==null && other.getBeforeAdjustChargeStr()==null) || 
             (this.beforeAdjustChargeStr!=null &&
              this.beforeAdjustChargeStr.equals(other.getBeforeAdjustChargeStr()))) &&
            ((this.errdescription==null && other.getErrdescription()==null) || 
             (this.errdescription!=null &&
              this.errdescription.equals(other.getErrdescription()))) &&
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              this.result.equals(other.getResult()))) &&
            ((this.tableResultDto==null && other.getTableResultDto()==null) || 
             (this.tableResultDto!=null &&
              this.tableResultDto.equals(other.getTableResultDto())));
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
        _hashCode += new Long(getAdjustCharge()).hashCode();
        if (getAdjustChargeStr() != null) {
            _hashCode += getAdjustChargeStr().hashCode();
        }
        _hashCode += new Long(getAfterAdjustCharge()).hashCode();
        if (getAfterAdjustChargeStr() != null) {
            _hashCode += getAfterAdjustChargeStr().hashCode();
        }
        _hashCode += new Long(getBeforeAdjustCharge()).hashCode();
        if (getBeforeAdjustChargeStr() != null) {
            _hashCode += getBeforeAdjustChargeStr().hashCode();
        }
        if (getErrdescription() != null) {
            _hashCode += getErrdescription().hashCode();
        }
        if (getResult() != null) {
            _hashCode += getResult().hashCode();
        }
        if (getTableResultDto() != null) {
            _hashCode += getTableResultDto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TableDataRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adjustCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustChargeStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "adjustChargeStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("afterAdjustCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "afterAdjustCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("afterAdjustChargeStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "afterAdjustChargeStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beforeAdjustCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beforeAdjustCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beforeAdjustChargeStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beforeAdjustChargeStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errdescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errdescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tableResultDto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tableResultDto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableResultDto"));
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
