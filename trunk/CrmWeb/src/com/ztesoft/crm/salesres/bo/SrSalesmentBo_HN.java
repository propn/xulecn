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
 * 为河南前台提供的接口
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
   * 处理资源实例销售的方法，对于传入的资源实例会占用资源或减少相应库存量
   * 如果其中一个资源处理失败则程序抛出异常，全部处理(包括成功处理的)都回滚
   * @param list：EntityResourceVO组成的List，每个EntityResourceVO代表一个需要处理的资源实例
   * @return Map:resultFlag结果标志（1成功，0失败），continueFlag继续受理标志（1 继续 0 不能受理），
   * failInfo错误信息。其中continueFlag目前只返回1
   * @throws Exception:保证事务，如果为DAOSystemException则回滚，异常中包含有错误信息
   */
  public Map entitySale(List list) throws SaleResLogicException{
      return this.entitySaleOrRele(list,1);
  }

  /**
   * 资源实例释放方法，对于传入的资源实例会释放资源或增加相应库存量
   * 如果其中一个资源处理失败则程序抛出异常，全部处理(包括成功处理的)都回滚
   * @param list：EntityResourceVO组成的List，每个EntityResourceVO代表一个需要处理的资源实例
   * @return Map:resultFlag结果标志（1成功，0失败），continueFlag继续受理标志（1 继续 0 不能受理），
   * failInfo错误信息。其中continueFlag目前只返回1
   * @throws Exception:保证事务，如果为DAOSystemException则回滚，异常中包含有错误信息
   */
  public Map entityRelease(List list) throws SaleResLogicException{
      return this.entitySaleOrRele(list,2);
  }

  /**
 * 处理资源实例销售的方法，对于传入的资源实例会占用资源或减少相应库存量
 * 如果其中一个资源处理失败则程序抛出异常，全部处理(包括成功处理的)都回滚
 * @param list：EntityResourceVO组成的List，每个EntityResourceVO代表一个需要处理的资源实例
 * @param type：1代表销售，2代表释放
 * @return Map:resultFlag结果标志（1成功，0失败），continueFlag继续受理标志（1 继续 0 不能受理），
 * failInfo错误信息。其中continueFlag目前只返回1
 * @throws Exception:保证事务，如果为DAOSystemException则回滚，异常中包含有错误信息
 */
