/**
 * QuerySubscriptionHistoryRsp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm.rsp;

public class QuerySubscriptionHistoryRsp  implements java.io.Serializable {
    private int resultCode;

    private java.lang.String streamingNo;

    private com.chinatelecom.ismp.crm.rsp.SubHistoryInfo[] subHistoryInfo;

    public QuerySubscriptionHistoryRsp() {
    }

    public QuerySubscriptionHistoryRsp(
           int resultCode,
           java.lang.String streamingNo,
           com.chinatelecom.ismp.crm.rsp.SubHistoryInfo[] subHistoryInfo) {
           this.resultCode = resultCode;
           this.streamingNo = streamingNo;
           this.subHistoryInfo = subHistoryInfo;
    }


    /**
     * Gets the resultCode value for this QuerySubscriptionHistoryRsp.
     * 
     * @return resultCode
     */
    public int getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this QuerySubscriptionHistoryRsp.
     * 
     * @param resultCode
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


    /**
     * Gets the streamingNo value for this QuerySubscriptionHistoryRsp.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this QuerySubscriptionHistoryRsp.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the subHistoryInfo value for this QuerySubscriptionHistoryRsp.
     * 
     * @return subHistoryInfo
     */
    public com.chinatelecom.ismp.crm.rsp.SubHistoryInfo[] getSubHistoryInfo() {
        return subHistoryInfo;
    }


    /**
     * Sets the subHistoryInfo value for this QuerySubscriptionHistoryRsp.
     * 
     * @param subHistoryInfo
     */
    public void setSubHistoryInfo(com.chinatelecom.ismp.crm.rsp.SubHistoryInfo[] subHistoryInfo) {
        this.subHistoryInfo = subHistoryInfo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QuerySubscriptionHistoryRsp)) return false;
        QuerySubscriptionHistoryRsp other = (QuerySubscriptionHistoryRsp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.resultCode == other.getResultCode() &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            ((this.subHistoryInfo==null && other.getSubHistoryInfo()==null) || 
             (this.subHistoryInfo!=null &&
              java.util.Arrays.equals(this.subHistoryInfo, other.getSubHistoryInfo())));
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
        _hashCode += getResultCode();
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        if (getSubHistoryInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubHistoryInfo());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubHistoryInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QuerySubscriptionHistoryRsp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "QuerySubscriptionHistoryRsp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subHistoryInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subHistoryInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://rsp.crm.ismp.chinatelecom.com", "SubHistoryInfo"));
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
