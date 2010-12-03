package com.ztesoft.crm.business.accept.reception.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;

public class CustReceptionDAO extends AbstractDAO {
		
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.ORD_DATASOURCE ;

	//根据客户编号查询销售品实例。
	private static final String QUERY_CUST_OFFER_INSTANCE = 
		  " select product_offer_instance_id, offer_id, offer_kind, offer_name, product_id, serv_id			  " +
	      "   from (select product_offer_instance_id, offer_id, offer_kind, offer_name,						  " +
	      "				product_id, serv_id, rownum counts       											  " +
	      "           from (select a.product_offer_instance_id, c.offer_id, a.offer_kind, c.offer_name,		  " +
	      "						null product_id, null serv_id										          " +
	      "                   from offer_inst a, product_offer c                                              " +
	      "                  where a.cust_id = ?                                                              " +
	      "                    and a.offer_kind = '1'                                                         " +
	      "                    and a.product_offer_id = c.offer_id                                            " +
	      //"					   and a.state = '00A'															  " +
	      "                 union                                                                             " +
	      "                 select a.product_offer_instance_id, c.offer_id, a.offer_kind, 					  " +
	      "						c.offer_name, d.product_id, d.serv_id								          " +
	      "                   from offer_inst a, offer_INST_DETAIL b, product_offer c, serv d                 " +
	      "                  where a.product_offer_instance_id = b.product_offer_instance_id                  " +          
	      "                    and a.cust_id = ?                                                              " +
	      "                    and b.instance_type = '10A'                                                    " +
	      "                    and a.offer_kind = '0'                                                         " +
	      "                    and a.product_offer_id = c.offer_id                                            " +
	      //"					   and a.state = '00A'															  " +
	      "					   and b.instance_id = d.serv_id												  " +
	      "                    and not exists                                                                 " +
	      "                  (select 1                                                                        " +
	      "                           from offer_inst a1, offer_INST_DETAIL b1, serv c1                       " +
	      "                          where a1.product_offer_instance_id = b1.product_offer_instance_id        " +
	      "                            and a1.cust_id = ?                                                     " +
	      "                            and b1.instance_type = '10A'                                           " +
	      "                            and a1.offer_kind = '1'                                                " +
	      //"							   and a1.state = '00A'  												  " +
	      "							   and c1.serv_id = b1.instance_id 										  " +
	      "							   and b1.instance_type='10A' 											  " +
	      "							   and c1.serv_id=d.serv_id))				                              " +
	      "          where rownum <= ?                                                                        " +
	      "          order by product_offer_instance_id)                                                      " +
	      "  where counts > ?                                                                                 " ;
	
	//根据销售品实例查询产品实例
	private static final String QUERY_SERV_INSTANCE_BY_OFFER = 
			" select distinct a.instance_id, b.product_id, c.product_name, b.acc_nbr,b.comp_inst_id,e.product_offer_id    " +
			"   from offer_inst_detail a, serv b, PRODUCT c, offer_inst d,offer_inst e  " +
			"  where a.instance_type = '10A'                                            " +
			"    and a.instance_id = b.serv_id                                          " +
			"    and b.product_id = c.product_id                                        " +
			"    and d.comp_inst_id = ?                                                 " +
			"    and d.product_offer_instance_id = a.product_offer_instance_id			" +
			"	 and e.product_offer_instance_id=b.comp_inst_id          				";
		
	//根据商品规格查询服务提供
	private static final String QUERY_PRODUCT_OFFER_SERVICE = 
			" select a.service_offer_id id, b.service_offer_name name                            " +
			"   from PRODUCT_OFFER_SERVICE a, SERVICE_OFFER b	                                 " +
			"	  where a.offer_id = ?   and a.service_offer_id = b.service_offer_id             " +
			//"		and a.state='00A' 															 " +
			"	  order by a.order_id										 					 ";
	
