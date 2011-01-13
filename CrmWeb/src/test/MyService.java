package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dict.ServiceManager;

/**
 * 
 * @author easonwu 2009-12-22
 * 
 */
/**
 1.����SQL
 create table t_user(id number , name varchar2(64) , age number) ;
 
 2.����service��action 
 	--���� service
 	INSERT INTO tfm_services(Service_Name,Module_Id,Service_Desc,Class_Name)
       VALUES('MYACTION' ,'1' ,'����Action����' ,'test.MyAction') ;
       
    --����action 
    
INSERT INTO tfm_action_list(action_id,seq,action_desc,action_type,action_clause)
       VALUES('GET_USERLIST',1,'����action��ʽȡֵ',4,
       'select u.id,u.name,u.age from t_user u where name like ''%a%'' and age=? and name=?') ;

INSERT INTO tfm_action_args(action_id,arg_seq,arg_data_type,
       arg_length,arg_name,in_out_flag)
       VALUES('GET_USERLIST' ,1,2,64,'name' , 1) ;
INSERT INTO tfm_action_args(action_id,arg_seq,arg_data_type,
       arg_length,arg_name,in_out_flag)
       VALUES('GET_USERLIST' ,2,2,18,'age' , 1) ;   
           
  3.��д����  
       service>action 
 */
public class MyService {
	/**
	 * ����JavaBean��ʽ
	 * @param name
	 * @param age
	 * @return
	 * @throws Exception
	 */
	public  String testService(String name , String age) throws Exception {
		
		//���ò���,�Ѳ�����װ��HashMap��
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("age", age) ;
		
		//JavaBean��ʽ,����ServiceManager.callJavaBeanService(service , method , param)����
		Object obj = ServiceManager.callJavaBeanService("MyAction", "testService" , param) ;
		return obj == null ? "" : (String) obj ;
	}
	
	/**
	 * ����SQL��ʽ
	 * @param name
	 * @param age
	 * @return
	 * @throws Exception
	 */
	public  List testActionSQl(String name , String age) throws Exception {
		//���ò���,�Ѳ�����װ��HashMap��
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("age", age) ;
		
		//SQL��ʽ,����ServiceManager.callSQLService(service  , param)����
		Object result = ServiceManager.callSQLService("GET_USERLIST", param) ;
		return result == null ? null : (List) result ;
	}
	
	/**
	 * ����
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args ) throws Exception{
		MyService ft = new MyService() ;
		
		//service��ʽ �������
		String sResult = ft.testService("easonwu" , "1") ;
		System.out.println("��������====="+sResult) ;
		
		//System.out.println("��������====="+ft.checkStaffDefaultPriv("9") ) ;

		//action��ʽ �������
		List result = ft.testActionSQl("easonwu" , "1") ;
		for(int i = 0 ,k = result.size() ; i<k ; i++){
			Map m = (Map) result.get(i) ;
			System.out.println("[id="+m.get("ID") +" , name="+m.get("NAME")+",age="+m.get("AGE") +"]") ;
		}
	}
	
}
