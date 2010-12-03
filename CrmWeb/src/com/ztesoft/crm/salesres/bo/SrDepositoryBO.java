package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcAppTypeDAO;
import com.ztesoft.crm.salesres.dao.RcApplicationDAO;
import com.ztesoft.crm.salesres.dao.RcStorageDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrDepositoryDAO;
import com.ztesoft.crm.salesres.vo.MpOperDepartVO;
import com.ztesoft.crm.salesres.vo.RcAppTypeVO;
import com.ztesoft.crm.salesres.vo.RcApplicationVO;
import com.ztesoft.crm.salesres.vo.RcEntityVO;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.RcStockVO;

public class SrDepositoryBO {
    public SrDepositoryBO() {
    }

    /**
     * getAppTypeStyle
     *
     * @param map Map
     * @return Map
     */
    public Map getAppTypeStyle(Map map) {
        if (map == null || map.get("appType") == null) {
            return new HashMap();
        }
        String appType = (String) map.get("appType");
        RcAppTypeDAO dao = SrDAOFactory.getInstance().getRcAppTypeDAO();
        List list = dao.findByCond("app_type='" + appType+"'");
        Map map2 = new HashMap();

        if (list != null) {
            RcAppTypeVO vo = new RcAppTypeVO();
            for (int i = 0; i < list.size(); i++) {
                vo = (RcAppTypeVO) list.get(i);
            }
            map2 = vo.unloadToHashMap();
        }
        return map2;
    }

    /**
     * getStorageInfoByStyle
     *���MAP ΪNULL ��ʾ���Բ�ѯȫ���Ĳֿ⣬����ֻ��ͨ���ʵ���Ȩ�����鿴�ֿ���Ϣ��
     * @param map Map
     * @return List
     */
    public List getStorageInfoByStyle(Map map) {
        List list = new ArrayList();
        String sql = "select distinct st2.storage_id,st2.storage_name,st2.owner_id,st2.storage_state,st2.storage_desc,st2.address,st2.storage_code  from MP_OPERATOR_DEPART od,mp_storage st, STORAGE_DEPART_RELA dr, rc_storage st2 "+
            " where  (od.oper_id=st.oper_id and st.storage_id = st2.storage_id or od.depart_id=dr.depart_id and dr.storage_id = st2.storage_id) ";

        if(map==null){
            RcStorageDAO dao = SrDAOFactory.getInstance().getRcStorageDAO();
           list= dao.findByCond("1=1");

        }else{
            String operId = (String)map.get("operId");
            String departId = (String)map.get("departId");
            String lanId = (String)map.get("lanId");
            String regionId = (String)map.get("regionId");
            if(regionId==null || regionId.trim().length()==0){
            	List MpOperDepartList = SrDAOFactory.getInstance().getMpOperDepartDAO().findByCond(" DEPART_ID="+(departId==null || "".equals(departId.trim())?"-1":departId)+" AND OPER_ID="+(operId==null || "".equals(operId.trim())?"-1":operId));
            	if(MpOperDepartList.size()>0){
            		regionId = ((MpOperDepartVO) MpOperDepartList.get(0)).getRegionId();
            	}
            }
            
            if(operId!=null && departId!=null &&lanId!=null &&  regionId!=null && !lanId.equals("")&& !regionId.equals("")&&! operId.equals("")&& !departId.equals("")){
                sql+=" and od.oper_id="+operId+" and od.depart_id= "+departId+" and od.lan_id="+lanId+" and region_id= "+regionId;
                SrDepositoryDAO dao = SrDAOFactory.getInstance().getSrDepositoryDAO();
               list =  dao.findByCond(sql);
            }
        }
        System.out.println(sql);
        return list;
    }

 public String inOutStock(RcOrderVO vo,HashMap map) throws DAOSystemException {
     String msg = "";
         if(vo!=null && map!=null){
             msg= inOutStock2(vo,map);
         }else{
         msg = "-1";
     }
     return msg;
 }

