/**
 * SubActionToISMPResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public class SubActionToISMPResp  implements java.io.Serializable {
    private java.lang.Integer resultCode;

    private java.lang.String resultDesc;

    private com.chinatelecom.ismp.vsop.gx_ismp.active.ResultInfo[] resultInfo;

    private java.lang.String streamingNo;

    public SubActionToISMPResp() {
    }

    public SubActionToISMPResp(
           java.lang.Integer resultCode,
           java.lang.String resultDesc,
           com.chinatelecom.ismp.vsop.gx_ismp.active.ResultInfo[] resultInfo,
           java.lang.String streamingNo) {
           this.resultCode = resultCode;
           this.resultDesc = resultDesc;
           this.resultInfo = resultInfo;
           this.streamingNo = streamingNo;
    }


    /**
     * Gets the resultCode value for this SubActionToISMPResp.
     * 
     * @return resultCode
     */
    public java.lang.Integer getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this SubActionToISMPResp.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.Integer resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the resultDesc value for this SubActionToISMPResp.
     * 
     * @return resultDesc
     */
    public java.lang.String getResultDesc() {
        return resultDesc;
    }


    /**
     * Sets the resultDesc value for this SubActionToISMPResp.
     * 
     * @param resultDesc
     */
    public void setResultDesc(java.lang.String resultDesc) {
        this.resultDesc = resultDesc;
    }


    /**
     * Gets the resultInfo value for this SubActionToISMPResp.
     * 
     * @return resultInfo
     */
    public com.chinatelecom.ismp.vsop.gx_ismp.active.ResultInfo[] getResultInfo() {
        return resultInfo;
    }


    /**
     * Sets the resultInfo value for this SubActionToISMPResp.
     * 
     * @param resultInfo
     */
    public void setResultInfo(com.chinatelecom.ismp.vsop.gx_ismp.active.ResultInfo[] resultInfo) {
        this.resultInfo = resultInfo;
    }


    /**
     * Gets the streamingNo value for this SubActionToISMPResp.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this SubActionToISMPResp.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubActionToISMPResp)) return false;
        SubActionToISMPResp other = (SubActionToISMPResp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.resultCode==null && other.getResultCode()==null) || 
             (this.resultCode!=null &&
              this.resultCode.equals(other.getResultCode()))) &&
            ((this.resultDesc==null && other.getResultDesc()==null) || 
             (this.resultDesc!=null &&
              this.resultDesc.equals(other.getResultDesc()))) &&
            ((this.resultInfo==null && other.getResultInfo()==null) || 
             (this.resultInfo!=null &&
              java.util.Arrays.equals(this.resultInfo, other.getResultInfo()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo())));
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
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        if (getResultDesc() != null) {
            _hashCode += getResultDesc().hashCode();
        }
        if (getResultInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResultInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResultInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubActionToISMPResp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "SubActionToISMPResp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://rsp.vsop.ismp.chinatelecom.com", "ResultInfo"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
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