private Map entitySaleOrRele(List list,int type) throws SaleResLogicException{
   Map map = new HashMap();
   String keyMess = "销售";
   String state = ParamsConsConfig.RcEntityStateInValide;
   String amount = "-1";
   int changeType = 2;
   if(type==1){
     keyMess = "销售";
     state = ParamsConsConfig.RcEntityStateInValide;
     amount = "-1";
     changeType = 2;
   }else if(type==2){
     keyMess = "撤单";
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
   String result = "-1"; // 处理是否出错，默认为出错,如果对资源逻辑处理错误，抛出异常，保证所有资源要么全部处理，要么全部部处理
   String message = "";
   if(list==null||list.size()<1){
           Debug.print("类SrSalesmentBo，方法entitySale(List list)，传入的资源实例数为0");
           resultFlag = "1";
           failInfo.append("传入的资源实例数为0");
   }else{
           for(int i=0;i<list.size();i++){
                   EntityResourceVO vo = (EntityResourceVO)list.get(i);
                   if(vo!=null){
                        rescVO = rescDao.findByPrimaryKey(vo.getSalesRescId());
                        if(rescVO!=null&&rescVO.getSalesRescId()!=null&&rescVO.getSalesRescId().trim().length()>0){
                             manageMode = rescVO.getManageMode();
                             if (SalesRescVO.ManageMode_Entity.equals(manageMode)) { // 实例管理模式
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
                                   Debug.print("类SrSalesmentBo，方法entitySale(List list)，序列号为" +
                                               vo.getRescInstance2() + "的资源实例处理失败!");
                                   resultFlag = "0";
                                   throw new SaleResLogicException(message + ",不能"+keyMess+"!");
                                   //failInfo.append("序列号为"+vo.getRescInstance2()+"的资源实例处理失败!").append("\n");
                                 }
                               }
                               else {  // 传来的序列号为空
                                   throw new SaleResLogicException("物资："+rescVO.getSalesRescName() + "为实例管理，必须输入序列号!");
                               }
                             }
                             else if (SalesRescVO.ManageMode_Stock.equals(manageMode)) { // 存量管理模式
                               // 目前是对单个实例依次处理的，所以设为1，相应的实体数量改变函数也是对单个仓库处理数量的
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
                                     "类SrSalesmentBo，方法entitySale(List list)，相关营销资源在处理库存数量时失败!vo:" +
                                     vo);
                                 resultFlag = "0";
                                 throw new SaleResLogicException(message + ",不能"+keyMess+"!");
                               }
                             }
                             else {
                               throw new SaleResLogicException("营销资源："+rescVO.getSalesRescName() + "管理模式为不管理,不能"+keyMess+"!");
                             }
                        }else{
                           throw new SaleResLogicException("发生错误，不存在id为"+vo.getSalesRescId()+"的营销资源!");
                        }
                   }else{
                           Debug.print("类SrSalesmentBo，方法entitySale(List list):错误，传入需要处理的物资为空!");
                           throw new SaleResLogicException(keyMess+"失败，传入需要处理的物资为空!");
                   }
           }
           failInfo.append("成功处理"+keyMess+"!");
   }
   // 得到营销资源的价格信息
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
   * 更新资源实例状态
   * @param vo:需要包含的字段：rescInstance2、status、departId
   * @return 处理成功时，返回值为0；处理失败时，返回值为-1；
   */
  private Map entityStatueChange(EntityResourceVO vo) {
          Map map = new HashMap();
          String result = "0";
          String message = "";
          if(vo==null||vo.getRescInstance2()==null||vo.getRescInstance2().trim().length()<1
             ||vo.getState()==null||vo.getState().trim().length()<1||vo.getOperId()==null||vo.getOperId().trim().length()<1
             ||vo.getDepartId()==null||vo.getDepartId().trim().length()<1
             ||vo.getSalesRescId()==null||vo.getSalesRescId().trim().length()<1){
             message = "操作失败，缺少参数";
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
          // 根据familyId判断物资类型是否是sim卡还是普通物资实例
          String attrCode = "Rc_SIM_FamilyId";
          String familyId = segDao.findFamilyId(attrCode);
          SalesRescVO salesRescVO = salesRescDao.findByPrimaryKey(vo.getSalesRescId());
          if(salesRescVO!=null&&familyId!=null&&familyId.equals(salesRescVO.getFamilyId())){
             // 说明是sim卡类型物资
             tableName = "rc_sim";
          }else if(salesRescVO==null){
             message = "序列号为"+vo.getRescInstance2()+"的资源实例的营销资源不存在";
             map.put("result","-1");
             map.put("message",message);
             return map;
          }

          String old_rescState = null;
          String old_state = null;
          String old_instanceCode = null;
          String old_storageId = null;
          String old_rescInstanceId = null;

          //在单位有权限查看的仓库中查询资源实例
          if(!"rc_sim".equals(tableName)){
            String sql1 = "SELECT  RESOURCE_INSTANCE_ID,A.SALES_RESOURCE_ID," +
                "A.RESOURCE_INSTANCE_CODE,A.RESOURCE_LEVEL,A.RESOURCE_KIND," +
                "A.LAN_ID,A.OWNER_TYPE,A.OWNER_ID,A.STORAGE_ID,A.CURR_STATE," +
                "A.STATE,A.CREATE_DATE,A.EFF_DATE,A.EXP_DATE,A.PK_CALBODY," +
                "A.CINVENTORYID,A.VBATCHCODE " +
                " FROM RC_ENTITY A where A.RESOURCE_INSTANCE_CODE=? and A.SALES_RESOURCE_ID=? ";
            String sqlParams[] = null;
            if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // 如果是要销售物品,需要进行权限验证
               sql1 += " and (exists(select B.STORAGE_ID from STORAGE_DEPART_RELA B where A.STORAGE_ID=B.STORAGE_ID and B.depart_id=?) " +
                  " or exists(select C.STORAGE_ID from mp_storage C where A.STORAGE_ID=C.STORAGE_ID and C.OPER_ID=?)) ";
               sqlParams = new String[] {vo.getRescInstance2(), vo.getSalesRescId(),vo.getDepartId(),vo.getOperId()};
            }else if(ParamsConsConfig.RcEntityStateValide.equals(vo.getState())){
               sqlParams = new String[] {vo.getRescInstance2(), vo.getSalesRescId()};
            }
            List rtnList = entityDao.findBySql(sql1, sqlParams);
            if (rtnList == null || rtnList.size() < 1 || rtnList.get(0) == null) {
              Debug.print("类SrSalesmentBo，方法entityStatueChange，查询为空sql:"  + sql1 +
                          "##depart_id:" + vo.getDepartId() + " ##RESOURCE_INSTANCE_CODE:" +
                          vo.getRescInstance2());
              message = "序列号为" + vo.getRescInstance2() + "的资源实例不存在";
              if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // 如果是要销售物品,需要进行权限验证
                 message += "或没有权限";
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
          }else{  // 如果是sim卡类型物资
             String sql2 = "SELECT a.BACKUP,a.STATE,a.RESOURCE_STATE,a.STORAGE_ID,a.SALES_RESOURCE_ID,"
                         +"a.RESOURCE_LEVEL,a.RESOURCE_INSTANCE_CODE,a.RESOURCE_INSTANCE_ID "
                         +" FROM RC_SIM a where a.RESOURCE_INSTANCE_CODE='"+DAOUtils.filterQureyCond(vo.getRescInstance2())+"'"
                         +" and a.SALES_RESOURCE_ID="+DAOUtils.filterQureyCond(vo.getSalesRescId());
             if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // 如果是要销售物品,需要进行权限验证
                sql2 += " and (exists(select B.STORAGE_ID from STORAGE_DEPART_RELA B where A.STORAGE_ID=B.STORAGE_ID and B.depart_id="+DAOUtils.filterQureyCond(vo.getDepartId())+") "
                         +" or exists(select C.STORAGE_ID from mp_storage C where A.STORAGE_ID=C.STORAGE_ID and C.OPER_ID="+DAOUtils.filterQureyCond(vo.getOperId())+")) ";
             }
             String[] arrs = new String[]{"BACKUP","STATE","RESOURCE_STATE","STORAGE_ID","SALES_RESOURCE_ID","RESOURCE_LEVEL","RESOURCE_INSTANCE_CODE","RESOURCE_INSTANCE_ID"};
             List list2 = comDao.qryComSql(sql2,arrs);
             if (list2 == null || list2.size() < 1 || list2.get(0) == null) {
                 Debug.print("类SrSalesmentBo，方法entityStatueChange，查询为空sql:" + sql2 + "##depart_id:" +
                             vo.getDepartId() + " ##RESOURCE_INSTANCE_CODE:" +vo.getRescInstance2());
                 message = "序列号为" + vo.getRescInstance2() + "的sim卡不存在";
                 if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // 如果是要销售物品,需要进行权限验证
                     message += "或没有权限";
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

          if(ParamsConsConfig.RcEntityStateInValide.equals(vo.getState())){  // 如果是要销售物品
             if(ParamsConsConfig.RcEntityStateInValide.equals(old_state))
             {
                  Debug.print("类SrSalesmentBo，方法entityStatueChange,实例11:"+old_instanceCode+"是出库状态，销售失败!");
                  message = "序列号为"+vo.getRescInstance2()+"的资源实例处于出库状态";
                  map.put("result","-1");
                  map.put("message",message);
                  return map;
             }
             if(!"rc_sim".equals(tableName)){
               if (!ParamsConsConfig.RcEntityFreeState.equals(old_rescState)) {
                 Debug.print("类SrSalesmentBo，方法entityStatueChange,实例11:" +
                             old_instanceCode + "不是可用状态，销售失败!");
                 message = "序列号为" + vo.getRescInstance2() + "不是可用状态";
                 map.put("result", "-1");
                 map.put("message", message);
                 return map;
               }
             }else{ // 判断sim卡特有的限制，只有与开通状态才能销售
               if (!ParamsConsConfig.RescState_preSetUse.equals(old_rescState)) {
                Debug.print("类SrSalesmentBo，方法entityStatueChange,sim卡:" +
                            old_instanceCode + "不是预开通状态，销售失败!");
                message = "序列号为" + vo.getRescInstance2() + "不是预开通状态";
                map.put("result", "-1");
                map.put("message", message);
                return map;
              }
             }
          }else if(ParamsConsConfig.RcEntityStateValide.equals(vo.getState())){  // 如果是要释放物品
             if(ParamsConsConfig.RcEntityStateValide.equals(old_state))
             {
                  Debug.print("类SrSalesmentBo，方法entityStatueChange,实例11:"+old_instanceCode+"是在库状态，释放失败!");
                  message = "序列号为"+vo.getRescInstance2()+"的资源实例处于出库状态";
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
          // 更新状态为出库状态
          String updateSql = "update "+tableName+" set state='"+vo.getState()+"'"+resourceStateStr+" where RESOURCE_INSTANCE_ID="+old_rescInstanceId;
          int rtnCount = comDao.excute(updateSql);
          if(rtnCount<1){
              Debug.print("类SrSalesmentBo，方法entityStatueChange,更新资源占用状态失败，voTemp:");
              message = "更新资源实例"+vo.getRescInstance2()+"失败，数据库操作失败";
              map.put("result","-1");
              map.put("message",message);
              return map;
          }
          // 添加销售日志记录
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
          // 查找实例imei属性，并在此插入 TODO
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
          Debug.print("类SrSalesmentBo，方法entityStatueChange,添加了销售日志voInsert:"+voInsert);
          map.put("result","0");
          map.put("message",message);
          return map;
  }

  /**
   * 需要更新实例的仓库的数量
   * @param vo：需要包含的字段：salesRescId、departId、amount
   * @param changeType：更新类型，1是新增，2是减少
   * @return 处理成功时，返回值为0；处理失败或库存数量少于0时，返回值为-1；
   */
  private Map entityAmountChange(EntityResourceVO vo,int changeType){
          Map map = new HashMap();
          String result = "0";
          String message = "";
          Debug.print("类SrSalesmentBo，方法entityAmountChange参数changeType:"+changeType+"||vo:"+vo);
          if(vo==null||vo.getSalesRescId()==null||vo.getSalesRescId().trim().length()<1
             ||vo.getDepartId()==null||vo.getDepartId().trim().length()<1
             ||vo.getAmount()==null||vo.getAmount().trim().length()<1)
          {
               Debug.print("类SrSalesmentBo，方法entityAmountChange参数缺少，错误!");
               message = "操作失败，缺少参数";
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
          //if(changeType==1){ // 如果是释放资源，则从日志中查找相关的销售情况
          //   sql += " and exists(select d.accept_id from rc_sale_log d where d.IS_OUT='o' and d.accept_id="+vo.getAcceptId()
          //          +" and d.sales_resource_id="+vo.getSalesRescId()+" and a.storage_id=d.storage_id)";
          //}else if(changeType==2){ // 如果是销售，则需要增加权限控制
             sql += " and (exists(select d.STORAGE_ID from STORAGE_DEPART_RELA d where a.STORAGE_ID=d.STORAGE_ID and d.depart_id="+vo.getDepartId()+") "
                    +" or exists(select e.STORAGE_ID from mp_storage e where a.STORAGE_ID=e.STORAGE_ID and e.OPER_ID="+vo.getOperId()+")) ";
          //}
          stockDao.setSQL_SELECT(sql);
          List rtnList = stockDao.findByCond("");
          Debug.print("类SrSalesmentBo，方法entityAmountChange查询语句:"+sql);
          if(rtnList==null||rtnList.size()<1){
             Debug.print("类SrSalesmentBo，方法entityAmountChange查询结果有问题rtnList为空，默认处理成功");
             map.put("result","0");
             map.put("message","查询营销资源ID:"+vo.getSalesRescId()+"为空，但默认成功");
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
                 // 如果是新增资源则数量加1
                         int amount = Integer.parseInt(voTemp.getStockAmount())+Integer.parseInt(vo.getAmount());
                         stockDao.updateAmount(storageId, vo.getSalesRescId(), String.valueOf(amount));
                         needExtra = false;
                         break;
                 }else if(changeType==2){
                 // 如果是减少物资，则察看数量是否大于amount，如果大于则直接减少数量，如果小于则循环下一个仓库
                 // 这里因为一次处理的数量为1，所以不进行多仓库轮流递减。
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
              // 无论是对仓库的新增或减少如果没有仓库信息都返回错误信息
              // 说明没有一个仓库相应物资的数量满足需扣减的数量，在此返回失败。
              // 如果要进行对仓库数量的轮流扣减，代码需要更改
              if(voTemp!=null)
                 message = "营销资源："+voTemp.getSalesRescName()+"，数量不足";
              else
                 message = "营销资源ID："+vo.getSalesRescId()+"，数量不足";
              Debug.print("类SrSalesmentBo，方法entityAmountChange中needExtra:"+needExtra+"||changeType:"+changeType);
              map.put("result","-1");
              map.put("message",message);
              return map;
          }
          // 添加销售日志记录
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
          Debug.print("类SrSalesmentBo，方法entityAmountChange,添加销售日志voInsert:"+voInsert);
          map.put("result","0");
          map.put("message",message);
          return map;
  }


}
