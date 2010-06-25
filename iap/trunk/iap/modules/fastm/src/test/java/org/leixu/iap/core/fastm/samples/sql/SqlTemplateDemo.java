package org.leixu.iap.core.fastm.samples.sql;

import java.util.HashMap;
import java.util.Map;

import org.leixu.iap.core.fastm.sql.SqlHolder;
import org.leixu.iap.core.fastm.sql.SqlLoader;
import org.leixu.iap.core.fastm.sql.SqlLoaderImpl;
import org.leixu.iap.core.fastm.sql.SqlParam;

public class SqlTemplateDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Name name = new Name();
		User user = new User();
		user.setName(name);
		user.setDepartmentId(1001);

		Map condition = new HashMap();
		condition.put("user", user);
		condition.put("ageLimit", new Integer(30));

		SqlLoader sqlLoader = new SqlLoaderImpl();
		Class clazz = SqlTemplateDemo.class;
		String resourceName = clazz.getPackage().getName().replace('.', '/') 
			+ "/userInfo.sql";
		SqlHolder sqlHolder = sqlLoader.load(clazz, resourceName);

		SqlParam sqlParam = sqlHolder.getSqlParam(condition);
		System.out.println(sqlParam.getSql());

		name.setFirstName("warren");
		sqlParam = sqlHolder.getSqlParam(condition);
		System.out.println(sqlParam.getSql());

		name.setLastName("wang");
		sqlParam = sqlHolder.getSqlParam(condition);
		System.out.println(sqlParam.getSql());
	}
	/** output is 
select * from user_table1 
where 
dempartment_id = 1001
and age > ?

select * from user_table1 
where 
dempartment_id = 1001
and age > ?
and first_name = ?

select * from user_table1 
where 
dempartment_id = 1001
and age > ?
and first_name = ?
and last_name = 'wang'
	 */
}
