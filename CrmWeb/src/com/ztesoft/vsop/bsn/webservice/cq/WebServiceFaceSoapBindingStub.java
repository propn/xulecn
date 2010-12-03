/**
 * WebServiceFaceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public class WebServiceFaceSoapBindingStub extends org.apache.axis.client.Stub implements WebServiceFace_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[87];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
        _initOperationDesc5();
        _initOperationDesc6();
        _initOperationDesc7();
        _initOperationDesc8();
        _initOperationDesc9();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteAgentProperty");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "DeleteAgentPropertyReqDto"), DeleteAgentPropertyReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "DeleteAgentPropertyRespDto"));
        oper.setReturnClass(DeleteAgentPropertyRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "deleteAgentPropertyReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("checkPwd");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CheckPwdReqDto"), CheckPwdReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CheckPwdRespDto"));
        oper.setReturnClass(CheckPwdRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "checkPwdReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("changePwd");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangePwdReqDto"), ChangePwdReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangePwdRespDto"));
        oper.setReturnClass(ChangePwdRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "changePwdReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryUserType");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryUserTypeReqDto"), QueryUserTypeReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryUserTypeRespDto"));
        oper.setReturnClass(QueryUserTypeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryUserTypeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPatiType");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiTypeRespDto"));
        oper.setReturnClass(QueryPatiTypeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPatiTypeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryRelaPhone");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryRelaPhoneReqDto"), QueryRelaPhoneReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryRelaPhoneRespDto"));
        oper.setReturnClass(QueryRelaPhoneRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryRelaPhoneReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPayRecord");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordReqDto"), QueryPayRecordReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordRespDto"));
        oper.setReturnClass(QueryPayRecordRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPayRecordReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPayRecordCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordReqDto"), QueryPayRecordReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPayRecordCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPayRecordOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordReqDto"), QueryPayRecordReqDto.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordRespDto"));
        oper.setReturnClass(QueryPayRecordRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPayRecordOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPati");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiReqDto"), QueryPatiReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiRespDto"));
        oper.setReturnClass(QueryPatiRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPatiReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPatiCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiReqDto"), QueryPatiReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPatiCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPatiOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiReqDto"), QueryPatiReqDto.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiRespDto"));
        oper.setReturnClass(QueryPatiRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPatiOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryBill");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillReqDto"), QueryBillReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillRespDto"));
        oper.setReturnClass(QueryBillRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryBillReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryBillCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillReqDto"), QueryBillReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryBillCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryBillOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillReqDto"), QueryBillReqDto.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillRespDto"));
        oper.setReturnClass(QueryBillRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryBillOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAccNbr");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrReqDto"), QueryAccNbrReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrRespDto"));
        oper.setReturnClass(QueryAccNbrRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAccNbrReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAccNbrCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrReqDto"), QueryAccNbrReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAccNbrCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAccNbrOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrReqDto"), QueryAccNbrReqDto.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrRespDto"));
        oper.setReturnClass(QueryAccNbrRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAccNbrOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAcctBill");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctBillReqDto"), QueryAcctBillReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctBillRespDto"));
        oper.setReturnClass(QueryAcctBillRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAcctBillReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAcctBillCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctBillReqDto"), QueryAcctBillReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAcctBillCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAcctBillOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctBillReqDto"), QueryAcctBillReqDto.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageNo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "pageSize"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctBillRespDto"));
        oper.setReturnClass(QueryAcctBillRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAcctBillOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryListTypeList");
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfListTypeRespDto"));
        oper.setReturnClass(ListTypeRespDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryListTypeListReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryServList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryServReqDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServReqDto"), QueryServReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServRespDto"));
        oper.setReturnClass(QueryServRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryServListReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAcctList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryAcctReqDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctReqDto"), QueryAcctReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctRespDto"));
        oper.setReturnClass(QueryAcctRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAcctListReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryBillItemList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryBillItemRepDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillItemReqDto"), QueryBillItemReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillItemRespDto"));
        oper.setReturnClass(QueryBillItemRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryBillItemListReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryPaymentCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryPaymentRecordReqDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPaymentRecordReqDto"), QueryPaymentRecordReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPaymentRecordRespDto"));
        oper.setReturnClass(QueryPaymentRecordRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryPaymentCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryPaymentList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryPaymentRecordReqDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPaymentRecordReqDto"), QueryPaymentRecordReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPaymentRecordRespDto"));
        oper.setReturnClass(QueryPaymentRecordRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryPaymentListReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryListTypeCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeReqDto"), QueryListTypeReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeRespDto"));
        oper.setReturnClass(QueryListTypeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryListTypeCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryListList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListReqDto"), QueryListReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListRespDto"));
        oper.setReturnClass(QueryListRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryListListReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryServCredit");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServReqDto"), QueryServReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServCreditRespDto"));
        oper.setReturnClass(QueryServCreditRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryServCreditReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryServDisctByImsi");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctByImsiReqDto"), QueryServDisctByImsiReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctByImsiRespDto"));
        oper.setReturnClass(QueryServDisctByImsiRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryServDisctByImsiReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryServDisct");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctReqDto"), QueryServDisctReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctRespDto"));
        oper.setReturnClass(QueryServDisctRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryServDisctReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryServDisctForBSN");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctReqDto"), QueryServDisctReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctRespDto"));
        oper.setReturnClass(QueryServDisctRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryServDisctForBSNReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryListTypeCount_CDMA");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeReqDto"), QueryListTypeReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeRespDto"));
        oper.setReturnClass(QueryListTypeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryListTypeCount_CDMAReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryListList_CDMA");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListReqDto"), QueryListReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListRespDto"));
        oper.setReturnClass(QueryListRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryListList_CDMAReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryListExCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCDMAListTypeReqDto"), QueryCDMAListTypeReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeRespDto"));
        oper.setReturnClass(QueryListTypeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryListExCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryPatiOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListReqDto"), QueryListReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListRespDto"));
        oper.setReturnClass(QueryListRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryPatiOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryListTypeCount_CDMAev");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeReqDto"), QueryListTypeReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeRespDto"));
        oper.setReturnClass(QueryListTypeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryListTypeCount_CDMAevReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[37] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryListList_CDMAev");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListReqDto"), QueryListReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListRespDto"));
        oper.setReturnClass(QueryListRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryListList_CDMAevReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[38] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryPatiTypeOfServ");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiTypeRespDto"));
        oper.setReturnClass(QueryPatiTypeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryPatiTypeOfServReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[39] = oper;

    }

    private static void _initOperationDesc5(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPayLogCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codeDateRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDateRequDto"), CodeDateRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPayLogCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[40] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryPayLogOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codeDatePageRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDatePageRequDto"), CodeDatePageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryPayLogOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[41] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryLateFeeDisctCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codeDateRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDateRequDto"), CodeDateRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryLateFeeDisctCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[42] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryLateFeeDisctOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codeDatePageRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDatePageRequDto"), CodeDatePageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryLateFeeDisctOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[43] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryMXBillCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryMXBillCountReqDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryMXBillCountRequDto"), QueryMXBillCountRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryMXBillCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[44] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryMXBillOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryMXBillPageRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryMXBillPageRequDto"), QueryMXBillPageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillDataRespDto"));
        oper.setReturnClass(BillDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryMXBillOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[45] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryFZBillCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryFZBillCountRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryFZBillCountRequDto"), QueryFZBillCountRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryFZBillCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[46] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryFZBillOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryFZBillPageRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryFZBillPageRequDto"), QueryFZBillPageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillDataRespDto"));
        oper.setReturnClass(BillDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryFZBillOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[47] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryBalance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BalanceRespDto"));
        oper.setReturnClass(BalanceRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryBalanceReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[48] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryDetailBalanceCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryDetailBalanceCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[49] = oper;

    }

    private static void _initOperationDesc6(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryDetailBalanceOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodePageRequDto"), CodePageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryDetailBalanceOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[50] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryBalanceLogCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDateRequDto"), CodeDateRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryBalanceLogCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[51] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryBalanceLogOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDatePageRequDto"), CodeDatePageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryBalanceLogOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[52] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAdjusteCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDateRequDto"), CodeDateRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAdjusteCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[53] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryAdjusteOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDatePageRequDto"), CodeDatePageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "QueryAdjusteOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[54] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryLacGrpCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryLacGrpCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[55] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryLacGrpOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodePageRequDto"), CodePageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryLacGrpOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[56] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryLacCount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto"));
        oper.setReturnClass(CountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryLacCountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[57] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryLacOfOnePage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodePageRequDto"), CodePageRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryLacOfOnePageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[58] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryServAcctInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryBillItemReqDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillItemReqDto"), QueryBillItemReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServAcctInfoRespDto"));
        oper.setReturnClass(ServAcctInfoRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryServAcctInfoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[59] = oper;

    }

    private static void _initOperationDesc7(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryServByAcctCode");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrReqDto"), QueryAccNbrReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrByAcctCodeRespDto"));
        oper.setReturnClass(QueryAccNbrByAcctCodeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryServByAcctCodeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[60] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryowelist");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryOweReqDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryOweReqDto"), QueryOweReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryOweRespDto"));
        oper.setReturnClass(QueryOweRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryowelistReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[61] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryOweAcct");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codeRequDto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto"));
        oper.setReturnClass(TableDataRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryOweAcctReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[62] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAgentUpperAmount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentUpperAmountReqDto"), QueryAgentUpperAmountReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentUpperAmountRespDto"));
        oper.setReturnClass(QueryAgentUpperAmountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAgentUpperAmountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[63] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateAgentUpperAmount");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountReqDto"), UpdateAgentUpperAmountReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountRespDto"));
        oper.setReturnClass(UpdateAgentUpperAmountRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "updateAgentUpperAmountReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[64] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateAgentCharge");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentChargeReqDto"), UpdateAgentChargeReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentChargeRespDto"));
        oper.setReturnClass(UpdateAgentChargeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "updateAgentChargeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[65] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("addAgentProperty");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AddAgentPropertyReqDto"), AddAgentPropertyReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AddAgentPropertyRespDto"));
        oper.setReturnClass(AddAgentPropertyRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "addAgentPropertyReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[66] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAgentProperty");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentPropertyReqDto"), QueryAgentPropertyReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentPropertyRespDto"));
        oper.setReturnClass(QueryAgentPropertyRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryAgentPropertyReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[67] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateAgentUpperAmountAndCharge");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountAndChargeReqDto"), UpdateAgentUpperAmountAndChargeReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountAndChargeRespDto"));
        oper.setReturnClass(UpdateAgentUpperAmountAndChargeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "updateAgentUpperAmountAndChargeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[68] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryCreditLimit");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditLimitReqDto"), QueryCreditLimitReqDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditLimitRespDto"));
        oper.setReturnClass(QueryCreditLimitRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryCreditLimitReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[69] = oper;

    }

    private static void _initOperationDesc8(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryServState");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfQueryServStateRespDto"));
        oper.setReturnClass(QueryServStateRespDto[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryServStateReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[70] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryOweFee");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryOweFeeRespDto"));
        oper.setReturnClass(QueryOweFeeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryOweFeeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[71] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryBadFee");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBadFeeRespDto"));
        oper.setReturnClass(QueryBadFeeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryBadFeeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[72] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryCashFee");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCashFeeRespDto"));
        oper.setReturnClass(QueryCashFeeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryCashFeeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[73] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("servActive");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServActiveRequDto"), ServActiveRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServActiveRespDto"));
        oper.setReturnClass(ServActiveRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "servActiveReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[74] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryActiveBalance");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalanceRequDto"), QueryActiveBalanceRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalanceRespDto"));
        oper.setReturnClass(QueryActiveBalanceRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryActiveBalanceReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[75] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryActiveBalChange");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalChangeRequDto"), QueryActiveBalChangeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalChangeRespDto"));
        oper.setReturnClass(QueryActiveBalChangeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryActiveBalChangeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[76] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryActiveBill");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBillRequDto"), QueryActiveBillRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBillRespDto"));
        oper.setReturnClass(QueryActiveBillRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryActiveBillReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[77] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryFluxStop");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "FluxStopQryRequDto"), FluxStopQryRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "FluxStopQryRespDto"));
        oper.setReturnClass(FluxStopQryRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryFluxStopReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[78] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("recoverFlux");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "RecoverFluxRequDto"), RecoverFluxRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "RecoverFluxRespDto"));
        oper.setReturnClass(RecoverFluxRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "recoverFluxReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[79] = oper;

    }

    private static void _initOperationDesc9(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("termiRecover");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TermiRecoverRequDto"), TermiRecoverRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TermiRecoverRespDto"));
        oper.setReturnClass(TermiRecoverRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "termiRecoverReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[80] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cardAgreement");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CardAgreementRequDto"), CardAgreementRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CardAgreementRespDto"));
        oper.setReturnClass(CardAgreementRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "cardAgreementReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[81] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryServInfoForList");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDateRequDto"), CodeDateRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServInfoForListRespDto"));
        oper.setReturnClass(ServInfoForListRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryServInfoForListReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[82] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryServiceBillingInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingRequCsiDto"), ServiceBillingRequCsiDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingInfoRespDto"));
        oper.setReturnClass(ServiceBillingInfoRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryServiceBillingInfoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[83] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("changeCreditValue");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "dto"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CreditValueChangeRequDto"), CreditValueChangeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CreditValueChangeRespDto"));
        oper.setReturnClass(CreditValueChangeRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "changeCreditValueReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[84] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("querySixMonthBill");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QuerySixMonthBillRespDto"));
        oper.setReturnClass(QuerySixMonthBillRespDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "querySixMonthBillReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[85] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryCreditForCompt");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "req"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto"), CodeRequDto.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditForComptDto"));
        oper.setReturnClass(QueryCreditForComptDto.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryCreditForComptReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[86] = oper;

    }

    public WebServiceFaceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public WebServiceFaceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public WebServiceFaceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "ArrayOf_tns1_BillCsiDto");
            cachedSerQNames.add(qName);
            cls = BillCsiDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillCsiDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "ArrayOf_tns1_BillInfoDto");
            cachedSerQNames.add(qName);
            cls = BillInfoDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillInfoDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "ArrayOf_tns1_ChangeInfoDto");
            cachedSerQNames.add(qName);
            cls = ChangeInfoDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangeInfoDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfint");
            cachedSerQNames.add(qName);
            cls = int[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfString");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfString2D");
            cachedSerQNames.add(qName);
            cls = java.lang.String[][].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AcctInfoDto");
            cachedSerQNames.add(qName);
            cls = AcctInfoDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AddAgentPropertyReqDto");
            cachedSerQNames.add(qName);
            cls = AddAgentPropertyReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AddAgentPropertyRespDto");
            cachedSerQNames.add(qName);
            cls = AddAgentPropertyRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AgentBaseReqDto");
            cachedSerQNames.add(qName);
            cls = AgentBaseReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AgentBaseRespDto");
            cachedSerQNames.add(qName);
            cls = AgentBaseRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AgentPropertyDto");
            cachedSerQNames.add(qName);
            cls = AgentPropertyDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfAcctInfoDto");
            cachedSerQNames.add(qName);
            cls = AcctInfoDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "AcctInfoDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfBillItemAmountDto");
            cachedSerQNames.add(qName);
            cls = BillItemAmountDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillItemAmountDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfBrandDisctDto");
            cachedSerQNames.add(qName);
            cls = BrandDisctDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BrandDisctDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfListDataDto");
            cachedSerQNames.add(qName);
            cls = ListDataDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListDataDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfListTypeDto");
            cachedSerQNames.add(qName);
            cls = ListTypeDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfListTypeRespDto");
            cachedSerQNames.add(qName);
            cls = ListTypeRespDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeRespDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfListTypeSumDto");
            cachedSerQNames.add(qName);
            cls = ListTypeSumDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeSumDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfPaymentRecordDto");
            cachedSerQNames.add(qName);
            cls = PaymentRecordDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "PaymentRecordDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfQueryServStateRespDto");
            cachedSerQNames.add(qName);
            cls = QueryServStateRespDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServStateRespDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfRowDto");
            cachedSerQNames.add(qName);
            cls = RowDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "RowDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfServAcctRespDto");
            cachedSerQNames.add(qName);
            cls = ServAcctRespDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServAcctRespDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfServDisctDto");
            cachedSerQNames.add(qName);
            cls = ServDisctDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServDisctDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfServiceBillingInfoDto");
            cachedSerQNames.add(qName);
            cls = ServiceBillingInfoDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingInfoDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfServInfoDto");
            cachedSerQNames.add(qName);
            cls = ServInfoDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServInfoDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ArrayOfServRespDto");
            cachedSerQNames.add(qName);
            cls = ServRespDto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServRespDto");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BalanceRespDto");
            cachedSerQNames.add(qName);
            cls = BalanceRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillCsiDto");
            cachedSerQNames.add(qName);
            cls = BillCsiDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillDataRespDto");
            cachedSerQNames.add(qName);
            cls = BillDataRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillInfoDto");
            cachedSerQNames.add(qName);
            cls = BillInfoDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillItemAmountDto");
            cachedSerQNames.add(qName);
            cls = BillItemAmountDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BillResult");
            cachedSerQNames.add(qName);
            cls = BillResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "BrandDisctDto");
            cachedSerQNames.add(qName);
            cls = BrandDisctDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CardAgreementRequDto");
            cachedSerQNames.add(qName);
            cls = CardAgreementRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CardAgreementRespDto");
            cachedSerQNames.add(qName);
            cls = CardAgreementRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangeInfoDto");
            cachedSerQNames.add(qName);
            cls = ChangeInfoDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangePwdReqDto");
            cachedSerQNames.add(qName);
            cls = ChangePwdReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ChangePwdRespDto");
            cachedSerQNames.add(qName);
            cls = ChangePwdRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CheckPwdReqDto");
            cachedSerQNames.add(qName);
            cls = CheckPwdReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CheckPwdRespDto");
            cachedSerQNames.add(qName);
            cls = CheckPwdRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDatePageRequDto");
            cachedSerQNames.add(qName);
            cls = CodeDatePageRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeDateRequDto");
            cachedSerQNames.add(qName);
            cls = CodeDateRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodePageRequDto");
            cachedSerQNames.add(qName);
            cls = CodePageRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CodeRequDto");
            cachedSerQNames.add(qName);
            cls = CodeRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CountRespDto");
            cachedSerQNames.add(qName);
            cls = CountRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CreditValueChangeRequDto");
            cachedSerQNames.add(qName);
            cls = CreditValueChangeRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "CreditValueChangeRespDto");
            cachedSerQNames.add(qName);
            cls = CreditValueChangeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "DeleteAgentPropertyReqDto");
            cachedSerQNames.add(qName);
            cls = DeleteAgentPropertyReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "DeleteAgentPropertyRespDto");
            cachedSerQNames.add(qName);
            cls = DeleteAgentPropertyRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "FluxStopQryRequDto");
            cachedSerQNames.add(qName);
            cls = FluxStopQryRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "FluxStopQryRespDto");
            cachedSerQNames.add(qName);
            cls = FluxStopQryRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListDataDto");
            cachedSerQNames.add(qName);
            cls = ListDataDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeDto");
            cachedSerQNames.add(qName);
            cls = ListTypeDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeRespDto");
            cachedSerQNames.add(qName);
            cls = ListTypeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ListTypeSumDto");
            cachedSerQNames.add(qName);
            cls = ListTypeSumDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "PaymentRecordDto");
            cachedSerQNames.add(qName);
            cls = PaymentRecordDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrByAcctCodeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryAccNbrByAcctCodeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrReqDto");
            cachedSerQNames.add(qName);
            cls = QueryAccNbrReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAccNbrRespDto");
            cachedSerQNames.add(qName);
            cls = QueryAccNbrRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctBillReqDto");
            cachedSerQNames.add(qName);
            cls = QueryAcctBillReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctBillRespDto");
            cachedSerQNames.add(qName);
            cls = QueryAcctBillRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctReqDto");
            cachedSerQNames.add(qName);
            cls = QueryAcctReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAcctRespDto");
            cachedSerQNames.add(qName);
            cls = QueryAcctRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalanceRequDto");
            cachedSerQNames.add(qName);
            cls = QueryActiveBalanceRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalanceRespDto");
            cachedSerQNames.add(qName);
            cls = QueryActiveBalanceRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalChangeRequDto");
            cachedSerQNames.add(qName);
            cls = QueryActiveBalChangeRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBalChangeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryActiveBalChangeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBillRequDto");
            cachedSerQNames.add(qName);
            cls = QueryActiveBillRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryActiveBillRespDto");
            cachedSerQNames.add(qName);
            cls = QueryActiveBillRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentPropertyReqDto");
            cachedSerQNames.add(qName);
            cls = QueryAgentPropertyReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentPropertyRespDto");
            cachedSerQNames.add(qName);
            cls = QueryAgentPropertyRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentUpperAmountReqDto");
            cachedSerQNames.add(qName);
            cls = QueryAgentUpperAmountReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryAgentUpperAmountRespDto");
            cachedSerQNames.add(qName);
            cls = QueryAgentUpperAmountRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBadFeeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryBadFeeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillItemReqDto");
            cachedSerQNames.add(qName);
            cls = QueryBillItemReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillItemRespDto");
            cachedSerQNames.add(qName);
            cls = QueryBillItemRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillNewResult");
            cachedSerQNames.add(qName);
            cls = QueryBillNewResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillReqDto");
            cachedSerQNames.add(qName);
            cls = QueryBillReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillRespDto");
            cachedSerQNames.add(qName);
            cls = QueryBillRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryBillResult");
            cachedSerQNames.add(qName);
            cls = QueryBillResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCashFeeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryCashFeeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCDMAListTypeReqDto");
            cachedSerQNames.add(qName);
            cls = QueryCDMAListTypeReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditForComptDto");
            cachedSerQNames.add(qName);
            cls = QueryCreditForComptDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditLimitReqDto");
            cachedSerQNames.add(qName);
            cls = QueryCreditLimitReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryCreditLimitRespDto");
            cachedSerQNames.add(qName);
            cls = QueryCreditLimitRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryFZBillCountRequDto");
            cachedSerQNames.add(qName);
            cls = QueryFZBillCountRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryFZBillPageRequDto");
            cachedSerQNames.add(qName);
            cls = QueryFZBillPageRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListReqDto");
            cachedSerQNames.add(qName);
            cls = QueryListReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListRespDto");
            cachedSerQNames.add(qName);
            cls = QueryListRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeReqDto");
            cachedSerQNames.add(qName);
            cls = QueryListTypeReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryListTypeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryListTypeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryMXBillCountRequDto");
            cachedSerQNames.add(qName);
            cls = QueryMXBillCountRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryMXBillPageRequDto");
            cachedSerQNames.add(qName);
            cls = QueryMXBillPageRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryOweFeeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryOweFeeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryOweReqDto");
            cachedSerQNames.add(qName);
            cls = QueryOweReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryOweRespDto");
            cachedSerQNames.add(qName);
            cls = QueryOweRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiReqDto");
            cachedSerQNames.add(qName);
            cls = QueryPatiReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiRespDto");
            cachedSerQNames.add(qName);
            cls = QueryPatiRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPatiTypeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryPatiTypeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPaymentRecordReqDto");
            cachedSerQNames.add(qName);
            cls = QueryPaymentRecordReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPaymentRecordRespDto");
            cachedSerQNames.add(qName);
            cls = QueryPaymentRecordRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordReqDto");
            cachedSerQNames.add(qName);
            cls = QueryPayRecordReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryPayRecordRespDto");
            cachedSerQNames.add(qName);
            cls = QueryPayRecordRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryRelaPhoneReqDto");
            cachedSerQNames.add(qName);
            cls = QueryRelaPhoneReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryRelaPhoneRespDto");
            cachedSerQNames.add(qName);
            cls = QueryRelaPhoneRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServCreditRespDto");
            cachedSerQNames.add(qName);
            cls = QueryServCreditRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctByImsiReqDto");
            cachedSerQNames.add(qName);
            cls = QueryServDisctByImsiReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctByImsiRespDto");
            cachedSerQNames.add(qName);
            cls = QueryServDisctByImsiRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctReqDto");
            cachedSerQNames.add(qName);
            cls = QueryServDisctReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServDisctRespDto");
            cachedSerQNames.add(qName);
            cls = QueryServDisctRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServReqDto");
            cachedSerQNames.add(qName);
            cls = QueryServReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServRespDto");
            cachedSerQNames.add(qName);
            cls = QueryServRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryServStateRespDto");
            cachedSerQNames.add(qName);
            cls = QueryServStateRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QuerySixMonthBillRespDto");
            cachedSerQNames.add(qName);
            cls = QuerySixMonthBillRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryUserTypeReqDto");
            cachedSerQNames.add(qName);
            cls = QueryUserTypeReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "QueryUserTypeRespDto");
            cachedSerQNames.add(qName);
            cls = QueryUserTypeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "RecoverFluxRequDto");
            cachedSerQNames.add(qName);
            cls = RecoverFluxRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "RecoverFluxRespDto");
            cachedSerQNames.add(qName);
            cls = RecoverFluxRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "RowDto");
            cachedSerQNames.add(qName);
            cls = RowDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServAcctInfoRespDto");
            cachedSerQNames.add(qName);
            cls = ServAcctInfoRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServAcctRespDto");
            cachedSerQNames.add(qName);
            cls = ServAcctRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServActiveRequDto");
            cachedSerQNames.add(qName);
            cls = ServActiveRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServActiveRespDto");
            cachedSerQNames.add(qName);
            cls = ServActiveRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServDisctDto");
            cachedSerQNames.add(qName);
            cls = ServDisctDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingInfoDto");
            cachedSerQNames.add(qName);
            cls = ServiceBillingInfoDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingInfoRespDto");
            cachedSerQNames.add(qName);
            cls = ServiceBillingInfoRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServiceBillingRequCsiDto");
            cachedSerQNames.add(qName);
            cls = ServiceBillingRequCsiDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServInfoDto");
            cachedSerQNames.add(qName);
            cls = ServInfoDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServInfoForListRespDto");
            cachedSerQNames.add(qName);
            cls = ServInfoForListRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "ServRespDto");
            cachedSerQNames.add(qName);
            cls = ServRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableDataRespDto");
            cachedSerQNames.add(qName);
            cls = TableDataRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TableResultDto");
            cachedSerQNames.add(qName);
            cls = TableResultDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TermiRecoverRequDto");
            cachedSerQNames.add(qName);
            cls = TermiRecoverRequDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "TermiRecoverRespDto");
            cachedSerQNames.add(qName);
            cls = TermiRecoverRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentChargeReqDto");
            cachedSerQNames.add(qName);
            cls = UpdateAgentChargeReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentChargeRespDto");
            cachedSerQNames.add(qName);
            cls = UpdateAgentChargeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountAndChargeReqDto");
            cachedSerQNames.add(qName);
            cls = UpdateAgentUpperAmountAndChargeReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountAndChargeRespDto");
            cachedSerQNames.add(qName);
            cls = UpdateAgentUpperAmountAndChargeRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountReqDto");
            cachedSerQNames.add(qName);
            cls = UpdateAgentUpperAmountReqDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://commudto.custserviceinfmgr.bsn.ztesoft.com", "UpdateAgentUpperAmountRespDto");
            cachedSerQNames.add(qName);
            cls = UpdateAgentUpperAmountRespDto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public DeleteAgentPropertyRespDto deleteAgentProperty(DeleteAgentPropertyReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "deleteAgentProperty"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (DeleteAgentPropertyRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (DeleteAgentPropertyRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, DeleteAgentPropertyRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CheckPwdRespDto checkPwd(CheckPwdReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "checkPwd"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CheckPwdRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CheckPwdRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CheckPwdRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ChangePwdRespDto changePwd(ChangePwdReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "changePwd"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ChangePwdRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (ChangePwdRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, ChangePwdRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryUserTypeRespDto queryUserType(QueryUserTypeReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryUserType"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryUserTypeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryUserTypeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryUserTypeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPatiTypeRespDto queryPatiType(java.lang.String req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPatiType"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPatiTypeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPatiTypeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPatiTypeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryRelaPhoneRespDto queryRelaPhone(QueryRelaPhoneReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryRelaPhone"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryRelaPhoneRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryRelaPhoneRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryRelaPhoneRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPayRecordRespDto queryPayRecord(QueryPayRecordReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPayRecord"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPayRecordRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPayRecordRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPayRecordRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryPayRecordCount(QueryPayRecordReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPayRecordCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPayRecordRespDto queryPayRecordOfOnePage(QueryPayRecordReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPayRecordOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req, new java.lang.Integer(pageNo), new java.lang.Integer(pageSize)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPayRecordRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPayRecordRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPayRecordRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPatiRespDto queryPati(QueryPatiReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPati"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPatiRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPatiRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPatiRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryPatiCount(QueryPatiReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPatiCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPatiRespDto queryPatiOfOnePage(QueryPatiReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPatiOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req, new java.lang.Integer(pageNo), new java.lang.Integer(pageSize)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPatiRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPatiRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPatiRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryBillRespDto queryBill(QueryBillReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryBill"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryBillRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryBillRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryBillRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryBillCount(QueryBillReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryBillCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryBillRespDto queryBillOfOnePage(QueryBillReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryBillOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req, new java.lang.Integer(pageNo), new java.lang.Integer(pageSize)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryBillRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryBillRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryBillRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAccNbrRespDto queryAccNbr(QueryAccNbrReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAccNbr"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAccNbrRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAccNbrRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAccNbrRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryAccNbrCount(QueryAccNbrReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAccNbrCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAccNbrRespDto queryAccNbrOfOnePage(QueryAccNbrReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAccNbrOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req, new java.lang.Integer(pageNo), new java.lang.Integer(pageSize)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAccNbrRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAccNbrRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAccNbrRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAcctBillRespDto queryAcctBill(QueryAcctBillReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAcctBill"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAcctBillRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAcctBillRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAcctBillRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryAcctBillCount(QueryAcctBillReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAcctBillCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAcctBillRespDto queryAcctBillOfOnePage(QueryAcctBillReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAcctBillOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req, new java.lang.Integer(pageNo), new java.lang.Integer(pageSize)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAcctBillRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAcctBillRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAcctBillRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ListTypeRespDto[] queryListTypeList() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryListTypeList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ListTypeRespDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (ListTypeRespDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, ListTypeRespDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryServRespDto queryServList(QueryServReqDto queryServReqDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryServList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryServReqDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryServRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryServRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryServRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAcctRespDto queryAcctList(QueryAcctReqDto queryAcctReqDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAcctList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryAcctReqDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAcctRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAcctRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAcctRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryBillItemRespDto queryBillItemList(QueryBillItemReqDto queryBillItemRepDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryBillItemList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryBillItemRepDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryBillItemRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryBillItemRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryBillItemRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPaymentRecordRespDto queryPaymentCount(QueryPaymentRecordReqDto queryPaymentRecordReqDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryPaymentCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryPaymentRecordReqDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPaymentRecordRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPaymentRecordRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPaymentRecordRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPaymentRecordRespDto queryPaymentList(QueryPaymentRecordReqDto queryPaymentRecordReqDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryPaymentList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryPaymentRecordReqDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPaymentRecordRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPaymentRecordRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPaymentRecordRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListTypeRespDto queryListTypeCount(QueryListTypeReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryListTypeCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListTypeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListTypeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListTypeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListRespDto queryListList(QueryListReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryListList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryServCreditRespDto queryServCredit(QueryServReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryServCredit"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryServCreditRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryServCreditRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryServCreditRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryServDisctByImsiRespDto queryServDisctByImsi(QueryServDisctByImsiReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryServDisctByImsi"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryServDisctByImsiRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryServDisctByImsiRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryServDisctByImsiRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryServDisctRespDto queryServDisct(QueryServDisctReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryServDisct"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryServDisctRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryServDisctRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryServDisctRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryServDisctRespDto queryServDisctForBSN(QueryServDisctReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryServDisctForBSN"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryServDisctRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryServDisctRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryServDisctRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListTypeRespDto queryListTypeCount_CDMA(QueryListTypeReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryListTypeCount_CDMA"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListTypeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListTypeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListTypeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListRespDto queryListList_CDMA(QueryListReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryListList_CDMA"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListTypeRespDto queryListExCount(QueryCDMAListTypeReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryListExCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListTypeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListTypeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListTypeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListRespDto queryPatiOfOnePage(QueryListReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryPatiOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListTypeRespDto queryListTypeCount_CDMAev(QueryListTypeReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryListTypeCount_CDMAev"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListTypeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListTypeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListTypeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryListRespDto queryListList_CDMAev(QueryListReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryListList_CDMAev"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryListRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryListRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryListRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryPatiTypeRespDto queryPatiTypeOfServ(CodeRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[39]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryPatiTypeOfServ"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryPatiTypeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryPatiTypeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryPatiTypeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryPayLogCount(CodeDateRequDto codeDateRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[40]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPayLogCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codeDateRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryPayLogOfOnePage(CodeDatePageRequDto codeDatePageRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[41]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryPayLogOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codeDatePageRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryLateFeeDisctCount(CodeDateRequDto codeDateRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[42]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryLateFeeDisctCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codeDateRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryLateFeeDisctOfOnePage(CodeDatePageRequDto codeDatePageRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[43]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryLateFeeDisctOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codeDatePageRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryMXBillCount(QueryMXBillCountRequDto queryMXBillCountReqDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[44]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryMXBillCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryMXBillCountReqDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public BillDataRespDto queryMXBillOfOnePage(QueryMXBillPageRequDto queryMXBillPageRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[45]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryMXBillOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryMXBillPageRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (BillDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (BillDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, BillDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryFZBillCount(QueryFZBillCountRequDto queryFZBillCountRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[46]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryFZBillCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryFZBillCountRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public BillDataRespDto queryFZBillOfOnePage(QueryFZBillPageRequDto queryFZBillPageRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[47]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryFZBillOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryFZBillPageRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (BillDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (BillDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, BillDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public BalanceRespDto queryBalance(CodeRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[48]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryBalance"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (BalanceRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (BalanceRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, BalanceRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryDetailBalanceCount(CodeRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[49]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryDetailBalanceCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryDetailBalanceOfOnePage(CodePageRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[50]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryDetailBalanceOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryBalanceLogCount(CodeDateRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[51]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryBalanceLogCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryBalanceLogOfOnePage(CodeDatePageRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[52]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryBalanceLogOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryAdjusteCount(CodeDateRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[53]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAdjusteCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryAdjusteOfOnePage(CodeDatePageRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[54]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "QueryAdjusteOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryLacGrpCount(CodeRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[55]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryLacGrpCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryLacGrpOfOnePage(CodePageRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[56]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryLacGrpOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CountRespDto queryLacCount(CodeRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[57]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryLacCount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryLacOfOnePage(CodePageRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[58]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryLacOfOnePage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ServAcctInfoRespDto queryServAcctInfo(QueryBillItemReqDto queryBillItemReqDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[59]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryServAcctInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryBillItemReqDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ServAcctInfoRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (ServAcctInfoRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, ServAcctInfoRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAccNbrByAcctCodeRespDto queryServByAcctCode(QueryAccNbrReqDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[60]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryServByAcctCode"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAccNbrByAcctCodeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAccNbrByAcctCodeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAccNbrByAcctCodeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryOweRespDto queryowelist(QueryOweReqDto queryOweReqDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[61]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryowelist"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {queryOweReqDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryOweRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryOweRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryOweRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TableDataRespDto queryOweAcct(CodeRequDto codeRequDto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[62]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryOweAcct"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {codeRequDto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TableDataRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TableDataRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TableDataRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAgentUpperAmountRespDto queryAgentUpperAmount(QueryAgentUpperAmountReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[63]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAgentUpperAmount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAgentUpperAmountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAgentUpperAmountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAgentUpperAmountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public UpdateAgentUpperAmountRespDto updateAgentUpperAmount(UpdateAgentUpperAmountReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[64]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "updateAgentUpperAmount"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (UpdateAgentUpperAmountRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (UpdateAgentUpperAmountRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, UpdateAgentUpperAmountRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public UpdateAgentChargeRespDto updateAgentCharge(UpdateAgentChargeReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[65]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "updateAgentCharge"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (UpdateAgentChargeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (UpdateAgentChargeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, UpdateAgentChargeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public AddAgentPropertyRespDto addAgentProperty(AddAgentPropertyReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[66]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "addAgentProperty"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (AddAgentPropertyRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (AddAgentPropertyRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, AddAgentPropertyRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryAgentPropertyRespDto queryAgentProperty(QueryAgentPropertyReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[67]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryAgentProperty"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryAgentPropertyRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryAgentPropertyRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryAgentPropertyRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public UpdateAgentUpperAmountAndChargeRespDto updateAgentUpperAmountAndCharge(UpdateAgentUpperAmountAndChargeReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[68]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "updateAgentUpperAmountAndCharge"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (UpdateAgentUpperAmountAndChargeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (UpdateAgentUpperAmountAndChargeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, UpdateAgentUpperAmountAndChargeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryCreditLimitRespDto queryCreditLimit(QueryCreditLimitReqDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[69]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryCreditLimit"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryCreditLimitRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryCreditLimitRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryCreditLimitRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryServStateRespDto[] queryServState(CodeRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[70]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryServState"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryServStateRespDto[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryServStateRespDto[]) org.apache.axis.utils.JavaUtils.convert(_resp, QueryServStateRespDto[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryOweFeeRespDto queryOweFee(CodeRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[71]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryOweFee"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryOweFeeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryOweFeeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryOweFeeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryBadFeeRespDto queryBadFee(CodeRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[72]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryBadFee"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryBadFeeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryBadFeeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryBadFeeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryCashFeeRespDto queryCashFee(CodeRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[73]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryCashFee"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryCashFeeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryCashFeeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryCashFeeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ServActiveRespDto servActive(ServActiveRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[74]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "servActive"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ServActiveRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (ServActiveRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, ServActiveRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryActiveBalanceRespDto queryActiveBalance(QueryActiveBalanceRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[75]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryActiveBalance"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryActiveBalanceRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryActiveBalanceRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryActiveBalanceRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryActiveBalChangeRespDto queryActiveBalChange(QueryActiveBalChangeRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[76]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryActiveBalChange"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryActiveBalChangeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryActiveBalChangeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryActiveBalChangeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryActiveBillRespDto queryActiveBill(QueryActiveBillRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[77]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryActiveBill"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryActiveBillRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryActiveBillRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryActiveBillRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public FluxStopQryRespDto queryFluxStop(FluxStopQryRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[78]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryFluxStop"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (FluxStopQryRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (FluxStopQryRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, FluxStopQryRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public RecoverFluxRespDto recoverFlux(RecoverFluxRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[79]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "recoverFlux"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (RecoverFluxRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (RecoverFluxRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, RecoverFluxRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public TermiRecoverRespDto termiRecover(TermiRecoverRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[80]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "termiRecover"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (TermiRecoverRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (TermiRecoverRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, TermiRecoverRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CardAgreementRespDto cardAgreement(CardAgreementRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[81]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "cardAgreement"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CardAgreementRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CardAgreementRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CardAgreementRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ServInfoForListRespDto queryServInfoForList(CodeDateRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[82]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryServInfoForList"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ServInfoForListRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (ServInfoForListRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, ServInfoForListRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public ServiceBillingInfoRespDto queryServiceBillingInfo(ServiceBillingRequCsiDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[83]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryServiceBillingInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (ServiceBillingInfoRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (ServiceBillingInfoRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, ServiceBillingInfoRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public CreditValueChangeRespDto changeCreditValue(CreditValueChangeRequDto dto) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[84]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "changeCreditValue"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {dto});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (CreditValueChangeRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (CreditValueChangeRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, CreditValueChangeRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QuerySixMonthBillRespDto querySixMonthBill(CodeRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[85]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "querySixMonthBill"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QuerySixMonthBillRespDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QuerySixMonthBillRespDto) org.apache.axis.utils.JavaUtils.convert(_resp, QuerySixMonthBillRespDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public QueryCreditForComptDto queryCreditForCompt(CodeRequDto req) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[86]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://bll.custserviceinfmgr.bsn.ztesoft.com   ", "queryCreditForCompt"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {req});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (QueryCreditForComptDto) _resp;
            } catch (java.lang.Exception _exception) {
                return (QueryCreditForComptDto) org.apache.axis.utils.JavaUtils.convert(_resp, QueryCreditForComptDto.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
