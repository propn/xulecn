/**
 * CreateISMPUserReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.crm.req;

public class CreateISMPUserReq  implements java.io.Serializable {
    private java.lang.String cityID;

    private java.lang.String prePaidSystemID;

    private java.lang.String provinceID;

    private java.lang.String srcDeviceID;

    private java.lang.String streamingNo;

    private java.lang.String userID;

    private java.lang.String userIDType;

    private java.lang.String userName;

    private java.lang.String userPayType;

    public CreateISMPUserReq() {
    }

    public CreateISMPUserReq(
           java.lang.String cityID,
           java.lang.String prePaidSystemID,
           java.lang.String provinceID,
           java.lang.String srcDeviceID,
           java.lang.String streamingNo,
           java.lang.String userID,
           java.lang.String userIDType,
           java.lang.String userName,
           java.lang.String userPayType) {
           this.cityID = cityID;
           this.prePaidSystemID = prePaidSystemID;
           this.provinceID = provinceID;
           this.srcDeviceID = srcDeviceID;
           this.streamingNo = streamingNo;
           this.userID = userID;
           this.userIDType = userIDType;
           this.userName = userName;
           this.userPayType = userPayType;
    }


    /**
     * Gets the cityID value for this CreateISMPUserReq.
     * 
     * @return cityID
     */
    public java.lang.String getCityID() {
        return cityID;
    }


    /**
     * Sets the cityID value for this CreateISMPUserReq.
     * 
     * @param cityID
     */
    public void setCityID(java.lang.String cityID) {
        this.cityID = cityID;
    }


    /**
     * Gets the prePaidSystemID value for this CreateISMPUserReq.
     * 
     * @return prePaidSystemID
     */
    public java.lang.String getPrePaidSystemID() {
        return prePaidSystemID;
    }


    /**
     * Sets the prePaidSystemID value for this CreateISMPUserReq.
     * 
     * @param prePaidSystemID
     */
    public void setPrePaidSystemID(java.lang.String prePaidSystemID) {
        this.prePaidSystemID = prePaidSystemID;
    }


    /**
     * Gets the provinceID value for this CreateISMPUserReq.
     * 
     * @return provinceID
     */
    public java.lang.String getProvinceID() {
        return provinceID;
    }


    /**
     * Sets the provinceID value for this CreateISMPUserReq.
     * 
     * @param provinceID
     */
    public void setProvinceID(java.lang.String provinceID) {
        this.provinceID = provinceID;
    }


    /**
     * Gets the srcDeviceID value for this CreateISMPUserReq.
     * 
     * @return srcDeviceID
     */
    public java.lang.String getSrcDeviceID() {
        return srcDeviceID;
    }


    /**
     * Sets the srcDeviceID value for this CreateISMPUserReq.
     * 
     * @param srcDeviceID
     */
    public void setSrcDeviceID(java.lang.String srcDeviceID) {
        this.srcDeviceID = srcDeviceID;
    }


    /**
     * Gets the streamingNo value for this CreateISMPUserReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this CreateISMPUserReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the userID value for this CreateISMPUserReq.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this CreateISMPUserReq.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the userIDType value for this CreateISMPUserReq.
     * 
     * @return userIDType
     */
    public java.lang.String getUserIDType() {
        return userIDType;
    }


    /**
     * Sets the userIDType value for this CreateISMPUserReq.
     * 
     * @param userIDType
     */
    public void setUserIDType(java.lang.String userIDType) {
        this.userIDType = userIDType;
    }


    /**
     * Gets the userName value for this CreateISMPUserReq.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this CreateISMPUserReq.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the userPayType value for this CreateISMPUserReq.
     * 
     * @return userPayType
     */
    public java.lang.String getUserPayType() {
        return userPayType;
    }


    /**
     * Sets the userPayType value for this CreateISMPUserReq.
     * 
     * @param userPayType
     */
    public void setUserPayType(java.lang.String userPayType) {
        this.userPayType = userPayType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CreateISMPUserReq)) return false;
        CreateISMPUserReq other = (CreateISMPUserReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cityID==null && other.getCityID()==null) || 
             (this.cityID!=null &&
              this.cityID.equals(other.getCityID()))) &&
            ((this.prePaidSystemID==null && other.getPrePaidSystemID()==null) || 
             (this.prePaidSystemID!=null &&
              this.prePaidSystemID.equals(other.getPrePaidSystemID()))) &&
            ((this.provinceID==null && other.getProvinceID()==null) || 
             (this.provinceID!=null &&
              this.provinceID.equals(other.getProvinceID()))) &&
            ((this.srcDeviceID==null && other.getSrcDeviceID()==null) || 
             (this.srcDeviceID!=null &&
              this.srcDeviceID.equals(other.getSrcDeviceID()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID()))) &&
            ((this.userIDType==null && other.getUserIDType()==null) || 
             (this.userIDType!=null &&
              this.userIDType.equals(other.getUserIDType()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.userPayType==null && other.getUserPayType()==null) || 
             (this.userPayType!=null &&
              this.userPayType.equals(other.getUserPayType())));
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
        if (getCityID() != null) {
            _hashCode += getCityID().hashCode();
        }
        if (getPrePaidSystemID() != null) {
            _hashCode += getPrePaidSystemID().hashCode();
        }
        if (getProvinceID() != null) {
            _hashCode += getProvinceID().hashCode();
        }
        if (getSrcDeviceID() != null) {
            _hashCode += getSrcDeviceID().hashCode();
        }
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        if (getUserID() != null) {
            _hashCode += getUserID().hashCode();
        }
        if (getUserIDType() != null) {
            _hashCode += getUserIDType().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getUserPayType() != null) {
            _hashCode += getUserPayType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CreateISMPUserReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "createISMPUserReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cityID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cityID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prePaidSystemID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prePaidSystemID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provinceID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provinceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("srcDeviceID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "srcDeviceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userIDType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userIDType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userPayType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userPayType"));
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
