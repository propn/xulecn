//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.01.25 at 01:43:57 ���� CST 
//


package com.chinatelecom.www.crm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ɾ���ͻ���ϵ����
 * 
 * <p>Java class for DEL_CUST_REL_REQ_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DEL_CUST_REL_REQ_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PUB_REQ" type="{http://www.chinatelecom.com/crm/comm/}PUB_REQ_TYPE"/>
 *         &lt;element name="CUST_REL_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DEL_CUST_REL_REQ_TYPE", propOrder = {
    "pubreq",
    "custrelid"
})
public class DELCUSTRELREQTYPE {

    @XmlElement(name = "PUB_REQ", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected PUBREQTYPE pubreq;
    @XmlElement(name = "CUST_REL_ID", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String custrelid;

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
     * Gets the value of the custrelid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUSTRELID() {
        return custrelid;
    }

    /**
     * Sets the value of the custrelid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUSTRELID(String value) {
        this.custrelid = value;
    }

}