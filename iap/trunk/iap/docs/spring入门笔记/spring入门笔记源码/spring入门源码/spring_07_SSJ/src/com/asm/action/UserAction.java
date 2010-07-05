package com.asm.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.cache.EhCache;
import org.hibernate.cache.EhCacheProvider;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.asm.entity.User;
import com.asm.service.UserService;

public class UserAction extends Action {
	@Resource(name="userServiceBean")
	private UserService us;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// WebApplicationContext ctx = WebApplicationContextUtils
		// .getWebApplicationContext(this.servlet.getServletContext());
		// UserDao ud = (UserDao) ctx.getBean("userDaoImp");
		List<User> users = us.getUsers();// ud.getUsers();
		for (User user : users) {
			System.out.println(user.getName());
		}
		request.setAttribute("users", users);
		return mapping.findForward("success");
	}
}