	private static final String QUERY_OFFER_ADN_PRODUCT_SERVICE =
			" select id, name														 " +
			"   from (select a.service_offer_id id, b.service_offer_name name        " +
			"           from PRODUCT_OFFER_SERVICE a, SERVICE_OFFER b                " +
			"          where a.offer_id = ?                                          " +
			"            and a.service_offer_id = b.service_offer_id                 " +
			//"            and a.state = '00A'                                         " +
			"          order by a.ORDER_ID)                                          " +
			" union all                                                              " +
			" select id, name                                                        " +
			"   from (select a.service_offer_id id, b.service_offer_name name        " +
			"           from PRODUCT_SERVICE_RELATION a, SERVICE_OFFER b             " +
			"          where a.product_id = ?                                        " +
			"            and a.service_offer_id = b.service_offer_id                 " +
			//"            and a.state = '00A'                                         " +
			"          order by a.ORDER_ID)                                          ";
	
	//查询销售品名称和类型。
	private static final String QUERY_PRODUCT_OFFER_BY_OFFER_ID = 
			" select a.offer_name,a.offer_kind from product_offer a where a.offer_id=? and rownum =1 ";
	
	//根据产品号码和客户标识，查询产品号码对应的优惠销售品
	private static final String QUERY_YH_OFFER_BY_ACCNBR = 
			" select product_offer_instance_id, product_offer_id, offer_kind, offer_name, product_id, serv_id     " +
			"   from (select product_offer_instance_id, product_offer_id, offer_kind,                    " +
			"                offer_name,product_id, serv_id, rownum counts                               " +
			"           from (select distinct a1.product_offer_instance_id, a1.product_offer_id,         " +
			"                                 a1.offer_kind, c1.offer_name,b1.product_id, b1.serv_id     " +
			"                   from offer_inst a1, product_offer c1,                                    " +
			"                        (select c.comp_inst_id, a.product_id,a.serv_id                      " +
			"                           from serv a, offer_inst_detail b, offer_inst c                   " +
			"                          where a.serv_id = b.instance_id                                   " +
			"                            and b.instance_type = '10A'                                     " +
			"                            and b.product_offer_instance_id = c.product_offer_instance_id   " +
			"                            and a.acc_nbr = ?                                               " +
			"                            and a.cust_id = ? ) b1                                          " +
			"                  where a1.product_offer_instance_id = b1.comp_inst_id                      " +
			"                    and a1.offer_kind = ?                                                   " +
			"                    and a1.product_offer_id = c1.offer_id)                                  " +
			"          where rownum <= ?                                                                 " +
			"          order by product_offer_instance_id)                                               " +
			"  where counts > ?                                                                          " ;
	
