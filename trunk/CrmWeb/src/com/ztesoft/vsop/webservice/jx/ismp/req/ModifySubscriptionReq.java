/**
 * ModifySubscriptionReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.webservice.jx.ismp.req;

public class ModifySubscriptionReq  implements java.io.Serializable {
    private java.lang.String DA;

    private int DAType;

    private java.lang.String FA;

    private int FAType;

    private java.lang.String OA;

    private int OAType;

    private java.lang.String PProductOfferID;

    private java.lang.String[] packageID;

    private java.lang.String[] productID;

    private java.lang.String[] oldVerUserId;

    private java.lang.String[] verUserId;

    private java.lang.String srcDeviceID;

    private int srcDeviceType;

    private java.lang.String streamingNo;

    public ModifySubscriptionReq() {
    }

    public ModifySubscriptionReq(
           java.lang.String DA,
           int DAType,
           java.lang.String FA,
           int FAType,
           java.lang.String OA,
           int OAType,
           java.lang.String PProductOfferID,
           java.lang.String[] packageID,
           java.lang.String[] productID,
           java.lang.String[] oldVerUserId,
           java.lang.String[] verUserId,
           java.lang.String srcDeviceID,
           int srcDeviceType,
           java.lang.String streamingNo) {
           this.DA = DA;
           this.DAType = DAType;
           this.FA = FA;
           this.FAType = FAType;
           this.OA = OA;
           this.OAType = OAType;
           this.PProductOfferID = PProductOfferID;
           this.packageID = packageID;
           this.productID = productID;
           this.oldVerUserId = oldVerUserId;
           this.verUserId = verUserId;
           this.srcDeviceID = srcDeviceID;
           this.srcDeviceType = srcDeviceType;
           this.streamingNo = streamingNo;
    }


    /**
     * Gets the DA value for this ModifySubscriptionReq.
     * 
     * @return DA
     */
    public java.lang.String getDA() {
        return DA;
    }


    /**
     * Sets the DA value for this ModifySubscriptionReq.
     * 
     * @param DA
     */
    public void setDA(java.lang.String DA) {
        this.DA = DA;
    }


    /**
     * Gets the DAType value for this ModifySubscriptionReq.
     * 
     * @return DAType
     */
    public int getDAType() {
        return DAType;
    }


    /**
     * Sets the DAType value for this ModifySubscriptionReq.
     * 
     * @param DAType
     */
    public void setDAType(int DAType) {
        this.DAType = DAType;
    }


    /**
     * Gets the FA value for this ModifySubscriptionReq.
     * 
     * @return FA
     */
    public java.lang.String getFA() {
        return FA;
    }


    /**
     * Sets the FA value for this ModifySubscriptionReq.
     * 
     * @param FA
     */
    public void setFA(java.lang.String FA) {
        this.FA = FA;
    }


    /**
     * Gets the FAType value for this ModifySubscriptionReq.
     * 
     * @return FAType
     */
    public int getFAType() {
        return FAType;
    }


    /**
     * Sets the FAType value for this ModifySubscriptionReq.
     * 
     * @param FAType
     */
    public void setFAType(int FAType) {
        this.FAType = FAType;
    }


    /**
     * Gets the OA value for this ModifySubscriptionReq.
     * 
     * @return OA
     */
    public java.lang.String getOA() {
        return OA;
    }


    /**
     * Sets the OA value for this ModifySubscriptionReq.
     * 
     * @param OA
     */
    public void setOA(java.lang.String OA) {
        this.OA = OA;
    }


    /**
     * Gets the OAType value for this ModifySubscriptionReq.
     * 
     * @return OAType
     */
    public int getOAType() {
        return OAType;
    }


    /**
     * Sets the OAType value for this ModifySubscriptionReq.
     * 
     * @param OAType
     */
    public void setOAType(int OAType) {
        this.OAType = OAType;
    }


    /**
     * Gets the PProductOfferID value for this ModifySubscriptionReq.
     * 
     * @return PProductOfferID
     */
    public java.lang.String getPProductOfferID() {
        return PProductOfferID;
    }


    /**
     * Sets the PProductOfferID value for this ModifySubscriptionReq.
     * 
     * @param PProductOfferID
     */
    public void setPProductOfferID(java.lang.String PProductOfferID) {
        this.PProductOfferID = PProductOfferID;
    }


    /**
     * Gets the packageID value for this ModifySubscriptionReq.
     * 
     * @return packageID
     */
    public java.lang.String[] getPackageID() {
        return packageID;
    }


    /**
     * Sets the packageID value for this ModifySubscriptionReq.
     * 
     * @param packageID
     */
    public void setPackageID(java.lang.String[] packageID) {
        this.packageID = packageID;
    }

    public java.lang.String getPackageID(int i) {
        return this.packageID[i];
    }

    public void setPackageID(int i, java.lang.String _value) {
        this.packageID[i] = _value;
    }


    /**
     * Gets the productID value for this ModifySubscriptionReq.
     * 
     * @return productID
     */
    public java.lang.String[] getProductID() {
        return productID;
    }


    /**
     * Sets the productID value for this ModifySubscriptionReq.
     * 
     * @param productID
     */
    public void setProductID(java.lang.String[] productID) {
        this.productID = productID;
    }

    public java.lang.String getProductID(int i) {
        return this.productID[i];
    }

    public void setProductID(int i, java.lang.String _value) {
        this.productID[i] = _value;
    }


    /**
     * Gets the oldVerUserId value for this ModifySubscriptionReq.
     * 
     * @return oldVerUserId
     */
    public java.lang.String[] getOldVerUserId() {
        return oldVerUserId;
    }


    /**
     * Sets the oldVerUserId value for this ModifySubscriptionReq.
     * 
     * @param oldVerUserId
     */
    public void setOldVerUserId(java.lang.String[] oldVerUserId) {
        this.oldVerUserId = oldVerUserId;
    }

    public java.lang.String getOldVerUserId(int i) {
        return this.oldVerUserId[i];
    }

    public void setOldVerUserId(int i, java.lang.String _value) {
        this.oldVerUserId[i] = _value;
    }


    /**
     * Gets the verUserId value for this ModifySubscriptionReq.
     * 
     * @return verUserId
     */
    public java.lang.String[] getVerUserId() {
        return verUserId;
    }


    /**
     * Sets the verUserId value for this ModifySubscriptionReq.
     * 
     * @param verUserId
     */
    public void setVerUserId(java.lang.String[] verUserId) {
        this.verUserId = verUserId;
    }

    public java.lang.String getVerUserId(int i) {
        return this.verUserId[i];
    }

    public void setVerUserId(int i, java.lang.String _value) {
        this.verUserId[i] = _value;
    }


    /**
     * Gets the srcDeviceID value for this ModifySubscriptionReq.
     * 
     * @return srcDeviceID
     */
    public java.lang.String getSrcDeviceID() {
        return srcDeviceID;
    }


    /**
     * Sets the srcDeviceID value for this ModifySubscriptionReq.
     * 
     * @param srcDeviceID
     */
    public void setSrcDeviceID(java.lang.String srcDeviceID) {
        this.srcDeviceID = srcDeviceID;
    }


    /**
     * Gets the srcDeviceType value for this ModifySubscriptionReq.
     * 
     * @return srcDeviceType
     */
    public int getSrcDeviceType() {
        return srcDeviceType;
    }


    /**
     * Sets the srcDeviceType value for this ModifySubscriptionReq.
     * 
     * @param srcDeviceType
     */
    public void setSrcDeviceType(int srcDeviceType) {
        this.srcDeviceType = srcDeviceType;
    }


    /**
     * Gets the streamingNo value for this ModifySubscriptionReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this ModifySubscriptionReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ModifySubscriptionReq)) return false;
        ModifySubscriptionReq other = (ModifySubscriptionReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.DA==null && other.getDA()==null) || 
             (this.DA!=null &&
              this.DA.equals(other.getDA()))) &&
            this.DAType == other.getDAType() &&
            ((this.FA==null && other.getFA()==null) || 
             (this.FA!=null &&
              this.FA.equals(other.getFA()))) &&
            this.FAType == other.getFAType() &&
            ((this.OA==null && other.getOA()==null) || 
             (this.OA!=null &&
              this.OA.equals(other.getOA()))) &&
            this.OAType == other.getOAType() &&
            ((this.PProductOfferID==null && other.getPProductOfferID()==null) || 
             (this.PProductOfferID!=null &&
              this.PProductOfferID.equals(other.getPProductOfferID()))) &&
            ((this.packageID==null && other.getPackageID()==null) || 
             (this.packageID!=null &&
              java.util.Arrays.equals(this.packageID, other.getPackageID()))) &&
            ((this.productID==null && other.getProductID()==null) || 
             (this.productID!=null &&
              java.util.Arrays.equals(this.productID, other.getProductID()))) &&
            ((this.oldVerUserId==null && other.getOldVerUserId()==null) || 
             (this.oldVerUserId!=null &&
              java.util.Arrays.equals(this.oldVerUserId, other.getOldVerUserId()))) &&
            ((this.verUserId==null && other.getVerUserId()==null) || 
             (this.verUserId!=null &&
              java.util.Arrays.equals(this.verUserId, other.getVerUserId()))) &&
            ((this.srcDeviceID==null && other.getSrcDeviceID()==null) || 
             (this.srcDeviceID!=null &&
              this.srcDeviceID.equals(other.getSrcDeviceID()))) &&
            this.srcDeviceType == other.getSrcDeviceType() &&
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
        if (getDA() != null) {
            _hashCode += getDA().hashCode();
        }
        _hashCode += getDAType();
        if (getFA() != null) {
            _hashCode += getFA().hashCode();
        }
        _hashCode += getFAType();
        if (getOA() != null) {
            _hashCode += getOA().hashCode();
        }
        _hashCode += getOAType();
        if (getPProductOfferID() != null) {
            _hashCode += getPProductOfferID().hashCode();
        }
        if (getPackageID() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPackageID());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPackageID(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProductID() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProductID());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProductID(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getOldVerUserId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOldVerUserId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOldVerUserId(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVerUserId() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVerUserId());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVerUserId(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSrcDeviceID() != null) {
            _hashCode += getSrcDeviceID().hashCode();
        }
        _hashCode += getSrcDeviceType();
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ModifySubscriptionReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.crm.ismp.chinatelecom.com", "ModifySubscriptionReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DAType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DAType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FAType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FAType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OAType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OAType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PProductOfferID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PProductOfferID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oldVerUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oldVerUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VerUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
