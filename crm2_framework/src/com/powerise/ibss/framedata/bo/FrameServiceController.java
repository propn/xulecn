package com.powerise.ibss.framedata.bo;

import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framedata.dao.TfmActArgsDAO;
import com.powerise.ibss.framedata.dao.TfmServExtDAO;
import com.powerise.ibss.framedata.dao.TfmServRelationDAO;
import com.powerise.ibss.framedata.dao.TfmServicesDAO;
import com.powerise.ibss.framedata.vo.SQLArgs;
import com.powerise.ibss.framedata.vo.TfmActArgsVO;
import com.powerise.ibss.framedata.vo.TfmServRelationVO;
import com.powerise.ibss.framedata.vo.TfmServicesVO;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.framework.LogHelper;
import com.powerise.ibss.framework.SQLAction;
import com.powerise.ibss.util.IActionCache;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dict.DataTranslate;

public class FrameServiceController {

	//	private List iservices = new ArrayList() ;
	private Map iserviceMap = new HashMap();

	private static final String AUTOM = "1";//1 业务原子类

	private static final String QUERY = "3";//3 查询类

	private static final String COMB = "2";//2 业务组合类

	private FrameServiceController() {
		try {
			loadAllService();
		} catch (FrameException e) {
			e.printStackTrace();
		}
	}

	public static FrameServiceController getInstance() {
		return SingletonHolder.instatnce;
	}

	/**
	 * 根据服务名获取服务
	 * @param serviceName
	 * @return
	 * @throws FrameException
	 */
	public TfmServicesVO getServiceByName(String serviceName)
			throws FrameException {
		if (serviceName == null || "".equals(serviceName.trim()))
			throw new FrameException("传递服务名不能为空，请确认！");

		TfmServicesVO vo = null;
		//不存在，加载数据库数据
		serviceName = serviceName.toUpperCase() ;
		if (this.iserviceMap.get(serviceName) != null) {
			vo = (TfmServicesVO) this.iserviceMap.get(serviceName);
		} else {
			vo = loadServiceFromDB(serviceName);
		}
		return vo;
	}

	/**
	 * 缓存中清除服务
	 * @param serviceName
	 */
	public void removeService(String serviceName) {
		this.iserviceMap.remove(serviceName);
	}

	/**
	 * 服务调用
	 * @param serviceName
	 * @param dto
	 * @throws FrameException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void callService(String serviceName, DynamicDict dto)
			throws Exception {
		TfmServicesVO v = this.getServiceByName(serviceName);
		execService(v, dto);
	}

	/**
	 * 服务调用
	 * @param serviceName
	 * @param dto
	 * @throws FrameException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void callService(TfmServicesVO v, DynamicDict dto)
			throws Exception {
		execService(v, dto);
	}

	static class SingletonHolder {
		static FrameServiceController instatnce = new FrameServiceController();
	}

	/**
	 * 加载所用服务
	 * @throws FrameException
	 */
	private void loadAllService() throws FrameException {
		TfmServicesDAO dao = new TfmServicesDAO();
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		whereCond.append(" and state=? ");
		para.add("1");
		
		whereCond.append(" and cache_flag=? ");
		para.add("1");

		whereCond.append(" and if_log=? ");
		para.add("1");
		
		List services = dao.findByCond(whereCond.toString(), para);
		//关闭连接

		//转成VO处理
		toTfmServicesVO(services);

		//组合关系树 servie_type=2
		getServiceCombTree();

		//查询SQL servie_type=3
		loadSQLTypeData();

		dbProc(dao);
	}

	private void dbProc(AbstractDAO dao) {
//		try {
//
//			ConnectionContext.getContext().commit(dao.getDbName());
//			ConnectionContext.getContext().closeConnection(dao.getDbName());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void putToMap(TfmServicesVO v) {
		this.iserviceMap.put(v.getService_name(), v);
	}

	private void getServiceCombTree() throws FrameException {
		if (iserviceMap == null || iserviceMap.isEmpty())
			return;

		Iterator it = iserviceMap.values().iterator();
		while (it.hasNext()) {
			TfmServicesVO serviceVO = (TfmServicesVO) it.next();
			//组合类型服务
			if (COMB.equals(serviceVO.getService_type())) {
				List servRelationList = loadTfmServRelationByServiceName(serviceVO
						.getService_name());
				serviceVO.setTfmServRelationVOList(servRelationList);
			}
		}
	}

