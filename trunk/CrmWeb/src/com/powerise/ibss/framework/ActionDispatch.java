package com.powerise.ibss.framework;

import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import com.powerise.ibss.framedata.bo.FrameServiceController;
import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.powerise.ibss.util.GlobalNames;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.ConnectionContextUtil;
import com.ztesoft.common.exception.CrmError;
import com.ztesoft.common.exception.CrmException;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
/**
 * @author Powerise GD
 * @version 1.0
 * modify by easonwu 
 */
public class ActionDispatch {
    private static Logger m_Logger = Logger.getLogger(ActionDispatch.class);

    public static DynamicDict dispatch(DynamicDict dict) throws Exception {
    	if(!GlobalNames.CONFIG_LOADED){
    		// ���ò�������
			CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
			SysSet.initSystem(3);
    	}
    	ConnectionContextUtil.createConnectionContext() ;//�������ݿ����������� add by easonwu 2009-12-22
    	
        return performDictMulti(dict);
    }
    /**
     * ֧�ֶ�����ݿ����ӵĴ��� BHO����
     *�����XA,�����JTA��ʽ������ͨ��ֱ�ӵ�commit��rollback������
     * @param dto
     * @param strDBName
     *            ��ǰָ̨�������ݿ����ӣ���˲���ʹ��xa,��Ҫ��������
     * @return
     */
    private static DynamicDict performDictMulti(DynamicDict dto) throws Exception  {
        boolean commit = true;
        boolean bXAType = false;// �����Ƿ����XA
        
        String serviceName = null;
        UserTransaction usTran = null;
        Exception error = null ;
        try {
            serviceName = dto.getServiceName();
            FrameServiceController controller = FrameServiceController.getInstance() ;
            TfmServicesVO service = controller.getServiceByName(serviceName) ;
        
            //��Ҫ�ж��Ƿ����XA
            bXAType = SysSet.isXAType(service.getEnv_id());
            if (bXAType) {
               usTran = SysSet.getUserTransaction();
               SysSet.tpBegin(usTran);
            }
            //ִ�з���
            controller.callService(service, dto) ;
        } catch (FrameException fe) {
            dto.flag = fe.getErrorCode();
            dto.msg = fe.getErrorMsg();
            dto.exception = fe.getSysMsg() + "\n" + LogHelper.getStackMsg(fe);
            commit = false;
            error = fe ;
        }  catch (CrmException ce) {
        	CrmError err = ce.getError() ;
            dto.flag = err.getLevel();
            dto.msg = err.getErrorMessage();
            dto.exception = err.getErrorMessage() + "\n" + LogHelper.getStackMsg(ce);
            commit = false;
            error = ce ;
        } catch (Exception th) {
            dto.flag = -5009;
            dto.msg = "ϵͳ�����쳣" + th.getClass().getName();
            dto.exception = LogHelper.getStackMsg(th);
            commit = false;
            error = th ;
//            throw new FrameException(th) ;
        } finally {
            try {
                //ͳһ�ر����ݿ�����
            	if (commit) {
                      //һ��ִ������
                      if (bXAType)
                        	SysSet.tpCommit(usTran);
                      else 
                        	ConnectionContext.getContext().allCommit() ;
               } else{
                    	 //�����쳣
                      if (bXAType)
                        	SysSet.tpRollback(usTran);
                      else 
                        	ConnectionContext.getContext().allRollback() ;
               }
                    
               ConnectionContext.getContext().allCloseConnection() ;
                
                if (usTran != null) {
                    m_Logger.debug("�ر�UserTransaction");
                    usTran = null;
                }
            } catch (Exception e) {
                m_Logger.error("�ر����ݿ�����ʱ�������쳣" + LogHelper.getStackMsg(e));
                dto.flag = -5001;
                dto.msg = "�ر����ݿ�����ʱ�������쳣";
                dto.exception = e.getMessage();
                commit = false;
            }
            
            m_Logger.debug(dto.m_ActionId + " ����ִ�д���:" + dto.flag);
            if (commit == false ) {
                if(GlobalNames.ERR_TO_DB){
                	long lErrSeq = LogHelper.writeLog(dto.m_ActionId, dto.flag, dto.msg, dto.exception == null ? "" : dto.exception);
                }

                throw error ;
            }
        }
        
      
        return dto;
    }

}
