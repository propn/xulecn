package org.leixu.student;

import org.leixu.dao.GenericDAO;

public interface StudentDao extends GenericDAO <Student, String>{
	public int getCount();
}
