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
     *如果MAP 为NULL 表示可以查询全部的仓库，否则，只能通过适当的权限来查看仓库信息；
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
     *判断ORDERID是否为空，如果是则按照序列生成。
     *1.写rc_order表；
     * 2。如果管理方式为存量存储则写rc_stock表；
     * 3。如果管理方式为实例存储则写rc_entity表；
     * 4。如果定单类型为采购，则要写rc_entity表和rc_entity_attr表；
     * @param vo RcOrderVO
     * @return String
     */
    public String inOutStock2(RcOrderVO vo,HashMap map) throws DAOSystemException {
        String rcOrderId = vo.getOrderId();

        String appType = vo.getAppType();
        //如果时上传文件,或者时存量存储方式，将该两个字段清楚掉
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
        vo.setTacheId("5");//设置环节为结束
        vo.setStateId("n");//设置状态为正常
        
        vo.setAppStorageId(vo.getInStorageId());
        
        //写RC_ORDER表
        if(rcOrderId.equals("")){
            rcOrderId = getRcOrderId();
            vo.setOrderId(rcOrderId);
            //如果是新生成的主键，插入
          try{
              SrDAOFactory.getInstance().getRcOrderDAO().insert(vo);
           }catch(DAOSystemException dse){
             dse.printStackTrace();
             throw new DAOSystemException("---写rc_order表发生异常,订单号:"+vo.getOrderId());
           }

        }else{
            //否则，更新
            try{
              SrDAOFactory.getInstance().getRcOrderDAO().update("order_id="+rcOrderId,vo);
           }catch(DAOSystemException dse){
             dse.printStackTrace();
             throw new DAOSystemException("---写rc_order表发生异常,订单号:"+vo.getOrderId());
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

        //如果管理方式为存量存储则写rc_stock表.没有起止编码值,不写rc_entity表，不写rc_order_list表
        if(map.get("manageMode")!=null &&map.get("manageMode").equals("1")){
            RcStockVO voo = new RcStockVO();
            String outStorageId =vo.getOutStorageId();
            String inStorageId = vo.getInStorageId();

            //必须要有出入库
            if(appType.equals("L")||appType.equals("T")||appType.equals("X")||appType.equals("J")||appType.equals("S")){
            //先检查出库和入库仓库的有效性
            List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+outStorageId+" and storage_state='0'");
            if(storageList!=null && storageList.size()==0){
                throw new DAOSystemException("---出库仓库无效 :"+outStorageId);
            }else{
                storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+inStorageId+" and storage_state='0'");
                if(storageList!=null && storageList.size()==0){
                     throw new DAOSystemException("---入库仓库无效 :"+outStorageId);
                }
            }
           voo= SrDAOFactory.getInstance().getRcStockDAO().findByPrimaryKey(vo.getSalesRescId(),outStorageId);
           if(voo==null){
               throw new DAOSystemException("--- rc_stock表没有该记录 ;营销资源ID:"+vo.getSalesRescId()+", 仓库ID:"+outStorageId);
           }
           long lon1 = Long.parseLong(voo.getStockAmount());//原仓库的数量
           long lon2 = Long.parseLong(vo.getActAmount());//被减少的数量
           long res = lon1-lon2;//如果不够减，则。。。。。。。。。。。。。。。。，是不是应该抛出异常哦？
           if(res<0){
               throw new DAOSystemException("---出库仓库 "+voo.getStorageName()+" 没有足够数量的资源 :" +voo.getSalesRescName() );
           }
           voo.setStockAmount(String.valueOf(res));
            //将出库仓库的数量减少
             boolean boo = false;
            try{System.out.println("storage_id ="+voo.getStorageId()+" and sales_resource_id ="+voo.getSalesRescId());
                boo =  SrDAOFactory.getInstance().getRcStockDAO().update("storage_id ="+voo.getStorageId()+" and sales_resource_id ="+voo.getSalesRescId(),voo);
            }catch(DAOSystemException dse){ dse.printStackTrace();
                throw new DAOSystemException("---出库仓库 "+voo.getStorageName()+" 在更新资源 " +voo.getSalesRescName() +"  的数量，发生异常" );
            }
            if(boo==false){
               throw new DAOSystemException("---出库仓库 "+voo.getStorageName()+" 无法更新资源 " +voo.getSalesRescName()+" 的数量" );
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
               throw new DAOSystemException("添加 rc_stock 记录：仓库 "+voo.getStorageName()+" 营销资源 " +voo.getSalesRescName() +" 发生异常");
               }
           }
           lon1 = Long.parseLong(voo.getStockAmount());//原仓库的数量
           res = lon1+lon2;//将原仓库的数量增加
           voo.setStockAmount(String.valueOf(res));
           try{
               boo= SrDAOFactory.getInstance().getRcStockDAO().update("storage_id ="+voo.getStorageId()+" and sales_resource_id ="+voo.getSalesRescId(),voo);
           }catch(DAOSystemException dse){ dse.printStackTrace();
             throw new DAOSystemException("---入库仓库 "+voo.getStorageName()+" 在更新资源 " +voo.getSalesRescName() +" 的数量，发生异常" );
         }
         if(boo==false){
             throw new DAOSystemException("---入库仓库 "+voo.getStorageName()+" 无法更新资源 " +voo.getSalesRescName()+" 的数量");
         }

         //采购，只需要入库
        }else if(appType.equals("C")){
             List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+inStorageId+" and storage_state='0'");
            if(storageList!=null && storageList.size()==0){
             throw new DAOSystemException("---入库仓库无效："+inStorageId);
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
                throw new DAOSystemException("---入库仓库 "+voo.getStorageId()+" 在 资源实例 "+ voo.getSalesRescId()+ "入 库时发生异常");
            }

            //只需要出库
        }else if(appType.equals("B")||appType.equals("H")){
            List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+outStorageId+" and storage_state='0'");
            if(storageList!=null && storageList.size()==0){
             throw new DAOSystemException("---出库仓库无效："+outStorageId);
            }

             voo= SrDAOFactory.getInstance().getRcStockDAO().findByPrimaryKey(vo.getSalesRescId(),outStorageId);
             if(voo==null){
                  throw new DAOSystemException(" ---rc_stock表没有该记录；营销资源ID:"+vo.getSalesRescId()+",仓库ID:"+outStorageId);
             }else{
               long lon=  Long.parseLong(voo.getStockAmount())-Long.parseLong(vo.getActAmount());
            if(lon>=0){
                 try{
                     SrDAOFactory.getInstance().getRcStockDAO(). updateAmount(voo.getStorageId(), voo.getSalesRescId(),String.valueOf(lon));
                 }catch(DAOSystemException dse){
                     dse.printStackTrace();
                      throw new DAOSystemException("---出库仓库 "+voo.getStorageName()+" 无法更新资源的数量 " +voo.getSalesRescName() );
                 }
           } else{
                throw new DAOSystemException("---出库仓库 "+voo.getStorageName()+" 没有足够数量的资源 " +voo.getSalesRescName() );
           }
         }
        }
     }

        //如果管理方式为实例存储则写rc_entity表，1：有起止编码 2：文件上传
        if(map.get("manageMode")!=null &&map.get("manageMode").equals("0")){
            int byFile=Integer.parseInt(String.valueOf(map.get("byFile")));
            Collection coll = new ArrayList();
            //通过有起止编码
            if(byFile==0){
                long beginCode = Long.parseLong(String.valueOf(vo.getResBCode())) ;
                long endCode = Long.parseLong(String.valueOf(vo.getResECode())) ;
                for(long i=beginCode;i<=endCode;i++){
                    coll.add(String.valueOf(i));
                }
                
                if(appType.equals("K")){//按起止编码方式，回收订单，更改编码的资源状态即可
                	String inStorageId = vo.getInStorageId();
                	List storageList = SrDAOFactory.getInstance().getRcStorageDAO().findByCond(" storage_id="+inStorageId+" and storage_state='0'");
                    if(storageList!=null && storageList.size()==0){
                     throw new DAOSystemException("---入库仓库无效："+inStorageId);
                    }
                    String sql = "update rc_entity set curr_state='G',state='00A' where storage_id="+inStorageId
                    	+" and resource_instance_code >='"+beginCode+"' and resource_instance_code<='"+endCode+"'";
                    List list = new ArrayList();
                    try{
                        SrDAOFactory.getInstance().getSrDepositoryDAO(). update(sql,list);
                    }catch(DAOSystemException dse){
                        dse.printStackTrace();
                         throw new DAOSystemException("---更新资源实体属性表发生异常");
                    }
                }
              }
            //通过文件上传，其实不许要得到文件上传成功的集合，可以通过查询rc_order_list表得到集合，利用上传时生成的order_id;
            if(byFile==1){
            }
            updateStockOut(coll,vo,map);
            //如果时通过起止编码上传的不需清楚订单，它没有生成订单
            if(byFile==0)
                return "";
            System.out.println(coll.size()+"--------------");
            //否则，清楚rc_order_list相应的数据,从实体表得到实体的ID，如果在定单清单表里能查找当前清单的记录
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
             throw new DAOSystemException("---清除实体订单清单报表失败，实体订单ID:"+vo.getOrderId());
            }
        }
        return "";
    }
    /**
     * 循环，先判断rc_entity，的storage_id是否对应outstorageId的值,如果是，则将该storage_id 更新为instorageId的值;否则，抛出异常。。。。
     * @param orderId String
     * @param salesRescId String
     * @param co Collection
     * @param inStorageId String
     * @param outStorageId String
     * @param byFile int ,用以表示是否是通过文件上传的方式。。。0：不是 1：是
     * @throws DAOSystemException
     * @return String[]
     * 判断定单类型：
     *L 领用 	i	m
     *T	退库		m	o
     *X	下发		i	m
     *J	调拨		i	m
     *C	采购		i	n
     *B	报废		n	o
     *H	退货		n	o
co:起止资源实体编码的集合、rcvo：订单实体、map:实体属性和判断标志集合
     */
    public String[] updateStockOut( Collection co,RcOrderVO rcvo,HashMap map) throws DAOSystemException{

        String inStorageId = rcvo.getInStorageId();
        String outStorageId = rcvo.getOutStorageId();
        String appType = rcvo.getAppType();
        String orderId = rcvo.getOrderId();
        String salesRescId = rcvo.getSalesRescId();
        int byFile = Integer.parseInt(String.valueOf(map.get("byFile")));
        //如果是通过文件上传，则通过rc_entity的resource_instance_id关联rc_order_list的到对应的rc_order记录，先判断定单表的出库仓库标志是否与实体表的仓库一致，再将实体表的仓库更新为定单表的入库的仓库
        //在文件上传时，已经判断了仓库和实体的有效性，在此就无需再做判断。。。
        if(byFile==1){
            StringBuffer sb = new StringBuffer();
           HashMap maps = new HashMap();
           List attrsList = (List)map.get("attrList");
          List list=  SrDAOFactory.getInstance().getRcOrderListDAO().findByCond(" order_id="+rcvo.getOrderId());
           if(list!=null && list.size()>0){
             if(appType.equals("C")){//只需要入库仓库，生产实体
             if(inStorageId!=null && !inStorageId.equals("")){
                maps.put("inStorageId",inStorageId);
             }

             }else if(appType.equals("B")||appType.equals("H")){//只需要出库仓库,删除实体
                 if(outStorageId!=null && !outStorageId.equals("")){
                     sb.append("delete from rc_entity where storage_id=? and  resource_instance_id=?");
                     maps.put("outStorageId",outStorageId);
                 }
             }else if(appType.equals("L")||appType.equals("X")||appType.equals("J")||appType.equals("T") ||appType.equals("S")){//需要出入库仓库，更新实体
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
                     voo.setRescInstanceId(resourceInstanceId);//在文件上传时就以经生产了实体ID,无需在生成
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
                             throw new DAOSystemException("--- 向实体表添加实体失败 ！实体编码："+resourceInstanceCode);
                         }
                     if(attrsList!=null){
                       String attrId = "";
                       String attrValue="";
                       //对每个实体的属性循环。。。
                       for(int j=0;j<attrsList.size();j++){
                           HashMap attMap =(HashMap) attrsList.get(j);
                           attrId = String.valueOf(attMap.get("attrId"));
                           attrValue = String.valueOf(attMap.get("attrValue"));
                           try{
                               SrDAOFactory.getInstance().getRcEntityDAO2().insertRcEnAttrInfo(voo.getRescInstanceId(),attrId, attrValue);
                           }catch(DAOSystemException dse){ dse.printStackTrace();
                                throw new DAOSystemException("---向资源实体属性表添加记录 失败：实体ID为:"+voo.getRescInstanceId()+" ,实体属性ID："+attrId+" " );
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
                   //如果时删除实体，先得删除实体的属性记录
                   if(appType.equals("B")||appType.equals("H")){
                       try{
                       SrDAOFactory.getInstance().getSrDepositoryDAO(). update("delete from rc_entity_attr where entity_id="+list2.get(list2.size()-1),new ArrayList());
                   }catch(DAOSystemException dse){
                       dse.printStackTrace();
                        throw new DAOSystemException("---更新资源实体属性表发生异常，资源实体ID："+list2.get(list2.size()-1));
                   }
               }
                   if(!appType.equals("K")){
                       boo= SrDAOFactory.getInstance().getSrDepositoryDAO(). update(sb.toString(), list2);                	   
                   }
               }catch(DAOSystemException dse){
                   dse.printStackTrace();
                   throw new DAOSystemException("---更新资源实体 表发生异常 ：仓库 ID:"+outStorageId+",资源实体ID:"+resourceInstanceId+"  ");
               }
               if(boo==0&&!appType.equals("K")){
                   throw new DAOSystemException("---更新资源实体表 失败 仓库ID: "+outStorageId+",资源实体ID :"+resourceInstanceId+"  ");
               }
               list2.remove(list2.size()-1);
         }
        }

      ////如果是通过起止编码的方式,并且定单类型为采购,则生成对应的资源实例和实例属性
        }else if(appType.equals("C")){
            //检查仓库有效性
            long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond( " storage_id="+rcvo.getInStorageId()+" and storage_state='0'");
            if(lon==0){
                throw new DAOSystemException ("---入库仓库不可用 :"+rcvo.getInStorageId());
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
                voo.setRescInstanceId(getRcEntityId());//每次循环生成一个资源实体主键
                 voo.setRescInstanceCode(tmp);//得到不同的实列编码
                 List li = SrDAOFactory.getInstance().getRcEntityDAO().findByCond(" resource_instance_code='"+tmp+"'");
                  if(li!=null && li.size()>0){
                      throw new DAOSystemException ("---资源编码已经存在了"+tmp);
                  }
                  try{System.out.println(voo.getRescInstanceId()+" "+voo.getRescInstanceCode()+ " "+voo.getSalesRescId());
                          SrDAOFactory.getInstance().getRcEntityDAO().insert(voo);
                  }catch(DAOSystemException ex){ ex.printStackTrace();
                          throw new DAOSystemException ("---向资源实体 表添加记录失败:资源编码："+tmp);
                   }
                      //对每个资源实体，可能存在任意个属性；
                      if(attrsList!=null){
                          String attrId = "";
                          String attrValue="";
                          //对每个实体的属性循环。。。{attrId=30004, attrValue=003, attrName=下拉框1}
                          for(int i=0;i<attrsList.size();i++){
                              HashMap attMap =(HashMap) attrsList.get(i);
                              attrId = String.valueOf(attMap.get("attrId"));
                              attrValue = String.valueOf(attMap.get("attrValue"));
                              try{
                                  SrDAOFactory.getInstance().getRcEntityDAO2().insertRcEnAttrInfo(voo.getRescInstanceId(),attrId, attrValue);
                              }catch(DAOSystemException dse){ dse.printStackTrace();
                                   throw new DAOSystemException("---向rc_entity_attr添加记录 ：实体ID为:"+voo.getRescInstanceId()+" ,实体属性ID："+attrId+" 失败" );
                              }
                          }
                      }
            }
        //如果不是采购，则不许要生成资源实例，该实例已经存在，在采购时生成的。
        }else if(!appType.equals("K")){
            Iterator iter = co.iterator();
            String str[] = null;
             StringBuffer sql = new StringBuffer();
             boolean bool = false;
             //如果是报废，退货；则需要删除实体；否则是领用、退库、下发、调拨则需要出库和入库仓库
              if(appType.equals("B")||appType.equals("H")){
                sql.append("delete rc_entity where RESOURCE_INSTANCE_ID=?");
                 bool = false;
             }else if(appType.equals("L")||appType.equals("X")||appType.equals("J")||appType.equals("T")||appType.equals("S")){
                 sql.append("update rc_entity set storage_id=? where RESOURCE_INSTANCE_ID= ?");
                  bool = true;
              }
              //检查仓库状态
              long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond( " storage_id="+rcvo.getOutStorageId()+" and storage_state='0'");
            if(lon==0){
                throw new DAOSystemException ("---出库仓库不可用 :"+rcvo.getInStorageId());
            }
            if(bool){
                lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond( " storage_id="+rcvo.getInStorageId()+" and storage_state='0'");
                if(lon==0){
                    throw new DAOSystemException ("---入库仓库不可用 :"+rcvo.getInStorageId());
                }
            }
            while (iter.hasNext()) {
                   String tmp = (String) iter.next();
                   List entityLi = new ArrayList();
                   //查询资源实体是否存在,(.salesRescId, tmp,outStorageId))
                   if ( (entityLi = SrDAOFactory.getInstance().getRcEntityDAO().findBySql("select * from rc_entity where sales_resource_id=? and resource_instance_code=? and storage_id=? and state='00A'",new String[]{salesRescId,tmp,outStorageId}) )!= null && entityLi.size()==1)  {
                      ArrayList entityList = new ArrayList();
                      String RescInstanceId = ((RcEntityVO)entityLi.get(0)).getRescInstanceId();
                       int result = 0;
                       System.out.println(inStorageId+"-------------++++"+RescInstanceId+"++++============= "+sql);
                       if (true==bool){
                             entityList.add(inStorageId);
                         }
                         entityList.add(RescInstanceId);

                           //如果需要删除实体，需要先删除实体属性
                           if(!bool)
                           {
                               try{
                                   SrDAOFactory.getInstance().getSrDepositoryDAO(). update("delete from rc_entity_attr where entity_id="+RescInstanceId,new ArrayList());
                               }catch(DAOSystemException dse){
                                   dse.printStackTrace();
                                   throw new DAOSystemException("---更新资源实体属性表发生异常，资源实体ID："+RescInstanceId);
                               }

                           }
                      try{
                           // 如果存在，则更新资源实体的仓库标志为入库的仓库
                          result  = SrDAOFactory.getInstance().getSrDepositoryDAO().update(sql.toString(),entityList);
                       }catch(DAOSystemException dse){
                           dse.printStackTrace();
                           throw new DAOSystemException("---更新实体表记录发生异常： 实体编码："+tmp+" ");
                       }
                      if(0==result){
                          throw new DAOSystemException("---无法更新实体表记录：实体编码： " +tmp);
                      }
                   }else{
                       throw new DAOSystemException("---资源实体不存在 或者不可用；资源种类ID:"+salesRescId+" , 实体编码 :" +tmp+" , 仓库ID :"  +outStorageId);
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
     * @param map Map： 资源种类ID
     * @param fileList Collection：  资源实体代码-->>>资源实体ID
     * @return String[]
     */
    public List upLoadStockOut(Map map, Collection fileList) {
        if(map==null || fileList==null ) { throw new DAOSystemException("---无法从界面得到有效的数据");}
        String outStorageId =((String) map.get("outStorageId")).trim();
        String inStorageId = ((String)map.get("inStorageId")).trim();
        System.out.println(outStorageId +"***********"+inStorageId);
        //检查仓库是否为有效。。。
        if(outStorageId!=null&& !outStorageId.equals("")){
            long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond(" storage_id="+outStorageId+" and storage_state='0'");
            if(lon==0){
                throw new DAOSystemException("--- 请检查出库仓库是否存在或者有效");
            }
        }
        if(inStorageId!=null &&!inStorageId.equals("")){
            long lon = SrDAOFactory.getInstance().getRcStorageDAO().countByCond(" storage_id="+inStorageId+" and storage_state='0'");
           if(lon==0){
               throw new DAOSystemException("---请检查入库仓库是否存在或者有效");
           }

        }
        String salesRescId = (String) map.get("salesRescId");
        String orderId = (String) map.get("orderId");
        String appType = String.valueOf(map.get("appType"));
        int resultCount = 0;
        StringBuffer buf=new StringBuffer();
        List returnList = new ArrayList();
        //if(fileList.size()==0)throw new DAOSystemException("---上传文件无任何有效数据");
        if(fileList.size()>0){
        //通过资源种类ID，资源实体代码，资源实体ID和定单ID，生成清单实例
        if(salesRescId!=null && !salesRescId.equals("")){
        	if(orderId==null||orderId.equals("")){//河南的情况是为空的。
        		orderId = getRcOrderId();
        		//需要先生成订单实例。。。
        		RcOrderVO ordervo = new RcOrderVO();
        		ordervo.setOrderId(orderId);
        		try{
        			SrDAOFactory.getInstance().getRcOrderDAO().insert(ordervo);
        		}catch(DAOSystemException dse){
        			dse.printStackTrace();
        			throw new DAOSystemException("--- 增加订单实例发生异常，订单ID："+orderId);
        		}
        	}
            Iterator it = fileList.iterator();
            while(it.hasNext()){

                String rescInstanceCode = String.valueOf(it.next());
               try{
                   //对应采购来说，很特殊。正好和其他的订单类型相反。。。
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
//    	throw new DAOSystemException("---成功上传的有效数据数量为0");
    }
    if(resultCount>0){
        List list = new ArrayList();
        list.add(orderId);// 订单ID
        list.add(new Integer(resultCount));//执行成功的数量
        list.add(returnList);//执行成功的资源实例编码
        list.add(buf.toString());//执行失败的资源实例编码

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