    /**
     * inOutStock
     *�ж�ORDERID�Ƿ�Ϊ�գ�����������������ɡ�
     *1.дrc_order��
     * 2���������ʽΪ�����洢��дrc_stock��
     * 3���������ʽΪʵ���洢��дrc_entity��
     * 4�������������Ϊ�ɹ�����Ҫдrc_entity���rc_entity_attr��
     * @param vo RcOrderVO
     * @return String
     */
    public String inOutStock2(RcOrderVO vo,HashMap map) throws DAOSystemException {
        String rcOrderId = vo.getOrderId();

        String appType = vo.getAppType();
        //���ʱ�ϴ��ļ�,����ʱ�����洢��ʽ�����������ֶ������
        if(map.get("byFile")!=null && map.get("byFile").equals("1") ||map.get("manageMode")!=null &&map.get("manageMode").equals("1")){
            vo.setResECode("");
            vo.setResBCode("");
        }

        String appId = getRcApplicationId();
        RcApplicationVO appvo = new RcApplicationVO();
        appvo.setAppId(appId);
        appvo.setAppTime(vo.getAppTime());
        appvo.setAppType(vo.getAppType());
//        appvo.setAppTypeName(vo.getAppTypeName());
        appvo.setOperId(vo.getOperId());
        appvo.setDepartId(vo.getDepartId());
        RcApplicationDAO dao = SrDAOFactory.getInstance().getRcApplicationDAO();
        dao.insert(appvo);
        
        vo.setAppId(appId);
        vo.setTacheTime(DateFormatUtils.getFormatedDateTime());
        vo.setAppTime(DateFormatUtils.getFormatedDateTime());
        vo.setEndTime(DateFormatUtils.getFormatedDateTime());
        vo.setRequireTime(DateFormatUtils.getFormatedDateTime());
        vo.setTacheId("5");//���û���Ϊ����
        vo.setStateId("n");//����״̬Ϊ����
        
        vo.setAppStorageId(vo.getInStorageId());
        
        //дRC_ORDER��
        if(rcOrderId.equals("")){
            rcOrderId = getRcOrderId();
            vo.setOrderId(rcOrderId);
            //����������ɵ�����������
          try{
              SrDAOFactory.getInstance().getRcOrderDAO().insert(vo);
           }catch(DAOSystemException dse){
             dse.printStackTrace();
             throw new DAOSystemException("---дrc_order�����쳣,������:"+vo.getOrderId());
           }

        }else{
            //���򣬸���
            try{
              SrDAOFactory.getInstance().getRcOrderDAO().update("order_id="+rcOrderId,vo);
           }catch(DAOSystemException dse){
             dse.printStackTrace();
             throw new DAOSystemException("---дrc_order�����쳣,������:"+vo.getOrderId());
           }

        }
//        if(appType.equals("C")||appType.equals("J")||appType.equals("X")){
//
//            String insql = "insert into rc_bizorder_log (sales_resource_id,oper_id,depart_id,sale_time,biz_type,stock_amount,storage_id,IS_OUT) "
//            	+" select sales_resource_id,oper_id,depart_id,app_time,app_type,app_amount,in_storage_id,'i'  from rc_order where order_id = ?";
//            SqlComDAO cdao = SrDAOFactory.getInstance().getSqlComDAO();
//            String []p = new String[]{vo.getOrderId()};
//            cdao.excute(insql, p);
//        }

        //�������ʽΪ�����洢��дrc_stock��.û����ֹ����ֵ,��дrc_entity����дrc_order_list��
        if(map.get("manageMode")!=null &&map.get("manageMode").equals("1")){
            RcStockVO voo = new RcStockVO();
            String outStorageId =vo.getOutStorageId();
            String inStorageId = vo.getInStorageId();

            //����Ҫ�г����
            if(appType.equals("L")||appType.equals("T")||appType.equals("X")||appType.equals("J")||appType.equals("S")){
            //�ȼ���������ֿ����Ч��
            List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+outStorageId+" and storage_state='0'");
            if(storageList!=null && storageList.size()==0){
                throw new DAOSystemException("---����ֿ���Ч :"+outStorageId);
            }else{
                storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+inStorageId+" and storage_state='0'");
                if(storageList!=null && storageList.size()==0){
                     throw new DAOSystemException("---���ֿ���Ч :"+outStorageId);
                }
            }
           voo= SrDAOFactory.getInstance().getRcStockDAO().findByPrimaryKey(vo.getSalesRescId(),outStorageId);
           if(voo==null){
               throw new DAOSystemException("--- rc_stock��û�иü�¼ ;Ӫ����ԴID:"+vo.getSalesRescId()+", �ֿ�ID:"+outStorageId);
           }
           long lon1 = Long.parseLong(voo.getStockAmount());//ԭ�ֿ������
           long lon2 = Long.parseLong(vo.getActAmount());//�����ٵ�����
           long res = lon1-lon2;//������������򡣡��������������������������������ǲ���Ӧ���׳��쳣Ŷ��
           if(res<0){
               throw new DAOSystemException("---����ֿ� "+voo.getStorageName()+" û���㹻��������Դ :" +voo.getSalesRescName() );
           }
           voo.setStockAmount(String.valueOf(res));
            //������ֿ����������
             boolean boo = false;
            try{System.out.println("storage_id ="+voo.getStorageId()+" and sales_resource_id ="+voo.getSalesRescId());
                boo =  SrDAOFactory.getInstance().getRcStockDAO().update("storage_id ="+voo.getStorageId()+" and sales_resource_id ="+voo.getSalesRescId(),voo);
            }catch(DAOSystemException dse){ dse.printStackTrace();
                throw new DAOSystemException("---����ֿ� "+voo.getStorageName()+" �ڸ�����Դ " +voo.getSalesRescName() +"  �������������쳣" );
            }
            if(boo==false){
               throw new DAOSystemException("---����ֿ� "+voo.getStorageName()+" �޷�������Դ " +voo.getSalesRescName()+" ������" );
           }

           voo= SrDAOFactory.getInstance().getRcStockDAO().findByPrimaryKey(vo.getSalesRescId(),inStorageId);
           if(voo==null){
               voo = new RcStockVO();
               voo.setSalesRescId(vo.getSalesRescId());
               voo.setStorageId(inStorageId);
               voo.setStockAmount("0");
               try{
               SrDAOFactory.getInstance().getRcStockDAO().insert(voo);
           }catch(DAOSystemException des){
               des.printStackTrace();
               throw new DAOSystemException("��� rc_stock ��¼���ֿ� "+voo.getStorageName()+" Ӫ����Դ " +voo.getSalesRescName() +" �����쳣");
               }
           }
           lon1 = Long.parseLong(voo.getStockAmount());//ԭ�ֿ������
           res = lon1+lon2;//��ԭ�ֿ����������
           voo.setStockAmount(String.valueOf(res));
           try{
               boo= SrDAOFactory.getInstance().getRcStockDAO().update("storage_id ="+voo.getStorageId()+" and sales_resource_id ="+voo.getSalesRescId(),voo);
           }catch(DAOSystemException dse){ dse.printStackTrace();
             throw new DAOSystemException("---���ֿ� "+voo.getStorageName()+" �ڸ�����Դ " +voo.getSalesRescName() +" �������������쳣" );
         }
         if(boo==false){
             throw new DAOSystemException("---���ֿ� "+voo.getStorageName()+" �޷�������Դ " +voo.getSalesRescName()+" ������");
         }

         //�ɹ���ֻ��Ҫ���
        }else if(appType.equals("C")){
             List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+inStorageId+" and storage_state='0'");
            if(storageList!=null && storageList.size()==0){
             throw new DAOSystemException("---���ֿ���Ч��"+inStorageId);
            }
             voo= SrDAOFactory.getInstance().getRcStockDAO().findByPrimaryKey(vo.getSalesRescId(),inStorageId);
              try{
                  if(voo==null){
                      voo = new RcStockVO();
                      voo.setSalesRescId(vo.getSalesRescId());
                      voo.setStorageId(vo.getInStorageId());
                      voo.setStockAmount(vo.getActAmount());

                    SrDAOFactory.getInstance().getRcStockDAO().insert(voo);

                }else{
                    SrDAOFactory.getInstance().getRcStockDAO().updateAmount(voo.getStorageId(),voo.getSalesRescId(),String.valueOf(Long.parseLong(voo.getStockAmount())+Long.parseLong(vo.getActAmount())));
             }
        }catch(DAOSystemException dse){
                dse.printStackTrace();
                throw new DAOSystemException("---���ֿ� "+voo.getStorageId()+" �� ��Դʵ�� "+ voo.getSalesRescId()+ "�� ��ʱ�����쳣");
            }

