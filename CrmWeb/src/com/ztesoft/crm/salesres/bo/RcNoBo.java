package com.ztesoft.crm.salesres.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.crm.customer.custinfo.dao.CustDAO;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcApplicationDAO;
import com.ztesoft.crm.salesres.dao.RcLevelDefDAO;
import com.ztesoft.crm.salesres.dao.RcLevelLogDAO;
import com.ztesoft.crm.salesres.dao.RcNoDAO;
import com.ztesoft.crm.salesres.dao.RcNoRemainDAO;
import com.ztesoft.crm.salesres.dao.RcNoRemainInfoDAO;
import com.ztesoft.crm.salesres.dao.RcNoSegDAO;
import com.ztesoft.crm.salesres.dao.RcNoServDAO;
import com.ztesoft.crm.salesres.dao.RcOrderDAO;
import com.ztesoft.crm.salesres.dao.RcOrderExcDAO;
import com.ztesoft.crm.salesres.dao.RcPublicLogDAO;
import com.ztesoft.crm.salesres.dao.RcStateDAO;
import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.dao.RnNumberPreDAO;
import com.ztesoft.crm.salesres.dao.SqlComDAO;
import com.ztesoft.crm.salesres.dao.SrDAOFactory;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.exception.LogicInfoException;
import com.ztesoft.crm.salesres.vo.RcApplicationVO;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;
import com.ztesoft.crm.salesres.vo.RcLevelLogVO;
import com.ztesoft.crm.salesres.vo.RcNoRemainInfoVO;
import com.ztesoft.crm.salesres.vo.RcNoRemainVO;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;
import com.ztesoft.crm.salesres.vo.RcNoVO;
import com.ztesoft.crm.salesres.vo.RcOrderExcVO;
import com.ztesoft.crm.salesres.vo.RcOrderVO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;
import com.ztesoft.crm.salesres.vo.RcStateVO;
import com.ztesoft.crm.salesres.vo.RnNumberPreVO;


public class RcNoBo  extends DictAction {
    private SrFileUploadBo fileUpload = new SrFileUploadBo();
	public RcNoBo() {
    	
    }
	/**
	 * �ṩǰ̨ѡ���õĲ�ѯ����
	 * @param dto
	 * @return
	 * @throws FrameException
	 */
	public PageModel getRcNo(DynamicDict dto ) throws FrameException {
		Map m = (Map)dto.getValueByName("parameter") ;
		int pi  = ((Integer)m.get("pageIndex")).intValue();
		int ps  = ((Integer)m.get("pageSize")).intValue();
		String lanId = (String)m.get("lan_id");
		String regionId = (String)m.get("business_id");
		String operId = (String)m.get("oper_id");
		String noLevel = (String)m.get("nbr_level");
		String serType = (String)m.get("search_type");//     ��ѯ��ʽ��1.β����ѯ  2.��ȷ��ѯ��
		String serValue = (String)m.get("search_value");
		RcNoServDAO dao = new RcNoServDAO();
	
		//״̬Ϊ��Ч�� resource_state='A'
		StringBuffer sbuffer = new StringBuffer(" and a.resource_state='A' and a.lan_id =? ");
		List para = new ArrayList();
		para.add(lanId);
		

		if ("2".equals(serType)) {//��ȷģʽ
			sbuffer.append(" and a.resource_instance_code =? ");
			para.add(serValue);
		} else if ("1".equals(serType)) {//λ��ƥ��ģʽ
			sbuffer.append(" and a.resource_instance_code like ? ");
			para.add("%"+serValue);
		} else {
			return new PageModel();
		}
		if(operId!=null){//�������������ֿ�ĺ�����Դ
			sbuffer.append(" and a.storage_id in ( ");
			sbuffer.append(" select mp.storage_id from mp_storage mp where mp.oper_id =? )");
			para.add(operId);
		}
		if(noLevel!=null){//����ȼ�
			sbuffer.append(" and a.resource_level =? ");
			para.add(noLevel);
		}
		if(regionId!=null){//Ӫҵ��
			sbuffer.append(" and a.region_id =? ");
			para.add(regionId);
		}

        PageModel result = dao.searchByCond(sbuffer.toString(), para,
                pi, ps);

		return result;
	}
    
    /**
     * ���ݺ���ļ���id���õ��������͵����кŶμ���,�Ŷ�״̬Ϊ'δ����'��'����'
     *
     * @param familyId
     *            String
     * @return PageModel
     * @throws FrameException 
     */
    public PageModel qryRcNoSegByFamilyId(DynamicDict dto ) throws FrameException {
    	Map m = (Map)dto.getValueByName("parameter") ;
		int pi  = ((Integer)m.get("pageIndex")).intValue();
		int ps  = ((Integer)m.get("pageSize")).intValue();
		String noSegName = (String)m.get("noSegName");
		String lanId = (String)m.get("lanId");
	       
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        String familyId = ParamsConsConfig.Rc_No_FamilyId;//segDao.findNoFamilyId();

        

        String cond = "  a.sales_resource_id=c.sales_resource_id and a.no_group_id=b.no_group_id and (a.state='0' or a.state='2') ";

        if ((noSegName != null) && (noSegName.trim().length() > 0)) {
            cond += (" and a.NO_SEG_NAME like '%" +
            DAOUtils.filterQureyCond(noSegName.trim()) + "%'");
        }

        if (lanId != null && !lanId.equals("")) {
            cond += (" and exists (select null from rc_no_group b where b.no_group_id = a.no_group_id and b.lan_id = " +
            		lanId + ")");
        }

        PageModel pm = new PageModel();

        if ((familyId != null) && (familyId.trim().length() > 0)) {
            pm = PageHelper.popupPageModel(segDao, cond, pi, ps);
        }

        return pm;
    }
    /**
     * ���ݽ���������ѯ����
     *
     * @param map
     *            Map
     * @param pi
     *            int
     * @param ps
     *            int
     * @return PageModel
     * @throws FrameException 
     */
    public PageModel qryRcNo(DynamicDict dto) throws FrameException {
        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        dao.setFlag(1);
        Map map = (Map)dto.getValueByName("parameter") ;
		int pi  = ((Integer)map.get("pageIndex")).intValue();
		int ps  = ((Integer)map.get("pageSize")).intValue();
        
		PageModel pm = new PageModel();
        String cond = " 1=1 ";
        String rescLevel = null;
        String noSegId = null;
        String storageId = null;
        String oldLevel = null;
        String operId = null;
        String departId = null;

        //		String provId = null;
        if (map != null) {
            if ((map.get("localLanId") != null) &&
                    !map.get("localLanId").toString().trim().equals("")) {
                if ((map.get("provId") != null) &&
                        map.get("provId").toString().trim().equals("20")) {
                    cond += (" and exists(SELECT NULL FROM RC_NO_SEG A,RC_NO_GROUP B WHERE A.NO_GROUP_ID=B.NO_GROUP_ID AND A.NO_SEG_ID=RC_NO.NO_SEG_ID AND B.LAN_ID=" +
                    map.get("localLanId") + ")");
                } else if ((map.get("provId") != null) &&
                        map.get("provId").toString().trim().equals("2")) {
                    cond += (" and lan_id=" + map.get("localLanId"));
                }
            }

            //			if (map.get("qryType") != null){
            //				String qryType = (String)map.get("qryType");
            //				if("0".equals(qryType)){
            if ((map.get("startCode") != null) &&
                    !map.get("startCode").equals("")) {
                cond += (" and  RESOURCE_INSTANCE_CODE >= '" +
                map.get("startCode").toString().trim() + "'");
            }

            if ((map.get("endCode") != null) && !map.get("endCode").equals("")) {
                cond += (" and  RESOURCE_INSTANCE_CODE <= '" +
                map.get("endCode").toString().trim() + "'");
            }

            //				}else if("1".equals(qryType)){
            if ((map.get("rescInstanceCode") != null) &&
                    !map.get("rescInstanceCode").equals("")) {
                cond += (" and  RESOURCE_INSTANCE_CODE like '" +
                DAOUtils.filterQureyCond(map.get("rescInstanceCode").toString()
                                            .trim()) + "%'");
            }

            //				}else{
            if ((map.get("searchContent") != null) &&
                    !map.get("searchContent").equals("")) {
                String sContent = (String) map.get("searchContent");

                if (sContent.indexOf(",") > 0) {
                    if (sContent.endsWith(",")) {
                        cond += ("  and RESOURCE_INSTANCE_CODE in ('" +
                        sContent.substring(0, sContent.length() - 1)
                                .replaceAll(",", "','") + "' )");
                    } else {
                        cond += ("  and RESOURCE_INSTANCE_CODE in ('" +
                        sContent.replaceAll(",", "','") + "')");
                    }
                } else {
                    cond += ("  and RESOURCE_INSTANCE_CODE ='" + sContent +
                    "'");
                }

                //					}

                //				}
            }

            if ((map.get("rescState") != null) &&
                    !map.get("rescState").equals("")) {
                cond += (" and  RESOURCE_STATE = '" +
                map.get("rescState").toString().trim() + "'");
            }

            if (map.get("rescLevel") != null) {
                rescLevel = (String) map.get("rescLevel");

                if ((rescLevel != null) && !"".equals(rescLevel)) {
                    cond += (" and  RESOURCE_LEVEL = " + rescLevel);
                }
            }

            if (map.get("noSegId") != null) {
                noSegId = (String) map.get("noSegId");

                if ((noSegId != null) && !"".equals(noSegId)) {
                    cond += (" and  rc_no.no_seg_id = " + noSegId);
                }
            }

            if ((map.get("lanId") != null) &&
                    !map.get("lanId").toString().trim().equals("")) {
                cond += (" and  lan_id=" + map.get("lanId"));
            }

            if ((map.get("regionId") != null) &&
                    !map.get("regionId").toString().trim().equals("")) {
                cond += (" and  region_id=" + map.get("regionId"));
            }

            if ((map.get("exchId") != null) &&
                    !map.get("exchId").toString().trim().equals("")) {
                cond += (" and  exch_id=" + map.get("exchId"));
            }

            storageId = (String) map.get("storageId");

            if ((map.get("storageId") != null) &&
                    (((String) map.get("storageId")).trim().length() > 0)) {
                if ((storageId != null) && !"".equals(storageId)) {
                    storageId = (!storageId.endsWith(",")) ? storageId
                                                           : storageId.substring(0,
                            storageId.length() - 1);

                    if (storageId.indexOf(",") > 0) {
                        cond += (" and  STORAGE_ID in (" + storageId + ")");
                    } else {
                        cond += (" and  STORAGE_ID = " + storageId);
                    }
                }
            } else {
                if ((map.get("operId") != null) &&
                        (((String) map.get("operId")).trim().length() > 0)) {
                    operId = (String) map.get("operId");
                }

                if ((map.get("departId") != null) &&
                        (((String) map.get("departId")).trim().length() > 0)) {
                    departId = (String) map.get("departId");
                }

                // storage right filte
                String right_cond = CommonUtilBo.getStorageRightFilteSql(operId,
                        departId, "RC_NO.STORAGE_ID");

                if ((right_cond != null) && (right_cond.trim().length() > 0)) {
                    cond += right_cond;
                }
            }

            if (map.get("getOldLevel") != null) {
                oldLevel = (String) map.get("getOldLevel");

                if ((oldLevel != null) && oldLevel.equals("1")) {
                    dao.setFlag(3);

                    String dc_sql = ("SELECT DC_SQL FROM rcdb.DC_SQL WHERE DC_NAME='" +
                        ParamsConsConfig.Rc_No_FamilyId + "'");
                    List list = SrDAOFactory.getInstance().getSqlComDAO()
                                            .qryComSql(dc_sql,
                            new String[] { "DC_SQL" });

                    if ((list != null) && (list.size() > 0)) {
                        dao.setNoFamilyId(String.valueOf(((Map) list.get(0)).get(
                                    "DC_SQL")));
                    }
                }
            }
        }

        dao.setFlag(4);
        cond += " order by  RESOURCE_INSTANCE_ID ";
        pm = PageHelper.popupPageModel(dao, cond, pi, ps);

        return pm;
    }

    /**
     * ���ݽ���������ѯ����,��Թ���--�����ѯ�˵�
     *
     * @param map
     *            Map
     * @param pi
     *            int
     * @param ps
     *            int
     * @return PageModel
     */
    public PageModel qryRcNo2(Map map, int pi, int ps) {
        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        dao.setFlag(1);

        PageModel pm = new PageModel();
        String cond = " 1=1 ";

        if (map != null) {
            if ((map.get("startCode") != null) &&
                    !map.get("startCode").equals("")) {
                cond += (" and  RESOURCE_INSTANCE_CODE >= '" +
                map.get("startCode").toString().trim() + "'");
            }

            if ((map.get("endCode") != null) && !map.get("endCode").equals("")) {
                cond += (" and  RESOURCE_INSTANCE_CODE <= '" +
                map.get("endCode").toString().trim() + "'");
            }
        }

        cond += " order by  RESOURCE_INSTANCE_ID ";
        pm = PageHelper.popupPageModel(dao, cond, pi, ps);

        return pm;
    }

    /**
     * ����һ�����룬�����������룻
     *
     * @param vo
     *            RcNoVO
     * @return int:-1ȱ�ٲ�����-2:�����Ѵ��ڴ�������pk
     */
//    public String addRcNo(RcNoVO vo) {
    public String addRcNo(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        RcNoVO vo  = (RcNoVO)map.get("vo");
        return doAddRcNo(vo);
    }

	protected String doAddRcNo(RcNoVO vo) {
		String rtn = "0";

        if (vo != null) {
            RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
            String pk = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                     .getNextSequence("RC_ENTITY", "RcEntity_ID");

            // ��ѯ�����Ƿ��ظ�
            List list = dao.findByCond(" RESOURCE_INSTANCE_CODE='" +
                    vo.getRescInstanceCode().trim() + "'");

            if ((list != null) && (list.size() > 0)) {
                rtn = "-2";
            } else {
                // ���Ŷ�״̬,������δ����״̬
                rtn = checkNoSegState(vo);

                if ((rtn == null) || !"1".equals(rtn)) {
                    rtn = "-3";
                } else {
                    // ���ú������ʱ��
                    vo.setInitTime(DAOUtils.getFormatedDate());
                    // ���ú��뼶��
                    vo.setRescLevel(this.setEntityLevel(
                            vo.getRescInstanceCode(), null));
                    // �������
                    vo.setRescInstanceId(pk);
                    dao.insert(vo);
                    rtn = pk;
                }
            }
        } else {
            rtn = "-1";
        }

        return rtn;
	}

    // /**
    // * ����һ�����룬�����������룻
    // * @param vo RcNoVO
    // * @return int:-1ȱ�ٲ�����-2:�����Ѵ��ڴ�������pk
    // */
    // private String assembleInsertNoSql(RcNoVO vo) {
    // String rtn = null;
    // if (vo != null) {
    // // �������
    // dao.insert(vo);
    // rtn = pk;
    // }
    // else {
    // rtn = null;
    // }
    // return rtn;
    // }

