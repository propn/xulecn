package fortest.nxa.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import fortest.nxa.dao.impl.TMasterDAOImpl;
import fortest.nxa.dao.impl.TSecondDAOImpl;
import fortest.nxa.vo.TMasterVO;
import fortest.nxa.vo.TSecondVO;

public class NXABO2 implements IAction  {
	public int perform(DynamicDict dto) throws FrameException {
		Object result = null ;
		String methodName = (String) dto.getValueByName(Const.ACTION_METHOD);
		
			try {
				if(methodName.equals("searchData"))
					result =  searchData( dto) ;
				else if(methodName.equals("insert"))
					result =  Boolean.valueOf(insert( dto)) ;
				else if(methodName.equals("update"))
					result = Boolean.valueOf( update( dto)) ;
				else if(methodName.equals("delete"))
					result =  Boolean.valueOf( delete( dto) );
				else if(methodName.equals("getObjById"))
					result =  getObjById( dto) ;
				dto.setValueByName(Const.ACTION_RESULT, result) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0 ;
			
	}
//public class NXABO2 extends DictAction  {
	private static  long SEQ_T_MASTER = 1 ;
	private static  long SEQ_T_SECOND = 1 ;
	
	public boolean insert(DynamicDict dto) throws Exception {
		long s = System.currentTimeMillis() ;
		Map param = (Map) dto.getValueByName("parameter") ;
		TSecondVO svo = (TSecondVO)param.get("TSecondVO") ;
		TMasterVO tvo = (TMasterVO)param.get("TMasterVO") ;
		svo.setSid(Long.toString(SEQ_T_SECOND++)) ;
		svo.setFId(Long.toString(SEQ_T_MASTER)) ;
		
		tvo.setId(Long.toString(SEQ_T_MASTER)) ;
		SEQ_T_MASTER ++ ;
		
		
		new TMasterDAOImpl().insert(tvo);
		new TSecondDAOImpl().insert(svo) ;
		
		System.out.println("NXA insert(IAction)---->"+(System.currentTimeMillis()-s)) ;
		return true ;
	}

	
	public boolean update(DynamicDict dto) throws Exception {
		long s = System.currentTimeMillis() ;
		Map param = (Map) dto.getValueByName("parameter") ;
		TSecondVO svo = (TSecondVO)param.get("TSecondVO") ;
		TMasterVO tvo = (TMasterVO)param.get("TMasterVO") ;
		
		boolean result =  new TMasterDAOImpl().update(" id="+tvo.getId(), tvo)  
			&& new TSecondDAOImpl().update(" id="+svo.getSid(), svo)  ;
		System.out.println("NXA update(IAction)---->"+(System.currentTimeMillis()-s)) ;
		return result ;
	}
	
	
	public PageModel searchData(DynamicDict dto) throws Exception {
		
		long s = System.currentTimeMillis() ;
		Map m = (Map)dto.getValueByName("parameter") ;
		long s2 = System.currentTimeMillis() ;
		System.out.println("NXA Query get param---->"+(s2-s)) ;
		int pageIndex  = ((Integer)m.get("pageIndex")).intValue();
		int pageSize  = ((Integer)m.get("pageSize")).intValue();
		StringBuffer sb = new StringBuffer(" 1=1 ") ;
		if(m.get("mName") != null ){
			String mName = (String)m.get("mName") ;
			if(!"".equals(mName.trim()))
			sb.append(" and m_name like '%"+m.get("mName")+"%'") ;
		}
		
		PageModel result = PageHelper.popupPageModel(new TMasterDAOImpl(), sb.toString(), pageIndex, pageSize);
		System.out.println("NXA Query---->"+(System.currentTimeMillis()-s)) ;
		return result ;
	}
	
	
	public List getObjById(DynamicDict dto) throws Exception {
		long s = System.currentTimeMillis() ;
		Map m = (Map)dto.getValueByName("parameter") ;
		
		if(m.get("id") == null )
			return new ArrayList();
		String id = (String)m.get("id");
		if("".equals(id))
			return new ArrayList();
		
		List list = new TMasterDAOImpl().findByCond(" id="+id) ;
		System.out.println("NXA getObjById(IAction)---->"+(System.currentTimeMillis()-s)) ;
		return list ;
	}
	
	public boolean delete(DynamicDict dto) throws Exception {
		long s = System.currentTimeMillis() ;
		Map param = (Map) dto.getValueByName("parameter") ;
		String id = (String)param.get("id") ;
		String sid = (String)param.get("sid") ;
		
		if(id == null || "".equals(id.trim()) ||
				sid == null || "".equals(sid.trim()))
			return false ;

		boolean result = new TMasterDAOImpl().deleteByCond(" id="+id) > 0 
			&& new TSecondDAOImpl().deleteByCond(" id="+sid) > 0  ;
			System.out.println("NXA delete(IAction)---->"+(System.currentTimeMillis()-s)) ;
		return result ;
			
	}
} 
