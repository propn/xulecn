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
 * ��ѯͨ�����
 * 
 * <p>Java class for QRY_RES_TYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QRY_RES_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TOTAL_COUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAGE_SIZE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAGE_INDEX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QRY_RES_TYPE", propOrder = {
    "totalcount",
    "pagesize",
    "pageindex"
})
public class QRYRESTYPE {

    @XmlElement(name = "TOTAL_COUNT", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String totalcount;
    @XmlElement(name = "PAGE_SIZE", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String pagesize;
    @XmlElement(name = "PAGE_INDEX", namespace = "http://www.chinatelecom.com/crm/comm/", required = true)
    protected String pageindex;

    /**
     * Gets the value of the totalcount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTOTALCOUNT() {
        return totalcount;
    }

    /**
     * Sets the value of the totalcount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTOTALCOUNT(String value) {
        this.totalcount = value;
    }

    /**
     * Gets the value of the pagesize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAGESIZE() {
        return pagesize;
    }

    /**
     * Sets the value of the pagesize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAGESIZE(String value) {
        this.pagesize = value;
    }

    /**
     * Gets the value of the pageindex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAGEINDEX() {
        return pageindex;
    }

    /**
     * Sets the value of the pageindex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAGEINDEX(String value) {
        this.pageindex = value;
    }

}