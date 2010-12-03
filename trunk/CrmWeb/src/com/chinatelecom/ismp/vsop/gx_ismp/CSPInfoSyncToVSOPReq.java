/**
 * CSPInfoSyncToVSOPReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public class CSPInfoSyncToVSOPReq  implements java.io.Serializable {
    private java.lang.Integer opFlag;

    private com.chinatelecom.ismp.vsop.gx_ismp.SPInfo spInfo;

    private java.lang.String streamingNo;

    private java.lang.String systemId;

    private java.util.Calendar timeStamp;

    public CSPInfoSyncToVSOPReq() {
    }

    public CSPInfoSyncToVSOPReq(
           java.lang.Integer opFlag,
           com.chinatelecom.ismp.vsop.gx_ismp.SPInfo spInfo,
           java.lang.String streamingNo,
           java.lang.String systemId,
           java.util.Calendar timeStamp) {
           this.opFlag = opFlag;
           this.spInfo = spInfo;
           this.streamingNo = streamingNo;
           this.systemId = systemId;
           this.timeStamp = timeStamp;
    }


    /**
     * Gets the opFlag value for this CSPInfoSyncToVSOPReq.
     * 
     * @return opFlag
     */
    public java.lang.Integer getOpFlag() {
        return opFlag;
    }


    /**
     * Sets the opFlag value for this CSPInfoSyncToVSOPReq.
     * 
     * @param opFlag
     */
    public void setOpFlag(java.lang.Integer opFlag) {
        this.opFlag = opFlag;
    }


    /**
     * Gets the spInfo value for this CSPInfoSyncToVSOPReq.
     * 
     * @return spInfo
     */
    public com.chinatelecom.ismp.vsop.gx_ismp.SPInfo getSpInfo() {
        return spInfo;
    }


    /**
     * Sets the spInfo value for this CSPInfoSyncToVSOPReq.
     * 
     * @param spInfo
     */
    public void setSpInfo(com.chinatelecom.ismp.vsop.gx_ismp.SPInfo spInfo) {
        this.spInfo = spInfo;
    }


    /**
     * Gets the streamingNo value for this CSPInfoSyncToVSOPReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this CSPInfoSyncToVSOPReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the systemId value for this CSPInfoSyncToVSOPReq.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this CSPInfoSyncToVSOPReq.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the timeStamp value for this CSPInfoSyncToVSOPReq.
     * 
     * @return timeStamp
     */
    public java.util.Calendar getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this CSPInfoSyncToVSOPReq.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.util.Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CSPInfoSyncToVSOPReq)) return false;
        CSPInfoSyncToVSOPReq other = (CSPInfoSyncToVSOPReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.opFlag==null && other.getOpFlag()==null) || 
             (this.opFlag!=null &&
              this.opFlag.equals(other.getOpFlag()))) &&
            ((this.spInfo==null && other.getSpInfo()==null) || 
             (this.spInfo!=null &&
              this.spInfo.equals(other.getSpInfo()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            ((this.systemId==null && other.getSystemId()==null) || 
             (this.systemId!=null &&
              this.systemId.equals(other.getSystemId()))) &&
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp())));
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
        if (getOpFlag() != null) {
            _hashCode += getOpFlag().hashCode();
        }
        if (getSpInfo() != null) {
            _hashCode += getSpInfo().hashCode();
        }
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        if (getSystemId() != null) {
            _hashCode += getSystemId().hashCode();
        }
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CSPInfoSyncToVSOPReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "CSPInfoSyncToVSOPReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "SPInfo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("systemId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "systemId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
