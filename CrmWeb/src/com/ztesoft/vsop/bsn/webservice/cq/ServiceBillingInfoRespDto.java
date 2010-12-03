/**
 * ServiceBillingInfoRespDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServiceBillingInfoRespDto  implements java.io.Serializable {
    private java.lang.String desc;

    private int result;

    private ServiceBillingInfoDto[] serviceBillingInfoDtoArr;

    public ServiceBillingInfoRespDto() {
    }

    public ServiceBillingInfoRespDto(
           java.lang.String desc,
           int result,
           ServiceBillingInfoDto[] serviceBillingInfoDtoArr) {
           this.desc = desc;
           this.result = result;
           this.serviceBillingInfoDtoArr = serviceBillingInfoDtoArr;
    }


    /**
     * Gets the desc value for this ServiceBillingInfoRespDto.
     * 
     * @return desc
     */
    public java.lang.String getDesc() {
        return desc;
    }


    /**
     * Sets the desc value for this ServiceBillingInfoRespDto.
     * 
     * @param desc
     */
    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }


    /**
     * Gets the result value for this ServiceBillingInfoRespDto.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this ServiceBillingInfoRespDto.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the serviceBillingInfoDtoArr value for this ServiceBillingInfoRespDto.
     * 
     * @return serviceBillingInfoDtoArr
     */
    public ServiceBillingInfoDto[] getServiceBillingInfoDtoArr() {
        return serviceBillingInfoDtoArr;
    }


    /**
     * Sets the serviceBillingInfoDtoArr value for this ServiceBillingInfoRespDto.
     * 
     * @param serviceBillingInfoDtoArr
     */
    public void setServiceBillingInfoDtoArr(ServiceBillingInfoDto[] serviceBillingInfoDtoArr) {
        this.serviceBillingInfoDtoArr = serviceBillingInfoDtoArr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceBillingInfoRespDto)) return false;
        ServiceBillingInfoRespDto other = (ServiceBillingInfoRespDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.desc==null && other.getDesc()==null) || 
             (this.desc!=null &&
              this.desc.equals(other.getDesc()))) &&
            this.result == other.getResult() &&
            ((this.serviceBillingInfoDtoArr==null && other.getServiceBillingInfoDtoArr()==null) || 
             (this.serviceBillingInfoDtoArr!=null &&
              java.util.Arrays.equals(this.serviceBillingInfoDtoArr, other.getServiceBillingInfoDtoArr())));
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
        if (getDesc() != null) {
            _hashCode += getDesc().hashCode();
        }
        _hashCode += getResult();
        if (getServiceBillingInfoDtoArr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getServiceBillingInfoDtoArr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getServiceBillingInfoDtoArr(), i);
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
        new org.apache.axis.description.TypeDesc(ServiceBillingInfoRespDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingInfoRespDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("serviceBillingInfoDtoArr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceBillingInfoDtoArr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingInfoDto"));
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
