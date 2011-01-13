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
    		// 配置参数载入
			CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
			SysSet.initSystem(3);
    	}
    	ConnectionContextUtil.createConnectionContext() ;//创建数据库连接上下文 add by easonwu 2009-12-22
    	
        return performDictMulti(dict);
    }
    /**
     * 支持多个数据库连接的处理 BHO调用
     *如果是XA,则采用JTA方式，否则通过直接的commit和rollback来操作
     * @param dto
     * @param strDBName
     *            由前台指定的数据库连接，因此不能使用xa,需要单独处理
     * @return
     */
    private static DynamicDict performDictMulti(DynamicDict dto) throws Exception  {
        boolean commit = true;
        boolean bXAType = false;// 根据是否采用XA
        
        String serviceName = null;
        UserTransaction usTran = null;
        Exception error = null ;
        try {
            serviceName = dto.getServiceName();
            FrameServiceController controller = FrameServiceController.getInstance() ;
            TfmServicesVO service = controller.getServiceByName(serviceName) ;
        
            //需要判断是否采用XA
            bXAType = SysSet.isXAType(service.getEnv_id());
            if (bXAType) {
               usTran = SysSet.getUserTransaction();
               SysSet.tpBegin(usTran);
            }
            //执行服务
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
            dto.msg = "系统调用异常" + th.getClass().getName();
            dto.exception = LogHelper.getStackMsg(th);
            commit = false;
            error = th ;
//            throw new FrameException(th) ;
        } finally {
            try {
                //统一关闭数据库连接
            	if (commit) {
                      //一切执行正常
                      if (bXAType)
                        	SysSet.tpCommit(usTran);
                      else 
                        	ConnectionContext.getContext().allCommit() ;
               } else{
                    	 //出现异常
                      if (bXAType)
                        	SysSet.tpRollback(usTran);
                      else 
                        	ConnectionContext.getContext().allRollback() ;
               }
                    
               ConnectionContext.getContext().allCloseConnection() ;
                
                if (usTran != null) {
                    m_Logger.debug("关闭UserTransaction");
                    usTran = null;
                }
            } catch (Exception e) {
                m_Logger.error("关闭数据库连接时，出现异常" + LogHelper.getStackMsg(e));
                dto.flag = -5001;
                dto.msg = "关闭数据库连接时，出现异常";
                dto.exception = e.getMessage();
                commit = false;
            }
            
            m_Logger.debug(dto.m_ActionId + " 返回执行代码:" + dto.flag);
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