	//根据号码查询客户的所有实例信息
	private static final String QUERY_CUST_OFFER_INSTANCE_BY_ACC_NBR =
			" select product_offer_instance_id, offer_id, offer_kind, offer_name, product_id, serv_id " +
			"   from (select product_offer_instance_id, offer_id, offer_kind, offer_name,             " +
			"                	product_id, serv_id, rownum counts                                    " +
			"           from (select product_offer_instance_id, offer_id, offer_kind,                 " +
			"                        	offer_name,product_id,serv_id                                 " +
			"                   from (select distinct a1.product_offer_instance_id,                   " +
			"                                         c1.offer_id, a1.offer_kind,					  " +
			"                                         c1.offer_name, b1.product_id,b1.serv_id         " +
			"                           from offer_inst a1,                                           " +
			"                                product_offer c1,                                        " +
			"                                (select c.comp_inst_id,a.product_id ,a.serv_id           " +
			"                                   from serv              a,                             " +
			"                                        offer_inst_detail b,                             " +
			"                                        offer_inst        c                              " +
			"                                  where a.serv_id = b.instance_id                        " +
			"                                    and b.instance_type = '10A'                          " +
			"                                    and b.product_offer_instance_id =                    " +
			"                                        c.product_offer_instance_id                      " +
			"                                    and a.acc_nbr = ?                                    " +
			"                                    and a.cust_id = ?) b1                                " +
			"                          where a1.product_offer_instance_id = b1.comp_inst_id           " +
			"                            and a1.offer_kind = '1'                                      " +
			"                            and a1.product_offer_id = c1.offer_id                        " +
			"                         union all                                                       " +
			"                         select distinct a1.product_offer_instance_id,                   " +
			"                                         a1.product_offer_id, a1.offer_kind,             " +
			"                                         c1.offer_name, b1.product_id, b1.serv_id        " +
			"                           from offer_inst a1,                                           " +
			"                                product_offer c1,                                        " +
			"                                (select c.comp_inst_id, a.product_id,a.serv_id           " +
			"                                   from serv              a,                             " +
			"                                        offer_inst_detail b,                             " +
			"                                        offer_inst        c                              " +
			"                                  where a.serv_id = b.instance_id                        " +
			"                                    and b.instance_type = '10A'                          " +
			"                                    and b.product_offer_instance_id =                    " +
			"                                        c.product_offer_instance_id                      " +
			"                                    and a.acc_nbr = ?                                    " +
			"                                    and a.cust_id = ?) b1                                " +
			"                          where a1.product_offer_instance_id = b1.comp_inst_id           " +
			"                            and a1.offer_kind = '0'                                      " +
			"                            and a1.product_offer_id = c1.offer_id                        " +
			"                            and not exists                                               " +
			"                          (select 1						                              " +
			"                                   from offer_inst a1,                                   " +
			"                                        (select c.comp_inst_id                           " +
			"                                           from serv              a,                     " +
			"                                                offer_inst_detail b,                     " +
			"                                                offer_inst        c                      " +
			"                                          where a.serv_id = b.instance_id                " +
			"                                            and b.instance_type = '10A'                  " +
			"                                            and b.product_offer_instance_id =            " +
			"                                                c.product_offer_instance_id              " +
			"                                            and a.acc_nbr = ?                            " +
			"                                            and a.cust_id = ?) b1                        " +
			"                                  where a1.product_offer_instance_id =                   " +
			"                                        b1.comp_inst_id                                  " +
			"                                    and a1.offer_kind = '1'))                            " +
			"                 union all                                                               " +
			"                 select product_offer_instance_id, offer_id, offer_kind,                 " +
			"                        	offer_name, product_id, serv_id                               " +
			"                   from (select a.product_offer_instance_id, c.offer_id,                 " +
			"                                a.offer_kind, c.offer_name, null product_id,null serv_id " +
			"                           from offer_inst a, product_offer c                            " +
			"                          where a.cust_id = ?                                            " +
			"                            and a.offer_kind = '1'                                       " +
			"                            and a.product_offer_id = c.offer_id                          " +
			//"                            and a.state = '00A'                                          " +
			"                         union all                                                       " +
			"                         select a.product_offer_instance_id, c.offer_id,                 " +
			"                                a.offer_kind, c.offer_name, d.product_id,d.serv_id       " +
			"                           from offer_inst        a,                                     " +
			"                                offer_INST_DETAIL b,                                     " +
			"                                product_offer     c,									  " +
			"								 serv   		   d                                      " +
			"                          where a.product_offer_instance_id =                            " +
			"                                b.product_offer_instance_id                              " +
			"                            and a.cust_id = ?                                            " +
			"                            and b.instance_type = '10A'                                  " +
			"                            and a.offer_kind = '0'                                       " +
			"                            and a.product_offer_id = c.offer_id                          " +
			//"                            and a.state = '00A'                                          " +
			"							 and b.instance_id = d.serv_id								  " +
			"                            and not exists                                               " +
			"                  		(select 1                                                         " +
			"                           from offer_inst a1, offer_INST_DETAIL b1, serv c1             " +
			"                          where a1.product_offer_instance_id = b1.product_offer_instance_id " +
			"                            and a1.cust_id = ?                                           " +
			"                            and b1.instance_type = '10A'                                 " +
			"                            and a1.offer_kind = '1'                                      " +
			//"							   and a1.state = '00A'  									  " +
			"							   and c1.serv_id = b1.instance_id 							  " +
			"							   and b1.instance_type='10A' 								  " +
			"							   and c1.serv_id=d.serv_id)) tab1		                      " +
			"                  where not exists                                                       " +
			"                  (select c.comp_inst_id                                                 " +
			"                           from serv a, offer_inst_detail b, offer_inst c                " +
			"                          where a.serv_id = b.instance_id                                " +
			"                            and b.instance_type = '10A'                                  " +
			"                            and b.product_offer_instance_id =                            " +
			"                                c.product_offer_instance_id                              " +
			"                            and a.acc_nbr = ?                                            " +
			"                            and a.cust_id = ?                                            " +
			"                            and c.comp_inst_id = tab1.product_offer_instance_id))        " +
			"          where rownum <= ?)                                                            " +
			"  where counts > ?                                                                       ";

	
	//根据用户标识，查询附属产品名称
	private static final String QUERY_SERV_PRODUCT_NAME =
			" select a.serv_product_id, a.product_id, b.product_name    " +
			"   from serv_product a, product b                          " +
			"  where a.serv_id = ?                                      " +
			"    and a.product_id = b.product_id                        ";

