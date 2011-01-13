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
 1.建表SQL
 create table t_user(id number , name varchar2(64) , age number) ;
 
 2.配置service、action 
 	--配置 service
 	INSERT INTO tfm_services(Service_Name,Module_Id,Service_Desc,Class_Name)
       VALUES('MYACTION' ,'1' ,'测试Action配置' ,'test.MyAction') ;
       
    --配置action 
    
INSERT INTO tfm_action_list(action_id,seq,action_desc,action_type,action_clause)
       VALUES('GET_USERLIST',1,'测试action方式取值',4,
       'select u.id,u.name,u.age from t_user u where name like ''%a%'' and age=? and name=?') ;

INSERT INTO tfm_action_args(action_id,arg_seq,arg_data_type,
       arg_length,arg_name,in_out_flag)
       VALUES('GET_USERLIST' ,1,2,64,'name' , 1) ;
INSERT INTO tfm_action_args(action_id,arg_seq,arg_data_type,
       arg_length,arg_name,in_out_flag)
       VALUES('GET_USERLIST' ,2,2,18,'age' , 1) ;   
           
  3.编写代码  
       service>action 
 */
public class MyService {
	/**
	 * 测试JavaBean方式
	 * @param name
	 * @param age
	 * @return
	 * @throws Exception
	 */
	public  String testService(String name , String age) throws Exception {
		
		//设置参数,把参数组装到HashMap中
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("age", age) ;
		
		//JavaBean方式,调用ServiceManager.callJavaBeanService(service , method , param)方法
		Object obj = ServiceManager.callJavaBeanService("MyAction", "testService" , param) ;
		return obj == null ? "" : (String) obj ;
	}
	
	/**
	 * 测试SQL方式
	 * @param name
	 * @param age
	 * @return
	 * @throws Exception
	 */
	public  List testActionSQl(String name , String age) throws Exception {
		//设置参数,把参数组装到HashMap中
		Map param = new HashMap() ;
		param.put("name", name) ;
		param.put("age", age) ;
		
		//SQL方式,调用ServiceManager.callSQLService(service  , param)方法
		Object result = ServiceManager.callSQLService("GET_USERLIST", param) ;
		return result == null ? null : (List) result ;
	}
	
	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args ) throws Exception{
		MyService ft = new MyService() ;
		
		//service方式 结果处理
		String sResult = ft.testService("easonwu" , "1") ;
		System.out.println("处理结果是====="+sResult) ;
		
		//System.out.println("处理结果是====="+ft.checkStaffDefaultPriv("9") ) ;

		//action方式 结果处理
		List result = ft.testActionSQl("easonwu" , "1") ;
		for(int i = 0 ,k = result.size() ; i<k ; i++){
			Map m = (Map) result.get(i) ;
			System.out.println("[id="+m.get("ID") +" , name="+m.get("NAME")+",age="+m.get("AGE") +"]") ;
		}
	}
	
}
