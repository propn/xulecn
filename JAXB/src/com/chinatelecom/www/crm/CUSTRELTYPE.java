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
 * �ͻ���������
 * 
 * <p>Java class for CUST_REL_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CUST_REL_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CUST_REL_ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RELATION_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUST_ID_A" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CUST_ID_B" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RELATIVE_DIRECTION" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS_CD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STATUS_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CREATE_DATE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CUST_REL_TYPE", propOrder = {
    "custrelid",
    "relationtype",
    "custida",
    "custidb",
    "relativedirection",
    "statuscd",
    "statusdate",
    "createdate"
})
public class CUSTRELTYPE {

    @XmlElement(name = "CUST_REL_ID", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String custrelid;
    @XmlElement(name = "RELATION_TYPE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String relationtype;
    @XmlElement(name = "CUST_ID_A", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String custida;
    @XmlElement(name = "CUST_ID_B", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String custidb;
    @XmlElement(name = "RELATIVE_DIRECTION", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String relativedirection;
    @XmlElement(name = "STATUS_CD", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String statuscd;
    @XmlElement(name = "STATUS_DATE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String statusdate;
    @XmlElement(name = "CREATE_DATE", namespace = "http://www.chinatelecom.com/crm/comm/")
    protected String createdate;

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

    /**
     * Gets the value of the relationtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRELATIONTYPE() {
        return relationtype;
    }

    /**
     * Sets the value of the relationtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRELATIONTYPE(String value) {
        this.relationtype = value;
    }

    /**
     * Gets the value of the custida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUSTIDA() {
        return custida;
    }

    /**
     * Sets the value of the custida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUSTIDA(String value) {
        this.custida = value;
    }

    /**
     * Gets the value of the custidb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCUSTIDB() {
        return custidb;
    }

    /**
     * Sets the value of the custidb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCUSTIDB(String value) {
        this.custidb = value;
    }

    /**
     * Gets the value of the relativedirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRELATIVEDIRECTION() {
        return relativedirection;
    }

    /**
     * Sets the value of the relativedirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRELATIVEDIRECTION(String value) {
        this.relativedirection = value;
    }

    /**
     * Gets the value of the statuscd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUSCD() {
        return statuscd;
    }

    /**
     * Sets the value of the statuscd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUSCD(String value) {
        this.statuscd = value;
    }

    /**
     * Gets the value of the statusdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATUSDATE() {
        return statusdate;
    }

    /**
     * Sets the value of the statusdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATUSDATE(String value) {
        this.statusdate = value;
    }

    /**
     * Gets the value of the createdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREATEDATE() {
        return createdate;
    }

    /**
     * Sets the value of the createdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREATEDATE(String value) {
        this.createdate = value;
    }

}