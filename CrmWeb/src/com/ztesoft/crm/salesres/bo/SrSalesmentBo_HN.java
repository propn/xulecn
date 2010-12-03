package com.ztesoft.crm.salesres.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcEntityDAO;
import com.ztesoft.crm.salesres.dao.RcNoSegDAO;
import com.ztesoft.crm.salesres.dao.RcSaleLogDAO;
import com.ztesoft.crm.salesres.dao.RcSimDAO;
import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.exception.SaleResLogicException;
import com.ztesoft.crm.salesres.vo.EntityResourceVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO;
import com.ztesoft.crm.salesres.vo.RcSaleLogVO;
import com.ztesoft.crm.salesres.vo.RcStockVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

/**
 * Ϊ����ǰ̨�ṩ�Ľӿ�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SrSalesmentBo_HN extends SrSalesmentBo{
  public SrSalesmentBo_HN() {
  }

  /**
   * ������Դʵ�����۵ķ��������ڴ������Դʵ����ռ����Դ�������Ӧ�����
   * �������һ����Դ����ʧ��������׳��쳣��ȫ������(�����ɹ������)���ع�
   * @param list��EntityResourceVO��ɵ�List��ÿ��EntityResourceVO����һ����Ҫ�������Դʵ��
   * @return Map:resultFlag�����־��1�ɹ���0ʧ�ܣ���continueFlag���������־��1 ���� 0 ����������
   * failInfo������Ϣ������continueFlagĿǰֻ����1
   * @throws Exception:��֤�������ΪDAOSystemException��ع����쳣�а����д�����Ϣ
   */
  public Map entitySale(List list) throws SaleResLogicException{
      return this.entitySaleOrRele(list,1);
  }

  /**
   * ��Դʵ���ͷŷ��������ڴ������Դʵ�����ͷ���Դ��������Ӧ�����
   * �������һ����Դ����ʧ��������׳��쳣��ȫ������(�����ɹ������)���ع�
   * @param list��EntityResourceVO��ɵ�List��ÿ��EntityResourceVO����һ����Ҫ�������Դʵ��
   * @return Map:resultFlag�����־��1�ɹ���0ʧ�ܣ���continueFlag���������־��1 ���� 0 ����������
   * failInfo������Ϣ������continueFlagĿǰֻ����1
   * @throws Exception:��֤�������ΪDAOSystemException��ع����쳣�а����д�����Ϣ
   */
  public Map entityRelease(List list) throws SaleResLogicException{
      return this.entitySaleOrRele(list,2);
  }

  /**
 * ������Դʵ�����۵ķ��������ڴ������Դʵ����ռ����Դ�������Ӧ�����
 * �������һ����Դ����ʧ��������׳��쳣��ȫ������(�����ɹ������)���ع�
 * @param list��EntityResourceVO��ɵ�List��ÿ��EntityResourceVO����һ����Ҫ�������Դʵ��
 * @param type��1�������ۣ�2�����ͷ�
 * @return Map:resultFlag�����־��1�ɹ���0ʧ�ܣ���continueFlag���������־��1 ���� 0 ����������
 * failInfo������Ϣ������continueFlagĿǰֻ����1
 * @throws Exception:��֤�������ΪDAOSystemException��ع����쳣�а����д�����Ϣ
 */
