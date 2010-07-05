package com.asm.hibernate.osv;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtilOSV;

public class UserDao {
	static void addUseDao(User user) {
		HibernateUtilOSV.getThreadLocalSession().save(user);
	}
}
