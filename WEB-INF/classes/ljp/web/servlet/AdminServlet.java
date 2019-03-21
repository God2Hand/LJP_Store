package ljp.web.servlet;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.constant.Constant;
import ljp.domain.Admin;
import ljp.service.AdminService;
import ljp.utils.BeanFactory;
import ljp.utils.DataSourceUtils;
import ljp.utils.UUIDUtils;
import ljp.utils.ZZUtils;
import ljp.web.servlet.base.BaseServlet;

import org.apache.commons.io.FileUtils;

import sun.misc.BASE64Encoder;

public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 备份 数据库
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String backup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//----------------------------------
			String filename = request.getParameter("filename") + ".sql";
			
			//下载注意事项1：设置下载文件的mimeType
			response.setContentType("application/octet-stream");
			String agent=request.getHeader("user-agent");
			if (agent.contains("MSIE")) {
				// IE浏览器
				filename = URLEncoder.encode(filename, "utf-8");
				filename = filename.replace("+", " ");//utf-8的问题
			} else if (agent.contains("Firefox")) {
				// 火狐浏览器
				BASE64Encoder base64Encoder = new BASE64Encoder();
				filename = "=?utf-8?B?" + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
			}else {
				// 其它浏览器
				filename = URLEncoder.encode(filename, "utf-8");
			}
			//下载注意事项2：永远是下载--设置响应头
			response.setHeader("content-disposition", "attachment;filename=" + filename);

			//---------------------------------
			// 调用 调用mysql的安装目录的命令
			Runtime rt = Runtime.getRuntime();
			Process child = rt.exec(Constant.backup_mysqldumpDir);
			// 设置导出编码为utf-8。这里必须是utf-8
			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStreamReader xx = new InputStreamReader(child.getInputStream(), "utf-8");// 控制台的输出信息作为输入流
			// 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			xx.close();
			br.close();

			// 要用来做导入用的sql目标文件：
			OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "utf-8");
			writer.write(outStr);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "备份失败！");
			return "/admin/br/backup.jsp";
		}
		//request.setAttribute("msg", "备份完成！");
		return null;
	}
	
	/**
	 * 跳转备份数据库页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String backupUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/br/backup.jsp";
	}
	
	/**
	 * 跳转还原数据库页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String restoreUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*这里的超管过滤放过滤器了*/
		return "/admin/br/restore.jsp";
	}
	
	/**
	 * 检查重复
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
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			Admin admin = as.findByMname(value);
			if (admin == null) {
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
	 * 添加管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String mAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			/*这里的超管过滤放过滤器了*/
			String mname = request.getParameter("mname");
			String mpassword = request.getParameter("mpassword");
			
			String regex = "^[a-zA-Z0-9_-]{4,32}$";
			boolean c1 = ZZUtils.check(regex, mpassword);
			regex = "^.{3,32}$";
			boolean c2 = ZZUtils.check(regex, mname);
			if (!(c1 && c2)) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/admin/admin/alist.jsp";
			}
			
			Admin admin = new Admin();
			admin.setMid(UUIDUtils.getId());
			admin.setMname(mname);
			admin.setMpassword(mpassword);
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			as.mAdd(admin);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "改用户名已存在！"
					+ "<br />请使用其他用户名或者返回列表修改密码！");
			return "/admin/admin/add.jsp";
		}
		response.sendRedirect(request.getContextPath() + "/manager?method=findAllNormal");
		return null;
	}
	
	/**
	 * 超管删除普通管理员
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			/*这里的超管过滤放过滤器了*/
			String mid = request.getParameter("mid");
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			as.delete(mid);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "未知异常，删除管理员失败！");
			return "/admin/welcome.jsp";
		}
		return "/manager?method=findAllNormal";
	}
	
	/**
	 * 超管修改普通管理员密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pwdA(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			/*这里的超管过滤放过滤器了*/
			String mid = request.getParameter("mid");
			String mpassword = request.getParameter("mpassword");
			
			//前端校验
			String regex = "^[a-zA-Z0-9_-]{4,32}$";
			boolean c1 = ZZUtils.check(regex, mpassword);
			if (!c1) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/admin/admin/alist.jsp";
			}
			
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			Admin admin = as.pwd(mid, mpassword);
			if (admin == null) {
				request.setAttribute("msg", "发生异常！找不到要修改密码的管理员账号！");
				return "/admin/admin/alist.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "未知异常，修改密码失败！");
			return "/admin/welcome.jsp";
		}
		return "/manager?method=findAllNormal&tips=yes";
	}
	
	/**
	 * 普通管理员列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllNormal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			/*这里的超管过滤放过滤器了*/
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			List<Admin> aList = as.findAllNormal();
			request.setAttribute("aList", aList);
			if ("yes".equalsIgnoreCase(request.getParameter("tips"))) {
				request.setAttribute("msg", "<font color='red'>修改密码成功！</font>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "未知异常，查询管理员失败！"
					+ "<br />请重新登录！");
			return "/admin/welcome.jsp";
		}
		return "/admin/admin/alist.jsp";
	}
	
	/**
	 * 注销
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String mlogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("admin");
		//超管权限注销
		DataSourceUtils.setAdmin(false);
		response.sendRedirect(request.getContextPath() + "/houtai");
		return null;
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String mid = request.getParameter("mid");
			String mpassword = request.getParameter("mpassword");
			
			//前端校验
			String regex = "^[a-zA-Z0-9_-]{4,32}$";
			boolean c1 = ZZUtils.check(regex, mpassword);
			if (!c1) {
				request.setAttribute("msg", "请不要尝试越过检查！");
				return "/admin/welcome.jsp";
			}
			
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			Admin admin = as.pwd(mid, mpassword);
			request.getSession().removeAttribute("admin");
			if (admin == null) {
				request.setAttribute("msg", "发生异常！找不到要修改密码的管理员账号！");
				return "/admin/welcome.jsp";
			}
			//修改session
			request.getSession().setAttribute("admin", admin);
			request.setAttribute("msg", "修改密码成功！下次请记得用新密码登录！");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "未知异常，修改密码失败！");
			return "/admin/welcome.jsp";
		}
		return "/admin/welcome.jsp";
	}
	
	/**
	 * 跳转修改密码页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pwdUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/admin/pwd.jsp";
	}
	
	/**
	 * 管理员登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String mlogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String mname = request.getParameter("mname");
			String mpassword = request.getParameter("mpassword");
			
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			Admin admin = as.mlogin(mname, mpassword);
			//3.判断admin，根据结果返回信息
			if (admin == null) {
				////用户名和密码不匹配
				request.setAttribute("msg", "用户名或者密码错误！");
				//牛逼的话还可以三次错误封锁ip什么的
				return "/houtai/index.jsp";
			}
			//4.登录成功，保存管理员的登录状态
			request.getSession().removeAttribute("admin");
			request.getSession().setAttribute("admin", admin);
			//如果是【超管】，在存入session一个super
			if (Constant.SUPER.equals(admin.getMid())) {
				request.getSession().setAttribute("ljp_super", Constant.SUPER);
				//如果是超管，还可以开启超管的datasource
				DataSourceUtils.setAdmin(true);
			} else {
				request.getSession().removeAttribute("ljp_super");
				DataSourceUtils.setAdmin(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "出现异常！请重新登录或联系维护人员！");
			//牛逼的话还可以三次错误封锁ip什么的
			return "/houtai/index.jsp";
		}
		return "/admin/home.jsp";
	}

}
