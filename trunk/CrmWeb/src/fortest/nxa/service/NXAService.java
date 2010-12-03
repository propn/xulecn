package fortest.nxa.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;

import fortest.LogControl;
import fortest.nxa.vo.TMasterVO;
import fortest.nxa.vo.TSecondVO;

/**
 * 
 * @author easonwu 2009-01-05
 *
 */
public class NXAService {
	private static final String NXABO = "NXABO" ;
	
	/**
	 * insert action 
	 * @param tvo
	 * @param svo
	 * @return
	 * @throws Exception
	 */
	public boolean insert(TMasterVO tvo ,TSecondVO svo) throws Exception {
		long s1 = System.currentTimeMillis() ;
		Map param = new HashMap() ;
		param.put("TMasterVO", tvo) ;
		param.put("TSecondVO", svo) ;
		Object result = ServiceManager.callJavaBeanService(NXAService.NXABO,"insert" ,param) ;
		System.out.println("NXA insert Service---->"+(System.currentTimeMillis()-s1)) ;
		LogControl.log("NXA insert action" , (System.currentTimeMillis()-s1)) ;
		return ((Boolean)result).booleanValue() ;
	}

	/**
	 * update action
	 * @param tvo
	 * @param svo
	 * @return
	 * @throws Exception
	 */
	public boolean update(TMasterVO tvo ,TSecondVO svo ) throws Exception {
		long s1 = System.currentTimeMillis() ;
		Map param = new HashMap() ;
		param.put("TMasterVO", tvo) ;
		param.put("TSecondVO", svo) ;
		Object result = ServiceManager.callJavaBeanService(NXAService.NXABO,"update" ,param) ;
		System.out.println("NXA update Service---->"+(System.currentTimeMillis()-s1)) ;
		LogControl.log("NXA update action" , (System.currentTimeMillis()-s1)) ;
		return ((Boolean)result).booleanValue() ;
	}
	
	
	/**
	 * search action
	 * @param mName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageModel searchData(String mName , int pageIndex , int pageSize) throws Exception {
		long s1 = System.currentTimeMillis() ;
		Map param = new HashMap() ;
		param.put("mName", mName) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		Object result = ServiceManager.callJavaBeanService(NXAService.NXABO,"searchData" ,param) ;
		System.out.println("NXA Query Service---->"+(System.currentTimeMillis()-s1)) ;
		LogControl.log("NXA query action" , (System.currentTimeMillis()-s1)) ;
		
		PageModel p =  result == null ? null : (PageModel) result ;
		return p ;
	}
	
	/**
	 * load action
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List getObjById(String id ) throws Exception {
		long s1 = System.currentTimeMillis() ;
		Map param = new HashMap() ;
		param.put("id", id) ;
		
		Object result = ServiceManager.callJavaBeanService(NXAService.NXABO,"getObjById" ,param) ;
		System.out.println("NXA getObjById Service---->"+(System.currentTimeMillis()-s1)) ;
		return result == null ? null : (List) result ;
	}
	
	/**
	 * delete action 
	 * @param id
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public boolean delete(String id  , String sid) throws Exception {
		long s1 = System.currentTimeMillis() ;
		Map param = new HashMap() ;
		param.put("id", id) ;
		param.put("sid", sid) ;

		Object result = ServiceManager.callJavaBeanService(NXAService.NXABO,"delete" ,param) ;
		System.out.println("NXA delete Service---->"+(System.currentTimeMillis()-s1)) ;
		LogControl.log("NXA delete action" , (System.currentTimeMillis()-s1)) ;
		
		return ((Boolean)result).booleanValue()  ;
	}
}
