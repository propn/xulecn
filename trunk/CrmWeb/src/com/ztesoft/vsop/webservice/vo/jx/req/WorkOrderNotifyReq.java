/**
 * WorkOrderNotifyReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:58 LKT)
 */
package com.ztesoft.vsop.webservice.vo.jx.req;


/**
 *  WorkOrderNotifyReq bean class
 */
public class WorkOrderNotifyReq implements org.apache.axis2.databinding.ADBBean {
    /**
     * field for StreamingNo
     */
    protected java.lang.String localStreamingNo;

    /**
     * field for OrderNO
     */
    protected java.lang.String localOrderNO;

    /**
     * field for ConstNo
     */
    protected java.lang.String localConstNo;

    /**
     * field for ConstTime
     */
    protected java.lang.String localConstTime;

    /**
     * field for Linkman
     */
    protected java.lang.String localLinkman;

    /**
     * field for LinkPh
     */
    protected java.lang.String localLinkPh;

    /**
     * field for CustName
     */
    protected java.lang.String localCustName;

    /**
     * field for CustnNum
     */
    protected java.lang.String localCustnNum;

    /**
     * field for ConNo
     */
    protected java.lang.String localConNo;

    /**
     * field for ActionFlag
     */
    protected int localActionFlag;

    /**
     * field for SvcBound
     */
    protected int localSvcBound;

    /**
     * field for CN2Ip
     */
    protected java.lang.String localCN2Ip;

    /**
     * field for ZipBizId
     */
    protected java.lang.String localZipBizId;

    /**
     * field for EqProvince
     */
    protected java.lang.String localEqProvince;

    /**
     * field for SmsNotCallbUrl
     */
    protected java.lang.String localSmsNotCallbUrl;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSmsNotCallbUrlTracker = false;

    /**
     * field for SmsStCallbUrl
     */
    protected java.lang.String localSmsStCallbUrl;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSmsStCallbUrlTracker = false;

    /**
     * field for MmsNotCallbUrl
     */
    protected java.lang.String localMmsNotCallbUrl;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localMmsNotCallbUrlTracker = false;

    /**
     * field for MmsStCallbUrl
     */
    protected java.lang.String localMmsStCallbUrl;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localMmsStCallbUrlTracker = false;

    /**
     * field for WapPStCallbUrl
     */
    protected java.lang.String localWapPStCallbUrl;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localWapPStCallbUrlTracker = false;