	/**
	 * HashMap > VO
	 * @param tfmServicesMaps
	 */
	private void toTfmServicesVO(List tfmServicesMaps) {
		if (tfmServicesMaps == null || tfmServicesMaps.isEmpty())
			return;
		for (int i = 0, j = tfmServicesMaps.size(); i < j; i++) {
			Map tfmServicesMap = (Map) tfmServicesMaps.get(i);
			TfmServicesVO tfmServicesVO = TfmServicesVO
					.getVOFromHashMap(tfmServicesMap);
			iserviceMap.put(tfmServicesVO.getService_name(), tfmServicesVO);
		}
	}

	private List loadTfmServRelationByServiceName(String serviceName)
			throws FrameException {
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		whereCond.append(" and service_name= ? ");
		para.add(serviceName);

		//调用DAO代码
		TfmServRelationDAO dao = new TfmServRelationDAO();
		List result = dao.findByCond(whereCond.toString(), para);
		//转成VO
		result = toTfmServRelationVO(result);
		return result;
	}

	/**
	 * HashMap > VO
	 * @param tfmServicesMaps
	 */
	private List toTfmServRelationVO(List tfmServRelationMaps) {
		if (tfmServRelationMaps == null || tfmServRelationMaps.isEmpty())
			return null;
		List temp = new ArrayList();
		for (int i = 0, j = tfmServRelationMaps.size(); i < j; i++) {
			Map tfmServicesMap = (Map) tfmServRelationMaps.get(i);
			TfmServRelationVO tfmServRelationVO = TfmServRelationVO
					.getVOFromHashMap(tfmServicesMap);
			temp.add(tfmServRelationVO);
		}
		return temp;
	}

	private void loadSQLTypeData() throws FrameException {
		if (this.iserviceMap == null || iserviceMap.isEmpty())
			return;

		Iterator it = iserviceMap.values().iterator();
		while (it.hasNext()) {
			TfmServicesVO serviceVO = (TfmServicesVO) it.next();
			//SQL查询类型服务
			if (QUERY.equals(serviceVO.getService_type())) {
				String serviceName = serviceVO.getService_name();
				String sql = loadTfmServExtByServiceName(serviceName);
				if (sql == null || "".equals(sql.trim()))
					continue;
				String[] sqlArr = sql.split("#");
				List args = loadTfmServArgs(serviceName);
				SQLArgs sqlArgs = new SQLArgs(sqlArr[0], args, sqlArr[1]);
				serviceVO.setSqlArgs(sqlArgs);
			}
		}
	}

	private String loadTfmServExtByServiceName(String serviceName)
			throws FrameException {
		TfmServicesVO service = TfmServicesVO.getService(serviceName);
		if (service == null)
			return "";
		if (!QUERY.equals(service.getService_type()))
			return "";

		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		whereCond.append(" and service_name= ? ").append(" order by seq asc");
		para.add(serviceName);

		//调用DAO代码
		TfmServExtDAO dao = new TfmServExtDAO();
		List tresult = dao.findByCond(whereCond.toString(), para);
		if (tresult == null || tresult.isEmpty())
			return "";
		StringBuffer result = new StringBuffer();
		String actionType = "4";
		for (int i = 0, j = tresult.size(); i < j; i++) {
			Map m = (Map) tresult.get(i);
			actionType = (String) m.get("action_type");
			result.append((String) m.get("section"));
		}
		return result.toString() + "#" + actionType;
	}

	private List loadTfmServArgs(String serviceName) throws FrameException {
		TfmServicesVO service = TfmServicesVO.getService(serviceName);
		if (service == null)
			return null;
		if (!QUERY.equals(service.getService_type()))
			return null;

		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		whereCond.append(" and action_id= ? order by arg_seq asc ");
		para.add(serviceName);
		//调用DAO代码
		TfmActArgsDAO dao = new TfmActArgsDAO();
		List result = dao.findByCond(whereCond.toString(), para);
		return toTfmServArgsVO(result);
	}

	/**
	 * HashMap > VO
	 * @param tfmServicesMaps
	 */
	private List toTfmServArgsVO(List tfmServArgsMaps) {
		if (tfmServArgsMaps == null || tfmServArgsMaps.isEmpty())
			return null;
		List temp = new ArrayList();
		for (int i = 0, j = tfmServArgsMaps.size(); i < j; i++) {
			Map tfmServicesMap = (Map) tfmServArgsMaps.get(i);
			TfmActArgsVO argsVO = TfmActArgsVO.getVOFromHashMap(tfmServicesMap);
			temp.add(argsVO);
		}
		return temp;
	}

