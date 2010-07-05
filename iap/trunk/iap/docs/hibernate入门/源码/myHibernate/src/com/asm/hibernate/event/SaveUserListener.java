package com.asm.hibernate.event;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;

import com.asm.hibernate.domain.User;

public class SaveUserListener implements SaveOrUpdateEventListener {

	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		if (event.getObject() instanceof com.asm.hibernate.domain.User) {
			User user = (User) event.getObject();
			System.out.println("find save User:" + user.getName());
			user.setDate(new Date());
		}
	}
}
