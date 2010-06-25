package org.leixu.iap.core.fastm.samples.sql;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.leixu.iap.core.fastm.DynamicPart;
import org.leixu.iap.core.fastm.HelperParser;
import org.leixu.iap.core.fastm.sql.SqlHolder;
import org.leixu.iap.core.fastm.sql.SqlHolderImpl;
import org.leixu.iap.core.fastm.sql.SqlLoader;
import org.leixu.iap.core.fastm.sql.SqlLoaderImpl;
import org.leixu.iap.core.fastm.sql.SqlParam;

public class ManySqlsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Name name = new Name();
		name.setFirstName("warren");
		name.setLastName("wang");
		User user = new User();
		user.setName(name);
		user.setDepartmentId(1001);

		Map condition = new HashMap();
		condition.put("user", user);
		condition.put("ageLimit", new Integer(30));

		SqlLoader sqlLoader = new SqlLoaderImpl();
		Class clazz = SqlTemplateDemo.class;

		String resourceName = clazz.getPackage().getName().replace('.', '/') 
			+ "/many.sql";
		InputStream inputStream = clazz.getClassLoader().getResourceAsStream(resourceName);
		if(inputStream == null) throw new RuntimeException("resource " + resourceName + " not exists in the class path");

		DynamicPart sqlsTemplate = HelperParser.parse(inputStream);
		
		List sqlsTemplateList = sqlsTemplate.getDynamicChildren();
		for(int i = 0; i < sqlsTemplateList.size(); i++){
			DynamicPart sqlTemplate = (DynamicPart)sqlsTemplateList.get(i);
			SqlHolder sqlHolder = new SqlHolderImpl(sqlTemplate);

			SqlParam sqlParam = sqlHolder.getSqlParam(condition);
			System.out.println(sqlTemplate.getName());
			System.out.println(sqlParam.getSql());
		}
	}

}
