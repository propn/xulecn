/**
 * PackageSyncFromISMPReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public class PackageSyncFromISMPReq  implements java.io.Serializable {
    private java.lang.String chargingPolicyCN;

    private java.lang.String chargingPolicyID;

    private java.lang.Integer corpOnly;

    private java.lang.Integer opFlag;

    private java.lang.Integer packageHost;

    private java.lang.String packageID;

    private java.lang.String pdesCN;

    private java.lang.String pdesEn;

    private java.lang.String pnameCN;

    private java.lang.String pnameEN;

    private java.lang.Integer presentOption;

    private com.chinatelecom.ismp.vsop.gx_ismp.ProdOfferRelation prodOfferRelation;

    private java.lang.String productID;

    private java.lang.Integer scope;

    private java.lang.Integer status;

    private java.lang.String streamingNo;

    private java.lang.Integer subOption;

    private java.lang.String systemId;

    private java.util.Calendar timeStamp;

    public PackageSyncFromISMPReq() {
    }

    public PackageSyncFromISMPReq(
           java.lang.String chargingPolicyCN,
           java.lang.String chargingPolicyID,
           java.lang.Integer corpOnly,
           java.lang.Integer opFlag,
           java.lang.Integer packageHost,
           java.lang.String packageID,
           java.lang.String pdesCN,
           java.lang.String pdesEn,
           java.lang.String pnameCN,
           java.lang.String pnameEN,
           java.lang.Integer presentOption,
           com.chinatelecom.ismp.vsop.gx_ismp.ProdOfferRelation prodOfferRelation,
           java.lang.String productID,
           java.lang.Integer scope,
           java.lang.Integer status,
           java.lang.String streamingNo,
           java.lang.Integer subOption,
           java.lang.String systemId,
           java.util.Calendar timeStamp) {
           this.chargingPolicyCN = chargingPolicyCN;
           this.chargingPolicyID = chargingPolicyID;
           this.corpOnly = corpOnly;
           this.opFlag = opFlag;
           this.packageHost = packageHost;
           this.packageID = packageID;
           this.pdesCN = pdesCN;
           this.pdesEn = pdesEn;
           this.pnameCN = pnameCN;
           this.pnameEN = pnameEN;
           this.presentOption = presentOption;
           this.prodOfferRelation = prodOfferRelation;
           this.productID = productID;
           this.scope = scope;
           this.status = status;
           this.streamingNo = streamingNo;
           this.subOption = subOption;
           this.systemId = systemId;
           this.timeStamp = timeStamp;
    }


    /**
     * Gets the chargingPolicyCN value for this PackageSyncFromISMPReq.
     * 
     * @return chargingPolicyCN
     */
    public java.lang.String getChargingPolicyCN() {
        return chargingPolicyCN;
    }


    /**
     * Sets the chargingPolicyCN value for this PackageSyncFromISMPReq.
     * 
     * @param chargingPolicyCN
     */
    public void setChargingPolicyCN(java.lang.String chargingPolicyCN) {
        this.chargingPolicyCN = chargingPolicyCN;
    }


    /**
     * Gets the chargingPolicyID value for this PackageSyncFromISMPReq.
     * 
     * @return chargingPolicyID
     */
    public java.lang.String getChargingPolicyID() {
        return chargingPolicyID;
    }


    /**
     * Sets the chargingPolicyID value for this PackageSyncFromISMPReq.
     * 
     * @param chargingPolicyID
     */
    public void setChargingPolicyID(java.lang.String chargingPolicyID) {
        this.chargingPolicyID = chargingPolicyID;
    }


    /**
     * Gets the corpOnly value for this PackageSyncFromISMPReq.
     * 
     * @return corpOnly
     */
    public java.lang.Integer getCorpOnly() {
        return corpOnly;
    }


    /**
     * Sets the corpOnly value for this PackageSyncFromISMPReq.
     * 
     * @param corpOnly
     */
    public void setCorpOnly(java.lang.Integer corpOnly) {
        this.corpOnly = corpOnly;
    }


    /**
     * Gets the opFlag value for this PackageSyncFromISMPReq.
     * 
     * @return opFlag
     */
    public java.lang.Integer getOpFlag() {
        return opFlag;
    }


    /**
     * Sets the opFlag value for this PackageSyncFromISMPReq.
     * 
     * @param opFlag
     */
    public void setOpFlag(java.lang.Integer opFlag) {
        this.opFlag = opFlag;
    }


    /**
     * Gets the packageHost value for this PackageSyncFromISMPReq.
     * 
     * @return packageHost
     */
    public java.lang.Integer getPackageHost() {
        return packageHost;
    }


    /**
     * Sets the packageHost value for this PackageSyncFromISMPReq.
     * 
     * @param packageHost
     */
    public void setPackageHost(java.lang.Integer packageHost) {
        this.packageHost = packageHost;
    }


    /**
     * Gets the packageID value for this PackageSyncFromISMPReq.
     * 
     * @return packageID
     */
    public java.lang.String getPackageID() {
        return packageID;
    }


    /**
     * Sets the packageID value for this PackageSyncFromISMPReq.
     * 
     * @param packageID
     */
    public void setPackageID(java.lang.String packageID) {
        this.packageID = packageID;
    }


    /**
     * Gets the pdesCN value for this PackageSyncFromISMPReq.
     * 
     * @return pdesCN
     */
    public java.lang.String getPdesCN() {
        return pdesCN;
    }


    /**
     * Sets the pdesCN value for this PackageSyncFromISMPReq.
     * 
     * @param pdesCN
     */
    public void setPdesCN(java.lang.String pdesCN) {
        this.pdesCN = pdesCN;
    }


    /**
     * Gets the pdesEn value for this PackageSyncFromISMPReq.
     * 
     * @return pdesEn
     */
    public java.lang.String getPdesEn() {
        return pdesEn;
    }


    /**
     * Sets the pdesEn value for this PackageSyncFromISMPReq.
     * 
     * @param pdesEn
     */
    public void setPdesEn(java.lang.String pdesEn) {
        this.pdesEn = pdesEn;
    }


    /**
     * Gets the pnameCN value for this PackageSyncFromISMPReq.
     * 
     * @return pnameCN
     */
    public java.lang.String getPnameCN() {
        return pnameCN;
    }


    /**
     * Sets the pnameCN value for this PackageSyncFromISMPReq.
     * 
     * @param pnameCN
     */
    public void setPnameCN(java.lang.String pnameCN) {
        this.pnameCN = pnameCN;
    }


    /**
     * Gets the pnameEN value for this PackageSyncFromISMPReq.
     * 
     * @return pnameEN
     */
    public java.lang.String getPnameEN() {
        return pnameEN;
    }


    /**
     * Sets the pnameEN value for this PackageSyncFromISMPReq.
     * 
     * @param pnameEN
     */
    public void setPnameEN(java.lang.String pnameEN) {
        this.pnameEN = pnameEN;
    }


    /**
     * Gets the presentOption value for this PackageSyncFromISMPReq.
     * 
     * @return presentOption
     */
    public java.lang.Integer getPresentOption() {
        return presentOption;
    }


    /**
     * Sets the presentOption value for this PackageSyncFromISMPReq.
     * 
     * @param presentOption
     */
    public void setPresentOption(java.lang.Integer presentOption) {
        this.presentOption = presentOption;
    }


    /**
     * Gets the prodOfferRelation value for this PackageSyncFromISMPReq.
     * 
     * @return prodOfferRelation
     */
    public com.chinatelecom.ismp.vsop.gx_ismp.ProdOfferRelation getProdOfferRelation() {
        return prodOfferRelation;
    }


    /**
     * Sets the prodOfferRelation value for this PackageSyncFromISMPReq.
     * 
     * @param prodOfferRelation
     */
    public void setProdOfferRelation(com.chinatelecom.ismp.vsop.gx_ismp.ProdOfferRelation prodOfferRelation) {
        this.prodOfferRelation = prodOfferRelation;
    }


    /**
     * Gets the productID value for this PackageSyncFromISMPReq.
     * 
     * @return productID
     */
    public java.lang.String getProductID() {
        return productID;
    }


    /**
     * Sets the productID value for this PackageSyncFromISMPReq.
     * 
     * @param productID
     */
    public void setProductID(java.lang.String productID) {
        this.productID = productID;
    }


    /**
     * Gets the scope value for this PackageSyncFromISMPReq.
     * 
     * @return scope
     */
    public java.lang.Integer getScope() {
        return scope;
    }


    /**
     * Sets the scope value for this PackageSyncFromISMPReq.
     * 
     * @param scope
     */
    public void setScope(java.lang.Integer scope) {
        this.scope = scope;
    }


    /**
     * Gets the status value for this PackageSyncFromISMPReq.
     * 
     * @return status
     */
    public java.lang.Integer getStatus() {
        return status;
    }


    /**
     * Sets the status value for this PackageSyncFromISMPReq.
     * 
     * @param status
     */
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }


    /**
     * Gets the streamingNo value for this PackageSyncFromISMPReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this PackageSyncFromISMPReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the subOption value for this PackageSyncFromISMPReq.
     * 
     * @return subOption
     */
    public java.lang.Integer getSubOption() {
        return subOption;
    }


    /**
     * Sets the subOption value for this PackageSyncFromISMPReq.
     * 
     * @param subOption
     */
    public void setSubOption(java.lang.Integer subOption) {
        this.subOption = subOption;
    }


    /**
     * Gets the systemId value for this PackageSyncFromISMPReq.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this PackageSyncFromISMPReq.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the timeStamp value for this PackageSyncFromISMPReq.
     * 
     * @return timeStamp
     */
    public java.util.Calendar getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this PackageSyncFromISMPReq.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.util.Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageSyncFromISMPReq)) return false;
        PackageSyncFromISMPReq other = (PackageSyncFromISMPReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.chargingPolicyCN==null && other.getChargingPolicyCN()==null) || 
             (this.chargingPolicyCN!=null &&
              this.chargingPolicyCN.equals(other.getChargingPolicyCN()))) &&
            ((this.chargingPolicyID==null && other.getChargingPolicyID()==null) || 
             (this.chargingPolicyID!=null &&
              this.chargingPolicyID.equals(other.getChargingPolicyID()))) &&
            ((this.corpOnly==null && other.getCorpOnly()==null) || 
             (this.corpOnly!=null &&
              this.corpOnly.equals(other.getCorpOnly()))) &&
            ((this.opFlag==null && other.getOpFlag()==null) || 
             (this.opFlag!=null &&
              this.opFlag.equals(other.getOpFlag()))) &&
            ((this.packageHost==null && other.getPackageHost()==null) || 
             (this.packageHost!=null &&
              this.packageHost.equals(other.getPackageHost()))) &&
            ((this.packageID==null && other.getPackageID()==null) || 
             (this.packageID!=null &&
              this.packageID.equals(other.getPackageID()))) &&
            ((this.pdesCN==null && other.getPdesCN()==null) || 
             (this.pdesCN!=null &&
              this.pdesCN.equals(other.getPdesCN()))) &&
            ((this.pdesEn==null && other.getPdesEn()==null) || 
             (this.pdesEn!=null &&
              this.pdesEn.equals(other.getPdesEn()))) &&
            ((this.pnameCN==null && other.getPnameCN()==null) || 
             (this.pnameCN!=null &&
              this.pnameCN.equals(other.getPnameCN()))) &&
            ((this.pnameEN==null && other.getPnameEN()==null) || 
             (this.pnameEN!=null &&
              this.pnameEN.equals(other.getPnameEN()))) &&
            ((this.presentOption==null && other.getPresentOption()==null) || 
             (this.presentOption!=null &&
              this.presentOption.equals(other.getPresentOption()))) &&
            ((this.prodOfferRelation==null && other.getProdOfferRelation()==null) || 
             (this.prodOfferRelation!=null &&
              this.prodOfferRelation.equals(other.getProdOfferRelation()))) &&
            ((this.productID==null && other.getProductID()==null) || 
             (this.productID!=null &&
              this.productID.equals(other.getProductID()))) &&
            ((this.scope==null && other.getScope()==null) || 
             (this.scope!=null &&
              this.scope.equals(other.getScope()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.streamingNo==null && other.getStreamingNo()==null) || 
             (this.streamingNo!=null &&
              this.streamingNo.equals(other.getStreamingNo()))) &&
            ((this.subOption==null && other.getSubOption()==null) || 
             (this.subOption!=null &&
              this.subOption.equals(other.getSubOption()))) &&
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
        if (getChargingPolicyCN() != null) {
            _hashCode += getChargingPolicyCN().hashCode();
        }
        if (getChargingPolicyID() != null) {
            _hashCode += getChargingPolicyID().hashCode();
        }
        if (getCorpOnly() != null) {
            _hashCode += getCorpOnly().hashCode();
        }
        if (getOpFlag() != null) {
            _hashCode += getOpFlag().hashCode();
        }
        if (getPackageHost() != null) {
            _hashCode += getPackageHost().hashCode();
        }
        if (getPackageID() != null) {
            _hashCode += getPackageID().hashCode();
        }
        if (getPdesCN() != null) {
            _hashCode += getPdesCN().hashCode();
        }
        if (getPdesEn() != null) {
            _hashCode += getPdesEn().hashCode();
        }
        if (getPnameCN() != null) {
            _hashCode += getPnameCN().hashCode();
        }
        if (getPnameEN() != null) {
            _hashCode += getPnameEN().hashCode();
        }
        if (getPresentOption() != null) {
            _hashCode += getPresentOption().hashCode();
        }
        if (getProdOfferRelation() != null) {
            _hashCode += getProdOfferRelation().hashCode();
        }
        if (getProductID() != null) {
            _hashCode += getProductID().hashCode();
        }
        if (getScope() != null) {
            _hashCode += getScope().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getStreamingNo() != null) {
            _hashCode += getStreamingNo().hashCode();
        }
        if (getSubOption() != null) {
            _hashCode += getSubOption().hashCode();
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
        new org.apache.axis.description.TypeDesc(PackageSyncFromISMPReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "PackageSyncFromISMPReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargingPolicyCN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chargingPolicyCN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargingPolicyID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "chargingPolicyID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("corpOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("", "corpOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("opFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "opFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageHost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageHost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdesCN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pdesCN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdesEn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pdesEn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pnameCN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pnameCN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pnameEN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pnameEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("presentOption");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presentOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodOfferRelation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodOfferRelation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProdOfferRelation"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scope");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scope"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streamingNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "streamingNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subOption");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
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