	//根据销售品标识，查询可选包名称
	private static final String QUERY_OFFER_SELECT_PACKAGE = 
			" select b.offer_id, b.offer_name || '(' || b.offer_comments || ')' offer_name     " +
			"   from offer_inst a, product_offer b                                  " +
			"  where a.comp_inst_id <> a.product_offer_instance_id                  " +
			"    and a.comp_inst_id = ?		                                        " +
			"    and a.product_offer_id = b.offer_id                                ";
	
	//根据客户标识，查询未确认的定单信息。
	private static final String QUERY_ACCEPT_ASK_BY_CUST_ID = 
			" select cust_ord_id,ord_id,ord_type,instance_type,instance_type_id,instance_id,service_offer_id,state,ask_id " +
			"   from ( "+
			"	select cust_ord_id,ord_id,ord_type,instance_type,instance_type_id,instance_id,service_offer_id,state,ask_id ,rownum counts " +
			"	  from ( "+
			"		select b.cust_ord_id, b.ord_id,b.ord_type,b.instance_type,b.instance_type_id,b.instance_id,b.service_offer_id,b.state,b.ask_id "+
			"		  from ord_customer_order a , ord_ask b "+ 
			"	  	  where a.cust_id = ? and a.cust_ord_id = b.cust_ord_id and b.ord_id=b.ask_id and b.state ='100' "+
			"	  ) where rownum <=? order by ord_id "+
			"   ) where counts > ? ";
	
	//根据客户标识，查询在途的定单信息。
	private static final String QUERY_NOT_FINISHED_ASK_BY_CUST_ID = 
			" select cust_ord_id,ord_id,ord_type,instance_type,instance_type_id,instance_id,service_offer_id,state,ask_id " +
			"   from ( "+
			"	select cust_ord_id,ord_id,ord_type,instance_type,instance_type_id,instance_id,service_offer_id,state,ask_id ,rownum counts " +
			"	  from ( "+
			"		select b.cust_ord_id, b.ord_id,b.ord_type,b.instance_type,b.instance_type_id,b.instance_id,b.service_offer_id,b.state,b.ask_id "+
			"		  from ord_customer_order a , ord_ask b "+ 
			"	  	  where a.cust_id = ? and a.cust_ord_id = b.cust_ord_id and b.ord_id=b.ask_id and b.state <>'100' "+
			"	  ) where rownum <=? order by ord_id "+
			"   ) where counts > ? ";
	
	public CustReceptionDAO() {

	}
	
