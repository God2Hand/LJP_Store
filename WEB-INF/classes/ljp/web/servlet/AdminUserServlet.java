package ljp.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import ljp.domain.User;
import ljp.service.UserService;
import ljp.utils.BeanFactory;
import ljp.utils.ZZUtils;
import ljp.web.servlet.base.BaseServlet;

public class AdminUserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 更新用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			
			//前端校验
			String regex = "^[a-zA-Z0-9_-]{4,20}$";
			boolean c1 = ZZUtils.check(regex, user.getUsername());
			boolean c2 = ZZUtils.check(regex, user.getPassword());
			regex = "^.{1,20}$";
			boolean c3 = ZZUtils.check(regex, user.getName());
			regex = "^1[34578]\\d{9}$";
			boolean c4 = ZZUtils.check(regex, user.getTelephone());
			if (!(c1 && c2 && c3 && c4)) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/admin/welcome.jsp";
			}
			
			UserService us = (UserService) BeanFactory.getBean("UserService");
			us.update_p_n_t_s(user);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			response.getWriter().write("有些字段不能为空！");
			return null;
		}
		response.sendRedirect(request.getContextPath() + "/adminUser?method=findAll");
		return null;
	}

	/**
	 * 跳转到用户编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String uid = request.getParameter("uid");
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = us.getById(uid);
			request.setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，编辑失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/user/edit.jsp";
	}

	/**
	 * 用户列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserService us = (UserService) BeanFactory.getBean("UserService");
			List<User> uList = us.findAll();
			request.setAttribute("uList", uList);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
			request.setAttribute("msg", "未知异常，打开失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/user/list.jsp";
	}
}