private Map entitySaleOrRele(List list,int type) throws SaleResLogicException{
   Map map = new HashMap();
   String keyMess = "����";
   String state = ParamsConsConfig.RcEntityStateInValide;
   String amount = "-1";
   int changeType = 2;
   if(type==1){
     keyMess = "����";
     state = ParamsConsConfig.RcEntityStateInValide;
     amount = "-1";
     changeType = 2;
   }else if(type==2){
     keyMess = "����";
     state = ParamsConsConfig.RcEntityStateValide;
     amount = "1";
     changeType = 1;
   }
   String resultFlag = "1";
   String continueFlag = "1";
   StringBuffer failInfo = new StringBuffer();
   SalesRescDAO rescDao = SrDAOFactory.getInstance().getSalesRescDAO();
   SalesRescVO rescVO = null;
   String manageMode = null;
   Map rtnMap = null;
   int successNum = 0;
   String result = "-1"; // �����Ƿ����Ĭ��Ϊ����,�������Դ�߼���������׳��쳣����֤������ԴҪôȫ������Ҫôȫ��������
   String message = "";
   if(list==null||list.size()<1){
           Debug.print("��SrSalesmentBo������entitySale(List list)���������Դʵ����Ϊ0");
           resultFlag = "1";
           failInfo.append("�������Դʵ����Ϊ0");
   }else{
           for(int i=0;i<list.size();i++){
                   EntityResourceVO vo = (EntityResourceVO)list.get(i);
                   if(vo!=null){
                        rescVO = rescDao.findByPrimaryKey(vo.getSalesRescId());
                        if(rescVO!=null&&rescVO.getSalesRescId()!=null&&rescVO.getSalesRescId().trim().length()>0){
                             manageMode = rescVO.getManageMode();
                             if (SalesRescVO.ManageMode_Entity.equals(manageMode)) { // ʵ������ģʽ
                               if (vo.getRescInstance2() != null &&
                                   vo.getRescInstance2().trim().length() > 0) {
                                 vo.setState(state);
                                 rtnMap = this.entityStatueChange(vo);
                                 if (rtnMap != null && rtnMap.get("result") != null)
                                   result = (String) rtnMap.get("result");
                                 if ("0".equals(result)) {
                                   successNum++;
                                 }
                                 else {
                                   message = (String) rtnMap.get("message");
                                   Debug.print("��SrSalesmentBo������entitySale(List list)�����к�Ϊ" +
                                               vo.getRescInstance2() + "����Դʵ������ʧ��!");
                                   resultFlag = "0";
                                   throw new SaleResLogicException(message + ",����"+keyMess+"!");
                                   //failInfo.append("���к�Ϊ"+vo.getRescInstance2()+"����Դʵ������ʧ��!").append("\n");
                                 }
                               }
                               else {  // ���������к�Ϊ��
                                   throw new SaleResLogicException("���ʣ�"+rescVO.getSalesRescName() + "Ϊʵ�����������������к�!");
                               }
                             }
                             else if (SalesRescVO.ManageMode_Stock.equals(manageMode)) { // ��������ģʽ
                               // Ŀǰ�ǶԵ���ʵ�����δ���ģ�������Ϊ1����Ӧ��ʵ�������ı亯��Ҳ�ǶԵ����ֿ⴦��������
                               vo.setAmount(amount);
                               rtnMap = this.entityAmountChange(vo, changeType);
                               if (rtnMap != null && rtnMap.get("result") != null)
                                 result = (String) rtnMap.get("result");
                               if ("0".equals(result)) {
                                 successNum++;
                               }
                               else {
                                 message = (String) rtnMap.get("message");
                                 Debug.print(
                                     "��SrSalesmentBo������entitySale(List list)�����Ӫ����Դ�ڴ���������ʱʧ��!vo:" +
                                     vo);
                                 resultFlag = "0";
                                 throw new SaleResLogicException(message + ",����"+keyMess+"!");
                               }
                             }
                             else {
                               throw new SaleResLogicException("Ӫ����Դ��"+rescVO.getSalesRescName() + "����ģʽΪ������,����"+keyMess+"!");
                             }
                        }else{
                           throw new SaleResLogicException("�������󣬲�����idΪ"+vo.getSalesRescId()+"��Ӫ����Դ!");
                        }
                   }else{
                           Debug.print("��SrSalesmentBo������entitySale(List list):���󣬴�����Ҫ���������Ϊ��!");
                           throw new SaleResLogicException(keyMess+"ʧ�ܣ�������Ҫ���������Ϊ��!");
                   }
           }
           failInfo.append("�ɹ�����"+keyMess+"!");
   }
   // �õ�Ӫ����Դ�ļ۸���Ϣ
   Map priceMap = this.getSalesRescPriceInfo(list);
   if(priceMap==null)
           priceMap = new HashMap();

   map.put("resultFlag", resultFlag);
   map.put("continueFlag", continueFlag);
   map.put("failInfo", failInfo.toString());
   map.put("price", priceMap);
   return map;
}


  /**
   * ������Դʵ��״̬
   * @param vo:��Ҫ�������ֶΣ�rescInstance2��status��departId
   * @return ����ɹ�ʱ������ֵΪ0������ʧ��ʱ������ֵΪ-1��
   */
  private Map entityStatueChange(EntityResourceVO vo) {
          Map map = new HashMap();
          String result = "0";
          String message = "";
          if(vo==null||vo.getRescInstance2()==null||vo.getRescInstance2().trim().length()<1
             ||vo.getState()==null||vo.getState().trim().length()<1||vo.getOperId()==null||vo.getOperId().trim().length()<1
             ||vo.getDepartId()==null||vo.getDepartId().trim().length()<1
             ||vo.getSalesRescId()==null||vo.getSalesRescId().trim().length()<1){
             message = "����ʧ�ܣ�ȱ�ٲ���";
             map.put("result","-1");
             map.put("message",message);
             return map;
          }
          RcEntityDAO entityDao = SrDAOFactory.getInstance().getRcEntityDAO();
          RcSimDAO simDao = SrNSDAOFactory.getInstance().getRcSimDAO();
          SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
          RcSaleLogDAO saleLogDao = SrDAOFactory.getInstance().getRcSaleLogDAO();
          SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
          RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
          SalesRescDAO salesRescDao = SrDAOFactory.getInstance().getSalesRescDAO();
          String tableName = "rc_entity";
          // ����familyId�ж����������Ƿ���sim��������ͨ����ʵ��
          String attrCode = "Rc_SIM_FamilyId";
          String familyId = segDao.findFamilyId(attrCode);
          SalesRescVO salesRescVO = salesRescDao.findByPrimaryKey(vo.getSalesRescId());
          if(salesRescVO!=null&&familyId!=null&&familyId.equals(salesRescVO.getFamilyId())){
             // ˵����sim����������
             tableName = "rc_sim";
          }else if(salesRescVO==null){
             message = "���к�Ϊ"+vo.getRescInstance2()+"����Դʵ����Ӫ����Դ������";
             map.put("result","-1");
             map.put("message",message);
             return map;
          }

          String old_rescState = null;
          String old_state = null;
          String old_instanceCode = null;
          String old_storageId = null;
          String old_rescInstanceId = null;

          //�ڵ�λ��Ȩ�޲鿴�Ĳֿ��в�ѯ��Դʵ��
          if(!"rc_sim".equals(tableName)){
            String sql1 = "SELECT  RESOURCE_INSTANCE_ID,A.SALES_RESOURCE_ID," +
                "A.RESOURCE_INSTANCE_CODE,A.RESOURCE_LEVEL,A.RESOURCE_KIND," +
                "A.LAN_ID,A.OWNER_TYPE,A.OWNER_ID,A.STORAGE_ID,A.CURR_STATE," +
                "A.STATE,A.CREATE_DATE,A.EFF_DATE,A.EXP_DATE,A.PK_CALBODY," +
                "A.CINVENTORYID,A.VBATCHCODE " +
                " FROM RC_ENTITY A where A.RESOURCE_INSTANCE_CODE=? and A.SALES_RESOURCE_ID=? ";
            String sqlParams[] = null;
            if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // �����Ҫ������Ʒ,��Ҫ����Ȩ����֤
               sql1 += " and (exists(select B.STORAGE_ID from STORAGE_DEPART_RELA B where A.STORAGE_ID=B.STORAGE_ID and B.depart_id=?) " +
                  " or exists(select C.STORAGE_ID from mp_storage C where A.STORAGE_ID=C.STORAGE_ID and C.OPER_ID=?)) ";
               sqlParams = new String[] {vo.getRescInstance2(), vo.getSalesRescId(),vo.getDepartId(),vo.getOperId()};
            }else if(ParamsConsConfig.RcEntityStateValide.equals(vo.getState())){
               sqlParams = new String[] {vo.getRescInstance2(), vo.getSalesRescId()};
            }
            List rtnList = entityDao.findBySql(sql1, sqlParams);
            if (rtnList == null || rtnList.size() < 1 || rtnList.get(0) == null) {
              Debug.print("��SrSalesmentBo������entityStatueChange����ѯΪ��sql:"  + sql1 +
                          "##depart_id:" + vo.getDepartId() + " ##RESOURCE_INSTANCE_CODE:" +
                          vo.getRescInstance2());
              message = "���к�Ϊ" + vo.getRescInstance2() + "����Դʵ��������";
              if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // �����Ҫ������Ʒ,��Ҫ����Ȩ����֤
                 message += "��û��Ȩ��";
              }
              map.put("result", "-1");
              map.put("message", message);
              return map;
            }else{
              RcEntityVO voTemp = (RcEntityVO) rtnList.get(0);
              old_rescState = voTemp.getRescState();
              old_state = voTemp.getState();
              old_instanceCode = voTemp.getRescInstanceCode();
              old_storageId = voTemp.getStorageId();
              old_rescInstanceId = voTemp.getRescInstanceId();
            }
          }else{  // �����sim����������
             String sql2 = "SELECT a.BACKUP,a.STATE,a.RESOURCE_STATE,a.STORAGE_ID,a.SALES_RESOURCE_ID,"
                         +"a.RESOURCE_LEVEL,a.RESOURCE_INSTANCE_CODE,a.RESOURCE_INSTANCE_ID "
                         +" FROM RC_SIM a where a.RESOURCE_INSTANCE_CODE='"+DAOUtils.filterQureyCond(vo.getRescInstance2())+"'"
                         +" and a.SALES_RESOURCE_ID="+DAOUtils.filterQureyCond(vo.getSalesRescId());
             if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // �����Ҫ������Ʒ,��Ҫ����Ȩ����֤
                sql2 += " and (exists(select B.STORAGE_ID from STORAGE_DEPART_RELA B where A.STORAGE_ID=B.STORAGE_ID and B.depart_id="+DAOUtils.filterQureyCond(vo.getDepartId())+") "
                         +" or exists(select C.STORAGE_ID from mp_storage C where A.STORAGE_ID=C.STORAGE_ID and C.OPER_ID="+DAOUtils.filterQureyCond(vo.getOperId())+")) ";
             }
             String[] arrs = new String[]{"BACKUP","STATE","RESOURCE_STATE","STORAGE_ID","SALES_RESOURCE_ID","RESOURCE_LEVEL","RESOURCE_INSTANCE_CODE","RESOURCE_INSTANCE_ID"};
             List list2 = comDao.qryComSql(sql2,arrs);
             if (list2 == null || list2.size() < 1 || list2.get(0) == null) {
                 Debug.print("��SrSalesmentBo������entityStatueChange����ѯΪ��sql:" + sql2 + "##depart_id:" +
                             vo.getDepartId() + " ##RESOURCE_INSTANCE_CODE:" +vo.getRescInstance2());
                 message = "���к�Ϊ" + vo.getRescInstance2() + "��sim��������";
                 if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // �����Ҫ������Ʒ,��Ҫ����Ȩ����֤
                     message += "��û��Ȩ��";
                 }
                 map.put("result", "-1");
                 map.put("message", message);
                 return map;
             }else{
                 Map simMap = (Map)list2.get(0);
                 old_rescState = (String)simMap.get("RESOURCE_STATE");
                 old_state = (String)simMap.get("STATE");
                 old_instanceCode = (String)simMap.get("RESOURCE_INSTANCE_CODE");
                 old_storageId = (String)simMap.get("STORAGE_ID");
                 old_rescInstanceId = (String)simMap.get("RESOURCE_INSTANCE_ID");
             }
          }

          if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // �����Ҫ������Ʒ
             if(ParamsConsConfig.RcEntityStateInValide.equals(old_state))
             {
                  Debug.print("��SrSalesmentBo������entityStatueChange,ʵ��11:"+old_instanceCode+"�ǳ���״̬������ʧ��!");
                  message = "���к�Ϊ"+vo.getRescInstance2()+"����Դʵ�����ڳ���״̬";
                  map.put("result","-1");
                  map.put("message",message);
                  return map;
             }
             if(!"rc_sim".equals(tableName)){
               if (!ParamsConsConfig.RcEntityFreeState.equals(old_rescState)) {
                 Debug.print("��SrSalesmentBo������entityStatueChange,ʵ��11:" +
                             old_instanceCode + "���ǿ���״̬������ʧ��!");
                 message = "���к�Ϊ" + vo.getRescInstance2() + "���ǿ���״̬";
                 map.put("result", "-1");
                 map.put("message", message);
                 return map;
               }
             }else{ // �ж�sim�����е����ƣ�ֻ���뿪ͨ״̬��������
               if (!ParamsConsConfig.RescState_preSetUse.equals(old_rescState)) {
                Debug.print("��SrSalesmentBo������entityStatueChange,sim��:" +
                            old_instanceCode + "����Ԥ��ͨ״̬������ʧ��!");
                message = "���к�Ϊ" + vo.getRescInstance2() + "����Ԥ��ͨ״̬";
                map.put("result", "-1");
                map.put("message", message);
                return map;
              }
             }
          }else if(ParamsConsConfig.RcEntityStateValide.equals(vo.getState())){  // �����Ҫ�ͷ���Ʒ
             if(ParamsConsConfig.RcEntityStateValide.equals(old_state))
             {
                  Debug.print("��SrSalesmentBo������entityStatueChange,ʵ��11:"+old_instanceCode+"���ڿ�״̬���ͷ�ʧ��!");
                  message = "���к�Ϊ"+vo.getRescInstance2()+"����Դʵ�����ڳ���״̬";
                  map.put("result","-1");
                  map.put("message",message);
                  return map;
             }
          }

          String resourceStateStr = "";
          if(vo.getRescState()!=null&&vo.getRescState().trim().length()>0){
             resourceStateStr = ",CURR_STATE=";
             if("rc_sim".equals(tableName))
                resourceStateStr = ",RESOURCE_STATE=";
             resourceStateStr += "'"+DAOUtils.filterQureyCond(vo.getRescState())+"'";
          }
          // ����״̬Ϊ����״̬
          String updateSql = "update "+tableName+" set state='"+vo.getState()+"'"+resourceStateStr+" where RESOURCE_INSTANCE_ID="+old_rescInstanceId;
          int rtnCount = comDao.excute(updateSql);
          if(rtnCount<1){
              Debug.print("��SrSalesmentBo������entityStatueChange,������Դռ��״̬ʧ�ܣ�voTemp:");
              message = "������Դʵ��"+vo.getRescInstance2()+"ʧ�ܣ����ݿ����ʧ��";
              map.put("result","-1");
              map.put("message",message);
              return map;
          }
          // ���������־��¼
          RcSaleLogVO voInsert = new RcSaleLogVO();
          String logId = sequenceManageDAO.getNextSequence("rc_sale_log","log_id");
          voInsert.setLogId(logId);
          voInsert.setSaleTime(DAOUtils.getFormatedDate());
          voInsert.setSalesRescId(vo.getSalesRescId());
          voInsert.setRescInstance2(vo.getRescInstance2());
          voInsert.setDepartId(vo.getDepartId());
          voInsert.setOperId(vo.getOperId());
          voInsert.setCustId(vo.getCustId());
          voInsert.setDealInfo(vo.getDealInfo());
          voInsert.setDealType(vo.getDealType());
          voInsert.setStyle("A");
          if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){
              voInsert.setIsOut("o");
              voInsert.setStockAmount("-1");
          }
          else if(ParamsConsConfig.RcEntityStateValide.equals(vo.getState())){
              voInsert.setIsOut("i");
              voInsert.setStockAmount("1");
          }
          voInsert.setStorageId(old_storageId);
          voInsert.setBeforeState(old_rescState);
          voInsert.setAfterState(vo.getRescState());
          // ����ʵ��imei���ԣ����ڴ˲��� TODO
          String sql_imei = "select a.ATTR_VALUE from RC_ENTITY_ATTR a,attribute b where a.ATTR_ID=b.ATTR_ID "
                            +" and b.ATTR_CODE='IMEI' and a.ENTITY_ID="+old_rescInstanceId;
          String[] imeiArr = new String[]{"ATTR_VALUE"};
          List list3 = comDao.qryComSql(sql_imei,imeiArr);
          if(list3!=null&&list3.size()>0){
             Map mapTemp = (Map)list3.get(0);
             voInsert.setImei((String)mapTemp.get("ATTR_VALUE"));
          }
          voInsert.setAcceptId(vo.getAcceptId());
          saleLogDao.insert(voInsert);
          Debug.print("��SrSalesmentBo������entityStatueChange,�����������־voInsert:"+voInsert);
          map.put("result","0");
          map.put("message",message);
          return map;
  }

  /**
   * ��Ҫ����ʵ���Ĳֿ������
   * @param vo����Ҫ�������ֶΣ�salesRescId��departId��amount
   * @param changeType���������ͣ�1��������2�Ǽ���
   * @return ����ɹ�ʱ������ֵΪ0������ʧ�ܻ�����������0ʱ������ֵΪ-1��
   */
  private Map entityAmountChange(EntityResourceVO vo,int changeType){
          Map map = new HashMap();
          String result = "0";
          String message = "";
          Debug.print("��SrSalesmentBo������entityAmountChange����changeType:"+changeType+"||vo:"+vo);
          if(vo==null||vo.getSalesRescId()==null||vo.getSalesRescId().trim().length()<1
             ||vo.getDepartId()==null||vo.getDepartId().trim().length()<1
             ||vo.getAmount()==null||vo.getAmount().trim().length()<1)
          {
               Debug.print("��SrSalesmentBo������entityAmountChange����ȱ�٣�����!");
               message = "����ʧ�ܣ�ȱ�ٲ���";
               map.put("result","-1");
               map.put("message",message);
               return map;
          }
          RcStockDAO stockDao = SrDAOFactory.getInstance().getRcStockDAO();
          RcSaleLogDAO saleLogDao = SrDAOFactory.getInstance().getRcSaleLogDAO();
          SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
          String sql = "SELECT a.storage_id,b.storage_name,a.sales_resource_id,c.sales_resource_name,a.stock_amount FROM RC_STOCK a,"
                       +" RC_STORAGE b,SALES_RESOURCE c where a.storage_id=b.storage_id and a.sales_resource_id=c.sales_resource_id"
                       +" and a.sales_resource_id="+vo.getSalesRescId();
          //if(changeType==1){ // ������ͷ���Դ�������־�в�����ص��������
          //   sql += " and exists(select d.accept_id from rc_sale_log d where d.IS_OUT='o' and d.accept_id="+vo.getAcceptId()
          //          +" and d.sales_resource_id="+vo.getSalesRescId()+" and a.storage_id=d.storage_id)";
          //}else if(changeType==2){ // ��������ۣ�����Ҫ����Ȩ�޿���
             sql += " and (exists(select d.STORAGE_ID from STORAGE_DEPART_RELA d where a.STORAGE_ID=d.STORAGE_ID and d.depart_id="+vo.getDepartId()+") "
                    +" or exists(select e.STORAGE_ID from mp_storage e where a.STORAGE_ID=e.STORAGE_ID and e.OPER_ID="+vo.getOperId()+")) ";
          //}
          stockDao.setSQL_SELECT(sql);
          List rtnList = stockDao.findByCond("");
          Debug.print("��SrSalesmentBo������entityAmountChange��ѯ���:"+sql);
          if(rtnList==null||rtnList.size()<1){
             Debug.print("��SrSalesmentBo������entityAmountChange��ѯ���������rtnListΪ�գ�Ĭ�ϴ���ɹ�");
             map.put("result","0");
             map.put("message","��ѯӪ����ԴID:"+vo.getSalesRescId()+"Ϊ�գ���Ĭ�ϳɹ�");
             return map;
          }
          RcStockVO voTemp = null;
          String storageId = null;
          boolean needExtra = true;
          for(int i=0;i<rtnList.size();i++){
              voTemp = (RcStockVO)rtnList.get(i);
              storageId = ((RcStockVO)rtnList.get(i)).getStorageId();
              if(voTemp!=null&&storageId!=null&&storageId.trim().length()>0){
                 if(changeType==1){
                 // �����������Դ��������1
                         int amount = Integer.parseInt(voTemp.getStockAmount())+Integer.parseInt(vo.getAmount());
                         stockDao.updateAmount(storageId, vo.getSalesRescId(), String.valueOf(amount));
                         needExtra = false;
                         break;
                 }else if(changeType==2){
                 // ����Ǽ������ʣ���쿴�����Ƿ����amount�����������ֱ�Ӽ������������С����ѭ����һ���ֿ�
                 // ������Ϊһ�δ��������Ϊ1�����Բ����ж�ֿ������ݼ���
                         int amount = Integer.parseInt(vo.getAmount());
                         if(amount>0)
                                 amount = -amount;
                         amount = Integer.parseInt(voTemp.getStockAmount())+amount;
                         if(amount>=0){
                                 stockDao.updateAmount(storageId, vo.getSalesRescId(), String.valueOf(amount));
                                 needExtra = false;
                                 break;
                         }else
                                 continue;
                 }
              }
          }
          if(needExtra){
              // �����ǶԲֿ��������������û�вֿ���Ϣ�����ش�����Ϣ
              // ˵��û��һ���ֿ���Ӧ���ʵ�����������ۼ����������ڴ˷���ʧ�ܡ�
              // ���Ҫ���жԲֿ������������ۼ���������Ҫ����
              if(voTemp!=null)
                 message = "Ӫ����Դ��"+voTemp.getSalesRescName()+"����������";
              else
                 message = "Ӫ����ԴID��"+vo.getSalesRescId()+"����������";
              Debug.print("��SrSalesmentBo������entityAmountChange��needExtra:"+needExtra+"||changeType:"+changeType);
              map.put("result","-1");
              map.put("message",message);
              return map;
          }
          // ���������־��¼
          RcSaleLogVO voInsert = new RcSaleLogVO();
          String logId = sequenceManageDAO.getNextSequence("rc_sale_log","log_id");
          voInsert.setLogId(logId);
          voInsert.setSaleTime(DAOUtils.getFormatedDate());
          voInsert.setSalesRescId(vo.getSalesRescId());
          //voInsert.setRescInstance2(vo.getRescInstance2());
          if(changeType==1)
                  voInsert.setIsOut("i");
          else if(changeType==2)
                  voInsert.setIsOut("o");
          voInsert.setDepartId(vo.getDepartId());
          voInsert.setOperId(vo.getOperId());
          voInsert.setCustId(vo.getCustId());
          voInsert.setStyle("A");
          voInsert.setStorageId(storageId);
          if((Integer.parseInt(vo.getAmount())>=0)&&changeType==2)
             voInsert.setStockAmount("-"+vo.getAmount());
          else
             voInsert.setStockAmount(vo.getAmount());
          voInsert.setAcceptId(vo.getAcceptId());
          saleLogDao.insert(voInsert);
          Debug.print("��SrSalesmentBo������entityAmountChange,���������־voInsert:"+voInsert);
          map.put("result","0");
          map.put("message",message);
          return map;
  }


}
