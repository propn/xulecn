//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.25 at 01:43:57 下午 CST 
//


package com.chinatelecom.www.crm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 查询客户联系信息输入
 * 
 * <p>Java class for QRY_CUST_CONTACT_INFO_REQ_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QRY_CUST_CONTACT_INFO_REQ_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PUB_REQ" type="{http://www.chinatelecom.com/crm/comm/}PUB_REQ_TYPE"/>
 *         &lt;element name="QRY_REQ" type="{http://www.chinatelecom.com/crm/comm/}QRY_REQ_TYPE" minOccurs="0"/>
 *         &lt;element name="CUST_ID" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="PROD_INST_ID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QRY_CUST_CONTACT_INFO_REQ_TYPE", propOrder = {
    "pubreq",
    "qryreq",
    "custid",
    "prodinstid"
})
public class QRYCUSTCONTACTINFOREQTYPE {

    @XmlElement(name = "PUB_REQ", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected PUBREQTYPE pubreq;
    @XmlElement(name = "QRY_REQ", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected QRYREQTYPE qryreq;
    @XmlElement(name = "CUST_ID", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected Object custid;
    @XmlElement(name = "PROD_INST_ID", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected Integer prodinstid;

    /**
     * Gets the value of the pubreq property.
     * 
     * @return
     *     possible object is
     *     {@link PUBREQTYPE }
     *     
     */
    public PUBREQTYPE getPUBREQ() {
        return pubreq;
    }

    /**
     * Sets the value of the pubreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link PUBREQTYPE }
     *     
     */
    public void setPUBREQ(PUBREQTYPE value) {
        this.pubreq = value;
    }

    /**
     * Gets the value of the qryreq property.
     * 
     * @return
     *     possible object is
     *     {@link QRYREQTYPE }
     *     
     */
    public QRYREQTYPE getQRYREQ() {
        return qryreq;
    }

    /**
     * Sets the value of the qryreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link QRYREQTYPE }
     *     
     */
    public void setQRYREQ(QRYREQTYPE value) {
        this.qryreq = value;
    }

    /**
     * Gets the value of the custid property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCUSTID() {
        return custid;
    }

    /**
     * Sets the value of the custid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCUSTID(Object value) {
        this.custid = value;
    }

    /**
     * Gets the value of the prodinstid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPRODINSTID() {
        return prodinstid;
    }

    /**
     * Sets the value of the prodinstid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPRODINSTID(Integer value) {
        this.prodinstid = value;
    }

}
