/**
 * ServiceBillingInfoDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class ServiceBillingInfoDto  implements java.io.Serializable {
    private java.lang.String accNbr;

    private java.lang.String createDate;

    private java.lang.String errInfo;

    private java.lang.String oweBusinessTypeName;

    private java.lang.String serialNo;

    private java.lang.String servId;

    private java.lang.String staffCode;

    private java.lang.String staffName;

    private java.lang.String state;

    public ServiceBillingInfoDto() {
    }

    public ServiceBillingInfoDto(
           java.lang.String accNbr,
           java.lang.String createDate,
           java.lang.String errInfo,
           java.lang.String oweBusinessTypeName,
           java.lang.String serialNo,
           java.lang.String servId,
           java.lang.String staffCode,
           java.lang.String staffName,
           java.lang.String state) {
           this.accNbr = accNbr;
           this.createDate = createDate;
           this.errInfo = errInfo;
           this.oweBusinessTypeName = oweBusinessTypeName;
           this.serialNo = serialNo;
           this.servId = servId;
           this.staffCode = staffCode;
           this.staffName = staffName;
           this.state = state;
    }


    /**
     * Gets the accNbr value for this ServiceBillingInfoDto.
     * 
     * @return accNbr
     */
    public java.lang.String getAccNbr() {
        return accNbr;
    }


    /**
     * Sets the accNbr value for this ServiceBillingInfoDto.
     * 
     * @param accNbr
     */
    public void setAccNbr(java.lang.String accNbr) {
        this.accNbr = accNbr;
    }


    /**
     * Gets the createDate value for this ServiceBillingInfoDto.
     * 
     * @return createDate
     */
    public java.lang.String getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this ServiceBillingInfoDto.
     * 
     * @param createDate
     */
    public void setCreateDate(java.lang.String createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the errInfo value for this ServiceBillingInfoDto.
     * 
     * @return errInfo
     */
    public java.lang.String getErrInfo() {
        return errInfo;
    }


    /**
     * Sets the errInfo value for this ServiceBillingInfoDto.
     * 
     * @param errInfo
     */
    public void setErrInfo(java.lang.String errInfo) {
        this.errInfo = errInfo;
    }


    /**
     * Gets the oweBusinessTypeName value for this ServiceBillingInfoDto.
     * 
     * @return oweBusinessTypeName
     */
    public java.lang.String getOweBusinessTypeName() {
        return oweBusinessTypeName;
    }


    /**
     * Sets the oweBusinessTypeName value for this ServiceBillingInfoDto.
     * 
     * @param oweBusinessTypeName
     */
    public void setOweBusinessTypeName(java.lang.String oweBusinessTypeName) {
        this.oweBusinessTypeName = oweBusinessTypeName;
    }


    /**
     * Gets the serialNo value for this ServiceBillingInfoDto.
     * 
     * @return serialNo
     */
    public java.lang.String getSerialNo() {
        return serialNo;
    }


    /**
     * Sets the serialNo value for this ServiceBillingInfoDto.
     * 
     * @param serialNo
     */
    public void setSerialNo(java.lang.String serialNo) {
        this.serialNo = serialNo;
    }


    /**
     * Gets the servId value for this ServiceBillingInfoDto.
     * 
     * @return servId
     */
    public java.lang.String getServId() {
        return servId;
    }


    /**
     * Sets the servId value for this ServiceBillingInfoDto.
     * 
     * @param servId
     */
    public void setServId(java.lang.String servId) {
        this.servId = servId;
    }


    /**
     * Gets the staffCode value for this ServiceBillingInfoDto.
     * 
     * @return staffCode
     */
    public java.lang.String getStaffCode() {
        return staffCode;
    }


    /**
     * Sets the staffCode value for this ServiceBillingInfoDto.
     * 
     * @param staffCode
     */
    public void setStaffCode(java.lang.String staffCode) {
        this.staffCode = staffCode;
    }


    /**
     * Gets the staffName value for this ServiceBillingInfoDto.
     * 
     * @return staffName
     */
    public java.lang.String getStaffName() {
        return staffName;
    }


    /**
     * Sets the staffName value for this ServiceBillingInfoDto.
     * 
     * @param staffName
     */
    public void setStaffName(java.lang.String staffName) {
        this.staffName = staffName;
    }


    /**
     * Gets the state value for this ServiceBillingInfoDto.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this ServiceBillingInfoDto.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceBillingInfoDto)) return false;
        ServiceBillingInfoDto other = (ServiceBillingInfoDto) obj;
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
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.errInfo==null && other.getErrInfo()==null) || 
             (this.errInfo!=null &&
              this.errInfo.equals(other.getErrInfo()))) &&
            ((this.oweBusinessTypeName==null && other.getOweBusinessTypeName()==null) || 
             (this.oweBusinessTypeName!=null &&
              this.oweBusinessTypeName.equals(other.getOweBusinessTypeName()))) &&
            ((this.serialNo==null && other.getSerialNo()==null) || 
             (this.serialNo!=null &&
              this.serialNo.equals(other.getSerialNo()))) &&
            ((this.servId==null && other.getServId()==null) || 
             (this.servId!=null &&
              this.servId.equals(other.getServId()))) &&
            ((this.staffCode==null && other.getStaffCode()==null) || 
             (this.staffCode!=null &&
              this.staffCode.equals(other.getStaffCode()))) &&
            ((this.staffName==null && other.getStaffName()==null) || 
             (this.staffName!=null &&
              this.staffName.equals(other.getStaffName()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState())));
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
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getErrInfo() != null) {
            _hashCode += getErrInfo().hashCode();
        }
        if (getOweBusinessTypeName() != null) {
            _hashCode += getOweBusinessTypeName().hashCode();
        }
        if (getSerialNo() != null) {
            _hashCode += getSerialNo().hashCode();
        }
        if (getServId() != null) {
            _hashCode += getServId().hashCode();
        }
        if (getStaffCode() != null) {
            _hashCode += getStaffCode().hashCode();
        }
        if (getStaffName() != null) {
            _hashCode += getStaffName().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceBillingInfoDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingInfoDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accNbr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accNbr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oweBusinessTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oweBusinessTypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serialNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("staffCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "staffCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("staffName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "staffName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "state"));
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
