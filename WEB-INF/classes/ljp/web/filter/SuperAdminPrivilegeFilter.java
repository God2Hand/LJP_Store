package ljp.web.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.constant.Constant;
import ljp.domain.Admin;

public class SuperAdminPrivilegeFilter implements Filter {
	
	private List<String> superAdmins;
	private List<String> superAdminsServlet;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		//1.强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//2.逻辑
		//从session中获取用户
		try {
			// 判断当前资源是否需要权限控制。
			String uri = request.getRequestURI();
			String contextPath = request.getContextPath();
			String path = uri.substring(contextPath.length());
			if (!superAdminsServlet.contains(path)) {
				//不包含必过滤名称，检查方法是否需要校验
				
				//1.获取方法名称
				String mName = request.getParameter("method");
				//1.2 判断参数是否为空，若为空，执行默认的方法
				if (mName == null || mName.trim().length() == 0) {
					chain.doFilter(request, response);
					return;
				}
				
				//如果【不等于】等于某些方法，放行。检查是否超管登录（删去）
				if (!superAdmins.contains(mName)) {
					chain.doFilter(request, response);
					return;
				}
			}
			
			//过滤
			Admin admin = (Admin) request.getSession().getAttribute("admin");
			if (!Constant.SUPER.equals(admin.getMid())) {
				request.setAttribute("msg", "只有超级管理员有权限执行该操作！"
						+ "<br />请用超级管理员账户登录，然后才能执行该操作！");
				request.getRequestDispatcher("/admin/welcome.jsp").forward(request, response);
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		//3.放行
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.superAdmins = new ArrayList<String>();
		this.superAdminsServlet = new ArrayList<String>();
		
		fillPath("superAdmins", superAdmins, "method");
		fillPath("superAdmins", superAdminsServlet, "url");
	}
	
	private void fillPath(String baseName, List<String> list, String keyName) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);//可以直接找src下的文件
		String path = bundle.getString(keyName);
		String[] paths = path.split(",");
		for (String p : paths) {
			list.add(p);
		}
	}

}