    /**
     * ����һ������״̬
     * @param map
     * @return Map��result:-1ȱ�ٲ���,1�ɹ�ִ�У���������ʧ�ܼ�¼��errorList:ʧ�ܺ���ļ��ϣ���Щ�������߼����󣬼�������Ҫ��ĺ���
     */
    //public Map updatMultiNoState(Map map) {
    public Map updatMultiNoState(DynamicDict dto) throws FrameException {
        Map map = (Map)dto.getValueByName("parameter");
        RcNoVO vo = (RcNoVO) map.get("vo");
        String operId = (String) map.get("operId");
        String departId = (String) map.get("departId");
        String operCode = (String) map.get("operCode");
        String states = (String) map.get("states"); //dc_public���ò����޸ĵĺ���״̬
        String[] noArr = null;
        int checkType = 0; // Ϊ1ʱ�������Ƿ�Ϊsates��Ϊ0ʱ�������

        if ((map.get("checkType") != null) &&
                ((String) map.get("checkType")).equals("1")) {
            checkType = 1;
        }

        if (map.get("operId") != null) {
            operId = (String) map.get("operId");
            vo.setOperId(operId + "@" + departId);
        }

        if (map.get("operCode") != null) {
            operCode = (String) map.get("operCode");
            vo.setOperCode(operCode);
        }

        if (map.get("noArr") != null) {
            noArr = (String[]) map.get("noArr");
        }

        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        Map rtnmap = null;
        // ���ڴ��жϺ����Ƿ�ҵ�����ã�ֱ��ͨ������״̬�ж�
        //		SqlExcuteByStr G = new SqlExcuteByStr();
        //		String sqlStr = " select count(1) as result from pdn_pdinfo where dn_no = '"+vo.getRescInstanceCode()+"'";
        //		String sCount = "";
        //		sCount=G.getString(sqlStr);
        //		if(Integer.parseInt(sCount,10)>0){
        //			return -1;
        //		}else{
        //			sqlStr = " select count(1) as result from pd_pdinfo where dn_no = '"+vo.getRescInstanceCode()+"'";
        //			sCount=G.getString(sqlStr);
        //			if(Integer.parseInt(sCount,10)>0){
        //				return -1;
        //			}else{
        //				sqlStr = " select count(1) as result from rc_no where RESOURCE_INSTANCE_CODE = '"+vo.getRescInstanceCode()+"' and IMSI_ID is not null  ";
        //				sCount=G.getString(sqlStr);
        //				if(Integer.parseInt(sCount,10)>0){
        //					return -1;
        //				}
        //				// �ڴ˲��жϣ���Ϊ�ڽ��������û�
        //				else{
        //					sqlStr = " select count(1) as result from rc_no where RESOURCE_INSTANCE_CODE = '"+vo.getRescInstanceCode()+"' and state = '00A' and RESOURCE_STATE = '"+ParamsConsConfig.RescState_preSelect+"'  ";
        //					sCount=G.getString(sqlStr);
        //					if(Integer.parseInt(sCount,10)>0){
        //						return -1;
        //					}
        //				}
        //			}	
        //		}
        // RcNoVO vo_old = dao.findByPrimaryKey(vo.getRescInstanceId());
        rtnmap = dao.updateBatchNoState(vo, noArr, checkType, states);

        return rtnmap;
    }