	private TfmServicesVO loadServiceFromDB(String serviceName)
			throws FrameException {
		TfmServicesDAO dao = new TfmServicesDAO();
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();

		whereCond.append(" and state=? ");
		para.add("1");

		whereCond.append(" and service_name=? ");
		para.add(serviceName);

		List services = dao.findByCond(whereCond.toString(), para);
		if (services == null || services.isEmpty())
			throw new FrameException("库表中没service_name=" + serviceName
					+ "的数据记录！");

		Map ms = (Map) services.get(0);
		TfmServicesVO serviceVO = TfmServicesVO.getVOFromHashMap(ms);

		//组合关系树 servie_type=2
		if (COMB.equals(serviceVO.getService_type())) {
			List servRelationList = loadTfmServRelationByServiceName(serviceVO
					.getService_name());
			serviceVO.setTfmServRelationVOList(servRelationList);
		}

		//查询SQL servie_type=3
		if (QUERY.equals(serviceVO.getService_type())) {
			String sql = loadTfmServExtByServiceName(serviceName);
			if (sql != null && !"".equals(sql.trim())) {
				String[] sqlArr = sql.split("#");
				List args = loadTfmServArgs(serviceName);
				SQLArgs sqlArgs = new SQLArgs(sqlArr[0], args, sqlArr[1]);
				serviceVO.setSqlArgs(sqlArgs);
			}
		}
		//存储于Map
		putToMap(serviceVO);
		dbProc(dao);
		return serviceVO;
	}

	private void doSomething(String serviceName) throws Exception {
		Map m = this.iserviceMap;
		DynamicDict dto = new DynamicDict();
		try {
			dto.setValueByName("target....", "let's go!");
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TfmServicesVO v = (TfmServicesVO) m.get(serviceName);
		execService(v, dto);
	}

	private void execute(TfmServicesVO v, DynamicDict dto)
			throws Exception{
		String className = v.getClassName();
		String serviceName = v.getService_name();
		IAction action = IActionCache.getInstance().get(serviceName);
		if (action == null) {
			System.out.println("className==="+className+",serviceName=="+serviceName) ;
			action = (IAction) Class.forName(className).newInstance();
			IActionCache.getInstance().put(serviceName, action);
		}
		action.perform(dto);
	}

	/**
	 * 配置SQL方式处理方法
	 * @param dict
	 * @param strDBName
	 * @return
	 * 
	 */
	public DynamicDict execQueryTypeService(DynamicDict dict, TfmServicesVO v)
			throws FrameException {
		SQLArgs sqlArgs = v.getSqlArgs();
		if (sqlArgs == null)
			throw new FrameException("服务:" + v.getService_name()
					+ ",对应的执行SQL对象(SQLArgs)为空!");
		String sql = sqlArgs.getSQL();
		if (sql == null || "".equals(sql.trim()))
			throw new FrameException("服务:" + v.getService_name()
					+ ",对应的执行SQL为空!");

		try {
			SQLAction action = new SQLAction(dict, v);
			action.execute();
		} catch (FrameException fre) {
			dict.flag = fre.getErrorCode();
			dict.msg = fre.getErrorMsg();
			dict.exception = fre.getSysMsg() + "\n"
					+ LogHelper.getStackMsg(fre);
		} catch (java.lang.Throwable th) {
			dict.flag = -22990004;
			dict.msg = "系统调用异常:" + th.getClass().getName();
			dict.exception = LogHelper.getStackMsg(th);
		}
		return dict;
	}

	private static boolean getBooleanFromDto(DynamicDict dto, String key) {
		boolean v = false;
		try {
			System.out.println("check===(" + key + "=="
					+ dto.getValueByName(key) + ")");
			Object o = dto.getValueByName(key);
			if (o == null)
				return v;
			String cn = o.getClass().getName();
			if (cn.equals("java.lang.String")) {
				if ("true".equals((String) o)) {
					return true;
				} else {
					return false;
				}
			}

			return DataTranslate._boolean(o);
		} catch (FrameException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void execService(TfmServicesVO v, DynamicDict dto)
			throws Exception {
		String type = v.getService_type();
		//原子类型
		if (AUTOM.equals(type)) {
			execute(v, dto);
		} else if (COMB.equals(type)) {
			List mi = v.getTfmServRelationVOList();
			if (mi != null && !mi.isEmpty()) {
				for (int i = 0, j = mi.size(); i < j; i++) {
					TfmServRelationVO trv = (TfmServRelationVO) mi.get(i);
					String f = trv.getCondition_flag();//1需要条件 0 不用，默认为0

					if (AUTOM.equals(f)) {
						TfmServicesVO condv = trv.getCondService();
						execute(condv, dto);
						boolean check = getBooleanFromDto(dto, trv
								.getCond_arg_name());
						if (check) {
							TfmServicesVO cv = trv.getCoService();
							execute(cv, dto);
						}
					} else {
						TfmServicesVO cv = trv.getCoService();
						execute(cv, dto);
					}
				}
			}
		} else if (dto.flag == 1 || QUERY.equals(type)) {
			execQueryTypeService(dto, v);
		}
	}

}