	/**
	 * 查询客户的商品实例信息。
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList showOffInstance(ArrayList paramsList)throws FrameException{
		
		return (ArrayList)findBySql(QUERY_CUST_OFFER_INSTANCE, paramsList);
		
	}
	
	/**
	 * 根据产品号码查询时，查询出客户的实例信息，并且把产品号码相关的商品优先显示。
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList showOffInstanceByAccNbr(ArrayList paramsList)throws FrameException{
		
		return (ArrayList)findBySql(QUERY_CUST_OFFER_INSTANCE_BY_ACC_NBR, paramsList);
		
	}
	
	/**
	 * 根据销售品实例，查询销售品的构成。
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList showServInstance(ArrayList paramsList)throws FrameException{
		return (ArrayList)findBySql(QUERY_SERV_INSTANCE_BY_OFFER, paramsList);
	}
	
	/**
	 * 根据销售品规格标识，查询出销售品的服务提供
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList showOffService(ArrayList paramsList)throws FrameException{
		return (ArrayList)findBySql(QUERY_PRODUCT_OFFER_SERVICE, paramsList);
	}
	
	/**
	 * 根据基础销售品和产品规格，获取服务提供
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList showOffAndProdService(ArrayList paramsList)throws FrameException{
		return (ArrayList)findBySql(QUERY_OFFER_ADN_PRODUCT_SERVICE, paramsList);
	}
	
	/**
	 * 根据销售品规格标识，查询销售品信息。
	 * @param offerId
	 * @return
	 * @throws FrameException
	 */
	public ArrayList queryProductOfferByOfferId(String offerId) throws FrameException{
		ArrayList paramsList = new ArrayList();
		paramsList.add(offerId);
		return (ArrayList) findBySql(QUERY_PRODUCT_OFFER_BY_OFFER_ID, paramsList);
	}
	
	/**
	 * 根据客户标识、产品号码查询优惠销售品
	 * @param accNbr
	 * @param custId
	 * @param pageCount
	 * @param currentPages
	 * @return
	 * @throws FrameException
	 */
	public ArrayList queryYHOfferByAccNbr(String accNbr,String custId,int pageCount,int currentPages,String offerKind) throws FrameException{
		ArrayList paramsList = new ArrayList();
		paramsList.add(accNbr);
		paramsList.add(custId);
		paramsList.add(offerKind);
		paramsList.add(currentPages*pageCount+"");
		paramsList.add((currentPages-1)*pageCount+"");
		
		return (ArrayList) findBySql(QUERY_YH_OFFER_BY_ACCNBR, paramsList);
	}
	
	/**
	 * 根据用户实例标识，查询附属产品
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList queryServProductName(ArrayList paramsList)throws FrameException{
		return (ArrayList)findBySql(QUERY_SERV_PRODUCT_NAME, paramsList);
	}
	
	/**
	 * 根据销售品实例标识，查询可选包
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList queryOfferSelectPackage(ArrayList paramsList)throws FrameException{
		return (ArrayList)findBySql(QUERY_OFFER_SELECT_PACKAGE, paramsList);
	}
	
	/**
	 * 根据客户标识查询未确认定单信息。
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList queryAcceptAskByCustId(ArrayList paramsList)throws FrameException{
		return (ArrayList) findBySql(QUERY_ACCEPT_ASK_BY_CUST_ID, paramsList);
	}
	
	/**
	 * 根据客户标识查询在途定单信息。
	 * @param paramsList
	 * @return
	 * @throws FrameException
	 */
	public ArrayList queryNotFinishedAskByCustId(ArrayList paramsList)throws FrameException{
		return (ArrayList) findBySql(QUERY_NOT_FINISHED_ASK_BY_CUST_ID, paramsList);
	}
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return null ;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
					
		return null;
				
	}
	
	public String getSelectSQL(){
		return null ;
	}
	
	public String getSelectCountSQL(){
		return null ;
	}
	
	public String getInsertSQL(){
		return null ;
	}
	
	public String getUpdateSQL(){
		return null ;
	}
	
	public String getDeleteSQL(){
		return null ;
	}
	
	public String getSQLSQLByKey() throws FrameException {
					
		return null ;
				
	}
	
}
