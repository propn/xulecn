/**
 * WebServiceFace_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ztesoft.vsop.bsn.webservice.cq;

public interface WebServiceFace_PortType extends java.rmi.Remote {
    public DeleteAgentPropertyRespDto deleteAgentProperty(DeleteAgentPropertyReqDto dto) throws java.rmi.RemoteException;
    public CheckPwdRespDto checkPwd(CheckPwdReqDto req) throws java.rmi.RemoteException;
    public ChangePwdRespDto changePwd(ChangePwdReqDto req) throws java.rmi.RemoteException;
    public QueryUserTypeRespDto queryUserType(QueryUserTypeReqDto req) throws java.rmi.RemoteException;
    public QueryPatiTypeRespDto queryPatiType(java.lang.String req) throws java.rmi.RemoteException;
    public QueryRelaPhoneRespDto queryRelaPhone(QueryRelaPhoneReqDto req) throws java.rmi.RemoteException;
    public QueryPayRecordRespDto queryPayRecord(QueryPayRecordReqDto req) throws java.rmi.RemoteException;
    public CountRespDto queryPayRecordCount(QueryPayRecordReqDto req) throws java.rmi.RemoteException;
    public QueryPayRecordRespDto queryPayRecordOfOnePage(QueryPayRecordReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException;
    public QueryPatiRespDto queryPati(QueryPatiReqDto req) throws java.rmi.RemoteException;
    public CountRespDto queryPatiCount(QueryPatiReqDto req) throws java.rmi.RemoteException;
    public QueryPatiRespDto queryPatiOfOnePage(QueryPatiReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException;
    public QueryBillRespDto queryBill(QueryBillReqDto req) throws java.rmi.RemoteException;
    public CountRespDto queryBillCount(QueryBillReqDto req) throws java.rmi.RemoteException;
    public QueryBillRespDto queryBillOfOnePage(QueryBillReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException;
    public QueryAccNbrRespDto queryAccNbr(QueryAccNbrReqDto req) throws java.rmi.RemoteException;
    public CountRespDto queryAccNbrCount(QueryAccNbrReqDto req) throws java.rmi.RemoteException;
    public QueryAccNbrRespDto queryAccNbrOfOnePage(QueryAccNbrReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException;
    public QueryAcctBillRespDto queryAcctBill(QueryAcctBillReqDto req) throws java.rmi.RemoteException;
    public CountRespDto queryAcctBillCount(QueryAcctBillReqDto req) throws java.rmi.RemoteException;
    public QueryAcctBillRespDto queryAcctBillOfOnePage(QueryAcctBillReqDto req, int pageNo, int pageSize) throws java.rmi.RemoteException;
    public ListTypeRespDto[] queryListTypeList() throws java.rmi.RemoteException;
    public QueryServRespDto queryServList(QueryServReqDto queryServReqDto) throws java.rmi.RemoteException;
    public QueryAcctRespDto queryAcctList(QueryAcctReqDto queryAcctReqDto) throws java.rmi.RemoteException;
    public QueryBillItemRespDto queryBillItemList(QueryBillItemReqDto queryBillItemRepDto) throws java.rmi.RemoteException;
    public QueryPaymentRecordRespDto queryPaymentCount(QueryPaymentRecordReqDto queryPaymentRecordReqDto) throws java.rmi.RemoteException;
    public QueryPaymentRecordRespDto queryPaymentList(QueryPaymentRecordReqDto queryPaymentRecordReqDto) throws java.rmi.RemoteException;
    public QueryListTypeRespDto queryListTypeCount(QueryListTypeReqDto req) throws java.rmi.RemoteException;
    public QueryListRespDto queryListList(QueryListReqDto req) throws java.rmi.RemoteException;
    public QueryServCreditRespDto queryServCredit(QueryServReqDto req) throws java.rmi.RemoteException;
    public QueryServDisctByImsiRespDto queryServDisctByImsi(QueryServDisctByImsiReqDto req) throws java.rmi.RemoteException;
    public QueryServDisctRespDto queryServDisct(QueryServDisctReqDto req) throws java.rmi.RemoteException;
    public QueryServDisctRespDto queryServDisctForBSN(QueryServDisctReqDto req) throws java.rmi.RemoteException;
    public QueryListTypeRespDto queryListTypeCount_CDMA(QueryListTypeReqDto req) throws java.rmi.RemoteException;
    public QueryListRespDto queryListList_CDMA(QueryListReqDto req) throws java.rmi.RemoteException;
    public QueryListTypeRespDto queryListExCount(QueryCDMAListTypeReqDto req) throws java.rmi.RemoteException;
    public QueryListRespDto queryPatiOfOnePage(QueryListReqDto req) throws java.rmi.RemoteException;
    public QueryListTypeRespDto queryListTypeCount_CDMAev(QueryListTypeReqDto req) throws java.rmi.RemoteException;
    public QueryListRespDto queryListList_CDMAev(QueryListReqDto req) throws java.rmi.RemoteException;
    public QueryPatiTypeRespDto queryPatiTypeOfServ(CodeRequDto req) throws java.rmi.RemoteException;
    public CountRespDto queryPayLogCount(CodeDateRequDto codeDateRequDto) throws java.rmi.RemoteException;
    public TableDataRespDto queryPayLogOfOnePage(CodeDatePageRequDto codeDatePageRequDto) throws java.rmi.RemoteException;
    public CountRespDto queryLateFeeDisctCount(CodeDateRequDto codeDateRequDto) throws java.rmi.RemoteException;
    public TableDataRespDto queryLateFeeDisctOfOnePage(CodeDatePageRequDto codeDatePageRequDto) throws java.rmi.RemoteException;
    public CountRespDto queryMXBillCount(QueryMXBillCountRequDto queryMXBillCountReqDto) throws java.rmi.RemoteException;
    public BillDataRespDto queryMXBillOfOnePage(QueryMXBillPageRequDto queryMXBillPageRequDto) throws java.rmi.RemoteException;
    public CountRespDto queryFZBillCount(QueryFZBillCountRequDto queryFZBillCountRequDto) throws java.rmi.RemoteException;
    public BillDataRespDto queryFZBillOfOnePage(QueryFZBillPageRequDto queryFZBillPageRequDto) throws java.rmi.RemoteException;
    public BalanceRespDto queryBalance(CodeRequDto req) throws java.rmi.RemoteException;
    public CountRespDto queryDetailBalanceCount(CodeRequDto req) throws java.rmi.RemoteException;
    public TableDataRespDto queryDetailBalanceOfOnePage(CodePageRequDto req) throws java.rmi.RemoteException;
    public CountRespDto queryBalanceLogCount(CodeDateRequDto req) throws java.rmi.RemoteException;
    public TableDataRespDto queryBalanceLogOfOnePage(CodeDatePageRequDto req) throws java.rmi.RemoteException;
    public CountRespDto queryAdjusteCount(CodeDateRequDto req) throws java.rmi.RemoteException;
    public TableDataRespDto queryAdjusteOfOnePage(CodeDatePageRequDto req) throws java.rmi.RemoteException;
    public CountRespDto queryLacGrpCount(CodeRequDto req) throws java.rmi.RemoteException;
    public TableDataRespDto queryLacGrpOfOnePage(CodePageRequDto req) throws java.rmi.RemoteException;
    public CountRespDto queryLacCount(CodeRequDto req) throws java.rmi.RemoteException;
    public TableDataRespDto queryLacOfOnePage(CodePageRequDto req) throws java.rmi.RemoteException;
    public ServAcctInfoRespDto queryServAcctInfo(QueryBillItemReqDto queryBillItemReqDto) throws java.rmi.RemoteException;
    public QueryAccNbrByAcctCodeRespDto queryServByAcctCode(QueryAccNbrReqDto req) throws java.rmi.RemoteException;
    public QueryOweRespDto queryowelist(QueryOweReqDto queryOweReqDto) throws java.rmi.RemoteException;
    public TableDataRespDto queryOweAcct(CodeRequDto codeRequDto) throws java.rmi.RemoteException;
    public QueryAgentUpperAmountRespDto queryAgentUpperAmount(QueryAgentUpperAmountReqDto dto) throws java.rmi.RemoteException;
    public UpdateAgentUpperAmountRespDto updateAgentUpperAmount(UpdateAgentUpperAmountReqDto dto) throws java.rmi.RemoteException;
    public UpdateAgentChargeRespDto updateAgentCharge(UpdateAgentChargeReqDto dto) throws java.rmi.RemoteException;
    public AddAgentPropertyRespDto addAgentProperty(AddAgentPropertyReqDto dto) throws java.rmi.RemoteException;
    public QueryAgentPropertyRespDto queryAgentProperty(QueryAgentPropertyReqDto dto) throws java.rmi.RemoteException;
    public UpdateAgentUpperAmountAndChargeRespDto updateAgentUpperAmountAndCharge(UpdateAgentUpperAmountAndChargeReqDto dto) throws java.rmi.RemoteException;
    public QueryCreditLimitRespDto queryCreditLimit(QueryCreditLimitReqDto dto) throws java.rmi.RemoteException;
    public QueryServStateRespDto[] queryServState(CodeRequDto dto) throws java.rmi.RemoteException;
    public QueryOweFeeRespDto queryOweFee(CodeRequDto dto) throws java.rmi.RemoteException;
    public QueryBadFeeRespDto queryBadFee(CodeRequDto dto) throws java.rmi.RemoteException;
    public QueryCashFeeRespDto queryCashFee(CodeRequDto dto) throws java.rmi.RemoteException;
    public ServActiveRespDto servActive(ServActiveRequDto dto) throws java.rmi.RemoteException;
    public QueryActiveBalanceRespDto queryActiveBalance(QueryActiveBalanceRequDto dto) throws java.rmi.RemoteException;
    public QueryActiveBalChangeRespDto queryActiveBalChange(QueryActiveBalChangeRequDto dto) throws java.rmi.RemoteException;
    public QueryActiveBillRespDto queryActiveBill(QueryActiveBillRequDto dto) throws java.rmi.RemoteException;
    public FluxStopQryRespDto queryFluxStop(FluxStopQryRequDto dto) throws java.rmi.RemoteException;
    public RecoverFluxRespDto recoverFlux(RecoverFluxRequDto dto) throws java.rmi.RemoteException;
    public TermiRecoverRespDto termiRecover(TermiRecoverRequDto dto) throws java.rmi.RemoteException;
    public CardAgreementRespDto cardAgreement(CardAgreementRequDto dto) throws java.rmi.RemoteException;
    public ServInfoForListRespDto queryServInfoForList(CodeDateRequDto dto) throws java.rmi.RemoteException;
    public ServiceBillingInfoRespDto queryServiceBillingInfo(ServiceBillingRequCsiDto dto) throws java.rmi.RemoteException;
    public CreditValueChangeRespDto changeCreditValue(CreditValueChangeRequDto dto) throws java.rmi.RemoteException;
    public QuerySixMonthBillRespDto querySixMonthBill(CodeRequDto req) throws java.rmi.RemoteException;
    public QueryCreditForComptDto queryCreditForCompt(CodeRequDto req) throws java.rmi.RemoteException;
}