    /**
     * ����һ������
     *
     * @param vo
     *            RcNoVO
     * @return boolean
     */
    public int updatMultiNo(Map map) {
        RcNoVO vo = (RcNoVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");
        String operCode = (String) map.get("operCode");

        vo.setOperCode(operCode);
        vo.setOperId(operId);

        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();

        // RcNoVO vo_old = dao.findByPrimaryKey(vo.getRescInstanceId());
        int rtn = dao.updateBatch(vo);

        // �������ͨ������
        RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
        RcPublicLogVO logVO = new RcPublicLogVO();
        logVO.setAct("M");
        logVO.setReworkTime(DAOUtils.getFormatedDate());
        logVO.setReworkTable("rc_no");
        logVO.setReworkWen(operId);
        logVO.setReworkIp(reworkIp);
        logDao.logVO(logVO, "", vo);

        return rtn;
    }

    public Map updateTxtBatch(Map map) {
        RcNoVO vo = (RcNoVO) map.get("vo");
        vo.setOperCode((String) map.get("operCode"));

        String flag = (String) map.get("flag");
        List list = new ArrayList();
        list = (List) map.get("list");

        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();

        return dao.updateTxtBatch(vo, flag, list);
    }

    /**
     * ����һ������
     *
     * @param vo
     *            RcNoVO
     * @return boolean
     */

    public boolean updateNo(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
    	
        RcNoVO vo = (RcNoVO) map.get("vo");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        if ((vo == null) || (vo.getRescInstanceId() == null) ||
                (vo.getRescInstanceId().trim().length() < 1)) {
            return false;
        }

        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcNoVO vo_old = dao.findByPrimaryKey(vo.getRescInstanceId());
        boolean rtn = dao.update(vo.getRescInstanceId(), vo);

        // �������ͨ������
        RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
        RcPublicLogVO logVO = new RcPublicLogVO();
        logVO.setAct("M");
        logVO.setReworkTime(DAOUtils.getFormatedDate());
        logVO.setReworkTable("rc_no");
        logVO.setReworkWen(operId);
        logVO.setReworkIp(reworkIp);
        logDao.logVO(logVO, vo_old, vo);

        return rtn;
    }

    /**
     * ɾ������,֧�ֶ�������ɾ��,�������","����
     *
     * @param vo
     *            RcNoVO
     * @return long.-2Ϊ�к��벻�ǿ���״̬,����ɾ��
     */
//    public long delNo(Map map) {
    public long delNo(DynamicDict dto) throws FrameException {
            
        Map map = (Map)dto.getValueByName("parameter");
        
        if ((map == null) || (map.get("pks") == null)) {
            return 0L;
        }

        String pks = (String) map.get("pks");
        String reworkIp = (String) map.get("reworkIp");
        String operId = (String) map.get("operId");

        if ((pks == null) || (pks.trim().length() < 1)) {
            return 0L;
        }

        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();

        if (pks.endsWith(",")) {
            pks = pks.substring(0, pks.length() - 1);
        }

        String cond1 = " RESOURCE_STATE!='" +
            ParamsConsConfig.RcEntityFreeState +
            "' and RESOURCE_INSTANCE_ID in (" + pks + ")";
        String cond2 = " RESOURCE_INSTANCE_ID in (" + pks + ")";

        // �����Щ�����״̬�Ƿ���ռ��״̬��
        List list = dao.findByCond(cond1);

        if ((list != null) && (list.size() > 0)) {
            return -2;
        }

        // �������ͨ������
        RcNoVO vo_old = null;
        RcPublicLogDAO logDao = SrDAOFactory.getInstance().getRcPublicLogDAO();
        StringTokenizer token = new StringTokenizer(pks, ",");

        while (token.hasMoreTokens()) {
            vo_old = dao.findByPrimaryKey(token.nextToken());

            RcPublicLogVO logVO = new RcPublicLogVO();
            logVO.setAct("D");
            logVO.setReworkTime(DAOUtils.getFormatedDate());
            logVO.setReworkTable("rc_no");
            logVO.setReworkWen(operId);
            logVO.setReworkIp(reworkIp);
            logDao.logVO(logVO, vo_old, null);
        }

        // ɾ������
        long rtn = dao.deleteByCond(cond2);

        return rtn;
    }

    /**
     * ���ݽ���İ��Ŷη�ʽ���ɺ���
     *
     * @param map
     *            Map
     * @return int
     */
    public Map geneNos(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
		
        Map rtnMap = new HashMap();
        String result = "0";
        String info = "";

        if ((map == null) || (map.get("No") == null) ||
                (map.get("beginno") == null) || (map.get("endno") == null)) {
            result = "0";
            info = "ȱ�ٲ���!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);

            return rtnMap;
        }

        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        long rtn = 0L;
        RcNoVO vo = (RcNoVO) map.get("No");
        String beginnoStr = (String) map.get("beginno");
        String endnoStr = (String) map.get("endno");
        String operId = null;
        String departId = null;

        if (map.get("operId") != null) {
            operId = (String) map.get("operId");
        }

        if (map.get("departId") != null) {
            departId = (String) map.get("departId");
        }

        List levelList = null;
        String familyId = segDao.findNoFamilyId();
        RcLevelDefDAO levelDao = SrDAOFactory.getInstance().getRcLevelDefDAO();

        if ((familyId != null) && (familyId.trim().length() > 0)) {
            levelList = levelDao.findByFimilyForCal(familyId);
        }

        // ��֤��ʼ����֮��ĺ����Ƿ����
        /*
        String condTemp = "  resource_instance_code>="
                        + DAOUtils.filterQureyCond(beginnoStr)
                        + " and resource_instance_code<="
                        + DAOUtils.filterQureyCond(endnoStr);
        List listTempt = dao.findByCond(condTemp);
        */

        //----modified by panyazong ��Ϊ�����󶨣�Ԥ���뷽ʽ   20090618
        String SQL_SELECT = "SELECT * FROM RC_NO where resource_instance_code>=? and resource_instance_code<= ?";
        String[] sqlParams = new String[2];
        sqlParams[0] = DAOUtils.filterQureyCond(beginnoStr);
        sqlParams[1] = DAOUtils.filterQureyCond(endnoStr);

        List listTempt = dao.findBySql(SQL_SELECT, sqlParams);

        if ((listTempt != null) && (listTempt.size() > 0)) {
            result = "0";
            info = "��ʼ�������ֹ����֮���Ѿ��к��������,���ɺ���ʧ��!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);

            return rtnMap;
        }

        // ���Ŷ�״̬�������δ����������
        String rtnSeg = checkNoSegState(vo);

        if ((rtnSeg == null) || !"1".equals(rtnSeg)) {
            result = "0";
            info = "�Ŷ���Ϣ������鿴�úŶ��Ƿ������Ϊ��Ϊ����״̬!";
            rtnMap.put("result", result);
            rtnMap.put("info", info);

            return rtnMap;
        }

        String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("rc_order", "order_id");
        // ���ú������ʱ��
        vo.setInitTime(DAOUtils.getFormatedDate());
        // �������ɺ���
        rtn = dao.batchInsert(vo, beginnoStr, endnoStr, levelList);
        // ���ɺ�����ⶩ��
        this.insertOrderInfoNo("C", vo.getSalesRescId(), operId, departId,
            vo.getStorageId(), "", String.valueOf(rtn), beginnoStr, endnoStr,
            null, orderId, "", "i");
        result = "1";
        info = "�ɹ�����" + rtn + "�����룬���κ������ɵĶ�����Ϊ:" + orderId;
        rtnMap.put("result", result);
        rtnMap.put("info", info);

        return rtnMap;
    }

    /**
     * �����ļ��ϴ���ʽ���ɺ���
     * �����߼�:
     * 1 �Ƚ��ϴ������ݷ�װ��VO�ļ���,
            2 ���˵��Ѿ����ڵļ�¼.
            3 д��������������¼
            4 �ڶ���������������
            5 �ڶ����嵥�������ɶ����嵥.
     * @param vo
     *            RcNoVO
     * @param list
     *            List
     * @return String
     * @throws SQLException
     * @throws Exception
     */
    public Map geneNosFromFile(DynamicDict dto ) throws FrameException {
    	Map paraMap = (Map)dto.getValueByName("parameter") ;
		
        if (paraMap == null) {
            return null;
        }

        Map map = new HashMap();
        RcNoVO vo = (RcNoVO) paraMap.get("No");
        List list = (List) paraMap.get("NoList");
        String operId = null;
        String departId = null;

        if (paraMap.get("operId") != null) {
            operId = (String) paraMap.get("operId");
        }

        if (paraMap.get("departId") != null) {
            departId = (String) paraMap.get("departId");
        }

        Map nosMap = new HashMap();

        //String result = "1";
        String alertInfo = "";
        String txtInfo = "";

        if ((vo == null) || (list == null) || (list.size() < 1)) {
            alertInfo = "ȱ��������������Ի��ϴ��������Ϊ0,���ɺ���ʧ��!";
            txtInfo = "ȱ��������������Ի��ϴ��������Ϊ0,���ɺ���ʧ��!";
            map.put("result", "0");
            map.put("alertInfo", alertInfo);
            map.put("txtInfo", txtInfo);

            return map;
        }

        int successCount = 0;
        int failCount = 0;
        String nowDate = DAOUtils.getFormatedDate();
        RcNoVO insertVO = null;
        List txtIngoList = new ArrayList();
        List levelList = null;
        String levelId = "";

        //String rtn = null;
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        RcNoSegVO segVO = null;

        //SqlComDAO comDao = new SqlComDAOImpl(true);
        // �����жϼ���ļ���
        String familyId = segDao.findNoFamilyId();
        RcLevelDefDAO levelDao = SrDAOFactory.getInstance().getRcLevelDefDAO();
        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();

        if ((familyId != null) && (familyId.trim().length() > 0)) {
            levelList = levelDao.findByFimilyForCal(familyId);
        }

        // ����rc_order_list��sql
        //String sql_orderlist = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE ) values ";
        String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("rc_order", "order_id");

        //�������жϺ�������������,����״�ö�Ӧ��VOΪд��׼��
        ListIterator iter = list.listIterator();

        while (iter.hasNext()) {
            insertVO = (RcNoVO) iter.next();

            if (insertVO != null) {
                if ((insertVO.getRescInstanceCode() == null) ||
                        (insertVO.getRescInstanceCode().trim().length() < 1) ||
                        (insertVO.getNoSegName() == null) ||
                        (insertVO.getNoSegName().trim().length() < 1)) {
                    txtIngoList.add("����û��ָ���ϴ������ŶΣ�����ʧ��!");
                    failCount++;
                    iter.remove();

                    continue;
                } else if (!this.isInSegNo(insertVO)) {
                    txtIngoList.add("����:" + insertVO.getRescInstanceCode() +
                        "�ͺŶ�:" + insertVO.getNoSegName() + "��һ�£�����ʧ��!");
                    failCount++;
                    iter.remove();

                    continue;
                } else {
                    if (nosMap.get(insertVO.getRescInstanceCode()) != null) {
                        txtIngoList.add("����:" + insertVO.getRescInstanceCode() +
                            "���ϴ��ļ����Ѿ����ڣ�����ʧ��");
                        failCount++;
                        iter.remove();

                        continue;
                    }

                    nosMap.put(insertVO.getRescInstanceCode(),
                        insertVO.getRescInstanceCode());
                    levelId = this.setEntityLevel(insertVO.getRescInstanceCode(),
                            levelList);
                    // ���ú������ʱ��
                    insertVO.setRescInstanceId(SeqDAOFactory.getInstance()
                                                            .getSequenceManageDAO()
                                                            .getNextSequence("RC_ENTITY",
                            "RcEntity_ID"));
                    insertVO.setInitTime(nowDate);
                    insertVO.setRescLevel(levelId);
                    insertVO.setEffDate(vo.getEffDate());
                    insertVO.setExpDate(vo.getExpDate());
                    insertVO.setStorageId(vo.getStorageId());
                    insertVO.setStorageName(vo.getStorageName());
                    insertVO.setSelfhelpflag(vo.getSelfhelpflag());
                    insertVO.setState(ParamsConsConfig.RcEntityStateValide);
                    insertVO.setRescState(ParamsConsConfig.RcEntityFreeState);

                    // ���Һ����sales_resource_id
                    if ((insertVO.getNoSegId() != null) &&
                            (insertVO.getNoSegId().trim().length() > 0)) {
                        if (segVO == null) {
                            segVO = segDao.findByPrimaryKey(insertVO.getNoSegId());
                        }

                        if (segVO != null) {
                            insertVO.setBalaMode(segVO.getBalaMode());
                            insertVO.setSalesRescId(segVO.getSalesRescId());
                            insertVO.setSalesRescName(segVO.getSalesRescName());
                            insertVO.setNoSegName(segVO.getNoSegName());
                        }
                    }

                    /*rtn = this.addRcNo(insertVO);
                    if (rtn == null || "".equals(rtn)) {
                            txtIngoList.add("����:" + insertVO.getRescInstanceCode()
                                            + "������ʧ��!");
                            failCount++;
                    } else if ("-2".equals(rtn)) {
                            txtIngoList.add("����:" + insertVO.getRescInstanceCode()
                                            + "�Ѿ����ڣ�����ʧ��!");
                            failCount++;
                    } else {
                            txtIngoList.add("����:" + insertVO.getRescInstanceCode()
                                            + "�����ɹ�");
                            successCount++;
                            // �����ɹ�Ҫ�Ѻ���Ԥ����rc_order_list��
                            comDao.addBatchSql(sql_orderlist + " (" + orderId
                                            + ",null," + insertVO.getSalesRescId() + ",'"
                                            + insertVO.getRescInstanceCode() + "')");
                    }*/
                }
            }
        }

        //�������Ƿ��Ѿ�����
        List errList = fileUpload.filterExistsVO(list, -1);
        failCount += ((errList == null) ? 0 : errList.size());

        if (errList != null) {
            for (int i = 0; i < errList.size(); i++) {
                txtIngoList.add("����:" +
                    ((RcNoVO) errList.get(i)).getRescInstanceCode() +
                    "�Ѿ����ڣ�����ʧ��");
            }
        }

        //��������������
        dao.batchAdd(list);
        successCount = list.size();

        alertInfo = "���ɹ�����" + (successCount) + "�����룬ʧ��" + (failCount) + "��";

        if (successCount > 0) {
            // ����rc_order�ж�����Ϣ
            String salesRescId = ((RcNoVO) list.get(0)).getSalesRescId();
            this.insertOrderInfoNo("C", salesRescId, operId, departId,
                vo.getStorageId(), "", String.valueOf(successCount), null,
                null, null, orderId, "", "f");

            Iterator ite = list.listIterator();
            List paramList = new ArrayList();

            while (ite.hasNext()) {
                paramList.add(((RcNoVO) ite.next()).getRescInstanceCode());
            }

            //			�����ɹ�Ҫ�Ѻ���Ԥ����rc_order_list��
            fileUpload.batchFileUpload(orderId, null, null, paramList, "1=1",
                "RC_NO", "INSERT");

            //comDao.batchExecute(null); // ����rc_order_list���е�����
            alertInfo += ("�����κ������ɵĶ�����Ϊ:" + orderId);
        }

        //comDao.closeConnection();
        txtIngoList.add(alertInfo);
        map.put("alertInfo", alertInfo);
        map.put("txtIngoList", txtIngoList);

        return map;
    }

    /**
     * ��������������ʱ��Ҫ���ɶ���
     *
     * @param appType
     *            String
     * @param salesRescId
     *            String
     * @param operId
     *            String
     * @param departId
     *            String
     * @param inStorageId
     *            String
     * @param appAmount
     *            String
     * @param resBCode
     *            String
     * @param resECode
     *            String
     * @return boolean
     */
    public boolean insertOrderInfoNo(String appType, String salesRescId,
        String operId, String departId, String inStorageId,
        String outStorageId, String appAmount, String resBCode,
        String resECode, String noSegId, String orderId, String batchSql,
        String flag) {
        if ((appType == null) || (appType.trim().length() < 1) ||
                (inStorageId == null) || (inStorageId.trim().length() < 1)) {
            return false;
        }

        RcOrderDAO orderDao = (RcOrderDAO) SrDAOFactory.getInstance()
                                                       .getRcOrderDAO();
        RcApplicationDAO appDao = (RcApplicationDAO) SrDAOFactory.getInstance()
                                                                 .getRcApplicationDAO();
        RcOrderExcDAO execDao = (RcOrderExcDAO) SrDAOFactory.getInstance()
                                                            .getRcOrderExcDAO();
        SequenceManageDAO sequenceManageDAO = SeqDAOFactory.getInstance()
                                                           .getSequenceManageDAO();
        String appId = sequenceManageDAO.getNextSequence("rc_application",
                "app_id");

        if ((orderId == null) || (orderId.trim().length() < 1)) {
            orderId = sequenceManageDAO.getNextSequence("rc_order", "order_id");
        }

        String date = DateFormatUtils.getFormatedDateTime();

        RcApplicationVO appVO = new RcApplicationVO();
        RcOrderVO rcOrderVO = new RcOrderVO();
        RcOrderExcVO rcOrderExcVO = new RcOrderExcVO();

        // ���ݺŶβ�����ʼ��ֹ�������
        if (((resBCode == null) || (resBCode.trim().length() < 1)) &&
                ((resECode == null) || (resECode.trim().length() < 1)) &&
                ((noSegId != null) && (noSegId.trim().length() > 0))) {
            RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
            RcNoSegVO segVO = segDao.findByPrimaryKey(noSegId);

            if (segVO != null) {
                resBCode = segVO.getBeginn();
                resECode = segVO.getEndno();

                if (salesRescId == null) {
                    salesRescId = segVO.getSalesRescId();
                }
            }
        }

        // װ��rc_application
        appVO.setAppId(appId);
        appVO.setAppTime(date);
        appVO.setAppType(appType);
        appVO.setDepartId(departId);
        appVO.setOperId(operId);
        // װ��rc_order
        rcOrderVO.setOrderId(orderId);
        rcOrderVO.setAppId(appId);
        rcOrderVO.setAppType(appType);
        rcOrderVO.setOperId(operId);
        rcOrderVO.setDepartId(departId);
        rcOrderVO.setTacheId("5");
        rcOrderVO.setStateId("n");
        rcOrderVO.setAppTime(date);
        rcOrderVO.setTacheTime(date);
        rcOrderVO.setEndTime(date);
        rcOrderVO.setOutStorageId(outStorageId);
        rcOrderVO.setInStorageId(inStorageId);
        rcOrderVO.setAppStorageId(inStorageId);
        rcOrderVO.setSalesRescId(salesRescId);
        rcOrderVO.setAppAmount(appAmount);
        rcOrderVO.setActAmount(appAmount);
        rcOrderVO.setResBCode(resBCode);
        rcOrderVO.setResECode(resECode);
        rcOrderVO.setRecOptType(flag);

        // װ��rc_order_exc
        String logId = sequenceManageDAO.getNextSequence("rc_order_exc",
                "log_id");
        rcOrderExcVO.setLogId(logId);
        rcOrderExcVO.setOrderId(rcOrderVO.getOrderId());
        rcOrderExcVO.setTacheId("4");
        rcOrderExcVO.setExcTime(rcOrderVO.getTacheTime());
        rcOrderExcVO.setDepartId(departId);
        rcOrderExcVO.setOperId(operId);
        rcOrderExcVO.setStateId(rcOrderVO.getStateId());

        appDao.insert(appVO);
        orderDao.insert(rcOrderVO);
        execDao.insert(rcOrderExcVO);

        // ���batchSql��Ϊ�գ���ִ�в���rc_order_list��
        if ((batchSql != null) && (batchSql.length() > 0)) {
            SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();
            int count = comDao.excute(batchSql); // ����ִ�в���rc_order_list�ļ�¼

            //----modified by panyazong ��Ϊ�����󶨣�Ԥ���뷽ʽ   20090618
            String updatesql = "update rc_order set app_amount=?,act_amount=? where order_id=? ";
            String[] sqlParams = new String[3];
            sqlParams[0] = String.valueOf(count);
            sqlParams[1] = String.valueOf(count);
            sqlParams[2] = orderId;
            comDao.excute(updatesql, sqlParams);
        }

        return true;
    }

    /**
     * �жϺ����Ƿ��ںŶη�Χ��,����ҵ�������Ϣ��ѺŶ�id���ý�vo��
     *
     * @param rescInstanceCode
     *            String
     * @param noSegId
     *            String
     * @return boolean
     */
    private boolean isInSegNo(RcNoVO vo) {
        boolean rtn = false;

        if ((vo == null) || (vo.getRescInstanceCode() == null) ||
                (vo.getRescInstanceCode().trim().length() < 1) ||
                (vo.getNoSegName() == null) ||
                (vo.getNoSegName().trim().length() < 1)) {
            return rtn;
        }

        String rescInstanceCode = vo.getRescInstanceCode().trim();
        String segName = vo.getNoSegName().trim();
        RcNoSegDAO dao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        List list = dao.findByCond(
                " a.sales_resource_id = c.sales_resource_id and a.NO_GROUP_ID=b.NO_GROUP_ID and a.NO_SEG_NAME='" +
                segName + "'");
        RcNoSegVO segvo = null;

        if ((list != null) && (list.size() > 0)) {
            segvo = (RcNoSegVO) list.get(0);
        }

        if ((segvo == null) || (segvo.getBeginn() == null) ||
                (segvo.getBeginn().trim().length() < 1) ||
                (segvo.getEndno() == null) || (segvo.getEndno().length() < 1)) {
            return rtn;
        } else {
            vo.setNoSegId(segvo.getNoSegId());
            vo.setLanId(segvo.getLanId());
        }

        try {
            long codeNum = Long.parseLong(rescInstanceCode);
            long beginNo = Long.parseLong(segvo.getBeginn());
            long endNo = Long.parseLong(segvo.getEndno());

            if ((codeNum >= beginNo) && (codeNum <= endNo)) {
                rtn = true;
            } else {
                rtn = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtn = false;
        }

        return rtn;
    }

    /**
     * ���Ŷ�״̬�����״̬��δ���ã���ĳ�����״̬;��������salesRescIdΪ�գ����ڴ˸���
     *
     * @param noSegId
     *            String
     * @return int��-1ȱ�ٲ�����-2���Ŷβ����ڣ�-3���Ŷ��ǽ���״̬��1����
     */
    public String checkNoSegState(RcNoVO vo) {
        if ((vo == null) || (vo.getNoSegId() == null)) {
            return "-1";
        }

        String noSegId = vo.getNoSegId();
        RcNoSegVO voSeg = null;
        RcNoSegDAO dao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        voSeg = dao.findByPrimaryKey(noSegId);

        if (vo == null) {
            return "-2";
        }

        if ("1".equals(voSeg.getState())) {
            return "-3";
        } else if ("0".equals(voSeg.getState())) {
            // ��������salesResourceIdΪ�գ����ڴ�д��Ŷε�salesRescId
            if ((vo.getSalesRescId() == null) ||
                    (vo.getSalesRescId().trim().length() < 1)) {
                vo.setSalesRescId(voSeg.getSalesRescId());

                // ����δ����״̬�ĺŶ�Ҫ������Ŷ�״̬Ϊ����
            }

            voSeg.setState("2");
            dao.update(noSegId, voSeg);
        }

        return "1";
    }

    /**
     * ���ݴ���ļ��𼯺�ȷ������ʵ���ļ���,�˴�Ҫ��֤�����levelList�еĵȼ��ǰ����ȼ���С����˳�����е�(important)
     *
     * @param vo
     *            RcEntityVO
     * @param levelList
     *            List
     * @return RcEntityVO
     */
    public String setEntityLevel(String code, List levelList) {
        if (code == null) {
            return "";
        }

        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();

        if ((levelList == null) || (levelList.size() < 1)) {
            String familyId = segDao.findNoFamilyId();
            RcLevelDefDAO levelDao = SrDAOFactory.getInstance()
                                                 .getRcLevelDefDAO();

            if ((familyId != null) && (familyId.trim().length() > 0)) {
                levelList = levelDao.findByCond(" family_id=" + familyId, null);
            }
        }

        if ((levelList == null) || (levelList.size() < 1)) {
            return "";
        }

        RcLevelDefVO levelVO = null;

        // int prior = Integer.MAX_VALUE;
        String levelId = "";
        String rule = "";

        for (int i = 0; i < levelList.size(); i++) {
            levelVO = (RcLevelDefVO) levelList.get(i);

            if ((levelVO != null) && (levelVO.getRuleString() != null)) {
                rule = levelVO.getRuleString();

                if (code.matches(rule)) {
                    levelId = levelVO.getRcLevelId();

                    break;
                }
            }
        }

        return levelId;
    }

    /**
     * �������
     *
     * @param map
     *            Map
     * @return Map
     * @throws SQLException
     */
    public Map deliverNos(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
        long count = 0L;
        String result = "1";
        String info = "";

        String updateCond = null;

        if ((map == null) || (map.get("deliverType") == null) ||
                (map.get("No") == null) ||
                (((RcNoVO) map.get("No")).getStorageId() == null) ||
                (((RcNoVO) map.get("No")).getStorageId().trim().length() < 1) ||
                (((RcNoVO) map.get("No")).getStorageId_old() == null) ||
                (((RcNoVO) map.get("No")).getStorageId_old().trim().length() < 1)) {
            result = "0";
            info = "ȱ�ٲ�������!";
        }

        String operId = null;
        String departId = null;

        if (map.get("operId") != null) {
            operId = (String) map.get("operId");
        }

        if (map.get("departId") != null) {
            departId = (String) map.get("departId");
        }

        // ���ڷ���ĺ�������ж���ֿ���Դ�����ַ���Ϊ���붩�������ֿ�
        String outStroageIdTemp = "";

        RcNoVO vo = (RcNoVO) map.get("No");
        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        String deliverType = ((String) map.get("deliverType")).trim();
        String noSegId = null;
        String nobagId = null;
        String beginno = null;
        String endno = null;
        String salesRescId = "";

        String sql = "select * from rc_no where 1 =1 ";
        String[] sqlParams = new String[5];
        int i = 0;

        if (vo.getStorageId_old().indexOf(",") == -1) {
            outStroageIdTemp = vo.getStorageId_old();
        }

        String qryCond = " STORAGE_ID in(" + vo.getStorageId_old() +
            ") and STATE='" + ParamsConsConfig.RcEntityStateValide + "'";

        // ����rc_order_list�е�����
        String orderId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                      .getNextSequence("rc_order", "order_id");
        String batchSql = "INSERT INTO RC_ORDER_LIST ( ORDER_ID,RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,STORAGE_ID_FROM,STORAGE_NAME_FROM,STORAGE_ID_TO,STORAGE_NAME_TO )" +
            " select " + orderId +
            ",RESOURCE_INSTANCE_ID,SALES_RESOURCE_ID,RESOURCE_INSTANCE_CODE,STORAGE_ID,STORAGE_NAME, " +
            vo.getStorageId().trim() + ",'" + vo.getStorageName().trim() + "'" +
            " from rc_no where ";

        if ("1".equals(deliverType)) { // ���Ŷη�ʽ���к������

            if (vo.getNoSegId() != null) {
                noSegId = vo.getNoSegId();
                qryCond += (" and NO_SEG_ID=" + noSegId);

                if (!"".equals(vo.getRescLevel())) {
                    qryCond += (" and RESOURCE_LEVEL =" + vo.getRescLevel());
                }

                if (!"".equals(vo.getNoNumber())) {
                    qryCond += (" and rownum  <=" + vo.getNoNumber());
                }

                List listTemp = dao.findByCond(qryCond);

                if ((listTemp != null) && (listTemp.size() > 0)) {
                    // �ɹ�������ѯ���úŶε���ʼ����ֹ����
                    RcNoSegVO segVO = segDao.findByPrimaryKey(noSegId);

                    if (segVO != null) {
                        beginno = segVO.getBeginn();
                        endno = segVO.getEndno();
                        salesRescId = segVO.getSalesRescId();
                    }

                    // ����rc_order_list����־����ΪҪ�������ĺ��룬���Ҫ�ȼ�¼��־
                    batchSql += qryCond;
                    this.insertOrderInfoNo("J", salesRescId, operId, departId,
                        vo.getStorageId().trim(), outStroageIdTemp,
                        String.valueOf(count), beginno, endno, noSegId,
                        orderId, batchSql, "s");

                    /*
                     * if(batchSql!=null&&batchSql.length()>0){ SqlComDAO comDao =
                     * SrDAOFactory.getInstance().getSqlComDAO();
                     * comDao.excute(batchSql); // ����ִ�в���rc_order_list�ļ�¼ }
                     */

                    // �������
                    updateCond = qryCond;
                    count = dao.updateStorage(vo.getStorageId().trim(),
                            vo.getStorageName().trim(), updateCond);

                    result = "1";
                    info = "�ɹ�����" + count + "������!";
                } else {
                    result = "0";
                    info = "���������Ϊ0��������Ŷ����Ƿ����ڿ���õĺ���!";
                }
            }
        } else if ("2".equals(deliverType)) { // ���ļ��ϴ���ʽ���к������

            List noList = (List) map.get("noList");

            // ��������һ�������ѯsalesRescId��ֵ
            if (noList.size() > 0) {
                RcNoVO tempNO = dao.findByCode((String) noList.get(0));

                if (tempNO != null) {
                    salesRescId = tempNO.getSalesRescId();
                }

                if (!"".equals(vo.getRescLevel())) {
                    qryCond += (" and RESOURCE_LEVEL =" + vo.getRescLevel());
                }

                /*
                 * if (!"".equals(vo.getNoNumber())) { qryCond += " and rownum
                 * <=" + vo.getNoNumber(); }
                 */

                // ����rc_order����ΪҪ�������ĺ��룬���Ҫ�ȼ�¼��־
                this.insertOrderInfoNo("J", salesRescId, operId, departId,
                    vo.getStorageId().trim(), outStroageIdTemp,
                    String.valueOf(count), beginno, endno, noSegId, orderId,
                    null, "f");
                // ����rc_order_list
                fileUpload.batchFileUpload(orderId, vo.getStorageId().trim(),
                    vo.getStorageName().trim(), noList, qryCond, " rc_no ",
                    "insert");

                // ����rc_no�Ŀ��ID
                int res = fileUpload.batchFileUpload(orderId, vo.getStorageId().trim(),
                        vo.getStorageName().trim(), noList, qryCond, " rc_no ",
                        "update");
                result = "1";
                info = "����ɹ�����,���鿴�б�!";
            } else {
                result = "0";
                info = "����Ч����.";
            }
        } else if ("3".equals(deliverType)) { // ����ʼ��ֹ������к������

            if ((map.get("beginno") != null) && (map.get("endno") != null)) {
                beginno = (String) map.get("beginno");
                endno = (String) map.get("endno");

                if ((beginno != null) && (beginno.trim().length() > 0) &&
                        (endno != null) && (endno.trim().length() > 0)) {
                    String condTemp = " and resource_instance_code>='" +
                        DAOUtils.filterQureyCond(beginno).trim() +
                        "' and resource_instance_code<='" +
                        DAOUtils.filterQureyCond(endno).trim() + "'";

                    if (!"".equals(vo.getRescLevel())) {
                        condTemp += (" and RESOURCE_LEVEL =" +
                        vo.getRescLevel());
                    }

                    if (!"".equals(vo.getNoNumber())) {
                        condTemp += (" and rownum  <=" + vo.getNoNumber());
                    }

                    qryCond += condTemp;

                    List listTemp = dao.findByCond(qryCond);

                    if ((listTemp != null) && (listTemp.size() > 0)) {
                        // ��������һ�������ѯsalesRescId��ֵ
                        RcNoVO tempNO = dao.findByCode(beginno);

                        if (tempNO != null) {
                            salesRescId = tempNO.getSalesRescId();
                        }

                        // ����rc_order_list����־����ΪҪ�������ĺ��룬���Ҫ�ȼ�¼��־
                        batchSql += qryCond;
                        this.insertOrderInfoNo("J", salesRescId, operId,
                            departId, vo.getStorageId().trim(),
                            outStroageIdTemp, String.valueOf(count), beginno,
                            endno, noSegId, orderId, batchSql, "b");

                        // �������
                        updateCond = qryCond;
                        count = dao.updateStorage(vo.getStorageId().trim(),
                                vo.getStorageName().trim(), updateCond);
                        result = "1";
                        info = "�ɹ�����" + count + "������!";
                    } else {
                        result = "0";
                        info = "���������Ϊ0������������Ƿ����ڿ���õĺ���!";
                    }
                } else {
                    result = "0";
                    info = "ָ���ĺ����а����ĺ�����Ϊ0��������ʧ��!";
                }
            }
        } else if ("4".equals(deliverType)) { // ���������ʽ���к������

            if (vo.getNobagId() != null) {
                nobagId = vo.getNobagId();
                qryCond += (" and exists(select rc_nobag_no.NOBAG_ID from rc_nobag_no " +
                " where rc_nobag_no.RESOURCE_INSTANCE_ID=rc_no.RESOURCE_INSTANCE_ID " +
                " and rc_nobag_no.NOBAG_ID=" + nobagId + ")");

                if (!"".equals(vo.getRescLevel())) {
                    qryCond += (" and RESOURCE_LEVEL =" + vo.getRescLevel());
                }

                if (!"".equals(vo.getNoNumber())) {
                    qryCond += (" and rownum  <=" + vo.getNoNumber());
                }

                List listTemp = dao.findByCond(qryCond);

                if ((listTemp != null) && (listTemp.size() > 0)) {
                    // �ɹ�������ѯ�������salesRescId
                    List listTempNos = dao.findByCond(qryCond);

                    if ((listTempNos != null) && (listTempNos.size() > 0)) {
                        salesRescId = ((RcNoVO) listTempNos.get(0)).getSalesRescId();
                    }

                    // ����rc_order_list����־����ΪҪ�������ĺ��룬���Ҫ�ȼ�¼��־
                    batchSql += qryCond;
                    this.insertOrderInfoNo("J", salesRescId, operId, departId,
                        vo.getStorageId().trim(), outStroageIdTemp,
                        String.valueOf(count), beginno, endno, noSegId,
                        orderId, batchSql, "n");

                    // �������
                    updateCond = qryCond;
                    count = dao.updateStorage(vo.getStorageId().trim(),
                            vo.getStorageName().trim(), updateCond);
                    result = "1";
                    info = "�ɹ�����" + count + "������!";
                } else {
                    result = "0";
                    info = "���������Ϊ0���������������Ƿ����ڿ���õĺ���!";
                }
            }
        }

        Map rtnMap = new HashMap();
        rtnMap.put("result", result);
        rtnMap.put("info", info);
        rtnMap.put("orderId", orderId);

        return rtnMap;
    }

    /**
     * ���պ���,֧�ֶ������Ļ���,�������","����
     *
     * @param vo
     *            RcNoVO
     * @return
     */
    public Map recycleNo(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
        Map rtnMap = new HashMap();

        if ((map == null) || (map.get("pks") == null)) {
            rtnMap.put("result", "0");
            rtnMap.put("message", "ȱ�ٲ��������պ���ʧ��!");

            return rtnMap;
        }

        String pks = (String) map.get("pks");

        if ((pks == null) || (pks.trim().length() < 1)) {
            rtnMap.put("result", "0");
            rtnMap.put("message", "ȱ�ٲ��������պ���ʧ��!");

            return rtnMap;
        }

        List list1 = null;
        List list2 = null;
        List list3 = null;
        Map map1 = null;
        Map map2 = null;
        Map map3 = null;
        String defaultDayNum = "90";
        String recTime = null;
        String dayTime = null;
        String rescState = null;
        int successNum = 0;
        int failNum = 0;
        String result = "0";
        String message = "������ճ�����ȷ�������ǻ���״̬�Ҵﵽ�����������������ʧ��!";
        String cond = " RESOURCE_INSTANCE_ID=";
        String pk = null;
        String nowDate = DateFormatUtils.getFormatedDateTime();
        SqlComDAO dao = SrDAOFactory.getInstance().getSqlComDAO();
        RcNoDAO noDao = SrNSDAOFactory.getInstance().getRcNoDAO();

        if (pks.endsWith(",")) {
            pks = pks.substring(0, pks.length() - 1);
        }

        String sql1_ori = "select a.resource_instance_id,a.resource_instance_code,a.resource_state,a.rec_time,c.lan_id from rc_no a,rc_no_seg b,rc_no_group c " +
            " where a.no_seg_id=b.no_seg_id and b.no_group_id=c.no_group_id and a.resource_instance_id=";
        String sql1 = null;
        String[] arr1 = new String[] {
                "resource_instance_id", "resource_instance_code",
                "resource_state", "rec_time", "lan_id"
            };
        String sql2_ori = "select lan_id,day_num from rc_no_rec_rule where lan_id=";
        String sql2 = null;
        String[] arr2 = new String[] { "lan_id", "day_num" };
        String sql3 = "select CODEA,CODEB from dc_public where stype=94911 and PKEY='1' ";
        String[] arr3 = new String[] { "CODEB" };

        // �õ�Ĭ�ϵĻ�������
        list3 = dao.qryComSql(sql3, arr3);

        if ((list3 != null) && (list3.size() > 0)) {
            map3 = (Map) list3.get(0);

            if ((map3 != null) && (map3.get("CODEB") != null) &&
                    (((String) map3.get("CODEB")).trim().length() > 0)) {
                defaultDayNum = ((String) map3.get("CODEB")).trim();
            }
        }

        StringTokenizer token = new StringTokenizer(pks, ",");

        while (token.hasMoreTokens()) {
            pk = DAOUtils.filterQureyCond(token.nextToken());
            sql1 = sql1_ori + pk;
            list1 = dao.qryComSql(sql1, arr1);

            if ((list1 != null) && (list1.size() > 0)) {
                dayTime = defaultDayNum;
                map1 = (Map) list1.get(0);

                if ((map1 != null) && (map1.get("lan_id") != null) &&
                        (((String) map1.get("lan_id")).trim().length() > 0)) {
                    rescState = (String) map1.get("resource_state");
                    recTime = (String) map1.get("rec_time");
                    sql2 = sql2_ori + (String) map1.get("lan_id");
                    list2 = dao.qryComSql(sql2, arr2);

                    // ���û�������
                    if ((list2 != null) && (list2.size() > 0)) {
                        map2 = (Map) list2.get(0);

                        if ((map2 != null) && (map2.get("day_num") != null) &&
                                (((String) map2.get("day_num")).trim().length() > 0)) {
                            dayTime = (String) map2.get("day_num");
                        }
                    }

                    // ���պ���
                    if ((recTime != null) && (recTime.trim().length() > 0) &&
                            (rescState != null) &&
                            ParamsConsConfig.RescState_recycle.equals(rescState)) {
                        if (compareIntvDates(nowDate, recTime,
                                    Integer.parseInt(dayTime))) {
                            long oper_num = noDao.updateRescState(ParamsConsConfig.RcEntityFreeState,
                                    cond + pk);

                            if (oper_num > 0) {
                                result = "1";
                                successNum++;
                                message = "������ճɹ�!";
                            }
                        } else {
                            failNum++;
                        }
                    } else {
                        message = "<info:begin>����" +
                            map1.get("resource_instance_code") +
                            "���ճ���,����״̬���ǻ���״̬���߻���ʱ����Ч<info:end>";
                        throw new LogicInfoException(message);
                    }
                }
            }
        }

        message = "���ɹ�����" + successNum + "�����ڻ��յĺ���";

        if (failNum > 0) {
            message += ("������" + failNum + "������δ��������");
        }

        rtnMap.put("result", result);
        rtnMap.put("message", message);

        return rtnMap;
    }

    /**
     * ʱ��date1��ȥʱ��date2�������Ƿ���ڵ���ָ��������days
     *
     * @param date1
     *            String
     * @param date2
     *            String
     * @param days
     *            long
     * @return boolean
     */
    private boolean compareIntvDates(String date1, String date2, int days) {
        String date_temp = DateFormatUtils.minusDate(date1,
                CrmConstants.DATE_TIME_FORMAT, days, DateFormatUtils.DAY);
        int nums = DateFormatUtils.compareDate(date_temp, date2,
                CrmConstants.DATE_TIME_FORMAT);

        if (nums >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * �������
     *
     * @param map
     *            Map
     * @return Map
     */
    public Map freezeNos(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
        long count = 0;
        String result = "1";
        String info = "";
        String qryCond = null;
        String updateCond = null;
        String noNotIn = "";
        String noWhyNotIn = "";
        Map rtnMap = new HashMap();

        if ((map == null) || (map.get("deliverType") == null) ||
                (map.get("No") == null)) {
            result = "0";
            info = "ȱ�ٲ�������!";
        }

        String noSegId = (String) map.get("noSegId");
        String rescLevel = (String) map.get("rescLevel");
        String noNumber = (String) map.get("noNumber");
        String storageId = (String) map.get("storageId");
        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        String deliverType = ((String) map.get("deliverType")).trim();

        if ("1".equals(deliverType)) { // �Ŷζ���

            if ((noSegId != null) && !"".equals(noSegId)) {
                qryCond = " NO_SEG_ID=" + noSegId + " and STATE='" +
                    ParamsConsConfig.RcEntityStateValide + "'" +
                    " and RESOURCE_STATE ='" +
                    ParamsConsConfig.RcEntityFreeState + "'";

                if ((rescLevel != null) && (rescLevel.length() > 0)) {
                    qryCond += (" and RESOURCE_LEVEL =" + rescLevel);
                }

                qryCond += (" and storage_id=" + storageId);

                List qryList = dao.findByCond(qryCond);

                if ((qryList != null) && (qryList.size() == 0)) {
                    result = "0";
                    info = "�ú��������ʧЧ״̬���߲��ǿ���״̬�ĺ���,���ߵȼ�����,����ʧ��!";
                } else if ((noNumber != null) && !"".equals(noNumber) &&
                        (qryList.size() <= Integer.parseInt(noNumber))) {
                    updateCond = " NO_SEG_ID=" + noSegId;
                    updateCond += (" and storage_id=" + storageId);

                    if ((rescLevel != null) && (rescLevel.length() > 0)) {
                        updateCond += (" and RESOURCE_LEVEL =" + rescLevel);
                    }

                    updateCond += (" and resource_state ='" +
                    ParamsConsConfig.RcEntityFreeState + "'");
                    count = dao.updateRescState(ParamsConsConfig.RescState_frozen,
                            updateCond);

                    if (count > 0) {
                        result = "0";
                        info = "�ɹ�����" + count + "������!";
                    } else {
                        result = "0";
                        info = "���������Ϊ0��������Ŷ����Ƿ���ں���!";
                    }
                } else if ((noNumber != null) && !"".equals(noNumber) &&
                        (qryList.size() > Integer.parseInt(noNumber))) {
                    updateCond = " NO_SEG_ID=" + noSegId;
                    updateCond += (" and storage_id=" + storageId);

                    if ((rescLevel != null) && (rescLevel.length() > 0)) {
                        updateCond += (" and RESOURCE_LEVEL =" + rescLevel);
                    }

                    updateCond += (" and rownum <=" + noNumber +
                    " and resource_state ='" +
                    ParamsConsConfig.RcEntityFreeState + "'");

                    count = dao.updateRescState(ParamsConsConfig.RescState_frozen,
                            updateCond);

                    if (count > 0) {
                        result = "0";
                        info = "�ɹ�����" + count + "������!";
                    } else {
                        result = "0";
                        info = "���������Ϊ0��������Ŷ����Ƿ���ں���!";
                    }
                }
            }
        } else if ("2".equals(deliverType)) { // �ļ����붳��

            if (map.get("nos") != null) {
                String nos = (String) map.get("nos");

                if ((nos != null) && (nos.trim().length() > 0)) {
                    if (nos.endsWith(",")) {
                        nos = nos.substring(0, nos.length() - 1);
                    }

                    qryCond = "  RESOURCE_INSTANCE_CODE in (" + nos +
                        ") and STATE='" + ParamsConsConfig.RcEntityStateValide +
                        "'" + " and RESOURCE_STATE='" +
                        ParamsConsConfig.RcEntityFreeState + "'";

                    if ((rescLevel != null) && (rescLevel.length() > 0)) {
                        qryCond += (" and RESOURCE_LEVEL =" + rescLevel);
                    }

                    qryCond += (" and storage_id=" + storageId);

                    String[] arrayValue = nos.split(",");
                    long lCount = 0;
                    long lState = 0;
                    lCount = dao.countByCond(qryCond); // �жϺ����Ƿ����,�������в��ֲ���������.

                    if (lCount != arrayValue.length) {
                        for (int i = 0; i < arrayValue.length; i++) {
                            noNotIn = " RESOURCE_INSTANCE_CODE = " +
                                arrayValue[i].trim() + " and STATE ='" +
                                ParamsConsConfig.RcEntityStateValide + "'";
                            lCount = dao.countByCond(noNotIn);
                            noWhyNotIn = " RESOURCE_INSTANCE_CODE = " +
                                arrayValue[i].trim() +
                                " and RESOURCE_STATE ='" +
                                ParamsConsConfig.RcEntityFreeState + "'";

                            if ((rescLevel != null) &&
                                    (rescLevel.length() > 0)) {
                                noWhyNotIn += (" and RESOURCE_LEVEL =" +
                                rescLevel);
                            }

                            noWhyNotIn += (" and storage_id=" + storageId);
                            lState = dao.countByCond(noWhyNotIn);

                            if (lCount == 0) {
                                result = "0";
                                info = "����: " +
                                    arrayValue[i].substring(1,
                                        arrayValue[i].length() - 1) +
                                    " �����ڻ�����ʧЧ,���ߵȼ�����,����ʧ��!";
                                rtnMap.put("result", result);
                                rtnMap.put("info", info);

                                return rtnMap;
                            }

                            if (lState == 0) {
                                result = "0";
                                info = "����: " +
                                    arrayValue[i].substring(1,
                                        arrayValue[i].length() - 1) +
                                    " ״̬���ǿ���״̬,���ߵȼ�����,����ʧ��!";
                                rtnMap.put("result", result);
                                rtnMap.put("info", info);

                                return rtnMap;
                            }
                        }
                    }

                    updateCond = " RESOURCE_INSTANCE_CODE in (" + nos + ")";
                    updateCond += (" and storage_id=" + storageId);

                    if ((rescLevel != null) && (rescLevel.length() > 0)) {
                        updateCond += (" and RESOURCE_LEVEL =" + rescLevel);
                    }

                    count = dao.updateRescState(ParamsConsConfig.RescState_frozen,
                            updateCond);

                    if (count > 0) {
                        result = "0";
                        info = "�ɹ�����" + count + "������!";
                    } else {
                        result = "0";
                        info = "���������Ϊ0���������ϴ��ĺ����Ƿ񶼴���!";
                    }
                } else {
                    result = "0";
                    info = "�ϴ��ļ��а����ĺ�����Ϊ0������ʧ��!";
                }
            }
        } else if ("3".equals(deliverType)) {
            if ((map.get("beginno") != null) && (map.get("endno") != null)) {
                String beginno = (String) map.get("beginno");
                String endno = (String) map.get("endno");

                if ((beginno != null) && (beginno.trim().length() > 0) &&
                        (endno != null) && (endno.trim().length() > 0)) {
                    qryCond = "  resource_instance_code>='" +
                        DAOUtils.filterQureyCond(beginno) +
                        "' and resource_instance_code<='" +
                        DAOUtils.filterQureyCond(endno) + "'";
                    qryCond += (" and STATE ='" +
                    ParamsConsConfig.RcEntityStateValide + "'" +
                    " and RESOURCE_STATE ='" +
                    ParamsConsConfig.RcEntityFreeState + "'");
                    qryCond += (" and storage_id=" + storageId);

                    if ((rescLevel != null) && (rescLevel.length() > 0)) {
                        qryCond += (" and RESOURCE_LEVEL =" + rescLevel);
                    }

                    List qryList = dao.findByCond(qryCond);

                    if ((qryList != null) && (qryList.size() == 0)) {
                        result = "0";
                        info = "ָ���ĺ�������ʧЧ״̬,����ȼ������ϻ��߲��ǿ���״̬�ĺ��룬����ʧ��!";
                    } else if ((noNumber != null) && !"".equals(noNumber) &&
                            (qryList.size() <= Integer.parseInt(noNumber))) {
                        updateCond = qryCond;
                        updateCond += (" and resource_state ='" +
                        ParamsConsConfig.RcEntityFreeState + "'");
                        count = dao.updateRescState(ParamsConsConfig.RescState_frozen,
                                updateCond);

                        if (count > 0) {
                            result = "0";
                            info = "�ɹ�����" + count + "������!";
                        }
                    } else if ((noNumber != null) && !"".equals(noNumber) &&
                            (qryList.size() > Integer.parseInt(noNumber))) {
                        updateCond = qryCond;
                        updateCond += (" and rownum<=" + noNumber +
                        " and resource_state ='" +
                        ParamsConsConfig.RcEntityFreeState + "'");
                        count = dao.updateRescState(ParamsConsConfig.RescState_frozen,
                                updateCond);

                        if (count > 0) {
                            result = "0";
                            info = "�ɹ�����" + count + "������!";
                        }
                    }
                } else {
                    result = "0";
                    info = "ָ���ĺ����а����ĺ�����Ϊ0������ʧ��!";
                }
            }
        }

        rtnMap.put("result", result);
        rtnMap.put("info", info);

        return rtnMap;
    }

    /**
     * �ⶳ����
     *
     * @param map
     *            Map
     * @return Map
     */
    public Map deFreezeNos(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
        long count = 0;
        String result = "1";
        String info = "";
        String qryCond = null;
        String updateCond = null;
        String noNotIn = "";
        Map rtnMap = new HashMap();

        if ((map == null) || (map.get("deliverType") == null) ||
                (map.get("No") == null)) {
            result = "0";
            info = "ȱ�ٲ�������!";
        }

        String noSegId = (String) map.get("noSegId");
        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        String deliverType = ((String) map.get("deliverType")).trim();

        if ("1".equals(deliverType)) {
            if ((noSegId != null) && !"".equals(noSegId)) {
                qryCond = " NO_SEG_ID=" + noSegId + " and (STATE!='" +
                    ParamsConsConfig.RcEntityStateValide + "'" +
                    " or RESOURCE_STATE!='" +
                    ParamsConsConfig.RescState_frozen + "')";

                List qryList = dao.findByCond(qryCond);

                if ((qryList != null) && (qryList.size() > 0)) {
                    result = "0";
                    info = "������д���ʧЧ������Ƕ���״̬�ĺ��룬�ⶳʧ��!";
                } else {
                    updateCond = " NO_SEG_ID=" + noSegId;
                    count = dao.updateRescState(ParamsConsConfig.RcEntityFreeState,
                            updateCond);

                    if (count > 0) {
                        result = "1";
                        info = "�ɹ��ⶳ" + count + "������!";
                    } else {
                        result = "0";
                        info = "�ⶳ������Ϊ0��������Ŷ����Ƿ���ں���!";
                    }
                }
            }
        } else if ("2".equals(deliverType)) {
            if (map.get("nos") != null) {
                String nos = (String) map.get("nos");

                if ((nos != null) && (nos.trim().length() > 0)) {
                    if (nos.endsWith(",")) {
                        nos = nos.substring(0, nos.length() - 1);
                    }

                    qryCond = "  RESOURCE_INSTANCE_CODE in (" + nos +
                        ") and STATE='" + ParamsConsConfig.RcEntityStateValide +
                        "'" + " and RESOURCE_STATE='" +
                        ParamsConsConfig.RescState_frozen + "'";

                    String[] arrayValue = nos.split(",");
                    long lCount = 0;
                    lCount = dao.countByCond(qryCond); // �жϺ����Ƿ����

                    if (lCount != arrayValue.length) {
                        for (int i = 0; i < arrayValue.length; i++) {
                            noNotIn = " RESOURCE_INSTANCE_CODE = " +
                                arrayValue[i] + " and STATE ='" +
                                ParamsConsConfig.RcEntityStateValide + "'" +
                                " and RESOURCE_STATE ='" +
                                ParamsConsConfig.RescState_frozen + "'";
                            lCount = dao.countByCond(noNotIn);

                            if (lCount == 0) {
                                result = "0";
                                info = "����: " +
                                    arrayValue[i].substring(1,
                                        arrayValue[i].length() - 1) +
                                    " �����ڻ�����ʧЧ���Ƕ���״̬,�ⶳʧ��!";
                                rtnMap.put("result", result);
                                rtnMap.put("info", info);

                                return rtnMap;
                            }
                        }
                    } else { // ����ĺ��붼�ǿ��Խⶳ��
                        updateCond = " RESOURCE_INSTANCE_CODE in (" + nos +
                            ")";
                        count = dao.updateRescState(ParamsConsConfig.RcEntityFreeState,
                                updateCond);

                        if (count > 0) {
                            result = "1";
                            info = "�ɹ��ⶳ" + count + "������!";
                        } else {
                            result = "0";
                            info = "�ⶳ������Ϊ0���������ϴ��ĺ����Ƿ񶼴���!";
                        }
                    }
                } else {
                    result = "0";
                    info = "�ϴ��ļ��а����ĺ�����Ϊ0���ⶳʧ��!";
                }
            }
        } else if ("3".equals(deliverType)) {
            if ((map.get("beginno") != null) && (map.get("endno") != null)) {
                String beginno = (String) map.get("beginno");
                String endno = (String) map.get("endno");

                if ((beginno != null) && (beginno.trim().length() > 0) &&
                        (endno != null) && (endno.trim().length() > 0)) {
                    String condTemp = "  resource_instance_code>='" +
                        DAOUtils.filterQureyCond(beginno) +
                        "' and resource_instance_code<='" +
                        DAOUtils.filterQureyCond(endno) + "'";
                    qryCond = condTemp + " and (STATE!='" +
                        ParamsConsConfig.RcEntityStateValide + "'" +
                        " or RESOURCE_STATE!='" +
                        ParamsConsConfig.RescState_frozen + "')";

                    List qryList = dao.findByCond(qryCond);

                    if ((qryList != null) && (qryList.size() > 0)) {
                        result = "0";
                        info = "ָ���ĺ�������ʧЧ������߲��Ƕ���״̬�ĺ��룬�ⶳʧ��!";
                    } else {
                        updateCond = condTemp;
                        count = dao.updateRescState(ParamsConsConfig.RcEntityFreeState,
                                updateCond);
                        result = "1";
                        info = "����ⶳ�ɹ�!";
                    }
                } else {
                    result = "0";
                    info = "ָ���ĺ����а����ĺ�����Ϊ0���ⶳʧ��!";
                }
            }
        }

        rtnMap.put("result", result);
        rtnMap.put("info", info);

        return rtnMap;
    }

    private boolean getRealCfg(String lanId, String tableName) {
        RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();

        return dao.getRealCfg(lanId, tableName);
    }

    /**
     * ����������ѯ�����sim����ʵ��������Ŀǰ���Խ��ܵĲ����У�deptID��rescState��state��tableName
     *
     * @param deptID:������id1,id2,id3����ʽ
     * @return
     */
    public PageModel satNoSimStockNum(Map map, int pi, int ps) {
        String databaseType = CrmParamsConfig.getInstance()
                                             .getParamValue("DATABASE_TYPE");
        PageModel pm = new PageModel();
        String cond0 = "";
        String cond1 = "";
        String cond2 = "";
        String cond_right = "";
        String provId = "";
        String lanId = "";
        String familyId = null;
        String tableName = null;
        String operId = null;
        String regionId = null;
        RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();
        String departId = null;
        String rescState = null;
        String rescLevel = null;
        String balaMode = null;
        String state = ParamsConsConfig.RcEntityStateValide;
        String startDate = null;
        String endDate = null;
        String upStorageId = null;
        String storageId = null;

        //		String provId=null;
        if (map != null) {
            if ((map.get("departId") != null) &&
                    (((String) map.get("departId")).trim().length() > 0)) {
                departId = (String) map.get("departId");
            }

            if ((map.get("lanId") != null) &&
                    (((String) map.get("lanId")).trim().length() > 0)) {
                lanId = (String) map.get("lanId");
            }

            if ((map.get("state") != null) &&
                    (((String) map.get("state")).trim().length() > 0)) {
                state = (String) map.get("state");
            }

            if ((map.get("tableName") != null) &&
                    (((String) map.get("tableName")).trim().length() > 0)) {
                tableName = (String) map.get("tableName");
            }

            if ((map.get("operId") != null) &&
                    (((String) map.get("operId")).trim().length() > 0)) {
                operId = (String) map.get("operId");
            }

            if ((map.get("regionId") != null //������������ʵ����lanId
                ) && (((String) map.get("regionId")).trim().length() > 0)) {
                regionId = (String) map.get("regionId");
            }

            if ((map.get("rescState") != null) &&
                    (((String) map.get("rescState")).trim().length() > 0)) {
                rescState = (String) map.get("rescState");
            }

            if ((map.get("rescLevel") != null) &&
                    (((String) map.get("rescLevel")).trim().length() > 0)) {
                rescLevel = (String) map.get("rescLevel");
            }

            if ((map.get("balaMode") != null) &&
                    (((String) map.get("balaMode")).trim().length() > 0)) {
                balaMode = (String) map.get("balaMode");
            }

            /*if (map.get("startDate") != null
                            && ((String) map.get("startDate")).trim().length() > 0)
                    startDate = (String) map.get("startDate");
            if (map.get("endDate") != null
                            && ((String) map.get("endDate")).trim().length() > 0)
                    endDate = (String) map.get("endDate");*/
            if (map.get("provId") != null) {
                provId = String.valueOf(map.get("provId")).trim();
            }

            if ((map.get("upStorageId") != null) &&
                    (((String) map.get("upStorageId")).trim().length() > 0)) {
                upStorageId = (String) map.get("upStorageId");
            }

            if ((map.get("storageId") != null) &&
                    (((String) map.get("storageId")).trim().length() > 0)) {
                storageId = (String) map.get("storageId");
            }
        }

        if ((tableName == null) || (tableName.trim().length() < 1)) {
            return pm;
        }

        if ((regionId != null) && regionId.endsWith(",")) {
            regionId = regionId.substring(0, regionId.length() - 1);
        }

        if ((storageId != null) && storageId.endsWith(",")) {
            storageId = storageId.substring(0, storageId.length() - 1);
        }

        if ((departId != null) && departId.endsWith(",")) {
            departId = departId.substring(0, departId.length() - 1);
        }

        // ��ѯrc_rp_real_cfg���ñ�,
        if (getRealCfg(lanId, tableName)) { // ���ش򿪲�ѯ���ܱ�.

            String sql = "";
            String sql_count = "";
            String sql_cond = "";

            if ("rc_no".equals(tableName)) {
                sql = "select a.storage_id,a.storage_name,sum(a.st_count) as stock_amount,a.resource_state,'' as UP_LIMIT,'' as DOWN_LIMIT,'' as sales_resource_id,'' as sales_resource_name," +
                    "'' as depart_id  from  rc_rp_no_count a";
                sql_count = "select a.storage_id from rc_rp_no_count a ";
            } else if ("rc_sim".equals(tableName)) {
                sql = "select a.storage_id,a.storage_name,a.st_count as stock_amount,a.resource_state,a.UP_LIMIT,a.DOWN_LIMIT,null as sales_resource_id,'' as sales_resource_name," +
                    "'' as depart_id  from  rc_rp_sim_count a";
                sql_count = "select a.storage_id from rc_rp_sim_count a ";
            } else {
                return pm;
            }

            if (!"".equals(upStorageId) && (upStorageId != null)) {
                sql += (" inner join rc_storage b on  ( b.UP_STORAGE_ID = " +
                upStorageId +
                " and b.storage_id = a.storage_id and b.rc_type=-1 )");
            }

            if ("rc_no".equals(tableName) && "20".equals(provId)) {
            } else { //�����ĺ������̵㲻�ü���һ��RC-296 (ID:41564)

                if ((storageId != null) && (storageId.trim().length() > 0)) { // ������洫�����ѡ��Ĳֿ⣬��ֱ���òֿ��ѯ
                    sql_cond += (" and a.storage_id in(" + storageId + ") ");
                } else {
                    if ((departId != null) && !"".equals(departId)) {
                        cond2 += (" and exists( select e.storage_id from STORAGE_DEPART_RELA e " +
                        " where e.storage_id=a.storage_id and e.DEPART_ID in (" +
                        departId + " ) union all " +
                        " select y.storage_id from mp_storage y where a.storage_id=y.storage_id " +
                        " and y.oper_id=" + operId + " )");
                    } else {
                        cond2 = " and (exists(select e.storage_id from STORAGE_DEPART_RELA e,mp_operator_depart f " +
                            " where e.storage_id=a.storage_id and e.DEPART_ID=f.DEPART_ID and f.oper_id=" +
                            operId + " and f.lan_id=" + lanId;

                        if (!"".equals(regionId)) {
                            cond2 += (" and f.REGION_ID in (" + regionId +
                            " )");
                        }

                        cond2 += (") or " +
                        " exists(select 1 from mp_storage g where g.storage_id=a.storage_id and g.STATE='00' and g.OPER_ID=" +
                        operId + ")" + ")");
                    }
                }
            }

            sql += " where 1=1 ";
            sql_count += " where 1=1 ";

            if ((rescState != null) && (rescState.trim().length() > 0)) {
                sql_cond += (" and a.resource_state='" + rescState + "'");
            }

            if ("rc_no".equals(tableName)) { //�������̵�����

                if ((rescLevel != null) && (rescLevel.trim().length() > 0)) {
                    sql_cond += (" and a.RESOURCE_LEVEL=" + rescLevel);
                }

                if ((balaMode != null) && (balaMode.trim().length() > 0)) {
                    sql_cond += (" and a.BALA_MODE='" + balaMode + "' ");
                }
            }

            sql_cond += cond2;

            if ("rc_no".equals(tableName)) {
                sql_cond += " group by a.resource_state,a.storage_id,a.storage_name";
            }

            sql += sql_cond;
            sql_count = "select count(1) as COL_COUNTS from (" + sql_count +
                sql_cond + ") z";
            // ����Ҫ��ѯ��������ѯ���
            dao.setSQL_SELECT(sql);
            dao.setSQL_SELECT_COUNT(sql_count);
            dao.setFlag(2); // ����Ҫ��ѯ�ֿ�ģ��������޺���Դ״̬
            pm = PageHelper.popupPageModel(dao, "", pi, ps);
        } else {
            // start ��װ��ѯ����,ʵʱ��ѯ
            if ((storageId != null) && (storageId.trim().length() > 0)) { // ������洫�����ѡ��Ĳֿ⣬��ֱ���òֿ��ѯ
                cond_right += (" and a.storage_id in(" + storageId + ") ");
            } else {
                if ("rc_no".equals(tableName) && "20".equals(provId)) {
                } else { //�����ĺ������̵㲻�ü���һ��RC-296 (ID:41564)

                    if ((departId != null) && (departId.trim().length() > 0)) {
                        cond1 += (" and b.depart_id in (" + departId + ")");

                        if ("INFORMIX".equals(databaseType)) {
                            cond_right = " and exists (table(MULTISET(select * from STORAGE_DEPART_RELA b where a.storage_id=b.storage_id " +
                                cond1 + ")) union all table(MULTISET(" +
                                " select y.storage_id from mp_storage y where a.storage_id=y.storage_id " +
                                " and y.oper_id=" + operId + "))) ";
                        } else {
                            cond_right = " and exists (select b.storage_id from STORAGE_DEPART_RELA b where a.storage_id=b.storage_id " +
                                cond1 +
                                " union all select y.storage_id from mp_storage y where a.storage_id=y.storage_id " +
                                " and y.oper_id=" + operId + " ) ";
                        }
                    } else if ((operId != null) &&
                            (operId.trim().length() > 0) && (regionId != null) &&
                            (regionId.trim().length() > 0)) {
                        cond2 = " (exists(select e.storage_id from STORAGE_DEPART_RELA e,mp_operator_depart f " +
                            " where e.storage_id=a.storage_id and e.DEPART_ID=f.DEPART_ID and f.oper_id=" +
                            operId + " and f.REGION_ID in(" + regionId +
                            ")) or " +
                            " exists(select 1 from mp_storage g where g.storage_id=a.storage_id and g.STATE='00'" +
                            " and g.OPER_ID=" + operId + "))";

                        if ("INFORMIX".equals(databaseType)) {
                            cond2 = " exists table(MULTISET(select * from STORAGE_DEPART_RELA e,mp_operator_depart f where e.DEPART_ID=f.DEPART_ID and a.storage_id=e.storage_id and f.REGION_ID =" +
                                regionId + " and f.oper_id=" + operId + " )) ";
                        }

                        cond_right = " and " + cond2;
                    }
                }
            }

            if ((rescState != null) && (rescState.trim().length() > 0)) {
                cond0 = " and r.resource_state='" + rescState + "'";
            }

            // �Ƿ���Ҫ��Ӳֿ�����¼���ϵ
            String storage_cond = "";

            if ((upStorageId != null) && (upStorageId.trim().length() > 0)) {
                storage_cond = " and c.UP_STORAGE_ID = " + upStorageId + " ";
            }

            // ��װ��ʼ����ʱ��
            if ("INFORMIX".equals(databaseType)) {
                if ((startDate != null) && !"".equals(startDate)) {
                    cond0 += ("  and r.INIT_TIME>=to_date('" + startDate +
                    "','%Y-%m-%d')  ");
                }

                if ((endDate != null) && !"".equals(endDate)) {
                    cond0 += ("  and r.INIT_TIME<=to_date('" + endDate +
                    " 23:59:59','%Y-%m-%d %H:%M:%S')  ");
                }
            } else {
                if ((startDate != null) && !"".equals(startDate)) {
                    cond0 += ("  and r.INIT_TIME>=to_date('" + startDate +
                    "','yyyy-mm-dd')  ");
                }

                if ((endDate != null) && !"".equals(endDate)) {
                    cond0 += ("  and r.INIT_TIME<=to_date('" + endDate +
                    " 23:59:59','yyyy-mm-dd hh24:mi:ss')  ");
                }
            }

            // end ��װ��ѯ����
            // ��ѯ��Դ��Ӧ�ļ���id
            String attrCode = null;
            RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();

            if ("rc_no".equalsIgnoreCase(tableName)) {
                attrCode = "Rc_No_FamilyId";

                if ((rescLevel != null) && (rescLevel.trim().length() > 0)) {
                    cond0 += (" and r.RESOURCE_LEVEL=" + rescLevel);
                }

                if ((balaMode != null) && (balaMode.trim().length() > 0)) {
                    cond0 += (" and r.BALA_MODE='" + balaMode + "' ");
                }
            } else if ("rc_sim".equalsIgnoreCase(tableName)) {
                attrCode = "Rc_SIM_FamilyId";
            }

            if (attrCode != null) {
                familyId = segDao.findFamilyId(attrCode);
            }

            if ((familyId == null) || (familyId.trim().length() < 1)) {
                return pm;
            }

            // ��sql���
            String sql_oracle = "select c.storage_id,c.storage_name,a.stock_amount,a.resource_state,d.UP_LIMIT,d.DOWN_LIMIT,null as sales_resource_id,'' as sales_resource_name,'" +
                departId + "' as depart_id " +
                " from (select storage_id,resource_state,count(resource_instance_id) as stock_amount from " +
                tableName + " r" + " where r.state = '" + state + "' " + cond0 +
                " group by storage_id,resource_state) a " +
                " inner join rc_storage c on (a.storage_id=c.storage_id ) " +
                " left outer join rc_stock_limit d on(a.storage_id = d.storage_id and d.family_id=" +
                familyId + ") " + " where c.rc_type=-1 " + storage_cond +
                cond_right;

            String sql_oracle_count = "select count(1) as COL_COUNTS " +
                " from (select storage_id,resource_state,count(resource_instance_id) as stock_amount from " +
                tableName + " r" + " where r.state = '" + state + "' " + cond0 +
                " group by storage_id,resource_state) a " +
                " inner join rc_storage c on (a.storage_id=c.storage_id ) " +
                " left outer join rc_stock_limit d on(a.storage_id = d.storage_id and d.family_id=" +
                familyId + ") " + " where c.rc_type=-1 " + storage_cond +
                cond_right;

            if ("rc_no".equals(tableName) && "20".equals(provId)) { //�����ĺ�����
                                                                    // ��sql���
                sql_oracle = "select a.lanid as lan_id,c.storage_id,c.storage_name,a.resource_level,a.stock_amount,a.resource_state,d.UP_LIMIT,d.DOWN_LIMIT,null as sales_resource_id,'' as sales_resource_name,'" +
                    departId + "' as depart_id " +
                    " from (select storage_id,resource_state,count(resource_instance_id) as stock_amount , f.lan_id as lanid,r.resource_level as resource_level from " +
                    tableName + "  r,rc_no_seg g, rc_no_group f" +
                    " where r.state = '" + state + "' " + cond0 +
                    " and r.no_seg_id = g.no_seg_id" +
                    " and g.no_group_id = f.no_group_id";

                if ((regionId != null) && (regionId.length() > 0)) {
                    sql_oracle += (" and f.lan_id='" + regionId + "'");
                }

                sql_oracle += (" group by f.lan_id,storage_id,resource_state,r.resource_level) a " +
                " inner join rc_storage c on (a.storage_id=c.storage_id ) " +
                " left outer join rc_stock_limit d on(a.storage_id = d.storage_id and d.family_id=" +
                familyId + ") " + " where c.rc_type=-1 " + storage_cond +
                cond_right);

                sql_oracle_count = "select count(1) as COL_COUNTS " +
                    " from (select storage_id,resource_state,count(resource_instance_id) as stock_amount , f.lan_id as lanid ,r.resource_level as resource_level from " +
                    tableName + "  r,rc_no_seg g, rc_no_group f" +
                    " where r.state = '" + state + "' " + cond0 +
                    " and r.no_seg_id = g.no_seg_id" +
                    " and g.no_group_id = f.no_group_id";

                if ((regionId != null) && (regionId.length() > 0)) {
                    sql_oracle_count += (" and f.lan_id='" + regionId + "'");
                }

                sql_oracle_count += (" group by f.lan_id,storage_id,resource_state,r.resource_level) a " +
                " inner join rc_storage c on (a.storage_id=c.storage_id ) " +
                " left outer join rc_stock_limit d on(a.storage_id = d.storage_id and d.family_id=" +
                familyId + ") " + " where c.rc_type=-1 " + storage_cond +
                cond_right);
            }

            String sql_informix = "select c.storage_id,c.storage_name,a.stock_amount,a.resource_state,d.UP_LIMIT,d.DOWN_LIMIT,null as sales_resource_id,'' as sales_resource_name,'" +
                departId + "' as depart_id " +
                " from table(MULTISET(select storage_id,resource_state,count(resource_instance_id) as stock_amount from " +
                tableName + " r" + " where r.state = '" + state + "' " + cond0 +
                " group by storage_id,resource_state)) a " +
                " inner join rc_storage c on (a.storage_id=c.storage_id ) " +
                " left outer join rc_stock_limit d on(a.storage_id = d.storage_id and d.family_id=" +
                familyId + ") " + " where c.rc_type=-1 " + storage_cond +
                cond_right;

            String sql = sql_oracle;
            String sql_count = sql_oracle_count;

            if ("INFORMIX".equals(databaseType)) {
                sql = sql_informix;
            }

            System.out.println("��SrStockBo,����satNoStockNum��ִ�е�sql��:" + sql);

            // ����Ҫ��ѯ��������ѯ���
            dao.setSQL_SELECT(sql);
            dao.setSQL_SELECT_COUNT(sql_count);
            dao.setFlag(2); // ����Ҫ��ѯ�ֿ�ģ��������޺���Դ״̬

            if ("rc_no".equals(tableName) && "20".equals(provId)) {
                dao.setFlag2(1);
            }

            pm = PageHelper.popupPageModel(dao, "", pi, ps);
        }

        return pm;
    }

    public String addRcNoPre(RnNumberPreVO vo) {
        String flag = "0";
        vo.setNoType("c");

        RnNumberPreDAO dao = SrNSDAOFactory.getInstance().getRnNumberPreDAO();
        RcNoDAO pdao = SrNSDAOFactory.getInstance().getRcNoDAO();
        String whereCond = " RESOURCE_INSTANCE_ID = " + vo.getRescInstanceId();
        dao.deleteByCond(whereCond);
        dao.insert(vo);
        pdao.updateRescState(ParamsConsConfig.RescState_preSelect, whereCond);
        flag = "1";

        return flag;
    }

    /**
     * ��ѯ�����sim���Ķ���
     *
     * @param map
     *            Map
     * @return List
     */
    public List qryOrderNo(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
        if (map == null) {
            map = new HashMap();
        }

        List list = null;
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        RcOrderDAO orderDao = SrDAOFactory.getInstance().getRcOrderDAO();
        String familyId = segDao.findNoFamilyId();
        map.put("familyId", familyId);
        map.put("appType", "C");
        list = orderDao.qryOrderNoSim(map);

        if (list == null) {
            list = new ArrayList();
        }

        return list;
    }

    /**
     * ɾ�������ĺ����sim��
     *
     * @param map
     *            Map
     * @return Map
     */
//    public Map delNoSimOrder(Map map) {
    public Map delNoSimOrder(DynamicDict dto) throws FrameException {
         
        Map map = (Map)dto.getValueByName("parameter");
        
        if (map == null) {
            return null;
        }

        String tableName = null;
        String orderId = null;
        String salesRescId = null;
        String resBCode = null;
        String resECode = null;
        String storageId = null;
        String operId = null;
        String actAmount = null;
        String departId = null;

        if (map.get("tableName") != null) {
            tableName = map.get("tableName").toString();
        }

        if (map.get("orderId") != null) {
            orderId = map.get("orderId").toString();
        }

        if (map.get("salesRescId") != null) {
            salesRescId = map.get("salesRescId").toString();
        }

        if (map.get("resBCode") != null) {
            resBCode = map.get("resBCode").toString();
        }

        if (map.get("resECode") != null) {
            resECode = map.get("resECode").toString();
        }

        if (map.get("storageId") != null) {
            storageId = map.get("storageId").toString();
        }

        if (map.get("operId") != null) {
            operId = map.get("operId").toString();
        }

        if (map.get("actAmount") != null) {
            actAmount = map.get("actAmount").toString();
        }

        if (map.get("departId") != null) {
            departId = map.get("departId").toString();
        }

        if ((tableName == null) || (tableName.trim().length() < 1) ||
                (orderId == null) || (orderId.trim().length() < 1) ||
                (salesRescId == null) || (salesRescId.trim().length() < 1) ||
                (storageId == null) || (storageId.trim().length() < 1) ||
                (operId == null) || (operId.trim().length() < 1) ||
                (actAmount == null) || (actAmount.trim().length() < 1) ||
                (departId == null) || (departId.trim().length() < 1)) {
            return null;
        }

        Map retMap = new HashMap();
        String result = "1"; // ��������ɹ�
        String message = "";
        SqlComDAO comDao = SrDAOFactory.getInstance().getSqlComDAO();

        // ����Ƿ���Ȩ�޲����òֿ�
        String sql_right = " select count(a.storage_id) as COL_COUNTS from rc_storage a where a.storage_id=" +
            storageId +
            " and (exists (select 1 from mp_storage b where a.storage_id=b.storage_id and " +
            " b.oper_id=" + operId + ") or " +
            " exists(select 1 from STORAGE_DEPART_RELA c where c.storage_Id=a.storage_id " +
            " and c.depart_id=" + departId + ")) ";
        long storage_num = comDao.count(sql_right);

        if (storage_num < 1) {
            result = "2"; // ����Աû��Ȩ�޲����òֿ�
            retMap.put("result", result);

            return retMap;
        }

        // ���ĵ��е������Ƿ���ͬһ�ֿⲢ�Ҷ��ǿ���
        String sql = "select count(a.storage_id) as COL_COUNTS from " +
            tableName + " a where a.STORAGE_ID=" + storageId +
            " and a.RESOURCE_STATE='" + ParamsConsConfig.RcEntityFreeState +
            "' ";
        String cond2 = "";

        if ((resBCode != null) && (resBCode.trim().length() > 0) &&
                (resECode != null) && (resECode.trim().length() > 0)) {
            cond2 = " and a.RESOURCE_INSTANCE_CODE>='" + resBCode +
                "' and a.RESOURCE_INSTANCE_CODE<='" + resECode +
                "' and a.SALES_RESOURCE_ID=" + salesRescId;
        } else {
            cond2 = " and exists(select * from rc_order_list b where a.SALES_RESOURCE_ID=b.SALES_RESOURCE_ID " +
                " and a.RESOURCE_INSTANCE_CODE=b.RESOURCE_INSTANCE_CODE and b.ORDER_ID=" +
                orderId + ")";
        }

        long num = comDao.count(sql + cond2);

        if (num == 0) {
            result = "3"; // �����sim���ѱ�ɾ��
            retMap.put("result", result);

            return retMap;
        } else if (Long.parseLong(actAmount) != num) {
            result = "4"; // �ö������ɵĺ����sim������ͬһ�ֿ��л��в����õ�
            retMap.put("result", result);

            return retMap;
        }

        // ɾ���ö�����صĺ����sim��
        String sql2 = "delete from " + tableName + " a where 1=1 " + cond2;
        int successNum = comDao.excute(sql2);
        message = String.valueOf(successNum);
        retMap.put("result", result);
        retMap.put("message", message);

        return retMap;
    }

    /**
     * ���¼������ȼ�
     *
     * @param list
     * @return
     * @throws DAOSystemException
     * @throws SQLException
     */
    public String reCountLevel(DynamicDict dto ) throws FrameException {
    	Map map = (Map)dto.getValueByName("parameter") ;
    	List list = (List)map.get("list");
    	String operId = (String)map.get("operId");
        return doRecountLevel(list, operId);
    }

	private String doRecountLevel(List list, String operId) {
		if ((list == null) || (list.size() == 0)) {
            return "-2";
        }

        String logId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                    .getNextSequence("SEQ_NO_LEVEL_LOG_ID");
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        List levelList = null;

        if ((levelList == null) || (levelList.size() < 1)) {
            String familyId = segDao.findNoFamilyId();
            RcLevelDefDAO levelDao = SrDAOFactory.getInstance()
                                                 .getRcLevelDefDAO();

            if ((familyId != null) && (familyId.trim().length() > 0)) {
                levelList = levelDao.findByFimilyForCal(familyId);
            }
        }

        if ((levelList == null) || (levelList.size() == 0)) {
            return "-3";
        }

        List logList = new ArrayList();
        String operTime = DateFormatUtils.getFormatedDateTime();
        ListIterator iter = list.listIterator();

        while (iter.hasNext()) {
            Object obj = iter.next();

            //System.out.println(obj);
            if (obj instanceof RcNoVO) {
                String rescInstanceId = (String) ((RcNoVO) obj).getRescInstanceId();
                String code = (String) ((RcNoVO) obj).getRescInstanceCode();

                if ((code != null) && !code.equals("") &&
                        (rescInstanceId != null) && !"".equals(rescInstanceId)) {
                    String newLevelId = this.setEntityLevel(code, levelList);

                    // if(newLevelId!=null &&! newLevelId.equals("")){
                    ((RcNoVO) obj).setOldRescLevle(((RcNoVO) obj).getRescLevel());
                    ((RcNoVO) obj).setRescLevel(newLevelId);
                    logList.add(new RcLevelLogVO(logId, rescInstanceId, code,
                            ((RcNoVO) obj).getRescLevel(),
                            ((RcNoVO) obj).getSalesRescId(),
                            ((RcNoVO) obj).getOldRescLevle(), operId, operTime));
                } else {
                    iter.remove();
                }
            } else {
                iter.remove();
            }
        }

        // ����rc_no���ȼ��ֶ�
        int ii = SrNSDAOFactory.getInstance().getRcNoDAO()
                               .updateRescStateByBatch(list);

        // ����log��־
        int iii = SrNSDAOFactory.getInstance().getRcLevelLogDAO()
                                .insertByBatch(logList);

        return "0+" + logId;
	}

    /**
     *
     * @param map
     * @return
     */
    public String reCountLevel(Map map) throws DAOSystemException {
        if (map == null) {
            return "-1";
        }

        String storageId = (String) map.get("storageId");
        String startCode = (String) map.get("startCode");
        String endCode = (String) map.get("endCode");
        String operId = (String) map.get("operId");
        String type = (String) map.get("recountType");

        String SQL_SELECT_2 = "SELECT a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE,a.RESOURCE_LEVEL,a.SALES_RESOURCE_ID,a.STORAGE_ID,a.RESOURCE_STATE,a.STATE,a.EFF_DATE,a.EXP_DATE,a.IMSI_ID,a.NO_SEG_ID,a.REC_TIME,a.BALA_MODE,a.INIT_TIME,b.NO_SEG_NAME,c.SALES_RESOURCE_NAME,d.STORAGE_NAME " +
            ",a.LAN_ID,a.REGION_ID,a.EXCH_ID,a.self_help_flag" +
            " FROM RC_NO a,rc_no_seg b,SALES_RESOURCE c,RC_STORAGE d where a.no_seg_id=b.no_seg_id and b.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and a.STORAGE_ID=d.STORAGE_ID ";

        String[] sqlParams = new String[10];
        int i = 0;

        String SQL = " and resource_state = 'A'";

        if ("1".equals(type)) { //�ļ��ϴ���ʽ

            StringBuffer tmp = new StringBuffer();
            List codes = (ArrayList) map.get("noList");
            String code = "";

            for (Iterator iter = codes.iterator(); iter.hasNext();) {
                code = (String) iter.next();
                tmp.append("'").append(code).append("',");
            }

            //			SQL = " and a.resource_instance_code in ("+ tmp.toString();
            SQL += " and a.resource_instance_code in (?";
            sqlParams[i] = tmp.toString();
            i++;

            if (SQL.endsWith(",")) {
                SQL = SQL.substring(0, SQL.length() - 1) + ")";
            } else {
                SQL = SQL + ")";
            }
        } else { //

            if ((startCode == null) || "".equals(startCode) ||
                    (endCode == null) || "".equals(endCode) ||
                    (operId == null) || operId.equals("")) {
                return "-1";
            }

            //			SQL = " and a.resource_instance_code>=" + startCode
            //			+ " and a.resource_instance_code<=" + endCode ;
            SQL += " and a.resource_instance_code>=? and a.resource_instance_code<= ?";
            sqlParams[i] = startCode;
            i++;
            sqlParams[i] = endCode;
            i++;
        }

        if ((storageId != null) && !"".equals(storageId)) {
            storageId = (!storageId.endsWith(",")) ? storageId
                                                   : storageId.substring(0,
                    storageId.length() - 1);

            if (storageId.indexOf(",") > 0) {
                //				SQL+=" and d.storage_id in (" + storageId+")";
                SQL += " and d.storage_id in (?)";
                sqlParams[i] = storageId;
                i++;
            } else {
                //				SQL+=" and d.storage_id=" + storageId;
                SQL += " and d.storage_id =?";
                sqlParams[i] = storageId;
                i++;
            }
        }

        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        dao.setFlag(2);

        //		List list = dao.findByCond(SQL);
        String[] params = new String[i];

        for (int j = 0; j < i; j++) {
            params[j] = sqlParams[j];
        }

        List list = dao.findBySql(SQL_SELECT_2 + SQL, params);

        return this.doRecountLevel(list, (String) operId);
    }

    public String reCountLevelByLanId(String lanId, String operId) {
        RcNoDAO dao = SrNSDAOFactory.getInstance().getRcNoDAO();
        String SQL_SELECT_2 = "SELECT a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE,a.RESOURCE_LEVEL,a.SALES_RESOURCE_ID,a.STORAGE_ID,a.RESOURCE_STATE,a.STATE,a.EFF_DATE,a.EXP_DATE,a.IMSI_ID,a.NO_SEG_ID,a.REC_TIME,a.BALA_MODE,a.INIT_TIME,null as NO_SEG_NAME,null as SALES_RESOURCE_NAME,d.STORAGE_NAME " +
            ",a.LAN_ID,a.REGION_ID,a.EXCH_ID,a.self_help_flag" +
            " FROM RC_NO a,RC_STORAGE d where a.STORAGE_ID=d.STORAGE_ID and a.lan_id=?";
        String[] params = new String[] { lanId };
        List list = dao.findBySql(SQL_SELECT_2, params);

        return this.reCountLevelByLanId(list, (String) operId, lanId);
    }

    /**
     * ���¼������ȼ�
     *
     * @param list
     * @return
     * @throws DAOSystemException
     * @throws SQLException
     */
    public String reCountLevelByLanId(List list, String operId, String lanId)
        throws DAOSystemException {
        if ((list == null) || (list.size() == 0)) {
            return "-2";
        }

        String logId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                    .getNextSequence("SEQ_NO_LEVEL_LOG_ID");
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();
        List levelList = null;
        String familyId = segDao.findNoFamilyId();
        RcLevelDefDAO levelDao = SrDAOFactory.getInstance().getRcLevelDefDAO();

        if ((familyId != null) && (familyId.trim().length() > 0)) {
            levelList = levelDao.findByFimilyAndLanId(familyId, lanId);
        }

        if ((levelList == null) || (levelList.size() == 0)) {
            return "-3";
        }

        List logList = new ArrayList();
        String operTime = DateFormatUtils.getFormatedDateTime();
        ListIterator iter = list.listIterator();

        while (iter.hasNext()) {
            Object obj = iter.next();

            //System.out.println(obj);
            if (obj instanceof RcNoVO) {
                String rescInstanceId = (String) ((RcNoVO) obj).getRescInstanceId();
                String code = (String) ((RcNoVO) obj).getRescInstanceCode();

                if ((code != null) && !code.equals("") &&
                        (rescInstanceId != null) && !"".equals(rescInstanceId)) {
                    String newLevelId = this.setEntityLevel(code, levelList);

                    // if(newLevelId!=null &&! newLevelId.equals("")){
                    ((RcNoVO) obj).setOldRescLevle(((RcNoVO) obj).getRescLevel());
                    ((RcNoVO) obj).setRescLevel(newLevelId);
                } else {
                    iter.remove();
                }
            } else {
                iter.remove();
            }
        }

        // ����rc_no���ȼ��ֶ�
        int ii = SrNSDAOFactory.getInstance().getRcNoDAO()
                               .updateRescStateByBatch(list);
        // ����log��־
        logList.add(new RcLevelLogVO(logId, lanId, lanId, lanId, lanId, lanId,
                operId, operTime));

        int iii = SrNSDAOFactory.getInstance().getRcLevelLogDAO()
                                .insertByBatch(logList);

        return "0+" + logId;
    }

    public PageModel qryRcNoLogInfo(Map map) {
        if (map == null) {
            return new PageModel();
        }

        String logId = (String) map.get("logId");
        String storageId = (String) map.get("storageId");
        String startCode = (String) map.get("startCode");
        String endCode = (String) map.get("endCode");

        if ((storageId == null) || "".equals(storageId) || (startCode == null) ||
                "".equals(startCode) || (endCode == null) ||
                "".equals(endCode) || (logId == null) || "".equals(logId)) {
            return new PageModel();
        }

        int pageIndex = ((Integer) map.get("pageIndex")).intValue();
        int pageSize = ((Integer) map.get("pageSize")).intValue();
        RcLevelLogDAO dao = SrNSDAOFactory.getInstance().getRcLevelLogDAO();
        dao.setFlag(2);

        String whereCond = " and a.log_id=" + logId +
            " and a.resource_instance_code>='" + startCode +
            "' and a.resource_instance_code<='" + endCode + "' ";

        if ((storageId != null) && !"".equals(storageId)) {
            storageId = (!storageId.endsWith(",")) ? storageId
                                                   : storageId.substring(0,
                    storageId.length() - 1);

            if (storageId.indexOf(",") > 0) {
                whereCond += (" and c.storage_id in (" + storageId + ")");
            } else {
                whereCond += (" and c.storage_id=" + storageId);
            }
        }

        return PageHelper.popupPageModel(dao, whereCond, pageIndex, pageSize);
    }

    public List queryDownSIMNOStockNum(Map map) {
        String databaseType = CrmParamsConfig.getInstance()
                                             .getParamValue("DATABASE_TYPE");
        String cond0 = "";

        //		String cond1 = "";
        String cond2 = "";
        String provId = "";
        String familyId = null;
        String tableName = null;

        // String operId = null;
        // String regionId = null;
        RcStockDAO dao = SrDAOFactory.getInstance().getRcStockDAO();

        // String departId = null;
        String rescState = null;
        String rescLevel = null;
        String balaMode = null;
        String state = ParamsConsConfig.RcEntityStateValide;

        //		String startDate = null;
        //		String endDate = null;
        String downStorageIds = null;

        if (map != null) {
            // if(map.get("departId")!=null&&((String)map.get("departId")).trim().length()>0)
            // departId = (String)map.get("departId");
            if ((map.get("rescState") != null) &&
                    (((String) map.get("rescState")).trim().length() > 0)) {
                rescState = (String) map.get("rescState");
            }

            if ((map.get("state") != null) &&
                    (((String) map.get("state")).trim().length() > 0)) {
                state = (String) map.get("state");
            }

            if ((map.get("tableName") != null) &&
                    (((String) map.get("tableName")).trim().length() > 0)) {
                tableName = (String) map.get("tableName");
            }

            if ((map.get("rescState") != null) &&
                    (((String) map.get("rescState")).trim().length() > 0)) {
                rescState = (String) map.get("rescState");
            }

            if ((map.get("rescLevel") != null) &&
                    (((String) map.get("rescLevel")).trim().length() > 0)) {
                rescLevel = (String) map.get("rescLevel");
            }

            if ((map.get("balaMode") != null) &&
                    (((String) map.get("balaMode")).trim().length() > 0)) {
                balaMode = (String) map.get("balaMode");
            }

            /*if (map.get("startDate") != null
                            && ((String) map.get("startDate")).trim().length() > 0)
                    startDate = (String) map.get("startDate");
            if (map.get("endDate") != null
                            && ((String) map.get("endDate")).trim().length() > 0)
                    endDate = (String) map.get("endDate");*/
            if (map.get("provId") != null) {
                provId = String.valueOf(map.get("provId")).trim();
            }

            if ((map.get("downStorageIds") != null) &&
                    (((String) map.get("downStorageIds")).trim().length() > 0)) {
                downStorageIds = (String) map.get("downStorageIds");
            }
        }

        if ((tableName == null) || (tableName.trim().length() < 1) ||
                (downStorageIds == null) ||
                (downStorageIds.trim().length() < 1)) {
            return new ArrayList();
        }

        if ((rescState != null) && (rescState.trim().length() > 0)) {
            cond0 = " and resource_state='" + rescState + "'";
        }

        // ��װ��ʼ����ʱ��
        /*if ("INFORMIX".equals(databaseType)) {
                if (startDate != null && !"".equals(startDate)) {
                        cond0 += "  and INIT_TIME>=to_date('" + startDate
                                        + "','%Y-%m-%d')  ";
                }
                if (endDate != null && !"".equals(endDate)) {
                        cond0 += "  and INIT_TIME<=to_date('" + endDate
                                        + " 23:59:59','%Y-%m-%d %H:%M:%S')  ";
                }
        } else {
                if (startDate != null && !"".equals(startDate)) {
                        cond0 += "  and INIT_TIME>=to_date('" + startDate
                                        + "','yyyy-mm-dd')  ";
                }
                if (endDate != null && !"".equals(endDate)) {
                        cond0 += "  and INIT_TIME<=to_date('" + endDate
                                        + " 23:59:59','yyyy-mm-dd hh24:mi:ss')  ";
                }
        }*/

        // end ��װ��ѯ����
        // ��ѯ��Դ��Ӧ�ļ���id
        String attrCode = null;
        RcNoSegDAO segDao = SrNSDAOFactory.getInstance().getRcNoSegDAO();

        if ("rc_no".equalsIgnoreCase(tableName)) {
            attrCode = "Rc_No_FamilyId";

            if ((rescLevel != null) && (rescLevel.trim().length() > 0)) {
                cond0 += (" and RESOURCE_LEVEL=" + rescLevel);
            }

            if ((balaMode != null) && (balaMode.trim().length() > 0)) {
                cond0 += (" and BALA_MODE='" + balaMode + "' ");
            }
        } else if ("rc_sim".equalsIgnoreCase(tableName)) {
            attrCode = "Rc_SIM_FamilyId";
        }

        if (attrCode != null) {
            familyId = segDao.findFamilyId(attrCode);
        }

        if ((familyId == null) || (familyId.trim().length() < 1)) {
            return new ArrayList();
        }

        // ��sql���
        String sql_oracle = "select c.storage_id,c.storage_name,a.stock_amount,a.resource_state,d.UP_LIMIT,d.DOWN_LIMIT,null as sales_resource_id,'' as sales_resource_name" +
            " from (select storage_id,resource_state,count(resource_instance_id) as stock_amount from " +
            tableName + " " + " where state = '" + state + "' " + cond0 +
            " group by storage_id,resource_state) a " +
            " inner join rc_storage c on a.storage_id=c.storage_id " +
            " left outer join rc_stock_limit d on(a.storage_id = d.storage_id and d.family_id=" +
            familyId + ") " + " where  a.storage_id in(" + downStorageIds +
            ")" + cond2;

        String sql_informix = "select c.storage_id,c.storage_name,a.stock_amount,a.resource_state,d.UP_LIMIT,d.DOWN_LIMIT,null as sales_resource_id,'' as sales_resource_name" +
            " from table(MULTISET(select storage_id,resource_state,count(resource_instance_id) as stock_amount from " +
            tableName + " " + " where state = '" + state + "' " + cond0 +
            " group by storage_id,resource_state)) a " +
            " inner join rc_storage c on a.storage_id=c.storage_id " +
            " left outer join rc_stock_limit d on(a.storage_id = d.storage_id and d.family_id=" +
            familyId + ") " + " where a.storage_id in(" + downStorageIds + ")" +
            cond2;

        String sql = sql_oracle;

        if ("INFORMIX".equals(databaseType)) {
            sql = sql_informix;
        }

        Debug.print("��SrStockBo,����queryDownSIMNOStockNum��ִ�е�sql��:" + sql);

        List list = new ArrayList();

        // ����Ҫ��ѯ��������ѯ���
        dao.setSQL_SELECT(sql);
        dao.setFlag(2); // ����Ҫ��ѯ�ֿ�ģ��������޺���Դ״̬
        dao.setUpDown("down");
        list = dao.findByCond(" ");

        return list;
    }

    public PageModel qryNoRemainInfo(HashMap map) {
        PageModel pm = new PageModel();

        if (map == null) {
            return pm;
        }

        Object departId = map.get("departId");
        Object operId = map.get("operId");
        Object remainId = map.get("remainId");
        Object startDate = map.get("startDate");
        Object endDate = map.get("endDate");
        Object remained = map.get("remained");
        Object remainExp = map.get("remainExp");
        int pi = Integer.parseInt(String.valueOf(map.get("pi")));
        int ps = Integer.parseInt(String.valueOf(map.get("ps")));

        if ((departId == null) || departId.equals("") || (operId == null) ||
                operId.equals("") || (remained == null) || remained.equals("") ||
                (remainExp == null) || remainExp.equals("")) {
            return pm;
        }

        //modifed by panyazong ��������棬��ѡ��Ը�����ʱ����	   	
        //	   	String sql=" A.depart_id="+departId+" and A.oper_id="+operId;
        String sql = " A.depart_id in (" + departId + ") and A.oper_id=" +
            operId;

        //����ǽ��...
        if (remained.equals("1")) {
            //	   		sql = " A.rel_depart_id="+departId+" and A.rel_oper_id="+operId;
            sql = " A.rel_depart_id in (" + departId + ") and A.rel_oper_id=" +
                operId;
        }

        sql += (" and A.remain_Flag = '" + remained + "'");

        //���������...
        if (remainExp.equals("1") && remained.equals("0")) {
            sql += " and sysdate > (A.end_remain_time)+ (A.remain_time) ";
        } else if (remainExp.equals("0") && remained.equals("0")) {
            sql += " and sysdate<= (A.end_remain_time)+ (A.remain_time) ";
        }

        if ((remainId != null) && !remainId.equals("")) {
            sql += (" and A.remain_id=" + remainId);
        }

        if ((startDate != null) && !startDate.equals("")) {
            sql += (" and to_date('" + startDate +
            " 00:00:00','YYYY-MM-DD HH24:MI:SS')<=A.");

            if (remained.equals("0")) {
                sql += "remain_time";
            } else {
                sql += "rel_time";
            }
        }

        if ((endDate != null) && !endDate.equals("")) {
            sql += (" and to_date('" + endDate +
            " 23:59:59','YYYY-MM-DD HH24:MI:SS')>=A.");

            if (remained.equals("0")) {
                sql += "remain_time";
            } else {
                sql += "rel_time";
            }
        }

        RcNoRemainInfoDAO dao = SrNSDAOFactory.getInstance()
                                              .getRcNoRemainInfoDAO();
        pm = PageHelper.popupPageModel(dao, sql, pi, ps);

        return pm;
    }

    /**
     * * @param vo
     * @return
     */
    public String relNoRemain2(RcNoRemainInfoVO vo, String[] nos) {
        if (vo == null) {
            return "����ʧ�� ������ʧ";
        }

        String remainNum = vo.getRemainNum();
        String startNo = vo.getStartNo();
        String endNo = vo.getEndNo();
        String remainFlag = vo.getRemainFlag();
        String endRemainTime = vo.getEndRemainTime();
        String rescState = vo.getRescState();

        if ((vo.getRelOperId() == null) || vo.getRelOperId().equals("") ||
                (vo.getRelDepartId() == null) ||
                vo.getRelDepartId().equals("") || (vo.getOperId() == null) ||
                vo.getOperId().equals("") || (vo.getDepartId() == null) ||
                vo.getDepartId().equals("") || (remainNum == null) ||
                "".equals(remainNum) || (startNo == null) ||
                startNo.equals("") || (endNo == null) || endNo.equals("") ||
                (remainFlag == null) || remainFlag.equals("") ||
                (endRemainTime == null) || endRemainTime.equals("") ||
                (rescState == null) || "".equals(rescState)) {
            return "����ʧ�� ������Ч";
        }

        String[] str = vo.getOperId().split("@");

        if ((str == null) || (str.length != 2)) {
            return "����ʧ�� ������Ч";
        }

        vo.setOperId(str[0]);

        String provId = str[1];
        vo.setRelTime(DateFormatUtils.getFormatedDateTime());

        List remainList = SrNSDAOFactory.getInstance().getRcNoRemainDAO()
                                        .findByCond(" remain_id=" +
                vo.getRemainId() + "");

        //	   		int failCount = 0;
        //	   		if(remainList!=null && remainList.size()>0){
        //	   			RcNoDAO nodao = SrNSDAOFactory.getInstance().getRcNoDAO();
        //
        //	   			for(int i=0;i<remainList.size();i++){
        //
        //	   				RcNoVO noVO = new RcNoVO();	   		   		
        //	   				if("20".equals(provId)){//�����ļ��Ϲ���Ȩ���ж�
        //	   					noVO = nodao.findByCode2(((RcNoRemainVO)remainList.get(i)).getRescInstanceCode(),vo.getRelOperId(),vo.getDepartId());
        //	   		   		}else{
        //		   				noVO = nodao.findByCode(((RcNoRemainVO)remainList.get(i)).getRescInstanceCode());
        //	   		   		}
        //	   				if(noVO==null || !noVO.getRescState().equals(rescState)||!noVO.getState().equals(ParamsConsConfig.RcEntityStateValide)){
        //	   					//return "���ʧ�ܣ����룺"+((RcNoRemainVO)remainList.get(i)).getRescInstanceCode()+"�����ڻ�״̬���Ի���Ч";
        //	   					failCount ++;
        //	   				}else{
        //	   					//noVO.setState("00A");
        //	   					noVO.setRescState(ParamsConsConfig.RcEntityFreeState);
        //	   					nodao.update(((RcNoRemainVO)remainList.get(i)).getRescInstanceId(), noVO);
        //	   				}
        //	   			}
        //	   			//vo.setRescState(ParamsConsConfig.RcEntityFreeState);
        //	   			if(failCount==0){
        //		   			SrNSDAOFactory.getInstance().getRcNoRemainInfoDAO().update(vo.getRemainId(), vo);
        //	   			}
        //	   		}
        String updateNO = " update rc_no set  resource_state = 'A' where 1=1 and resource_state ='Q'";

        if ("20".equals(provId)) { //�����ļ��Ϲ���Ȩ���ж�
            updateNO += ("  and exists (select 1 from mp_storage b where RC_NO.storage_id=b.storage_id and " +
            " b.oper_id=" + vo.getRelOperId() + " union all " +
            "  select 1 from STORAGE_DEPART_RELA c where c.storage_Id=RC_NO.storage_id " +
            "  and c.depart_id=" + vo.getDepartId() + ") ");
        }

        updateNO += " and RESOURCE_INSTANCE_CODE = ";

        String updateRemain = " update rc_no_remain_info set remain_flag=?,rel_oper_id=?,rel_depart_id=?,rel_time=?,resource_state=?,comments=? where remain_id=?";
        String failList = "";
        Connection conn1 = null;
        Connection conn2 = null;
        Statement stmt1 = null;
        PreparedStatement stmt2 = null;

        try {
            conn1 = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
            stmt1 = conn1.createStatement();

            for (int i = 0; i < remainList.size(); i++) {
                stmt1.addBatch(updateNO + "'" +
                    ((RcNoRemainVO) remainList.get(i)).getRescInstanceCode() +
                    "'");
            }

            int[] upArr = stmt1.executeBatch();
            int failCount = 0;

            for (int j = 0; j < remainList.size(); j++) {
                if (upArr[j] <= 0) {
                    failCount++;
                    failList += (((RcNoRemainVO) remainList.get(j)).getRescInstanceCode() +
                    ",");
                }
            }

            if (failCount == remainList.size()) { //û��һ���ɹ�

                return failList;
            }

            conn2 = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
            stmt2 = conn2.prepareStatement(updateRemain);
            stmt2.setString(1, "1");
            stmt2.setString(2, vo.getRelOperId());
            stmt2.setString(3, vo.getDepartId());
            stmt2.setDate(4, DAOUtils.parseDateTime(vo.getRelTime()));
            stmt2.setString(5, "A");
            stmt2.setString(6, vo.getComments());
            stmt2.setString(7, vo.getRemainId());
            stmt2.executeUpdate();
        } catch (Exception e) {
            Debug.print("����������");
            Debug.print(updateNO + "?", this);
            Debug.print("���·�����");
            Debug.print(updateRemain, this);
            throw new DAOSystemException("SQLException while insert sql:\n", e);
        } finally {
            DAOUtils.closeStatement(stmt1);
            DAOUtils.closeConnection(conn1, this);
            DAOUtils.closeStatement(stmt2, this);
            DAOUtils.closeConnection(conn2, this);
        }

        return failList;
    }

    public PageModel qryNoRemains(String remainId, int pi, int ps) {
        if ((remainId == null) || "".equals(remainId)) {
            return new PageModel();
        }

        RcNoRemainDAO dao = SrNSDAOFactory.getInstance().getRcNoRemainDAO();

        return PageHelper.popupPageModel(dao, " remain_id=" + remainId, pi, ps);
    }

    /**
    * ����Ӫ����Դ���п�ѡ��״̬
    * @return List
    */
    public List qryRescAllState() {
        RcStateDAO dao = SrDAOFactory.getInstance().getRcStateDAO();
        String SQL = "select state,state_name from rc_state ";
        ArrayList list = dao.findBySql(SQL, new String[] {  });
        RcStateVO rcvo = new RcStateVO();
        rcvo.setState("������");
        rcvo.setStateName("������");

        int i = 0;
        List list1 = new ArrayList();
        list1.add(rcvo);

        while (i < list.size()) {
            list1.add(list.get(i));
            i++;
        }

        return list1;
    }

    //�����桢������
    public String handleNoRemain(RcNoRemainInfoVO vo, String[] nos, String type) {
        if (vo.getRemainFlag().equals("1")) {
            return relNoRemain2(vo, nos);
        }

        RcNoRemainDAO noRemainDAO = SrNSDAOFactory.getInstance()
                                                  .getRcNoRemainDAO();
        Map map = noRemainDAO.handleNoRemain(vo, nos, type);

        return (String) map.get("failList");
    }
}
