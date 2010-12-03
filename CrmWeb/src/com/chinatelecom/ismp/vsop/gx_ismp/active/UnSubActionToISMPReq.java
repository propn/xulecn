/**
 * UnSubActionToISMPReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public class UnSubActionToISMPReq  implements java.io.Serializable {
    private java.lang.String packageID;

    private java.lang.String pproductOfferID;

    private java.lang.String productID;

    private java.lang.String srcDeviceID;

    private java.lang.String srcDeviceType;

    private java.lang.String streamingNo;

    private java.lang.String userID;

    private java.lang.Integer userIDType;

    public UnSubActionToISMPReq() {
    }

    public UnSubActionToISMPReq(
           java.lang.String packageID,
           java.lang.String pproductOfferID,
           java.lang.String productID,
           java.lang.String srcDeviceID,
           java.lang.String srcDeviceType,
           java.lang.String streamingNo,
           java.lang.String userID,
           java.lang.Integer userIDType) {
           this.packageID = packageID;
           this.pproductOfferID = pproductOfferID;
           this.productID = productID;
           this.srcDeviceID = srcDeviceID;
           this.srcDeviceType = srcDeviceType;
           this.streamingNo = streamingNo;
           this.userID = userID;
           this.userIDType = userIDType;
    }


    /**
     * Gets the packageID value for this UnSubActionToISMPReq.
     * 
     * @return packageID
     */
    public java.lang.String getPackageID() {
        return packageID;
    }


    /**
     * Sets the packageID value for this UnSubActionToISMPReq.
     * 
     * @param packageID
     */
    public void setPackageID(java.lang.String packageID) {
        this.packageID = packageID;
    }


    /**
     * Gets the pproductOfferID value for this UnSubActionToISMPReq.
     * 
     * @return pproductOfferID
     */
    public java.lang.String getPproductOfferID() {
        return pproductOfferID;
    }


    /**
     * Sets the pproductOfferID value for this UnSubActionToISMPReq.
     * 
     * @param pproductOfferID
     */
    public void setPproductOfferID(java.lang.String pproductOfferID) {
        this.pproductOfferID = pproductOfferID;
    }


    /**
     * Gets the productID value for this UnSubActionToISMPReq.
     * 
     * @return productID
     */
    public java.lang.String getProductID() {
        return productID;
    }


    /**
     * Sets the productID value for this UnSubActionToISMPReq.
     * 
     * @param productID
     */
    public void setProductID(java.lang.String productID) {
        this.productID = productID;
    }


    /**
     * Gets the srcDeviceID value for this UnSubActionToISMPReq.
     * 
     * @return srcDeviceID
     */
    public java.lang.String getSrcDeviceID() {
        return srcDeviceID;
    }


    /**
     * Sets the srcDeviceID value for this UnSubActionToISMPReq.
     * 
     * @param srcDeviceID
     */
    public void setSrcDeviceID(java.lang.String srcDeviceID) {
        this.srcDeviceID = srcDeviceID;
    }


    /**
     * Gets the srcDeviceType value for this UnSubActionToISMPReq.
     * 
     * @return srcDeviceType
     */
    public java.lang.String getSrcDeviceType() {
        return srcDeviceType;
    }


    /**
     * Sets the srcDeviceType value for this UnSubActionToISMPReq.
     * 
     * @param srcDeviceType
     */
    public void setSrcDeviceType(java.lang.String srcDeviceType) {
        this.srcDeviceType = srcDeviceType;
    }


    /**
     * Gets the streamingNo value for this UnSubActionToISMPReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this UnSubActionToISMPReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the userID value for this UnSubActionToISMPReq.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this UnSubActionToISMPReq.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the userIDType value for this UnSubActionToISMPReq.
     * 
     * @return userIDType
     */
    public java.lang.Integer getUserIDType() {
        return userIDType;
    }


    /**
     * Sets the userIDType value for this UnSubActionToISMPReq.
     * 
     * @param userIDType
     */
    public void setUserIDType(java.lang.Integer userIDType) {
        this.userIDType = userIDType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UnSubActionToISMPReq)) return false;
        UnSubActionToISMPReq other = (UnSubActionToISMPReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.packageID==null && other.getPackageID()==null) || 
             (this.packageID!=null &&
              this.packageID.equals(other.getPackageID()))) &&
            ((this.pproductOfferID==null && other.getPproductOfferID()==null) || 
             (this.pproductOfferID!=null &&
              this.pproductOfferID.equals(other.getPproductOfferID()))) &&
            ((this.productID==null && other.getProductID()==null) || 
             (this.productID!=null &&
              this.productID.equals(other.getProductID()))) &&
            ((this.srcDeviceID==null && other.getSrcDeviceID()==null) || 
             (this.srcDeviceID!=null &&
              this.srcDeviceID.equals(other.getSrcDeviceID()))) &&
            ((this.srcDeviceType==null && other.getSrcDeviceType()==null) || 
             (this.srcDeviceType!=null &&
              this.srcDeviceType.equals(other.getSrcDeviceType()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID()))) &&
            ((this.userIDType==null && other.getUserIDType()==null) || 
             (this.userIDType!=null &&
              this.userIDType.equals(other.getUserIDType())));
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
        if (getPackageID() != null) {
            _hashCode += getPackageID().hashCode();
        }
        if (getPproductOfferID() != null) {
            _hashCode += getPproductOfferID().hashCode();
        }
        if (getProductID() != null) {
            _hashCode += getProductID().hashCode();
        }
        if (getSrcDeviceID() != null) {
            _hashCode += getSrcDeviceID().hashCode();
        }
        if (getSrcDeviceType() != null) {
            _hashCode += getSrcDeviceType().hashCode();
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UnSubActionToISMPReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "UnSubActionToISMPReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pproductOfferID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pproductOfferID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productID"));
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
        elemField.setFieldName("srcDeviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "srcDeviceType"));
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
