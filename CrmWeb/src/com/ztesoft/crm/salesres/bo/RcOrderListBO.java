package com.ztesoft.crm.salesres.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.salesres.dao.RcOrderListDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;

public class RcOrderListBO extends DictAction{
  public RcOrderListBO() {
  }
  /**
   * ��̬�ж���Դ�����Ƿ��иü�¼,����ޣ����˳������ٽ�����ȥ������м�¼����õ���¼�����ж�RC_ORDER_LIST�Ƿ��иü�¼��������򽫸ü�¼���룬���򲻲��롣
   *
   * @param orderId String
   * @param salesRescId String
   * @param fileList Collection
   * @throws DAOSystemException
   * @return String[]
   */
  public String[] upLoadStockOut(String orderId, String salesRescId,String appType, String inStorageId,String landId,Collection fileList) throws
      DAOSystemException {

    StringBuffer buf = new StringBuffer("�ڶ�Ӧ����Դ�����޼�¼��ʧ�ܵ�����:");
    StringBuffer buf1 = new StringBuffer("��RC_ORDER_LIST�����м�¼��ʧ�ܵ�����:");
    int total = 0;
    int total2=0;
    int total3=0;
    Iterator iter = fileList.iterator();
    while (iter.hasNext()) {
      String tmp = (String) iter.next();
      String str[]  = SrDAOFactory.getInstance().getRcOrderListDAO().checkRcEntity(salesRescId,tmp);

      if ( str != null&&!"C".equals(appType)) {//��Ϊ�ɹ�
       //System.out.println(str[0] + str[1] + str[2]);
        RcOrderListVO vo = new RcOrderListVO();
        vo.setOrderId(orderId);
        vo.setRescInstanceId(str[0]);
        vo.setSalesRescId(str[1]);
        vo.setRescInstanceCode(str[2]);
          // �ж�RC_ORDER_LIST���Ƿ��иü�¼
        RcOrderListVO voo = SrDAOFactory.getInstance().getRcOrderListDAO().
            findByPrimaryKey(vo.getOrderId(), vo.getRescInstanceId());
        //��RC_ORDER_LIST��������
        if (voo == null) {
          SrDAOFactory.getInstance().getRcOrderListDAO().insert(vo);
          total++;
        }else{
        total3++;
        }
      }else if (str == null&&"C".equals(appType)){
    	  String id = getRcEntityId(salesRescId);
    	  RcOrderListVO vo = new RcOrderListVO();
          vo.setOrderId(orderId);
          vo.setRescInstanceId(id);
          vo.setSalesRescId(salesRescId);
          vo.setRescInstanceCode(tmp);
          RcOrderListVO voo = SrDAOFactory.getInstance().getRcOrderListDAO().
          findByPrimaryKey(vo.getOrderId(), vo.getRescInstanceId());
          //        ��RC_ORDER_LIST��������
          if (voo == null) {
        	  SrDAOFactory.getInstance().getRcOrderListDAO().insert(vo);
            
            total++;
          }else{
        	  total3++;
          }
      }else{
        total2++;
      }

    }
    buf.append(total2+" ����");
    buf1.append(total3+" ����");
    return  new String[]{String.valueOf(total),buf.toString(), buf1.toString()};
  }

  public PageModel showOrderBill(DynamicDict dto ) throws FrameException {
  	Map map = (Map)dto.getValueByName("parameter") ;
  	String orderId = (String)map.get("orderId");
  	int pstat  = ((Integer)map.get("pageIndex")).intValue();
	int pend  = ((Integer)map.get("pageSize")).intValue();
  	//int pstat, int pend
    String sql = "";
    if (!orderId.equals("")) {
      sql += " and order_id=" + orderId;
    }
    RcOrderListDAO DAO = SrDAOFactory.getInstance().getRcOrderListDAO();
    return PageHelper.popupPageModel(DAO, sql, pstat, pend);
  }
  private String getRcEntityId(String salesRescId){
	    SeqDAOFactory seqDAOFactory = SeqDAOFactory.getInstance();
	    SequenceManageDAO sequenceManageDAO = seqDAOFactory.getSequenceManageDAO();
	    String ID =sequenceManageDAO.getNextSequence("RC_ENTITY", "RESOURCE_INSTANCE_ID");
	    return ID;
  }
  public String clearOrderBill(String orderId) throws DAOSystemException {

    long lon = SrDAOFactory.getInstance().getRcOrderListDAO().deleteByCond(
        " order_id=" + orderId);
    return String.valueOf(lon);
  }
  
  /**
   * ��ѯ��涩������ϸ���
   * @param orderId
   * @param salesRescName
   * @param resBCode
   * @param resECode
   * @param appType
   * @param outStorageName
   * @param inStorageName
   * @param operName
   * @param pstat
   * @param pend
   * @return
   * @throws DAOSystemException
   */
  public PageModel showOrderRescDetail(Map map,int pstat, int pend) 
  throws DAOSystemException {
	  PageModel pm = null;
	  if(map==null)
		  return new PageModel();
	  String orderId = (map.get("orderId"))==null?"":((String)map.get("orderId"));
	  String salesRescName = (map.get("salesRescName"))==null?"":((String)map.get("salesRescName"));
	  String resBCode = (map.get("resBCode"))==null?"":((String)map.get("resBCode"));
	  String resECode = (map.get("resECode"))==null?"":((String)map.get("resECode"));
	  String appType = (map.get("appType"))==null?"":((String)map.get("appType"));
	  String outStorageName = (map.get("outStorageName"))==null?"":((String)map.get("outStorageName"));
	  String inStorageName = (map.get("inStorageName"))==null?"":((String)map.get("inStorageName"));
	  String operName = (map.get("operName"))==null?"":((String)map.get("operName"));
	
	String cond = "";
	if (orderId!=null&&!orderId.equals("")) {
		cond += " and order_id=" + orderId;
	}
	RcOrderListDAO DAO = SrDAOFactory.getInstance().getRcOrderListDAO();
	pm = PageHelper.popupPageModel(DAO, cond, pstat, pend);
	if(pm==null||pm.getList().size()<1){
		pm = new PageModel();
		pm.setTotalCount(1);
		List listTemp = new ArrayList();
		RcOrderVO vo = new RcOrderVO();
		vo.setOrderId(orderId);
		vo.setSalesRescName(salesRescName);
		vo.setResBCode(resBCode);
		vo.setResECode(resECode);
		vo.setAppType(appType);
		vo.setOutStorageName(outStorageName);
		vo.setInStorageName(inStorageName);
		vo.setOperName(operName);
		listTemp.add(vo);
		pm.setList(listTemp);
	}else{
		List list_orig = pm.getList();
		List listTemp2 = new ArrayList();
		RcOrderListVO listVOTemp = null;
		RcOrderVO orderVO = null;
		if(list_orig!=null)
		for(int i=0;i<list_orig.size();i++){
			listVOTemp = (RcOrderListVO)list_orig.get(i);
			if(listVOTemp!=null){
				orderVO = new RcOrderVO();
				orderVO.setResBCode(listVOTemp.getRescInstanceCode());
				orderVO.setOrderId(listVOTemp.getOrderId());
				orderVO.setSalesRescName(salesRescName);
				orderVO.setAppType(appType);
				orderVO.setOutStorageName(outStorageName);
				orderVO.setInStorageName(inStorageName);
				orderVO.setOperName(operName);
				listTemp2.add(orderVO);
			}
		}
		pm.setList(listTemp2);
	}
	return pm;
  }
  
}
