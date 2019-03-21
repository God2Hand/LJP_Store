package ljp.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.constant.Constant;
import ljp.domain.User;
import ljp.service.UserService;
import ljp.utils.BeanFactory;
import ljp.utils.UUIDUtils;
import ljp.utils.ZZUtils;
import ljp.web.servlet.base.BaseServlet;

import org.apache.commons.beanutils.BeanUtils;
/**
 * 用户模块
 * @author Shinelon
 */

public class UserServler extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 邮箱查重复
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String checkEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//0.设置响应编码
			response.setContentType("text/html;charset=utf-8");
			String value = request.getParameter("value");
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = us.findByEmail(value);
			if (user == null) {
				response.getWriter().write("√");
			} else {
				response.getWriter().write("该邮箱已被使用！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("该邮箱不可用！");
		}
		return null;
	}
	
	/**
	 * 用户名是否重复
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String checkRepeat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//0.设置响应编码
			response.setContentType("text/html;charset=utf-8");
			String value = request.getParameter("value");
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = us.findByUsername(value);
			if (user == null) {
				response.getWriter().write("√");
			} else {
				response.getWriter().write("该用户名已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("该用户名不可用！");
		}
		return null;
	}

	/**
	 * 忘记密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String forgetPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//验证码
			// 获取session中的验证码
			String identifyingCode = (String)request.getSession().getAttribute("identifyingCode");
			// 获取表单中的验证码
			String code = request.getParameter("identifyingCode");
			// 判断是否相同（忽略大小写）
			if (code == null || !identifyingCode.equalsIgnoreCase(code)) {
				// 如果不相同，返回错误的信息
				request.setAttribute("msg", "&nbsp;&nbsp;验证码输入错误！");
				return "/jsp/forgetPwd.jsp";
			}
			
			String uid = request.getParameter("uid");
			UserService us = (UserService) BeanFactory.getBean("UserService");
			us.forgetPwd(uid);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "邮件发送失败了！您留下的邮箱不正确 或者 服务器内部错误！");
			return "/jsp/forgetPwd.jsp";
		}
		request.setAttribute("msg", "已向您的邮箱发送邮件，请注意查收！"
				+ "<br />建议收到邮件后修改密码！！");
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String changePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			String uid = request.getParameter("uid");
			String password = request.getParameter("password");
			String repassword = request.getParameter("repassword");
			
			//前端校验
			String regex = "^[a-zA-Z0-9_-]{4,20}$";
			boolean c2 = ZZUtils.check(regex, repassword);
			if (!c2) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/jsp/changePwd.jsp";
			}
			
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = us.changePwd(uid,password,repassword);
			if (user == null ) {
				request.setAttribute("msg", "旧密码输入错误！");
				return "/jsp/changePwd.jsp";
			}
			//修改session
			request.getSession().removeAttribute("user");
			request.getSession().setAttribute("user", user);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "发生未知错误，密码修改失败！");
			return "/jsp/msg.jsp";
		}
		request.setAttribute("msg", "修改密码成功！下次登录请记得使用新密码登录!");
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
		return null;
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//验证码
			// 获取session中的验证码
			String identifyingCode = (String)request.getSession().getAttribute("identifyingCode");
			// 获取表单中的验证码
			String code = request.getParameter("identifyingCode");
			// 判断是否相同（忽略大小写）
			if (code == null || !identifyingCode.equalsIgnoreCase(code)) {
				// 如果不相同，返回错误的信息
				request.setAttribute("msg", "&nbsp;&nbsp;验证码输入错误！");
				return "/jsp/login.jsp";
			}			
			
			//1.获取用户名和密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			//2.调用service玩层登录，返回值user
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = us.login(username, password);
			
			//3.判断user，根据结果返回信息
			if (user == null) {
				//用户名和密码不匹配
				request.setAttribute("msg", "用户名或密码不正确");
				return "jsp/login.jsp";
			}
			//若用户不为空，继续判断是否激活
			if (Constant.USER_IS_ACTIVE != user.getState()) {
				//未激活
				request.setAttribute("msg", "请先激活再登录");
				return "jsp/msg.jsp";
			}
			//登录成功，保存用户的登录状态
			request.getSession().setAttribute("user", user);
			
			////拓展：记住用户名////
			//判断是否勾选了记住用户名
			if (Constant.SAVE_NAME.equals(request.getParameter("savename"))) {
				Cookie cookie = new Cookie("saveName", URLEncoder.encode(username, "utf-8"));
				cookie.setMaxAge(Integer.MAX_VALUE);
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
			}
			////////////////////
			
			//跳转到index.jsp
			response.sendRedirect(request.getContextPath());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "用户登录失败！");
			return "jsp/msg.jsp";
		}
		return null;
	}
	
	/**
	 * 跳转到登录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}

	/**
	 * 用户激活
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.接收code
			String code = request.getParameter("code");
			
			//2.调用service，完成激活，返回值User
			UserService us = (UserService) BeanFactory.getBean("UserService");
			User user = us.active(code);
			
			//3.判断User，生成不同的提示信息
			if (user == null) {
				//没有找到这个用户，激活失败
				request.setAttribute("msg", "激活失败！请重新激活或者注册！");
				return "/jsp/msg.jsp";
			}
			
			//激活成功！
			request.setAttribute("msg", "恭喜您！激活成功！可以登录了");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "激活失败！请重新激活或者注册！");
			return "/jsp/msg.jsp";
		}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.封装对象
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			
			//验证码
			// 获取session中的验证码
			String identifyingCode = (String)request.getSession().getAttribute("identifyingCode");
			// 获取表单中的验证码
			String code = request.getParameter("identifyingCode");
			// 判断是否相同（忽略大小写）
						if (code == null || !identifyingCode.equalsIgnoreCase(code)) {
				// 如果不相同，返回错误的信息
				request.setAttribute("msg", "验证码输入错误！");
				return "/jsp/register.jsp";
			}
			
			//前端校验
			String regex = "^[a-zA-Z0-9_-]{4,20}$";
			boolean c1 = ZZUtils.check(regex, user.getUsername());
			boolean c2 = ZZUtils.check(regex, user.getPassword());
			regex = "^.{1,20}$";
			boolean c3 = ZZUtils.check(regex, user.getName());
			regex = "^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$";
			boolean c4 = ZZUtils.check(regex, user.getEmail());
			regex = "^\\d{4}(\\-)\\d{1,2}\\1\\d{1,2}$";
			boolean c5 = ZZUtils.check(regex, user.getBirthday());
			if (!(c1 && c2 && c3 && c4 && c5)) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/jsp/register.jsp";
			}
			
			
			//1.2 手动封装uid， state, code
			user.setUid(UUIDUtils.getId());
			user.setState(Constant.USER_IS_NOT_ACTIVE);
			user.setCode(UUIDUtils.getCode());
			
			//2.调用service完成注册
			UserService us = (UserService) BeanFactory.getBean("UserService");
			us.regist(user);
			
			//3.页面转发，提示信息
			request.setAttribute("msg", "恭喜您！注册成功！请登录邮箱完成激活！");
		} catch (Exception e) {
			e.printStackTrace();
			//转发到msg.jsp
			request.setAttribute("msg", "用户注册失败！非可用邮箱！！");
			return "/jsp/msg.jsp";
		}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

}
