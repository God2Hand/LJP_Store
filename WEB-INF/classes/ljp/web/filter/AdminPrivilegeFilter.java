package ljp.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ljp.domain.Admin;

public class AdminPrivilegeFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		//1.强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//1.获取方法名称
		String mName = request.getParameter("method");
		//1.2 判断参数是否为空，若为空，执行默认的方法
		if (mName == null || mName.trim().length() == 0) {
			chain.doFilter(request, response);
			return;
		}
		//如果【不等于】等于某些方法，放行。检查是否超管登录（删去）
		if ("mlogin".equals(mName)) {
			chain.doFilter(request, response);
			return;
		}
		
		//2.逻辑
		//从session中获取用户
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		
		if (admin == null) {
			//未登录
			request.setAttribute("msg", "请先登录~~~~~~~~~~~~");
			request.getRequestDispatcher("/houtai/index.jsp").forward(request, response);
			return;
		}
		
		//3.放行
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
