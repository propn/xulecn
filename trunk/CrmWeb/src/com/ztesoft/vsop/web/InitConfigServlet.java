package com.ztesoft.vsop.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.vsop.LegacyDAOUtil;

public class InitConfigServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(InitConfigServlet.class);


	public InitConfigServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Logger log = Logger.getLogger(InitConfigServlet.class.getName());
		log.info("refresh config begin......");
		
		String objectType=request.getParameter("objectType");//��������
		String objectId=request.getParameter("objectId");//��������ʶ
		String str="";
		
		response.setContentType("text/html;charset=GBK");
		PrintWriter out = response.getWriter();
		if(objectType!=null&&!"".equals(objectType)&&objectId!=null&&!"".equals(objectId)){
			this.refreshCacheIncrement(objectType, objectId);
			str+="����ˢ�»������ݳɹ�";
			logger.info(str+",objectType="+objectType+",objectId="+objectId);
		}else{//�������δ����Ĭ��ȫ��ˢ��
			this.refreshCache();
			str+="ȫ��ˢ�»������ݳɹ�";
			logger.info(str+",objectType="+objectType+",objectId="+objectId);
		}
		out.print(str);
		out.flush();
		out.close();
	}
	/**
	 * ȫ��ˢ�»���
	 */
	public void refreshCache(){
		getConfig();
		getOtherConfig();
		logger.info("ˢ�����ò���");
	}
	/**
	 * ����ˢ�»���
	 * @param objectType 0��Ʒ or 1����Ʒ or 2��Ʒ�ṩ��(spcp)
     * @param objectId   product.product_id or  prod_offer.prod_offer_id  or partner.partner_id
	 */
	public void refreshCacheIncrement(String objectType,String objectId){
		Connection conn = null ;
		try {
			DcSystemParamManager.getInstance().getIncrementData(objectType, objectId);
		}catch (Exception se) {
			se.printStackTrace();
			logger.error("",se);
		}finally {
			LegacyDAOUtil.releaseConnection();
		}
			
	}
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException { 
		System.out.println("###�������ݿ���������");
		this.refreshCache();
		System.out.println("###�������ݿ��������ݳɹ�");
	}
	private void getOtherConfig(){
		Connection conn = null ;
		try {
			DcSystemParamManager.getInstance().getAllData();
		}catch (Exception se) {
			logger.error("",se);
		}finally {
			LegacyDAOUtil.releaseConnection();
		}
			
	}
	private void getConfig(){
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet rs = null ;
		String sql = "select param_code,param_val  from dc_system_param ";
		//String sql="select  param_code, param_value from ua_config"; 
		if(DcSystemParamManager.getInstance().getConfigMap()==null){
			DcSystemParamManager.getInstance().setConfigMap(new HashMap());
		}
		try {
			conn = LegacyDAOUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while( rs.next() ){
				String key=rs.getString("param_code");
				String value=rs.getString("param_val");
				DcSystemParamManager.getInstance().getConfigMap().put(key, value);
				System.out.println("key: "+key+"  value: "+value);
			}
		} catch (SQLException se) {
			Debug.print(sql, this);
			logger.error("��ȡ��̬����sql["+sql+"]���ݿ�ϵͳ�쳣");
			//LegacyDAOUtil.releaseConnection(conn);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			LegacyDAOUtil.releaseConnection();
		}
	}
	 
}
