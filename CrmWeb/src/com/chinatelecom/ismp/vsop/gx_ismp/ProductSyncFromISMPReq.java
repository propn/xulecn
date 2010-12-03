/**
 * ProductSyncFromISMPReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp;

public class ProductSyncFromISMPReq  implements java.io.Serializable {
    private java.lang.String chargingPolicyCN;

    private java.lang.String chargingPolicyID;

    private java.lang.Integer corpOnly;

    private java.lang.Integer opFlag;

    private java.lang.Integer packageOnly;

    private java.lang.String pdescriptionCN;

    private java.lang.String pdescriptionEn;

    private java.lang.String platForm;

    private java.lang.String pnameCN;

    private java.lang.String pnameEN;

    private java.lang.Integer present;

    private com.chinatelecom.ismp.vsop.gx_ismp.ProdRelation prodRelation;

    private java.lang.Integer productHost;

    private java.lang.String productID;

    private com.chinatelecom.ismp.vsop.gx_ismp.ProductOPCode[] productOPCode;

    private java.lang.Integer scope;

    private java.lang.String serviceCapabilityID;

    private java.lang.Integer settlementCycle;

    private java.lang.Integer settlementPayType;

    private java.lang.String settlementPercent;

    private java.lang.String spID;

    private java.lang.Integer status;

    private java.lang.String streamingNo;

    private java.lang.Integer subOption;

    private java.lang.String systemId;

    private java.util.Calendar timeStamp;

    public ProductSyncFromISMPReq() {
    }

    public ProductSyncFromISMPReq(
           java.lang.String chargingPolicyCN,
           java.lang.String chargingPolicyID,
           java.lang.Integer corpOnly,
           java.lang.Integer opFlag,
           java.lang.Integer packageOnly,
           java.lang.String pdescriptionCN,
           java.lang.String pdescriptionEn,
           java.lang.String platForm,
           java.lang.String pnameCN,
           java.lang.String pnameEN,
           java.lang.Integer present,
           com.chinatelecom.ismp.vsop.gx_ismp.ProdRelation prodRelation,
           java.lang.Integer productHost,
           java.lang.String productID,
           com.chinatelecom.ismp.vsop.gx_ismp.ProductOPCode[] productOPCode,
           java.lang.Integer scope,
           java.lang.String serviceCapabilityID,
           java.lang.Integer settlementCycle,
           java.lang.Integer settlementPayType,
           java.lang.String settlementPercent,
           java.lang.String spID,
           java.lang.Integer status,
           java.lang.String streamingNo,
           java.lang.Integer subOption,
           java.lang.String systemId,
           java.util.Calendar timeStamp) {
           this.chargingPolicyCN = chargingPolicyCN;
           this.chargingPolicyID = chargingPolicyID;
           this.corpOnly = corpOnly;
           this.opFlag = opFlag;
           this.packageOnly = packageOnly;
           this.pdescriptionCN = pdescriptionCN;
           this.pdescriptionEn = pdescriptionEn;
           this.platForm = platForm;
           this.pnameCN = pnameCN;
           this.pnameEN = pnameEN;
           this.present = present;
           this.prodRelation = prodRelation;
           this.productHost = productHost;
           this.productID = productID;
           this.productOPCode = productOPCode;
           this.scope = scope;
           this.serviceCapabilityID = serviceCapabilityID;
           this.settlementCycle = settlementCycle;
           this.settlementPayType = settlementPayType;
           this.settlementPercent = settlementPercent;
           this.spID = spID;
           this.status = status;
           this.streamingNo = streamingNo;
           this.subOption = subOption;
           this.systemId = systemId;
           this.timeStamp = timeStamp;
    }


    /**
     * Gets the chargingPolicyCN value for this ProductSyncFromISMPReq.
     * 
     * @return chargingPolicyCN
     */
    public java.lang.String getChargingPolicyCN() {
        return chargingPolicyCN;
    }


    /**
     * Sets the chargingPolicyCN value for this ProductSyncFromISMPReq.
     * 
     * @param chargingPolicyCN
     */
    public void setChargingPolicyCN(java.lang.String chargingPolicyCN) {
        this.chargingPolicyCN = chargingPolicyCN;
    }


    /**
     * Gets the chargingPolicyID value for this ProductSyncFromISMPReq.
     * 
     * @return chargingPolicyID
     */
    public java.lang.String getChargingPolicyID() {
        return chargingPolicyID;
    }


    /**
     * Sets the chargingPolicyID value for this ProductSyncFromISMPReq.
     * 
     * @param chargingPolicyID
     */
    public void setChargingPolicyID(java.lang.String chargingPolicyID) {
        this.chargingPolicyID = chargingPolicyID;
    }


    /**
     * Gets the corpOnly value for this ProductSyncFromISMPReq.
     * 
     * @return corpOnly
     */
    public java.lang.Integer getCorpOnly() {
        return corpOnly;
    }


    /**
     * Sets the corpOnly value for this ProductSyncFromISMPReq.
     * 
     * @param corpOnly
     */
    public void setCorpOnly(java.lang.Integer corpOnly) {
        this.corpOnly = corpOnly;
    }


    /**
     * Gets the opFlag value for this ProductSyncFromISMPReq.
     * 
     * @return opFlag
     */
    public java.lang.Integer getOpFlag() {
        return opFlag;
    }


    /**
     * Sets the opFlag value for this ProductSyncFromISMPReq.
     * 
     * @param opFlag
     */
    public void setOpFlag(java.lang.Integer opFlag) {
        this.opFlag = opFlag;
    }


    /**
     * Gets the packageOnly value for this ProductSyncFromISMPReq.
     * 
     * @return packageOnly
     */
    public java.lang.Integer getPackageOnly() {
        return packageOnly;
    }


    /**
     * Sets the packageOnly value for this ProductSyncFromISMPReq.
     * 
     * @param packageOnly
     */
    public void setPackageOnly(java.lang.Integer packageOnly) {
        this.packageOnly = packageOnly;
    }


    /**
     * Gets the pdescriptionCN value for this ProductSyncFromISMPReq.
     * 
     * @return pdescriptionCN
     */
    public java.lang.String getPdescriptionCN() {
        return pdescriptionCN;
    }


    /**
     * Sets the pdescriptionCN value for this ProductSyncFromISMPReq.
     * 
     * @param pdescriptionCN
     */
    public void setPdescriptionCN(java.lang.String pdescriptionCN) {
        this.pdescriptionCN = pdescriptionCN;
    }


    /**
     * Gets the pdescriptionEn value for this ProductSyncFromISMPReq.
     * 
     * @return pdescriptionEn
     */
    public java.lang.String getPdescriptionEn() {
        return pdescriptionEn;
    }


    /**
     * Sets the pdescriptionEn value for this ProductSyncFromISMPReq.
     * 
     * @param pdescriptionEn
     */
    public void setPdescriptionEn(java.lang.String pdescriptionEn) {
        this.pdescriptionEn = pdescriptionEn;
    }


    /**
     * Gets the platForm value for this ProductSyncFromISMPReq.
     * 
     * @return platForm
     */
    public java.lang.String getPlatForm() {
        return platForm;
    }


    /**
     * Sets the platForm value for this ProductSyncFromISMPReq.
     * 
     * @param platForm
     */
    public void setPlatForm(java.lang.String platForm) {
        this.platForm = platForm;
    }


    /**
     * Gets the pnameCN value for this ProductSyncFromISMPReq.
     * 
     * @return pnameCN
     */
    public java.lang.String getPnameCN() {
        return pnameCN;
    }


    /**
     * Sets the pnameCN value for this ProductSyncFromISMPReq.
     * 
     * @param pnameCN
     */
    public void setPnameCN(java.lang.String pnameCN) {
        this.pnameCN = pnameCN;
    }


    /**
     * Gets the pnameEN value for this ProductSyncFromISMPReq.
     * 
     * @return pnameEN
     */
    public java.lang.String getPnameEN() {
        return pnameEN;
    }


    /**
     * Sets the pnameEN value for this ProductSyncFromISMPReq.
     * 
     * @param pnameEN
     */
    public void setPnameEN(java.lang.String pnameEN) {
        this.pnameEN = pnameEN;
    }


    /**
     * Gets the present value for this ProductSyncFromISMPReq.
     * 
     * @return present
     */
    public java.lang.Integer getPresent() {
        return present;
    }


    /**
     * Sets the present value for this ProductSyncFromISMPReq.
     * 
     * @param present
     */
    public void setPresent(java.lang.Integer present) {
        this.present = present;
    }


    /**
     * Gets the prodRelation value for this ProductSyncFromISMPReq.
     * 
     * @return prodRelation
     */
    public com.chinatelecom.ismp.vsop.gx_ismp.ProdRelation getProdRelation() {
        return prodRelation;
    }


    /**
     * Sets the prodRelation value for this ProductSyncFromISMPReq.
     * 
     * @param prodRelation
     */
    public void setProdRelation(com.chinatelecom.ismp.vsop.gx_ismp.ProdRelation prodRelation) {
        this.prodRelation = prodRelation;
    }


    /**
     * Gets the productHost value for this ProductSyncFromISMPReq.
     * 
     * @return productHost
     */
    public java.lang.Integer getProductHost() {
        return productHost;
    }


    /**
     * Sets the productHost value for this ProductSyncFromISMPReq.
     * 
     * @param productHost
     */
    public void setProductHost(java.lang.Integer productHost) {
        this.productHost = productHost;
    }


    /**
     * Gets the productID value for this ProductSyncFromISMPReq.
     * 
     * @return productID
     */
    public java.lang.String getProductID() {
        return productID;
    }


    /**
     * Sets the productID value for this ProductSyncFromISMPReq.
     * 
     * @param productID
     */
    public void setProductID(java.lang.String productID) {
        this.productID = productID;
    }


    /**
     * Gets the productOPCode value for this ProductSyncFromISMPReq.
     * 
     * @return productOPCode
     */
    public com.chinatelecom.ismp.vsop.gx_ismp.ProductOPCode[] getProductOPCode() {
        return productOPCode;
    }


    /**
     * Sets the productOPCode value for this ProductSyncFromISMPReq.
     * 
     * @param productOPCode
     */
    public void setProductOPCode(com.chinatelecom.ismp.vsop.gx_ismp.ProductOPCode[] productOPCode) {
        this.productOPCode = productOPCode;
    }


    /**
     * Gets the scope value for this ProductSyncFromISMPReq.
     * 
     * @return scope
     */
    public java.lang.Integer getScope() {
        return scope;
    }


    /**
     * Sets the scope value for this ProductSyncFromISMPReq.
     * 
     * @param scope
     */
    public void setScope(java.lang.Integer scope) {
        this.scope = scope;
    }


    /**
     * Gets the serviceCapabilityID value for this ProductSyncFromISMPReq.
     * 
     * @return serviceCapabilityID
     */
    public java.lang.String getServiceCapabilityID() {
        return serviceCapabilityID;
    }


    /**
     * Sets the serviceCapabilityID value for this ProductSyncFromISMPReq.
     * 
     * @param serviceCapabilityID
     */
    public void setServiceCapabilityID(java.lang.String serviceCapabilityID) {
        this.serviceCapabilityID = serviceCapabilityID;
    }


    /**
     * Gets the settlementCycle value for this ProductSyncFromISMPReq.
     * 
     * @return settlementCycle
     */
    public java.lang.Integer getSettlementCycle() {
        return settlementCycle;
    }


    /**
     * Sets the settlementCycle value for this ProductSyncFromISMPReq.
     * 
     * @param settlementCycle
     */
    public void setSettlementCycle(java.lang.Integer settlementCycle) {
        this.settlementCycle = settlementCycle;
    }


    /**
     * Gets the settlementPayType value for this ProductSyncFromISMPReq.
     * 
     * @return settlementPayType
     */
    public java.lang.Integer getSettlementPayType() {
        return settlementPayType;
    }


    /**
     * Sets the settlementPayType value for this ProductSyncFromISMPReq.
     * 
     * @param settlementPayType
     */
    public void setSettlementPayType(java.lang.Integer settlementPayType) {
        this.settlementPayType = settlementPayType;
    }


    /**
     * Gets the settlementPercent value for this ProductSyncFromISMPReq.
     * 
     * @return settlementPercent
     */
    public java.lang.String getSettlementPercent() {
        return settlementPercent;
    }


    /**
     * Sets the settlementPercent value for this ProductSyncFromISMPReq.
     * 
     * @param settlementPercent
     */
    public void setSettlementPercent(java.lang.String settlementPercent) {
        this.settlementPercent = settlementPercent;
    }


    /**
     * Gets the spID value for this ProductSyncFromISMPReq.
     * 
     * @return spID
     */
    public java.lang.String getSpID() {
        return spID;
    }


    /**
     * Sets the spID value for this ProductSyncFromISMPReq.
     * 
     * @param spID
     */
    public void setSpID(java.lang.String spID) {
        this.spID = spID;
    }


    /**
     * Gets the status value for this ProductSyncFromISMPReq.
     * 
     * @return status
     */
    public java.lang.Integer getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ProductSyncFromISMPReq.
     * 
     * @param status
     */
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }


    /**
     * Gets the streamingNo value for this ProductSyncFromISMPReq.
     * 
     * @return streamingNo
     */
    public java.lang.String getStreamingNo() {
        return streamingNo;
    }


    /**
     * Sets the streamingNo value for this ProductSyncFromISMPReq.
     * 
     * @param streamingNo
     */
    public void setStreamingNo(java.lang.String streamingNo) {
        this.streamingNo = streamingNo;
    }


    /**
     * Gets the subOption value for this ProductSyncFromISMPReq.
     * 
     * @return subOption
     */
    public java.lang.Integer getSubOption() {
        return subOption;
    }


    /**
     * Sets the subOption value for this ProductSyncFromISMPReq.
     * 
     * @param subOption
     */
    public void setSubOption(java.lang.Integer subOption) {
        this.subOption = subOption;
    }


    /**
     * Gets the systemId value for this ProductSyncFromISMPReq.
     * 
     * @return systemId
     */
    public java.lang.String getSystemId() {
        return systemId;
    }


    /**
     * Sets the systemId value for this ProductSyncFromISMPReq.
     * 
     * @param systemId
     */
    public void setSystemId(java.lang.String systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the timeStamp value for this ProductSyncFromISMPReq.
     * 
     * @return timeStamp
     */
    public java.util.Calendar getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this ProductSyncFromISMPReq.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.util.Calendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProductSyncFromISMPReq)) return false;
        ProductSyncFromISMPReq other = (ProductSyncFromISMPReq) obj;
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
            ((this.packageOnly==null && other.getPackageOnly()==null) || 
             (this.packageOnly!=null &&
              this.packageOnly.equals(other.getPackageOnly()))) &&
            ((this.pdescriptionCN==null && other.getPdescriptionCN()==null) || 
             (this.pdescriptionCN!=null &&
              this.pdescriptionCN.equals(other.getPdescriptionCN()))) &&
            ((this.pdescriptionEn==null && other.getPdescriptionEn()==null) || 
             (this.pdescriptionEn!=null &&
              this.pdescriptionEn.equals(other.getPdescriptionEn()))) &&
            ((this.platForm==null && other.getPlatForm()==null) || 
             (this.platForm!=null &&
              this.platForm.equals(other.getPlatForm()))) &&
            ((this.pnameCN==null && other.getPnameCN()==null) || 
             (this.pnameCN!=null &&
              this.pnameCN.equals(other.getPnameCN()))) &&
            ((this.pnameEN==null && other.getPnameEN()==null) || 
             (this.pnameEN!=null &&
              this.pnameEN.equals(other.getPnameEN()))) &&
            ((this.present==null && other.getPresent()==null) || 
             (this.present!=null &&
              this.present.equals(other.getPresent()))) &&
            ((this.prodRelation==null && other.getProdRelation()==null) || 
             (this.prodRelation!=null &&
              this.prodRelation.equals(other.getProdRelation()))) &&
            ((this.productHost==null && other.getProductHost()==null) || 
             (this.productHost!=null &&
              this.productHost.equals(other.getProductHost()))) &&
            ((this.productID==null && other.getProductID()==null) || 
             (this.productID!=null &&
              this.productID.equals(other.getProductID()))) &&
            ((this.productOPCode==null && other.getProductOPCode()==null) || 
             (this.productOPCode!=null &&
              java.util.Arrays.equals(this.productOPCode, other.getProductOPCode()))) &&
            ((this.scope==null && other.getScope()==null) || 
             (this.scope!=null &&
              this.scope.equals(other.getScope()))) &&
            ((this.serviceCapabilityID==null && other.getServiceCapabilityID()==null) || 
             (this.serviceCapabilityID!=null &&
              this.serviceCapabilityID.equals(other.getServiceCapabilityID()))) &&
            ((this.settlementCycle==null && other.getSettlementCycle()==null) || 
             (this.settlementCycle!=null &&
              this.settlementCycle.equals(other.getSettlementCycle()))) &&
            ((this.settlementPayType==null && other.getSettlementPayType()==null) || 
             (this.settlementPayType!=null &&
              this.settlementPayType.equals(other.getSettlementPayType()))) &&
            ((this.settlementPercent==null && other.getSettlementPercent()==null) || 
             (this.settlementPercent!=null &&
              this.settlementPercent.equals(other.getSettlementPercent()))) &&
            ((this.spID==null && other.getSpID()==null) || 
             (this.spID!=null &&
              this.spID.equals(other.getSpID()))) &&
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
        if (getPackageOnly() != null) {
            _hashCode += getPackageOnly().hashCode();
        }
        if (getPdescriptionCN() != null) {
            _hashCode += getPdescriptionCN().hashCode();
        }
        if (getPdescriptionEn() != null) {
            _hashCode += getPdescriptionEn().hashCode();
        }
        if (getPlatForm() != null) {
            _hashCode += getPlatForm().hashCode();
        }
        if (getPnameCN() != null) {
            _hashCode += getPnameCN().hashCode();
        }
        if (getPnameEN() != null) {
            _hashCode += getPnameEN().hashCode();
        }
        if (getPresent() != null) {
            _hashCode += getPresent().hashCode();
        }
        if (getProdRelation() != null) {
            _hashCode += getProdRelation().hashCode();
        }
        if (getProductHost() != null) {
            _hashCode += getProductHost().hashCode();
        }
        if (getProductID() != null) {
            _hashCode += getProductID().hashCode();
        }
        if (getProductOPCode() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProductOPCode());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProductOPCode(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getScope() != null) {
            _hashCode += getScope().hashCode();
        }
        if (getServiceCapabilityID() != null) {
            _hashCode += getServiceCapabilityID().hashCode();
        }
        if (getSettlementCycle() != null) {
            _hashCode += getSettlementCycle().hashCode();
        }
        if (getSettlementPayType() != null) {
            _hashCode += getSettlementPayType().hashCode();
        }
        if (getSettlementPercent() != null) {
            _hashCode += getSettlementPercent().hashCode();
        }
        if (getSpID() != null) {
            _hashCode += getSpID().hashCode();
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
        new org.apache.axis.description.TypeDesc(ProductSyncFromISMPReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProductSyncFromISMPReq"));
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
        elemField.setFieldName("packageOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdescriptionCN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pdescriptionCN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdescriptionEn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pdescriptionEn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("platForm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "platForm"));
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
        elemField.setFieldName("present");
        elemField.setXmlName(new javax.xml.namespace.QName("", "present"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodRelation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodRelation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProdRelation"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productHost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productHost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productOPCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productOPCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://req.vsop.ismp.chinatelecom.com", "ProductOPCode"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scope");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scope"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCapabilityID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceCapabilityID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settlementCycle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settlementCycle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settlementPayType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settlementPayType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settlementPercent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settlementPercent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
