package fortest.xa.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.PageHelper;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;

import fortest.xa.dao.impl.TMasterDAOImpl;
import fortest.xa.dao.impl.TSecondDAOImpl;
import fortest.xa.vo.TMasterVO;
import fortest.xa.vo.TSecondVO;

public class XABO extends DictAction {
	private static  long SEQ_T_MASTER = 1 ;
	private static  long SEQ_T_SECOND = 1 ;
	
	public boolean insert(DynamicDict dto ) throws Exception {
		long s = System.currentTimeMillis() ;
//		Map param = (Map) dto.getValueByName("parameter") ;
		Map param = Const.getParam(dto) ;
		
		dto.getValueByName("");
		TSecondVO svo = (TSecondVO)param.get("TSecondVO") ;
		TMasterVO tvo = (TMasterVO)param.get("TMasterVO") ;
		svo.setSid(Long.toString(SEQ_T_SECOND++)) ;
		svo.setFId(Long.toString(SEQ_T_MASTER)) ;
		
		tvo.setId(Long.toString(SEQ_T_MASTER)) ;
		SEQ_T_MASTER ++ ;
		
		new TMasterDAOImpl().insert(tvo);
		new TSecondDAOImpl().insert(svo) ;
		System.out.println("XA insert---->"+(System.currentTimeMillis()-s)) ;
		return true ;
	}

	
	public boolean update(DynamicDict dto) throws Exception {
		long s = System.currentTimeMillis() ;
		Map param = (Map) dto.getValueByName("parameter") ;
		TSecondVO svo = (TSecondVO)param.get("TSecondVO") ;
		TMasterVO tvo = (TMasterVO)param.get("TMasterVO") ;
		
		boolean result =  new TMasterDAOImpl().update(" id="+tvo.getId(), tvo)  
			&& new TSecondDAOImpl().update(" id="+svo.getSid(), svo)  ;
		System.out.println("XA update---->"+(System.currentTimeMillis()-s)) ;
		return result ;
	}
	
	
	public PageModel searchData(DynamicDict dto) throws Exception {
		long s = System.currentTimeMillis() ;
		Map m = (Map)dto.getValueByName("parameter") ;
		
		int pageIndex  = ((Integer)m.get("pageIndex")).intValue();
		int pageSize  = ((Integer)m.get("pageSize")).intValue();
		StringBuffer sb = new StringBuffer(" 1=1 ") ;
		if(m.get("mName") != null ){
			String mName = (String)m.get("mName") ;
			if(!"".equals(mName.trim()))
			sb.append(" and m_name like '%"+m.get("mName")+"%'") ;
		}
		
		PageModel result = PageHelper.popupPageModel(new TMasterDAOImpl(), sb.toString(), pageIndex, pageSize);
		System.out.println("XA Query---->"+(System.currentTimeMillis()-s)) ;
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
		
		List list =  new TMasterDAOImpl().findByCond(" id="+id) ;
		System.out.println("XA getObjById---->"+(System.currentTimeMillis()-s)) ;
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
			System.out.println("XA delete---->"+(System.currentTimeMillis()-s)) ;
		return result ;
			
	}
	
} 
