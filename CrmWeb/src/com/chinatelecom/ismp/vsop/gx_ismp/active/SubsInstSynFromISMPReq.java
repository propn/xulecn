/**
 * SubsInstSynFromISMPReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

public class SubsInstSynFromISMPReq  implements java.io.Serializable {
    private java.lang.String oldProductID;

    private java.lang.String oldpackageID;

    private java.lang.String opType;

    private java.lang.String packageID;

    private java.lang.String productID;

    private java.lang.String streamingNo;

    private java.lang.String userID;

    private java.lang.String userIDType;

    private java.lang.String verUserId;

    public SubsInstSynFromISMPReq() {
    }

    public SubsInstSynFromISMPReq(
           java.lang.String oldProductID,
           java.lang.String oldpackageID,
           java.lang.String opType,
           java.lang.String packageID,
           java.lang.String productID,
           java.lang.String streamingNo,
           java.lang.String userID,
           java.lang.String userIDType,
           java.lang.String verUserId) {
           this.oldProductID = oldProductID;
           this.oldpackageID = oldpackageID;
           this.opType = opType;
           this.packageID = packageID;
           this.productID = productID;
           this.streamingNo = streamingNo;
           this.userID = userID;
           this.userIDType = userIDType;
           this.verUserId = verUserId;
    }


    /**
     * Gets the oldProductID value for this SubsInstSynFromISMPReq.
     * 
     * @return oldProductID
     */
    public java.lang.String getOldProductID() {
        return oldProductID;
    }


    /**
     * Sets the oldProductID value for this SubsInstSynFromISMPReq.
     * 
     * @param oldProductID
     */
    public void setOldProductID(java.lang.String oldProductID) {
        this.oldProductID = oldProductID;
    }


    /**
     * Gets the oldpackageID value for this SubsInstSynFromISMPReq.
     * 
     * @return oldpackageID
     */
    public java.lang.String getOldpackageID() {
        return oldpackageID;
    }


    /**
     * Sets the oldpackageID value for this SubsInstSynFromISMPReq.
     * 
     * @param oldpackageID
     */
    public void setOldpackageID(java.lang.String oldpackageID) {
        this.oldpackageID = oldpackageID;
    }


    /**
     * Gets the opType value for this SubsInstSynFromISMPReq.
     * 
     * @return opType
     */
    public java.lang.String getOpType() {
        return opType;
    }


    /**
     * Sets the opType value for this SubsInstSynFromISMPReq.
     * 
     * @param opType
     */
    public void setOpType(java.lang.String opType) {
        this.opType = opType;
    }


    /**
     * Gets the packageID value for this SubsInstSynFromISMPReq.
     * 
     * @return packageID
     */
    public java.lang.String getPackageID() {
        return packageID;
    }


    /**
     * Sets the packageID value for this SubsInstSynFromISMPReq.
     * 
     * @param packageID
     */
    public void setPackageID(java.lang.String packageID) {
        this.packageID = packageID;
    }


    /**
     * Gets the productID value for this SubsInstSynFromISMPReq.
     * 
     * @return productID
     */
    public java.lang.String getProductID() {
        return productID;
    }


    /**
     * Sets the productID value for this SubsInstSynFromISMPReq.
     * 
     * @param productID
     */
    public void setProductID(java.lang.String productID) {
        this.productID = productID;
    }


    /**
     * Gets the streamingNo value for this SubsInstSynFromISMPReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this SubsInstSynFromISMPReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the userID value for this SubsInstSynFromISMPReq.
     * 
     * @return userID
     */
    public java.lang.String getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this SubsInstSynFromISMPReq.
     * 
     * @param userID
     */
    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }


    /**
     * Gets the userIDType value for this SubsInstSynFromISMPReq.
     * 
     * @return userIDType
     */
    public java.lang.String getUserIDType() {
        return userIDType;
    }


    /**
     * Sets the userIDType value for this SubsInstSynFromISMPReq.
     * 
     * @param userIDType
     */
    public void setUserIDType(java.lang.String userIDType) {
        this.userIDType = userIDType;
    }


    /**
     * Gets the verUserId value for this SubsInstSynFromISMPReq.
     * 
     * @return verUserId
     */
    public java.lang.String getVerUserId() {
        return verUserId;
    }


    /**
     * Sets the verUserId value for this SubsInstSynFromISMPReq.
     * 
     * @param verUserId
     */
    public void setVerUserId(java.lang.String verUserId) {
        this.verUserId = verUserId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SubsInstSynFromISMPReq)) return false;
        SubsInstSynFromISMPReq other = (SubsInstSynFromISMPReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oldProductID==null && other.getOldProductID()==null) || 
             (this.oldProductID!=null &&
              this.oldProductID.equals(other.getOldProductID()))) &&
            ((this.oldpackageID==null && other.getOldpackageID()==null) || 
             (this.oldpackageID!=null &&
              this.oldpackageID.equals(other.getOldpackageID()))) &&
            ((this.opType==null && other.getOpType()==null) || 
             (this.opType!=null &&
              this.opType.equals(other.getOpType()))) &&
            ((this.packageID==null && other.getPackageID()==null) || 
             (this.packageID!=null &&
              this.packageID.equals(other.getPackageID()))) &&
            ((this.productID==null && other.getProductID()==null) || 
             (this.productID!=null &&
              this.productID.equals(other.getProductID()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            ((this.userID==null && other.getUserID()==null) || 
             (this.userID!=null &&
              this.userID.equals(other.getUserID()))) &&
            ((this.userIDType==null && other.getUserIDType()==null) || 
             (this.userIDType!=null &&
              this.userIDType.equals(other.getUserIDType()))) &&
            ((this.verUserId==null && other.getVerUserId()==null) || 
             (this.verUserId!=null &&
              this.verUserId.equals(other.getVerUserId())));
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
        if (getOldProductID() != null) {
            _hashCode += getOldProductID().hashCode();
        }
        if (getOldpackageID() != null) {
            _hashCode += getOldpackageID().hashCode();
        }
        if (getOpType() != null) {
            _hashCode += getOpType().hashCode();
        }
        if (getPackageID() != null) {
            _hashCode += getPackageID().hashCode();
        }
        if (getProductID() != null) {
            _hashCode += getProductID().hashCode();
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
        if (getVerUserId() != null) {
            _hashCode += getVerUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubsInstSynFromISMPReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "SubsInstSynFromISMPReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldProductID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oldProductID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldpackageID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oldpackageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageID"));
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
        elemField.setFieldName("verUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "verUserId"));
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