    /* This type was generated from the piece of schema that had
       name = WorkOrderNotifyReq
       Namespace URI = http://req.crm.ismp.chinatelecom.com
       Namespace Prefix = ns1
     */
    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("http://req.crm.ismp.chinatelecom.com")) {
            return "ns1";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStreamingNo() {
        return localStreamingNo;
    }

    /**
     * Auto generated setter method
     * @param param StreamingNo
     */
    public void setStreamingNo(java.lang.String param) {
        this.localStreamingNo = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getOrderNO() {
        return localOrderNO;
    }

    /**
     * Auto generated setter method
     * @param param OrderNO
     */
    public void setOrderNO(java.lang.String param) {
        this.localOrderNO = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getConstNo() {
        return localConstNo;
    }

    /**
     * Auto generated setter method
     * @param param ConstNo
     */
    public void setConstNo(java.lang.String param) {
        this.localConstNo = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getConstTime() {
        return localConstTime;
    }

    /**
     * Auto generated setter method
     * @param param ConstTime
     */
    public void setConstTime(java.lang.String param) {
        this.localConstTime = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getLinkman() {
        return localLinkman;
    }

    /**
     * Auto generated setter method
     * @param param Linkman
     */
    public void setLinkman(java.lang.String param) {
        this.localLinkman = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getLinkPh() {
        return localLinkPh;
    }

    /**
     * Auto generated setter method
     * @param param LinkPh
     */
    public void setLinkPh(java.lang.String param) {
        this.localLinkPh = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCustName() {
        return localCustName;
    }

    /**
     * Auto generated setter method
     * @param param CustName
     */
    public void setCustName(java.lang.String param) {
        this.localCustName = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCustnNum() {
        return localCustnNum;
    }

    /**
     * Auto generated setter method
     * @param param CustnNum
     */
    public void setCustnNum(java.lang.String param) {
        this.localCustnNum = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getConNo() {
        return localConNo;
    }

    /**
     * Auto generated setter method
     * @param param ConNo
     */
    public void setConNo(java.lang.String param) {
        this.localConNo = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getActionFlag() {
        return localActionFlag;
    }

    /**
     * Auto generated setter method
     * @param param ActionFlag
     */
    public void setActionFlag(int param) {
        this.localActionFlag = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getSvcBound() {
        return localSvcBound;
    }

    /**
     * Auto generated setter method
     * @param param SvcBound
     */
    public void setSvcBound(int param) {
        this.localSvcBound = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCN2Ip() {
        return localCN2Ip;
    }

    /**
     * Auto generated setter method
     * @param param CN2Ip
     */
    public void setCN2Ip(java.lang.String param) {
        this.localCN2Ip = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getZipBizId() {
        return localZipBizId;
    }

    /**
     * Auto generated setter method
     * @param param ZipBizId
     */
    public void setZipBizId(java.lang.String param) {
        this.localZipBizId = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getEqProvince() {
        return localEqProvince;
    }

    /**
     * Auto generated setter method
     * @param param EqProvince
     */
    public void setEqProvince(java.lang.String param) {
        this.localEqProvince = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSmsNotCallbUrl() {
        return localSmsNotCallbUrl;
    }

    /**
     * Auto generated setter method
     * @param param SmsNotCallbUrl
     */
    public void setSmsNotCallbUrl(java.lang.String param) {
        if (param != null) {
            //update the setting tracker
            localSmsNotCallbUrlTracker = true;
        } else {
            localSmsNotCallbUrlTracker = true;
        }

        this.localSmsNotCallbUrl = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSmsStCallbUrl() {
        return localSmsStCallbUrl;
    }

    /**
     * Auto generated setter method
     * @param param SmsStCallbUrl
     */
    public void setSmsStCallbUrl(java.lang.String param) {
        if (param != null) {
            //update the setting tracker
            localSmsStCallbUrlTracker = true;
        } else {
            localSmsStCallbUrlTracker = true;
        }

        this.localSmsStCallbUrl = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getMmsNotCallbUrl() {
        return localMmsNotCallbUrl;
    }

    /**
     * Auto generated setter method
     * @param param MmsNotCallbUrl
     */
    public void setMmsNotCallbUrl(java.lang.String param) {
        if (param != null) {
            //update the setting tracker
            localMmsNotCallbUrlTracker = true;
        } else {
            localMmsNotCallbUrlTracker = true;
        }

        this.localMmsNotCallbUrl = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getMmsStCallbUrl() {
        return localMmsStCallbUrl;
    }

    /**
     * Auto generated setter method
     * @param param MmsStCallbUrl
     */
    public void setMmsStCallbUrl(java.lang.String param) {
        if (param != null) {
            //update the setting tracker
            localMmsStCallbUrlTracker = true;
        } else {
            localMmsStCallbUrlTracker = true;
        }

        this.localMmsStCallbUrl = param;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getWapPStCallbUrl() {
        return localWapPStCallbUrl;
    }

    /**
     * Auto generated setter method
     * @param param WapPStCallbUrl
     */
    public void setWapPStCallbUrl(java.lang.String param) {
        if (param != null) {
            //update the setting tracker
            localWapPStCallbUrlTracker = true;
        } else {
            localWapPStCallbUrlTracker = true;
        }

        this.localWapPStCallbUrl = param;
    }

    /**
     * isReaderMTOMAware
     * @return true if the reader supports MTOM
     */
    public static boolean isReaderMTOMAware(
        javax.xml.stream.XMLStreamReader reader) {
        boolean isReaderMTOMAware = false;

        try {
            isReaderMTOMAware = java.lang.Boolean.TRUE.equals(reader.getProperty(
                        org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
        } catch (java.lang.IllegalArgumentException e) {
            isReaderMTOMAware = false;
        }

        return isReaderMTOMAware;
    }

    /**
     *
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(
        final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {
        org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(this,
                parentQName) {
                public void serialize(
                    org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
                    throws javax.xml.stream.XMLStreamException {
                    WorkOrderNotifyReq.this.serialize(parentQName, factory,
                        xmlWriter);
                }
            };

        return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName,
            factory, dataSource);
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory,
        org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();

        if (namespace != null) {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace,
                    parentQName.getLocalPart());
            } else {
                if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, parentQName.getLocalPart(),
                    namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        } else {
            xmlWriter.writeStartElement(parentQName.getLocalPart());
        }

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "streamingNo", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "streamingNo");
            }
        } else {
            xmlWriter.writeStartElement("streamingNo");
        }

        if (localStreamingNo == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localStreamingNo);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "orderNO", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "orderNO");
            }
        } else {
            xmlWriter.writeStartElement("orderNO");
        }

        if (localOrderNO == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localOrderNO);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "constNo", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "constNo");
            }
        } else {
            xmlWriter.writeStartElement("constNo");
        }

        if (localConstNo == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localConstNo);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "constTime", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "constTime");
            }
        } else {
            xmlWriter.writeStartElement("constTime");
        }

        if (localConstTime == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localConstTime);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "linkman", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "linkman");
            }
        } else {
            xmlWriter.writeStartElement("linkman");
        }

        if (localLinkman == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localLinkman);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "linkPh", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "linkPh");
            }
        } else {
            xmlWriter.writeStartElement("linkPh");
        }

        if (localLinkPh == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localLinkPh);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "custName", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "custName");
            }
        } else {
            xmlWriter.writeStartElement("custName");
        }

        if (localCustName == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localCustName);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "custnNum", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "custnNum");
            }
        } else {
            xmlWriter.writeStartElement("custnNum");
        }

        if (localCustnNum == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localCustnNum);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "conNo", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "conNo");
            }
        } else {
            xmlWriter.writeStartElement("conNo");
        }

        if (localConNo == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localConNo);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "actionFlag", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "actionFlag");
            }
        } else {
            xmlWriter.writeStartElement("actionFlag");
        }

        if (localActionFlag == java.lang.Integer.MIN_VALUE) {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localActionFlag));
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "svcBound", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "svcBound");
            }
        } else {
            xmlWriter.writeStartElement("svcBound");
        }

        if (localSvcBound == java.lang.Integer.MIN_VALUE) {
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSvcBound));
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "cN2Ip", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "cN2Ip");
            }
        } else {
            xmlWriter.writeStartElement("cN2Ip");
        }

        if (localCN2Ip == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localCN2Ip);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "zipBizId", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "zipBizId");
            }
        } else {
            xmlWriter.writeStartElement("zipBizId");
        }

        if (localZipBizId == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localZipBizId);
        }

        xmlWriter.writeEndElement();

        namespace = "";

        if (!namespace.equals("")) {
            prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                xmlWriter.writeStartElement(prefix, "eqProvince", namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            } else {
                xmlWriter.writeStartElement(namespace, "eqProvince");
            }
        } else {
            xmlWriter.writeStartElement("eqProvince");
        }

        if (localEqProvince == null) {
            // write the nil attribute
            writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance",
                "nil", "1", xmlWriter);
        } else {
            xmlWriter.writeCharacters(localEqProvince);
        }

        xmlWriter.writeEndElement();

        if (localSmsNotCallbUrlTracker) {
            namespace = "";

            if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "smsNotCallbUrl",
                        namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                } else {
                    xmlWriter.writeStartElement(namespace, "smsNotCallbUrl");
                }
            } else {
                xmlWriter.writeStartElement("smsNotCallbUrl");
            }

            if (localSmsNotCallbUrl == null) {
                // write the nil attribute
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "nil", "1",
                    xmlWriter);
            } else {
                xmlWriter.writeCharacters(localSmsNotCallbUrl);
            }

            xmlWriter.writeEndElement();
        }

        if (localSmsStCallbUrlTracker) {
            namespace = "";

            if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "smsStCallbUrl",
                        namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                } else {
                    xmlWriter.writeStartElement(namespace, "smsStCallbUrl");
                }
            } else {
                xmlWriter.writeStartElement("smsStCallbUrl");
            }

            if (localSmsStCallbUrl == null) {
                // write the nil attribute
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "nil", "1",
                    xmlWriter);
            } else {
                xmlWriter.writeCharacters(localSmsStCallbUrl);
            }

            xmlWriter.writeEndElement();
        }

        if (localMmsNotCallbUrlTracker) {
            namespace = "";

            if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "mmsNotCallbUrl",
                        namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                } else {
                    xmlWriter.writeStartElement(namespace, "mmsNotCallbUrl");
                }
            } else {
                xmlWriter.writeStartElement("mmsNotCallbUrl");
            }

            if (localMmsNotCallbUrl == null) {
                // write the nil attribute
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "nil", "1",
                    xmlWriter);
            } else {
                xmlWriter.writeCharacters(localMmsNotCallbUrl);
            }

            xmlWriter.writeEndElement();
        }

        if (localMmsStCallbUrlTracker) {
            namespace = "";

            if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "mmsStCallbUrl",
                        namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                } else {
                    xmlWriter.writeStartElement(namespace, "mmsStCallbUrl");
                }
            } else {
                xmlWriter.writeStartElement("mmsStCallbUrl");
            }

            if (localMmsStCallbUrl == null) {
                // write the nil attribute
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "nil", "1",
                    xmlWriter);
            } else {
                xmlWriter.writeCharacters(localMmsStCallbUrl);
            }

            xmlWriter.writeEndElement();
        }

        if (localWapPStCallbUrlTracker) {
            namespace = "";

            if (!namespace.equals("")) {
                prefix = xmlWriter.getPrefix(namespace);

                if (prefix == null) {
                    prefix = generatePrefix(namespace);

                    xmlWriter.writeStartElement(prefix, "wapPStCallbUrl",
                        namespace);
                    xmlWriter.writeNamespace(prefix, namespace);
                    xmlWriter.setPrefix(prefix, namespace);
                } else {
                    xmlWriter.writeStartElement(namespace, "wapPStCallbUrl");
                }
            } else {
                xmlWriter.writeStartElement("wapPStCallbUrl");
            }

            if (localWapPStCallbUrl == null) {
                // write the nil attribute
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "nil", "1",
                    xmlWriter);
            } else {
                xmlWriter.writeCharacters(localWapPStCallbUrl);
            }

            xmlWriter.writeEndElement();
        }

        xmlWriter.writeEndElement();
    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix,
        java.lang.String namespace, java.lang.String attName,
        java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (xmlWriter.getPrefix(namespace) == null) {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        xmlWriter.writeAttribute(namespace, attName, attValue);
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace,
        java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace,
        java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String attributeNamespace = qname.getNamespaceURI();
        java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

        if (attributePrefix == null) {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }

        java.lang.String attributeValue;

        if (attributePrefix.trim().length() > 0) {
            attributeValue = attributePrefix + ":" + qname.getLocalPart();
        } else {
            attributeValue = qname.getLocalPart();
        }

        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attributeValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(namespace, attName, attributeValue);
        }
    }

    /**
     *  method to handle Qnames
     */
    private void writeQName(javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String namespaceURI = qname.getNamespaceURI();

        if (namespaceURI != null) {
            java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

            if (prefix == null) {
                prefix = generatePrefix(namespaceURI);
                xmlWriter.writeNamespace(prefix, namespaceURI);
                xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
                xmlWriter.writeCharacters(prefix + ":" +
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            } else {
                // i.e this is the default namespace
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    qname));
        }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (qnames != null) {
            // we have to store this data until last moment since it is not possible to write any
            // namespace data after writing the charactor data
            java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
            java.lang.String namespaceURI = null;
            java.lang.String prefix = null;

            for (int i = 0; i < qnames.length; i++) {
                if (i > 0) {
                    stringToWrite.append(" ");
                }

                namespaceURI = qnames[i].getNamespaceURI();

                if (namespaceURI != null) {
                    prefix = xmlWriter.getPrefix(namespaceURI);

                    if ((prefix == null) || (prefix.length() == 0)) {
                        prefix = generatePrefix(namespaceURI);
                        xmlWriter.writeNamespace(prefix, namespaceURI);
                        xmlWriter.setPrefix(prefix, namespaceURI);
                    }

                    if (prefix.trim().length() > 0) {
                        stringToWrite.append(prefix).append(":")
                                     .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                } else {
                    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qnames[i]));
                }
            }

            xmlWriter.writeCharacters(stringToWrite.toString());
        }
    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(
        javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null) {
            prefix = generatePrefix(namespace);

            while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    /**
     * databinding method to get an XML representation of this object
     *
     */
    public javax.xml.stream.XMLStreamReader getPullParser(
        javax.xml.namespace.QName qName)
        throws org.apache.axis2.databinding.ADBException {
        java.util.ArrayList elementList = new java.util.ArrayList();
        java.util.ArrayList attribList = new java.util.ArrayList();

        elementList.add(new javax.xml.namespace.QName("", "streamingNo"));

        elementList.add((localStreamingNo == null) ? null
                                                   : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localStreamingNo));

        elementList.add(new javax.xml.namespace.QName("", "orderNO"));

        elementList.add((localOrderNO == null) ? null
                                               : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localOrderNO));

        elementList.add(new javax.xml.namespace.QName("", "constNo"));

        elementList.add((localConstNo == null) ? null
                                               : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localConstNo));

        elementList.add(new javax.xml.namespace.QName("", "constTime"));

        elementList.add((localConstTime == null) ? null
                                                 : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localConstTime));

        elementList.add(new javax.xml.namespace.QName("", "linkman"));

        elementList.add((localLinkman == null) ? null
                                               : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localLinkman));

        elementList.add(new javax.xml.namespace.QName("", "linkPh"));

        elementList.add((localLinkPh == null) ? null
                                              : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localLinkPh));

        elementList.add(new javax.xml.namespace.QName("", "custName"));

        elementList.add((localCustName == null) ? null
                                                : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localCustName));

        elementList.add(new javax.xml.namespace.QName("", "custnNum"));

        elementList.add((localCustnNum == null) ? null
                                                : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localCustnNum));

        elementList.add(new javax.xml.namespace.QName("", "conNo"));

        elementList.add((localConNo == null) ? null
                                             : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localConNo));

        elementList.add(new javax.xml.namespace.QName("", "actionFlag"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localActionFlag));

        elementList.add(new javax.xml.namespace.QName("", "svcBound"));

        elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localSvcBound));

        elementList.add(new javax.xml.namespace.QName("", "cN2Ip"));

        elementList.add((localCN2Ip == null) ? null
                                             : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localCN2Ip));

        elementList.add(new javax.xml.namespace.QName("", "zipBizId"));

        elementList.add((localZipBizId == null) ? null
                                                : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localZipBizId));

        elementList.add(new javax.xml.namespace.QName("", "eqProvince"));

        elementList.add((localEqProvince == null) ? null
                                                  : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                localEqProvince));

        if (localSmsNotCallbUrlTracker) {
            elementList.add(new javax.xml.namespace.QName("", "smsNotCallbUrl"));

            elementList.add((localSmsNotCallbUrl == null) ? null
                                                          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSmsNotCallbUrl));
        }

        if (localSmsStCallbUrlTracker) {
            elementList.add(new javax.xml.namespace.QName("", "smsStCallbUrl"));

            elementList.add((localSmsStCallbUrl == null) ? null
                                                         : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localSmsStCallbUrl));
        }

        if (localMmsNotCallbUrlTracker) {
            elementList.add(new javax.xml.namespace.QName("", "mmsNotCallbUrl"));

            elementList.add((localMmsNotCallbUrl == null) ? null
                                                          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localMmsNotCallbUrl));
        }

        if (localMmsStCallbUrlTracker) {
            elementList.add(new javax.xml.namespace.QName("", "mmsStCallbUrl"));

            elementList.add((localMmsStCallbUrl == null) ? null
                                                         : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localMmsStCallbUrl));
        }

        if (localWapPStCallbUrlTracker) {
            elementList.add(new javax.xml.namespace.QName("", "wapPStCallbUrl"));

            elementList.add((localWapPStCallbUrl == null) ? null
                                                          : org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localWapPStCallbUrl));
        }

        return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,
            elementList.toArray(), attribList.toArray());
    }

    /**
     *  Factory class that keeps the parse method
     */
    public static class Factory {
        /**
         * static method to create the object
         * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
         *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
         * Postcondition: If this object is an element, the reader is positioned at its end element
         *                If this object is a complex type, the reader is positioned at the end element of its outer element
         */
        public static WorkOrderNotifyReq parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            WorkOrderNotifyReq object = new WorkOrderNotifyReq();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                    java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");

                    if (fullTypeName != null) {
                        java.lang.String nsPrefix = null;

                        if (fullTypeName.indexOf(":") > -1) {
                            nsPrefix = fullTypeName.substring(0,
                                    fullTypeName.indexOf(":"));
                        }

                        nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                        java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                    ":") + 1);

                        if (!"WorkOrderNotifyReq".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (WorkOrderNotifyReq) com.ztesoft.vsop.webservice.vo.jx.req.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "streamingNo").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setStreamingNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "orderNO").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setOrderNO(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "constNo").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setConstNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "constTime").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setConstTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "linkman").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setLinkman(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "linkPh").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setLinkPh(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "custName").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setCustName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "custnNum").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setCustnNum(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "conNo").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setConNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "actionFlag").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setActionFlag(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                                content));
                    } else {
                        object.setActionFlag(java.lang.Integer.MIN_VALUE);

                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "svcBound").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setSvcBound(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                                content));
                    } else {
                        object.setSvcBound(java.lang.Integer.MIN_VALUE);

                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "cN2Ip").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setCN2Ip(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "zipBizId").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setZipBizId(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "eqProvince").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setEqProvince(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                    // A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "smsNotCallbUrl").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setSmsNotCallbUrl(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "smsStCallbUrl").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setSmsStCallbUrl(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "mmsNotCallbUrl").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setMmsNotCallbUrl(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "mmsStCallbUrl").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setMmsStCallbUrl(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("", "wapPStCallbUrl").equals(
                            reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if (!"true".equals(nillableValue) &&
                            !"1".equals(nillableValue)) {
                        java.lang.String content = reader.getElementText();

                        object.setWapPStCallbUrl(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));
                    } else {
                        reader.getElementText(); // throw away text nodes if any.
                    }

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement()) {
                    // A start element we are not expecting indicates a trailing invalid property
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getLocalName());
                }
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
