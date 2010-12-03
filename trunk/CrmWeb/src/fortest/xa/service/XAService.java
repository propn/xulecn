package fortest.xa.service;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

import fortest.LogControl;
import fortest.xa.vo.TMasterVO;
import fortest.xa.vo.TSecondVO;

public class XAService {
	private static final String XABO = "XABO" ;
	
	public boolean insert(TMasterVO tvo ,TSecondVO svo) throws Exception {
		Date s = new Date() ;
		Map param = new HashMap() ;
		param.put("TMasterVO", tvo) ;
		param.put("TSecondVO", svo) ;

		Object result = ServiceManager.callJavaBeanService(XAService.XABO,"insert" ,param) ;
		LogControl.log("XA  insert action" , new Date().getTime()-s.getTime()) ;
		return ((Boolean)result).booleanValue() ;
	}

	
	public boolean update(TMasterVO tvo ,TSecondVO svo ) throws Exception {
		Date s = new Date() ;
		Map param = new HashMap() ;
		param.put("TMasterVO", tvo) ;
		param.put("TSecondVO", svo) ;
		Object result = ServiceManager.callJavaBeanService(XAService.XABO,"update" ,param) ;
		LogControl.log("XA  update action" , new Date().getTime()-s.getTime()) ;
		return ((Boolean)result).booleanValue() ;
	}
	
	
	public PageModel searchData(String mName , int pageIndex , int pageSize) throws Exception {
		System.out.println("IP=============="+InetAddress.getLocalHost().getHostAddress());
		Date s = new Date() ;
		Map param = new HashMap() ;
		param.put("mName", mName) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		Object result = ServiceManager.callJavaBeanService(XAService.XABO,"searchData" ,param) ;
		LogControl.log("XA  query action" , new Date().getTime()-s.getTime()) ;
		return result == null ? null : (PageModel) result ;
	}
	
	
	public List getObjById(String id ) throws Exception {
		Map param = new HashMap() ;
		param.put("id", id) ;
		
		Object result = ServiceManager.callJavaBeanService(XAService.XABO,"getObjById" ,param) ;
		return result == null ? null : (List) result ;
	}
	
	public boolean delete(String id  , String sid) throws Exception {
		Date s = new Date() ;
		Map param = new HashMap() ;
		param.put("id", id) ;
		param.put("sid", sid) ;

		Object result = ServiceManager.callJavaBeanService(XAService.XABO,"delete" ,param) ;
		LogControl.log("XA  delete action" , new Date().getTime()-s.getTime()) ;
		return ((Boolean)result).booleanValue()  ;
	}
}