            //ֻ��Ҫ����
        }else if(appType.equals("B")||appType.equals("H")){
            List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+outStorageId+" and storage_state='0'");
            if(storageList!=null && storageList.size()==0){
             throw new DAOSystemException("---����ֿ���Ч��"+outStorageId);
            }

             voo= SrDAOFactory.getInstance().getRcStockDAO().findByPrimaryKey(vo.getSalesRescId(),outStorageId);
             if(voo==null){
                  throw new DAOSystemException(" ---rc_stock��û�иü�¼��Ӫ����ԴID:"+vo.getSalesRescId()+",�ֿ�ID:"+outStorageId);
             }else{
               long lon=  Long.parseLong(voo.getStockAmount())-Long.parseLong(vo.getActAmount());
            if(lon>=0){
                 try{
                     SrDAOFactory.getInstance().getRcStockDAO(). updateAmount(voo.getStorageId(), voo.getSalesRescId(),String.valueOf(lon));
                 }catch(DAOSystemException dse){
                     dse.printStackTrace();
                      throw new DAOSystemException("---����ֿ� "+voo.getStorageName()+" �޷�������Դ������ " +voo.getSalesRescName() );
                 }
           } else{
                throw new DAOSystemException("---����ֿ� "+voo.getStorageName()+" û���㹻��������Դ " +voo.getSalesRescName() );
           }
         }
        }
     }

        //�������ʽΪʵ���洢��дrc_entity��1������ֹ���� 2���ļ��ϴ�
        if(map.get("manageMode")!=null &&map.get("manageMode").equals("0")){
            int byFile=Integer.parseInt(String.valueOf(map.get("byFile")));
            Collection coll = new ArrayList();
            //ͨ������ֹ����
            if(byFile==0){
                long beginCode = Long.parseLong(String.valueOf(vo.getResBCode())) ;
                long endCode = Long.parseLong(String.valueOf(vo.getResECode())) ;
                for(long i=beginCode;i<=endCode;i++){
                    coll.add(String.valueOf(i));
                }
                
                if(appType.equals("K")){//����ֹ���뷽ʽ�����ն��������ı������Դ״̬����
                	String inStorageId = vo.getInStorageId();
                	List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+inStorageId+" and storage_state='0'");
                    if(storageList!=null && storageList.size()==0){
                     throw new DAOSystemException("---���ֿ���Ч��"+inStorageId);
                    }
                    String sql = "update rc_entity set curr_state='G',state='00A' where storage_id="+inStorageId
                    	+" and resource_instance_code >='"+beginCode+"' and resource_instance_code<='"+endCode+"'";
                    List list = new ArrayList();
                    try{
                        SrDAOFactory.getInstance().getSrDepositoryDAO(). update(sql,list);
                    }catch(DAOSystemException dse){
                        dse.printStackTrace();
                         throw new DAOSystemException("---������Դʵ�����Ա����쳣");
                    }
                }
              }
            //ͨ���ļ��ϴ�����ʵ����Ҫ�õ��ļ��ϴ��ɹ��ļ��ϣ�����ͨ����ѯrc_order_list��õ����ϣ������ϴ�ʱ���ɵ�order_id;
            if(byFile==1){
            }
            updateStockOut(coll,vo,map);
            //���ʱͨ����ֹ�����ϴ��Ĳ��������������û�����ɶ���
            if(byFile==0)
                return "";
            System.out.println(coll.size()+"--------------");
            //�������rc_order_list��Ӧ������,��ʵ���õ�ʵ���ID������ڶ����嵥�����ܲ��ҵ�ǰ�嵥�ļ�¼
            Iterator iter = coll.iterator();
            StringBuffer sb = new StringBuffer();
            while(iter.hasNext()){
                String next = (String) iter.next();
                 sb.append(next);
                if(iter.hasNext())
                    sb.append(",");
            }
             System.out.println(sb);
             if(sb==null || sb.length()<=0){
            	 return "";
             }
             sb=new StringBuffer("'"+sb.toString().replaceAll(",", "','")+"'");
            long lon = SrDAOFactory.getInstance().getRcOrderListDAO().deleteByCond(" order_id="+vo.getOrderId()+" and resource_instance_code in("+sb+")");
            if(lon==0){
             throw new DAOSystemException("---���ʵ�嶩���嵥����ʧ�ܣ�ʵ�嶩��ID:"+vo.getOrderId());
            }
        }
        return "";
    }
    /**
     * ѭ�������ж�rc_entity����storage_id�Ƿ��ӦoutstorageId��ֵ,����ǣ��򽫸�storage_id ����ΪinstorageId��ֵ;�����׳��쳣��������
     * @param orderId String
     * @param salesRescId String
     * @param co Collection
     * @param inStorageId String
     * @param outStorageId String
     * @param byFile int ,���Ա�ʾ�Ƿ���ͨ���ļ��ϴ��ķ�ʽ������0������ 1����
     * @throws DAOSystemException
     * @return String[]
     * �ж϶������ͣ�
     *L ���� 	i	m
     *T	�˿�		m	o
     *X	�·�		i	m
     *J	����		i	m
     *C	�ɹ�		i	n
     *B	����		n	o
     *H	�˻�		n	o
co:��ֹ��Դʵ�����ļ��ϡ�rcvo������ʵ�塢map:ʵ�����Ժ��жϱ�־����
     */
    public String[] updateStockOut( Collection co,RcOrderVO rcvo,HashMap map) throws DAOSystemException{

        String inStorageId = rcvo.getInStorageId();
        String outStorageId = rcvo.getOutStorageId();
        String appType = rcvo.getAppType();
        String orderId = rcvo.getOrderId();
        String salesRescId = rcvo.getSalesRescId();
        int byFile = Integer.parseInt(String.valueOf(map.get("byFile")));
        //�����ͨ���ļ��ϴ�����ͨ��rc_entity��resource_instance_id����rc_order_list�ĵ���Ӧ��rc_order��¼�����ж϶�����ĳ���ֿ��־�Ƿ���ʵ���Ĳֿ�һ�£��ٽ�ʵ���Ĳֿ����Ϊ����������Ĳֿ�
        //���ļ��ϴ�ʱ���Ѿ��ж��˲ֿ��ʵ�����Ч�ԣ��ڴ˾����������жϡ�����
        if(byFile==1){
            StringBuffer sb = new StringBuffer();
           HashMap maps = new HashMap();
           List attrsList = (List)map.get("attrList");
          List list=  SrDAOFactory.getInstance().getRcOrderListDAO().findByCond(" order_id="+rcvo.getOrderId());
           if(list!=null && list.size()>0){
             if(appType.equals("C")){//ֻ��Ҫ���ֿ⣬����ʵ��
             if(inStorageId!=null && !inStorageId.equals("")){
                maps.put("inStorageId",inStorageId);
             }

             }else if(appType.equals("B")||appType.equals("H")){//ֻ��Ҫ����ֿ�,ɾ��ʵ��
                 if(outStorageId!=null && !outStorageId.equals("")){
                     sb.append("delete from rc_entity where storage_id=? and  resource_instance_id=?");
                     maps.put("outStorageId",outStorageId);
                 }
             }else if(appType.equals("L")||appType.equals("X")||appType.equals("J")||appType.equals("T") ||appType.equals("S")){//��Ҫ�����ֿ⣬����ʵ��
                 if(outStorageId!=null && !outStorageId.equals("")&& inStorageId!=null && !inStorageId.equals("")){
                     sb.append("update rc_entity set storage_id=? where storage_id=? and  resource_instance_id=? ");
                    maps.put("inStorageId",inStorageId);
                    maps.put("outStorageId",outStorageId);
                 }
             }
             System.out.println("======================= "+maps);
              List list2 = new ArrayList();
             if(maps.get("inStorageId")!=null)
               list2.add(maps.get("inStorageId"));
             if(maps.get("outStorageId")!=null)
              list2.add(maps.get("outStorageId"));

             for(int i=0;i<list.size();i++){
                  String resourceInstanceId = ((RcOrderListVO)list.get(i)).getRescInstanceId();
                   String resourceInstanceCode = ((RcOrderListVO)list.get(i)).getRescInstanceCode();
                   co.add(resourceInstanceCode);
                   if(appType.equals("K")){
                       String sql = "update rc_entity set curr_state='G',state='00A' where storage_id="+inStorageId
                   			+" and resource_instance_id >='"+resourceInstanceId+"'";
                       SrDAOFactory.getInstance().getSrDepositoryDAO(). update(sql,new ArrayList());
                   }
                   else if(appType.equals("C")){
                     RcEntityVO voo = new RcEntityVO();
                     voo.setRescInstanceId(resourceInstanceId);//���ļ��ϴ�ʱ���Ծ�������ʵ��ID,����������
                     voo.setRescInstanceCode(resourceInstanceCode);
                         voo.setSalesRescId(rcvo.getSalesRescId());
                         voo.setRescKind(String.valueOf(map.get("manageMode")));
                         voo.setLanId(String.valueOf(map.get("lanId")));
                         voo.setOwnerId(inStorageId);
                         voo.setOwnerType("5");
                         voo.setStorageId(inStorageId);
                         voo.setRescState(ParamsConsConfig.RcEntityFreeState);
                         voo.setState("00A");
                         voo.setEffDate(String.valueOf(map.get("effDate")));
                         voo.setExpDate(String.valueOf(map.get("expDate")));
                         voo.setCreateDate(DateFormatUtils.getFormatedDateTime());
                         try{
                             SrDAOFactory.getInstance().getRcEntityDAO().insert(
                                 voo);
                         }catch(DAOSystemException dse){
                             dse.printStackTrace();
                             throw new DAOSystemException("--- ��ʵ������ʵ��ʧ�� ��ʵ����룺"+resourceInstanceCode);
                         }
                     if(attrsList!=null){
                       String attrId = "";
                       String attrValue="";
                       //��ÿ��ʵ�������ѭ��������
                       for(int j=0;j<attrsList.size();j++){
                           HashMap attMap =(HashMap) attrsList.get(j);
                           attrId = String.valueOf(attMap.get("attrId"));
                           attrValue = String.valueOf(attMap.get("attrValue"));
                           try{
                               SrDAOFactory.getInstance().getRcEntityDAO2().insertRcEnAttrInfo(voo.getRescInstanceId(),attrId, attrValue);
                           }catch(DAOSystemException dse){ dse.printStackTrace();
                                throw new DAOSystemException("---����Դʵ�����Ա���Ӽ�¼ ʧ�ܣ�ʵ��IDΪ:"+voo.getRescInstanceId()+" ,ʵ������ID��"+attrId+" " );
                           }
                       }
                   }
                   continue;
                 }

                 String sql = "update rc_entity set curr_state='G',state='00A' where storage_id="+inStorageId
             	+" and resource_instance_id >='"+resourceInstanceId+"'";
                 SrDAOFactory.getInstance().getSrDepositoryDAO(). update(sql,new ArrayList());
             list2.add(resourceInstanceId);
             int boo=0;
               try{
                   //���ʱɾ��ʵ�壬�ȵ�ɾ��ʵ������Լ�¼
                   if(appType.equals("B")||appType.equals("H")){
                       try{
                       SrDAOFactory.getInstance().getSrDepositoryDAO(). update("delete from rc_entity_attr where entity_id="+list2.get(list2.size()-1),new ArrayList());
                   }catch(DAOSystemException dse){
                       dse.printStackTrace();
                        throw new DAOSystemException("---������Դʵ�����Ա����쳣����Դʵ��ID��"+list2.get(list2.size()-1));
                   }
               }
                   if(!appType.equals("K")){
                       boo= SrDAOFactory.getInstance().getSrDepositoryDAO(). update(sb.toString(), list2);                	   
                   }
               }catch(DAOSystemException dse){
                   dse.printStackTrace();
                   throw new DAOSystemException("---������Դʵ�� �����쳣 ���ֿ� ID:"+outStorageId+",��Դʵ��ID:"+resourceInstanceId+"  ");
               }
               if(boo==0&&!appType.equals("K")){
                   throw new DAOSystemException("---������Դʵ��� ʧ�� �ֿ�ID: "+outStorageId+",��Դʵ��ID :"+resourceInstanceId+"  ");
               }
               list2.remove(list2.size()-1);
         }
        }

      ////�����ͨ����ֹ����ķ�ʽ,���Ҷ�������Ϊ�ɹ�,�����ɶ�Ӧ����Դʵ����ʵ������
        }else if(appType.equals("C")){
            //���ֿ���Ч��
            long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond( " storage_id="+rcvo.getInStorageId()+" and storage_state='0'");
            if(lon==0){
                throw new DAOSystemException ("---���ֿⲻ���� :"+rcvo.getInStorageId());
            }
            Iterator iter = co.iterator();
            List attrsList = (List)map.get("attrList");
            RcEntityVO voo = new RcEntityVO();
            voo.setSalesRescId(rcvo.getSalesRescId());
            voo.setRescKind(String.valueOf(map.get("manageMode")));
            voo.setLanId(String.valueOf(map.get("lanId")));
            voo.setOwnerId(rcvo.getInStorageId());
            voo.setOwnerType("5");
            voo.setStorageId(rcvo.getInStorageId());
            voo.setRescState(ParamsConsConfig.RcEntityFreeState);
            voo.setState("00A");
            voo.setEffDate(String.valueOf(map.get("effDate")));
            voo.setExpDate(String.valueOf(map.get("expDate")));
            voo.setCreateDate(DateFormatUtils.getFormatedDateTime());

            while (iter.hasNext()) {
                String tmp = (String) iter.next();
                voo.setRescInstanceId(getRcEntityId());//ÿ��ѭ������һ����Դʵ������
                 voo.setRescInstanceCode(tmp);//�õ���ͬ��ʵ�б���
                 List li = SrDAOFactory.getInstance().getRcEntityDAO().findByCond(" resource_instance_code='"+tmp+"'");
                  if(li!=null && li.size()>0){
                      throw new DAOSystemException ("---��Դ�����Ѿ�������"+tmp);
                  }
                  try{System.out.println(voo.getRescInstanceId()+" "+voo.getRescInstanceCode()+ " "+voo.getSalesRescId());
                          SrDAOFactory.getInstance().getRcEntityDAO().insert(voo);
                  }catch(DAOSystemException ex){ ex.printStackTrace();
                          throw new DAOSystemException ("---����Դʵ�� ����Ӽ�¼ʧ��:��Դ���룺"+tmp);
                   }
                      //��ÿ����Դʵ�壬���ܴ�����������ԣ�
                      if(attrsList!=null){
                          String attrId = "";
                          String attrValue="";
                          //��ÿ��ʵ�������ѭ��������{attrId=30004, attrValue=003, attrName=������1}
                          for(int i=0;i<attrsList.size();i++){
                              HashMap attMap =(HashMap) attrsList.get(i);
                              attrId = String.valueOf(attMap.get("attrId"));
                              attrValue = String.valueOf(attMap.get("attrValue"));
                              try{
                                  SrDAOFactory.getInstance().getRcEntityDAO2().insertRcEnAttrInfo(voo.getRescInstanceId(),attrId, attrValue);
                              }catch(DAOSystemException dse){ dse.printStackTrace();
                                   throw new DAOSystemException("---��rc_entity_attr��Ӽ�¼ ��ʵ��IDΪ:"+voo.getRescInstanceId()+" ,ʵ������ID��"+attrId+" ʧ��" );
                              }
                          }
                      }
            }
        //������ǲɹ�������Ҫ������Դʵ������ʵ���Ѿ����ڣ��ڲɹ�ʱ���ɵġ�
        }else if(!appType.equals("K")){
            Iterator iter = co.iterator();
            String str[] = null;
             StringBuffer sql = new StringBuffer();
             boolean bool = false;
             //����Ǳ��ϣ��˻�������Ҫɾ��ʵ�壻���������á��˿⡢�·�����������Ҫ��������ֿ�
              if(appType.equals("B")||appType.equals("H")){
                sql.append("delete rc_entity where RESOURCE_INSTANCE_ID=?");
                 bool = false;
             }else if(appType.equals("L")||appType.equals("X")||appType.equals("J")||appType.equals("T")||appType.equals("S")){
                 sql.append("update rc_entity set storage_id=? where RESOURCE_INSTANCE_ID= ?");
                  bool = true;
              }
              //���ֿ�״̬
              long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond( " storage_id="+rcvo.getOutStorageId()+" and storage_state='0'");
            if(lon==0){
                throw new DAOSystemException ("---����ֿⲻ���� :"+rcvo.getInStorageId());
            }
            if(bool){
                lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond( " storage_id="+rcvo.getInStorageId()+" and storage_state='0'");
                if(lon==0){
                    throw new DAOSystemException ("---���ֿⲻ���� :"+rcvo.getInStorageId());
                }
            }
            while (iter.hasNext()) {
                   String tmp = (String) iter.next();
                   List entityLi = new ArrayList();
                   //��ѯ��Դʵ���Ƿ����,(.salesRescId, tmp,outStorageId))
                   if ( (entityLi = SrDAOFactory.getInstance().getRcEntityDAO().findBySql("select * from rc_entity where sales_resource_id=? and resource_instance_code=? and storage_id=? and state='00A'",new String[]{salesRescId,tmp,outStorageId}) )!= null && entityLi.size()==1)  {
                      ArrayList entityList = new ArrayList();
                      String RescInstanceId = ((RcEntityVO)entityLi.get(0)).getRescInstanceId();
                       int result = 0;
                       System.out.println(inStorageId+"-------------++++"+RescInstanceId+"++++============= "+sql);
                       if (true==bool){
                             entityList.add(inStorageId);
                         }
                         entityList.add(RescInstanceId);

                           //�����Ҫɾ��ʵ�壬��Ҫ��ɾ��ʵ������
                           if(!bool)
                           {
                               try{
                                   SrDAOFactory.getInstance().getSrDepositoryDAO(). update("delete from rc_entity_attr where entity_id="+RescInstanceId,new ArrayList());
                               }catch(DAOSystemException dse){
                                   dse.printStackTrace();
                                   throw new DAOSystemException("---������Դʵ�����Ա����쳣����Դʵ��ID��"+RescInstanceId);
                               }

                           }
                      try{
                           // ������ڣ��������Դʵ��Ĳֿ��־Ϊ���Ĳֿ�
                          result  = SrDAOFactory.getInstance().getSrDepositoryDAO().update(sql.toString(),entityList);
                       }catch(DAOSystemException dse){
                           dse.printStackTrace();
                           throw new DAOSystemException("---����ʵ����¼�����쳣�� ʵ����룺"+tmp+" ");
                       }
                      if(0==result){
                          throw new DAOSystemException("---�޷�����ʵ����¼��ʵ����룺 " +tmp);
                      }
                   }else{
                       throw new DAOSystemException("---��Դʵ�岻���� ���߲����ã���Դ����ID:"+salesRescId+" , ʵ����� :" +tmp+" , �ֿ�ID :"  +outStorageId);
                   }
               }
        }
      return  new String[]{};
    }

    /**
     * get the next pk id value of the table :rc_order;
     * @return String
     */
    private String getRcOrderId(){
        SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
        SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();

        String rcOrderId = sequenceManageDAO.getNextSequence("SEQ_RC_ORDER_ID");
        return rcOrderId;
    }
    
    /**
     * get the next pk id value of the table :rc_order;
     * @return String
     */
    private String getRcApplicationId(){
        SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
        SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();
		String appId = sequenceManageDAO.getNextSequence("rc_application",
		"app_id");
        return appId;
    }
    
    /**
     * get the next pk id value of the table :rc_entity;
     * @return String
     */
    private String getRcEntityId(){
    SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
    SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();

    String ID = sequenceManageDAO.getNextSequence("RC_ENTITY", "RESOURCE_INSTANCE_ID");
    return ID;
    }

    /**
     * upLoadStockOut
     *
     * @param map Map�� ��Դ����ID
     * @param fileList Collection��  ��Դʵ�����-->>>��Դʵ��ID
     * @return String[]
     */
    public List upLoadStockOut(Map map, Collection fileList) {
        if(map==null || fileList==null ) { throw new DAOSystemException("---�޷��ӽ���õ���Ч������");}
        String outStorageId =((String) map.get("outStorageId")).trim();
        String inStorageId = ((String)map.get("inStorageId")).trim();
        System.out.println(outStorageId +"***********"+inStorageId);
        //���ֿ��Ƿ�Ϊ��Ч������
        if(outStorageId!=null&& !outStorageId.equals("")){
            long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond(" storage_id="+outStorageId+" and storage_state='0'");
            if(lon==0){
                throw new DAOSystemException("--- �������ֿ��Ƿ���ڻ�����Ч");
            }
        }
        if(inStorageId!=null &&!inStorageId.equals("")){
            long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond(" storage_id="+inStorageId+" and storage_state='0'");
           if(lon==0){
               throw new DAOSystemException("---�������ֿ��Ƿ���ڻ�����Ч");
           }

        }
        String salesRescId = (String) map.get("salesRescId");
        String orderId = (String) map.get("orderId");
        String appType = String.valueOf(map.get("appType"));
        int resultCount = 0;
        StringBuffer buf=new StringBuffer();
        List returnList = new ArrayList();
        //if(fileList.size()==0)throw new DAOSystemException("---�ϴ��ļ����κ���Ч����");
        if(fileList.size()>0){
        //ͨ����Դ����ID����Դʵ����룬��Դʵ��ID�Ͷ���ID�������嵥ʵ��
        if(salesRescId!=null && !salesRescId.equals("")){
        	if(orderId==null||orderId.equals("")){//���ϵ������Ϊ�յġ�
        		orderId = getRcOrderId();
        		//��Ҫ�����ɶ���ʵ��������
        		RcOrderVO ordervo = new RcOrderVO();
        		ordervo.setOrderId(orderId);
        		try{
        			SrDAOFactory.getInstance().getRcOrderDAO().insert(ordervo);
        		}catch(DAOSystemException dse){
        			dse.printStackTrace();
        			throw new DAOSystemException("--- ���Ӷ���ʵ�������쳣������ID��"+orderId);
        		}
        	}
            Iterator it = fileList.iterator();
            while(it.hasNext()){

                String rescInstanceCode = String.valueOf(it.next());
               try{
                   //��Ӧ�ɹ���˵�������⡣���ú������Ķ��������෴������
                   if(appType.equals("C")){
                      List list0 = SrDAOFactory.getInstance().getRcEntityDAO(). findByCond(" state='00A' and sales_resource_id="+ salesRescId+" and resource_instance_code='" + rescInstanceCode+"' and storage_id="+inStorageId);
                      if(list0!=null && list0.size()==0){

                          RcOrderListVO vo = new RcOrderListVO();
                          vo.setOrderId(orderId);
                          vo.setRescInstanceCode(rescInstanceCode);

                          vo.setRescInstanceId(this.getRcEntityId());
                          vo.setSalesRescId(salesRescId);
                          SrDAOFactory.getInstance().getRcOrderListDAO().insert( vo);
                          resultCount++;
                          returnList.add(rescInstanceCode);
                      }else{
                          buf.append(rescInstanceCode+",");
                      }
                      continue;
                   }

                   List list = null;
                   if(!appType.equals("K")){
                	   list=SrDAOFactory.getInstance().getRcEntityDAO(). findByCond(" state='00A' and sales_resource_id="+ salesRescId+" and resource_instance_code='" + rescInstanceCode+"' and storage_id="+outStorageId);
                   }
                   List list2 = null;
                  if(appType.equals("L")||appType.equals("X")||appType.equals("J")||appType.equals("T")||appType.equals("S")||appType.equals("K"))
                      list2 = SrDAOFactory.getInstance().getRcEntityDAO(). findByCond(" state='00A' and sales_resource_id="+ salesRescId+" and resource_instance_code='" + rescInstanceCode+"' and storage_id="+inStorageId);
                  	if(appType.equals("K")){
                  		if (list2==null || list2.size()>0) {

                            RcOrderListVO vo = new RcOrderListVO();
                            vo.setOrderId(orderId);
                            vo.setRescInstanceCode(rescInstanceCode);

                            vo.setRescInstanceId(String.valueOf( ( ( RcEntityVO) list2.get(0)).getRescInstanceId()));
                            vo.setSalesRescId(salesRescId);
                            SrDAOFactory.getInstance().getRcOrderListDAO().insert(vo);
                            resultCount++;
                            returnList.add(rescInstanceCode);
                        }else{
                         buf.append(rescInstanceCode+",");

                        }
                  	}else{
                        if (list != null && list.size()>0 && (list2==null || list2.size()==0)) {

                            RcOrderListVO vo = new RcOrderListVO();
                            vo.setOrderId(orderId);
                            vo.setRescInstanceCode(rescInstanceCode);

                            vo.setRescInstanceId(String.valueOf( ( ( RcEntityVO) list.get(0)).getRescInstanceId()));
                            vo.setSalesRescId(salesRescId);
                            SrDAOFactory.getInstance().getRcOrderListDAO().insert(vo);
                            resultCount++;
                            returnList.add(rescInstanceCode);
                        }else{
                         buf.append(rescInstanceCode+",");

                    }
                  	}

               }catch(DAOSystemException dse){
                   dse.printStackTrace();
                   buf.append(rescInstanceCode+",");
                   continue;
               }
            }
        }
    }
    if(resultCount==0 &&!appType.equals("K")){
    	return null;
//    	throw new DAOSystemException("---�ɹ��ϴ�����Ч��������Ϊ0");
    }
    if(resultCount>0){
        List list = new ArrayList();
        list.add(orderId);// ����ID
        list.add(new Integer(resultCount));//ִ�гɹ�������
        list.add(returnList);//ִ�гɹ�����Դʵ������
        list.add(buf.toString());//ִ��ʧ�ܵ���Դʵ������

        return list;
    }
    return null;
    }

    /**
     * deleteOrder_HENAN
     *
     * @param orderId String
     * @return String
     */
    public String deleteOrder_HENAN(String orderId) {
        if(orderId==null || orderId.equals(""))
            return "-1";
       DAO dao =  SrDAOFactory.getInstance().getRcOrderListDAO();
        dao.deleteByCond(" order_id="+orderId);
        dao =  SrDAOFactory.getInstance().getRcOrderDAO();
        dao.deleteByCond(" order_id="+orderId);
        return orderId;
    }
}
